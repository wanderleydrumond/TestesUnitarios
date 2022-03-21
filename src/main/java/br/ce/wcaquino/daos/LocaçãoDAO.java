package br.ce.wcaquino.daos;

import br.ce.wcaquino.entidades.Locação;

/**
 * Contém as assinaturas dos métodos responsáveis pelas ações de CRUD.
 * @author Wanderley Drumond
 * @version 1.0
 * @since 18/03/2022
 */
public interface LocaçãoDAO {
    /**
     * Chamada do método salvar. (Em banco, em arquivo, etc.)
     *
     * @param locação O objeto <code>Locação</code> que pretende salvar.
     */
    public void salvar(Locação locação);
}