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
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.model.app.search.PLSearchDataM;

public class SearchBundleCCWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(this.getClass().getName());
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
		logger.debug("SearchBundleCCWebAction");
		UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		PLSearchDataM searchBundleCCDataM = (PLSearchDataM) getRequest().getSession().getAttribute("SEARCH_BUNDLE_CC_DATAM");
		String userName=userM.getUserName();
		
		String thaiFirstName = null;
		String thaiLastName = null;
		String applicationNo = null;
		String idNo = null;
		String refNo = null;
		
		//check user click search button or not? (beware change input criteria and click next page)
		String fromSearch = getRequest().getParameter("fromSearch");
		logger.debug("@@@@@ fromSearch:"+fromSearch);
		if (("Y").equals(fromSearch)) {
			//clear session of search criteria, if user click search button.
			searchBundleCCDataM = null;
        }
		if(searchBundleCCDataM == null){	
			thaiFirstName = (String)getRequest().getParameter("firstName");
			thaiLastName = (String)getRequest().getParameter("lastName");
			applicationNo = (String)getRequest().getParameter("appNo");
			idNo = (String)getRequest().getParameter("idNo");
			refNo = (String)getRequest().getParameter("refNo");
			logger.debug("@@@@@ thaiFirstName:"+thaiFirstName);
			logger.debug("@@@@@ thaiLastName:"+thaiLastName);
			logger.debug("@@@@@ applicationNo:"+applicationNo);
			logger.debug("@@@@@ idNo:"+idNo);
			logger.debug("@@@@@ refNo:"+refNo);
			searchBundleCCDataM = new PLSearchDataM();
			try{
				if(thaiFirstName != null && !"".equals(thaiFirstName.trim())){
					searchBundleCCDataM.setFirstName(thaiFirstName.trim());
				}
				if(thaiLastName != null && !"".equals(thaiLastName.trim())){
					searchBundleCCDataM.setLastName(thaiLastName.trim());
				}
				if(applicationNo != null && !"".equals(applicationNo.trim())){
					searchBundleCCDataM.setApplicationNo(applicationNo.trim());
				}
				if(idNo != null && !"".equals(idNo.trim())){
					searchBundleCCDataM.setCitizenID(idNo.trim());
				}
				if(refNo != null && !"".equals(refNo.trim())){
					searchBundleCCDataM.setRefNo(refNo.trim());
				}
			}catch (Exception e){
				e.printStackTrace();
				logger.error("##### cannot pase date at FU inbox:" + e.getMessage());
			}
			getRequest().getSession().setAttribute("SEARCH_BUNDLE_CC_DATAM",searchBundleCCDataM);
		}else{
			thaiFirstName = searchBundleCCDataM.getFirstName();
			thaiLastName = searchBundleCCDataM.getLastName();
			applicationNo = searchBundleCCDataM.getApplicationNo();
			idNo = searchBundleCCDataM.getCitizenID();
			refNo = searchBundleCCDataM.getRefNo();
		}
		
		try{
			StringBuffer sql = new StringBuffer();
            ValueListM valueListM = new ValueListM();
            int index = 0;

            sql.append("select app.application_record_id, app.sale_type, si.cash_dayone_name, app.priority, app.application_no, ");
            		sql.append("app.application_status, (pi.th_first_name||' '||pi.th_last_name) name_surname, pi.idno, bus.product_id, ");
            		sql.append("pka_util.date_to_string_thai_date(app.application_date,'dd/mm/yyyy hh24:mi:ss') application_date, ");
            		sql.append("pka_util.date_to_string_thai_date(app.update_date,'dd/mm/yyyy hh24:mi:ss') update_date, ");
            		sql.append("pka_app_util.get_kbank_user_name_surname(app.update_by) update_by ");
            sql.append("from orig_application app ");
            		sql.append("inner join table(bus_class.getBusClassByUser(?)) userBus on app.business_class_id = userBus.bus_class_id ");
            		valueListM.setString(++index, userName);
            		sql.append("inner join orig_personal_info pi on pi.application_record_id = app.application_record_id ");
            		sql.append("inner join orig_sale_info si on si.application_record_id = app.application_record_id ");
            		sql.append("inner join business_class bus on bus.bus_class_id = app.business_class_id ");
            sql.append("where pi.personal_type = ? ");
            		valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
            		sql.append("and app.job_state in (?,?) ");
            		valueListM.setString(++index, WorkflowConstant.JobState.CA_BUNDLE_CQ);
            		valueListM.setString(++index, WorkflowConstant.JobState.CA_BUNDLE_CQ_ESCALATE);
            		sql.append("and exists (select * from bus_class_grp_map bgm where bgm.bus_class_id = bus.bus_class_id and bgm.bus_grp_id = ?) ");
            		valueListM.setString(++index, OrigConstant.BusGroup.KEC_CC_FLOW);
            if(thaiFirstName != null && !"".equals(thaiFirstName)){
            		sql.append("and pi.th_first_name like '" + thaiFirstName + "%' ");
            }
            if(thaiLastName != null && !"".equals(thaiLastName)){
        			sql.append("and pi.th_last_name like '" + thaiLastName + "%' ");
            } 
            if(applicationNo != null && !"".equals(applicationNo)){
        			sql.append("and app.application_no = UPPER(?) ");
        			valueListM.setString(++index, applicationNo);
            } 
            if(idNo != null && !"".equals(idNo)){
        			sql.append("and pi.idno = ? ");
        			valueListM.setString(++index, idNo);
            }
            if(refNo != null && !"".equals(refNo)){
    				sql.append("and app.ref_no = UPPER(?) ");
    				valueListM.setString(++index, refNo);
            }
            sql.append("order by app.priority desc, app.application_date");
            logger.debug("@@@@@ Search Bundle CC SQL>>> " + sql);

            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(20);
            valueListM.setReturnToAction("page=CA_SEARCH_BUNDLE_SCREEN");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";
		}catch (Exception e){
			
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
