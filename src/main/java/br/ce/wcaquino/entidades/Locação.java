package br.ce.wcaquino.entidades;

import java.util.Date;
import java.util.List;

/**
 * Entidade de locação.
 *
 * @author Wagner Aquino
 * @version 1.0
 */
public class Locação {

    /**
     * Objeto do tipo <code>Usuario</code>.
     */
    private Usuario usuario;
    /**
     * Lista de objetos do tipo <code>Filme</code>.
     */
    private List<Filme> filmes;
    /**
     * Data da locação do filme.
     */
    private Date dataLocacao;
    /**
     * Data de entrega do filme.
     */
    private Date dataRetorno;
    /**
     * Valor final da locação.
     */
    private Double valor;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(Date dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public Date getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(Date dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<Filme> filmes) {
        this.filmes = filmes;
    }
}