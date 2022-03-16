package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Filme;
import org.jetbrains.annotations.NotNull;

/**
 * Objeto que cria um filme.
 * @author Wanderley Drumond
 * @since 16/03/2022
 * @version 1.0
 */
public class FilmeBuilder {
    private Filme filme;

    /**
     * Método construtor privado para somente o próprio <i>Builder</i> possa criar instâncias de si mesmo.
     */
    private FilmeBuilder() {}

    /**
     * Porta de entrada para a criação de um <code>Filme</code>.
     * <p>Este método ficou público e estático para ele poder ser acessado externamente sem haver necessidade de criar uma instância.
     *
     * @return um <code>FilmeBuilder</code> que contém um <code>Filme</code> já instanciado e inicializado.
     */
    public static @NotNull FilmeBuilder umFilme() {
        FilmeBuilder filmeBuilder = new FilmeBuilder();
        filmeBuilder.filme = new Filme();
        filmeBuilder.filme.setNome("Avengers, Infinity War");
        filmeBuilder.filme.setPrecoLocacao(5.5);
        filmeBuilder.filme.setEstoque(6);

        return filmeBuilder;
    }

    public static FilmeBuilder umFilmeSemEstoque() {
        FilmeBuilder filmeBuilder = new FilmeBuilder();
        filmeBuilder.filme = new Filme();
        filmeBuilder.filme.setNome("Avengers, Infinity War");
        filmeBuilder.filme.setPrecoLocacao(5.5);
        filmeBuilder.filme.setEstoque(0);

        return filmeBuilder;
    }

    /**
     * Atualiza o estado do objeto <code>FilmeBuilder</code>.
     * <p>Mais uma etapa que compõe o padrão <i>chained method.</i></p>
     *
     * @return uma instância de <code>FilmeBuilder</code> atualizada com estoque 0.
     */
    public FilmeBuilder semEstoque() {
        filme.setEstoque(0);
        return this;
    }

    /**
     * Retorna um <code>Filme</code> com valor atribuído.
     *
     * @return um <code>Filme</code> já instanciado e inicializado.
     */
    public Filme agora() {
        return filme;
    }
}