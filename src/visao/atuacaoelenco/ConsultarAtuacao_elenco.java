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
public class ConsultarAtuacao_elenco extends javax.swing.JFrame {

    public ConsultarAtuacao_elenco() {
        initComponents();
        carregarTabela("");
        carregarCombos();
    }
    
    private void carregarCombos() {
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            
            // Carregar atores para o combo
            String sqlAtor = "SELECT ID_Elenco, nome FROM elenco ORDER BY nome";
            Statement stmtAtor = conn.createStatement();
            ResultSet rsAtor = stmtAtor.executeQuery(sqlAtor);
            jComboBoxAtor.addItem("-- Selecione um Ator --");
            while (rsAtor.next()) {
                jComboBoxAtor.addItem(rsAtor.getInt("ID_Elenco") + " - " + rsAtor.getString("nome"));
            }
            
            // Carregar filmes para o combo
            String sqlFilme = "SELECT ID_Filme, nome_filme FROM filme ORDER BY nome_filme";
            Statement stmtFilme = conn.createStatement();
            ResultSet rsFilme = stmtFilme.executeQuery(sqlFilme);
            jComboBoxFilme.addItem("-- Selecione um Filme --");
            while (rsFilme.next()) {
                jComboBoxFilme.addItem(rsFilme.getInt("ID_Filme") + " - " + rsFilme.getString("nome_filme"));
            }
            
            // Carregar tipos de participação
            String sqlTipo = "SELECT ID_TipoElenco, descricao_elenco FROM tipo_elenco ORDER BY descricao_elenco";
            Statement stmtTipo = conn.createStatement();
            ResultSet rsTipo = stmtTipo.executeQuery(sqlTipo);
            jComboBoxTipo.addItem("-- Selecione um Tipo --");
            while (rsTipo.next()) {
                jComboBoxTipo.addItem(rsTipo.getInt("ID_TipoElenco") + " - " + rsTipo.getString("descricao_elenco"));
            }
            
            rsAtor.close();
            rsFilme.close();
            rsTipo.close();
            stmtAtor.close();
            stmtFilme.close();
            stmtTipo.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar combos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void carregarTabela(String filtro) {
        try {
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
            modelo.setRowCount(0);
            
            String sql = "";
            
            if (filtro.equals("ator")) {
                // Pesquisar por ator - mostra todos os filmes que ele atuou
                sql = "SELECT ae.ID_atuacaoelenco, e.nome as ator, f.nome_filme, t.descricao_elenco as participacao " +
                      "FROM atuacao_elenco ae " +
                      "INNER JOIN elenco e ON ae.ID_Elenco = e.ID_Elenco " +
                      "INNER JOIN filme f ON ae.ID_Filme = f.ID_Filme " +
                      "INNER JOIN tipo_elenco t ON ae.ID_TipoElenco = t.ID_TipoElenco " +
                      "WHERE e.ID_Elenco = ? " +
                      "ORDER BY f.nome_filme";
            } else if (filtro.equals("filme")) {
                // Pesquisar por filme - mostra todos os atores do filme
                sql = "SELECT ae.ID_atuacaoelenco, e.nome as ator, f.nome_filme, t.descricao_elenco as participacao " +
                      "FROM atuacao_elenco ae " +
                      "INNER JOIN elenco e ON ae.ID_Elenco = e.ID_Elenco " +
                      "INNER JOIN filme f ON ae.ID_Filme = f.ID_Filme " +
                      "INNER JOIN tipo_elenco t ON ae.ID_TipoElenco = t.ID_TipoElenco " +
                      "WHERE f.ID_Filme = ? " +
                      "ORDER BY e.nome";
            } else if (filtro.equals("tipo")) {
                // Pesquisar por tipo de participação
                sql = "SELECT ae.ID_atuacaoelenco, e.nome as ator, f.nome_filme, t.descricao_elenco as participacao " +
                      "FROM atuacao_elenco ae " +
                      "INNER JOIN elenco e ON ae.ID_Elenco = e.ID_Elenco " +
                      "INNER JOIN filme f ON ae.ID_Filme = f.ID_Filme " +
                      "INNER JOIN tipo_elenco t ON ae.ID_TipoElenco = t.ID_TipoElenco " +
                      "WHERE t.ID_TipoElenco = ? " +
                      "ORDER BY f.nome_filme, e.nome";
            } else {
                // Mostrar tudo
                sql = "SELECT ae.ID_atuacaoelenco, e.nome as ator, f.nome_filme, t.descricao_elenco as participacao " +
                      "FROM atuacao_elenco ae " +
                      "INNER JOIN elenco e ON ae.ID_Elenco = e.ID_Elenco " +
                      "INNER JOIN filme f ON ae.ID_Filme = f.ID_Filme " +
                      "INNER JOIN tipo_elenco t ON ae.ID_TipoElenco = t.ID_TipoElenco " +
                      "ORDER BY ae.ID_atuacaoelenco DESC";
            }
            
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            if (filtro.equals("ator")) {
                String atorSel = jComboBoxAtor.getSelectedItem().toString();
                int idAtor = Integer.parseInt(atorSel.split(" - ")[0]);
                stmt.setInt(1, idAtor);
            } else if (filtro.equals("filme")) {
                String filmeSel = jComboBoxFilme.getSelectedItem().toString();
                int idFilme = Integer.parseInt(filmeSel.split(" - ")[0]);
                stmt.setInt(1, idFilme);
            } else if (filtro.equals("tipo")) {
                String tipoSel = jComboBoxTipo.getSelectedItem().toString();
                int idTipo = Integer.parseInt(tipoSel.split(" - ")[0]);
                stmt.setInt(1, idTipo);
            }
            
            ResultSet rs = stmt.executeQuery();
            
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
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAtualizar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabelQuantidade = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxAtor = new javax.swing.JComboBox<>();
        jComboBoxFilme = new javax.swing.JComboBox<>();
        jComboBoxTipo = new javax.swing.JComboBox<>();
        btnPesquisarAtor = new javax.swing.JButton();
        btnPesquisarFilme = new javax.swing.JButton();
        btnPesquisarTipo = new javax.swing.JButton();
        btnLimparFiltros = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consultar Atuações do Elenco");

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
        jScrollPane1.setViewportView(jTable1);

        btnAtualizar.setText("Mostrar Todos");
        btnAtualizar.addActionListener(this::btnAtualizarActionPerformed);

        btnSair.setText("Sair");
        btnSair.addActionListener(this::btnSairActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel2.setText("Consultar Atuações do Elenco");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jLabel1.setText("Pesquisar:");

        jLabelQuantidade.setFont(new java.awt.Font("Segoe UI", 0, 11));
        jLabelQuantidade.setForeground(new java.awt.Color(100, 100, 100));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 11));
        jLabel3.setText("Selecione uma opção de pesquisa:");

        btnPesquisarAtor.setText("Pesquisar por Ator");
        btnPesquisarAtor.addActionListener(this::btnPesquisarAtorActionPerformed);

        btnPesquisarFilme.setText("Pesquisar por Filme");
        btnPesquisarFilme.addActionListener(this::btnPesquisarFilmeActionPerformed);

        btnPesquisarTipo.setText("Pesquisar por Tipo");
        btnPesquisarTipo.addActionListener(this::btnPesquisarTipoActionPerformed);

        btnLimparFiltros.setText("Limpar Filtros");
        btnLimparFiltros.addActionListener(this::btnLimparFiltrosActionPerformed);

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
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBoxAtor, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPesquisarAtor))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBoxFilme, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPesquisarFilme))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPesquisarTipo)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLimparFiltros)
                            .addComponent(btnAtualizar)
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
                .addGap(5, 5, 5)
                .addComponent(jLabel3)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxAtor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisarAtor)
                    .addComponent(btnAtualizar))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxFilme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisarFilme)
                    .addComponent(btnSair))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisarTipo)
                    .addComponent(btnLimparFiltros)
                    .addComponent(jLabelQuantidade))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void btnPesquisarAtorActionPerformed(java.awt.event.ActionEvent evt) {
        if (jComboBoxAtor.getSelectedIndex() == 0 || jComboBoxAtor.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um Ator para pesquisar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        carregarTabela("ator");
    }

    private void btnPesquisarFilmeActionPerformed(java.awt.event.ActionEvent evt) {
        if (jComboBoxFilme.getSelectedIndex() == 0 || jComboBoxFilme.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um Filme para pesquisar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        carregarTabela("filme");
    }

    private void btnPesquisarTipoActionPerformed(java.awt.event.ActionEvent evt) {
        if (jComboBoxTipo.getSelectedIndex() == 0 || jComboBoxTipo.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um Tipo de Participação para pesquisar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        carregarTabela("tipo");
    }

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {
        carregarTabela("");
        // Resetar os combos
        jComboBoxAtor.setSelectedIndex(0);
        jComboBoxFilme.setSelectedIndex(0);
        jComboBoxTipo.setSelectedIndex(0);
    }
    
    private void btnLimparFiltrosActionPerformed(java.awt.event.ActionEvent evt) {
        jComboBoxAtor.setSelectedIndex(0);
        jComboBoxFilme.setSelectedIndex(0);
        jComboBoxTipo.setSelectedIndex(0);
        carregarTabela("");
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

        java.awt.EventQueue.invokeLater(() -> new ConsultarAtuacao_elenco().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnLimparFiltros;
    private javax.swing.JButton btnPesquisarAtor;
    private javax.swing.JButton btnPesquisarFilme;
    private javax.swing.JButton btnPesquisarTipo;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> jComboBoxAtor;
    private javax.swing.JComboBox<String> jComboBoxFilme;
    private javax.swing.JComboBox<String> jComboBoxTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelQuantidade;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration
}