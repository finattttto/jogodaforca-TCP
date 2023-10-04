package org.example;

import JogoForca.JogoDaForca;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class Receptor implements Runnable {
    private Socket socket;
    private BufferedReader receber;
    private BufferedWriter enviar;
    private String username;
    private int vidas;
    private Boolean dica;

    public static ArrayList<Receptor> players = new ArrayList<>( );
    public static JogoDaForca jogo = new JogoDaForca();


    @Override
    public void run( ) {
        int index = 0;
        String msg;
        boolean jogoEmAndamento = false;
        while ( socket.isConnected( ) ) {
            if(players.size() < 2 && !jogoEmAndamento){
                continue;
            }
            jogoEmAndamento = true;
            try {

//               try{
//                   players.get( index ).socket.setSoTimeout( 3000 );
//                   players.get( index ).enviar.write( "ConectionVerify" );
//                   players.get( index ).enviar.newLine( );
//                   players.get( index ).enviar.flush( );
//                   String resposta = players.get( index ).receber.readLine();
//                   System.out.println( players.get( index ).username + resposta );
//               } catch ( SocketTimeoutException ex ){
//                   System.out.println(players.get( index ).username + " desconectou" );
//                   players.remove( index );
//                   index--;
//                   continue;
//               }

                players.get( index ).enviar.write( "Sua vez " + players.get( index ).username );
                players.get( index ).enviar.newLine( );
                players.get( index ).enviar.flush( );
                msg = players.get( index ).receber.readLine( );
                System.out.println( "Mensagem recebida do " + players.get( index ).username + "  " + msg );

                verificador( msg, players.get( index ));

                if ( index < players.size( ) - 1 ) index++;
                else index = 0;

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
            this.vidas = 7;
            this.dica = false;
            this.username = receber.readLine( );
            this.vidas = 7;
            this.dica = false;
            players.add( this );
        } catch ( IOException e ) {
            fechaTudo( socket, receber, enviar );
        }
    }


    public void verificador( String frame, Receptor p ) {
        if ( frame.contains( "T | " ) ) {
            System.out.println( "Tentativa do jogador..." );
            String resultChute = jogo.chute(frame);
            String retorno;
            if (resultChute.contains("Palavra Incorreta!") || resultChute.contains( "Você errou" )){
                players.get(players.indexOf(p)).vidas = p.getVidas() - 1;

                retorno = "Errrrouuuuu";
            }else {
                retorno = "Letra correta";
            }
            //Aqui vai retornar resultChute para os jogadores, para que saibam o que aconteceu
            try {
                p.enviar.write( retorno );
                p.enviar.newLine( );
                p.enviar.flush( );
            } catch ( IOException e ) {
                fechaTudo( socket, receber, enviar );
            }

        } else if ( frame.contains( "P | " ) ) {
            System.out.println( "Tentativa de adivinhar a palavra..." );
        } else if ( frame.contains( "D | " ) ) {
            String dica = jogo.dica();
            try {
                p.enviar.write( "D | " + dica );
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

    public void validaRetorno(String retorno){

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


    public String getUsername() {
        return username;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public Boolean getDica() {
        return dica;
    }

    public void setDica(Boolean dica) {
        this.dica = dica;
    }
}
