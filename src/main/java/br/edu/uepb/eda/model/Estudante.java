package br.edu.uepb.eda.model;

/**
 * Representa um estudante com nota, nome e matricula.
 *
 * Criterio de ordenacao natural (Comparable):
 *   1. Nota decrescente (maior nota primeiro)
 *   2. Nome crescente em caso de empate de nota
 *   3. Matricula crescente em caso de empate anterior
 */
public class Estudante implements Comparable<Estudante> {

    private final int matricula;
    private final String nome;
    private final int nota; // 0 a 10

    public Estudante(int matricula, String nome, int nota) {
        this.matricula = matricula;
        this.nome = nome;
        this.nota = nota;
    }

    @Override
    public int compareTo(Estudante outro) {
        // Nota decrescente: inverte a ordem natural do int
        int cmpNota = Integer.compare(outro.nota, this.nota);
        if (cmpNota != 0) return cmpNota;

        // Nome crescente
        int cmpNome = this.nome.compareTo(outro.nome);
        if (cmpNome != 0) return cmpNome;

        // Matricula crescente
        return Integer.compare(this.matricula, outro.matricula);
    }

    public int getMatricula() { return matricula; }
    public String getNome()    { return nome; }
    public int getNota()       { return nota; }

    @Override
    public String toString() {
        return "Estudante{matricula=" + matricula + ", nome='" + nome + "', nota=" + nota + "}";
    }
}