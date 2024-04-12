package com.eaf.orig.shared.utility;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;

//import com.eaf.orig.cache.util.ORIGCacheUtil;
//import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.pl.config.ORIGConfig;
import com.eaf.orig.ulo.pl.scheduler.task.PLSchedulerManager;
import com.eaf.orig.ulo.pl.scheduler.task.PLSchedulerManagerHome;

public class OrigSchedulerUtil {
	static OrigSchedulerUtil me;
	static Logger  logger = Logger.getLogger(OrigSchedulerUtil.class);
	public static OrigSchedulerUtil getInstance() {
		if (me == null) {
			me = new OrigSchedulerUtil();
		}
		return me;
	}
	
	public PLSchedulerManager getSchedulerManager(){
		try{
			
//			String providerAddress = ORIGCacheUtil.getInstance().getGeneralParamByCode(OrigConstant.GeneralParamCode.SCHEDULER_EJB_SERVER);
//			String providerPort = ORIGCacheUtil.getInstance().getGeneralParamByCode(OrigConstant.GeneralParamCode.SCHEDULER_EJB_PORT);
			
			String providerURL = "iiop://" + ORIGConfig.SCHEDULER_EJB_URL + ":" + ORIGConfig.SCHEDULER_EJB_PORT;
			logger.info("[Scheduler Service] providerURL : " + providerURL);
			
			Hashtable<String,String> env = new Hashtable<String, String>();
			
			env.put(Context.INITIAL_CONTEXT_FACTORY,"com.ibm.websphere.naming.WsnInitialContextFactory");
			env.put(Context.PROVIDER_URL,providerURL);				
			Context ctx = new InitialContext(env);
			
			Object schedulerHandlerObj = ctx.lookup(JNDI.SCHEDULER_MANAGER_JNDI_NAME);
			PLSchedulerManagerHome schedulerHome = (PLSchedulerManagerHome)PortableRemoteObject.narrow(schedulerHandlerObj, PLSchedulerManagerHome.class);
			return schedulerHome.create();
		}catch(Exception e){
			logger.fatal("Exception ",e);
		}
		return null;
	}
	public static class JNDI{
		public static final String SCHEDULER_JNDI_NAME = "sched/orig_scheduler";
		public static final String SCHEDULER_MANAGER_JNDI_NAME = "ejb/PLSchedulerManagerHome";
		public static final String SCHEDULER_PROCESS_JNDI_NAME = "ejb/PLSchedulerProcessHome";
	}
}
