package modelo;

import java.util.Date;

public class Usuario {
    private int ID_usuario;
    private String senha;
    private String email;
    private String CPF;
    private String telefone;
    private Date data_nascimento;
    private String sexo;
    private int status_usuario;
    private String nome;
    private Tipo_usuario ID_TipoUsuario;

    public Usuario() {
    }

    public Usuario(int ID_usuario, String senha, String email, String CPF, String telefone, Date data_nascimento, String sexo, int status_usuario, String nome, Tipo_usuario ID_TipoUsuario) {
        this.ID_usuario = ID_usuario;
        this.senha = senha;
        this.email = email;
        this.CPF = CPF;
        this.telefone = telefone;
        this.data_nascimento = data_nascimento;
        this.sexo = sexo;
        this.status_usuario = status_usuario;
        this.nome = nome;
        this.ID_TipoUsuario = ID_TipoUsuario;
    }

    public int getID_usuario() {
        return ID_usuario;
    }

    public void setID_usuario(int ID_usuario) {
        this.ID_usuario = ID_usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getStatus_usuario() {
        return status_usuario;
    }

    public void setStatus_usuario(int status_usuario) {
        this.status_usuario = status_usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tipo_usuario getID_TipoUsuario() {
        return ID_TipoUsuario;
    }

    public void setID_TipoUsuario(Tipo_usuario ID_TipoUsuario) {
        this.ID_TipoUsuario = ID_TipoUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" + "ID_usuario=" + ID_usuario + ", senha=" + senha + ", email=" + email + ", CPF=" + CPF + ", telefone=" + telefone + ", data_nascimento=" + data_nascimento + ", sexo=" + sexo + ", status_usuario=" + status_usuario + ", nome=" + nome + ", ID_TipoUsuario=" + ID_TipoUsuario + '}';
    }

    

    
   
}
