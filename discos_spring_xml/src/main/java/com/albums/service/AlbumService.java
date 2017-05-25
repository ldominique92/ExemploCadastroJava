package com.albums.service;

import java.util.List;

import com.albums.model.Album;

public interface AlbumService {

	List<Album> findAll();
	
	Album find(int id);
	
	void createBatch(List<Album> album);
	
	void edit(Album album);
	
	void delete(int id);
	
	List<Album> filter(int artistId, int labelId);
}