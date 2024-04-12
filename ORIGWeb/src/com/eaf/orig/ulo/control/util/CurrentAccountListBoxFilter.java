package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.AccountDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class CurrentAccountListBoxFilter implements ListBoxFilterInf{
	Logger logger = Logger.getLogger(CurrentAccountListBoxFilter.class);
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId, String cacheId, String typeId, HttpServletRequest request) {
		logger.debug("CurrentAccountListboxFilter.filter");
		ArrayList<HashMap<String, Object>> list = new ArrayList<>();
		try{
			Object masterObjectForm = FormControl.getMasterObjectForm(request);		
			PersonalInfoDataM personalInfo = null;
			logger.debug("masterObjectForm >> " + masterObjectForm);
			if(masterObjectForm instanceof ApplicationGroupDataM){			
				ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
				personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
			}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
				PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
				personalInfo = personalApplicationInfo.getPersonalInfo();
			}	
			if(null == personalInfo){
				personalInfo = new PersonalInfoDataM();
			}
			
			logger.debug("CurrentAccountListboxFilter personalId>> " +  personalInfo.getPersonalId());
			String personalId = personalInfo.getPersonalId();
			 ArrayList<AccountDataM> accounts = personalInfo.getAccounts();
			 if(!Util.empty(accounts)){
				for(AccountDataM account : accounts){
					HashMap<String, Object> data = new HashMap<>();
					data.put("CODE", account.getAccountNo());
					data.put("VALUE", FormatUtil.getAccountNo(account.getAccountNo())+" "+account.getAccountName());
					list.add(data);
				}
			 }
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return list;
	}

	@Override
	public void setFilterProperties(String configId, String cacheId, String typeId, HttpServletRequest request) {
		
	}

	@Override
	public void init(Object objectForm) {
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
}
