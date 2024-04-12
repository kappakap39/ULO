package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class NationalityListBoxFilter implements ListBoxFilterInf{
	public static final String EDIT = "EDIT";
	public static final String VIEW = "VIEW";
	private static transient Logger logger = Logger.getLogger(NationalityListBoxFilter.class);
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String NATIONALITY_STATELESS = SystemConstant.getConstant("NATIONALITY_STATELESS");
	String CIDTYPE_MIGRANT = SystemConstant.getConstant("CIDTYPE_MIGRANT");
	String CIDTYPE_NON_THAI_NATINALITY = SystemConstant.getConstant("CIDTYPE_NON_THAI_NATINALITY");
	String FIELD_ID_NATIONALITY = SystemConstant.getConstant("FIELD_ID_NATIONALITY");
	String CIS_CUST_TYPE_STATELESS = SystemConstant.getConstant("CIS_CUST_TYPE_STATELESS");
	String displayMode;
	String CACHE_NATIONALITY = SystemConstant.getConstant("CACHE_NATIONALITY");
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {
		
		
		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		PersonalInfoDataM personalInfo = null;
		ArrayList<HashMap<String,Object>> List = ListBoxControl.getListBox(cacheId,"ACTIVE");
		ArrayList<HashMap<String,Object>> vNationality = new ArrayList<>();
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
		
		
		String CID_TYPE = personalInfo.getCidType();
		String CIS_NO = personalInfo.getCisNo();
		String CIS_CUST_TYPE = personalInfo.getCisCustType();
		String idNo = personalInfo.getIdno();
		logger.debug("CID_TYPE : "+CID_TYPE+"| CIS_NO : "+CIS_NO+"| CIS_CUST_TYPE : "+CIS_CUST_TYPE);
		logger.debug("displayMode >> " + displayMode);
		
		if(EDIT.equals(displayMode)){
			if(CIDTYPE_NON_THAI_NATINALITY.equals(CID_TYPE)){
				
				ArrayList<HashMap<String, Object>> MYANMAR = CacheControl.search(CACHE_NATIONALITY, "CODE", "MM");
				ArrayList<HashMap<String, Object>> CAMBODIA = CacheControl.search(CACHE_NATIONALITY, "CODE", "KH");
				ArrayList<HashMap<String, Object>> LAO = CacheControl.search(CACHE_NATIONALITY, "CODE", "LA");
				ArrayList<HashMap<String, Object>> NOT_HAVE_NATION = CacheControl.search(CACHE_NATIONALITY, "CODE", "XX");
				ArrayList<HashMap<String, Object>> THAI = CacheControl.search(CACHE_NATIONALITY, "CODE", "TH");
				//logger.debug("idNo >> " + idNo);
				String ID_NO = request.getParameter("ID_NO");
				
				if(!Util.empty(ID_NO)){
					idNo=ID_NO;
				}
				logger.debug("idNo >> " + idNo);
				
				if(!Util.empty(idNo)&&idNo.length()>=13){
					if("00".equals(idNo.substring(0, 2))){
						vNationality.addAll(MYANMAR);
						vNationality.addAll(CAMBODIA);
						vNationality.addAll(LAO);
					}else if("0".equals(idNo.substring(0, 1))){
						vNationality.addAll(NOT_HAVE_NATION);
						//personalInfo.setNationality("XX");;
						//this.displayMode = VIEW;
					}else if("6".equals(idNo.substring(0, 1))&&inLength(Integer.valueOf(idNo.substring(5, 7)),0,49)){
						vNationality.addAll(MYANMAR);
						vNationality.addAll(CAMBODIA);
						vNationality.addAll(LAO);
					}else if("6".equals(idNo.substring(0, 1))&&inLength(Integer.valueOf(idNo.substring(5, 7)),50,72)){
						vNationality.addAll(NOT_HAVE_NATION);
						//personalInfo.setNationality("XX");
						//this.displayMode = VIEW;
					}else{
						vNationality.addAll(THAI);
						//personalInfo.setNationality("TH");
						//this.displayMode = VIEW;
					}
				}
			
			}
			else if( !(CIDTYPE_MIGRANT.equals(CID_TYPE) && Util.empty(CIS_NO)) && 
				    !(CIDTYPE_MIGRANT.equals(CID_TYPE) && CIS_CUST_TYPE_STATELESS.equals(CIS_CUST_TYPE) && !Util.empty(CIS_NO))){
					if(null != List){
						for (HashMap<String, Object> nationality : List){		
							String value = SQLQueryEngine.display(nationality,"CHOICE_NO");
							if(!NATIONALITY_STATELESS.equals(value)){
								vNationality.add(nationality);
							}
						}
					}
				}
		}
		
		
		if(Util.empty(vNationality)){
			return List;
		}
		return vNationality;
	}

	public boolean inLength(Integer value,Integer start ,Integer end){
		if(value>=start && value <= end){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public void setFilterProperties(String configId, String cacheId,
			String typeId, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(Object objectForm) {
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		this.displayMode = displayMode;
	}
}
