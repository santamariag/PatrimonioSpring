package it.poste.patrimonio.batch.bl.reader;

import java.math.BigDecimal;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import it.poste.patrimonio.batch.bl.util.AFBFieldNames;
import it.poste.patrimonio.itf.model.AFBBalanceDTO;

public class AFBFileRowMapper implements FieldSetMapper<AFBBalanceDTO> {

	@Override
	public AFBBalanceDTO mapFieldSet(FieldSet fieldSet) {
		return AFBBalanceDTO.builder()
				.number(fieldSet.readString(AFBFieldNames.NUMBER))
				.agency(fieldSet.readString(AFBFieldNames.AGENCY))
				.branch(fieldSet.readString(AFBFieldNames.BRANCH))
				.ctv(new BigDecimal(fieldSet.readString(AFBFieldNames.CTV)))
				.price(new BigDecimal(fieldSet.readString(AFBFieldNames.PRICE)))
				.productId(fieldSet.readString(AFBFieldNames.ID_PRODUCT))
				.qta(new BigDecimal(fieldSet.readString(AFBFieldNames.QTA)))
				.referenceDate(fieldSet.readString(AFBFieldNames.REF_DATE))
				.build();
	}

}
