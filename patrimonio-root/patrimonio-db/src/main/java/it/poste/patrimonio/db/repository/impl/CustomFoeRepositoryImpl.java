package it.poste.patrimonio.db.repository.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.db.repository.CustomFoeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;


@Slf4j
public class CustomFoeRepositoryImpl<T, ID> implements CustomFoeRepository{
	
	@Autowired
	private MongoTemplate template;


	@Override
	public Foe saveFoe(Foe foe) {
		foe.getPatrimonioOld().getPosizioni().forEach(p->log.debug("----------ISIN {} PRICE {} QUANTITY {} CONTR {}",p.getIsin(), p.getIprzat(), p.getQqta(), p.getIvalbas()));
		return template.save(foe);
	}

	@Override
	public List<Foe> findByKey(String rapporto, String productPrevinet, String productId) {
		Query query=new Query();
		query.addCriteria(Criteria.where("rapporto").is(rapporto)
				.andOperator(Criteria.where("patrimonioOld.posizioni.cstrfin").is(productPrevinet)
						.andOperator(Criteria.where("patrimonioOld.posizioni.idProd").is(productId))));

		return template.find(query, Foe.class);
	}

}
