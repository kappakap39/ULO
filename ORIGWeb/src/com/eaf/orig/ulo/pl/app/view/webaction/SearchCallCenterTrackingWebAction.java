package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;

public class SearchCallCenterTrackingWebAction extends WebActionHelper implements WebAction{

	private Logger log = Logger.getLogger(SearchCallCenterTrackingWebAction.class);

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
		
		String idno = getRequest().getParameter("idno");
		String firstName = getRequest().getParameter("firstName");
		String lastName = getRequest().getParameter("lastName");
		String appNo = getRequest().getParameter("appNo");
		String refNo = getRequest().getParameter("refNo");
		String product = getRequest().getParameter("product");
//		log.debug("idno = "+idno);
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		
		SearchHandler handler = (SearchHandler)getRequest().getSession().getAttribute("SEARCH_DATAM");
		if(handler==null){
			handler = new SearchHandler();
		}
		SearchHandler.SearchDataM searchDataM = handler.getSearchM();
		
		if (searchDataM == null) {
			searchDataM = new SearchHandler.SearchDataM();
		}

		if (!ORIGUtility.isEmptyString(idno) || !ORIGUtility.isEmptyString(firstName) || !ORIGUtility.isEmptyString(lastName) || !ORIGUtility.isEmptyString(appNo) || !ORIGUtility.isEmptyString(refNo) || !ORIGUtility.isEmptyString(product) ) {
			searchDataM.setCitizenID(HTMLRenderUtil.replaceNull(idno));
			searchDataM.setCustomerName(HTMLRenderUtil.replaceNull(firstName));
			searchDataM.setCustomerLName(HTMLRenderUtil.replaceNull(lastName));
			searchDataM.setApplicationNo(HTMLRenderUtil.replaceNull(appNo));
			searchDataM.setProductCode(HTMLRenderUtil.replaceNull(product));
			searchDataM.setRefNo(HTMLRenderUtil.replaceNull(refNo));
		}
		handler.setSearchM(searchDataM);
		getRequest().getSession().setAttribute("SEARCH_DATAM", handler);
		
