package br.ce.wcaquino.entidades;

/**
 * Entidade de usuário.
 *
 * @author Wagner Costa Aquino
 * @version 1.1
 */
public class Usuario {

    /**
     * Nome do usuário.
     */
    private String nome;

    /**
     * Construtor vazio.
     */
    public Usuario() {
    }

    /**
     * Construtor cheio.
     *
     * @param nome nome do usuário.
     */
    public Usuario(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Usuario usuario)) return false;

        return nome.equals(usuario.nome);
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                '}';
    }
}