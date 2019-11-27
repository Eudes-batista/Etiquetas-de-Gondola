package tiquetas.teste;

import etiquetas.controle.ImpressaoControle;
import etiquetas.modelo.Produto;
import java.io.IOException;

public class TesteTresColunasEtiqueta {

    private static final String CONFIGURA_ALTURA_E_ESPACO_ETIQUETA = "Q120,24";
    private static final String CONFIGURA_LARGURA_TOTAL_DA_ETIQUETA = "q240";
    private static final String CONFIGURA_PONTO_ORIGEM_IMPRESSAO = "R24,8";
    private static final String LIMPA_BUFFER_IMPRESSAO = "N";
    private static final String DENSIDADE_DA_CABECA_DE_IMPRESSAO = "D9"; // o valor vai de 0-15
    private static final String VALOCIDADE_IMPRESSAO = "S1"; // configura a Velocidade de impressão para 3 pol./seg
    private static final String CONFIGURA_ESPACO_ENTRE_AS_ETIQUETAS = "JF";
    private static final String INICIA_IMPRESSAO_A_PARTIR_DO_TOPO = "ZB"; //indica que a impressão deve inciar a partir do topo, ou seja, de cabeça para baixo ZT) para cima ZB     

    public static void main(String[] args) throws IOException {

        Produto produtoSimple = new Produto();
                    produtoSimple.setReferencia("250");
//            produtoSimple.setReferencia(codigo);
            produtoSimple.setNome("BISCOITO TRELOSO DE CHOCOLATE".substring(0, 25));
            produtoSimple.setBarras("7891234567891");
            produtoSimple.setPrecoVarejo("100,99");
            produtoSimple.setPrecoAtacado("100,99");
            produtoSimple.setQuantidadeAtacado("5");
            produtoSimple.setQuantidadeAserImpresso("1");

            ImpressaoControle impressao = new ImpressaoControle();

            String gondolaSimples = gerarEtiquetaGondolaSimples(produtoSimple);
            impressao.imprimirEtiqueta("\\\\localhost\\l42", gondolaSimples);

    }

