package com.eaf.orig.ulo.search.view.webaction;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SearchFormHandler;
import com.eaf.core.ulo.common.engine.SearchQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;

public class SearchOldDataWebAction extends WebActionHelper implements WebAction {
	private static transient Logger logger = Logger.getLogger(SearchOldDataWebAction.class);
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
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

		SearchFormHandler searchForm = new SearchFormHandler(getRequest()) {
			@Override
			public void postSearchResult(ArrayList<HashMap<String, Object>> List) 
			{
				logger.debug("do postSearchResult()..");
				if (null != List) {
					for (HashMap<String, Object> tableResult : List) 
					{
						String name = (String) tableResult.get("NAME");
						String idNo = (String) tableResult.get("IDNO");
						if(!Util.empty(name))
						{
							String[] nameS = name.split(",");
							tableResult.put("PERSONAL_APPLICANT1", nameS[0]);
							tableResult.put("PERSONAL_APPLICANT2", (nameS.length > 1) ? nameS[1] : null);
						}
						if(!Util.empty(idNo))
						{
							String[] idNoS = idNo.split(",");
							tableResult.put("PERSONAL_APPLICANT_ID1", idNoS[0]);
							tableResult.put("PERSONAL_APPLICANT_ID2", (idNoS.length > 1) ? idNoS[1] : null);
						}
					}
				}
			}
		};
		logger.info(" SearchOldDataWebAction.... ");
		searchForm.setNextPage(false);
		searchForm.setAtPage(1);
		searchForm.setSQL(setParameter(searchForm));
		searchForm.setSubSQL(null);
		SearchQueryEngine engine = new SearchQueryEngine();

