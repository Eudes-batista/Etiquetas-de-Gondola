package etiquetas.modelo;

import java.util.Objects;

public class Produto {

    private String referencia;
    private String nome;
    private String barras;
    private String precoVarejo;
    private String precoAtacado;
    private String quantidadeAtacado;
    private String quantidadeAserImpresso;

    public Produto(String referencia) {
        this.referencia = referencia;
    }

    public Produto(String referencia, String nome, String barras, String precoVarejo, String precoAtacado, String quantidadeAtacado) {
        this.referencia = referencia;
        this.nome = nome;
        this.barras = barras;
        this.precoVarejo = precoVarejo;
        this.precoAtacado = precoAtacado;
        this.quantidadeAtacado = quantidadeAtacado;
    }

    public Produto() {
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBarras() {
        return barras;
    }

    public void setBarras(String barras) {
        this.barras = barras;
    }

    public String getPrecoVarejo() {
        return precoVarejo;
    }

    public void setPrecoVarejo(String precoVarejo) {
        this.precoVarejo = precoVarejo;
    }

    public String getPrecoAtacado() {
        return precoAtacado;
    }

    public void setPrecoAtacado(String precoAtacado) {
        this.precoAtacado = precoAtacado;
    }

    public String getQuantidadeAtacado() {
        return quantidadeAtacado;
    }

    public void setQuantidadeAtacado(String quantidadeAtacado) {
        this.quantidadeAtacado = quantidadeAtacado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.referencia);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        return Objects.equals(this.referencia, other.referencia);
    }

    @Override
    public String toString() {
        return "Produto{" + "referencia=" + referencia + ", nome=" + nome + ", barras=" + barras + ", precoVarejo=" + precoVarejo + ", precoAtacado=" + precoAtacado + ", quantidadeAtacado=" + quantidadeAtacado + '}';
    }

    public String getQuantidadeAserImpresso() {
        return quantidadeAserImpresso;
    }

    public void setQuantidadeAserImpresso(String quantidadeAserImpresso) {
        this.quantidadeAserImpresso = quantidadeAserImpresso;
    }
    
}
