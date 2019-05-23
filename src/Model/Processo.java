package Model;

import java.util.Random;

public class Processo implements Comparable<Processo> {
    private String nome;
    private Integer id;
    private Status status;
    private Prioridade prioridade;
    private String descrição;
    //Tempo total que levaria para executar o processo, considerando que houve uma pré-execução para determinar esse tempo. Perguntar Érico.
    private Integer tempoTotal;
    private Integer tempoRestante;
    private Integer tempoExecutando = 0;
    private Integer qtdBytesTotal;
    private Integer qtdBytesOriginal;
    private boolean novo = false;

    public Integer getQtdBytesOriginal() {
        return qtdBytesOriginal;
    }

    public void setQtdBytesOriginal(Integer qtdBytesOriginal) {
        this.qtdBytesOriginal = qtdBytesOriginal;
    }

    public Processo(int id) {
        this.id = id;
        this.setarNomeDescrição();
        this.status = Status.PRONTO;
        this.prioridade = randomPrioridade();
        this.tempoTotal = randomTempoTotal();
        this.tempoRestante = tempoTotal;
        //Random entre 32 e 1024
        this.qtdBytesTotal = new Random().nextInt(993) + 32;
        this.qtdBytesOriginal = qtdBytesTotal;
    }

    public Integer getQtdBytesTotal() {
        return qtdBytesTotal;
    }

    public void setQtdBytesTotal(Integer qtdBytesTotal) {
        this.qtdBytesTotal = qtdBytesTotal;
    }

    private static Prioridade randomPrioridade() {
        int randomica = new Random().nextInt(3) + 1;
        switch (randomica) {
            case 1:
                return Prioridade.BAIXA;
            case 2:
                return Prioridade.NORMAL;
            case 3:
                return Prioridade.ALTA;
        }
        return null;
    }

    private static Integer randomTempoTotal() {
        Integer random = new Random().nextInt(17) + 4;
        return random;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }

    private void setarNomeDescrição() {
        String[][] matrizString = {
                {"Chrome", "Browser"},
                {"IntelliJ", "IDE"},
                {"Blitz", "eSports"},
                {"Discord", "VoiceChat"},
                {"LightShot", "Screenshot"},
                {"Word", "Editor de texto"},
                {"Xbox", "MasterRace"},
                {"Wargroove", "Turn Strategy"},
                {"Halo Wars", "RTS"},
                {"Halo Wars 02", "RTS"},
                {"Mario 64", "GOAT"}
        };
        int escolha = new Random().nextInt(matrizString.length);
        this.nome = matrizString[escolha][0];
        this.descrição = matrizString[escolha][1];
    }

    void zerarTempo() {
        this.tempoExecutando = 0;
    }

    void incrementarTempo(int segundos) {
        this.tempoExecutando += segundos;
    }

    @Override
    public String toString() {
        return "Processo{" +
                "nome='" + nome + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", prioridade=" + prioridade +
                ", descrição='" + descrição + '\'' +
                ", tempoTotal=" + tempoTotal +
                ", tempoRestante=" + tempoRestante +
                '}';
    }

    @Override
    public int compareTo(Processo processo) {
        //Compara os tempo totais para decidir qual processo é maior
        return (Integer.compare(this.getTempoTotal(), processo.getTempoTotal()));
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public String getDescrição() {
        return descrição;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

    public Integer getTempoTotal() {
        return tempoTotal;
    }

    public void setTempoTotal(Integer tempoTotal) {
        this.tempoTotal = tempoTotal;
    }

    public Integer getTempoRestante() {
        return tempoRestante;
    }

    public void setTempoRestante(Integer tempoRestante) {
        this.tempoRestante = tempoRestante;
    }

    public Integer getTempoExecutando() {
        return tempoExecutando;
    }

    public void setTempoExecutando(Integer tempoExecutando) {
        this.tempoExecutando = tempoExecutando;
    }

}
