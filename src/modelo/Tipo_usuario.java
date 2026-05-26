package modelo;

public class Tipo_usuario {
    private int ID_TipoUsuario;
    private String descricao_usuario;

    public Tipo_usuario() {
    }

    public Tipo_usuario(int ID_TipoUsuario, String descricao_usuario) {
        this.ID_TipoUsuario = ID_TipoUsuario;
        this.descricao_usuario = descricao_usuario;
    }

    public int getID_TipoUsuario() {
        return ID_TipoUsuario;
    }

    public void setID_TipoUsuario(int ID_TipoUsuario) {
        this.ID_TipoUsuario = ID_TipoUsuario;
    }

    public String getDescricao_usuario() {
        return descricao_usuario;
    }

    // CORRIGIDO: o parâmetro agora tem o nome correto
    public void setDescricao_usuario(String descricao_usuario) {
        this.descricao_usuario = descricao_usuario;
    }

    @Override
    public String toString() {
        return "ID: " + ID_TipoUsuario + " | Descrição: " + descricao_usuario;
    }
}