package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class AddressListBoxFilter implements ListBoxFilterInf {
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
	private static transient Logger logger = Logger.getLogger(AddressListBoxFilter.class);
	String FLAG_YES = MConstant.FLAG.YES;
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {
		ArrayList<HashMap<String,Object>> List = ListBoxControl.getListBox(cacheId,"ACTIVE");
		ArrayList<HashMap<String,Object>> vAddressType = new ArrayList<>();
		
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}

		if(null != List){
			for (HashMap<String, Object> AddressType : List){				
				String ADDRESS_TYPE = SQLQueryEngine.display(AddressType,"CHOICE_NO");				
				if(("A_ADDRESS_TYPE").equals(configId)){
					String SYSTEM_ID1 = SQLQueryEngine.display(AddressType,"SYSTEM_ID1");
					if(FLAG_YES.equals(SYSTEM_ID1)){
						if(Util.empty(personalInfo.getAddress(ADDRESS_TYPE))){
							vAddressType.add(AddressType);
						}
					}
				}else if(("S_ADDRESS_TYPE").equals(configId)) {
					String SYSTEM_ID2 = SQLQueryEngine.display(AddressType,"SYSTEM_ID2");
					if (FLAG_YES.equals(SYSTEM_ID2)) {
						if(Util.empty(personalInfo.getAddress(ADDRESS_TYPE))){
							vAddressType.add(AddressType);
						}
					}
				}else if("COPY_ADDRESS_TYPE_CURRENT".equals(configId)) {
					ModuleFormHandler moduleForm = (ModuleFormHandler)request.getSession().getAttribute("ModuleForm");
					String personalType = moduleForm.getRequestData("PERSONAL_TYPE");
					PersonalInfoDataM personalInfoData = applicationGroup.getPersonalInfo(personalType);
					if(Util.empty(personalInfoData)){
						personalInfoData = new PersonalInfoDataM();
					}
					logger.debug("personalType >> "+personalType);
					if(PERSONAL_TYPE_APPLICANT.equals(personalType)){
						String CURRENT_ADDRESS_APPLICANT = SQLQueryEngine.display(AddressType,"SYSTEM_ID5");
						if (FLAG_YES.equals(CURRENT_ADDRESS_APPLICANT)) {
							if(!Util.empty(personalInfoData.getAddress(ADDRESS_TYPE))){
								vAddressType.add(AddressType);
							}
						}
					}else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalType)){
						String CURRENT_ADDRESS_SUPPLEMENTARY = SQLQueryEngine.display(AddressType,"SYSTEM_ID8");
						if (FLAG_YES.equals(CURRENT_ADDRESS_SUPPLEMENTARY)) {
							if(!Util.empty(personalInfoData.getAddress(ADDRESS_TYPE))){
								vAddressType.add(AddressType);
							}
						}
					}
				}else if("COPY_ADDRESS_TYPE_OFFICE".equals(configId)) {
					ModuleFormHandler moduleForm = (ModuleFormHandler)request.getSession().getAttribute("ModuleForm");
					String personalType = moduleForm.getRequestData("PERSONAL_TYPE");
					PersonalInfoDataM personalInfoData = applicationGroup.getPersonalInfo(personalType);
					if(Util.empty(personalInfoData)){
						personalInfoData = new PersonalInfoDataM();
					}
					logger.debug("personalType >> "+personalType);
					if(PERSONAL_TYPE_APPLICANT.equals(personalType)){
						String OFFICE_ADDRESS_APPLICANT = SQLQueryEngine.display(AddressType,"SYSTEM_ID6");
						if (FLAG_YES.equals(OFFICE_ADDRESS_APPLICANT)) {
							if(!Util.empty(personalInfoData.getAddress(ADDRESS_TYPE))){
								vAddressType.add(AddressType);
							}
						}
					}else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalType)){
						String OFFICE_ADDRESS_SUPPLEMENTARY = SQLQueryEngine.display(AddressType,"SYSTEM_ID9");
						if (FLAG_YES.equals(OFFICE_ADDRESS_SUPPLEMENTARY)) {
							if(!Util.empty(personalInfoData.getAddress(ADDRESS_TYPE))){
								vAddressType.add(AddressType);
							}
						}
					}
				}else if("COPY_ADDRESS_TYPE_HOME".equals(configId)) {
					ModuleFormHandler moduleForm = (ModuleFormHandler)request.getSession().getAttribute("ModuleForm");
					String personalType = moduleForm.getRequestData("PERSONAL_TYPE");
					PersonalInfoDataM personalInfoData = applicationGroup.getPersonalInfo(personalType);
					if(Util.empty(personalInfoData)){
						personalInfoData = new  PersonalInfoDataM();
					}
					logger.debug("personalType >> "+personalType);
					String HOME_ADDRESS_APPLICANT = SQLQueryEngine.display(AddressType,"SYSTEM_ID7");
					
					if (FLAG_YES.equals(HOME_ADDRESS_APPLICANT)) {
						if(!Util.empty(personalInfoData.getAddress(ADDRESS_TYPE))){
							vAddressType.add(AddressType);
						}
					}
				}else if(("SEND_DOC_"+PERSONAL_TYPE_APPLICANT).equals(configId)) {
					String SYSTEM_ID3 = SQLQueryEngine.display(AddressType,"SYSTEM_ID3");
					if (FLAG_YES.equals(SYSTEM_ID3)) {
						vAddressType.add(AddressType);
					}
				}else if(("SEND_DOC_"+PERSONAL_TYPE_SUPPLEMENTARY).equals(configId)) {
					String SYSTEM_ID4 = SQLQueryEngine.display(AddressType,"SYSTEM_ID4");
					if (FLAG_YES.equals(SYSTEM_ID4)) {
						vAddressType.add(AddressType);
					}
				}else if(("SEND_DOC").equals(configId)){
					Object object = FormControl.getMasterObjectForm(request);
					if(object instanceof PersonalApplicationInfoDataM){
						String SYSTEM_ID4 = SQLQueryEngine.display(AddressType,"SYSTEM_ID4");
						if (FLAG_YES.equals(SYSTEM_ID4)) {
							vAddressType.add(AddressType);
						}
					}					
				}
			}
		}
		return vAddressType;
	}

	@Override
	public void setFilterProperties(String configId, String cacheId, String typeId,HttpServletRequest request) {
		
	}

	@Override
	public void init(Object objectForm) {
		
	}

}
