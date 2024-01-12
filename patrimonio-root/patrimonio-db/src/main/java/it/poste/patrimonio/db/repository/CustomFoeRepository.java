package it.poste.patrimonio.db.repository;

import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.db.model.Gpm;

import java.util.List;

public interface CustomFoeRepository {
	
	Foe saveFoe(Foe foe);

	List<Foe> findByKey(String fiscalCode, String productPrevinet, String productId);

}
