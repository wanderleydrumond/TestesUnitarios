package br.ce.wcaquino.entidades;

/**
 * Entidade de filme.
 *
 * @author Wagner Aquino
 * @version 1.0
 */
public class Filme {

    /**
     * Nome do filme.
     */
    private String nome;
    /**
     * Quantidade em estoque.
     */
    private Integer estoque;
    /**
     * Preço de locação.
     */
    private Double precoLocacao;

    /**
     * Construtor vazio.
     */
    public Filme() {
    }

    /**
     * Construtor cheio.
     *
     * @param nome Nome do filme
     * @param estoque Quantidade em estoque
     * @param precoLocacao Preço do exemplar
     */
    public Filme(String nome, Integer estoque, Double precoLocacao) {
        this.nome = nome;
        this.estoque = estoque;
        this.precoLocacao = precoLocacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Double getPrecoLocacao() {
        return precoLocacao;
    }

    public void setPrecoLocacao(Double precoLocacao) {
        this.precoLocacao = precoLocacao;
    }
}