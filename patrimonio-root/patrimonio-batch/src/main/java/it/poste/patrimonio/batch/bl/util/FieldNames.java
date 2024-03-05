package it.poste.patrimonio.batch.bl.util;

public class FieldNames {
	
	private FieldNames() {
        throw new UnsupportedOperationException("Cannot initialize utility classes");
    }

    public static final String CONTACT_RECORD = "contactRecord";
    public static final String ISIN = "isin";
    public static final String PRICE = "price";
  

    public static String[] getFieldnames() {
        return new String[]{CONTACT_RECORD, ISIN, PRICE};
    }

}
