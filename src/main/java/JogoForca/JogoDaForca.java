package JogoForca;

import org.example.Receptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JogoDaForca {

    private static String palavra;
    private Sorteador sorteador = new Sorteador();

    private static String letrasJaUsadas = "";

    private static List<String> progressoAdivinhacao = new ArrayList<>();

    public JogoDaForca(  ) {
        palavra = sorteador.geraPalavra("facil");
    }

    public String chute(String frame, Receptor p){
        // verifica

        //extrai a letra/palavra do chute
        String letra = frame.substring(3);
        System.out.println("letra/palavra do chute: " + letra);

        //string que receberá tudo que será enviado aos players após o chute
        String retorno = "";

        if ( letra.length( ) == 1 ) {

            if ( letrasJaUsadas.contains( letra ) ) {
                System.out.println( "A letra " + letra + " já foi informada." );
                retorno += "A letra " + letra + " já foi informada." ;
            }

            letrasJaUsadas += letra + " ";
            // Verifica a existencia da letra dentro da palavra sorteada:
            if ( palavra.contains( letra ) ) {//caso a palavra contenha a letra, passa para a proxima parte

                for ( int i = 0; i < palavra.length( ); i++ ) {
                    //Percorre cada letra da palavra com substrings
                    //caso a letra esteja na palavra,
                    //Altera o mesmo index no progresso de adivinhação
                    //Substituindo-o pela letra
                    if ( palavra.substring( i, i + 1 ).equals( letra ) ) {
                        progressoAdivinhacao.set( i, letra );
                    }
                }
                //O Progresso de adivinhacao contem o caracter _ em letras que nao foram adivinhadas
                //caso o progresso de adivinhacao nao contenha nenhum _, mostra a vitoria do jogador
                if(!progressoAdivinhacao.toString().contains( "_" )){
                    System.out.println( p.getUsername() + " VENCEU!!! A palavra era: "+palavra );
                    retorno += p.getUsername() + " VENCEU!!! A palavra era: "+palavra ;
                }
                System.out.println( "Você acertou uma letra!!\n" );
            } else {
                p.setVidas( p.getVidas() - 1);
                if (  p.getVidas() == 0 ) {
                    System.out.println( p.getUsername() + " perdeu! :(" );
                    System.out.println( "A palavra era: " + palavra );
                }
                System.out.println( "Você errou :(\n" );
            }

            //Comparando para palavra completa (letra = palavra adivinhada pelo jogador)
        } else if ( letra.length( ) > 1 && letra.length( ) == palavra.length( ) ) {
            if ( palavra.equals( letra ) ) {
                System.out.println( p.getUsername() + " VENCEU!!! A palavra era: "+palavra );
                retorno += p.getUsername() + " VENCEU!!! A palavra era: "+palavra ;

            } else {
                System.out.println( "Palavra Incorreta!" );
                p.setVidas( p.getVidas() - 1);
            }
        } else { //Caso tenha inserido mais de uma letra ou uma palavra que não seja do tamanho correto, informa um erro
            System.out.println( "Digite uma letra, ou uma palavra com o tamanho correto!" );
        }

        // Chama a função que imprime a forca
        retorno += desenharPessoaNaForca( p.getVidas(), p.getUsername());

        // Mostra as letras acertadas
        System.out.println( "Progresso na adivinhação: " );
        for ( Object i : progressoAdivinhacao ) {
            System.out.print( i + " " );
            retorno += i + " " ;
        }

        // Mostra quantas vidas o jogador ainda tem
        System.out.println( p.getUsername() + " tem mais " + p.getVidas() + " vidas! " );

        System.out.println( "------------ Letras já usadas ---------------" );
        System.out.println(letrasJaUsadas);
        System.out.println( "---------------------------------------------" );

        //Adicionando ao retorno
        retorno +=  p.getUsername() + " tem mais " + p.getVidas() + " vidas! " ;

        retorno += "------------ Letras já usadas ---------------" ;
        retorno += letrasJaUsadas;
        retorno += "---------------------------------------------" ;

        return retorno;
    }

    public String dica(String palavra){
        // envia a dica
        return "Letra A";
    }


    private static void playGame(String palavra, List palavraQuebrada, List progressoAdivinhacao, int tentativasJogador) {

        int maxTentativas = 7;


        // Imprimindo a quantidade de campos da palavra sorteada
        for ( Object i : progressoAdivinhacao ) {
            System.out.print( i + " " );
        }

        Scanner sc = new Scanner( System.in );

        //loop da adivinhação do jogador
        while ( maxTentativas > 0 ) {

            System.out.print( "\nEscolha uma letra (ou tente adivinhar a palavra): " );
            String letra = sc.nextLine( );

            //Comparando para apenas uma letra


            //Imprime como está a palavra
            System.out.println( "Progresso na adivinhação: " );
            for ( Object i : progressoAdivinhacao ) {
                System.out.print( i + " " );
            }

            // Chama a função que imprime a forca
            desenharPessoaNaForca( maxTentativas , "");

            // Mostra quantas vidas o jogador ainda tem
            System.out.println( "Você tem mais " + maxTentativas + " vidas! " );

            System.out.println( "------------ Letras já usadas ---------------" );
            System.out.println(letrasJaUsadas);
            System.out.println( "---------------------------------------------" );
        }
    }

    private static String desenharPessoaNaForca( int tentativa, String username) {
        String desenhoForca = "";

        //Passa por cada linha do desenho e desenha as partes necessarias do personagem a partir das tentativas

        System.out.println( "\n" );
        System.out.println( " _______" );
        System.out.println( " |     |" );

        desenhoForca += "\n" ;
        desenhoForca +=  " _______" ;
        desenhoForca +=  " |     |" ;

        if ( tentativa <= 6 ) {
            System.out.println( " |     O" );
            desenhoForca +=  "\n |     O" ;
        }
        if ( tentativa == 6 ) {
            System.out.println( " |      " );
            desenhoForca +=  "\n |      " ;
        }
        if ( tentativa == 5 ) {
            System.out.println( " |     |" );
            System.out.println( " |     |" );

            desenhoForca +=  "\n |     |" ;
            desenhoForca +=  "\n |     |" ;
        }
        if ( tentativa == 4 ) {
            System.out.println( " |    /|" );
            System.out.println( " |     |" );

            desenhoForca += "\n |    /|" ;
            desenhoForca += "\n |     |" ;
        }
        if ( tentativa <= 3 ) {
            System.out.println( " |    /|\\" );
            System.out.println( " |     |" );

            desenhoForca += "\n |    /|\\" ;
            desenhoForca +=  "\n |     |" ;
        }
        if ( tentativa == 2 ) {
            System.out.println( " |    / " );

            desenhoForca += "\n |    / " ;
        }
        if ( tentativa <= 1 ) {
            System.out.println( " |    / \\" );

            desenhoForca +=  "\n |    / \\" ;
        }

        System.out.println( " |      " );
        System.out.println( "_|______________" );

        desenhoForca +=  "\n |      " ;
        desenhoForca +=  "\n_|______________" ;

        if ( tentativa == 0 ) {
            System.out.println(username + " MORREU! " );

            desenhoForca +=  "\n" + username + " MORREU! " ;
        }

        return desenhoForca;

    }
    public String getPalavra() {
        return palavra;
    }
}
