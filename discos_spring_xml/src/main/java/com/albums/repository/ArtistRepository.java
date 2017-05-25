package com.albums.repository;

import com.albums.model.Artist;

public interface ArtistRepository {
	void create(Artist artist);
	
	Artist find(int id);
}
