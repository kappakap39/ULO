package com.eaf.orig.ulo.search.view.webaction;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
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
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.wmtask.WMTaskDAO;
import com.eaf.orig.ulo.app.wmtask.WMTaskDAOImpl;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;

public class SearchWMTaskWebAction extends WebActionHelper implements WebAction {

	private static transient Logger logger = Logger.getLogger(SearchWMTaskWebAction.class);
	
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
			public void postSearchResult(ArrayList<HashMap<String, Object>> list) {
				logger.debug("do postSearchResult()..");
				if(!Util.empty(list)) {
					for(HashMap<String, Object> Row : list) {
						String refCode  = SQLQueryEngine.display(Row, "REF_CODE");
						WMTaskDAO wmTaskDao = new WMTaskDAOImpl();
						try {
							List<HashMap<String, Object>> results = wmTaskDao.getTask(refCode);
							Row.put("DATA", results);
						} catch(Exception e) {
							logger.fatal("ERROR ", e);
						}
					}
				}
				
			}
		};
		searchForm.setNextPage(false);
		searchForm.setAtPage(1);
		searchForm.setSQL(setParameter(searchForm));
		SearchQueryEngine engine = new SearchQueryEngine();
		try {
			engine.search(searchForm);
		} catch(Exception e) {
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

		String refCode = searchForm.getString("APPLICATION_GROUP_NO");
		String statusCode = searchForm.getString("STATUS");
		
		sql.append(" SELECT WM.REF_CODE AS REF_CODE, ");
		sql.append(" 		MAX(AG.APPLICATION_GROUP_ID) AS APPLICATION_GROUP_ID, ");
		sql.append(" 		MAX(APP.APPLICATION_RECORD_ID) AS APPLICATION_RECORD_ID, ");
		sql.append(" 		MAX(AG.APPLICATION_STATUS) AS APPLICATION_STATUS ");
		sql.append(" FROM ORIG_APP.WM_TASK WM ");
		sql.append(" LEFT JOIN ORIG_APP.ORIG_APPLICATION_GROUP AG ON WM.REF_CODE = AG.APPLICATION_GROUP_NO ");
		sql.append(" LEFT JOIN ORIG_APP.ORIG_APPLICATION APP ON AG.APPLICATION_GROUP_ID = APP.APPLICATION_GROUP_ID AND ");
		sql.append(" 		AG.LIFE_CYCLE = APP.LIFE_CYCLE ");
		sql.append(" WHERE 1 = 1 ");
		if(!Util.empty(refCode)) {
			sql.append(" AND REF_CODE = ? ");
		}
		if(!Util.empty(statusCode)) {
			sql.append(" AND STATUS = ? ");
		}
		sql.append(" GROUP BY REF_CODE ");
		sql.append(" ORDER BY MAX(CREATE_TIME) DESC ");

		if(!Util.empty(refCode)) {
			searchForm.setString(refCode);
		}
		if(!Util.empty(statusCode)) {
			searchForm.setString(statusCode);
		}
		
		return sql.toString();
	}
	
}
