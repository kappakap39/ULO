package com.eaf.orig.ulo.control.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.util.Util;

public class OrigDownloadFileUtil {
	private static transient Logger logger = Logger.getLogger(OrigDownloadFileUtil.class);
	public static String EXCEL_DATA_KEY = "EXCEL_DATA_KEY";
	public class DISPLAY_UPLOAD_KEY{
		public static final String MF_TOTOL = "MF_TOTAL";
		public static final String MF_VALID_RECORD = "MF_VALID_RECORD";
		public static final String MF_INVALID_RECORD = "MF_INVALID_RECORD";
		public static final String MF_DOWNLOAD_INVALID = "MF_DOWNLOAD_INVALID";
	}
	
	public class DOWNLOAD_PROCESS_NAME{
		public static final String DOWNLOAD_INVALID = "DOWNLOAD_INVALID";
		public static final String DOWNLOAD_EXISTING = "DOWNLOAD_EXISTING";
	}
	
	public static void createExceldRecordData(ArrayList<Map<String, Object[]>> dataList, String FILE_NAME,String templateFile, String outputDataPath) {
		logger.debug("templateFile : " + templateFile);
		logger.debug("outputDataPath : " + outputDataPath);
		String outputFile = outputDataPath+FILE_NAME;
		try {
			File outputPath = new File(outputDataPath);
			if (!outputPath.exists()&&outputPath.isDirectory()) {
				outputPath.mkdirs();
			}
			File deleteFile = new File(outputFile);
			if (deleteFile.isFile()&&deleteFile.exists()) {
				deleteFile.delete();
			}
			logger.debug("dataList.size : " + dataList.size());
			logger.debug("templateFile : " + templateFile);
			FileInputStream fis = new FileInputStream(new File(templateFile));
			Workbook workbook = null;
			workbook = WorkbookFactory.create(fis);
			CellStyle style = workbook.createCellStyle();
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

			Sheet sheet = workbook.getSheetAt(0);
			logger.debug("workbook.getSheetName : "+ workbook.getSheetAt(0).getSheetName());
			int rownum = 1;
			for (Map<String, Object[]> mData : dataList) {
				Object[] excelDatas = mData.get(EXCEL_DATA_KEY);
				logger.debug("excelDatas = " + excelDatas.length);
				int cellnum = 0;
				Row row = sheet.createRow(rownum++);
				for (Object excelData : excelDatas) {
					Cell cell = row.createCell(cellnum++);
					cell.setCellValue((String) excelData);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellStyle(style);
				}
			}
			// Write Excel
			workbook.write(new FileOutputStream(outputFile));
			workbook.close();
			if (null != fis) {
				fis.close();
			}
			logger.debug("close file");
		} catch (Exception e) {
			logger.fatal("ERROR ", e);
		}
	}

	public static Workbook createExcelFile(ArrayList<Map<String, Object[]>> dataList, String FILE_NAME,String templateFile) {
		Workbook workbook = null;
		try{
			FileInputStream fis = new FileInputStream(new File(templateFile));
			workbook = WorkbookFactory.create(fis);
			CellStyle style = workbook.createCellStyle();
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			Sheet sheet = workbook.getSheetAt(0);
			logger.debug("sheet >>"+sheet);
			logger.debug("workbook.getSheetName : "+ workbook.getSheetAt(0).getSheetName());
			int rownum = 1;
			for (Map<String, Object[]> mData : dataList) {
				Object[] rejectArr = mData.get(EXCEL_DATA_KEY);
				int cellnum = 0;
				Row row = sheet.createRow(rownum++);
				for (Object rejectData : rejectArr) {
					Cell cell = row.createCell(cellnum++);
					cell.setCellValue((String) rejectData);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellStyle(style);
				}
			}
			if (null != fis) {
				fis.close();
			}
		} catch (Exception e) {
			logger.fatal("ERROR ", e);
		}
		return workbook;
	}
	
