package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;

public class SearchCardMaintenanceWebAction extends WebActionHelper implements WebAction {

	private Logger log = Logger.getLogger(SearchCardMaintenanceWebAction.class);
	
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
		
		getRequest().getSession().removeAttribute("MSG_RESP");
		
		SearchHandler handler = (SearchHandler)getRequest().getSession().getAttribute("SEARCH_DATAM");
		if(handler==null){
			handler = new SearchHandler();
		}
		SearchHandler.SearchDataM searchDataM = handler.getSearchM();
		
		if (searchDataM == null) {
			searchDataM = new SearchHandler.SearchDataM();
		}
		
		String sendCardDate = getRequest().getParameter("sendCardDate");
		String product = getRequest().getParameter("product");
		String application_no = getRequest().getParameter("application_no");
		String cardNo = getRequest().getParameter("cardNo");
		String firstName = getRequest().getParameter("firstName");
		String lastName = getRequest().getParameter("lastName");
	    String cardRefNo = getRequest().getParameter("cardRefNo");
	    String cardStatus = getRequest().getParameter("cardStatus");

		if (sendCardDate != null && !"".equals(sendCardDate)) {
	    	int year = Integer.parseInt(sendCardDate.substring(6, 10));
	    	sendCardDate = sendCardDate.substring(0, 6) + String.valueOf(year- 543);
	    }
	    
	    if(!ORIGUtility.isEmptyString(sendCardDate) || !ORIGUtility.isEmptyString(product) 
	    		|| !ORIGUtility.isEmptyString(application_no) || !ORIGUtility.isEmptyString(cardNo) 
	    				|| !ORIGUtility.isEmptyString(firstName) || !ORIGUtility.isEmptyString(lastName) 
	    						|| !ORIGUtility.isEmptyString(cardRefNo) || !ORIGUtility.isEmptyString(cardStatus)){
			searchDataM.setStringDate(HTMLRenderUtil.replaceNull(sendCardDate));
			searchDataM.setProductCode(HTMLRenderUtil.replaceNull(product));
			searchDataM.setApplicationNo(HTMLRenderUtil.replaceNull(application_no));
			searchDataM.setCardNo(HTMLRenderUtil.replaceNull(cardNo));
			searchDataM.setCustomerName(HTMLRenderUtil.replaceNull(firstName));
			searchDataM.setCustomerLName(HTMLRenderUtil.replaceNull(lastName));
			searchDataM.setCardRefNo(HTMLRenderUtil.replaceNull(cardRefNo));
			searchDataM.setCardStatus(HTMLRenderUtil.replaceNull(cardStatus));
		}
			
	    handler.setSearchM(searchDataM);
		getRequest().getSession().setAttribute("SEARCH_DATAM", handler);
		
