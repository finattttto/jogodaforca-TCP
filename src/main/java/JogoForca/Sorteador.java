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
        String[] listaPalavras = {
            "cacto-deserto",
            "atlas-mapa",
            "iogurte-leite",
            "ritmo-musica",
            "pedagio-estrada",
            "madrasta-familia",
            "reciclar-lixo",
            "milionario-dinheiro",
            "gnomo-jardim",
            "alquimia-transformação"
            // Adicione mais palavras e dicas conforme necessário
        };
        Random random = new Random();
        return listaPalavras[random.nextInt(10)];
    }

    public String palavraMedia(){
        String[] listaPalavras = {
            "amendoim-comida",
            "pneumonia-doença",
            "bergamota-fruta",
            "ampulheta-relojo",
            "estribo-cavalo",
            "quadriciclo-veiculo",
            "travesseiro-repouso",
            "zodíaco-astros",
            "ventilador-verão",
            "cicatriz-pele"
            // Adicione mais palavras e dicas conforme necessário
        };
        Random random = new Random();
        return listaPalavras[random.nextInt(10)];
    }

    public String palavraDificil(){
        String[] listaPalavras = {
            "estapafúrdio-caracteristica",
            "manteigueira-leite",
            "cleptomaníaco-roubo",
            "alambique-alcool",
            "escaravelho-animal",
            "nanotecnologo-profissao",
            "bumerangue-brinquedo",
            "invertebrado-biologia",
            "quinquilharia-velho",
            "ziguezague-drible"
            // Adicione mais palavras e dicas conforme necessário
        };
        Random random = new Random();
        return listaPalavras[random.nextInt(10)];
    }

}
