package it.poste.patrimonio.batch.bl.util;

import org.springframework.batch.item.file.transform.Range;

import lombok.Getter;

@Getter
public enum PriceDetail {
	
	PRICE_RECORD( new Range(1,13)),
	ISIN(new Range(1, 7)),
    PRICE(new Range(8, 13));
    

    private final Range range;

    PriceDetail(Range range) {
        this.range = range;
    }

    public static Range[] getColumnRanges() {
        return new Range[]{
                PRICE_RECORD.getRange(),
                ISIN.getRange(),
                PRICE.getRange()
                };
    }

}
