package com.eaf.orig.shared.view.form.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;

import com.eaf.cache.TableLookupCache;
import com.eaf.j2ee.pattern.util.DisplayFormatUtil;
import com.eaf.orig.cache.properties.GeneralParamProperties;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ORIGFormUtil extends com.eaf.orig.shared.utility.ORIGFormUtil {
	static Logger log = Logger.getLogger(ORIGFormUtil.class);

	/**
	 * Constructor for NaosFormUtil.
	 */
	public ORIGFormUtil() {
		super();
	}

	private static ORIGFormUtil me;

	public static ORIGFormUtil getInstance_SG() {
		if (me == null) {
			me = new ORIGFormUtil();
		}
		return me;
	}

	// Province by Joe
	/*public static Vector parseProvinceToCacheDataM() {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ZipCodeCacheDataM :");
		HashMap h = TableLookupCache.getCacheStructure();
		Vector vZip = (Vector) (h.get("ProvinceCacheDataM"));

		Vector reVec = new Vector();
		try {
			CacheDataM cache;
			for (int j = 0; j < vZip.size(); j++) {
					cache = new CacheDataM();
					ProvinceProperties provinceData = (ProvinceProperties) vZip.elementAt(j);
					cache.setCode(provinceData.getProvince());
					cache.setShortDesc(provinceData.getProvince());
					reVec.add(cache);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reVec;
	}*/

	// District by Joe
	/*public static Vector parseDistrictToCacheDataM() {
		HashMap h = TableLookupCache.getCacheStructure();
		Vector vZip = (Vector) (h.get("ZipCodeCacheDataM"));
		Vector reVec = new Vector();
		try {
			CacheDataM cache;
			for (int j = 0; j < vZip.size(); j++) {
				cache = new CacheDataM();
				ZipCodeProperties districtData = (ZipCodeProperties) vZip.elementAt(j);
				cache.setCode(districtData.getDistrict());
				cache.setShortDesc(districtData.getDistrict());
				reVec.add(cache); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reVec;
	}*/
	
	public static String convertEnDateToChDate(Date enDate) {
		String date;
		String str1;
		String str2;
		int temp;
		
		date=DisplayFormatUtil.datetoString(parseDate(enDate));
		str1 = date.substring(0,6);
		str2 = date.substring(6,10);
		
		temp= Integer.parseInt(str2)-1911;
		str2 = Integer.toString(temp);
		if(str2.length()==1){
			str2="000"+str2;
		}else if(str2.length()==2){
			str2="00"+str2;
		}else if(str2.length()==3){
			str2="0"+str2;
		}
		date=str1+str2;
			
		
		return date;
	}

	
//	s


	public Vector LoadExcel(String filename,long filelength) throws Exception {
		
		Vector codeVt = new Vector();	
//		System.out.println("File name ----->"+filename);
		try {  
			Workbook workbook= Workbook.getWorkbook(new File(filename));
			Sheet sheet = workbook.getSheet(0); 
			
			int startRow = 0;		//Set start row that you have first data
			int startCol = 0;		//Set start row that you have first data
			int rowIndex = startRow;
			int rowSize = sheet.getRows();
			int colIndex = startCol;
			int colSize = sheet.getColumns();
			boolean loop = true;
			
			while(loop) {
				if (rowIndex > rowSize-1) {
					loop = false;
				} else {
					Vector cols = new Vector();
					colIndex = startCol;
					boolean loop2 = true;
					while(loop2){
						if(colIndex > colSize-1){
						loop2 = false;}
						else{
						Cell code = sheet.getCell(colIndex,rowIndex);
						String articleCode = code.getContents();
						cols.addElement(articleCode);
						colIndex++;
						}
					}
					codeVt.add(cols);
					rowIndex++;
				}
			}	
		}catch(Exception h){
			h.printStackTrace(); 
		}
		return codeVt;	
	}
	
	public Vector loadText(String filename,long filelength) throws Exception {
		
		Vector codeVt = new Vector();	
//		System.out.println("File name ----->"+filename);
		try { 
			
			FileReader fr = new FileReader(filename);  
			BufferedReader br = new BufferedReader(fr); 
			String record ;
			while ( (record=br.readLine()) != null ) {  
			Vector cols = new Vector();
			/*StringTokenizer recordT = new StringTokenizer(record,"|");
			while (recordT.hasMoreTokens()){
				cols.add(recordT.nextToken());
			}*/
			while (record.length()>0){
				String temp;
				int i = record.indexOf("|");
				if(i>0)
				temp = record.substring(0,i-1);
				else if(i ==0) temp = "";
				else {
						temp = record.substring(0,record.length());
						cols.add(temp);
						break;
				}
				if (record.length()>(i+1))
				record = record.substring(i+1);
				else{
						cols.add(temp);
						cols.add("");
						break;
				}
				cols.add(temp);
			}
			codeVt.add(cols);
   			}  
			fr.close();

		}catch(Exception h){
			h.printStackTrace(); 
		}
		return codeVt;	
	}


	public void deleteFile(String filename,long filelength) throws Exception {
		
		try { 
			File myFile = new File(filename);
			myFile.delete();
//			log.debug("delete file -------> " +filename);
		}catch(Exception h){
			h.printStackTrace(); 
		}
	}

  public static void executeCommand(String command) {
    try {
      String[] finalCommand;
      if (isWindows()) {
        finalCommand = new String[4];
     	if (System.getProperty("os.name").toLowerCase().indexOf("xp") != -1){
        	finalCommand[0] = "C:\\windows\\system32\\cmd.exe";
     	}  
     	else
        	finalCommand[0] = "C:\\winnt\\system32\\cmd.exe";     	      
        finalCommand[1] = "/y";
        finalCommand[2] = "/c";
        finalCommand[3] = command;
      }
      else {
        finalCommand = new String[3];
        finalCommand[0] = "/bin/sh";
        finalCommand[1] = "-c";
        finalCommand[2] = command;
      }
  
      final Process pr = Runtime.getRuntime().exec(finalCommand);
      new Thread(new Runnable(){
        public void run() {
          BufferedReader br_in = null;
          try {
            br_in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String buff = null;
            while ((buff = br_in.readLine()) != null) {
              System.out.println("Process out :" + buff);
              try {Thread.sleep(100); } catch(Exception e) {}
            }
            br_in.close();
          }
          catch (IOException ioe) {
            System.out.println("Exception caught printing process output.");
            ioe.printStackTrace();
          }
          finally {
            try {
              br_in.close();
            } catch (Exception ex) {}
          }
        }
      }).start();
  
      new Thread(new Runnable(){
        public void run() {
          BufferedReader br_err = null;
          try {
            br_err = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            String buff = null;
            while ((buff = br_err.readLine()) != null) {
              System.out.println("Process err :" + buff);
              try {Thread.sleep(100); } catch(Exception e) {}
            }
            br_err.close();
          }
          catch (IOException ioe) {
            System.out.println("Exception caught printing process error.");
            ioe.printStackTrace();
          }
          finally {
            try {
              br_err.close();
            } catch (Exception ex) {}
          }
        }
      }).start();
    }
    catch (Exception ex) {
      System.out.println(ex.getLocalizedMessage());
    }
  }
  
  public static boolean isWindows() {
    if (System.getProperty("os.name").toLowerCase().indexOf("windows") != -1){
    	System.out.println("OS name |>>>----------->"+System.getProperty("os.name"));
      return true;
    }
    else
      return false;
  }

	public void createTextFile(String filename,Vector inputs) throws Exception {
		
		try { 
			File f = new File(filename);
			f.delete();
			FileWriter fw = new FileWriter(filename);  
			BufferedWriter bw = new BufferedWriter(fw); 
			String record ;
			for(int i=0;i<inputs.size();i++){
				record = (String)inputs.elementAt(i);
				bw.write(record);
				bw.newLine();
			}
				bw.close();

		}catch(Exception h){
			h.printStackTrace(); 
		}
	}
		
	/*public static Vector getEXUserRoleMappingCacheDataM() {
		HashMap h = TableLookupCache.getCacheStructure();
		Vector vUserRole = (Vector) (h.get("UserRoleMappingCacheDataM"));
		Vector reVec = new Vector();
		try {

			CacheDataM cache;
			for (int j = 0; j < vUserRole.size(); j++) {
				cache = new CacheDataM();
				UserRoleMappingProperties userRoleData = (UserRoleMappingProperties) vUserRole.elementAt(j);
				String temp = userRoleData.getRoleId();
				if(temp.equalsIgnoreCase("EX")){
				cache.setCode(userRoleData.getUserName());
				cache.setShortDesc(userRoleData.getUserName());
				reVec.add(cache);				
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reVec;
	}*/

	/*public static Vector getUWUserRoleMappingCacheDataM() {
		HashMap h = TableLookupCache.getCacheStructure();
		Vector vUserRole = (Vector) (h.get("UserRoleMappingCacheDataM"));
		Vector reVec = new Vector();
		String tempCode = "";
		String tempDesc = "";
		try {
			CacheDataM cache;
			for (int j = 0; j < vUserRole.size(); j++) {
				cache = new CacheDataM();
				UserRoleMappingProperties userRoleData = (UserRoleMappingProperties) vUserRole.elementAt(j);
				String temp = userRoleData.getRoleId();
				if(temp.equalsIgnoreCase("UW")){
				cache.setCode(userRoleData.getUserName());
				cache.setShortDesc(userRoleData.getUserName());
				tempCode = tempCode + "|" + userRoleData.getUserName();
				reVec.add(cache);				
				}
			}
				cache = new CacheDataM();
				cache.setCode(tempCode);
				cache.setShortDesc("All");
				reVec.add(cache);				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reVec;
	}*/
	
	/*public static Vector getCancelReasons(String busID) {
		Vector reVec = new Vector();
		String tempCode = "";
		String tempDesc = "";
		NaosCacheUtil cacheUtil = NaosCacheUtil.getInstance();
		Vector cancels = (Vector) cacheUtil.getNaosCacheDataMs(busID,21);
		try {
			CacheDataM cache;
			for (int j = 0; j < cancels.size(); j++) {
				cache = (CacheDataM)cancels.elementAt(j);
				tempCode = tempCode + "|" + cache.getCode();
				reVec.add(cache);				
			}
				cache = new CacheDataM();
				cache.setCode(tempCode);
				cache.setShortDesc("All");
				reVec.add(cache);				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reVec;
	}*/
	
	public static double getMinSize() {
		HashMap h = TableLookupCache.getCacheStructure();
		Vector vPro = (Vector) (h.get("GeneralParamDataM"));
		for (int i = 0; i < vPro.size(); i++) {
			GeneralParamProperties gpData =
				(GeneralParamProperties) vPro.elementAt(i);
			if ("MIN_FILE_SIZE".equalsIgnoreCase(gpData.getCode())) {
				return Double.parseDouble(gpData.getParamvalue());
			}
		}

		return 0.0;
	}
	
	public static double getMaxSize() {
		HashMap h = TableLookupCache.getCacheStructure();
		Vector vPro = (Vector) (h.get("GeneralParamDataM"));
		for (int i = 0; i < vPro.size(); i++) {
			GeneralParamProperties gpData =
				(GeneralParamProperties) vPro.elementAt(i);
			if ("MAX_FILE_SIZE".equalsIgnoreCase(gpData.getCode())) {
				return Double.parseDouble(gpData.getParamvalue());
			}
		}

		return 0.0;
	}
	
}
