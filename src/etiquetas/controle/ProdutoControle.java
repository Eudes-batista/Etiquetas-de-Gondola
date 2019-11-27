package etiquetas.controle;

import etiquetas.modelo.Configuracao;
import etiquetas.modelo.Produto;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

public class ProdutoControle {

    private final ConectaBanco conecta;
    private final List<Produto> produtos;

    private DecimalFormat decimalFormat;
    private ArquivoControle arquivoControle;

    public ProdutoControle() {
        this.conecta = new ConectaBanco();
        this.produtos = new ArrayList<>();
        this.decimalFormat = new DecimalFormat("###,##0.00");
        try {
            this.arquivoControle = new ArquivoControle();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar arquivo de configuração.");
        }
    }

    public List<Produto> listarProdutos(String pesquisa,String empresa) {
        try {
            Configuracao configuracao = arquivoControle.lerArquivo();
            if (configuracao.getHost() == null) {
                return Arrays.asList();
            }
            String host = configuracao.getHost(), caminho = configuracao.getCaminho();
            if (!this.conecta.conexao(host, caminho)) {
                return Arrays.asList();
            }
            String sql = "select first 10 \n" + "  PRREFERE as PRREFERE\n" + " ,PRDESCRI as PRDESCRI\n" + " ,EEPLQTB1 as EEPLQTB1\n" + " ,PRCODBAR as PRCODBAR\n" + " ,EET2PVD1 as EET2PVD1\n" + " ,PRQTDATA as PRQTDATA\n" + "from \n" + "  scea07 \n" + "inner join \n" + "  scea01 on(PRREFERE=EEREFERE and EECODEMP='"+empresa+"') \n" + "where \n"
                    + "  PRDESCRI like '%" + pesquisa + "%' or PRREFERE='" + pesquisa + "' or PRCODBAR='" + pesquisa + "'\n order by PRDESCRI";
            if (!conecta.executaSQL(sql)) {
                return Arrays.asList();
            }
            try {
                if (!conecta.getRs().first()) {
                    JOptionPane.showMessageDialog(null, "Produto não existe.");
                    return Arrays.asList();
                }
                produtos.clear();
                do {
                    String referencia = this.conecta.getRs().getString("PRREFERE");
                    String nome = this.conecta.getRs().getString("PRDESCRI");
                    String codigoBarra = this.conecta.getRs().getString("PRCODBAR");
                    double precoVarejo = this.conecta.getRs().getDouble("EEPLQTB1");
                    double precoAtacado = this.conecta.getRs().getDouble("EET2PVD1");
                    String quantidadeAtacado = this.conecta.getRs().getString("PRQTDATA");
                    Produto produto = new Produto(referencia, nome, codigoBarra, decimalFormat.format(precoVarejo), decimalFormat.format(precoAtacado), quantidadeAtacado);
                    produto.setQuantidadeAserImpresso("1");
                    this.produtos.add(produto);
                } while (this.conecta.getRs().next());
                this.conecta.desconecta();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao consultar.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo.");
        }
        return this.produtos;
    }

    public List<String> listarEmpresas() {
        List<String> empresas = new ArrayList<>();
        try {
            Configuracao configuracao = arquivoControle.lerArquivo();
            if (configuracao.getHost() == null) {
                return Arrays.asList();
            }
            String host = configuracao.getHost(), caminho = configuracao.getCaminho();
            if (!this.conecta.conexao(host, caminho)) {
                return Arrays.asList();
            }
            String sql = "select LJCODEMP from LAPA19 order by LJCODEMP desc";
            if (!conecta.executaSQL(sql)) {
                return Arrays.asList();
            }
            try {
                if (!conecta.getRs().first()) {
                    return Arrays.asList();
                }
                do {
                    empresas.add(this.conecta.getRs().getString("LJCODEMP"));
                } while (this.conecta.getRs().next());
                this.conecta.desconecta();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao consultar.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo.");
        }
        return empresas;
    }

    public Produto buscarProduto(String pesquisa,String empresa) {
        Produto produto = new Produto();
        try {
           Configuracao configuracao = arquivoControle.lerArquivo();
            if (configuracao.getHost() == null) {
                return null;
            }
            String host = configuracao.getHost(), caminho = configuracao.getCaminho();
            if (!this.conecta.conexao(host, caminho)) {
                return null;
            }
            String sql = "select first 1 \n" + "  PRREFERE as PRREFERE\n" + " ,PRDESCRI as PRDESCRI\n" + " ,EEPLQTB1 as EEPLQTB1\n" + " ,PRCODBAR as PRCODBAR\n" + " ,EET2PVD1 as EET2PVD1\n" + " ,PRQTDATA as PRQTDATA\n" + "from \n" + "  scea07 \n" + "inner join \n" + "  scea01 on(PRREFERE=EEREFERE and eecodemp='"+empresa+"') \n" + "where \n"
                    + "  PRREFERE='" + pesquisa + "' or PRCODBAR='" + pesquisa + "'\n order by PRDESCRI";
            if (!this.conecta.executaSQL(sql)) {
                return null;
            }
            try {
                if (!this.conecta.getRs().first()) {
                    return null;
                }
                String referencia = this.conecta.getRs().getString("PRREFERE");
                String nome = this.conecta.getRs().getString("PRDESCRI");
                String codigoBarra = this.conecta.getRs().getString("PRCODBAR");
                double precoVarejo = this.conecta.getRs().getDouble("EEPLQTB1");
                double precoAtacado = this.conecta.getRs().getDouble("EET2PVD1");
                String quantidadeAtacado = this.conecta.getRs().getString("PRQTDATA");
                produto.setReferencia(referencia);
                produto.setNome(nome);
                produto.setBarras(codigoBarra);
                produto.setPrecoVarejo(decimalFormat.format(precoVarejo));
                produto.setPrecoAtacado(decimalFormat.format(precoAtacado));
                produto.setQuantidadeAtacado(quantidadeAtacado);
                produto.setQuantidadeAserImpresso("1");
                this.conecta.desconecta();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao consultar.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo.");
        }
        return produto;
    }

}
