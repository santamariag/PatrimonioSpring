package it.poste.patrimonio.batch.bl.partitioner;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.poste.patrimonio.batch.bl.config.FileConfig;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class CsvStepPartitioner implements Partitioner {
	
	@Autowired
	private FileConfig fileConfig;
	
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> result = new HashMap<>();

        int noOfLines = 0;
        try {
            noOfLines = getNoOfLines(fileConfig.getProcessingPath().concat(System.getProperty("file.separator")).concat(fileConfig.getFileName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int firstLine = 0;
        int lastLine = gridSize;
        int partitionNumber = 0;

        while(firstLine < noOfLines) {

            if(lastLine >= noOfLines) {
                lastLine = noOfLines;
            }

            log.info("Partition number : {}, first line is : {}, last  line is : {} ", partitionNumber, firstLine+1, lastLine);

            ExecutionContext value = new ExecutionContext();

            value.putLong("partition_number", partitionNumber);
            value.putLong("first_line", firstLine);
            value.putLong("last_line", lastLine);

            result.put("PartitionNumber-" + partitionNumber, value);

            firstLine = firstLine + gridSize;
            lastLine = lastLine + gridSize;
            partitionNumber++;
        }

        log.info("No of lines {}", noOfLines);

        return result;
    }

    public int getNoOfLines(String filePath) throws IOException {
        //FileSystemResource resource = new FileSystemResource(filePath);
        LineNumberReader reader = new LineNumberReader(new FileReader(filePath));
        reader.skip(Long.MAX_VALUE);
        reader.close();
        return reader.getLineNumber();
    }
}