/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;



import java.util.ArrayList;
import modelo.Elenco;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ElencoControle{

    public ElencoControle() {
    }

    public ArrayList<Elenco> consultarElenco() {
        ArrayList<Elenco> vElenco = new ArrayList();
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "SELECT * FROM elenco";
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);
           
            while (resultado.next()) {
                Elenco ele = new Elenco();
                ele.setID_Elenco(resultado.getInt("ID_Elenco"));
                ele.setNome(resultado.getString("nome"));
                ele.setFoto_elenco(resultado.getString("foto_elenco"));
                vElenco.add(ele);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(ElencoControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vElenco;
    }

    public Elenco consultarID_Elenco(int ID_Elenco) {
         Elenco ele = new Elenco();
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "SELECT * FROM Elenco WHERE ID_Elenco = " + ID_Elenco;
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);
           
            while (resultado.next()) {
                ele.setID_Elenco(resultado.getInt("ID_Elenco"));
                ele.setNome(resultado.getString("nome"));
                ele.setFoto_elenco(resultado.getString("foto_elenco"));
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(ElencoControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ele;
    }

    public String inserirElenco(Elenco ele) {
    String resultado = "";
    try {
        // Verifica se o nome já existe antes de inserir
        Elenco eleExistente = consultarElencoNomeCom(ele.getNome());
        if (eleExistente.getNome() != null) {
            return "ja existe";
        }

        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String consulta = "INSERT INTO elenco "
                + "(nome, foto_elenco) VALUES "
                + "(?,?)";
        PreparedStatement stm = conn.prepareStatement(consulta);
        stm.setString(1, ele.getNome());
        stm.setString(2, ele.getFoto_elenco());
        stm.executeUpdate();
        resultado = "inserido";
    } catch (SQLException ex) {
        resultado = ex.getSQLState();
        System.err.println(ex);
    }
    return resultado;
}
    
    
    public String deletarElenco(int ID_Elenco) {
    String resultado = "";
    try {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        
        
        String consulta = "DELETE FROM elenco WHERE ID_Elenco = ?";
        
        PreparedStatement stm = conn.prepareStatement(consulta);
        stm.setInt(1, ID_Elenco); 
        
        int linhasAfetadas = stm.executeUpdate();
        
        if (linhasAfetadas > 0) {
            resultado = "removido";
        } else {
            resultado = "ID não encontrado";
        }
        
        conn.close(); 
    } catch (SQLException ex) {
        resultado = ex.getSQLState();
        System.err.println("Erro ao deletar: " + ex.getMessage());
    }
    return resultado;
}

    public String alterarElenco(Elenco ele) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "UPDATE elenco SET nome = ?, foto_elenco = ? WHERE ID_Elenco = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, ele.getNome());
            stm.setString(2, ele.getFoto_elenco());
            stm.setInt(3, ele.getID_Elenco());
            stm.executeUpdate();
            resultado = "alterado";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }
    
     /*lalal*/
    
    public ArrayList<Elenco> consultarElencoporNome(String nome) {
    ArrayList<Elenco> vElenco = new ArrayList();
    try {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String consulta = "SELECT * FROM elenco WHERE nome LIKE ?";
        PreparedStatement stm = conn.prepareStatement(consulta);
        stm.setString(1, "%" + nome + "%");
        ResultSet resultado = stm.executeQuery();

        while (resultado.next()) {
            Elenco ele = new Elenco();
            ele.setID_Elenco(resultado.getInt("ID_Elenco"));
            ele.setNome(resultado.getString("nome"));
            ele.setFoto_elenco(resultado.getString("foto_elenco"));
            vElenco.add(ele);
        }

    } catch (SQLException ex) {
        Logger.getLogger(ElencoControle.class.getName()).log(Level.SEVERE, null, ex);
    }
    return vElenco;
}
    
    public Elenco consultarElencoNomeCom(String nome) {
    Elenco ele = new Elenco();
    try {
        ConexaoMySQL conexao = new ConexaoMySQL();
        Connection conn = conexao.conectar();
        String consulta = "SELECT * FROM elenco WHERE nome = ?";
        PreparedStatement stm = conn.prepareStatement(consulta);
        stm.setString(1,  nome );
        ResultSet resultado = stm.executeQuery();

        if (resultado.next()) {
            
            ele.setNome(resultado.getString("nome"));
            
        }

    } catch (SQLException ex) {
        Logger.getLogger(ElencoControle.class.getName()).log(Level.SEVERE, null, ex);
    }
    return ele;
}

    public static void main(String[] args) {
    ElencoControle ec = new ElencoControle();
    /*
    // 1. CONSULTA INICIAL (READ)
    System.out.println("=== LISTA DE ELENCO (INICIAL) ===");
    ArrayList<Elenco> listaInicial = ec.consultarElenco();
    for (Elenco ele : listaInicial) {
        System.out.println(ele.toString());
    }
    */
    // 2. INSERINDO UM NOVO (CREATE)
    
    /*
    System.out.println("\n=== INSERINDO NOVO ELENCO ===");
    Elenco novo = new Elenco();
    novo.setNome("Benedict Cumberbatch");
    novo.setFoto_elenco("wagner.jpg");
    String resInsert = ec.inserirElenco(novo);
    System.out.println("Resultado Inserir: " + resInsert);
    */

    // 3. ALTERANDO UM REGISTRO (UPDATE)
    /*
    
    System.out.println("\n=== ALTERANDO ELENCO ===");
    Elenco eleAlterar = ec.consultarID_Elenco(101);
    if (eleAlterar.getNome() != null) {
        eleAlterar.setNome("Nome Alterado via Java");
        String resUpdate = ec.alterarElenco(eleAlterar);
        System.out.println("Resultado Alterar: " + resUpdate);
    }
    */

    // 4. DELETANDO O ID 4 (DELETE)
    /*
    System.out.println("\n=== DELETANDO ELENCO ID 4 ===");
    String resDelete = ec.deletarElenco(101);
    System.out.println("Resultado Deletar: " + resDelete);
    */
    // 5. CONSULTA FINAL PARA CONFERIR
    /*
    System.out.println("\n=== LISTA DE ELENCO (APÓS DELETAR) ===");
    for (Elenco ele3 : ec.consultarElenco()) {
        System.out.println(ele3.toString());
    }
*/
    
    System.out.println("\n--- Busca por Nome ---");
    ArrayList<Elenco> resultado = ec.consultarElencoporNome("Bob");
    for (Elenco ele : resultado) {
        System.out.println(ele.toString());

    }

}

}

