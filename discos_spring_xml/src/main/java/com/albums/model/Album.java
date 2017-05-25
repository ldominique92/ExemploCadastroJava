package com.albums.model;


public class Album {
	private Artist artist;
	private int id;
	private Label label;
	private String title;
	private int views;
	private int year;
	
	public Artist getArtist() {
		return artist;
	}
	
	public int getId() {
		return id;
	}
	
	public Label getLabel() {
		return label;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getViews() {
		return views;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setLabel(Label label) {
		this.label = label;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setViews(int views) {
		this.views = views;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
}
