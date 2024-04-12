package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction;

public class LoadDistrictPopupWebaction extends WebActionHelper implements WebAction  {
	private final Logger logger = Logger.getLogger(LoadDistrictPopupWebaction.class);
	private String nextAction = "";
	
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
	public boolean preModelRequest() {
				
        String amphur = getRequest().getParameter("amphur");
        String province = getRequest().getParameter("province");
        
        String code = getRequest().getParameter("code");
        
		logger.debug("province >> "+province);
		logger.debug("amphur >> "+amphur);
		logger.debug("code >> "+code);
        
        String textboxCode = "amphur";
        String textboxDesc = "amphur_desc";
        
        try {
        	
        	StringBuilder sql = new StringBuilder();
        	
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append(" SELECT DISTRICT_ID, DISTRICT_DESC ");
			sql.append(" FROM MS_DISTRICT WHERE ACTIVE_STATUS='A' ");
            
			sql.append(" AND PROVINCE_ID = ? ");			
			valueListM.setString(++index, province);
			
			
			if(!OrigUtil.isEmptyString(code)){
			    sql.append(" AND DISTRICT_DESC like ? ");
				valueListM.setString(++index, "%" + code.toUpperCase().replace("%", "chr(37)") + "%");
			}
			
			if(!OrigUtil.isEmptyString(amphur)){
			    sql.append(" AND DISTRICT_ID = ? ");
				valueListM.setString(++index, amphur);
			}

			sql.append(" ORDER BY DISTRICT_ID ");
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadDistrict");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MASTER_DIALOG_POPUP");
            valueListM.setCurrentScreen("MASTER_DIALOG_POPUP");
            
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListDialogPopup";
        
           getRequest().getSession().removeAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE); 
           
           if(!ORIGUtility.isEmptyString(code)){
        	   getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
           }

       }catch(Exception e){
           logger.error("exception ",e);
       }
        return true;
	}

	@Override
	public int getNextActivityType(){
		return FrontController.ACTION;
	}
	
	public String getNextActionParameter(){
        return nextAction;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
