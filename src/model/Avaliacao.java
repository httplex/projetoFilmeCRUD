package model;

public class Avaliacao {
	public int id;
	public Filme filme;
	public Usuario usuario;
	public int nota;
	public String comentario;
	
	public Avaliacao(int id, Filme filme, Usuario usuario, int nota, String comentario) {
		super();
		this.id = id;
		this.filme = filme;
		this.usuario = usuario;
		this.nota = nota;
		this.comentario = comentario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}
