package controle;

import modelo.Perfil;
import modelo.Usuario;
import java.util.ArrayList;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PerfilControle {

    public PerfilControle() {

    }

    public ArrayList<Perfil> consultarPerfis() {

        ArrayList<Perfil> vPerfis = new ArrayList();

        try {

            ConexaoMySQL conexao = new ConexaoMySQL();

            Connection conn = conexao.conectar();

            String consulta = "SELECT * FROM perfil";

            Statement stm = conn.createStatement();

            ResultSet resultado = stm.executeQuery(consulta);

            while (resultado.next()) {

                Perfil p = new Perfil();

                p.setID_Perfil(resultado.getInt("ID_Perfil"));

                p.setNome_perfil(resultado.getString("nome_perfil"));

                p.setClassificacao_perfil(resultado.getInt("classificacao_perfil"));

                p.setFoto_perfil(resultado.getString("foto_perfil"));

                p.setID_usuario(new UsuarioControle().consultarUsuarioCodigo(resultado.getInt("ID_usuario")));

                vPerfis.add(p);

            }

        } catch (SQLException ex) {

            Logger.getLogger(PerfilControle.class.getName()).log(Level.SEVERE, null, ex);

        }

        return vPerfis;

    }

    public Perfil consultarPerfilCodigo(int idPerfil) {

        Perfil p = new Perfil();
            ConexaoMySQL conexao = new ConexaoMySQL();

            Connection conn = conexao.conectar();
        try {

            

            String consulta = "SELECT * FROM perfil WHERE ID_Perfil = " + idPerfil;

            Statement stm = conn.createStatement();

            ResultSet resultado = stm.executeQuery(consulta);

            while (resultado.next()) {

                p.setID_Perfil(resultado.getInt("ID_Perfil"));

                p.setNome_perfil(resultado.getString("nome_perfil"));

                p.setClassificacao_perfil(resultado.getInt("classificacao_perfil"));

                p.setFoto_perfil(resultado.getString("foto_perfil"));

                p.setID_usuario(new UsuarioControle().consultarUsuarioCodigo(resultado.getInt("ID_usuario")));

            }

        } catch (SQLException ex) {

            Logger.getLogger(PerfilControle.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            // ESSA PARTE É VITAL: Garante que a conexão feche sempre
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar: " + e.getMessage());
            }
        }

        return p;

    }

    public String inserirPerfil(Perfil p) {

        String resultado = "";

        try {

            ConexaoMySQL conexao = new ConexaoMySQL();

            Connection conn = conexao.conectar();

            String consulta = "INSERT INTO perfil "
                    + "(nome_perfil, classificacao_perfil, ID_usuario, foto_perfil) VALUES "
                    + "(?,?,?,?)";

            PreparedStatement stm = conn.prepareStatement(consulta);

            stm.setString(1, p.getNome_perfil());

            stm.setInt(2, p.getClassificacao_perfil());

            stm.setInt(3, p.getID_usuario().getID_usuario());

            stm.setString(4, p.getFoto_perfil());

            stm.executeUpdate();

            resultado = "inserido";

        } catch (SQLException ex) {

            resultado = ex.getSQLState();

            System.out.println(ex);

        }

        return resultado;

    }

    public String alterarPerfil(Perfil p) {

        String resultado = "";

        try {

            ConexaoMySQL conexao = new ConexaoMySQL();

            Connection conn = conexao.conectar();

            String consulta = "UPDATE perfil SET "
                    + "nome_perfil = ?, classificacao_perfil = ?, ID_usuario = ?, foto_perfil = ? "
                    + "WHERE ID_Perfil = ?";

            PreparedStatement stm = conn.prepareStatement(consulta);

            stm.setString(1, p.getNome_perfil());

            stm.setInt(2, p.getClassificacao_perfil());

            stm.setInt(3, p.getID_usuario().getID_usuario());

            stm.setString(4, p.getFoto_perfil());

            stm.setInt(5, p.getID_Perfil());

            stm.executeUpdate();

            resultado = "alterado";

        } catch (SQLException ex) {

            resultado = ex.getSQLState();

            System.out.println(ex);

        }

        return resultado;

    }

    public String deletarPerfil(int idPerfil) {

        String resultado = "";

        try {

            ConexaoMySQL conexao = new ConexaoMySQL();

            Connection conn = conexao.conectar();

            String consulta = "DELETE FROM perfil WHERE ID_Perfil = ?";

            PreparedStatement stm = conn.prepareStatement(consulta);

            stm.setInt(1, idPerfil);

            stm.executeUpdate();

            resultado = "deletado";

        } catch (SQLException ex) {

            resultado = ex.getSQLState();

            System.out.println(ex);

        }

        return resultado;

    }

    public static void main(String[] args) {
        PerfilControle controle = new PerfilControle();

        // consultando todos:
        for (Perfil p : controle.consultarPerfis()) {
            System.out.println(p.toString());
        }

        // inserindo um obj:
       
       
        Perfil novoPerfil = new Perfil();
        novoPerfil.setNome_perfil("Novo Perfil Teste");
        novoPerfil.setClassificacao_perfil(5);
        novoPerfil.setFoto_perfil("caminho/imagem.png");
        novoPerfil.setID_usuario(new UsuarioControle().consultarUsuarioCodigo(1));

        String resultadoInsert = controle.inserirPerfil(novoPerfil);
        System.out.println(resultadoInsert);
       
       
        // consultando por id
       
       
        Perfil perfilConsultado = controle.consultarPerfilCodigo(1);
        System.out.println(perfilConsultado);
       
         
        // alterando um obj:
       
       
        Perfil perfilCons = controle.consultarPerfilCodigo(1);
       
       
        perfilCons.setNome_perfil("Administrador Master");
        perfilCons.setClassificacao_perfil(10);
        System.out.println(perfilCons.toString());
       
        String resultadoUpdate = controle.alterarPerfil(perfilCons);
        System.out.println("Resultado alterar: " + resultadoUpdate);
       
       
        // deletando um obj:
       
       
        String resultadoDelete = controle.deletarPerfil(109);
        System.out.println(resultadoDelete);
       
    }
}
