package it.poste.patrimonio.db.repository;


import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Meta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import it.poste.patrimonio.db.model.Titoli;

public interface ITitoliRepository extends MongoRepository<Titoli, String>, CustomTitoliRepository {
	
	@Query(value = "{ 'ndg' : ?0 and 'externalKeys.key1' : ?1 }")
	public List<Titoli> findByNdgExternaKey(String ndg, String key);
	
	public List<Titoli> findByNdgIn(List<String> ndg);
	
	@Meta(cursorBatchSize = 100)
	@Query(value = "{ 'patrimonioOld.posizioni.detail.isin' : { $in : ?0 } }")
	public Stream<Titoli> findByIsinIn(Set<String> isinList);
	
	//@Query(value = "{ 'patrimonioOld.posizioni.isin' : { $in : ?0 } }")
	//public long updatePriceOld(String isin, BigDecimal price);
	
	@Query(value = "{ 'patrimonioOld.posizioni.detail.isin' : { $in : ?0 } }")
	public Page<Titoli> findByIsinIn(Set<String> isinList, Pageable page);
	
	@Query(value = "{ $and : [ {'patrimonioOld.posizioni.detail.isin' : { $in : ?0 } }, { 'patrimonioOld.posizioni.detail.iprzat' : { $nin : ?1 } } ] }")
	public Page<Titoli> findByIsinInAndPriceNotIn(Set<String> isinList,  Collection<BigDecimal> prices, Pageable page);
	
	@Query(value = "{ 'patrimonioOld.posizioni.detail.isin' : { $in : ?0 } }", count = true)
	public Long countByIsin(Set<String> isinList);
	
}