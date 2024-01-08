package it.poste.patrimonio.batch.bl;


import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.support.SynchronizedItemStreamWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import it.poste.patrimonio.batch.bl.config.FileConfig;
import it.poste.patrimonio.batch.bl.listener.MoveFileListener;
import it.poste.patrimonio.batch.bl.processor.PriceDTOToPriceProcessor;
import it.poste.patrimonio.batch.bl.reader.PriceFileRowMapper;
import it.poste.patrimonio.batch.bl.util.FieldNames;
import it.poste.patrimonio.batch.bl.util.PriceDetail;
import it.poste.patrimonio.batch.bl.writer.PriceItemWriter;
import it.poste.patrimonio.db.model.Price;
import it.poste.patrimonio.db.repository.IGpmRepository;
import it.poste.patrimonio.db.repository.IPriceRepository;
import it.poste.patrimonio.itf.mapper.PriceMapper;
import it.poste.patrimonio.itf.model.PriceDTO;
import lombok.extern.slf4j.Slf4j;


@Configuration
//@EnableAsync
@Slf4j
public class BusinessConfig {

	public static final String MOVE_FILE_LISTENER_QUALIF = "moveFileListener";
	public static final String PRICE_FILE_PROCESSOR_QUALIF = "priceFileProcessor";
	public static final String PRICE_FILE_WRITER_QUALIF = "priceFileWriter";
	public static final String PRICE_FILE_SYNC_WRITER_QUALIF = "priceFileSyncWriter";
	public static final String PRICE_FILE_CSV_READER_QUALIF = "priceFileCsvReader";
	public static final String PRICE_FILE_FIXED_READER_QUALIF = "priceFileFixedReader";


	@Autowired
    private IGpmRepository gpmRepository;
	@Autowired
    private IPriceRepository priceRepository;  
    @Autowired
	private FileConfig fileConfig;
    
    @Bean
    @Qualifier(MOVE_FILE_LISTENER_QUALIF)
    public MoveFileListener jobListener() {
    	
    	return new MoveFileListener();
    }

   
    
    @Bean
    @Qualifier(PRICE_FILE_PROCESSOR_QUALIF)
	public PriceDTOToPriceProcessor priceProcessor(PriceMapper priceMapper, IPriceRepository priceRepository) {
		return new PriceDTOToPriceProcessor(priceMapper, priceRepository);
    }

    

    @Bean
    @Qualifier(PRICE_FILE_WRITER_QUALIF)
    public PriceItemWriter writer() {
        return new PriceItemWriter(priceRepository, gpmRepository);
    }
    
    
    @Bean
    @Qualifier(PRICE_FILE_SYNC_WRITER_QUALIF)
    public SynchronizedItemStreamWriter<Price> syncWriter() {

        SynchronizedItemStreamWriter<Price> synchronizedWriter = new SynchronizedItemStreamWriter<>();
        synchronizedWriter.setDelegate(writer());

        return synchronizedWriter;
    }
 
    @Bean
    @Qualifier(PRICE_FILE_CSV_READER_QUALIF)
    @StepScope
    public FlatFileItemReader<PriceDTO> flatFileItemReader(
            @Value("#{stepExecutionContext[partition_number]}") final Long partitionNumber,
            @Value("#{stepExecutionContext[first_line]}") final Long firstLine,
            @Value("#{stepExecutionContext[last_line]}") final Long lastLine) {

 
        log.info("Partition Number : {}, Reading file from line : {}, to line: {} ", partitionNumber, firstLine!=null?firstLine+1:null, lastLine);

        FlatFileItemReader<PriceDTO> reader = new FlatFileItemReader<>();
        if(firstLine!=null)
        	reader.setLinesToSkip(Math.toIntExact(firstLine));
        if(lastLine!=null)
        	reader.setMaxItemCount(Math.toIntExact(lastLine));
        
        reader.setStrict(false);
        reader.setResource(new FileSystemResource(fileConfig.getProcessingPath().concat(System.getProperty("file.separator")).concat(fileConfig.getFileName())));
        
        
        reader.setLineMapper(new DefaultLineMapper<PriceDTO>() {
            {
               setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("isin", "price");
                    }
                });

                setFieldSetMapper(new BeanWrapperFieldSetMapper<PriceDTO>() {
                    {
                        setTargetType(PriceDTO.class);
                    }
                });

            }
        });
        
        return reader;
    }
    
    private FieldSetMapper<PriceDTO> fieldSetMapper() {
    	
    	return new PriceFileRowMapper();
    }
    
    
    private FixedLengthTokenizer lineTokenizer() {
        final var lineTokenizer = new FixedLengthTokenizer();
        lineTokenizer.setNames(FieldNames.getFieldnames());
        lineTokenizer.setColumns(PriceDetail.getColumnRanges());
        lineTokenizer.setStrict(false);
        return lineTokenizer;
    }
    
    @Bean
    @Qualifier(PRICE_FILE_FIXED_READER_QUALIF)
    @StepScope
    public FlatFileItemReader<PriceDTO> flatFileItemReaderFixed(
            @Value("#{stepExecutionContext[partition_number]}") final Long partitionNumber,
            @Value("#{stepExecutionContext[first_line]}") final Long firstLine,
            @Value("#{stepExecutionContext[last_line]}") final Long lastLine) {

        log.info("Partition Number : {}, Reading file from line : {}, to line: {} ", partitionNumber, firstLine!=null?firstLine+1:null, lastLine);

        FlatFileItemReader<PriceDTO> reader = new FlatFileItemReader<>();
        if(firstLine!=null)
        	reader.setLinesToSkip(Math.toIntExact(firstLine));
        if(lastLine!=null)
        	reader.setMaxItemCount(Math.toIntExact(lastLine));
        reader.setResource(new FileSystemResource(fileConfig.getProcessingPath().concat(System.getProperty("file.separator")).concat(fileConfig.getFileName())));
        reader.setStrict(false);
        reader.setLineMapper(new DefaultLineMapper<PriceDTO>() {
            {
               setLineTokenizer(lineTokenizer());

               setFieldSetMapper(fieldSetMapper());

            }
        });
        return reader;
    }
}