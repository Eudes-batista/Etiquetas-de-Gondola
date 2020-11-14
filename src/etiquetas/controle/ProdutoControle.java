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

    public List<Produto> listarProdutos(String pesquisa) {
        try {
            Configuracao configuracao = arquivoControle.lerArquivo();
            if (configuracao.getHost() == null) {
                return Arrays.asList();
            }
            String host = configuracao.getHost(), caminho = configuracao.getCaminho();
            if (!this.conecta.conexao(host, caminho)) {
                return Arrays.asList();
            }
            if (!conecta.executaSQL(this.montarSqlDePesquisaDeProduto(pesquisa))) {
                return Arrays.asList();
            }
            try {
                if (!conecta.getRs().first()) {
                    JOptionPane.showMessageDialog(null, "Produto não existe.");
                    return Arrays.asList();
                }
                produtos.clear();
                do {
                    String referencia = this.conecta.getRs().getString("referencia");
                    String nome = this.conecta.getRs().getString("descricao");
                    String codigoBarra = this.conecta.getRs().getString("codigo_barra");
                    double precoVarejo = this.conecta.getRs().getDouble("preco");
                    double precoAtacado = this.conecta.getRs().getDouble("preco_atacado");
                    String quantidadeAtacado = this.conecta.getRs().getString("quantidade_atacado");
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

    public Produto buscarProduto(String pesquisa) {
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
            if (!this.conecta.executaSQL(this.montarSqlDePesquisaDeProdutoApenasPorReferencia(pesquisa))) {
                return null;
            }
            try {
                if (!this.conecta.getRs().first()) {
                    return null;
                }
                String referencia = this.conecta.getRs().getString("referencia");
                String nome = this.conecta.getRs().getString("descricao");
                String codigoBarra = this.conecta.getRs().getString("codigo_barra");
                double precoVarejo = this.conecta.getRs().getDouble("preco");
                double precoAtacado = this.conecta.getRs().getDouble("preco_atacado");
                String quantidadeAtacado = this.conecta.getRs().getString("quantidade_atacado");
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

    private String montarSqlDePesquisaDeProduto(String pesquisa) {
        String sql = "select \n"
                + "  first 50 \n"
                + "  PRREFERE as referencia\n"
                + " ,MAX(PRDESCRI) as descricao\n"
                + " ,MAX(EEPLQTB1) as preco\n"
                + " ,MAX(PRCODBAR) as codigo_barra\n"
                + " ,MAX(EET2PVD1) as preco_atacado\n"
                + " ,MAX(PRQTDATA) as quantidade_atacado\n"
                + "from \n"
                + "  scea07 \n"
                + "inner join\n"
                + "  scea01 on(PRREFERE=EEREFERE) \n"
                + "left outer join\n"
                + "  scea09 on(RAREFERE=EEREFERE)\n"
                + "where \n"
                + "   PRDATCAN is null\n";
        sql += this.adicionarCondicao(pesquisa);
        sql += "group by\n"
                + "   referencia\n"
                + "order by \n"
                + "   descricao";
        return sql;
    }

    private String montarSqlDePesquisaDeProdutoApenasPorReferencia(String pesquisa) {
        String sql = "select \n"
                + "  first 1 \n"
                + "  PRREFERE as referencia\n"
                + " ,MAX(PRDESCRI) as descricao\n"
                + " ,MAX(EEPLQTB1) as preco\n"
                + " ,MAX(PRCODBAR) as codigo_barra\n"
                + " ,MAX(EET2PVD1) as preco_atacado\n"
                + " ,MAX(PRQTDATA) as quantidade_atacado\n"
                + "from \n"
                + "  scea07 \n"
                + "inner join \n"
                + "  scea01 on(PRREFERE=EEREFERE) \n"
                + "left outer join\n"
                + "  scea09 on(RAREFERE=EEREFERE)\n"
                + "where \n"
                + "   PRDATCAN is NULL\n"
                + "AND\n"
                + "(\n"
                + "   PRREFERE='" + pesquisa + "' \n"
                + "or PRCODBAR='" + pesquisa + "' \n"
                + "or RAREFAUX='" + pesquisa + "' \n"
                + ")\n"
                + " group by \n"
                + "   referencia\n";
        return sql;
    }

    private String adicionarCondicao(String pesquisa) {
        String palavras[] = pesquisa.split(" ");
        String condicao = "";
        for (String palavra : palavras) {
            condicao += "AND (\n"
                    + "   PRDESCRI like '%" + palavra + "%' \n"
                    + "or PRAPLICA like '%" + palavra + "%'\n"
                    + "or PRREFERE='" + palavra + "' \n"
                    + "or PRCODBAR='" + palavra + "'\n"
                    + "or RAREFAUX='" + palavra + "'\n"
                    + ")\n";
        }
        return condicao;
    }

}
