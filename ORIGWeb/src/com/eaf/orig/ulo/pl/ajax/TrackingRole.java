package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;

public class TrackingRole implements AjaxDisplayGenerateInf{
	static Logger logger = Logger.getLogger(TrackingRole.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		String tracking_group = request.getParameter("tracking_group");
		logger.debug("tracking_group >> "+tracking_group);
		
	    SearchHandler HandlerM = (SearchHandler) request.getSession().getAttribute("SEARCH_DATAM");
		if(HandlerM == null){
			HandlerM = new SearchHandler();
		}
		SearchHandler.SearchDataM searchDataM = HandlerM.getSearchM();
		if(searchDataM == null){
			searchDataM = new SearchHandler.SearchDataM();
		}
		
		ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
		Vector<ORIGCacheDataM> vRole = (Vector<ORIGCacheDataM>)origCache.getRoleTracking();
		if(!OrigUtil.isEmptyString(tracking_group)){
			vRole = getRoleByGroup(vRole, tracking_group);
		}
		JsonObjectUtil tool = new JsonObjectUtil();
			tool.CreateJsonObject("tr_tracking_role", HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vRole,searchDataM.getRole(),"tracking_role",HTMLRenderUtil.DISPLAY_MODE_EDIT,""));
		return tool.returnJson();
	}
	public Vector<ORIGCacheDataM> getRoleByGroup(Vector<ORIGCacheDataM> vRole,String tracking_group){
		Vector<ORIGCacheDataM>  data = new Vector<ORIGCacheDataM>();
			for(ORIGCacheDataM dataM : vRole){
				if(null != tracking_group && tracking_group.equals(dataM.getSystemID2())){
					data.add(dataM);
				}
			}
		return data;		
	}
}
