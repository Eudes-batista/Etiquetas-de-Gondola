package etiquetas;

import etiquetas.controle.ArquivoControle;
import etiquetas.controle.ConectaBanco;
import etiquetas.modelo.Configuracao;
import etiquetas.visao.FormConectaBanco;
import etiquetas.visao.FormCriarEtiquetas;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Etiquetas {

    public static void main(String[] args) {
        try {
            ArquivoControle arquivoControle = new ArquivoControle();
            ConectaBanco conectaBanco = new ConectaBanco();
            Configuracao configuracao = arquivoControle.lerArquivo();

            if (configuracao.getHost() == null) {
                new FormConectaBanco().setVisible(true);
                return;
            }
            String host = configuracao.getHost(), banco = configuracao.getCaminho();

            if (!conectaBanco.conexao(host, banco)) {
                new FormConectaBanco().setVisible(true);
                return;
            }
            conectaBanco.desconecta();
            new FormCriarEtiquetas().setVisible(true);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar o arquivo de configuração do banco.");
        }

    }

}
