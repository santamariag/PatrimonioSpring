package it.poste.patrimonio.batch.bl.mfm;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;

import it.poste.patrimonio.batch.bl.config.FileConfig;
import it.poste.patrimonio.batch.bl.listener.FileNameListener;
import it.poste.patrimonio.batch.bl.listener.MoveFileListener;
import it.poste.patrimonio.batch.bl.listener.StepExecutionContextInjector;
import it.poste.patrimonio.batch.bl.processor.MFMBalanceToGpmProcessor;
import it.poste.patrimonio.batch.bl.util.FileType;
import it.poste.patrimonio.batch.bl.writer.MFMItemWriter;
import it.poste.patrimonio.db.repository.IGpmRepository;
import it.poste.patrimonio.itf.model.MFMBalanceDTO;

@Configuration
@Profile("gpm-mfm")
public class GpmMFMBusinessConfig {
	
	public static final String MOVE_FILE_LISTENER_QUALIF = "moveFileListener";
	public static final String MFM_FILE_NAME_LISTENER_QUALIF = "mfmFileNameListener";
	public static final String MFM_FILE_PROCESSOR_QUALIF = "mfmFileProcessor";
	public static final String MFM_FILE_WRITER_QUALIF = "mfmFileWriter";
	public static final String MFM_FILE_CSV_READER_QUALIF = "mfmFileCsvReader";
	public static final String STEP_CONTEXT_LISTENER = "stepContextListener";


	@Autowired
    private IGpmRepository gpmRepository; 
    @Autowired
	private FileConfig fileConfig;
    
    @Bean
    @Qualifier(MOVE_FILE_LISTENER_QUALIF)
    public MoveFileListener jobListener() {
    	
    	return new MoveFileListener();
    }
    
    @Bean
    @Qualifier(MFM_FILE_NAME_LISTENER_QUALIF)
    public FileNameListener fileNameListener() {
    	
    	return new FileNameListener(FileType.MFM);
    }
    
    @Bean
    @Qualifier(STEP_CONTEXT_LISTENER)
    public StepExecutionContextInjector stepListener() {
    	
    	return new StepExecutionContextInjector();
    }

    @Bean
    @Qualifier(MFM_FILE_PROCESSOR_QUALIF)
    @StepScope
	public MFMBalanceToGpmProcessor mfmProcessor() {
		return new MFMBalanceToGpmProcessor(gpmRepository);
    }
    

    @Bean
    @Qualifier(MFM_FILE_WRITER_QUALIF)
    public MFMItemWriter writer() {
        return new MFMItemWriter(gpmRepository);
    }
    
    @Bean
    @Qualifier(MFM_FILE_CSV_READER_QUALIF)
    @StepScope
    public FlatFileItemReader<MFMBalanceDTO> flatFileItemReader(@Qualifier(STEP_CONTEXT_LISTENER)StepExecutionContextInjector stepListener,
    		@Value("#{jobExecutionContext[filename]}") final String filename) {

        FlatFileItemReader<MFMBalanceDTO> reader = new FlatFileItemReader<>();
        
        reader.setStrict(false);
        reader.setResource(new FileSystemResource(fileConfig.getProcessingPath().concat(System.getProperty("file.separator")).concat(filename)));
        
        reader.setLinesToSkip(1);
        reader.setSkippedLinesCallback(new LineCallbackHandler() {
			
			@Override
			public void handleLine(String line) {
				
				String referenceDate=line.split(",")[3];
				stepListener.getStepExecutionCtx().put("referenceDate", referenceDate);
	                       			
			}
		});
        reader.setLineMapper(new DefaultLineMapper<MFMBalanceDTO>() {
            {
               setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                    	setNames("fiscalCode", "productId", "qtaSub", "qtaRef", "ctv", "product");
                        setIncludedFields(0, 1, 3, 4, 5, 11);
                    }
                });

                setFieldSetMapper(new BeanWrapperFieldSetMapper<MFMBalanceDTO>() {
                    {
                        setTargetType(MFMBalanceDTO.class);
                    }
                });

            }
        });
        
        return reader;
    }

}
