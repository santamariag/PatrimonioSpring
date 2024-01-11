package it.poste.patrimonio.db.repository.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import it.poste.patrimonio.db.model.Titoli;
import it.poste.patrimonio.db.repository.CustomTitoliRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CustomTitoliRepositoryImpl<T, ID> implements CustomTitoliRepository{
	
	@Autowired
	private MongoTemplate template;


	@Override
	public Titoli saveTitoli(Titoli titoli) {
		titoli.getPatrimonioOld().getPosizioni().forEach(p->log.debug("----------ISIN {} PRICE {} QUANTITY {} CONTR {}",p.getIsin(), p.getIprzat(), p.getQqta(), p.getIvalbas()));
		return template.save(titoli);
	}


}
