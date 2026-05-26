package controle;

import java.util.ArrayList;
import modelo.Filme_assistidos;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Filme_AssistidosControle {

    public Filme_AssistidosControle() {
    }

   public ArrayList<Filme_assistidos> consultarFilme_assistidos() {
    ArrayList<Filme_assistidos> vFilme_assistidos = new ArrayList();
    Connection conn = null;
    try {
        conn = new ConexaoMySQL().conectar();
        String consulta = "SELECT fa.ID_Assistidos, fa.data_assistido, "
                + "f.ID_Filme, f.nome_filme, "
                + "p.ID_Perfil, p.nome_perfil "
                + "FROM filme_assistidos fa "
                + "JOIN filme f ON fa.ID_Filme = f.ID_Filme "
                + "JOIN perfil p ON fa.ID_Perfil = p.ID_Perfil";
        Statement stm = conn.createStatement();
        ResultSet resultado = stm.executeQuery(consulta);
        while (resultado.next()) {
            Filme_assistidos fa = new Filme_assistidos();
            fa.setID_Assistidos(resultado.getInt("ID_Assistidos"));
            fa.setData_assistido(resultado.getDate("data_assistido"));

            modelo.Filme f = new modelo.Filme();
            f.setID_Filme(resultado.getInt("ID_Filme"));
            f.setNome_filme(resultado.getString("nome_filme"));
            fa.setID_Filme(f);

            modelo.Perfil p = new modelo.Perfil();
            p.setID_Perfil(resultado.getInt("ID_Perfil"));
            p.setNome_perfil(resultado.getString("nome_perfil"));
            fa.setID_Perfil(p);

            vFilme_assistidos.add(fa);
        }
    } catch (SQLException ex) {
        Logger.getLogger(Filme_AssistidosControle.class.getName()).log(Level.SEVERE, null, ex);
    } 
    return vFilme_assistidos;
}
    public Filme_assistidos consultarID_Filme_assistidos(int id_assistidos) {
        Filme_assistidos fa = new Filme_assistidos();
        ConexaoMySQL conexao = new ConexaoMySQL();
        try (Connection conn = conexao.conectar();
             Statement stm = conn.createStatement();
             ResultSet resultado = stm.executeQuery("SELECT * FROM filme_assistidos WHERE ID_Assistidos = " + id_assistidos)) {

            while (resultado.next()) {
                fa.setID_Assistidos(resultado.getInt("ID_Assistidos"));
                fa.setData_assistido(resultado.getDate("data_assistido"));
                fa.setID_Filme(new FilmeControle().consultarFilmeCodigo(resultado.getInt("ID_Filme")));
                fa.setID_Perfil(new PerfilControle().consultarPerfilCodigo(resultado.getInt("ID_Perfil")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Filme_AssistidosControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fa;
    }

    public String inserirFilme_assistidos(Filme_assistidos fa) {
        String resultado = "";
        ConexaoMySQL conexao = new ConexaoMySQL();
        String consulta = "INSERT INTO filme_assistidos (data_assistido, ID_Filme, ID_Perfil) VALUES (?, ?, ?)";
        try (Connection conn = conexao.conectar();
             PreparedStatement stm = conn.prepareStatement(consulta)) {

            stm.setDate(1, new java.sql.Date(fa.getData_assistido().getTime()));
            stm.setInt(2, fa.getID_Filme().getID_Filme());
            stm.setInt(3, fa.getID_Perfil().getID_Perfil());
            stm.executeUpdate();
            resultado = "inserido";

        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }

    public String alterarFilme_assistidos(Filme_assistidos fa) {
        String resultado = "";
        ConexaoMySQL conexao = new ConexaoMySQL();
        String consulta = "UPDATE filme_assistidos SET data_assistido = ?, ID_Filme = ?, ID_Perfil = ? WHERE ID_Assistidos = ?";
        try (Connection conn = conexao.conectar();
             PreparedStatement stm = conn.prepareStatement(consulta)) {

            stm.setDate(1, new java.sql.Date(fa.getData_assistido().getTime()));
            stm.setInt(2, fa.getID_Filme().getID_Filme());
            stm.setInt(3, fa.getID_Perfil().getID_Perfil());
            stm.setInt(4, fa.getID_Assistidos());
            stm.executeUpdate();
            resultado = "alterado";

        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }

    public String deletarID_Filme_assistidos(int id_assistidos) {
        String resultado = "";
        ConexaoMySQL conexao = new ConexaoMySQL();
        String consulta = "DELETE FROM Filme_assistidos WHERE ID_Assistidos = ?";
        try (Connection conn = conexao.conectar();
             PreparedStatement stm = conn.prepareStatement(consulta)) {

            stm.setInt(1, id_assistidos);
            stm.executeUpdate();
            resultado = "removido";

        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }

    public int contarEspectadoresPorFilme(int id_filme) {
        int total = 0;
        ConexaoMySQL conexao = new ConexaoMySQL();
        String consulta = "SELECT COUNT(DISTINCT ID_Perfil) AS total FROM Filme_Assistidos WHERE ID_Filme = ?";
        try (Connection conn = conexao.conectar();
             PreparedStatement stm = conn.prepareStatement(consulta)) {

            stm.setInt(1, id_filme);
            try (ResultSet resultado = stm.executeQuery()) {
                if (resultado.next()) {
                    total = resultado.getInt("total");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Filme_AssistidosControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public ArrayList<Filme_assistidos> consultarFilmesPorPerfil(int id_perfil) {
        ArrayList<Filme_assistidos> vFilme_assistidos = new ArrayList();
        ConexaoMySQL conexao = new ConexaoMySQL();
        String consulta = "SELECT * FROM Filme_assistidos WHERE ID_Perfil = ?";
        try (Connection conn = conexao.conectar();
             PreparedStatement stm = conn.prepareStatement(consulta)) {

            stm.setInt(1, id_perfil);
            try (ResultSet resultado = stm.executeQuery()) {
                while (resultado.next()) {
                    Filme_assistidos fa = new Filme_assistidos();
                    fa.setID_Assistidos(resultado.getInt("ID_Assistidos"));
                    fa.setData_assistido(resultado.getDate("data_assistido"));
                    fa.setID_Filme(new FilmeControle().consultarFilmeCodigo(resultado.getInt("ID_Filme")));
                    fa.setID_Perfil(new PerfilControle().consultarPerfilCodigo(resultado.getInt("ID_Perfil")));
                    vFilme_assistidos.add(fa);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Filme_AssistidosControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vFilme_assistidos;
    }

    public static void main(String[] args) {
        Filme_AssistidosControle fac = new Filme_AssistidosControle();

        for (Filme_assistidos fa : fac.consultarFilme_assistidos())
            System.out.println(fa.toString());

        fac.deletarID_Filme_assistidos(1);

        for (Filme_assistidos fa : fac.consultarFilme_assistidos())
            System.out.println(fa.toString());

        /*
        usar o consultar filme por perfil
        int ID_Perfil = 2;
        System.out.println("Filmes assistidos pelo perfil " + ID_Perfil + ":");
        for (Filme_assistidos fa : fac.consultarFilmesPorPerfil(ID_Perfil)) {
          System.out.println(fa.toString());
        }
        */
    }

}