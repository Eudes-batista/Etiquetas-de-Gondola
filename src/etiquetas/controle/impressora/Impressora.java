
package etiquetas.controle.impressora;

import etiquetas.modelo.Produto;


public interface Impressora {
    
    public String gerarEtiquetaGondolaSimples(Produto produto);
    public String gerarEtiquetaGondolaAtacado(Produto produto);
    public String gerarEtiquetaGondolaTresColunas(Produto produto);
}
