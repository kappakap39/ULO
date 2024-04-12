package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class PlaceListBoxFilter implements ListBoxFilterInf
{
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,
			String cacheId, String typeId, HttpServletRequest request) {
		//Incident 1238223
		//ArrayList<HashMap<String,Object>> List = ListBoxControl.getListBox(cacheId,"ACTIVE");
		ArrayList<HashMap<String,Object>> List = ListBoxControl.getListBox(cacheId, typeId == null ? "ACTIVE" : typeId);
		
		ArrayList<HashMap<String,Object>> vPlaceReceive = new ArrayList<>();
		String flag = MConstant.FLAG.YES;
		String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
		String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
		
		if(null != List){
			for (HashMap<String, Object> AddressType : List) {
				if(("PLACE_RECEIVE_CARD_"+PERSONAL_TYPE_APPLICANT).equals(configId))
				{
					String SYSTEM_ID1 = SQLQueryEngine.display(AddressType,"SYSTEM_ID1");
					if(flag.equals(SYSTEM_ID1))
					{
						vPlaceReceive.add(AddressType);
					}
				}
				else if(("PLACE_RECEIVE_CARD_"+PERSONAL_TYPE_SUPPLEMENTARY).equals(configId))
				{
					String SYSTEM_ID2 = SQLQueryEngine.display(AddressType,"SYSTEM_ID2");
					if(flag.equals(SYSTEM_ID2))
					{
						vPlaceReceive.add(AddressType);
					}
				}
			}
		}
		return vPlaceReceive;
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
