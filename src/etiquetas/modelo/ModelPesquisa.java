package etiquetas.modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelPesquisa extends AbstractTableModel {

    private String[] colunas;
    private List<Produto> produtos;

    public ModelPesquisa(String[] colunas, List<Produto> produtos) {
        this.colunas = colunas;
        this.produtos = produtos;
    }

    @Override
    public int getRowCount() {
        return this.produtos.size();
    }

    @Override
    public int getColumnCount() {
        return this.colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        return this.colunas[column];
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return this.produtos.get(linha).getReferencia();
            case 1:
                return this.produtos.get(linha).getNome();
            case 2:
                return this.produtos.get(linha).getBarras();
            case 3:
                return this.produtos.get(linha).getPrecoVarejo();
            case 4:
                return this.produtos.get(linha).getPrecoAtacado();
            default:
                return this.produtos.get(linha);
        }
    }

    public void setColunas(String[] colunas) {
        this.colunas = colunas;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

}
