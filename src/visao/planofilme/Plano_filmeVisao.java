/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package visao.planofilme;

import controle.Plano_filmeControle;
import modelo.Plano_filme;
import modelo.Filme;
import modelo.Plano;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Plano_filmeVisao extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Plano_filmeVisao.class.getName());

    private Plano_filmeControle controle;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField txtBuscaFilme, txtIdFilme, txtIdPlano;
    private JComboBox<String> cmbFiltroPlano;
    private ArrayList<Plano_filme> listaCompleta;
    private int idSelecionado = -1;

    public Plano_filmeVisao() {
        controle = new Plano_filmeControle();
        listaCompleta = new ArrayList<>();
        //initComponents();//
        configurarComponentes();  // Chama o método que cria a interface
        carregarDados();
    }
    
    private void configurarComponentes() {
        setTitle("CineMatch - Planos por Filme");
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Remove o layout padrão e cria seu próprio
        getContentPane().removeAll();
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // ===== PAINEL SUPERIOR (BUSCA) =====
        JPanel panelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusca.setBorder(BorderFactory.createTitledBorder("Buscar Filme"));
        
        txtBuscaFilme = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimparBusca = new JButton("Limpar");
        
        panelBusca.add(new JLabel("Nome do Filme:"));
        panelBusca.add(txtBuscaFilme);
        panelBusca.add(btnBuscar);
        panelBusca.add(btnLimparBusca);
        
        // ===== TABELA =====
        String[] colunas = {"ID", "ID Filme", "Filme", "ID Plano", "Plano", "Valor"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.setPreferredSize(new Dimension(760, 250));
        
        // ===== PAINEL FILTRO =====
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.setBorder(BorderFactory.createTitledBorder("Filtrar por Plano"));
        
        cmbFiltroPlano = new JComboBox<>(new String[]{"Todos", "Premium", "Família", "Básico"});
        JButton btnFiltrar = new JButton("Filtrar");
        
        panelFiltro.add(new JLabel("Plano:"));
        panelFiltro.add(cmbFiltroPlano);
        panelFiltro.add(btnFiltrar);
        
        // ===== FORMULÁRIO =====
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados da Associação"));
        
        formPanel.add(new JLabel("ID do Filme:"));
        txtIdFilme = new JTextField();
        formPanel.add(txtIdFilme);
        
        formPanel.add(new JLabel("ID do Plano (1=Premium, 2=Família, 3=Básico):"));
        txtIdPlano = new JTextField();
        formPanel.add(txtIdPlano);
        
        JLabel lblIdSelecionado = new JLabel("Nenhum selecionado");
        formPanel.add(new JLabel("ID Selecionado:"));
        formPanel.add(lblIdSelecionado);
        
        // ===== BOTÕES =====
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnInserir = new JButton("Inserir");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnAtualizarLista = new JButton("Atualizar");
        
        btnPanel.add(btnInserir);
        btnPanel.add(btnAtualizar);
        btnPanel.add(btnExcluir);
        btnPanel.add(btnLimpar);
        btnPanel.add(btnAtualizarLista);
        
        // ===== MONTAGEM DO PAINEL =====
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(panelBusca, BorderLayout.NORTH);
        panelCentro.add(scrollTabela, BorderLayout.CENTER);
        panelCentro.add(panelFiltro, BorderLayout.SOUTH);
        
        JPanel panelSul = new JPanel(new BorderLayout());
        panelSul.add(formPanel, BorderLayout.CENTER);
        panelSul.add(btnPanel, BorderLayout.SOUTH);
        
        panel.add(panelCentro, BorderLayout.CENTER);
        panel.add(panelSul, BorderLayout.SOUTH);
        
        getContentPane().add(panel);
        
        // ===== EVENTOS =====
        tabela.getSelectionModel().addListSelectionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0 && !e.getValueIsAdjusting()) {
                idSelecionado = (int) modeloTabela.getValueAt(linha, 0);
                txtIdFilme.setText(modeloTabela.getValueAt(linha, 1).toString());
                txtIdPlano.setText(modeloTabela.getValueAt(linha, 3).toString());
                lblIdSelecionado.setText(String.valueOf(idSelecionado));
            }
        });
        
        btnAtualizarLista.addActionListener(e -> carregarDados());
        
        btnBuscar.addActionListener(e -> buscarPorNome());
        txtBuscaFilme.addActionListener(e -> buscarPorNome());
        btnLimparBusca.addActionListener(e -> {
            txtBuscaFilme.setText("");
            carregarDados();
        });
        
        btnFiltrar.addActionListener(e -> filtrarPorPlano());
        
        btnInserir.addActionListener(e -> {
            if (txtIdFilme.getText().trim().isEmpty() || txtIdPlano.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                return;
            }
            
            try {
                int idFilme = Integer.parseInt(txtIdFilme.getText().trim());
                int idPlano = Integer.parseInt(txtIdPlano.getText().trim());
                
                Plano_filme pf = new Plano_filme();
                Filme f = new Filme();
                f.setID_Filme(idFilme);
                pf.setID_Filme(f);
                
                Plano p = new Plano();
                p.setID_plano(idPlano);
                pf.setID_Plano(p);
                
                String resultado = controle.inserir(pf);
                JOptionPane.showMessageDialog(null, resultado);
                
                if (resultado.contains("sucesso")) {
                    carregarDados();
                    limpar();
                    lblIdSelecionado.setText("Nenhum selecionado");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Digite números válidos!");
            }
        });
        
        btnAtualizar.addActionListener(e -> {
            if (idSelecionado == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um registro na tabela!");
                return;
            }
            
            if (txtIdFilme.getText().trim().isEmpty() || txtIdPlano.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(null, "Confirmar atualização?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    int idFilme = Integer.parseInt(txtIdFilme.getText().trim());
                    int idPlano = Integer.parseInt(txtIdPlano.getText().trim());
                    
                    Plano_filme pf = new Plano_filme();
                    pf.setID_Plano_Filme(idSelecionado);
                    
                    Filme f = new Filme();
                    f.setID_Filme(idFilme);
                    pf.setID_Filme(f);
                    
                    Plano p = new Plano();
                    p.setID_plano(idPlano);
                    pf.setID_Plano(p);
                    
                    String resultado = controle.atualizar(pf);
                    JOptionPane.showMessageDialog(null, resultado);
                    
                    if (resultado.contains("sucesso")) {
                        carregarDados();
                        limpar();
                        lblIdSelecionado.setText("Nenhum selecionado");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Digite números válidos!");
                }
            }
        });
        
        btnExcluir.addActionListener(e -> {
            if (idSelecionado == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um registro na tabela!");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(null, "Confirmar exclusão?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String resultado = controle.excluir(idSelecionado);
                JOptionPane.showMessageDialog(null, resultado);
                
                if (resultado.contains("sucesso")) {
                    carregarDados();
                    limpar();
                    lblIdSelecionado.setText("Nenhum selecionado");
                }
            }
        });
        
        btnLimpar.addActionListener(e -> limpar());
    }
    
    private void carregarDados() {
        listaCompleta = controle.listar();
        carregarTabela(listaCompleta);
    }

    private void carregarTabela(ArrayList<Plano_filme> lista) {
        modeloTabela.setRowCount(0);
        
        for (Plano_filme pf : lista) {
            String nomeFilme = "N/A";
            String tipoPlano = "N/A";
            double valor = 0;
            
            if (pf.getID_Filme() != null && pf.getID_Filme().getNome_filme() != null) {
                nomeFilme = pf.getID_Filme().getNome_filme();
            }
            
            if (pf.getID_Plano() != null) {
                if (pf.getID_Plano().getTipo_plano() != null) {
                    tipoPlano = pf.getID_Plano().getTipo_plano();
                }
                valor = pf.getID_Plano().getValor();
            }
            
            modeloTabela.addRow(new Object[]{
                pf.getID_Plano_Filme(),
                pf.getID_Filme() != null ? pf.getID_Filme().getID_Filme() : 0,
                nomeFilme,
                pf.getID_Plano() != null ? pf.getID_Plano().getID_plano() : 0,
                tipoPlano,
                String.format("R$ %.2f", valor)
            });
        }
    }

    private void buscarPorNome() {
        String termo = txtBuscaFilme.getText().toLowerCase().trim();
        if (termo.isEmpty()) {
            carregarTabela(listaCompleta);
            return;
        }
        
        ArrayList<Plano_filme> filtrados = new ArrayList<>();
        for (Plano_filme pf : listaCompleta) {
            String nomeFilme = pf.getID_Filme() != null && pf.getID_Filme().getNome_filme() != null ?
                              pf.getID_Filme().getNome_filme().toLowerCase() : "";
            if (nomeFilme.contains(termo)) {
                filtrados.add(pf);
            }
        }
        carregarTabela(filtrados);
    }

    private void filtrarPorPlano() {
        String planoSelecionado = (String) cmbFiltroPlano.getSelectedItem();
        if (planoSelecionado.equals("Todos")) {
            carregarDados();
            return;
        }
        
        int idPlano = 0;
        switch (planoSelecionado) {
            case "Premium": idPlano = 1; break;
            case "Família": idPlano = 2; break;
            case "Básico": idPlano = 3; break;
        }
        
        ArrayList<Plano_filme> filtrados = new ArrayList<>();
        for (Plano_filme pf : listaCompleta) {
            int planoId = pf.getID_Plano() != null ? pf.getID_Plano().getID_plano() : 0;
            if (planoId == idPlano) {
                filtrados.add(pf);
            }
        }
        carregarTabela(filtrados);
    }

    private void limpar() {
        txtIdFilme.setText("");
        txtIdPlano.setText("");
        tabela.clearSelection();
        idSelecionado = -1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Plano_filmeVisao().setVisible(true));
    }

    // Variables declaration - do not modify                     
    // End of variables declaration                   
}