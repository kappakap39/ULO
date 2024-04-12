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

public class LoadSubDistrictPopupWebAction extends WebActionHelper implements WebAction {
	
	Logger logger = Logger.getLogger(LoadSubDistrictPopupWebAction.class);
	
	private String nextAction = "";

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
				
		String province = getRequest().getParameter("province");
		String amphur = getRequest().getParameter("amphur");
		String tambol = getRequest().getParameter("tambol");
		String code = getRequest().getParameter("code");
		
		logger.debug("province >> "+province);
		logger.debug("amphur >> "+amphur);
		logger.debug("tambol >> "+tambol);
		logger.debug("code >> "+code);       
        
        String textboxCode = "tambol";
        String textboxDesc = "tambol_desc";
        
        try{        	
        	StringBuilder sql = new StringBuilder();
        	
			ValueListM valueListM = new ValueListM();
			int index = 0;
						
			sql.append(" SELECT ");
			sql.append("     TMP.SUB_DISTRICT_ID SUB_DISTRICT_ID, ");
			sql.append("     TMP.SUB_DISTRICT_DESC SUB_DISTRICT_DESC , ");
			sql.append("     TMP.ZIPCODE ZIPCODE ");
			sql.append(" FROM ");
			sql.append("     ( ");
			sql.append("         SELECT ");
			sql.append("             COUNT(Z.ZIPCODE) NUM, ");
			sql.append("             LISTAGG(Z.ZIPCODE ||'|')WITHIN GROUP ( ORDER BY Z.ZIPCODE) ZIPCODE , ");
			sql.append("             Z.SUB_DISTRICT_ID, ");
			sql.append("             Z.DISTRICT_ID, ");
			sql.append("             Z.PROVINCE_ID, ");
			sql.append("             S.SUB_DISTRICT_DESC, ");
			sql.append("             Z.ACTIVE_STATUS ");
			sql.append("         FROM ");
			sql.append("             MS_ZIPCODE Z, ");
			sql.append("             MS_SUB_DISTRICT S ");
			sql.append("         WHERE ");
			sql.append("             S.SUB_DISTRICT_ID = Z.SUB_DISTRICT_ID ");
			sql.append("         AND Z.ACTIVE_STATUS='A' ");
			sql.append("         AND S.ACTIVE_STATUS = 'A' ");
			sql.append("         GROUP BY ");
			sql.append("             Z.SUB_DISTRICT_ID, ");
			sql.append("             Z.DISTRICT_ID, ");
			sql.append("             Z.PROVINCE_ID, ");
			sql.append("             S.SUB_DISTRICT_DESC, ");
			sql.append("             Z.ACTIVE_STATUS ");
			sql.append("     ) ");
			sql.append("     TMP ");
			sql.append(" WHERE ");
			sql.append("     TMP.PROVINCE_ID = ? ");
			
			valueListM.setString(++index, province);
			
			sql.append(" AND TMP.ACTIVE_STATUS='A' ");
			
			sql.append(" AND TMP.DISTRICT_ID = ? ");
			valueListM.setString(++index, amphur);
			
			if(!OrigUtil.isEmptyString(code)){
			    sql.append("AND TMP.SUB_DISTRICT_DESC LIKE ? ");
				valueListM.setString(++index, "%" + code.toUpperCase().replace("%", "chr(37)") + "%");
			}
						
			if(!OrigUtil.isEmptyString(tambol)){
			    sql.append(" AND TMP.SUB_DISTRICT_ID = ? ");
			    valueListM.setString(++index,tambol);
			}
			
			sql.append(" ORDER BY TMP.SUB_DISTRICT_ID ");
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadSubDistrict");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(20);
            valueListM.setReturnToAction("page=MASTER_DIALOG_POPUP");
            valueListM.setCurrentScreen("MASTER_DIALOG_POPUP");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListDialogPopup";
            
            getRequest().getSession().removeAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE);  
            
            if(!ORIGUtility.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
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
		return false;
	}

}
