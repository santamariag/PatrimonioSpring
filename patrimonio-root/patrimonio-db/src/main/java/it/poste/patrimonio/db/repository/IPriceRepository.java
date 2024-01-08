package it.poste.patrimonio.db.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;


import it.poste.patrimonio.db.model.Price;

public interface IPriceRepository extends MongoRepository<Price, String> {
	
	public Optional<Price> findByIsin(String isin);
	
	public Optional<Price> findByIsinAndPrice(String isin, BigDecimal price);

}
