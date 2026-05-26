/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package visao.atuacaoelenco;

import conexao.ConexaoMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author aluno
 */
public class InserirAtuacao_elenco extends javax.swing.JFrame {

    public InserirAtuacao_elenco() {
        initComponents();
        carregarCombos();
    }
    
    private void carregarCombos() {
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            // Carregar elencos
            try (Connection conn = conexao.conectar()) {
                // Carregar elencos
                String sqlElenco = "SELECT ID_Elenco, nome FROM elenco ORDER BY nome";
                Statement stmtFilme;
                Statement stmtTipo;
                try (Statement stmtElenco = conn.createStatement()) {
                    ResultSet rsElenco = stmtElenco.executeQuery(sqlElenco);
                    while (rsElenco.next()) {
                        jComboBoxElenco.addItem(rsElenco.getInt("ID_Elenco") + " - " + rsElenco.getString("nome"));
                    }   // Carregar filmes
                    String sqlFilme = "SELECT ID_Filme, nome_filme FROM filme ORDER BY nome_filme";
                    stmtFilme = conn.createStatement();
                    ResultSet rsFilme = stmtFilme.executeQuery(sqlFilme);
                    while (rsFilme.next()) {
                        jComboBoxFilme.addItem(rsFilme.getInt("ID_Filme") + " - " + rsFilme.getString("nome_filme"));
                    }   // Carregar tipos de elenco
                    String sqlTipo = "SELECT ID_TipoElenco, descricao_elenco FROM tipo_elenco ORDER BY descricao_elenco";
                    stmtTipo = conn.createStatement();
                    ResultSet rsTipo = stmtTipo.executeQuery(sqlTipo);
                    while (rsTipo.next()) {
                        jComboBoxTipoElenco.addItem(rsTipo.getInt("ID_TipoElenco") + " - " + rsTipo.getString("descricao_elenco"));
                    }   rsElenco.close();
                    rsFilme.close();
                    rsTipo.close();
                }
                stmtFilme.close();
                stmtTipo.close();
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jComboBoxElenco = new javax.swing.JComboBox<>();
        jComboBoxFilme = new javax.swing.JComboBox<>();
        jComboBoxTipoElenco = new javax.swing.JComboBox<>();
        btnInserir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnLimpar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnVerConsultar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Inserir Atuação no Elenco");

        btnInserir.setText("Inserir");
        btnInserir.addActionListener(this::btnInserirActionPerformed);

        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(this::btnLimparActionPerformed);

        btnSair.setText("Sair");
        btnSair.addActionListener(this::btnSairActionPerformed);
        
        btnVerConsultar.setText("Ver Todas as Atuações");
        btnVerConsultar.addActionListener(this::btnVerConsultarActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel2.setText("Inserir Atuação no Elenco");

        jLabel1.setText("Elenco:");

        jLabel3.setText("Filme:");

        jLabel4.setText("Tipo do Elenco:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxElenco, 0, 302, Short.MAX_VALUE)
                            .addComponent(jComboBoxFilme, 0, 302, Short.MAX_VALUE)
                            .addComponent(jComboBoxTipoElenco, 0, 302, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnInserir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSair)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnVerConsultar)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxElenco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxFilme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxTipoElenco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInserir)
                    .addComponent(btnLimpar)
                    .addComponent(btnSair)
                    .addComponent(btnVerConsultar))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void btnInserirActionPerformed(java.awt.event.ActionEvent evt) {
        if (jComboBoxElenco.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um Elenco!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (jComboBoxFilme.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um Filme!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (jComboBoxTipoElenco.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um Tipo de Elenco!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            String elencoStr = jComboBoxElenco.getSelectedItem().toString();
            String filmeStr = jComboBoxFilme.getSelectedItem().toString();
            String tipoStr = jComboBoxTipoElenco.getSelectedItem().toString();
            
            int idElenco = Integer.parseInt(elencoStr.split(" - ")[0]);
            int idFilme = Integer.parseInt(filmeStr.split(" - ")[0]);
            int idTipo = Integer.parseInt(tipoStr.split(" - ")[0]);
            
            String sql = "INSERT INTO atuacao_elenco (ID_Elenco, ID_Filme, ID_TipoElenco) VALUES (?, ?, ?)";
            
            ConexaoMySQL conexao = new ConexaoMySQL();
            try (Connection conn = conexao.conectar()) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, idElenco);
                stmt.setInt(2, idFilme);
                stmt.setInt(3, idTipo);
                
                int resultado = stmt.executeUpdate();
                
                if (resultado > 0) {
                    JOptionPane.showMessageDialog(this, "Atuação inserida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                    
                    int resposta = JOptionPane.showConfirmDialog(this,
                            "Deseja ver todas as atuações cadastradas?",
                            "Ver Lista",
                            JOptionPane.YES_NO_OPTION);
                    
                    if (resposta == JOptionPane.YES_OPTION) {
                        abrirConsulta();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao inserir atuação!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
                
                stmt.close();
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void btnVerConsultarActionPerformed(java.awt.event.ActionEvent evt) {
        abrirConsulta();
    }
    
    private void abrirConsulta() {
        InserirAtuacao_elenco consulta = new InserirAtuacao_elenco();
        consulta.setVisible(true);
    }
    
    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {
        limparCampos();
    }
    
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }
    
    private void limparCampos() {
        jComboBoxElenco.setSelectedIndex(-1);
        jComboBoxFilme.setSelectedIndex(-1);
        jComboBoxTipoElenco.setSelectedIndex(-1);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
        }

        java.awt.EventQueue.invokeLater(() -> new InserirAtuacao_elenco().setVisible(true));
    }

    // Variables declaration
    private javax.swing.JButton btnInserir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnVerConsultar;
    private javax.swing.JComboBox<String> jComboBoxElenco;
    private javax.swing.JComboBox<String> jComboBoxFilme;
    private javax.swing.JComboBox<String> jComboBoxTipoElenco;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration
}