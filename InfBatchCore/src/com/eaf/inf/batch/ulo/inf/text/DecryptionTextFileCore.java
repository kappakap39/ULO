package com.eaf.inf.batch.ulo.inf.text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.GenerateFileInf;
import com.eaf.core.ulo.common.model.GenerateFileDataM;
import com.eaf.core.ulo.common.model.InfResultDataM;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FileUtil;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.core.ulo.security.exception.EncryptionException;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.inf.batch.ulo.kbmf.CardlinkValidation;
import com.eaf.orig.ulo.constant.MConstant;

public class DecryptionTextFileCore extends InfBatchObjectDAO implements GenerateFileInf {
	private static transient Logger logger = Logger.getLogger(DecryptionTextFileCore.class);
	String USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
	String CARDLINK_ACCOUNT_SETUP = InfBatchProperty.getInfBatchConfig(InfBatchConstant.CARDLINK.ACCOUNT_SETUP+"_"+InfBatchConstant.ReportParam.MODULE_ID);
	String CARDLINK_CASH_DAY1 = InfBatchProperty.getInfBatchConfig(InfBatchConstant.CARDLINK.CASH_DAY1+"_"+InfBatchConstant.ReportParam.MODULE_ID);
	String CARDLINK_CUSTOMER_OCCUPATION = InfBatchProperty.getInfBatchConfig(InfBatchConstant.CARDLINK.CUSTOMER_OCCUPATION+"_"+InfBatchConstant.ReportParam.MODULE_ID);
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	public String executeData(String MODULE_ID, String OUTPUT_NAME) throws Exception{
		String executeResult = "";
		if(CARDLINK_ACCOUNT_SETUP.equals(MODULE_ID)){
			executeResult = InfFactory.getInfDAO().getCardLinkAccountSetUp(USER_ID, OUTPUT_NAME);
		}else if(CARDLINK_CASH_DAY1.equals(MODULE_ID)){
			executeResult = InfFactory.getInfDAO().getCardLinkCashDay1(USER_ID, OUTPUT_NAME);
		}else if(CARDLINK_CUSTOMER_OCCUPATION.equals(MODULE_ID)){
			executeResult = InfFactory.getInfDAO().getCardLinkCustomerOccupation(USER_ID, OUTPUT_NAME);
		}
		return executeResult;
	}
	
	@Override
	public InfResultDataM create(GenerateFileDataM generateFile)throws Exception{
		InfResultDataM infResult = new InfResultDataM();
		try{
			String MODULE_ID = generateFile.getUniqueId();
			String FILE_NAME = generateFile.getFileOutputName();
			String executeResult = executeData(MODULE_ID,FILE_NAME);
			String contentText = "";
			if(CARDLINK_ACCOUNT_SETUP.equals(MODULE_ID))
			{
				//use executeResult instead which is ORIG+MLP merged result
				contentText = executeResult;
			}
			else
			{
				logger.debug("executeResult >> "+executeResult);
				contentText = InfFactory.getInfDAO().getInfExport(MODULE_ID);
			}
			
			generateFileProcess(generateFile,contentText);
			infResult.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infResult.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infResult.setResultDesc(e.getLocalizedMessage());
		}
		return infResult;
	}
	
	@Deprecated
	public String replaceText(String text){
		String[] lines = text.split("\n");
		Encryptor enc = EncryptorFactory.getDIHEncryptor();
		for(String line : lines){
			line = line.trim();
			if(line.matches("(.*)<card_no>(.*)")){
				String[] tagCardNos = line.split("<card_no>");
				for(String tagCardNo : tagCardNos){
					tagCardNo = tagCardNo.trim();
					if(tagCardNo.matches("(.*)</card_no>(.*)")){
						String[] valueObject = tagCardNo.split("</card_no>");
						String value = valueObject[0];
						String decryptCardNo = null;
						try{
							if(!InfBatchUtil.empty(value)&&!"0000000000000000".equals(value)){
								decryptCardNo = enc.decrypt(value);
							}else{
								decryptCardNo = getStringSpace(16);
							}
						}catch(Exception e){
							logger.fatal("ERROR ",e);
							logger.warn("ERROR "+e.getLocalizedMessage());
							decryptCardNo = getStringSpace(16);
						}
						text = text.replace("<card_no>"+value+"</card_no>",decryptCardNo);
					}
				}
			}
		}
		logger.info("record size >> "+lines.length);
		return text;
	}
	
