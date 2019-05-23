package Model;

import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class SJF extends Processador {

    public SJF(int nNúcleos, int nProcessos, int quantum, int tamMemoria, String algoritmoMemoria) {
        super(nNúcleos, nProcessos, quantum, tamMemoria, algoritmoMemoria);
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
            //Checa se é possível adicionar o processo no núcleo
            if (memória.alocar(getProcessosAptos().get(0))) {
                //Adiciona o primeiro processo da fila de aptos no núcleo
                getNúcleos().get(r).setProcesso(getProcessosAptos().get(0));
                //Remove o dito processo da fila de aptos
                getProcessosAptos().remove(0);
            } else {
                //Remover da fila de aptos, setar para abortar e adicionar na fila de terminados
                getProcessosAptos().get(0).setStatus(Status.ABORTADO);
                processosTerminados.add(getProcessosAptos().get(0));
                getProcessosAptos().remove(0);
            }
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
                    //TODO Problema do SJF, quando múltiplos núcleos( >= 3) estão sendo utilizados a alocação não funciona corretamente
                    //TODO Aparece um erro Timer-O NPE
                    //Checa se o núcleo está preenchido
                    if (getNúcleos().get(r).getProcesso() != null) {
                        //Checa se o processo acabou
                        if (getNúcleos().get(r).getProcesso().getTempoRestante() <= 0) {
                            //Desaloca o processo terminado da memória
                            memória.desalocar(getNúcleos().get(r).processo);
                            //Ainda tem processos nos aptos
                            if (getProcessosAptos().size() != 0) {
                                //Caso seja possível alocar o processo
                                if (memória.alocar(getProcessosAptos().get(0))) {
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
                            if (memória.alocar(getProcessosAptos().get(0))) {
                                //Preenche com o primeiro processo na lista de aptos
                                getNúcleos().get(r).setProcesso(getProcessosAptos().get(0));
                                //Remove o dito processo da lista de aptos
                                getProcessosAptos().remove(0);
                            } else {
                                getProcessosAptos().get(0).setStatus(Status.ABORTADO);
                                processosTerminados.add(getProcessosAptos().get(0));
                                getProcessosAptos().remove(0);
                            }
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
