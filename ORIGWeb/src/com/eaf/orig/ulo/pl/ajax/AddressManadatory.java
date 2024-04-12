package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;

public class AddressManadatory implements AjaxDisplayGenerateInf{
	
	static Logger logger = Logger.getLogger(AddressManadatory.class);
	
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		
		String address_type = request.getParameter("address_type");
		String zipcode = request.getParameter("zipcode");
		String country_no = request.getParameter("country_no");
		String address_status = request.getParameter("address_status");
		
		JsonObjectUtil json = new JsonObjectUtil();
		
		if(OrigUtil.isEmptyString(address_type)){
			json.CreateJsonObject("address_pop_err_div","<div>"+ErrorUtil.getShortErrorMessage(request,"ADDRESS_TYPE")+"</div>");
		}		
		if(OrigUtil.isEmptyString(zipcode)){
			json.CreateJsonObject("address_pop_err_div","<div>"+ ErrorUtil.getShortErrorMessage(request,"ZIPCODE")+"</div>");
		}
		if(OrigUtil.isEmptyString(country_no)){
			json.CreateJsonObject("address_pop_err_div", "<div>"+ErrorUtil.getShortErrorMessage(request,"COUNTRY")+"</div>");
		}
		if("02".equals(address_type)){
			if(OrigUtil.isEmptyString(address_status)){
				json.CreateJsonObject("address_pop_err_div","<div>"+ ErrorUtil.getShortErrorMessage(request,"ADDRESS_STYLE")+"</div>");
			}
		}
		
		String number = request.getParameter("number");
		String building = request.getParameter("building");
		String soi = request.getParameter("soi");
		String moo = request.getParameter("moo");
		String road = request.getParameter("road");
		
		String province = request.getParameter("province");
		String province_desc = request.getParameter("province_desc");
		String amphur_desc = request.getParameter("amphur_desc");
		String tambol_desc = request.getParameter("tambol_desc");
		
		String companyTitle = request.getParameter("address_company_title");
		String companyName = request.getParameter("address_company_name");
		String department = request.getParameter("occ_department");
		
		if("03".equals(address_type)){
			if(OrigUtil.isEmptyString(companyName)){
				json.CreateJsonObject("address_pop_err_div","<div>"+ ErrorUtil.getShortErrorMessage(request,"MSG_ERROR_REQUIRE_COMPANY_NAME")+"</div>");
			}
		}
		
		CardLinkLine cardlink = new CardLinkLine();		
		String cardLinkLine1 = cardlink.getCardLinkLine1(address_type, number, building, soi, moo, road, companyTitle, companyName, department);
		String cardLinkLine2 = cardlink.getCardLinkLine2(province, province_desc, amphur_desc, tambol_desc);
		
		if(OrigUtil.isEmptyString(cardLinkLine1)){	
			json.CreateJsonObject("address_pop_err_div","<div>"+ ErrorUtil.getShortErrorMessage(request,"MSG_ERROR_CARDLINK_LINE_1")+"</div>");
		}else{
			if(!OrigUtil.isEmptyString(cardLinkLine1)){	
				if(cardLinkLine1.length() > 50){
					json.CreateJsonObject("address_pop_err_div","<div>"+ ErrorUtil.getShortErrorMessage(request,"MSG_ERROR_CARDLINK_LINE_1_MORE_50")+"</div>");
				}
			}
		}
		
		if(OrigUtil.isEmptyString(cardLinkLine2)){	
			json.CreateJsonObject("address_pop_err_div","<div>"+ ErrorUtil.getShortErrorMessage(request,"MSG_ERROR_CARDLINK_LINE_2")+"</div>");
		}else{
			if(!OrigUtil.isEmptyString(cardLinkLine2)){	
				if(cardLinkLine2.length() > 40){
					json.CreateJsonObject("address_pop_err_div","<div>"+ ErrorUtil.getShortErrorMessage(request,"MSG_ERROR_CARDLINK_LINE_2_MORE_40")+"</div>");
				}
			}
		}
		
		return json.returnJson();
	}

}
