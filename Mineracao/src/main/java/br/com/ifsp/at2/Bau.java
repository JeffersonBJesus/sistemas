package br.com.ifsp.at2;

public class Bau {
    private int totalOuro = 0;


    public synchronized void depositar(int quantidade, String nome) {
        System.out.println(nome + " chegou ao baú para depositar " + quantidade + " ouros.");
        int novoTotal = totalOuro + quantidade;


        try { Thread.sleep(10); } catch (InterruptedException e) {}

        totalOuro = novoTotal;
        System.out.println("------> Total no Baú: " + totalOuro);
    }
}
