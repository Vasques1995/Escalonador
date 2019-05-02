package Model;

public class Bloco {
    Integer identificador;
    Integer espaçoTotal;
    Integer espaçoUsado;
    Integer idProcesso;


    public Bloco(int identificador, int espaçoTotal, int espaçoUsado) {
        this.identificador = identificador;
        this.espaçoTotal = espaçoTotal;
        this.espaçoUsado = espaçoUsado;
    }

    public Bloco(Integer identificador, Integer espaçoTotal, Integer espaçoUsado, Integer idProcesso) {
        this.identificador = identificador;
        this.espaçoTotal = espaçoTotal;
        this.espaçoUsado = espaçoUsado;
        this.idProcesso = idProcesso;
    }

    boolean isOcupado() {
        if (espaçoUsado != 0)
            return true;
        return false;
    }

    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }

    public Integer getEspaçoTotal() {
        return espaçoTotal;
    }

    public void setEspaçoTotal(Integer espaçoTotal) {
        this.espaçoTotal = espaçoTotal;
    }

    public Integer getEspaçoUsado() {
        return espaçoUsado;
    }

    public void setEspaçoUsado(Integer espaçoUsado) {
        this.espaçoUsado = espaçoUsado;
    }

    public Integer getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Integer idProcesso) {
        this.idProcesso = idProcesso;
    }

    int getEspaçoLivre(){
        return espaçoTotal - espaçoUsado;
    }
}
