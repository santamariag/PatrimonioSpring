package it.poste.patrimonio.db.repository;

import it.poste.patrimonio.db.model.Foe;

import java.util.List;

public interface CustomFoeRepository {
	
	Foe saveFoe(Foe foe);

	List<Foe> findByKey(String rapporto, String productPrevinet, String productId);

}
