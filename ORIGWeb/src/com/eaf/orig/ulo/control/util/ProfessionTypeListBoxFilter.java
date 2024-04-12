package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.constant.MConstant;

public class ProfessionTypeListBoxFilter implements ListBoxFilterInf
{
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {
		ArrayList<HashMap<String,Object>> List = ListBoxControl.getListBox(cacheId,"ACTIVE");
		ArrayList<HashMap<String,Object>> OccupationSelect = new ArrayList<>();
		String flag = MConstant.FLAG.YES;
		String mainProfession = SystemConstant.getConstant("FILTER_PROFESSION_MAIN");
		String otherProfession = SystemConstant.getConstant("FILTER_PROFESSION_OTHER");
		
		if(null != List){
			for (HashMap<String, Object> Occupation : List) {
				if((mainProfession).equals(configId))
				{
					String SYSTEM_ID1 = SQLQueryEngine.display(Occupation,"SYSTEM_ID1");
					if(flag.equals(SYSTEM_ID1))
					{
						OccupationSelect.add(Occupation);
					}
				}
				else if((otherProfession).equals(configId))
				{
					String SYSTEM_ID2 = SQLQueryEngine.display(Occupation,"SYSTEM_ID2");
					if(flag.equals(SYSTEM_ID2))
					{
						OccupationSelect.add(Occupation);
					}
				}
			}
		}
		return OccupationSelect;
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
