package com.eaf.orig.shared.valuelist.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class ValueListDialogWebAction extends WebActionHelper implements WebAction{
	static Logger logger = Logger.getLogger(ValueListDialogWebAction.class);
	public static final String VALUE_LIST_SEARCH_CODE = "VALUE_LIST_SEARCH_CODE";
	@Override
	public Event toEvent(){
		return null;
	}

	@Override
	public boolean requiredModelRequest(){
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response){
		return false;
	}

	@Override
	public boolean preModelRequest(){
		try{
			ValueListM valueListM = (ValueListM)getRequest().getSession().getAttribute("DIALOG_LIST");
			
			int atPage = 1;
			if (valueListM.getAtPage()!=0) atPage = valueListM.getAtPage();
			
			int itemsPerPage = valueListM.getItemsPerPage();
			
			String atPageStr = getRequest().getParameter("atPage");			
			String itemsPerPageStr = getRequest().getParameter("itemsPerPage");
						
			if(OrigUtil.isEmptyString(itemsPerPageStr)){
				itemsPerPageStr = "20";
			}
			
			if(!OrigUtil.isEmptyString(atPageStr)) atPage = Integer.parseInt(atPageStr);
			
			if(!OrigUtil.isEmptyString(itemsPerPageStr)){ 
				int itemsPerPageTemp = Integer.parseInt(itemsPerPageStr);
				if(itemsPerPage!=itemsPerPageTemp){ 
					itemsPerPage = itemsPerPageTemp;
					valueListM.setAtPage(1);
				}
			}
			
			logger.debug("atPage : "+atPage);			
			logger.debug("itemsPerPage : "+itemsPerPage);
			
			valueListM.setItemsPerPage(itemsPerPage);
			valueListM.setAtPage(atPage);
						
			ORIGDAOUtilLocal origDAOBean =  PLORIGEJBService.getORIGDAOUtilLocal();
			
			if(valueListM.getSqlCriteria() != null && !valueListM.getSqlCriteria().isEmpty()){
				valueListM = origDAOBean.getResult2(valueListM);
			}else{
				valueListM = origDAOBean.getResult(valueListM);
			}
						
			getRequest().getSession().setAttribute("DIALOG_LIST", valueListM);

			com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
				(com.eaf.j2ee.pattern.control.ScreenFlowManager) getRequest().getSession(true).getAttribute("screenFlowManager");
				screenFlowManager.setCurrentScreen(valueListM.getCurrentScreen());
			
		}catch(Exception e){
			logger.fatal("Exception >> ",e);
		}		
		return true;
	}

	@Override
	public int getNextActivityType(){
		return FrontController.POPUP_DIALOG;
	}
	
	@Override
	public String getNextActionParameter() {
		ValueListM valueListM = (ValueListM)getRequest().getSession().getAttribute("DIALOG_LIST");
		logger.debug("valueListM.getReturnToAction() >> "+valueListM.getReturnToAction());
		if(!OrigUtil.isEmptyString(valueListM.getReturnToAction())) 
				return valueListM.getReturnToAction();
		return valueListM.getReturnToPage();
	}
	
	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
