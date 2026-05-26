package controle;

import java.util.ArrayList;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Plano;


public class PlanoControle {

    public PlanoControle() {
    }
   
    public ArrayList<Plano> consultarPlano(){
        ArrayList<Plano> vPlano =  new ArrayList();
        try{
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = (Connection) conexao.conectar();
            String consulta = "SELECT * FROM plano";
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);
            while(resultado.next()){
                Plano pla = new Plano ();
                pla.setID_plano(resultado.getInt("ID_plano"));
                pla.setTipo_plano(resultado.getString("tipo_plano"));
                pla.setValor(resultado.getFloat("valor"));
                vPlano.add(pla);
            }
           
        }catch (SQLException ex) {
            Logger.getLogger(PlanoControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vPlano;
    }
   
    public Plano consultarPlanoCodigo(int ID_plano){
        Plano pla = new Plano();
        try{
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = (Connection) conexao.conectar();
            String consulta = "SELECT * FROM plano WHERE ID_plano = " +ID_plano;
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);
            while(resultado.next()){
                pla.setID_plano(resultado.getInt("ID_plano"));
                pla.setTipo_plano(resultado.getString("tipo_plano"));
                pla.setValor(resultado.getFloat("valor"));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(PlanoControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pla;
    }
    public String inserirPlano(Plano pla){
        String resultado = "";
        try{
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "INSERT INTO plano (tipo_plano, valor) VALUES (?, ?)";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, pla.getTipo_plano());
            stm.setDouble(2, pla.getValor());
            stm.executeUpdate();
            resultado = "inserido";
        }
        catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }
    public String alterarPlano(Plano pla){
        String resultado = "";
        try{
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "UPDATE plano SET "
                    + "tipo_plano = ?, valor = ? WHERE "
                    + "ID_plano = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, pla.getTipo_plano());
            stm.setDouble(2, pla.getValor());
            stm.setInt(3, pla.getID_plano());
            stm.executeUpdate();
            resultado = "alterado";
        }
        catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }
    public String deletarPlano(int ID_plano){
        String resultado = "";
        try{
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta ="DELETE FROM plano WHERE "
                    + "ID_plano = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, ID_plano);
            stm.executeUpdate();
            resultado = "deletado";
        }
        catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }
    public void imprimirTipoAssinaturaPorPlano(int ID_plano) {
    Plano pla = consultarPlanoCodigo(ID_plano);
   
    if (pla.getTipo_plano() != null) {
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
           
            String consulta = "SELECT p.tipo_plano, ap.tipo_pagamento " +
                              "FROM Plano p " +
                              "INNER JOIN Assinatura_Plano ap ON p.ID_Plano = ap.ID_Plano " +
                              "WHERE p.ID_Plano = ?";
           
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, ID_plano);
            ResultSet resultado = stm.executeQuery();
           
            System.out.println("=== PLANO: " + pla.getTipo_plano() + " ===");
           
            boolean encontrou = false;
            while (resultado.next()) {
                encontrou = true;
                System.out.println("Tipo de Assinatura: " + resultado.getString("tipo_pagamento"));
            }
           
            if (!encontrou) {
                System.out.println("Nenhuma assinatura encontrada para este plano.");
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(PlanoControle.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        System.out.println("Plano com ID " + ID_plano + " não encontrado.");
    }
}
    public static void main(String[] args) {
        PlanoControle pc = new PlanoControle();

    // 1. Consultando todos os planos (READ)
    /*
    System.out.println("=== LISTA DE PLANOS ===");
    for (Plano p : pc.consultarPlano()) {
        System.out.println("ID: " + p.getID_plano() + " | Tipo: " + p.getTipo_plano() + " | Valor: R$ " + p.getValor());
    }

    // 2. Inserindo um plano (CREATE)
    /*
    System.out.println("\n=== INSERINDO PLANO ===");
    Plano novoPlano = new Plano();
    novoPlano.setTipo_plano("Premium Mensal");
    novoPlano.setValor(49.90f);

    String resultadoInsert = pc.inserirPlano(novoPlano);
    System.out.println("Resultado Inserir: " + resultadoInsert);
   

    // 3. Consultando por ID (READ BY ID)
   
    System.out.println("\n=== CONSULTA POR ID ===");
    Plano planoCons = pc.consultarPlanoCodigo(1);
    if(planoCons.getTipo_plano() != null) {
        System.out.println("Plano: " + planoCons.getTipo_plano() + " - Valor: " + planoCons.getValor());
    } else {
        System.out.println("Plano não encontrado.");
    }
 

    // 4. Alterando um plano (UPDATE)
    /*
    System.out.println("\n=== ALTERANDO PLANO ===");
    Plano pAlt = pc.consultarPlanoCodigo(1);
   
    if(pAlt.getTipo_plano() != null) {
        pAlt.setTipo_plano("Plano Família");
        pAlt.setValor(79.90f);

        // Atenção: no seu código o método chama-se alterarUsuario
        String resultadoUpdate = pc.alterarPlano(pAlt);
        System.out.println("Resultado alterar: " + resultadoUpdate);
    }
    */

    // 5. Deletando um plano (DELETE)
    /*
    System.out.println("\n=== DELETANDO PLANO ===");
    String resultadoDelete = pc.deletarPlano(1);
    System.out.println("Resultado deletar: " + resultadoDelete);
    */
    //
   
    pc.imprimirTipoAssinaturaPorPlano(1);

   
    }
   
}
