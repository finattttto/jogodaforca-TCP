package org.example;

import org.example.Cliente.Jogador;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

class Receptor implements Runnable {
    private Socket socket;
    private BufferedReader receber;
    private BufferedWriter enviar;
    private String username;

    public static ArrayList<Receptor> players = new ArrayList<>( );
    public static JogoDaForca jogo = new JogoDaForca( );


    @Override
    public void run( ) {
        String msg;
        while ( socket.isConnected( ) ) {
            if(players.size() < 1){
                continue;
            }
            try {
                for ( Receptor p : players ) {
                    p.enviar.write( "Sua vez "+p.username );
                    p.enviar.newLine();
                    p.enviar.flush();
                    msg = receber.readLine( );
                    System.out.println("Mensgem recebida do "+p.username+"  " +msg);
//                    verificador( msg, p );
                }
            } catch ( Exception e ) {
                fechaTudo( socket, receber, enviar );
            }
        }
    }

    public Receptor( Socket socket ) {
        try {
            this.socket = socket;
            this.receber = new BufferedReader( new InputStreamReader( socket.getInputStream( ) ) );
            this.enviar = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream( ) ) );
            this.username = receber.readLine( );
            players.add( this );
        } catch ( IOException e ) {
            fechaTudo( socket, receber, enviar );
        }
    }


    public void verificador( String frame, Receptor p ) {
        if ( frame.contains( "T | " ) ) {
            System.out.println( "Tentativa do jogador..." );
            jogo.chute( frame );
        } else if ( frame.contains( "P | " ) ) {
            System.out.println( "Tentativa de adivinhar a palavra..." );
        } else if ( frame.contains( "D | " ) ) {
            String dica = jogo.dica( );
            try {
                p.enviar.write( dica );
                p.enviar.newLine( );
                p.enviar.flush( );
            } catch ( IOException e ) {
                fechaTudo( socket, receber, enviar );
            }

            System.out.println( "Receptor solicitando a dica..." );
        } else {
            System.out.println( "Nenhuma das opcoes..." );
        }
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
