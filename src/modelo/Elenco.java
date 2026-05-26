/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author aluno
 */
public class Elenco {
    private int ID_Elenco;
    private String nome; 
    private String foto_elenco;

    public Elenco() {
    }

    public Elenco(int ID_Elenco, String nome, String foto_elenco) {
        this.ID_Elenco = ID_Elenco;
        this.nome = nome;
        this.foto_elenco = foto_elenco;
    }

    public int getID_Elenco() {
        return ID_Elenco;
    }

    public void setID_Elenco(int ID_Elenco) {
        this.ID_Elenco = ID_Elenco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFoto_elenco() {
        return foto_elenco;
    }

    public void setFoto_elenco(String foto_elenco) {
        this.foto_elenco = foto_elenco;
    }

    @Override
    public String toString() {
        return "Elenco{" + "ID_Elenco=" + ID_Elenco + ", nome=" + nome + ", foto_elenco=" + foto_elenco + '}';
    }
    
}


