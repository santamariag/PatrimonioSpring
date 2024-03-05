package it.poste.patrimonio.batch.bl.listener;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;

public class StepExecutionContextInjector implements StepExecutionListener {

	private ExecutionContext stepExecutionCtx;

	
	@Override
	public void beforeStep(final StepExecution stepExecution) {
		this.stepExecutionCtx = stepExecution.getExecutionContext();

	}


	public ExecutionContext getStepExecutionCtx() {
		return this.stepExecutionCtx;
	}


}
