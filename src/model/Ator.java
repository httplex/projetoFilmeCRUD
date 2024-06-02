package model;

import java.time.LocalDate;

public class Ator {
	private int id;
	private String nome;
	private LocalDate data_nascimento;
	
	public Ator(int id, String nome, LocalDate data_nascimento) {
		super();
		this.id = id;
		this.nome = nome;
		this.data_nascimento = data_nascimento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(LocalDate data_nascimento) {
		this.data_nascimento = data_nascimento;
	}
	
	
}
