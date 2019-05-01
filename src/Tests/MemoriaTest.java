package Tests;

import Model.Memoria;
import Model.Particão;

public class MemoriaTest {


    public static void main(String[] args) {
        Memoria mem = new Memoria(500);


        mem.addParticão(100, 0);
        mem.addParticão(100, 1);
        mem.addParticão(100, 2);
        mem.addParticão(50, 3);
        mem.addParticão(20, 4);
        System.out.println(mem);
        Particão a = mem.getParticãobyId(3);
        Particão b = mem.getParticãobyId(4);

//        Processo prop = new Processo()

        mem.mergeParticao(a, b);
        System.out.println("\n");
        System.out.println(mem);


    }

}
