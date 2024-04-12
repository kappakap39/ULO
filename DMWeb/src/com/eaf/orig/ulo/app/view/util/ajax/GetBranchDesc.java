package com.eaf.orig.ulo.app.view.util.ajax;

//import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
//import com.eaf.core.ulo.common.properties.CacheControl;
//import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.app.view.util.dih.model.KbankSaleInfoDataM;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.DMFormHandler;
import com.eaf.orig.ulo.model.dm.DocumentManagementDataM;

public class GetBranchDesc implements AjaxInf  {
	private static transient Logger logger = Logger.getLogger(GetBranchDesc.class);
	String CACHE_SALE_INFO = SystemConstant.getConstant("CACHE_SALE_INFO");	
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		ResponseData responseData = new ResponseData();
		JSONUtil json = new JSONUtil();	
		DocumentManagementDataM dmManageDataM = DMFormHandler.getObjectForm(request);
		try {
			String DM_REQUESTED_USER = request.getParameter("DM_REQUESTED_USER");
			logger.debug("DM_REQUESTED_USER : "+DM_REQUESTED_USER);			
//			HashMap<String,Object> hData = CacheControl.get(CACHE_SALE_INFO, DM_REQUESTED_USER); 
//			String TH_DEPT_NM = (String)hData.get("TH_DEPT_NM");
//			String OFFC_PH1 = (String)hData.get("OFFC_PH1");
//			String MBL_PH1 = (String)hData.get("MBL_PH1");
//			logger.debug(">>hData>>>"+hData);
//			logger.debug(">>>DM_REQUESTED_USER>>>"+DM_REQUESTED_USER);
//			logger.debug(">>>TH_DEPT_NM>>>"+TH_DEPT_NM);
//			logger.debug(">>>OFFC_PH1>>>"+OFFC_PH1);
//			logger.debug(">>>MBL_PH1>>>"+MBL_PH1);
//						
			DIHQueryResult<KbankSaleInfoDataM> queryResult = DIHProxy.getKbankSaleInfo(DM_REQUESTED_USER);
			KbankSaleInfoDataM kbankSaleInfo = queryResult.getResult();
			String TH_DEPT_NM = kbankSaleInfo.getThDeptName();
			String OFFC_PH1 = kbankSaleInfo.getOfficePhone();
			String MBL_PH1 = kbankSaleInfo.getMobileNo();			
			if(!Util.empty(TH_DEPT_NM)){
				json.put("DM_DEPARTMENT",FormatUtil.displayText(TH_DEPT_NM));
				json.put("DM_OFFICE_PHONE_NO",FormatUtil.displayText(OFFC_PH1));
				json.put("DM_MOBILE_NO",FormatUtil.displayText(MBL_PH1));
			}
			logger.debug("TH_DEPT_NM : "+TH_DEPT_NM);
			logger.debug("OFFC_PH1 : "+OFFC_PH1);
			logger.debug("MBL_PH1 : "+MBL_PH1);
			logger.debug("dmManageDataM.getStatus() : "+dmManageDataM.getStatus());
						
			String AUTH_USER  =CacheControl.getName("UsAuth", DM_REQUESTED_USER, request);
			logger.debug("AUTH_USER>>>>>"+AUTH_USER);
			if(!Util.empty(AUTH_USER) && MConstant.DM_STATUS.AVAILABLE.equals(dmManageDataM.getStatus())){
				json.put("AUTH_USER",SystemConstant.getConstant("FLAG_YES"));
			}else{
				json.put("AUTH_USER",SystemConstant.getConstant("FLAG_NO"));
			}			
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		responseData.setData(json.getJSON());
		return responseData;
	}

}
