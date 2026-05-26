/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Date;

public class Filme {
    private int ID_Filme;
    private String nome_filme;
    private int classificacao;
    private String nacionalidade;
    private Date lancamento;
    private int orcamento;
    private String diretor_principal;
    private String foto_filme;
    private String trailer;
    private String foto_filme_folder;
    private String descricao;

    public Filme(int ID_Filme, String nome_filme, int classificacao, String nacionalidade, Date lancamento, int orcamento, String diretor_principal, String foto_filme, String trailer, String foto_filme_folder, String descricao) {
        this.ID_Filme = ID_Filme;
        this.nome_filme = nome_filme;
        this.classificacao = classificacao;
        this.nacionalidade = nacionalidade;
        this.lancamento = lancamento;
        this.orcamento = orcamento;
        this.diretor_principal = diretor_principal;
        this.foto_filme = foto_filme;
        this.trailer = trailer;
        this.foto_filme_folder = foto_filme_folder;
        this.descricao = descricao;
    }

    public Filme() {
    }

    public int getID_Filme() {
        return ID_Filme;
    }

    public void setID_Filme(int ID_Filme) {
        this.ID_Filme = ID_Filme;
    }

    public String getNome_filme() {
        return nome_filme;
    }

    public void setNome_filme(String nome_filme) {
        this.nome_filme = nome_filme;
    }

    public int getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Date getLancamento() {
        return lancamento;
    }

    public void setLancamento(Date lancamento) {
        this.lancamento = lancamento;
    }

    public int getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(int orcamento) {
        this.orcamento = orcamento;
    }

    public String getDiretor_principal() {
        return diretor_principal;
    }

    public void setDiretor_principal(String diretor_principal) {
        this.diretor_principal = diretor_principal;
    }

    public String getFoto_filme() {
        return foto_filme;
    }

    public void setFoto_filme(String foto_filme) {
        this.foto_filme = foto_filme;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getFoto_filme_folder() {
        return foto_filme_folder;
    }

    public void setFoto_filme_folder(String foto_filme_folder) {
        this.foto_filme_folder = foto_filme_folder;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Filme{" + "ID_Filme=" + ID_Filme + ", nome_filme=" + nome_filme + ", classificacao=" + classificacao + ", nacionalidade=" + nacionalidade + ", lancamento=" + lancamento + ", orcamento=" + orcamento + ", diretor_principal=" + diretor_principal + ", foto_filme=" + foto_filme + ", trailer=" + trailer + ", foto_filme_folder=" + foto_filme_folder + ", descricao=" + descricao + '}';
    }
      
}
