package com.eaf.service.common.controller;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.RefreshCacheController;
import com.eaf.ulo.cache.dao.CacheServiceLocator;
import com.eaf.ulo.cache.dao.engine.SQLQueryController;

@Stateless
public class ApplicationDateController {
	private static transient Logger logger = Logger.getLogger(ApplicationDateController.class);
	@EJB ApplicationDateController bean;
	@Schedule(second="0", minute="0", hour="6", dayOfWeek="*",dayOfMonth="*", month="*", year="*", info="Schedule Application Date")
    private void scheduledTimeout(final Timer t) {
		logger.info("Timer : "+t);
		bean.updateApplicaitonDate();
		try{
			RefreshCacheController.execute(CacheConstant.CacheName.APPLICATION_DATE);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
    }
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateApplicaitonDate(){
		SQLQueryController query = new SQLQueryController();
		int relativeDate = query.getRelativeDate(CacheServiceLocator.ORIG_DB);
		logger.debug("relativeDate >> "+relativeDate);
		query.updateApplicaitonDate(CacheServiceLocator.ORIG_DB, relativeDate);
	}
}