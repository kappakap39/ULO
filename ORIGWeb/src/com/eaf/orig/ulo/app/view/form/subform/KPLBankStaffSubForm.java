package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.RenderSubform;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.SaleInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;

@SuppressWarnings("serial")
public class KPLBankStaffSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(BankStaffSubForm1.class);
	private String ROLE_DV = SystemConstant.getConstant("ROLE_DV");	
	
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String SALE_TYPE_NORMAL_SALES = SystemConstant.getConstant("SALE_TYPE_NORMAL_SALES");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		
		String SALES_NAME  = request.getParameter("SALES_NAME");
		String SALES_BRANCH_CODE  = request.getParameter("SALES_BRANCH_CODE");
		String SALES_PHONE_NO  = request.getParameter("SALES_PHONE_NO");
		
		logger.debug("SALES_NAME :"+SALES_NAME);
		logger.debug("SALES_BRANCH_CODE :"+SALES_BRANCH_CODE);
		logger.debug("SALES_PHONE_NO :"+SALES_PHONE_NO);
		
		SaleInfoDataM saleInfoNormalSale = applicationGroup.getSaleInfoByType(SALE_TYPE_NORMAL_SALES);
		if(Util.empty(saleInfoNormalSale)){
			saleInfoNormalSale = new SaleInfoDataM();
			saleInfoNormalSale.setSalesType(SALE_TYPE_NORMAL_SALES);
			applicationGroup.addSaleInfos(saleInfoNormalSale);
		}
		saleInfoNormalSale.setSalesId(SALES_NAME);
		SaleInfoUtil.mapSaleInfoDetails(saleInfoNormalSale);
		saleInfoNormalSale.setSalesPhoneNo(FormatUtil.removeDash(SALES_PHONE_NO));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		ArrayList<String> products = applicationGroup.getProducts(applicationGroup.getMaxLifeCycle());
		String subformId = "KPL_BANK_STAFF_SUBFORM";
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();

		SaleInfoDataM saleInfoNormalSale =	applicationGroup.getSaleInfoByType(SALE_TYPE_NORMAL_SALES);
		if(null == saleInfoNormalSale){
			saleInfoNormalSale = new SaleInfoDataM();
		}
		
		if(KPLUtil.isSavingPlus(applicationGroup))
		{
			formError.mandatory(subformId,"SALES_NAME",saleInfoNormalSale.getSalesName(),request);
			formError.mandatory(subformId,"SALES_BRANCH_CODE",SaleInfoUtil.showBranchDetp(saleInfoNormalSale),request);
		}
		//formError.mandatory(subformId,"SALES_PHONE_NO",saleInfoNormalSale.getSalesPhoneNo(),request);

		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
	@Override
	public String renderSubformFlag(HttpServletRequest request,Object objectForm) 
	{
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		if(KPLUtil.isKPL(applicationGroup))
		{
			return MConstant.FLAG_Y;
		}		
		return MConstant.FLAG_N;
	}


}
