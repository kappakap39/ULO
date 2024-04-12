package com.eaf.orig.ulo.app.view.webaction;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.ulo.app.factory.DMModuleFactory;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.dm.dao.DMApplicationGroupDAO;
import com.eaf.orig.ulo.dm.dao.DMDocDAO;
import com.eaf.orig.ulo.dm.dao.DMTransactionDAO;
import com.eaf.orig.ulo.formcontrol.view.form.DMFormHandler;
import com.eaf.orig.ulo.model.dm.DocumentManagementDataM;
import com.eaf.orig.ulo.model.dm.HistoryDataM;

public class LoadDMDocManagementWebAction extends WebActionHelper implements WebAction {
	private static transient Logger logger = Logger.getLogger(LoadDMDocManagementWebAction.class);
	private String nextAction = null;
	
	@Override
	public Event toEvent() {
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	@Override
	public boolean preModelRequest() {
		try {
			String dmId  = getRequest().getParameter("DM_ID");
			logger.debug("DM_ID:"+dmId);
			DocumentManagementDataM dmManageDataM =  new DocumentManagementDataM();
			
			DMDocDAO  dmDao = DMModuleFactory.getDMDocDAO();
			dmManageDataM = dmDao.selectTableDMDoc(dmId);
			
			DMTransactionDAO transDao = DMModuleFactory.getDMTransactionDAO();
			ArrayList<HistoryDataM> dmTransList = transDao.selectTableDMTransaction(dmId);
			dmManageDataM.setHistoryLog(dmTransList);
						
			DMApplicationGroupDAO appGroupDao   =DMModuleFactory.getDMApplicationGroupDAO();
			appGroupDao.setApplicationInfo(dmManageDataM);
			
			DMFormHandler formHandler = new DMFormHandler();
			formHandler.setFormId(MConstant.DM_FORM_NAME.DM_BORROW_FORM);
			formHandler.setObjectForm(dmManageDataM);
			getRequest().getSession().setAttribute("DMForm",formHandler);
						
			nextAction="page=DM_BORROW_DOCUMENT_FORM";
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}
	
	@Override
	public String getNextActionParameter() {
        return nextAction;
    }
}
