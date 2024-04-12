package com.eaf.orig.ulo.app.view.form.manual;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.control.util.SaleInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;

public class MuangThaiPopupForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(MuangThaiPopupForm.class);
	String SALE_TYPE_MTL_SALES = SystemConstant.getConstant("SALE_TYPE_MTL_SALES");
	@Override
	public Object getObjectForm() {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		HashMap<String,Object> supplementaryApplicant = new HashMap<>();		
		SaleInfoDataM saleInfos = ORIGForm.getObjectForm().getSaleInfoByType(SALE_TYPE_MTL_SALES);
		if(Util.empty(saleInfos)){
			saleInfos = new SaleInfoDataM();
		}
		String uniqueId = getRequestData("uniqueId");
	    logger.debug("uniqueId >> "+uniqueId);
	    ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	    ApplicationDataM application = applicationGroup.getApplicationById(uniqueId);
		supplementaryApplicant.put("SALE_INFO",saleInfos);	
		supplementaryApplicant.put("APPLICATION",application);
		return supplementaryApplicant;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String processForm() {
		String uniqueId = getRequestData("uniqueId");
		logger.debug("uniqueId >>"+uniqueId);
		HashMap<String,Object> supplementaryApplicant = (HashMap<String,Object>)objectForm;
		SaleInfoDataM saleInfoData = (SaleInfoDataM)supplementaryApplicant.get("SALE_INFO");
		ApplicationDataM appItem = (ApplicationDataM)supplementaryApplicant.get("APPLICATION");
		if(!Util.empty(saleInfoData) && !Util.empty(saleInfoData.getSalesId())){			
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
			ApplicationGroupDataM applicationgroupDataM = ORIGForm.getObjectForm();
			SaleInfoDataM saleInfo = applicationgroupDataM.getSaleInfoByType(SALE_TYPE_MTL_SALES);
			
			if(Util.empty(saleInfo)){
				saleInfo = new SaleInfoDataM();
				saleInfo.setSalesType(SALE_TYPE_MTL_SALES);
				ORIGForm.getObjectForm().addSaleInfos(saleInfo);
			}
			saleInfo.setSalesId(saleInfoData.getSalesId());
			SaleInfoUtil.mapSaleInfoDetails(saleInfo);
			saleInfo.setSalesPhoneNo(saleInfoData.getSalesPhoneNo());			
		}
		
		if(!Util.empty(appItem)&&!Util.empty(appItem.getCard().getMembershipNo())){
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
			ApplicationGroupDataM applicationgroupDataM = ORIGForm.getObjectForm();
			ApplicationDataM applicationDataM = applicationgroupDataM.getApplicationById(uniqueId);
			applicationDataM.getCard().setMembershipNo(appItem.getCard().getMembershipNo());
			if(!Util.empty(appItem.getCard().getCompleteFlag())){
				applicationDataM.getCard().setCompleteFlag(appItem.getCard().getCompleteFlag());
			}
		}
		return null;
	}
	
}
