package it.poste.patrimonio.batch.bl.afb;

import it.poste.patrimonio.batch.bl.config.FileConfig;
import it.poste.patrimonio.batch.bl.listener.FileNameListener;
import it.poste.patrimonio.batch.bl.listener.MoveFileListener;
import it.poste.patrimonio.batch.bl.processor.AFBBalanceToFoeProcessor;
import it.poste.patrimonio.batch.bl.reader.AFBFileRowMapper;
import it.poste.patrimonio.batch.bl.util.*;
import it.poste.patrimonio.batch.bl.writer.AFBItemWriter;
import it.poste.patrimonio.db.repository.IFoeRepository;
import it.poste.patrimonio.itf.model.AFBBalanceDTO;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;

@Configuration
@Profile("foe-afb")
public class FoeAFBBusinessConfig {
	
	public static final String MOVE_FILE_LISTENER_QUALIF = "moveFileListener";
	public static final String AFB_FILE_NAME_LISTENER_QUALIF = "afbFileNameListener";
	public static final String AFB_FILE_PROCESSOR_QUALIF = "afbFileProcessor";
	public static final String AFB_FILE_WRITER_QUALIF = "afbFileWriter";
	public static final String AFB_FILE_CSV_READER_QUALIF = "afbFileCsvReader";


	@Autowired
    private IFoeRepository foeRepository; 
    @Autowired
	private FileConfig fileConfig;
    
    @Bean
    @Qualifier(MOVE_FILE_LISTENER_QUALIF)
    public MoveFileListener jobListener() {
    	
    	return new MoveFileListener();
    }
    
    @Bean
    @Qualifier(AFB_FILE_NAME_LISTENER_QUALIF)
    public FileNameListener fileNameListener() {
    	
    	return new FileNameListener(FileType.AFB);
    }

    @Bean
    @Qualifier(AFB_FILE_PROCESSOR_QUALIF)
    @StepScope
	public AFBBalanceToFoeProcessor afbProcessor() {
		return new AFBBalanceToFoeProcessor(foeRepository);
    }
    

    @Bean
    @Qualifier(AFB_FILE_WRITER_QUALIF)
    public AFBItemWriter writer() {
        return new AFBItemWriter(foeRepository);
    }

    private FixedLengthTokenizer lineTokenizer() {
        final var lineTokenizer = new FixedLengthTokenizer();
        lineTokenizer.setNames(AFBFieldNames.getFieldnames());
        lineTokenizer.setColumns(AFBDetail.getColumnRanges());
        lineTokenizer.setStrict(false);
        return lineTokenizer;
       
    }
    
 private FieldSetMapper<AFBBalanceDTO> fieldSetMapper() {
    	
    	return new AFBFileRowMapper();
    }
    
    @Bean
    @Qualifier(AFB_FILE_CSV_READER_QUALIF)
    @StepScope
    public FlatFileItemReader<AFBBalanceDTO> flatFileItemReader(@Value("#{jobExecutionContext[filename]}") final String filename) {

        FlatFileItemReader<AFBBalanceDTO> reader = new FlatFileItemReader<>();
        
        reader.setStrict(false);
        reader.setResource(new FileSystemResource(fileConfig.getProcessingPath().concat(System.getProperty("file.separator")).concat(filename)));
        

        reader.setLineMapper(new DefaultLineMapper<AFBBalanceDTO>() {
            {
               setLineTokenizer(lineTokenizer());
               setFieldSetMapper(fieldSetMapper());
            
            }
        });
        
        return reader;
    }

}
