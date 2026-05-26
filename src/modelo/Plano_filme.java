/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class Plano_filme {

    private int ID_Plano_Filme;
    private Filme ID_Filme;
    private Plano ID_Plano;

    public Plano_filme() {
    }

    public Plano_filme(int ID_Plano_Filme, Filme ID_Filme, Plano ID_Plano) {
        this.ID_Plano_Filme = ID_Plano_Filme;
        this.ID_Filme = ID_Filme;
        this.ID_Plano = ID_Plano;
    }

    public int getID_Plano_Filme() {
        return ID_Plano_Filme;
    }

    public void setID_Plano_Filme(int ID_Plano_Filme) {
        this.ID_Plano_Filme = ID_Plano_Filme;
    }

    public Filme getID_Filme() {
        return ID_Filme;
    }

    public void setID_Filme(Filme ID_Filme) {
        this.ID_Filme = ID_Filme;
    }

    public Plano getID_Plano() {
        return ID_Plano;
    }

    public void setID_Plano(Plano ID_Plano) {
        this.ID_Plano = ID_Plano;
    }

    @Override
    public String toString() {
        return "Plano_filme{" +
                "ID_Plano_Filme=" + ID_Plano_Filme +
                ", ID_Filme=" + ID_Filme +
                ", ID_Plano=" + ID_Plano +
                '}';
    }
}