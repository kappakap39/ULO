package com.eaf.orig.ulo.app.view.util.dih; 

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.ulo.app.view.util.dih.dao.DihDAO;
import com.eaf.orig.ulo.app.view.util.dih.dao.DihDAOImpl;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.app.view.util.dih.model.KbankBranchInfoDataM;
import com.eaf.orig.ulo.app.view.util.dih.model.KbankSaleInfoDataM;
import com.eaf.orig.ulo.model.app.DIHAccountResult;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.dih.AccountInformationDataM;
import com.eaf.orig.ulo.model.dih.CISAddressDataM;
import com.eaf.orig.ulo.model.dih.CISCustomerDataM;
import com.eaf.orig.ulo.model.dih.CISelcAddressDataM;
import com.eaf.orig.ulo.model.dih.CardLinkDataM;
import com.eaf.orig.ulo.model.dih.VcCardDataM;

public class DIHProxy{
	private static transient Logger logger = Logger.getLogger(DIHProxy.class); 
	String DEFAULT_VLD_TO_DT = SystemConstant.getConstant("DEFAULT_VLD_TO_DT"); 
	String DEFAULT_LAST_VRSN_F = SystemConstant.getConstant("DEFAULT_LAST_VRSN_F"); 
	String DEFAULT_IP_ST_CD = SystemConstant.getConstant("DEFAULT_IP_ST_CD"); 
	String DEFAULT_CONSND_F = SystemConstant.getConstant("DEFAULT_CONSND_F"); 	
	String DEFAULT_AR_X_IP_REL_TP_CD = SystemConstant.getConstant("DEFAULT_AR_X_IP_REL_TP_CD"); 
	String DEFAULT_AR_X_IP_REL_ST_CD = SystemConstant.getConstant("DEFAULT_AR_X_IP_REL_ST_CD"); 
	String DEFAULT_CIS_SRC_STM_CD = SystemConstant.getConstant("DEFAULT_CIS_SRC_STM_CD"); 	
	String DEFALUT_SUB_FIX_GUARANTEE = SystemConstant.getConstant("DEFALUT_SUB_FIX_GUARANTEE");
	public DIHQueryResult<String> getCIS_NUMBER(String CID_TYPE,String ID_NO){
		DihDAO dih = new DihDAOImpl();
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();	
		try{
			logger.info("CID_TYPE : "+CID_TYPE);
			logger.info("ID_NO : "+ID_NO);
			String cisNo =  dih.getCIS_NUMBER(CID_TYPE,ID_NO); 
			queryResult.setResult(cisNo);
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	public DIHQueryResult<String> getCIS_NUMBER(String CID_TYPE,String ID_NO,String TH_FIRST_NAME,String TH_LAST_NAME,String BIRTH_DATE ){		
		DihDAO dih = new DihDAOImpl();  
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();	
		try{
			String cisNo = dih.getCIS_NUMBER(CID_TYPE,ID_NO, TH_FIRST_NAME, TH_LAST_NAME, BIRTH_DATE);  
			queryResult.setResult(cisNo);
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	public DIHQueryResult<String> getCIS_NUMBER(String CID_TYPE,String TH_FIRST_NAME,String TH_LAST_NAME,String BIRTH_DATE ){		
		DihDAO dih = new DihDAOImpl();  
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();
		try{
			logger.debug("BIRTH_DATE : "+BIRTH_DATE);
			String cisNo = dih.getCIS_NUMBER(CID_TYPE, TH_FIRST_NAME, TH_LAST_NAME, BIRTH_DATE);  
			queryResult.setResult(cisNo);
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	public DIHQueryResult<String> foundMoreThanRows(String CID_TYPE,String ID_NO){
		DihDAO dih = new DihDAOImpl();  
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();	
		try{
			boolean foundMoreThanRows = dih.foundMoreThanRows(CID_TYPE,ID_NO);  
			queryResult.setResult((foundMoreThanRows)?"Y":"N");
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	public DIHQueryResult<String> foundMoreThanRows(String CID_TYPE,String TH_FIRST_NAME,String TH_LAST_NAME,String BIRTH_DATE ){
		DihDAO dih = new DihDAOImpl(); 
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();
		try{
			logger.debug("BIRTH_DATE : "+BIRTH_DATE);
			boolean foundMoreThanRows = dih.foundMoreThanRows(CID_TYPE, TH_FIRST_NAME, TH_LAST_NAME, BIRTH_DATE); 
			queryResult.setResult((foundMoreThanRows)?"Y":"N");
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	public DIHQueryResult<String> personalInfoMapper(String CIS_NUMBER,PersonalInfoDataM personalInfo,HashMap<String,CompareDataM> comparisonFields){
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();
		try{
			DihDAO dih = new DihDAOImpl(); 
			dih.personalInfoMapper(CIS_NUMBER,personalInfo,comparisonFields); 
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	public DIHQueryResult<String> personalDataMapper(String CIS_NUMBER,PersonalInfoDataM personalInfo
			,HashMap<String, CompareDataM> comparisonFields,boolean saveModel){
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();
		try{
			logger.info("CIS_NUMBER : "+CIS_NUMBER);
			logger.info("saveModel : "+saveModel);
			DihDAO dih = new DihDAOImpl(); 
			dih.personalDataMapper(CIS_NUMBER, personalInfo,comparisonFields,saveModel); 
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}	
	public DIHQueryResult<String> eAppPersonalDataMapper(String CIS_NUMBER,PersonalInfoDataM personalInfo
			,HashMap<String, CompareDataM> comparisonFields,boolean saveModel){
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();
		try{
			logger.info("CIS_NUMBER : "+CIS_NUMBER);
			logger.info("saveModel : "+saveModel);
			DihDAOImpl dih = new DihDAOImpl(); 
			dih.eAppPersonalDataMapper(CIS_NUMBER, personalInfo,comparisonFields,saveModel); 
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}	
	public DIHQueryResult<String> getAccountName(String ACCOUNT_NUMBER, String SUB){
		String accountName = "";
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();
		try{
			if(!Util.empty(ACCOUNT_NUMBER)){
				DihDAO dih = new DihDAOImpl(); 
				accountName = dih.getAccountName(ACCOUNT_NUMBER, SUB); 
			}
			queryResult.setResult(accountName);
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	public DIHQueryResult<String> getAccountNameFixGuarantee(String ACCOUNT_NUMBER, String SUB){
		String accountName = "";
		if(Util.empty(SUB)){
			SUB = DEFALUT_SUB_FIX_GUARANTEE;
		}
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();
		try{
			if(!Util.empty(ACCOUNT_NUMBER)){
				DihDAO dih = new DihDAOImpl(); 
				if(!DEFALUT_SUB_FIX_GUARANTEE.equals(SUB)){
					accountName = dih.getAccountNameFixGuarantee(ACCOUNT_NUMBER, SUB); 
				}else{
					accountName = dih.getAccountNamDevGuarantee(ACCOUNT_NUMBER);
				}
			}
			queryResult.setResult(accountName);
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	public DIHQueryResult<String> getAccountNameGuaranteeValidation(String ACCOUNT_NUMBER){
		String accountName = "";
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();
		try{
			if(!Util.empty(ACCOUNT_NUMBER)){
				DihDAO dih = new DihDAOImpl(); 
					accountName = dih.getAccountNameFixGuarantee(ACCOUNT_NUMBER);
					if(Util.empty(accountName)){
						accountName = dih.getAccountNamDevGuarantee(ACCOUNT_NUMBER);
					}
					
			}
			queryResult.setResult(accountName);
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	public DIHQueryResult<List<VcCardDataM>> searchCardInfoByCisNo(String CIS_NO){
		DIHQueryResult<List<VcCardDataM>> queryResult = new DIHQueryResult<>();
		try{
			if(!Util.empty(CIS_NO)){
				DihDAO dih = new DihDAOImpl(); 
				List<VcCardDataM> results = dih.searchCardInfoByCisNo(CIS_NO); 
				queryResult.setResult(results);
			}
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}			
	public DIHQueryResult<String> getCisNoByAccountNo(String ACCT_NUMBER){
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();
		try{
			if(!Util.empty(ACCT_NUMBER)){
				DihDAO dih = new DihDAOImpl(); 
				String data = dih.getCisInfoByAcctNumber(ACCT_NUMBER, "IP_ID"); 
				queryResult.setResult(data);
			}else{
				queryResult.setResult("");
			}
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}	
	public DIHQueryResult<String> getCisInfoByAccountNo(String ACCT_NUMBER, String field){
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();
		try{
			if(!Util.empty(ACCT_NUMBER)){
				DihDAO dih = new DihDAOImpl(); 
				String data = dih.getCisInfoByAcctNumber(ACCT_NUMBER,field); 
				queryResult.setResult(data);
			}else{
				queryResult.setResult("");
			}
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;		
	}	
	public DIHQueryResult<String> getAccountInfo(String ACCT_NUMBER, String field){
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();
		try{
			DihDAO dih = new DihDAOImpl(); 
			if(!Util.empty(ACCT_NUMBER)){
				String CIS_SRC_STM_CD = dih.getCisInfoByAcctNumber(ACCT_NUMBER,"CIS_SRC_STM_CD"); 
				String acctTableName = AccountInformationDataM.getAcctTableName(CIS_SRC_STM_CD); 
				if(!Util.empty(acctTableName)) {
					String data = dih.getAccountInfo(ACCT_NUMBER,acctTableName,field); 
					queryResult.setResult(data);
				} 
			}
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	public DIHQueryResult<String> getAccountName(String ACCT_NUMBER){
		return getAccountInfo(ACCT_NUMBER,"AR_NM_TH");
	}
	
	public DIHQueryResult<String> getCisCompanyInfo(String COMPANY_TITLE,String COMPANY_NAME, PersonalInfoDataM personalInfo,HashMap<String, CompareDataM> comparisonFields){
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();
		try {
			if(Util.empty(COMPANY_TITLE) && Util.empty(COMPANY_NAME)){
				queryResult.setStatusCode(ResponseData.SUCCESS);
				return null; 
			}
			DihDAO dih = new DihDAOImpl(); 		
			queryResult = dih.getCisCompanyInfo(COMPANY_TITLE, COMPANY_NAME, personalInfo,comparisonFields); 
		} catch (Exception e) {
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	
	public DIHQueryResult<BigDecimal> getShareholderPercent(PersonalInfoDataM personalInfo){
		DIHQueryResult<BigDecimal> queryResult = new DIHQueryResult<>();
		try{
			DihDAO dih = new DihDAOImpl();
			queryResult.setResult(dih.getShareholderPercent(personalInfo));
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	
	public static DIHQueryResult<String> getKbankBranchData(String branchCode,String fieldDataName){
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();
		try{
			DihDAO dih = new DihDAOImpl();
			queryResult.setResult(dih.getKbankBranchData(branchCode,fieldDataName));
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	public static DIHQueryResult<KbankBranchInfoDataM> getKbankBranchData(String branchCode){
		DIHQueryResult<KbankBranchInfoDataM> queryResult = new DIHQueryResult<>();
		try{
			DihDAO dih = new DihDAOImpl();
			queryResult.setResult(dih.getKbankBranchData(branchCode));
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	public static DIHQueryResult<KbankBranchInfoDataM> getBranchInfoByRC_CD(String rcCode){
		DIHQueryResult<KbankBranchInfoDataM> queryResult = new DIHQueryResult<>();
		try{
			DihDAO dih = new DihDAOImpl();
			queryResult.setResult(dih.getBranchInfoByRC_CD(rcCode));
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	public static DIHQueryResult<KbankSaleInfoDataM> getKbankSaleInfo(String saleId) throws Exception{
		DIHQueryResult<KbankSaleInfoDataM> queryResult = new DIHQueryResult<>();
		try{
			DihDAO dih = new DihDAOImpl();
			queryResult.setResult(dih.getKbankSaleInfo(saleId));
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
			throw new Exception(e.getMessage()); 
		}
		return queryResult;
	}
	public DIHQueryResult<CardLinkDataM> getCardLinkInfoENCPT(String CARD_NUMBER){
		DIHQueryResult<CardLinkDataM> queryResult = new DIHQueryResult<>();
		try{
			if(!Util.empty(CARD_NUMBER)){
				DihDAO dih = new DihDAOImpl();
				queryResult.setResult(dih.getCardLinkInfoENCPT(CARD_NUMBER));
			}
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	public DIHQueryResult<String> existUserNo(String userNo){
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();
		try{
			DihDAO dih = new DihDAOImpl(); 
			boolean exist = dih.existUserNo(userNo); 
			queryResult.setResult((exist)?"Y":"N");
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}	
	
	public DIHQueryResult<DIHAccountResult> getAccountData(String ACCOUNT_NO,String TYPE_LIMIT){
		DIHQueryResult<DIHAccountResult> queryResult = new DIHQueryResult<>();
		try{
			if(!Util.empty(ACCOUNT_NO)){
				DihDAO dih = new DihDAOImpl();
				queryResult.setResult(dih.getAccountData(ACCOUNT_NO,TYPE_LIMIT));
			}
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	
	public  DIHQueryResult<CISCustomerDataM> selectCISCustomer(String cidType, String idNo){
		DIHQueryResult<CISCustomerDataM> queryResult = new DIHQueryResult<>();
		try{
			if(!Util.empty(cidType) && !Util.empty(idNo)){
				DihDAO dih = new DihDAOImpl(); 
				CISCustomerDataM cisCustomerDataM = dih.selectVC_IPData(cidType, idNo);
				if(!Util.empty(cisCustomerDataM)&& !Util.empty(cisCustomerDataM.getIpId())){
					ArrayList<CISelcAddressDataM> elcAddresses = dih.selectVC_IP_X_ELC_ADR_RELData(cisCustomerDataM.getIpId());
					if(!Util.empty(elcAddresses)){
						cisCustomerDataM.setElcAddresses(elcAddresses);
					}
					
					ArrayList<CISAddressDataM> cisAddresses = dih.selectVC_IP_X_ADR_RELData(cisCustomerDataM.getIpId());
					if(!Util.empty(cisAddresses)){
						cisCustomerDataM.setAddresses(cisAddresses);
					}
				}
				queryResult.setResult(cisCustomerDataM);
			}

			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	
	public DIHQueryResult<String> getBussinessDesc(String busCode){
		DIHQueryResult<String> queryResult = new DIHQueryResult<>();
		try{
			DihDAO dih = new DihDAOImpl(); 
			String busDesc = dih.getBussinessDesc(busCode);
			queryResult.setResult((busDesc));
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	
	public  DIHQueryResult<CISCustomerDataM> selectCISCustomer(String cisNo){
		DIHQueryResult<CISCustomerDataM> queryResult = new DIHQueryResult<>();
		try{
			if(!Util.empty(cisNo)){
				DihDAO dih = new DihDAOImpl(); 
				CISCustomerDataM cisCustomerDataM = dih.selectVC_IPData(cisNo);
				if(!Util.empty(cisCustomerDataM)&& !Util.empty(cisCustomerDataM.getIpId())){
					ArrayList<CISelcAddressDataM> elcAddresses = dih.selectVC_IP_X_ELC_ADR_RELData(cisCustomerDataM.getIpId());
					if(!Util.empty(elcAddresses)){
						cisCustomerDataM.setElcAddresses(elcAddresses);
					}
					
					ArrayList<CISAddressDataM> cisAddresses = dih.selectVC_IP_X_ADR_RELData(cisCustomerDataM.getIpId());
					if(!Util.empty(cisAddresses)){
						cisCustomerDataM.setAddresses(cisAddresses);
					}
				}
				queryResult.setResult(cisCustomerDataM);
			}

			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
	public DIHQueryResult<ArrayList<CISCustomerDataM>> foundMoreThanRows(String CID_TYPE,String ID_NO, String EN_BIRTH_DATE){
		String CIDTYPE_IDCARD = SystemConstant.getConstant("CIDTYPE_IDCARD");
		DihDAO dih = new DihDAOImpl();
		DIHQueryResult<ArrayList<CISCustomerDataM>> queryResult = new DIHQueryResult<ArrayList<CISCustomerDataM>>();
		ArrayList<CISCustomerDataM> cisResult = new ArrayList<CISCustomerDataM>();
		try{
			ArrayList<CISCustomerDataM> cisDatas = dih.selectVC_IPDataByIDNo(CID_TYPE,ID_NO);
			if(!Util.empty(cisDatas)){
				for(CISCustomerDataM cisData : cisDatas){
					if(CIDTYPE_IDCARD.equals(CID_TYPE)){
						cisResult.add(cisData);
					}else{
//						String brthEstbDate = cisData.getBrthEstbDate();
//						brthEstbDate = brthEstbDate.substring(0, 10);
//						EN_BIRTH_DATE = EN_BIRTH_DATE.substring(0, 10);
//						logger.debug("brthEstbDate: "+brthEstbDate+" EN_BIRTH_DATE: "+EN_BIRTH_DATE);
//						if(EN_BIRTH_DATE.compareTo(brthEstbDate) == 0){
//							cisResult.add(cisData);
//						}
						if(!Util.empty(cisData.getBrthEstbDate())&&!Util.empty(EN_BIRTH_DATE)&&EN_BIRTH_DATE.equals(cisData.getBrthEstbDate())){
							cisResult.add(cisData);
						}
					}
				}
			}else{
				if(!CIDTYPE_IDCARD.equals(CID_TYPE)){
					queryResult.setResult(cisResult);
					queryResult.setStatusCode(ResponseData.WARNING);
					return queryResult;
				}
			}
			logger.debug("cisResult > >"+cisResult.size());
			queryResult.setResult(cisResult);
			queryResult.setStatusCode(ResponseData.SUCCESS);
		}catch(Exception e){
			logger.debug("ERROR",e);
			queryResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			queryResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		return queryResult;
	}
}
