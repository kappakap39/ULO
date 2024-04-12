package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class ThTitleCodeFilter implements ListBoxFilterInf{
	private static transient Logger logger = Logger.getLogger(ThTitleCodeFilter.class);
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String CIDTYPE_IDCARD = SystemConstant.getConstant("CIDTYPE_IDCARD");
	String CIDTYPE_PASSPORT = SystemConstant.getConstant("CIDTYPE_PASSPORT");
	String CIDTYPE_MIGRANT = SystemConstant.getConstant("CIDTYPE_MIGRANT");
	String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE");
	String FIELD_ID_EN_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_EN_TITLE_CODE");
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {
		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		PersonalInfoDataM personalInfo = null;
		ArrayList<HashMap<String,Object>> List = new ArrayList<>();
		ArrayList<HashMap<String,Object>> vThTitleCode = new ArrayList<>();
		
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
		String CID_TYPE = personalInfo.getCidType();
		List = ListBoxControl.getListBox(FIELD_ID_TH_TITLE_CODE,"ACTIVE");
		/*if(CIDTYPE_PASSPORT.equals(CID_TYPE)) {
			List = ListBoxControl.getListBox(FIELD_ID_EN_TITLE_CODE,"ACTIVE");
		}else{
			List = ListBoxControl.getListBox(FIELD_ID_TH_TITLE_CODE,"ACTIVE");
		}
		*/
		if(null != List){
			return List;
		}
		return vThTitleCode;
	}

	@Override
	public void setFilterProperties(String configId, String cacheId,String typeId, HttpServletRequest request) {		
		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		PersonalInfoDataM personalInfo = null;
		if(masterObjectForm instanceof ApplicationGroupDataM){			
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			personalInfo = personalApplicationInfo.getPersonalInfo();
		}		
		if(null != personalInfo){
			String CID_TYPE = request.getParameter("CID_TYPE");
			logger.debug("CID_TYPE >> "+CID_TYPE);
			personalInfo.setCidType(CID_TYPE);
		}
	}

	@Override
	public void init(Object objectForm) {
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
}