		try{
			StringBuilder SQL = new StringBuilder("");
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
				SQL.append(" SELECT ");
				SQL.append("     PI.TH_FIRST_NAME, ");
				SQL.append("     PI.TH_LAST_NAME, ");
				SQL.append("     PI.IDNO, ");
				SQL.append("     TO_CHAR( APP.APPLICATION_DATE,'DD/MM/')||TO_CHAR(TO_NUMBER(TO_CHAR(APP.APPLICATION_DATE,'YYYY'))+543), ");
				SQL.append("     APP.APPLICATION_STATUS, ");
				SQL.append("     TO_CHAR(APP.FINAL_APP_DECISION_DATE,'DD/MM/')||TO_CHAR(TO_NUMBER(TO_CHAR(APP.FINAL_APP_DECISION_DATE,'YYYY'))+543), ");
				SQL.append("     APP.FINAL_CREDIT_LIMIT, ");
				SQL.append("     ( ");
				SQL.append("         SELECT ");
				SQL.append("             LB.DISPLAY_NAME ");
				SQL.append("         FROM ");
				SQL.append("             LIST_BOX_MASTER LB ");
				SQL.append("         WHERE ");
				SQL.append("             LB.FIELD_ID = '91' ");
				SQL.append("         AND LB.CHOICE_NO = CT.CASH_TRANSFER_TYPE ");
				SQL.append("     ) ");
				SQL.append("     CASH_TRANSFER_TYPE, ");
				SQL.append("     CT.FIRST_TRANSFER_AMOUNT, ");
				SQL.append("     CT.TRANSFER_ACCOUNT, ");
				SQL.append("     APP.APPLICATION_NO, ");
				SQL.append("     APP.REF_NO, ");
				SQL.append("     MP.PRODUCT_DESC, ");
				SQL.append("     ( ");
				SQL.append("         SELECT ");
				SQL.append("             BM.DISPLAY_NAME ");
				SQL.append("         FROM ");
				SQL.append("             LIST_BOX_MASTER BM ");
				SQL.append("         WHERE ");
				SQL.append("             BM.FIELD_ID = '50' ");
				SQL.append("         AND BM.CHOICE_NO = APP.APPLY_CHANNEL ");
				SQL.append("         AND BM.ACTIVE_STATUS = ? ");
				SQL.append("     ) ");
				SQL.append("     CHANNEL, ");
				SQL.append("     ( ");
				SQL.append("         SELECT ");
				SQL.append("             ST.SALE_TYPE_DESC ");
				SQL.append("         FROM ");
				SQL.append("             MS_SALES_TYPE ST ");
				SQL.append("         WHERE ");
				SQL.append("             ST.SALE_TYPE_ID = APP.SALE_TYPE ");
				SQL.append("         AND ST.ACTIVE_STATUS = ? ");
				SQL.append("     ) ");
				SQL.append("     SALE_TYPE, ");
				SQL.append("     APP.PROJECT_CODE, ");
				SQL.append("     ( ");
				SQL.append("         SELECT ");
				SQL.append("             VB.ADD_THAI_SHORT_ADDR ");
				SQL.append("         FROM ");
				SQL.append("             VW_MS_BRANCH_CODE VB ");
				SQL.append("         WHERE ");
				SQL.append("             APP.APPLICATION_RECORD_ID = SI.APPLICATION_RECORD_ID ");
				SQL.append("         AND SI.SALES_BRANCH_CODE = VB.KBANK_BR_NO ");
				SQL.append("     ) ");
				SQL.append("     BRANCH, ");
				SQL.append("     SI.SALES_NAME, ");
				SQL.append("     ( ");
				SQL.append("         SELECT ");
				SQL.append("             MSP.SALE_NAME ");
				SQL.append("         FROM ");
				SQL.append("             VW_MS_SALE_PERSON MSP ");
				SQL.append("         WHERE ");
				SQL.append("             MSP.SALE_ID = SI.SALES_NAME ");
				SQL.append("     ) ");
				SQL.append("     SALE_NAME, ");
				SQL.append("     APP.APPLICATION_RECORD_ID, ");
				SQL.append("     APP.JOB_STATE, ");
				SQL.append("    (SELECT CARD_FACE.CARD_FACE_DESC FROM CARD_FACE WHERE CARD_FACE.CARD_FACE_ID = OC.CARD_FACE AND ROWNUM = 1), ");
				SQL.append("     APP.FINAL_APP_DECISION ");
				SQL.append(" FROM ");
				SQL.append("     ORIG_APPLICATION APP, ");
				SQL.append("     ORIG_CASH_TRANSFER CT, ");
				SQL.append("     ORIG_PERSONAL_INFO PI, ");
				SQL.append("     BUSINESS_CLASS BC, ");
				SQL.append("     MS_PRODUCT MP, ");
				SQL.append("     ORIG_SALE_INFO SI, ");
				SQL.append("     ORIG_CARD OC ");
				SQL.append(" WHERE ");
				SQL.append("     APP.APPLICATION_RECORD_ID = CT.APPLICATION_RECORD_ID(+) ");
				SQL.append(" AND APP.APPLICATION_RECORD_ID = PI.APPLICATION_RECORD_ID(+) ");
				SQL.append(" AND NVL (PI.PERSONAL_TYPE, ?) = ? ");
				SQL.append(" AND BC.BUS_CLASS_ID = APP.BUSINESS_CLASS_ID ");
				SQL.append(" AND MP.PRODUCT_CODE = BC.PRODUCT_ID ");
				SQL.append(" AND APP.APPLICATION_RECORD_ID = SI.APPLICATION_RECORD_ID(+) ");
				SQL.append(" AND PI.PERSONAL_ID = OC.PERSONAL_ID(+)");
//				#septemwi show all application
//				SQL.append(" AND APP.BLOCK_FLAG IS NULL ");
				SQL.append(" AND MP.ACTIVE_STATUS = ? ");
				SQL.append(" AND APP.BUSINESS_CLASS_ID IN ");
				SQL.append("     ( ");
				SQL.append("         SELECT ");
				SQL.append("             BUS_CLASS_ID ");
				SQL.append("         FROM ");
				SQL.append("             TABLE( BUS_CLASS.GETBUSCLASSBYUSER(?) ) ");
				SQL.append("     ) ");
				
			valueListM.setString(++index, OrigConstant.Status.STATUS_ACTIVE);
			valueListM.setString(++index, OrigConstant.Status.STATUS_ACTIVE);
			valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
			valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
			valueListM.setString(++index, OrigConstant.Status.STATUS_ACTIVE);
			valueListM.setString(++index, userM.getUserName());
			
			if(!ORIGUtility.isEmptyString(searchDataM.getCitizenID())){
				SQL.append(" AND PI.IDNO = ? ");
				valueListM.setString(++index, searchDataM.getCitizenID());
			}
			if(!ORIGUtility.isEmptyString(searchDataM.getCustomerName())){
				SQL.append(" AND PI.TH_FIRST_NAME LIKE ? ");
				valueListM.setString(++index, (searchDataM.getCustomerName()).replace("%", "chr(37)")+"%");
			}
			if(!ORIGUtility.isEmptyString(searchDataM.getCustomerLName())){
				SQL.append(" AND PI.TH_LAST_NAME LIKE ? ");
				valueListM.setString(++index, (searchDataM.getCustomerLName()).replace("%", "chr(37)")+"%");
			}
			if(!ORIGUtility.isEmptyString(searchDataM.getApplicationNo())){
				SQL.append(" AND UPPER(APP.APPLICATION_NO) = UPPER(?) ");
				valueListM.setString(++index, searchDataM.getApplicationNo());
			}
			if(!ORIGUtility.isEmptyString(searchDataM.getRefNo())){
				SQL.append(" AND UPPER(APP.REF_NO) = UPPER(?) ");
				valueListM.setString(++index, searchDataM.getRefNo());
			}
			if(!ORIGUtility.isEmptyString(searchDataM.getProductCode())){
				SQL.append(" AND BC.PRODUCT_ID = ? ");
				valueListM.setString(++index, searchDataM.getProductCode());
			}
			SQL.append(" ORDER BY PI.IDNO, MP.PRODUCT_DESC");
			
			log.debug("SQL " + String.valueOf(SQL));
			valueListM.setSQL(String.valueOf(SQL));
			valueListM.setNextPage(false);
			valueListM.setItemsPerPage(20);
			valueListM.setReturnToAction("page=CS_CALL_CENTER_TRACKING_SCREEN");
			getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
			nextAction = "action=ValueListWebAction";

		}catch(Exception e){
			log.error("exception ", e);
		}
		return true;
	}

	@Override
	public int getNextActivityType(){
		return FrontController.ACTION;
	}

	@Override
	public String getNextActionParameter(){
		return nextAction;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
