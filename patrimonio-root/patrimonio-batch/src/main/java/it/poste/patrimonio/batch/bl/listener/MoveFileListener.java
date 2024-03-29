package it.poste.patrimonio.batch.bl.listener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;


import it.poste.patrimonio.batch.bl.config.FileConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoveFileListener implements JobExecutionListener {

	@Autowired
	private FileConfig fileConfig;
	

	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");


	@Override
	public void beforeJob(JobExecution jobExecution) {

		log.info("Launched job {}",jobExecution.getJobInstance().getInstanceId());
		moveToProcessing(jobExecution.getExecutionContext().getString("filename"));
	}

	@Override
	public void afterJob(JobExecution jobExecution) {

		log.info("Finished job {} with result {}",jobExecution.getJobInstance().getInstanceId(), jobExecution.getExitStatus().getExitCode());
		if(jobExecution.getExitStatus().compareTo(ExitStatus.COMPLETED)==0){
			moveToCompleted(jobExecution.getExecutionContext().getString("filename"));
		} else if(jobExecution.getExitStatus().compareTo(ExitStatus.FAILED)==0){
			moveToDiscarded(jobExecution.getExecutionContext().getString("filename"));
		} else {
			moveToNoOp(jobExecution.getExecutionContext().getString("filename"));
		}
	}

	private void moveToProcessing(String filename) {

		String inputFile=new StringBuilder()
				.append(fileConfig.getInputPath())
				.append(System.getProperty("file.separator"))
				.append(filename)
				.toString();
		String targetPath=new StringBuilder()
				.append(fileConfig.getProcessingPath())
				.append(System.getProperty("file.separator"))
				.append(filename)
				.toString();
		moveFile(inputFile, targetPath);
	}

	private void moveToCompleted(String filename) {

		String processingFile=new StringBuilder()
				.append(fileConfig.getProcessingPath())
				.append(System.getProperty("file.separator"))
				.append(filename)
				.toString();
		String targetPath=new StringBuilder()
				.append(fileConfig.getCompletedPath())
				.append(System.getProperty("file.separator"))
				.append(filename+".completed_"+LocalDateTime.now().format(dtf))
				.toString();
		moveFile(processingFile, targetPath);
	}

	private void moveToDiscarded(String filename) {

		String processingFile=new StringBuilder()
				.append(fileConfig.getProcessingPath())
				.append(System.getProperty("file.separator"))
				.append(filename)
				.toString();
		String outputPath=new StringBuilder()
				.append(fileConfig.getDiscardedPath())
				.append(System.getProperty("file.separator"))
				.append(filename+".discarded_"+LocalDateTime.now().format(dtf))
				.toString();
		moveFile(processingFile, outputPath);

	}

	private void moveToNoOp(String filename) {

		String processingFile=new StringBuilder()
				.append(fileConfig.getProcessingPath())
				.append(System.getProperty("file.separator"))
				.append(filename)
				.toString();
		String outputPath=new StringBuilder()
				.append(fileConfig.getNoOpPath())
				.append(System.getProperty("file.separator"))
				.append(filename+".noop_"+LocalDateTime.now().format(dtf))
				.toString();
		moveFile(processingFile, outputPath);

	}


	private void moveFile(String sourcePath, String targetPath) {

		if(Files.exists(Paths.get(sourcePath), LinkOption.NOFOLLOW_LINKS)) {
			log.info("Move from {} to {} ",sourcePath, targetPath);
			try {

				Path path= Files.copy(Paths.get(sourcePath), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
				log.info("Destination file "+path.toAbsolutePath().toString());
				Files.delete(Paths.get(sourcePath));

			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}

	}



}
