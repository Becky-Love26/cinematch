/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author aluno
 */
public class Atuacao_elenco {
    private int ID_atuacaoelenco;
    private int ID_Filme;
    private int ID_TipoElenco;
    private int ID_Elenco;

    public Atuacao_elenco() {
    }

    public Atuacao_elenco(int ID_atuacaoelenco, int ID_Filme, int ID_TipoElenco, int ID_Elenco) {
        this.ID_atuacaoelenco = ID_atuacaoelenco;
        this.ID_Filme = ID_Filme;
        this.ID_TipoElenco = ID_TipoElenco;
        this.ID_Elenco = ID_Elenco;
    }

    public int getID_atuacaoelenco() {
        return ID_atuacaoelenco;
    }

    public void setID_atuacaoelenco(int idAtuacaoelenco) {
        this.ID_atuacaoelenco = idAtuacaoelenco;
    }

    public int getID_Filme() {
        return ID_Filme;
    }

    public void setID_Filme(int ID_Filme) {
        this.ID_Filme = ID_Filme;
    }

    public int getID_TipoElenco() {
        return ID_TipoElenco;
    }

    public void setIdTipoelenco(int ID_TipoElenco) {
        this.ID_TipoElenco = ID_TipoElenco;
    }

    public int getID_Elenco() {
        return ID_Elenco;
    }

    public void setID_Elenco(int ID_Elenco) {
        this.ID_Elenco = ID_Elenco;
    }

    @Override
    public String toString() {
        return "Atuacao_elenco{" + "ID_atuacaoelenco=" + ID_atuacaoelenco + ", ID_Filme=" + ID_Filme + ", ID_TipoElenco=" + ID_TipoElenco + ", ID_Elenco=" + ID_Elenco + '}';
    }
   
}