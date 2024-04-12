package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ResponseData;
//import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.app.view.util.dih.model.KbankSaleInfoDataM;

public class DMDocumentManageForm implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(DMDocumentManageForm.class);
//	String CACHE_SALE_INFO = SystemConstant.getConstant("CACHE_SALE_INFO");		
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		ResponseData responseData = new ResponseData();
		JSONUtil json = new JSONUtil();	
		try{
			UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
//			HashMap<String,Object> hData = CacheControl.get(CACHE_SALE_INFO, userM.getUserNo());
//			String REQUEST = (String)hData.get("TH_DEPT_NM");
//			String BRANCH = (String)hData.get("EN_PRN_DEPT_NM");	
			DIHQueryResult<KbankSaleInfoDataM> queryResult = DIHProxy.getKbankSaleInfo(userM.getUserNo());
			KbankSaleInfoDataM kbankSaleInfo = queryResult.getResult();
			String REQUEST = kbankSaleInfo.getThDeptName();
			String BRANCH = kbankSaleInfo.getEnDeptName();
			logger.debug("USER_NO : "+userM.getUserNo());
			logger.debug("REQUEST : "+REQUEST);
			logger.debug("BRANCH : "+BRANCH);
			if(!Util.empty(REQUEST)){
				json.put("REQUEST",FormatUtil.displayText(REQUEST));
				json.put("BRANCH",FormatUtil.displayText(BRANCH));
			}			
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		responseData.setData(json.getJSON());
		return responseData;
	}
}
