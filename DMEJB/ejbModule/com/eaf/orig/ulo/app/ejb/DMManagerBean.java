package com.eaf.orig.ulo.app.ejb;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.ava.bpm.model.response.WorkflowResponse;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.ejb.view.DMManager;
import com.eaf.orig.ulo.app.factory.DMModuleFactory;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.dm.dao.DMDocDAO;
import com.eaf.orig.ulo.model.dm.DocumentManagementDataM;
import com.orig.bpm.ulo.model.WorkflowDataM;
import com.orig.bpm.workflow.proxy.BPMWorkflowDMProxy;

/**
 * Session Bean implementation class DMManagerBean
 */
@Stateless
@Local(DMManager.class)
@LocalBean
public class DMManagerBean implements DMManager {
	private static transient Logger logger = Logger.getLogger(DMManagerBean.class);
    public DMManagerBean() {
    	super();
    }

	@Override
	public void saveDMStore(DocumentManagementDataM documentManage,UserDetailM userM) {
		try {
			logger.debug("##saveDMStore##");
			DMDocDAO dmDao = DMModuleFactory.getDMDocDAO();
			dmDao.saveTableDMDoc(documentManage,true);
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
    		throw new EJBException(e.getMessage());
		}
		
	}

	@Override
	public void saveDMBorrow(DocumentManagementDataM documentManage,UserDetailM userM) {
		try{
			logger.debug("##saveDMBorrow##");
			DMDocDAO dmDao = DMModuleFactory.getDMDocDAO();			
			String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
			String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
			BPMWorkflowDMProxy proxy = new BPMWorkflowDMProxy(BPM_HOST,BPM_PORT);			
			WorkflowDataM workflowM = new WorkflowDataM();		
			workflowM.setInstantId(Integer.parseInt(documentManage.getRefNo1()));
			workflowM.setBpdId(SystemConfig.getProperty("DM_BPD_ID"));
			workflowM.setProcessAppId(SystemConfig.getProperty("DM_PROCESS_APP_ID"));
			WorkflowResponse workResp =  new WorkflowResponse();
			if(!Util.empty(documentManage.getHistory())){
				if(MConstant.DM_MANAGEMENT_ACTION.BORROW_ACTION.equals(documentManage.getHistory().getAction())){
					workResp= proxy.borrowDocument(workflowM);
					documentManage.setRefNo1(String.valueOf(workResp.getInstantId()));
				}else if(MConstant.DM_MANAGEMENT_ACTION.RETURN_ACTION.equals(documentManage.getHistory().getAction())){
					workResp= proxy.returnDocument(workflowM);
					documentManage.setRefNo1(String.valueOf(workResp.getInstantId()));
				}	
			}
			logger.debug("workResp.getInstantId()>>>"+workResp.getInstantId());
			dmDao.saveTableDMDoc(documentManage,true);			
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
    		throw new EJBException(e.getMessage());
		}
		
	}
    

}
