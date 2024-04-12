package com.eaf.orig.ulo.control.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;

public class SalaryUtil {
	private static transient Logger logger = Logger.getLogger(SalaryUtil.class);
	
	
	public static String getSalaryCode(BigDecimal salary){
		String salaryCode = "";
		try{
			if(Util.empty(salary)){
				String FIELD_ID_SALARY_RANGE = SystemConstant.getConstant("FIELD_ID_SALARY_RANGE");
				ArrayList<HashMap> SALARY_RANGE_LIST_BOX_MASTER = CacheControl.getCacheList(FIELD_ID_SALARY_RANGE);
				if(!Util.empty(SALARY_RANGE_LIST_BOX_MASTER)){
					for(HashMap SALARY_RANGE : SALARY_RANGE_LIST_BOX_MASTER){
						BigDecimal START_RANGE = new BigDecimal(SALARY_RANGE.get("SYSTEM_ID1").toString());
						BigDecimal END_RANGE = new BigDecimal(SALARY_RANGE.get("SYSTEM_ID2").toString());
						if(salary.floatValue() >= START_RANGE.floatValue() && salary.floatValue() <= END_RANGE.floatValue()){
							return SALARY_RANGE.get("MAPPING4").toString();
						}
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return salaryCode;
	}
}
