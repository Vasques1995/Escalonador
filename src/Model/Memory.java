package Model;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.LinkedList;

public class Memory extends LinkedList<Bloco> {
    int tamanhoTotalMemory;
    int espaçoLivre;
    String algoritmo;

    public Memory(int tamanhoTotalMemory, String algoritmoMemoria) {
        this.tamanhoTotalMemory = tamanhoTotalMemory;
        this.espaçoLivre = tamanhoTotalMemory;
        this.algoritmo = algoritmoMemoria;
    }

    //TODO Generalizar classe memória para best-fit / merge-fit
    //TODO Best-Fit
    boolean alocar(Processo processo) {
        if (algoritmo.equals("Best-Fit")) {
            int idBlocoAuxiliar = 0;
            int diferençaEspaço = 100000;
            int espaçoRequerido = processo.getQtdBytes();

            //Caso ainda exista espaço livre para criar um bloco
            if (processo.getQtdBytes() <= espaçoLivre) {
                //Crio um bloco
                Bloco novoBloco = new Bloco(size(), processo.getQtdBytes(), processo.getQtdBytes(), processo.getId());
                //Adiciono o bloco na memoria
                add(novoBloco);
                //Reduzo o espaço livre da memória
                espaçoLivre -= processo.getQtdBytes();
                return true;
            }
            //Caso não exista espaço livre para criar mais blocos
            else {
                //Procuro em todos os blocos pelo que possuam espaço livre mais próximo da requisição
                for (int r = 0; r < size(); r++) {
                    //Caso o espaço livre do bloco seja igual ao espaço requerido
                    if (get(r).getEspaçoLivre() == espaçoRequerido) {
                        get(r).idProcesso = processo.getId();
                        get(r).espaçoUsado = espaçoRequerido;
                        return true;
                    } else {
                        //O espaço livre do bloco é maior que o espaço requerido
                        if (get(r).getEspaçoLivre() > espaçoRequerido) {
                            //Caso a diferença de espaço livre entre o bloco e o espaço requerido do processo seja menor que a auxiliar
                            if (get(r).getEspaçoLivre() - espaçoRequerido <= diferençaEspaço) {
                                //Atualizo as auxiliares
                                diferençaEspaço = get(r).getEspaçoLivre() - espaçoRequerido;
                                idBlocoAuxiliar = get(r).identificador;
                            }
                        }
                    }
                }
                if (getBloco(idBlocoAuxiliar).espaçoTotal >= espaçoRequerido) {
                    getBloco(idBlocoAuxiliar).idProcesso = processo.getId();
                    getBloco(idBlocoAuxiliar).espaçoUsado = espaçoRequerido;
                    return true;
                } else {
                    //TODO Colocar lógica de setar status aqui?
                    processo.setStatus(Status.ABORTADO);
                    //Não foi possível alocar o processo
                    return false;
                    //TODO Abortar processo / Retornar falso?
                }
            }
        }
        //TODO Merge-Fit
        else {
            return false;
        }
    }

    void desalocar(Processo processo) {
        //TODO Procurar na minha lista de blocos pelo processo
        for (int r = 0; r < size(); r++) {
            if (get(r).idProcesso == processo.getId()) {
                //TODO Marcar o bloco como livre
                get(r).espaçoUsado = 0;
            }
        }
    }

    public ArrayList<Bloco> getBlocos() {
        ArrayList<Bloco> listaBlocos = new ArrayList<>();
        listaBlocos.addAll(this);
        System.out.println("Tamanho do ArrayList retornado pelo getBlocos:" + listaBlocos.size() + "\nTamanho da Memória: " + this.size() + "\nTamanho de espaço livre da memória: " + espaçoLivre);
        return listaBlocos;
    }

    Bloco getBloco(int idBloco) {
        Bloco auxiliar = null;
        for (int r = 0; r < size(); r++) {
            if (get(r).identificador == idBloco)
                auxiliar = get(r);
        }
        return auxiliar;
    }

    public int containsProcesso(Processo processo) {
        int auxiliar = -1;
        for (int r = 0; r < size(); r++) {
            if (get(r).idProcesso.equals(processo.getId())) {
                auxiliar = processo.getId();
            }
        }
        return auxiliar;
    }

    @Override
    public String toString() {
        String test = new String();
        for (Bloco bloco: this) {
            test+="\nBloco: " + bloco.identificador + " ProcessoID: " + bloco.idProcesso;
        }
        return test;
    }
}
