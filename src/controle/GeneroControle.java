package controle;

import java.util.ArrayList;
import modelo.Genero;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneroControle{

    public GeneroControle() {
    }

    public ArrayList<Genero> consultarGenero() {
        ArrayList<Genero> vGenero = new ArrayList();
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "SELECT * FROM Genero";
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);
           
            while (resultado.next()) {
                Genero ge = new Genero();
                ge.setID_Genero(resultado.getInt("ID_Genero"));
                ge.setNome_genero(resultado.getString("nome_genero"));
                ge.setDescricao(resultado.getString("descricao"));
                vGenero.add(ge);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(GeneroControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vGenero;
    }

    public Genero consultarID_Genero(int ID_Genero) {
         Genero ge = new Genero();
         ConexaoMySQL conexao = new ConexaoMySQL();
           Connection conn = conexao.conectar();
        try {
            
            String consulta = "SELECT * FROM Genero WHERE ID_Genero = " + ID_Genero;
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);
           
            while (resultado.next()) {
                ge.setID_Genero(resultado.getInt("ID_Genero"));
                ge.setNome_genero(resultado.getString("nome_genero"));
                ge.setDescricao(resultado.getString("descricao"));
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(ElencoControle.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            // ESSA PARTE É VITAL: Garante que a conexão feche sempre
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar: " + e.getMessage());
            }
        }
        return ge;
    }

    public String inserirGenero(Genero ge) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "INSERT INTO Genero "
                    + "(nome_genero, descricao) VALUES "
                    + "(?,?)";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, ge.getNome_genero());
            stm.setString(2, ge.getDescricao());
            stm.executeUpdate();
            resultado = "inserido";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }

   

    public String deletarGenero(int ID_Genero) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "DELETE FROM Genero WHERE ID_Genero = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, ID_Genero);
            stm.executeUpdate();
            resultado = "removido";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }
    
    public String alterarGenero(Genero ge) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "UPDATE Genero SET "
                    + "nome_genero = ?, descricao = ? WHERE ID_Genero = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, ge.getNome_genero());
            stm.setString(2, ge.getDescricao());
            stm.setInt(3, ge.getID_Genero()); // Faltava esta linha para identificar qual registro alterar
            stm.executeUpdate();
            resultado = "alterado";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }
   
    
    public static void main(String[] args) {
        GeneroControle gc = new GeneroControle();

        // 1. Consultando todos (READ)
        
        System.out.println("=== LISTA DE GÊNEROS ===");
        for (Genero ge : gc.consultarGenero()) {
            System.out.println(ge.toString());
        }
        
        // 2. Inserindo um objeto (CREATE)
        /*
        System.out.println("\n=== INSERINDO GÊNERO ===");
        Genero novoGenero = new Genero();
        novoGenero.setNome_genero("Ficção Científica");
        novoGenero.setDescricao("Filmes com tecnologia avançada e exploração espacial.");

        String resultadoInsert = gc.inserirGenero(novoGenero);
        System.out.println("Resultado Inserir: " + resultadoInsert);
        */

        // 3. Consultando por ID (READ BY ID)
        /*
        System.out.println("\n=== CONSULTA POR ID ===");
        Genero generoCons = gc.consultarID_Genero(1); // Altere o ID conforme necessário
        System.out.println(generoCons.toString());
        */

        // 4. Alterando um objeto (UPDATE)
        /*
        System.out.println("\n=== ALTERANDO GÊNERO ===");
        Genero edit = gc.consultarID_Genero(11);
        if(edit.getNome_genero() != null) {
            edit.setNome_genero(" ATUALIZADO");
            edit.setDescricao("Nova descrição atualizada.");

            String resultadoUpdate = gc.alterarGenero(edit);
            System.out.println("Resultado Alterar: " + resultadoUpdate);
        } else {
            System.out.println("Gênero não encontrado para alteração.");
        }
        */

        // 5. Deletando um objeto (DELETE)
        /*
        System.out.println("\n=== DELETANDO GÊNERO ===");
        String resultadoDelete = gc.deletarGenero(11); // Altere o ID conforme necessário
        System.out.println("Resultado Deletar: " + resultadoDelete);
        */
        
        // 6. Listagem final para conferência
        /*
        System.out.println("\n=== LISTAGEM FINAL ===");
        for (Genero ge : gc.consultarGenero()) {
            System.out.println(ge.toString());
        }
        */
    }
}