	    try {
        	StringBuilder SQL = new StringBuilder("");
			ValueListM valueListM = new ValueListM();
			
			
			SQL.append(" SELECT ");
			SQL.append("     PKA_CRYPTO.DECRYPT_FIELD(CARD.CARD_NO), ");
			SQL.append("     ACC.APPLICATION_NO, ");
			SQL.append("     APP.APPLICATION_RECORD_ID, ");
			SQL.append("     ACC.TH_FIRST_NAME, ");
			SQL.append("     ACC.TH_LAST_NAME, ");
			SQL.append("     (SELECT MP.PRODUCT_DESC FROM MS_PRODUCT MP WHERE MP.PRODUCT_CODE = BS.PRODUCT_ID AND ROWNUM = 1) PRODUCT,");
			SQL.append("     APP.APPLICATION_DATE, ");
			SQL.append("     (SELECT LB.DISPLAY_NAME FROM LIST_BOX_MASTER LB  WHERE LB.FIELD_ID = '63' AND LB.CHOICE_NO = ACC.CARDLINK_STATUS ");
			SQL.append("        AND ROWNUM = 1  ");
			SQL.append("     ) CARD_STATUS, ");
			SQL.append("     ACC.CARDLINK_DATE, ");
			SQL.append("     CARD.UPDATE_DATE CREATE_DATE, ");
			SQL.append("     CARD.UPDATE_BY CREATE_BY , ");
			SQL.append("     ACC.ACCOUNT_ID, ");
			SQL.append("     CARD.SEQ, ");
			SQL.append("     CARD.ACCOUNT_CARD_ID, ");
			SQL.append("     CARD.CARD_TYPE, ");
			SQL.append("    (SELECT CF.CARD_FACE_DESC FROM CARD_FACE CF WHERE CF.CARD_FACE_ID = CARD.CARD_FACE AND ROWNUM = 1) CARD_FACE, ");
			SQL.append(" 	I.ACTION ");
			SQL.append(" FROM ");
			SQL.append("     AC_ACCOUNT ACC, ");
			SQL.append("     AC_ACCOUNT_CARD CARD, ");
			SQL.append("     BUSINESS_CLASS BS, ");
			SQL.append("     ORIG_APPLICATION APP, ");
			SQL.append("     WF_JOBID_MAPPING J, WF_INSTANT I ");
			SQL.append(" WHERE ");
			SQL.append("     ACC.ACCOUNT_ID = CARD.ACCOUNT_ID ");
			SQL.append(" AND BS.BUS_CLASS_ID = ACC.BUSINESS_CLASS_ID ");
			SQL.append(" AND CARD.ACCOUNT_CARD_ID = APP.ACCOUNT_CARD_ID ");
			SQL.append(" AND APP.APPLICATION_RECORD_ID = J.APPLICATION_RECORD_ID ");
			SQL.append(" AND J.JOB_ID = I.JOB_ID ");
			SQL.append(" AND CARD.SEQ =(SELECT MAX(SEQ) FROM AC_ACCOUNT_CARD WHERE AC_ACCOUNT_CARD.ACCOUNT_CARD_ID = CARD.ACCOUNT_CARD_ID )");
			SQL.append(" AND J.JOB_VERSION = (SELECT MAX(JOB_VERSION) FROM WF_JOBID_MAPPING WHERE APPLICATION_RECORD_ID = J.APPLICATION_RECORD_ID) ");
			SQL.append(" AND (CARD.CARD_STATUS = ? OR I.ACTION = ? ) ");
			
			int index = 0;
			valueListM.setString(++index, OrigConstant.Status.STATUS_ACTIVE);
			valueListM.setString(++index, OrigConstant.Action.CANCEL_APPLICATION_AFTER_CARDLINK);
			
			if (!ORIGUtility.isEmptyString(searchDataM.getStringDate())) {
				SQL.append(" AND TRUNC(ACC.CARDLINK_DATE) =  TRUNC(TO_DATE (?, 'dd/mm/yyyy')) ");
				valueListM.setString(++index, searchDataM.getStringDate());
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getProductCode())) {
				SQL.append(" AND BS.PRODUCT_ID = ? ");
				valueListM.setString(++index, searchDataM.getProductCode());
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getCustomerName())) {
				SQL.append(" AND ACC.TH_FIRST_NAME LIKE ? ");
				valueListM.setString(++index, (searchDataM.getCustomerName()).replace("%", "chr(37)")+"%");
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getCustomerLName())) {
				SQL.append(" AND ACC.TH_LAST_NAME LIKE ? ");
				valueListM.setString(++index, (searchDataM.getCustomerLName()).replace("%", "chr(37)")+"%");
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getApplicationNo())) {
				SQL.append(" AND APP.APPLICATION_NO = UPPER(?) ");
				valueListM.setString(++index, searchDataM.getApplicationNo());
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getCardNo())) {
				SQL.append(" AND CARD.HASHING_CARD_NO = PKA_CRYPTO.HASHING_FIELD(?) ");
				valueListM.setString(++index, searchDataM.getCardNo());
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getCardRefNo())) {
				SQL.append(" AND APP.APPLICATION_RECORD_ID = ? ");
				valueListM.setString(++index, searchDataM.getCardRefNo());
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getCardStatus())) {
				SQL.append(" AND ACC.CARDLINK_STATUS = ? ");
				valueListM.setString(++index, searchDataM.getCardStatus());
			}
			SQL.append(" ORDER BY APP.APPLICATION_NO");
			
		    log.debug("SQL >> "+SQL);
		    
            valueListM.setSQL(String.valueOf(SQL));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=CARD_MAINTENANCE");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";
            
       } catch (Exception e) {
           log.error("exception ",e);
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

	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
