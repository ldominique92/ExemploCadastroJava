package com.albums.service;

import java.util.List;

import com.albums.model.Album;
import com.albums.repository.AlbumRepository;

public class AlbumServiceImpl implements AlbumService {
	
	private AlbumRepository albumRepository;
	
	public AlbumServiceImpl(AlbumRepository albumRepository) 
	{
		this.albumRepository = albumRepository;
	}
	
	@Override
	public void createBatch(List<Album> albums) {
		albumRepository.createBatch(albums);
		
	}

	@Override
	public void delete(int id) {
		albumRepository.delete(id);
		
	}

	@Override
	public void edit(Album album) {
		albumRepository.edit(album);
		
	}

	@Override
	public List<Album> filter(int artistId, int labelId) {
		return albumRepository.filter(artistId, labelId);
	}

	@Override
	public Album find(int id) {
		return albumRepository.find(id);
	}

	@Override
	public List<Album> findAll() {
		return albumRepository.findAll();
	}

	public void setAlbumRepository(AlbumRepository albumRepository) {
		this.albumRepository = albumRepository;
	}
	
}
