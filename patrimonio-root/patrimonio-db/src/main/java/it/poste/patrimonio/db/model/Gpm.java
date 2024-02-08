package it.poste.patrimonio.db.model;


import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "gpms")
@CompoundIndexes({
    @CompoundIndex(name = "idx-ndg_prod_idprod", def = "{'ndg' : 1, 'patrimonioOld.posizioni.cstrfin': 1, 'patrimonioOld.posizioni.idProd': 1}"),
    @CompoundIndex(name = "idx-fiscalcode_prod_idprod", def = "{'fiscalCode' : 1, 'patrimonioOld.posizioni.cstrfin': 1, 'patrimonioOld.posizioni.idProd': 1}")
})
public class Gpm extends CommonData{
	

}
