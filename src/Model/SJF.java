package Model;

import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class SJF extends Processador {

    public SJF(int nNúcleos, int nProcessos, int quantum) {
        super(nNúcleos, nProcessos, quantum);
        ordenarAptos();
        preencherNucleos();
    }

    public SJF(int nNúcleos, int nProcessos) {
        super(nNúcleos, nProcessos, 0);
        ordenarAptos();
        preencherNucleos();
    }


    private void ordenarAptos() {
        Collections.sort(getAptos());
    }

    //TODO É melhor deixar essa lógica na thread
    private void preencherNucleos() {
        for (int r = 0; r < getNúcleos().size(); r++) {
            //Adiciona o primeiro processo da fila de aptos no núcleo
            getNúcleos().get(r).setProcesso(getAptos().get(0));
            //Remove o dito processo da fila de aptos
            getAptos().remove(0);
        }
    }

    //Implementação de Thread característica do algoritmo
    @Override
    public void run() {
        Timer timerSJF = new Timer();
        timerSJF.schedule(new TimerTask() {
            @Override
            public void run() {
                ordenarAptos();
                for (int r = 0; r < getNúcleos().size(); r++) {
                    //Checa se o núcleo está preenchdio
                    if (getNúcleos().get(r).getProcesso() != null) {
                        //Checa se o processo acabou
                        if (getNúcleos().get(r).getProcesso().getTempoRestante() <= 0) {
                            //Ainda tem processos nos aptos
                            if (getAptos().size() != 0) {
                                //Preenche com o primeiro processo na lista de aptos
                                getNúcleos().get(r).setProcesso(getAptos().get(0));
                                //Remove o dito processo da lista de aptos
                                getAptos().remove(0);
                            }
                        }
                    } else {
                        //Caso o núcleo esteja vazio, preenche com o primeiro processo na lista de aptos
                        getNúcleos().get(r).setProcesso(getAptos().get(0));
                        //Removo o processo que da fila de aptos
                        getAptos().remove(0);
                    }
                }
                System.out.println("Tamanho do Núcleo: " + getNúcleos().size());
                System.out.println("Tamanho do Aptos: " + getAptos().size());
                System.out.println("Tamanho do TodosProcessos: " + getTodosProcessos().size());
            }
        }, 1000, 1000);
        super.run();
    }
}
