package it.poste.patrimonio.batch.bl.listener;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import it.poste.patrimonio.batch.bl.config.FileConfig;
import it.poste.patrimonio.batch.bl.util.FileType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileNameListener implements JobExecutionListener {

	@Autowired
	private FileConfig fileConfig;
	
	private FileType type; 
	

	public FileNameListener(FileType type) {
		this.type = type;
	}


	@Override
	public void beforeJob(JobExecution jobExecution) {

		log.info("Launched job {}",jobExecution.getJobInstance().getInstanceId());
		String filename=retrieveFileName(type);
		
		jobExecution.getExecutionContext().putString("filename", filename);
	}

	
	private String retrieveFileName(FileType type) {
		
		File dir = new File(fileConfig.getInputPath());
	    File[] files = null;
		
		String filename= switch (type) {
		case MFM ->{
			
			log.info("Listing MFM files...");
			files = dir.listFiles(new FilenameFilter() {
				
		    	@Override
		        public boolean accept(File dir, String name) {
		            return name.matches(fileConfig.getFileNamePattern());
		        }        
		    });
			Arrays.sort(files, Comparator.comparingLong(File::lastModified));
			
			yield files.length!=0 ? files[0].getName() : "";
		}

		
		case AFB ->{

			log.info("Listing AFB files...");
			files = dir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.matches(fileConfig.getFileNamePattern());
				}        
			});
			Arrays.sort(files, Comparator.comparingLong(File::lastModified));

			yield files.length!=0 ? files[0].getName() : "";
		}

		default ->{
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
		
		};

		log.info("Retrieving filename [{}]", filename);
		return filename;
		
	}

}
