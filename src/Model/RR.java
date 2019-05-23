package Model;

import java.util.Timer;
import java.util.TimerTask;

public class RR extends Processador {
    public RR(int nNúcleos, int nProcessos, int quantum, int tamMemoria, String algoritmoMemoria) {
        super(nNúcleos, nProcessos, quantum, tamMemoria, algoritmoMemoria);
    }

    //TODO É melhor deixar essa lógica na thread
    void preencherNucleos() {
        for (int r = 0; r < getNúcleos().size(); r++) {
            //Adiciona o primeiro processo da fila de aptos no núcleo
//            getNúcleos().get(r).setProcesso(getProcessosAptos().get(0));
//            //Remove o dito processo da fila de aptos
//            getProcessosAptos().remove(0);

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
                            if (memória.alocar(getProcessosAptos().get(0))) {
                                //TODO Caso eu possa alocar
                                //Caso o núcleo esteja vazio e ainda tenha processos na lista de aptos, insiro o primeiro processo da fila de aptos
                                getNúcleos().get(r).setProcesso(getProcessosAptos().get(0));
                                //Removo o processo da fila de aptos
                                getProcessosAptos().remove(0);
                            }
                            //TODO Caso eu não consiga alocar
                            else {
                                getProcessosAptos().get(0).setStatus(Status.ABORTADO);
                                processosTerminados.add(getProcessosAptos().get(0));
                                getProcessosAptos().remove(0);
                            }
                        }
                    }
                    //Caso o nucleo esteja preenchido
                    else {
                        //Checa se o processo já terminou de executar
                        if (getNúcleos().get(r).processo.getTempoRestante() <= 0) {
                            //Desaloca o processo terminado da memória
                            memória.desalocar(getNúcleos().get(r).processo);
                            //Insere o primeiro processo da fila de aptos
                            if (getProcessosAptos().size() != 0) {
                                //TODO Checa se posso alocar
                                //TODO É AQUI TAFAREL
                                if (memória.containsProcesso(getProcessosAptos().get(0)) != -1) {
                                    //TODO EU POSSUO O IDENTIFICADOR DO BLOCO
                                    //Insiro o primeiro processo da fila de aptos
                                    getNúcleos().get(r).setProcesso(getProcessosAptos().get(0));
                                    //Removo o processo da fila de aptos
                                    getProcessosAptos().remove(0);
//                                        memória.alocar(getProcessosAptos().get(0));
                                } else {
                                    //Ou seja o processo não existe na lista de blocos
                                    //TODO Checa se posso alocar
                                    if (memória.alocar(getProcessosAptos().get(0))) {
                                        //Insiro o primeiro processo da fila de aptos
                                        getNúcleos().get(r).setProcesso(getProcessosAptos().get(0));
                                        //Removo o processo da fila de aptos
                                        getProcessosAptos().remove(0);
                                    } else {
                                        getProcessosAptos().get(0).setStatus(Status.ABORTADO);
                                        processosTerminados.add(getProcessosAptos().get(0));
                                        getProcessosAptos().remove(0);
                                    }
                                }
//                                if (memória.alocar(getProcessosAptos().get(0))) {
//                                    //Preenche com o primeiro processo na lista de aptos
//                                    getNúcleos().get(r).setProcesso(getProcessosAptos().get(0));
//                                    //Remove da lista de Aptos
//                                    getProcessosAptos().remove(0);
//                                } else {
//                                    //TODO Caso eu não possa alocar
//                                    getProcessosAptos().get(0).setStatus(Status.ABORTADO);
//                                    processosTerminados.add(getProcessosAptos().get(0));
//                                    getProcessosAptos().remove(0);
//                                }
                            }
                            //TODO Não existem processos na fila de aptos e o processo que estava no núcleo acabou
                            else {
                                //Removo o processo que estiver no núcleo
//                                getNúcleos().get(r).processo.setStatus(Status.ABORTADO);
//                                getProcessosAptos().get(0).setStatus(Status.ABORTADO);
//                                processosTerminados.add(getNúcleos().get(r).getProcesso());
                                memória.desalocar(getNúcleos().get(r).getProcesso());
                                getNúcleos().get(r).setProcesso(null);
                            }
                        }
                        //Processo não terminou de executar
                        else {
                            //Checa se o tempo de execução do processo alcançou o quantum
                            if (getNúcleos().get(r).processo.getTempoExecutando() >= getQuantum()) {
                                //TODO Processo que estava no núcleo alcançou o Quantum - Retirar ele do núcleo, adicionar a lista de aptos e mudar o status
                                //TODO ERRO AQUI EM ALGUM LUGAR, PROCESSOS NOVOS NÃO ESTÃO SENDO INSERIDOS EM BLOCOS OU ESTÃO CRIANDO BLOCOS DUPLICADOS
                                if (getProcessosAptos().size() != 0) {
                                    //Retorna o processo a fila de aptos
                                    getNúcleos().get(r).processo.setStatus(Status.ESPERANDO);
                                    getProcessosAptos().add(getNúcleos().get(r).processo);
//                                    //TODO Checa se o processo da lista de aptos já existe na lista de blocos
                                    if (memória.containsProcesso(getProcessosAptos().get(0)) != -1) {
                                        //TODO EU POSSUO O IDENTIFICADOR DO BLOCO
                                        //Insiro o primeiro processo da fila de aptos
                                        getNúcleos().get(r).setProcesso(getProcessosAptos().get(0));
                                        //Removo o processo da fila de aptos
                                        getProcessosAptos().remove(0);
//                                        memória.alocar(getProcessosAptos().get(0));
                                    } else {
                                        //Ou seja o processo não existe na lista de blocos
                                        //TODO Checa se posso alocar
                                        if (memória.alocar(getProcessosAptos().get(0))) {
                                            //Insiro o primeiro processo da fila de aptos
                                            getNúcleos().get(r).setProcesso(getProcessosAptos().get(0));
                                            //Removo o processo da fila de aptos
                                            getProcessosAptos().remove(0);
                                        } else {
                                            getProcessosAptos().get(0).setStatus(Status.ABORTADO);
                                            processosTerminados.add(getProcessosAptos().get(0));
                                            getProcessosAptos().remove(0);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, 1000, 200);
        super.run();
    }
}
