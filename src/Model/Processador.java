package Model;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Processador extends TimerTask {
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

    Processador(int nNúcleos, int nProcessos, int quantum) {
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
        run();
    }

    @Override
    synchronized public void run() {
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

    //    Todo ID gerado é baseado no maior ID presente na table, é possível melhorar???
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
        processosAptos.add(processoNovo);
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
