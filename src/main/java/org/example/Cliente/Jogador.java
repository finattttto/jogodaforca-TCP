package org.example.Cliente;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

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
        Socket socket = new Socket( "26.23.23.20", 8080 );
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
            System.out.println( "Sua escolha" );
            String msg = scan.nextLine( );
            enviar.write( username + ": " + msg );
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
                        } else {
                            System.out.println(msgDoChat);
                            if (msgDoChat.contains(username)) {
                                System.out.println("Ta na hora de jogar " + username);
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
}
