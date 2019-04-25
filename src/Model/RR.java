package Model;

import java.util.Timer;
import java.util.TimerTask;

public class RR extends Processador {
    public RR(int nNúcleos, int nProcessos, int quantum) {
        super(nNúcleos, nProcessos, quantum);
    }

    //TODO É melhor deixar essa lógica na thread
    void preencherNucleos() {
        for (int r = 0; r < getNúcleos().size(); r++) {
            //Adiciona o primeiro processo da fila de aptos no núcleo
            getNúcleos().get(r).setProcesso(getProcessosAptos().get(0));
            //Remove o dito processo da fila de aptos
            getProcessosAptos().remove(0);
        }
    }

    @Override
    synchronized public void run() {
        Timer timerRR = new Timer();
        timerRR.schedule(new TimerTask() {
            @Override
            public void run() {
                //Percorrer os núcleos
                for (int r = 0; r < getNúcleos().size(); r++) {
                    //Se o núcleo estiver vazio
                    if (getNúcleos().get(r).processo == null) {
                        if (getProcessosAptos().size() != 0) {
                            //Caso o núcleo esteja vazio, insiro o primeiro processo da fila de aptos
                            getNúcleos().get(r).setProcesso(getProcessosAptos().get(0));
                            //Removo o processo da fila de aptos
                            getProcessosAptos().remove(0);
                        }
                    }
                    //Caso o nucleo não esteja vazio
                    else {
                        //Checa se o processo já terminou de executar
                        if (getNúcleos().get(r).processo.getTempoRestante() <= 0) {
                            //Insere o primeiro processo da fila de aptos
                            if (getProcessosAptos().size() != 0) {
                                //Preenche com o primeiro processo na lista de aptos
                                getNúcleos().get(r).setProcesso(getProcessosAptos().get(0));
                                //Remove da lista de Aptos
                                getProcessosAptos().remove(0);
                            } else {
                                //Removo o processo que estiver no núcleo
                                getNúcleos().get(r).setProcesso(null);
                            }
                        }
                        //Processo não terminou de executar
                        else {
                            //Checa se o tempo de execução do processo alcançou o quantum
                            if (getNúcleos().get(r).processo.getTempoExecutando() >= getQuantum()) {
//                          Retorna o processo a fila de aptos
                                getNúcleos().get(r).processo.setStatus(Status.ESPERANDO);
                                getProcessosAptos().add(getNúcleos().get(r).processo);
                                //Insiro o primeiro processo da fila de aptos
                                getNúcleos().get(r).setProcesso(getProcessosAptos().get(0));
                                //Removo o processo da fila de aptos
                                getProcessosAptos().remove(0);
                            }
                        }
                    }
                }
            }
        }, 1000, 10);
        super.run();
    }
}
