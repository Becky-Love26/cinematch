/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package visao.atuacaoelenco;

import conexao.ConexaoMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aluno
 */
public class DeletarAtuacao_elenco extends javax.swing.JFrame {
    
    private int idSelecionado = -1;

    public DeletarAtuacao_elenco() {
        initComponents();
        carregarTabela();
    }
    
    private void carregarTabela() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
            modelo.setRowCount(0);
            
            String sql = "SELECT ae.ID_atuacaoelenco, e.nome as elenco, f.nome_filme, t.descricao_elenco " +
                         "FROM atuacao_elenco ae " +
                         "INNER JOIN elenco e ON ae.ID_Elenco = e.ID_Elenco " +
                         "INNER JOIN filme f ON ae.ID_Filme = f.ID_Filme " +
                         "INNER JOIN tipo_elenco t ON ae.ID_TipoElenco = t.ID_TipoElenco " +
                         "ORDER BY ae.ID_atuacaoelenco DESC";
            
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("ID_atuacaoelenco"),
                    rs.getString("elenco"),
                    rs.getString("nome_filme"),
                    rs.getString("descricao_elenco")
                });
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            jLabelQuantidade.setText("Total de registros: " + modelo.getRowCount());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnDeletar = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelQuantidade = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Deletar Atuação do Elenco");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Elenco", "Filme", "Tipo de Elenco"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        btnDeletar.setText("Deletar");
        btnDeletar.addActionListener(this::btnDeletarActionPerformed);

        btnAtualizar.setText("Atualizar");
        btnAtualizar.addActionListener(this::btnAtualizarActionPerformed);

        btnSair.setText("Sair");
        btnSair.addActionListener(this::btnSairActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel2.setText("Deletar Atuação do Elenco");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jLabel1.setText("Selecione um registro na tabela para deletar:");

        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("");

        jLabelQuantidade.setFont(new java.awt.Font("Segoe UI", 0, 11));
        jLabelQuantidade.setForeground(new java.awt.Color(100, 100, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnDeletar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAtualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSair)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelQuantidade)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeletar)
                    .addComponent(btnAtualizar)
                    .addComponent(btnSair)
                    .addComponent(jLabelQuantidade))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        int linha = jTable1.getSelectedRow();
        if (linha >= 0) {
            idSelecionado = (int) jTable1.getValueAt(linha, 0);
            jLabelQuantidade.setText("Registro selecionado: ID " + idSelecionado);
        }
    }

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um registro na tabela primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Tem certeza que deseja deletar a atuação de ID " + idSelecionado + "?", 
                "Confirmar Deleção", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                String sql = "DELETE FROM atuacao_elenco WHERE ID_atuacaoelenco = ?";
                
                ConexaoMySQL conexao = new ConexaoMySQL();
                Connection conn = conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, idSelecionado);
                
                int resultado = stmt.executeUpdate();
                
                if (resultado > 0) {
                    JOptionPane.showMessageDialog(this, "Atuação deletada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarTabela();
                    idSelecionado = -1;
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao deletar atuação!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
                
                stmt.close();
                conn.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {
        carregarTabela();
        idSelecionado = -1;
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

        java.awt.EventQueue.invokeLater(() -> new DeletarAtuacao_elenco().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnSair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelQuantidade;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration
}