/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author aluno
 */
public class Filme_genero {
    private int ID_Filme_Genero;
    private Genero ID_Genero; 
    private Filme ID_Filme;

    public Filme_genero() {
    }

    public Filme_genero(int ID_Filme_Genero, Genero ID_Genero, Filme ID_Filme) {
        this.ID_Filme_Genero = ID_Filme_Genero;
        this.ID_Genero = ID_Genero;
        this.ID_Filme = ID_Filme;
    }

    public int getID_Filme_Genero() {
        return ID_Filme_Genero;
    }

    public void setID_Filme_Genero(int ID_Filme_Genero) {
        this.ID_Filme_Genero = ID_Filme_Genero;
    }

    public Genero getID_Genero() {
        return ID_Genero;
    }

    public void setID_Genero(Genero ID_Genero) {
        this.ID_Genero = ID_Genero;
    }

    public Filme getID_Filme() {
        return ID_Filme;
    }

    public void setID_Filme(Filme ID_Filme) {
        this.ID_Filme = ID_Filme;
    }

    @Override
    public String toString() {
        return "Filme_Genero{" + "ID_Filme_Genero=" + ID_Filme_Genero + ", [" + ID_Genero.toString() + "], ID_Filme" + ID_Filme + '}';
    }
    
}
