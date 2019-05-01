package Model;

import java.util.ArrayList;
//import java.util.Timer;
//import java.util.TimerTask;

public class Memoria
//        extends TimerTask
{
    private Integer tamanhoTotal;
    private Integer tamanhoLivre;
    private ArrayList<Particão> particoes = new ArrayList<>();

//    TimerTask memTempo = new TimerTask() {
//        @Override
//        public void run() {
//            System.out.println("ok");
//        }
//    };

    public Memoria(Integer tamanhoTotal) {
        this.tamanhoTotal = tamanhoTotal;
        this.tamanhoLivre = tamanhoTotal;
    }

    public Particão getParticãobyId(int id) {
        for (Particão particao : particoes) {
            if (particao.getId() == id) {
                return particao;
            }
        }
        return null;
    }


    public void addParticão(int tamanho) {
        if (tamanho <= tamanhoLivre) {
            Particão part = new Particão(tamanho);
            this.particoes.add(part);
            tamanhoLivre -= tamanho;
        } else {
            System.out.println("Erro limite da memoria");
        }
    }

    public void addParticão(int tamanho, int id) {
        if (tamanho <= tamanhoLivre) {
            Particão part = new Particão(tamanho, id);
            this.particoes.add(part);
            tamanhoLivre -= tamanho;
        } else {
            System.out.println("Erro limite da memoria");
        }
    }

    public void removeParticãobyId(int id) {
        for (Particão particao : particoes) {
            if (particao.getId() == id) {
                particoes.remove(particao);
            }
        }
    }


    public void removeParticão(Particão particão) {
        particoes.remove(particão);
    }


    public void mergeParticao(Particão a, Particão b) {

        removeParticão(b);
        changeSizeofParticão(a, a.getTamanho() + b.getTamanho());

    }

    public void addProcessoNaParticao(Processo processo, Particão particão) {

        for (Particão parti : particoes) {
            if (particão == parti) {
                parti.setProcesso(processo);
            }
        }

    }

    public void removeProcessoDaParticao(Particão particão) {
        for (Particão parti : particoes) {
            if (particão == parti) {
                parti.setProcesso(null);
            }
        }

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


//    @Override
//    synchronized public void run() {
//
//    }



    @Override
    public String toString() {
        return "Memoria{" +
                "tamanhoTotal=" + tamanhoTotal +
                ", tamanhoLivre=" + tamanhoLivre +
                ", particoes=" + particoes +
                '}';
    }

}

