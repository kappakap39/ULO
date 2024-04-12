package com.eaf.inf.batch.ulo.inf.text;

import java.io.File;
import java.io.FileWriter;

import org.apache.log4j.Logger;

public class InfReport {
	private static transient Logger logger = Logger.getLogger(InfReport.class);
	
	public static void generateInfReport(String FILE_PATH, String FILE_NAME,String CONTENT){
		try{
			logger.debug("FILE_PATH >> "+FILE_PATH);
			logger.debug("FILE_NAME >> "+FILE_NAME);
			File PATH = new File(FILE_PATH);
			if(!PATH.exists()){
				PATH.mkdirs();
			}
			
			File FILE = new File(FILE_PATH+FILE_NAME);
			FileWriter fw = new FileWriter(FILE);
			fw.write(CONTENT);
			fw.close();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}
}
