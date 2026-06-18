# EDA/LEDA - Benchmark de Algoritmos de Ordenação e Busca

Projeto acadêmico desenvolvido na **UEPB** para a disciplina de Estrutura de Dados e Algoritmos (EDA/LEDA). Implementa e mede o desempenho de algoritmos de ordenação e busca sobre um array de objetos `Estudante`, usando o framework de microbenchmark **JMH (Java Microbenchmark Harness)**.

---

## Sumário

- [Pré-requisitos](#pré-requisitos)
- [Instalação do zero](#instalação-do-zero)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Algoritmos implementados](#algoritmos-implementados)
- [Como executar o benchmark](#como-executar-o-benchmark)
- [Parâmetros do benchmark](#parâmetros-do-benchmark)
- [Resultados](#resultados)

---

## Pré-requisitos

| Ferramenta | Versão mínima | Verificar |
|------------|--------------|-----------|
| Java (JDK) | 21           | `java -version` |
| Maven      | 3.8          | `mvn -version` |
| Git        | qualquer     | `git --version` |

---

## Instalação do zero

### 1. Instalar o JDK 21

**Ubuntu / Debian / WSL:**
```bash
sudo apt update
sudo apt install -y openjdk-21-jdk
```

**macOS (Homebrew):**
```bash
brew install openjdk@21
echo 'export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

**Windows:**
Baixe o instalador em [adoptium.net](https://adoptium.net/) e siga o assistente. Certifique-se de marcar a opção **"Set JAVA_HOME"** durante a instalação.

Verifique a instalação:
```bash
java -version
# java version "21.x.x" ...
```

### 2. Instalar o Maven

**Ubuntu / Debian / WSL:**
```bash
sudo apt install -y maven
```

**macOS (Homebrew):**
```bash
brew install maven
```

**Windows:**
Baixe o binário em [maven.apache.org](https://maven.apache.org/download.cgi), extraia e adicione a pasta `bin` ao `PATH` do sistema.

Verifique a instalação:
```bash
mvn -version
# Apache Maven 3.x.x ...
```

### 3. Clonar o repositório

```bash
git clone https://github.com/rafaelsodrepsc/SortingBenchmark.git
cd SortingBenchmark
```

### 4. Baixar dependências e compilar

```bash
mvn clean package -DskipTests
```

Esse comando:
1. Baixa as dependências do JMH declaradas no `pom.xml`.
2. Compila o código-fonte e gera os stubs de benchmark via processamento de anotações.
3. Empacota tudo em um **uber-jar** executável: `target/benchmarks.jar`.

---

## Estrutura do projeto

```
SortingBenchmark/
├── pom.xml                          # Configuração Maven + JMH
├── resultados.json                  # Saída do benchmark de ordenação (gerado ao executar)
├── resultados_busca.json            # Saída do benchmark de busca (gerado ao executar)
└── src/
    ├── main/java/br/edu/uepb/eda/
    │   ├── model/
    │   │   └── Estudante.java       # Entidade com nota, nome e matrícula
    │   ├── sorting/
    │   │   ├── BubbleSort.java      # Slide + Otimizado
    │   │   ├── SelectionSort.java   # Slide + Estável
    │   │   ├── InsertionSort.java
    │   │   ├── MergeSort.java       # Clássico + TimSort (Java)
    │   │   ├── QuickSort.java       # Slide + Shuffle + Java Dual-Pivot
    │   │   └── CountingSort.java
    │   ├── search/
    │   │   └── SearchAlgorithms.java # Buscas linear (3) e binária (2)
    │   └── util/
    │       └── DataGenerator.java   # Gerador de dados: RANDOM, SORTED, REVERSED
    └── test/java/br/edu/uepb/eda/
        └── benchmark/
            ├── SortingBenchmark.java  # Benchmark JMH de ordenação
            └── SearchBenchmark.java   # Benchmark JMH de busca
```

---

## Algoritmos implementados

### Ordenação

| Algoritmo | Variante | Complexidade (pior) | Estável |
|-----------|----------|---------------------|---------|
| BubbleSort | Slide | O(n²) | Sim |
| BubbleSort | Otimizado (flag) | O(n²) / O(n) melhor | Sim |
| SelectionSort | Slide | O(n²) | Não |
| SelectionSort | Estável | O(n²) | Sim |
| InsertionSort | - | O(n²) / O(n) melhor | Sim |
| MergeSort | Clássico | O(n log n) | Sim |
| MergeSort | TimSort (Java) | O(n log n) | Sim |
| QuickSort | Slide (Lomuto) | O(n²) | Não |
| QuickSort | Slide + Shuffle | O(n log n) esperado | Não |
| QuickSort | Java Dual-Pivot | O(n log n) | Não |
| CountingSort | - | O(n + k) | Sim |

### Busca

| Algoritmo | Requer ordenação | Complexidade |
|-----------|-----------------|--------------|
| Linear iterativa | Não | O(n) |
| Linear recursiva | Não | O(n) |
| Linear duas pontas | Não | O(n) |
| Binária iterativa | Sim | O(log n) |
| Binária recursiva | Sim | O(log n) |

### Critério de ordenação de `Estudante`

1. **Nota** - decrescente (maior nota primeiro)
2. **Nome** - crescente (desempate)
3. **Matrícula** - crescente (desempate final)

---

## Como executar o benchmark

### Opção A - uber-jar (recomendado)

```bash
java -jar target/benchmarks.jar
```

Os resultados são salvos automaticamente em `resultados.json` na raiz do projeto.

### Opção B - via Maven

```bash
mvn clean package -DskipTests && java -jar target/benchmarks.jar
```

### Opção C - filtrar benchmarks específicos

Execute apenas um subconjunto de algoritmos passando um padrão regex:

```bash
# Apenas QuickSort
java -jar target/benchmarks.jar "quickSort"

# Apenas MergeSort e InsertionSort
java -jar target/benchmarks.jar "mergeSort|insertionSort"
```

### Benchmark de busca

O projeto tem um benchmark separado para os algoritmos de busca (`SearchBenchmark`). A saída vai para `resultados_busca.json`:

```bash
java -jar target/benchmarks.jar SearchBenchmark -rf json -rff resultados_busca.json
```

A busca é sempre por um elemento **ausente** no vetor (pior caso): as buscas lineares percorrem o vetor inteiro (O(n)) e as binárias descem até o fim sem achar (O(log n)). A busca linear recursiva recursa em profundidade O(n); por isso o benchmark já roda a JVM com pilha aumentada (`-Xss512m`), configurado na própria classe, para ela não estourar em vetores grandes.

---

## Parâmetros do benchmark

O JMH executa automaticamente todas as combinações dos parâmetros abaixo:

| Parâmetro | Valores |
|-----------|---------|
| `tamanho` | `5000`, `15000`, `50000` |
| `cenario` | `RANDOM`, `SORTED`, `REVERSED` (apenas na ordenação) |

**Configuração de medição (ordenação):**
- Aquecimento: 5 iterações × 1 segundo
- Medição: 15 iterações × 1 segundo
- Fork: 1 JVM separada por benchmark
- Unidade de saída: nanosegundos (tempo médio por invocação)

**Configuração de medição (busca):**
- Aquecimento: 5 iterações × 1 segundo
- Medição: 10 iterações × 1 segundo
- Fork: 1 JVM separada por benchmark, com `-Xss512m`
- Unidade de saída: nanosegundos (tempo médio por invocação)

Cada método de benchmark cria uma **cópia defensiva** do array antes de ordenar, garantindo que a entrada de cada algoritmo seja sempre o cenário original.

---

## Resultados

Após a execução, os arquivos `resultados.json` (ordenação) e `resultados_busca.json` (busca) contêm os dados brutos no formato JMH. Para visualizá-los de forma gráfica, acesse o [JMH Visualizer](https://jmh.morethan.io/) e faça o upload do arquivo.

Exemplo do formato de saída no terminal:

```
Benchmark                         (cenario)  (tamanho)  Mode  Cnt       Score   Error  Units
SortingBenchmark.bubbleSortSlide     RANDOM       5000  avgt   15   xxxxxxx ± xxxxx  ns/op
SortingBenchmark.mergeSortClassico   RANDOM       5000  avgt   15   xxxxxxx ± xxxxx  ns/op
...
```