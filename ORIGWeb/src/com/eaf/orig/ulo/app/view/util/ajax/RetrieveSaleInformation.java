package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.ulo.control.util.SaleInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;

public class RetrieveSaleInformation implements AjaxInf  {
	private static transient Logger logger = Logger.getLogger(RetrieveSaleInformation.class);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String SALE_TYPE_NORMAL_SALES = SystemConstant.getConstant("SALE_TYPE_NORMAL_SALES");
	String SALE_TYPE_REFERENCE_SALES = SystemConstant.getConstant("SALE_TYPE_REFERENCE_SALES");
	String SALE_TYPE_CASH_DAY_ONE_SALES = SystemConstant.getConstant("SALE_TYPE_CASH_DAY_ONE_SALES");
	String SALE_TYPE_ALLIANCE_SALES = SystemConstant.getConstant("SALE_TYPE_ALLIANCE_SALES");
	@Override
	public ResponseData processAction(HttpServletRequest request){	
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.RETRIEVE_SALE_INFORMATION);	
		try{
			
			String functionCode=request.getParameter("FUNCTION_CODE");
			
			String REF_NAME  = request.getParameter("REF_NAME");
			//String REF_BRANCH_CODE  = request.getParameter("REF_BRANCH_CODE");
			
			String SALES_NAME  = request.getParameter("SALES_NAME");
			//String SALES_BRANCH_CODE  = request.getParameter("SALES_BRANCH_CODE");
			String CASH_DAYONE_NAME  = request.getParameter("CASH_DAYONE_NAME");
			//String CASH_DAYONE_BRANCH_CODE  = request.getParameter("CASH_DAYONE_BRANCH_CODE");
			String BRANCH_STAFF_NAME  = request.getParameter("BRANCH_STAFF_NAME");		
			
			ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute("ORIGForm");
			ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
			
			FormErrorUtil formError = new FormErrorUtil();
			
			if(functionCode.endsWith("NORMAL")){
				
				SaleInfoDataM saleInfoRefSalse = applicationGroup.getSaleInfoByType(SALE_TYPE_REFERENCE_SALES);
				if(Util.empty(saleInfoRefSalse)){
					saleInfoRefSalse = new SaleInfoDataM();
					saleInfoRefSalse.setSalesType(SALE_TYPE_REFERENCE_SALES);
					applicationGroup.addSaleInfos(saleInfoRefSalse);
				}
				
				saleInfoRefSalse.setSalesId(REF_NAME);
				SaleInfoUtil.mapSaleInfoDetails(saleInfoRefSalse,formError);
				
				SaleInfoDataM saleInfoNormalSale = applicationGroup.getSaleInfoByType(SALE_TYPE_NORMAL_SALES);
				if(Util.empty(saleInfoNormalSale)){
					saleInfoNormalSale = new SaleInfoDataM();
					saleInfoNormalSale.setSalesType(SALE_TYPE_NORMAL_SALES);
					applicationGroup.addSaleInfos(saleInfoNormalSale);
				}
				
				saleInfoNormalSale.setSalesId(SALES_NAME);
				SaleInfoUtil.mapSaleInfoDetails(saleInfoNormalSale,formError);
					
				SaleInfoDataM saleInfoAllianceSale = applicationGroup.getSaleInfoByType(SALE_TYPE_ALLIANCE_SALES);
				if(Util.empty(saleInfoAllianceSale)){
					saleInfoAllianceSale = new SaleInfoDataM();
					saleInfoAllianceSale.setSalesType(SALE_TYPE_ALLIANCE_SALES);
					applicationGroup.addSaleInfos(saleInfoAllianceSale);
				}
			
				
				ArrayList<String> products = applicationGroup.getProducts(applicationGroup.getMaxLifeCycle());
				if(!Util.empty(products) && !products.contains(PRODUCT_CRADIT_CARD)){
					SaleInfoDataM saleInfoCashDayOneSale =	applicationGroup.getSaleInfoByType(SALE_TYPE_CASH_DAY_ONE_SALES);
					if(Util.empty(saleInfoCashDayOneSale)){
						saleInfoCashDayOneSale = new SaleInfoDataM();
						saleInfoCashDayOneSale.setSalesType(SALE_TYPE_CASH_DAY_ONE_SALES);
						applicationGroup.addSaleInfos(saleInfoCashDayOneSale);
					}
					
					saleInfoCashDayOneSale.setSalesId(CASH_DAYONE_NAME);		
					SaleInfoUtil.mapSaleInfoDetails(saleInfoCashDayOneSale,formError);
				}
				
			}
			else if(functionCode.equals("KPL"))
			{
				//KPL Additional
				SaleInfoDataM saleInfo = applicationGroup.getSaleInfoByType(SALE_TYPE_NORMAL_SALES);
				if(Util.empty(saleInfo)){
					saleInfo = new SaleInfoDataM();
					saleInfo.setSalesType(SALE_TYPE_NORMAL_SALES);	
					applicationGroup.addSaleInfos(saleInfo);
				}
				
				saleInfo.setSalesId(SALES_NAME);
				SaleInfoUtil.mapSaleInfoDetails(saleInfo);
			}
			else
			{
				SaleInfoDataM saleInfo = applicationGroup.getSaleInfoByType(SALE_TYPE_NORMAL_SALES);
				if(Util.empty(saleInfo)){
					saleInfo = new SaleInfoDataM();
					saleInfo.setSalesType(SALE_TYPE_NORMAL_SALES);	
					applicationGroup.addSaleInfos(saleInfo);
				}
				
				saleInfo.setSalesId(BRANCH_STAFF_NAME);
				SaleInfoUtil.mapSaleInfoDetails(saleInfo);
			}
			
			ArrayList<String> errors = (ArrayList)formError.getFormError().get(formError.ERROR);
			logger.debug("errors"+errors);
			if(errors!=null&&errors.size()>0){
				return responseData.error(errors.get(0));
			}else{
				return responseData.success("SUCCESS");
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);		
		}		
	}
}
