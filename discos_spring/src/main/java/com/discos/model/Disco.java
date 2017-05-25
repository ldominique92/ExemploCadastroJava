package com.discos.model;


public class Disco {
	private int ano;
	private Artista artista;
	private Gravadora gravadora;
	private int id;
	private String titulo;
	private int visualizacoes;
	
	public int getAno() {
		return ano;
	}
	
	public Artista getArtista() {
		return artista;
	}
	
	public Gravadora getGravadora() {
		return gravadora;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public int getVisualizacoes() {
		return visualizacoes;
	}
	
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	public void setArtista(Artista artista) {
		this.artista = artista;
	}
	
	public void setGravadora(Gravadora gravadora) {
		this.gravadora = gravadora;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public void setVisualizacoes(int visualizacoes) {
		this.visualizacoes = visualizacoes;
	} 
}
