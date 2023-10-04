package org.example.Cliente;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Jogador {

    private static BufferedReader receber;
    private static BufferedWriter enviar;
    private static String username;
    private Socket socket;
    private Boolean dica;
    private Integer vidas;

    public Boolean taNaHoraDeJogar = false;

    public Jogador( String username, Socket socket, Boolean dica, Integer vidas ) {
        try {
            this.socket = socket;
            receber = new BufferedReader( new InputStreamReader( socket.getInputStream( ) ) );
            enviar = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream( ) ) );
            Jogador.username = username;
            this.dica = dica;
            this.vidas = vidas;
        } catch ( Exception e ) {
            fechaTudo( socket, receber, enviar );
        }
    }

    /* PARTE DO JOGADOR
     *  Inicia a conexao com o servidor e instancia um novo jogador
     * */
    public static void main( String[] args ) throws IOException {
        Scanner scan = new Scanner( System.in );
        System.out.println( "Digite seu nome de usuário: " );
        String username = scan.nextLine( );
        Socket socket = new Socket( "192.168.19.22", 8080 );
        Jogador jogador = new Jogador( username, socket, false, 5 );
        cadastrar( username );
        jogador.receberMsg( );
    }

    public static void cadastrar( String username ) {
        try {
            enviar.write( username );
            enviar.newLine( );
            enviar.flush( );
        } catch ( IOException ex ) {
            System.out.println( "erro ao cadastrar" );
        }

    }

    // Função pra enviar a mensagem
    public void enviarMsg( ) {
        try {
            Scanner scan = new Scanner( System.in );
            System.out.println( "\nEscolhe uma opção: \n" +
                "1 - Chuter Letra\n" +
                "2 - Dica\n" );

            String msg = "";

            switch ( scan.nextInt( ) ) {
                case 1:
                    System.out.print("Escolha uma letra: " );
                    msg = "T | " + scan.next();
                    break;
                case 2:
                    System.out.println("Solicitando dica...!" );
                    msg = "D | ";
                    break;
                default:
                    System.out.println("Opção inválida. Perdeu a vez otario" );
                    break;

            }
            enviar.write( msg );
            enviar.newLine( );
            enviar.flush( );
        } catch ( IOException e ) {
            fechaTudo( socket, receber, enviar );
        }
    }

    // Thread que vai ficar aguardando a hora do jogador
    public void receberMsg( ) {
        new Thread( new Runnable( ) {
            @Override
            public void run( ) {
                String msgDoChat;
                while ( socket.isConnected( ) ) {
                    try {
                        msgDoChat = receber.readLine();
                        if(msgDoChat == null){
                            continue;
                        }else if(msgDoChat.contains( "ConectionVerify" )){
                            enviar.write( "Online" );
                            enviar.newLine( );
                            enviar.flush( );
                        } else if ( msgDoChat.contains( "D | " ) ) {
                            String[] dica = msgDoChat.split( "\\|" );
                            System.out.println("A dica para a palavra é: "+dica[1]);
                        } else if (msgDoChat.contains( "R | " )) {
                            // Aqui é o retorno quando tentar um chute
                            String[] partes = msgDoChat.split("\\|");
                            String retorno = partes[1].trim();
                            String progressoAdivinhacao = partes[2].trim();
                            String letrasJaUsadas = partes[3].trim();
                            String vidas = partes[4].trim();
                            
                            System.out.println(retorno);
                            
                            System.out.println( "\nProgresso na adivinhação: " );
//                            for ( Object i : progressoAdivinhacao ) {
//                                System.out.print( i + " " );
//                            }
                            System.out.println(progressoAdivinhacao);
                            
                            System.out.println( "------------ Letras já usadas ---------------" );
                            System.out.println( letrasJaUsadas );
                            System.out.println( "---------------------------------------------" );
                            
                            System.out.println(vidas);
                            
                            desenharPessoaNaForca( Integer.parseInt( extrairNumero(vidas) ), username );
                            
                        } else if (msgDoChat.contains( "RO | " )) {
                            // Aqui é o retorno tenta um chute, porem pros outros jogadores saber oq aconteceu
                            String[] partes = msgDoChat.split( "\\|" );
                            String retorno = partes[1].trim();
                            
                            System.out.println( retorno );
                        }else {
                            System.out.println(msgDoChat);
                            if (msgDoChat.contains(username)) {
                                enviarMsg();
                            } else {
                                System.out.println("ta na hora do amiguinho");
                            }
                        }
                    } catch ( IOException e ) {
                        fechaTudo( socket, receber, enviar );
                    }
                }
            }
        } ).start( );
    }

    // Fecha todas as conexoes
    public void fechaTudo( Socket socket, BufferedReader receber, BufferedWriter enviar ) {
        try {
            if ( socket != null ) socket.close( );
            if ( enviar != null ) enviar.close( );
            if ( receber != null ) receber.close( );
        } catch ( IOException e ) {
            e.printStackTrace( );
        }
    }
    
    private String extrairNumero(String texto) {
        Pattern pattern = Pattern.compile("\\d+"); // Procura uma sequência de dígitos
        Matcher matcher = pattern.matcher(texto);
        
        if (matcher.find()) {
            return matcher.group(); // Retorna o primeiro número encontrado na string
        } else {
            return "0"; // Retorna 0 se nenhum número for encontrado
        }
    }

    private String desenharPessoaNaForca( int vidas, String username) {
        String desenhoForca = "";

        //Passa por cada linha do desenho e desenha as partes necessarias do personagem a partir das tentativas

        System.out.println( "\n" );
        System.out.println( " _______" );
        System.out.println( " |     |" );

        desenhoForca += "\n" ;
        desenhoForca +=  " _______" ;
        desenhoForca +=  " |     |" ;

        if ( vidas <= 6 ) {
            System.out.println( " |     O" );
            desenhoForca +=  "\n |     O" ;
        }
        if ( vidas == 6 ) {
            System.out.println( " |      " );
            desenhoForca +=  "\n |      " ;
        }
        if ( vidas == 5 ) {
            System.out.println( " |     |" );
            System.out.println( " |     |" );

            desenhoForca +=  "\n |     |" ;
            desenhoForca +=  "\n |     |" ;
        }
        if ( vidas == 4 ) {
            System.out.println( " |    /|" );
            System.out.println( " |     |" );

            desenhoForca += "\n |    /|" ;
            desenhoForca += "\n |     |" ;
        }
        if ( vidas <= 3 ) {
            System.out.println( " |    /|\\" );
            System.out.println( " |     |" );

            desenhoForca += "\n |    /|\\" ;
            desenhoForca +=  "\n |     |" ;
        }
        if ( vidas == 2 ) {
            System.out.println( " |    / " );

            desenhoForca += "\n |    / " ;
        }
        if ( vidas <= 1 ) {
            System.out.println( " |    / \\" );

            desenhoForca +=  "\n |    / \\" ;
        }

        System.out.println( " |      " );
        System.out.println( "_|______________" );

        desenhoForca +=  "\n |      " ;
        desenhoForca +=  "\n_|______________" ;

        if ( vidas == 0 ) {
            System.out.println(username + " MORREU! " );

            desenhoForca +=  "\n" + username + " MORREU! " ;
        }

        return desenhoForca;

    }
}
