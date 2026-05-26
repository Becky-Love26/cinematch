package visao.Filme_avaliados;

import controle.Filme_avaliadosControle;
import modelo.Filme_avaliados;
import modelo.Filme;
import modelo.Perfil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Filme_avaliadosVisao extends JFrame {
   
    private Filme_avaliadosControle controle;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField txtBusca, txtResenha, txtIdFilme, txtIdPerfil;
    private JComboBox<Integer> cmbEstrelas;
    private JComboBox<String> cmbFiltroEstrelas;
    private ArrayList<Filme_avaliados> listaCompleta;
    private int idSelecionado = -1; 
   
    public Filme_avaliadosVisao() {
        controle = new Filme_avaliadosControle();
        listaCompleta = new ArrayList<>();
        initComponents();
        carregarDados();
    }
   
    private void initComponents() {
        setTitle("Avaliações de Filmes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
       
        // Painel principal
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
       
        // Painel de busca e filtro
        JPanel panelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusca.setBorder(BorderFactory.createTitledBorder("Buscar por nome do Filme ou Perfil"));
       
        txtBusca = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnLimparBusca = new JButton("Limpar");
       
        panelBusca.add(new JLabel("Nome:"));
        panelBusca.add(txtBusca);
        panelBusca.add(btnBuscar);
        panelBusca.add(btnLimparBusca);
       
        // Painel de filtro por estrelas
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.setBorder(BorderFactory.createTitledBorder("Filtrar por estrelas"));
       
        cmbFiltroEstrelas = new JComboBox<>(new String[]{"Todas", "1", "2", "3", "4", "5"});
        JButton btnFiltrar = new JButton("Filtrar");
       
        panelFiltro.add(new JLabel("Estrelas:"));
        panelFiltro.add(cmbFiltroEstrelas);
        panelFiltro.add(btnFiltrar);
       
        // Painel superior combinando busca e filtro
        JPanel panelSuperior = new JPanel(new GridLayout(2, 1));
        panelSuperior.add(panelBusca);
        panelSuperior.add(panelFiltro);
       
        // Tabela
        String[] colunas = {"ID", "Filme", "Perfil", "Estrelas", "Resenha", "Data"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // TABELA NÃO EDITÁVEL
            }
        };
        tabela = new JTable(modeloTabela);
        sorter = new TableRowSorter<>(modeloTabela);
        tabela.setRowSorter(sorter);
       
        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.setPreferredSize(new Dimension(780, 200));
       
        // Formulário
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Formulário de Avaliação"));
       
        formPanel.add(new JLabel("Resenha:"));
        txtResenha = new JTextField();
        formPanel.add(txtResenha);
       
        formPanel.add(new JLabel("Estrelas (1-5):"));
        cmbEstrelas = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        formPanel.add(cmbEstrelas);
       
        formPanel.add(new JLabel("ID do Filme:"));
        txtIdFilme = new JTextField();
        formPanel.add(txtIdFilme);
       
        formPanel.add(new JLabel("ID do Perfil:"));
        txtIdPerfil = new JTextField();
        formPanel.add(txtIdPerfil);
       
        // Informação do ID selecionado
        formPanel.add(new JLabel("ID Selecionado:"));
        JLabel lblIdSelecionado = new JLabel("Nenhum");
        formPanel.add(lblIdSelecionado);
       
        // Botões CRUD
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnInserir = new JButton("Inserir");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnLimpar = new JButton("Limpar Formulário");
        JButton btnAtualizarLista = new JButton("Atualizar Lista");
       
        btnPanel.add(btnInserir);
        btnPanel.add(btnAtualizar);
        btnPanel.add(btnExcluir);
        btnPanel.add(btnLimpar);
        btnPanel.add(btnAtualizarLista);
       
        // Adicionar ao painel principal
        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(scrollTabela, BorderLayout.CENTER);
       
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(formPanel, BorderLayout.CENTER);
        panelInferior.add(btnPanel, BorderLayout.SOUTH);
        panel.add(panelInferior, BorderLayout.SOUTH);
       
        add(panel);
       
        // ========== EVENTOS ==========
        
        btnAtualizarLista.addActionListener(e -> carregarDados());
       
        btnBuscar.addActionListener(e -> buscarPorNome());
        txtBusca.addActionListener(e -> buscarPorNome());
        btnLimparBusca.addActionListener(e -> {
            txtBusca.setText("");
            carregarTabela(listaCompleta);
        });
       
        btnFiltrar.addActionListener(e -> filtrarPorEstrelas());
       
        // SELECIONAR LINHA DA TABELA
        tabela.getSelectionModel().addListSelectionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0 && !e.getValueIsAdjusting()) {
                int modelRow = tabela.convertRowIndexToModel(linha);
                idSelecionado = (int) modeloTabela.getValueAt(modelRow, 0);
                lblIdSelecionado.setText(String.valueOf(idSelecionado));
               
                // Preencher formulário com dados da linha selecionada
                txtResenha.setText(modeloTabela.getValueAt(modelRow, 4) != null ?
                                  modeloTabela.getValueAt(modelRow, 4).toString() : "");
                cmbEstrelas.setSelectedItem(modeloTabela.getValueAt(modelRow, 3));
               
                // Buscar os IDs do filme e perfil na lista completa
                for (Filme_avaliados fa : listaCompleta) {
                    if (fa.getID_Avaliacao() == idSelecionado) {
                        if (fa.getID_Filme() != null) {
                            txtIdFilme.setText(String.valueOf(fa.getID_Filme().getID_Filme()));
                        }
                        if (fa.getID_Perfil() != null) {
                            txtIdPerfil.setText(String.valueOf(fa.getID_Perfil().getID_Perfil()));
                        }
                        break;
                    }
                }
            }
        });
       
        // INSERIR - COM VALIDAÇÕES
        btnInserir.addActionListener(e -> {
            if (validarCampos()) {
                try {
                    Filme_avaliados fa = new Filme_avaliados();
                    fa.setResenha(txtResenha.getText().trim());
                    fa.setEstrelas((Integer) cmbEstrelas.getSelectedItem());
                    fa.setDataAvaliacao(new Date());
                   
                    Filme f = new Filme();
                    f.setID_Filme(Integer.parseInt(txtIdFilme.getText().trim()));
                    fa.setID_Filme(f);
                   
                    Perfil p = new Perfil();
                    p.setID_Perfil(Integer.parseInt(txtIdPerfil.getText().trim()));
                    fa.setID_Perfil(p);
                   
                    String resultado = controle.inserir(fa);
                    JOptionPane.showMessageDialog(this, resultado);
                   
                    if (resultado.contains("sucesso")) {
                        carregarDados();
                        limpar();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "IDs devem ser números inteiros!", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao inserir: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
       
        // ATUALIZAR - USA O ID SELECIONADO NA TABELA
        btnAtualizar.addActionListener(e -> {
            if (idSelecionado == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma avaliação na tabela primeiro!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
           
            if (validarCampos()) {
                try {
                    int confirm = JOptionPane.showConfirmDialog(this, 
                        "Confirma a atualização da avaliação ID " + idSelecionado + "?", 
                        "Confirmar Atualização", 
                        JOptionPane.YES_NO_OPTION);
                   
                    if (confirm == JOptionPane.YES_OPTION) {
                        Filme_avaliados fa = new Filme_avaliados();
                        fa.setID_Avaliacao(idSelecionado);
                        fa.setResenha(txtResenha.getText().trim());
                        fa.setEstrelas((Integer) cmbEstrelas.getSelectedItem());
                        fa.setDataAvaliacao(new Date());
                       
                        Filme f = new Filme();
                        f.setID_Filme(Integer.parseInt(txtIdFilme.getText().trim()));
                        fa.setID_Filme(f);
                       
                        Perfil p = new Perfil();
                        p.setID_Perfil(Integer.parseInt(txtIdPerfil.getText().trim()));
                        fa.setID_Perfil(p);
                       
                        String resultado = controle.atualizar(fa);
                        JOptionPane.showMessageDialog(this, resultado);
                       
                        if (resultado.contains("sucesso")) {
                            carregarDados();
                            limpar();
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "IDs devem ser números inteiros!", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
       
        // EXCLUIR - USA O ID SELECIONADO NA TABELA
        btnExcluir.addActionListener(e -> {
            if (idSelecionado == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma avaliação na tabela primeiro!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
           
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Confirma a exclusão da avaliação ID " + idSelecionado + "?", 
                "Excluir Avaliação", 
                JOptionPane.YES_NO_OPTION);
           
            if (confirm == JOptionPane.YES_OPTION) {
                String resultado = controle.excluir(idSelecionado);
                JOptionPane.showMessageDialog(this, resultado);
               
                if (resultado.contains("sucesso")) {
                    carregarDados();
                    limpar();
                }
            }
        });
       
        btnLimpar.addActionListener(e -> limpar());
    }
   
    // VALIDA CAMPOS DO FORMULÁRIO
    private boolean validarCampos() {
        if (txtResenha.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a resenha!", "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
            txtResenha.requestFocus();
            return false;
        }
        if (txtIdFilme.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o ID do Filme!", "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
            txtIdFilme.requestFocus();
            return false;
        }
        if (txtIdPerfil.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o ID do Perfil!", "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
            txtIdPerfil.requestFocus();
            return false;
        }
       
        try {
            int idFilme = Integer.parseInt(txtIdFilme.getText().trim());
            int idPerfil = Integer.parseInt(txtIdPerfil.getText().trim());
           
            if (idFilme <= 0) {
                JOptionPane.showMessageDialog(this, "ID do Filme deve ser maior que 0!", "Valor inválido", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (idPerfil <= 0) {
                JOptionPane.showMessageDialog(this, "ID do Perfil deve ser maior que 0!", "Valor inválido", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "IDs devem ser números inteiros válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
       
        return true;
    }
   
    private void carregarDados() {
        listaCompleta = controle.listar();
        carregarTabela(listaCompleta);
    }
   
    private void carregarTabela(ArrayList<Filme_avaliados> lista) {
        modeloTabela.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
       
        for (Filme_avaliados fa : lista) {
            String nomeFilme = fa.getID_Filme() != null && fa.getID_Filme().getNome_filme() != null ?
                              fa.getID_Filme().getNome_filme() : "ID: " + fa.getID_Filme().getID_Filme();
            String nomePerfil = fa.getID_Perfil() != null && fa.getID_Perfil().getNome_perfil() != null ?
                               fa.getID_Perfil().getNome_perfil() : "ID: " + fa.getID_Perfil().getID_Perfil();
           
            modeloTabela.addRow(new Object[]{
                fa.getID_Avaliacao(),
                nomeFilme,
                nomePerfil,
                fa.getEstrelas(),
                fa.getResenha(),
                fa.getDataAvaliacao() != null ? sdf.format(fa.getDataAvaliacao()) : ""
            });
        }
    }
   
    private void buscarPorNome() {
        String termo = txtBusca.getText().toLowerCase().trim();
        if (termo.isEmpty()) {
            carregarTabela(listaCompleta);
            return;
        }
       
        ArrayList<Filme_avaliados> filtrados = new ArrayList<>();
        for (Filme_avaliados fa : listaCompleta) {
            String nomeFilme = fa.getID_Filme() != null && fa.getID_Filme().getNome_filme() != null ?
                              fa.getID_Filme().getNome_filme().toLowerCase() : "";
            String nomePerfil = fa.getID_Perfil() != null && fa.getID_Perfil().getNome_perfil() != null ?
                               fa.getID_Perfil().getNome_perfil().toLowerCase() : "";
           
            if (nomeFilme.contains(termo) || nomePerfil.contains(termo)) {
                filtrados.add(fa);
            }
        }
       
        carregarTabela(filtrados);
    }
   
    private void filtrarPorEstrelas() {
        int index = cmbFiltroEstrelas.getSelectedIndex();
        if (index == 0) { // Todas
            carregarTabela(listaCompleta);
            return;
        }
       
        ArrayList<Filme_avaliados> filtrados = new ArrayList<>();
        for (Filme_avaliados fa : listaCompleta) {
            if (fa.getEstrelas() == index) {
                filtrados.add(fa);
            }
        }
       
        carregarTabela(filtrados);
    }
   
    private void limpar() {
        txtResenha.setText("");
        cmbEstrelas.setSelectedIndex(0);
        txtIdFilme.setText("");
        txtIdPerfil.setText("");
        tabela.clearSelection();
        idSelecionado = -1;
    }
   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Filme_avaliadosVisao().setVisible(true);
        });
    }
}