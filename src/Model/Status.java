package Model;

public enum Status {
    //Pronto: Está pronto para entrar na fila de aptos
    //Esperando: Está na fila de aptos, mas não está em execução
    //Executando: Está sendo ativamente executado pelo processador
    //Morto: Já terminou de ser executado
    PRONTO, ESPERANDO, EXECUTANDO, TERMINADO;
}
