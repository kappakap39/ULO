package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SearchFormHandler;
import com.eaf.core.ulo.common.engine.SearchQueryEngine;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;

public class SearchAddressZipCodeAjax implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(SearchAddressZipCodeAjax.class);
	@Override
	public ResponseData processAction(HttpServletRequest request){
		logger.debug("SearchAddressInfo..");
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.SEARCH_ADDRESS_INFO); 
		String atPageStr = request.getParameter("atPage");
		String itemsPerPageStr = request.getParameter("itemsPerPage");
		logger.debug("atPageStr>>"+atPageStr);
		logger.debug("itemsPerPageStr>>"+itemsPerPageStr);
		try{
			int atPage  = FormatUtil.getInt(atPageStr);
			int itemPerPage  = FormatUtil.getInt(itemsPerPageStr);
			if(0==atPage){
				atPage =1;
			}
		
			SearchFormHandler searchForm = new SearchFormHandler(request);
			searchForm.setNextPage(false);
			searchForm.setAtPage(atPage);
			if(0!=itemPerPage){
				searchForm.setItemsPerPage(itemPerPage);
			}
			searchForm.setSQL(getSelectSerchSQL(searchForm));
			try{
				SearchQueryEngine SearchEngine = new SearchQueryEngine();
					SearchEngine.search(searchForm);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}		
			request.getSession().setAttribute(SearchFormHandler.SearchForm,searchForm);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			return responseData.error(e);
		}
		return responseData.success();
	}

	private String getSelectSerchSQL(SearchFormHandler searchForm){
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT   ZIPCODE_ID, ZIPCODE, PROVINCE, DISTRICT AS AMPHUR, SUB_DISTRICT  AS TAMBOL FROM  MS_ZIPCODE");
		sql.append(" WHERE (1=1)");
		 
    	if(!searchForm.empty("SEARCH_TAMBOL")){
			sql.append(" AND   SUB_DISTRICT LIKE ? ");
			searchForm.setString("%"+searchForm.getString("SEARCH_TAMBOL")+"%");
		}
    	if(!searchForm.empty("SEARCH_AMPHUR")){
    		sql.append(" AND   DISTRICT LIKE  ? ");
			searchForm.setString("%"+searchForm.getString("SEARCH_AMPHUR")+"%");
    	}
    	if(!searchForm.empty("SEARCH_PROVINCE")){
    		sql.append(" AND  PROVINCE LIKE ? ");
			searchForm.setString("%"+searchForm.getString("SEARCH_PROVINCE")+"%");
    	}
    	if(!searchForm.empty("SEARCH_ZIPCODE")){
    		sql.append(" AND  ZIPCODE LIKE ? ");
			searchForm.setString("%"+searchForm.getString("SEARCH_ZIPCODE")+"%");
    	}
    	
        sql.append("  ORDER BY  PROVINCE,DISTRICT,SUB_DISTRICT,ZIPCODE");

        logger.debug("sql>>"+sql.toString());
		return sql.toString();
	}
}
