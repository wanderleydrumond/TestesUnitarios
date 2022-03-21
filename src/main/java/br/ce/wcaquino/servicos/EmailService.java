package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Usuario;

/**
 * ‘Interface’ que contém os métodos de serviço de e-mail.
 *
 * @author Wanderley Drumond
 * @since 21/02/2022
 * @version 1.0
 */
public interface EmailService {
    /**
     * Notifica o usuário em questão sobre o atraso na entrega da lista de filmes.
     *
     * @param usuario Usuário que será notificado.
     */
    public void notificarAtraso(Usuario usuario);
}