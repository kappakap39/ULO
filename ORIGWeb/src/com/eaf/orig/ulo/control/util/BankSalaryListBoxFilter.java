package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;

public class BankSalaryListBoxFilter  implements ListBoxFilterInf {
	private static transient Logger logger = Logger.getLogger(BankSalaryListBoxFilter.class);	
	String BANK_SALARY_LIST_BOX_ID = SystemConstant.getConstant("BANK_SALARY_LIST_BOX_ID");
	String BANK_SALARY_ADDITIONAL_LIST_BOX_ID = SystemConstant.getConstant("BANK_SALARY_ADDITIONAL_LIST_BOX_ID");
	String BANK_SALARY_TYPE_S = SystemConstant.getConstant("BANK_SALARY_TYPE_S");
	String BANK_SALARY_TYPE_A = SystemConstant.getConstant("BANK_SALARY_TYPE_A");
	String SALARY_TYPE = SystemConstant.getConstant("SALARY_TYPE");
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {
		ArrayList<HashMap<String,Object>> filterListBoxList = new ArrayList<>();		
		String BANK_CODE = request.getParameter("BANK_CODE");
		try {
			ArrayList<HashMap<String,Object>> List = CacheControl.search(cacheId, "BANK_CODE", BANK_CODE);
			logger.debug(">>>List>>"+List);
			logger.debug(">>>configId>>"+configId);
			logger.debug(">>>cacheId>>"+cacheId);
			logger.debug(">>>BANK_CODE>>"+BANK_CODE);
			if(null != List){
				for (HashMap<String, Object> bankSalary : List){
					String SALARY_TYPE_VAL = SQLQueryEngine.display(bankSalary,SALARY_TYPE);
					if(BANK_SALARY_LIST_BOX_ID.equals(configId) && BANK_SALARY_TYPE_S.equals(SALARY_TYPE_VAL)){
						filterListBoxList.add(bankSalary);
					}else if(BANK_SALARY_ADDITIONAL_LIST_BOX_ID.equals(configId) && BANK_SALARY_TYPE_A.equals(SALARY_TYPE_VAL)){
						filterListBoxList.add(bankSalary);
					}
				}
			}				
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return filterListBoxList;
	}

	@Override
	public void setFilterProperties(String configId, String cacheId,
			String typeId, HttpServletRequest request) {
	}

	@Override
	public void init(Object objectForm) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
}
