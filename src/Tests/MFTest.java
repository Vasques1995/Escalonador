package Tests;

import Model.MF;
import Model.Particão;

public class MFTest {


    public static void main(String[] args) {
        MF mem = new MF(500);


        mem.addParticão(100, 0);
        mem.addParticão(100, 1);
        mem.addParticão(100, 2);
        mem.addParticão(50, 3);
        mem.addParticão(20, 4);
        System.out.println(mem);
        Particão a = mem.getParticãobyId(3);
        Particão b = mem.getParticãobyId(4);

//        Processo prop = new Processo()

        mem.findToMerge();
        mem.findToMerge();
        mem.findToMerge();
        mem.findToMerge();
        mem.addParticão(100, 4);
        mem.addParticão(100, 4);


        System.out.println("\n");
        System.out.println(mem);


    }


}
