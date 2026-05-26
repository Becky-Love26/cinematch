/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author aluno
 */
public class Genero {
        private int ID_Genero;
        private String nome_genero; 
        private String descricao;

    public Genero(int ID_Genero, String nome_genero, String descricao) {
        this.ID_Genero = ID_Genero;
        this.nome_genero = nome_genero;
        this.descricao = descricao;
    }

    public Genero() {
    }

    public int getID_Genero() {
        return ID_Genero;
    }

    

    public void setID_Genero(int ID_Genero) {
        this.ID_Genero = ID_Genero;
    }

    public String getNome_genero() {
        return nome_genero;
    }

    public void setNome_genero(String nome_genero) {
        this.nome_genero = nome_genero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    @Override
    public String toString() {
        return "Genero{" + "ID_Genero=" + ID_Genero + ", nome_genero=" + nome_genero + ", descricao=" + descricao + '}';
    }
        
}
