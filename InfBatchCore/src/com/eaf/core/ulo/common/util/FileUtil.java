package com.eaf.core.ulo.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.file.FileControl;
import com.eaf.core.ulo.common.properties.SystemConfig;

public class FileUtil {
	private static transient Logger logger = Logger.getLogger(FileUtil.class);
	private static String INPUT_PATH = InfBatchConstant.ReportParam.INPUT_PATH;
	private static String INPUT_NAME = InfBatchConstant.ReportParam.INPUT_NAME;
	private static String BATCH_ID = InfBatchConstant.ReportParam.BATCH_ID;
	private static String OUTPUT_PATH = InfBatchConstant.ReportParam.OUTPUT_PATH;
	private static String OUTPUT_NAME = InfBatchConstant.ReportParam.OUTPUT_NAME;
	private static String REPORT_TEMPLATE = InfBatchConstant.ReportParam.REPORT_TEMPLATE;
	private static String BACKUP_PATH = InfBatchConstant.PATH.BACKUP;
	private static String DEFAULT_FILE_ENCODING = InfBatchProperty.getInfBatchConfig("DEFAULT_FILE_ENCODING");
	private static String DEFAULT_BACKUP_FOLDER = InfBatchProperty.getInfBatchConfig("DEFAULT_BACKUP_FOLDER");
	private static ArrayList<String> backupDirectorys = new ArrayList<>();
	private static ArrayList<String> sourceDirectorys = new ArrayList<>();
	
