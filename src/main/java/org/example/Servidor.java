package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private ServerSocket socket;

    public Servidor(ServerSocket socket) {
        this.socket = socket;
    }

    public void iniciarSessao() throws IOException {
        while(!socket.isClosed()){
            Socket conexao = socket.accept();
            System.out.println("Um cliente se conectou!");
            Receptor gc = new Receptor(conexao);
            Thread t = new Thread(gc);
            t.start();
            System.out.println("Thread iniciada" );
        }
    }

    public void fecharSessao() throws IOException{
        if(socket != null){
            socket.close();
        }
    }

    public static void main(String[] args) throws IOException{
        ServerSocket socket = new ServerSocket(8080);
        Servidor servidor = new Servidor(socket);
        System.out.println("Servidor subiu!");
        servidor.iniciarSessao();
    }
}
