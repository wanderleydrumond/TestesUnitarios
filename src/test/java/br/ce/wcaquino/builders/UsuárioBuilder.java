package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Usuario;
import org.jetbrains.annotations.NotNull;

/**
 * Objeto que cria um usuário.
 * @author Wanderley Drumond
 * @since 16/03/2022
 * @version 1.0
 */
public class UsuárioBuilder {
    private Usuario usuario;

    /**
     * Método construtor privado para somente o próprio <i>Builder</i> possa criar instâncias de si mesmo.
     */
    private UsuárioBuilder() {
    }

    /**
     * Porta de entrada para a criação de um <code>Usuario</code>.
     * <p>Este método ficou público e estático para ele poder ser acessado externamente sem haver necessidade de criar uma instância.
     *
     * @return um <code>UsuarioBuilder</code> que contém um <code>Usuario</code> já instanciado e inicializado.
     */
    public static @NotNull UsuárioBuilder umUsuário() {
        UsuárioBuilder usuárioBuilder = new UsuárioBuilder();
        usuárioBuilder.usuario = new Usuario();
        usuárioBuilder.usuario.setNome("Wanderley");
        return usuárioBuilder;
    }

    /**
     * Retorna um <code>Usuario</code> com valor atribuído.
     *
     * @return um <code>Usuario</code> já instanciado e inicializado.
     */
    public Usuario agora() {
        return usuario;
    }
}