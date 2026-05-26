package controle;

import java.util.ArrayList;
import modelo.Filme_avaliados;
import modelo.Filme;
import modelo.Perfil;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Filme_avaliadosControle {

    private Connection conectar() throws SQLException {
        return new ConexaoMySQL().conectar();
    }

    private void fecharConexao(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Buscar filme básico
    private Filme buscarFilmeBasico(int idFilme) {
        Filme filme = new Filme();
        Connection conn = null;
        try {
            conn = conectar();
            String sql = "SELECT * FROM Filme WHERE ID_Filme = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, idFilme);
            ResultSet rs = stm.executeQuery();
           
            if (rs.next()) {
                filme.setID_Filme(rs.getInt("ID_Filme"));
                try { filme.setNome_filme(rs.getString("nome_filme")); } catch (SQLException e) {}
                try { filme.setClassificacao(rs.getInt("classificacao")); } catch (SQLException e) {}
                try { filme.setNacionalidade(rs.getString("nacionalidade")); } catch (SQLException e) {}
                try { filme.setLancamento(rs.getDate("lancamento")); } catch (SQLException e) {}
                try { filme.setOrcamento(rs.getInt("orcamento")); } catch (SQLException e) {}
                try { filme.setDiretor_principal(rs.getString("diretor_principal")); } catch (SQLException e) {}
                try { filme.setFoto_filme(rs.getString("foto_filme")); } catch (SQLException e) {}
                try { filme.setTrailer(rs.getString("trailer")); } catch (SQLException e) {}
                try { filme.setDescricao(rs.getString("descricao")); } catch (SQLException e) {}
                try { filme.setFoto_filme_folder(rs.getString("foto_filme_folder")); } catch (SQLException e) {}
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar filme " + idFilme + ": " + ex.getMessage());
        } finally {
            fecharConexao(conn);
        }
        return filme;
    }

    // Buscar perfil básico
    private Perfil buscarPerfilBasico(int idPerfil) {
        Perfil perfil = new Perfil();
        Connection conn = null;
        try {
            conn = conectar();
            String sql = "SELECT ID_Perfil, nome_perfil, classificacao_perfil, ID_usuario, foto_perfil FROM Perfil WHERE ID_Perfil = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, idPerfil);
            ResultSet rs = stm.executeQuery();
           
            if (rs.next()) {
                perfil.setID_Perfil(rs.getInt("ID_Perfil"));
                perfil.setNome_perfil(rs.getString("nome_perfil"));
                perfil.setClassificacao_perfil(rs.getInt("classificacao_perfil"));
                perfil.setFoto_perfil(rs.getString("foto_perfil"));
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao buscar perfil " + idPerfil + ": " + ex.getMessage());
        } finally {
            fecharConexao(conn);
        }
        return perfil;
    }

    private Filme_avaliados extrairAvaliacao(ResultSet resultado) throws SQLException {
        Filme_avaliados fa = new Filme_avaliados();
        fa.setID_Avaliacao(resultado.getInt("ID_Avaliacao"));
        fa.setResenha(resultado.getString("resenha"));
        fa.setEstrelas(resultado.getInt("estrelas"));
        fa.setDataAvaliacao(resultado.getDate("data_avaliacao"));
       
        int idFilme = resultado.getInt("ID_Filme");
        int idPerfil = resultado.getInt("ID_Perfil");
       
        fa.setID_Filme(buscarFilmeBasico(idFilme));
        fa.setID_Perfil(buscarPerfilBasico(idPerfil));
       
        return fa;
    }

    // ========== CRUD BÁSICO ==========
   
    public ArrayList<Filme_avaliados> listar() {
        ArrayList<Filme_avaliados> lista = new ArrayList<>();
        Connection conn = null;
        try {
            conn = conectar();
            PreparedStatement stm = conn.prepareStatement(
                "SELECT ID_Avaliacao, resenha, estrelas, data_avaliacao, ID_Filme, ID_Perfil " +
                "FROM Filme_avaliados ORDER BY data_avaliacao DESC"
            );
            ResultSet resultado = stm.executeQuery();
            while (resultado.next()) {
                lista.add(extrairAvaliacao(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao(conn);
        }
        return lista;
    }

    public Filme_avaliados buscarPorId(int id) {
        Filme_avaliados fa = null;
        Connection conn = null;
        try {
            conn = conectar();
            PreparedStatement stm = conn.prepareStatement(
                "SELECT ID_Avaliacao, resenha, estrelas, data_avaliacao, ID_Filme, ID_Perfil " +
                "FROM Filme_avaliados WHERE ID_Avaliacao = ?"
            );
            stm.setInt(1, id);
            ResultSet resultado = stm.executeQuery();
            if (resultado.next()) {
                fa = extrairAvaliacao(resultado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao(conn);
        }
        return fa;
    }

    // ✅ INSERIR CORRIGIDO - TRATA DATA NULA
    public String inserir(Filme_avaliados fa) {
        String resultado = "";
        Connection conn = null;
        try {
            conn = conectar();
            String sql = "INSERT INTO Filme_avaliados (resenha, estrelas, data_avaliacao, ID_Filme, ID_Perfil) VALUES (?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, fa.getResenha());
            stm.setInt(2, fa.getEstrelas());
            
            // SOLUÇÃO SIMPLES: SE DATA FOR NULA, USA DATA ATUAL
            if (fa.getDataAvaliacao() != null) {
                stm.setDate(3, new java.sql.Date(fa.getDataAvaliacao().getTime()));
            } else {
                stm.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            }
            
            stm.setInt(4, fa.getID_Filme().getID_Filme());
            stm.setInt(5, fa.getID_Perfil().getID_Perfil());
            stm.executeUpdate();
            resultado = "Inserido com sucesso!";
        } catch (SQLException ex) {
            resultado = "Erro: " + ex.getMessage();
        } finally {
            fecharConexao(conn);
        }
        return resultado;
    }

    // ✅ ATUALIZAR CORRIGIDO - TRATA DATA NULA
    public String atualizar(Filme_avaliados fa) {
        String resultado = "";
        Connection conn = null;
        try {
            conn = conectar();
            String sql = "UPDATE Filme_avaliados SET resenha=?, estrelas=?, data_avaliacao=?, ID_Filme=?, ID_Perfil=? WHERE ID_Avaliacao=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, fa.getResenha());
            stm.setInt(2, fa.getEstrelas());
            
            // SOLUÇÃO SIMPLES: SE DATA FOR NULA, USA DATA ATUAL
            if (fa.getDataAvaliacao() != null) {
                stm.setDate(3, new java.sql.Date(fa.getDataAvaliacao().getTime()));
            } else {
                stm.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            }
            
            stm.setInt(4, fa.getID_Filme().getID_Filme());
            stm.setInt(5, fa.getID_Perfil().getID_Perfil());
            stm.setInt(6, fa.getID_Avaliacao());
            stm.executeUpdate();
            resultado = "Atualizado com sucesso!";
        } catch (SQLException ex) {
            resultado = "Erro: " + ex.getMessage();
        } finally {
            fecharConexao(conn);
        }
        return resultado;
    }

    public String excluir(int id) {
        String resultado = "";
        Connection conn = null;
        try {
            conn = conectar();
            PreparedStatement stm = conn.prepareStatement("DELETE FROM Filme_avaliados WHERE ID_Avaliacao = ?");
            stm.setInt(1, id);
            stm.executeUpdate();
            resultado = "Excluído com sucesso!";
        } catch (SQLException ex) {
            resultado = "Erro: " + ex.getMessage();
        } finally {
            fecharConexao(conn);
        }
        return resultado;
    }

    // ========== MÉTODOS ESPECÍFICOS ==========
   
    public int contarPorPerfil(int idPerfil) {
        int quantidade = 0;
        Connection conn = null;
        try {
            conn = conectar();
            PreparedStatement stm = conn.prepareStatement("SELECT COUNT(*) as total FROM Filme_avaliados WHERE ID_Perfil = ?");
            stm.setInt(1, idPerfil);
            ResultSet resultado = stm.executeQuery();
            if (resultado.next()) quantidade = resultado.getInt("total");
        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao(conn);
        }
        return quantidade;
    }

    public double mediaEstrelasPorFilme(int idFilme) {
        double media = 0;
        Connection conn = null;
        try {
            conn = conectar();
            PreparedStatement stm = conn.prepareStatement("SELECT AVG(estrelas) as media FROM Filme_avaliados WHERE ID_Filme = ?");
            stm.setInt(1, idFilme);
            ResultSet resultado = stm.executeQuery();
            if (resultado.next()) media = resultado.getDouble("media");
        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao(conn);
        }
        return media;
    }

    public ArrayList<Filme_avaliados> listarPorPerfil(int idPerfil) {
        ArrayList<Filme_avaliados> lista = new ArrayList<>();
        Connection conn = null;
        try {
            conn = conectar();
            PreparedStatement stm = conn.prepareStatement(
                "SELECT ID_Avaliacao, resenha, estrelas, data_avaliacao, ID_Filme, ID_Perfil " +
                "FROM Filme_avaliados WHERE ID_Perfil = ? ORDER BY data_avaliacao DESC"
            );
            stm.setInt(1, idPerfil);
            ResultSet resultado = stm.executeQuery();
            while (resultado.next()) {
                lista.add(extrairAvaliacao(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao(conn);
        }
        return lista;
    }

    public ArrayList<Filme_avaliados> listarPorFilme(int idFilme) {
        ArrayList<Filme_avaliados> lista = new ArrayList<>();
        Connection conn = null;
        try {
            conn = conectar();
            PreparedStatement stm = conn.prepareStatement(
                "SELECT ID_Avaliacao, resenha, estrelas, data_avaliacao, ID_Filme, ID_Perfil " +
                "FROM Filme_avaliados WHERE ID_Filme = ? ORDER BY data_avaliacao DESC"
            );
            stm.setInt(1, idFilme);
            ResultSet resultado = stm.executeQuery();
            while (resultado.next()) {
                lista.add(extrairAvaliacao(resultado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao(conn);
        }
        return lista;
    }

    public int totalEstrelasPorFilme(int idFilme) {
        int total = 0;
        Connection conn = null;
        try {
            conn = conectar();
            PreparedStatement stm = conn.prepareStatement("SELECT SUM(estrelas) as total FROM Filme_avaliados WHERE ID_Filme = ?");
            stm.setInt(1, idFilme);
            ResultSet resultado = stm.executeQuery();
            if (resultado.next()) total = resultado.getInt("total");
        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao(conn);
        }
        return total;
    }

    public ArrayList<String> buscarResenhasPorFilme(int idFilme) {
        ArrayList<String> resenhas = new ArrayList<>();
        Connection conn = null;
        try {
            conn = conectar();
            PreparedStatement stm = conn.prepareStatement(
                "SELECT resenha FROM Filme_avaliados WHERE ID_Filme = ? AND resenha IS NOT NULL AND resenha != ''"
            );
            stm.setInt(1, idFilme);
            ResultSet resultado = stm.executeQuery();
            while (resultado.next()) {
                resenhas.add(resultado.getString("resenha"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Filme_avaliadosControle.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao(conn);
        }
        return resenhas;
    }

    public String excluirPorPerfil(int idPerfil) {
        String resultado = "";
        Connection conn = null;
        try {
            conn = conectar();
            PreparedStatement stm = conn.prepareStatement("DELETE FROM Filme_avaliados WHERE ID_Perfil = ?");
            stm.setInt(1, idPerfil);
            int linhas = stm.executeUpdate();
            resultado = linhas + " avaliacoes excluidas!";
        } catch (SQLException ex) {
            resultado = "Erro: " + ex.getMessage();
        } finally {
            fecharConexao(conn);
        }
        return resultado;
    }

    public String excluirPorFilme(int idFilme) {
        String resultado = "";
        Connection conn = null;
        try {
            conn = conectar();
            PreparedStatement stm = conn.prepareStatement("DELETE FROM Filme_avaliados WHERE ID_Filme = ?");
            stm.setInt(1, idFilme);
            int linhas = stm.executeUpdate();
            resultado = linhas + " avaliacoes excluidas!";
        } catch (SQLException ex) {
            resultado = "Erro: " + ex.getMessage();
        } finally {
            fecharConexao(conn);
        }
        return resultado;
    }
}