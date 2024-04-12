package com.eaf.orig.ulo.app.view.util.dih.dao; 

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.app.view.util.dih.model.KbankBranchInfoDataM;
import com.eaf.orig.ulo.app.view.util.dih.model.KbankSaleInfoDataM;
import com.eaf.orig.ulo.model.app.DIHAccountResult;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.dih.CISAddressDataM;
import com.eaf.orig.ulo.model.dih.CISCustomerDataM;
import com.eaf.orig.ulo.model.dih.CISelcAddressDataM;
import com.eaf.orig.ulo.model.dih.CardLinkDataM;
import com.eaf.orig.ulo.model.dih.VcCardDataM;
//import com.eaf.orig.ulo.app.view.util.dih.model.QueryDIHResponse; 

public interface DihDAO {
	public String getCIS_NUMBER(String CID_TYPE,String ID_NO)throws ApplicationException; 
	public String getCIS_NUMBER(String CID_TYPE,String ID_NO,String TH_FIRST_NAME,String TH_LAST_NAME,String BIRTH_DATE)throws ApplicationException; 
	public String getCIS_NUMBER(String CID_TYPE,String TH_FIRST_NAME,String TH_LAST_NAME,String BIRTH_DATE)throws ApplicationException; 
	public void personalDataMapper(String CIS_NUMBER,PersonalInfoDataM personalInfo,HashMap<String, CompareDataM> comparisonFields,boolean saveModel)throws ApplicationException; 
	public void personalInfoMapper(String CIS_NUMBER,PersonalInfoDataM personalInfo,HashMap<String,CompareDataM> comparisonFields)throws ApplicationException; 
	public String getAccountName(String ACCOUNT_NUMBER, String SUB) throws ApplicationException; 
	public String getAccountNameFixGuarantee(String ACCOUNT_NUMBER, String SUB) throws ApplicationException;
	public String getAccountNameFixGuarantee(String ACCOUNT_NUMBER) throws ApplicationException;
	public String getAccountNamDevGuarantee(String ACCOUNT_NUMBER) throws ApplicationException;
	public String getCisInfoByAcctNumber(String ACCT_NUMBER, String field) throws ApplicationException; 
	public String getAccountInfo(String ACCT_NUMBER, String tableName, String field) throws ApplicationException; 
	public boolean isKbankEmployee(String idNo) throws ApplicationException; 
	public List<VcCardDataM> searchCardInfoByCisNo(String CIS_NO)throws ApplicationException; 
	public DIHQueryResult<String> getCisCompanyInfo(String COMPANY_TITLE,String COMPANY_NAME, PersonalInfoDataM personalInfo,HashMap<String, CompareDataM> comparisonFields)throws ApplicationException; 
	public BigDecimal getShareholderPercent(PersonalInfoDataM personalInfo)throws ApplicationException; 
	public boolean foundMoreThanRows(String CID_TYPE,String ID_NO)throws ApplicationException; 
	public boolean foundMoreThanRows(String CID_TYPE,String TH_FIRST_NAME,String TH_LAST_NAME,String BIRTH_DATE)throws ApplicationException; 
	public CardLinkDataM getCardLinkInfoENCPT(String CARD_NUMBER)throws ApplicationException; 
	public String getKbankBranchData(String branchCode,String fieldDataName)throws ApplicationException; 
	public KbankBranchInfoDataM getKbankBranchData(String branchCode)throws ApplicationException; 
	public KbankSaleInfoDataM getKbankSaleInfo(String saleId)throws ApplicationException; 
	public boolean existUserNo(String userNO) throws ApplicationException; 
	public DIHAccountResult getAccountData(String ACCOUNT_NO,String TYPE_LIMIT)throws ApplicationException;
	public DIHAccountResult getAccountTypeCurrent(String ACCOUNT_NO)throws ApplicationException;
	public DIHAccountResult getAccountTypeLoan(String ACCOUNT_NO)throws ApplicationException;
	public DIHAccountResult getAccountTypeTCBLoan(String ACCOUNT_NO)throws ApplicationException;
	public DIHAccountResult getAccountTypeSAFE(String ACCOUNT_NO)throws ApplicationException;
	public String getKBankBranchNo(String rcCode)throws ApplicationException; 
	public CISCustomerDataM selectVC_IPData (String cidType, String idNo) throws Exception;
	public ArrayList<CISelcAddressDataM> selectVC_IP_X_ELC_ADR_RELData (String ipId) throws Exception;
	public ArrayList<CISAddressDataM> selectVC_IP_X_ADR_RELData (String ipId) throws Exception;
	public String getAccountSource (String ACCOUNT_NO) throws Exception;
	public String getBussinessDesc (String busCode) throws Exception;
	public CISCustomerDataM selectVC_IPData (String cisNo) throws Exception;
	public ArrayList<CISCustomerDataM> selectVC_IPDataByIDNo(String CID_TYPE, String ID_NO) throws Exception;
	public KbankBranchInfoDataM getBranchInfoByRC_CD(String branchCode)throws ApplicationException;
}
