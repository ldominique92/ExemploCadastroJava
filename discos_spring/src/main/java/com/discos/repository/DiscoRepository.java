package com.discos.repository;

import java.util.List;

import com.discos.model.Disco;

public interface DiscoRepository {

	List<Disco> findAll();

}