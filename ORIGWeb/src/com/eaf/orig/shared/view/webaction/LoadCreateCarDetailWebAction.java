package com.eaf.orig.shared.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
//import com.eaf.orig.shared.dao.ValueListDAO;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LoadCreateCarDetailWebAction extends WebActionHelper implements WebAction {
	static Logger log = Logger.getLogger(LoadCreateCarDetailWebAction.class);
    private String nextAction = null;
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
	    log.debug("++++++++++++++ Start LoadCreateCarDetailWebAction  ++++++++++++++++++++");
		getRequest().getSession().removeAttribute("ORIGForm");
		ORIGFormHandler ORIGForm = new ORIGFormHandler();
		ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");		
		if(ORIGForm == null){
				ORIGForm = new ORIGFormHandler();
		}
		getRequest().getSession().setAttribute("ORIGForm",ORIGForm);	
	 	
	 	UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
	 	Vector userRoles = userM.getRoles();
		String clientGroup = getRequest().getParameter("selectClientGroup");
		
		try {
        	StringBuffer sql = new StringBuffer();
        	StringBuffer sqlTemp = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			ValueListM valueListMCoClient = new ValueListM();
			int index = 0;
			int indexCoClient = 0;
			
			sql.append("SELECT (SELECT THDESC FROM HPTBHP07 WHERE CLTGRP = CLIENT_GROUP) AS CLIENT_GROUP, FINAL_CREDIT_LIMIT, TO_CHAR(CREDIT_EXPIRY_DATE,'dd/mm/yyyy') ");
			sql.append(",(SELECT SUM(L.COST_OF_FINANCIAL_AMT) FROM ORIG_VEHICLE V, ORIG_LOAN L, ORIG_PERSONAL_INFO CUST WHERE L.VEHICLE_ID = V.VEHICLE_ID AND V.IDNO = CUST.IDNO AND CUST.CLTGRP = CLIENT_GROUP AND V.DRAW_DOWN_STATUS <> 'Rejected' AND V.DRAW_DOWN_STATUS <> 'Cancelled' AND V.DRAW_DOWN_STATUS <> 'Withdrew') AS CREATE_CAR_AMT ");
			sql.append(",(SELECT SUM(L.COST_OF_FINANCIAL_AMT) FROM ORIG_VEHICLE V, ORIG_LOAN L, ORIG_PERSONAL_INFO CUST WHERE L.VEHICLE_ID = V.VEHICLE_ID AND V.IDNO = CUST.IDNO AND CUST.CLTGRP = CLIENT_GROUP AND V.DRAW_DOWN_STATUS = 'BOOKED') AS DRAW_DOWN_AMT ");
			sql.append(", (SELECT SUM(p.final_credit_limit) FROM orig_proposal p WHERE p.CREDIT_EXPIRY_DATE < sysdate and p.CLIENT_GROUP = ?) as final_credit_limit_ex ");
			sql.append("FROM ORIG_PROPOSAL ");   
		    
			valueListM.setString(++index,clientGroup);           
            if(!ORIGUtility.isEmptyString(clientGroup)){
			    sql.append("WHERE CLIENT_GROUP = ? ");
			    valueListM.setString(++index,clientGroup);
			}
			
			
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=CREATE_CAR_DETAIL");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";
            
            //Call other valuelist for co client group
            sqlTemp.append("SELECT HPMSHP00.IDNO, HPMSHP00.TH_FIRST_NAME||'-'||HPMSHP00.TH_LAST_NAME AS NAME FROM ORIG_PERSONAL_INFO HPMSHP00 ");
            
            if(!ORIGUtility.isEmptyString(clientGroup)){
                sqlTemp.append("WHERE HPMSHP00.CLTGRP = ? ");
                valueListMCoClient.setString(++indexCoClient,clientGroup);
			}
            
//            ValueListDAO valueListDAO = ORIGDAOFactory.getValueListDAO();
            
            valueListMCoClient.setSQL(String.valueOf(sqlTemp));
            valueListMCoClient.setNextPage(false);
            valueListMCoClient.setItemsPerPage(10);
    		
//            valueListMCoClient = valueListDAO.getResult2(valueListMCoClient);
            
            valueListMCoClient = PLORIGEJBService.getORIGDAOUtilLocal().getResult2(valueListMCoClient);
			
			getRequest().getSession().setAttribute("VALUE_LIST_COCLIENT", valueListMCoClient);
			getRequest().getSession().setAttribute("CLIENT_GROUP_NAME", clientGroup);
            
       } catch (Exception e) {
           log.error("exception ",e);
       }		
	
		log.debug("++++++++++++++ End LoadCreateCarDetailWebAction  ++++++++++++++++++++");		
		return true;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		return FrontController.ACTION;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
	 */
	public String getNextActionParameter() {
		return nextAction;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
