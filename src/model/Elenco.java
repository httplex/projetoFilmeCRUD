package model;

public class Elenco {
	private int id;
	private Filme filme;
	private Ator ator;
	private String papel;
	
	public Elenco(int id, Filme filme, Ator ator, String papel) {
		super();
		this.id = id;
		this.filme = filme;
		this.ator = ator;
		this.papel = papel;
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

	public Ator getAtor() {
		return ator;
	}

	public void setAtor(Ator ator) {
		this.ator = ator;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}
}
