package br.com.ifsp.at2;

import java.util.Random;

public class Minerador extends Thread {
    private String nome;
    private Bau bau;
    private Random random = new Random();

    public Minerador(String nome, Bau bau) {
        this.nome = nome;
        this.bau = bau;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            int ouroEncontrado = random.nextInt(10) + 1;

            System.out.println(nome + " está minerando...");
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {}

            bau.depositar(ouroEncontrado, nome);
        }
        System.out.println("!!! " + nome + " terminou seu turno.");
    }

}
