package controle;

import java.util.ArrayList;
import modelo.Tipo_usuario;
import conexao.ConexaoMySQL;
import java.sql.*;

public class Tipo_usuarioControle {

    // CONSULTAR TODOS
    public ArrayList<Tipo_usuario> consultarTipos() {
        ArrayList<Tipo_usuario> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
       
        try {
            conn = new ConexaoMySQL().conectar();
            String sql = "SELECT * FROM tipo_usuario ORDER BY ID_TipoUsuario";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
           
            while (rs.next()) {
                Tipo_usuario tipo = new Tipo_usuario();
                tipo.setID_TipoUsuario(rs.getInt("ID_TipoUsuario"));
                String descricao = rs.getString("descricao_usuario");
               
                // Se for null, coloca um valor padrão
                if (descricao == null || descricao.isEmpty()) {
                    descricao = "Tipo " + rs.getInt("ID_TipoUsuario");
                }
               
                tipo.setDescricao_usuario(descricao);
                lista.add(tipo);
            }
           
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stm != null) stm.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return lista;
    }

    // INSERIR
    public String inserirTipo(Tipo_usuario tipo) {
        Connection conn = null;
        PreparedStatement stm = null;
       
        try {
            conn = new ConexaoMySQL().conectar();
            String sql = "INSERT INTO tipo_usuario (descricao_usuario) VALUES (?)";
            stm = conn.prepareStatement(sql);
            stm.setString(1, tipo.getDescricao_usuario());
            stm.executeUpdate();
            return "sucesso|Inserido com sucesso!";
           
        } catch (SQLException e) {
            return "erro|" + e.getMessage();
        } finally {
            try { if (stm != null) stm.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    // ALTERAR
    public String alterarTipo(Tipo_usuario tipo) {
        Connection conn = null;
        PreparedStatement stm = null;
       
        try {
            conn = new ConexaoMySQL().conectar();
            String sql = "UPDATE tipo_usuario SET descricao_usuario = ? WHERE ID_TipoUsuario = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, tipo.getDescricao_usuario());
            stm.setInt(2, tipo.getID_TipoUsuario());
            stm.executeUpdate();
            return "sucesso|Alterado com sucesso!";
           
        } catch (SQLException e) {
            return "erro|" + e.getMessage();
        } finally {
            try { if (stm != null) stm.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    // DELETAR
    public String deletarTipo(int id) {
        Connection conn = null;
        PreparedStatement stm = null;
       
        try {
            conn = new ConexaoMySQL().conectar();
            String sql = "DELETE FROM tipo_usuario WHERE ID_TipoUsuario = ?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            return "sucesso|Deletado com sucesso!";
           
        } catch (SQLException e) {
            return "erro|" + e.getMessage();
        } finally {
            try { if (stm != null) stm.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
   
    // MAIN PARA TESTAR
    public static void main(String[] args) {
        Tipo_usuarioControle controle = new Tipo_usuarioControle();
       
        System.out.println("=== TESTE DE CONSULTA ===");
        ArrayList<Tipo_usuario> lista = controle.consultarTipos();
       
        if (lista.isEmpty()) {
            System.out.println("Nenhum tipo encontrado!");
           
            // Insere um tipo de teste
            System.out.println("\nInserindo tipo de teste...");
            Tipo_usuario novoTipo = new Tipo_usuario();
            novoTipo.setDescricao_usuario("Administrador");
            String resultado = controle.inserirTipo(novoTipo);
            System.out.println("Resultado: " + resultado);
           
            // Consulta novamente
            System.out.println("\nConsultando novamente...");
            lista = controle.consultarTipos();
        }
       
        System.out.println("\n=== RESULTADOS ===");
        for (Tipo_usuario t : lista) {
            System.out.println("ID: " + t.getID_TipoUsuario() + " | Descrição: " + t.getDescricao_usuario());
        }
    }
}