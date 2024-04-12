package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class AddressCompany  implements AjaxDisplayGenerateInf {
	
	static Logger logger = Logger.getLogger(AddressCompany.class);
	
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		
		String address_type = request.getParameter("address_type");
		String companyTitle = request.getParameter("companyTitle");
		String companyName = request.getParameter("companyName");
						
		PLOrigFormHandler origFrom = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
		
		PLApplicationDataM applicationM = origFrom.getAppForm();
		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		PLAddressDataM addressM = personalM.getAddressDataM(address_type);
		
		if(null == addressM){
			addressM = new PLAddressDataM();
		}
		
		String department = "";
		String building = "";
		JsonObjectUtil json = new JsonObjectUtil();		
		if("03".equals(address_type)){
			if(!OrigUtil.isEmptyString(addressM.getCompanyName())){
				companyTitle = addressM.getCompanyTitle();
				companyName = addressM.getCompanyName();
			}
			department = personalM.getDepartment();
			building = addressM.getBuilding();
			json.CreateJsonObject("tr-address-company", HTML_CompanyName(companyTitle, companyName, HTMLRenderUtil.DISPLAY_MODE_EDIT,applicationM.getBusinessClassId()));
			json.CreateJsonObject("td-address-department", HTML_Department(department, HTMLRenderUtil.DISPLAY_MODE_EDIT));
			json.CreateJsonObject("td-address-building", HTML_Building(building, HTMLRenderUtil.DISPLAY_MODE_EDIT));
			
		}else{
			companyTitle = "";
			companyName = "";
			department = "";
			building = "";
			json.CreateJsonObject("tr-address-company", HTML_CompanyName(companyTitle, companyName, HTMLRenderUtil.DISPLAY_MODE_VIEW,applicationM.getBusinessClassId()));
			json.CreateJsonObject("td-address-department", HTML_Department(department, HTMLRenderUtil.DISPLAY_MODE_VIEW));
			json.CreateJsonObject("td-address-building", HTML_Building(building, HTMLRenderUtil.DISPLAY_MODE_VIEW));
		}
		
		return json.returnJson();
	}
	
	private String HTML_CompanyName(String companyTitle ,String companyName ,String displayMode,String busclassID){
		StringBuilder HTML = new StringBuilder("");
//		HTML.append("<table>");
//			HTML.append("<tr>");
//			HTML.append("<td class='inputL' nowrap='nowrap'>").append(HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(busclassID, 17, companyTitle,"address_company_title",displayMode,"")).append("</td>");
//			HTML.append("<td class='inputL' nowrap='nowrap'>").append(HTMLRenderUtil.displayInputTagScriptAction(companyName, displayMode, "30", "address_company_name", "textbox-workplace","","30")).append("</td>");
//			HTML.append("</tr>");
//		HTML.append("</table>");
		String data = "";
		if(!OrigUtil.isEmptyString(companyName)){
			if(!OrigUtil.isEmptyString(companyTitle)){
				ORIGCacheUtil cache = new ORIGCacheUtil();
				String value = cache.getDisplayNameCache(17, companyTitle);
				if(null != value){
					data = (value).trim();
				}
			}
			data += companyName;
		}
		if(data != null && data.length() > 30){
			data = data.substring(0,30);
		}
		HTML.append(HTMLRenderUtil.displayInputTagScriptAction(data , displayMode, "30", "address_company_name", "textbox-workplace","","30"));
		return HTML.toString();
	}
	
	private String HTML_Department(String department,String displayMode){
		StringBuilder HTML = new StringBuilder("");
		HTML.append(HTMLRenderUtil.displayInputTagScriptAction(department, displayMode, "20", "occ_department","textbox-workplace","","20"));
		return HTML.toString();
	}
	
	private String HTML_Building(String department,String displayMode){
		StringBuilder HTML = new StringBuilder("");
		HTML.append(HTMLRenderUtil.displayInputTagScriptAction(department,displayMode,"30","building","textbox-address-building","","30"));
		return HTML.toString();
	}
	
}
