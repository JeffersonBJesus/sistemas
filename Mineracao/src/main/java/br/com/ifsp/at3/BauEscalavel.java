package br.com.ifsp.at3;

import java.util.concurrent.atomic.AtomicInteger;

public class BauEscalavel {
    // Usando AtomicInteger para garantir atomicidade sem travar a thread inteira (mais escalável que synchronized)
    private AtomicInteger totalOuro = new AtomicInteger(0);

    public void depositar(int quantidade) {
        // Região Crítica protegida por operação atômica de baixo nível (Compare-and-Swap)
        totalOuro.addAndGet(quantidade);
    }

    public int getTotal() {
        return totalOuro.get();
    }
}
