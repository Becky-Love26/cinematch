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
public class Filme_avaliados {

    private int ID_Avaliacao;
    private String resenha;
    private int estrelas;
    private Date data_avaliacao;

    private Filme ID_Filme;
    private Perfil ID_Perfil;

    public Filme_avaliados() {
    }

    public Filme_avaliados(int ID_Avaliacao, String resenha, int estrelas, Date dataAvaliacao, Filme ID_Filme, Perfil ID_Perfil) {
        this.ID_Avaliacao = ID_Avaliacao;
        this.resenha = resenha;
        this.estrelas = estrelas;
        this.data_avaliacao = dataAvaliacao;
        this.ID_Filme = ID_Filme;
        this.ID_Perfil = ID_Perfil;
    }

    public int getID_Avaliacao() {
        return ID_Avaliacao;
    }

    public void setID_Avaliacao(int ID_Avaliacao) {
        this.ID_Avaliacao = ID_Avaliacao;
    }

    public String getResenha() {
        return resenha;
    }

    public void setResenha(String resenha) {
        this.resenha = resenha;
    }

    public int getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(int estrelas) {
        this.estrelas = estrelas;
    }

    public Date getDataAvaliacao() {
        return data_avaliacao;
    }

    public void setDataAvaliacao(Date dataAvaliacao) {
        this.data_avaliacao = data_avaliacao;
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

    @Override
    public String toString() {
        return "Filmes_avaliados{" +
                "ID_Avaliacao=" + ID_Avaliacao +
                ", resenha='" + resenha + '\'' +
                ", estrelas=" + estrelas +
                ", dataAvaliacao=" + data_avaliacao +
                ", ID_Filme=" + ID_Filme +
                ", ID_Perfil=" + ID_Perfil +
                '}';
    }
}