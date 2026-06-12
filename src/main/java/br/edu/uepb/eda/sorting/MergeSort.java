package br.edu.uepb.eda.sorting;

import br.edu.uepb.eda.model.Estudante;

import java.util.Arrays;

/**
 * MergeSort em duas versoes:
 *
 * VERSAO CLASSICA: divisao recursiva ao meio + merge com array auxiliar.
 *   - Complexidade: O(n log n) em todos os casos (melhor, medio e pior).
 *   - Memoria auxiliar: O(n) - alocada a cada merge, o que gera pressao
 *     no GC para grandes entradas.
 *   - Estavel: SIM. O merge favorece o elemento da metade esquerda em
 *     caso de empate (condicao `<=` na comparacao).
 *
 * VERSAO TIMSORT (Java): delega para Arrays.sort(Object[]).
 *   - O Java usa TimSort para arrays de objetos desde o Java 7.
 *   - TimSort e um hibrido de MergeSort + InsertionSort que explora
 *     sequencias ja ordenadas (runs) naturais no vetor.
 *   - Melhor caso O(n): vetor ja ordenado (um unico run).
 *   - Pior caso O(n log n): garantido.
 *   - Memoria auxiliar: O(n) no pior caso, mas frequentemente menor
 *     por aproveitar runs existentes.
 *   - Estavel: SIM (garantia de spec do Java).
 */
public class MergeSort {

    private MergeSort() {}

    // --- Versao Classica ------------------------------------------------------

    public static void sortClassico(Estudante[] arr) {
        if (arr.length <= 1) return;
        mergeSort(arr, 0, arr.length - 1);
    }

    private static void mergeSort(Estudante[] arr, int esq, int dir) {
        if (esq >= dir) return;
        int meio = esq + (dir - esq) / 2;
        mergeSort(arr, esq, meio);
        mergeSort(arr, meio + 1, dir);
        merge(arr, esq, meio, dir);
    }

    private static void merge(Estudante[] arr, int esq, int meio, int dir) {
        int tamanhoEsq = meio - esq + 1;
        int tamanhoDir = dir - meio;

        Estudante[] L = new Estudante[tamanhoEsq];
        Estudante[] R = new Estudante[tamanhoDir];

        System.arraycopy(arr, esq, L, 0, tamanhoEsq);
        System.arraycopy(arr, meio + 1, R, 0, tamanhoDir);

        int i = 0, j = 0, k = esq;
        while (i < tamanhoEsq && j < tamanhoDir) {
            // <= garante estabilidade: em empate, prefere o elemento da esquerda
            if (L[i].compareTo(R[j]) <= 0) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        while (i < tamanhoEsq) arr[k++] = L[i++];
        while (j < tamanhoDir)  arr[k++] = R[j++];
    }

    // --- Versao TimSort (Java) ------------------------------------------------

    public static void sortTimSort(Estudante[] arr) {
        Arrays.sort(arr);
    }
}