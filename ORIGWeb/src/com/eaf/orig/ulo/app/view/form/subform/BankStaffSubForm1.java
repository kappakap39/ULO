package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.RenderSubform;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.SaleInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;

@SuppressWarnings("serial")
public class BankStaffSubForm1 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(BankStaffSubForm1.class);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String SALE_TYPE_NORMAL_SALES = SystemConstant.getConstant("SALE_TYPE_NORMAL_SALES");
	String SALE_TYPE_REFERENCE_SALES = SystemConstant.getConstant("SALE_TYPE_REFERENCE_SALES");
	String SALE_TYPE_CASH_DAY_ONE_SALES = SystemConstant.getConstant("SALE_TYPE_CASH_DAY_ONE_SALES");
	String SALE_TYPE_ALLIANCE_SALES = SystemConstant.getConstant("SALE_TYPE_ALLIANCE_SALES");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
//		ArrayList<String> products = applicationGroup.getProducts(applicationGroup.getMaxLifeCycle());
		
		String REF_NAME  = request.getParameter("REF_NAME");
		String REF_BRANCH_CODE  = request.getParameter("REF_BRANCH_CODE");
		
		String SALES_NAME  = request.getParameter("SALES_NAME");
		String SALES_BRANCH_CODE  = request.getParameter("SALES_BRANCH_CODE");
		String SALES_PHONE_NO  = request.getParameter("SALES_PHONE_NO");
		
		String CASH_DAYONE_NAME  = request.getParameter("CASH_DAYONE_NAME");
		String CASH_DAYONE_BRANCH_CODE  = request.getParameter("CASH_DAYONE_BRANCH_CODE");
		String SALES_COMMENT  = request.getParameter("SALES_COMMENT");
		
		String SALES_CODE = request.getParameter("SALES_CODE_"+SALE_TYPE_ALLIANCE_SALES);
		String SALES_BRANCH_CODE_ALLIANCE = request.getParameter("SALES_BRANCH_CODE_"+SALE_TYPE_ALLIANCE_SALES);
		String SALES_NAME_ALLIANCE = request.getParameter("SALES_NAME_"+SALE_TYPE_ALLIANCE_SALES);
		
		logger.debug("REF_NAME :"+REF_NAME);
		logger.debug("REF_BRANCH_CODE :"+REF_BRANCH_CODE);
		logger.debug("SALES_NAME :"+SALES_NAME);
		logger.debug("SALES_BRANCH_CODE :"+SALES_BRANCH_CODE);
		logger.debug("SALES_PHONE_NO :"+SALES_PHONE_NO);
		logger.debug("CASH_DAYONE_NAME :"+CASH_DAYONE_NAME);
		logger.debug("CASH_DAYONE_BRANCH_CODE :"+CASH_DAYONE_BRANCH_CODE);
		logger.debug("SALES_COMMENT :"+SALES_COMMENT);
		logger.debug("SALES_CODE :"+SALES_CODE);
		logger.debug("SALES_BRANCH_CODE_ALLIANCE :"+SALES_BRANCH_CODE_ALLIANCE);
		logger.debug("SALES_NAME_ALLIANCE :"+SALES_NAME_ALLIANCE);
		
		SaleInfoDataM saleInfoRefSalse = applicationGroup.getSaleInfoByType(SALE_TYPE_REFERENCE_SALES);
		if(Util.empty(saleInfoRefSalse)){
			saleInfoRefSalse = new SaleInfoDataM();
			saleInfoRefSalse.setSalesType(SALE_TYPE_REFERENCE_SALES);
			applicationGroup.addSaleInfos(saleInfoRefSalse);
		}
		
		//saleInfoRefSalse.setSalesId(REF_NAME);
		//SaleInfoUtil.mapSaleInfoDetails(saleInfoRefSalse);
		
		
		SaleInfoDataM saleInfoNormalSale = applicationGroup.getSaleInfoByType(SALE_TYPE_NORMAL_SALES);
		if(Util.empty(saleInfoNormalSale)){
			saleInfoNormalSale = new SaleInfoDataM();
			saleInfoNormalSale.setSalesType(SALE_TYPE_NORMAL_SALES);
			applicationGroup.addSaleInfos(saleInfoNormalSale);
		}
