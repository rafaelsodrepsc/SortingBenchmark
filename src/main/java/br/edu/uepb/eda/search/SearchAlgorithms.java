package br.edu.uepb.eda.search;

import br.edu.uepb.eda.model.Estudante;

/**
 * Cinco algoritmos de busca sobre Estudante[].
 *
 * PRE-CONDICAO: para as buscas binarias, o array deve estar previamente
 * ordenado pelo mesmo criterio usado na comparacao (Comparable de Estudante).
 * Busca em vetor nao ordenado com algoritmo binario produz resultado indefinido.
 *
 * Retorno: indice do elemento encontrado, ou -1 se nao presente.
 */
public class SearchAlgorithms {

    private SearchAlgorithms() {}

    // -------------------------------------------------------------------------
    // Busca Linear Iterativa
    // -------------------------------------------------------------------------

    /**
     * Percorre o array posicao a posicao do inicio ao fim.
     * Complexidade: O(n) em todos os casos.
     * Nao exige vetor ordenado.
     */
    public static int linearIterativa(Estudante[] arr, Estudante alvo) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].compareTo(alvo) == 0) return i;
        }
        return -1;
    }

    // -------------------------------------------------------------------------
    // Busca Linear Recursiva
    // -------------------------------------------------------------------------

    /**
     * Variante recursiva da busca linear. Mesma complexidade O(n).
     * Consome O(n) na pilha de recursao (risco de StackOverflow para n grande).
     * Nao exige vetor ordenado.
     */
    public static int linearRecursiva(Estudante[] arr, Estudante alvo) {
        return linearRecursivaHelper(arr, alvo, 0);
    }

    private static int linearRecursivaHelper(Estudante[] arr, Estudante alvo, int idx) {
        if (idx >= arr.length) return -1;
        if (arr[idx].compareTo(alvo) == 0) return idx;
        return linearRecursivaHelper(arr, alvo, idx + 1);
    }

    // -------------------------------------------------------------------------
    // Busca Linear Iterativa Duas Pontas
    // -------------------------------------------------------------------------

    /**
     * Percorre o array simultaneamente a partir das duas extremidades.
     * Encontra o elemento em no maximo n/2 iteracoes.
     *
     * Complexidade: O(n) - a constante e reduzida pela metade na media,
     * mas a ordem assintotica permanece linear.
     * Nao exige vetor ordenado.
     *
     * ATENCAO: pode retornar qualquer uma das duas pontas se ambas apontarem
     * para o mesmo alvo ao mesmo tempo. O indice retornado e o da ponta
     * esquerda nesse caso (prioridade no if).
     */
    public static int linearDuasPontas(Estudante[] arr, Estudante alvo) {
        int esq = 0, dir = arr.length - 1;
        while (esq <= dir) {
            if (arr[esq].compareTo(alvo) == 0) return esq;
            if (arr[dir].compareTo(alvo) == 0) return dir;
            esq++;
            dir--;
        }
        return -1;
    }

    // -------------------------------------------------------------------------
    // Busca Binaria Iterativa
    // -------------------------------------------------------------------------

    /**
     * Divide o intervalo de busca ao meio a cada iteracao.
     * Complexidade: O(log n) - exige vetor ordenado.
     * Memoria auxiliar: O(1).
     *
     * Para n = 500.000: no maximo ~19 comparacoes.
     */
    public static int binariaIterativa(Estudante[] arr, Estudante alvo) {
        int esq = 0, dir = arr.length - 1;
        while (esq <= dir) {
            int meio = esq + (dir - esq) / 2; // evita overflow vs (esq+dir)/2
            int cmp = arr[meio].compareTo(alvo);
            if (cmp == 0)      return meio;
            else if (cmp < 0)  esq = meio + 1;
            else               dir = meio - 1;
        }
        return -1;
    }

    // -------------------------------------------------------------------------
    // Busca Binaria Recursiva
    // -------------------------------------------------------------------------

    /**
     * Variante recursiva da busca binaria.
     * Complexidade: O(log n) - exige vetor ordenado.
     * Memoria auxiliar: O(log n) na pilha de recursao (aceitavel na pratica).
     */
    public static int binariaRecursiva(Estudante[] arr, Estudante alvo) {
        return binariaRecursivaHelper(arr, alvo, 0, arr.length - 1);
    }

    private static int binariaRecursivaHelper(Estudante[] arr, Estudante alvo, int esq, int dir) {
        if (esq > dir) return -1;
        int meio = esq + (dir - esq) / 2;
        int cmp = arr[meio].compareTo(alvo);
        if (cmp == 0)      return meio;
        else if (cmp < 0)  return binariaRecursivaHelper(arr, alvo, meio + 1, dir);
        else               return binariaRecursivaHelper(arr, alvo, esq, meio - 1);
    }
}