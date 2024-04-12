/*
 * Created on Nov 26, 2007
 *
 *  
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.utility;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

/**
 * @author Joe
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FTPUtil{
	private Logger log = Logger.getLogger(this.getClass());
	
	private String hostip;
	private String username;
	private String passwd;
	
	public FTPUtil(String hostip,String username,String passwd){
		this.hostip=hostip;
		this.username=username;
		this.passwd=passwd;		
	}
//	public void get(String inputfile,String outputfile)throws Exception{
//		try{
//			System.out.println("Start FtpUtil.get()");			
//			System.out.println("Input File:"+inputfile);			
//			System.out.println("Output File:"+outputfile);			
//			FTPClient fcMyFtp = new FTPClient();			
//			int ch;
//			fcMyFtp.openServer(this.hostip);
//			fcMyFtp.login(this.username,this.passwd); 
//		
//			fcMyFtp.binary();
//			TelnetInputStream in = fcMyFtp.get(inputfile);
//			FileOutputStream fo = new FileOutputStream(outputfile); 
//			
//			byte buffer[] = new byte[102];
//			int len=0;
//
//			System.out.println(in.read(buffer));
//			while((len=in.read(buffer)) != -1) {
//				System.out.println(len);
//				fo.write(buffer,0,len);
//			}
//			fo.close();
//			in.close();
//			System.out.println("End FtpUtil.get()");
//		}catch(Exception e){
//			throw new Exception(e.getMessage()); 
//		}
//	}
	public void put(String inputfile, String dirName, String folder)throws Exception{
		FTPClient ftp = new FTPClient();
		try{
			log.info("Start FtpUtil.put()");			
			
			ftp.connect(this.hostip);
			ftp.login(this.username,this.passwd);
			ftp.changeWorkingDirectory(dirName);
		
			if(ftp.makeDirectory(folder)){
				ftp.changeWorkingDirectory(folder);
				log.info(">> inputfile "+inputfile);
				FileInputStream inputF = new FileInputStream(inputfile);
				BufferedInputStream inputBuf = new BufferedInputStream(inputF);
				ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
				if(ftp.storeFile("test001.tif",inputBuf)){
					log.info("Was uploaded");
				}else{
					log.info("Not uploaded");
				}
				if(inputBuf!=null) inputBuf.close();
		        if(inputF!=null) inputF.close();
			}else{
				log.info("Can not make Directory");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage()); 
		}finally{
			
			if(ftp.isConnected()) {
		        try {
		          ftp.disconnect();
		        } catch(IOException ioe) {
		          ioe.printStackTrace();
		        }
			}

		}
	}
	
	public boolean createAllFolder(String workDirName, String parentfolder)throws Exception{
		
		FTPClient ftp = new FTPClient();
		try{
			log.info("Start FtpUtil.put()");			
			
			ftp.connect(this.hostip);
			ftp.login(this.username,this.passwd);
			ftp.changeWorkingDirectory(workDirName);
		
			if(ftp.makeDirectory(parentfolder)){
				ftp.changeWorkingDirectory(parentfolder);
				
				if(!ftp.makeDirectory("L")){
					log.info("Cannot create directory L");
					return false;
				}
				if(!ftp.makeDirectory("N")){
					log.info("Cannot create directory N");
					return false;
				}
				if(!ftp.makeDirectory("S")){
					log.info("Cannot create directory S");
					return false;
				}
			}else{
				System.out.println("Cannot create directory "+parentfolder);
				return false;
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage()); 
		}finally{
			
			if(ftp.isConnected()) {
		        try {
		          ftp.disconnect();
		        } catch(IOException ioe) {
		          ioe.printStackTrace();
		        }
			}

		}
	}
	
	public boolean ftpToFolder(String inputfile, String newName, String dirName)throws Exception{
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try{
			log.info("Start ftpToFolder");			
			
			ftp.connect(this.hostip);
			log.info(">> login to ftp "+ftp.login(this.username,this.passwd));
			ftp.changeWorkingDirectory(dirName);
			
			log.info(">> isConnected "+ftp.isConnected());
		
			log.info(">> inputfile "+inputfile);
			log.info(">> dirName "+dirName);
			log.info(">> newName "+newName);
			log.info(">> WorkingDirectory "+ftp.printWorkingDirectory());
			
			FileInputStream inputF = new FileInputStream(inputfile);
			BufferedInputStream inputBuf = new BufferedInputStream(inputF);
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			if(ftp.storeFile(newName,inputBuf)){
				log.info("Was uploaded");
				result = true;
			}else{
				log.info("Not uploaded");
			}
			if(inputBuf!=null) inputBuf.close();
	        if(inputF!=null) inputF.close();
	        return result;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e.getMessage()); 
		}finally{
			if(ftp.isConnected()) {
		        try {
		          ftp.disconnect();
		        } catch(IOException ioe) {
		          ioe.printStackTrace();
		        }
			}

		}
	}

}
