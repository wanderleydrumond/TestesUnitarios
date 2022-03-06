package br.ce.wcaquino.entidades;

public class Usuario {

	private String nome;
	
	public Usuario() {}
	
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
}