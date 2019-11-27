package etiquetas.visao;

import etiquetas.controle.ArquivoControle;
import etiquetas.controle.ConectaBanco;
import etiquetas.modelo.Configuracao;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FormConectaBanco extends javax.swing.JFrame {

    public FormConectaBanco() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        editHost = new javax.swing.JTextField();
        editCaminho = new javax.swing.JTextField();
        btnConectar = new javax.swing.JButton();
        btnPesquisar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Conexão com Banco de dados");

        editHost.setText("localhost");

        btnConectar.setText("Conectar");
        btnConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConectarActionPerformed(evt);
            }
        });

        btnPesquisar.setText("...");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        jLabel2.setText("Host");

        jLabel3.setText("Caminho");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editHost)
                            .addComponent(editCaminho))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(btnConectar)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editCaminho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(btnConectar)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(430, 221));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConectarActionPerformed
        ConectaBanco conectaBanco = new ConectaBanco();
        boolean conexao = conectaBanco.conexao(editHost.getText(), editCaminho.getText());
        if (conexao) {
            try {
                ArquivoControle arquivoControle = new ArquivoControle();
                Configuracao configuracao = new Configuracao();
                configuracao.setHost(this.editHost.getText());
                configuracao.setCaminho(this.editCaminho.getText());
                arquivoControle.escreverNoArquivo(configuracao);
                conectaBanco.desconecta();
                FormCriarEtiquetas formCriarEtiquetas = new FormCriarEtiquetas();
                formCriarEtiquetas.setVisible(true);
                dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao escrever no arquivo.");
            }
            return;
        }
        JOptionPane.showMessageDialog(null, "Não conseguiu conexão com o banco.");

    }//GEN-LAST:event_btnConectarActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        JFileChooser chooser = new JFileChooser("C:\\omega\\dba\\");
        chooser.setFileFilter(new FileNameExtensionFilter("*.gdb", "gdb"));
        int retorno = chooser.showOpenDialog(new JDialog());
        if (retorno == JFileChooser.APPROVE_OPTION) {
            editCaminho.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_btnPesquisarActionPerformed

    public static void main(String args[]) {
        Arrays.asList(javax.swing.UIManager.getInstalledLookAndFeels()).stream()
                .filter(name -> "Nimbus".equals(name.getName()))
                .forEach(info -> {
                    try {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(FormConectaBanco.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
        java.awt.EventQueue.invokeLater(() -> new FormConectaBanco().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConectar;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JTextField editCaminho;
    private javax.swing.JTextField editHost;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
