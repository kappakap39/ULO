package com.eaf.orig.scheduler;

import java.util.ArrayList;

import javax.ejb.Schedule;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.app.dao.BpmTaskDAO;
import com.eaf.orig.ulo.app.dao.BpmTaskDAOImpl;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.orig.bpm.proxy.BpmProxyConstant;

/**
 * Session Bean implementation class WMTaskScheduler
 */
@Stateless
public class BpmTaskSchedule {

	private static Logger logger = Logger.getLogger(BpmTaskSchedule.class);
	private static String eAppInstancePrefix = BpmProxyConstant.EAPP_INSTANCE_PREFIX;
	
	@Schedule(hour="*", minute="0/1", second="0")
	public void SchedDVGetBpmInstance(){
		logger.debug("============== Start Get Bpd Instance ==============");
		try {
			BpmTaskDAO bpmTaskDao = new BpmTaskDAOImpl();
			ArrayList<String> roles = new ArrayList<String>();
			roles.add(MConstant.ROLE.DV);
			roles.add(MConstant.ROLE.DVS1);
			roles.add(MConstant.ROLE.DVS1S3);
			String result = bpmTaskDao.foundEappTaskInstance(roles, eAppInstancePrefix);
			logger.debug("foundEappDVTaskInstance [result] : " + result);
			
			// Udpate Cache
			bpmTaskDao.saveUpdateCacheParameter(BpmProxyConstant.RoleCache.FOUND_EAPP_DV_CENTRAL_QUEUE, result);
			
			// Refresh Cache
			CacheController.refreshCache( CacheConstant.CacheName.CACHE_PARAMETER );
			
		} catch (Exception e) {
			logger.fatal("Error : ",e);
		}
		logger.debug("============== End Get Bpd Instance ==============");
	}
	
	@Schedule(hour="*", minute="0/1", second="10")
	public void SchedDE12GetBpmInstance(){
		logger.debug("============== Start Get Bpd Instance ==============");
		try {
			BpmTaskDAO bpmTaskDao = new BpmTaskDAOImpl();
			ArrayList<String> roles = new ArrayList<String>();
			roles.add(MConstant.ROLE.BPMDE1_2);
			String result = bpmTaskDao.foundEappTaskInstance(roles, eAppInstancePrefix);
			logger.debug("foundEappDE1_2TaskInstance [result] : " + result);
			
			// Udpate Cache
			bpmTaskDao.saveUpdateCacheParameter(BpmProxyConstant.RoleCache.FOUND_EAPP_DE1_2_CENTRAL_QUEUE, result);
			
			// Refresh Cache
			CacheController.refreshCache( CacheConstant.CacheName.CACHE_PARAMETER );
			
		} catch (Exception e) {
			logger.fatal("Error : ",e);
		}
		logger.debug("============== End Get Bpd Instance ==============");
	}
}
