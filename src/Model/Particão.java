package Model;

public class Particão {
    private Integer tamanho;
    private Integer id;
    private Boolean uso;
    private Processo processo;


    public Particão(Integer tamanho, Integer id) {
        this.tamanho = tamanho;
        this.id = id;
    }

    public Particão(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getUso() {
        return uso;
    }

    public void setUso(Boolean uso) {
        this.uso = uso;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

//    public int usedSpace() {
//        return this.tamanho - processo.getSize;
//    }
//

    @Override
    public String toString() {
        return "\nParticão{" +
                "tamanho=" + tamanho +
                ", id=" + id +
                ", uso=" + uso +
                ", processo=" + processo +
                '}';
    }
}
