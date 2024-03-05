package it.poste.patrimonio.batch.bl.reader;

import java.math.BigDecimal;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import it.poste.patrimonio.batch.bl.util.FieldNames;
import it.poste.patrimonio.itf.model.PriceDTO;

public class PriceFileRowMapper implements FieldSetMapper<PriceDTO> {

	@Override
	public PriceDTO mapFieldSet(FieldSet fieldSet) {
		return PriceDTO.builder()
				.isin(fieldSet.readString(FieldNames.ISIN))
				.price(new BigDecimal(fieldSet.readString(FieldNames.PRICE)))
				.build();
	}

}