    public static String gerarEtiquetaGondolaSimples(Produto produto) {
     String indentificacaoDoProduto = produto.getBarras() == null || "".equals(produto.getBarras()) ? produto.getReferencia() : produto.getBarras();
        String tipoDeCodigoBarra = produto.getReferencia().length() < 13 ? definirModeloDeCodigoBarraEAN128() : definirModeloDeCodigoBarraEAN13();
        StringBuilder comandosGondolaTresColunas = new StringBuilder();
        comandosGondolaTresColunas.append(LIMPA_BUFFER_IMPRESSAO).append("\n");
        comandosGondolaTresColunas.append(DENSIDADE_DA_CABECA_DE_IMPRESSAO).append("\n");
        comandosGondolaTresColunas.append(VALOCIDADE_IMPRESSAO).append("\n");
        comandosGondolaTresColunas.append(CONFIGURA_ESPACO_ENTRE_AS_ETIQUETAS).append("\n");
        comandosGondolaTresColunas.append(INICIA_IMPRESSAO_A_PARTIR_DO_TOPO).append("\n");
        comandosGondolaTresColunas.append(CONFIGURA_ALTURA_E_ESPACO_ETIQUETA).append("\n");
        comandosGondolaTresColunas.append(CONFIGURA_LARGURA_TOTAL_DA_ETIQUETA).append("\n");
        comandosGondolaTresColunas.append(CONFIGURA_PONTO_ORIGEM_IMPRESSAO).append("\n");

        comandosGondolaTresColunas.append("A").append(definirPosicaoDeColuna("0")).append(",")
                .append(definirPosicaoDeLinha("8")).append(",").append("0").append(",")
                .append(definirTamanhoDaFonte("1")).append(",1,1,N,\"").append(produto.getNome()).append("\"\n");

        comandosGondolaTresColunas.append("A").append(definirPosicaoDeColuna("16")).append(",")
                .append(definirPosicaoDeLinha("48")).append(",0,").append(definirTamanhoDaFonte("2"))
                .append(",1,1,N,\"").append("R$").append("\"\n");

        comandosGondolaTresColunas.append("A").append(definirPosicaoDeColuna("48")).append(",")
                .append(definirPosicaoDeLinha("48")).append(",0,").append(definirTamanhoDaFonte("3"))
                .append(",1,1,N,\"").append(produto.getPrecoVarejo()).append("\"\n");

        comandosGondolaTresColunas.append("B5,90,0,").append(tipoDeCodigoBarra)
                .append(",2,2,40,B,\"")
                .append(indentificacaoDoProduto).append("\"\n");
/// segunda coluna
        comandosGondolaTresColunas.append("A").append(definirPosicaoDeColuna("268")).append(",")
                .append(definirPosicaoDeLinha("8")).append(",").append("0").append(",")
                .append(definirTamanhoDaFonte("1")).append(",1,1,N,\"").append(produto.getNome()).append("\"\n");

        comandosGondolaTresColunas.append("A").append(definirPosicaoDeColuna("295")).append(",")
                .append(definirPosicaoDeLinha("48")).append(",0,").append(definirTamanhoDaFonte("2"))
                .append(",1,1,N,\"").append("R$").append("\"\n");

        comandosGondolaTresColunas.append("A").append(definirPosicaoDeColuna("325")).append(",")
                .append(definirPosicaoDeLinha("48")).append(",0,").append(definirTamanhoDaFonte("3"))
                .append(",1,1,N,\"").append(produto.getPrecoVarejo()).append("\"\n");

        comandosGondolaTresColunas.append("B275,90,0,").append(tipoDeCodigoBarra)
                .append(",2,2,40,B,\"")
                .append(indentificacaoDoProduto).append("\"\n");
// fim
/// terceira coluna
        comandosGondolaTresColunas.append("A").append(definirPosicaoDeColuna("540")).append(",")
                .append(definirPosicaoDeLinha("8")).append(",").append("0").append(",")
                .append(definirTamanhoDaFonte("1")).append(",1,1,N,\"").append(produto.getNome()).append("\"\n");

        comandosGondolaTresColunas.append("A").append(definirPosicaoDeColuna("570")).append(",")
                .append(definirPosicaoDeLinha("48")).append(",0,").append(definirTamanhoDaFonte("2"))
                .append(",1,1,N,\"").append("R$").append("\"\n");

        comandosGondolaTresColunas.append("A").append(definirPosicaoDeColuna("600")).append(",")
                .append(definirPosicaoDeLinha("48")).append(",0,").append(definirTamanhoDaFonte("3"))
                .append(",1,1,N,\"").append(produto.getPrecoVarejo()).append("\"\n");

        comandosGondolaTresColunas.append("B550,90,0,").append(tipoDeCodigoBarra)
                .append(",2,2,40,B,\"")
                .append(indentificacaoDoProduto).append("\"\n");
// fim

        comandosGondolaTresColunas.append("P").append(produto.getQuantidadeAserImpresso()).append("\n");
        return comandosGondolaTresColunas.toString();
    }

    /**
     * tamanho da fonte vai de 0 a 5
     *
     * @param tamanho
     * @return o tamanho passado se caso não tiver entre 0-5 o valor sera vazio
     */
    private static String definirTamanhoDaFonte(String tamanho) {
        int tamanhoDaFonte = Integer.parseInt(tamanho);
        return tamanhoDaFonte > 5 || tamanhoDaFonte < 0 ? "" : tamanho;
    }

    /**
     * a posição e determinada por pontos a cada 8 pontos corresponde a 1mm
     *
     * @param posicao
     * @return
     */
    private static String definirPosicaoDeColuna(String posicao) {
        return posicao;
    }

    /**
     * a posição é determinada por pontos a cada 8 pontos corresponde a 1mm
     *
     * @param posicao
     * @return String com a posicao
     */
    private static String definirPosicaoDeLinha(String posicao) {
        return posicao;
    }

    private static String definirModeloDeCodigoBarraEAN13() {
        return "1";
    }

    private static String definirModeloDeCodigoBarraEAN128() {
        return "1";
    }
}
