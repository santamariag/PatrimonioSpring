package it.poste.patrimonio.db.repository;

import java.math.BigDecimal;
import java.util.List;

import it.poste.patrimonio.db.model.gpmfoe.Gpm;

public interface CustomGpmRepository {
	
	public Long updateExternalKey(String ndg, String key);
	
	public long updatePriceOld(String isin, BigDecimal price);
	
	public Gpm saveGpm(Gpm dpm);
	
	public List<Gpm> findByKey(String fiscalCode, String productMifid, String productId);
	

}
