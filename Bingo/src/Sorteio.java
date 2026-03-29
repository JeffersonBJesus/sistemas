import java.util.*;

public class Sorteio implements Runnable {
    private Queue<Integer> numeros = new LinkedList<>();
    private List<Integer> sorteados = new ArrayList<>();
    private List<Jogador> jogadores;

    public Sorteio(List<Jogador> jogadores) {
        this.jogadores = jogadores;
        List<Integer> lista = new ArrayList<>();
        for (int i = 1; i <= 99; i++) lista.add(i);
        Collections.shuffle(lista);
        numeros.addAll(lista);
    }

    public void run() {
        while (!numeros.isEmpty() && !PontuacaoGlobal.tresCartelasPremiadas) {
            int numero = numeros.poll();
            sorteados.add(numero);
            System.out.println("\nNúmero sorteado: " + numero);

            // Broadcast para todos os jogadores
            for (Jogador j : jogadores) {
                j.receberNumero(numero);
            }

            try {
                Thread.sleep(200); // pausa entre sorteios
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Integer> getSorteados() {
        return sorteados;
    }
}
