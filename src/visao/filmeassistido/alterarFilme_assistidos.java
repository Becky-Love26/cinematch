/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package visao.filmeassistido;

import controle.Filme_AssistidosControle;
import controle.FilmeControle;
import controle.PerfilControle;
import modelo.Filme;
import modelo.Filme_assistidos;
import modelo.Perfil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class alterarFilme_assistidos extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(alterarFilme_assistidos.class.getName());

     private ArrayList<Filme> listaFilmes;
    private ArrayList<Perfil> listaPerfis;
    
    public alterarFilme_assistidos() {
        initComponents();
        carregarFilmes();
        carregarPerfis();
        
        try {
        javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("##/##/####");
        mask.setPlaceholderCharacter('_');
        mask.install(jFormattedTextField1);
    } catch (java.text.ParseException e) {
        e.printStackTrace();
    }
    }

    private void carregarFilmes() {
        listaFilmes = new FilmeControle().consultarFilmes();
        jComboBox1.removeAllItems();
        for (Filme f : listaFilmes) {
            jComboBox1.addItem(f.getNome_filme());
        }
    }

    private void carregarPerfis() {
        listaPerfis = new PerfilControle().consultarPerfis();
        jComboBox2.removeAllItems();
        for (Perfil p : listaPerfis) {
            jComboBox2.addItem(p.getNome_perfil());
        }
    }
    
     private void limparCampos() {
        jTextField1.setText("");
        jFormattedTextField1.setValue(null);
        if (!listaFilmes.isEmpty())  jComboBox1.setSelectedIndex(0);
        if (!listaPerfis.isEmpty())  jComboBox2.setSelectedIndex(0);
    }
     
     
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/logopp.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Alterar Filme Assistidos");

        jLabel3.setText("ID:");

        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        jButton1.setText("Buscar");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jLabel4.setText("Data Assistido:");

        jFormattedTextField1.addActionListener(this::jFormattedTextField1ActionPerformed);

        jLabel5.setText("Filme:");

        jLabel6.setText("Perfil:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton2.setText("Alterar");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setText("Limpar");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setText("Fechar");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jButton2)
                        .addGap(56, 56, 56)
                        .addComponent(jButton3)
                        .addGap(57, 57, 57)
                        .addComponent(jButton4)))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel2)))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(44, 44, 44))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
       
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         String idTexto = jTextField1.getText().trim();
       String dataTexto = jFormattedTextField1.getText().trim();

        if (idTexto.isEmpty() || dataTexto.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        try {
            int id = Integer.parseInt(idTexto);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date dataUtil = sdf.parse(dataTexto);

            Filme_assistidos fa = new Filme_assistidos();
            fa.setID_Assistidos(id);
            fa.setData_assistido(dataUtil);
            fa.setID_Filme(listaFilmes.get(jComboBox1.getSelectedIndex()));
            fa.setID_Perfil(listaPerfis.get(jComboBox2.getSelectedIndex()));

            String resultado = new Filme_AssistidosControle().alterarFilme_assistidos(fa);

            if ("alterado".equals(resultado)) {
                javax.swing.JOptionPane.showMessageDialog(this, "Filme assistido alterado com sucesso!");
                limparCampos();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Erro ao alterar. Código: " + resultado);
            }

        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "ID inválido.");
        } catch (java.text.ParseException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/MM/yyyy.");
        }
      }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         String idTexto = jTextField1.getText().trim();
    if (idTexto.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Digite um ID para buscar.");
        return;
    }
    try {
        int id = Integer.parseInt(idTexto);
        Filme_assistidos fa = new Filme_AssistidosControle().consultarID_Filme_assistidos(id);

        if (fa.getID_Assistidos() == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Registro não encontrado.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        jFormattedTextField1.setValue(sdf.format(fa.getData_assistido()));

        // Busca pelo ID_Filme retornado, dentro da listaFilmes já carregada
        for (int i = 0; i < listaFilmes.size(); i++) {
            if (listaFilmes.get(i).getID_Filme() == fa.getID_Filme().getID_Filme()) {
                jComboBox1.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < listaPerfis.size(); i++) {
            if (listaPerfis.get(i).getID_Perfil() == fa.getID_Perfil().getID_Perfil()) {
                jComboBox2.setSelectedIndex(i);
                break;
            }
        }

    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "ID inválido. Digite apenas números.");
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        limparCampos();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
         dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new alterarFilme_assistidos().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
