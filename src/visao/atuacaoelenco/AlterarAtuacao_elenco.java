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
public class AlterarAtuacao_elenco extends javax.swing.JFrame {
    
    private int idSelecionado = -1;

    public AlterarAtuacao_elenco() {
        initComponents();
        carregarTabela();
        carregarCombos();
    }
    
    private void carregarCombos() {
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            
            // Carregar elencos
            String sqlElenco = "SELECT ID_Elenco, nome FROM elenco ORDER BY nome";
            Statement stmtElenco = conn.createStatement();
            ResultSet rsElenco = stmtElenco.executeQuery(sqlElenco);
            jComboBoxElenco.addItem("-- Selecione um Elenco --");
            while (rsElenco.next()) {
                jComboBoxElenco.addItem(rsElenco.getInt("ID_Elenco") + " - " + rsElenco.getString("nome"));
            }
            
            // Carregar filmes
            String sqlFilme = "SELECT ID_Filme, nome_filme FROM filme ORDER BY nome_filme";
            Statement stmtFilme = conn.createStatement();
            ResultSet rsFilme = stmtFilme.executeQuery(sqlFilme);
            jComboBoxFilme.addItem("-- Selecione um Filme --");
            while (rsFilme.next()) {
                jComboBoxFilme.addItem(rsFilme.getInt("ID_Filme") + " - " + rsFilme.getString("nome_filme"));
            }
            
            // Carregar tipos de elenco
            String sqlTipo = "SELECT ID_TipoElenco, descricao_elenco FROM tipo_elenco ORDER BY descricao_elenco";
            Statement stmtTipo = conn.createStatement();
            ResultSet rsTipo = stmtTipo.executeQuery(sqlTipo);
            jComboBoxTipoElenco.addItem("-- Selecione um Tipo --");
            while (rsTipo.next()) {
                jComboBoxTipoElenco.addItem(rsTipo.getInt("ID_TipoElenco") + " - " + rsTipo.getString("descricao_elenco"));
            }
            
            rsElenco.close();
            rsFilme.close();
            rsTipo.close();
            stmtElenco.close();
            stmtFilme.close();
            stmtTipo.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar combos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void carregarTabela() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
            modelo.setRowCount(0);
            
            String sql = "SELECT ae.ID_atuacaoelenco, e.nome as ator, f.nome_filme, t.descricao_elenco as participacao " +
                         "FROM atuacao_elenco ae " +
                         "INNER JOIN elenco e ON ae.ID_Elenco = e.ID_Elenco " +
                         "INNER JOIN filme f ON ae.ID_Filme = f.ID_Filme " +
                         "INNER JOIN tipo_elenco t ON ae.ID_TipoElenco = t.ID_TipoElenco " +
                         "ORDER BY ae.ID_atuacaoelenco";
            
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("ID_atuacaoelenco"),
                    rs.getString("ator"),
                    rs.getString("nome_filme"),
                    rs.getString("participacao")
                });
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            jLabelQuantidade.setText("Total de registros: " + modelo.getRowCount());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void carregarDadosParaEdicao() {
        try {
            String sql = "SELECT ID_Elenco, ID_Filme, ID_TipoElenco FROM atuacao_elenco WHERE ID_atuacaoelenco = ?";
            
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idSelecionado);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int idElenco = rs.getInt("ID_Elenco");
                int idFilme = rs.getInt("ID_Filme");
                int idTipo = rs.getInt("ID_TipoElenco");
                
                // Selecionar o elenco no combo
                for (int i = 0; i < jComboBoxElenco.getItemCount(); i++) {
                    String item = jComboBoxElenco.getItemAt(i);
                    if (item.startsWith(String.valueOf(idElenco) + " -")) {
                        jComboBoxElenco.setSelectedIndex(i);
                        break;
                    }
                }
                
                // Selecionar o filme no combo
                for (int i = 0; i < jComboBoxFilme.getItemCount(); i++) {
                    String item = jComboBoxFilme.getItemAt(i);
                    if (item.startsWith(String.valueOf(idFilme) + " -")) {
                        jComboBoxFilme.setSelectedIndex(i);
                        break;
                    }
                }
                
                // Selecionar o tipo no combo
                for (int i = 0; i < jComboBoxTipoElenco.getItemCount(); i++) {
                    String item = jComboBoxTipoElenco.getItemAt(i);
                    if (item.startsWith(String.valueOf(idTipo) + " -")) {
                        jComboBoxTipoElenco.setSelectedIndex(i);
                        break;
                    }
                }
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados para edição: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void limparCampos() {
        jComboBoxElenco.setSelectedIndex(0);
        jComboBoxFilme.setSelectedIndex(0);
        jComboBoxTipoElenco.setSelectedIndex(0);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAlterar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxElenco = new javax.swing.JComboBox<>();
        jComboBoxFilme = new javax.swing.JComboBox<>();
        jComboBoxTipoElenco = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabelQuantidade = new javax.swing.JLabel();
        btnAtualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Alterar Atuação do Elenco");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Ator/Atriz", "Filme", "Tipo de Participação"
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

        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(this::btnAlterarActionPerformed);

        btnLimpar.setText("Limpar Campos");
        btnLimpar.addActionListener(this::btnLimparActionPerformed);

        btnSair.setText("Sair");
        btnSair.addActionListener(this::btnSairActionPerformed);
        
        btnAtualizar.setText("Atualizar Tabela");
        btnAtualizar.addActionListener(this::btnAtualizarActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel2.setText("Alterar Atuação do Elenco");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jLabel1.setText("Selecione um registro na tabela para alterar:");

        jLabel3.setText("Elenco:");

        jLabel4.setText("Filme:");

        jLabel5.setText("Tipo do Elenco:");

        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Clique na linha da tabela para carregar os dados");

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
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxElenco, 0, 300, Short.MAX_VALUE)
                            .addComponent(jComboBoxFilme, 0, 300, Short.MAX_VALUE)
                            .addComponent(jComboBoxTipoElenco, 0, 300, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAlterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpar)
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
                .addComponent(jLabel6)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxElenco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxFilme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBoxTipoElenco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAlterar)
                    .addComponent(btnLimpar)
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
            carregarDadosParaEdicao();
            jLabelQuantidade.setText("Editando registro ID: " + idSelecionado);
        }
    }

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um registro na tabela primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (jComboBoxElenco.getSelectedIndex() == 0 || jComboBoxFilme.getSelectedIndex() == 0 || jComboBoxTipoElenco.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Pega os IDs selecionados
            String elencoStr = jComboBoxElenco.getSelectedItem().toString();
            String filmeStr = jComboBoxFilme.getSelectedItem().toString();
            String tipoStr = jComboBoxTipoElenco.getSelectedItem().toString();
            
            int idElenco = Integer.parseInt(elencoStr.split(" - ")[0]);
            int idFilme = Integer.parseInt(filmeStr.split(" - ")[0]);
            int idTipo = Integer.parseInt(tipoStr.split(" - ")[0]);
            
            // Confirmar alteração
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Tem certeza que deseja alterar a atuação de ID " + idSelecionado + "?", 
                "Confirmar Alteração", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                String sql = "UPDATE atuacao_elenco SET ID_Elenco = ?, ID_Filme = ?, ID_TipoElenco = ? WHERE ID_atuacaoelenco = ?";
                
                ConexaoMySQL conexao = new ConexaoMySQL();
                Connection conn = conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, idElenco);
                stmt.setInt(2, idFilme);
                stmt.setInt(3, idTipo);
                stmt.setInt(4, idSelecionado);
                
                int resultado = stmt.executeUpdate();
                
                if (resultado > 0) {
                    JOptionPane.showMessageDialog(this, "Atuação alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarTabela();
                    limparCampos();
                    idSelecionado = -1;
                    jLabelQuantidade.setText("Total de registros: " + jTable1.getRowCount());
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao alterar atuação!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
                
                stmt.close();
                conn.close();
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {
        carregarTabela();
        limparCampos();
        idSelecionado = -1;
        jLabelQuantidade.setText("Total de registros: " + jTable1.getRowCount());
    }

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {
        limparCampos();
        idSelecionado = -1;
        jTable1.clearSelection();
        jLabelQuantidade.setText("Total de registros: " + jTable1.getRowCount());
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

        java.awt.EventQueue.invokeLater(() -> new AlterarAtuacao_elenco().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> jComboBoxElenco;
    private javax.swing.JComboBox<String> jComboBoxFilme;
    private javax.swing.JComboBox<String> jComboBoxTipoElenco;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelQuantidade;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration
}