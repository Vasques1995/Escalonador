package Model;

import static Model.Status.EXECUTANDO;

class Núcleo {
    Processo processo;

    Núcleo() { }

    Núcleo(Processo processo) {
        processo.setStatus(EXECUTANDO);
        this.processo = processo;
    }

    Processo getProcesso() {
        return processo;
    }

    void setProcesso(Processo processo) {
        processo.setStatus(EXECUTANDO);
        processo.zerarTempo();
        this.processo = processo;
    }
}
