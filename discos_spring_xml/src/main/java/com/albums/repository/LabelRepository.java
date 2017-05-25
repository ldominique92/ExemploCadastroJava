package com.albums.repository;

import com.albums.model.Label;;

public interface LabelRepository {
	void create(Label label);
	
	Label find(int id);
}
