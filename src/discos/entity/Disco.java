package discos.entity;

public class Disco {
	private int id;
	private String titulo;
	private Artista artista;
	private Gravadora gravadora;
	private int ano;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Artista getArtista() {
		return artista;
	}
	
	public void setArtista(Artista idArtista) {
		this.artista = idArtista;
	}
	
	public int getAno() {
		return ano;
	}
	
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	public Gravadora getGravadora() {
		return gravadora;
	}
	
	public void setGravadora(Gravadora gravadora) {
		this.gravadora = gravadora;
	}
}
