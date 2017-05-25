package com.albums.service;

import com.albums.model.Artist;;

public interface ArtistService {

	Artist find(int id);

	void create(Artist artist);
}