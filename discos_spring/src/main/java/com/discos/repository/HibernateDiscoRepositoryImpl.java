package com.discos.repository;

import java.util.*;
import com.discos.model.*;

public class HibernateDiscoRepositoryImpl implements DiscoRepository {
	
	/* (non-Javadoc)
	 * @see com.discos.repository.DiscoRepository#findAll()
	 */
	@Override
	public List<Disco> findAll()
	{
		List<Disco> discos = new ArrayList<Disco>();
		
		Disco disco = new Disco();
		disco.setTitulo("Rumours");
		disco.setAno(1977);
		disco.setArtista(new Artista());
		disco.getArtista().setNome("Fleetwood Mac");
		disco.setGravadora(new Gravadora());
		disco.getGravadora().setNome("Warner Bros.");
		
		discos.add(disco);
		
		return discos;
	}
}
