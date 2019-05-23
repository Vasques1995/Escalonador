package Model;

import java.util.Timer;
import java.util.TimerTask;

public class FilaPrioridade extends Processador {
    public FilaPrioridade(int nNúcleos, int nProcessos, int quantum, int tamMemoria, String algoritmoMemoria) {
        super(nNúcleos, nProcessos, quantum, tamMemoria, algoritmoMemoria);
    }

    Prioridade controle = Prioridade.ALTA;

    //Função auxiliar utilizada para definir a prioridade do próximo processo a ser carregado no núcleo
    private void iteraControle() {
        switch (controle) {
            case ALTA:
                this.controle = Prioridade.NORMAL;
                break;
            case NORMAL:
                this.controle = Prioridade.BAIXA;
                break;
            case BAIXA:
                this.controle = Prioridade.ALTA;
        }
    }

    //TODO Rever código para ter certeza que não estou me repetindo
    @Override
    synchronized public void run() {
        Timer timerRR = new Timer();
        timerRR.schedule(new TimerTask() {
            @Override
            public void run() {
                //Percorrer os núcleos
                boolean achouProcesso;
                for (int r = 0; r < getNúcleos().size(); r++) {
                    achouProcesso = false;
                    if (getNúcleos().get(r).processo == null) {
                        //Percorro a lista de aptos
                        for (int v = 0; v < getProcessosAptos().size(); v++) {
                            //Vai buscar na fila de aptos o processo com a prioridade controle
                            if (getProcessosAptos().get(v).getPrioridade() == controle) {
                                //TODO Adicionar clausula do contains
                                achouProcesso = true;
                                //Achou o processo com prioridade correta, adiciona ao núcleo e remove dos Aptos
                                //Verifica se eu posso alocar o processo escolhido
                                if (memória.alocar(getProcessosAptos().get(v))) {
                                    //TODO Caso eu possa alocar
                                    //Caso o núcleo esteja vazio e ainda tenha processos na lista de aptos, insiro o primeiro processo da fila de aptos
                                    getNúcleos().get(r).setProcesso(getProcessosAptos().get(v));
                                    //Removo o processo da fila de aptos
                                    getProcessosAptos().remove(v);
                                    iteraControle();
                                    //Sair do loop
                                    v = getProcessosAptos().size();
                                }
                                //TODO Caso eu não consiga alocar
                                else {
                                    getProcessosAptos().get(0).setStatus(Status.ABORTADO);
                                    processosTerminados.add(getProcessosAptos().get(0));
                                    getProcessosAptos().remove(0);
                                }

                                //TODO Não sei se está correto abortar o processo nessa situação
//                                getNúcleos().get(r).setProcesso(getProcessosAptos().get(v));
//                                getProcessosAptos().remove(v);
//                                iteraControle();
//                                //Saio do Loop
//                                v = getProcessosAptos().size();
                            }
                            //Não achei o processo com a prioridade especificada
                            else if (v == getProcessosAptos().size() - 1) {
                                //Mudo a prioridade e reseto a lista
                                iteraControle();
                                v = 0;
                            }
                        }
                    }
                    //Núcleo está preenchido
                    else {
                        //Checa se o processo já terminou de executar
                        if (getNúcleos().get(r).processo.getTempoRestante() <= 0) {
                            //TODO Desalocar processo da memória
                            memória.desalocar(getNúcleos().get(r).processo);
                            //Checa se ainda existem processos na lista de aptos
                            if (getProcessosAptos().size() != 0) {
                                for (int v = 0; v < getProcessosAptos().size(); v++) {
                                    //Vai buscar na fila de aptos o processo com a prioridade controle
                                    if (getProcessosAptos().get(v).getPrioridade() == controle) {
                                        achouProcesso = true;
                                        //Achou o processo com prioridade correta, adiciona ao núcleo e remove dos Aptos
                                        //TODO ACHEI O PROCESSO QUE VAI PARA O NÚCLEO
                                        //TODO TAFAREL, MANDA PARA A MEMÓRIA TAFAREL
                                        //Verifica se eu posso alocar o processo escolhido
                                        //Verifica se eu contenho o processo nos blocos
                                        if (memória.containsProcesso(getProcessosAptos().get(v)) != -1){
                                            //TODO EU POSSUO O IDENTIFICADOR DO BLOCO
                                            //Insiro o primeiro processo da fila de aptos
                                            getNúcleos().get(r).setProcesso(getProcessosAptos().get(v));
                                            //Removo o processo da fila de aptos
                                            getProcessosAptos().remove(v);
                                        }
                                        else {
                                            if (memória.alocar(getProcessosAptos().get(v))) {
                                                //TODO Caso eu possa alocar
                                                //Caso o núcleo esteja vazio e ainda tenha processos na lista de aptos, insiro o primeiro processo da fila de aptos
                                                getNúcleos().get(r).setProcesso(getProcessosAptos().get(v));
                                                //Removo o processo da fila de aptos
                                                getProcessosAptos().remove(v);
                                                iteraControle();
                                                //Sair do loop
                                                v = getProcessosAptos().size();
                                            }
                                            //TODO Caso eu não consiga alocar
                                            else {
                                                getProcessosAptos().get(0).setStatus(Status.ABORTADO);
                                                processosTerminados.add(getProcessosAptos().get(0));
                                                getProcessosAptos().remove(0);
                                            }
                                        }
//                                        getNúcleos().get(r).setProcesso(getProcessosAptos().get(v));
//                                        getProcessosAptos().remove(v);
//                                        iteraControle();
//                                        //Saio do Loop
//                                        v = getProcessosAptos().size();
                                    }
                                    //Não achei o processo com a prioridade especificada
                                    else if (v == getProcessosAptos().size() - 1) {
                                        //Mudo a prioridade e reseto a lista
                                        iteraControle();
                                        v = 0;
                                    }
                                }
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
                                //Retorna o processo a fila de aptos
                                if (getProcessosAptos().size() != 0) {
                                    getNúcleos().get(r).processo.setStatus(Status.ESPERANDO);
                                    getProcessosAptos().add(getNúcleos().get(r).processo);
                                    for (int v = 0; v < getProcessosAptos().size(); v++) {
                                        //Vai buscar na fila de aptos o processo com a prioridade controle
                                        if (getProcessosAptos().get(v).getPrioridade() == controle) {
                                            achouProcesso = true;
                                            //Achou o processo com prioridade correta, adiciona ao núcleo e remove dos Aptos
                                            //TODO ACHEI O PROCESSO QUE VAI PARA O NÚCLEO
                                            //TODO TAFAREL, MANDA PARA A MEMÓRIA TAFAREL
                                            //Verifica se eu posso alocar o processo escolhido
                                            if (memória.alocar(getProcessosAptos().get(v))) {
                                                //TODO Caso eu possa alocar
                                                //Caso o núcleo esteja vazio e ainda tenha processos na lista de aptos, insiro o primeiro processo da fila de aptos
                                                getNúcleos().get(r).setProcesso(getProcessosAptos().get(v));
                                                //Removo o processo da fila de aptos
                                                getProcessosAptos().remove(v);
                                                iteraControle();
                                                //Sair do loop
                                                v = getProcessosAptos().size();
                                            }
                                            //TODO Caso eu não consiga alocar
                                            else {
                                                getProcessosAptos().get(0).setStatus(Status.ABORTADO);
                                                processosTerminados.add(getProcessosAptos().get(0));
                                                getProcessosAptos().remove(0);
                                            }
//                                            achouProcesso = true;
//                                            //Achou o processo com prioridade correta, adiciona ao núcleo e remove dos Aptos
//                                            getNúcleos().get(r).setProcesso(getProcessosAptos().get(v));
//                                            getProcessosAptos().remove(v);
//                                            iteraControle();
//                                            //Saio do Loop
//                                            v = getProcessosAptos().size();
                                        }
//                                        //Não achei o processo com a prioridade especificada
//                                        else if (v == getProcessosAptos().size() - 1) {
//                                            //Mudo a prioridade e reseto a lista
//                                            iteraControle();
//                                            v = 0;
//                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, 1000, 10);
        super.run();
    }

    public Prioridade getControle() {
        return controle;
    }

    public void setControle(Prioridade controle) {
        this.controle = controle;
    }
}
