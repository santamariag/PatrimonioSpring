package it.poste.patrimonio.batch.bl.processor;

import java.time.LocalDate;
import java.util.List;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import it.poste.patrimonio.bl.util.BusinessLogicUtil;
import it.poste.patrimonio.db.model.Gpm;
import it.poste.patrimonio.db.repository.IGpmRepository;
import it.poste.patrimonio.itf.model.MFMBalanceDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MFMBalanceToGpmProcessor implements ItemProcessor<MFMBalanceDTO, List<Gpm>>{
	@Autowired
	BusinessLogicUtil util;

	@Value("#{stepExecution}")
	private StepExecution stepExecution;

	private final IGpmRepository gpmRepository;

	@Autowired
	private BusinessLogicUtil businessLogicUtil;


	public MFMBalanceToGpmProcessor(IGpmRepository gpmRepository) {

		this.gpmRepository = gpmRepository;
	}

	public void setStepExecution(final StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}



	@Override
	public List<Gpm> process(MFMBalanceDTO item) throws Exception {

		List<Gpm> gpmList = util.processGpm(item);
		return gpmList;
	}
	/*private String retrieveProductMifid(String product) {
		// Invoke external service. For now retrieve the same value
		return product;
	}
*/
}
