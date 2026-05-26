// Atuacao_elencoControle.java
package controle;

import java.util.ArrayList;
import modelo.Atuacao_elenco;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Atuacao_elencoControle {

    public Atuacao_elencoControle() {
    }

    public ArrayList<Atuacao_elenco> consultarAtuacoes() {
        ArrayList<Atuacao_elenco> vAtuacoes = new ArrayList<>();

        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            try (Connection conn = conexao.conectar()) {
                String consulta = "SELECT * FROM atuacao_elenco";
                Statement stm = conn.createStatement();
                ResultSet resultado = stm.executeQuery(consulta);

                while (resultado.next()) {
                    Atuacao_elenco ae = new Atuacao_elenco();
                    ae.setID_atuacaoelenco(resultado.getInt("ID_atuacaoelenco"));
                    ae.setID_Filme(resultado.getInt("ID_Filme"));
                    ae.setIdTipoelenco(resultado.getInt("ID_TipoElenco"));
                    ae.setID_Elenco(resultado.getInt("ID_Elenco"));
                    vAtuacoes.add(ae);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Atuacao_elencoControle.class.getName()).log(Level.SEVERE, null, ex);
        }

        return vAtuacoes;
    }

    public Atuacao_elenco consultarAtuacacoCodigo(int ID_atuacaoelenco) {
        Atuacao_elenco ae = null; // CORRIGIDO: inicia como null

        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            try (Connection conn = conexao.conectar()) {
                // CORRIGIDO: substituída concatenação por PreparedStatement
                String consulta = "SELECT * FROM atuacao_elenco WHERE ID_atuacaoelenco = ?";
                PreparedStatement stm = conn.prepareStatement(consulta);
                stm.setInt(1, ID_atuacaoelenco);
                ResultSet resultado = stm.executeQuery();

                if (resultado.next()) { // CORRIGIDO: while -> if
                    ae = new Atuacao_elenco();
                    ae.setID_atuacaoelenco(resultado.getInt("ID_atuacaoelenco"));
                    ae.setID_Filme(resultado.getInt("ID_Filme"));
                    ae.setIdTipoelenco(resultado.getInt("ID_TipoElenco"));
                    ae.setID_Elenco(resultado.getInt("ID_Elenco"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Atuacao_elencoControle.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ae;
    }

    public String inserirAtuacacao(Atuacao_elenco ae) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "INSERT INTO atuacao_elenco "
                    + "(ID_atuacaoelenco, ID_Filme, ID_TipoElenco, ID_Elenco) VALUES (?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, ae.getID_atuacaoelenco());
            stm.setInt(2, ae.getID_Filme());
            stm.setInt(3, ae.getID_TipoElenco());
            stm.setInt(4, ae.getID_Elenco());
            stm.executeUpdate();
            conn.close();
            resultado = "inserido";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.out.println(ex);
        }
        return resultado;
    }
    
     public ArrayList<String> pesquisarInteracaoElenco(int idFilmePesquisado) {
        ArrayList<String> resultados = new ArrayList<>();
       
       
        String sql = "SELECT f.nome_filme, e.nome, t.descricao_elenco " +
                     "FROM atuacao_elenco ae " +
                     "INNER JOIN filme f ON ae.ID_Filme = f.ID_Filme " +
                     "INNER JOIN elenco e ON ae.ID_Elenco = e.ID_Elenco " +
                     "INNER JOIN tipo_elenco t ON ae.ID_TipoElenco = t.ID_TipoElenco " +
                     "WHERE ae.ID_Filme = ?";

        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            try (Connection conn = conexao.conectar()) {
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setInt(1, idFilmePesquisado);
                ResultSet rs = stm.executeQuery();

                while (rs.next()) {
                    String filme = rs.getString("nome_filme");
                    String ator = rs.getString("nome");
                    String papel = rs.getString("descricao_elenco");

                   
                    String frase =  filme + ", " + ator +
                                   ", " + papel + ".";
                    resultados.add(frase);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Atuacao_elencoControle.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return resultados;
    }

    public String alterarAtuacacao(Atuacao_elenco ae) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "UPDATE atuacao_elenco SET "
                    + "ID_Filme = ?, ID_TipoElenco = ?, ID_Elenco = ? "
                    + "WHERE ID_atuacaoelenco = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, ae.getID_Filme());
            stm.setInt(2, ae.getID_TipoElenco());
            stm.setInt(3, ae.getID_Elenco());
            stm.setInt(4, ae.getID_atuacaoelenco());
            stm.executeUpdate();
            conn.close();
            resultado = "alterado";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.out.println(ex);
        }
        return resultado;
    }

    public String deletarAtuacacao(int ID_atuacaoelenco) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "DELETE FROM atuacao_elenco WHERE ID_atuacaoelenco = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, ID_atuacaoelenco);
            stm.executeUpdate();
            conn.close();
            resultado = "deletado";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.out.println(ex);
        }
        return resultado;
    }

    public static void main(String[] args) {
        Atuacao_elencoControle aec = new Atuacao_elencoControle();

        // consultando todos:
        for (Atuacao_elenco ae : aec.consultarAtuacoes()) {
            System.out.println(ae.toString());
        }

        // inserindo um obj:
        /*
        Atuacao_elenco novaAtuacao = new Atuacao_elenco();
        novaAtuacao.setID_Filme(1);
        novaAtuacao.setIdTipoelenco(1);
        novaAtuacao.setID_Elenco(1);
        String resultadoInsert = aec.inserirAtuacacao(novaAtuacao);
        System.out.println(resultadoInsert);
        */

        // consultando por id:
        /*
        Atuacao_elenco atuacaoConsultada = aec.consultarAtuacacoCodigo(100);
        System.out.println(atuacaoConsultada);
        */

        // alterando um obj:
        /*
        Atuacao_elenco atuacaoCons = aec.consultarAtuacacoCodigo(1);
        atuacaoCons.setID_Filme(5);
        String resultadoUpdate = aec.alterarAtuacacao(atuacaoCons);
        System.out.println(resultadoUpdate);
        */

        // deletando um obj:
        /*
        String resultadoDelete = aec.deletarAtuacacao(101);
        System.out.println(resultadoDelete);
        */
    }
}