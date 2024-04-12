package com.avalant.wm.timer;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.avalant.wm.proxy.WorkManagerProxy;
import com.avalant.wm.work.WorkTask;
import com.ibm.websphere.asynchbeans.WorkException;
import com.ibm.websphere.asynchbeans.WorkItem;


public class WorkTimer {
	
	Logger logger = Logger.getLogger(WorkTimer.class);

    public WorkTimer() {
      
    }
	
    public void startTask(){
    	WorkTask work = new WorkTask();
    	WorkItem item1;
    	try {
			item1 = WorkManagerProxy.lookup().startWork(work);
			
		} catch (WorkException e) {
			e.printStackTrace();
			logger.error("Error WorkException : ",e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("Error IllegalArgumentException : ",e);
		} catch (NamingException e) {
			e.printStackTrace();
			logger.error("Error NamingException : ",e);
		}
    }
}