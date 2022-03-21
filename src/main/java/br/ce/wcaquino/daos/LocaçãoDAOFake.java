package br.ce.wcaquino.daos;

import br.ce.wcaquino.entidades.Locação;

import java.util.List;

/**
 * Implementação da ‘interface’ <code>LocaçãoDAO</code>
 * @author Wanderley Drumond
 * @version 1.0
 * @since 18/03/2022
 */
public class LocaçãoDAOFake implements LocaçãoDAO{
    /**
     * Implementa, sem nenhuma ação, o método salvar.
     *
     * @param locação objeto a ser salvo.
     */
    @Override
    public void salvar(Locação locação) {}

    /**
     * Implementa, sem nenhuma ação, o método que retorna as locações pendentes.
     *
     * @return <code>null</code>.
     */
    @Override
    public List<Locação> obterLocaçõesPendentes() {
        return null;
    }
}