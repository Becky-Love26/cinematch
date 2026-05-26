package controle;

import java.util.ArrayList;
import modelo.Filme_genero;
import modelo.Filme;
import modelo.Genero;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Filme_GeneroControle{

    public Filme_GeneroControle() {
    }

    public ArrayList<Filme_genero> consultarFilme_genero() {
        ArrayList<Filme_genero> vFilme_genero = new ArrayList();
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "SELECT * FROM Filme_genero";
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);
           
            while (resultado.next()) {
                Filme_genero fg = new Filme_genero();
                fg.setID_Filme_Genero(resultado.getInt("ID_Filme_Genero"));
                fg.setID_Genero(new GeneroControle().consultarID_Genero(resultado.getInt("ID_Genero")));
                fg.setID_Filme(new FilmeControle().consultarFilmeCodigo(resultado.getInt("ID_Filme")));
                vFilme_genero.add(fg);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(ElencoControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vFilme_genero;
    }

    public Filme_genero consultarID_Filme_Genero(int ID_Filme_Genero) {
         Filme_genero fg = new Filme_genero();
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "SELECT * FROM Filme_genero WHERE ID_Filme_Genero = " + ID_Filme_Genero;
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);
           
            while (resultado.next()) {
                fg.setID_Filme_Genero(resultado.getInt("ID_Filme_Genero"));
                fg.setID_Genero(new GeneroControle().consultarID_Genero(resultado.getInt("ID_Genero")));
                fg.setID_Filme(new FilmeControle().consultarFilmeCodigo(resultado.getInt("ID_Filme")));
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(Filme_GeneroControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fg;
    }
    public ArrayList<Filme_genero> consultarFIlmeporGenero(int ID_Genero) {
         
         ArrayList<Filme_genero> verfilmegenero= new ArrayList() ;
        try {
            
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "SELECT * FROM Filme_genero WHERE ID_Genero = "+ ID_Genero;
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);
           
            while (resultado.next()) {
                Filme_genero fg = new Filme_genero();
                fg.setID_Filme_Genero(resultado.getInt("ID_Filme_Genero"));
                fg.setID_Genero(new GeneroControle().consultarID_Genero(resultado.getInt("ID_Genero")));
                fg.setID_Filme(new FilmeControle().consultarFilmeCodigo(resultado.getInt("ID_Filme")));
                verfilmegenero.add(fg);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(Filme_GeneroControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return verfilmegenero;
    }

    public String inserirFilme_genero(Filme_genero fg) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "INSERT INTO Filme_genero "
                    + "(ID_Genero, ID_Filme) VALUES "
                    + "(?,?)";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, fg.getID_Genero().getID_Genero());
            stm.setInt(2, fg.getID_Filme().getID_Filme());
            stm.executeUpdate();
            resultado = "inserido";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }

    public String alterarFilme_genero(Filme_genero fg) {
    String resultado = "";
    try {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String consulta = "UPDATE Filme_genero SET ID_Genero = ?, ID_Filme = ? WHERE ID_Filme_Genero = ?";
        PreparedStatement stm = conn.prepareStatement(consulta);
       
        stm.setInt(1, fg.getID_Genero().getID_Genero());
        stm.setInt(2, fg.getID_Filme().getID_Filme());
        stm.setInt(3, fg.getID_Filme_Genero());
       
        stm.executeUpdate();
        resultado = "alterado";
    } catch (SQLException ex) {
        resultado = ex.getSQLState();
        System.err.println(ex);
    }
    return resultado;
}

    public String deletarID_Filme_Genero(int ID_Filme_Genero) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "DELETE FROM Filme_genero WHERE ID_Filme_Genero = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, ID_Filme_Genero);
            stm.executeUpdate();
            resultado = "removido";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }
    
    public ArrayList<Filme_genero> consultarGeneroPorFilme(int ID_Filme) {
    ArrayList<Filme_genero> vFilmeGenero = new ArrayList();
    try {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String consulta = "SELECT * FROM Filme_genero WHERE ID_Filme = " + ID_Filme;
        Statement stm = conn.createStatement();
        ResultSet resultado = stm.executeQuery(consulta);

        while (resultado.next()) {
            Filme_genero fg = new Filme_genero();
            fg.setID_Filme_Genero(resultado.getInt("ID_Filme_Genero"));
            fg.setID_Genero(new GeneroControle().consultarID_Genero(resultado.getInt("ID_Genero")));
            fg.setID_Filme(new FilmeControle().consultarFilmeCodigo(resultado.getInt("ID_Filme")));
            vFilmeGenero.add(fg);
        }

    } catch (SQLException ex) {
        Logger.getLogger(Filme_GeneroControle.class.getName()).log(Level.SEVERE, null, ex);
    }
    return vFilmeGenero;
}
   
    public static void main(String[] args) {
    Filme_GeneroControle fgc = new Filme_GeneroControle();

    // 1. Consultando todas as relações (READ)
    System.out.println("=== LISTA DE FILMES E GÊNEROS ===");
    for (Filme_genero fg : fgc.consultarFilme_genero()) {
        System.out.println(fg.toString());
    }

    // 2. Inserindo uma relação (CREATE)
    /*
    System.out.println("\n=== INSERINDO RELAÇÃO FILME/GÊNERO ===");
    Filme_genero novoFG = new Filme_genero();
   
    // IMPORTANTE: Buscando os objetos das outras tabelas
    // Aqui assumimos que o Gênero ID 1 e Filme ID 1 existem
    novoFG.setID_Genero(new GeneroControle().consultarID_Genero(1));
    novoFG.setID_Filme(new FilmeControle().consultarFilmeCodigo(1));

    String resultadoInsert = fgc.inserirFilme_genero(novoFG);
    System.out.println("Resultado Inserir: " + resultadoInsert);
    */

    // 3. Consultando por ID (READ BY ID)
    
    System.out.println("\n=== CONSULTA POR ID ===");
    ArrayList<Filme_genero> fgConsultado = fgc.consultarFIlmeporGenero(3);
    for (Filme_genero fg : fgConsultado) {
        System.out.println(fg.toString());
    }
    
    System.out.println("\n=== GÊNEROS DO FILME ID 2 ===");
ArrayList<Filme_genero> generos = fgc.consultarGeneroPorFilme(2);
for (Filme_genero fg : generos) {
    System.out.println(fg.toString());
}
    

    // 4. Alterando uma relação (UPDATE)
    /*
    System.out.println("\n=== ALTERANDO RELAÇÃO ===");
    // Buscamos a relação de ID 1
    Filme_genero fgCons = fgc.consultarID_Filme_Genero(1);
   
    if(fgCons.getID_Filme_Genero() > 0) {
        // Alterando para o Gênero de ID 2, por exemplo
        fgCons.setID_Genero(new GeneroControle().consultarID_Genero(2));
       
        String resultadoUpdate = fgc.alterarFilme_genero(fgCons);
        System.out.println("Resultado alterar: " + resultadoUpdate);
    } else {
        System.out.println("Registro não encontrado para alteração.");
    }
    */

    // 5. Deletando uma relação (DELETE)
    /*
    System.out.println("\n=== DELETANDO RELAÇÃO ===");
    String resultadoDelete = fgc.deletarID_Filme_Genero(1);
    System.out.println("Resultado deletar: " + resultadoDelete);
    */
}

}
