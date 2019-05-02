package Model;

import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class SJF extends Processador {

    public SJF(int nNúcleos, int nProcessos, int quantum, int tamMemoria) {
        super(nNúcleos, nProcessos, quantum, tamMemoria);
        ordenarAptos();
        preencherNucleos();
    }

//    public SJF(int nNúcleos, int nProcessos) {
//        super(nNúcleos, nProcessos, 0);
//        ordenarAptos();
//        preencherNucleos();
//    }


    private void ordenarAptos() {
        Collections.sort(getProcessosAptos());
    }

    //TODO É melhor deixar essa lógica na thread
    private void preencherNucleos() {
        for (int r = 0; r < getNúcleos().size(); r++) {
            //Adiciona o primeiro processo da fila de aptos no núcleo
            getNúcleos().get(r).setProcesso(getProcessosAptos().get(0));
            //Aloca o processo na memória
            //TODO Checar algoritmo de memória escolhido
            memória.alocarBestFit(getNúcleos().get(r).processo);
            //Remove o dito processo da fila de aptos
            getProcessosAptos().remove(0);
        }
    }

    //Implementação de Thread característica do algoritmo
    @Override
    synchronized public void run() {
        Timer timerSJF = new Timer();
        timerSJF.schedule(new TimerTask() {
            @Override
            public void run() {
                ordenarAptos();
                for (int r = 0; r < getNúcleos().size(); r++) {
                    //Checa se o núcleo está preenchido
                    if (getNúcleos().get(r).getProcesso() != null) {
                        //Checa se o processo acabou
                        if (getNúcleos().get(r).getProcesso().getTempoRestante() <= 0) {
                            //Desaloca o processo terminado da memória
                            memória.desalocar(getNúcleos().get(r).processo);
                            //Ainda tem processos nos aptos
                            if (getProcessosAptos().size() != 0) {
                                //Caso seja possível alocar o processo
                                if (memória.alocarBestFit(getProcessosAptos().get(0))) {
                                    //Preenche com o primeiro processo na lista de aptos
                                    getNúcleos().get(r).setProcesso(getProcessosAptos().get(0));
                                    //Remove o dito processo da lista de aptos
                                    getProcessosAptos().remove(0);
                                }
                                //Não foi possível alocar o processo
                                else {
                                    //Seta o status do processo para abortado, retira da lista de aptos e adiciona na lista de processos terminados
                                    getProcessosAptos().get(0).setStatus(Status.ABORTADO);
                                    processosTerminados.add(getProcessosAptos().get(0));
                                    getProcessosAptos().remove(0);
                                }
                            }
                            //O processo acabou e não tem mais processos nos aptos
                            else {
                                getNúcleos().get(r).setProcesso(null);
                            }
                        }
                    } else {
                        if (getProcessosAptos().size() != 0) {
                            //Preenche com o primeiro processo na lista de aptos
                            getNúcleos().get(r).setProcesso(getProcessosAptos().get(0));
                            //Aloca o processo na memória
                            memória.alocarBestFit(getNúcleos().get(r).processo);
                            //Remove o dito processo da lista de aptos
                            getProcessosAptos().remove(0);
                        }
//                        //Caso o núcleo esteja vazio, preenche com o primeiro processo na lista de aptos
//                        getNúcleos().get(r).setProcesso(getProcessosAptos().get(0));
//                        //Removo o processo que da fila de aptos
//                        getProcessosAptos().remove(0);
                    }
                }
            }
        }, 1000, 10);
        super.run();
    }
}
