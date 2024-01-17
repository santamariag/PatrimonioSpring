package it.poste.patrimonio.db.repository.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import it.poste.patrimonio.db.model.Titoli;
import it.poste.patrimonio.db.repository.CustomTitoliRepository;



public class CustomTitoliRepositoryImpl<T, ID> implements CustomTitoliRepository{
	
	@Autowired
	private MongoTemplate template;


	@Override
	public Titoli saveTitoli(Titoli titoli) {
		
		return template.save(titoli);
	}


}
