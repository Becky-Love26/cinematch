/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package visao.tipo_elenco;

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
public class ConsultarTipoElenco extends javax.swing.JFrame {

    public ConsultarTipoElenco() {
        initComponents();
        carregarTabela("");
        carregarCombos();
    }
    
    private void carregarCombos() {
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            
            String sql = "SELECT ID_TipoElenco, descricao_elenco FROM tipo_elenco ORDER BY descricao_elenco";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            jComboBoxFiltro.addItem("-- Selecione um Tipo --");
            while (rs.next()) {
                jComboBoxFiltro.addItem(rs.getInt("ID_TipoElenco") + " - " + rs.getString("descricao_elenco"));
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar combos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void carregarTabela(String filtro) {
        try {
            DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
            modelo.setRowCount(0);
            
            String sql = "";
            
            if (filtro.equals("tipo")) {
                sql = "SELECT ID_TipoElenco, descricao_elenco FROM tipo_elenco WHERE ID_TipoElenco = ? ORDER BY ID_TipoElenco";
            } else {
                sql = "SELECT ID_TipoElenco, descricao_elenco FROM tipo_elenco ORDER BY ID_TipoElenco";
            }
            
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            
            if (filtro.equals("tipo")) {
                String tipoSel = jComboBoxFiltro.getSelectedItem().toString();
                int idTipo = Integer.parseInt(tipoSel.split(" - ")[0]);
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, idTipo);
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                    modelo.addRow(new Object[]{
                        rs.getInt("ID_TipoElenco"),
                        rs.getString("descricao_elenco")
                    });
                }
                rs.close();
                stmt.close();
            } else {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                
                while (rs.next()) {
                    modelo.addRow(new Object[]{
                        rs.getInt("ID_TipoElenco"),
                        rs.getString("descricao_elenco")
                    });
                }
                rs.close();
                stmt.close();
            }
            
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
        jComboBoxFiltro = new javax.swing.JComboBox<>();
        btnPesquisar = new javax.swing.JButton();
        btnLimparFiltro = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consultar Tipos de Elenco");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Descrição"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
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
        jLabel2.setText("Consultar Tipos de Elenco");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12));
        jLabel1.setText("Lista de todos os tipos cadastrados:");

        jLabelQuantidade.setFont(new java.awt.Font("Segoe UI", 0, 11));
        jLabelQuantidade.setForeground(new java.awt.Color(100, 100, 100));

        btnPesquisar.setText("Pesquisar");
        btnPesquisar.addActionListener(this::btnPesquisarActionPerformed);

        btnLimparFiltro.setText("Limpar Filtro");
        btnLimparFiltro.addActionListener(this::btnLimparFiltroActionPerformed);

        jLabel3.setText("Pesquisar por ID:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPesquisar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimparFiltro))
                    .addGroup(layout.createSequentialGroup()
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
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar)
                    .addComponent(btnLimparFiltro))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAtualizar)
                    .addComponent(btnSair)
                    .addComponent(jLabelQuantidade))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {
        if (jComboBoxFiltro.getSelectedIndex() == 0 || jComboBoxFiltro.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um Tipo para pesquisar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        carregarTabela("tipo");
    }

    private void btnLimparFiltroActionPerformed(java.awt.event.ActionEvent evt) {
        jComboBoxFiltro.setSelectedIndex(0);
        carregarTabela("");
    }

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {
        carregarTabela("");
        jComboBoxFiltro.setSelectedIndex(0);
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

        java.awt.EventQueue.invokeLater(() -> new ConsultarTipoElenco().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnLimparFiltro;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> jComboBoxFiltro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelQuantidade;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration
}