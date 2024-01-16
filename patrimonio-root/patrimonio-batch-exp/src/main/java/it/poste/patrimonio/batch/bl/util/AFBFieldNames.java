package it.poste.patrimonio.batch.bl.util;


public class AFBFieldNames {

	private AFBFieldNames() {
        throw new UnsupportedOperationException("Cannot initialize utility classes");
    }

    public static final String AFB_RECORD = "afbRecord";
    public static final String BRANCH = "branch";
    public static final String AGENCY = "agency";
    public static final String NUMBER = "number";
    public static final String INDEX = "index";
    public static final String PRODUCT = "product";
    public static final String ID_PRODUCT ="productId";
    public static final String QTA ="qta";
    public static final String REF_DATE = "referenceDate";
    public static final String CTV = "ctv";
    public static final String PRICE ="price";
    
    public static String[] getFieldnames() {
        return new String[]{AFB_RECORD, BRANCH, AGENCY, NUMBER, ID_PRODUCT, QTA, REF_DATE, CTV, PRICE};
    }

}
