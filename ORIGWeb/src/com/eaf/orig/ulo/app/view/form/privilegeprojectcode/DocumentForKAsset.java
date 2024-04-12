package com.eaf.orig.ulo.app.view.form.privilegeprojectcode;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeKassetDocDataM;

public class DocumentForKAsset extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(DocumentForKAsset.class);	
	String KASSET_TYPE_MORE_6_MONTH =  SystemConstant.getConstant("KASSET_TYPE_MORE_6_MONTH");
	String KASSET_TYPE_6_MONTH = SystemConstant.getConstant("KASSET_TYPE_6_MONTH");
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {	
		return "/orig/ulo/subform/projectcode/document/DocumentfForKAssetSubForm.jsp";
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		int PROJECT_DOC_ASSET_INDEX=0;
		
		String FUND_6M = request.getParameter("FUND_6M");
//		String FUND_L6M = request.getParameter("FUND_L6M");
//		String WEALTH = request.getParameter("WEALTH");
//		String INCOME = request.getParameter("INCOME");
		
		String MONTH1_AMT = request.getParameter("MONTH1_AMT");
		String MONTH2_AMT = request.getParameter("MONTH2_AMT");
		String MONTH3_AMT = request.getParameter("MONTH3_AMT");
		String MONTH4_AMT = request.getParameter("MONTH4_AMT");
		String MONTH5_AMT = request.getParameter("MONTH5_AMT");
		String MONTH6_AMT = request.getParameter("MONTH6_AMT");
		
		String KASSET_TYPE = request.getParameter("KASSET_TYPE");
		
		logger.debug("KASSET_TYPE >> "+KASSET_TYPE);
		
		logger.debug("MONTH1_AMT >> "+MONTH1_AMT);
		logger.debug("MONTH2_AMT >> "+MONTH2_AMT);
		logger.debug("MONTH3_AMT >> "+MONTH3_AMT);
		logger.debug("MONTH4_AMT >> "+MONTH4_AMT);
		logger.debug("MONTH5_AMT >> "+MONTH5_AMT);
		logger.debug("MONTH6_AMT >> "+MONTH6_AMT);
		
		PrivilegeProjectCodeDocDataM privilegeProjectCodeDoc = ((PrivilegeProjectCodeDocDataM)objectElement);	 
		
		ArrayList<PrivilegeProjectCodeKassetDocDataM> prvlgProjectCodeKassets = privilegeProjectCodeDoc.getPrivilegeProjectCodeKassetDocs();
		if(Util.empty(prvlgProjectCodeKassets)){
			prvlgProjectCodeKassets = new ArrayList<PrivilegeProjectCodeKassetDocDataM>();
		}
		
		PrivilegeProjectCodeKassetDocDataM	prvlgProjectCodeKasset =privilegeProjectCodeDoc.getPrivilegeProjectCodeKassetDoc(PROJECT_DOC_ASSET_INDEX);
		if(Util.empty(prvlgProjectCodeKasset)){
			prvlgProjectCodeKasset = new PrivilegeProjectCodeKassetDocDataM();
			prvlgProjectCodeKassets.add(PROJECT_DOC_ASSET_INDEX,prvlgProjectCodeKasset);
		}
		
		
	
		prvlgProjectCodeKasset.setKassetType(KASSET_TYPE);
		if(KASSET_TYPE_MORE_6_MONTH.equals(KASSET_TYPE)){
			prvlgProjectCodeKasset.setMonth1m(FormatUtil.toBigDecimal(MONTH1_AMT, true));
			prvlgProjectCodeKasset.setMonth2m(FormatUtil.toBigDecimal(MONTH2_AMT, true));
			prvlgProjectCodeKasset.setMonth3m(FormatUtil.toBigDecimal(MONTH3_AMT, true));
			prvlgProjectCodeKasset.setMonth4m(FormatUtil.toBigDecimal(MONTH4_AMT, true));
			prvlgProjectCodeKasset.setMonth5m(FormatUtil.toBigDecimal(MONTH5_AMT, true));
			prvlgProjectCodeKasset.setMonth6m(FormatUtil.toBigDecimal(MONTH6_AMT, true));
			prvlgProjectCodeKasset.setFund6m(null);
		}else if(KASSET_TYPE_6_MONTH.equals(KASSET_TYPE)){
			prvlgProjectCodeKasset.setFund6m(!Util.empty(FUND_6M)?FormatUtil.toBigDecimal(FUND_6M):null);
			prvlgProjectCodeKasset.setMonth1m(null);
			prvlgProjectCodeKasset.setMonth2m(null);
			prvlgProjectCodeKasset.setMonth3m(null);
			prvlgProjectCodeKasset.setMonth4m(null);
			prvlgProjectCodeKasset.setMonth5m(null);
			prvlgProjectCodeKasset.setMonth6m(null);
		}
		
		
//		prvlgProjectCodeKasset.setFundL6m(!Util.empty(FUND_L6M)?FormatUtil.toBigDecimal(FUND_L6M):null);
//		prvlgProjectCodeKasset.setWealth(!Util.empty(WEALTH)?FormatUtil.toBigDecimal(WEALTH):null);
//		prvlgProjectCodeKasset.setIncome(!Util.empty(INCOME)?FormatUtil.toBigDecimal(INCOME):null);
		privilegeProjectCodeDoc.setPrivilegeProjectCodeKassetDocs(prvlgProjectCodeKassets);
		privilegeProjectCodeDoc.setPrivilegeProjectCodeExceptionDocs(null);
		privilegeProjectCodeDoc.setPrivilegeProjectCodeProductTradings(null);
		
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {	
		String subformId ="VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM";
		int PROJECT_DOC_ASSET_INDEX=0;
		//DOCUMENT_FOR_K_ASSET_TABLE
		FormErrorUtil formError = new FormErrorUtil();
		try {
			PrivilegeProjectCodeKassetDocDataM  prvlgProjectCodeKasset = new PrivilegeProjectCodeKassetDocDataM();
			PrivilegeProjectCodeDocDataM privilegeProjectCodeDoc  = (PrivilegeProjectCodeDocDataM)objectElement;
			ArrayList<PrivilegeProjectCodeKassetDocDataM> prvlgProjectCodeKassets =  new ArrayList<PrivilegeProjectCodeKassetDocDataM>();
			if(!Util.empty(privilegeProjectCodeDoc.getPrivilegeProjectCodeKassetDocs())){
				prvlgProjectCodeKassets = privilegeProjectCodeDoc.getPrivilegeProjectCodeKassetDocs();
				 if(!Util.empty(prvlgProjectCodeKassets)){
					 prvlgProjectCodeKasset = prvlgProjectCodeKassets.get(PROJECT_DOC_ASSET_INDEX);
				 }
			}	
			
			
			if(Util.empty(prvlgProjectCodeKasset.getFund6m()) && Util.empty(prvlgProjectCodeKasset.getKassetType())
					&& Util.empty(prvlgProjectCodeKasset.getMonth1m()) && Util.empty(prvlgProjectCodeKasset.getMonth2m())
					&& Util.empty(prvlgProjectCodeKasset.getMonth3m()) && Util.empty(prvlgProjectCodeKasset.getMonth4m())
					&& Util.empty(prvlgProjectCodeKasset.getMonth5m()) && Util.empty(prvlgProjectCodeKasset.getMonth6m())){
				formError.mandatory(subformId,"DOCUMENT_FOR_K_ASSET_TABLE", "DOCUMENT_FOR_K_ASSET",prvlgProjectCodeKasset.getFund6m(), request);
			}else{
				if(!Util.empty(prvlgProjectCodeKasset.getKassetType()) && KASSET_TYPE_MORE_6_MONTH.equals(prvlgProjectCodeKasset.getKassetType())){
					formError.mandatory(subformId,"MONTH_1", "MONTH1_AMT","",prvlgProjectCodeKasset.getMonth1m(), request);
					formError.mandatory(subformId,"MONTH_2", "MONTH2_AMT","",prvlgProjectCodeKasset.getMonth2m(), request);
					formError.mandatory(subformId,"MONTH_3", "MONTH3_AMT","",prvlgProjectCodeKasset.getMonth3m(), request);
					formError.mandatory(subformId,"MONTH_4", "MONTH4_AMT","",prvlgProjectCodeKasset.getMonth4m(), request);
					formError.mandatory(subformId,"MONTH_5", "MONTH5_AMT","",prvlgProjectCodeKasset.getMonth5m(), request);
					formError.mandatory(subformId,"MONTH_6", "MONTH6_AMT","",prvlgProjectCodeKasset.getMonth6m(), request);
				}else if(!Util.empty(prvlgProjectCodeKasset.getKassetType()) && KASSET_TYPE_6_MONTH.equals(prvlgProjectCodeKasset.getKassetType())){
					formError.mandatory(subformId,"FUNDS_OWN_UNDER_6_MONTH", "FUND_6M",MessageErrorUtil.getText("ERROR_FUND_6M_PRODUCT"),prvlgProjectCodeKasset.getFund6m(), request);	
				}

			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return formError.getFormError();
	}
}
