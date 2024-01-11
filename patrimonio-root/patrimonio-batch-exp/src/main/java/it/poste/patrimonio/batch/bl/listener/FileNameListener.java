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
			
			files = dir.listFiles(new FilenameFilter() {
				
		    	@Override
		        public boolean accept(File dir, String name) {
		            return name.matches("POSTE-MF-AUM-[^_]*_V1.CSV");
		        }        
		    });
			Arrays.sort(files, Comparator.comparingLong(File::lastModified));
			
			yield files[0].getName();
		}

		
		case AFB ->{

			files = dir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.matches("FIB5BE05_[^_]*");
				}        
			});
			Arrays.sort(files, Comparator.comparingLong(File::lastModified));

			yield files[0].getName();
		}

		default ->{
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
		
		};

		return filename;
		
	}

}
