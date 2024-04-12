package com.avalant.wm.scheduler;

import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.avalant.wm.dao.WmDAOFactory;
import com.avalant.wm.lookup.LookUpWMManager;
import com.avalant.wm.model.WmTask;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;


public class AssignTask extends TimerTask {

	private Logger logger = Logger.getLogger(AssignTask.class);
	private String WORK_MANAGER01 = SystemConstant.getConstant("WORK_MANAGER01");
	private String WORK_MANAGER02 = SystemConstant.getConstant("WORK_MANAGER02");
	
	@Override
	public void run() {
		logger.debug("############## Start Assign Task ##############");
		boolean server1 = true;
		try {
			String lastAssign = WmDAOFactory.getWMDAO().getLastAssign();
			if(!Util.empty(lastAssign) && WORK_MANAGER01.equals(lastAssign)){
				server1 = false;
			}
			List<WmTask> taskList = WmDAOFactory.getWMDAO().getNotTaskAssign();
			for(WmTask task : taskList){
				if(server1){
					task.setServerName(WORK_MANAGER01);
					server1 = false;
				}
				else{
					task.setServerName(WORK_MANAGER02);
					server1 = true;
				}
				LookUpWMManager.getWmManager().saveWorkTask(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error : ",e);
		}
		
		logger.debug("############## End Assign Task ##############");
	}

}
