package it.poste.patrimonio.batch.bl.partitioner;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


import static it.poste.patrimonio.batch.bl.util.BatchConstants.FILENAME;;
@Slf4j
@Component
public class CsvStepPartitioner implements Partitioner {
	
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> result = new HashMap<>();

        int noOfLines = 0;
        try {
            noOfLines = getNoOfLines(FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int firstLine = 0;
        int lastLine = gridSize;
        int partitionNumber = 0;

        while(firstLine <= noOfLines) {

            if(lastLine >= noOfLines) {
                lastLine = noOfLines;
            }

            log.info("Partition number : {}, first line is : {}, last  line is : {} ", partitionNumber, firstLine, lastLine);

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

    public int getNoOfLines(String fileName) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        LineNumberReader reader = new LineNumberReader(new FileReader(classPathResource.getFile().getAbsolutePath()));
        reader.skip(Long.MAX_VALUE);
        reader.close();
        return reader.getLineNumber();
    }
}