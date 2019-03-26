package Model;

import java.util.Timer;
import java.util.TimerTask;

public class FilaPrioridade extends Processador {
    public FilaPrioridade(int nNúcleos, int nProcessos, int quantum) {
        super(nNúcleos, nProcessos, quantum);
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
    public void run() {
        Timer timerRR = new Timer();
        timerRR.schedule(new TimerTask() {
            @Override
            public void run() {
                //Percorrer os núcleos
                boolean achouProcesso;
                for (int r = 0; r < getNúcleos().size(); r++) {
                    achouProcesso = false;
                    //TODO Preencher núcleos vazios está correto
                    if (getNúcleos().get(r).processo == null) {
                        //Percorro a lista de aptos
                        for (int v = 0; v < getAptos().size(); v++) {
//                            Vai buscar na fila de aptos o processo com a prioridade controle
                            if (getAptos().get(v).getPrioridade() == controle) {
                                achouProcesso = true;
                                //Achou o processo com prioridade correta, adiciona ao núcleo e remove dos Aptos
                                getNúcleos().get(r).setProcesso(getAptos().get(v));
                                getAptos().remove(v);
                                iteraControle();
                                //Saio do Loop
                                v = getAptos().size();
                            }
                            //Não achei o processo com a prioridade especificada
                            else if (v == getAptos().size() - 1) {
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
                            //Checa se ainda existem processos na lista de aptos
                            if (getAptos().size() != 0) {
                                for (int v = 0; v < getAptos().size(); v++) {
                                    //Vai buscar na fila de aptos o processo com a prioridade controle
                                    if (getAptos().get(v).getPrioridade() == controle) {
                                        achouProcesso = true;
                                        //Achou o processo com prioridade correta, adiciona ao núcleo e remove dos Aptos
                                        getNúcleos().get(r).setProcesso(getAptos().get(v));
                                        getAptos().remove(v);
                                        iteraControle();
                                        //Saio do Loop
                                        v = getAptos().size();
                                    }
                                    //Não achei o processo com a prioridade especificada
                                    else if (v == getAptos().size() - 1) {
                                        //Mudo a prioridade e reseto a lista
                                        iteraControle();
                                        v = 0;
                                    }
                                }
                            }
                        }
                        //Processo não terminou de executar
                        else {
                            //Checa se o tempo de execução do processo alcançou o quantum
                            if (getNúcleos().get(r).processo.getTempoExecutando() >= getQuantum()) {
                                //Retorna o processo a fila de aptos
                                getNúcleos().get(r).processo.setStatus(Status.ESPERANDO);
                                getAptos().add(getNúcleos().get(r).processo);
                                if (getAptos().size() != 0) {
                                    for (int v = 0; v < getAptos().size(); v++) {
                                        //Vai buscar na fila de aptos o processo com a prioridade controle
                                        if (getAptos().get(v).getPrioridade() == controle) {
                                            achouProcesso = true;
                                            //Achou o processo com prioridade correta, adiciona ao núcleo e remove dos Aptos
                                            getNúcleos().get(r).setProcesso(getAptos().get(v));
                                            getAptos().remove(v);
                                            iteraControle();
                                            //Saio do Loop
                                            v = getAptos().size();
                                        }
                                        //Não achei o processo com a prioridade especificada
                                        else if (v == getAptos().size() - 1) {
                                            //Mudo a prioridade e reseto a lista
                                            iteraControle();
                                            v = 0;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, 1000, 1000);
        super.run();
    }

    public Prioridade getControle() {
        return controle;
    }

    public void setControle(Prioridade controle) {
        this.controle = controle;
    }
}
