import java.util.*;

public class Jogador {
    private String nome;
    private List<Cartela> cartelas;
    private int pontos = 0;

    // Flags por jogador
    private boolean linhaPremiadaJogador = false;
    private boolean cartelaPremiadaJogador = false;
    private boolean tresCartelasPremiadasJogador = false;

    public Jogador(String nome, List<Cartela> cartelas) {
        this.nome = nome;
        this.cartelas = cartelas;
    }

    public void receberNumero(int numero) {
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
            if (!PontuacaoGlobal.linhaPremiada) {
                PontuacaoGlobal.linhaPremiada = true;
                pontos += 10;
                System.out.println(nome + " gritou BINGO! (linha/coluna) com velocidade " + velocidade + "ms");
            } else {
                pontos += 5;
                System.out.println(nome + " também gritou BINGO! (linha/coluna), mas levou uma BUZINADA!");
            }
        }

        // Cartela completa
        if (!cartelaPremiadaJogador && cartelas.stream().anyMatch(Cartela::verificarCartelaCompleta)) {
            cartelaPremiadaJogador = true;
            if (!PontuacaoGlobal.cartelaPremiada) {
                PontuacaoGlobal.cartelaPremiada = true;
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
            if (!PontuacaoGlobal.tresCartelasPremiadas) {
                PontuacaoGlobal.tresCartelasPremiadas = true;
                pontos += 100;
                System.out.println(nome + " gritou BINGO! (TODAS as cartelas) com velocidade " + velocidade + "ms");
            } else {
                pontos += 50;
                System.out.println(nome + " também gritou BINGO! (TODAS as cartelas), mas levou uma BUZINADA!");
            }
        }
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
}
