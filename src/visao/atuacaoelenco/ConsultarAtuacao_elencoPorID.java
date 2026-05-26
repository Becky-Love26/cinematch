/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package visao.atuacaoelenco;

import conexao.ConexaoMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aluno
 */
public class ConsultarAtuacao_elencoPorID extends javax.swing.JFrame {

    public ConsultarAtuacao_elencoPorID() {
        initComponents();
        limparTabela();
    }
    
    private void limparTabela() {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);
    }
    
    private void carregarTabela(String tipoPesquisa, int id) {
        try {
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
            modelo.setRowCount(0);
            
            String sql = "";
            
            if (tipoPesquisa.equals("atuacao")) {
                // Pesquisar por ID da atuação
                sql = "SELECT ae.ID_atuacaoelenco, e.nome as ator, f.nome_filme, t.descricao_elenco as participacao " +
                      "FROM atuacao_elenco ae " +
                      "INNER JOIN elenco e ON ae.ID_Elenco = e.ID_Elenco " +
                      "INNER JOIN filme f ON ae.ID_Filme = f.ID_Filme " +
                      "INNER JOIN tipo_elenco t ON ae.ID_TipoElenco = t.ID_TipoElenco " +
                      "WHERE ae.ID_atuacaoelenco = ?";
            } else if (tipoPesquisa.equals("ator")) {
                // Pesquisar por ID do Ator
                sql = "SELECT ae.ID_atuacaoelenco, e.nome as ator, f.nome_filme, t.descricao_elenco as participacao " +
                      "FROM atuacao_elenco ae " +
                      "INNER JOIN elenco e ON ae.ID_Elenco = e.ID_Elenco " +
                      "INNER JOIN filme f ON ae.ID_Filme = f.ID_Filme " +
                      "INNER JOIN tipo_elenco t ON ae.ID_TipoElenco = t.ID_TipoElenco " +
                      "WHERE e.ID_Elenco = ? " +
                      "ORDER BY f.nome_filme";
            } else if (tipoPesquisa.equals("filme")) {
                // Pesquisar por ID do Filme
                sql = "SELECT ae.ID_atuacaoelenco, e.nome as ator, f.nome_filme, t.descricao_elenco as participacao " +
                      "FROM atuacao_elenco ae " +
                      "INNER JOIN elenco e ON ae.ID_Elenco = e.ID_Elenco " +
                      "INNER JOIN filme f ON ae.ID_Filme = f.ID_Filme " +
                      "INNER JOIN tipo_elenco t ON ae.ID_TipoElenco = t.ID_TipoElenco " +
                      "WHERE f.ID_Filme = ? " +
                      "ORDER BY e.nome";
            } else if (tipoPesquisa.equals("tipo")) {
                // Pesquisar por ID do Tipo
                sql = "SELECT ae.ID_atuacaoelenco, e.nome as ator, f.nome_filme, t.descricao_elenco as participacao " +
                      "FROM atuacao_elenco ae " +
                      "INNER JOIN elenco e ON ae.ID_Elenco = e.ID_Elenco " +
                      "INNER JOIN filme f ON ae.ID_Filme = f.ID_Filme " +
                      "INNER JOIN tipo_elenco t ON ae.ID_TipoElenco = t.ID_TipoElenco " +
                      "WHERE t.ID_TipoElenco = ? " +
                      "ORDER BY f.nome_filme, e.nome";
            }
            
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("ID_atuacaoelenco"),
                    rs.getString("ator"),
                    rs.getString("nome_filme"),
                    rs.getString("participacao")
                });
            }
            
            if (modelo.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Nenhum registro encontrado com o ID " + id + "!", "Informação", JOptionPane.INFORMATION_MESSAGE);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            jLabelQuantidade.setText("Total de registros encontrados: " + modelo.getRowCount());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao pesquisar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnSair = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabelQuantidade = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        btnPesquisarAtuacao = new javax.swing.JButton();
        btnPesquisarAtor = new javax.swing.JButton();
        btnPesquisarFilme = new javax.swing.JButton();
        btnPesquisarTipo = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pesquisar Atuações por ID");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Atuação", "Ator/Atriz", "Filme", "Tipo de Participação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        btnSair.setText("Sair");
        btnSair.addActionListener(this::btnSairActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel2.setText("Pesquisar Atuações por ID");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jLabel1.setText("Digite o ID e escolha o tipo de pesquisa:");

        jLabelQuantidade.setFont(new java.awt.Font("Segoe UI", 0, 11));
        jLabelQuantidade.setForeground(new java.awt.Color(100, 100, 100));

        jLabel3.setText("ID:");

        btnPesquisarAtuacao.setText("Pesquisar por ID da Atuação");
        btnPesquisarAtuacao.addActionListener(this::btnPesquisarAtuacaoActionPerformed);

        btnPesquisarAtor.setText("Pesquisar por ID do Ator");
        btnPesquisarAtor.addActionListener(this::btnPesquisarAtorActionPerformed);

        btnPesquisarFilme.setText("Pesquisar por ID do Filme");
        btnPesquisarFilme.addActionListener(this::btnPesquisarFilmeActionPerformed);

        btnPesquisarTipo.setText("Pesquisar por ID do Tipo");
        btnPesquisarTipo.addActionListener(this::btnPesquisarTipoActionPerformed);

        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(this::btnLimparActionPerformed);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 10));
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Ex: 1, 2, 3...");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 10));
        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setText("Mostra todos os filmes que o ator participou");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 10));
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Mostra todos os atores do filme");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 10));
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("Mostra todas as atuações com esse tipo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnPesquisarAtuacao)
                                    .addComponent(btnPesquisarAtor)
                                    .addComponent(btnPesquisarFilme)
                                    .addComponent(btnPesquisarTipo))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLimpar)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(btnSair)
                            .addComponent(jLabelQuantidade))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPesquisarAtuacao)
                        .addGap(5, 5, 5)
                        .addComponent(btnPesquisarAtor)
                        .addGap(5, 5, 5)
                        .addComponent(btnPesquisarFilme)
                        .addGap(5, 5, 5)
                        .addComponent(btnPesquisarTipo))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnLimpar)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel5)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel6)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel7)
                        .addGap(10, 10, 10)
                        .addComponent(btnSair)
                        .addGap(10, 10, 10)
                        .addComponent(jLabelQuantidade)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void btnPesquisarAtuacaoActionPerformed(java.awt.event.ActionEvent evt) {
        if (txtID.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um ID!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = Integer.parseInt(txtID.getText().trim());
            carregarTabela("atuacao", id);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnPesquisarAtorActionPerformed(java.awt.event.ActionEvent evt) {
        if (txtID.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o ID do Ator!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = Integer.parseInt(txtID.getText().trim());
            carregarTabela("ator", id);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnPesquisarFilmeActionPerformed(java.awt.event.ActionEvent evt) {
        if (txtID.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o ID do Filme!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = Integer.parseInt(txtID.getText().trim());
            carregarTabela("filme", id);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnPesquisarTipoActionPerformed(java.awt.event.ActionEvent evt) {
        if (txtID.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o ID do Tipo!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = Integer.parseInt(txtID.getText().trim());
            carregarTabela("tipo", id);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {
        txtID.setText("");
        limparTabela();
        jLabelQuantidade.setText("");
    }

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> new ConsultarAtuacao_elencoPorID().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnPesquisarAtor;
    private javax.swing.JButton btnPesquisarAtuacao;
    private javax.swing.JButton btnPesquisarFilme;
    private javax.swing.JButton btnPesquisarTipo;
    private javax.swing.JButton btnSair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelQuantidade;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtID;
    // End of variables declaration
}