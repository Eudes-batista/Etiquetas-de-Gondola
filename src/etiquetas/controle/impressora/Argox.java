package etiquetas.controle.impressora;

import etiquetas.modelo.Produto;

public class Argox implements Impressora {

    @Override
    public String gerarEtiquetaGondolaSimples(Produto produto) {
        StringBuilder comandosGondolaSimples = this.definirCabecalho();
        comandosGondolaSimples.append("1911A1000600021").append(produto.getReferencia()).append("\n");
        comandosGondolaSimples.append("123600000120202R$ ").append(produto.getPrecoVarejo()).append("\n");
        comandosGondolaSimples.append("1911A1400820017").append(produto.getNome()).append("\n");
        comandosGondolaSimples.append("Q000").append(produto.getQuantidadeAserImpresso()).append("\n");
        comandosGondolaSimples.append("E").append("\n");
        return comandosGondolaSimples.toString();
    }

    @Override
    public String gerarEtiquetaGondolaAtacado(Produto produto) {
        StringBuilder comandoGondolaAtacado = this.definirCabecalho();
        comandoGondolaAtacado.append("1911A1000700200").append(produto.getReferencia()).append("\n");
        comandoGondolaAtacado.append("123600000060202R$ ").append(produto.getPrecoVarejo()).append("\n");
        comandoGondolaAtacado.append("1911A1400820017").append(produto.getNome()).append("\n");
        comandoGondolaAtacado.append("1911A1000600021ATACADO ").append(produto.getQuantidadeAtacado()).append(" UNID").append("\n");
        comandoGondolaAtacado.append("123600000060021R$").append(produto.getPrecoVarejo());
        comandoGondolaAtacado.append("Q000").append(produto.getQuantidadeAserImpresso()).append("\n");
        comandoGondolaAtacado.append("E").append("\n");
        return comandoGondolaAtacado.toString();
    }

    @Override
    public String gerarEtiquetaGondolaTresColunas(Produto produto) {
        return "";
    }

    private StringBuilder definirCabecalho() {
        StringBuilder comandosGondolaSimples = new StringBuilder();
        comandosGondolaSimples.append("n").append("\n");
        comandosGondolaSimples.append("M0500").append("\n");
        comandosGondolaSimples.append("V0").append("\n");
        comandosGondolaSimples.append("SE").append("\n");
        comandosGondolaSimples.append("c0000").append("\n");
        comandosGondolaSimples.append("r").append("\n");
        comandosGondolaSimples.append("D").append("\n");
        comandosGondolaSimples.append("L").append("\n");
        comandosGondolaSimples.append("D11").append("\n");
        comandosGondolaSimples.append("PE").append("\n");
        comandosGondolaSimples.append("A2").append("\n");
        return comandosGondolaSimples;
    }

}