	public static String getLinkForDownload(HttpServletRequest request,String FILE_NAME, String downloadId,String downLoadProcessName) {
		String linkDownLoad = "";
		if (!Util.empty(FILE_NAME)) {
			String param = "DOWNLOAD_ID=" + downloadId + "&FILE_NAME="+ FILE_NAME+"&PROCESS_NAME="+downLoadProcessName;
			linkDownLoad = "<img class='imginbox' src='images/mf/exel.png'></img>"+ HtmlUtil.hyperlink("DOWN_LOAD_INVALID", FILE_NAME,
						    request.getContextPath() + "/DownloadServlet?"+ param, "", HtmlUtil.EDIT, "", request);
		}
		logger.debug("DowloadLink "+linkDownLoad);
		return linkDownLoad;
	}

	public static boolean dateValidate(int date, String month, int year) {
		boolean chDate;
		switch (month) {
		case "01":
			if (date <= 31) {
				chDate = true;
			} else
				chDate = false;
			break;
		case "02":
			if (checkLeap(year)) {
				if (date <= 29) {
					chDate = true;
				} else
					chDate = false;
			} else {
				if (date <= 28) {
					chDate = true;
				} else
					chDate = false;
			}
			break;
		case "03":
			if (date <= 31) {
				chDate = true;
			} else
				chDate = false;
			break;
		case "04":
			if (date <= 30) {
				chDate = true;
			} else
				chDate = false;
			break;
		case "05":
			if (date <= 31) {
				chDate = true;
			} else
				chDate = false;
			break;
		case "06":
			if (date <= 30) {
				chDate = true;
			} else
				chDate = false;
			break;
		case "07":
			if (date <= 31) {
				chDate = true;
			} else
				chDate = false;
			break;
		case "08":
			if (date <= 31) {
				chDate = true;
			} else
				chDate = false;
			break;
		case "09":
			if (date <= 30) {
				chDate = true;
			} else
				chDate = false;
			break;
		case "10":
			if (date <= 31) {
				chDate = true;
			} else
				chDate = false;
			break;
		case "11":
			if (date <= 30) {
				chDate = true;
			} else
				chDate = false;
			break;
		case "12":
			if (date <= 31) {
				chDate = true;
			} else
				chDate = false;
			break;
		default:
			chDate = false;
			break;
		}
		return chDate;
	}

