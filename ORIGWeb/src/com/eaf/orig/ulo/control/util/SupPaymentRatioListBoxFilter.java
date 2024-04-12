package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.file.ExcelTemplate.logger;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.orig.ulo.constant.MConstant;

public class SupPaymentRatioListBoxFilter implements ListBoxFilterInf {
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {
		ArrayList<HashMap<String,Object>> List = ListBoxControl.getListBox(cacheId,"ACTIVE");
		ArrayList<HashMap<String,Object>> vPaymentType = new ArrayList<>();
		String FLAG_YES = MConstant.FLAG.YES;

//		String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
//		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
//		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
//		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
//		if(null == personalInfo){
//			personalInfo = new PersonalInfoDataM();
//		}
		if(null != List){
			for (HashMap<String, Object> payment : List){							
				if(("SupPaymentRatio").equals(configId))				{
					String SYSTEM_ID1 = SQLQueryEngine.display(payment,"SYSTEM_ID1");
					if(FLAG_YES.equals(SYSTEM_ID1))
					{
						vPaymentType.add(payment);
					}
				}
			}
		}
		logger.debug("vPaymentType>>>"+vPaymentType);
		return vPaymentType;
	}
	@Override
	public void setFilterProperties(String configId, String cacheId, String typeId,HttpServletRequest request) {
		
	}
	@Override
	public void init(Object objectForm) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
}
