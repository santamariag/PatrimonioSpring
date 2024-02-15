package it.poste.patrimonio.db.model;


import it.poste.patrimonio.db.model.gpmfoe.CommonDocument;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "foes")
@CompoundIndexes({
    @CompoundIndex(name = "idx-ndg_prod_idprod", def = "{'ndg' : 1, 'patrimonioOld.posizioni.cstrfin': 1, 'patrimonioOld.posizioni.idProd': 1}"),
    @CompoundIndex(name = "idx-rapporto_prod_idprod", def = "{'rapporto' : 1, 'patrimonioOld.posizioni.cstrfin': 1, 'patrimonioOld.posizioni.idProd': 1}")
})
public class Foe extends CommonDocument {
	
}
