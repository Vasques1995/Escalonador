package Model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Processador extends TimerTask {
    Memory memória;
    ArrayList<Núcleo> núcleos;
    private int quantum;
    ArrayList<Processo> processosAptos = new ArrayList<>();
    ArrayList<Processo> processosTerminados = new ArrayList<>();

    //TimerTask que administra o tempo de execução total e restante dos processosAptos nos núcleos
    //TODO Condição de Parada
    TimerTask passaTempo = new TimerTask() {
        @Override
        public void run() {
            for (Núcleo nucleo : núcleos) {
                if (nucleo != null && nucleo.processo != null && nucleo.processo.getTempoRestante() > 0) {
                    //Tira um segundo do tempo restante de execução
                    nucleo.getProcesso().setTempoRestante(nucleo.processo.getTempoRestante() - 1);
                    //Adiciona um segundo ao tempo em que ele já foi executado
                    nucleo.getProcesso().incrementarTempo(1);
                    //TODO O que é uma requisição dinâmica, é um processo pedindo mais memória
                    //TODO Desalocar o processo da memória, aumentar tamanho requerido e tentar alocar?
                    //TODO Primeiro a condição para ativar a requisição - Chançe de 1/20
                    if (new Random().nextInt(20) + 1 == 20){
                        //Checa se é possível alocar o processo com o novo tamanho
                        memória.desalocar(nucleo.processo);
                        nucleo.processo.setQtdBytes(nucleo.processo.getQtdBytes() + new Random().nextInt(101));
                        if (!memória.alocar(nucleo.processo)){
                            //TODO Caso não seja possível alocar o processo com o novo tamanho é preciso abortar e retirar do núcleo
                            nucleo.processo.setStatus(Status.ABORTADO);
                            processosTerminados.add(nucleo.getProcesso());
                            nucleo.setProcesso(null);
                        }
                    }
                }
                //TODO Melhorar lógica de processos terminados
                if (nucleo != null && nucleo.processo != null && nucleo.processo.getTempoRestante() <= 0) {
                    //Caso o processo tenha terminado altera o status e adiciona na lista de processos terminados
                    nucleo.getProcesso().setStatus(Status.TERMINADO);
                    processosTerminados.add(nucleo.getProcesso());
                }
            }
        }
    };

    public Memory getMemória() {
        return memória;
    }

    public void setMemória(Memory memória) {
        this.memória = memória;
    }

    Processador(int nNúcleos, int nProcessos, int quantum, int tamMemory, String algoritmo) {
        núcleos = new ArrayList<>(nNúcleos);
        //Inicializa os núcleos
        for (int r = 0; r < nNúcleos; r++) {
            Núcleo núcleo = new Núcleo();
            núcleos.add(núcleo);
        }
        this.quantum = quantum;
        //Gera os processos aleatórios
        geradorProcesso(nProcessos);
        //Run Forrest, RUN!
        memória = new Memory(tamMemory, algoritmo);
        run();
    }

    @Override
    synchronized public void run() {
        //TODO Rever
        //Passagem de tempo dentro do núcleo
        Timer timer = new Timer();
        timer.schedule(passaTempo, 1000, 1000);
    }

    private void geradorProcesso(int nProcessos) {
        for (int i = 0; i < nProcessos; i++) {
            Processo processo = new Processo(i);
            processosAptos.add(processo);
        }
    }

    //Todo ID gerado é baseado no maior ID presente na table, é possível melhorar???
    public int getMaiorId() {
        int auxiliar = 0;
        for (Processo processo : getTodosProcessos()) {
            if (processo.getId() >= auxiliar)
                auxiliar = processo.getId();
        }
        return auxiliar;
    }

    synchronized public ArrayList<Processo> getTodosProcessos() {
        ArrayList<Processo> todos = new ArrayList<>();
        for (Núcleo nucleo : núcleos) {
            if (nucleo != null && nucleo.processo != null)
                todos.add(nucleo.getProcesso());
        }
        todos.addAll(processosAptos);
        return todos;
    }

    public ArrayList<Processo> getProcessosTerminados() {
        return processosTerminados;
    }

    public void setProcessosTerminados(ArrayList<Processo> processosTerminados) {
        this.processosTerminados = processosTerminados;
    }

    //TODO Isso parece mto errado, pensar melhor dps
    public ArrayList<Processo> getProcessosNucleo() {
        ArrayList<Processo> processosNucleo = new ArrayList<>();
        for (Núcleo nucleo : núcleos) {
            processosNucleo.add(nucleo.getProcesso());
        }
        return processosNucleo;
    }

    //Todo Em mundo ideal conseguir pausar todas as Threads
    public void pause() {
    }

    public void addNovoProcesso(Processo processoNovo) {
        //TODO Problema, novos processos não são checados imediatamente com relação a memória
        if (memória.alocar(processoNovo)){
            processosAptos.add(processoNovo);
        }
        else {
            processoNovo.setStatus(Status.ABORTADO);
            processosTerminados.add(processoNovo);
        }
    }

    int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public ArrayList<Processo> getProcessosAptos() {
        return processosAptos;
    }

    public void setProcessosAptos(ArrayList<Processo> processosAptos) {
        this.processosAptos = processosAptos;
    }

    ArrayList<Núcleo> getNúcleos() {
        return núcleos;
    }

    public void setNúcleos(ArrayList<Núcleo> núcleos) {
        this.núcleos = núcleos;
    }
}
