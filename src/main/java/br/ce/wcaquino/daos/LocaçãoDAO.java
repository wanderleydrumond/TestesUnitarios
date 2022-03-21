package br.ce.wcaquino.daos;

import br.ce.wcaquino.entidades.Locação;

import java.util.List;

/**
 * Contém as assinaturas dos métodos responsáveis pelas ações de CRUD.
 * @author Wanderley Drumond
 * @version 2.0
 * @since 18/03/2022
 */
public interface LocaçãoDAO {
    /**
     * Assinatura do método salvar. (Em banco, em arquivo, etc.)
     *
     * @param locação O objeto <code>Locação</code> que pretende salvar.
     */
    public void salvar(Locação locação);

    /**
     * Assinatura do método que busca lista de filmes que devem ser devolvidos.
     *
     * @return lista de filmes a serem devolvidos.
     */
    List<Locação> obterLocaçõesPendentes();
}