package br.edu.uepb.eda.benchmark;

import br.edu.uepb.eda.model.Estudante;
import br.edu.uepb.eda.search.SearchAlgorithms;
import br.edu.uepb.eda.util.DataGenerator;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Benchmark JMH para os cinco algoritmos de busca sobre Estudante[].
 *
 * O vetor e gerado ordenado (sortedEstudantes), pre-condicao das buscas
 * binarias. As buscas lineares funcionam em qualquer ordem, entao usam o
 * mesmo vetor para uma comparacao justa.
 *
 * ALVO AUSENTE: a busca e sempre por um Estudante que nao existe no vetor
 * (nota = -1, fora do dominio 0..10). Isso forca o pior caso:
 *   - buscas lineares percorrem o vetor inteiro (O(n));
 *   - buscas binarias descem ate o fim sem achar (O(log n)).
 * Assim o contraste linear x binario fica evidente, sem favorecer nenhuma
 * variante por causa da posicao do alvo.
 *
 * -Xss512m: aumenta a pilha da JVM para que a busca linear recursiva, que
 * recursa em profundidade O(n), nao estoure (StackOverflowError) em n grande.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1, jvmArgsAppend = {"-Xss512m"})
public class SearchBenchmark {

    @Param({"5000", "15000", "50000"})
    private int tamanho;

    private Estudante[] arr;
    private Estudante alvoAusente;

    @Setup(Level.Trial)
    public void setup() {
        arr = DataGenerator.sortedEstudantes(tamanho);
        // nota = -1 nunca casa com nenhum elemento (notas validas sao 0..10)
        alvoAusente = new Estudante(Integer.MAX_VALUE, "ZZZ_inexistente", -1);
    }

    @Benchmark
    public void linearIterativa(Blackhole bh) {
        bh.consume(SearchAlgorithms.linearIterativa(arr, alvoAusente));
    }

    @Benchmark
    public void linearRecursiva(Blackhole bh) {
        bh.consume(SearchAlgorithms.linearRecursiva(arr, alvoAusente));
    }

    @Benchmark
    public void linearDuasPontas(Blackhole bh) {
        bh.consume(SearchAlgorithms.linearDuasPontas(arr, alvoAusente));
    }

    @Benchmark
    public void binariaIterativa(Blackhole bh) {
        bh.consume(SearchAlgorithms.binariaIterativa(arr, alvoAusente));
    }

    @Benchmark
    public void binariaRecursiva(Blackhole bh) {
        bh.consume(SearchAlgorithms.binariaRecursiva(arr, alvoAusente));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SearchBenchmark.class.getSimpleName())
                .resultFormat(org.openjdk.jmh.results.format.ResultFormatType.JSON)
                .result("resultados_busca.json")
                .build();
        new Runner(opt).run();
    }
}
