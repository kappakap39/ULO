package com.eaf.orig.ulo.app.view.util.dih.dao; 

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.app.view.util.dih.model.KbankBranchInfoDataM;
import com.eaf.orig.ulo.app.view.util.dih.model.KbankSaleInfoDataM;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.DIHAccountResult;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.dih.CISAddressDataM;
import com.eaf.orig.ulo.model.dih.CISCustomerDataM;
import com.eaf.orig.ulo.model.dih.CISelcAddressDataM;
import com.eaf.orig.ulo.model.dih.CardLinkDataM;
import com.eaf.orig.ulo.model.dih.VcCardDataM;

public class DihDAOImpl extends OrigObjectDAO implements DihDAO{
	private static transient Logger logger = Logger.getLogger(DihDAOImpl.class); 
	String CIDTYPE_IDCARD = SystemConstant.getConstant("CIDTYPE_IDCARD"); 
	String CIDTYPE_PASSPORT = SystemConstant.getConstant("CIDTYPE_PASSPORT"); 
	String CIDTYPE_MIGRANT = SystemConstant.getConstant("CIDTYPE_MIGRANT"); 
	String DIH_CIDTYPE_IDCARD = SystemConstant.getConstant("DIH_CIDTYPE_IDCARD"); 
	String DIH_CIDTYPE_PASSPORT = SystemConstant.getConstant("DIH_CIDTYPE_PASSPORT"); 
	String DIH_CIDTYPE_MIGRANT = SystemConstant.getConstant("DIH_CIDTYPE_MIGRANT"); 
	String DEFAULT_VLD_TO_DT = SystemConstant.getConstant("DEFAULT_VLD_TO_DT"); 
	String DEFAULT_LAST_VRSN_F = SystemConstant.getConstant("DEFAULT_LAST_VRSN_F"); 
	String DEFAULT_IP_ST_CD = SystemConstant.getConstant("DEFAULT_IP_ST_CD"); 
//	String DEFAULT_CONSND_F = SystemConstant.getConstant("DEFAULT_CONSND_F"); 	
	String DEFAULT_AR_X_IP_REL_TP_CD = SystemConstant.getConstant("DEFAULT_AR_X_IP_REL_TP_CD"); 
	String DEFAULT_AR_X_IP_REL_ST_CD = SystemConstant.getConstant("DEFAULT_AR_X_IP_REL_ST_CD"); 
	String DEFAULT_CIS_SRC_STM_CD = SystemConstant.getConstant("DEFAULT_CIS_SRC_STM_CD"); 
	String COMPANY_IP_ST_CD=SystemConstant.getConstant("COMPANY_IP_ST_CD"); 
	String COMPANY_IP_TP_CD=SystemConstant.getConstant("COMPANY_IP_TP_CD"); 
	String FIELD_ID_NATIONALITY = SystemConstant.getConstant("FIELD_ID_NATIONALITY"); 
	String FIELD_ID_GENDER = SystemConstant.getConstant("FIELD_ID_GENDER"); 
	String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE"); 
	String FIELD_ID_EN_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_EN_TITLE_CODE"); 	
	String FIELD_ID_ADDRESS_TYPE = SystemConstant.getConstant("FIELD_ID_ADDRESS_TYPE"); 
	String FIELD_ID_ADDRESS_STYLE = SystemConstant.getConstant("FIELD_ID_ADDRESS_STYLE"); 
	String FIELD_ID_COMPANY_TITLE = SystemConstant.getConstant("FIELD_ID_COMPANY_TITLE"); 
	String[] DISPLAY_ADDRESS_TYPE = SystemConstant.getArrayConstant("DISPLAY_ADDRESS_TYPE"); 
	String FIELD_ID_OCCUPATION = SystemConstant.getConstant("FIELD_ID_OCCUPATION"); 
	String FIELD_ID_PROFESSION = SystemConstant.getConstant("FIELD_ID_PROFESSION"); 	
	String FIELD_ID_MARRIED = SystemConstant.getConstant("FIELD_ID_MARRIED"); 
	String FIELD_ID_DEGREE = SystemConstant.getConstant("FIELD_ID_DEGREE"); 
	String FIELD_ID_APPLICATION_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_APPLICATION_CARD_TYPE"); 
	String BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER"); 
	String SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY"); 
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT"); 
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK"); 
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT"); 
	String TITLE_OTHER = SystemConstant.getConstant("TITLE_OTHER"); 
	String PROFESSION_OTHER = SystemConstant.getConstant("PROFESSION_OTHER");
	String PROFESSION_OTHER_CIS = SystemConstant.getConstant("PROFESSION_OTHER_CIS");
	String FIELD_ID_CID_TYPE = SystemConstant.getConstant("FIELD_ID_CID_TYPE"); 
	String DEFUALT_EFF_ST_CD = SystemConstant.getConstant("DEFUALT_EFF_ST_CD");
	String ACCOUNT_TYPE_PROFILE  = SystemConstant.getConstant("ACCOUNT_TYPE_PROFILE");
	String ACCOUNT_TYPE_TCB  = SystemConstant.getConstant("ACCOUNT_TYPE_TCB");
	String ACCOUNT_TYPE_SAFE  = SystemConstant.getConstant("ACCOUNT_TYPE_SAFE");
	String ADRSTS_CITY_CD_TH = SystemConstant.getConstant("ADRSTS_CITY_CD_TH");
	ArrayList<String> CORPARATE_CARD = SystemConstant.getArrayListConstant("CORPARATE_CARD");
	String CC_CST_NO_SUP = SystemConstant.getConstant("CC_CST_NO_SUP");
	String DEFAULT_ADR_ST_CD = SystemConstant.getConstant("DEFAULT_ADR_ST_CD");
	String DEFAULT_IP_X_ELC_ADR_REL_ST_CD = SystemConstant.getConstant("DEFAULT_IP_X_ELC_ADR_REL_ST_CD");
	String DEFALUT_SUB_FIX_GUARANTEE = SystemConstant.getConstant("DEFALUT_SUB_FIX_GUARANTEE");
	ArrayList<String> PROFESSION_RISK_GROUP = SystemConstant.getArrayListConstant("PROFESSION_RISK_GROUP");
	String ORIG_ID_CC = SystemConstant.getConstant("ORIG_ID_CC");
	String ORIG_ID_KEC = SystemConstant.getConstant("ORIG_ID_KEC");
	@Override
	public String getCIS_NUMBER(String CID_TYPE, String ID_NO ) throws ApplicationException {
		String CIS_NUMBER = ""; 
		String FIELD_ID_CID_TYPE = SystemConstant.getConstant("FIELD_ID_CID_TYPE"); 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try{
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
				SQL.append(" SELECT IP_ID FROM IP_SHR.VC_IP "); 
				SQL.append(" WHERE VLD_TO_DT = ? "); //--Customer not delete 
				SQL.append(" AND LAST_VRSN_F = ? "); // --Customer Current Record 
				SQL.append(" AND IP_ST_CD = ? "); //--Customer Active 
//				SQL.append(" AND CONSND_F = ? "); 
				SQL.append(" AND DOC_ITM_CD = ? "); 
				SQL.append(" AND IDENT_NO = ? "); 
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,DEFAULT_VLD_TO_DT); 
			ps.setString(index++,DEFAULT_LAST_VRSN_F); 
			ps.setString(index++,DEFAULT_IP_ST_CD); 
//			ps.setString(index++,DEFAULT_CONSND_F); 
			CID_TYPE = ListBoxControl.getName(FIELD_ID_CID_TYPE,"CHOICE_NO",CID_TYPE,"MAPPING4"); 
			logger.info("MAPPING CID_TYPE >> "+CID_TYPE); 
			ps.setString(index++,CID_TYPE); 
			ps.setString(index++,ID_NO); 	
			rs = ps.executeQuery(); 
			if (rs.next()) {
				CIS_NUMBER	= FormatUtil.trim(rs.getString("IP_ID")); 
				logger.info("CIS_NUMBER : "+CIS_NUMBER); 	
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		}finally{
			try{
				closeConnection(conn, rs, ps); 
			}catch(Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return CIS_NUMBER; 
	}
	@Override
	public boolean foundMoreThanRows(String CID_TYPE, String ID_NO ) throws ApplicationException {
		int  CNT = 0; 
		String FIELD_ID_CID_TYPE = SystemConstant.getConstant("FIELD_ID_CID_TYPE"); 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			SQL.append(" SELECT COUNT(1) AS CNT FROM IP_SHR.VC_IP "); 
			SQL.append(" WHERE VLD_TO_DT = ? "); //--Customer not delete 
			SQL.append(" AND LAST_VRSN_F = ? "); // --Customer Current Record 
			SQL.append(" AND IP_ST_CD = ? "); //--Customer Active 
//			SQL.append(" AND CONSND_F = ? "); 
			SQL.append(" AND DOC_ITM_CD = ? "); 
			SQL.append(" AND IDENT_NO = ? "); 
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,DEFAULT_VLD_TO_DT); 
			ps.setString(index++,DEFAULT_LAST_VRSN_F); 
			ps.setString(index++,DEFAULT_IP_ST_CD); 
//			ps.setString(index++,DEFAULT_CONSND_F); 
			CID_TYPE = ListBoxControl.getName(FIELD_ID_CID_TYPE,"CHOICE_NO",CID_TYPE,"MAPPING4"); 
			logger.debug("MAPPING CID_TYPE >> "+CID_TYPE); 
			ps.setString(index++,CID_TYPE); 
			ps.setString(index++,ID_NO); 	
			rs = ps.executeQuery(); 
			if (rs.next()) {
				CNT	= rs.getInt("CNT"); 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		logger.debug("CNT : "+CNT); 	
		return CNT>1; 
	}

	@Override
	public String getCIS_NUMBER(String CID_TYPE, String ID_NO,String TH_FIRST_NAME, String TH_LAST_NAME, String BIRTH_DATE) throws ApplicationException {
		logger.debug("getCIS_NUMBER BY TYPE 02,03....."); 
		String CIS_NUMBER = ""; 
		String FIELD_ID_CID_TYPE = SystemConstant.getConstant("FIELD_ID_CID_TYPE"); 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
				SQL.append(" SELECT IP_ID FROM IP_SHR.VC_IP "); 
				SQL.append(" WHERE VLD_TO_DT = ? "); //--Customer not delete 
				SQL.append(" AND LAST_VRSN_F = ? "); // --Customer Current Record 
				SQL.append(" AND IP_ST_CD = ? "); //--Customer Active 
//				SQL.append(" AND CONSND_F = ? "); 
				SQL.append(" AND DOC_ITM_CD = ? "); 
				SQL.append(" AND IDENT_NO = ? "); 
				SQL.append(" AND TH_FRST_NM = ? "); 
				SQL.append(" AND TH_SURNM = ? "); 
				SQL.append(" AND BRTH_ESTB_DT = ? "); 
			logger.debug("SQL >> "+SQL); 
			CID_TYPE = ListBoxControl.getName(FIELD_ID_CID_TYPE,"CHOICE_NO",CID_TYPE,"MAPPING4"); 
			logger.debug("MAPPING CID_TYPE >> "+CID_TYPE); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,DEFAULT_VLD_TO_DT); 
			ps.setString(index++,DEFAULT_LAST_VRSN_F); 
			ps.setString(index++,DEFAULT_IP_ST_CD); 
//			ps.setString(index++,DEFAULT_CONSND_F); 
			ps.setString(index++,CID_TYPE); 
			ps.setString(index++,ID_NO); 
			ps.setString(index++,TH_FIRST_NAME); 
			ps.setString(index++,TH_LAST_NAME); 
			ps.setString(index++,BIRTH_DATE); 		
			rs = ps.executeQuery(); 
			if (rs.next()) {
				CIS_NUMBER	= FormatUtil.trim(rs.getString("IP_ID")); 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		logger.debug("CIS_NUMBER : "+CIS_NUMBER); 	
		return CIS_NUMBER; 
	}
	@Override
	public String getCIS_NUMBER(String CID_TYPE,String TH_FIRST_NAME, String TH_LAST_NAME, String BIRTH_DATE) throws ApplicationException {
		logger.debug("getCIS_NUMBER BY TYPE 02,03....."); 
		String CIS_NUMBER = ""; 
		String FIELD_ID_CID_TYPE = SystemConstant.getConstant("FIELD_ID_CID_TYPE"); 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			SQL.append(" SELECT IP_ID FROM IP_SHR.VC_IP "); 
			SQL.append(" WHERE VLD_TO_DT = ? "); //--Customer not delete 
			SQL.append(" AND LAST_VRSN_F = ? "); // --Customer Current Record 
			SQL.append(" AND IP_ST_CD = ? "); //--Customer Active 
//				SQL.append(" AND CONSND_F = ? "); 
			SQL.append(" AND DOC_ITM_CD = ? "); 
			SQL.append(" AND TH_FRST_NM = ? "); 
			SQL.append(" AND TH_SURNM = ? "); 
			SQL.append(" AND BRTH_ESTB_DT = ? "); 
			logger.debug("SQL >> "+SQL); 
			CID_TYPE = ListBoxControl.getName(FIELD_ID_CID_TYPE,"CHOICE_NO",CID_TYPE,"MAPPING4"); 
			logger.debug("MAPPING CID_TYPE >> "+CID_TYPE); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,DEFAULT_VLD_TO_DT); 
			ps.setString(index++,DEFAULT_LAST_VRSN_F); 
			ps.setString(index++,DEFAULT_IP_ST_CD); 
//			ps.setString(index++,DEFAULT_CONSND_F); 
			ps.setString(index++,CID_TYPE); 
			ps.setString(index++,TH_FIRST_NAME); 
			ps.setString(index++,TH_LAST_NAME); 
			ps.setString(index++,BIRTH_DATE); 		
			rs = ps.executeQuery(); 
			if (rs.next()) {
				CIS_NUMBER	= FormatUtil.trim(rs.getString("IP_ID")); 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		logger.debug("CIS_NUMBER : "+CIS_NUMBER); 	
		return CIS_NUMBER; 
	}
	@Override
	public boolean foundMoreThanRows(String CID_TYPE,String TH_FIRST_NAME, String TH_LAST_NAME, String BIRTH_DATE) throws ApplicationException {
		logger.debug("getCIS_NUMBER BY TYPE 02,03....."); 
		int CNT = 0; 
		String FIELD_ID_CID_TYPE = SystemConstant.getConstant("FIELD_ID_CID_TYPE"); 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			SQL.append(" SELECT COUNT(1) AS CNT FROM IP_SHR.VC_IP "); 
			SQL.append(" WHERE VLD_TO_DT = ? "); //--Customer not delete 
			SQL.append(" AND LAST_VRSN_F = ? "); // --Customer Current Record 
			SQL.append(" AND IP_ST_CD = ? "); //--Customer Active 
//			SQL.append(" AND CONSND_F = ? "); 
			SQL.append(" AND DOC_ITM_CD = ? "); 
			SQL.append(" AND TH_FRST_NM = ? "); 
			SQL.append(" AND TH_SURNM = ? "); 
			SQL.append(" AND BRTH_ESTB_DT = ? "); 
			logger.debug("SQL >> "+SQL); 
			CID_TYPE = ListBoxControl.getName(FIELD_ID_CID_TYPE,"CHOICE_NO",CID_TYPE,"MAPPING4"); 
			logger.debug("MAPPING CID_TYPE >> "+CID_TYPE); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,DEFAULT_VLD_TO_DT); 
			ps.setString(index++,DEFAULT_LAST_VRSN_F); 
			ps.setString(index++,DEFAULT_IP_ST_CD); 
//			ps.setString(index++,DEFAULT_CONSND_F); 
			ps.setString(index++,CID_TYPE); 
			ps.setString(index++,TH_FIRST_NAME); 
			ps.setString(index++,TH_LAST_NAME); 
			ps.setString(index++,BIRTH_DATE); 		
			rs = ps.executeQuery(); 
			if (rs.next()) {
				CNT	= rs.getInt("CNT"); 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		logger.debug("CNT : "+CNT); 	
		return CNT>1; 
	}

	@Override
	public void personalDataMapper(String cisNo,PersonalInfoDataM personalInfo,HashMap<String,CompareDataM> comparisonFields,boolean saveModel) throws ApplicationException{
		logger.debug("cisNo >> "+cisNo); 	
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		if(null == comparisonFields){
			comparisonFields = new HashMap<String, CompareDataM>(); 
		}
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
				SQL.append(" SELECT IP_ID, DOC_ITM_CD, TH_TTL, TH_FRST_NM, TH_MDL_NM, ");  
				SQL.append(" TH_SURNM, EN_TTL, EN_FRST_NM, EN_MDL_NM, EN_SURNM, "); 
				SQL.append(" BRTH_ESTB_DT, MAR_ST_CD, CTF_TP_CD, OCP_CD, PROF_CD, "); 
				SQL.append(" GND_CD, NAT_CD, DOC_ITM_CD, IDENT_NO, PRIM_SEG_CD, "); 
				SQL.append(" PRIM_SUB_SEG_CD, DUAL_SEG_CD, DUAL_SUB_SEG_CD, PROF_DTL,IP_LGL_STC_TP_CD "); 				
				SQL.append(" FROM IP_SHR.VC_IP "); 
//				SQL.append(" LEFT JOIN IP_SHR.VC_IP_X_ELC_ADR_REL B ON A.IP_ID = B.IP_ID "); 
				SQL.append(" WHERE VLD_TO_DT = ? "); //--Customer not delete 
				SQL.append(" AND LAST_VRSN_F = ? "); // --Customer Current Record 
				SQL.append(" AND IP_ST_CD = ? "); //--Customer Active 
//				SQL.append(" AND CONSND_F = ? "); 
				SQL.append(" AND IP_ID = ? "); 		
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,DEFAULT_VLD_TO_DT); 
			ps.setString(index++,DEFAULT_LAST_VRSN_F); 
			ps.setString(index++,DEFAULT_IP_ST_CD); 
//			ps.setString(index++,DEFAULT_CONSND_F); 	
			ps.setString(index++,cisNo); 	
			rs = ps.executeQuery(); 
			if (rs.next()) {
				personalMapper(rs,cisNo,personalInfo,comparisonFields,saveModel); 				
				addressDataMapper(cisNo,personalInfo,comparisonFields,saveModel); 								
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
	}
	
	private void personalMapper(ResultSet rs,String CIS_NUMBER,PersonalInfoDataM personalInfo,HashMap<String,CompareDataM> comparisonFields,boolean saveModel) throws SQLException{
		String CIS_NO = FormatUtil.trim(rs.getString("IP_ID")); 
		String CID_TYPE = FormatUtil.trim(rs.getString("DOC_ITM_CD")); 
		/*String fieldId = null; 
		if(DIH_CIDTYPE_IDCARD.equals(CID_TYPE) || DIH_CIDTYPE_MIGRANT.equals(CID_TYPE)){
			fieldId = FIELD_ID_TH_TITLE_CODE; 
		}else{
			fieldId = FIELD_ID_EN_TITLE_CODE; 					
		}*/
		String th_titleCode = FormatUtil.trim(rs.getString("TH_TTL")); 
		String TH_TITLE_CODE = ListBoxControl.getName(FIELD_ID_TH_TITLE_CODE,"MAPPING4",th_titleCode,"CHOICE_NO"); 
		String TH_TITLE_DESC = th_titleCode; 
		if(Util.empty(TH_TITLE_CODE) && !Util.empty(TH_TITLE_DESC)){
			TH_TITLE_CODE = TITLE_OTHER; 
		}else{
			TH_TITLE_DESC = ListBoxControl.getName(FIELD_ID_TH_TITLE_CODE,TH_TITLE_CODE); 	
		}
		
		String en_titleCODE = FormatUtil.trim(rs.getString("EN_TTL"));
		String EN_TITLE_CODE = ListBoxControl.getName(FIELD_ID_EN_TITLE_CODE,"MAPPING4",en_titleCODE,"CHOICE_NO"); 
	 	String EN_TITLE_DESC = en_titleCODE;
	 	if(Util.empty(EN_TITLE_CODE) && !Util.empty(EN_TITLE_DESC)){
	 		EN_TITLE_CODE = TITLE_OTHER;
		}else{
			EN_TITLE_DESC = ListBoxControl.getName(FIELD_ID_EN_TITLE_CODE,EN_TITLE_CODE);
		}
		String TH_FIRST_NAME = FormatUtil.trim(rs.getString("TH_FRST_NM")); 
		String TH_MID_NAME = FormatUtil.trim(rs.getString("TH_MDL_NM")); 
		String TH_LAST_NAME = FormatUtil.trim(rs.getString("TH_SURNM"));  
		String EN_FIRST_NAME = FormatUtil.trim(rs.getString("EN_FRST_NM")); 
		String EN_MID_NAME = FormatUtil.trim(rs.getString("EN_MDL_NM")); 
		String EN_LAST_NAME = FormatUtil.trim(rs.getString("EN_SURNM")); 
		String PROFESSION_CODE = FormatUtil.trim(rs.getString("PROF_CD")); 
		String BRTH_ESTB_DT = FormatUtil.trim(rs.getString("BRTH_ESTB_DT")); 
		logger.debug("BRTH_ESTB_DT : "+BRTH_ESTB_DT); 
		
//		Date tt = FormatUtil.toDate(Date_BRTH_ESTB_DT,FormatUtil.EN,"YYYY-MM-DD"); 
//		String ff =  FormatUtil.display(tt, FormatUtil.TH); 
		Date BIRTH_DATE = FormatUtil.toDate(BRTH_ESTB_DT,FormatUtil.EN,"yyyy-MM-dd"); 
		
		String MARRIED = ListBoxControl.getName(FIELD_ID_MARRIED,"MAPPING4",FormatUtil.trim(rs.getString("MAR_ST_CD")),"CHOICE_NO"); 
		String DEGREE = FormatUtil.trim(rs.getString("CTF_TP_CD")); 
			   DEGREE = ListBoxControl.getName(FIELD_ID_DEGREE,"MAPPING4",DEGREE,"CHOICE_NO"); 
		String OCCUPATION = ListBoxControl.getName(FIELD_ID_OCCUPATION,"MAPPING4",FormatUtil.trim(rs.getString("OCP_CD")),"CHOICE_NO"); 
		String PROFESSION = ListBoxControl.getName(FIELD_ID_PROFESSION,"MAPPING4",FormatUtil.trim(rs.getString("PROF_CD")),"CHOICE_NO"); 
		String PROFESSION_DETAIL =  FormatUtil.trim(rs.getString("PROF_DTL"));
		
		if(!Util.empty(PROFESSION) && !PROFESSION_OTHER_CIS.equals(PROFESSION_CODE)){
			PROFESSION_DETAIL = null;
		}else if(Util.empty(PROFESSION) || (!Util.empty(PROFESSION) && PROFESSION_OTHER_CIS.equals(PROFESSION_CODE))){
			PROFESSION = PROFESSION_OTHER;
		}
		
		String GENDER = ListBoxControl.getName(FIELD_ID_GENDER,"MAPPING4",FormatUtil.trim(rs.getString("GND_CD")),"CHOICE_NO"); 
		String NATIONALITY = ListBoxControl.getName(FIELD_ID_NATIONALITY,"MAPPING4",FormatUtil.trim(rs.getString("NAT_CD")),"CHOICE_NO"); 
//		String TITLE_MOBILE = rs.getString("ELC_ADR_LO_USE_TP_CD"); 
//		String TEL_NO = rs.getString("TEL_NO"); 
//		String MOBILE_NO = ""; 
//		if(!Util.empty(TITLE_MOBILE) && !Util.empty(TEL_NO)){
//			MOBILE_NO = TITLE_MOBILE+TEL_NO; 
//		}
//		String EMAIL = rs.getString("ELC_ADR_DTL"); 
		
		String CID_TYPE_FLP = ListBoxControl.getName(FIELD_ID_CID_TYPE,"MAPPING4",FormatUtil.trim(rs.getString("DOC_ITM_CD")),"CHOICE_NO"); 
		String ID_NO = FormatUtil.trim(rs.getString("IDENT_NO")); 
		
		String PRIM_SEG_CD = FormatUtil.trim(rs.getString("PRIM_SEG_CD")); 
		String PRIM_SUB_SEG_CD = FormatUtil.trim(rs.getString("PRIM_SUB_SEG_CD")); 
		String DUAL_SEG_CD = FormatUtil.trim(rs.getString("DUAL_SEG_CD")); 
		String DUAL_SUB_SEG_CD = FormatUtil.trim(rs.getString("DUAL_SUB_SEG_CD")); 
		String CUST_TYPE_CD = FormatUtil.trim(rs.getString("IP_LGL_STC_TP_CD")); 
		
		logger.debug("CIS_NO : "+CIS_NO); 	
		logger.debug("CID_TYPE_FLP : "+CID_TYPE_FLP); 	
		logger.debug("ID_NO : "+ID_NO); 	
		logger.debug("TH_TITLE_CODE : "+TH_TITLE_CODE); 	
		logger.debug("TH_TITLE_DESC : "+TH_TITLE_DESC); 	
		logger.debug("TH_FIRST_NAME : "+TH_FIRST_NAME); 	
		logger.debug("TH_MID_NAME : "+TH_MID_NAME); 	
		logger.debug("TH_LAST_NAME : "+TH_LAST_NAME); 	
		logger.debug("EN_TITLE_CODE : "+EN_TITLE_CODE); 	
		logger.debug("EN_TITLE_DESC : "+EN_TITLE_DESC); 	
		logger.debug("EN_FIRST_NAME : "+EN_FIRST_NAME); 	
		logger.debug("EN_MID_NAME : "+EN_MID_NAME); 	
		logger.debug("EN_LAST_NAME : "+EN_LAST_NAME); 	
		logger.debug("BIRTH_DATE : "+BIRTH_DATE); 	
		logger.debug("MARRIED : "+MARRIED); 	
		logger.debug("DEGREE : "+DEGREE); 	
		logger.debug("OCCUPATION : "+OCCUPATION); 	
		logger.debug("PROFESSION : "+PROFESSION); 	
		logger.debug("GENDER : "+GENDER); 
		logger.debug("NATIONALITY : "+NATIONALITY); 	
//		logger.debug("MOBILE_NO : "+MOBILE_NO); 
//		logger.debug("EMAIL : "+EMAIL); 	
		
		logger.debug("PRIM_SEG_CD : "+PRIM_SEG_CD); 
		logger.debug("PRIM_SUB_SEG_CD : "+PRIM_SUB_SEG_CD); 
		logger.debug("DUAL_SEG_CD : "+DUAL_SEG_CD); 
		logger.debug("DUAL_SUB_SEG_CD : "+DUAL_SUB_SEG_CD); 
		logger.debug("CUST_TYPE_CD : "+CUST_TYPE_CD);
		personalInfo.setCisNo(Util.removeNotAllowSpeacialCharactors(CIS_NUMBER));
		logger.debug("personalInfo.getCisNo() : "+personalInfo.getCisNo()); 
		if(saveModel){
		personalInfo.setCidType(CID_TYPE_FLP); 
		if(Util.empty(personalInfo.getIdno())||SystemConstant.getConstant("PERSONAL_TYPE_INFO").equals(personalInfo.getPersonalType()))
		personalInfo.setIdno(Util.removeNotAllowSpeacialCharactors(ID_NO)); 
		personalInfo.setThTitleCode(Util.removeNotAllowSpeacialCharactors(TH_TITLE_CODE)); 
		personalInfo.setThTitleDesc(Util.removeNotAllowSpeacialCharactors(TH_TITLE_DESC)); 
		personalInfo.setThFirstName(Util.removeNotAllowSpeacialCharactors(TH_FIRST_NAME)); 
		personalInfo.setThMidName(Util.removeNotAllowSpeacialCharactors(TH_MID_NAME));
		personalInfo.setThLastName(Util.removeNotAllowSpeacialCharactors(TH_LAST_NAME)); 
		personalInfo.setEnTitleCode(Util.removeNotAllowSpeacialCharactors(EN_TITLE_CODE)); 
		personalInfo.setEnTitleDesc(Util.removeNotAllowSpeacialCharactors(EN_TITLE_DESC)); 
		personalInfo.setEnFirstName(Util.removeNotAllowSpeacialCharactors(EN_FIRST_NAME)); 
		personalInfo.setEnMidName(Util.removeNotAllowSpeacialCharactors(EN_MID_NAME)); 
		personalInfo.setEnLastName(Util.removeNotAllowSpeacialCharactors(EN_LAST_NAME)); 
		personalInfo.setBirthDate(BIRTH_DATE);
		personalInfo.setMarried(Util.removeNotAllowSpeacialCharactors(MARRIED)); 
		personalInfo.setDegree(Util.removeNotAllowSpeacialCharactors(DEGREE)); 
		personalInfo.setOccupation(Util.removeNotAllowSpeacialCharactors(OCCUPATION)); 
		
		boolean isExistProfRisk = false; 
		String profMapping = null;
		for(String PROFESSION_RISK : PROFESSION_RISK_GROUP){
			if(PROFESSION_RISK.equals(Util.removeNotAllowSpeacialCharactors(PROFESSION))){
				profMapping = ListBoxControl.getName(FIELD_ID_PROFESSION,"CHOICE_NO",Util.removeNotAllowSpeacialCharactors(PROFESSION),"SYSTEM_ID3"); 
				isExistProfRisk = true;
			}
		}
		if(isExistProfRisk){
			personalInfo.setBusinessType(profMapping);
			personalInfo.setProfession("");
		}else{
			personalInfo.setProfession(Util.removeNotAllowSpeacialCharactors(PROFESSION));
		}
		personalInfo.setProfessionOther(Util.removeAllSpecialCharactors(PROFESSION_DETAIL));
		personalInfo.setGender(Util.removeNotAllowSpeacialCharactors(GENDER)); 
		personalInfo.setNationality(Util.removeNotAllowSpeacialCharactors(NATIONALITY)); 	
//		personalInfo.setMobileNo(MOBILE_NO); 
//		personalInfo.setEmailPrimary(EMAIL); 

		personalInfo.setCisPrimSegment(PRIM_SEG_CD); 
		personalInfo.setCisPrimSubSegment(PRIM_SUB_SEG_CD); 
		personalInfo.setCisSecSegment(DUAL_SEG_CD); 
		personalInfo.setCisSecSubSegment(DUAL_SUB_SEG_CD);
		personalInfo.setCisCustType(CUST_TYPE_CD);
		}
		String refId = CompareSensitiveFieldUtil.getPersonalRefId(personalInfo); 	
		String refLevel = CompareDataM.RefLevel.PERSONAL; 
		logger.debug("refId >> "+refId); 
		logger.debug("refLevel >> "+refLevel); 
		
		createComparisonField("CIS_NO",refId,refLevel,CIS_NUMBER,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("TH_TITLE_CODE",refId,refLevel,TH_TITLE_CODE,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("TH_TITLE_DESC",refId,refLevel,TH_TITLE_DESC,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("TH_FIRST_NAME",refId,refLevel,TH_FIRST_NAME,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("TH_MID_NAME",refId,refLevel,TH_MID_NAME,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("TH_LAST_NAME",refId,refLevel,TH_LAST_NAME,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("EN_TITLE_CODE",refId,refLevel,EN_TITLE_CODE,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("EN_TITLE_DESC",refId,refLevel,EN_TITLE_DESC,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("EN_FIRST_NAME",refId,refLevel,EN_FIRST_NAME,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("EN_MID_NAME",refId,refLevel,EN_MID_NAME,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("EN_LAST_NAME",refId,refLevel,EN_LAST_NAME,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("BIRTH_DATE",refId,refLevel,BIRTH_DATE,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("MARRIED",refId,refLevel,MARRIED,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("DEGREE",refId,refLevel,DEGREE,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("OCCUPATION",refId,refLevel,OCCUPATION,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("PROFESSION",refId,refLevel,PROFESSION,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo);
		createComparisonField("GENDER",refId,refLevel,GENDER,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("NATIONALITY",refId,refLevel,NATIONALITY,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		
//		logCisComparisonField("MOBILE_NO",refId,refLevel,MOBILE_NO,comparisonFields); 
//		logCisComparisonField("EMAIL",refId,refLevel,EMAIL,comparisonFields); 
		
	}
	
	public void eAppPersonalDataMapper(String cisNo, PersonalInfoDataM personalInfo, HashMap<String, CompareDataM> comparisonFields, boolean saveModel) throws ApplicationException {
		logger.debug("cisNo >> "+cisNo); 	
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		if(null == comparisonFields){
			comparisonFields = new HashMap<String, CompareDataM>(); 
		}
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
				SQL.append(" SELECT IP_ID, DOC_ITM_CD, TH_TTL, TH_FRST_NM, TH_MDL_NM, ");  
				SQL.append(" TH_SURNM, EN_TTL, EN_FRST_NM, EN_MDL_NM, EN_SURNM, "); 
				SQL.append(" BRTH_ESTB_DT, MAR_ST_CD, CTF_TP_CD, OCP_CD, PROF_CD, "); 
				SQL.append(" GND_CD, NAT_CD, DOC_ITM_CD, IDENT_NO, PRIM_SEG_CD, "); 
				SQL.append(" PRIM_SUB_SEG_CD, DUAL_SEG_CD, DUAL_SUB_SEG_CD, PROF_DTL,IP_LGL_STC_TP_CD "); 				
				SQL.append(" FROM IP_SHR.VC_IP "); 
//				SQL.append(" LEFT JOIN IP_SHR.VC_IP_X_ELC_ADR_REL B ON A.IP_ID = B.IP_ID "); 
				SQL.append(" WHERE VLD_TO_DT = ? "); //--Customer not delete 
				SQL.append(" AND LAST_VRSN_F = ? "); // --Customer Current Record 
				SQL.append(" AND IP_ST_CD = ? "); //--Customer Active 
//				SQL.append(" AND CONSND_F = ? "); 
				SQL.append(" AND IP_ID = ? "); 		
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,DEFAULT_VLD_TO_DT); 
			ps.setString(index++,DEFAULT_LAST_VRSN_F); 
			ps.setString(index++,DEFAULT_IP_ST_CD); 
//			ps.setString(index++,DEFAULT_CONSND_F); 	
			ps.setString(index++,cisNo); 	
			rs = ps.executeQuery(); 
			if (rs.next()) {
				eAppPersonalMapper(rs,cisNo,personalInfo,comparisonFields,saveModel); 				
				eAppAddressDataMapper(cisNo,personalInfo,comparisonFields,saveModel); 								
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
	}
	
	private void eAppPersonalMapper(ResultSet rs, String CIS_NUMBER, PersonalInfoDataM personalInfo, HashMap<String, CompareDataM> comparisonFields, boolean saveModel) throws SQLException {
		String CIS_NO = FormatUtil.trim(rs.getString("IP_ID"));
		String th_titleCode = FormatUtil.trim(rs.getString("TH_TTL")); 
		String TH_TITLE_CODE = ListBoxControl.getName(FIELD_ID_TH_TITLE_CODE, "MAPPING4", th_titleCode, "CHOICE_NO"); 
		String TH_TITLE_DESC = th_titleCode; 
		if(Util.empty(TH_TITLE_CODE) && !Util.empty(TH_TITLE_DESC)) {
			TH_TITLE_CODE = TITLE_OTHER; 
		} else {
			TH_TITLE_DESC = ListBoxControl.getName(FIELD_ID_TH_TITLE_CODE, TH_TITLE_CODE); 	
		}
		String en_titleCODE = FormatUtil.trim(rs.getString("EN_TTL"));
		String EN_TITLE_CODE = ListBoxControl.getName(FIELD_ID_EN_TITLE_CODE, "MAPPING4", en_titleCODE, "CHOICE_NO"); 
	 	String EN_TITLE_DESC = en_titleCODE;
	 	if(Util.empty(EN_TITLE_CODE) && !Util.empty(EN_TITLE_DESC)) {
	 		EN_TITLE_CODE = TITLE_OTHER;
		} else {
			EN_TITLE_DESC = ListBoxControl.getName(FIELD_ID_EN_TITLE_CODE, EN_TITLE_CODE);
		}
		String TH_FIRST_NAME = FormatUtil.trim(rs.getString("TH_FRST_NM")); 
		String TH_MID_NAME = FormatUtil.trim(rs.getString("TH_MDL_NM")); 
		String TH_LAST_NAME = FormatUtil.trim(rs.getString("TH_SURNM"));  
		String EN_FIRST_NAME = FormatUtil.trim(rs.getString("EN_FRST_NM")); 
		String EN_MID_NAME = FormatUtil.trim(rs.getString("EN_MDL_NM")); 
		String EN_LAST_NAME = FormatUtil.trim(rs.getString("EN_SURNM")); 
		String PROFESSION_CODE = FormatUtil.trim(rs.getString("PROF_CD")); 
		String BRTH_ESTB_DT = FormatUtil.trim(rs.getString("BRTH_ESTB_DT")); 
		logger.debug("BRTH_ESTB_DT : "+BRTH_ESTB_DT); 
		Date BIRTH_DATE = FormatUtil.toDate(BRTH_ESTB_DT, FormatUtil.EN, "yyyy-MM-dd"); 
		String MARRIED = ListBoxControl.getName(FIELD_ID_MARRIED, "MAPPING4", FormatUtil.trim(rs.getString("MAR_ST_CD")), "CHOICE_NO"); 
		String DEGREE = FormatUtil.trim(rs.getString("CTF_TP_CD")); 
			   DEGREE = ListBoxControl.getName(FIELD_ID_DEGREE, "MAPPING4", DEGREE, "CHOICE_NO"); 
		String OCCUPATION = ListBoxControl.getName(FIELD_ID_OCCUPATION, "MAPPING4", FormatUtil.trim(rs.getString("OCP_CD")), "CHOICE_NO"); 
		String PROFESSION = ListBoxControl.getName(FIELD_ID_PROFESSION, "MAPPING4", FormatUtil.trim(rs.getString("PROF_CD")), "CHOICE_NO"); 
		String PROFESSION_DETAIL =  FormatUtil.trim(rs.getString("PROF_DTL"));
		if(!Util.empty(PROFESSION) && !PROFESSION_OTHER_CIS.equals(PROFESSION_CODE)) {
			PROFESSION_DETAIL = null;
		} else if (Util.empty(PROFESSION) || (!Util.empty(PROFESSION) && PROFESSION_OTHER_CIS.equals(PROFESSION_CODE))){
			PROFESSION = PROFESSION_OTHER;
		}
		String GENDER = ListBoxControl.getName(FIELD_ID_GENDER, "MAPPING4", FormatUtil.trim(rs.getString("GND_CD")), "CHOICE_NO"); 
		String NATIONALITY = ListBoxControl.getName(FIELD_ID_NATIONALITY, "MAPPING4", FormatUtil.trim(rs.getString("NAT_CD")), "CHOICE_NO"); 
		String CID_TYPE_FLP = ListBoxControl.getName(FIELD_ID_CID_TYPE, "MAPPING4", FormatUtil.trim(rs.getString("DOC_ITM_CD")), "CHOICE_NO"); 
		String ID_NO = FormatUtil.trim(rs.getString("IDENT_NO"));
		String PRIM_SEG_CD = FormatUtil.trim(rs.getString("PRIM_SEG_CD")); 
		String PRIM_SUB_SEG_CD = FormatUtil.trim(rs.getString("PRIM_SUB_SEG_CD")); 
		String DUAL_SEG_CD = FormatUtil.trim(rs.getString("DUAL_SEG_CD")); 
		String DUAL_SUB_SEG_CD = FormatUtil.trim(rs.getString("DUAL_SUB_SEG_CD")); 
		String CUST_TYPE_CD = FormatUtil.trim(rs.getString("IP_LGL_STC_TP_CD")); 
		
		logger.debug("CIS_NO : "+CIS_NO); 	
		logger.debug("CID_TYPE_FLP : "+CID_TYPE_FLP); 	
		logger.debug("ID_NO : "+ID_NO); 	
		logger.debug("TH_TITLE_CODE : "+TH_TITLE_CODE); 	
		logger.debug("TH_TITLE_DESC : "+TH_TITLE_DESC); 	
		logger.debug("TH_FIRST_NAME : "+TH_FIRST_NAME); 	
		logger.debug("TH_MID_NAME : "+TH_MID_NAME); 	
		logger.debug("TH_LAST_NAME : "+TH_LAST_NAME); 	
		logger.debug("EN_TITLE_CODE : "+EN_TITLE_CODE); 	
		logger.debug("EN_TITLE_DESC : "+EN_TITLE_DESC); 	
		logger.debug("EN_FIRST_NAME : "+EN_FIRST_NAME); 	
		logger.debug("EN_MID_NAME : "+EN_MID_NAME);
		logger.debug("EN_LAST_NAME : "+EN_LAST_NAME);
		logger.debug("BIRTH_DATE : "+BIRTH_DATE);
		logger.debug("MARRIED : "+MARRIED);
		logger.debug("DEGREE : "+DEGREE);
		logger.debug("OCCUPATION : "+OCCUPATION);
		logger.debug("PROFESSION : "+PROFESSION);
		logger.debug("GENDER : "+GENDER);
		logger.debug("NATIONALITY : "+NATIONALITY);
		logger.debug("PRIM_SEG_CD : "+PRIM_SEG_CD); 
		logger.debug("PRIM_SUB_SEG_CD : "+PRIM_SUB_SEG_CD); 
		logger.debug("DUAL_SEG_CD : "+DUAL_SEG_CD); 
		logger.debug("DUAL_SUB_SEG_CD : "+DUAL_SUB_SEG_CD); 
		logger.debug("CUST_TYPE_CD : "+CUST_TYPE_CD);
		logger.debug("personalInfo.getCisNo() : "+personalInfo.getCisNo()); 
		logger.debug("PRIM_SEG_CD : " + PRIM_SEG_CD); 
		logger.debug("PRIM_SUB_SEG_CD : " + PRIM_SUB_SEG_CD); 
		logger.debug("DUAL_SEG_CD : " + DUAL_SEG_CD); 
		logger.debug("DUAL_SUB_SEG_CD : " + DUAL_SUB_SEG_CD); 
		logger.debug("CUST_TYPE_CD : " + CUST_TYPE_CD);
		
		personalInfo.setCisPrimSegment(PRIM_SEG_CD); 
		personalInfo.setCisPrimSubSegment(PRIM_SUB_SEG_CD); 
		personalInfo.setCisSecSegment(DUAL_SEG_CD); 
		personalInfo.setCisSecSubSegment(DUAL_SUB_SEG_CD);
		personalInfo.setCisCustType(CUST_TYPE_CD);
		
		String refId = CompareSensitiveFieldUtil.getPersonalRefId(personalInfo); 	
		String refLevel = CompareDataM.RefLevel.PERSONAL; 
		logger.debug("refId >> " + refId); 
		logger.debug("refLevel >> " + refLevel); 
		
		createComparisonField("CIS_NO",refId,refLevel,CIS_NUMBER,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("TH_TITLE_CODE",refId,refLevel,TH_TITLE_CODE,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("TH_TITLE_DESC",refId,refLevel,TH_TITLE_DESC,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("TH_FIRST_NAME",refId,refLevel,TH_FIRST_NAME,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("TH_MID_NAME",refId,refLevel,TH_MID_NAME,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("TH_LAST_NAME",refId,refLevel,TH_LAST_NAME,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("EN_TITLE_CODE",refId,refLevel,EN_TITLE_CODE,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("EN_TITLE_DESC",refId,refLevel,EN_TITLE_DESC,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("EN_FIRST_NAME",refId,refLevel,EN_FIRST_NAME,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("EN_MID_NAME",refId,refLevel,EN_MID_NAME,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("EN_LAST_NAME",refId,refLevel,EN_LAST_NAME,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("BIRTH_DATE",refId,refLevel,BIRTH_DATE,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("MARRIED",refId,refLevel,MARRIED,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("DEGREE",refId,refLevel,DEGREE,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("OCCUPATION",refId,refLevel,OCCUPATION,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("PROFESSION",refId,refLevel,PROFESSION,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo);
		createComparisonField("GENDER",refId,refLevel,GENDER,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		createComparisonField("NATIONALITY",refId,refLevel,NATIONALITY,comparisonFields, personalInfo.getPersonalId(),CompareDataM.UniqueLevel.PERSONAL,personalInfo); 
		
	}
	
	public void addressDataMapper(String CIS_NUMBER,PersonalInfoDataM personalInfo,HashMap<String,CompareDataM> comparisonFields,boolean saveModel) throws ApplicationException{
		logger.debug("getCisAddressInfo..."); 
		String ADRSTS_STATUS_ACTIVE = SystemConstant.getConstant("ADRSTS_STATUS_ACTIVE");
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 		
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
				SQL.append("SELECT ADR_USE_TP_CD, WRKPLC_NM, VILL, BLD_NM, ROOM_NO, "); 
				SQL.append(" FLR_NO, HS_NO, VILL_NO, ALY, STR_NM, "); 
				SQL.append(" CTY_CD, SUBDSTC, DSTC, PROV,PSTCD_AREA_CD "); 
				SQL.append(" FROM IP_SHR.VC_IP_X_ADR_REL WHERE IP_ID = ? AND IP_X_ADR_REL_ST_CD = ? AND ADR_ST_CD = ? ");
				SQL.append(" AND VLD_TO_DT = ? ");
			logger.debug("SQL >> "+SQL); 
			logger.debug("CIS_NUMBER >> "+CIS_NUMBER);
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,CIS_NUMBER); 	
			ps.setString(index++,ADRSTS_STATUS_ACTIVE);
			ps.setString(index++,DEFAULT_ADR_ST_CD);
			ps.setString(index++,DEFAULT_VLD_TO_DT);
			
			rs = ps.executeQuery(); 
			while(rs.next()) {
				addressMapper(rs,personalInfo,comparisonFields,saveModel); 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
//		#rawi add for validate address type.
		ArrayList<String> addressTypes = new ArrayList<String>(Arrays.asList(DISPLAY_ADDRESS_TYPE));
		if(null!=addressTypes){
			for(String addressType : addressTypes){
				AddressDataM address = personalInfo.getAddress(addressType);
				if(null==address){
					address = new AddressDataM(); 
					String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK); 
					address.setAddressId(addressId); 
					address.setAddressType(addressType); 
					address.setPersonalId(personalInfo.getPersonalId()); 
					personalInfo.addAddress(address); 
				}
			}
		}
	}
	
	private void addressMapper(ResultSet rs,PersonalInfoDataM personalInfo,HashMap<String,CompareDataM> comparisonFields,boolean saveModel) throws SQLException{
		ArrayList<String> displayAddressTypes = new ArrayList<String>(Arrays.asList(DISPLAY_ADDRESS_TYPE)); 		
		
		String ADDRESS_TYPE = ListBoxControl.getName(FIELD_ID_ADDRESS_TYPE, "MAPPING4", FormatUtil.trim(rs.getString("ADR_USE_TP_CD")), "CHOICE_NO"); 
		String COMPANY_NAME = FormatUtil.trim(rs.getString("WRKPLC_NM")); 
		String VILAPT = FormatUtil.trim(rs.getString("VILL")); 
		String BUILDING = FormatUtil.trim(rs.getString("BLD_NM")); 
		String ROOM = FormatUtil.trim(rs.getString("ROOM_NO")); 
		String FLOOR = FormatUtil.trim(rs.getString("FLR_NO")); 
		String ADDRESS = FormatUtil.trim(rs.getString("HS_NO")); 
		String MOO = FormatUtil.trim(rs.getString("VILL_NO")); 
		String SOI = FormatUtil.trim(rs.getString("ALY")); 
		String ROAD = FormatUtil.trim(rs.getString("STR_NM")); 
		String COUNTRY = FormatUtil.trim(rs.getString("CTY_CD")); 
		String TAMBOL = FormatUtil.trim(rs.getString("SUBDSTC")); 
		String AMPHUR = FormatUtil.trim(rs.getString("DSTC")); 				
		String PROVINCE = FormatUtil.trim(rs.getString("PROV")); 
		String ZIPCODE = FormatUtil.trim(rs.getString("PSTCD_AREA_CD")); 
		
		logger.debug("ADDRESS_TYPE >> "+ADDRESS_TYPE); 
		logger.debug("COMPANY_NAME >> "+COMPANY_NAME); 
		logger.debug("VILAPT >> "+VILAPT); 
		logger.debug("BUILDING >> "+BUILDING); 
		logger.debug("ROOM >> "+ROOM); 
		logger.debug("FLOOR >> "+FLOOR); 
		logger.debug("ADDRESS >> "+ADDRESS); 
		logger.debug("MOO >> "+MOO); 
		logger.debug("SOI >> "+SOI); 
		logger.debug("ROAD >> "+ROAD); 
		logger.debug("COUNTRY >> "+COUNTRY); 
		logger.debug("TAMBOL >> "+TAMBOL); 
		logger.debug("AMPHUR >> "+AMPHUR); 
		logger.debug("ZIPCODE >> "+ZIPCODE); 
		logger.debug("PROVINCE >> "+PROVINCE); 
		
		if(!Util.empty(ADDRESS_TYPE) && displayAddressTypes.contains(ADDRESS_TYPE)){
			AddressDataM address = personalInfo.getAddress(ADDRESS_TYPE); 
			if(null == address){
				address = new AddressDataM(); 
				String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK); 
				address.setAddressId(Util.removeNotAllowSpeacialCharactors(addressId)); 
				address.setAddressType(Util.removeNotAllowSpeacialCharactors(ADDRESS_TYPE)); 
				address.setPersonalId(personalInfo.getPersonalId()); 
				personalInfo.addAddress(address); 
			   }
			
			if(Util.empty(address.getAddressId())){
				String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK); 
				address.setAddressId(Util.removeNotAllowSpeacialCharactors(addressId));
			}
			/*
			 * CR75
			 *  For all address types: If country of address <> Thai, no display address data 
			 */
			if(saveModel)
			address.setCompanyName(Util.removeNotAllowSpeacialCharactors(COMPANY_NAME)); 
			
			String refId = CompareSensitiveFieldUtil.getPersonalRefId(personalInfo); 
			String refLevel = CompareDataM.RefLevel.ADDRESS; 
			logger.debug("refId >> "+refId); 					
			logger.debug("refLevel >> "+refLevel); 
			createComparisonField("COMPANY_NAME",refId,refLevel,ADDRESS_TYPE,Util.removeNotAllowSpeacialCharactors(COMPANY_NAME),comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo);
				if(saveModel){
					if(ADRSTS_CITY_CD_TH.equals(COUNTRY)) {
						address.setAddress(Util.removeNotAllowSpeacialCharactors(ADDRESS)); 
						address.setMoo(Util.removeNotAllowSpeacialCharactors(MOO)); 
						address.setSoi(Util.removeNotAllowSpeacialCharactors(SOI)); 
						address.setRoad(Util.removeNotAllowSpeacialCharactors(ROAD)); 
						address.setCountry(Util.removeNotAllowSpeacialCharactors(COUNTRY)); 
						address.setTambol(Util.removeNotAllowSpeacialCharactors(TAMBOL)); 
						address.setAmphur(Util.removeNotAllowSpeacialCharactors(AMPHUR)); 
						address.setProvinceDesc(Util.removeNotAllowSpeacialCharactors(PROVINCE)); 
						address.setZipcode(Util.removeNotAllowSpeacialCharactors(ZIPCODE)); 
	
						if(ADDRESS_TYPE_CURRENT.equals(ADDRESS_TYPE)){
							address.setVilapt(Util.removeNotAllowSpeacialCharactors(VILAPT));
							address.setBuilding(Util.removeNotAllowSpeacialCharactors(BUILDING));
							address.setRoom(Util.removeNotAllowSpeacialCharactors(ROOM));
							address.setFloor(Util.removeNotAllowSpeacialCharactors(FLOOR)); 
						}else if(ADDRESS_TYPE_WORK.equals(ADDRESS_TYPE)){
							address.setBuilding(Util.removeNotAllowSpeacialCharactors(BUILDING));
							address.setFloor(Util.removeNotAllowSpeacialCharactors(FLOOR)); 
						}
					}
				}
				createComparisonField("ADDRESS_NAME",refId,refLevel,ADDRESS_TYPE,VILAPT,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
				createComparisonField("BUILDING",refId,refLevel,ADDRESS_TYPE,BUILDING,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
				createComparisonField("ROOM",refId,refLevel,ADDRESS_TYPE,ROOM,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
				createComparisonField("FLOOR",refId,refLevel,ADDRESS_TYPE,FLOOR,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
				createComparisonField("ADDRESS_ID",refId,refLevel,ADDRESS_TYPE,ADDRESS,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
				createComparisonField("MOO",refId,refLevel,ADDRESS_TYPE,MOO,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
				createComparisonField("SOI",refId,refLevel,ADDRESS_TYPE,SOI,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
				createComparisonField("ROAD",refId,refLevel,ADDRESS_TYPE,ROAD,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
				createComparisonField("COUNTRY",refId,refLevel,ADDRESS_TYPE,COUNTRY,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
				createComparisonField("TAMBOL",refId,refLevel,ADDRESS_TYPE,TAMBOL,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
				createComparisonField("AMPHUR",refId,refLevel,ADDRESS_TYPE,AMPHUR,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
				createComparisonField("PROVINCE",refId,refLevel,ADDRESS_TYPE,PROVINCE,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
				createComparisonField("ZIPCODE",refId,refLevel,ADDRESS_TYPE,ZIPCODE,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
				// end of if : check country
		}
	}
	
	public void eAppAddressDataMapper(String CIS_NUMBER, PersonalInfoDataM personalInfo, HashMap<String, CompareDataM> comparisonFields, boolean saveModel) throws ApplicationException {
		logger.debug("getCisAddressInfo..."); 
		String ADRSTS_STATUS_ACTIVE = SystemConstant.getConstant("ADRSTS_STATUS_ACTIVE");
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 		
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
				SQL.append("SELECT ADR_USE_TP_CD, WRKPLC_NM, VILL, BLD_NM, ROOM_NO, "); 
				SQL.append(" FLR_NO, HS_NO, VILL_NO, ALY, STR_NM, "); 
				SQL.append(" CTY_CD, SUBDSTC, DSTC, PROV,PSTCD_AREA_CD "); 
				SQL.append(" FROM IP_SHR.VC_IP_X_ADR_REL WHERE IP_ID = ? AND IP_X_ADR_REL_ST_CD = ? AND ADR_ST_CD = ? ");
				SQL.append(" AND VLD_TO_DT = ? ");
			logger.debug("SQL >> "+SQL); 
			logger.debug("CIS_NUMBER >> "+CIS_NUMBER);
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,CIS_NUMBER); 	
			ps.setString(index++,ADRSTS_STATUS_ACTIVE);
			ps.setString(index++,DEFAULT_ADR_ST_CD);
			ps.setString(index++,DEFAULT_VLD_TO_DT);
			
			rs = ps.executeQuery(); 
			while(rs.next()) {
				eAppAddressMapper(rs,personalInfo,comparisonFields,saveModel); 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
//		#rawi add for validate address type.
		ArrayList<String> addressTypes = new ArrayList<String>(Arrays.asList(DISPLAY_ADDRESS_TYPE));
		if(null!=addressTypes){
			for(String addressType : addressTypes){
				AddressDataM address = personalInfo.getAddress(addressType);
				if(null==address){
					address = new AddressDataM(); 
					String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK); 
					address.setAddressId(addressId); 
					address.setAddressType(addressType); 
					address.setPersonalId(personalInfo.getPersonalId()); 
					personalInfo.addAddress(address); 
				}
			}
		}
	}
	
	private void eAppAddressMapper(ResultSet rs,PersonalInfoDataM personalInfo,HashMap<String,CompareDataM> comparisonFields,boolean saveModel) throws SQLException{
		ArrayList<String> displayAddressTypes = new ArrayList<String>(Arrays.asList(DISPLAY_ADDRESS_TYPE)); 		
		
		String ADDRESS_TYPE = ListBoxControl.getName(FIELD_ID_ADDRESS_TYPE, "MAPPING4", FormatUtil.trim(rs.getString("ADR_USE_TP_CD")), "CHOICE_NO"); 
		String COMPANY_NAME = FormatUtil.trim(rs.getString("WRKPLC_NM")); 
		String VILAPT = FormatUtil.trim(rs.getString("VILL")); 
		String BUILDING = FormatUtil.trim(rs.getString("BLD_NM")); 
		String ROOM = FormatUtil.trim(rs.getString("ROOM_NO")); 
		String FLOOR = FormatUtil.trim(rs.getString("FLR_NO")); 
		String ADDRESS = FormatUtil.trim(rs.getString("HS_NO")); 
		String MOO = FormatUtil.trim(rs.getString("VILL_NO")); 
		String SOI = FormatUtil.trim(rs.getString("ALY")); 
		String ROAD = FormatUtil.trim(rs.getString("STR_NM")); 
		String COUNTRY = FormatUtil.trim(rs.getString("CTY_CD")); 
		String TAMBOL = FormatUtil.trim(rs.getString("SUBDSTC")); 
		String AMPHUR = FormatUtil.trim(rs.getString("DSTC")); 				
		String PROVINCE = FormatUtil.trim(rs.getString("PROV")); 
		String ZIPCODE = FormatUtil.trim(rs.getString("PSTCD_AREA_CD")); 
		
		logger.debug("ADDRESS_TYPE >> "+ADDRESS_TYPE); 
		logger.debug("COMPANY_NAME >> "+COMPANY_NAME); 
		logger.debug("VILAPT >> "+VILAPT); 
		logger.debug("BUILDING >> "+BUILDING); 
		logger.debug("ROOM >> "+ROOM); 
		logger.debug("FLOOR >> "+FLOOR); 
		logger.debug("ADDRESS >> "+ADDRESS); 
		logger.debug("MOO >> "+MOO); 
		logger.debug("SOI >> "+SOI); 
		logger.debug("ROAD >> "+ROAD); 
		logger.debug("COUNTRY >> "+COUNTRY); 
		logger.debug("TAMBOL >> "+TAMBOL); 
		logger.debug("AMPHUR >> "+AMPHUR); 
		logger.debug("ZIPCODE >> "+ZIPCODE); 
		logger.debug("PROVINCE >> "+PROVINCE); 
		
		if(!Util.empty(ADDRESS_TYPE) && displayAddressTypes.contains(ADDRESS_TYPE)) {
			AddressDataM address = personalInfo.getAddress(ADDRESS_TYPE);
			if(null == address) {
				address = new AddressDataM(); 
				String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK); 
				address.setAddressId(Util.removeNotAllowSpeacialCharactors(addressId));
				personalInfo.addAddress(address); 
			}
			
			if(Util.empty(address.getAddressId())){
				String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK); 
				address.setAddressId(Util.removeNotAllowSpeacialCharactors(addressId));
			}
			String refId = CompareSensitiveFieldUtil.getPersonalRefId(personalInfo); 
			String refLevel = CompareDataM.RefLevel.ADDRESS; 
			logger.debug("refId >> "+refId); 					
			logger.debug("refLevel >> "+refLevel); 
			createComparisonField("COMPANY_NAME",refId,refLevel,ADDRESS_TYPE,Util.removeNotAllowSpeacialCharactors(COMPANY_NAME),comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo);
			createComparisonField("ADDRESS_NAME",refId,refLevel,ADDRESS_TYPE,VILAPT,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
			createComparisonField("BUILDING",refId,refLevel,ADDRESS_TYPE,BUILDING,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
			createComparisonField("ROOM",refId,refLevel,ADDRESS_TYPE,ROOM,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
			createComparisonField("FLOOR",refId,refLevel,ADDRESS_TYPE,FLOOR,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
			createComparisonField("ADDRESS_ID",refId,refLevel,ADDRESS_TYPE,ADDRESS,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
			createComparisonField("MOO",refId,refLevel,ADDRESS_TYPE,MOO,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
			createComparisonField("SOI",refId,refLevel,ADDRESS_TYPE,SOI,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
			createComparisonField("ROAD",refId,refLevel,ADDRESS_TYPE,ROAD,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
			createComparisonField("COUNTRY",refId,refLevel,ADDRESS_TYPE,COUNTRY,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
			createComparisonField("TAMBOL",refId,refLevel,ADDRESS_TYPE,TAMBOL,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
			createComparisonField("AMPHUR",refId,refLevel,ADDRESS_TYPE,AMPHUR,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
			createComparisonField("PROVINCE",refId,refLevel,ADDRESS_TYPE,PROVINCE,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
			createComparisonField("ZIPCODE",refId,refLevel,ADDRESS_TYPE,ZIPCODE,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
		}
	}
	
	public void createComparisonField(String fieldNameType,String refId,String refLevel,String refType,Object value,HashMap<String, CompareDataM> comparisonFields,String uniqueId,String uniqueLevel,PersonalInfoDataM personalInfo){
		String fieldName = fieldNameType+"_"+refId+"_"+refType; 
		logger.debug("fieldNameType >> "+fieldNameType); 
		logger.debug("refId >> "+refId); 
		logger.debug("refLevel >> "+refLevel); 
		logger.debug("fieldName >> "+fieldName); 
		CompareDataM compareData = comparisonFields.get(fieldName); 
		String setValue = null; 
		try{
			if(value instanceof String){
				setValue = (String)value; 
			}else if(value instanceof Date){
				setValue = FormatUtil.display((Date)value,FormatUtil.EN,FormatUtil.Format.ddMMyyyy); 
			}else if(value instanceof BigDecimal){
				setValue = FormatUtil.display((BigDecimal)value); 
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e); 
		}
		if(null == compareData){
			compareData = new CompareDataM(); 
			compareData.setFieldNameType(fieldNameType); 
			compareData.setValue(setValue); 
			compareData.setRefId(refId); 
			compareData.setRefLevel(refLevel); 
			compareData.setSrcOfData(CompareDataM.SoruceOfData.CIS); 
			compareData.setUniqueId(uniqueId); 
			compareData.setUniqueLevel(uniqueLevel); 
			compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData)); 
			compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(personalInfo,"")); 
//			compareData.setFieldName(fieldName); 
			comparisonFields.put(fieldName,compareData); 
		}else{
			compareData.setValue(setValue); 
		}
	}
	public void createComparisonField(String fieldNameType,String refId,String refLevel,Object value,HashMap<String, CompareDataM> comparisonFields,String uniqueId, String uniqueLevel,PersonalInfoDataM personalInfo){
		String fieldName = fieldNameType+"_"+refId; 
		logger.debug("fieldNameType >> "+fieldNameType); 
		logger.debug("refId >> "+refId); 
		logger.debug("refLevel >> "+refLevel); 
		logger.debug("fieldName >> "+fieldName); 
		CompareDataM compareData = comparisonFields.get(fieldName); 
		String setValue = null; 
		try{
			if(value instanceof String){
				setValue = (String)value; 
			}else if(value instanceof Date){
				setValue = FormatUtil.display((Date)value,FormatUtil.EN,FormatUtil.Format.ddMMyyyy); 
			}else if(value instanceof BigDecimal){
				setValue = FormatUtil.display((BigDecimal)value); 
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e); 
		}
		if(null == compareData){
			compareData = new CompareDataM(); 
			compareData.setFieldNameType(fieldNameType); 
			compareData.setValue(setValue); 
			compareData.setRefId(refId); 
			compareData.setRefLevel(refLevel); 
			compareData.setSrcOfData(CompareDataM.SoruceOfData.CIS); 
			compareData.setUniqueId(uniqueId); 
			compareData.setUniqueLevel(uniqueLevel); 
			compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData)); 
			compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(personalInfo,"")); 
//			compareData.setFieldName(fieldName); 
			comparisonFields.put(fieldName,compareData); 
		}else{
			compareData.setValue(setValue); 
		}
	}
	
	private HashMap<String,String> getCisMainCard(String CC_CST_NO,String CARD_ORG_NO) throws ApplicationException{
		
		logger.debug("getCisMainCard..."); 
		HashMap<String,String> result = new HashMap<String,String>(); 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			
			/* #Chatmongkol Change Query Reference #DIH_Query_Avalant_20170515_DIHComment
			SQL.append(" SELECT CARD_NO_ENCPT  , IP_ID");   
			SQL.append(" FROM AR_SHR.VC_CC_CARD A ");
			SQL.append(" INNER JOIN IP_SHR.VL_AR_X_IP_REL B ON A.CC_CST_NO = B.AR_ADL_INF1 AND A.CC_CST_ORG_NO = TRIM(B.AR_ADL_INF2)");
			SQL.append(" WHERE  B.AR_X_IP_REL_TP_CD = ? "); //set Default
			SQL.append(" AND B.AR_X_IP_REL_ST_CD = ? "); //set Default
			SQL.append(" AND B.CIS_SRC_STM_CD = ? "); //set Default
			SQL.append(" AND B.VLD_TO_DT = ? "); //set Default		
			SQL.append(" AND B.AR_ADL_INF1 = ? "); 
			SQL.append(" AND B.AR_ADL_INF2 =?"); 
			*/
			
			SQL.append(" SELECT A.CARD_NO_ENCPT,B.IP_ID ");   
			SQL.append(" FROM   AR_SHR.VC_CC_CARD A INNER JOIN IP_SHR.VL_AR_X_IP_REL B ON  1 = 1 ");
			SQL.append(" WHERE ((A.MAIN_CC_CST_NO = ? AND A.MAIN_CC_CST_ORG_NO = ? AND A.ALT_CC_CST_NO = ?  AND A.CARD_TP NOT IN (? , ?)) ");
			SQL.append(" OR (A.ALT_CC_CST_NO = ?  AND A.ALT_CC_CST_ORG_NO = ? AND A.CARD_TP NOT IN (?, ?))) "); //set Default
			SQL.append(" AND B.AR_X_IP_REL_TP_CD = ? "); //set Default
			SQL.append(" AND B.AR_X_IP_REL_ST_CD = ? "); //set Default
			SQL.append(" AND B.CIS_SRC_STM_CD = ? "); //set Default
			SQL.append(" AND B.VLD_TO_DT = ? "); //set Default		
			SQL.append(" AND B.AR_ADL_INF1 = ? ");
			SQL.append(" AND B.AR_ADL_INF2 =? ");  
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			
			ps.setString(index++,CC_CST_NO);
			ps.setString(index++,CARD_ORG_NO);
			ps.setString(index++,CC_CST_NO_SUP);
			for(String CORPARATE : CORPARATE_CARD){
				ps.setString(index++,CORPARATE);
			}
			
			ps.setString(index++,CC_CST_NO);
			ps.setString(index++,CARD_ORG_NO);
			for(String CORPARATE : CORPARATE_CARD){
				ps.setString(index++,CORPARATE);
			}
			
			ps.setString(index++,DEFAULT_AR_X_IP_REL_TP_CD); 
			ps.setString(index++,DEFAULT_AR_X_IP_REL_ST_CD); 
			ps.setString(index++,DEFAULT_CIS_SRC_STM_CD); 
			ps.setString(index++,DEFAULT_VLD_TO_DT); 
			ps.setString(index++,CC_CST_NO); 
			ps.setString(index++,CARD_ORG_NO); 
			
			rs = ps.executeQuery(); 
			if (rs.next()) {
				result.put("IP_ID", FormatUtil.trim(rs.getString("IP_ID"))); 
				result.put("CARD_NO_ENCPT", FormatUtil.trim(rs.getString("CARD_NO_ENCPT"))); 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return result; 
	}

	@Override
	public void personalInfoMapper(String CIS_NUMBER,PersonalInfoDataM personalInfo,HashMap<String,CompareDataM> comparisonFields) throws ApplicationException {
		logger.debug("getCisCustomerInfo....."); 	
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		if(null == comparisonFields){
			comparisonFields = new HashMap<String, CompareDataM>(); 
		}
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
				SQL.append(" SELECT IP_ID, DOC_ITM_CD, TH_TTL, TH_FRST_NM, TH_MDL_NM, "); 
				SQL.append(" TH_SURNM, EN_TTL, EN_FRST_NM, EN_MDL_NM, EN_SURNM, "); 
				SQL.append(" BRTH_ESTB_DT, MAR_ST_CD, CTF_TP_CD, OCP_CD, PROF_CD, "); 
				SQL.append(" GND_CD, NAT_CD, DOC_ITM_CD, IDENT_NO, PRIM_SEG_CD,  "); 
				SQL.append(" PRIM_SUB_SEG_CD, DUAL_SEG_CD, DUAL_SUB_SEG_CD, PROF_DTL,IP_LGL_STC_TP_CD "); 
				SQL.append(" FROM IP_SHR.VC_IP "); 
//				SQL.append(" LEFT JOIN IP_SHR.VC_IP_X_ELC_ADR_REL B ON A.IP_ID = B.IP_ID "); 
				SQL.append(" WHERE VLD_TO_DT = ? "); //--Customer not delete 
				SQL.append(" AND LAST_VRSN_F = ? "); // --Customer Current Record 
				SQL.append(" AND IP_ST_CD = ? "); //--Customer Active 
//				SQL.append(" AND CONSND_F = ? "); 
				SQL.append(" AND IP_ID = ? "); 		
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,DEFAULT_VLD_TO_DT); 
			ps.setString(index++,DEFAULT_LAST_VRSN_F); 
			ps.setString(index++,DEFAULT_IP_ST_CD); 
//			ps.setString(index++,DEFAULT_CONSND_F); 	
			ps.setString(index++,CIS_NUMBER); 	
			rs = ps.executeQuery(); 
			if (rs.next()) {
				personalMapper(rs, CIS_NUMBER, personalInfo, comparisonFields,true); 						
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		}finally{
			try{
				closeConnection(conn, rs, ps); 
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
	}

	@Override
	public String getAccountName(String ACCOUNT_NUMBER, String SUB)	throws ApplicationException {
		logger.debug("getAccountName..."); 
		String ACCOUNT_NAME = ""; 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			SQL.append(" SELECT DISTINCT AR_NM_TH FROM AR_SHR.VC_FIX_DEP_AR WHERE AR_ID = ? AND SUB_AR = ? "); 
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++, ACCOUNT_NUMBER); 	
			ps.setString(index++, SUB); 	
			rs = ps.executeQuery(); 
			if (rs.next()) {
				ACCOUNT_NAME = FormatUtil.trim(rs.getString("AR_NM_TH")); 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		logger.debug("ACCOUNT_NAME : "+ACCOUNT_NAME); 	
		return ACCOUNT_NAME; 
	}

	@Override
	public String getAccountNamDevGuarantee(String ACCOUNT_NUMBER)	throws ApplicationException {
		logger.debug("getAccountName..."); 
		String ACCOUNT_NAME = ""; 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT DISTINCT AR_NM_TH FROM AR_SHR.VC_SVG_DEP_AR WHERE AR_ID = ? "); 
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++, ACCOUNT_NUMBER); 	 	
			rs = ps.executeQuery(); 
			if (rs.next()) {
				ACCOUNT_NAME = FormatUtil.trim(rs.getString("AR_NM_TH")); 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		logger.debug("ACCOUNT_NAME : "+ACCOUNT_NAME); 
		logger.debug("ACCOUNT_NO : "+ACCOUNT_NUMBER); 
		return ACCOUNT_NAME; 
	}
	
	@Override
	public String getAccountNameFixGuarantee(String ACCOUNT_NUMBER, String SUB)	throws ApplicationException {
		logger.debug("getAccountName..."); 
		String ACCOUNT_NAME = ""; 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT DISTINCT AR_NM_TH FROM AR_SHR.VC_FIX_DEP_AR WHERE AR_ID = ? AND SUB_AR = ? "); 
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++, ACCOUNT_NUMBER); 	
			ps.setString(index++, SUB); 	
			rs = ps.executeQuery(); 
			if (rs.next()) {
				ACCOUNT_NAME = FormatUtil.trim(rs.getString("AR_NM_TH")); 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		logger.debug("ACCOUNT_NAME : "+ACCOUNT_NAME); 
		logger.debug("ACCOUNT_NO : "+ACCOUNT_NUMBER); 
		return ACCOUNT_NAME; 
	}
	
	@Override
	public String getAccountNameFixGuarantee(String ACCOUNT_NUMBER)	throws ApplicationException {
		logger.debug("getAccountName..."); 
		String ACCOUNT_NAME = ""; 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder();
			SQL.append(" SELECT DISTINCT AR_NM_TH FROM AR_SHR.VC_FIX_DEP_AR WHERE AR_ID = ? "); 
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++, ACCOUNT_NUMBER); 	
			rs = ps.executeQuery(); 
			if (rs.next()) {
				ACCOUNT_NAME = FormatUtil.trim(rs.getString("AR_NM_TH")); 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		logger.debug("ACCOUNT_NAME : "+ACCOUNT_NAME); 
		logger.debug("ACCOUNT_NO : "+ACCOUNT_NUMBER); 
		return ACCOUNT_NAME; 
	}
	
	@Override
	public String getCisInfoByAcctNumber(String ACCT_NUMBER, String field) throws ApplicationException {
		logger.debug("getCISNumber..."); 
		String searchData = ""; 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
//			SQL.append(" SELECT IP_ID, TRIM(AR_ADL_INF2) AR_ADL_INF2, AR_OWN_TP_CD, TRIM(CIS_SRC_STM_CD) CIS_SRC_STM_CD "); 
//			SQL.append(" FROM IP_SHR.VL_AR_X_IP_REL "); 
//			SQL.append(" WHERE AR_ADL_INF2 = ? "); 
//			SQL.append(" AND CIS_SRC_STM_CD IN ('72', '73','74','75') ");  //SA,CA,FD,SFD
//			SQL.append(" AND AR_X_IP_REL_ST_CD = ? ");  //ACTIVE
//			SQL.append(" AND AR_X_IP_REL_TP_CD = ? ");  // OWNER
////			SQL.append(" AND cis.AR_OWN_TP_CD = '2' ");  //SINGLE OWNER
//			SQL.append(" AND VLD_TO_DT = ? ");  //not delete from CIS 
			SQL.append(" SELECT A.IP_ID, A.AR_ADL_INF2, A.AR_OWN_TP_CD, A.CIS_SRC_STM_CD ");
			SQL.append(" FROM ");
			SQL.append(" ( ");
			SQL.append(" 	SELECT ROW_NUMBER() OVER(ORDER BY VLD_FM_DT DESC) AS RN, IP_ID, TRIM(AR_ADL_INF2) AR_ADL_INF2, AR_OWN_TP_CD, TRIM(CIS_SRC_STM_CD) CIS_SRC_STM_CD ");
			SQL.append(" 	FROM IP_SHR.VL_AR_X_IP_REL ");
			SQL.append(" 	WHERE AR_ADL_INF2 = ? ");
			SQL.append(" 	AND CIS_SRC_STM_CD IN ("+SystemConstant.getSQLParameter("DEFAULT_LIST_CIS_SRC_STM_CD")+") ");
			SQL.append(" 	AND AR_X_IP_REL_ST_CD = ? ");	//ACTIVE
			SQL.append(" 	AND AR_X_IP_REL_TP_CD = ? ");		// OWNER
			SQL.append(" 	AND VLD_TO_DT = ?   ");	//not delete from CIS 
			SQL.append(" ) A ");
			SQL.append(" WHERE RN = 1 ");
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++, ACCT_NUMBER); 
			ps.setString(index++,DEFAULT_AR_X_IP_REL_ST_CD); 
			ps.setString(index++,DEFAULT_AR_X_IP_REL_TP_CD); 
			ps.setString(index++,DEFAULT_VLD_TO_DT); 
			rs = ps.executeQuery(); 
			if (rs.next()) {
				searchData = FormatUtil.trim(rs.getString(field)); 
				logger.debug(field+" : "+searchData); 	
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return searchData; 
	}

	@Override
	public String getAccountInfo(String ACCT_NUMBER, String tableName, String field) throws ApplicationException {
		logger.debug("getAccountInformation..."); 
		String ACCT_STATUS = ""; 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			SQL.append(" SELECT DISTINCT AR_ID, AR_NM_TH, AR_LCS_TP_CD, DOMC_BR_NO "); 
			SQL.append(" FROM "+tableName); 
			SQL.append(" WHERE AR_ID = ? "); 
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++, ACCT_NUMBER); 
			rs = ps.executeQuery(); 
			if (rs.next()) {
				ACCT_STATUS = FormatUtil.trim(rs.getString(field)); 
				logger.debug("VALUE : "+ACCT_STATUS); 	
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return ACCT_STATUS; 
	}

	@Override
	public boolean isKbankEmployee(String idNo) throws ApplicationException {
		logger.debug("isKbankEmployee..."); 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		int CNT=0; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			SQL.append("SELECT COUNT(1) AS CNT FROM IP_SHR.VC_EMP WHERE IDENT_NO = ? AND EMP_ST_CD ='A'"); 
			SQL.append(" AND EMP_OFCR_TP_CD NOT IN ('F') "); 
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++, idNo); 
			rs = ps.executeQuery(); 
			if (rs.next()) {
				CNT = rs.getInt("CNT"); 	
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		logger.debug("CNT : "+CNT); 
		return CNT>0; 
	}

	@Override
	public List<VcCardDataM> searchCardInfoByCisNo(String CIS_NO) throws ApplicationException {
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		List<VcCardDataM> resultList = new ArrayList<VcCardDataM>(); 
		HashMap<String, HashMap<String, String>> hCondtions = getCisCardInfoCondition(CIS_NO); 
		try{
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			if(null!=hCondtions && !hCondtions.isEmpty()){
				SQL.append(" SELECT "); 
				SQL.append(" A.ACH_F, "); 
				SQL.append(" A.ALT_CC_CST_NO, "); 
				SQL.append(" A.ALT_CC_CST_ORG_NO, "); 
				SQL.append(" A.CASH_ADV_CYC_AVL_AMT, "); 
				SQL.append(" A.BILL_CYC, "); 
				
				SQL.append(" A.BLC_CD, "); 
				SQL.append(" A.BLC_DT, "); 
//				SQL.append(" A.CARD_NO_HASH, "); 
				SQL.append(" A.CARD_NO_MASK, "); 
				SQL.append(" A.CARD_ORG_NO, "); 
				
				SQL.append(" A.CARD_TP, "); 
				SQL.append(" A.CARD_TP1, "); 
				SQL.append(" A.CASH_ADV_BAL, "); 
				SQL.append(" A.CC_CST_NO, "); 
				SQL.append(" A.CC_CST_ORG_NO, "); 
				
				SQL.append(" A.CR_LMT_AMT, "); 
				SQL.append(" A.CR_LMT_DT, "); 
				SQL.append(" A.CRN_BAL, "); 
				SQL.append(" A.EMB_NM1, "); 
				SQL.append(" A.EMB_NM2, "); 
				
				SQL.append(" A.EMB_NM3, "); 
				SQL.append(" A.EXP_DT, "); 
				SQL.append(" A.HIST, "); 
				SQL.append(" A.LAST_PYMT_DT, "); 
				SQL.append(" A.MAIN_CC_CST_NO, "); 
				
				SQL.append(" A.MAIN_CC_CST_ORG_NO, "); 
				SQL.append(" A.OPN_DT, "); 
				SQL.append(" A.PERM_CR_LMT_AMT, "); 
				SQL.append(" A.PPN_TMS, "); 
				SQL.append(" A.SRC_STM_ID, "); 
				
				SQL.append(" A.ST_CD, "); 
				SQL.append(" A.TEMP_CR_LMT_AMT, "); 
				SQL.append(" A.TEMP_CR_LMT_EFF_DT, "); 
				SQL.append(" A.TEMP_CR_LMT_EXP_DT, "); 
				SQL.append(" A.NBR_RQS4 "); 
				/*
				  
				SQL.append(" WHERE ((A.MAIN_CC_CST_NO IN (?) ");
            	SQL.append(" AND A.MAIN_CC_CST_ORG_NO IN (?) ");
            	SQL.append(" AND A.ALT_CC_CST_NO = '0000000000000000'
            	SQL.append(" AND A.CARD_TP NOT IN (?, ?)) ");
        		SQL.append(" OR (A.ALT_CC_CST_NO IN (?) ");
            	SQL.append(" AND A.ALT_CC_CST_ORG_NO IN (?) ");
            	SQL.append(" AND A.CARD_TP NOT IN (?,?))) ");	 
				 */
				SQL.append(" FROM AR_SHR.VC_CC_CARD A "); 
				/*SQL.append(" WHERE   A.CC_CST_NO  IN ("); 
				String COMMA1=""; 
				for(HashMap<String, String> condition1 : hCondtions.values()){
					String AR_ADL_INF1 = condition1.get("AR_ADL_INF1"); 
					SQL.append(COMMA1+"'"+AR_ADL_INF1+"'"); 
					COMMA1=","; 
				}				
				SQL.append(" )"); 
				
				SQL.append(" AND  TO_CHAR(A.CARD_ORG_NO)  IN ("); 
				String COMMA2=""; 
				for(HashMap<String, String> condition2 : hCondtions.values()){
					String AR_ADL_INF2 = condition2.get("AR_ADL_INF2"); 
					SQL.append(COMMA2+"'"+AR_ADL_INF2+"'"); 
					COMMA2=","; 
				}				
				SQL.append(" )"); */
				
				SQL.append(" WHERE ((A.MAIN_CC_CST_NO  IN ("); 
				String COMMA1=""; 
				for(HashMap<String, String> condition1 : hCondtions.values()){
					String AR_ADL_INF1 = condition1.get("AR_ADL_INF1"); 
					SQL.append(COMMA1+"'"+AR_ADL_INF1+"'"); 
					COMMA1=","; 
				}				
				SQL.append(" )"); 
				
				SQL.append(" AND  A.MAIN_CC_CST_ORG_NO  IN ("); 
				String COMMA2=""; 
				for(HashMap<String, String> condition2 : hCondtions.values()){
					String AR_ADL_INF2 = condition2.get("AR_ADL_INF2"); 
					SQL.append(COMMA2+"'"+AR_ADL_INF2+"'"); 
					COMMA2=","; 
				}				
				SQL.append(" )"); 
				
				SQL.append(" AND  A.ALT_CC_CST_NO = "); 
				SQL.append("'"+CC_CST_NO_SUP+"'");
				
				SQL.append(" AND A.CARD_TP NOT IN ("); 
				String COMMA3=""; 
				for(String CORPARATE : CORPARATE_CARD){
					SQL.append(COMMA3+"'"+CORPARATE+"'"); 
					COMMA3=","; 
				}				
				SQL.append(" ))");
				
				SQL.append(" OR  (A.ALT_CC_CST_NO  IN ("); 
				COMMA1=""; 
				for(HashMap<String, String> condition2 : hCondtions.values()){
					String AR_ADL_INF2 = condition2.get("AR_ADL_INF1"); 
					SQL.append(COMMA1+"'"+AR_ADL_INF2+"'"); 
					COMMA1=","; 
				}				
				SQL.append(" )");
				
				
				SQL.append(" AND  A.ALT_CC_CST_ORG_NO  IN ("); 
				COMMA2=""; 
				for(HashMap<String, String> condition2 : hCondtions.values()){
					String AR_ADL_INF2 = condition2.get("AR_ADL_INF2"); 
					SQL.append(COMMA2+"'"+AR_ADL_INF2+"'"); 
					COMMA2=","; 
				}				
				SQL.append(" )"); 
				
				SQL.append(" AND A.CARD_TP NOT IN ("); 
				COMMA3=""; 
				for(String CORPARATE : CORPARATE_CARD){
					SQL.append(COMMA3+"'"+CORPARATE+"'"); 
					COMMA3=","; 
				}				
				SQL.append(" )))");
				
				logger.debug("SQL >> "+SQL); 
				ps = conn.prepareStatement(SQL.toString()); 
				rs = ps.executeQuery(); 
				
				while(rs.next()) {
					String KEY_MAPING = rs.getString("CC_CST_NO")+"_"+FormatUtil.toString(rs.getInt("CARD_ORG_NO"));  
					logger.debug("KEY_MAPING>>>>"+KEY_MAPING); 
					if(hCondtions.containsKey(KEY_MAPING)){
						VcCardDataM vcCard = new VcCardDataM(); 			
						vcCard.setACH_F(FormatUtil.trim(rs.getString("ACH_F"))); 
						vcCard.setALT_CC_CST_NO(FormatUtil.trim(rs.getString("ALT_CC_CST_NO"))); 
						vcCard.setALT_CC_CST_ORG_NO(rs.getInt("ALT_CC_CST_ORG_NO")); 
						vcCard.setAVL_BAL(rs.getInt("CASH_ADV_CYC_AVL_AMT")); 
						vcCard.setBILL_CYC(rs.getInt("BILL_CYC")); 
						
						vcCard.setBLC_CD(FormatUtil.trim(rs.getString("BLC_CD"))); 
						Date BLC_DT = FormatUtil.toDate(FormatUtil.trim(rs.getString("BLC_DT")),FormatUtil.EN,"YYYY-MM-DD"); 
						vcCard.setBLC_DT(BLC_DT); 
						
//						vcCard.setCARD_NO_HASH(FormatUtil.trim(rs.getString("CARD_NO_HASH"))); 
						
						vcCard.setCARD_NO_MASK(FormatUtil.trim(rs.getString("CARD_NO_MASK"))); 
						vcCard.setCARD_ORG_NO(rs.getInt("CARD_ORG_NO")); 
						
						vcCard.setCARD_TP(rs.getInt("CARD_TP")); 
						vcCard.setCARD_TP_CD(FormatUtil.trim(rs.getString("CARD_TP1"))); 
						vcCard.setCASH_ADV_BAL(rs.getInt("CASH_ADV_BAL")); 
						vcCard.setCC_CST_NO(FormatUtil.trim(rs.getString("CC_CST_NO"))); 
						vcCard.setCC_CST_ORG_NO(rs.getInt("CC_CST_ORG_NO")); 
						
						vcCard.setCR_LMT_AMT(rs.getInt("CR_LMT_AMT")); 
						Date CR_LMT_DT = FormatUtil.toDate(FormatUtil.trim(rs.getString("CR_LMT_DT")),FormatUtil.EN,"YYYY-MM-DD"); 
						vcCard.setCR_LMT_DT(CR_LMT_DT); 
						vcCard.setCRN_BAL(rs.getInt("CRN_BAL")); 
						vcCard.setEMB_NM1(FormatUtil.trim(rs.getString("EMB_NM1"))); 
						vcCard.setEMB_NM2(FormatUtil.trim(rs.getString("EMB_NM2"))); 
						
						vcCard.setEMB_NM3(FormatUtil.trim(rs.getString("EMB_NM3"))); 
						vcCard.setEXP_DT(FormatUtil.trim(rs.getString("EXP_DT"))); 
						vcCard.setHIST(FormatUtil.trim(rs.getString("HIST"))); 
						Date LAST_PYMT_DT = FormatUtil.toDate(FormatUtil.trim(rs.getString("LAST_PYMT_DT")),FormatUtil.EN,"YYYY-MM-DD"); 
						vcCard.setLAST_PYMT_DT(LAST_PYMT_DT); 
						vcCard.setMAIN_CC_CST_NO(FormatUtil.trim(rs.getString("MAIN_CC_CST_NO"))); 
						
						vcCard.setMAIN_CC_CST_ORG_NO(rs.getInt("MAIN_CC_CST_ORG_NO")); 
						Date OPN_DT = FormatUtil.toDate(FormatUtil.trim(rs.getString("OPN_DT")),FormatUtil.EN,"YYYY-MM-DD"); 
						vcCard.setOPN_DT(OPN_DT); 
						vcCard.setPERM_CR_LMT_AMT(rs.getInt("PERM_CR_LMT_AMT")); 
						vcCard.setPPN_TMS(rs.getTimestamp("PPN_TMS")); 
						vcCard.setSRC_STM_ID(rs.getInt("SRC_STM_ID")); 
						
						vcCard.setST_CD(FormatUtil.trim(rs.getString("ST_CD"))); 
						vcCard.setTEMP_CR_LMT_AMT(rs.getInt("TEMP_CR_LMT_AMT")); 
						Date TEMP_CR_LMT_EFF_DT = FormatUtil.toDate(FormatUtil.trim(rs.getString("TEMP_CR_LMT_EFF_DT")),FormatUtil.EN,"YYYY-MM-DD"); 
						vcCard.setTEMP_CR_LMT_EFF_DT(TEMP_CR_LMT_EFF_DT); 
						Date TEMP_CR_LMT_EXP_DT = FormatUtil.toDate(FormatUtil.trim(rs.getString("TEMP_CR_LMT_EXP_DT")),FormatUtil.EN,"YYYY-MM-DD"); 
						vcCard.setTEMP_CR_LMT_EXP_DT(TEMP_CR_LMT_EXP_DT); 
						vcCard.setUSR_AC(rs.getString("NBR_RQS4")); 
						
						resultList.add(vcCard); 
					}
				}
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return resultList; 
	}
	
	private HashMap<String, HashMap<String, String>> getCisCardInfoCondition(String CIS_NO) throws ApplicationException{
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		HashMap<String, HashMap<String, String>> hConditions = new HashMap<String, HashMap<String, String>>(); 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			SQL.append("  SELECT DISTINCT B.AR_ADL_INF1, B.AR_ADL_INF2 "); 
			SQL.append(" FROM IP_SHR.VL_AR_X_IP_REL B "); 
			SQL.append(" WHERE  B.IP_ID = ? "); // CIS No.
			SQL.append(" AND B.AR_X_IP_REL_TP_CD = ? "); //set Default
			SQL.append(" AND B.AR_X_IP_REL_ST_CD = ? "); //set Default
			SQL.append(" AND B.CIS_SRC_STM_CD = ? "); //set Default
			SQL.append(" AND B.VLD_TO_DT = ? "); //set Default	
			
			//Fix for CIS new design
			SQL.append(" AND B.AR_ADL_INF2 IN ('" + ORIG_ID_CC + "','" + ORIG_ID_KEC + "') ");
			
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,CIS_NO); 
			ps.setString(index++,DEFAULT_AR_X_IP_REL_TP_CD); 
			ps.setString(index++,DEFAULT_AR_X_IP_REL_ST_CD); 
			ps.setString(index++,DEFAULT_CIS_SRC_STM_CD); 
			ps.setString(index++,DEFAULT_VLD_TO_DT); 
			rs = ps.executeQuery(); 
			
			while(rs.next()) {
				String AR_ADL_INF1 = rs.getString("AR_ADL_INF1"); 
				String AR_ADL_INF2 = rs.getString("AR_ADL_INF2"); 
				String KEY=AR_ADL_INF1+"_"+AR_ADL_INF2; 
				logger.debug("KEY>>>>"+KEY); 
				HashMap<String, String> hData = hConditions.get(KEY); 
				if(Util.empty(hData)){
					hData = new HashMap<String, String>(); 
					hConditions.put(KEY,hData); 
				}
				hData.put("AR_ADL_INF1", AR_ADL_INF1); 
				hData.put("AR_ADL_INF2", AR_ADL_INF2); 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return hConditions; 
	}
	
	@Override
	public DIHQueryResult<String> getCisCompanyInfo(String COMPANY_TITLE, String COMPANY_NAME,PersonalInfoDataM personalInfo,HashMap<String, CompareDataM> comparisonFields)throws ApplicationException {
		logger.debug("getCisCompanyInfo..."); 	
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		if(null == comparisonFields){
			comparisonFields = new HashMap<String, CompareDataM>(); 
		}
		DIHQueryResult<String>   dihResult = new DIHQueryResult<String>();
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
				SQL.append(" SELECT IP_ID FROM IP_SHR.VC_IP "); 
				SQL.append(" WHERE VLD_TO_DT = ? "); //--Customer not delete 
				SQL.append(" AND IP_ST_CD = ? ");  // --active customer
				SQL.append(" AND IP_TP_CD = ? ");  // -- Organization Customer
				SQL.append(" AND TH_FRST_NM  = ? "); //--'<Company name>'
				SQL.append(" AND TH_TTL  = ? "); //--'<Company title>'
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			logger.debug("DEFAULT_VLD_TO_DT >> "+DEFAULT_VLD_TO_DT); 
			logger.debug("COMPANY_IP_ST_CD >> "+COMPANY_IP_ST_CD); 
			logger.debug("COMPANY_IP_TP_CD >> "+COMPANY_IP_TP_CD); 
			ps.setString(index++,DEFAULT_VLD_TO_DT); 
			ps.setString(index++,COMPANY_IP_ST_CD); 
			ps.setString(index++,COMPANY_IP_TP_CD); 
			ps.setString(index++,COMPANY_NAME); 
			ps.setString(index++,COMPANY_TITLE); 
			rs = ps.executeQuery(); 
			
			String companyCisNo = null; 
			int cntFoundCompany = 0; 
			while(rs.next()){
				companyCisNo = FormatUtil.trim(rs.getString("IP_ID")); 
				cntFoundCompany++; 									
			}	
			if(cntFoundCompany>1){
				dihResult.setStatusCode(ResponseData.BUSINESS_EXCEPTION);
				dihResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
						, ErrorData.ErrorType.DATA_INCORRECT,MessageErrorUtil.getText("ERROR_COMPANY_NAME_COUPLE")));
			}else if(!Util.empty(companyCisNo)){
				AddressDataM address =   personalInfo.getAddress(ADDRESS_TYPE_WORK); 
				if(Util.empty(address)){
					String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK); 
					address = new AddressDataM(); 
					address.setAddressId(addressId); 
				}
				personalInfo.setCompanyCISId(companyCisNo); 
				String refId = CompareSensitiveFieldUtil.getPersonalRefId(personalInfo); 
				String refLevel = CompareDataM.RefLevel.PERSONAL; 
				logger.debug("refId >> "+refId); 
				logger.debug("refLevel >> "+refLevel); 
				createComparisonField("COMPANY_CIS_ID",refId,refLevel,companyCisNo,comparisonFields,address.getAddressId(),CompareDataM.UniqueLevel.ADDRESS,personalInfo); 
				dihResult.setStatusCode(ResponseData.SUCCESS);
			}else{
				dihResult.setStatusCode(ResponseData.SUCCESS); 
			}
			
			
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		}finally{
			try{
				closeConnection(conn, rs, ps); 
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return dihResult; 
	}
	public BigDecimal getShareholderPercent(PersonalInfoDataM personalInfo)throws ApplicationException {
		logger.debug("getShareholderPercent....."); 	
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		String companyOrgIdBol = personalInfo.getCompanyOrgIdBol();
		String idNo = personalInfo.getIdno();
		logger.debug("companyOrgIdBol : "+companyOrgIdBol); 
		logger.debug("idNo : "+idNo); 
		if(Util.empty(companyOrgIdBol)||Util.empty(idNo))return null;
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			SQL.append(" SELECT SHR.SHR_PCT "); 
			SQL.append(" FROM IP_SHR.V_BOL_ORG_X_SHRHLR SHR "); 
			SQL.append(" LEFT OUTER JOIN IP_SHR.V_BOL_IDV IDV "); 
			SQL.append(" ON SHR.SHRHLR_ID = IDV.PSN_ID "); 
			SQL.append(" WHERE ORG_ID = ? AND IDENT_NO =?"); //-- ORIG_PERSONAL_INFO.COMPANY_ORG_ID_BOL
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,companyOrgIdBol); 	
			ps.setString(index++,idNo); 	
			rs = ps.executeQuery(); 
			if (rs.next()) {
				BigDecimal SHR_PCT = rs.getBigDecimal("SHR_PCT"); 
				logger.debug("SHR_PCT : "+SHR_PCT); 
				return SHR_PCT; 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return null; 
	}
	
	@Override
	public CardLinkDataM getCardLinkInfoENCPT(String CARD_NUMBER)throws ApplicationException {
		logger.debug("setCardRequestInfo..."); 
		CardLinkDataM cardLink = new CardLinkDataM(); 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			HashMap<String, String> hCondition= getCardLinkInfo(CARD_NUMBER,cardLink); 
			if(null!=hCondition && !hCondition.isEmpty()){
//				SQL.append(" SELECT  C.MBL_NO, C.EMAIL_ADR, C.NM_TH, B.IP_ID, C.STMT_ADR_LINE1, C.STMT_ADR_LINE2, C.STMT_ADR_LINE3, C.MAIN_ZIP_CD "); 
//				SQL.append(" FROM IP_SHR.VL_AR_X_IP_REL B "); 
//				SQL.append(" INNER JOIN IP_SHR.VC_CC_CST C ON C.CC_CST_NO = B.AR_ADL_INF1 AND C.CC_CST_ORG_NO = TRIM(B.AR_ADL_INF2)  "); 
//				SQL.append(" WHERE  B.AR_X_IP_REL_TP_CD = ? ");   //DEFAULT_AR_X_IP_REL_TP_CD=-1
//				SQL.append(" AND B.AR_X_IP_REL_ST_CD = ?  "); //DEFAULT_AR_X_IP_REL_ST_CD=1
//				SQL.append(" AND B.CIS_SRC_STM_CD = ?  ");  //DEFAULT_CIS_SRC_STM_CD=9
//				SQL.append(" AND B.VLD_TO_DT = ?  ");  //DEFAULT_VLD_TO_DT=9999-12-31 
//				SQL.append(" AND B.AR_ADL_INF1 = ? ");  //A.MAIN_CC_CST_NO
//				SQL.append(" AND TRIM(B.AR_ADL_INF2) = ?  ");  //A.MAIN_CC_CST_ORG_NO,
				SQL.append(" SELECT A.MBL_NO, A.EMAIL_ADR, A.NM_TH, A.IP_ID ");
				SQL.append("	, A.STMT_ADR_LINE1, A.STMT_ADR_LINE2, A.STMT_ADR_LINE3, A.MAIN_ZIP_CD ");
				SQL.append(" FROM ");
				SQL.append(" ( ");
				SQL.append(" 	SELECT  ROW_NUMBER() OVER(ORDER BY B.VLD_FM_DT DESC) AS RN, C.MBL_NO, C.EMAIL_ADR, C.NM_TH, B.IP_ID  ");
				SQL.append("		, C.STMT_ADR_LINE1, C.STMT_ADR_LINE2, C.STMT_ADR_LINE3, C.MAIN_ZIP_CD	");
				SQL.append(" 	FROM IP_SHR.VL_AR_X_IP_REL B ");
				SQL.append(" 	INNER JOIN IP_SHR.VC_CC_CST C ON C.CC_CST_NO = B.AR_ADL_INF1 AND C.CC_CST_ORG_NO = TRIM(B.AR_ADL_INF2)   ");
				SQL.append(" 	WHERE  B.AR_X_IP_REL_TP_CD = ? ");   	//DEFAULT_AR_X_IP_REL_TP_CD=-1
				SQL.append(" 	AND B.AR_X_IP_REL_ST_CD = ?  "); 		//DEFAULT_AR_X_IP_REL_ST_CD=1
				SQL.append(" 	AND B.CIS_SRC_STM_CD = ?  ");  			//DEFAULT_CIS_SRC_STM_CD=9
				SQL.append(" 	AND B.VLD_TO_DT = ?  ");  				//DEFAULT_VLD_TO_DT=9999-12-31 
				SQL.append(" 	AND B.AR_ADL_INF1 = ? ");  				//A.MAIN_CC_CST_NO
				SQL.append(" 	AND TRIM(B.AR_ADL_INF2) = ?  ");  		//A.MAIN_CC_CST_ORG_NO,
				SQL.append(" ) A ");
				SQL.append(" WHERE RN = 1 ");
				
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,DEFAULT_AR_X_IP_REL_TP_CD); 
			ps.setString(index++,DEFAULT_AR_X_IP_REL_ST_CD); 
			ps.setString(index++,DEFAULT_CIS_SRC_STM_CD); 
			ps.setString(index++,DEFAULT_VLD_TO_DT); 
			logger.debug("Cardlink cusno >> "+hCondition.get("CC_CST_NO")); 
			ps.setString(index++,hCondition.get("CC_CST_NO")); 
			ps.setString(index++,hCondition.get("CC_CST_ORG_NO")); 
			
			rs = ps.executeQuery(); 
			if (rs.next()) {
				cardLink.setCisNo(FormatUtil.trim(rs.getString("IP_ID"))); 
				cardLink.setPhoneNo(FormatUtil.trim(rs.getString("MBL_NO"))); 
				cardLink.setEmail(FormatUtil.trim(rs.getString("EMAIL_ADR"))); 
				cardLink.setMainCardHolderName(FormatUtil.trim(rs.getString("NM_TH"))); 
				cardLink.setAddressLine1(FormatUtil.trim(rs.getString("STMT_ADR_LINE1")));
				cardLink.setAddressLine2(FormatUtil.trim(rs.getString("STMT_ADR_LINE2")));
				cardLink.setAddressLine3(FormatUtil.trim(rs.getString("STMT_ADR_LINE3")));
				cardLink.setMainZipCode(FormatUtil.trim(rs.getString("MAIN_ZIP_CD")));
				if(BORROWER.equals(cardLink.getApplicationType())){
					cardLink.setMainCis(cardLink.getCisNo()); 
				}
			}
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return cardLink; 
	}
	
	
	private  HashMap<String, String> getCardLinkInfo(String CARD_NUMBER,CardLinkDataM cardLink)throws ApplicationException {
		logger.debug("setCardRequestInfo..."); 
		HashMap<String, String> hCondition = new HashMap<String, String>(); 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			SQL.append(" SELECT  A.MAIN_CC_CST_NO, A.MAIN_CC_CST_ORG_NO, A.CARD_NO_ENCPT, "); 
			SQL.append(" A.CARD_ORG_NO, A.CARD_NO_MASK, A.CC_CST_NO,  A.ALT_CC_CST_NO, A.ALT_CC_CST_ORG_NO, A.CC_CST_ORG_NO  ");            
            SQL.append(" FROM AR_SHR.VC_CC_CARD A "); 
            SQL.append(" WHERE A.CARD_NO_ENCPT = ? ");   //Card no form page
			logger.debug("SQL >> "+SQL); 
			logger.debug("CARD_NUMBER >> "+CARD_NUMBER); 
			ps = conn.prepareStatement(SQL.toString()); 

			ps.setString(1,CARD_NUMBER); 
			rs = ps.executeQuery(); 
			if (rs.next()) {
				cardLink.setCardNo(FormatUtil.trim(rs.getString("CARD_NO_ENCPT"))); 
				cardLink.setCardOrgNo(FormatUtil.trim(rs.getString("CARD_ORG_NO"))); 
				cardLink.setCardNoMark(FormatUtil.trim(rs.getString("CARD_NO_MASK"))); 
				String realCard = FormatUtil.trim(rs.getString("CC_CST_NO")); 
				String mainCard = FormatUtil.trim(rs.getString("MAIN_CC_CST_NO")); 
				String mainCardOrgdNo = FormatUtil.trim(rs.getString("MAIN_CC_CST_ORG_NO")); 
				String subCard = FormatUtil.trim(rs.getString("ALT_CC_CST_NO")); 
				String subCardNo = FormatUtil.trim(rs.getString("ALT_CC_CST_ORG_NO")); 
				if(!Util.empty(mainCard) && mainCard.equals(realCard)){
					cardLink.setApplicationType(BORROWER); 
					cardLink.setApplicationNo(subCardNo); 
				}else if(!Util.empty(subCard) && subCard.equals(realCard)){
					cardLink.setApplicationType(SUPPLEMENTARY); 		
					cardLink.setApplicationNo(mainCardOrgdNo); 	
					HashMap<String,String> result = getCisMainCard(mainCard,mainCardOrgdNo); 
					String mainCis = result.get("IP_ID"); 
					String mianCardNo = result.get("CARD_NO_ENCPT"); 
					cardLink.setMainCis(mainCis); 
					cardLink.setMainCardNo(mianCardNo); 
					cardLink.setCardLinkCustNo(realCard);
				}
				
				hCondition.put("MAIN_CC_CST_NO", mainCard); 
				hCondition.put("MAIN_CC_CST_ORG_NO", mainCardOrgdNo); 
				hCondition.put("CC_CST_NO", realCard); 
				hCondition.put("CC_CST_ORG_NO", FormatUtil.trim(rs.getString("CC_CST_ORG_NO"))); 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return hCondition; 
	}
	@Override
	public String getKbankBranchData(String branchCode, String fieldDataName)throws ApplicationException {
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			SQL.append(" SELECT "+fieldDataName); 
			SQL.append(" FROM IP_SHR.VC_RC_CD "); 
			SQL.append(" WHERE KBNK_BR_NO = ?  AND  EFF_ST_CD = ?"); 
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,branchCode); 	
			ps.setString(index++,DEFUALT_EFF_ST_CD); 	
			rs = ps.executeQuery(); 
			
			if (rs.next()) {
				String data = FormatUtil.trim(rs.getString(fieldDataName)); 
				logger.debug(fieldDataName+" : "+data); 
				return data; 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return null; 
	}
	@Override
	public KbankBranchInfoDataM getKbankBranchData(String branchCode)throws ApplicationException {
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		KbankBranchInfoDataM kbankBranchInfo = new KbankBranchInfoDataM(); 
		kbankBranchInfo.setBranchNo(branchCode); 
		try{
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			SQL.append(" SELECT TH_CNTR_NM,KBNK_RGON_CD,KBNK_ZON_NO FROM IP_SHR.VC_RC_CD WHERE KBNK_BR_NO = ?  AND  EFF_ST_CD = ?"); 
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,branchCode); 	
			ps.setString(index++,DEFUALT_EFF_ST_CD); 	
			rs = ps.executeQuery(); 			
			if(rs.next()){
				kbankBranchInfo.setBranchName(FormatUtil.trim(rs.getString("TH_CNTR_NM"))); 
				kbankBranchInfo.setBranchRegion(FormatUtil.trim(rs.getString("KBNK_RGON_CD"))); 
				kbankBranchInfo.setBranchZone(FormatUtil.trim(rs.getString("KBNK_ZON_NO"))); 
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		}finally{
			try{
				closeConnection(conn, rs, ps); 
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return kbankBranchInfo; 
	}
	@Override
	public KbankBranchInfoDataM getBranchInfoByRC_CD(String rcCode)throws ApplicationException {
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		KbankBranchInfoDataM kbankBranchInfo = new KbankBranchInfoDataM(); 
		try{
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			SQL.append(" SELECT TH_CNTR_NM,KBNK_RGON_CD,KBNK_ZON_NO, KBNK_BR_NO FROM IP_SHR.VC_RC_CD WHERE RC_CD = ?  AND  EFF_ST_CD = ?"); 
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,rcCode); 	
			ps.setString(index++,DEFUALT_EFF_ST_CD); 	
			rs = ps.executeQuery(); 			
			if(rs.next()){
				kbankBranchInfo.setBranchName(FormatUtil.trim(rs.getString("TH_CNTR_NM"))); 
				kbankBranchInfo.setBranchRegion(FormatUtil.trim(rs.getString("KBNK_RGON_CD"))); 
				kbankBranchInfo.setBranchZone(FormatUtil.trim(rs.getString("KBNK_ZON_NO"))); 
				kbankBranchInfo.setBranchNo(FormatUtil.trim(rs.getString("KBNK_BR_NO"))); 
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		}finally{
			try{
				closeConnection(conn, rs, ps); 
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return kbankBranchInfo; 
	}
	@Override
	public KbankSaleInfoDataM getKbankSaleInfo(String saleId) throws ApplicationException {
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		KbankSaleInfoDataM kbankSaleInfo = new KbankSaleInfoDataM(); 
		kbankSaleInfo.setSaleId(saleId); 
		try{
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
				SQL.append(" SELECT EMP_ID, "); 
				SQL.append("     TH_FRST_NM||' '||TH_SURNM SALE_NAME, "); 
				SQL.append("     KBNK_BR_NO, "); 
				SQL.append("     TH_DEPT_NM, "); 
				SQL.append("     EN_PRN_DEPT_NM, "); 
				SQL.append("     DECODE(MBL_PH1,NULL,MBL_PH2,MBL_PH1) AS MOBILE, "); 
				SQL.append("     OFFC_PH1, "); 
				SQL.append("     RGON_CD, "); 
				SQL.append("     ZON_CD, "); 
				SQL.append("     DEPT_ID, "); 
				SQL.append("     TH_BSN_LINE_DEPT_NM, "); 
				SQL.append("     EMP_ST_CD, "); 						
				SQL.append("     FAX "); 
				SQL.append(" FROM IP_SHR.VC_EMP "); 
				SQL.append(" WHERE EMP_ID = ? "); 
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,saleId); 	
			rs = ps.executeQuery(); 			
			if(rs.next()){
				kbankSaleInfo.setFoundResult(true); 
				kbankSaleInfo.setSaleName(FormatUtil.trim(rs.getString("SALE_NAME"))); 
				kbankSaleInfo.setBranchCode(FormatUtil.trim(rs.getString("KBNK_BR_NO"))); 
				kbankSaleInfo.setThDeptName(FormatUtil.trim(rs.getString("TH_DEPT_NM"))); 
				kbankSaleInfo.setEnDeptName(FormatUtil.trim(rs.getString("EN_PRN_DEPT_NM"))); 
				kbankSaleInfo.setMobileNo(FormatUtil.trim(rs.getString("MOBILE"))); 
				kbankSaleInfo.setOfficePhone(FormatUtil.trim(rs.getString("OFFC_PH1"))); 
				kbankSaleInfo.setRegion(FormatUtil.trim(rs.getString("RGON_CD"))); 
				kbankSaleInfo.setZone(FormatUtil.trim(rs.getString("ZON_CD"))); 
				kbankSaleInfo.setRcCode(FormatUtil.trim(rs.getString("DEPT_ID"))); 
				kbankSaleInfo.setThBnsDeptName(FormatUtil.trim(rs.getString("TH_BSN_LINE_DEPT_NM"))); 
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		}finally{
			try{
				closeConnection(conn, rs, ps); 
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return kbankSaleInfo; 
	}
	@Override
	public boolean existUserNo(String userNO) throws ApplicationException {
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		boolean isExists = false; 
		try{
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder sql = new StringBuilder(); 
			sql.append("SELECT 1 FROM IP_SHR.VC_EMP "); 
			sql.append("WHERE EMP_ID = ? "); 
			
			String dSql = String.valueOf(sql); 
			logger.debug("Sql=" + dSql); 
			ps = conn.prepareStatement(dSql); 
			rs = null; 
			ps.setString(1, userNO); 
			rs = ps.executeQuery(); 					
			
			if(rs.next()){
				isExists = true; 
			}
		}catch(Exception e){
			logger.fatal("ERROR",e); 
			throw new ApplicationException(e.getLocalizedMessage()); 
		}finally{
			try{
				closeConnection(conn, rs, ps); 
			}catch(Exception e){
				logger.fatal("ERROR",e); 
				throw new ApplicationException(e.getLocalizedMessage()); 
			}
		}
		return isExists; 
	}
	@Override
	public DIHAccountResult getAccountData(String ACCOUNT_NO,String TYPE_LIMIT) throws ApplicationException {
		DIHAccountResult dihAccountResult = new DIHAccountResult();
		try{
		String ACCOUNT_TYPE_LOAN = SystemConstant.getConstant("ACCOUNT_TYPE_LOAN");
		String ACCOUNT_TYPE_CURRENT = SystemConstant.getConstant("ACCOUNT_TYPE_CURRENT");
		if(ACCOUNT_TYPE_LOAN.equals(TYPE_LIMIT)){
			dihAccountResult = getAccountTypeLoan(ACCOUNT_NO);
		}else if(ACCOUNT_TYPE_CURRENT.equals(TYPE_LIMIT)){
			dihAccountResult = getAccountTypeCurrent(ACCOUNT_NO);
		}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			dihAccountResult.setStatusCode(ResponseData.SYSTEM_EXCEPTION);
			dihAccountResult.setErrorData(ErrorController.error(ResponseData.SystemType.DIH
					, ErrorData.ErrorType.CONNECTION_ERROR, e));
		}
		
		return dihAccountResult;
	}
	
	@Override
	public DIHAccountResult getAccountTypeCurrent(String ACCOUNT_NO) throws ApplicationException {
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		String dSql = null;
		int index = 1;
		DIHAccountResult dihAccountResult = new DIHAccountResult();
		dihAccountResult.setAccountNo(ACCOUNT_NO);
		try{
			conn = getConnection(OrigServiceLocator.DIH);	
			StringBuilder sql = new StringBuilder();	
				sql.append(" SELECT OPN_DT AS OPEN_DATE, ");
				sql.append(" AR_NM_TH AS ACCOUNT_NAME, ");
				sql.append(" AR_LCS_TP_CD AS STATUS ");
				sql.append(" FROM AR_SHR.VC_CRN_DEP_AR ");
				sql.append(" WHERE OD_LMT_F = ? AND AR_ID  = ? ");
			dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, SystemConstant.getConstant("OD_LMT_F"));
			logger.debug("ACCOUNT NO >>"+ACCOUNT_NO);
			logger.debug("Sql=" + dSql);
			ps.setString(index++, ACCOUNT_NO); 
			rs = ps.executeQuery();
			if(rs.next()){
				dihAccountResult.setFoundAccount(true);
				dihAccountResult.setAccountDate(rs.getDate("OPEN_DATE"));
				dihAccountResult.setAccountName(rs.getString("ACCOUNT_NAME"));
				dihAccountResult.setAccountStatus(rs.getString("STATUS"));
			}
		}catch(Exception e){
			logger.fatal("ERROR",e); 
			throw new ApplicationException(e.getLocalizedMessage()); 
		}finally{
			try{
				closeConnection(conn, rs, ps); 
			}catch(Exception e){
				logger.fatal("ERROR",e); 
				throw new ApplicationException(e.getLocalizedMessage()); 
			}
		}
	
		return dihAccountResult;
	}
	
	@Override
	public DIHAccountResult getAccountTypeTCBLoan(String ACCOUNT_NO) throws ApplicationException {
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		String dSql = null;
		DIHAccountResult dihAccountResult = new DIHAccountResult();
		dihAccountResult.setAccountNo(ACCOUNT_NO);
		try{
			conn = getConnection(OrigServiceLocator.DIH);
			StringBuilder sql = new StringBuilder();	
			/**			
			sql.append(" SELECT TRIM(AR_NM_TH) AS ARRNAME, AR_ID AS ARRNO,OPN_DT AS OPENDATE, ");
			sql.append(" TRIM(AR_LCS_TP_CD) AS ARRSTATUS, OTSND_BAL AS OUTSTANDINGBAL, ");
			sql.append(" TRIM(COA_PD_FTR_CD) AS COAPRODUCTCODE ");
			sql.append(" FROM AR_SHR.V_FNC_SVC_AR ");
			sql.append(" WHERE AR_ID  = ? ");
			*/
			sql.append(" SELECT OPENDATE,ARRNAME,ARRSTATUS ");
			sql.append(" FROM ( ");
			sql.append(" 	SELECT ROW_NUMBER() OVER(PARTITION BY AR_ID ORDER BY POS_DT DESC) AS RN, ");
			sql.append(" 	OPN_DT AS OPENDATE, ");
			sql.append(" 	TRIM(AR_NM_TH) AS ARRNAME, ");
			sql.append(" 	TRIM(AR_LCS_TP_CD) AS ARRSTATUS ");
			sql.append(" 	FROM AR_SHR.V_FNC_SVC_AR  ");
			sql.append(" 	WHERE AR_ID = ? ");
			sql.append(" ) A ");
			sql.append(" WHERE A.RN = 1 ");
			
			dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, ACCOUNT_NO);
			rs = ps.executeQuery();
			if(rs.next()){
				String OPENDATE = FormatUtil.trim(rs.getString("OPENDATE")); 
				Date OPENDATE_BUNDLE = FormatUtil.toDate(OPENDATE,FormatUtil.EN,"yyyy-MM-dd"); 
				dihAccountResult.setFoundAccount(true);
				dihAccountResult.setAccountDate(OPENDATE_BUNDLE);
				dihAccountResult.setAccountName(rs.getString("ARRNAME"));
				dihAccountResult.setAccountStatus(rs.getString("ARRSTATUS"));
			}		
		}catch(Exception e){
			logger.fatal("ERROR",e); 
			throw new ApplicationException(e.getLocalizedMessage()); 
		}finally{
			try{
				closeConnection(conn, rs, ps); 
			}catch(Exception e){
				logger.fatal("ERROR",e); 
				throw new ApplicationException(e.getLocalizedMessage()); 
			}
		}
		return dihAccountResult;
	}
	@Override
	public DIHAccountResult getAccountTypeSAFE(String ACCOUNT_NO) throws ApplicationException {
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		String dSql = null;
		int index = 1;	
		DIHAccountResult dihAccountResult = new DIHAccountResult();
		dihAccountResult.setAccountNo(ACCOUNT_NO);
		try{
			conn = getConnection(OrigServiceLocator.DIH);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT MAX(lnsub.OPN_DT) MAX_OPEN_DATE,safe.AR_NM_EN ");
			sql.append(" FROM AR_SHR.VC_SAFE_LOAN_SUB lnsub JOIN AR_SHR.V_SAFE_LOAN_AR safe ON safe.AR_ID = lnsub.AR_ID ");
			sql.append(" WHERE lnsub.AR_ID  = ? ");
			sql.append(" AND COALESCE(TRIM(lnsub.CLS_DT),' ') = ' ' ");
			sql.append(" GROUP BY safe.AR_NM_EN ");
			dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, ACCOUNT_NO);
			rs = ps.executeQuery();
			if(rs.next()){
				String OPENDATE = FormatUtil.trim(rs.getString("MAX_OPEN_DATE")); 
				Date OPENDATE_BUNDLE = FormatUtil.toDate(OPENDATE,FormatUtil.EN,"yyyy-MM-dd"); 
				dihAccountResult.setFoundAccount(true);
				dihAccountResult.setAccountDate(OPENDATE_BUNDLE);
				dihAccountResult.setAccountName(rs.getString("AR_NM_EN"));
			}		
		}catch(Exception e){
			logger.fatal("ERROR",e); 
			throw new ApplicationException(e.getLocalizedMessage()); 
		}finally{
			try{
				closeConnection(conn, rs, ps); 
			}catch(Exception e){
				logger.fatal("ERROR",e); 
				throw new ApplicationException(e.getLocalizedMessage()); 
			}
		}
		return dihAccountResult;
	}
	
	@Override
	public DIHAccountResult getAccountTypeLoan(String ACCOUNT_NO) throws ApplicationException {
		String SOURCE = "";
		DIHAccountResult dihAccountResult = new DIHAccountResult();
		dihAccountResult.setAccountNo(ACCOUNT_NO);
		try{
			SOURCE = getAccountSource(ACCOUNT_NO);
			
			logger.debug("SOURCE : "+SOURCE);
			if(!Util.empty(SOURCE) && (ACCOUNT_TYPE_PROFILE.equals(SOURCE) || ACCOUNT_TYPE_TCB.equals(SOURCE))){
				dihAccountResult = getAccountTypeTCBLoan(ACCOUNT_NO);
			}else if(!Util.empty(SOURCE) && ACCOUNT_TYPE_SAFE.equals(SOURCE)){
				dihAccountResult = getAccountTypeSAFE(ACCOUNT_NO);
			}
			if(!Util.empty(SOURCE))dihAccountResult.setSourceAccount(SOURCE);
		}catch(Exception e){
			logger.fatal("ERROR",e); 
			throw new ApplicationException(e.getLocalizedMessage()); 
		}
		return dihAccountResult;
	}
	
	@Override
	public String getAccountSource(String ACCOUNT_NO) throws Exception {
		String SOURCE = "";
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		String dSql = null;
		try{
			int index = 1;
			StringBuilder sql = new StringBuilder();
				sql.append(" SELECT IP_ID AS CIS_NO, CIS_SRC_STM_CD AS SOURCE ");
				sql.append(" FROM IP_SHR.VL_AR_X_IP_REL ");
				sql.append(" WHERE AR_ADL_INF2 = ? AND AR_X_IP_REL_TP_CD = ? ");
				sql.append(" AND AR_X_IP_REL_ST_CD = ? ");
				sql.append(" AND VLD_TO_DT = ? ");
				sql.append(" AND CIS_SRC_STM_CD IN ( ? , ? , ? ) ");
			dSql = String.valueOf(sql);		
			conn = getConnection(OrigServiceLocator.DIH);
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, ACCOUNT_NO); 
			ps.setString(index++, DEFAULT_AR_X_IP_REL_TP_CD);
			ps.setString(index++, DEFAULT_AR_X_IP_REL_ST_CD);
			ps.setString(index++, DEFAULT_VLD_TO_DT);
			ps.setString(index++, ACCOUNT_TYPE_PROFILE);	
			ps.setString(index++, ACCOUNT_TYPE_TCB);	
			ps.setString(index++, ACCOUNT_TYPE_SAFE);	
			logger.debug("Sql=" + dSql);
			rs = ps.executeQuery();
			if(rs.next()){
				SOURCE = rs.getString("SOURCE");
				if(!Util.empty(SOURCE)){
					SOURCE = SOURCE.trim();
				}
			}
			logger.debug("Account Source : "+SOURCE);
		}catch(Exception e){
			logger.fatal("ERROR",e); 
			throw new ApplicationException(e.getLocalizedMessage()); 
		}finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return SOURCE;
	}
	
	@Override
	public String getKBankBranchNo(String rcCode) throws ApplicationException {
		logger.debug("getKBankBranchNo....."); 	
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			SQL.append(" SELECT RC.KBNK_BR_NO  FROM IP_SHR.VC_RC_CD RC WHERE RC.RC_CD = ? "); 
			SQL.append(" AND RC.KBNK_AREA_TP_CD IN ('3', '4', '5') ");  // BRANCH (NOT INCLUDING HUB, AND OTHER)
			SQL.append(" AND RC.RC_TP_CD = 'O' ");  //FILTER GET ONLY PHYSICAL BRANCH (NOT INCLUDING DUMMY, MERGE BRANCH) 
			SQL.append(" AND RC.EFF_ST_CD = 'A'  "); 
			SQL.append(" AND RC.EFF_DT = (SELECT MAX(Z.EFF_DT)  "); 
			SQL.append("     FROM IP_SHR.VC_RC_CD Z  "); 
			SQL.append("     WHERE Z.EFF_DT <= CURRENT_DATE AND RC.RC_CD = Z.RC_CD AND RC.RC_TP_CD = Z.RC_TP_CD AND RC.KBNK_BR_NO = Z.KBNK_BR_NO) "); 
			
			
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,rcCode); 	
			rs = ps.executeQuery(); 
			
			if (rs.next()) {
				String KBNK_BR_NO = FormatUtil.trim(rs.getString("KBNK_BR_NO")); 
				logger.debug("KBNK_BR_NO : "+KBNK_BR_NO); 
				return KBNK_BR_NO; 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return null; 
	}
	
	@Override
	public CISCustomerDataM selectVC_IPData(String CID_TYPE, String ID_NO)throws Exception {
		CISCustomerDataM   cisCustomer = null; 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			SQL.append(" SELECT IP_ID, YNGST_DPND_BRTH_DT, VLD_TO_DT , VLD_FM_DT, ");
			SQL.append(" TH_TTL, TH_SURNM,TH_MDL_NM, TH_FRST_NM, TEL_OFFC_NO2, ");
			SQL.append(" TEL_OFFC_NO1, TEL_OFFC_EXN_NO2, TEL_OFFC_EXN_NO1, TEL_MBL_NO2, ");
			SQL.append(" TEL_MBL_NO1, TEL_HM_NO2, TEL_HM_NO1, TEL_HM_EXN_NO2, TEL_HM_EXN_NO1, ");
			SQL.append(" SRC_STM_ID, RLG_CD, RACE_CD, PRVN_F, PROF_CD,  ");
			SQL.append(" PRIM_SUB_SEG_CD, PRIM_SEG_CD, PREF_CTC_TM, PREF_CTC_MTH_CD, POS_CD, ");
			SQL.append(" ORG_RGST_CPTL, ORG_REV_SEG_CD, ORG_PROF_F, ORG_LGLTY_END_DT, ORG_LGL_BSN_F, ");
			SQL.append(" ORG_INCM_CASH_F, OLDST_DPND_BRTH_DT, OCP_DT, OCP_CD, NO_OF_EMP, ");
			SQL.append(" NO_OF_DPND_CHL_DT, NO_OF_DPND_CHL, NAT_CD, MRG_ST_CD, MAR_ST_CD, ");
			SQL.append(" LAST_VRSN_F, KBNK_IDY_CL_CD, ISSU_DT, IP_TP_CD, IP_TAX_ID, IP_ST_CD, ");
			SQL.append(" IP_PERF_ST_CD, IP_LGL_STC_TP_CD, IDV_INCM_SEG_CD, IDENT_NO, GOVT_ISSUR, GND_CD, ");
			SQL.append(" FAV_MEDIA_OTHR_INF, FAV_MEDIA_CD, FAM_INCM_SEG_CD, EXP_DT, EN_TTL, EN_SURNM, ");
			SQL.append(" EN_MDL_NM, EN_FRST_NM, EMPR_NM, ED_INST_OTHR_INF, ED_INST_CD, DUAL_SUB_SEG_CD, ");
			SQL.append(" DUAL_SEG_DT, DUAL_SEG_CD, DOMC_BR_NO, DOC_ITM_CD, DEATH_F, DEATH_DT, ");
			SQL.append(" CTF_TP_CD, CST_REL_TP_CD2, CST_REL_TP_CD1, CST_REFR_NM2, CST_REFR_NM1, ");
			SQL.append(" CST_LKE, CST_DSLKE, CONSND_SRC_STM_CD, CONSND_F, CONSND_DT, ");
			SQL.append(" CO_IDY_CL_CD, BSN_DSC, BRTH_ESTB_DT, AST_EXCLD_LAND, ACT_STRT_DT, ");
			SQL.append(" ACT_END_DT ");
			SQL.append(" FROM IP_SHR.VC_IP  ");
			SQL.append(" WHERE VLD_TO_DT = ? "); //--Customer not delete :9999-12-31 23:59:59
			SQL.append(" AND LAST_VRSN_F = ? "); // --Customer Current Record :1
			SQL.append(" AND IP_ST_CD = ? "); //--Customer Active :1
			SQL.append(" AND DOC_ITM_CD = ? "); 
			SQL.append(" AND IDENT_NO = ? "); 
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,DEFAULT_VLD_TO_DT); 
			ps.setString(index++,DEFAULT_LAST_VRSN_F); 
			ps.setString(index++,DEFAULT_IP_ST_CD); 
			CID_TYPE = ListBoxControl.getName(FIELD_ID_CID_TYPE,"CHOICE_NO",CID_TYPE,"MAPPING4"); 
			logger.debug("MAPPING CID_TYPE >> "+CID_TYPE); 
			ps.setString(index++,CID_TYPE); 
			ps.setString(index++,ID_NO); 	
			rs = ps.executeQuery(); 
			if (rs.next()) {
				cisCustomer = new CISCustomerDataM();
				cisCustomer.setIpId(FormatUtil.trim(rs.getString("IP_ID")));
				cisCustomer.setVldFmDt(FormatUtil.trim(rs.getString("VLD_FM_DT")));
				cisCustomer.setActStrtDt(FormatUtil.trim(rs.getString("ACT_STRT_DT")));
				cisCustomer.setActEndDt(FormatUtil.trim(rs.getString("ACT_END_DT")));
				cisCustomer.setPrvnF(FormatUtil.trim(rs.getString("PRVN_F")));
				cisCustomer.setMrgStCd(FormatUtil.trim(rs.getString("MRG_ST_CD")));
				cisCustomer.setIpStCd(FormatUtil.trim(rs.getString("IP_ST_CD")));
				cisCustomer.setIpTpCd(FormatUtil.trim(rs.getString("IP_TP_CD")));
				cisCustomer.setIpLglStcTpCd(FormatUtil.trim(rs.getString("IP_LGL_STC_TP_CD")));
				cisCustomer.setBrthEstbDt(FormatUtil.trim(rs.getString("BRTH_ESTB_DT")));
				cisCustomer.setDocItmCd(FormatUtil.trim(rs.getString("DOC_ITM_CD")));
				cisCustomer.setIpTaxId(FormatUtil.trim(rs.getString("IP_TAX_ID")));
				cisCustomer.setIdentNo(FormatUtil.trim(rs.getString("IDENT_NO")));
				cisCustomer.setIssuDt(FormatUtil.trim(rs.getString("ISSU_DT")));
				cisCustomer.setExpDt(FormatUtil.trim(rs.getString("EXP_DT")));
				cisCustomer.setGovtIssur(FormatUtil.trim(rs.getString("GOVT_ISSUR")));
				cisCustomer.setDomcBrNo(FormatUtil.trim(rs.getString("DOMC_BR_NO")));
				cisCustomer.setIpPerfStCd(FormatUtil.trim(rs.getString("IP_PERF_ST_CD")));
				cisCustomer.setConsndDt(FormatUtil.trim(rs.getString("CONSND_DT")));
				cisCustomer.setConsndF(FormatUtil.trim(rs.getString("CONSND_F")));
				cisCustomer.setConsndSrcStmCd(FormatUtil.trim(rs.getString("CONSND_SRC_STM_CD")));
				cisCustomer.setCstLke(FormatUtil.trim(rs.getString("CST_LKE")));
				cisCustomer.setCstDslke(FormatUtil.trim(rs.getString("CST_DSLKE")));
				cisCustomer.setFavMediaCd(FormatUtil.trim(rs.getString("FAV_MEDIA_CD")));
				cisCustomer.setFavMediaOthrInf(FormatUtil.trim(rs.getString("FAV_MEDIA_OTHR_INF")));
				cisCustomer.setPrefCtcMthCd(FormatUtil.trim(rs.getString("PREF_CTC_MTH_CD")));
				cisCustomer.setPrefCtcTm(FormatUtil.trim(rs.getString("PREF_CTC_TM")));
				cisCustomer.setAstExcldLand(FormatUtil.trim(rs.getString("AST_EXCLD_LAND")));
				cisCustomer.setThTtl(FormatUtil.trim(rs.getString("TH_TTL")));
				cisCustomer.setThFrstNm(FormatUtil.trim(rs.getString("TH_FRST_NM")));
				cisCustomer.setThMdlNm(FormatUtil.trim(rs.getString("TH_MDL_NM")));
				cisCustomer.setThSurnm(FormatUtil.trim(rs.getString("TH_SURNM")));
				cisCustomer.setEnTtl(FormatUtil.trim(rs.getString("EN_TTL")));
				cisCustomer.setEnFrstNm(FormatUtil.trim(rs.getString("EN_FRST_NM")));
				cisCustomer.setEnMdlNm(FormatUtil.trim(rs.getString("EN_MDL_NM")));
				cisCustomer.setEnSurnm(FormatUtil.trim(rs.getString("EN_SURNM")));
				cisCustomer.setDeathDt(FormatUtil.trim(rs.getString("DEATH_DT")));
				cisCustomer.setDeathF(FormatUtil.trim(rs.getString("DEATH_F")));
				cisCustomer.setGndCd(FormatUtil.trim(rs.getString("GND_CD")));
				cisCustomer.setRlgCd(FormatUtil.trim(rs.getString("RLG_CD")));
				cisCustomer.setNatCd(FormatUtil.trim(rs.getString("NAT_CD")));
				cisCustomer.setRaceCd(FormatUtil.trim(rs.getString("RACE_CD")));
				cisCustomer.setMarStCd(FormatUtil.trim(rs.getString("MAR_ST_CD")));
				cisCustomer.setEdInstCd(FormatUtil.trim(rs.getString("ED_INST_CD")));
				cisCustomer.setEdInstOthrInf(FormatUtil.trim(rs.getString("ED_INST_OTHR_INF")));
				cisCustomer.setCtfTpCd(FormatUtil.trim(rs.getString("CTF_TP_CD")));
				cisCustomer.setOcpCd(FormatUtil.trim(rs.getString("OCP_CD")));
				cisCustomer.setOcpDt(FormatUtil.trim(rs.getString("OCP_DT")));
				cisCustomer.setPosCd(FormatUtil.trim(rs.getString("POS_CD")));
				cisCustomer.setProfCd(FormatUtil.trim(rs.getString("PROF_CD")));
				cisCustomer.setEmprNm(FormatUtil.trim(rs.getString("EMPR_NM")));
				cisCustomer.setIdvIncmSegCd(FormatUtil.trim(rs.getString("IDV_INCM_SEG_CD")));
				cisCustomer.setFamIncmSegCd(FormatUtil.trim(rs.getString("FAM_INCM_SEG_CD")));
				cisCustomer.setYngstDpndBrthDt(FormatUtil.trim(rs.getString("YNGST_DPND_BRTH_DT")));
				cisCustomer.setOldstDpndBrthDt(FormatUtil.trim(rs.getString("OLDST_DPND_BRTH_DT")));
				cisCustomer.setNoOfDpndChl(FormatUtil.trim(rs.getString("NO_OF_DPND_CHL")));
				cisCustomer.setNoOfDpndChlDt(FormatUtil.trim(rs.getString("NO_OF_DPND_CHL_DT")));
				cisCustomer.setOrgLgltyEndDt(FormatUtil.trim(rs.getString("ORG_LGLTY_END_DT")));
				cisCustomer.setNoOfEmp(FormatUtil.trim(rs.getString("NO_OF_EMP")));
				cisCustomer.setOrgRevSegCd(FormatUtil.trim(rs.getString("ORG_REV_SEG_CD")));
				cisCustomer.setOrgRgstCptl(FormatUtil.trim(rs.getString("ORG_RGST_CPTL")));
				cisCustomer.setOrgIncmCashF(FormatUtil.trim(rs.getString("ORG_INCM_CASH_F")));
				cisCustomer.setOrgLglBsnF(FormatUtil.trim(rs.getString("ORG_LGL_BSN_F")));
				cisCustomer.setOrgProfF(FormatUtil.trim(rs.getString("ORG_PROF_F")));
				cisCustomer.setBsnDsc(FormatUtil.trim(rs.getString("BSN_DSC")));
				cisCustomer.setPrimSegCd(FormatUtil.trim(rs.getString("PRIM_SEG_CD")));
				cisCustomer.setPrimSubSegCd(FormatUtil.trim(rs.getString("PRIM_SUB_SEG_CD")));
				cisCustomer.setDualSegCd(FormatUtil.trim(rs.getString("DUAL_SEG_CD")));
				cisCustomer.setDualSubSegCd(FormatUtil.trim(rs.getString("DUAL_SUB_SEG_CD")));
				cisCustomer.setDualSegDt(FormatUtil.trim(rs.getString("DUAL_SEG_DT")));
				cisCustomer.setKbnkIdyClCd(FormatUtil.trim(rs.getString("KBNK_IDY_CL_CD")));
				cisCustomer.setCoIdyClCd(FormatUtil.trim(rs.getString("CO_IDY_CL_CD")));
				cisCustomer.setCstRefrNm1(FormatUtil.trim(rs.getString("CST_REFR_NM1")));
				cisCustomer.setCstRelTpCd1(FormatUtil.trim(rs.getString("CST_REL_TP_CD1")));
				cisCustomer.setTelHmNo1(FormatUtil.trim(rs.getString("TEL_HM_NO1")));
				cisCustomer.setTelHmExnNo1(FormatUtil.trim(rs.getString("TEL_HM_EXN_NO1")));
				cisCustomer.setTelOffcNo1(FormatUtil.trim(rs.getString("TEL_OFFC_NO1")));
				cisCustomer.setTelOffcExnNo1(FormatUtil.trim(rs.getString("TEL_OFFC_EXN_NO1")));
				cisCustomer.setTelMblNo1(FormatUtil.trim(rs.getString("TEL_MBL_NO1")));
				cisCustomer.setCstRefrNm2(FormatUtil.trim(rs.getString("CST_REFR_NM2")));
				cisCustomer.setCstRelTpCd2(FormatUtil.trim(rs.getString("CST_REL_TP_CD2")));
				cisCustomer.setTelHmNo2(FormatUtil.trim(rs.getString("TEL_HM_NO2")));
				cisCustomer.setTelHmExnNo2(FormatUtil.trim(rs.getString("TEL_HM_EXN_NO2")));
				cisCustomer.setTelOffcNo2(FormatUtil.trim(rs.getString("TEL_OFFC_NO2")));
				cisCustomer.setTelOffcExnNo2(FormatUtil.trim(rs.getString("TEL_OFFC_EXN_NO2")));
				cisCustomer.setTelMblNo2(FormatUtil.trim(rs.getString("TEL_MBL_NO2")));
				cisCustomer.setLastVrsnF(FormatUtil.trim(rs.getString("LAST_VRSN_F")));
				cisCustomer.setVldToDt(FormatUtil.trim(rs.getString("VLD_TO_DT")));
				cisCustomer.setSrcStmId(FormatUtil.trim(rs.getString("SRC_STM_ID")));
				cisCustomer.setBrthEstbDate(FormatUtil.trim(rs.getString("BRTH_ESTB_DT")));
				cisCustomer.setVldToDate(FormatUtil.trim(rs.getString("VLD_TO_DT")));
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}	
		return cisCustomer; 
	}
	
	@Override
	public ArrayList<CISelcAddressDataM> selectVC_IP_X_ELC_ADR_RELData(String IP_ID)throws Exception {
		 ArrayList<CISelcAddressDataM>   cisAddressList = null; 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			SQL.append(" SELECT IP_ID, ELC_ADR_ID, VLD_FM_DT, IP_X_ELC_ADR_REL_EFF_DT, IP_X_ELC_ADR_REL_END_DT," );
			SQL.append(" IP_X_ELC_ADR_REL_ST_CD, ELC_ADR_LO_USE_TP_CD, TEL_NO, TEL_EXN_NO, ELC_ADR_DTL," );
			SQL.append(" CTC_TM_RNG_CD, ELC_ADR_USE_TP_CD, PREF_F, PRIM_F, RANK, UNCTC_ST_DT, UNCTC_ST_CD," );
			SQL.append(" UNCTC_ST_INF, UNCTC_RSN_CD, SVC_BR_NO, LAST_VRSN_F, VLD_TO_DT, SRC_STM_ID" );
			SQL.append(" FROM IP_SHR.VC_IP_X_ELC_ADR_REL  ");
			SQL.append(" WHERE IP_ID = ? AND IP_X_ELC_ADR_REL_ST_CD = ? AND VLD_TO_DT = ? ");
			 
			logger.debug("SQL >> "+SQL); 
			logger.debug("IP_ID >> "+IP_ID); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,IP_ID);
			ps.setString(index++,DEFAULT_IP_X_ELC_ADR_REL_ST_CD);
			ps.setString(index++,DEFAULT_VLD_TO_DT);
			rs = ps.executeQuery(); 
			while (rs.next()) {
				if(null==cisAddressList){
					cisAddressList = new ArrayList<CISelcAddressDataM>();
				}
				CISelcAddressDataM cisAddr = new CISelcAddressDataM(); 
				cisAddr.setIpid(FormatUtil.trim(rs.getString("IP_ID")));
				cisAddr.setElcAdrId(FormatUtil.trim(rs.getString("ELC_ADR_ID")));
				cisAddr.setVldFmDt(FormatUtil.trim(rs.getString("VLD_FM_DT")));
				cisAddr.setIpXElcAdrRelEffDt(FormatUtil.trim(rs.getString("IP_X_ELC_ADR_REL_EFF_DT")));
				cisAddr.setIpXElcAdrRelEndDt(FormatUtil.trim(rs.getString("IP_X_ELC_ADR_REL_END_DT")));
				cisAddr.setIpXElcAdrRelStCd(FormatUtil.trim(rs.getString("IP_X_ELC_ADR_REL_ST_CD")));
				cisAddr.setElcAdrLoUseTpCd(FormatUtil.trim(rs.getString("ELC_ADR_LO_USE_TP_CD")));
				cisAddr.setTelNo(FormatUtil.trim(rs.getString("TEL_NO")));
				cisAddr.setTelExnNo(FormatUtil.trim(rs.getString("TEL_EXN_NO")));
				cisAddr.setElcAdrDtl(FormatUtil.trim(rs.getString("ELC_ADR_DTL")));
				cisAddr.setCtcTmRngCd(FormatUtil.trim(rs.getString("CTC_TM_RNG_CD")));
				cisAddr.setElcAdrUseTpCd(FormatUtil.trim(rs.getString("ELC_ADR_USE_TP_CD")));
				cisAddr.setPrefF(FormatUtil.trim(rs.getString("PREF_F")));
				cisAddr.setPrimF(FormatUtil.trim(rs.getString("PRIM_F")));
				cisAddr.setRank(rs.getInt("RANK"));
				cisAddr.setUnctcStDt(FormatUtil.trim(rs.getString("UNCTC_ST_DT")));
				cisAddr.setUnctcStCd(FormatUtil.trim(rs.getString("UNCTC_ST_CD")));
				cisAddr.setUnctcStInf(FormatUtil.trim(rs.getString("UNCTC_ST_INF")));
				cisAddr.setUnctcRsnCd(FormatUtil.trim(rs.getString("UNCTC_RSN_CD")));
				cisAddr.setSvcBrNo(FormatUtil.trim(rs.getString("SVC_BR_NO")));
				cisAddr.setLastVrsnF(FormatUtil.trim(rs.getString("LAST_VRSN_F")));
				cisAddr.setVldToDt(FormatUtil.trim(rs.getString("VLD_TO_DT")));
				cisAddr.setSrcStmId(FormatUtil.trim(rs.getString("SRC_STM_ID")));
				cisAddressList.add(cisAddr);	
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}	
		return cisAddressList; 
	}
	@Override
	public ArrayList<CISAddressDataM> selectVC_IP_X_ADR_RELData(String ipId) throws Exception {
		logger.debug("getCisAddressInfo..."); 
		String ADRSTS_STATUS_ACTIVE = SystemConstant.getConstant("ADRSTS_STATUS_ACTIVE");
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		ArrayList<CISAddressDataM> cisAddresses = null;
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
				SQL.append("SELECT ADR_USE_TP_CD, WRKPLC_NM, VILL, BLD_NM, ROOM_NO, "); 
				SQL.append(" FLR_NO, HS_NO, VILL_NO, ALY, STR_NM, "); 
				SQL.append(" CTY_CD, SUBDSTC, DSTC, PROV,PSTCD_AREA_CD, ADR_ID "); 
				SQL.append(" FROM IP_SHR.VC_IP_X_ADR_REL WHERE IP_ID = ? AND IP_X_ADR_REL_ST_CD = ? AND ADR_ST_CD = ? ");
				SQL.append(" AND VLD_TO_DT = ? ");
			logger.debug("SQL >> "+SQL); 
			logger.debug("ipId >> "+ipId); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,ipId); 	
			ps.setString(index++,ADRSTS_STATUS_ACTIVE);
			ps.setString(index++,DEFAULT_ADR_ST_CD);
			ps.setString(index++,DEFAULT_VLD_TO_DT);
			rs = ps.executeQuery(); 
			while(rs.next()) {
				if(null==cisAddresses){
					cisAddresses = new ArrayList<CISAddressDataM>();
				}
				CISAddressDataM cisAddressDataM = new CISAddressDataM();
//				String FLP_ADDRESS_TYPE = ListBoxControl.getName(FIELD_ID_ADDRESS_TYPE, "MAPPING4", FormatUtil.trim(rs.getString("ADR_USE_TP_CD")), "CHOICE_NO"); 
				String ADDRESS_TYPE=FormatUtil.trim(rs.getString("ADR_USE_TP_CD"));
				String COMPANY_NAME = FormatUtil.trim(rs.getString("WRKPLC_NM")); 
				String VILAPT = FormatUtil.trim(rs.getString("VILL")); 
				String BUILDING = FormatUtil.trim(rs.getString("BLD_NM")); 
				String ROOM = FormatUtil.trim(rs.getString("ROOM_NO")); 
				String FLOOR = FormatUtil.trim(rs.getString("FLR_NO")); 
				String ADDRESS_NO = FormatUtil.trim(rs.getString("HS_NO")); 
				String MOO = FormatUtil.trim(rs.getString("VILL_NO")); 
				String SOI = FormatUtil.trim(rs.getString("ALY")); 
				String ROAD = FormatUtil.trim(rs.getString("STR_NM")); 
				String COUNTRY = FormatUtil.trim(rs.getString("CTY_CD")); 
				String TAMBOL = FormatUtil.trim(rs.getString("SUBDSTC")); 
				String AMPHUR = FormatUtil.trim(rs.getString("DSTC")); 				
				String PROVINCE = FormatUtil.trim(rs.getString("PROV")); 
				String ZIPCODE = FormatUtil.trim(rs.getString("PSTCD_AREA_CD")); 
				String ADR_ID = FormatUtil.trim(rs.getString("ADR_ID")); 
				if(null==ADR_ID){ADR_ID="";}
				
				cisAddressDataM.setAddressType(ADDRESS_TYPE);
				cisAddressDataM.setAddressNo(ADDRESS_NO);
				cisAddressDataM.setCompanyName(COMPANY_NAME);
				cisAddressDataM.setVilapt(VILAPT);
				cisAddressDataM.setBuilding(BUILDING);
				cisAddressDataM.setRoom(ROOM);
				cisAddressDataM.setFloor(FLOOR);
				cisAddressDataM.setMoo(MOO);
				cisAddressDataM.setSoi(SOI);
				cisAddressDataM.setRoad(ROAD);
				cisAddressDataM.setCountry(COUNTRY);
				cisAddressDataM.setTambol(TAMBOL);
				cisAddressDataM.setAmphur(AMPHUR);
				cisAddressDataM.setProvince(PROVINCE);
				cisAddressDataM.setZipCode(ZIPCODE);
				cisAddressDataM.setAdrId(ADR_ID);
				cisAddresses.add(cisAddressDataM);
				
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return cisAddresses;
	}
	@Override
	public String getBussinessDesc(String busCode) throws Exception {
		logger.debug("getBussinessDesc....."); 	
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			SQL.append(" SELECT DSC_TH "); 
			SQL.append(" FROM IP_SHR.VL_IDY_CL_CD "); 
			SQL.append(" WHERE IDY_CL_CD = ? "); 
				
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 
			ps.setString(index++,busCode);
			rs = ps.executeQuery(); 
			
			if (rs.next()) {
				String busDesc = FormatUtil.trim(rs.getString("DSC_TH")); 
				return busDesc; 
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage()); 
			throw new ApplicationException(e.getMessage()); 
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage()); 
			}
		}
		return null;
	}
	
	@Override
	public CISCustomerDataM selectVC_IPData(String cisNo)throws Exception {
		CISCustomerDataM   cisCustomer = null; 
		PreparedStatement ps = null; 
		ResultSet rs = null; 
		Connection conn = null; 
		try {
			conn = getConnection(OrigServiceLocator.DIH); 
			StringBuilder SQL = new StringBuilder(); 
			SQL.append(" SELECT IP_ID, YNGST_DPND_BRTH_DT, VLD_TO_DT , VLD_FM_DT, ");
			SQL.append(" TH_TTL, TH_SURNM,TH_MDL_NM, TH_FRST_NM, TEL_OFFC_NO2, ");
			SQL.append(" TEL_OFFC_NO1, TEL_OFFC_EXN_NO2, TEL_OFFC_EXN_NO1, TEL_MBL_NO2, ");
			SQL.append(" TEL_MBL_NO1, TEL_HM_NO2, TEL_HM_NO1, TEL_HM_EXN_NO2, TEL_HM_EXN_NO1, ");
			SQL.append(" SRC_STM_ID, RLG_CD, RACE_CD, PRVN_F, PROF_CD,  ");
			SQL.append(" PRIM_SUB_SEG_CD, PRIM_SEG_CD, PREF_CTC_TM, PREF_CTC_MTH_CD, POS_CD, ");
			SQL.append(" ORG_RGST_CPTL, ORG_REV_SEG_CD, ORG_PROF_F, ORG_LGLTY_END_DT, ORG_LGL_BSN_F, ");
			SQL.append(" ORG_INCM_CASH_F, OLDST_DPND_BRTH_DT, OCP_DT, OCP_CD, NO_OF_EMP, ");
			SQL.append(" NO_OF_DPND_CHL_DT, NO_OF_DPND_CHL, NAT_CD, MRG_ST_CD, MAR_ST_CD, ");
			SQL.append(" LAST_VRSN_F, KBNK_IDY_CL_CD, ISSU_DT, IP_TP_CD, IP_TAX_ID, IP_ST_CD, ");
			SQL.append(" IP_PERF_ST_CD, IP_LGL_STC_TP_CD, IDV_INCM_SEG_CD, IDENT_NO, GOVT_ISSUR, GND_CD, ");
			SQL.append(" FAV_MEDIA_OTHR_INF, FAV_MEDIA_CD, FAM_INCM_SEG_CD, EXP_DT, EN_TTL, EN_SURNM, ");
			SQL.append(" EN_MDL_NM, EN_FRST_NM, EMPR_NM, ED_INST_OTHR_INF, ED_INST_CD, DUAL_SUB_SEG_CD, ");
			SQL.append(" DUAL_SEG_DT, DUAL_SEG_CD, DOMC_BR_NO, DOC_ITM_CD, DEATH_F, DEATH_DT, ");
			SQL.append(" CTF_TP_CD, CST_REL_TP_CD2, CST_REL_TP_CD1, CST_REFR_NM2, CST_REFR_NM1, ");
			SQL.append(" CST_LKE, CST_DSLKE, CONSND_SRC_STM_CD, CONSND_F, CONSND_DT, ");
			SQL.append(" CO_IDY_CL_CD, BSN_DSC, BRTH_ESTB_DT, AST_EXCLD_LAND, ACT_STRT_DT, ");
			SQL.append(" ACT_END_DT ");
			SQL.append(" FROM IP_SHR.VC_IP  ");
			SQL.append(" WHERE IP_ID = ? "); 
			logger.debug("SQL >> "+SQL); 
			ps = conn.prepareStatement(SQL.toString()); 
			int index = 1; 	
			ps.setString(index++,cisNo); 	
			rs = ps.executeQuery(); 
			if (rs.next()) {
				cisCustomer = new CISCustomerDataM();
				cisCustomer.setIpId(FormatUtil.trim(rs.getString("IP_ID")));
				cisCustomer.setVldFmDt(FormatUtil.trim(rs.getString("VLD_FM_DT")));
				cisCustomer.setActStrtDt(FormatUtil.trim(rs.getString("ACT_STRT_DT")));
				cisCustomer.setActEndDt(FormatUtil.trim(rs.getString("ACT_END_DT")));
				cisCustomer.setPrvnF(FormatUtil.trim(rs.getString("PRVN_F")));
				cisCustomer.setMrgStCd(FormatUtil.trim(rs.getString("MRG_ST_CD")));
				cisCustomer.setIpStCd(FormatUtil.trim(rs.getString("IP_ST_CD")));
				cisCustomer.setIpTpCd(FormatUtil.trim(rs.getString("IP_TP_CD")));
				cisCustomer.setIpLglStcTpCd(FormatUtil.trim(rs.getString("IP_LGL_STC_TP_CD")));
				cisCustomer.setBrthEstbDt(FormatUtil.trim(rs.getString("BRTH_ESTB_DT")));
				cisCustomer.setDocItmCd(FormatUtil.trim(rs.getString("DOC_ITM_CD")));
				cisCustomer.setIpTaxId(FormatUtil.trim(rs.getString("IP_TAX_ID")));
				cisCustomer.setIdentNo(FormatUtil.trim(rs.getString("IDENT_NO")));
				cisCustomer.setIssuDt(FormatUtil.trim(rs.getString("ISSU_DT")));
				cisCustomer.setExpDt(FormatUtil.trim(rs.getString("EXP_DT")));
				cisCustomer.setGovtIssur(FormatUtil.trim(rs.getString("GOVT_ISSUR")));
				cisCustomer.setDomcBrNo(FormatUtil.trim(rs.getString("DOMC_BR_NO")));
				cisCustomer.setIpPerfStCd(FormatUtil.trim(rs.getString("IP_PERF_ST_CD")));
				cisCustomer.setConsndDt(FormatUtil.trim(rs.getString("CONSND_DT")));
				cisCustomer.setConsndF(FormatUtil.trim(rs.getString("CONSND_F")));
				cisCustomer.setConsndSrcStmCd(FormatUtil.trim(rs.getString("CONSND_SRC_STM_CD")));
				cisCustomer.setCstLke(FormatUtil.trim(rs.getString("CST_LKE")));
				cisCustomer.setCstDslke(FormatUtil.trim(rs.getString("CST_DSLKE")));
				cisCustomer.setFavMediaCd(FormatUtil.trim(rs.getString("FAV_MEDIA_CD")));
				cisCustomer.setFavMediaOthrInf(FormatUtil.trim(rs.getString("FAV_MEDIA_OTHR_INF")));
				cisCustomer.setPrefCtcMthCd(FormatUtil.trim(rs.getString("PREF_CTC_MTH_CD")));
				cisCustomer.setPrefCtcTm(FormatUtil.trim(rs.getString("PREF_CTC_TM")));
				cisCustomer.setAstExcldLand(FormatUtil.trim(rs.getString("AST_EXCLD_LAND")));
				cisCustomer.setThTtl(FormatUtil.trim(rs.getString("TH_TTL")));
				cisCustomer.setThFrstNm(FormatUtil.trim(rs.getString("TH_FRST_NM")));
				cisCustomer.setThMdlNm(FormatUtil.trim(rs.getString("TH_MDL_NM")));
				cisCustomer.setThSurnm(FormatUtil.trim(rs.getString("TH_SURNM")));
				cisCustomer.setEnTtl(FormatUtil.trim(rs.getString("EN_TTL")));
				cisCustomer.setEnFrstNm(FormatUtil.trim(rs.getString("EN_FRST_NM")));
				cisCustomer.setEnMdlNm(FormatUtil.trim(rs.getString("EN_MDL_NM")));
				cisCustomer.setEnSurnm(FormatUtil.trim(rs.getString("EN_SURNM")));
				cisCustomer.setDeathDt(FormatUtil.trim(rs.getString("DEATH_DT")));
				cisCustomer.setDeathF(FormatUtil.trim(rs.getString("DEATH_F")));
				cisCustomer.setGndCd(FormatUtil.trim(rs.getString("GND_CD")));
				cisCustomer.setRlgCd(FormatUtil.trim(rs.getString("RLG_CD")));
				cisCustomer.setNatCd(FormatUtil.trim(rs.getString("NAT_CD")));
				cisCustomer.setRaceCd(FormatUtil.trim(rs.getString("RACE_CD")));
				cisCustomer.setMarStCd(FormatUtil.trim(rs.getString("MAR_ST_CD")));
				cisCustomer.setEdInstCd(FormatUtil.trim(rs.getString("ED_INST_CD")));
				cisCustomer.setEdInstOthrInf(FormatUtil.trim(rs.getString("ED_INST_OTHR_INF")));
				cisCustomer.setCtfTpCd(FormatUtil.trim(rs.getString("CTF_TP_CD")));
				cisCustomer.setOcpCd(FormatUtil.trim(rs.getString("OCP_CD")));
				cisCustomer.setOcpDt(FormatUtil.trim(rs.getString("OCP_DT")));
				cisCustomer.setPosCd(FormatUtil.trim(rs.getString("POS_CD")));
				cisCustomer.setProfCd(FormatUtil.trim(rs.getString("PROF_CD")));
				cisCustomer.setEmprNm(FormatUtil.trim(rs.getString("EMPR_NM")));
				cisCustomer.setIdvIncmSegCd(FormatUtil.trim(rs.getString("IDV_INCM_SEG_CD")));
				cisCustomer.setFamIncmSegCd(FormatUtil.trim(rs.getString("FAM_INCM_SEG_CD")));
				cisCustomer.setYngstDpndBrthDt(FormatUtil.trim(rs.getString("YNGST_DPND_BRTH_DT")));
				cisCustomer.setOldstDpndBrthDt(FormatUtil.trim(rs.getString("OLDST_DPND_BRTH_DT")));
				cisCustomer.setNoOfDpndChl(FormatUtil.trim(rs.getString("NO_OF_DPND_CHL")));
				cisCustomer.setNoOfDpndChlDt(FormatUtil.trim(rs.getString("NO_OF_DPND_CHL_DT")));
				cisCustomer.setOrgLgltyEndDt(FormatUtil.trim(rs.getString("ORG_LGLTY_END_DT")));
				cisCustomer.setNoOfEmp(FormatUtil.trim(rs.getString("NO_OF_EMP")));
				cisCustomer.setOrgRevSegCd(FormatUtil.trim(rs.getString("ORG_REV_SEG_CD")));
				cisCustomer.setOrgRgstCptl(FormatUtil.trim(rs.getString("ORG_RGST_CPTL")));
				cisCustomer.setOrgIncmCashF(FormatUtil.trim(rs.getString("ORG_INCM_CASH_F")));
				cisCustomer.setOrgLglBsnF(FormatUtil.trim(rs.getString("ORG_LGL_BSN_F")));
				cisCustomer.setOrgProfF(FormatUtil.trim(rs.getString("ORG_PROF_F")));
				cisCustomer.setBsnDsc(FormatUtil.trim(rs.getString("BSN_DSC")));
				cisCustomer.setPrimSegCd(FormatUtil.trim(rs.getString("PRIM_SEG_CD")));
				cisCustomer.setPrimSubSegCd(FormatUtil.trim(rs.getString("PRIM_SUB_SEG_CD")));
				cisCustomer.setDualSegCd(FormatUtil.trim(rs.getString("DUAL_SEG_CD")));
				cisCustomer.setDualSubSegCd(FormatUtil.trim(rs.getString("DUAL_SUB_SEG_CD")));
				cisCustomer.setDualSegDt(FormatUtil.trim(rs.getString("DUAL_SEG_DT")));
				cisCustomer.setKbnkIdyClCd(FormatUtil.trim(rs.getString("KBNK_IDY_CL_CD")));
				cisCustomer.setCoIdyClCd(FormatUtil.trim(rs.getString("CO_IDY_CL_CD")));
				cisCustomer.setCstRefrNm1(FormatUtil.trim(rs.getString("CST_REFR_NM1")));
				cisCustomer.setCstRelTpCd1(FormatUtil.trim(rs.getString("CST_REL_TP_CD1")));
				cisCustomer.setTelHmNo1(FormatUtil.trim(rs.getString("TEL_HM_NO1")));
				cisCustomer.setTelHmExnNo1(FormatUtil.trim(rs.getString("TEL_HM_EXN_NO1")));
				cisCustomer.setTelOffcNo1(FormatUtil.trim(rs.getString("TEL_OFFC_NO1")));
				cisCustomer.setTelOffcExnNo1(FormatUtil.trim(rs.getString("TEL_OFFC_EXN_NO1")));
				cisCustomer.setTelMblNo1(FormatUtil.trim(rs.getString("TEL_MBL_NO1")));
				cisCustomer.setCstRefrNm2(FormatUtil.trim(rs.getString("CST_REFR_NM2")));
				cisCustomer.setCstRelTpCd2(FormatUtil.trim(rs.getString("CST_REL_TP_CD2")));
				cisCustomer.setTelHmNo2(FormatUtil.trim(rs.getString("TEL_HM_NO2")));
				cisCustomer.setTelHmExnNo2(FormatUtil.trim(rs.getString("TEL_HM_EXN_NO2")));
				cisCustomer.setTelOffcNo2(FormatUtil.trim(rs.getString("TEL_OFFC_NO2")));
				cisCustomer.setTelOffcExnNo2(FormatUtil.trim(rs.getString("TEL_OFFC_EXN_NO2")));
				cisCustomer.setTelMblNo2(FormatUtil.trim(rs.getString("TEL_MBL_NO2")));
				cisCustomer.setLastVrsnF(FormatUtil.trim(rs.getString("LAST_VRSN_F")));
				cisCustomer.setVldToDt(FormatUtil.trim(rs.getString("VLD_TO_DT")));
				cisCustomer.setSrcStmId(FormatUtil.trim(rs.getString("SRC_STM_ID")));
				cisCustomer.setBrthEstbDate(FormatUtil.trim(rs.getString("BRTH_ESTB_DT")));
				cisCustomer.setVldToDate(FormatUtil.trim(rs.getString("VLD_TO_DT")));
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps); 
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}	
		return cisCustomer; 
	}
	@Override
	public ArrayList<CISCustomerDataM> selectVC_IPDataByIDNo(String CID_TYPE, String ID_NO) throws Exception {
		  ArrayList<CISCustomerDataM> cisCustomerList = null; 
		 PreparedStatement ps = null; 
			ResultSet rs = null; 
			Connection conn = null; 
			try {
				conn = getConnection(OrigServiceLocator.DIH); 
				StringBuilder SQL = new StringBuilder(); 
				SQL.append(" SELECT IP_ID,IDENT_NO,BRTH_ESTB_DT FROM IP_SHR.VC_IP "); 
				SQL.append(" WHERE VLD_TO_DT = ? "); //--Customer not delete 
				SQL.append(" AND LAST_VRSN_F = ? "); // --Customer Current Record 
				SQL.append(" AND IP_ST_CD = ? "); //--Customer Active 
				SQL.append(" AND DOC_ITM_CD = ? "); 
				SQL.append(" AND IDENT_NO = ? "); 
				logger.debug("SQL >> "+SQL); 
				ps = conn.prepareStatement(SQL.toString()); 
				int index = 1; 
				ps.setString(index++,DEFAULT_VLD_TO_DT); 
				ps.setString(index++,DEFAULT_LAST_VRSN_F); 
				ps.setString(index++,DEFAULT_IP_ST_CD); 
				CID_TYPE = ListBoxControl.getName(FIELD_ID_CID_TYPE,"CHOICE_NO",CID_TYPE,"MAPPING4"); 
				logger.debug("MAPPING CID_TYPE >> "+CID_TYPE); 
				ps.setString(index++,CID_TYPE); 
				ps.setString(index++,ID_NO); 	
				rs = ps.executeQuery(); 
				while (rs.next()) {
					if(null==cisCustomerList){
						cisCustomerList = new ArrayList<CISCustomerDataM>();
					}
					CISCustomerDataM cisCust = new CISCustomerDataM(); 
					cisCust.setIpId(FormatUtil.trim(rs.getString("IP_ID")));
					cisCust.setIdentNo(FormatUtil.trim(rs.getString("IDENT_NO")));
					cisCust.setBrthEstbDate(FormatUtil.trim(rs.getString("BRTH_ESTB_DT")));
					cisCustomerList.add(cisCust);	
				}
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			} finally {
				try {
					closeConnection(conn, rs, ps); 
				} catch (Exception e) {
					logger.fatal(e.getLocalizedMessage());
				}
			}	
			return cisCustomerList; 
		}
	
}
