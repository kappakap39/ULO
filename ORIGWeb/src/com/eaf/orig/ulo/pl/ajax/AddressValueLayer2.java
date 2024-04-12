package com.eaf.orig.ulo.pl.ajax;

import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.system.LoadXML;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.TooltipResourceUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class AddressValueLayer2 implements AjaxDisplayGenerateInf {
	private static Logger logger = Logger.getLogger(AddressValueLayer2.class);
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		JsonObjectUtil _JsonObjectUtil = new JsonObjectUtil();
		try{
			ValueListM valueListM = (ValueListM)request.getSession().getAttribute("VALUE_LIST");
			
			int atPage = 1;
			
			int itemsPerPage = valueListM.getItemsPerPage();
			
			String action = valueListM.getSearchAction();
			String isNextPage =  request.getParameter("isNextPage");
			String atPageStr = request.getParameter("atPage");
			String itemsPerPageStr = request.getParameter("itemsPerPage");
	        String desc = request.getParameter("desc");
	        			
			if(atPageStr!=null && !atPageStr.equals("")) atPage = Integer.parseInt(atPageStr);
			
			if(itemsPerPageStr!=null && !itemsPerPageStr.equals("")){ 
				int itemsPerPageTemp = Integer.parseInt(itemsPerPageStr);
				if(itemsPerPage!=itemsPerPageTemp){ 
					itemsPerPage = itemsPerPageTemp;
					valueListM.setAtPage(1);
				}
			}
			
			valueListM.setItemsPerPage(itemsPerPage);
			valueListM.setAtPage(atPage);
			
			if(isNextPage != null && !"".equals(isNextPage)){
				valueListM.setNextPage(Boolean.parseBoolean(isNextPage));
			}
			if(!valueListM.isNextPage()){
				String sql = "";
				valueListM.getSqlCriteria().clear();
				if(OrigConstant.AddressAction.LOAD_DISTRICT.equals(action)){
					sql = generateSQLDistrict(desc, valueListM, request);
				}else if(OrigConstant.AddressAction.LOAD_NATIONAL.equals(action)){
					sql = generateSQLNational(desc, valueListM);
				}else if(OrigConstant.AddressAction.LOAD_PROVINCE.equals(action)){
					sql = generateSQLProvince(desc, valueListM, request);
				}else if(OrigConstant.AddressAction.LOAD_SUB_DISTRICT.equals(action)){
					sql = generateSQLSubDistrict(desc, valueListM, request);
				}else if(OrigConstant.AddressAction.LOAD_ZIPCODE.equals(action)){
					sql = generateSQLZipcode(desc, valueListM);
				}
				
				valueListM.setSQL(sql);
			}
			
			if(valueListM.getSqlCriteria() != null && !valueListM.getSqlCriteria().isEmpty()){
				valueListM = PLORIGEJBService.getORIGDAOUtilLocal().getResult2(valueListM);
			}else{
				valueListM = PLORIGEJBService.getORIGDAOUtilLocal().getResult(valueListM);
			}
			
			logger.debug("valueListM.getAtPage():" + valueListM.getAtPage());
			request.getSession().setAttribute("VALUE_LIST", valueListM);

			_JsonObjectUtil.CreateJsonObject("div_address_layer2_record_list", drowItemList(valueListM, request));
			_JsonObjectUtil.CreateJsonObject("div_address_layer2_value_list", drowValueList(valueListM, desc, request));
			return _JsonObjectUtil.returnJson();
		}catch(Exception e){
			logger.fatal(e.toString());
			return null;
		}
	}
	
	private String drowItemList(ValueListM valueListM, HttpServletRequest request){
		StringBuffer itemList = new StringBuffer();
		itemList.append("<table class=\"TableFrame\">");
			itemList.append("<tr class=\"Header\">");
				itemList.append("<td align=\"center\" width=\"20%\">" + PLMessageResourceUtil.getTextDescription(request, "CODE") + "</td>");
				itemList.append("<td align=\"center\" width=\"80%\">" + PLMessageResourceUtil.getTextDescription(request, "DESCRIPTION") + "</td>");
			itemList.append("</tr> ");
		Vector valueList = valueListM.getResult(); 
		if(valueList != null && valueList.size() > 1){
			for(int i=1;i<valueList.size();i++){
				Vector elementList = (Vector)valueList.get(i);
				String styleTr = ((i-1)%2==0)?"ResultEven":"ResultOdd";
				if(elementList.size()!=9){
			itemList.append("<tr class=\"Result-Obj "+styleTr+"\" onclick=\"javascript:setData('"+elementList.elementAt(1)+"','"+HTMLRenderUtil.displayText((String)elementList.elementAt(2))+"')\">");
				}else{
			itemList.append("<tr class=\"Result-Obj "+styleTr+"\" onclick=\"javascript:setDataAddress('"+elementList.elementAt(1)+"','"+HTMLRenderUtil.displayText((String)elementList.elementAt(2))+"','"+HTMLRenderUtil.displayText((String)elementList.elementAt(3))+"','"+HTMLRenderUtil.displayText((String)elementList.elementAt(4))+"','"+HTMLRenderUtil.displayText((String) elementList.elementAt(5))+"','"+HTMLRenderUtil.displayText((String)elementList.elementAt(6))+"','"+HTMLRenderUtil.displayText((String) elementList.elementAt(7))+"','"+HTMLRenderUtil.displayText((String)elementList.elementAt(8))+"')\">");		
				}
				itemList.append("<td width=\"20%\">"+elementList.elementAt(1)+"</td>");
				itemList.append("<td width=\"80%\" class=\"TextTDLeft\">"+HTMLRenderUtil.displayText((String)elementList.elementAt(2))+"</td>");
			}
		}else{
			itemList.append("<tr><td align=\"center\" colspan=\"2\">No record found</td></tr>");
		}
		itemList.append("</table>");
		logger.debug("@@@@@ itemList :"+itemList.toString());
		return itemList.toString();
	}
	private String drowValueList(ValueListM valueListM, String searchCode, HttpServletRequest request){
		TooltipResourceUtil tooltipUtil=TooltipResourceUtil.getInstance();
		StringBuffer valueList = new StringBuffer();
		Vector resultVt=null;
	   	if (valueListM == null) {
		    valueListM = new  ValueListM();
		}
		resultVt=valueListM.getResult();  
		if(resultVt==null){
			resultVt=new Vector();
		}
		String popup_search ="popup_search";
		if(valueListM.getTextboxCode()!=null&&valueListM.getTextboxCode().equalsIgnoreCase("title_eng")){
			popup_search=null;
			popup_search="popup_search_en";
		}

		String code = searchCode;
		if((resultVt != null) && (resultVt.size() > 1)){
			
			HashMap pagePerPageMap = LoadXML.getPagePerPageMap();
			HashMap itemPerPageMap = LoadXML.getItemPerPageMap();
					
			int pagePerPage = (pagePerPageMap != null && pagePerPageMap.get("1") != null)?Integer.parseInt((String)pagePerPageMap.get("1")):1;
			int allItemCount = valueListM.getCount();
			System.out.println("allItemCount = "+allItemCount);
			// int itemsPerPage = valueListM.getItemsPerPage();
			int itemsPerPage = 20;
			int pageCount = ((allItemCount%itemsPerPage)==0)?(allItemCount/itemsPerPage):(allItemCount/itemsPerPage)+1;
			int periodCount = ((pageCount%pagePerPage)==0)?(pageCount/pagePerPage):(pageCount/pagePerPage)+1;
			int atPage = valueListM.getAtPage();
			int atPeriod = ((atPage%pagePerPage)==0)?(atPage/pagePerPage):((atPage/pagePerPage)+1);
			int beginPage = ((atPeriod-1)*pagePerPage)+1;
			int endPage = ((beginPage+pagePerPage-1)>pageCount)?pageCount:(beginPage+pagePerPage-1);
			
			valueList.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">");
				valueList.append("<tr>");
					valueList.append("<td align=\"left\" width=\"35%\">");
						valueList.append("<input type=\"text\" name=\"code\" id=\"code\" class=\"textbox\" maxlength=\"100\" value=\""+((code != null && !"".equals(code))? code:"")+"\" size=\"10\" onMouseOver=\""+tooltipUtil.getTooltip(request,popup_search)+"\">&nbsp;&nbsp;");
						valueList.append("<input type=\"button\" name=\"Search\" class=\"buttonNew\" value=\""+PLMessageResourceUtil.getTextDescription(request, "SEARCH")+"\" onclick=\"javascript:clickSearch()\">");
					valueList.append("</td>");
					valueList.append("<td class=\"inputL\">");
			if(pageCount==0){pageCount=pageCount+1;}
						valueList.append(PLMessageResourceUtil.getTextDescription(request, "PAGE")+" : "+atPage+" / "+pageCount);
			if(atPeriod!=1){
						valueList.append("&nbsp;<A href=\"javascript:clickPageList('"+(beginPage-1)+"')\">&lt;&lt;</A>&nbsp;&nbsp;");
			}
			for(int i=beginPage;i<=endPage;i++){
				if(i!=atPage){
						valueList.append("&nbsp;<A href=\"javascript:clickPageList('"+i+"')\"><FONT class=\"bk\">"+i+"</FONT></A>&nbsp;");
				}else{
						valueList.append("&nbsp;<FONT color=\"red\">"+i+"</FONT>&nbsp;");
				}
			}
			if(atPeriod!=periodCount){
						valueList.append("&nbsp;&nbsp;<A href=\"javascript:clickPageList('"+(beginPage+pagePerPage)+"')\">&gt;&gt;</A>&nbsp;&nbsp;");
			}
						valueList.append("&nbsp;<SELECT name=\"select2\" id=\"select2\" onchange=\"javascript:clickItemPerPageList(this.value)\">");
						valueList.append("<OPTION value='"+itemsPerPage+"'>"+itemsPerPage+"</OPTION></SELECT>");
					valueList.append("</td>");
				valueList.append("<tr>");
			valueList.append("</table>");
		}else{
			valueList.append("<input type=\"text\" name=\"code\" id=\"code\" class=\"textbox\" maxlength=\"100\" value=\""+((code != null && !"".equals(code))? code:"")+ "\" size=\"10\" onMouseOver=\""+tooltipUtil.getTooltip(request,popup_search)+"\">&nbsp;&nbsp;");
			valueList.append("<input type=\"button\" name=\"Search\" class=\"buttonNew\" value=\""+PLMessageResourceUtil.getTextDescription(request,"SEARCH")+"\" onclick=\"javascript:clickSearch()\">");
		}
		valueList.append("<input type=\"hidden\" name=\"atPage\" id=\"atPage\">");
		valueList.append("<input type=\"hidden\" name=\"itemsPerPage\" id=\"itemsPerPage\">");
		valueList.append("<input type=\"hidden\" name=\"textboxCode\" id=\"textboxCode\" value=\""+valueListM.getTextboxCode()+"\">");
		valueList.append("<input type=\"hidden\" name=\"textboxDesc\" id=\"textboxDesc\" value=\""+valueListM.getTextboxDesc()+"\">");
		
		logger.debug("@@@@@ valueList :"+valueList.toString());
		return valueList.toString();
	}
	
	private String generateSQLZipcode(String desc, ValueListM valueListM){
		logger.debug("@@@@@ generateSQLZipcode with desc:"+ desc);
		StringBuffer sql = new StringBuffer();
		int index = 0;
		sql.append(" select * from (");
		sql.append(" SELECT ZIPCODE,   (select sub_district_desc from ms_sub_district s where s.sub_district_id=z.SUB_DISTRICT_ID)|| ' '");
		sql.append(" ||(select district_desc from ms_district d where d.district_id=z.DISTRICT_ID)||' ' ");
		sql.append(" ||(select province_desc from ms_province p where p.province_id=z.PROVINCE_ID) zip_desc");  
		sql.append("  ,z.SUB_DISTRICT_ID, (select sub_district_desc from ms_sub_district s where s.sub_district_id=z.SUB_DISTRICT_ID) sub_district_desc");
		sql.append("  ,z.DISTRICT_ID, (select district_desc from ms_district d where d.district_id=z.DISTRICT_ID) district_desc");
		sql.append("  ,z.PROVINCE_ID, (select province_desc from ms_province p where p.province_id=z.PROVINCE_ID) province_desc");
		sql.append(" FROM MS_ZIPCODE z ");
		sql.append(" ) ");
		sql.append("WHERE 1=1");
		
		if(!ORIGUtility.isEmptyString(desc)){
			 sql.append(" AND zip_desc like ? ");
			 valueListM.setString(++index,"%"+desc+"%");
		}
		
		sql.append(" ORDER BY ZIPCODE ");
		return sql.toString();
	}
	
	private String generateSQLNational(String desc, ValueListM valueListM){
		logger.debug("@@@@@ generateSQLNational with desc:"+ desc);
		StringBuffer sql = new StringBuffer();
		int index = 0;
		sql.append("SELECT CHOICE_NO, DISPLAY_NAME ");
		sql.append("FROM LIST_BOX_MASTER WHERE FIELD_ID='104' ");
        
		if(!ORIGUtility.isEmptyString(desc)){
		    sql.append("AND DISPLAY_NAME like ? ");
		    valueListM.setString(++index,"%"+desc+"%");
		}
		sql.append("ORDER BY CHOICE_NO ");
		return sql.toString();
	}
	
	private String generateSQLDistrict(String desc, ValueListM valueListM, HttpServletRequest request){
		logger.debug("@@@@@ generateSQLDistrict with desc:"+ desc);
		
		String amphur = request.getParameter("amphur");
        String province = request.getParameter("province");
        logger.debug("@@@@@ generateSQLDistrict with amphur:"+ amphur);
        logger.debug("@@@@@ generateSQLDistrict with province:"+ province);
        
		StringBuffer sql = new StringBuffer();
		int index = 0;
		sql.append("SELECT DISTRICT_ID, DISTRICT_DESC ");
		sql.append("FROM MS_DISTRICT WHERE 1=1  ");
        
		if(!ORIGUtility.isEmptyString(desc)){
		    sql.append(" AND DISTRICT_DESC like ? ");
		    valueListM.setString(++index,"%"+desc.toUpperCase()+"%");
		}else if(!ORIGUtility.isEmptyString(amphur)){
		    sql.append(" AND DISTRICT_ID like ? ");
		    valueListM.setString(++index,"%"+amphur.toUpperCase()+"%");
		}
		
		if(!ORIGUtility.isEmptyString(province)){
		    sql.append(" AND PROVINCE_ID = ? ");
		    valueListM.setString(++index,province);
		}else{
			province = "9999";
		    sql.append(" AND PROVINCE_ID = ? ");
		    valueListM.setString(++index,province);
		}

		sql.append(" ORDER BY DISTRICT_ID ");
		return sql.toString();
	}
	
	private String generateSQLSubDistrict(String desc, ValueListM valueListM, HttpServletRequest request){
		logger.debug("@@@@@ generateSQLSubDistrict with desc:"+ desc);
		
		String tambol = request.getParameter("tambol");
		String amphur = request.getParameter("amphur");
        logger.debug("@@@@@ generateSQLSubDistrict with tambol:"+ tambol);
        logger.debug("@@@@@ generateSQLSubDistrict with amphur:"+ amphur);
        
		StringBuffer sql = new StringBuffer();
		int index = 0;
		sql.append("SELECT SUB_DISTRICT_ID, SUB_DISTRICT_DESC ");
		sql.append("FROM MS_SUB_DISTRICT WHERE 1=1");
		
		if(!ORIGUtility.isEmptyString(desc)){
		    sql.append("AND SUB_DISTRICT_DESC like ? ");
		    valueListM.setString(++index,"%"+desc.toUpperCase()+"%");
		}else if(!ORIGUtility.isEmptyString(tambol)){
		    sql.append(" AND SUB_DISTRICT_ID = ? ");
		    valueListM.setString(++index,tambol);
		}
					
		if(!ORIGUtility.isEmptyString(amphur)){
		    sql.append(" AND DISTRICT_ID = ? ");
		    valueListM.setString(++index,amphur);
		}else{
			 amphur="9999";
			 sql.append(" AND DISTRICT_ID = ? ");
			 valueListM.setString(++index,amphur);
		}
		
		sql.append(" ORDER BY SUB_DISTRICT_ID ");
		return sql.toString();
	}
	
	private String generateSQLProvince(String desc, ValueListM valueListM, HttpServletRequest request){
		logger.debug("@@@@@ generateSQLProvince with desc:"+ desc);
		
        String zipcode = request.getParameter("zipcode");
        logger.debug("@@@@@ generateSQLProvince zipcode:"+zipcode);
		
		StringBuffer sql = new StringBuffer();
		int index = 0;
		sql.append("SELECT PROVINCE_ID, PROVINCE_DESC, ACTIVE_STATUS ");
		sql.append("FROM MS_PROVINCE WHERE 1=1");
        
		if(!ORIGUtility.isEmptyString(zipcode)){
		    sql.append("AND PROVINCE_ID IN (SELECT MS_PROVINCE FROM MS_ZIPCODE WHERE ZIPCODE_ID = ?) ");
		    valueListM.setString(++index,zipcode);
		}
		if(!ORIGUtility.isEmptyString(desc)){
		    sql.append("AND PROVINCE_DESC like ? ");
		    valueListM.setString(++index,"%"+desc+"%");
		}
		
		sql.append("ORDER BY PROVINCE_ID ");
		return sql.toString();
	}

}
