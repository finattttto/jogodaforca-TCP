package org.example.Cliente;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Jogador {

    private BufferedReader receber;
    private BufferedWriter enviar;
    private String username;
    private Socket socket;
    private Boolean dica;
    private Integer vidas;

    public Boolean taNaHoraDeJogar = false;

    public Jogador( String username, Socket socket, Boolean dica, Integer vidas ) {
        try {
            this.socket = socket;
            this.receber = new BufferedReader( new InputStreamReader( socket.getInputStream( ) ) );
            this.enviar = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream( ) ) );
            this.username = username;
            this.dica = dica;
            this.vidas = vidas;
        } catch ( Exception e ) {
            fechaTudo( socket, receber, enviar );
        }
    }

    public static void main( String[] args ) throws IOException {
        Scanner scan = new Scanner( System.in );
        System.out.println( "Digite seu nome de usuário: " );
        String username = scan.nextLine( );
        Socket socket = new Socket( "192.168.19.22", 8080 );
        Jogador jogador = new Jogador( username, socket, false, 5 );

        jogador.receberMsg( );
        jogador.enviarMsg( );
    }

    public void enviarMsg( ) {
        try {
            enviar.write( username );
            enviar.newLine( );
            enviar.flush( );
            Scanner scan = new Scanner( System.in );
            while ( socket.isConnected( ) ) {
               if(taNaHoraDeJogar){
                   System.out.println("Sua escolha" );
                   String msg = scan.nextLine( );
                   enviar.write( username + ": " + msg );
                   enviar.newLine( );
                   enviar.flush( );
                   taNaHoraDeJogar = false;
               }
            }
        } catch ( IOException e ) {
            fechaTudo( socket, receber, enviar );
        }
    }

    public void receberMsg( ) {
        new Thread( new Runnable( ) {
            @Override
            public void run( ) {
                String msgDoChat;
                while ( socket.isConnected( ) ) {
                    try {
                        msgDoChat = receber.readLine( );
                        System.out.println(msgDoChat );
                        if(msgDoChat.contains( username )){
                            System.out.println("Ta na hora de jogar" );
                            taNaHoraDeJogar = true;
                        }
                        System.out.println( msgDoChat );
                    } catch ( IOException e ) {
                        fechaTudo( socket, receber, enviar );
                    }
                }
            }
        } ).start( );
    }

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
