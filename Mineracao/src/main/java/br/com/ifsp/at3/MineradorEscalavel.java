package br.com.ifsp.at3;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MineradorEscalavel {
    public static void main(String[] args) throws InterruptedException {
        int numJogadores = 1000; // Escalando para 1.000 jogadores!
        int tarefasPorJogador = 100;

        BauEscalavel bau = new BauEscalavel();

        // Criando um Pool de Threads (Paralelismo controlado pelo número de núcleos do seu PC)
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        System.out.println("--- Iniciando Simulação com " + numJogadores + " jogadores ---");
        long inicio = System.currentTimeMillis();

        for (int i = 0; i < numJogadores; i++) {
            executor.execute(() -> {
                for (int j = 0; j < tarefasPorJogador; j++) {
                    // Simulação de processamento (Concorrência)
                    bau.depositar(10);
                }
            });
        }

        // Finalizando o executor e esperando as threads terminarem
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        long fim = System.currentTimeMillis();

        System.out.println("--- Relatório de Performance ---");
        System.out.println("Total de Ouro: " + bau.getTotal());
        System.out.println("Tempo Total: " + (fim - inicio) + "ms");
        System.out.println("Jogadores processados: " + numJogadores);
    }
}