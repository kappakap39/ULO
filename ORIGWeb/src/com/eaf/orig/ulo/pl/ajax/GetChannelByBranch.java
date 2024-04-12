package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class GetChannelByBranch implements AjaxDisplayGenerateInf {
	static Logger logger = Logger.getLogger(GetChannelByBranch.class);
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		
		String branch_code = request.getParameter("branch_code");
		String displayMode = request.getParameter("displayMode");
		String channel 	   = request.getParameter("channel");
				
		ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();
		String group = origBean.getBranchGroupByBranchNo(branch_code);
		
//		logger.debug("branch group >> "+group);
		
		ORIGCacheUtil origcUtil = new ORIGCacheUtil();
		Vector vChannel = (Vector)origcUtil.getvListboxbyFieldID(50);
		Vector vNewChannel = new Vector();
		if(null != vChannel){
			if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode)){
				for(ORIGCacheDataM dataM :(Vector<ORIGCacheDataM>) vChannel){
					if(dataM.getSystemID1().equals(group) && OrigConstant.Status.STATUS_ACTIVE.equals(dataM.getActiveStatus())){
						vNewChannel.add(dataM);
					}
				}
			}else{
				for(ORIGCacheDataM dataM :(Vector<ORIGCacheDataM>) vChannel){
					if(dataM.getSystemID1().equals(group)){
						vNewChannel.add(dataM);
					}
				}
			}
		}
		return HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vNewChannel,channel,"channel",displayMode,"");
	}

}
