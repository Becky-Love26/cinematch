package controle;

import java.util.ArrayList;
import modelo.Usuario;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioControle {

    private static final Logger logger = Logger.getLogger(UsuarioControle.class.getName());

    public UsuarioControle() {
    }

    // ── CONSULTAR TODOS ──────────────────────────────────────────────────────
    public ArrayList<Usuario> consultarUsuario() {
        ArrayList<Usuario> vUsuario = new ArrayList<>();
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();

        try {
            String consulta = "SELECT * FROM usuario";
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);
            while (resultado.next()) {
                Usuario usu = new Usuario();
                usu.setCPF(resultado.getString("CPF"));
                usu.setData_nascimento(resultado.getDate("data_nascimento"));
                usu.setEmail(resultado.getString("email"));
                usu.setID_usuario(resultado.getInt("ID_usuario"));
                usu.setNome(resultado.getString("nome"));
                usu.setSenha(resultado.getString("senha"));
                usu.setSexo(resultado.getString("sexo"));
                usu.setStatus_usuario(resultado.getInt("status_usuario"));
                usu.setTelefone(resultado.getString("telefone"));
                vUsuario.add(usu);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao(conn);
        }
        return vUsuario;
    }

    // ── CONSULTAR POR ID ─────────────────────────────────────────────────────
    public Usuario consultarUsuarioCodigo(int ID_usuario) {
        Usuario usu = new Usuario();
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();

        try {
            String consulta = "SELECT * FROM usuario WHERE ID_usuario = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, ID_usuario);
            ResultSet resultado = stm.executeQuery();
            if (resultado.next()) {
                usu.setCPF(resultado.getString("CPF"));
                usu.setData_nascimento(resultado.getDate("data_nascimento"));
                usu.setEmail(resultado.getString("email"));
                usu.setID_usuario(resultado.getInt("ID_usuario"));
                usu.setNome(resultado.getString("nome"));
                usu.setSenha(resultado.getString("senha"));
                usu.setSexo(resultado.getString("sexo"));
                usu.setStatus_usuario(resultado.getInt("status_usuario"));
                usu.setTelefone(resultado.getString("telefone"));
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao(conn);
        }
        return usu;
    }

    // ── VERIFICAR CPF DUPLICADO ──────────────────────────────────────────────
    public boolean cpfJaCadastrado(String CPF) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();

        try {
            String consulta = "SELECT COUNT(*) FROM usuario WHERE CPF = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, CPF);
            ResultSet resultado = stm.executeQuery();
            if (resultado.next()) {
                return resultado.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao(conn);
        }
        return false;
    }

    // ── INSERIR ──────────────────────────────────────────────────────────────
    public String inserirUsuario(Usuario usu) {
        if (cpfJaCadastrado(usu.getCPF())) {
            return "CPF já cadastrado para outro usuário.";
        }

        String resultado = "";
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();

        try {
            String consulta = "INSERT INTO usuario "
        + "(nome, CPF, sexo, email, data_nascimento, senha, status_usuario, telefone, ID_TipoUsuario) "
        + "VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, usu.getNome());
            stm.setString(2, usu.getCPF());
            stm.setString(3, usu.getSexo());
            stm.setString(4, usu.getEmail());
            if (usu.getData_nascimento() != null) {
                stm.setDate(5, new java.sql.Date(usu.getData_nascimento().getTime()));
            } else {
                stm.setDate(5, null);
            }
            stm.setString(6, usu.getSenha());
            stm.setInt(7, usu.getStatus_usuario());
            stm.setString(8, usu.getTelefone());
            stm.setInt(9, usu.getID_TipoUsuario().getID_TipoUsuario());
            stm.executeUpdate();
            resultado = "inserido";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        } finally {
            fecharConexao(conn);
        }
        return resultado;
    }

    // ── ALTERAR ──────────────────────────────────────────────────────────────
    public String alterarUsuario(Usuario usu) {
        String resultado = "";
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();

        try {
            // corrigido: espaços nas strings SQL
            String consulta = "UPDATE usuario SET "
                    + "nome = ?, CPF = ?, sexo = ?, email = ?, "
                    + "data_nascimento = ?, senha = ?, status_usuario = ?, telefone = ? "
                    + "WHERE ID_usuario = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, usu.getNome());
            stm.setString(2, usu.getCPF());
            stm.setString(3, usu.getSexo());
            stm.setString(4, usu.getEmail());
            stm.setDate(5, new java.sql.Date(usu.getData_nascimento().getTime()));
            stm.setString(6, usu.getSenha());
            stm.setInt(7, usu.getStatus_usuario());
            stm.setString(8, usu.getTelefone());
            stm.setInt(9, usu.getID_usuario());
            stm.executeUpdate();
            resultado = "alterado";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        } finally {
            fecharConexao(conn);
        }
        return resultado;
    }

    // ── DELETAR ──────────────────────────────────────────────────────────────
    public String deletarUsuario(int ID_usuario) {
        String resultado = "";
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();

        try {
            // corrigido: espaço antes de ID_usuario
            String consulta = "DELETE FROM usuario WHERE ID_usuario = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, ID_usuario);
            stm.executeUpdate();
            resultado = "deletado";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        } finally {
            fecharConexao(conn);
        }
        return resultado;
    }

    // ── CONSULTAR TIPO DE USUÁRIO ────────────────────────────────────────────
    public String consultarDescricaoTipoUsuario(int ID_usuario) {
        String descricao = "";
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();

        try {
            String consulta = "SELECT tu.descricao_usuario "
                    + "FROM Tipo_Usuario tu "
                    + "INNER JOIN Usuario u ON tu.ID_Usuario = u.ID_Usuario "
                    + "WHERE u.ID_Usuario = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, ID_usuario);
            ResultSet resultado = stm.executeQuery();
            if (resultado.next()) {
                descricao = resultado.getString("descricao_usuario");
            } else {
                descricao = "Tipo de usuário não encontrado.";
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            fecharConexao(conn);
        }
        return descricao;
    } // ← chave que estava faltando

    // ── IMPRIMIR NOME POR ID (utilitário) ───────────────────────────────────
    public void imprimirNomePorId(int ID_usuario) {
        Usuario usu = consultarUsuarioCodigo(ID_usuario);
        if (usu.getNome() != null) {
            System.out.println("Nome do usuário (ID " + ID_usuario + "): " + usu.getNome());
        } else {
            System.out.println("Usuário com ID " + ID_usuario + " não encontrado.");
        }
    }

    // ── IMPRIMIR TIPO DE USUÁRIO (utilitário) ────────────────────────────────
    public void imprimirDescricaoTipoUsuario(int ID_usuario) {
        Usuario usu = consultarUsuarioCodigo(ID_usuario);
        if (usu.getNome() != null) {
            String descricao = consultarDescricaoTipoUsuario(ID_usuario);
            System.out.println("Usuário: " + usu.getNome() + " | Tipo: " + descricao);
        } else {
            System.out.println("Usuário com ID " + ID_usuario + " não encontrado.");
        }
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
        UsuarioControle uc = new UsuarioControle();

        // LISTAR TODOS
        System.out.println("=== LISTA DE USUÁRIOS ===");
        for (Usuario usu : uc.consultarUsuario()) {
            System.out.println("ID: " + usu.getID_usuario() + " | Nome: " + usu.getNome() + " | Email: " + usu.getEmail());
        }

        // Descomente o bloco que quiser testar:

        // CONSULTAR POR ID
        /*
        Usuario u = uc.consultarUsuarioCodigo(1);
        if (u.getNome() != null) System.out.println("Encontrado: " + u.getNome());
        */

        // INSERIR
        /*
        Usuario novo = new Usuario();
        novo.setNome("João Silva");
        novo.setCPF("123.456.789-00");
        novo.setSexo("M");
        novo.setEmail("joao@email.com");
        novo.setData_nascimento(new java.util.Date());
        novo.setSenha("123456");
        novo.setStatus_usuario(1);
        novo.setTelefone("(11) 99999-9999");
        System.out.println("Inserir: " + uc.inserirUsuario(novo));
        */

        // ALTERAR
        /*
        Usuario u = uc.consultarUsuarioCodigo(1);
        if (u.getNome() != null) {
            u.setNome("Nome Alterado");
            System.out.println("Alterar: " + uc.alterarUsuario(u));
        }
        */

        // DELETAR
        /*
        System.out.println("Deletar: " + uc.deletarUsuario(1));
        */

        // TIPO DE USUÁRIO
        /*
        uc.imprimirDescricaoTipoUsuario(1);
        */
    }
}