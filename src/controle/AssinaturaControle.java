package controle;

import java.util.ArrayList;
import modelo.Assinatura_plano;
import modelo.Usuario;
import modelo.Plano;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

public class AssinaturaControle {

    public AssinaturaControle() {
    }

    public ArrayList<Assinatura_plano> consultarAssinaturas() {
        ArrayList<Assinatura_plano> vAssinaturas = new ArrayList<>();
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "SELECT * FROM assinatura_plano";
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);

            while (resultado.next()) {
                Assinatura_plano ass = new Assinatura_plano();
                ass.setID_Assinatura(resultado.getInt("ID_Assinatura"));
                ass.setTipo_pagamento(resultado.getString("tipo_pagamento"));
                ass.setData_assinatura(resultado.getDate("data_assinatura"));
                ass.setStatus_assinatura(resultado.getInt("status_assinatura"));
                ass.setID_Plano(new PlanoControle().consultarPlanoCodigo(resultado.getInt("ID_Plano")));
                ass.setID_Usuario(new UsuarioControle().consultarUsuarioCodigo(resultado.getInt("ID_Usuario")));
                vAssinaturas.add(ass);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssinaturaControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vAssinaturas;
    }

    public Assinatura_plano consultarAssinaturaPorID(int idAssinatura) {
        Assinatura_plano ass = new Assinatura_plano();
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "SELECT * FROM assinatura_plano WHERE ID_Assinatura = " + idAssinatura;
            Statement stm = conn.createStatement();
            ResultSet resultado = stm.executeQuery(consulta);

            while (resultado.next()) {
                ass.setID_Assinatura(resultado.getInt("ID_Assinatura"));
                ass.setTipo_pagamento(resultado.getString("tipo_pagamento"));
                ass.setData_assinatura(resultado.getDate("data_assinatura"));
                ass.setStatus_assinatura(resultado.getInt("status_assinatura"));
                ass.setID_Plano(new PlanoControle().consultarPlanoCodigo(resultado.getInt("ID_Plano")));
                ass.setID_Usuario(new UsuarioControle().consultarUsuarioCodigo(resultado.getInt("ID_Usuario")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssinaturaControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ass;
    }

    public ArrayList<Assinatura_plano> consultarPorTipoPagamento(String tipoPagamento) {
        ArrayList<Assinatura_plano> vAssinaturas = new ArrayList<>();
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "SELECT * FROM assinatura_plano WHERE tipo_pagamento = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, tipoPagamento);
            ResultSet resultado = stm.executeQuery();

            while (resultado.next()) {
                Assinatura_plano ass = new Assinatura_plano();
                ass.setID_Assinatura(resultado.getInt("ID_Assinatura"));
                ass.setTipo_pagamento(resultado.getString("tipo_pagamento"));
                ass.setData_assinatura(resultado.getDate("data_assinatura"));
                ass.setStatus_assinatura(resultado.getInt("status_assinatura"));
                ass.setID_Plano(new PlanoControle().consultarPlanoCodigo(resultado.getInt("ID_Plano")));
                ass.setID_Usuario(new UsuarioControle().consultarUsuarioCodigo(resultado.getInt("ID_Usuario")));
                vAssinaturas.add(ass);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssinaturaControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vAssinaturas;
    }

    public ArrayList<Assinatura_plano> consultarPorDataAssinatura(Date dataInicio, Date dataFim) {
        ArrayList<Assinatura_plano> vAssinaturas = new ArrayList<>();
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "SELECT * FROM assinatura_plano WHERE data_assinatura BETWEEN ? AND ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setDate(1, new java.sql.Date(dataInicio.getTime()));
            stm.setDate(2, new java.sql.Date(dataFim.getTime()));
            ResultSet resultado = stm.executeQuery();

            while (resultado.next()) {
                Assinatura_plano ass = new Assinatura_plano();
                ass.setID_Assinatura(resultado.getInt("ID_Assinatura"));
                ass.setTipo_pagamento(resultado.getString("tipo_pagamento"));
                ass.setData_assinatura(resultado.getDate("data_assinatura"));
                ass.setStatus_assinatura(resultado.getInt("status_assinatura"));
                ass.setID_Plano(new PlanoControle().consultarPlanoCodigo(resultado.getInt("ID_Plano")));
                ass.setID_Usuario(new UsuarioControle().consultarUsuarioCodigo(resultado.getInt("ID_Usuario")));
                vAssinaturas.add(ass);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssinaturaControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vAssinaturas;
    }

    public Plano consultarPlanoPorUsuario(int idUsuario) {
        Plano plano = null;
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "SELECT ID_Plano FROM assinatura_plano WHERE ID_Usuario = ? AND status_assinatura = 1";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, idUsuario);
            ResultSet resultado = stm.executeQuery();

            if (resultado.next()) {
                plano = new PlanoControle().consultarPlanoCodigo(resultado.getInt("ID_Plano"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssinaturaControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plano;
    }

    public ArrayList<Assinatura_plano> consultarPorStatus(int status) {
        ArrayList<Assinatura_plano> vAssinaturas = new ArrayList<>();
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "SELECT * FROM assinatura_plano WHERE status_assinatura = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, status);
            ResultSet resultado = stm.executeQuery();

            while (resultado.next()) {
                Assinatura_plano ass = new Assinatura_plano();
                ass.setID_Assinatura(resultado.getInt("ID_Assinatura"));
                ass.setTipo_pagamento(resultado.getString("tipo_pagamento"));
                ass.setData_assinatura(resultado.getDate("data_assinatura"));
                ass.setStatus_assinatura(resultado.getInt("status_assinatura"));
                ass.setID_Plano(new PlanoControle().consultarPlanoCodigo(resultado.getInt("ID_Plano")));
                ass.setID_Usuario(new UsuarioControle().consultarUsuarioCodigo(resultado.getInt("ID_Usuario")));
                vAssinaturas.add(ass);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssinaturaControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vAssinaturas;
    }

    public int contarUsuariosPorPlano(int idPlano) {
        int total = 0;
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "SELECT COUNT(DISTINCT ID_Usuario) AS total FROM assinatura_plano WHERE ID_Plano = ? AND status_assinatura = 1";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, idPlano);
            ResultSet resultado = stm.executeQuery();

            if (resultado.next()) {
                total = resultado.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssinaturaControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public String inserirAssinatura(Assinatura_plano ass) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "INSERT INTO assinatura_plano "
                    + "(tipo_pagamento, data_assinatura, status_assinatura, ID_Plano, ID_Usuario) VALUES "
                    + "(?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, ass.getTipo_pagamento());
            stm.setDate(2, new java.sql.Date(ass.getData_assinatura().getTime()));
            stm.setInt(3, ass.getStatus_assinatura());
            stm.setInt(4, ass.getID_Plano().getID_plano());
            stm.setInt(5, ass.getID_Usuario().getID_usuario());
            stm.executeUpdate();
            resultado = "inserido";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }

    public String alterarAssinatura(Assinatura_plano ass) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "UPDATE assinatura_plano SET "
                    + "tipo_pagamento = ?, data_assinatura = ?, status_assinatura = ?, ID_Plano = ?, ID_Usuario = ? "
                    + "WHERE ID_Assinatura = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setString(1, ass.getTipo_pagamento());
            stm.setDate(2, new java.sql.Date(ass.getData_assinatura().getTime()));
            stm.setInt(3, ass.getStatus_assinatura());
            stm.setInt(4, ass.getID_Plano().getID_plano());
            stm.setInt(5, ass.getID_Usuario().getID_usuario());
            stm.setInt(6, ass.getID_Assinatura());
            stm.executeUpdate();
            resultado = "alterado";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }

    public String deletarAssinatura(int idAssinatura) {
        String resultado = "";
        try {
            ConexaoMySQL conexao = new ConexaoMySQL();
            Connection conn = conexao.conectar();
            String consulta = "DELETE FROM assinatura_plano WHERE ID_Assinatura = ?";
            PreparedStatement stm = conn.prepareStatement(consulta);
            stm.setInt(1, idAssinatura);
            stm.executeUpdate();
            resultado = "removido";
        } catch (SQLException ex) {
            resultado = ex.getSQLState();
            System.err.println(ex);
        }
        return resultado;
    }

    public static void main(String[] args) {
        AssinaturaControle ac = new AssinaturaControle();

        System.out.println("=== TODAS AS ASSINATURAS ===");
        for (Assinatura_plano ass : ac.consultarAssinaturas()) {
            System.out.println(ass.toString());
        }

        System.out.println("\n=== POR TIPO DE PAGAMENTO (PIX) ===");
        for (Assinatura_plano ass : ac.consultarPorTipoPagamento("PIX")) {
            System.out.println(ass.toString());
        }

        System.out.println("\n=== POR STATUS ATIVO (1) ===");
        for (Assinatura_plano ass : ac.consultarPorStatus(1)) {
            System.out.println(ass.toString());
        }

        System.out.println("\n=== PLANO DO USUARIO ID 1 ===");
        System.out.println(ac.consultarPlanoPorUsuario(1));

        System.out.println("\n=== TOTAL USUARIOS NO PLANO ID 1 ===");
        System.out.println(ac.contarUsuariosPorPlano(1));
    }
}