	public String newReplaceText(String text){
		String[] lines = text.trim().split("\n");
		Encryptor encryt = EncryptorFactory.getDIHEncryptor();
		for(String line : lines){
			String[] cardNoList = StringUtils.substringsBetween(line, "<card_no>", "</card_no>");
			for(String cardNo : cardNoList){
				String decryptCardNo = "";
				try{
					if(!InfBatchUtil.empty(cardNo) && !"0000000000000000".equals(cardNo)){
						decryptCardNo = encryt.decrypt(cardNo);
					}else{
						decryptCardNo = getStringSpace(16);
					}
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					logger.warn("ERROR "+e.getLocalizedMessage());
					decryptCardNo = getStringSpace(16);
				}
				text = text.replace("<card_no>"+cardNo+"</card_no>",decryptCardNo);
			}
		}
		return text;
	}
	
	public String replaceCardNoWithDecryptedValue(String text){
		if(text == null || text.isEmpty()){
			return "";
		}
		
		final Pattern pattern = Pattern.compile("<card_no>(.+?)</card_no>");
		final Matcher matcher = pattern.matcher(text);
		int count = 1;
		Encryptor encryt = EncryptorFactory.getDIHEncryptor();
		final String spaces = getStringSpace(16);
		StringBuffer bufStr = new StringBuffer();

		while(matcher.find()){
			String value = matcher.group(1);
			if(value == null || value.isEmpty())continue;
			
			String decryptCardNo = null;
			if(!"0000000000000000".equals(value)){				
				try {
					decryptCardNo = encryt.decrypt(value);
				} catch (EncryptionException e) {
					e.printStackTrace();
				}
				if(null==decryptCardNo){
					decryptCardNo = "";
				}
				matcher.appendReplacement(bufStr, decryptCardNo);
			}else{
				matcher.appendReplacement(bufStr, spaces);
			}
			
			count ++;
		}
		matcher.appendTail(bufStr);
		
		logger.debug("All matched <card_no> tags = "+count);

		return bufStr.toString();
	}
	
	public String getStringSpace(int length){
		StringBuffer result = new StringBuffer("");
		for(int i=0; i<length; i++){
			result.append(" ");
		}		
		return result.toString();
	}

