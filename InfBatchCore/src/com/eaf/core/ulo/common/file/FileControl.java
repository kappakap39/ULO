package com.eaf.core.ulo.common.file;

import java.io.File;

import org.apache.log4j.Logger;

public class FileControl {
	private static transient Logger logger = Logger.getLogger(FileControl.class);
	public static void mkdir(String folderName){
		try{
			File file = new File(folderName);
			if(!file.exists()){
				logger.debug("mkdir >> "+folderName);
				file.mkdirs();
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}
	public static void delete(String folderName){
		try{
			logger.debug("delete >> "+folderName);
			File file = new File(folderName);
			deleteRecursive(file);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}
	private static void deleteRecursive(File fileOrDirectory) {
		if (fileOrDirectory.isDirectory())
		for (File child : fileOrDirectory.listFiles())
			deleteRecursive(child);
		fileOrDirectory.delete();
	}
	public static String fileName(String file){
		try{
			int pos = file.lastIndexOf(".");
			if(pos > 0){
				return file.substring(0,pos);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return null;
	}
}
