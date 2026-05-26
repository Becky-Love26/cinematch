/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;


public class Perfil {
    private int ID_Perfil;
    private String  nome_perfil;
    private int classificacao_perfil;
   
    private Usuario ID_usuario;
    private String foto_perfil;

    public Perfil() {
    }

    public Perfil(int ID_Perfil, String nome_perfil, int classificacao_perfil, Usuario ID_usuario, String foto_perfil) {
        this.ID_Perfil = ID_Perfil;
        this.nome_perfil = nome_perfil;
        this.classificacao_perfil = classificacao_perfil;
        this.ID_usuario = ID_usuario;
        this.foto_perfil = foto_perfil;
    }

    public int getID_Perfil() {
        return ID_Perfil;
    }

    public void setID_Perfil(int ID_Perfil) {
        this.ID_Perfil = ID_Perfil;
    }

    public String getNome_perfil() {
        return nome_perfil;
    }

    public void setNome_perfil(String nome_perfil) {
        this.nome_perfil = nome_perfil;
    }

    public int getClassificacao_perfil() {
        return classificacao_perfil;
    }

    public void setClassificacao_perfil(int classificacao_perfil) {
        this.classificacao_perfil = classificacao_perfil;
    }

    public Usuario getID_usuario() {
        return ID_usuario;
    }

    public void setID_usuario(Usuario ID_usuario) {
        this.ID_usuario = ID_usuario;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    @Override
    public String toString() {
        return "Perfil{" + "ID_Perfil=" + ID_Perfil + ", nome_perfil=" + nome_perfil + ", classificacao_perfil=" + classificacao_perfil + ", ID_usuario=" + ID_usuario + ", foto_perfil=" + foto_perfil + '}';
    }
           
           
}