	@Override
	public InfResultDataM create(GenerateFileDataM generateFile, Connection con)throws Exception{
		return null;
	}
	public void generateFileProcess(GenerateFileDataM generateFile,String contentText)throws Exception{
		String CARDLINK_ACCOUNT_SETUP_VALIDATE_FLAG = InfBatchProperty.getInfBatchConfig("CARDLINK_ACCOUNT_SETUP_VALIDATE_FLAG");
		try{
			String MODULE_ID = generateFile.getUniqueId();
			String FILE_PATH = generateFile.getFileOutputPath();
			String FILE_NAME = generateFile.getFileOutputName();
			String ENCODE = generateFile.getEncode();
			String FILE_NAME_ERROR = generateFile.getFileOutputName()+"_ERROR";
			logger.debug("MODULE_ID : "+MODULE_ID);
			if(CARDLINK_ACCOUNT_SETUP.equals(MODULE_ID)){
				
				//Split part MLP and normal one
				String[] splitString = contentText.split("\\r?\\n");
				String flpStringPart = "";
				String mlpStringPart = "";
				boolean flpPart = true;
				for(String str : splitString)
				{
					if(str.startsWith("MLP_DATA"))
					{
						flpPart = false;
						continue;
					}
					if(flpPart)
					{
						flpStringPart += str + "\n";
					}
					else
					{
						mlpStringPart += str + "\n";
					}
				}
				
				flpStringPart = StringUtils.chomp(flpStringPart);
				mlpStringPart = StringUtils.chomp(mlpStringPart);
				//logger.debug("flpStringPart\n" + flpStringPart);
				//logger.debug("mlpStringPart\n" + mlpStringPart);
				
				if(CARDLINK_ACCOUNT_SETUP_VALIDATE_FLAG.equals(FLAG_YES))
				{
					String outputText = "";
					String notValidateOutputText = "";
					
					if(!Util.empty(flpStringPart) || !Util.empty(mlpStringPart))
					{
						String flpDecrypted = replaceCardNoWithDecryptedValue(flpStringPart);
						String mlpDecrypted = replaceCardNoWithDecryptedValue(mlpStringPart);
						
						flpStringPart = Util.empty(flpDecrypted) ? "" : CardlinkValidation.validateContent(flpDecrypted);
						mlpStringPart = Util.empty(mlpDecrypted) ? "" : CardlinkValidation.validateContent(mlpDecrypted);
						//Remove Duplicate
						outputText = removeDuplicateCardlink(flpStringPart, mlpStringPart);
						
						String notValidateFlpStringPartText = "";
						String notValidateMlpStringPartText = "";
						
						if(!InfBatchUtil.empty(flpDecrypted))
						{
							notValidateFlpStringPartText = CardlinkValidation.notValidateContent(flpDecrypted);
						}
						if(!InfBatchUtil.empty(mlpDecrypted))
						{
							notValidateMlpStringPartText = CardlinkValidation.notValidateContent(mlpDecrypted);
						}
						
						notValidateOutputText = notValidateFlpStringPartText;
						if(!Util.empty(notValidateMlpStringPartText))
						{
							notValidateOutputText += "\n" + notValidateMlpStringPartText;
						}
					}
					
					FileUtil.generateFile(FILE_PATH, FILE_NAME, outputText, ENCODE);
					FileUtil.generateFile(FILE_PATH, FILE_NAME_ERROR, notValidateOutputText, ENCODE);
				}else
				{
					String outputText = "";
					if(!Util.empty(flpStringPart) || !Util.empty(mlpStringPart))
					{
						String flpDecrypted = replaceCardNoWithDecryptedValue(flpStringPart);
						String mlpDecrypted = replaceCardNoWithDecryptedValue(mlpStringPart);
						
						flpStringPart = Util.empty(flpDecrypted) ? "" : flpDecrypted;
						mlpStringPart = Util.empty(mlpDecrypted) ? "" : mlpDecrypted;
						
						//Remove Duplicate
						outputText = removeDuplicateCardlink(flpStringPart, mlpStringPart);
					}
					FileUtil.generateFile(FILE_PATH,FILE_NAME,outputText,ENCODE);
				}
			}else{
				if(!InfBatchUtil.empty(contentText)){
					contentText = replaceCardNoWithDecryptedValue(contentText);
				}
				FileUtil.generateFile(FILE_PATH,FILE_NAME,contentText,ENCODE);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getMessage());
		}
	}

