package it.poste.patrimonio.db.repository.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import it.poste.patrimonio.db.model.gpmfoe.Foe;
import it.poste.patrimonio.db.repository.CustomFoeRepository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;


public class CustomFoeRepositoryImpl<T, ID> implements CustomFoeRepository{
	
	@Autowired
	private MongoTemplate template;


	@Override
	public Foe saveFoe(Foe foe) {
		return template.save(foe);
	}

	@Override
	public List<Foe> findByKey(String rapporto, String productPrevinet, String productId) {
		Query query=new Query();
		query.addCriteria(Criteria.where("rapporto").is(rapporto)
				.andOperator(Criteria.where("patrimonioOld.posizioni.detail.cstrfin").is(productPrevinet)
						.andOperator(Criteria.where("patrimonioOld.posizioni.detail.idProd").is(productId))));

		return template.find(query, Foe.class);
	}

}
