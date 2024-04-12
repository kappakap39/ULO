package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListDataM;
import com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM;

public class MandatoryFollowDoc implements AjaxDisplayGenerateInf{
	static Logger logger = Logger.getLogger(MandatoryFollowDoc.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		
		logger.debug("MandatoryFollowDoc >> ");
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = formHandler.getAppForm();
		
		String saleBranchCode = request.getParameter("seller_branch_code");
		String saleID = request.getParameter("saleId");
		String refID = request.getParameter("refId");
		
		logger.debug("Sale Branch Code >> "+saleBranchCode);
		logger.debug("SaleID >> "+saleID);
		logger.debug("RefID >> "+refID);
		
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
			formHandler.setAppForm(applicationM);
		}		
		PLSaleInfoDataM saleM = applicationM.getSaleInfo();
		if(saleM == null){
			saleM = new PLSaleInfoDataM();
			applicationM.setSaleInfo(saleM);
		}
		
		if(!OrigUtil.isEmptyString(saleID)){
			saleM.setSalesName(saleID);
		}
		
		if(!OrigUtil.isEmptyString(refID)){
			saleM.setRefName(refID);
		}
		
		saleM.setSalesBranchCode(saleBranchCode);
		
		JsonObjectUtil json = new JsonObjectUtil();
		
		if(OrigUtil.isEmptyString(saleBranchCode) || !ReciveTrackDoc(applicationM)){
			json.CreateJsonObject("", ErrorUtil.getShortErrorMessage(request,"SEND_MAIL_NO_BRANCH_NO_DOC"));
		}
	
		return json.returnJson();
	}
	
	public boolean ReciveTrackDoc(PLApplicationDataM applicationM){
		if(applicationM != null && applicationM.getDocCheckListVect() != null && applicationM.getDocCheckListVect().size() > 0){
			Vector<PLDocumentCheckListDataM> docVt = applicationM.getDocCheckListVect();
			for(PLDocumentCheckListDataM docM :docVt){
				if(docM != null && OrigConstant.DocumentStatus.TrackDoc.equals(docM.getReceive())){
					return true;
				}
			}
		}
		return false;
	}
	
}
