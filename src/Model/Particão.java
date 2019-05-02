package Model;

import java.util.concurrent.atomic.AtomicInteger;

public class Particão {
    private Integer tamanho;
    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;
    private Boolean uso;
    private Processo processo;


    public Particão(Integer tamanho) {
        this.tamanho = tamanho;
        id = count.getAndIncrement();

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
