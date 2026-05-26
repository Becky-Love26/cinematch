package controle;

import java.util.ArrayList;
import modelo.Filme;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilmeControle {

    private static final Logger logger = Logger.getLogger(FilmeControle.class.getName());

    public FilmeControle() {
    }
    public ArrayList<Filme> consultarFilmesPorNome(String nome_filme) {
    ArrayList<Filme> vFilme = new ArrayList<>();
    ConexaoMySQL conexao = new ConexaoMySQL();
    Connection conn = conexao.conectar();

    try {
        String consulta = "SELECT * FROM Filme WHERE nome_filme LIKE ?";
        PreparedStatement stm = conn.prepareStatement(consulta);
        stm.setString(1, "%" + nome_filme + "%");
        ResultSet resultado = stm.executeQuery();

        while (resultado.next()) {  // while, não if — pega todos!
            Filme fil = new Filme();
            fil.setID_Filme(resultado.getInt("ID_Filme"));
            fil.setNome_filme(resultado.getString("nome_filme"));
            fil.setClassificacao(resultado.getInt("classificacao"));
            fil.setNacionalidade(resultado.getString("nacionalidade"));
            fil.setLancamento(resultado.getDate("lancamento"));
            fil.setOrcamento(resultado.getInt("orcamento"));
            fil.setDiretor_principal(resultado.getString("diretor_principal"));
            fil.setFoto_filme(resultado.getString("foto_filme"));
            fil.setTrailer(resultado.getString("trailer"));
            fil.setFoto_filme_folder(resultado.getString("foto_filme_folder"));
            fil.setDescricao(resultado.getString("descricao"));
            vFilme.add(fil);
        }

    } catch (SQLException ex) {
        logger.log(Level.SEVERE, null, ex);
    } finally {
        fecharConexao(conn);
    }

    return vFilme;
}

    // ── CONSULTAR TODOS ──────────────────────────────────────────────────────
    public ArrayList<Filme> consultarFilmes() {
        ArrayList<Filme> vFilme = new ArrayList<>(); // corrigido: era new ArrayList() sem tipo
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();

        try {
            String consulta = "SELECT * FROM Filme";
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);

            while (resultado.next()) {
                Filme fil = new Filme();
                fil.setID_Filme(resultado.getInt("ID_Filme"));
                fil.setNome_filme(resultado.getString("nome_filme"));
                fil.setClassificacao(resultado.getInt("classificacao"));
                fil.setNacionalidade(resultado.getString("nacionalidade"));
                fil.setLancamento(resultado.getDate("lancamento"));
                fil.setOrcamento(resultado.getInt("orcamento"));
                fil.setDiretor_principal(resultado.getString("diretor_principal"));
                fil.setFoto_filme(resultado.getString("foto_filme"));
                fil.setTrailer(resultado.getString("trailer"));
                fil.setFoto_filme_folder(resultado.getString("foto_filme_folder"));
                fil.setDescricao(resultado.getString("descricao"));
                vFilme.add(fil);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao(conn); // corrigido: conexão agora é fechada
        }

        return vFilme;
    }

    // ── CONSULTAR POR ID ─────────────────────────────────────────────────────
    public Filme consultarFilmeCodigo(int ID_Filme) {
        Filme cli = new Filme();
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();

        try {
            String consulta = "SELECT * FROM Filme WHERE ID_Filme = ?";
            PreparedStatement stm = conn.prepareStatement(consulta); // corrigido: usando PreparedStatement
            stm.setInt(1, ID_Filme);
            ResultSet resultado = stm.executeQuery();

            if (resultado.next()) {
                cli.setID_Filme(resultado.getInt("ID_Filme"));
                cli.setNome_filme(resultado.getString("nome_filme"));
                cli.setClassificacao(resultado.getInt("classificacao"));
                cli.setNacionalidade(resultado.getString("nacionalidade"));
                cli.setLancamento(resultado.getDate("lancamento"));
                cli.setOrcamento(resultado.getInt("orcamento"));
                cli.setDiretor_principal(resultado.getString("diretor_principal"));
                cli.setFoto_filme(resultado.getString("foto_filme"));
                cli.setTrailer(resultado.getString("trailer"));
                cli.setFoto_filme_folder(resultado.getString("foto_filme_folder"));
                cli.setDescricao(resultado.getString("descricao"));
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao(conn);
        }

        return cli;
    }

    // ── CONSULTAR POR NOME ───────────────────────────────────────────────────
    public Filme consultarFilmeNome(String nome_filme) {
        Filme cli = new Filme();
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();

        try {
            String consulta = "SELECT * FROM Filme WHERE nome_filme LIKE ?"; // corrigido: usando PreparedStatement
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, "%" + nome_filme + "%");
            ResultSet resultado = stm.executeQuery();

            if (resultado.next()) {
                cli.setID_Filme(resultado.getInt("ID_Filme"));
                cli.setNome_filme(resultado.getString("nome_filme"));
                cli.setClassificacao(resultado.getInt("classificacao"));
                cli.setNacionalidade(resultado.getString("nacionalidade"));
                cli.setLancamento(resultado.getDate("lancamento"));
                cli.setOrcamento(resultado.getInt("orcamento"));
                cli.setDiretor_principal(resultado.getString("diretor_principal"));
                cli.setFoto_filme(resultado.getString("foto_filme"));
                cli.setTrailer(resultado.getString("trailer"));
                cli.setFoto_filme_folder(resultado.getString("foto_filme_folder"));
                cli.setDescricao(resultado.getString("descricao"));
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao(conn);
        }

        return cli;
    }

    // ── INSERIR ──────────────────────────────────────────────────────────────
    public String inserirFilme(Filme fil) {
        String resultado = "";
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar(); // corrigido: movido para fora do try

        try {
            String consulta = "INSERT INTO Filme "
                    + "(nome_filme, classificacao, nacionalidade, lancamento, "
                    + "orcamento, diretor_principal, foto_filme, trailer, "
                    + "foto_filme_folder, descricao) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, fil.getNome_filme());
            stm.setInt(2, fil.getClassificacao());
            stm.setString(3, fil.getNacionalidade());
            stm.setDate(4, new java.sql.Date(fil.getLancamento().getTime()));
            stm.setInt(5, fil.getOrcamento());
            stm.setString(6, fil.getDiretor_principal());
            stm.setString(7, fil.getFoto_filme());
            stm.setString(8, fil.getTrailer());
            stm.setString(9, fil.getFoto_filme_folder());
            stm.setString(10, fil.getDescricao());
            stm.executeUpdate();
            resultado = "inserido";

        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        } finally {
            fecharConexao(conn); // corrigido: conexão agora é fechada
        }

        return resultado;
    }

    // ── ALTERAR ──────────────────────────────────────────────────────────────
    public String alterarFilme(Filme fil) {
        String resultado = "";
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar(); // corrigido: movido para fora do try

        try {
            String consulta = "UPDATE Filme SET "
                    + "nome_filme = ?, classificacao = ?, nacionalidade = ?, lancamento = ?, "
                    + "orcamento = ?, diretor_principal = ?, foto_filme = ?, trailer = ?, "
                    + "foto_filme_folder = ?, descricao = ? "
                    + "WHERE ID_Filme = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, fil.getNome_filme());
            stm.setInt(2, fil.getClassificacao());
            stm.setString(3, fil.getNacionalidade());
            stm.setDate(4, new java.sql.Date(fil.getLancamento().getTime()));
            stm.setInt(5, fil.getOrcamento());
            stm.setString(6, fil.getDiretor_principal());
            stm.setString(7, fil.getFoto_filme());
            stm.setString(8, fil.getTrailer());
            stm.setString(9, fil.getFoto_filme_folder());
            stm.setString(10, fil.getDescricao());
            stm.setInt(11, fil.getID_Filme());
            stm.executeUpdate();
            resultado = "alterado";

        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println("Erro ao alterar: " + ex.getMessage());
        } finally {
            fecharConexao(conn); // corrigido: conexão agora é fechada
        }

        return resultado;
    }

    // ── DELETAR ──────────────────────────────────────────────────────────────
    public String deletarFilme(int ID_Filme) {
        String resultado = "";
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar(); // corrigido: movido para fora do try

        try {
            String consulta = "DELETE FROM Filme WHERE ID_Filme = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, ID_Filme);
            stm.executeUpdate();
            resultado = "deletado";

        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        } finally {
            fecharConexao(conn); // corrigido: conexão agora é fechada
        }

        return resultado;
    }

    // ── MÉTODO AUXILIAR: fechar conexão ──────────────────────────────────────
    private void fecharConexao(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexao: " + e.getMessage());
        }
    }

    // ── MAIN (para testes) ───────────────────────────────────────────────────
    public static void main(String[] args) {
        FilmeControle fc = new FilmeControle();

        // Descomente o bloco que quiser testar:

        // LISTAR TODOS
        /*
        System.out.println("=== LISTA DE FILMES ===");
        for (Filme fil : fc.consultarFilmes()) {
            System.out.println(fil.toString());
        }
        */

        // CONSULTAR POR NOME
        /*
        Filme encontrado = fc.consultarFilmeNome("Era");
        System.out.println(encontrado.toString());
        */

        // INSERIR
        /*
        Filme novo = new Filme();
        novo.setNome_filme("Interestelar");
        novo.setClassificacao(12);
        novo.setNacionalidade("EUA");
        novo.setLancamento(new java.util.Date());
        novo.setOrcamento(165000000);
        novo.setDiretor_principal("Christopher Nolan");
        novo.setFoto_filme("interestelar.jpg");
        novo.setTrailer("youtube.com/trailer");
        novo.setFoto_filme_folder("folder_inter.jpg");
        novo.setDescricao("Um filme sobre exploração espacial.");
        System.out.println("Inserir: " + fc.inserirFilme(novo));
        */

        // ALTERAR
        /*
        Filme filAlterar = fc.consultarFilmeCodigo(152);
        if (filAlterar.getNome_filme() != null) {
            filAlterar.setNome_filme("Nome Alterado");
            System.out.println("Alterar: " + fc.alterarFilme(filAlterar));
        }
        */

        // DELETAR
        /*
        System.out.println("Deletar: " + fc.deletarFilme(153));
        */
    }
}