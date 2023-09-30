package JogoForca;

import java.util.Random;

public class Sorteador {

    public String geraPalavra(String dificuldade) {
        //
        switch (dificuldade) {
            case "facil":
                System.out.println("Gerando uma palavra fácil...");
                return palavraFacil();
            case "medio":
                System.out.println("Gerando uma palavra média...");
                return palavraMedia();
            case "dificil":
                System.out.println("Gerando uma palavra dificil...");
                return palavraDificil();
            default:
                break;
        }
        return "";
    }

    public String palavraFacil(){
        final String[] listaPalavras = {
                "falar", "azul", "banana", "carro", "canto",
                "amigo", "laranja", "janela", "ninho", "gosto",
                "folha", "bola", "tempo", "verde", "lua",
                "caneta", "música", "chuva", "risco", "corpo",
                "dente", "feira", "chave", "flores", "pular",
                "praia", "piano", "leite", "peixe", "quarto",
                "risco", "solto", "teatro", "vento", "aberto",
                "caminho", "galo", "escola", "fundo", "grande",
                "imagem", "jovem", "karma", "lugar", "magia",
                "notas", "olhos", "ponte", "quarto", "rosto"
        };
        Random random = new Random();
        return listaPalavras[random.nextInt(50)];
    }

    public String palavraMedia(){
        String[] listaPalavras = {
                "abacaxi", "computar", "diretor", "elefante", "fantasia",
                "girassol", "habilidade", "inovacao", "jogador", "kanguru",
                "lambari", "maravilha", "novidade", "orquidea", "palavras",
                "quilometro", "resiliente", "sapateiro", "tamarindo", "ultimato",
                "vencedor", "xadrez", "yoga", "zeppelin", "abobora",
                "brilhante", "caminhao", "desenvolver", "escritor", "felicidade",
                "guitarra", "habitat", "inspiracao", "janela", "kiwi",
                "limonada", "melancia", "narrativa", "oportunidade", "piano",
                "quadrado", "raios", "sabedoria", "tartaruga", "universo",
                "velocidade", "xenofobia", "youtuber", "zumbi"
        };
        Random random = new Random();
        return listaPalavras[random.nextInt(50)];
    }

    public String palavraDificil(){
        String[] listaPalavras = {
                "abacateiro", "computador", "eletricidade", "fantasmagoria", "governamental",
                "hipopotamo", "inconstitucional", "javascript", "kaleidoscopio", "laboratorio",
                "macroeconomia", "necessidade", "organizacao", "paralelepipedo", "qualificacao",
                "responsabilidade", "superinteressante", "tecnologia", "ultrassom", "ventilador",
                "xenofobia", "yogurteira", "zeladoria", "abecedario", "bicicletario",
                "desenvolvimento", "especificacao", "fotossintese", "geopolitica", "hiperatividade",
                "institucionalizacao", "jurisprudencia", "kilometragem", "laboratorial", "macroestrutura",
                "nomenclatura", "organizacional", "parlamentarismo", "quantificacao", "reconhecimento",
                "sustentabilidade", "tecnologico", "ultrapassagem", "ventilacao", "xilofone",
                "youtuber", "zeloso"
        };
        Random random = new Random();
        return listaPalavras[random.nextInt(50)];
    }

}
