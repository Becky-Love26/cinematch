package modelo;


public class Plano {
    private int ID_plano;
    private float valor;
    private String tipo_plano;

    public Plano() {
    }

    public Plano(int ID_plano, float valor, String tipo_plano) {
        this.ID_plano = ID_plano;
        this.valor = valor;
        this.tipo_plano = tipo_plano;
    }

    public int getID_plano() {
        return ID_plano;
    }

    public void setID_plano(int ID_plano) {
        this.ID_plano = ID_plano;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getTipo_plano() {
        return tipo_plano;
    }

    public void setTipo_plano(String tipo_plano) {
        this.tipo_plano = tipo_plano;
    }

    @Override
    public String toString() {
        return "Plano{" + "ID_plano=" + ID_plano + ", valor=" + valor + ", tipo_plano=" + tipo_plano + '}';
    }
   
}
