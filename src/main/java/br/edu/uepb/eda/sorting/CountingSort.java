package br.edu.uepb.eda.sorting;

import br.edu.uepb.eda.model.Estudante;

/**
 * CountingSort adaptado para Estudante[], usando o campo 'nota' (0..10) como chave.
 *
 * CountingSort nao e um algoritmo de comparacao: ele conta a frequencia de cada
 * chave e reconstroi o array a partir das contagens acumuladas.
 *
 * Complexidade: O(n + k), onde k = amplitude do dominio da chave (aqui k = 11).
 *   - Para n grandes e k pequeno, e significativamente mais rapido que O(n log n).
 *   - Para k >> n, o espaco e tempo gastos no array de contagem dominam.
 *
 * Estavel: SIM, desde que o preenchimento do array de saida percorra o array
 * de entrada da direita para a esquerda (implementado abaixo).
 *   - Isso preserva a ordem relativa de estudantes com mesma nota.
 *
 * Limitacoes:
 *   - Aplicavel apenas quando a chave e um inteiro com amplitude conhecida e pequena.
 *   - Nao e aplicavel diretamente ao Comparable completo (nota + nome + matricula);
 *     ordena exclusivamente por nota, como exigido pelo enunciado.
 *
 * Memoria auxiliar: O(n + k).
 */
public class CountingSort {

    private static final int MAX_NOTA = 10;

    private CountingSort() {}

    /**
     * Ordena por nota em ordem DECRESCENTE (maior nota primeiro),
     * alinhado ao criterio primario do Comparable de Estudante.
     */
    public static void sort(Estudante[] arr) {
        int n = arr.length;
        int[] contagem = new int[MAX_NOTA + 1]; // indices 0..10

        // 1. Conta frequencia de cada nota
        for (Estudante e : arr) {
            contagem[e.getNota()]++;
        }

        // 2. Acumula contagens da direita para a esquerda (para saida decrescente)
        for (int i = MAX_NOTA - 1; i >= 0; i--) {
            contagem[i] += contagem[i + 1];
        }

        // 3. Constroi array de saida percorrendo da direita para a esquerda
        //    (garante estabilidade)
        Estudante[] saida = new Estudante[n];
        for (int i = n - 1; i >= 0; i--) {
            int nota = arr[i].getNota();
            saida[--contagem[nota]] = arr[i];
        }

        // 4. Copia de volta para o array original
        System.arraycopy(saida, 0, arr, 0, n);
    }
}