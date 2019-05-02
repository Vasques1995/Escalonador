package Tests;

import Model.MF;
import Model.Particão;

import java.util.ArrayList;

public class MFTest {


    public static void main(String[] args) {
        MF mem = new MF(500);

        System.out.println(mem);
        ArrayList<Particão> k = mem.getParticoes();
        mem.unMergeParticao(k.get(0), 250);

//        Processo prop = new Processo()

//        mem.findToMerge();
//        mem.findToMerge();
//        mem.findToMerge();
//        mem.findToMerge();


        System.out.println("\n");
        System.out.println(mem);


    }


}
