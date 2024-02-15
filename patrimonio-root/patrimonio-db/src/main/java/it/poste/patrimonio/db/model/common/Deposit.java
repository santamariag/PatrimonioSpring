package it.poste.patrimonio.db.model.common;

import lombok.Data;

@Data
public class Deposit {
	
	private String branch;
	private String agency;
	private String number;
	private String index; //rubrica

}
