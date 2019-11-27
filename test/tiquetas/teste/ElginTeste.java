package tiquetas.teste;

import etiquetas.controle.impressora.Elgin;
import etiquetas.controle.ImpressaoControle;
import etiquetas.controle.impressora.Impressora;
import etiquetas.modelo.Produto;
import java.io.IOException;

public class ElginTeste {

    public static void main(String[] args) throws IOException {

        ImpressaoControle impressao = new ImpressaoControle();

        Impressora impressora = new Elgin();

        impressaoEtiquetaSimples(impressao, impressora);
       // impressaoEtiquetaAtacado(impressao, impressora);

    }

    public static void impressaoEtiquetaSimples(ImpressaoControle impressao, Impressora impressora) throws IOException {
        Produto produtoSimple = new Produto();

        produtoSimple.setReferencia("7896496980826");
        produtoSimple.setNome("BISCOITO MAE TERRA COOKIES INTIMUS COCO CACAU 120GR");
        produtoSimple.setBarras("7896496980826");
        produtoSimple.setPrecoVarejo("6,99");

        String gondolaSimples = impressora.gerarEtiquetaGondolaSimples(produtoSimple);
        impressao.imprimirEtiqueta("\\\\localhost\\l42", gondolaSimples);
    }

    public static void impressaoEtiquetaAtacado(ImpressaoControle impressao, Impressora impressora) throws IOException {
        Produto produtoAtacado = new Produto();

        produtoAtacado.setReferencia("7896496980826");
        produtoAtacado.setNome("BISCOITO MAE TERRA COOKIES INTIMUS COCO CACAU 120GR");
        produtoAtacado.setBarras("7896496980826");
        produtoAtacado.setPrecoVarejo("6,99");
        produtoAtacado.setPrecoAtacado("16,99");
        produtoAtacado.setQuantidadeAtacado("5");

        String gondolaAtcado = impressora.gerarEtiquetaGondolaAtacado(produtoAtacado);
        impressao.imprimirEtiqueta("\\\\localhost\\l42", gondolaAtcado);
    }

}
