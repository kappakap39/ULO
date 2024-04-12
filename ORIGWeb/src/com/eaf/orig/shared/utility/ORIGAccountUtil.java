package com.eaf.orig.shared.utility;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class ORIGAccountUtil {
	Logger logger = Logger.getLogger(this.getClass().getName());
	static ORIGAccountUtil me;
	
	public static ORIGAccountUtil getInstance() {
		if (me == null) {
			me = new ORIGAccountUtil();
		}
		return me;
	}
	
	public PLAccountCardDataM loadAccountCardByAppNo(String appNo){
		try{
//			return PLORIGDAOFactory.getPLOrigRuleDAO().getRulesDetailsConfig(busClass);
			return PLORIGEJBService.getORIGDAOUtilLocal().loadAccountCardByAppNo(appNo);
		}catch (Exception e){
			logger.fatal("##### getRulesDetailsConfig error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
