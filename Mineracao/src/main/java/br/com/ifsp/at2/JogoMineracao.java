package br.com.ifsp.at2;



public class JogoMineracao {
    public static void main(String[] args) {
        Bau bauCentral = new Bau();

        System.out.println("--- Iniciando a Corrida do Ouro ---");

        // Criando as Threads
        Minerador m1 = new Minerador("Minerador-Alfa", bauCentral);
        Minerador m2 = new Minerador("Minerador-Beta", bauCentral);
        Minerador m3 = new Minerador("Minerador-Gama", bauCentral);

        // Iniciando a execução em paralelo
        m1.start();
        m2.start();
        m3.start();

        try {
            m1.join();
            m2.join();
            m3.join();
        } catch (InterruptedException e) {}

        System.out.println("--- Mineração Encerrada ---");
    }
}