/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Date;

/**
 *
 * @author aluno
 */
public class Filme_assistidos {
    private int ID_Assistidos;
    private Date data_assistido;
    private Filme ID_Filme;
    private Perfil ID_Perfil;

    @Override
    public String toString() {
        return "filme_assistidos{" + "ID_Assistidos=" + ID_Assistidos + ", data_assistido=" + data_assistido + ", [" + ID_Filme.toString() + "], ID_Perfil=" + ID_Perfil + '}';
    }

    public int getID_Assistidos() {
        return ID_Assistidos;
    }

    public void setID_Assistidos(int ID_Assistidos) {
        this.ID_Assistidos = ID_Assistidos;
    }

    public Date getData_assistido() {
        return data_assistido;
    }

    public void setData_assistido(Date data_assistido) {
        this.data_assistido = data_assistido;
    }

    public Filme getID_Filme() {
        return ID_Filme;
    }

    public void setID_Filme(Filme ID_Filme) {
        this.ID_Filme = ID_Filme;
    }

    public Perfil getID_Perfil() {
        return ID_Perfil;
    }

    public void setID_Perfil(Perfil ID_Perfil) {
        this.ID_Perfil = ID_Perfil;
    }

    public Filme_assistidos(int ID_Assistidos, Date data_assistido, Filme ID_Filme, Perfil ID_Perfil) {
        this.ID_Assistidos = ID_Assistidos;
        this.data_assistido = data_assistido;
        this.ID_Filme = ID_Filme;
        this.ID_Perfil = ID_Perfil;
    }

    public Filme_assistidos() {
    }


}
