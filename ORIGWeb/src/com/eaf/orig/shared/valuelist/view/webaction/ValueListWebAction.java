package com.eaf.orig.shared.valuelist.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.dao.ValueListDAO;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
//import com.eaf.orig.shared.dao.ValueListDAO;
import com.eaf.orig.shared.model.ValueListM;
//import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author unchan
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ValueListWebAction extends WebActionHelper implements WebAction {
	
	private static Logger logger = Logger.getLogger(ValueListWebAction.class);
	public static final String VALUE_LIST_SEARCH_CODE = "VALUE_LIST_SEARCH_CODE";
	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		return null;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {
		return false;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		try{
			ValueListM valueListM = (ValueListM)getRequest().getSession().getAttribute("VALUE_LIST");
			//	For check master data
			String dataValue = null;
			boolean masterFlag = false;
			if( null != getRequest().getSession().getAttribute("dataValue") ) {
				masterFlag = true;
				dataValue = (String) getRequest().getSession().getAttribute("dataValue");
			} else if( null != getRequest().getSession().getAttribute("checkMaster") ) {
				masterFlag = true;
				dataValue = "";
				logger.debug("masterFlag >> "+masterFlag);
			}
			int atPage = 1;
			
			int itemsPerPage = valueListM.getItemsPerPage();
			
			String atPageStr = getRequest().getParameter("atPage");
			
			// Set itemsPerPageStr = 20 by Vikrom
			String itemsPerPageStr = getRequest().getParameter("itemsPerPage");
			if(itemsPerPageStr == null || "".equals(itemsPerPageStr)){
				itemsPerPageStr = "20";
			}
			
			//String itemsPerPageStr = "20";
			logger.debug("atPageStr : "+atPageStr);
			
			logger.debug("itemsPerPageStr : "+itemsPerPageStr);
			
			if(atPageStr!=null && !atPageStr.equals("")) atPage = Integer.parseInt(atPageStr);
			
			if(itemsPerPageStr!=null && !itemsPerPageStr.equals("")){ 
				int itemsPerPageTemp = Integer.parseInt(itemsPerPageStr);
				if(itemsPerPage!=itemsPerPageTemp){ 
					itemsPerPage = itemsPerPageTemp;
					valueListM.setAtPage(1);
				}
			}
			
			valueListM.setItemsPerPage(itemsPerPage);
			valueListM.setAtPage(atPage);
			
			logger.debug("valueListM.getAtPage()"+valueListM.getAtPage());
			
//			valueListM = (ValueListM)getRequest().getSession().getAttribute("VALUE_LIST");
			
//			ValueListDAO valueListDAO = ORIGDAOFactory.getValueListDAO();
			
			logger.debug("valueListM.getSqlCriteria() >> "+valueListM.getSqlCriteria().size());
			logger.debug("masterFlag >>>"+masterFlag);
			logger.debug("valueListM >>"+valueListM);
			
			
			if(valueListM.getSqlCriteria() != null && !valueListM.getSqlCriteria().isEmpty()){
				if(masterFlag) {
					ValueListDAO valueListDAO = ORIGDAOFactory.getValueListDAO(valueListM.getDbType());
					valueListM = valueListDAO.getResult_master2(valueListM, dataValue);
					logger.debug("valueListM .. "+valueListM);
				} else {
					ValueListDAO valueListDAO = ORIGDAOFactory.getValueListDAO(valueListM.getDbType());
					valueListM = valueListDAO.getResult2(valueListM);
				}
				logger.debug("num of result :" + valueListM.getResult().size());
				System.out.println("num of result1 :" + valueListM.getResult().size()); //pae debug
			}else{
				if(masterFlag) {
					ValueListDAO valueListDAO = ORIGDAOFactory.getValueListDAO(valueListM.getDbType());
					valueListM = valueListDAO.getResult_master(valueListM, dataValue);
					logger.debug("valueListM Size Not SqlCriteria.. "+valueListM.getResult().size());
				} else {
					ValueListDAO valueListDAO = ORIGDAOFactory.getValueListDAO(valueListM.getDbType());
					valueListM = valueListDAO.getResult(valueListM);
				}
				logger.debug("num of result2 :" + valueListM.getResult().size());
			}
			getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
			getRequest().getSession().setAttribute("dataValue", dataValue);
		}catch(Exception e){
			logger.fatal(e.toString());
			return false;
		}
		
		return true;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		ValueListM valueListM = (ValueListM)getRequest().getSession().getAttribute("VALUE_LIST");
		if(valueListM.getReturnToAction().equals("")) return FrontController.PAGE;
		return FrontController.ACTION;
	}

	public String getNextActionParameter(){
		ValueListM valueListM = (ValueListM)getRequest().getSession().getAttribute("VALUE_LIST");
		if(!valueListM.getReturnToAction().equals("")) return valueListM.getReturnToAction();
		return valueListM.getReturnToPage();
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
