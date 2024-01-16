package it.poste.patrimonio.batch.bl.util;

import lombok.Getter;
import org.springframework.batch.item.file.transform.Range;

@Getter
public enum AFBDetail {

	AFB_RECORD( new Range(1,340)),
	BRANCH(new Range(1, 5)),
    AGENCY(new Range(6, 9)),
    NUMBER(new Range(10, 20)),
    ID_PRODUCT(new Range(21, 32)),
    QTA(new Range(33, 50)), //15 PARTE INTERA 3 DECIMALE
    REF_DATE(new Range(96, 103)),
    CTV(new Range(136, 153)), //15 PARTE INTERA 3 DECIMALE
    PRICE(new Range(302, 319)); //12 INTERA 6 DECIMALE
    
    private final Range range;

    AFBDetail(Range range) {
        this.range = range;
    }

    public static Range[] getColumnRanges() {
        return new Range[]{
                AFB_RECORD.getRange(),
                BRANCH.getRange(),
                AGENCY.getRange(),
                NUMBER.getRange(),
                ID_PRODUCT.getRange(),
                QTA.getRange(),
                REF_DATE.getRange(),
                CTV.getRange(),
                PRICE.getRange()
                };
    }

}
