package src;

import java.util.Random;

public class App {
    /**
     * se colocar um valor pequeno como condição para gerar valores
     * aleatorios estes valores sempre serão os mesmos. Porem utilizando
     * o metodo de marcacao por contagem de milisegundos os valores irão
     * variar na maioria das vezes.
     */
    static Random sorteio = new Random(System.currentTimeMillis());
    static final int marcacao = 50;

    /** Metodo para limpar tela antes de aparecer primeiro menu */
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static int[] gerarVetor(int tam, double bagunca) {
        int[] vet = new int[tam]; // criando o vetor

        for (int i = 0; i < vet.length; i++) {
            vet[i] = i + 1;
        }
        int vezes = (int) (tam * bagunca);// bagunando o vetor
        for (int j = 0; j < vezes; j++) {
            int pos1 = sorteio.nextInt(tam);
            int pos2 = sorteio.nextInt(tam);
            int aux = vet[pos1];
            vet[pos1] = vet[pos2];
            vet[pos2] = aux;
        }
        return vet;
    }

    public static int[] ordenacaoBolha(int[] desordenado) {
        int trocas = 0;
        for (int ref = desordenado.length - 1; ref > 0; ref--) {
            int aux;
            for (int comp = 0; comp < ref; comp++) {
                if (desordenado[comp] > desordenado[comp + 1]) {
                    aux = desordenado[comp];
                    desordenado[comp] = desordenado[comp + 1];
                    desordenado[comp + 1] = aux;
                    trocas = trocas + 1;
                }
            }
        }
        if (trocas == 0) {
            return desordenado;
        }
        ordenacaoBolha(desordenado);
        return null;
    }

    public static void quicksort(int[] desordenado, int inicio, int fim) {
        int aux = ordenaQuicksort(desordenado, inicio, fim);

        if (inicio < aux - 1) {
            quicksort(desordenado, inicio, aux - 1);
        }
        if (aux < fim) {
            quicksort(desordenado, aux, fim);
        }
    }

    static int ordenaQuicksort(int[] ordenando, int inicio, int fim) {
        int maior = inicio, menor = fim;
        int tmp;
        int media = ordenando[(inicio + fim) / 2]; // o famoso pivô

        while (maior <= menor) {
            while (ordenando[maior] < media)
                maior++;
            while (ordenando[menor] > media)
                menor--;
            if (maior <= menor) {
                tmp = ordenando[maior];
                ordenando[maior] = ordenando[menor];
                ordenando[menor] = tmp;
                maior++;
                menor--;
            }
        }

        return maior;
    }

    static void PrimeiraFaseDeTestes(int tamVet, String valTest) {
        long tempoInicial;
        long tempoFinal;
        float mediaBolha = 0;
        long mediaQuick = 0;

        System.out.println("==============================/ /==============================");
        System.out.println(valTest + " Vetor de " + tamVet + " posicoes\n");

        for (int i = 0; i < 50; i++) {
            int[] desordenado = gerarVetor(tamVet, 0.05);
            // Ordenacao por bolha
            tempoInicial = System.nanoTime() / 1000000000;
            ordenacaoBolha(desordenado);
            tempoFinal = System.nanoTime() / 1000000000;
            mediaBolha += (tempoFinal - tempoInicial);

            // Ordenacao por QuickSort
            tempoInicial = System.nanoTime();
            quicksort(desordenado, 0, desordenado.length - 1);
            tempoFinal = System.nanoTime();
            mediaQuick += (tempoFinal - tempoInicial);
        }
        long mediaQ = mediaQuick / 50;
        float mediaB = mediaBolha / 50;
        System.out.printf(">>>>>>>  Usando a ordenacao por bolha demorou em media: " + "%.2f"
                + " segundos \n\n", mediaB);

        System.out.printf(">>>>>>>  Usando a ordenacao por quicksort demorou em media: " + mediaQ
                + " nanosegundos \n\n");
    }

    static void SegundaFaseDeTestes(int tamVet, String valTest) {
        long tempoInicial;
        long tempoFinal;
        long mediaQuick = 0;
        long mediaQuickOrdenado = 0;
        System.out.println("==============================/ /==============================");
        System.out.println(valTest + " Vetor de " + tamVet + " posicoes\n");
        int[] desordenado = gerarVetor(tamVet, 0.05);
        for (int i = 0; i < 10; i++) {
            // Ordenacao por QuickSort
            tempoInicial = System.nanoTime();
            quicksort(desordenado, 0, desordenado.length - 1);
            tempoFinal = System.nanoTime();
            mediaQuick += (tempoFinal - tempoInicial);
        }

        int[] ordenado = desordenado;
        for (int i = 0; i < 10; i++) {
            // Ordenacao por QuickSort
            tempoInicial = System.nanoTime();
            quicksort(ordenado, 0, desordenado.length - 1);
            tempoFinal = System.nanoTime();
            mediaQuickOrdenado += (tempoFinal - tempoInicial);
        }
        System.out.println(">>>>>>>  Ordenando por quicksort demorou em media: " + (mediaQuick / 2)
                + " nanosegundos \n\n");
        System.out.println(
                ">>>>>>>  Usando o Quicksort para no vetor reordenado, demorou em media: " + (mediaQuickOrdenado / 2)
                        + " nanosegundos \n\n");

    }

    public static void main(String[] args) throws Exception {
        limparTela();

        PrimeiraFaseDeTestes(62500, "TESTE 1: ");
        PrimeiraFaseDeTestes(125000, "TESTE 2: ");
        PrimeiraFaseDeTestes(250000, "TESTE 3: ");
        PrimeiraFaseDeTestes(375000, "TESTE 4: ");

        SegundaFaseDeTestes(375000, "TESTE UNICO: ");
    }
}
