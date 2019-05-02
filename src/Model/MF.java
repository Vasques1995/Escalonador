package Model;

public class MF extends Memoria {


    public MF(Integer tamanhoTotal) {
        super(tamanhoTotal);
    }

    public void findToMerge() {
        for (Particão particão : particoes) {
            if (particão.getProcesso() == null) {
                int id = particoes.indexOf(particão);
                if (id - 1 != -1) {
                    if (particoes.get(id - 1).getProcesso() == null) {
                        mergeParticao(particão, particoes.get(id - 1));
                        break;
                    } else if (id + 1 != particoes.size() + 1) {
                        if (particoes.get(id + 1).getProcesso() == null) {
                            mergeParticao(particão, particoes.get(id + 1));
                            break;
                        }
                    }
                }
            }
        }
    }



}
