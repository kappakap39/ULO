package com.eaf.orig.ulo.pl.app.utility;

import org.apache.log4j.Logger;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.orig.bpm.ulo.model.WorkflowDataM;

public class CBPullJobUtility {
	
	private Logger log = Logger.getLogger(CBPullJobUtility.class);
	
	private static CBPullJobUtility me;
	
	public static synchronized CBPullJobUtility GetInstance(){
		if(me == null){
			me = new CBPullJobUtility();
		}
		return me;
	}
		
	public synchronized void CBPullJob(WorkflowDataM workflowM, UserDetailM userM) throws Exception{
		try{			
			log.debug("[CBPullJob]..");			
			PLORIGApplicationManager origBean = PLORIGEJBService.getPLORIGApplicationManager();
				origBean.PullWorkflowJob(workflowM, userM);			
		}catch(Exception e){
			log.error("CBpullJob error ", e);
			throw new Exception("Exception in CBpullJob >> "+e.getMessage());
		}
	}
	
}
