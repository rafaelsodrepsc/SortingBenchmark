package br.edu.uepb.eda.util;

import br.edu.uepb.eda.model.Estudante;

import java.util.Arrays;
import java.util.Random;

/**
 * Gera arrays de Estudante e int[] nos tres cenarios exigidos pelo projeto:
 *   - RANDOM:   dados completamente aleatorios
 *   - SORTED:   dados em ordem crescente (ja ordenados pelo Comparable)
 *   - REVERSED: dados em ordem decrescente (pior caso para varios algoritmos)
 *
 * Nomes ficticios sao compostos de prefixo + numero para garantir unicidade
 * e variacao de nome sem depender de bibliotecas externas.
 *
 * A semente fixada (SEED) garante reproducibilidade dos experimentos.
 */
public class DataGenerator {

    public static final long SEED = 42L;

    private static final String[] PREFIXOS = {
            "Ana", "Bruno", "Carla", "Diego", "Elena",
            "Fabio", "Giulia", "Hugo", "Iris", "Jonas",
            "Karen", "Lucas", "Mariana", "Nuno", "Olivia"
    };

    private DataGenerator() {}

    // --- Estudante[] ----------------------------------------------------------

    /** Array de Estudante com dados completamente aleatorios. */
    public static Estudante[] randomEstudantes(int n) {
        Random rng = new Random(SEED);
        Estudante[] arr = new Estudante[n];
        for (int i = 0; i < n; i++) {
            arr[i] = new Estudante(
                    i + 1,
                    PREFIXOS[rng.nextInt(PREFIXOS.length)] + (i + 1),
                    rng.nextInt(11) // 0 a 10
            );
        }
        return arr;
    }

    /**
     * Array de Estudante em ordem crescente conforme Comparable.
     * Gerado a partir do random e ordenado com Arrays.sort (TimSort).
     * Usado para medir o comportamento dos algoritmos em vetores ja ordenados.
     */
    public static Estudante[] sortedEstudantes(int n) {
        Estudante[] arr = randomEstudantes(n);
        Arrays.sort(arr);
        return arr;
    }

    /**
     * Array de Estudante em ordem decrescente (inverso do Comparable).
     * Representa o pior caso para InsertionSort e QuickSort sem shuffle.
     */
    public static Estudante[] reversedEstudantes(int n) {
        Estudante[] arr = sortedEstudantes(n);
        reverse(arr);
        return arr;
    }

    // --- int[] ----------------------------------------------------------------

    /** Array de int com valores aleatorios no intervalo [0, n). */
    public static int[] randomInts(int n) {
        Random rng = new Random(SEED);
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rng.nextInt(n);
        }
        return arr;
    }

    /** Array de int em ordem crescente. */
    public static int[] sortedInts(int n) {
        int[] arr = randomInts(n);
        Arrays.sort(arr);
        return arr;
    }

    /** Array de int em ordem decrescente. */
    public static int[] reversedInts(int n) {
        int[] arr = sortedInts(n);
        for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
            int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
        }
        return arr;
    }

    // --- Utilitario -----------------------------------------------------------

    private static void reverse(Estudante[] arr) {
        for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
            Estudante tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
        }
    }

    /** Copia defensiva de Estudante[] para nao reutilizar saida ordenada como entrada. */
    public static Estudante[] copyOf(Estudante[] src) {
        return Arrays.copyOf(src, src.length);
    }

    /** Copia defensiva de int[] para nao reutilizar saida ordenada como entrada. */
    public static int[] copyOf(int[] src) {
        return Arrays.copyOf(src, src.length);
    }
}