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

public class LoadSellInformationWebaction extends WebActionHelper implements WebAction {
	 Logger logger = Logger.getLogger(LoadSellInformationWebaction.class);
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
		String seller_title = getRequest().getParameter("seller_title");
        String code = getRequest().getParameter("code");
        String textboxCode = "seller_title";
        String textboxDesc = "seller_name_th";
        String dataValue = getRequest().getParameter("dataValue");
//      String branch = getRequest().getParameter("channel");
        String channelGroup = getRequest().getParameter("saleChannel");
        logger.debug("channelGroup= "+channelGroup);
		if(!ORIGUtility.isEmptyString(dataValue)){
			seller_title=dataValue;
		}
        
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			sql.append("SELECT SALE_ID, SALE_NAME");
			sql.append("FROM VW_MS_SALE_PERSON WHERE 1=1 ");
			
			if(ORIGUtility.isEmptyString(channelGroup)){
				channelGroup="9999";
			}
			
			sql.append("AND CHANNEL_GROUP = ? ");
			valueListM.setString(++index,channelGroup);
			
			if(!ORIGUtility.isEmptyString(seller_title)){
			    sql.append("AND SALE_ID like ? ");
			    valueListM.setString(++index,seller_title+"%");
			}
			
			if(!ORIGUtility.isEmptyString(code)){
				sql.append("AND SALE_NAME like ? ");
			    valueListM.setString(++index,"%"+code+"%");
			}
			
			sql.append("ORDER BY SALE_ID ");
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadSellInformation");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(true);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=PL_MASTER_POPUP");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";
            
            if(!ORIGUtility.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
            }else{
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, seller_title);
            }

       } catch (Exception e) {
           logger.error("exception ",e);
       }
        return true;
	}

	@Override
	public int getNextActivityType() {
        return FrontController.ACTION;
    }
   
    public String getNextActionParameter() {
        return nextAction;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
