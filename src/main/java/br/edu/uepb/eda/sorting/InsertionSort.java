package br.edu.uepb.eda.sorting;

import br.edu.uepb.eda.model.Estudante;

/**
 * InsertionSort - versao classica.
 *
 * Mantém um sub-array esquerdo sempre ordenado e insere o proximo elemento
 * na posicao correta usando shift (deslocamento), nao troca.
 *
 * Complexidade:
 *   - Melhor caso O(n): vetor ja ordenado, nenhum shift e realizado.
 *   - Medio e pior caso O(n^2): vetor aleatorio ou inversamente ordenado.
 *
 * Estavel: SIM. O shift para quando encontra elemento igual ou menor,
 * preservando a ordem relativa de elementos iguais.
 *
 * Na pratica, e o algoritmo mais rapido entre os O(n^2) para vetores
 * pequenos ou quase ordenados, motivo pelo qual o TimSort o usa internamente
 * para blocos de tamanho reduzido (runs).
 */
public class InsertionSort {

    private InsertionSort() {}

    public static void sort(Estudante[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            Estudante chave = arr[i];
            int j = i - 1;
            // Desloca elementos maiores que 'chave' uma posicao para a direita
            while (j >= 0 && arr[j].compareTo(chave) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = chave;
        }
    }
}