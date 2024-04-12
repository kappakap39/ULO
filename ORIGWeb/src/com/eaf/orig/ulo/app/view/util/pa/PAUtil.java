package com.eaf.orig.ulo.app.view.util.pa;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class PAUtil 
{
	private static Logger logger = Logger.getLogger(PAUtil.class);
	public static String PA_CLAIM_JOB_TEXT = SystemConstant.getConstant("PA_CLAIM_JOB_TEXT");
	public static String PA_INSTRUCTION_MEMO = SystemConstant.getConstant("PA_INSTRUCTION_MEMO");
	public static String PA_STAMP_DUTY = SystemConstant.getConstant("PA_STAMP_DUTY");
	public static String PA_MAILING_ADDRESS = SystemConstant.getConstant("PA_MAILING_ADDRESS");
	
	public static String PA_NOT_CLAIM = SystemConstant.getConstant("PA_NOT_CLAIM");
	public static String PA_CLAIMED = SystemConstant.getConstant("PA_CLAIMED");
	public static String PA_COMPLETED = SystemConstant.getConstant("PA_COMPLETED");
	public static String PA_CANCELLED = SystemConstant.getConstant("PA_CANCELLED");
	public static String PA_WAIT_FRAUD_CHECK = SystemConstant.getConstant("PA_WAIT_FRAUD_CHECK");
	
	public static String PA_STAMP_DUTY_ACTION_LABEL = SystemConstant.getConstant("PA_STAMP_DUTY_ACTION_LABEL");
	public static String PA_MAILING_ADDRESS_ACTION_LABEL = SystemConstant.getConstant("PA_MAILING_ADDRESS_ACTION_LABEL");
	public static String PA_STAMP_DUTY_ACTION_TYPE_CODE = SystemConstant.getConstant("PA_STAMP_DUTY_ACTION_TYPE_CODE");
	public static String PA_MAILING_ADDRESS_ACTION_TYPE_CODE = SystemConstant.getConstant("PA_MAILING_ADDRESS_ACTION");
	
	public static String RESEND_TYPE_EMAIL = SystemConstant.getConstant("RESEND_TYPE_EMAIL");
	public static String RESEND_TYPE_PAPER = SystemConstant.getConstant("RESEND_TYPE_PAPER");
	public static String RESEND_TYPE_EMAIL_STR = SystemConstant.getConstant("RESEND_TYPE_EMAIL_STR");
	public static String RESEND_TYPE_PAPER_STR = SystemConstant.getConstant("RESEND_TYPE_PAPER_STR");
	
	public static String getClaimJobActionLabel(String action)
	{
		String label = SystemConstant.getConstant(action + "_LABEL");
		if(isMyTask(action))
		{
			return label;
		}
		else
		{return PA_CLAIM_JOB_TEXT + label;}
	}
	
	public static boolean isMyTask(String action)
	{
		if(action != null && action.contains("MY_TASK_ACTION"))
		{
			return true;
		}
		return false;
	}
	
	public static boolean isStampDuty(String action)
	{
		if(action != null && action.contains("STAMP_DUTY_ACTION"))
		{
			return true;
		}
		return false;
	}
	
	public static boolean isAccountSetup(String action)
	{
		if(action != null && action.contains("ACCOUNT_SETUP_ACTION"))
		{
			return true;
		}
		return false;
	}
	
	public static String getClaimJobActionType(String action)
	{
		String typeCode = SystemConstant.getConstant(action + "_TYPE_CODE");
		if(typeCode == null) 
		{return "";}
		else
		{return typeCode;}
	}
	
	public static String claimFlagCheckBox(String claimFlag, String loanSetupStatus, boolean isCTHead)
	{
		if(
				loanSetupStatus.equalsIgnoreCase("COMPLETED") 
				|| loanSetupStatus.equalsIgnoreCase("CANCELLED")
				// Incident 1237962
				|| (isCTHead && !claimFlag.equals(MConstant.FLAG.YES))
				|| (!isCTHead && claimFlag.equals(MConstant.FLAG.YES))
		  )
		{
			return HtmlUtil.VIEW;
		}
		
		return HtmlUtil.EDIT;
	}
	
	public static String completeFlagCheckBox(String completeFlag)
	{
		if(completeFlag.equals(MConstant.FLAG.YES))
		{return HtmlUtil.VIEW;}
		return HtmlUtil.EDIT;
	}
	
	public static String formatDate(Object dateObj,String format)
	{
		try
		{
			if(dateObj != null)
			{
				if(dateObj instanceof java.sql.Timestamp)
				{
					return new SimpleDateFormat(format).format((java.sql.Timestamp)dateObj);
				}
				if(dateObj instanceof java.sql.Date)
				{
					return new SimpleDateFormat(format).format((java.sql.Date)dateObj);
				}
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		    return "Invalid Date";
		}
		return "-";
	}
	
	public static String calcCreditLimitDate(Object obj)
	{
		
		String s = "-";
		try
		{
			if(obj != null)
			{
				Timestamp tS = (java.sql.Timestamp) obj;
				Calendar cal = Calendar.getInstance();
				cal.setTime(tS);
				
				//TODO create logic to calculate date from requirements
				cal.add(Calendar.DAY_OF_WEEK, 1);
				
				tS.setTime(cal.getTime().getTime()); 
				s = new SimpleDateFormat("dd/MM/yyyy").format(tS);
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			return "Invalid Date";
		}
		
		return s;
	}
	
	public static String getViewLink(String jobType,String appGroupNo, String completeFlag)
	{
		logger.debug(jobType + " - " + appGroupNo  + " " + completeFlag);
		String linkURL = "";
		if(!Util.empty(completeFlag))
		{
			completeFlag = "','" + completeFlag;
		}
		if(jobType.equals("I"))
		{linkURL = "getMemoLink('" + appGroupNo + completeFlag + "');";}
		if(jobType.equals("S"))
		{linkURL = "getStampLink('" + appGroupNo + completeFlag + "');";}
		if(jobType.equals("M"))
		{linkURL = "getMailLink('" + appGroupNo + completeFlag + "');";}
		logger.debug("return : " + linkURL);
		return linkURL;
	}
	
	public static int countCompleteFlag(ArrayList<HashMap<String, Object>> list)
	{
		int count = 0;
		if (list != null & list.size() > 0) 
		{
			for (HashMap<String, Object> row : list) 
			{
				if(row.get("COMPLETE_FLAG") != null && row.get("COMPLETE_FLAG").toString().equals(MConstant.FLAG.YES))
				{
					count++;
				}
			}
		}
		return count;
	}
	
	public static void claimJob(String claimId,String claimBy)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		

		OrigObjectDAO orig = new OrigObjectDAO();
		try{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE LOAN_SETUP_CLAIM SET CLAIM_FLAG = ?, ");
			sql.append(" CLAIM_BY= ? , CLAIM_DATE = ? , LOAN_SETUP_STATUS = ?");
			sql.append(" WHERE CLAIM_ID = ?");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, "Y");
			ps.setString(2, claimBy);
			ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
			ps.setString(4, "Claimed");
			ps.setString(5, claimId);
			logger.debug("Claim job for user : " + claimBy);
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				orig.closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	public static boolean isUnClaimAuthorized(String userName)
	{
		//TODO logic for unclaim authorize
		return true;
	}
	
	public static String getAccountSetupClaimStatus(String appGroupNo)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		OrigObjectDAO orig = new OrigObjectDAO();
		ArrayList<String> claimStatusList = new ArrayList<String>();
		
		try{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT DISTINCT LOAN_SETUP_STATUS FROM LOAN_SETUP_CLAIM ");
			sql.append(" WHERE APPLICATION_GROUP_NO = ?");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appGroupNo);
			logger.debug("getAccountSetupClaimStatus : " + appGroupNo);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				claimStatusList.add(rs.getString("LOAN_SETUP_STATUS"));
			}
			
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				orig.closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		
		if(claimStatusList.size() > 0)
		{
			boolean allComplete = true;
			boolean claim = false;
			for(String claimS : claimStatusList)
			{
				if(!claimS.equals(PA_COMPLETED))
				{allComplete = false;}
				if(claimS.equals(PA_CLAIMED))
				{claim = true;}
				if(claimS.equals(PA_CANCELLED))
				{return PA_CANCELLED;}
			}
			
			if(allComplete)
			{
				return PA_COMPLETED;
			}
			else if(claim)
			{
				return PA_CLAIMED;
			}
			else
			{
				return PA_NOT_CLAIM;
			}
		}
		else
		{
			return "-";
		}
		
	}
	
	public static void createLoanSetupClaim(ApplicationGroupDataM applicationGroup)
	{
		try
		{
			String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
			String APPLICATION_GROUP_NO = applicationGroup.getApplicationGroupNo();
			
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
			String CIS_NO = personalInfo.getCisNo();
			String TH_TITLE_CODE = personalInfo.getThTitleCode();
			String TH_FIRST_NAME = personalInfo.getThFirstName();
			String TH_MID_NAME = personalInfo.getThMidName();
			String TH_LAST_NAME = personalInfo.getThLastName();
			
			Date DE2_SUBMIT_DATE = applicationGroup.getLastDecisionDate();
			
			String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
			AddressDataM workAddr = personalInfo.getAddress(ADDRESS_TYPE_WORK);
			String ADDRESS_ID = workAddr.getAddressId();
			
			//Loan data will be update later with EOD Batch
			String LOAN_ACCOUNT_NO = null;
			String LOAN_ACCOUNT_STATUS = null;
			Date LOAN_ACCOUNT_OPEN_DATE = null;
			
			PreparedStatement ps = null;
			Connection conn = null;
			
			OrigObjectDAO orig = new OrigObjectDAO();
			try {
				conn = orig.getConnection();
				//first loop = stamp duty , second loop = mailing
				for(int i=0 ; i<2 ; i++)
				{
					StringBuffer sql = new StringBuffer("");
					sql.append("INSERT INTO LOAN_SETUP_CLAIM ");
					sql.append(" (CLAIM_ID, STAMP_DUTY_ID, ADDRESS_ID, TYPE, APPLICATION_GROUP_NO, CIS_NO, ");
					sql.append(" TH_TITLE_CODE, TH_FIRST_NAME, TH_MID_NAME, TH_LAST_NAME, DE2_SUBMIT_DATE, ");
					sql.append(" CLAIM_FLAG, CLAIM_BY, CLAIM_DATE, UN_CLAIM_FLAG_BY, UN_CLAIM_FLAG_DATE, ");
					sql.append(" COMPLETE_FLAG, COMPLETE_BY, COMPLETE_DATE, LOAN_SETUP_STATUS, LOAN_ACCOUNT_NO, ");
					sql.append(" LOAN_ACCOUNT_STATUS, LOAN_ACCOUNT_OPEN_DATE, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY) ");
					sql.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					String dSql = String.valueOf(sql);
					logger.debug("Sql=" + dSql);
					ps = conn.prepareStatement(dSql);
					int cnt = 1;
					ps.setString(cnt++, GenerateUnique.generate("PA_CLAIM_ID_PK",OrigServiceLocator.OL_DB));
					ps.setString(cnt++, (i==0) ? GenerateUnique.generate("PA_STAMP_DUTY_ID_PK",OrigServiceLocator.OL_DB) : null);
					ps.setString(cnt++, ADDRESS_ID);
					ps.setString(cnt++, (i==0) ? "S" : "M");
					ps.setString(cnt++, APPLICATION_GROUP_NO);
					ps.setString(cnt++, CIS_NO);
					ps.setString(cnt++, TH_TITLE_CODE);
					ps.setString(cnt++, TH_FIRST_NAME);
					ps.setString(cnt++, TH_MID_NAME);
					ps.setString(cnt++, TH_LAST_NAME);
					ps.setDate(cnt++, DE2_SUBMIT_DATE);
					ps.setString(cnt++, null);
					ps.setString(cnt++, null);
					ps.setDate(cnt++, null);
					ps.setString(cnt++, null);
					ps.setDate(cnt++, null);
					ps.setString(cnt++, null);
					ps.setString(cnt++, null);
					ps.setDate(cnt++, null);
					ps.setString(cnt++, PA_WAIT_FRAUD_CHECK);
					ps.setString(cnt++, LOAN_ACCOUNT_NO);
					ps.setString(cnt++, LOAN_ACCOUNT_STATUS);
					ps.setDate(cnt++, LOAN_ACCOUNT_OPEN_DATE);
					ps.setDate(cnt++, ApplicationDate.getDate());
					ps.setString(cnt++, applicationGroup.getCreateBy());
					ps.setDate(cnt++, ApplicationDate.getDate());
					ps.setString(cnt++, applicationGroup.getUpdateBy());
					
					ps.executeUpdate();
				}
				
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			} finally {
				try {
					orig.closeConnection(conn, ps);
				} catch (Exception e) {
					logger.fatal(e.getLocalizedMessage());
				}
			}
		}
		catch(Exception e)
		{
			logger.debug(e.getMessage() + " # Fail to create setup claim");
		}
	}
	
	public static XSSFWorkbook exportClaimResults(String contextPath, ArrayList<HashMap<String, Object>> results, String claimType, String UserName)
	{
		//load Template
		String SEARCH_CLAIM_EXCEL_TEMPLATE = SystemConstant.getConstant("SEARCH_CLAIM_EXCEL_TEMPLATE");
		String templateFilePath = contextPath + "/excel/" + SEARCH_CLAIM_EXCEL_TEMPLATE;
		logger.debug("Template File Path : " + templateFilePath);
		XSSFWorkbook wb = null;
		try
		{
			File templateFile = new File(templateFilePath);
			File tempFile = File.createTempFile("expClaim_",".xlsx");
			Files.copy(Paths.get(templateFile.getAbsolutePath()), Paths.get(tempFile.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
			if(templateFile.exists())
			{
				wb = new XSSFWorkbook(tempFile);
				XSSFCellStyle borderStyle = borderCellStyle(wb);
				XSSFSheet sheet = wb.getSheetAt(0); //Get first Sheet
				logger.debug("SheetName : " + sheet.getSheetName());
				
				//A3 = Date as of MM DD, YYYY
				//A2 = Cliam Job - JOB_TYPE
				//O6 = DD/MM/YYYYY  hh:mm:ss AMPM
				//O7 = Export ID: EXP_R001
				//A0-O10 = First row of results
				
				Calendar cal = Calendar.getInstance();
				int DD = cal.get(Calendar.DAY_OF_MONTH);
				int YYYY = cal.get(Calendar.YEAR);
				String MM = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
				int hour = cal.get(Calendar.HOUR_OF_DAY);
				int minute = cal.get(Calendar.MINUTE);
				int second = cal.get(Calendar.SECOND);
				String AMPM = (cal.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM";
				claimType = getClaimTypeFullName(claimType);
				
				getCell(sheet, "A3").setCellValue("Date as of " + MM + " " +  DD + ", " + YYYY);
				getCell(sheet, "A2").setCellValue("Cliam Job - " + claimType);
				getCell(sheet, "O6").setCellValue(DD + "/" + MM + "/" + YYYY + "  " + hour + ":" + minute + ":" + second + " " + AMPM);
				getCell(sheet, "O7").setCellValue("Export ID: " + UserName);
				
				int row_Index = 10;
				for (HashMap<String, Object> Row : results)
				{
					setCell(sheet, "A" + row_Index, String.valueOf(row_Index-9), borderStyle);
					setCell(sheet, "B" + row_Index, claimType, borderStyle);
					setCell(sheet, "C" + row_Index, FormatUtil.display(Row, "APPLICATION_GROUP_NO"), borderStyle);
					setCell(sheet, "D" + row_Index, FormatUtil.display(Row, "CIS_NO"), borderStyle);
					setCell(sheet, "E" + row_Index, (Row.get("TH_FIRST_NAME") == null) ? "" : (String)Row.get("TH_FIRST_NAME"), borderStyle);
					setCell(sheet, "F" + row_Index, (Row.get("TH_MID_NAME") == null) ? "-" : (String)Row.get("TH_MID_NAME"), borderStyle);
					setCell(sheet, "G" + row_Index, (Row.get("TH_LAST_NAME") == null) ? "" : (String)Row.get("TH_LAST_NAME"), borderStyle);
					setCell(sheet, "H" + row_Index, (Row.get("DE2_SUBMIT_DATE") == null) ? "-" : FormatUtil.display(Row, "DE2_SUBMIT_DATE"), borderStyle);
					//setCell(sheet, "I" + row_Index, PAUtil.calcCreditLimitDate(Row.get("DE2_SUBMIT_DATE")), borderStyle); //Defect#3312
					setCell(sheet, "I" + row_Index, (Row.get("LOAN_ACCOUNT_OPEN_DATE") == null) ? "-" : FormatUtil.display(Row, "LOAN_ACCOUNT_OPEN_DATE", "dd/MM/yyyy", FormatUtil.EN), borderStyle);
					setCell(sheet, "J" + row_Index, FormatUtil.display(Row, "STAMP_DUTY_FEE"), borderStyle);
					setCell(sheet, "K" + row_Index, (Row.get("CLAIM_FLAG") == null) ? "-" : FormatUtil.display(Row, "CLAIM_FLAG"), borderStyle);
					setCell(sheet, "L" + row_Index, (Row.get("CLAIM_BY") == null) ? "-" : FormatUtil.display(Row, "CLAIM_BY"), borderStyle);
					setCell(sheet, "M" + row_Index, (Row.get("CLAIM_DATE") == null) ? "-" : FormatUtil.display(Row, "CLAIM_DATE"), borderStyle);
					setCell(sheet, "N" + row_Index, (Row.get("COMPLETE_DATE") == null) ? "-" : FormatUtil.display(Row, "COMPLETE_DATE"), borderStyle);
					String LOAN_ACCOUNT_STATUS = "Inactive";
					if(MConstant.FLAG.ACTIVE.equals(FormatUtil.display(Row, "LOAN_ACCOUNT_STATUS")))
					{
						LOAN_ACCOUNT_STATUS = "Active";
					}
					setCell(sheet, "O" + row_Index, (Row.get("LOAN_ACCOUNT_NO") == null) ? "-" : FormatUtil.display(Row, "LOAN_ACCOUNT_NO") + " - (" + LOAN_ACCOUNT_STATUS + ")", borderStyle);
					
					row_Index++;
				}
				logger.debug("update workbook fields done.");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("[Error] - " + e.getMessage());
		}
			
		return wb;
	}

	public static String getClaimTypeFullName(String claimType)
	{		
		return SystemConstant.getConstant(claimType+ "_LABEL");
	}
	
	public static XSSFCell getCell(XSSFSheet sheet, String cellName)
	{
		//logger.debug("getCell - " + cellName);
		CellReference cr = new CellReference(cellName);
		XSSFRow row = sheet.getRow(cr.getRow());
		//logger.debug("is row " + cr.getRow() + " Null : " + row == null);
		if(row == null)
		{
			row = sheet.createRow(cr.getRow());
		}
		XSSFCell cell = row.getCell(cr.getCol());
		//logger.debug("is cell Null : " + cell == null);
		if(cell == null)
		{
			cell = row.createCell(cr.getCol());
		}
		
		return cell;
	}
	
	public static void setCell(XSSFSheet sheet, String cellName, String value, XSSFCellStyle style)
	{
		XSSFCell cell = getCell(sheet, cellName);
		
		cell.setCellValue(value);
		cell.setCellStyle(style);
	}
	
	public static XSSFCellStyle borderCellStyle(XSSFWorkbook wb)
	{
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		return cellStyle;
	}
	
	public static String getLetterTypeString(String letterTypeCode)
	{
		if(RESEND_TYPE_EMAIL.equals(letterTypeCode))
		{return RESEND_TYPE_EMAIL_STR;}
		if(RESEND_TYPE_PAPER.equals(letterTypeCode))
		{return RESEND_TYPE_PAPER_STR;}
		return "";
	}
	
	
}
