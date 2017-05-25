package com.albums.service;

import com.albums.model.Label;
import com.albums.repository.LabelRepository;

public class LabelServiceImpl implements LabelService {

private LabelRepository labelRepository;
	
	public LabelServiceImpl(LabelRepository labelRepository) 
	{
		this.labelRepository = labelRepository;
	}
	
	public void setAlbumRepository(LabelRepository labelRepository) {
		this.labelRepository = labelRepository;
	}

	@Override
	public Label find(int id) {
		return labelRepository.find(id);
	}

	@Override
	public void create(Label artist) {
		labelRepository.create(artist);
	}
}
