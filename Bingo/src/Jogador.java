import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Jogador  implements Runnable{
    private String nome;
    private List<Cartela> cartelas;
    private int pontos = 0;

    // Flags por jogador
    private boolean linhaPremiadaJogador = false;
    private boolean cartelaPremiadaJogador = false;
    private boolean tresCartelasPremiadasJogador = false;


    private LinkedBlockingQueue<Integer> filaNumeros = new LinkedBlockingQueue<>();
    private volatile boolean jogoAtivo = true;

    public Jogador(String nome, List<Cartela> cartelas) {
        this.nome = nome;
        this.cartelas = cartelas;
    }

    public void receberNumero(int numero) {
        filaNumeros.add(numero);
    }

    public boolean todasCartelasCompletas() {
        return cartelas.stream().allMatch(Cartela::verificarCartelaCompleta);
    }

    public int getPontos() {
        return pontos;
    }

    public String getNome() {
        return nome;
    }

    public void mostrarCartelas() {
        System.out.println("Cartelas de " + nome + ":");
        for (Cartela c : cartelas) {
            c.mostrarCartela();
            System.out.println();
        }
    }

    // Lista de nomes brasileiros
    public static List<String> nomesBrasileiros() {
        return Arrays.asList(
                "João", "Maria", "Pedro", "Ana", "Carlos",
                "Fernanda", "Lucas", "Juliana", "Rafael", "Camila"
        );
    }

    public void parar() { this.jogoAtivo = false; }


    @Override
    public void run() {
        while (jogoAtivo && !PontuacaoGlobal.tresCartelasPremiadas.get()) {
            try {
                Integer numero = filaNumeros.take(); // Bloqueia até ter número
                processarNumero(numero);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; // Opcional: sai do loop se a thread for interrompida
            }
        }
    }
    private void processarNumero(int numero) {
        int velocidade = new Random().nextInt(400) + 100; // 100-500ms
        try {
            Thread.sleep(velocidade);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Cartela c : cartelas) {
            c.marcarNumero(numero);
        }

        // Linha/coluna
        if (!linhaPremiadaJogador && cartelas.stream().anyMatch(Cartela::verificarLinhaOuColuna)) {
            linhaPremiadaJogador = true;
            if (PontuacaoGlobal.linhaPremiada.compareAndSet(false, true)) {
                pontos += 10; // Primeiro a ganhar
            } else {
                pontos += 5;  // Buzinada
            }
        }

        // Cartela completa
        if (!cartelaPremiadaJogador && cartelas.stream().anyMatch(Cartela::verificarCartelaCompleta)) {
            cartelaPremiadaJogador = true;
            if (PontuacaoGlobal.linhaPremiada.compareAndSet(false, true)) {
                pontos += 50;
                System.out.println(nome + " gritou BINGO! (cartela completa) com velocidade " + velocidade + "ms");
            } else {
                pontos += 25;
                System.out.println(nome + " também gritou BINGO! (cartela completa), mas levou uma BUZINADA!");
            }
        }

        // Todas as 3 cartelas
        if (!tresCartelasPremiadasJogador && todasCartelasCompletas()) {
            tresCartelasPremiadasJogador = true;
            if (PontuacaoGlobal.linhaPremiada.compareAndSet(false, true)) {
                pontos += 100;
                System.out.println(nome + " gritou BINGO! (TODAS as cartelas) com velocidade " + velocidade + "ms");
            } else {
                pontos += 50;
                System.out.println(nome + " também gritou BINGO! (TODAS as cartelas), mas levou uma BUZINADA!");
            }
        }
    }

}
