package it.poste.patrimonio.batch.bl.reader;

import java.time.ZoneId;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import it.poste.patrimonio.batch.bl.util.AFBFieldNames;
import it.poste.patrimonio.itf.model.AFBBalanceDTO;

public class AFBFileRowMapper implements FieldSetMapper<AFBBalanceDTO> {

    @Override
    public AFBBalanceDTO mapFieldSet(FieldSet fieldSet) {
        return AFBBalanceDTO.builder()
                .branch(fieldSet.readString(AFBFieldNames.BRANCH))
                .agency(fieldSet.readString(AFBFieldNames.AGENCY))
                .number(fieldSet.readString(AFBFieldNames.NUMBER))
                .index("00000")
                .product("FE")
                .productId(fieldSet.readString(AFBFieldNames.ID_PRODUCT))
                .qta(fieldSet.readBigDecimal(AFBFieldNames.QTA).movePointLeft(3))
                .referenceDate(fieldSet.readDate(AFBFieldNames.REF_DATE, "yyyyMMdd").toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .ctv(fieldSet.readBigDecimal(AFBFieldNames.CTV).movePointLeft(3))
                .price(fieldSet.readBigDecimal(AFBFieldNames.PRICE).movePointLeft(6))
                .build();
    }

}