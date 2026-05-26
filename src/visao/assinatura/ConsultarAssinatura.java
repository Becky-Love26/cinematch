/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package visao.assinatura;

import controle.AssinaturaControle;
import modelo.Assinatura_plano;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class ConsultarAssinatura extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ConsultarAssinatura.class.getName());

    /**
     * Creates new form ConsultarAssinatura
     */
    public ConsultarAssinatura() {
    initComponents();
    jComboBox1.addItem("Todos");
    jComboBox1.addItem("Tipo Pagamento");
    jComboBox1.addItem("Status");
    jComboBox1.addItem("ID");
    jComboBox1.addItem("Data (ex: 2024-01-01 até 2024-12-31)");
    carregarTabela();
}

        private void carregarTabela() {
    AssinaturaControle controle = new AssinaturaControle();
    preencherTabela(controle.consultarAssinaturas());
}
        
    private void preencherTabela(ArrayList<Assinatura_plano> lista) {
    String[] colunas = {"ID", "Tipo Pagamento", "Data Assinatura", "Status", "Plano", "Usuário"};
    DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
    for (Assinatura_plano ass : lista) {
        Object[] linha = {
            ass.getID_Assinatura(),
            ass.getTipo_pagamento(),
            ass.getData_assinatura(),
            ass.getStatus_assinatura() == 1 ? "Ativo" : "Inativo",
            ass.getID_Plano().getTipo_plano(),
            ass.getID_Usuario().getNome()
        };
        modelo.addRow(linha);
    }
    jTable1.setModel(modelo);
}
    
    
    private void filtrar() {
    String filtroSelecionado = jComboBox1.getSelectedItem().toString();
    String valor = jTextField1.getText().trim();
    AssinaturaControle controle = new AssinaturaControle();
    ArrayList<Assinatura_plano> lista;

    switch (filtroSelecionado) {
        case "Tipo Pagamento":
            lista = controle.consultarPorTipoPagamento(valor);
            break;
        case "Status":
            lista = controle.consultarPorStatus(Integer.parseInt(valor)); // 1 = Ativo, 0 = Inativo
            break;
        case "ID":
            lista = new ArrayList<>();
            lista.add(controle.consultarAssinaturaPorID(Integer.parseInt(valor)));
            break;
        case "Data (ex: 2024-01-01 até 2024-12-31)":
            String[] datas = valor.split(" até ");
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                java.util.Date inicio = sdf.parse(datas[0].trim());
                java.util.Date fim = sdf.parse(datas[1].trim());
                lista = controle.consultarPorDataAssinatura(inicio, fim);
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Formato de data inválido!\nUse: 2024-01-01 até 2024-12-31");
                return;
            }
            break;
        default:
            lista = controle.consultarAssinaturas();
            break;
    }

    preencherTabela(lista);
}
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Consultar assinatura");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tipo Pagamento", "Data Assinatura", "Status", "Planos", "Usuários"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/logopp.png"))); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos" }));

        jTextField1.setText("Pesquisar...");
        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        jButton1.setText("Filtrar");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Limpar");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    filtrar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jTextField1.setText("");
        jComboBox1.setSelectedIndex(0);
        carregarTabela();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

  
    public static void main(String args[]) {
 
        java.awt.EventQueue.invokeLater(() -> new ConsultarAssinatura().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
