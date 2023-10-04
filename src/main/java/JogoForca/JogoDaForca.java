package JogoForca;

import org.example.Receptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JogoDaForca {

    private static String palavra;
    private static String dica;
    private Sorteador sorteador = new Sorteador( );

    private static String letrasJaUsadas = "";

    private static List<String> progressoAdivinhacao = new ArrayList<>( );

    public JogoDaForca( ) {
        String result = sorteador.geraPalavra( "facil" );
        String[] partes = result.split( "-" );
        palavra = partes[ 0 ];
        System.out.println( "A palavra sorteada foi: " + palavra );
        dica = partes[ 1 ];
    }

    public String chute( String frame ) {

        // Extrai a letra/palavra do chute
        String letra = frame.substring( 3 );
        System.out.println( "Letra/palavra do chute: " + letra );

        //string que receberá tudo que será enviado aos players após o chute
        String retorno = "";

        if ( letra.length( ) == 1 ) {

            // Caso a letra ja tenha sido chutada, retorna a mensagem e permite ele jogar novamente;
            if ( letrasJaUsadas.contains( letra ) ) {
                retorno = "A letra " + letra + " já foi informada.";
            }

            letrasJaUsadas += letra + " ";

            // Verifica a existencia da letra dentro da palavra sorteada:
            if ( palavra.contains( letra ) && !retorno.equals( "" ) ) { //caso a palavra contenha a letra, passa para a proxima parte
                for ( int i = 0; i < palavra.length( ); i++ ) {
                    //Percorre cada letra da palavra com substrings caso a letra esteja na palavra, Altera o mesmo index no progresso de adivinhação Substituindo-o pela letra
                    if ( palavra.substring( i, i + 1 ).equals( letra ) ) {
                        progressoAdivinhacao.set( i, letra );
                    }
                }
                //O Progresso de adivinhacao contem o caracter _ em letras que nao foram adivinhadas caso o progresso de adivinhacao nao contenha nenhum _, mostra a vitoria do jogador
                if ( !progressoAdivinhacao.toString( ).contains( "_" ) ) {
                    retorno = " VENCEU!!! A palavra era: " + palavra;
                }
                System.out.println( "Você acertou uma letra!!\n" );
            } else if ( !retorno.equals( "" ) ) {

                //errou a letra
                // DEPOIS A GENTE VALIDA A MORTE DA BEZERRA

                retorno = "Você errou :(";
            }

            //Comparando para palavra completa (letra = palavra adivinhada pelo jogador)
        } else if ( letra.length( ) > 1 && letra.length( ) == palavra.length( ) ) {
            if ( palavra.equals( letra ) ) {
                retorno =  " VENCEU!!! A palavra era: " + palavra;
            } else {
                retorno = "Palavra Incorreta!";
            }
        } else { //Caso tenha inserido mais de uma letra ou uma palavra que não seja do tamanho correto, informa um erro
            retorno = "Digite uma letra, ou uma palavra com o tamanho correto!" ;
        }

        // Mostra as letras acertadas
        System.out.println( "Progresso na adivinhação: " );
        for ( Object i : progressoAdivinhacao ) {
            System.out.print( i + " " );
        }


        return retorno + " | " + progressoAdivinhacao + " | " + letrasJaUsadas;
    }

    public String dica( ) {
        // envia a dica
        return dica;
    }


    private static void playGame( String palavra, List palavraQuebrada, List progressoAdivinhacao, int tentativasJogador ) {

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
            desenharPessoaNaForca( maxTentativas, "" );

            // Mostra quantas vidas o jogador ainda tem
            System.out.println( "Você tem mais " + maxTentativas + " vidas! " );

            System.out.println( "------------ Letras já usadas ---------------" );
            System.out.println( letrasJaUsadas );
            System.out.println( "---------------------------------------------" );
        }
    }

    private static String desenharPessoaNaForca( int tentativa, String username ) {
        String desenhoForca = "";

        //Passa por cada linha do desenho e desenha as partes necessarias do personagem a partir das tentativas

        System.out.println( "\n" );
        System.out.println( " _______" );
        System.out.println( " |     |" );

        desenhoForca += "\n";
        desenhoForca += " _______";
        desenhoForca += " |     |";

        if ( tentativa <= 6 ) {
            System.out.println( " |     O" );
            desenhoForca += "\n |     O";
        }
        if ( tentativa == 6 ) {
            System.out.println( " |      " );
            desenhoForca += "\n |      ";
        }
        if ( tentativa == 5 ) {
            System.out.println( " |     |" );
            System.out.println( " |     |" );

            desenhoForca += "\n |     |";
            desenhoForca += "\n |     |";
        }
        if ( tentativa == 4 ) {
            System.out.println( " |    /|" );
            System.out.println( " |     |" );

            desenhoForca += "\n |    /|";
            desenhoForca += "\n |     |";
        }
        if ( tentativa <= 3 ) {
            System.out.println( " |    /|\\" );
            System.out.println( " |     |" );

            desenhoForca += "\n |    /|\\";
            desenhoForca += "\n |     |";
        }
        if ( tentativa == 2 ) {
            System.out.println( " |    / " );

            desenhoForca += "\n |    / ";
        }
        if ( tentativa <= 1 ) {
            System.out.println( " |    / \\" );

            desenhoForca += "\n |    / \\";
        }

        System.out.println( " |      " );
        System.out.println( "_|______________" );

        desenhoForca += "\n |      ";
        desenhoForca += "\n_|______________";

        if ( tentativa == 0 ) {
            System.out.println( username + " MORREU! " );

            desenhoForca += "\n" + username + " MORREU! ";
        }

        return desenhoForca;

    }

    public String getPalavra( ) {
        return palavra;
    }
}
