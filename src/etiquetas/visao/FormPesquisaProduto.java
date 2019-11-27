package etiquetas.visao;

import etiquetas.controle.ProdutoControle;
import etiquetas.modelo.FieldToUpperCase;
import etiquetas.modelo.ModelPesquisa;
import etiquetas.modelo.Produto;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class FormPesquisaProduto extends javax.swing.JDialog {

    private final ProdutoControle produtoControle;
    private boolean sai = false;
    private final String[] colunas = {"Referencia", "Descrição", "Codigo Barras", "Preço", "Preço Atacado"};
    private String empresa;
    String referencia;

    public FormPesquisaProduto(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        editPesquisa.setDocument(new FieldToUpperCase());
        produtoControle = new ProdutoControle();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editPesquisa = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pesquisa de Produto");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        editPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                editPesquisaKeyPressed(evt);
            }
        });

        jLabel1.setText("Pesquisa");

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        tabela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(editPesquisa))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(682, 431));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void editPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editPesquisaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            preencherTabela();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            this.tabela.requestFocus();
        }
    }//GEN-LAST:event_editPesquisaKeyPressed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        if (evt.getClickCount() == 2) {
            if (selecionarReferencia()) return;
            dispose();
        }
    }//GEN-LAST:event_tabelaMouseClicked

    private boolean selecionarReferencia() {
        int index = this.tabela.getSelectedRow();
        if (index == -1 || this.sai) {
            this.referencia ="";
            return true;
        }
        this.referencia = String.valueOf(this.tabela.getValueAt(index, 0));
        return false;
    }
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.sai = true;
    }//GEN-LAST:event_formWindowClosing

    private void tabelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (selecionarReferencia()) return;
            dispose();
        }
    }//GEN-LAST:event_tabelaKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (this.tabela.getModel().getRowCount() == 0) {
            preencherTabela();
        }
    }//GEN-LAST:event_formWindowOpened

    private void preencherTabela() {
        List<Produto> produtos = produtoControle.listarProdutos(this.editPesquisa.getText().toUpperCase(), this.empresa);
        ModelPesquisa modelPesquisa = new ModelPesquisa(colunas, produtos);
        this.tabela.setModel(modelPesquisa);
        this.tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.tabela.getColumnModel().getColumn(1).setPreferredWidth(250);
        DefaultTableCellRenderer precoCellRenderer = new DefaultTableCellRenderer();
        precoCellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        this.tabela.getColumnModel().getColumn(3).setPreferredWidth(50);
        this.tabela.getColumnModel().getColumn(3).setCellRenderer(precoCellRenderer);
        DefaultTableCellRenderer precoAtacadoCellRenderer = new DefaultTableCellRenderer();
        precoAtacadoCellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        this.tabela.getColumnModel().getColumn(4).setPreferredWidth(80);
        this.tabela.getColumnModel().getColumn(4).setCellRenderer(precoAtacadoCellRenderer);
    }

    public void setPesquisa(String produto) {
        this.editPesquisa.setText(produto.toUpperCase());
    }

    public Produto getProduto() {
        if("".equals(this.referencia)) return null;
        Produto produto = this.produtoControle.buscarProduto(this.referencia, this.empresa);
        this.sai = false;
        return produto;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPesquisaProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            FormPesquisaProduto dialog = new FormPesquisaProduto(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField editPesquisa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