	public static String removeSpacialCh(String data) {
		String stringData = "";
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) != ' ' && data.charAt(i) != '`'
					&& data.charAt(i) != '~' && data.charAt(i) != '!'
					&& data.charAt(i) != '@' && data.charAt(i) != '#'
					&& data.charAt(i) != '$' && data.charAt(i) != '%'
					&& data.charAt(i) != '^' && data.charAt(i) != '&'
					&& data.charAt(i) != '*' && data.charAt(i) != '('
					&& data.charAt(i) != ')' && data.charAt(i) != '-'
					&& data.charAt(i) != '+' && data.charAt(i) != '='
					&& data.charAt(i) != '_' && data.charAt(i) != '['
					&& data.charAt(i) != ']' && data.charAt(i) != '{'
					&& data.charAt(i) != '}' && data.charAt(i) != '\\'
					&& data.charAt(i) != '|' && data.charAt(i) != ';'
					&& data.charAt(i) != ':' && data.charAt(i) != '"'
					&& data.charAt(i) != '\'' && data.charAt(i) != '<'
					&& data.charAt(i) != ',' && data.charAt(i) != '>'
					&& data.charAt(i) != '.' && data.charAt(i) != '?'
					&& data.charAt(i) != '/') {
				stringData = stringData + data.charAt(i);
			}
		}
		return stringData;
	}

	public static boolean isInteger(String date) {
		if (date.matches("[0-9]+")) {
			return true;
		} else
			return false;
	}

	public static boolean isEmail(String data) {
		if (data.matches("([a-zA-Z]+(\\@[a-zA-Z]+\\.[a-zA-Z][a-zA-Z][a-zA-Z]))")) {
			return true;
		} else
			return false;
	}

	public static boolean maxLength(Object data, int max) {
		if (data.toString().length() > max) {
			return true;
		} else
			return false;
	}

	public static String removeComma(String data) {
		String resultStr = "";
		for (int i = 0; data.length() > i; i++) {
			if (data.charAt(i) != ',') {
				resultStr = resultStr + data.charAt(i);
			}
		}
		return resultStr;
	}
	
	public static boolean isNumeric(String str){  
	  try{  
	    double d = Double.parseDouble(str);  
	  }catch(NumberFormatException e){  
	    return false;  
	  }  
	  return true;  
	}
	
	public static boolean isBigdecimal(String str){  
	  try{  
		  BigDecimal big = new BigDecimal((str).toString());
	  }catch(NumberFormatException e){  
	    return false;  
	  }  
	  return true;  
	}
	
	public static boolean isDecimal(String str){
		String[] string = str.split("\\.");
		if(str.length()==2){
			return true;
		}else{
			return false;
		}
	}
	public static boolean isFloat(String str){
	  try{  
	    double d = Float.parseFloat(str);  
	  }catch(NumberFormatException e){  
	    return false;  
	  }  
	  return true;  
	}
	public static String genFileName(String name){
		Random rn = new Random();
		logger.debug("FILE_NAME#####"+name);
		if(name.endsWith(".xlsx")){
			name=name.replaceAll(".xlsx","_"+rn.nextInt(9)+""+rn.nextInt(9)+""+rn.nextInt(9)+".xlsx");
		}
		logger.debug("GenFileName#####"+name);
		return name;
	}
	public static boolean decimalLength(String data,int lengthdata,int lengthdecimal){
		String[] number = data.split("\\.");
		if(number.length==2&&number[0].length()==lengthdata&&number[1].length()==lengthdecimal){
			return true;
		}else{
			return false;
		}
	}
	public static boolean isInt(String str) {  
	  try{  
	    int d = Integer.parseInt(str);  
	  }catch(NumberFormatException e) {  
	    return false;  
	  }  
	  return true;  
	}
	public static boolean isDouble(String data){
		String[] resData = data.split("\\.");
		logger.debug("testdata =>>"+data+" "+resData.length);
		if(resData.length==2)
		logger.debug("testdata =>>"+resData.length+" "+OrigDownloadFileUtil.isInteger(resData[0])+" "+OrigDownloadFileUtil.isInteger(resData[1]));
		if(resData.length==2 && OrigDownloadFileUtil.isBigdecimal(resData[0]) && OrigDownloadFileUtil.isBigdecimal(resData[1])){
			return true;
		}else{
			return false;
		}
	}
	public static boolean positiveNumber(String data, int integer, int decimal,
			boolean checkMax, double max) {
		if (data.matches("([0-9]+(\\.[0-9]+))")) {
			String[] resultData = data.split("\\.");
			if (checkMax) {
				if (Double.parseDouble(data) <= max)
					if (resultData.length == 2
							&& resultData[0].length() <= integer
							&& resultData[1].length() <= decimal) {
						return true;
					} else
						return false;
			} else {
				if (resultData.length == 2 && resultData[0].length() <= integer
						&& resultData[1].length() <= decimal) {
					return true;
				} else
					return false;
			}
		} else
			return false;
		return false;
	}
	public static boolean checkLeap(int year) {
		if (year % 4 == 0) {
			return true;
		} else
			return false;
	}
	public static String coString(ArrayList<String> strArr) {
		String coStr = null;
		for (int i = 0; i < strArr.size(); i++) {
			if (i == 0) {
				coStr = strArr.get(i);
			} else {
				coStr = coStr + ", " + strArr.get(i);
			}
		}
		return coStr;

	}
	public static String getGenerateFileName(String fileFormat) {
		try{
			if(fileFormat.contains("DDMMYYYY")){
				String DDMMYYY =FormatUtil.display(ApplicationDate.getDate(),FormatUtil.EN,FormatUtil.Format.DDMMYYYY);
				return fileFormat.replace("DDMMYYYY", DDMMYYY);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return fileFormat;
	}
}
