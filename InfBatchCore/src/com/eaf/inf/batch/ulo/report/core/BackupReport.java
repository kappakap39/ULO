package com.eaf.inf.batch.ulo.report.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;

public class BackupReport extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(BackupReport.class);
	private static final int BACKUP_DAY = Integer.parseInt(InfBatchProperty.getInfBatchConfig("BACKUP_DAY"));
	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private static Date date = null;
	
	//delete backup file older than config 
	public void init(File DESTINATION_DIRECTORY){
		if(!DESTINATION_DIRECTORY.exists()){
			DESTINATION_DIRECTORY.mkdirs();
		}
		date = new Date();
		date = DateUtils.addDays(date, -BACKUP_DAY+1);
		try{
			for(File file : DESTINATION_DIRECTORY.listFiles()){
				if (dateFormat.parse(file.getName()).before(dateFormat.parse(dateFormat.format(date)))) {
					logger.debug("this file "+file+" older than "+BACKUP_DAY+" days");
					FileUtils.deleteDirectory(file);
				}
			}
		}catch(Exception e){
			logger.debug("ERROR ",e);
		}
	}
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
		String BACKUP_SOURCE_PATH = InfBatchProperty.getInfBatchConfig("PREFIX_FLP_REPORT_PATH");
		String BACKUP_DESTINATION_PATH = InfBatchProperty.getInfBatchConfig("BACKUP_DESTINATION_PATH");
		File SOURCE_DIRECTORY = new File(BACKUP_SOURCE_PATH);
		File DESTINATION_DIRECTORY = new File(BACKUP_DESTINATION_PATH);
		init(DESTINATION_DIRECTORY);
		String YYYYMMDD = Formatter.display(InfBatchProperty.getDate(), Formatter.TH, Formatter.Format.YYYYMMDD);
		DESTINATION_DIRECTORY = new File(BACKUP_DESTINATION_PATH+File.separator+YYYYMMDD);
		try {
			copyDirectory(SOURCE_DIRECTORY, DESTINATION_DIRECTORY);
		} catch (IOException e) {
			logger.debug("ERROR ",e);
		}
		return infBatchResponse;
	}

	public void copy(File sourceLocation, File targetLocation) throws IOException {
	    if (sourceLocation.isDirectory()) {
	        copyDirectory(sourceLocation, targetLocation);
	    } else {
	        copyFile(sourceLocation, targetLocation);
	    }
	}
	
	private void copyDirectory(File source, File target) throws IOException {
	    if (!target.exists()) {
	        target.mkdir();
	    }

    	String[] targets = target.list();
	    for (String f : source.list()) {
	    	source.lastModified();
	    	if(Arrays.asList(targets).contains(f)){
	    		logger.debug("this "+f+" folder is duplicate");
	    	}else{
	    		try{
		    		if(dateFormat.parse(f).after(dateFormat.parse(dateFormat.format(date)))){
		    			copy(new File(source, f), new File(target, f));
		    		}
	    		}catch(Exception e){
	    			copy(new File(source, f), new File(target, f));
	    		}
	    	}
	    }
	}
	
	private void copyFile(File source, File target) throws IOException {        
	    try (
	            InputStream in = new FileInputStream(source);
	            OutputStream out = new FileOutputStream(target)
	    ) {
	        byte[] buf = new byte[1024];
	        int length;
	        while ((length = in.read(buf)) > 0) {
	            out.write(buf, 0, length);
	        }
	    }
	}
}
