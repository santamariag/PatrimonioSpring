package it.poste.patrimonio.batch.bl.reader;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import it.poste.patrimonio.batch.bl.util.AFBFieldNames;
import it.poste.patrimonio.itf.model.AFBBalanceDTO;

public class AFBFileRowMapper implements FieldSetMapper<AFBBalanceDTO> {

    private final DateTimeFormatter dateFormatter_yyyyMMdd = DateTimeFormatter.ofPattern( "yyyyMMdd" );
    @Override
    public AFBBalanceDTO mapFieldSet(FieldSet fieldSet) {
        return AFBBalanceDTO.builder()
                .branch(fieldSet.readString(AFBFieldNames.BRANCH))
                .agency(fieldSet.readString(AFBFieldNames.AGENCY))
                .number(fieldSet.readString(AFBFieldNames.NUMBER))
                .index("00000")
                .product("FE")
                .qta(new BigDecimal(fieldSet.readString(AFBFieldNames.QTA)).movePointLeft(3))
                .referenceDate(LocalDate.parse(fieldSet.readString(AFBFieldNames.REF_DATE), dateFormatter_yyyyMMdd))
                .ctv(new BigDecimal(fieldSet.readString(AFBFieldNames.CTV)).movePointLeft(3))
                .price(new BigDecimal(fieldSet.readString(AFBFieldNames.PRICE)).movePointLeft(6))
                .build();
    }

}