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

public class LoadZipCodeWebAction extends WebActionHelper implements WebAction {
	
	Logger logger = Logger.getLogger(LoadZipCodeWebAction.class);
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
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	@Override
	public boolean preModelRequest() {
		
        String zipcode = getRequest().getParameter("zipcode");
        String code = getRequest().getParameter("code");
        
		String province = getRequest().getParameter("province");
		String amphur = getRequest().getParameter("amphur");
		String tambol = getRequest().getParameter("tambol");
        
        String textboxCode = "zipcode";
        String textboxDesc = "";
        
        logger.debug("textboxCode >> "+textboxCode);
        logger.debug("textboxDesc >> "+textboxDesc);
        
        try {
        	
        	StringBuilder sql = new StringBuilder();
        	
			ValueListM valueListM = new ValueListM();
			int index = 0;

			sql.append(" SELECT TMP.ZIPCODE ZIPCODE , (TMP.SUB_DISTRICT_DESC ||' '|| TMP.DISTRICT_DESC ||' '|| TMP.PROVINCE_DESC) ZIPDESC, ");
			sql.append(" TMP.SUB_DISTRICT_ID SUB_DISTRICT_ID, TMP.SUB_DISTRICT_DESC SUB_DISTRICT_DESC, TMP.DISTRICT_ID DISTRICT_ID, TMP.DISTRICT_DESC DISTRICT_DESC, ");
			sql.append(" TMP.PROVINCE_ID PROVINCE_ID, TMP.PROVINCE_DESC PROVINCE_DESC ");
			sql.append(" FROM (");
			sql.append(" SELECT ZIPCODE, "); 
			sql.append(" Z.SUB_DISTRICT_ID, (SELECT SUB_DISTRICT_DESC FROM MS_SUB_DISTRICT S WHERE S.SUB_DISTRICT_ID = Z.SUB_DISTRICT_ID) SUB_DISTRICT_DESC ,");
			sql.append(" Z.DISTRICT_ID, (SELECT DISTRICT_DESC FROM MS_DISTRICT D WHERE D.DISTRICT_ID=Z.DISTRICT_ID) DISTRICT_DESC ,");
			sql.append(" Z.PROVINCE_ID, (SELECT PROVINCE_DESC FROM MS_PROVINCE P WHERE P.PROVINCE_ID=Z.PROVINCE_ID) PROVINCE_DESC ");
			sql.append(" FROM MS_ZIPCODE Z WHERE Z.ACTIVE_STATUS = 'A'");
			sql.append(" )TMP ");
			sql.append("WHERE 1=1");
			
			if(!OrigUtil.isEmptyString(zipcode)){
				if(zipcode.length()==5){				
					sql.append(" AND TMP.ZIPCODE = ? ");
					valueListM.setString(++index, zipcode);					
					if(!OrigUtil.isEmptyString(province) 
							&& !OrigUtil.isEmptyString(amphur) 
								&& !OrigUtil.isEmptyString(tambol)){						
						sql.append(" AND TMP.SUB_DISTRICT_ID = ? ");
						valueListM.setString(++index, tambol);
						sql.append(" AND TMP.DISTRICT_ID = ? ");
						valueListM.setString(++index, amphur);
						sql.append(" AND TMP.PROVINCE_ID= ? ");
						valueListM.setString(++index, province);
					}					
				}else{					
					if(!OrigUtil.isEmptyString(province) 
							&& !OrigUtil.isEmptyString(amphur) 
								&& !OrigUtil.isEmptyString(tambol)){						
						sql.append(" AND TMP.SUB_DISTRICT_ID = ? ");
						valueListM.setString(++index, tambol);
						sql.append(" AND TMP.DISTRICT_ID = ? ");
						valueListM.setString(++index, amphur);
						sql.append(" AND TMP.PROVINCE_ID= ? ");
						valueListM.setString(++index, province);
					}					
					sql.append(" AND TMP.ZIPCODE LIKE ? ");
				    valueListM.setString(++index, zipcode.replace("%", "chr(37)") + "%");
				}
			}else{
				if(!OrigUtil.isEmptyString(province) 
						&& !OrigUtil.isEmptyString(amphur) 
							&& !OrigUtil.isEmptyString(tambol)){					
					sql.append(" AND TMP.SUB_DISTRICT_ID = ? ");
					valueListM.setString(++index, tambol);
					sql.append(" AND TMP.DISTRICT_ID = ? ");
					valueListM.setString(++index, amphur);
					sql.append(" AND TMP.PROVINCE_ID= ? ");
					valueListM.setString(++index, province);
				}
			}
			
			if(!OrigUtil.isEmptyString(code)){
				sql.append(" AND TRIM(UPPER(REPLACE(TMP.SUB_DISTRICT_DESC||TMP.DISTRICT_DESC||TMP.PROVINCE_DESC||TMP.ZIPCODE,' ',''))) LIKE TRIM(UPPER(REPLACE(?,' ',''))) ");
				valueListM.setString(++index, "%"+code+"%");
			}
			
			sql.append(" ORDER BY ZIPCODE ");
			
			logger.debug(sql);
			
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadPLZipCode");
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
            }else{
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, zipcode);
            }
            
       } catch (Exception e) {
           logger.error("exception ",e);
       }
        return true;
    }

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
