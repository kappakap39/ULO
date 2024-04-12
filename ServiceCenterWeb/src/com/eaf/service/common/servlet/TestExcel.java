package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestExcel {
	private static transient Logger logger = Logger.getLogger(TestExcel.class);
	
	public void readExcel(){
		try{
			logger.debug("before read file");
			File file = new File("C:/Windows/system32/config/systemprofile/Desktop/test.xlsx");
			FileInputStream inputStream = new FileInputStream(file);
			XSSFWorkbook workBook = new XSSFWorkbook(inputStream);	//get workbook from file
			XSSFSheet sheet = workBook.getSheetAt(0);	//get first sheet
			Iterator<Row> rowIterator = sheet.iterator();	//get iterator to row
			while(rowIterator.hasNext()){
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while(cellIterator.hasNext()){
					Cell cell = cellIterator.next();
					switch(cell.getCellType()){
					case Cell.CELL_TYPE_STRING:
						break;
					case Cell.CELL_TYPE_NUMERIC:
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						break;
					default :
					}
				}
			}
			Double countRow = new Double(sheet.getLastRowNum()+1).doubleValue();
			logger.debug("count row : "+countRow);
			Map<String,Object[]> data = new HashMap<String, Object[]>();
			data.put(new Double(countRow).toString(), new Object[] {countRow, "Bill^3^","Peerapol","Phutthawetmongkhong"});
			
			Set<String> newRows = data.keySet();	//set iterate and add rows
			int rownum =  sheet.getLastRowNum()+1;	//get last row+1
			logger.debug("lastrow+1 : "+rownum);
			for(String key : newRows){
				logger.debug("key : "+key);
				Row row = sheet.createRow(rownum++);	//create new row
				Object[] objArr = data.get(key);
				int cellnum = 0;
				for(Object obj : objArr){
					Cell cell = row.createCell(cellnum++);
					if(obj instanceof String){
						cell.setCellValue((String)obj);
						logger.debug("String : "+obj);
					}else if(obj instanceof Boolean){
						cell.setCellValue((Boolean)obj);
						logger.debug("Boolean : "+obj);
					}else if(obj instanceof Date){
						cell.setCellValue((Date)obj);
						logger.debug("Date : "+obj);
					}else if(obj instanceof Double){
						cell.setCellValue((Double)obj);
						logger.debug("Double : "+obj);
					}
				}
			}
			
			FileOutputStream outputStream = new FileOutputStream(file);
			workBook.write(outputStream);
			
			inputStream.close();
			workBook.close();
			outputStream.close();
			logger.debug("write success");
		}catch(Exception e){
			logger.fatal("Error : "+e);
		}
	}
}
