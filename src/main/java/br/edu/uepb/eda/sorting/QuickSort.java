package br.edu.uepb.eda.sorting;

import br.edu.uepb.eda.model.Estudante;

import java.util.Arrays;
import java.util.Random;

/**
 * QuickSort em tres versoes para Estudante[] e uma versao extra para int[]:
 *
 * VERSAO SLIDE: pivo = ultimo elemento do sub-array (esquema de Lomuto).
 *   - Complexidade media: O(n log n).
 *   - Pior caso: O(n^2) quando o vetor ja esta ordenado ou inversamente
 *     ordenado, pois o pivo sempre cai na borda da particao.
 *   - Estavel: NAO. A troca de longa distancia nao preserva ordem relativa.
 *   - Memoria auxiliar: O(log n) media (pilha de recursao); O(n) no pior caso.
 *
 * VERSAO SLIDE + SHUFFLE: aplica Fisher-Yates antes de ordenar.
 *   - O embaralhamento aleatorio elimina o pior caso pratico O(n^2),
 *     tornando a probabilidade de O(n^2) negligenciavel.
 *   - Custo do shuffle: O(n), absorvido pela complexidade total.
 *   - Desvantagem: destroi qualquer ordem pre-existente, tornando ruim
 *     o desempenho em vetores quase ordenados que poderiam ser baratos.
 *
 * VERSAO JAVA (Arrays.sort para primitivos): usa Dual-Pivot QuickSort.
 *   - Algoritmo de Yaroslavskiy: dois pivotos dividem o array em tres partes,
 *     reduzindo o numero medio de comparacoes versus QuickSort de um pivo.
 *   - Complexidade media: O(n log n) com constante menor que o classico.
 *   - Pior caso: O(n^2) teorico, mas a implementacao do JDK tem heuristicas
 *     para detectar e tratar casos degenerados.
 *   - Apenas para int[] (primitivos). Para Object[], o Java usa TimSort.
 *
 * NOTA: a versao Java para Estudante[] e coberta pelo MergeSort.sortTimSort().
 */
public class QuickSort {

    private static final Random RNG = new Random(42L);

    private QuickSort() {}

    // --- Versao Slide (Estudante[]) -------------------------------------------

    public static void sortSlide(Estudante[] arr) {
        quickSortSlide(arr, 0, arr.length - 1);
    }

    private static void quickSortSlide(Estudante[] arr, int esq, int dir) {
        if (esq >= dir) return;
        int p = particionarLomuto(arr, esq, dir);
        quickSortSlide(arr, esq, p - 1);
        quickSortSlide(arr, p + 1, dir);
    }

    private static int particionarLomuto(Estudante[] arr, int esq, int dir) {
        Estudante pivo = arr[dir];
        int i = esq - 1;
        for (int j = esq; j < dir; j++) {
            if (arr[j].compareTo(pivo) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, dir);
        return i + 1;
    }

    // --- Versao Slide + Shuffle (Estudante[]) ---------------------------------

    public static void sortShuffle(Estudante[] arr) {
        shuffle(arr);
        quickSortSlide(arr, 0, arr.length - 1);
    }

    private static void shuffle(Estudante[] arr) {
        // Fisher-Yates O(n): garante permutacao uniforme
        for (int i = arr.length - 1; i > 0; i--) {
            int j = RNG.nextInt(i + 1);
            swap(arr, i, j);
        }
    }

    // --- Versao Java para Estudante[] -----------------------------------------
    // Arrays.sort(Object[]) usa TimSort; esta versao e equivalente ao MergeSort.sortTimSort()
    // e esta aqui apenas para completude da comparacao exigida pelo enunciado.
    public static void sortJavaObjeto(Estudante[] arr) {
        Arrays.sort(arr);
    }

    // --- Versao Slide (int[]) - experimento extra ----------------------------

    public static void sortSlideInt(int[] arr) {
        quickSortSlideInt(arr, 0, arr.length - 1);
    }

    private static void quickSortSlideInt(int[] arr, int esq, int dir) {
        if (esq >= dir) return;
        int p = particionarInt(arr, esq, dir);
        quickSortSlideInt(arr, esq, p - 1);
        quickSortSlideInt(arr, p + 1, dir);
    }

    private static int particionarInt(int[] arr, int esq, int dir) {
        int pivo = arr[dir];
        int i = esq - 1;
        for (int j = esq; j < dir; j++) {
            if (arr[j] <= pivo) {
                i++;
                swapInt(arr, i, j);
            }
        }
        swapInt(arr, i + 1, dir);
        return i + 1;
    }

    // --- Versao Slide + Shuffle (int[]) - experimento extra ------------------

    public static void sortShuffleInt(int[] arr) {
        shuffleInt(arr);
        quickSortSlideInt(arr, 0, arr.length - 1);
    }

    private static void shuffleInt(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = RNG.nextInt(i + 1);
            swapInt(arr, i, j);
        }
    }

    // --- Versao Java Dual-Pivot (int[]) - experimento extra ------------------

    public static void sortJavaInt(int[] arr) {
        Arrays.sort(arr);
    }

    // --- Utilitarios ---------------------------------------------------------

    private static void swap(Estudante[] arr, int i, int j) {
        Estudante tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
    }

    private static void swapInt(int[] arr, int i, int j) {
        int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
    }
}