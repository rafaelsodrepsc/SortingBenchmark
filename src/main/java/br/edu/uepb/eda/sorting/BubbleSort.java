package br.edu.uepb.eda.sorting;

import br.edu.uepb.eda.model.Estudante;

/**
 * BubbleSort em duas versoes:
 *
 * VERSAO SLIDE: implementacao direta do pseudocodigo ensinado em sala.
 *   - Sempre executa n-1 passagens completas, mesmo se o vetor ja estiver ordenado.
 *   - Complexidade: O(n^2) em todos os casos.
 *   - Estavel: sim (troca apenas quando arr[j] > arr[j+1], nunca em empate).
 *
 * VERSAO OTIMIZADA: adiciona flag 'trocou' para detectar vetor ja ordenado.
 *   - Melhor caso O(n): se o vetor ja estiver ordenado, a primeira passagem
 *     nao realiza nenhuma troca e o algoritmo encerra imediatamente.
 *   - Pior e medio caso permanecem O(n^2).
 *   - Estavel: sim, pelo mesmo motivo da versao base.
 */
public class BubbleSort {

    private BubbleSort() {}

    // --- Versao Slide ---------------------------------------------------------

    public static void sortSlide(Estudante[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    // --- Versao Otimizada -----------------------------------------------------

    public static void sortOtimizado(Estudante[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean trocou = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    swap(arr, j, j + 1);
                    trocou = true;
                }
            }
            // Se nenhuma troca ocorreu, o vetor ja esta ordenado
            if (!trocou) break;
        }
    }

    // --- Utilitario -----------------------------------------------------------

    private static void swap(Estudante[] arr, int i, int j) {
        Estudante tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}