package it.poste.patrimonio.itf.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KeyDTO{
	
    @JsonProperty("CIST")
	private String cist;
    @JsonProperty("CFIL")
	private String cfil;
    @JsonProperty("CAGE")
	private String cage;
    @JsonProperty("CPRG")
	private String cprg;
    @JsonProperty("CSDP")
	private String csdp;
    @JsonProperty("CTIT")
	private String ctit;
    @JsonProperty("DVLDDA")
	private String dvldda;
    @JsonProperty("CCAU")
	private String ccau;
    @JsonProperty("NREGTER")
	private String nregter;
    @JsonProperty("NREGANN")
	private String nregann;
    @JsonProperty("NREGNUM")
	private String nregnum;
    @JsonProperty("NREGSNR")
	private String nregsnr;
    @JsonProperty("CSTT")
	private String cstt;

	
}

