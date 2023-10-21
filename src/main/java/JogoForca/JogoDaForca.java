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

    public JogoDaForca(String dificuldade) {
        String result = sorteador.geraPalavra( dificuldade );
        String[] partes = result.split( "-" );
        palavra = partes[ 0 ];
        System.out.println( "A palavra sorteada foi: " + palavra );
        dica = partes[ 1 ];

        //Adicionando a palavra sorteada a uma lista e completando a lista de adivinhação com ""
        for ( int i = 0; i < palavra.length( ); i++ ) {
            progressoAdivinhacao.add( "_" );
        }
    }

    public String chute( String frame ) {

        // Extrai a letra/palavra do chute
        String letra = frame.substring( 3 );
        System.out.println( "Letra/palavra do chute: " + letra );

        //string que receberá tudo que será enviado aos players após o chute
        String retorno = "";



        letra = letra.replace( " ", "" );
        if ( letra.length( ) == 1 ) {

            // Caso a letra ja tenha sido chutada, retorna a mensagem e permite ele jogar novamente;
            if ( letrasJaUsadas.contains( letra ) ) {
                return "A letra " + letra + " já foi informada.";
            }

            letrasJaUsadas += letra + " ";

            // Verifica a existencia da letra dentro da palavra sorteada:
            if ( palavra.contains( letra ) ) { //caso a palavra contenha a letra, passa para a proxima parte
                for ( int i = 0; i < palavra.length( ); i++ ) {
                    //Percorre cada letra da palavra com substrings caso a letra esteja na palavra, Altera o mesmo index no progresso de adivinhação Substituindo-o pela letra
                    if ( palavra.substring( i, i + 1 ).equals( letra ) ) {
                        progressoAdivinhacao.set( i, letra );
                    }
                }
                //O Progresso de adivinhacao contem o caracter _ em letras que nao foram adivinhadas caso o progresso de adivinhacao nao contenha nenhum _, mostra a vitoria do jogador
                if ( !progressoAdivinhacao.toString( ).contains( "_" ) ) {
                    retorno = " VENCEU!!! A palavra era: :-" + palavra;

                }else{
                    retorno = " Você acertou uma letra! ";
                }
            } else {
                retorno = "Letra Incorreta!";
            }

            //Comparando para palavra completa (letra = palavra adivinhada pelo jogador)
        } else if ( letra.length( ) > 1 && letra.length( ) == palavra.length( ) ) {
            if ( palavra.equals( letra ) ) {
                retorno =  " VENCEU!!! A palavra era: :-" + palavra;
            } else {
                retorno = "Palavra Incorreta!";
            }
        } else { //Caso tenha inserido mais de uma letra ou uma palavra que não seja do tamanho correto, informa um erro
            retorno = "Digite uma letra, ou uma palavra com o tamanho correto!" ;
        }

        if(retorno.contains( "VENCEU" )){
            String result = sorteador.geraPalavra( "facil" );
            String[] partes = result.split( "-" );
            palavra = partes[ 0 ];
            System.out.println( "A palavra sorteada foi: " + palavra );
            dica = partes[ 1 ];

            //Adicionando a palavra sorteada a uma lista e completando a lista de adivinhação com ""
            progressoAdivinhacao.clear();
            for ( int i = 0; i < palavra.length( ); i++ ) {
                progressoAdivinhacao.add( "_" );
            }
            letrasJaUsadas = "";

            return "R | " + retorno;
        }

        StringBuilder palavraComLetrasAcertadas = new StringBuilder( );
        // Mostra as letras acertadas
        System.out.println( "Progresso na adivinhação: " );
        for ( Object i : progressoAdivinhacao ) {
            System.out.print( i + " " );
            palavraComLetrasAcertadas.append( i ).append( " " );
        }

        // o retorno vai apenas para o jogador da vez, o progresso e as letras usadas devem ir pra todos os jogadores
        return "R | " + retorno + " | " + palavraComLetrasAcertadas.toString() + " | " + letrasJaUsadas;
    }

    public String dica( ) {
        // envia a dica
        return dica;
    }


    public String getPalavra( ) {
        return palavra;
    }
}