	private String removeDuplicateCardlink(String flpStringPart, String mlpStringPart) throws InfBatchException
	{
		if(!Util.empty(mlpStringPart))
		{
			ArrayList<String> acctSetupTextLines = new ArrayList<String>(Arrays.asList(flpStringPart.split("\\r?\\n")));
			HashMap<String,ArrayList<String>> acctSetupIdProductMap = new HashMap<String,ArrayList<String>>();
			for(int i = 0 ; i < acctSetupTextLines.size() ; i++)
			{
				if(acctSetupTextLines.get(i) != null 
				&& (!Util.empty(acctSetupTextLines.get(i).substring(706,719)) || !Util.empty(acctSetupTextLines.get(i).substring(1428,1441)))
				)
				{
					String cardLinkCustNo = acctSetupTextLines.get(i).substring(1741,1757);
					String product = cardLinkCustNo.substring(0, 2).trim();
					String idNo = acctSetupTextLines.get(i).substring(706,719).trim();
					
					String cardLinkCustNoSup = acctSetupTextLines.get(i).substring(1758,1774);
					String productSup = cardLinkCustNoSup.substring(0, 2).trim();
					String idNoSup = acctSetupTextLines.get(i).substring(1428,1441).trim();
					
					if(!Util.empty(idNo) && !Util.empty(product))
					{
						if(Util.empty(acctSetupIdProductMap.get(idNo)))
						{
							ArrayList<String> productList = new ArrayList<String>();
							productList.add(product);
							acctSetupIdProductMap.put(idNo, productList);
						}
						else
						{
							ArrayList<String> productList = acctSetupIdProductMap.get(idNo);
							if(!productList.contains(product))
							{productList.add(product);}
							acctSetupIdProductMap.put(idNo, productList);
						}
					}
					
					if(!Util.empty(idNoSup) && !Util.empty(productSup))
					{
						if(Util.empty(acctSetupIdProductMap.get(idNoSup)))
						{
							ArrayList<String> productList = new ArrayList<String>();
							productList.add(productSup);
							acctSetupIdProductMap.put(idNoSup, productList);
						}
						else
						{
							ArrayList<String> productList = acctSetupIdProductMap.get(idNoSup);
							if(!productList.contains(productSup))
							{productList.add(productSup);}
							acctSetupIdProductMap.put(idNoSup, productList);
						}
					}
				}
			}
		
			String[] mobileAcctSetupTextLines = mlpStringPart.split("\\r?\\n");
			for(int i = 0 ; i < mobileAcctSetupTextLines.length ; i++)
			{
				if(mobileAcctSetupTextLines[i] != null && 
				!Util.empty(mobileAcctSetupTextLines[i].substring(706,719)))	
				{
					try
					{
						String mlpIdNo = mobileAcctSetupTextLines[i].substring(706,719).trim();
						String mlpExistingCardlinkCust = mobileAcctSetupTextLines[i].substring(1738,1739);
						String mlpCardLinkCustNo = mobileAcctSetupTextLines[i].substring(1741,1757);
						
						if(Util.empty(acctSetupIdProductMap.get(mlpIdNo))
							|| (!Util.empty(acctSetupIdProductMap.get(mlpIdNo)) && !acctSetupIdProductMap.get(mlpIdNo).contains(mlpCardLinkCustNo.substring(0,2)))	
							|| (MConstant.FLAG.YES.equals(mlpExistingCardlinkCust))
						)
						{
							flpStringPart += "\n" + mobileAcctSetupTextLines[i];
						}
						
					}catch(Exception e)
					{
						logger.fatal("ERROR ", e);
					}
				}
			}
		}
		
		return flpStringPart;
	}
	
	public boolean isNewCardLinkCust(String cardLinkCustNo, String cardLinkRefNo, Connection conn) throws InfBatchException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isNewCardLinkCustFlag = true;
		try
		{
			StringBuilder SQL = new StringBuilder("");
			SQL.append(" SELECT CUS.NEW_CARDLINK_CUST_FLAG FROM ORIG_APPLICATION  A");
			SQL.append(" JOIN ORIG_LOAN L ON L.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID");
			SQL.append(" JOIN ORIG_CARD C ON C.LOAN_ID = L.LOAN_ID");
			SQL.append(" JOIN ORIG_CARDLINK_CUSTOMER CUS ON CUS.CARDLINK_CUST_ID = C.CARDLINK_CUST_ID");
			SQL.append(" WHERE CUS.CARDLINK_CUST_NO = ? AND A.CARDLINK_REF_NO = ? ");
			logger.debug("SQL >> "+SQL);
			logger.debug("cardLinkCustNo >> " + cardLinkCustNo);
			logger.debug("cardLinkRefNo >> " + cardLinkRefNo);
			ps = conn.prepareStatement(SQL.toString());
			ps.setString(1, cardLinkCustNo);
			ps.setString(2, cardLinkRefNo);
			rs = ps.executeQuery();		
			if(rs.next())
			{
				if(MConstant.FLAG.YES.equals(rs.getString("NEW_CARDLINK_CUST_FLAG")))
				{
					isNewCardLinkCustFlag = true;
				}
				else
				{
					isNewCardLinkCustFlag = false;
				}
			}
		}
		catch(Exception e)
		{
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
		finally
		{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return isNewCardLinkCustFlag;
	}
}