	public static void clearBatchFile(String batchId){
		try{
			ArrayList<String> batchIds = InfBatchProperty.getListInfBatchConfig(batchId+"_"+BATCH_ID);
			if(!InfBatchUtil.empty(batchIds)){
				for(String batch : batchIds){
					List<String> outputPaths = PathUtil.getMultiplePath(batch, OUTPUT_PATH);
					clearFile(outputPaths);
				}
			}else{
				List<String> outputPaths = PathUtil.getMultiplePath(batchId, OUTPUT_PATH);
				clearFile(outputPaths);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			logger.error("ERROR "+e.getLocalizedMessage());
		}
	}
	
	public static void clearFile(List<String> paths){
		try{
			if(!InfBatchUtil.empty(paths)){
				for(String path : paths){
					Path target = Paths.get(path);
					if(Files.exists(target, LinkOption.NOFOLLOW_LINKS)){
						Files.walkFileTree(target, new SimpleFileVisitor<Path>(){
							@Override
							public FileVisitResult preVisitDirectory(Path arg0,	BasicFileAttributes arg1) throws IOException {
								return FileVisitResult.CONTINUE;
							}
							@Override
							public FileVisitResult visitFile(Path file,	BasicFileAttributes arg1) throws IOException {
								Files.deleteIfExists(file);
								return FileVisitResult.CONTINUE;
							}
							@Override
							public FileVisitResult postVisitDirectory(Path directory, IOException arg1) throws IOException {
//								Files.deleteIfExists(directory);
								return FileVisitResult.CONTINUE;
							}
						});
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			logger.error("ERROR "+e.getLocalizedMessage());
		}
	}
	
	public static void backUpBatchFile(String batchId){
		try{
			ArrayList<String> batchIds = InfBatchProperty.getListInfBatchConfig(batchId+"_"+BATCH_ID);
			if(!InfBatchUtil.empty(batchIds)){
				for(String batch : batchIds){
					String outputPath = PathUtil.getPath(batch, OUTPUT_PATH);
					String inputPath = PathUtil.getPath(batch, INPUT_PATH);
					if(!InfBatchUtil.empty(outputPath) && !InfBatchUtil.empty(inputPath)){
						backupFolder(batch, outputPath);
						backupFolder(batch, inputPath);
					}else{
						backupFile(batch, outputPath);
						backupFile(batch, inputPath);
					}
					List<String> inputPaths = PathUtil.getMultiplePath(batch, INPUT_PATH);
					clearFile(inputPaths);
				}
			}else{
				String outputPath = PathUtil.getPath(batchId, OUTPUT_PATH);
				backupFile(batchId, outputPath);
				String inputPath = PathUtil.getPath(batchId, INPUT_PATH);
				backupFile(batchId, inputPath);
				List<String> inputPaths = PathUtil.getMultiplePath(batchId, INPUT_PATH);
				clearFile(inputPaths);
			}
			clearBackupFile(batchId);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			logger.error("ERROR "+e.getLocalizedMessage());
		}
	}
	
	public static void backupFile(String batchId, String soucrePath){
		String yyyyMMdd = Formatter.display(InfBatchProperty.getDate(), Formatter.TH, Formatter.Format.YYYYMMDD);
		try{
			String[] soucrePaths = StringUtils.split(soucrePath,',');
			if(!InfBatchUtil.empty(soucrePath)){
				for(String path : soucrePaths){
					final Path soucreDirectory = Paths.get(path);
					if(Files.exists(soucreDirectory, LinkOption.NOFOLLOW_LINKS)){
						String targetPath = PathUtil.getPath(batchId, BACKUP_PATH)+File.separator+yyyyMMdd;
						Path targetDirectory = Paths.get(targetPath);
						Path newBackupDirectory = checkBackupDirectory(soucreDirectory, targetDirectory);
						if(!InfBatchUtil.empty(newBackupDirectory)){
							targetDirectory = newBackupDirectory;
						}
						if(!sourceDirectorys.contains(soucreDirectory.toString())){
							copyDirectory(soucreDirectory, targetDirectory);
						}
						sourceDirectorys.add(soucreDirectory.toString());
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			logger.error("ERROR "+e.getLocalizedMessage());
		}
	}
	
	public static void backupFolder(String batchId, String soucrePath){
		String yyyyMMdd = Formatter.display(InfBatchProperty.getDate(), Formatter.TH, Formatter.Format.YYYYMMDD);
		try{
			if(!InfBatchUtil.empty(soucrePath)){
				final Path soucreDirectory = Paths.get(soucrePath);
				if(Files.exists(soucreDirectory, LinkOption.NOFOLLOW_LINKS)){
					String targetPath = PathUtil.getPath(batchId, BACKUP_PATH)+File.separator+yyyyMMdd+soucreDirectory.getFileName();
					Path targetDirectory = Paths.get(targetPath);
					Path newBackupDirectory = checkBackupDirectory(soucreDirectory, targetDirectory);
					if(!InfBatchUtil.empty(newBackupDirectory)){
						targetDirectory = newBackupDirectory;
					}
					if(!sourceDirectorys.contains(soucreDirectory.toString())){
						copyDirectory(soucreDirectory, targetDirectory);
					}
					sourceDirectorys.add(soucreDirectory.toString());
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			logger.error("ERROR "+e.getLocalizedMessage());
		}
	}
	
	public static Path checkBackupDirectory(Path soucreDirectory, Path targetDirectory){
		Path newBackupDirectory = null;
		try{
			File[] listFiles = targetDirectory.toFile().listFiles();
			if(!InfBatchUtil.empty(listFiles)){
				Path newTargetDirectory = getNewTargetDirectory(targetDirectory);
				for(File file : listFiles){
					if(file.isFile() && !backupDirectorys.contains(targetDirectory.toString())){
						moveFile(file, newTargetDirectory.toFile());
						newBackupDirectory = getNewTargetDirectory(targetDirectory);
					}
				}
			}
			backupDirectorys.add(targetDirectory.toString());
		}catch(Exception e){
			//logger.warn("WARN "+e.getLocalizedMessage());
			logger.fatal("ERROR",e);
		}
		return newBackupDirectory;
	}
	
	private static Path getNewTargetDirectory(Path targetDirectory){
		String directory = targetDirectory.toString();
		directory = directory+File.separator+DEFAULT_BACKUP_FOLDER;
		directory = StringUtils.replace(directory, "timestamp", String.valueOf(InfBatchProperty.getTimestamp()));
		directory = StringUtils.replace(directory, ":", ".");
		Path newTargetDirectory = Paths.get(directory);
		return newTargetDirectory;
	}
	
	public static void moveFile(File soucreDirectory, File targetDirectory){
		try{
			FileUtils.moveFileToDirectory(soucreDirectory, targetDirectory, true);
		}catch(Exception e){
			logger.warn("WARN "+e.getLocalizedMessage());
		}
	}
	
//	public static void moveFile(Path soucreDirectory, Path targetDirectory){
//		try{
//			logger.debug("soucreDirectory >> "+soucreDirectory);
//			logger.debug("targetDirectory >> "+targetDirectory);
//			Files.move(soucreDirectory, targetDirectory, StandardCopyOption.ATOMIC_MOVE);
//		}catch(Exception e){
//			logger.warn("WARN "+e.getLocalizedMessage());
//		}
//	}
	
	public static void copyDirectory(final Path soucreDirectory,final Path targetDirectory){
		try{
			Files.walkFileTree(soucreDirectory, new SimpleFileVisitor<Path>(){
				@Override
				public FileVisitResult preVisitDirectory(Path arg0,	BasicFileAttributes arg1) throws IOException {
					Files.createDirectories(targetDirectory.resolve(soucreDirectory.relativize(arg0)));
					return FileVisitResult.CONTINUE;
				}
				@Override
				public FileVisitResult visitFile(Path file,	BasicFileAttributes arg1) throws IOException {
					Files.copy(file, targetDirectory.resolve(soucreDirectory.relativize(file)),StandardCopyOption.REPLACE_EXISTING);
					return FileVisitResult.CONTINUE;
				}
			});
		}catch(Exception e){
			logger.warn("WARN "+e.getLocalizedMessage());
		}
	}
	
	public static void clearBackupFile(String batchId){
		try{
			ArrayList<String> batchIds = InfBatchProperty.getListInfBatchConfig(batchId+"_"+BATCH_ID);
			if(!InfBatchUtil.empty(batchIds)){
				for(String batch : batchIds){
					if(Util.empty(batch)){
						continue;
					}
					List<String> backUpDirectorys = PathUtil.getMultiplePath(batch, BACKUP_PATH);
					if(!InfBatchUtil.empty(backUpDirectorys)){
						for(String backUpDirectory : backUpDirectorys){
							File directory = new File(backUpDirectory);
							if(directory.exists()){
								File[] folders = directory.listFiles();
								if(null!=folders){
									Arrays.sort(folders,LastModifiedFileComparator.LASTMODIFIED_REVERSE);
									int BACKUP_LASTEST_NO = FormatUtil.getInt(InfBatchProperty.getInfBatchConfig(batch+"_"+InfBatchConstant.ReportParam.BACKUP_LASTEST));
									int lastest_no = 0;
									for(File file : folders){
										if (BACKUP_LASTEST_NO > lastest_no) {
											lastest_no++;
										}else{
											Path path = Paths.get(file.toString());
											if(Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)){
												FileUtils.deleteDirectory(file);
											}else{
												Files.delete(path);
											}
										}
									}
								}
							}
						}
					}
				}
			}else{
				List<String> backUpDirectorys = PathUtil.getMultiplePath(batchId, BACKUP_PATH);
				if(!InfBatchUtil.empty(backUpDirectorys)){
					for(String backUpDirectory : backUpDirectorys){
						File directory = new File(backUpDirectory);
						if(directory.exists()){
							File[] folders = directory.listFiles();
							if(null!=folders){
								Arrays.sort(folders, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
								String BACKUP_LASTEST_NO_TEXT = InfBatchProperty.getInfBatchConfig(batchId+"_"+InfBatchConstant.ReportParam.BACKUP_LASTEST);
								if(InfBatchUtil.empty(BACKUP_LASTEST_NO_TEXT)){
									BACKUP_LASTEST_NO_TEXT = InfBatchProperty.getInfBatchConfig("DEFAULT_BACKUP_LASTEST");
								}
								
								int BACKUP_LASTEST_NO = FormatUtil.getInt(BACKUP_LASTEST_NO_TEXT);
								int lastest_no = 0;
								for(File file : folders){
									if (BACKUP_LASTEST_NO > lastest_no) {
										lastest_no++;
									}else{
										Path path = Paths.get(file.toString());
										if(Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)){
											FileUtils.deleteDirectory(file);
										}else{
											Files.delete(path);
										}
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}
	
	public static String getBatchOutputParamFileName(String batchId, String formatDate)throws Exception{
		String fileName = InfBatchProperty.getInfBatchConfig(batchId+"_"+OUTPUT_NAME);
		try{
			String date = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, formatDate);
			if(!Util.empty(SystemConfig.getGeneralParam("CTE_DATE"))){
				date = Formatter.display(InfBatchProperty.getDateGeneralParam(), Formatter.EN, formatDate);
			}
			logger.debug("DATE FORMAT >>>> "+date);
			if(formatDate.length() == 6)	fileName = fileName.replaceAll("(?i)YYMMDD", date);
			else if(formatDate.length() == 8)	fileName = fileName.replaceAll("(?i)YYYYMMDD", date);
			else if(formatDate.length() == 15)	fileName = fileName.replaceAll("(?i)yyyymmdd_HHmmss", date);
		}catch(Exception e){
			throw new InfBatchException(e.getMessage());
		}
		return fileName;
	}
	
	public static String getBatchOutputFileName(String batchId, String formatDate){
		String fileName = InfBatchProperty.getInfBatchConfig(batchId+"_"+OUTPUT_NAME);
		try{
			String date = Formatter.display(InfBatchProperty.getTimestamp(), Formatter.EN, formatDate);
			if(formatDate.length() == 6)	fileName = fileName.replaceAll("(?i)YYMMDD", date);
			else if(formatDate.length() == 8)	fileName = fileName.replaceAll("(?i)YYYYMMDD", date);
			else if(formatDate.length() == 15)	fileName = fileName.replaceAll("(?i)yyyymmdd_HHmmss", date);
		}catch(Exception e){
		}
		return fileName;
	}
	
	public static String getReportOutputFileName(String taskId){
		String fileName = InfBatchProperty.getInfBatchConfig(taskId+"_"+OUTPUT_NAME);
		try{
			String YYYYMMDD = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.YYYYMMDD);
			String YEAR = String.valueOf(Integer.valueOf(YYYYMMDD.substring(0, 4)));
			String MONTH = YYYYMMDD.substring(4, 6);
			String DAY = YYYYMMDD.substring(6, 8);
			YYYYMMDD = YEAR+MONTH+DAY;
			logger.debug("REPORT_OUTPUT_NAME >> "+fileName);
			fileName = fileName.replace("YYYYMMDD", YYYYMMDD);
		}catch(Exception e){
		}
		return fileName;
	}
	
	public static String getTextOutputFileName(String batchId,String formatDate){
		String fileName = InfBatchProperty.getInfBatchConfig(batchId+"_"+OUTPUT_NAME);
		try{
			String date = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, formatDate);
			logger.debug("DATE FORMAT >>>> "+date);
			if(formatDate.length() == 6)	fileName = fileName.replaceAll("(?i)YYMMDD", date);
			else if(formatDate.length() == 8)	fileName = fileName.replaceAll("(?i)YYYYMMDD", date);
			else if(formatDate.length() == 15)	fileName = fileName.replaceAll("(?i)yyyymmdd_HHmmss", date);
		}catch(Exception e){
		}
		return fileName;
	}
	
	public static String getTextOutputParamFileName(String batchId,String formatDate){
		String fileName = InfBatchProperty.getInfBatchConfig(batchId+"_"+OUTPUT_NAME);
		try{			
			String date = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, formatDate);
			if(!Util.empty(SystemConfig.getGeneralParam("CTE_DATE"))){
				date = Formatter.display(InfBatchProperty.getDateGeneralParam(), Formatter.EN, formatDate);
			}
			
			logger.debug("DATE FORMAT >>>> "+date);
			if(formatDate.length() == 6)	fileName = fileName.replaceAll("(?i)YYMMDD", date);
			else if(formatDate.length() == 8)	fileName = fileName.replaceAll("(?i)YYYYMMDD", date);
			else if(formatDate.length() == 15)	fileName = fileName.replaceAll("(?i)yyyymmdd_HHmmss", date);
		}catch(Exception e){
		}
		return fileName;
	}
	
	public static String getFileTemplate(String taskId){
		String template = InfBatchProperty.getInfBatchConfig(taskId+"_"+REPORT_TEMPLATE);
		try{
			while(StringUtils.substringsBetween(template, "{", "}") != null){
				String[] prefixTypes = StringUtils.substringsBetween(template, "{", "}");
				for(String prefixType : prefixTypes){
					String PREFIX_PATH = InfBatchProperty.getInfBatchConfig(prefixType);
					template = template.replace("{"+prefixType+"}", PREFIX_PATH);
				}
			}
		}catch(Exception e){
			
		}
		return template;
	}
	
//	public static void generateTextFile(String filePath, String fileName,String content) throws Exception{
//		logger.debug("Process Create Output File");
//		FileControl.mkdir(filePath);
//		FileOutputStream fileStream = new FileOutputStream(new File(filePath+File.separator+fileName));
//		OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
//		if(null != content){
//			writer.write(content);
//		}
//		
//		if(null != writer){
//			writer.flush();
//			writer.close();
//		}
//
//		if(null != fileStream){
//			fileStream.flush();
//			fileStream.close();
//		}
//		System.gc();
//	}
	
//	public static void generateTextFile(String filePath, String fileName,String content, String encode) throws Exception{
//		logger.debug("Process Create Output File");
//		logger.debug("encode >> "+encode);
//		FileControl.mkdir(filePath);
//		FileOutputStream fileStream = new FileOutputStream(new File(filePath+File.separator+fileName));
//		OutputStreamWriter writer = null;
//		if(!InfBatchUtil.empty(encode)){
//			writer = new OutputStreamWriter(fileStream, encode);
//		}else{
//			writer = new OutputStreamWriter(fileStream, "UTF-8");
//		}
//		if(null != content){
//			writer.write(content);
//		}
//		
//		if(null != writer){
//			writer.flush();
//			writer.close();
//		}
//
//		if(null != fileStream){
//			fileStream.flush();
//			fileStream.close();
//		}
//		System.gc();
//	}
	
//	public static void generateCSVFile(String filePath, String fileName, String content) throws Exception{
//		File PATH = new File(filePath);
//		if(!PATH.exists()){
//			PATH.mkdirs();
//		}
//		File FILE = new File(filePath+File.separator+fileName);
//		FileWriter fw = new FileWriter(FILE);
//        StringBuilder sb = new StringBuilder();
//        sb.append(content);
//        fw.write(sb.toString());
//        fw.close();
//	}
	
//	public static void generateCSVFile(String filePath, String fileName, String content, String encode) throws Exception{
//		File PATH = new File(filePath);
//		if(!PATH.exists()){
//			PATH.mkdirs();
//		}
//		OutputStreamWriter writer = null;
//		FileOutputStream fileStream = new FileOutputStream(new File(filePath+File.separator+fileName));
//		if(!InfBatchUtil.empty(encode)){
//			writer = new OutputStreamWriter(fileStream, encode);
//		}else{
//			writer = new OutputStreamWriter(fileStream, "UTF-8");
//		}
//		if(null != content){
//			writer.write(content);
//		}
//		
//		if(null != writer){
//			writer.flush();
//			writer.close();
//		}
//
//		if(null != fileStream){
//			fileStream.flush();
//			fileStream.close();
//		}
//		System.gc();
//	}
	
	public static void generateFile(String filePath, String fileName,String content, String encode) throws Exception{
		logger.debug("Process Create Output File");
		logger.debug("encode >> "+encode);
		FileControl.mkdir(filePath);
		FileOutputStream fileStream = new FileOutputStream(new File(filePath+File.separator+fileName));
		OutputStreamWriter writer = null;
		if(!InfBatchUtil.empty(encode)){
			writer = new OutputStreamWriter(fileStream, encode);
		}else{
			writer = new OutputStreamWriter(fileStream, DEFAULT_FILE_ENCODING);
		}
		if(null != content){
			writer.write(content);
		}
		
		if(null != writer){
			writer.flush();
			writer.close();
		}

		if(null != fileStream){
			fileStream.flush();
			fileStream.close();
		}
		System.gc();
	}
}
