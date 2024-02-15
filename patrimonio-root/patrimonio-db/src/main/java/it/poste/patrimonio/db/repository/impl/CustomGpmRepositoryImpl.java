package it.poste.patrimonio.db.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationUpdate;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.data.mongodb.core.aggregation.SetOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.UpdateResult;

import it.poste.patrimonio.db.model.gpmfoe.Gpm;
import it.poste.patrimonio.db.repository.CustomGpmRepository;



public class CustomGpmRepositoryImpl<T, ID> implements CustomGpmRepository{
	
	@Autowired
	private MongoTemplate template;

	
	@Override
	public Long updateExternalKey(String ndg, String key) {
	
		Query query=new Query().addCriteria(Criteria.where("ndg").is(ndg));
		Update updateDefinition=new Update().set("externalKeys.key1", key);
			
		UpdateResult result= template.updateMulti(query, updateDefinition, Gpm.class);
		
		return result.getModifiedCount();
		
	}
	
	
	@Override
	public long updatePriceOld(String isin, BigDecimal price) {
	
		Query query=new Query().addCriteria(Criteria.where("patrimonioOld.posizioni.detail.isin").is(isin));
		
		
		AggregationUpdate updateDefinition = AggregationUpdate.update()		
				 											.set(SetOperation.builder().set("patrimonioOld.posizioni.detail.iprzat").toValue(price).and()
				 											.set("patrimonioOld.posizioni.detail.ivalbas").toValue(ArithmeticOperators.Multiply.valueOf(price)
				 													.multiplyBy(ArithmeticOperators.Multiply.valueOf(ConvertOperators.ToDecimal.toDecimal("patrimonioOld.posizioni.detail.qqta")))));	 
		
		ArrayOperators.Filter.filter("patrimonioOld.posizioni.detail.isin").as(isin).by(isin);
		
		AggregationOperation match = Aggregation.match(Criteria.where("patrimonioOld.posizioni.detail.isin").is(isin));
		AggregationOperation unwind = Aggregation.unwind("patrimonioOld.posizioni");
		/*AggregationOperation group = Aggregation.group("_id")             
		        .first("_class").as("_class")                
		        .push("lang_content_list").as("lang_content_list");*/

		List<AggregationOperation> operations = new ArrayList<>();
		operations.add(match);
		operations.add(unwind);
		operations.add(match);
		//operations.add(group);
		Aggregation aggregation = Aggregation.newAggregation(operations);
		List<Gpm> results = template.aggregate(aggregation, Gpm.class, Gpm.class).getMappedResults();
		
		
		/*Update updateDefinition=new Update()
								.set("patrimonioOld.posizioni.$[e].iprzat", price)
								.set("patrimonioOld.posizioni.$[e].ivalbas", "$mul: { patrimonioOld.posizioni.$[e].iprzat: "+price+" }")
								//.multiply("patrimonioOld.posizioni.$[e].ivalbas", price)
								.filterArray("e.isin", isin);*/
			
		UpdateResult result= template/*.update(Gpm.class).matching(query).apply(updateDefinition).all();*/
				.updateMulti(query, updateDefinition, Gpm.class);
		
		return result.getModifiedCount();
		
	}


	@Override
	//@Retryable(include = OptimisticLockingFailureException.class)
	public Gpm saveGpm(Gpm gpm) {
		
		return template.save(gpm);
	}


	@Override
	public List<Gpm> findByKey(String fiscalCode, String productMifid, String productId) {
		
		Query query=new Query();
		query.addCriteria(Criteria.where("fiscalCode").is(fiscalCode)
				.andOperator(Criteria.where("patrimonioOld.posizioni.detail.cstrfin").is(productMifid)
				.andOperator(Criteria.where("patrimonioOld.posizioni.detail.idProd").is(productId))));
		
		return template.find(query, Gpm.class);
	}

}
