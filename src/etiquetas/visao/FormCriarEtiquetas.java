package etiquetas.visao;

import etiquetas.controle.ArquivoControle;
import etiquetas.controle.ImpressaoControle;
import etiquetas.controle.ProdutoControle;
import etiquetas.controle.impressora.Argox;
import etiquetas.controle.impressora.Elgin;
import etiquetas.controle.impressora.Impressora;
import etiquetas.modelo.Configuracao;
import etiquetas.modelo.FieldToUpperCase;
import etiquetas.modelo.ModelEtiqueta;
import etiquetas.modelo.Produto;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class FormCriarEtiquetas extends javax.swing.JFrame {

    private Produto produto;
    private final ProdutoControle produtoControle;
    private final String[] colunas = {"Referencia", "Descrição", "Quantidade", "Barras", "Preço", "Preço Atacado"};
    private final List<Produto> produtos = new ArrayList<>();
    private Impressora impressora;
    private ArquivoControle arquivoControle;
    private Configuracao configuracao;

    public FormCriarEtiquetas() {
        initComponents();
        this.produtoControle = new ProdutoControle();
        this.editProduto.setDocument(new FieldToUpperCase());
        this.editQuantidade.setDocument(new FieldToNumber());
        this.editProduto.requestFocus();
        try {
            this.arquivoControle = new ArquivoControle();
            this.configuracao = this.arquivoControle.lerArquivo();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler infromações");
        }
        this.listarInformacoesPadroes();
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(999);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FormCriarEtiquetas.class.getName()).log(Level.SEVERE, null, ex);
                }
                editProduto.requestFocus();
            }
        }.start();
        this.adicionarTeclasDeAtalhos();
        this.listarImpressoras();
    }

    private void adicionarTeclasDeAtalhos() {
        InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "forward");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESCAPE");
        this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);
        this.getRootPane().getActionMap().put("forward", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                FormCriarEtiquetas.this.abrirPesquisaDeProduto();
            }
        });
        this.getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
    }

    private void listarInformacoesPadroes() {
        if (this.configuracao.getTipoEtiqueta() != null) {
            this.comboTipoEtiqueta.setSelectedItem(this.configuracao.getTipoEtiqueta());
        }
        if (this.configuracao.getImpressora() != null) {
            this.jComboBoxModeloImpressora.setSelectedItem(this.configuracao.getImpressora());
        }
        if (this.configuracao.getCompartilhamentoImpressora() != null) {
            this.jComboBoxImpressoras.setSelectedItem(this.configuracao.getCompartilhamentoImpressora());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBoxModeloImpressora = new javax.swing.JComboBox<>();
        jLabelProduto = new javax.swing.JLabel();
        editProduto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        editQuantidade = new javax.swing.JTextField();
        btAdicionar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        btImprimirSimples = new javax.swing.JButton();
        btApagarSelecionado = new javax.swing.JButton();
        btLimpar = new javax.swing.JButton();
        comboTipoEtiqueta = new javax.swing.JComboBox<>();
        jComboBoxImpressoras = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Etiquetas");

        jComboBoxModeloImpressora.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Impressora - Elgin-L42", "Impressora - Argox" }));

        jLabelProduto.setText("Produto");

        editProduto.setFocusCycleRoot(true);
        editProduto.setFocusTraversalPolicyProvider(true);
        editProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProdutoActionPerformed(evt);
            }
        });

        jLabel3.setText("Quantidade");

        editQuantidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                editQuantidadeKeyPressed(evt);
            }
        });

        btAdicionar.setMnemonic('d');
        btAdicionar.setText("Adicionar");
        btAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdicionarActionPerformed(evt);
            }
        });

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabela);

        btImprimirSimples.setMnemonic('I');
        btImprimirSimples.setText("Imprimir");
        btImprimirSimples.setEnabled(false);
        btImprimirSimples.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btImprimirSimplesActionPerformed(evt);
            }
        });

        btApagarSelecionado.setText("Apagar Selecionado");
        btApagarSelecionado.setEnabled(false);
        btApagarSelecionado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btApagarSelecionadoActionPerformed(evt);
            }
        });

        btLimpar.setMnemonic('L');
        btLimpar.setText("Limpar");
        btLimpar.setEnabled(false);
        btLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparActionPerformed(evt);
            }
        });

        comboTipoEtiqueta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Etiqueta Gondola", "Etiqueta Varejo", "Etiqueta Atacado" }));

        jComboBoxImpressoras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Impressora - Elgin-L42", "Impressora - Argox" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelProduto)
                                .addGap(75, 475, Short.MAX_VALUE))
                            .addComponent(editProduto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(editQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btAdicionar))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btImprimirSimples, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btApagarSelecionado, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboTipoEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxModeloImpressora, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxImpressoras, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxModeloImpressora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboTipoEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxImpressoras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelProduto)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAdicionar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btImprimirSimples)
                    .addComponent(btApagarSelecionado)
                    .addComponent(btLimpar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(778, 591));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void editProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProdutoActionPerformed
        if (this.editProduto.getText().trim().isEmpty()) {
            return;
        }
        Produto produtoEncontrado = this.produtoControle.buscarProduto(this.editProduto.getText());
        if (produtoEncontrado == null) {
            this.abrirPesquisaDeProduto();
            return;
        }
        this.produto = produtoEncontrado;
        jLabelProduto.setText(produto.getNome());
        editQuantidade.requestFocus();
        editQuantidade.setText("1");
        editQuantidade.selectAll();
    }//GEN-LAST:event_editProdutoActionPerformed

    private void abrirPesquisaDeProduto() {
        FormPesquisaProduto formPesquisaProduto = new FormPesquisaProduto(this, true);
        formPesquisaProduto.setPesquisa(this.editProduto.getText());
        formPesquisaProduto.setVisible(true);
        setProduto(formPesquisaProduto.getProduto());
    }

    private void btAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdicionarActionPerformed
        adicionarProduto();
        this.preencherTabela();
    }//GEN-LAST:event_btAdicionarActionPerformed

    private void editQuantidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editQuantidadeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!this.editQuantidade.getText().isEmpty()) {
                this.adicionarProduto();
                this.preencherTabela();
            }
        }
    }//GEN-LAST:event_editQuantidadeKeyPressed

    private void btApagarSelecionadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btApagarSelecionadoActionPerformed
        this.removerProduto();
        this.preencherTabela();
        this.editProduto.requestFocus();
        if (this.produtos.isEmpty()) {
            desabledButtons();
        }
    }//GEN-LAST:event_btApagarSelecionadoActionPerformed

    private void btImprimirSimplesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImprimirSimplesActionPerformed
        this.imprimir();
    }//GEN-LAST:event_btImprimirSimplesActionPerformed

    private void btLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparActionPerformed
        this.produtos.clear();
        preencherTabela();
        this.editProduto.requestFocus();
        desabledButtons();
    }//GEN-LAST:event_btLimparActionPerformed

    private void imprimir() {
        if (this.jComboBoxModeloImpressora.getSelectedIndex() == 0) {
            this.impressora = new Elgin();
        } else {
            this.impressora = new Argox();
        }
        salvarTipoDeEtiqueta();
        if (this.comboTipoEtiqueta.getSelectedIndex() == 0) {
            this.imprimirEtiquetaGondola();
            return;
        }
        if (this.comboTipoEtiqueta.getSelectedIndex() == 1) {
            this.imprimirEtiquetaVarejo();
            return;
        }
        if (this.comboTipoEtiqueta.getSelectedIndex() == 2) {
            this.imprimirEtiquetaAtcado();
        }
    }

    private void salvarTipoDeEtiqueta() {
        try {
            configuracao.setTipoEtiqueta(this.comboTipoEtiqueta.getSelectedItem().toString());
            configuracao.setImpressora(this.jComboBoxModeloImpressora.getSelectedItem().toString());
            configuracao.setCompartilhamentoImpressora(this.jComboBoxImpressoras.getSelectedItem().toString());
            this.arquivoControle.escreverNoArquivo(this.configuracao);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar no arquivo");
        }
    }

    private void imprimirEtiquetaGondola() {
        ImpressaoControle impressaoControle = new ImpressaoControle();
        String caminhoDaImpressora = this.jComboBoxImpressoras.getSelectedItem().toString();
        impressaoControle.setImpressora(caminhoDaImpressora);
        for (Produto produto : this.produtos) {
            String gerarEtiquetaGondolaTresColunas = impressora.gerarEtiquetaGondolaTresColunas(produto);
            try {
                impressaoControle.imprimirEtiqueta(gerarEtiquetaGondolaTresColunas);
            } catch (PrintException ex) {
                JOptionPane.showMessageDialog(null, "Erro de comunicação com a impressora\n verifique se ela está ligada.");
                break;
            }
        }
        this.editProduto.requestFocus();
    }

    private void imprimirEtiquetaVarejo() {
        ImpressaoControle impressaoControle = new ImpressaoControle();
        String caminhoDaImpressora = this.jComboBoxImpressoras.getSelectedItem().toString();
        impressaoControle.setImpressora(caminhoDaImpressora);
        for (Produto produto : this.produtos) {
            String gerarEtiquetaGondolaSimples = impressora.gerarEtiquetaGondolaSimples(produto);
            try {
                impressaoControle.imprimirEtiqueta(gerarEtiquetaGondolaSimples);
            } catch (PrintException ex) {
                JOptionPane.showMessageDialog(null, "Erro de comunicação com a impressora\n verifique se ela está ligada.");
                break;
            }
        }
        this.editProduto.requestFocus();
    }

    private void imprimirEtiquetaAtcado() {
        ImpressaoControle impressaoControle = new ImpressaoControle();
        String caminhoDaImpressora = this.jComboBoxImpressoras.getSelectedItem().toString();
        impressaoControle.setImpressora(caminhoDaImpressora);
        for (Produto produto : this.produtos) {
            String gerarEtiquetaGondolaAtacado = impressora.gerarEtiquetaGondolaAtacado(produto);
            try {
                impressaoControle.imprimirEtiqueta(gerarEtiquetaGondolaAtacado);
            } catch (PrintException ex) {
                JOptionPane.showMessageDialog(null, "Erro de comunicação com a impressora\n verifique se ela está ligada.");
                break;
            }
        }
        this.editProduto.requestFocus();
    }

    private void adicionarProduto() {
        if (this.produto == null) {
            JOptionPane.showMessageDialog(null, "Pesquise um produto.");
            this.editProduto.requestFocus();
            return;
        }
        if (this.editQuantidade.getText().length() >= 12) {
            JOptionPane.showMessageDialog(null, "Quantidade maior que o permitido");
            this.editQuantidade.requestFocus();
            this.editQuantidade.selectAll();
            return;
        }
        enabledButtons();
        this.produto.setQuantidadeAserImpresso(this.editQuantidade.getText());
        String nome = produto.getNome().length() > 25 ? produto.getNome().substring(0, 25) : produto.getNome();
        this.produto.setNome(nome);
        this.produtos.add(produto);
        this.limparCampos();
        this.editProduto.requestFocus();
        this.jLabelProduto.setText("Produto");
    }

    private void removerProduto() {
        int index = this.tabela.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "Selecione algum produto");
            return;
        }
        String referencia = String.valueOf(this.tabela.getValueAt(index, 0));
        this.produtos.remove(new Produto(referencia));
    }

    private void preencherTabela() {
        ModelEtiqueta modelEtiqueta = new ModelEtiqueta(colunas, produtos);
        this.tabela.setModel(modelEtiqueta);
        this.tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.tabela.getColumnModel().getColumn(1).setPreferredWidth(250);
        this.tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
        DefaultTableCellRenderer precoCellRenderer = new DefaultTableCellRenderer();
        precoCellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        this.tabela.getColumnModel().getColumn(4).setPreferredWidth(50);
        this.tabela.getColumnModel().getColumn(4).setCellRenderer(precoCellRenderer);
        DefaultTableCellRenderer precoAtacadoCellRenderer = new DefaultTableCellRenderer();
        precoAtacadoCellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        this.tabela.getColumnModel().getColumn(5).setPreferredWidth(100);
        this.tabela.getColumnModel().getColumn(5).setCellRenderer(precoAtacadoCellRenderer);
        DefaultTableCellRenderer quantidadeCellRenderer = new DefaultTableCellRenderer();
        quantidadeCellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        this.tabela.getColumnModel().getColumn(2).setCellRenderer(quantidadeCellRenderer);
    }

    private void limparCampos() {
        this.jLabelProduto.setText("");
        this.editProduto.setText("");
        this.editQuantidade.setText("");
    }

    public void setProduto(Produto produto) {
        if (produto == null) {
            return;
        }
        this.jLabelProduto.setText(produto.getNome());
        this.editProduto.setText(produto.getReferencia());
        this.editQuantidade.setText("1");
        this.editQuantidade.requestFocus();
        this.editQuantidade.selectAll();
        this.produto = produto;
    }

    private void listarImpressoras() {
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
        PrintService[] lookupPrintServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : lookupPrintServices) {
            defaultComboBoxModel.addElement(printService.getName());
        }
        this.jComboBoxImpressoras.setModel(defaultComboBoxModel);
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
            java.util.logging.Logger.getLogger(FormCriarEtiquetas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new FormCriarEtiquetas().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdicionar;
    private javax.swing.JButton btApagarSelecionado;
    private javax.swing.JButton btImprimirSimples;
    private javax.swing.JButton btLimpar;
    private javax.swing.JComboBox<String> comboTipoEtiqueta;
    private javax.swing.JTextField editProduto;
    private javax.swing.JTextField editQuantidade;
    private javax.swing.JComboBox<String> jComboBoxImpressoras;
    private javax.swing.JComboBox<String> jComboBoxModeloImpressora;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelProduto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables

    private void enabledButtons() {
        this.btImprimirSimples.setEnabled(true);
        this.btApagarSelecionado.setEnabled(true);
        this.btLimpar.setEnabled(true);
    }

    private void desabledButtons() {
        this.btImprimirSimples.setEnabled(false);
        this.btApagarSelecionado.setEnabled(false);
        this.btLimpar.setEnabled(false);
    }
}

class FieldToNumber extends PlainDocument {

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        str = str.replaceAll("\\D", "");
        super.insertString(offs, str, a);
    }
}
