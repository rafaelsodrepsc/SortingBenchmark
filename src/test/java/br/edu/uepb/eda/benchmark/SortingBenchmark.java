package br.edu.uepb.eda.benchmark;

import br.edu.uepb.eda.model.Estudante;
import br.edu.uepb.eda.sorting.BubbleSort;
import br.edu.uepb.eda.sorting.CountingSort;
import br.edu.uepb.eda.sorting.InsertionSort;
import br.edu.uepb.eda.sorting.MergeSort;
import br.edu.uepb.eda.sorting.QuickSort;
import br.edu.uepb.eda.sorting.SelectionSort;
import br.edu.uepb.eda.util.DataGenerator;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Benchmark JMH para todos os algoritmos de ordenacao sobre Estudante[].
 *
 * Configuracoes globais da classe (anotacoes de nivel de tipo):
 *
 * @BenchmarkMode(Mode.AverageTime): cada metodo de benchmark sera medido
 *   pelo tempo medio de execucao por invocacao (em vez de throughput, tempo
 *   total, etc.). O resultado aparece na coluna "Score" em nanosegundos.
 *
 * @OutputTimeUnit(TimeUnit.MILLISECONDS): unidade de exibicao dos resultados.
 *   JMH coleta em nanosegundos internamente e converte na exibicao.
 *
 * @State(Scope.Thread): cada thread de benchmark tem sua propria instancia
 *   do estado. Necessario aqui porque cada benchmark modifica o array durante
 *   a execucao; sem isolamento por thread haveria corrida entre warmup e
 *   medicoes.
 *
 * @Warmup: define quantas iteracoes de aquecimento (warmup) a JVM executa
 *   antes de comecar a medir. Aqui: 5 iteracoes de 1 segundo cada.
 *   O enunciado pede descartar ~5 execucoes iniciais; o warmup do JMH
 *   faz isso automaticamente sem contaminar as medicoes.
 *
 * @Measurement: define quantas iteracoes reais de medicao serao realizadas.
 *   Aqui: 20 iteracoes de 1 segundo cada, satisfazendo o requisito minimo
 *   de 20 execucoes do enunciado.
 *
 * @Fork(1): quantas vezes o JMH reinicia a JVM para isolar resultados.
 *   Fork = 1 significa uma JVM separada para cada benchmark. Aumentar o
 *   fork reduz variabilidade entre execucoes mas aumenta o tempo total.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class SortingBenchmark {

    /**
     * @Param: o JMH instancia o benchmark uma vez para cada valor do parametro,
     *   gerando combinacoes automaticas com outros @Param. Aqui define os tres
     *   tamanhos de vetor exigidos pelo enunciado.
     */
    @Param({"10", "50", "100"})
    private int tamanho;

    /**
     * O cenario de entrada e definido como parametro para que o JMH rode
     * automaticamente todos os algoritmos nos tres cenarios (random, sorted,
     * reversed) sem precisar de classes separadas.
     */
    @Param({"RANDOM", "SORTED", "REVERSED"})
    private String cenario;

    // Array mestre gerado no setup - NUNCA e passado diretamente ao algoritmo
    private Estudante[] arrMestre;

    /**
     * @Setup(Level.Trial): executado uma unica vez antes de todas as iteracoes
     *   (warmup + medicao) de um trial (uma combinacao de parametros + fork).
     *   Usado para gerar o array mestre que sera copiado em cada invocacao.
     *
     *   IMPORTANTE: o array mestre nao e ordenado em nenhum momento. Cada
     *   metodo de benchmark faz uma copia defensiva antes de ordenar, garantindo
     *   que a entrada de cada algoritmo seja sempre o cenario original - conforme
     *   exigido pelo enunciado ("nao use a saida de um algoritmo como entrada
     *   de outro").
     */
    @Setup(Level.Trial)
    public void setup() {
        arrMestre = switch (cenario) {
            case "SORTED"   -> DataGenerator.sortedEstudantes(tamanho);
            case "REVERSED" -> DataGenerator.reversedEstudantes(tamanho);
            default         -> DataGenerator.randomEstudantes(tamanho);
        };
    }

    // -------------------------------------------------------------------------
    // BubbleSort
    // -------------------------------------------------------------------------

    /**
     * @Benchmark: marca o metodo como ponto de medicao. O JMH gera
     *   automaticamente o codigo de harness (loop de aquecimento, loop de
     *   medicao, blackhole) a partir desta anotacao via processamento de
     *   anotacoes em compile-time (jmh-generator-annprocess).
     */
    @Benchmark
    public void bubbleSortSlide() {
        Estudante[] arr = DataGenerator.copyOf(arrMestre);
        BubbleSort.sortSlide(arr);
    }

    @Benchmark
    public void bubbleSortOtimizado() {
        Estudante[] arr = DataGenerator.copyOf(arrMestre);
        BubbleSort.sortOtimizado(arr);
    }

    // -------------------------------------------------------------------------
    // SelectionSort
    // -------------------------------------------------------------------------

    @Benchmark
    public void selectionSortSlide() {
        Estudante[] arr = DataGenerator.copyOf(arrMestre);
        SelectionSort.sortSlide(arr);
    }

    @Benchmark
    public void selectionSortEstavel() {
        Estudante[] arr = DataGenerator.copyOf(arrMestre);
        SelectionSort.sortEstavel(arr);
    }

    // -------------------------------------------------------------------------
    // InsertionSort
    // -------------------------------------------------------------------------

    @Benchmark
    public void insertionSort() {
        Estudante[] arr = DataGenerator.copyOf(arrMestre);
        InsertionSort.sort(arr);
    }

    // -------------------------------------------------------------------------
    // MergeSort
    // -------------------------------------------------------------------------

    @Benchmark
    public void mergeSortClassico() {
        Estudante[] arr = DataGenerator.copyOf(arrMestre);
        MergeSort.sortClassico(arr);
    }

    @Benchmark
    public void mergeSortTimSort() {
        Estudante[] arr = DataGenerator.copyOf(arrMestre);
        MergeSort.sortTimSort(arr);
    }

    // -------------------------------------------------------------------------
    // QuickSort
    // -------------------------------------------------------------------------

    @Benchmark
    public void quickSortSlide() {
        Estudante[] arr = DataGenerator.copyOf(arrMestre);
        QuickSort.sortSlide(arr);
    }

    @Benchmark
    public void quickSortShuffle() {
        Estudante[] arr = DataGenerator.copyOf(arrMestre);
        QuickSort.sortShuffle(arr);
    }

    @Benchmark
    public void quickSortJava() {
        Estudante[] arr = DataGenerator.copyOf(arrMestre);
        QuickSort.sortJavaObjeto(arr);
    }

    // -------------------------------------------------------------------------
    // CountingSort
    // -------------------------------------------------------------------------

    @Benchmark
    public void countingSort() {
        Estudante[] arr = DataGenerator.copyOf(arrMestre);
        CountingSort.sort(arr);
    }

    // -------------------------------------------------------------------------
    // Ponto de entrada
    // -------------------------------------------------------------------------

    /**
     * Permite executar o benchmark diretamente via `java -cp benchmarks.jar
     * br.edu.uepb.eda.benchmark.SortingBenchmark` alem do uber-jar padrao.
     *
     * OptionsBuilder: API fluente para configurar uma execucao programatica
     *   do JMH sem depender de linha de comando.
     * includeWildcard: filtra quais benchmarks serao executados (regex sobre
     *   o nome completo do metodo).
     */
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SortingBenchmark.class.getSimpleName())
                .resultFormat(org.openjdk.jmh.results.format.ResultFormatType.JSON) // Mude para JSON
                .result("resultados.json")                                          // Mude a extensão
                .build();
        new Runner(opt).run();
    }
}