package com.albums.service;

import com.albums.model.Artist;
import com.albums.repository.ArtistRepository;

public class ArtistServiceImpl implements ArtistService {

private ArtistRepository artistRepository;
	
	public ArtistServiceImpl(ArtistRepository artistRepository) 
	{
		this.artistRepository = artistRepository;
	}
	
	public void setAlbumRepository(ArtistRepository artistRepository) {
		this.artistRepository = artistRepository;
	}

	@Override
	public Artist find(int id) {
		return artistRepository.find(id);
	}

	@Override
	public void create(Artist artist) {
		artistRepository.create(artist);
		
	}
}
