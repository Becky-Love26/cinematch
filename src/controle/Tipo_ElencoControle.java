package controle;

import java.util.ArrayList;
import modelo.Tipo_elenco; 
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tipo_ElencoControle {

    public Tipo_ElencoControle() {
    }

    public ArrayList<Tipo_elenco> consultarTiposElenco() {
        ArrayList<Tipo_elenco> vTipos = new ArrayList<>();

        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "SELECT * FROM Tipo_elenco";
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);
            
            while (resultado.next()) {
                Tipo_elenco te = new Tipo_elenco();
                te.setID_TipoElenco(resultado.getInt("ID_TipoElenco"));
                te.setDescricao_elenco(resultado.getString("descricao_elenco"));
                vTipos.add(te);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Tipo_ElencoControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return vTipos;
    }

    public Tipo_elenco consultarTipoElencoPorCodigo(int idTipoElenco) {
        Tipo_elenco te = new Tipo_elenco();

        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "SELECT * FROM tipo_elenco WHERE ID_TipoElenco = " + idTipoElenco;
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);
            
            if (resultado.next()) {
                te.setID_TipoElenco(resultado.getInt("ID_TipoElenco"));
                te.setDescricao_elenco(resultado.getString("descricao_elenco"));
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Tipo_ElencoControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return te;
    }

    public String inserirTipoElenco(Tipo_elenco te) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "INSERT INTO tipo_elenco (ID_TipoElenco, descricao_elenco) VALUES (?,?)";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, te.getID_TipoElenco());
            stm.setString(2, te.getDescricao_elenco());
            stm.executeUpdate();
            
            resultado = "inserido";
            conn.close();
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.out.println(ex);
        }
        return resultado;
    }

    public String alterarTipoElenco(Tipo_elenco te) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            try (Connection conn = conexao.conectar()) {
                String consulta = "UPDATE tipo_elenco SET descricao_elenco = ? WHERE ID_TipoElenco = ?";
                PreparedStatement stm = conn.prepareStatement(consulta);
                stm.setString(1, te.getDescricao_elenco());
                stm.setInt(2, te.getID_TipoElenco());
                stm.executeUpdate();
                
                resultado = "alterado";
            }
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.out.println(ex);
        }
        return resultado;
    }

    public String deletarTipoElenco(int idTipoElenco) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            try (Connection conn = conexao.conectar()) {
                String consulta = "DELETE FROM tipo_elenco WHERE ID_TipoElenco = ?";
                PreparedStatement stm = conn.prepareStatement(consulta);
                stm.setInt(1, idTipoElenco);
                stm.executeUpdate();
                
                resultado = "deletado";
            }
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.out.println(ex);
        }
        return resultado;
    }

   
public static void main(String[] args) {
    Tipo_ElencoControle tec = new Tipo_ElencoControle();

    // 1. CONSULTAR TODOS (READ)
    
    System.out.println("=== LISTA INICIAL ===");
    for (Tipo_elenco te : tec.consultarTiposElenco()) {
        System.out.println(te.toString());
    }
    

    // 2. INSERIR NOVO REGISTRO (CREATE)
    /*
    System.out.println("\n=== INSERINDO ===");
    Tipo_elenco novo = new Tipo_elenco();
    // novo.setID_TipoElenco(10); // Use se não for auto-incremento
    novo.setDescricao_elenco("Dublador de Teste");
    
    String resInsert = tec.inserirTipoElenco(novo);
    System.out.println("Resultado Inserir: " + resInsert);
    */

    // 3. CONSULTAR POR CÓDIGO (READ BY ID)
    /*
    System.out.println("\n=== CONSULTA POR ID ===");
    Tipo_elenco buscado = tec.consultarTipoElencoPorCodigo(15); 
    System.out.println("Encontrado: " + buscado.toString());
    */

    // 4. ALTERAR REGISTRO (UPDATE)
    /*
    System.out.println("\n=== ALTERANDO ===");
    Tipo_elenco edit = tec.consultarTipoElencoPorCodigo(15);
    if (edit.getDescricao_elenco() != null) {
        edit.setDescricao_elenco("Papel Principal Atualizado");
        String resUpdate = tec.alterarTipoElenco(edit);
        System.out.println("Resultado Alterar: " + resUpdate);
    } else {
        System.out.println("Objeto não encontrado para alteração.");
    }
    */

    // 5. REMOVER REGISTRO (DELETE)
    /*
    System.out.println("\n=== DELETANDO ===");
    String resDelete = tec.deletarTipoElenco(15); 
    System.out.println("Resultado Deletar: " + resDelete);
    */

    // 6. LISTA FINAL PARA CONFERÊNCIA
    /*
    System.out.println("\n=== LISTA FINAL ===");
    for (Tipo_elenco te : tec.consultarTiposElenco()) {
        System.out.println(te.toString());
    }
    */
}
}