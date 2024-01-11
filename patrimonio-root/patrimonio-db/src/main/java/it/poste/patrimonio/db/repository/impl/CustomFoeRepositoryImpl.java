package it.poste.patrimonio.db.repository.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.db.repository.CustomFoeRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CustomFoeRepositoryImpl<T, ID> implements CustomFoeRepository{
	
	@Autowired
	private MongoTemplate template;


	@Override
	public Foe saveFoe(Foe foe) {
		foe.getPatrimonioOld().getPosizioni().forEach(p->log.debug("----------ISIN {} PRICE {} QUANTITY {} CONTR {}",p.getIsin(), p.getIprzat(), p.getQqta(), p.getIvalbas()));
		return template.save(foe);
	}

}
