package com.discos.service;

import java.util.List;

import com.discos.model.Disco;
import com.discos.repository.*;

public class DiscoServiceImpl implements DiscoService {
	
	private DiscoRepository customerRepository = 
			new HibernateDiscoRepositoryImpl();
	
	/* (non-Javadoc)
	 * @see com.discos.service.DiscoService#findAll()
	 */
	@Override
	public List<Disco> findAll() {
		return customerRepository.findAll();
	}
	
}
