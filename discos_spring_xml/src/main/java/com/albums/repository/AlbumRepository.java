package com.albums.repository;

import java.util.List;
import com.albums.model.Album;

public interface AlbumRepository {

	void createBatch(List<Album> album);
	
	void delete(int id);
	
	void edit(Album album);
	
	List<Album> filter(int artistId, int labelId);
	
	Album find(int id);
	
	List<Album> findAll();

}