package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class PersonalSupplementaryListBoxFilter implements ListBoxFilterInf{
	Logger logger = Logger.getLogger(PersonalSupplementaryListBoxFilter.class);
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {
		String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ArrayList<PersonalInfoDataM> List = ORIGForm.getObjectForm().getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
		ArrayList<HashMap<String,Object>> Select = new ArrayList<>();		
		if(null != List){
			for (PersonalInfoDataM personalInfo : List) {
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("CODE",personalInfo.getPersonalId());
				map.put("VALUE",personalInfo.getThName());
				Select.add(map);
			}
		}
		return Select;
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
