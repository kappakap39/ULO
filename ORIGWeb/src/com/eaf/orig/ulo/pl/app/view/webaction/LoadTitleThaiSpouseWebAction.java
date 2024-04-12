package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction;

public class LoadTitleThaiSpouseWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(LoadTitleThaiSpouseWebAction.class);
	private String nextAction = null;
	@Override
	public Event toEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean preModelRequest() {
	 	String title_Thai = getRequest().getParameter("mtitleThai");
	 	logger.debug("mtitleThai= "+title_Thai);
        String code = getRequest().getParameter("code");
		String dataValue=getRequest().getParameter("dataValue");
		if(dataValue!=null){
			title_Thai=dataValue;
		}	        
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			String textboxCode = getRequest().getParameter("textboxCode");
	        String textboxDesc = getRequest().getParameter("textboxDesc");
	        
			sql.append("SELECT CHOICE_NO, DISPLAY_NAME, SYSTEM_ID1, ENG_DISPLAY_NAME, ACTIVE_STATUS, FIELD_ID ");
			sql.append("FROM LIST_BOX_MASTER WHERE ACTIVE_STATUS='A' AND FIELD_ID='3' ");
			
			if(!ORIGUtility.isEmptyString(title_Thai)){
			    sql.append("AND DISPLAY_NAME like ? ");
			    valueListM.setString(++index,title_Thai+"%");
			}
			if(!ORIGUtility.isEmptyString(code)){
			    sql.append("AND DISPLAY_NAME like ? ");
			    valueListM.setString(++index,code+"%");
			}			
			
			
			sql.append("ORDER BY CHOICE_NO ");
			
//			valueListM.setTextboxCode(title_Thai);
//			valueListM.setTextboxDesc(title_Thai);
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadTitleThaiSpouse");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=PL_MASTER_POPUP");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";
            logger.debug("valueListM.getTextboxDesc="+valueListM.getTextboxDesc());
            
            if(!ORIGUtility.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
            }else{
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, title_Thai);
            }

       } catch (Exception e) {
           logger.error("exception ",e);
       }
        return true;
    }   

	  
	    public int getNextActivityType() {
	        return FrontController.ACTION;
	    }
	    /* (non-Javadoc)
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
