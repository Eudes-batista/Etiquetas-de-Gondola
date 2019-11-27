
package etiquetas.modelo;


public class Configuracao {
    
    private String host;
    private String caminho;
    private String empresa;
    private String tipoEtiqueta;
    private String impressora;
    private String compartilhamentoImpressora;

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTipoEtiqueta() {
        return tipoEtiqueta;
    }

    public void setTipoEtiqueta(String tipoEtiqueta) {
        this.tipoEtiqueta = tipoEtiqueta;
    }

    public String getImpressora() {
        return impressora;
    }

    public void setImpressora(String impressora) {
        this.impressora = impressora;
    }

    public String getCompartilhamentoImpressora() {
        return compartilhamentoImpressora;
    }

    public void setCompartilhamentoImpressora(String compartilhamentoImpressora) {
        this.compartilhamentoImpressora = compartilhamentoImpressora;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    @Override
    public String toString() {
        return "Configuracao{" + "host=" + host + ", caminho=" + caminho + ", empresa=" + empresa + ", tipoEtiqueta=" + tipoEtiqueta + ", impressora=" + impressora + ", compartilhamentoImpressora=" + compartilhamentoImpressora + '}';
    }
    
}
