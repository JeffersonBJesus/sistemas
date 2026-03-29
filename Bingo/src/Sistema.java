import java.util.*;

public class Sistema {
    public static void main(String[] args) {
        // Variáveis configuráveis
        int quantidadeNPCs = 3;       // número de NPCs
        int cartelasPorJogador = 3;   // número de cartelas por jogador

        // Lista de nomes brasileiros
        List<String> nomes = new ArrayList<>(Jogador.nomesBrasileiros());
        Collections.shuffle(nomes);

        // Total de jogadores (humano + NPCs)
        int totalJogadores = quantidadeNPCs + 1;

        // Gera cartelas
        List<Cartela> todas = new ArrayList<>();
        for (int i = 0; i < totalJogadores * cartelasPorJogador; i++) {
            todas.add(new Cartela());
        }
        Collections.shuffle(todas);

        // Jogador humano
        List<Cartela> jogadorCartelas = todas.subList(0, cartelasPorJogador);
        Jogador j1 = new Jogador("Você", jogadorCartelas);

        // NPCs
        List<Jogador> jogadores = new ArrayList<>();
        jogadores.add(j1);
        for (int i = 0; i < quantidadeNPCs; i++) {
            int inicio = (i + 1) * cartelasPorJogador;
            int fim = inicio + cartelasPorJogador;
            List<Cartela> npcCartelas = todas.subList(inicio, fim);
            Jogador npc = new Jogador(nomes.get(i), npcCartelas);
            jogadores.add(npc);
        }

        // Sorteio central
        Sorteio sorteio = new Sorteio(jogadores);
        Thread sorteioThread = new Thread(sorteio);
        sorteioThread.start();

        try {
            sorteioThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Mostrar números sorteados
        System.out.println("\nNúmeros sorteados: " + sorteio.getSorteados());

        // Mostrar cartelas finais
        for (Jogador j : jogadores) {
            j.mostrarCartelas();
        }

        // Comparar pontos
        System.out.println("\nPontuação final:");
        for (Jogador j : jogadores) {
            System.out.println(j.getNome() + ": " + j.getPontos());
        }

        // Determinar vencedor
        Jogador vencedor = jogadores.get(0);
        for (Jogador j : jogadores) {
            if (j.getPontos() > vencedor.getPontos()) vencedor = j;
        }

        System.out.println("\nVencedor: " + vencedor.getNome() + " com " + vencedor.getPontos() + " pontos!");
    }
}
