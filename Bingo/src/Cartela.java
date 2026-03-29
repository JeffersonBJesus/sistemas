import java.util.*;

public class Cartela {
    private int[][] numeros = new int[5][5];
    private boolean[][] marcados = new boolean[5][5];
    public boolean linhaPontuada = false;
    public boolean cartelaPontuada = false;

    public Cartela() {
        gerarCartela();
    }

    private void gerarCartela() {
        Set<Integer> usados = new HashSet<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 2 && j == 2) {
                    numeros[i][j] = 0; // centro livre
                    marcados[i][j] = true;
                } else {
                    int num;
                    do {
                        num = random.nextInt(99) + 1;
                    } while (usados.contains(num));
                    usados.add(num);
                    numeros[i][j] = num;
                }
            }
        }
    }

    public void marcarNumero(int numero) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (numeros[i][j] == numero) {
                    marcados[i][j] = true;
                }
            }
        }
    }

    public boolean verificarLinhaOuColuna() {
        // Verifica linhas
        for (int i = 0; i < 5; i++) {
            boolean linhaCompleta = true;
            for (int j = 0; j < 5; j++) {
                if (!marcados[i][j]) {
                    linhaCompleta = false;
                    break;
                }
            }
            if (linhaCompleta) return true;
        }

        // Verifica colunas
        for (int j = 0; j < 5; j++) {
            boolean colunaCompleta = true;
            for (int i = 0; i < 5; i++) {
                if (!marcados[i][j]) {
                    colunaCompleta = false;
                    break;
                }
            }
            if (colunaCompleta) return true;
        }

        return false;
    }

    public boolean verificarCartelaCompleta() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!marcados[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void mostrarCartela() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (marcados[i][j]) {
                    System.out.printf("[%02d] ", numeros[i][j]); // marcado
                } else {
                    System.out.printf(" %02d  ", numeros[i][j]); // não marcado
                }
            }
            System.out.println();
        }
    }
}
