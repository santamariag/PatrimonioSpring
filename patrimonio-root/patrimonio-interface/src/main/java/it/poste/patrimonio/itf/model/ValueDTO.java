package it.poste.patrimonio.itf.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ValueDTO {
	
    @JsonProperty("AUD_OBJECT")
	private String audObject;
    @JsonProperty("AUD_JOURNAL")
	private String audJournal;
    @JsonProperty("AUD_JOBNO")
	private String audJobno;
    @JsonProperty("AUD_PROGRAM")
	private String audProgram;
    @JsonProperty("AUD_ENTTYP")
	private String audEnttyp;
    @JsonProperty("AUD_USER")
	private String audUser;
    @JsonProperty("AUD_LIBRARY")
	private String audLibrary;
    @JsonProperty("AUD_TIMSTAMP")
	private String audTimstamp;
    @JsonProperty("AUD_SEQNO")
	private String audSeqno;
    @JsonProperty("AUD_CCID")
	private String audCcid;
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
    @JsonProperty("CTERINS")
	private String cterins;
    @JsonProperty("COPRINS")
	private String coprins;
    @JsonProperty("DDATINS")
	private String ddatins;
    @JsonProperty("OORAINS")
	private String oorains;
    @JsonProperty("CTERAGG")
	private String cteragg;
    @JsonProperty("COPRAGG")
	private String copragg;
    @JsonProperty("DDATAGG")
	private String ddatagg;
    @JsonProperty("OORAAGG")
	private String ooraagg;
    @JsonProperty("CSTT")
	private String cstt;
    @JsonProperty("CFOR")
	private String cfor;
    @JsonProperty("CREV")
	private String crev;
    @JsonProperty("SIMP")
	private String simp;
    @JsonProperty("CTIP")
	private String ctip;
    @JsonProperty("CAGEAPP")
	private String cageapp;
    @JsonProperty("CAGEDES")
	private String cagedes;
    @JsonProperty("CSDPORD")
	private String csdpord;
    @JsonProperty("NCDGORD")
	private String ncdgord;
    @JsonProperty("CPRM")
	private String cprm;
    @JsonProperty("CAGECNP")
	private String cagecnp;
    @JsonProperty("CPRGCNP")
	private String cprgcnp;
    @JsonProperty("CSDPCNP")
	private String csdpcnp;
    @JsonProperty("XTIT")
	private String xtit;
    @JsonProperty("DVAL")
	private String dval;
    @JsonProperty("SRVR")
	private String srvr;
    @JsonProperty("DVLDA")
	private String dvlda;
    @JsonProperty("DVLDINI")
	private String dvldini;
    @JsonProperty("DCON")
	private String dcon;
    @JsonProperty("DREG")
	private String dreg;
    @JsonProperty("OREG")
	private String oreg;
    @JsonProperty("DSPE")
	private String dspe;
    @JsonProperty("OSPE")
	private String ospe;
    @JsonProperty("CBOR")
	private String cbor;
    @JsonProperty("STRZ")
	private String strz;
    @JsonProperty("SCON")
	private String scon;
    @JsonProperty("CESE")
	private String cese;
    @JsonProperty("CTMP")
	private String ctmp;
    @JsonProperty("CQTA")
	private String cqta;
    @JsonProperty("CPRZ")
	private String cprz;
    @JsonProperty("SRAC")
	private String srac;
    @JsonProperty("OLIM")
	private String olim;
    @JsonProperty("QMIN")
	private String qmin;
    @JsonProperty("QQTA")
	private String qqta;
    @JsonProperty("QTRZ")
	private String qtrz;
    @JsonProperty("QSCO")
	private String qsco;
    @JsonProperty("NUNO")
	private String nuno;
    @JsonProperty("IPRZLIM")
	private String iprzlim;
    @JsonProperty("IPRZLIC")
	private String iprzlic;
    @JsonProperty("IPRZBAS")
	private String iprzbas;
    @JsonProperty("CDIVEMI")
	private String cdivemi;
    @JsonProperty("CDIVTRZ")
	private String cdivtrz;
    @JsonProperty("SNEGMOD")
	private String snegmod;
    @JsonProperty("CDIVREG")
	private String cdivreg;
    @JsonProperty("CNEGFIL")
	private String cnegfil;
    @JsonProperty("CNEGCAT")
	private String cnegcat;
    @JsonProperty("CNEGNUM")
	private String cnegnum;
    @JsonProperty("NCDGNEG")
	private String ncdgneg;
    @JsonProperty("DCAM")
	private String dcam;
    @JsonProperty("SCAM")
	private String scam;
    @JsonProperty("ICAM")
	private String icam;
    @JsonProperty("ICAMGIO")
	private String icamgio;
    @JsonProperty("ICAMCON")
	private String icamcon;
    @JsonProperty("ICAMPAR")
	private String icampar;
    @JsonProperty("PCOMCVE")
	private String pcomcve;
    @JsonProperty("ICOMMIN")
	private String icommin;
    @JsonProperty("ICOMMAX")
	private String icommax;
    @JsonProperty("ISPECVL")
	private String ispecvl;
    @JsonProperty("PCOSCVE")
	private String pcoscve;
    @JsonProperty("ICOSMIN")
	private String icosmin;
    @JsonProperty("ICOSMAX")
	private String icosmax;
    @JsonProperty("ISPSCVL")
	private String ispscvl;
    @JsonProperty("ISPEINE")
	private String ispeine;
    @JsonProperty("PCOMVAL")
	private String pcomval;
    @JsonProperty("CPAN")
	private String cpan;
    @JsonProperty("SPAN")
	private String span;
    @JsonProperty("CPANSUB")
	private String cpansub;
    @JsonProperty("SFSS")
	private String sfss;
    @JsonProperty("SBLL")
	private String sbll;
    @JsonProperty("PCSB")
	private String pcsb;
    @JsonProperty("NPARAVV")
	private String nparavv;
    @JsonProperty("IPARAVV")
	private String iparavv;
    @JsonProperty("SNUM")
	private String snum;
    @JsonProperty("SCOM")
	private String scom;
    @JsonProperty("SOPESCN")
	private String sopescn;
    @JsonProperty("XNOT")
	private String xnot;
    @JsonProperty("NRIFTER")
	private String nrifter;
    @JsonProperty("NRIFANN")
	private String nrifann;
    @JsonProperty("NRIFNUM")
	private String nrifnum;
    @JsonProperty("NRIFSNR")
	private String nrifsnr;
    @JsonProperty("NLEGTER")
	private String nlegter;
    @JsonProperty("NLEGANN")
	private String nlegann;
    @JsonProperty("NLEGNUM")
	private String nlegnum;
    @JsonProperty("NLEGSNR")
	private String nlegsnr;
    @JsonProperty("CFIGFIS")
	private String cfigfis;
    @JsonProperty("ICON")
	private String icon;
    @JsonProperty("PCONTAS")
	private String pcontas;
    @JsonProperty("DVALTER")
	private String dvalter;
    @JsonProperty("STASOPZ")
	private String stasopz;
    @JsonProperty("STITOPZ")
	private String stitopz;
    @JsonProperty("SITAOPZ")
	private String sitaopz;
    @JsonProperty("SDEN")
	private String sden;
    @JsonProperty("SINV")
	private String sinv;
    @JsonProperty("SBENPRL")
	private String sbenprl;
    @JsonProperty("DREGCTV")
	private String dregctv;
    @JsonProperty("SINC")
	private String sinc;
    @JsonProperty("CDIVINC")
	private String cdivinc;
    @JsonProperty("DPOS")
	private String dpos;
    @JsonProperty("SDIVINC")
	private String sdivinc;
    @JsonProperty("CFILOPE")
	private String cfilope;
    @JsonProperty("SFAC")
	private String sfac;
    @JsonProperty("CAGCNEF")
	private String cagcnef;
    @JsonProperty("CPRCNEF")
	private String cprcnef;
    @JsonProperty("ICAMREG")
	private String icamreg;
    @JsonProperty("ICAMDTR")
	private String icamdtr;
    @JsonProperty("ICAMDRG")
	private String icamdrg;
    @JsonProperty("PTASATT")
	private String ptasatt;
    @JsonProperty("NESE")
	private String nese;
    @JsonProperty("SPROFIN")
	private String sprofin;
    @JsonProperty("CPROFIN")
	private String cprofin;
    @JsonProperty("COPECDI")
	private String copecdi;
    @JsonProperty("SPRZ")
	private String sprz;
    @JsonProperty("IPRZSTL")
	private String iprzstl;
    @JsonProperty("SPRZSTL")
	private String sprzstl;
    @JsonProperty("CTITALT")
	private String ctitalt;
    @JsonProperty("ISPECVS")
	private String ispecvs;
    @JsonProperty("FNOTALL")
	private String fnotall;
    @JsonProperty("FTIPALL")
	private String ftipall;

	
}

