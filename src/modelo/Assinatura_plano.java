/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Date;

public class Assinatura_plano {
        private int ID_Assinatura;
        private String tipo_pagamento;
        private Date data_assinatura;
        private int status_assinatura;
        private Plano ID_Plano;
        private Usuario ID_Usuario;

    public Assinatura_plano() {
    }

    public Assinatura_plano(int ID_Assinatura, String tipo_pagamento, Date data_assinatura, int status_assinatura, Plano ID_Plano, Usuario ID_Usuario) {
        this.ID_Assinatura = ID_Assinatura;
        this.tipo_pagamento = tipo_pagamento;
        this.data_assinatura = data_assinatura;
        this.status_assinatura = status_assinatura;
        this.ID_Plano = ID_Plano;
        this.ID_Usuario = ID_Usuario;
    }

    public int getID_Assinatura() {
        return ID_Assinatura;
    }

    public void setID_Assinatura(int ID_Assinatura) {
        this.ID_Assinatura = ID_Assinatura;
    }

    public String getTipo_pagamento() {
        return tipo_pagamento;
    }

    public void setTipo_pagamento(String tipo_pagamento) {
        this.tipo_pagamento = tipo_pagamento;
    }

    public Date getData_assinatura() {
        return data_assinatura;
    }

    public void setData_assinatura(Date data_assinatura) {
        this.data_assinatura = data_assinatura;
    }

    public int getStatus_assinatura() {
        return status_assinatura;
    }

    public void setStatus_assinatura(int status_assinatura) {
        this.status_assinatura = status_assinatura;
    }

    public Plano getID_Plano() {
        return ID_Plano;
    }

    public void setID_Plano(Plano ID_Plano) {
        this.ID_Plano = ID_Plano;
    }

    public Usuario getID_Usuario() {
        return ID_Usuario;
    }

    public void setID_Usuario(Usuario ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }

    @Override
    public String toString() {
        return "assinatura_plano{" + "ID_Assinatura=" + ID_Assinatura + ", tipo_pagamento=" + tipo_pagamento + ", data_assinatura=" + data_assinatura + ", status_assinatura=" + status_assinatura + ", [" + ID_Plano.toString() + "], ID_Usuario=" + ID_Usuario + '}';
    }
        
        
    
}