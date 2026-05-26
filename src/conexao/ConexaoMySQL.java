package conexao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoMySQL {

    public ConexaoMySQL() {
    }

    public Connection conectar() {
        Connection conn = null;
        try {          
            System.out.println("Conectando ao banco...");
            Class.forName("com.mysql.cj.jdbc.Driver"); // driver
            String ip = "localhost"; // IP do servidor de banco
            String us = "root"; // usuário do banco
            String bd = "cinematc_cinemath"; // nome do banco de dados
            String pw = ""; // senha do usuário
            conn = DriverManager.getConnection("jdbc:mysql://" + ip
                    + "/" + bd, us, pw); // instrução para conectar com o BD
            // passamos o IP do servidor de Banco
            // nome do banco, usuário e senha
            System.out.println("Conectado.");   
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static void main(String[] args) {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        System.out.println(conn);
    }
}
