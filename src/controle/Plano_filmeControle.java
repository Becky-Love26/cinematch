package controle;

import java.util.ArrayList;
import modelo.Plano_filme;
import modelo.Filme;
import modelo.Plano;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Plano_filmeControle {

    private Connection conectar() throws SQLException {
        return new ConexaoMySQL().conectar();
    }

    private void fecharConexao(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException ex) {
            // silencioso
        }
    }

    private Filme buscarFilmeBasico(int idFilme) {
        Filme filme = new Filme();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = conectar();
            stm = conn.prepareStatement("SELECT ID_Filme, nome_filme FROM Filme WHERE ID_Filme = ?");
            stm.setInt(1, idFilme);
            rs = stm.executeQuery();
            if (rs.next()) {
                filme.setID_Filme(rs.getInt("ID_Filme"));
                filme.setNome_filme(rs.getString("nome_filme"));
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar filme " + idFilme);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            fecharConexao(conn);
        }
        return filme;
    }

    private Plano buscarPlanoBasico(int idPlano) {
        Plano plano = new Plano();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = conectar();
            stm = conn.prepareStatement("SELECT ID_Plano, tipo_plano, valor FROM Plano WHERE ID_Plano = ?");
            stm.setInt(1, idPlano);
            rs = stm.executeQuery();
            if (rs.next()) {
                plano.setID_plano(rs.getInt("ID_Plano"));
                plano.setValor(rs.getFloat("valor"));
                plano.setTipo_plano(rs.getString("tipo_plano"));
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar plano " + idPlano);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            fecharConexao(conn);
        }
        return plano;
    }

    // ========== CRUD ==========
   
    public ArrayList<Plano_filme> listar() {
        ArrayList<Plano_filme> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = conectar();
            stm = conn.prepareStatement("SELECT * FROM Plano_filme");
            rs = stm.executeQuery();
            while (rs.next()) {
                Plano_filme pf = new Plano_filme();
                pf.setID_Plano_Filme(rs.getInt("ID_Plano_Filme"));
                pf.setID_Filme(buscarFilmeBasico(rs.getInt("ID_Filme")));
                pf.setID_Plano(buscarPlanoBasico(rs.getInt("ID_Plano")));
                lista.add(pf);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Plano_filmeControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            fecharConexao(conn);
        }
        return lista;
    }

    public Plano_filme buscarPorId(int id) {
        Plano_filme pf = new Plano_filme();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = conectar();
            stm = conn.prepareStatement("SELECT * FROM Plano_filme WHERE ID_Plano_Filme = ?");
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                pf.setID_Plano_Filme(rs.getInt("ID_Plano_Filme"));
                pf.setID_Filme(buscarFilmeBasico(rs.getInt("ID_Filme")));
                pf.setID_Plano(buscarPlanoBasico(rs.getInt("ID_Plano")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Plano_filmeControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            fecharConexao(conn);
        }
        return pf;
    }

    public String inserir(Plano_filme pf) {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = conectar();
            stm = conn.prepareStatement("INSERT INTO Plano_filme (ID_Filme, ID_Plano) VALUES (?,?)");
            stm.setInt(1, pf.getID_Filme().getID_Filme());
            stm.setInt(2, pf.getID_Plano().getID_plano());
            stm.executeUpdate();
            return "Inserido com sucesso!";
        } catch (SQLException ex) {
            return "Erro: " + ex.getMessage();
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            fecharConexao(conn);
        }
    }

    public String atualizar(Plano_filme pf) {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = conectar();
            stm = conn.prepareStatement("UPDATE Plano_filme SET ID_Filme=?, ID_Plano=? WHERE ID_Plano_Filme=?");
            stm.setInt(1, pf.getID_Filme().getID_Filme());
            stm.setInt(2, pf.getID_Plano().getID_plano());
            stm.setInt(3, pf.getID_Plano_Filme());
            stm.executeUpdate();
            return "Atualizado com sucesso!";
        } catch (SQLException ex) {
            return "Erro: " + ex.getMessage();
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            fecharConexao(conn);
        }
    }

    public String excluir(int id) {
        Connection conn = null;
        PreparedStatement stm = null;
        try {
            conn = conectar();
            stm = conn.prepareStatement("DELETE FROM Plano_filme WHERE ID_Plano_Filme=?");
            stm.setInt(1, id);
            stm.executeUpdate();
            return "Excluído com sucesso!";
        } catch (SQLException ex) {
            return "Erro: " + ex.getMessage();
        } finally {
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            fecharConexao(conn);
        }
    }

    // ========== CONSULTAS ESPECÍFICAS ==========
   
    public ArrayList<Plano> listarPlanosPorFilme(int idFilme) {
        ArrayList<Plano> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = conectar();
            stm = conn.prepareStatement("SELECT ID_Plano FROM Plano_filme WHERE ID_Filme=?");
            stm.setInt(1, idFilme);
            rs = stm.executeQuery();
            while (rs.next()) {
                lista.add(buscarPlanoBasico(rs.getInt("ID_Plano")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Plano_filmeControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            fecharConexao(conn);
        }
        return lista;
    }

    public ArrayList<Filme> listarFilmesPorPlano(int idPlano) {
        ArrayList<Filme> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = conectar();
            stm = conn.prepareStatement("SELECT ID_Filme FROM Plano_filme WHERE ID_Plano=?");
            stm.setInt(1, idPlano);
            rs = stm.executeQuery();
            while (rs.next()) {
                lista.add(buscarFilmeBasico(rs.getInt("ID_Filme")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Plano_filmeControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            fecharConexao(conn);
        }
        return lista;
    }

    /**
     * Retorna um resumo: cada filme com seus planos em uma linha
     */
    public ArrayList<String> resumoFilmesComPlanos() {
        ArrayList<String> resumo = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = conectar();
            String sql = "SELECT f.ID_Filme, f.nome_filme, " +
                         "GROUP_CONCAT(p.tipo_plano SEPARATOR ', ') as planos " +
                         "FROM Filme f " +
                         "INNER JOIN Plano_filme pf ON f.ID_Filme = pf.ID_Filme " +
                         "INNER JOIN Plano p ON pf.ID_Plano = p.ID_Plano " +
                         "GROUP BY f.ID_Filme, f.nome_filme " +
                         "ORDER BY f.nome_filme";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                resumo.add(rs.getString("nome_filme") + " → " + rs.getString("planos"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Plano_filmeControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            fecharConexao(conn);
        }
        return resumo;
    }

    /**
     * Retorna resumo: cada perfil com seu plano (via Usuario)
     */
    public ArrayList<String> resumoPerfisComPlanos() {
        ArrayList<String> resumo = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = conectar();
            String sql = "SELECT pe.nome_perfil, u.nome, p.tipo_plano " +
                         "FROM Perfil pe " +
                         "INNER JOIN Usuario u ON pe.ID_usuario = u.ID_usuario " +
                         "LEFT JOIN Plano p ON u.ID_Plano = p.ID_Plano " +
                         "ORDER BY pe.nome_perfil";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String plano = rs.getString("tipo_plano") != null ?
                              rs.getString("tipo_plano") : "Sem plano";
                resumo.add(rs.getString("nome_perfil") + " (" + rs.getString("nome") + ") → " + plano);
            }
        } catch (SQLException ex) {
            System.err.println("Erro: verifique se a tabela Usuario tem coluna ID_Plano");
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stm != null) stm.close(); } catch (SQLException e) {}
            fecharConexao(conn);
        }
        return resumo;
    }


   
    public static void main(String[] args) {
        Plano_filmeControle pfc = new Plano_filmeControle();
       
        System.out.println("═══════════════════════════════════");
        System.out.println("   CINEMATCH - PLANOS E FILMES");
        System.out.println("═══════════════════════════════════\n");
       
        // PLANOS DISPONÍVEIS
        System.out.println(" PLANOS: Básico (R$19,90) | Premium (R$49,90) | Família (R$119,90)\n");
       
        // FILMES COM SEUS PLANOS
        System.out.println(" FILMES E SEUS PLANOS:");
        ArrayList<String> filmesPlanos = pfc.resumoFilmesComPlanos();
        for (String linha : filmesPlanos) {
            System.out.println("   " + linha);
        }
       
        // PERFIS COM SEUS PLANOS
        System.out.println("\n PERFIS E SEUS PLANOS:");
        ArrayList<String> perfisPlanos = pfc.resumoPerfisComPlanos();
        if (perfisPlanos.isEmpty()) {
            System.out.println("   (Tabela Usuario sem ID_Plano)");
        } else {
            for (String linha : perfisPlanos) {
                System.out.println("   " + linha);
            }
        }
       
       
        System.out.println("\n ESTATÍSTICAS:");
        System.out.println("   Total de associações: " + pfc.listar().size());
        System.out.println("   Filmes no Básico: " + pfc.listarFilmesPorPlano(3).size());
        System.out.println("   Filmes no Premium: " + pfc.listarFilmesPorPlano(1).size());
        System.out.println("   Filmes no Família: " + pfc.listarFilmesPorPlano(2).size());
       
        System.out.println("\n═══════════════════════════════════");
    }
}