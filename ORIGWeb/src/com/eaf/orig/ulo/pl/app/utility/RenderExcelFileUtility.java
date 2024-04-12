package com.eaf.orig.ulo.pl.app.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.eaf.orig.cache.properties.UserNameProperties;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM;
import com.eaf.orig.ulo.pl.model.app.PLImportCreditLineDataM;
import com.eaf.orig.ulo.pl.model.app.PLImportSpecialPointDataM;
import com.eaf.orig.ulo.pl.model.app.PLResponseImportSpecialPointDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;


public class RenderExcelFileUtility {
	
	private static final short ALIGN_CENTER = 2;
	private static final short BOLDWEIGHT_BOLD = 700;
	private Logger log = Logger.getLogger(this.getClass());
	
	public void exportExcelFile(HttpServletRequest request, HttpServletResponse response){		
		renderXLS(request, response);
		
	}
	public void renderXLS(HttpServletRequest request, HttpServletResponse response){
		
		ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
		Vector vUser = (Vector)origCache.loadUserNameCache(OrigConstant.Status.STATUS_ACTIVE);
		
		String dateFrom = request.getParameter("dateFrom");
		String dateTo = request.getParameter("dateTo");
		
		
		try{
			dateFrom = DataFormatUtility.StringDateTHToStringDateEN(dateFrom);
			dateTo = DataFormatUtility.StringDateTHToStringDateEN(dateTo);
		}catch(Exception e){
			log.fatal("Exception >> ",e);
		}
		Vector<String[]> resultVect = new Vector<String[]>();
		try{
			ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();
			resultVect = origBean.loadCBConsent(dateFrom, dateTo);
		}catch(Exception e){
			log.fatal("Exception >> ",e);
		}
		
		
		Workbook wb = new HSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet("Sheet1");
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font font = wb.createFont();
		font.setColor(IndexedColors.WHITE.getIndex());
		font.setBoldweight(BOLDWEIGHT_BOLD);
		style.setFont(font);
		style.setAlignment(ALIGN_CENTER);
		
		CellStyle cellStyleDate = wb.createCellStyle();
		cellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

		DataFormat format = wb.createDataFormat();
		CellStyle styleBlue = wb.createCellStyle();
		styleBlue.setDataFormat(format.getFormat("#,##0.00"));
		Font fontBlue = wb.createFont();
		fontBlue.setColor(IndexedColors.BLUE.getIndex());
		styleBlue.setFont(fontBlue);
		
		CellStyle styleRed = wb.createCellStyle();
		Font fontRed = wb.createFont();
		fontRed.setColor(IndexedColors.RED.getIndex());
		styleRed.setFont(fontRed);
		styleRed.setDataFormat(format.getFormat("#,##0.00"));		 
		CellStyle styleNumber = wb.createCellStyle();
		styleNumber.setDataFormat(format.getFormat("#,##0.00"));
		
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue(PLMessageResourceUtil.getTextDescription(request, "CONSENT_REF_NO_DATE"));
		row.createCell(1).setCellValue(PLMessageResourceUtil.getTextDescription(request, "RECEIVE_JOB_DATE"));
		row.createCell(2).setCellValue(PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO"));
		row.createCell(3).setCellValue(PLMessageResourceUtil.getTextDescription(request, "SALE_TYPE"));
		row.createCell(4).setCellValue(PLMessageResourceUtil.getTextDescription(request, "FULL_NAME"));
		row.createCell(5).setCellValue(PLMessageResourceUtil.getTextDescription(request, "ID_NO"));
		row.createCell(6).setCellValue(PLMessageResourceUtil.getTextDescription(request, "BIRTH_DATE"));
		row.createCell(7).setCellValue(PLMessageResourceUtil.getTextDescription(request, "BUREAU_REQUESTER"));
		row.createCell(8).setCellValue(PLMessageResourceUtil.getTextDescription(request, "APPROVE_BUREAU_BY"));
		row.createCell(9).setCellValue(PLMessageResourceUtil.getTextDescription(request, "CONSENT_REF_NO"));

		
		for (int i = 0; i <= 9; i++) {
			row.getCell(i).setCellStyle(style);
			sheet.autoSizeColumn(i);
		}
		
		
		if (resultVect != null && resultVect.size() > 0) {
			int curRow = 1;
			for (int i = 0; i < resultVect.size(); i++) {
				String[] stringResult = resultVect.get(i);
				Row rowRec = sheet.createRow(curRow);
				int index = 0;
				
				try {
					rowRec.createCell(0).setCellValue(DataFormatUtility.stringDateTimeValueListForThai(stringResult[index++]));
					rowRec.createCell(1).setCellValue(DataFormatUtility.stringDateTimeValueListForThai(stringResult[index++]));
					rowRec.createCell(2).setCellValue(stringResult[index++]);
					rowRec.createCell(3).setCellValue(HTMLRenderUtil.displaySaleType(stringResult[index++]));
					rowRec.createCell(4).setCellValue(stringResult[index++]);
					rowRec.createCell(5).setCellValue(stringResult[index++]);
					rowRec.createCell(6).setCellValue(DataFormatUtility.stringDateValueListForThai(stringResult[index++]));
					rowRec.createCell(7).setCellValue(HTMLRenderUtil.displayThFullName(stringResult[index++], vUser));
					rowRec.createCell(8).setCellValue(HTMLRenderUtil.displayThFullName(stringResult[index++], vUser));
					rowRec.createCell(9).setCellValue(stringResult[index++]);
				} catch (Exception e) {
					log.fatal(e.getMessage());
				}
				curRow++;
			}
		}
		
		try {
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {			 
			log.fatal(e.getMessage());
		}

	}
	
	public void renderRejectReportXLS(HttpServletRequest request, HttpServletResponse response, Vector<PLImportSpecialPointDataM> errImportVect){
		
		log.debug("renderRejectReportXLS");
		
		Workbook wb = new HSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet("Sheet1");
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font font = wb.createFont();
		font.setColor(IndexedColors.WHITE.getIndex());
		font.setBoldweight(BOLDWEIGHT_BOLD);
		style.setFont(font);
		style.setAlignment(ALIGN_CENTER);
		
		CellStyle cellStyleDate = wb.createCellStyle();
		cellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

		DataFormat format = wb.createDataFormat();
		CellStyle styleBlue = wb.createCellStyle();
		styleBlue.setDataFormat(format.getFormat("#,##0.00"));
		Font fontBlue = wb.createFont();
		fontBlue.setColor(IndexedColors.BLUE.getIndex());
		styleBlue.setFont(fontBlue);
		
		CellStyle styleRed = wb.createCellStyle();
		Font fontRed = wb.createFont();
		fontRed.setColor(IndexedColors.RED.getIndex());
		styleRed.setFont(fontRed);
		styleRed.setDataFormat(format.getFormat("#,##0.00"));		 
		CellStyle styleNumber = wb.createCellStyle();
		styleNumber.setDataFormat(format.getFormat("#,##0.00"));
		
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue(PLMessageResourceUtil.getTextDescription(request, "EMPLOYEE_CODE"));
		row.createCell(1).setCellValue(PLMessageResourceUtil.getTextDescription(request, "SPECIAL_WORK_HRS"));
		row.createCell(2).setCellValue(PLMessageResourceUtil.getTextDescription(request, "SPECIAL_WORK_APP"));
		row.createCell(3).setCellValue(PLMessageResourceUtil.getTextDescription(request, "SPECIAL_WORK_POINT"));
		row.createCell(4).setCellValue(PLMessageResourceUtil.getTextDescription(request, "OT_HRS"));
		row.createCell(5).setCellValue(PLMessageResourceUtil.getTextDescription(request, "OT_APP"));
		row.createCell(6).setCellValue(PLMessageResourceUtil.getTextDescription(request, "OT_POINT"));
		row.createCell(7).setCellValue(PLMessageResourceUtil.getTextDescription(request, "TRAINING_MEETING_ETC"));
		row.createCell(8).setCellValue(PLMessageResourceUtil.getTextDescription(request, "REASON_EN"));

		
		for (int i = 0; i <= 8; i++) {
			row.getCell(i).setCellStyle(style);
			sheet.autoSizeColumn(i);
		}
		
		
		if (errImportVect != null && errImportVect.size() > 0) {
			int curRow = 1;
			for (int i = 0; i < errImportVect.size(); i++) {
				PLImportSpecialPointDataM errImportM = errImportVect.get(i);
				Row rowRec = sheet.createRow(curRow);
				
				try {
					
					rowRec.createCell(0).setCellValue(errImportM.getE_userId());
					rowRec.createCell(1).setCellValue(errImportM.getE_specialWork_hrs());
					rowRec.createCell(2).setCellValue(errImportM.getE_specialWorkApp());
					rowRec.createCell(3).setCellValue(errImportM.getE_specialWorkPoint());
					rowRec.createCell(4).setCellValue(errImportM.getE_ot_hrs());
					rowRec.createCell(5).setCellValue(errImportM.getE_ot_app());
					rowRec.createCell(6).setCellValue(errImportM.getE_ot_point());
					rowRec.createCell(7).setCellValue(errImportM.getE_remark());
					rowRec.createCell(8).setCellValue(errImportM.getReason());
					
				} catch (Exception e) {
					log.fatal(e.getMessage());
				}
				curRow++;
			}
		}
		
		try {
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {			 
			log.fatal(e.getMessage());
		}

	}
	
	public void renderRejectReportCreditLineXLS(HttpServletRequest request, HttpServletResponse response, Vector<PLImportCreditLineDataM> errImportVect){
		
		Workbook wb = new HSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet("Sheet1");
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font font = wb.createFont();
		font.setColor(IndexedColors.WHITE.getIndex());
		font.setBoldweight(BOLDWEIGHT_BOLD);
		style.setFont(font);
		style.setAlignment(ALIGN_CENTER);
		
		CellStyle cellStyleDate = wb.createCellStyle();
		cellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

		DataFormat format = wb.createDataFormat();
		CellStyle styleBlue = wb.createCellStyle();
		styleBlue.setDataFormat(format.getFormat("#,##0.00"));
		Font fontBlue = wb.createFont();
		fontBlue.setColor(IndexedColors.BLUE.getIndex());
		styleBlue.setFont(fontBlue);
		
		CellStyle styleRed = wb.createCellStyle();
		Font fontRed = wb.createFont();
		fontRed.setColor(IndexedColors.RED.getIndex());
		styleRed.setFont(fontRed);
		styleRed.setDataFormat(format.getFormat("#,##0.00"));		 
		CellStyle styleNumber = wb.createCellStyle();
		styleNumber.setDataFormat(format.getFormat("#,##0.00"));
		
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue(PLMessageResourceUtil.getTextDescriptionMessage(request, "CARD_NO"));
		row.createCell(1).setCellValue(PLMessageResourceUtil.getTextDescriptionMessage(request, "CREDIT_LINE"));
		row.createCell(2).setCellValue(PLMessageResourceUtil.getTextDescriptionMessage(request, "REASON"));
		
		for (int i = 0; i < 3; i++) {
			row.getCell(i).setCellStyle(style);
			sheet.autoSizeColumn(i);
		}
		
		
		if (errImportVect != null && errImportVect.size() > 0) {
			int curRow = 1;
			for (int i = 0; i < errImportVect.size(); i++) {
				PLImportCreditLineDataM errImportM = errImportVect.get(i);
				Row rowRec = sheet.createRow(curRow);
				
				try {
					rowRec.createCell(0).setCellValue(errImportM.getCardNo());
					rowRec.createCell(1).setCellValue(errImportM.getCreditLine());
					rowRec.createCell(2).setCellValue(PLMessageResourceUtil.getTextDescriptionMessage(request, errImportM.getReason()));
				} catch (Exception e) {
					log.fatal(e.getMessage());
				}
				curRow++;
			}
		}
		
		try {
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {			 
			log.fatal(e.getMessage());
		}

	}
	
	public PLResponseImportSpecialPointDataM importExcelFile(HttpServletRequest request, HttpServletResponse response, PLAttachmentHistoryDataM attachmentDataM, String dataDate) throws Exception {
		DecimalFormat decFormat = new DecimalFormat("##");
		ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
		final Vector vUser = (Vector)origCache.loadUserNameCache(OrigConstant.Status.STATUS_ACTIVE);
		String filename = attachmentDataM.getFilePath()+File.separator+attachmentDataM.getFileName();
		String fileType = attachmentDataM.getFileName().substring(attachmentDataM.getFileName().lastIndexOf(".")+1, attachmentDataM.getFileName().length());
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		try {
			dataDate = DataFormatUtility.StringDateTHToStringDateEN(dataDate);
		} catch (Exception e) {
			log.fatal(e.getMessage());
		}
		
		Date date = null;
		try {
			date = DataFormatUtility.parseDate(DataFormatUtility.stringToDate(dataDate,"dd/mm/yyyy"));
		} catch (Exception e) {
			log.fatal(e.getMessage());
		}
		
		Vector<PLImportSpecialPointDataM> importSpePointVect = new Vector<PLImportSpecialPointDataM>();

        FileInputStream fis = null;
        int countRow = 0;
        try {
        	
            fis = new FileInputStream(filename);
            Workbook wb;
            
            if(fileType!=null && "XLS".equals(fileType.toUpperCase())){
            	wb = new HSSFWorkbook(fis);
            } else {
            	wb = new XSSFWorkbook(fis);
            }
            Sheet sheet = wb.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            
            if(rows.hasNext()) {
            	rows.next();
            }
            
            while (rows.hasNext()) {
            	log.debug("countRow = "+countRow);
            	Row  row = (Row) rows.next();
            	if(this.isNotEmptyExcelRow(row)){
	                Iterator cells = row.cellIterator();
	                
	                StringBuilder strErrorMsg = new StringBuilder();
	                boolean checkUser = false;
	                PLImportSpecialPointDataM importSpePointM = new PLImportSpecialPointDataM();
	                
	                Cell cell_0 = (Cell) row.getCell(0);	//userId
	                Cell cell_1 = (Cell) row.getCell(1);	//special work hrs
	                Cell cell_2 = (Cell) row.getCell(2);	//special work app
	                Cell cell_3 = (Cell) row.getCell(3);	//special work point
	                Cell cell_4 = (Cell) row.getCell(4);	//ot hr
	                Cell cell_5 = (Cell) row.getCell(5);	//ot app
	                Cell cell_6 = (Cell) row.getCell(6);	//ot point
	                Cell cell_7 = (Cell) row.getCell(7);	//remark
	                
	                //-----userId
	                String userId = null;
	                try{
						if (cell_0.getCellType() == HSSFCell.CELL_TYPE_NUMERIC || cell_0.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
							userId = decFormat.format(cell_0.getNumericCellValue());
		                } else {
		                	userId = cell_0.getStringCellValue();
		                }
						log.debug("userId:" + userId);
						importSpePointM.setUserId(userId);
	                }catch(Exception e){
	                	strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, OrigConstant.ErrorMsg.INVALID_NUMBER)).append(", ");
	                }finally{
	                	importSpePointM.setE_userId(userId);
	                }
					if(!OrigUtil.isEmptyVector(vUser)){
						for (int i = 0; i < vUser.size(); i++) {
							UserNameProperties userPro = new UserNameProperties();
							userPro = (UserNameProperties) vUser.get(i);
							if(!OrigUtil.isEmptyString(userPro.getUserName()) && !OrigUtil.isEmptyString(importSpePointM.getUserId()) && importSpePointM.getUserId().equals(userPro.getUserName())){
								checkUser = true;
								break;
							}
		            	}
					}
					if(!checkUser){
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, "EMPLOYEE_CODE")).append(" ");
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, OrigConstant.ErrorMsg.NOT_FOUND_USER_ID)).append(", ");
					}
					if(!OrigUtil.isEmptyString(importSpePointM.getUserId()) && importSpePointM.getUserId().length()>50){
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, "EMPLOYEE_CODE")).append(" ");
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, OrigConstant.ErrorMsg.DATA_TOO_LARGE)).append(", ");
					}
	                
					//-----special work hrs
					String workHoursStr = "";
					try{
						if(cell_1 != null){
							if(cell_1.getCellType() == HSSFCell.CELL_TYPE_NUMERIC || cell_1.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
								workHoursStr = String.valueOf(cell_1.getNumericCellValue());
							}else{
								workHoursStr = cell_1.getStringCellValue();
							}
							log.debug("workHoursStr:" + workHoursStr);
							if(!this.isEmptyCell(workHoursStr)){
								importSpePointM.setSpecialWork_hrs(new BigDecimal(Double.parseDouble(workHoursStr)).setScale(2, RoundingMode.HALF_UP));
							}
						}
					}catch(Exception e){
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, "SPECIAL_WORK_HRS")).append(" ");
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, OrigConstant.ErrorMsg.INVALID_NUMBER)).append(", ");
					}finally{
						importSpePointM.setE_specialWork_hrs(workHoursStr);
					}
					
					//-----special work app
					String workAppStr = "";
					double workApp;
					try{
						if(cell_2 != null){
							if(cell_2.getCellType() == HSSFCell.CELL_TYPE_NUMERIC || cell_2.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
								workAppStr = String.valueOf(cell_2.getNumericCellValue());						
							}else{
								workAppStr = cell_2.getStringCellValue();
							}
							log.debug("workAppStr:" + workAppStr);
							if(!this.isEmptyCell(workAppStr)){
								workApp = Double.parseDouble(workAppStr);
								if (workApp % 1 == 0) {
									importSpePointM.setSpecialWorkApp(Integer.parseInt(decFormat.format(workApp)));
								}else{
									strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, "SPECIAL_WORK_APP")).append(" ");
									strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, OrigConstant.ErrorMsg.INVALID_NUMBER)).append(", ");
								}
							}
						}
					}catch(Exception e){
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, "SPECIAL_WORK_APP")).append(" ");
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, OrigConstant.ErrorMsg.INVALID_NUMBER)).append(", ");
					}finally{
						importSpePointM.setE_specialWorkApp(workAppStr);
					}
					
					//-----special work point
					String workPointStr = "";
					double workPoint;
					try{
						if(cell_3 != null){
							if(cell_3.getCellType() == HSSFCell.CELL_TYPE_NUMERIC || cell_3.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
								workPointStr = String.valueOf(cell_3.getNumericCellValue());
							}else{
								workPointStr = cell_3.getStringCellValue();
							}
							log.debug("workPointStr:" + workPointStr);
							if(!this.isEmptyCell(workPointStr)){
								workPoint = Double.parseDouble(workPointStr);
								if (workPoint % 1 == 0) {
									importSpePointM.setSpecialWorkPoint(Integer.parseInt(decFormat.format(workPoint)));
								}else{
									strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, "SPECIAL_WORK_POINT")).append(" ");
									strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, OrigConstant.ErrorMsg.INVALID_NUMBER)).append(", ");
								}
							}
						}
					}catch(Exception e){
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, "SPECIAL_WORK_POINT")).append(" ");
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, OrigConstant.ErrorMsg.INVALID_NUMBER)).append(", ");
					}finally{
						importSpePointM.setE_specialWorkPoint(workPointStr);
					}
	                
					//-----ot hr
					String otHourStr = "";
					try{
						if(cell_4 != null){
							if (cell_4.getCellType() == HSSFCell.CELL_TYPE_NUMERIC || cell_4.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
								otHourStr = String.valueOf(cell_4.getNumericCellValue());
							}else{
								otHourStr = cell_4.getStringCellValue();
							}
							log.debug("otHourStr:" + otHourStr);
							if(!this.isEmptyCell(otHourStr)){
								importSpePointM.setOt_hrs(new BigDecimal(Double.parseDouble(otHourStr)).setScale(2, RoundingMode.HALF_UP));
							}
						}
					}catch(Exception e){
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, "OT_HRS")).append(" ");
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, OrigConstant.ErrorMsg.INVALID_NUMBER)).append(", ");
					}finally{
						importSpePointM.setE_ot_hrs(otHourStr);
					}
					
					//-----ot app
					String otAppStr = "";
					double otApp;
					try{
						if(cell_5 != null){
							if(cell_5.getCellType() == HSSFCell.CELL_TYPE_NUMERIC || cell_5.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
								otAppStr = String.valueOf(cell_5.getNumericCellValue());						
							}else{
								otAppStr = cell_5.getStringCellValue();
							}
							log.debug("otAppStr:" + otAppStr);
							if(!this.isEmptyCell(otAppStr)){
								otApp = Double.parseDouble(otAppStr);
								if (otApp % 1 == 0) {
									importSpePointM.setOt_app(Integer.parseInt(decFormat.format(otApp)));
								}else{
									strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, "OT_APP")).append(" ");
									strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, OrigConstant.ErrorMsg.INVALID_NUMBER)).append(", ");
								}
							}
						}
					}catch(Exception e){
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, "OT_APP")).append(" ");
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, OrigConstant.ErrorMsg.INVALID_NUMBER)).append(", ");
					}finally{
						importSpePointM.setE_ot_app(otAppStr);
					}
					
					//-----ot point
					String otPointStr = "";
					double otPoint;
					try{
						if(cell_6 != null){
							if(cell_6.getCellType() == HSSFCell.CELL_TYPE_NUMERIC || cell_6.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
								otPointStr = String.valueOf(cell_6.getNumericCellValue());
							}else{
								otPointStr = cell_6.getStringCellValue();
							}
							log.debug("otPointStr:" + otPointStr);
							if(!this.isEmptyCell(otPointStr)){
								otPoint = Double.parseDouble(otPointStr);
								if (otPoint % 1 == 0) {
									importSpePointM.setOt_point(Integer.parseInt(decFormat.format(otPoint)));
								}else{
									strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, "OT_POINT")).append(" ");
									strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, OrigConstant.ErrorMsg.INVALID_NUMBER)).append(", ");
								}
							}
						}
					}catch(Exception e){
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, "OT_POINT")).append(" ");
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, OrigConstant.ErrorMsg.INVALID_NUMBER)).append(", ");
					}finally{
						importSpePointM.setE_ot_point(otPointStr);
					}
					
					//-----remark
					String remark = "";
					try{
						if(cell_7 != null){
							if(cell_7.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								remark = String.valueOf(cell_7.getNumericCellValue());
							}else{
								remark = cell_7.getStringCellValue();
							}
							log.debug("remark:" + remark);
							importSpePointM.setRemark(remark);
						}
					}catch(Exception e){
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, "OT_APP")).append(" ");
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, OrigConstant.ErrorMsg.INVALID_NUMBER)).append(", ");
					}finally{
						importSpePointM.setE_remark(remark);
					}
					
					if (!OrigUtil.isEmptyString(importSpePointM.getRemark()) && importSpePointM.getRemark().length() > 100) {
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, "TRAINING_MEETING_ETC")).append(" ");
						strErrorMsg.append(PLMessageResourceUtil.getTextDescription(request, OrigConstant.ErrorMsg.DATA_TOO_LARGE)).append(", ");
					}
					
					if (strErrorMsg.length() > 0) {
						strErrorMsg.delete(strErrorMsg.length() - 2, strErrorMsg.length());
					}
					importSpePointM.setReason(strErrorMsg.toString());
	                importSpePointM.setCreateBy(userM.getUserName());
	                importSpePointM.setUpdateBy(userM.getUserName());
	                importSpePointM.setDataDate(date);
	                
	                importSpePointVect.add(importSpePointM);
	                countRow++;
	            }
            }
            
        } catch (Exception e) {
        	SearchHandler handler = new SearchHandler();//(SearchHandler)request.getSession().getAttribute("SEARCH_DATAM");
//        	if(handler==null){handler = new SearchHandler();}
			
			SearchHandler.SearchDataM searchM = handler.getSearchM();
			if(searchM==null){searchM = new SearchHandler.SearchDataM();}
			
			searchM.setPrefixErrorMsg(PLMessageResourceUtil.getTextDescription(request, "ERROR_DATA_FILE"));
			handler.PushErrorMessage("error", " ");
			handler.setSearchM(searchM);
			request.getSession().setAttribute("SEARCH_DATAM", handler);
        	
            log.fatal(e.getMessage());
			throw new Exception(e);
        } finally {
            if (fis != null) {
                try {
					fis.close();
				} catch (IOException e) {
					log.fatal(e.getMessage());
				}
            }
            
        }

        Vector<PLImportSpecialPointDataM> errorImportVect = saveExcel(importSpePointVect, date, request);
        
        PLResponseImportSpecialPointDataM resImportM = new PLResponseImportSpecialPointDataM();
        resImportM.setErrorImportReportVect(errorImportVect);
        resImportM.setTotalRecord(countRow);
        log.debug("resImportM.getTotalRecord() = "+resImportM.getTotalRecord());
        log.debug("resImportM.getErrorImportReportVect() = "+resImportM.getErrorImportReportVect());
        return resImportM;
	}
	
	private Vector<PLImportSpecialPointDataM> saveExcel(Vector<PLImportSpecialPointDataM> importSpePointVect, Date dataDate, HttpServletRequest request) {
		
		if (importSpePointVect != null && importSpePointVect.size() > 0) {
			log.debug("saveExcel in ifffffffffff");
//			 = new Vector<PLImportSpecialPointDataM>();
//			PLOrigImportSpecialPointDAOImpl plOirgImportSpecialPointDAO = (PLOrigImportSpecialPointDAOImpl) PLORIGDAOFactory.getPLOrigImportSpecialPointDAO();
			try {
//				errorImportVect = plOirgImportSpecialPointDAO.saveTableOrig_Special_Point(importSpePointVect, dataDate);
				Vector<PLImportSpecialPointDataM> errorImportVect = PLORIGEJBService.getORIGDAOUtilLocal().saveTableOrig_Special_Point(importSpePointVect, dataDate);
				return errorImportVect;
			} catch (Exception e) {
				log.fatal("saveExcel   saveExcel   saveExcel   saveExcel  "+e.getMessage());
				log.fatal("saveExcel   saveExcel   saveExcel   saveExcel  "+e.getCause());
			}
//			if (errorImportVect != null && errorImportVect.size() > 0) {
//				for(int i=0; i<errorImportVect.size(); i++){
//					PLImportSpecialPointDataM erImportM = errorImportVect.get(i);
//					erImportM.setReason(PLMessageResourceUtil.getTextDescription(request, erImportM.getReason()));
//				}
//			}
			
		}
		return null;
	}
	
	private boolean isEmptyCell(String cellValue){
		if(cellValue == null || "".equals(cellValue.trim()) || "null".equals(cellValue.trim()) || cellValue.length() == 0){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean isNotEmptyExcelRow(Row row){
		boolean result = false;
		short c;
		for (c = row.getFirstCellNum(); c < 8; c++) {
			Cell cell = row.getCell(c);
			if (cell != null && row.getCell(c).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
				result = true;
				break;
			}
		}
		return result;
	}
}
