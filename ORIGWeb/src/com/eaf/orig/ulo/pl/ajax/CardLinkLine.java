package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

//import com.eaf.orig.cache.util.ORIGCacheUtil;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;

public class CardLinkLine implements AjaxDisplayGenerateInf{
	
	static Logger logger = Logger.getLogger(CardLinkLine.class);
	
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		
		String address_type = request.getParameter("address_type");
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
		
		JsonObjectUtil json = new JsonObjectUtil();
		
		String cardLinkLine1 = getCardLinkLine1(address_type, number, building, soi, moo, road, companyTitle, companyName, department);
		String cardLinkLine2 = getCardLinkLine2(province, province_desc, amphur_desc, tambol_desc);
		
		String countLine1 = "<div style='color: green;'>0</div>";
		String countLine2 = "<div style='color: green;'>0</div>";
		
		if(!OrigUtil.isEmptyString(cardLinkLine1)){	
			String count = String.valueOf(cardLinkLine1.length());
			if(cardLinkLine1.length() > 50){
				countLine1 = "<div style='color: red;'>"+count+"</div>";
			}else{
				countLine1 = "<div style='color: green;'>"+count+"</div>";
			}
		}
		
		if(!OrigUtil.isEmptyString(cardLinkLine2)){
			String count = String.valueOf(cardLinkLine2.length());
			if(cardLinkLine2.length() > 40){
				countLine2 = "<div style='color: red;'>"+count+"</div>";
			}else{
				countLine2 = "<div style='color: green;'>"+count+"</div>";
			}
		}
		
		json.CreateJsonObject("cardlink_line1", cardLinkLine1);
		json.CreateJsonObject("cardlink_line2", cardLinkLine2);
		
		json.CreateJsonObject("countLine1", countLine1);
		json.CreateJsonObject("countLine2", countLine2);
		
		return json.returnJson();
	}
	
	public String getCardLinkLine2(String province ,String province_desc ,String amphur_desc ,String tambol_desc){
		StringBuilder STR = new StringBuilder("");
		if("10".equals(province)){
			if(!OrigUtil.isEmptyString(tambol_desc)){
				STR.append(tambol_desc.trim()).append(" ");
			}
		}else{
			if(!OrigUtil.isEmptyString(tambol_desc)){
				STR.append("\u0E15\u002E").append(tambol_desc.trim()).append(" ");
			}
		}
		if("10".equals(province)){
			if(!OrigUtil.isEmptyString(amphur_desc)){
				STR.append(amphur_desc.trim()).append(" ");
			}
		}else{
			if(!OrigUtil.isEmptyString(amphur_desc)){
				STR.append("\u0E2D\u002E").append(amphur_desc.trim()).append(" ");
			}
		}
		if(!OrigUtil.isEmptyString(province_desc)){
			STR.append(province_desc.trim()).append(" ");
		}
		return (STR.toString()).trim();
	}
	
	public String getCardLinkLine1(String address_type ,String number ,String building, String soi 
								,String moo,String road,String companyTitle ,String companyName,String department){
		StringBuilder STR = new StringBuilder("");
		
		if("03".equals(address_type)){
			if(!OrigUtil.isEmptyString(companyName)){
//				String name = "";
//				#septem comment remove field
//				if(!OrigUtil.isEmptyString(companyTitle)){
//					ORIGCacheUtil cache = new ORIGCacheUtil();
//					String value = cache.getDisplayNameCache(17, companyTitle);
//					if(null != value){
//						name = (value).trim();
//					}
//				}
//				name += companyName;
				STR.append(companyName.trim()).append(" ");
			}
			
			if(!OrigUtil.isEmptyString(building)){
				STR.append(building.trim()).append(" ");
			}
						
			if(!OrigUtil.isEmptyString(department)){
				STR.append(department.trim()).append(" ");
			}
		}

		if(!OrigUtil.isEmptyString(moo)){
			if("01".equals(address_type)){
				if(!OrigUtil.isEmptyString(number)){
					STR.append(number.trim()).append(" ");
				}
				STR.append((moo).trim()).append(" ");
			}else{
				if(null != number && ((number).trim()).length() < 7 && null != moo && ((moo).trim()).length() > 1 ){
					if(!OrigUtil.isEmptyString(number)){
						 STR.append(number.trim()).append(" ").append("\u0E21").append(" ");
					}else{
						if(null != moo && ((moo).trim()).length() == 2){
							STR.append("\u0E21").append(" ");
						}
					}
					STR.append((moo).trim()).append(" ");
				}else{	
					if(!OrigUtil.isEmptyString(number)){
						STR.append(number.trim()).append(" ");
					}
					if(null != moo && ((moo).trim()).length() == 1){
						STR.append("\u0E21").append((moo).trim()).append(" ");
					}else{
						if(OrigUtil.isEmptyString(number)){
							if(null != moo && ((moo).trim()).length() == 2){
								STR.append("\u0E21").append(" ");
							}
							STR.append((moo).trim()).append(" ");
						}else{
							STR.append((moo).trim()).append(" ");
						}
					}
				}
			}
		}else{			
			if(!OrigUtil.isEmptyString(number)){
				STR.append(number.trim()).append(" ");
			}
		}
		
		if(!OrigUtil.isEmptyString(soi)){
			STR.append((soi).trim()).append(" ");
		}
		
		if(!OrigUtil.isEmptyString(road)){
			STR.append((road).trim()).append(" ");
		}
		
		return (STR.toString()).trim();
	}
	
}
