package Model;

import java.util.ArrayList;

public class Memoria
{
    private Integer tamanhoTotal;
    private Integer tamanhoLivre;
    private Integer tamanhoMaximo;
    protected ArrayList<Particão> particoes = new ArrayList<>();

    public Memoria(Integer tamanhoTotal) {
        this.tamanhoTotal = tamanhoTotal;
        tamanhoLivre = tamanhoTotal;
        tamanhoMaximo = 0;


    }


    public void addParticão(int tamanho) {
        if (tamanho <= tamanhoMaximo) {
            Particão part = new Particão(tamanho);
            this.particoes.add(part);
            tamanhoMaximo += tamanho;
        } else {
            System.out.println("Error, Limite de partições na memoria");
        }
    }


    public void removeParticão(Particão particão) {
        tamanhoMaximo -= particão.getTamanho();
        particoes.remove(particão);
    }


    public void mergeParticao(Particão a, Particão b) {

        removeParticão(b);
        changeSizeofParticão(a, a.getTamanho() + b.getTamanho());

    }

    public void unMergeParticao(Particão a, int size) {
        changeSizeofParticão(a, a.getTamanho() - size);
        int x = particoes.indexOf(a);

        particoes.add(x + 1, new Particão(size));

    }

    public void addProcessoNaParticao(Processo processo, Particão particão) {

        for (Particão parti : particoes) {
            if (particão == parti) {
                parti.setProcesso(processo);
                tamanhoLivre -= processo.getQtdBytes();
            }
        }

    }

    public void removeProcessoDaParticao(Particão particão) {

        addProcessoNaParticao(null, particão);

    }

    public void removeProcessoDaParticao(Processo processo) {
        for (Particão parti : particoes) {
            if (parti.getProcesso() == processo) {
                tamanhoLivre += parti.getProcesso().getQtdBytes();
                parti.setProcesso(null);

            }
        }

    }

    public void addProcessCriaParticao(Processo processo) {
        if (processo.getQtdBytes() <= tamanhoMaximo && processo.getQtdBytes() <= tamanhoLivre) {
            Particão pp = new Particão(processo.getQtdBytes());
            particoes.add(pp);
            addProcessoNaParticao(processo, pp);


            tamanhoMaximo += processo.getQtdBytes();
            tamanhoLivre += processo.getQtdBytes();
        }

    }

    public ArrayList<Particão> getParticoes() {
        return particoes;
    }

    public Integer getTamanhoTotal() {
        return tamanhoTotal;
    }

    public void setTamanhoTotal(Integer tamanhoTotal) {
        this.tamanhoTotal = tamanhoTotal;
    }

    public Integer getTamanhoLivre() {
        return tamanhoLivre;
    }

    public void resetTamanhoLivre(Integer tamanhoLivre) {
        this.tamanhoLivre = getTamanhoTotal();
    }

    public void changeSizeofParticão(Particão alvo, int tamanho) {
        for (Particão particão : particoes) {
            if (particão == alvo) {
                particão.setTamanho(tamanho);
            }
        }
    }
    @Override
    public String toString() {
        return "Memoria{" +
                "tamanhoTotal=" + tamanhoTotal +
                ", tamanhoLivre=" + tamanhoLivre +
                ", particoes=" + particoes +
                '}';
    }

}

