package com.eaf.orig.ulo.pl.app.utility;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class OrigBusinessClassUtil {
	
	Logger logger = Logger.getLogger(this.getClass().getName());
	static OrigBusinessClassUtil me;
	
	public static OrigBusinessClassUtil getInstance() {
		if (me == null) {
			me = new OrigBusinessClassUtil();
		}
		return me;
	}
	
	public boolean isContainsBusGroup(PLApplicationDataM applicationM, String busGroup){
		boolean result = false;
		try{
			HashMap<String, String> busClassHash = PLORIGEJBService.getORIGDAOUtilLocal().getBusClassByBusGroup(busGroup);
			if(busClassHash.containsKey(applicationM.getBusinessClassId())){
				result = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean isContainsBusClass(String busClass, String busGroup){
		boolean result = false;
		try{
			HashMap<String, String> busGroupHash = PLORIGEJBService.getORIGDAOUtilLocal().getBusGroupByBusClass(busClass);
			if(busGroupHash.containsKey(busGroup)){
				result = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

}
