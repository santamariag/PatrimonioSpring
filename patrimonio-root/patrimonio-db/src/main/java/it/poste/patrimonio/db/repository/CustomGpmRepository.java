package it.poste.patrimonio.db.repository;

import java.math.BigDecimal;

import it.poste.patrimonio.db.model.Gpm;

public interface CustomGpmRepository {
	
	public Long updateExternalKey(String ndg, String key);
	
	public long updatePriceOld(String isin, BigDecimal price);
	
	public Gpm saveGpm(Gpm dpm);
	

}
