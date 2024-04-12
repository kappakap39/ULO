package com.eaf.orig.ulo.app.view.form.subform.increase;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.control.util.SaleInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;

@SuppressWarnings("serial")
public class BankStaffSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(BankStaffSubForm.class);
	String SALE_TYPE_NORMAL_SALES = SystemConstant.getConstant("SALE_TYPE_NORMAL_SALES");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);		
		String BRANCH_STAFF_NAME  = request.getParameter("BRANCH_STAFF_NAME");
		String SALES_BRANCH_CODE = request.getParameter("SALES_BRANCH_CODE");
		String BRANCH_PHONE_NO  = request.getParameter("BRANCH_PHONE_NO");
		String SALES_COMMENT  = request.getParameter("SALES_COMMENT");
		if(!Util.empty(BRANCH_PHONE_NO)){
			BRANCH_PHONE_NO = BRANCH_PHONE_NO.replaceAll("-", "");
		}
		logger.debug("BRANCH_STAFF_NAME :"+BRANCH_STAFF_NAME);
		logger.debug("BRANCH_CODE :"+SALES_BRANCH_CODE);
		logger.debug("BRANCH_PHONE_NO :"+BRANCH_PHONE_NO);
		logger.debug("SALES_COMMENT :"+SALES_COMMENT);		
		SaleInfoDataM saleInfo = applicationGroup.getSaleInfoByType(SALE_TYPE_NORMAL_SALES);
		if(Util.empty(saleInfo)){
			saleInfo = new SaleInfoDataM();
			saleInfo.setSalesType(SALE_TYPE_NORMAL_SALES);	
			applicationGroup.addSaleInfos(saleInfo);
		}
//		if(BRANCH_STAFF_NAME!=null&&!BRANCH_STAFF_NAME.equals(saleInfo.getSalesId())){
//			saleInfo.setSalesId(BRANCH_STAFF_NAME);
//			//SaleInfoUtil.mapSaleInfoDetails(saleInfo);
//		}
		saleInfo.setSalesPhoneNo(BRANCH_PHONE_NO);
		saleInfo.setSalesComment(SALES_COMMENT);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		String subformId = "INCREASE_BANK_STAFF_SUBFORM";
		logger.debug("subformId >> "+subformId);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		SaleInfoDataM saleInfo = applicationGroup.getSaleInfoByType(SALE_TYPE_NORMAL_SALES);
		if(null == saleInfo){
			saleInfo = new SaleInfoDataM();
		}
		FormErrorUtil formError = new FormErrorUtil();		
		formError.mandatory(subformId,"BRANCH_STAFF_NAME",saleInfo.getSalesName(),request);
		formError.mandatory(subformId,"SALES_BRANCH_CODE",saleInfo.getSalesBranchCode(),request);
		formError.mandatory(subformId,"BRANCH_PHONE_NO",saleInfo.getSalesPhoneNo(),request);
		formError.mandatory(subformId,"SALES_COMMENT",saleInfo.getSalesComment(),request);		
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request){
		return false;
	}
}
