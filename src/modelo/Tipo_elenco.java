
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author aluno
 */
public class Tipo_elenco {
    private int ID_TipoElenco;
    private String descricao_elenco;

    public Tipo_elenco() {
    }

    public Tipo_elenco(int ID_TipoElenco, String descricao_elenco) {
        this.ID_TipoElenco = ID_TipoElenco;
        this.descricao_elenco = descricao_elenco;
    }

   

    public int getID_TipoElenco() {
        return ID_TipoElenco;
    }

    public void setID_TipoElenco(int ID_TipoElenco) {
        this.ID_TipoElenco = ID_TipoElenco;
    }

    public String getDescricao_elenco() {
        return descricao_elenco;
    }

    public void setDescricao_elenco(String descricao_elenco) {
        this.descricao_elenco = descricao_elenco;
    }

    @Override
    public String toString() {
        return "Tipo_elenco{" + "ID_TipoElenco=" + ID_TipoElenco + ", descricao_elenco=" + descricao_elenco + '}';
    }

   

    
   
}