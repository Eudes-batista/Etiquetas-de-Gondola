package etiquetas.controle.impressora;

import etiquetas.modelo.Produto;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Properties;

public class Elgin implements Impressora {

    private static final String CONFIGURA_ALTURA_E_ESPACO_ETIQUETA = "Q120,24";
    private static final String CONFIGURA_LARGURA_TOTAL_DA_ETIQUETA = "q880,8";
    private static final String CONFIGURA_PONTO_ORIGEM_IMPRESSAO = "R24,8";
    private static final String LIMPA_BUFFER_IMPRESSAO = "N";
    private static final String DENSIDADE_DA_CABECA_DE_IMPRESSAO = "D9"; // o valor vai de 0-15
    private static final String VALOCIDADE_IMPRESSAO = "S1"; // configura a Velocidade de impressão para 3 pol./seg
    private static final String CONFIGURA_ESPACO_ENTRE_AS_ETIQUETAS = "JF";
    private static final String INICIA_IMPRESSAO_A_PARTIR_DO_TOPO = "ZB"; //indica que a impressão deve inciar a partir do topo, ou seja, de cabeça para baixo ZT) para cima ZB     

    @Override
    public String gerarEtiquetaGondolaSimples(Produto produto) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("alinha-mento.properties"));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        String colunaDescricao = properties.getProperty("elgin.etiqueta.varejo.descricao.coluna", "16");
        String linhaDescricao = properties.getProperty("elgin.etiqueta.varejo.descricao.linha", "8");
        String fonteDescricao = properties.getProperty("elgin.etiqueta.varejo.descricao.fonte", "4");
        String colunaCifrao = properties.getProperty("elgin.etiqueta.varejo.cifrao.coluna", "400");
        String linhaCifrao = properties.getProperty("elgin.etiqueta.varejo.cifrao.linha", "100");
        String fonteCifrao = properties.getProperty("elgin.etiqueta.varejo.cifrao.fonte", "4");
        String colunaValor = properties.getProperty("elgin.etiqueta.varejo.valor.coluna", "480");
        String linhaValor = properties.getProperty("elgin.etiqueta.varejo.valor.linha", "100");
        String fonteValor = properties.getProperty("elgin.etiqueta.varejo.valor.fonte", "5");
        String colunaBarra = properties.getProperty("elgin.etiqueta.varejo.barra.coluna", "B16");
        String linhaBarra = properties.getProperty("elgin.etiqueta.varejo.barra.linha", "112");

        String indentificacaoDoProduto = produto.getBarras() == null || "".equals(produto.getBarras()) ? produto.getReferencia() : produto.getBarras();
        String tipoDeCodigoBarra = produto.getReferencia().length() < 13 ? definirModeloDeCodigoBarraEAN128() : definirModeloDeCodigoBarraEAN13();
        StringBuilder comandosGondolaSimples = new StringBuilder();
        comandosGondolaSimples.append(LIMPA_BUFFER_IMPRESSAO).append("\n");
        comandosGondolaSimples.append(DENSIDADE_DA_CABECA_DE_IMPRESSAO).append("\n");
        comandosGondolaSimples.append(VALOCIDADE_IMPRESSAO).append("\n");
        comandosGondolaSimples.append(CONFIGURA_ESPACO_ENTRE_AS_ETIQUETAS).append("\n");
        comandosGondolaSimples.append(INICIA_IMPRESSAO_A_PARTIR_DO_TOPO).append("\n");
        comandosGondolaSimples.append("A").append(definirPosicaoDeColuna(colunaDescricao)).append(",").append(definirPosicaoDeLinha(linhaDescricao)).append(",").append("0").append(",")
                .append(definirTamanhoDaFonte(fonteDescricao)).append(",2,2,N,\"").append(this.removerAspas(produto.getNome()).trim()).append("\"\n");
        comandosGondolaSimples.append("A").append(definirPosicaoDeColuna(colunaCifrao)).append(",").append(definirPosicaoDeLinha(linhaCifrao)).append(",0,")
                .append(definirTamanhoDaFonte(fonteCifrao)).append(",1,1,R,\"").append("R$").append("\"\n");
        comandosGondolaSimples.append("A").append(definirPosicaoDeColuna(colunaValor)).append(",").append(definirPosicaoDeLinha(linhaValor)).append(",0,")
                .append(definirTamanhoDaFonte(fonteValor)).append(",3,4,N,\"").append(produto.getPrecoVarejo()).append("\"\n");
        comandosGondolaSimples.append(colunaBarra).append(",").append(linhaBarra).append(",0,").append(tipoDeCodigoBarra)
                .append(",3,5,60,B,\"")
                .append(indentificacaoDoProduto).append("\"\n");
        comandosGondolaSimples.append("P").append(produto.getQuantidadeAserImpresso()).append("\n");
        System.out.println("comandosGondolaSimples = " + comandosGondolaSimples);
        return comandosGondolaSimples.toString();
    }

    @Override
    public String gerarEtiquetaGondolaAtacado(Produto produto) {
        StringBuilder comandosGondolaAtacado = new StringBuilder();
        comandosGondolaAtacado.append(LIMPA_BUFFER_IMPRESSAO).append("\n");
        comandosGondolaAtacado.append(DENSIDADE_DA_CABECA_DE_IMPRESSAO).append("\n");
        comandosGondolaAtacado.append(VALOCIDADE_IMPRESSAO).append("\n");
        comandosGondolaAtacado.append(CONFIGURA_ESPACO_ENTRE_AS_ETIQUETAS).append("\n");
        comandosGondolaAtacado.append(INICIA_IMPRESSAO_A_PARTIR_DO_TOPO).append("\n");

        comandosGondolaAtacado.append("A16,8,0,").append(definirTamanhoDaFonte("4")).append(",1,1,N,\"").append(this.removerAspas(produto.getNome())).append("\"\n");
        comandosGondolaAtacado.append("A16,50,0,").append(definirTamanhoDaFonte("3")).append(",1,1,R,\"").append("Varejo").append("\"\n");
        comandosGondolaAtacado.append("A16,100,0,").append(definirTamanhoDaFonte("4")).append(",1,1,N,\"").append("R$").append("\"\n");
        comandosGondolaAtacado.append("A80,124,0,").append(definirTamanhoDaFonte("5")).append(",1,1,N,\"").append(produto.getPrecoVarejo()).append("\"\n");
        comandosGondolaAtacado.append("A360,50,0,").append(definirTamanhoDaFonte("4")).append(",1,1,R,\"").append("Atacado").append("\"\n");
        comandosGondolaAtacado.append("A400,100,0,").append(definirTamanhoDaFonte("4")).append(",1,1,N,\"").append("R$").append("\"\n");
        comandosGondolaAtacado.append("A480,124,0,").append(definirTamanhoDaFonte("5")).append(",1,1,R,\"").append(produto.getPrecoAtacado()).append("\"\n");
        comandosGondolaAtacado.append("A464,180,0,").append(definirTamanhoDaFonte("3")).append(",1,1,R,\"").append("Apartir de ").append(produto.getQuantidadeAtacado()).append(" UND\"\n");
        comandosGondolaAtacado.append("LO350,30,5,180\n");
        comandosGondolaAtacado.append("P").append(produto.getQuantidadeAserImpresso()).append("\n");
        return comandosGondolaAtacado.toString();
    }

    @Override
    public String gerarEtiquetaGondolaTresColunas(Produto produto) {
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
                .append(definirTamanhoDaFonte("1")).append(",1,1,N,\"").append(this.removerAspas(produto.getNome())).append("\"\n");

        comandosGondolaTresColunas.append("A").append(definirPosicaoDeColuna("30")).append(",")
                .append(definirPosicaoDeLinha("30")).append(",0,").append(definirTamanhoDaFonte("2"))
                .append(",1,1,N,\"").append("R$").append("\"\n");

        comandosGondolaTresColunas.append("A").append(definirPosicaoDeColuna("58")).append(",")
                .append(definirPosicaoDeLinha("30")).append(",0,").append(definirTamanhoDaFonte("3"))
                .append(",1,1,N,\"").append(produto.getPrecoVarejo()).append("\"\n");

        comandosGondolaTresColunas.append("B5,60,0,").append(tipoDeCodigoBarra)
                .append(",2,2,40,B,\"")
                .append(indentificacaoDoProduto).append("\"\n");
/// segunda coluna
        comandosGondolaTresColunas.append("A").append(definirPosicaoDeColuna("268")).append(",")
                .append(definirPosicaoDeLinha("8")).append(",").append("0").append(",")
                .append(definirTamanhoDaFonte("1")).append(",1,1,N,\"").append(this.removerAspas(produto.getNome())).append("\"\n");

        comandosGondolaTresColunas.append("A").append(definirPosicaoDeColuna("295")).append(",")
                .append(definirPosicaoDeLinha("30")).append(",0,").append(definirTamanhoDaFonte("2"))
                .append(",1,1,N,\"").append("R$").append("\"\n");

        comandosGondolaTresColunas.append("A").append(definirPosicaoDeColuna("325")).append(",")
                .append(definirPosicaoDeLinha("30")).append(",0,").append(definirTamanhoDaFonte("3"))
                .append(",1,1,N,\"").append(produto.getPrecoVarejo()).append("\"\n");

        comandosGondolaTresColunas.append("B275,60,0,").append(tipoDeCodigoBarra)
                .append(",2,2,40,B,\"")
                .append(indentificacaoDoProduto).append("\"\n");
// fim
/// terceira coluna
        comandosGondolaTresColunas.append("A").append(definirPosicaoDeColuna("540")).append(",")
                .append(definirPosicaoDeLinha("8")).append(",").append("0").append(",")
                .append(definirTamanhoDaFonte("1")).append(",1,1,N,\"").append(this.removerAspas(produto.getNome())).append("\"\n");

        comandosGondolaTresColunas.append("A").append(definirPosicaoDeColuna("570")).append(",")
                .append(definirPosicaoDeLinha("30")).append(",0,").append(definirTamanhoDaFonte("2"))
                .append(",1,1,N,\"").append("R$").append("\"\n");

        comandosGondolaTresColunas.append("A").append(definirPosicaoDeColuna("600")).append(",")
                .append(definirPosicaoDeLinha("30")).append(",0,").append(definirTamanhoDaFonte("3"))
                .append(",1,1,N,\"").append(produto.getPrecoVarejo()).append("\"\n");

        comandosGondolaTresColunas.append("B550,60,0,").append(tipoDeCodigoBarra)
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
    private String definirTamanhoDaFonte(String tamanho) {
        int tamanhoDaFonte = Integer.parseInt(tamanho);
        return tamanhoDaFonte > 5 || tamanhoDaFonte < 0 ? "" : tamanho;
    }

    /**
     * a posição e determinada por pontos a cada 8 pontos corresponde a 1mm
     *
     * @param posicao
     * @return
     */
    private String definirPosicaoDeColuna(String posicao) {
        return posicao;
    }

    /**
     * a posição é determinada por pontos a cada 8 pontos corresponde a 1mm
     *
     * @param posicao
     * @return String com a posicao
     */
    private String definirPosicaoDeLinha(String posicao) {
        return posicao;
    }

    private String definirModeloDeCodigoBarraEAN13() {
        return "1";
    }

    private String definirModeloDeCodigoBarraEAN128() {
        return "1";
    }
    
    private String removerAspas(String valor) {
        String formatada = Normalizer.normalize(valor, Normalizer.Form.NFD).replaceAll("[\\u0300-\\u036f]", "");
        return formatada;
    }

}
