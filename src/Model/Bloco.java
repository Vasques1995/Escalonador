package Model;

public class Bloco {
    int identificador;
    int espaçoTotal;
    int espaçoUsado;
    Bloco nextBloco;


    public Bloco(int identificador, int espaçoTotal, int espaçoUsado) {
        this.identificador = identificador;
        this.espaçoTotal = espaçoTotal;
        this.espaçoUsado = espaçoUsado;
    }

    public Bloco(int identificador, int espaçoTotal, int espaçoUsado, Bloco nextBloco) {
        this.identificador = identificador;
        this.espaçoTotal = espaçoTotal;
        this.espaçoUsado = espaçoUsado;
        this.nextBloco = nextBloco;
    }

    public Bloco() {
    }

    boolean isOcupado() {
        if (espaçoUsado != 0)
            return true;
        return false;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public int getEspaçoTotal() {
        return espaçoTotal;
    }

    public void setEspaçoTotal(int espaçoTotal) {
        this.espaçoTotal = espaçoTotal;
    }

    public int getEspaçoUsado() {
        return espaçoUsado;
    }

    public void setEspaçoUsado(int espaçoUsado) {
        this.espaçoUsado = espaçoUsado;
    }

    public Bloco getNextBloco() {
        return nextBloco;
    }

    public void setNextBloco(Bloco nextBloco) {
        this.nextBloco = nextBloco;
    }
}
