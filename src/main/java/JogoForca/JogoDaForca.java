package JogoForca;

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

    public void chute(String frame, int tentativas){
        // verifica

        String letra = frame.substring(3);
        System.out.println("letra/palavra do chute: " + letra);

        if ( letra.length( ) == 1 ) {

            if ( letrasJaUsadas.contains( letra ) ) {
                System.out.println( "A letra " + letra + " já foi informada." );
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
                    System.out.println( "VOCÊ VENCEU!!! A palavra era: "+palavra );
                    return;
                }
                System.out.println( "Você acertou uma letra!!\n" );
            } else {
                tentativas--;
                if ( tentativas == 0 ) {
                    System.out.println( "Você perdeu! :(" );
                    System.out.println( "A palavra era: " + palavra );
                }
                System.out.println( "Você errou :(\n" );
            }

            //Comparando para palavra completa (letra = palavra adivinhada pelo jogador)
        } else if ( letra.length( ) > 1 && letra.length( ) == palavra.length( ) ) {
            if ( palavra.equals( letra ) ) {
                System.out.println( "VOCÊ VENCEU!!! A palavra era: "+palavra );
                return;
            } else {
                System.out.println( "Palavra Incorreta!" );
                tentativas--;
            }
        } else { //Caso tenha inserido mais de uma letra ou uma palavra que não seja do tamanho correto, informa um erro
            System.out.println( "Digite uma letra, ou uma palavra com o tamanho correto!" );
        }

        // Chama a função que imprime a forca
        desenharPessoaNaForca( tentativas );

        // Mostra as letras acertadas
        System.out.println( "Progresso na adivinhação: " );
        for ( Object i : progressoAdivinhacao ) {
            System.out.print( i + " " );
        }

        // Mostra quantas vidas o jogador ainda tem
        System.out.println( "Você tem mais " + tentativas + " vidas! " );

        System.out.println( "------------ Letras já usadas ---------------" );
        System.out.println(letrasJaUsadas);
        System.out.println( "---------------------------------------------" );
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
            desenharPessoaNaForca( maxTentativas );

            // Mostra quantas vidas o jogador ainda tem
            System.out.println( "Você tem mais " + maxTentativas + " vidas! " );

            System.out.println( "------------ Letras já usadas ---------------" );
            System.out.println(letrasJaUsadas);
            System.out.println( "---------------------------------------------" );
        }
    }

    private static void desenharPessoaNaForca( int tentativa ) {

        //Passa por cada linha do desenho e desenha as partes necessarias do personagem a partir das tentativas

        System.out.println( "\n" );
        System.out.println( " _______" );
        System.out.println( " |     |" );

        if ( tentativa <= 6 ) {
            System.out.println( " |     O" );
        }
        if ( tentativa == 6 ) {
            System.out.println( " |      " );
        }
        if ( tentativa == 5 ) {
            System.out.println( " |     |" );
            System.out.println( " |     |" );
        }
        if ( tentativa == 4 ) {
            System.out.println( " |    /|" );
            System.out.println( " |     |" );
        }
        if ( tentativa <= 3 ) {
            System.out.println( " |    /|\\" );
            System.out.println( " |     |" );
        }
        if ( tentativa == 2 ) {
            System.out.println( " |    / " );
        }
        if ( tentativa <= 1 ) {
            System.out.println( " |    / \\" );
        }

        System.out.println( " |      " );
        System.out.println( "_|______________" );
        System.out.println( );

        if ( tentativa == 0 ) {
            System.out.println( "VOCÊ MORREU! " );
        }

    }
    public String getPalavra() {
        return palavra;
    }
}
