package br.edu.uepb.eda.sorting;

import br.edu.uepb.eda.model.Estudante;

/**
 * SelectionSort em duas versoes:
 *
 * VERSAO SLIDE: implementacao classica com troca direta.
 *   - A cada iteracao, encontra o minimo do sub-array restante e troca com
 *     a posicao atual. A troca de longa distancia quebra a estabilidade.
 *   - Complexidade: O(n^2) em todos os casos (sempre percorre o sub-array inteiro).
 *   - Numero de trocas: exatamente O(n), o que e vantagem quando a escrita
 *     em memoria e cara.
 *   - Estavel: NAO. A troca pode mover um elemento para antes de um igual.
 *
 * VERSAO ESTAVEL: substitui a troca por insercao local (shift).
 *   - Ao encontrar o minimo, em vez de trocar, desloca todos os elementos
 *     entre a posicao atual e o minimo uma posicao para a direita, e insere
 *     o minimo na posicao correta. Isso preserva a ordem relativa de iguais.
 *   - Complexidade: O(n^2) em todos os casos; numero de escritas aumenta para O(n^2).
 *   - Estavel: SIM.
 */
public class SelectionSort {

    private SelectionSort() {}

    // --- Versao Slide ---------------------------------------------------------

    public static void sortSlide(Estudante[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j].compareTo(arr[minIdx]) < 0) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                swap(arr, i, minIdx);
            }
        }
    }

    // --- Versao Estavel -------------------------------------------------------

    public static void sortEstavel(Estudante[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j].compareTo(arr[minIdx]) < 0) {
                    minIdx = j;
                }
            }
            // Salva o minimo e abre espaco com shift em vez de troca direta
            Estudante min = arr[minIdx];
            while (minIdx > i) {
                arr[minIdx] = arr[minIdx - 1];
                minIdx--;
            }
            arr[i] = min;
        }
    }

    // --- Utilitario -----------------------------------------------------------

    private static void swap(Estudante[] arr, int i, int j) {
        Estudante tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}