package com.eaf.orig.ulo.pl.ajax;


import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLProjectCodeDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class GetProjectCode implements AjaxDisplayGenerateInf {
	Logger log = Logger.getLogger(GetProjectCode.class);
	
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException{
		
		String project_code = request.getParameter("project_code");
		String product_feature = ORIGCacheUtil.getInstance().getSystemIDFromListbox(request.getParameter("product_feature"), "35", "2");
		String exception_project = request.getParameter("exception_project");
		
		log.debug("project_code >> "+project_code);
		log.debug("product_feature >> "+product_feature);
		log.debug("exception_project >> "+exception_project);	
		
		if(OrigUtil.isEmptyString(project_code)){
			return null;
		}		

		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(request);
		
		String busClassID = applicationM.getBusinessClassId();
		ORIGDAOUtilLocal origDaoBean =  PLORIGEJBService.getORIGDAOUtilLocal();
		
		PLProjectCodeDataM projectCodeM = origDaoBean.Loadtable(project_code,product_feature,busClassID,exception_project);
		
		if(null == projectCodeM || OrigUtil.isEmptyString(projectCodeM.getProjectcode())){
			return null;
		}
				
		int applicantFieldID = 110;
		
//		#septemwi comment
//		if(!OrigUtil.isEmptyString(busClassID) && busClassID.contains("XS")){
//			applicantFieldID = 35;
//		}
		
		ORIGCacheUtil origc = new ORIGCacheUtil();
		JsonObjectUtil jObj = new JsonObjectUtil();
		
			jObj.CreateJsonObject("startdate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getStartprojectdate())));
			jObj.CreateJsonObject("div_startdate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getStartprojectdate())));
			jObj.CreateJsonObject("enddate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getEndprojectdate())));
			jObj.CreateJsonObject("div_enddate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getEndprojectdate())));
			jObj.CreateJsonObject("details", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getProjectdesc()));
			jObj.CreateJsonObject("div_details", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getProjectdesc()));
			jObj.CreateJsonObject("promotion", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getPromotion()));
			jObj.CreateJsonObject("div_promotion", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getPromotion()));
			jObj.CreateJsonObject("approvedate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getApprovedate())));
			jObj.CreateJsonObject("div_approvedate", HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.dateToStringTH(projectCodeM.getApprovedate())));
			jObj.CreateJsonObject("customergroup", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getCustomertype()));
			jObj.CreateJsonObject("div_customergroup", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getCustomertype()));
			jObj.CreateJsonObject("projectCode", project_code);
			jObj.CreateJsonObject("professionCode", HTMLRenderUtil.DisplayReplaceLineWithNull(projectCodeM.getProfessionCode()));
			jObj.CreateJsonObject("Priority", HTMLRenderUtil.displayHTML(projectCodeM.getPriority()));
			jObj.CreateJsonObject("application_property", origc.getORIGCacheDisplayNameDataM(applicantFieldID, projectCodeM.getApplicationProperty()));
			jObj.CreateJsonObject("div_projectcode", HTMLRenderUtil.DisplayPopUpProjectCode(project_code,HTMLRenderUtil.DISPLAY_MODE_VIEW,"13","project_code","textbox-projectcode","13","...","button-popup"));
			jObj.CreateJsonObject("projectcode_displaymode", HTMLRenderUtil.DISPLAY_MODE_VIEW);
			jObj.CreateJsonObject("projectcode_buttonmode", HTMLRenderUtil.DISPLAY_MODE_EDIT);
		
		return jObj.returnJson();
	}
	

}
