package it.poste.patrimonio.batch.bl.processor;

import it.poste.patrimonio.bl.util.BusinessLogicUtil;
import it.poste.patrimonio.db.model.Foe;
import it.poste.patrimonio.db.repository.IFoeRepository;
import it.poste.patrimonio.itf.model.AFBBalanceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Slf4j
public class AFBBalanceToFoeProcessor implements ItemProcessor<AFBBalanceDTO, List<Foe>>{


	@Value("#{stepExecution}")
	private StepExecution stepExecution;

	private final IFoeRepository foeRepository;

	@Autowired
	private BusinessLogicUtil businessLogicUtil;


	public AFBBalanceToFoeProcessor(IFoeRepository foeRepository) {

		this.foeRepository = foeRepository;
	}

	public void setStepExecution(final StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}



	@Override
	public List<Foe> process(AFBBalanceDTO item) throws Exception {
		List<Foe> foeList = businessLogicUtil.processFoe(item);
		return foeList;

	}

}