//		if(SALES_NAME!=null&&!SALES_NAME.equals(saleInfoNormalSale.getSalesId())){
//			saleInfoNormalSale.setSalesId(SALES_NAME);
//			SaleInfoUtil.mapSaleInfoDetails(saleInfoNormalSale);
//		}
		saleInfoNormalSale.setSalesPhoneNo(FormatUtil.removeDash(SALES_PHONE_NO));
		
		SaleInfoDataM saleInfoAllianceSale = applicationGroup.getSaleInfoByType(SALE_TYPE_ALLIANCE_SALES);
		if(Util.empty(saleInfoAllianceSale)){
			saleInfoAllianceSale = new SaleInfoDataM();
			saleInfoAllianceSale.setSalesType(SALE_TYPE_ALLIANCE_SALES);
			applicationGroup.addSaleInfos(saleInfoAllianceSale);
		}
		saleInfoAllianceSale.setSalesName(SALES_NAME_ALLIANCE);
		saleInfoAllianceSale.setSalesBranchCode(SALES_BRANCH_CODE_ALLIANCE);
		saleInfoAllianceSale.setSalesId(SALES_CODE);
		
		ArrayList<String> products = applicationGroup.getProducts(applicationGroup.getMaxLifeCycle());
		if(!Util.empty(products) && !products.contains(PRODUCT_CRADIT_CARD)){
			SaleInfoDataM saleInfoCashDayOneSale =	applicationGroup.getSaleInfoByType(SALE_TYPE_CASH_DAY_ONE_SALES);
			if(Util.empty(saleInfoCashDayOneSale)){
				saleInfoCashDayOneSale = new SaleInfoDataM();
				saleInfoCashDayOneSale.setSalesType(SALE_TYPE_CASH_DAY_ONE_SALES);
				applicationGroup.addSaleInfos(saleInfoCashDayOneSale);
			}
//			if(CASH_DAYONE_NAME!=null&&!CASH_DAYONE_NAME.equals(saleInfoCashDayOneSale.getSalesId())){
//				saleInfoCashDayOneSale.setSalesId(CASH_DAYONE_NAME);		
//				SaleInfoUtil.mapSaleInfoDetails(saleInfoCashDayOneSale);
//			}
			saleInfoCashDayOneSale.setSalesComment(SALES_COMMENT);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		ArrayList<String> products = applicationGroup.getProducts(applicationGroup.getMaxLifeCycle());
		String subformId = "BANK_STAFF_SUBFORM_1";
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		SaleInfoDataM saleInfoRefSale =	applicationGroup.getSaleInfoByType(SALE_TYPE_REFERENCE_SALES);
		if(null == saleInfoRefSale){
			saleInfoRefSale = new SaleInfoDataM();
		}
		SaleInfoDataM saleInfoNormalSale =	applicationGroup.getSaleInfoByType(SALE_TYPE_NORMAL_SALES);
		if(null == saleInfoNormalSale){
			saleInfoNormalSale = new SaleInfoDataM();
		}
		SaleInfoDataM saleInfoCashDayOneSale =	applicationGroup.getSaleInfoByType(SALE_TYPE_CASH_DAY_ONE_SALES);	
		if(null == saleInfoCashDayOneSale){
			saleInfoCashDayOneSale = new SaleInfoDataM();
		}		 
		formError.mandatory(subformId,"REF_NAME",saleInfoRefSale.getSalesName(),request);
		formError.mandatory(subformId,"REF_BRANCH_CODE",saleInfoRefSale.getSalesBranchCode(),request);
		formError.mandatory(subformId,"SALES_NAME",saleInfoNormalSale.getSalesName(),request);
		formError.mandatory(subformId,"SALES_BRANCH_CODE",saleInfoNormalSale.getSalesBranchCode(),request);
		formError.mandatory(subformId,"SALES_PHONE_NO",saleInfoNormalSale.getSalesPhoneNo(),request);
		if(!Util.empty(products) && !products.contains(PRODUCT_CRADIT_CARD)){
			formError.mandatory(subformId,"CASH_DAYONE_NAME",saleInfoCashDayOneSale.getSalesName(),request);
			formError.mandatory(subformId,"CASH_DAYONE_BRANCH_CODE",saleInfoCashDayOneSale.getSalesBranchCode(),request);
			formError.mandatory(subformId,"SALES_COMMENT",saleInfoCashDayOneSale.getSalesComment(),request);
		}		
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}	
	@Override
	public String renderSubformFlag(HttpServletRequest request,	Object objectForm) {		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		String templateId = applicationGroup.getApplicationTemplate();
		logger.debug("templateId >> "+templateId);		
		RenderSubform render = new RenderSubform();
		String BANK_STAFF_SUBFORM_1 = SystemConstant.getConstant("BANK_STAFF_SUBFORM_1");			
		String checkProductType = render.determineBankStaff(templateId);
		if(BANK_STAFF_SUBFORM_1.equals(checkProductType)){
			return MConstant.FLAG_Y;
		}		
		return MConstant.FLAG_N;
	}	

}
