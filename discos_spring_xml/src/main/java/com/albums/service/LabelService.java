package com.albums.service;

import com.albums.model.Label;;

public interface LabelService {

	Label find(int id);

	void create(Label artist);
}