		try {
			engine.search(searchForm);
		} catch (Exception e) {
			logger.fatal("ERROR ", e);
		}
		getRequest().getSession().setAttribute(SearchFormHandler.SearchForm, searchForm);
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.PAGE;
	}

	private String setParameter(SearchFormHandler searchForm) {
		StringBuilder sql = new StringBuilder();

		String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
		String product = searchForm.getString("PRODUCT");
		product = ListBoxControl.getName(FIELD_ID_PRODUCT_TYPE, "CHOICE_NO", product, "SYSTEM_ID1");

		sql.append(" SELECT DISTINCT   "
				+ " AG.APPLICATION_GROUP_NO,   "
				+ " AG.APPLICATION_GROUP_ID,   "
				+ " AG.APPLICATION_STATUS,    "
				+ " AG.ARC_DATE,    "
				+ " AG.LIFE_CYCLE AS MAX_LIFE_CYCLE, " 
				+ " regexp_replace( LISTAGG(CONCAT(CONCAT(PI.TH_FIRST_NAME, ' '),PI.TH_LAST_NAME), ',') WITHIN GROUP (ORDER BY PI.PERSONAL_ID) " 
				+ " ,'([^,]+)(,\\1)*(,|$)', '\\1\\3') AS NAME, "
			    + " regexp_replace( LISTAGG(PI.IDNO, ',') WITHIN GROUP (ORDER BY PI.PERSONAL_ID) " 
			    + " ,'([^,]+)(,\\1)*(,|$)', '\\1\\3') AS IDNO "
				+ " FROM ORIG_APPLICATION_GROUP_CAT AG "
				+ " LEFT JOIN ORIG_APPLICATION_CAT APP ON AG.APPLICATION_GROUP_ID = APP.APPLICATION_GROUP_ID   "
				+ " LEFT JOIN ORIG_PERSONAL_INFO_CAT PI ON AG.APPLICATION_GROUP_ID = PI.APPLICATION_GROUP_ID  "
				+ " WHERE (1=1) ");
		
		if(!searchForm.empty("APPLICATION_GROUP_NO")) {
			sql.append(" AND AG.APPLICATION_GROUP_NO = ? ");
			searchForm.setString(searchForm.getString("APPLICATION_GROUP_NO"));
		}
		if(!searchForm.empty("CARD_LINK_REF_NO")){
			sql.append(" AND APP.CARDLINK_REF_NO = ? ");
			searchForm.setString(searchForm.getString("CARD_LINK_REF_NO"));
		}
		if(!searchForm.empty("PRODUCT")){
			String BUSINESS_CLASS_ID = CacheControl.getName(FIELD_ID_PRODUCT_TYPE, "CHOICE_NO", searchForm.getString("PRODUCT"), "SYSTEM_ID2");
			logger.debug("BUSINESS_CLASS_ID>>"+BUSINESS_CLASS_ID);
			sql.append(" AND APP.BUSINESS_CLASS_ID = ? ");
			searchForm.setString(BUSINESS_CLASS_ID);
		}
		if(!searchForm.empty("TH_FIRST_NAME")) {
			sql.append(" AND PI.TH_FIRST_NAME LIKE ( ? || '%') ");
			searchForm.setString(searchForm.getString("TH_FIRST_NAME"));
		}
		if(!searchForm.empty("TH_LAST_NAME")) {
			sql.append(" AND PI.TH_LAST_NAME LIKE ( ? || '%') ");
			searchForm.setString(searchForm.getString("TH_LAST_NAME"));
		}
		if(!searchForm.empty("IDNO")) {
			sql.append(" AND PI.IDNO = ? ");
			searchForm.setString(searchForm.getString("IDNO"));
		}
		if(!searchForm.empty("APPLICATION_STATUS")) {
			sql.append(" AND AG.APPLICATION_STATUS = ? ");
			searchForm.setString(searchForm.getString("APPLICATION_STATUS"));
		}
		if(!searchForm.empty("CARD_TYPE")){
			sql.append(" AND APP.CARD_TYPE IN (SELECT CARD_TYPE_ID FROM CARD_TYPE WHERE CARD_CODE = ?) ");
			searchForm.setString(searchForm.getString("CARD_TYPE"));
		}
		if(!searchForm.empty("SCAN_DATE_FROM") && !searchForm.empty("SCAN_DATE_TO")){
			sql.append(" AND AG.APPLICATION_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			searchForm.setDate(searchForm.getDate("SCAN_DATE_FROM", FormatUtil.TH));
			searchForm.setDate(searchForm.getDate("SCAN_DATE_TO", FormatUtil.TH));
		}
		if(!searchForm.empty("SCAN_DATE_FROM") && searchForm.empty("SCAN_DATE_TO")){
			sql.append(" AND AG.APPLICATION_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			searchForm.setDate(searchForm.getDate("SCAN_DATE_FROM", FormatUtil.TH));
			searchForm.setDate(searchForm.getDate("SCAN_DATE_FROM", FormatUtil.TH));
		}
		if(!searchForm.empty("FINAL_DECISION_DATE_FROM") && !searchForm.empty("FINAL_DECISION_DATE_TO")){
			sql.append(" AND APP.FINAL_APP_DECISION_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			searchForm.setDate(searchForm.getDate("FINAL_DECISION_DATE_FROM", FormatUtil.TH));
			searchForm.setDate(searchForm.getDate("FINAL_DECISION_DATE_TO", FormatUtil.TH));
		}
		if(!searchForm.empty("FINAL_DECISION_DATE_FROM") && searchForm.empty("FINAL_DECISION_DATE_TO")){
			sql.append(" AND APP.FINAL_APP_DECISION_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			searchForm.setDate(searchForm.getDate("FINAL_DECISION_DATE_FROM", FormatUtil.TH));
			searchForm.setDate(searchForm.getDate("FINAL_DECISION_DATE_FROM", FormatUtil.TH));
		}
		
		sql.append(" GROUP BY AG.APPLICATION_GROUP_NO, AG.APPLICATION_GROUP_ID, ");
		sql.append(" AG.APPLICATION_STATUS,AG.ARC_DATE, AG.LIFE_CYCLE ");
		   
		sql.append(" ORDER BY AG.APPLICATION_GROUP_NO DESC ");
		logger.debug("sql : "+sql);
		return sql.toString();
	}
}
