package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Usuario;

/**
 * Interface que possui os contratos dos serviços relativos ao SPC.
 * @author Wanderley Drumond
 * @since 21/03/2022
 * @version 1.0
 */
public interface SPCService {
    /**
     * Responde se o <code>Usuario</code> está negativado ou não.
     *
     * @param usuario <code>Usuario</code> para o qual a situação deve ser conferida.
     * @return Valor <code>boolean</code> contendo a resposta caso o mesmo esteja negativado ou não.
     */
    public boolean possuiNegativação(Usuario usuario);
}