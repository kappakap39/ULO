package com.eaf.orig.ulo.pl.app.utility;

import java.io.File;
import java.io.FilenameFilter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.Base64Utils;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.master.shared.model.ReportParam;
import com.eaf.orig.ulo.control.util.ReportFile;


public class DownloadReportController {
	static Logger logger = Logger.getLogger(DownloadReportController.class);
	public static String getReportName(ReportParam reportParam){
		return InfBatchProperty.getInfBatchConfig(reportParam.getParamCode()+"_"+InfBatchConstant.ReportParam.REPORT_NAME);
	}
	public static String displayHtml(ReportParam reportParam,String date){
		StringBuilder html = new StringBuilder();
		List<ReportFile> reportFiles = new ArrayList<ReportFile>();
		if(null!=reportParam.getReportPaths())
		for(String reportPath : reportParam.getReportPaths()){
			if(!Util.empty(reportPath)){
				String reportDatePath = reportPath+File.separator+date;
				logger.debug("reportDatePath : "+reportDatePath);
				File file = new File(reportDatePath);
				String[] fileList = file.list(new FilenameFilter(){
					  @Override
					  public boolean accept(File dir, String name){
					    return new File(dir,name).isFile();
					  }
				});
				logger.debug("fileList : "+fileList);
				if(null!=fileList&&fileList.length>0){
					for(String fileName:fileList){
						ReportFile reportFile = new ReportFile();
						reportFile.fileName = fileName;
						reportFile.reportPath = reportPath+File.separator+date+File.separator+fileName;
						reportFiles.add(reportFile);
					}
				}else{
					String[] directoryList = file.list(new FilenameFilter(){
						  @Override
						  public boolean accept(File dir, String name){
						    return new File(dir,name).isDirectory();
						  }
					});
					List<BackupFolder> backupFolders = new ArrayList<BackupFolder>();
					if(null!=directoryList&&directoryList.length>0){
						for (String directory: directoryList) {
							try{
							logger.debug("directory : "+directory);
							BackupFolder backupFolder = new BackupFolder();
							backupFolder.setFolderName(directory);
							String timeExecute = directory
									.replaceAll("\\.", "")
									.replaceAll(" ","")
									.replaceAll("executeTime","")
									.replaceAll("\\=","")
									.replaceAll("\\(","")
									.replaceAll("\\)","")  
									.replaceAll("\\-",""); 
							logger.debug("timeExecute : "+timeExecute);
							long folderId = new BigDecimal(timeExecute).longValue();
							backupFolder.setFolderId(folderId);
							logger.debug("folderId : "+folderId);
							backupFolders.add(backupFolder);
							}catch(Exception e){logger.fatal("ERROR",e);}
						}
						Collections.sort(backupFolders, new Comparator<BackupFolder>() {
					        @Override
					        public int compare(BackupFolder o1, BackupFolder o2) {
					            String p1 = String.valueOf(o1.getFolderId());
					            String p2 = String.valueOf(o2.getFolderId());
					            return p2.compareTo(p1);
					        }
					    });
					}
					if(null!=backupFolders&&backupFolders.size()>0){
						reportDatePath = reportDatePath+File.separator+backupFolders.get(0).getFolderName();
						logger.debug("reportDatePath : "+reportDatePath);
						File _file = new File(reportDatePath);
						String[] _fileList = _file.list(new FilenameFilter(){
							  @Override
							  public boolean accept(File dir, String name){
							    return new File(dir,name).isFile();
							  }
						});
						logger.debug("fileList : "+_fileList);
						if(null!=_fileList&&_fileList.length>0){
							for(String fileName:_fileList){
								ReportFile reportFile = new ReportFile();
								reportFile.fileName = fileName;
								reportFile.reportPath = reportDatePath+File.separator+fileName;
								reportFiles.add(reportFile);
							}
						}
					}
				}
				
			}
		}
		logger.debug("reportFiles : "+reportFiles);
		if(!Util.empty(reportFiles)){
			html.append("<tr>");
			html.append("<td rowspan='"+(reportFiles.size()+1)+"'>"+getReportName(reportParam)+"</td>");
			html.append("</tr>");
			for(ReportFile reportFile:reportFiles){
				html.append("<tr>");
				html.append("<td>"+reportFile.fileName+"</td>");
				String reportId = Base64Utils.encodeToString(reportFile.reportPath.getBytes());
				html.append("<td class='text-center'><button type='button' class='btn btn-download-green report_file' id='"+reportId+"'>Download</button></td>");
				html.append("</tr>");
			}
		}else{
			html.append("<tr>");
			html.append("<td>"+getReportName(reportParam)+"</td>");
			html.append("<td colspan='2' style='color:red;'>File not found.</td>");
			html.append("</tr>");
		}
		return html.toString();
	}
}
