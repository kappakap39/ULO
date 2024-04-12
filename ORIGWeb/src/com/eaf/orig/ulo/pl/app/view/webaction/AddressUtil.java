package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.util.MessageResourceUtil;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.constant.PLBusClassConstant;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.ulo.cache.util.CacheUtil;
//import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
//import com.eaf.orig.ulo.pl.model.app.personal.PLPersonalInfoDataM;

public class AddressUtil {
	
	static Logger logger = Logger.getLogger(AddressUtil.class);
	
	@Deprecated
	public String getAddressTable(Vector addressVect, HttpServletRequest request) {
//
//        StringBuilder tableData = new StringBuilder("");
//        PLAddressDataM addressM = null;
//        ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
//        String personalType = (String) request.getSession().getAttribute("PersonalType");
//        
//        try{
//
//            String addressTypeDesc = null;
//            String addressStatus = null;
//            String disabledChk = "";
//           
//            if (addressVect != null && addressVect.size() > 0) {
//                for (int i = 0; i < addressVect.size(); i++) {
//                	addressM = (PLAddressDataM) addressVect.get(i);
//                    addressTypeDesc = addressM.getAddressType();
//                    addressStatus = cacheUtil.getORIGMasterDisplayNameDataM("AddressStatus", addressM.getStatus());
//                    
//                    String addressDisplay = AddressUtil.getDisplayaddress(request, addressM);
//                    if (PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT.equals(personalType) 
//                    		&& !OrigConstant.ADDRESS_TYPE_HOME.equals(addressM.getAddressType())) {
//
//                        tableData.append("<tr bgcolor='FFFFFF' onmouseover=\"this.style.cursor='hand' ;this.style.background='#D7D6D7'\" onmouseout=\" this.style.background='#FFFFFF'\" >");
//	                    tableData.append("<td class='jobopening2' width='4%'  align='left' >"+ORIGDisplayFormatUtil.displayCheckBox("delete", "addressSeq", String.valueOf(addressM.getAddressSeq()), "") + "</td>");
//	                    tableData.append("<td class='jobopening2' width='4%'  align='center' noWrap='noWrap' onclick=\"javascript:loadPopup('address','LoadPLAddressPopup','1350','350','200','10','" +addressM.getAddressSeq()+i+"','"+addressM.getAddressType()+"','"+personalType+"', 'EDIT')\">" +DataFormatUtility.IntToString(addressM.getAddressSeq())+"</td>");
//	                    tableData.append("<td class='jobopening2' width='10%'  align='center' onclick=\"javascript:loadPopup('address','LoadPLAddressPopup','1350','350','200','10','" +addressM.getAddressSeq()+i+"','"+addressM.getAddressType()+"','"+personalType+"', 'EDIT')\">" + String.valueOf(addressM.getAddressType()) + "</td>");
//	                    tableData.append("<td class='jobopening2' width='35%'  align='left' onclick=\"javascript:loadPopup('address','LoadPLAddressPopup','1350','350','200','10','" +addressM.getAddressSeq()+i+"','"+addressM.getAddressType()+"','"+personalType+"', 'EDIT')\">"
//
//	                    		+ " " + HTMLRenderUtil.displayHTML(addressDisplay)
//	                    	    + "</td>");
//	                    tableData.append("<td class='jobopening2' width='10%' align='center' onclick=\"javascript:loadPopup('address','LoadPLAddressPopup','1350','350','200','10','" +addressM.getAddressSeq()+i+ "','"+addressM.getAddressType()+"','"+personalType+"', 'EDIT')\">" + ORIGDisplayFormatUtil.forHTMLTag(addressM.getPhoneNo1()) + "</td>");
//	                    tableData.append("<td class='jobopening2' width='4%' align='center'  onclick=\"javascript:loadPopup('address','LoadPLAddressPopup','1350','350','200','10','" +addressM.getAddressSeq()+i+ "','"+addressM.getAddressType()+"','"+personalType+"', 'EDIT')\">" + ORIGDisplayFormatUtil.forHTMLTag(addressM.getPhoneExt1()) + "</td>");
//	                    tableData.append("<td class='jobopening2' width='15%' onclick=\"javascript:loadPopup('address','LoadPLAddressPopup','1350','350','200','10','" +addressM.getAddressSeq()+i+ "','"+addressM.getAddressType()+"','"+personalType+"', 'EDIT')\">" + ORIGDisplayFormatUtil.forHTMLTag(addressM.getAddressType()) + "</td>");
//	                    tableData.append("<td class='jobopening2' width='10%' onclick=\"javascript:loadPopup('address','LoadPLAddressPopup','1350','350','200','10','" +addressM.getAddressSeq()+i+ "','"+addressM.getAddressType()+"','"+personalType+"', 'EDIT')\">" + ORIGDisplayFormatUtil.forHTMLTag(addressM.getRemark()) + "</td>");
//	                    tableData.append("</tr>");
//
//                    }
//                }
//            } else {
//            	tableData.append("<tr><td align='center' class='gumaygrey2'>");
//                tableData.append("<table cellpadding='0' cellspacing='0' width='100%' align='center' border='0'>");
//                tableData.append("<tr>");
//                tableData.append("<td class='jobopening2' colspan=\"8\" align=\"center\">No Record</td>");
//                tableData.append("</tr></table></td></tr>");
//            }
//            tableData.append("</table>");
//        } catch (Exception e) {
//            logger.error("Error >> ", e);
//        }
//        return tableData.toString();
		
		return null;
    }
		
	public String CreatePLAddressM(PLAddressDataM addressM ,String personalType, String displayMode, HttpServletRequest request,String department){		
		if(null == addressM){
			return "";			
		}
		String addressDisplay = AddressUtil.getDisplayaddress(request, addressM, department);
		
		StringBuilder HTML = new StringBuilder("");
		int num = addressM.getAddressSeq()+1;
		
		String STYLE = "ResultEven";
		if(num%2==0) STYLE = "ResultOdd";
		
		String param = (addressM.getAddressSeq()+1)+","+addressM.getAddressType()+","+personalType+"," 
										+DataFormatUtility.IntToString(addressM.getAddressSeq()+1);
		
		HTML.append(" <tr class='Result-Obj ").append(STYLE).append(" addressResult'");
		HTML.append(" value='").append(param).append("'");
		HTML.append(" id='").append("address_"+addressM.getAddressType()).append("'>");
		HTML.append(" <td width='3%' align='center'>")
			.append(HTMLRenderUtil.displayCheckBox("", "checkbox-addresstype", addressM.getAddressType(),displayMode," onclick=\"removecheckboxall('checkbox-address')\" "))
			.append(" </td>");
		HTML.append(" <td width='5%' align='center' nowrap='nowrap' class='showAddress' ")
			.append(" value='").append(param).append("'>")
			.append(HTMLRenderUtil.displayHTML(DataFormatUtility.IntToString(num)))
			.append(" </td>");
		HTML.append(" <td width='10%' align='center' class='showAddress' ")
			.append(" value='").append(param).append("'>")
			.append(HTMLRenderUtil.displayHTMLFieldIDDesc(addressM.getAddressType(),12))
			.append(" </td>");      
		HTML.append(" <td width='35%' align='left' class='showAddress TextTDLeft' ")
		 	.append(" value='").append(param).append("'>")
		 	.append(HTMLRenderUtil.displayHTML(addressDisplay))
		 	.append(" </td>");
		HTML.append(" <td width='10%' align='center' class='showAddress' ")
			.append(" value='").append(param).append("'>")
			.append(HTMLRenderUtil.displayHTML(addressM.getPhoneNo1()))
			.append(" </td>");		
		HTML.append(" <td width='4%' align='center' class='showAddress' ")
			.append(" value='").append(param).append("'>")
			.append(HTMLRenderUtil.displayHTML(addressM.getPhoneExt1()))
			.append(" </td>");		
		HTML.append(" <td width='10%' align='center' class='showAddress' ")
			.append(" value='").append(param).append("'>")
			.append(HTMLRenderUtil.displayHTML(addressM.getMobileNo()))
			.append(" </td>");	
		HTML.append(" <td width='15%' align='center' class='showAddress' ")
			.append(" value='").append(param).append("'>")
			.append(HTMLRenderUtil.displayHTMLFieldIDDesc(addressM.getAdrsts(),14))
			.append(" </td>");	
		HTML.append(" <td width='10%' align='center' class='showAddress' ")
			.append(" value='").append(param).append("'>")
			.append(HTMLRenderUtil.displayHTML(addressM.getRemark()))
			.append(" </td>");	
		HTML.append("</tr>");
        
		return HTML.toString();
	}
	
	public String CreatePLAddressM(PLAddressDataM addressM ,String personalType, int seq, String displayMode, HttpServletRequest request,String department) {	
		
		if(null == addressM){
			return "";			
		}
		
		String addressDisplay = AddressUtil.getDisplayaddress(request, addressM ,department);
		
		StringBuilder HTML = new StringBuilder("");
		int num = addressM.getAddressSeq()+1;
		
		String STYLE = "ResultEven";
		if(num%2==0) STYLE = "ResultOdd";
		
		String param = (addressM.getAddressSeq()+1)+","+addressM.getAddressType()+","+personalType+"," 
										+DataFormatUtility.IntToString(addressM.getAddressSeq()+1);
		
		HTML.append(" <tr class='Result-Obj ").append(STYLE).append(" addressResult'");
		HTML.append(" value='").append(param).append("'");
		HTML.append(" id='").append("address_"+addressM.getAddressType()).append("'>");
		HTML.append(" <td width='3%' align='center'>")
			.append(HTMLRenderUtil.displayCheckBox("", "checkbox-addresstype", addressM.getAddressType(),displayMode," onclick=\"removecheckboxall('checkbox-address')\" "))
			.append(" </td>");
		HTML.append(" <td width='5%' align='center' nowrap='nowrap' class='showAddress' ")
			.append(" value='").append(param).append("'>")
			.append(HTMLRenderUtil.displayHTML(DataFormatUtility.IntToString(num)))
			.append(" </td>");
		HTML.append(" <td width='10%' align='center' class='showAddress' ")
			.append(" value='").append(param).append("'>")
			.append(HTMLRenderUtil.displayHTMLFieldIDDesc(addressM.getAddressType(),12))
			.append(" </td>");      
		HTML.append(" <td width='35%' align='left' class='showAddress TextTDLeft' ")
		 	.append(" value='").append(param).append("'>")
		 	.append(HTMLRenderUtil.displayHTML(addressDisplay))
		 	.append(" </td>");
		HTML.append(" <td width='10%' align='center' class='showAddress' ")
			.append(" value='").append(param).append("'>")
			.append(HTMLRenderUtil.displayHTML(addressM.getPhoneNo1()))
			.append(" </td>");		
		HTML.append(" <td width='4%' align='center' class='showAddress' ")
			.append(" value='").append(param).append("'>")
			.append(HTMLRenderUtil.displayHTML(addressM.getPhoneExt1()))
			.append(" </td>");		
		HTML.append(" <td width='10%' align='center' class='showAddress' ")
			.append(" value='").append(param).append("'>")
			.append(HTMLRenderUtil.displayHTML(addressM.getMobileNo()))
			.append(" </td>");	
		HTML.append(" <td width='15%' align='center' class='showAddress' ")
			.append(" value='").append(param).append("'>")
			.append(HTMLRenderUtil.displayHTMLFieldIDDesc(addressM.getAdrsts(),14))
			.append(" </td>");	
		HTML.append(" <td width='10%' align='center' class='showAddress' ")
			.append(" value='").append(param).append("'>")
			.append(HTMLRenderUtil.displayHTML(addressM.getRemark()))
			.append(" </td>");	
		HTML.append("</tr>");        
		return HTML.toString();
		
	}
	
	public String CreateNorecPLAddressM() {
		StringBuilder HTML = new StringBuilder("");	
			HTML.append("<tr class=\"ResultNotFound ResultEven\" id=\"noRecordAddress\">");
				HTML.append("<td colspan=\"9\" align=\"center\">No record found</td>");		
			HTML.append("</tr>");
		return HTML.toString();
	}
	
	public Vector getAddressTypeCacheByPlAddressM(Vector<PLAddressDataM> addressVect){
		ORIGCacheUtil origCache = new ORIGCacheUtil();		
		Vector addressCache = (Vector)origCache.getNaosCacheDataMs(PLBusClassConstant.BusClass.ALL_ALL_ALL, 12);		
		Vector v = new Vector();		
		if(!ORIGUtility.isEmptyVector(addressVect)){
			for(PLAddressDataM addressM :addressVect){
				if(!ORIGUtility.isEmptyVector(addressCache))	{
					for(int i= 0 ;i<addressCache.size();i++){
						ORIGCacheDataM data = (ORIGCacheDataM) addressCache.get(i);
						if(data.getCode().equals(addressM.getAddressType()))
							v.add(data);
					}
				}
			}
		}		
		return v;
	}
	
	public static String getDisplayaddress(HttpServletRequest request, PLAddressDataM addressM,String department){
		
		StringBuilder STR = new StringBuilder("");
		
		ORIGCacheUtil origc = new ORIGCacheUtil();
		
//			if(!OrigUtil.isEmptyString(addressM.getPlaceName())){
//				addressResult.append(HTMLRenderUtil.replaceNull(addressM.getPlaceName()));
//				addressResult.append(" ");
//			}
			
			if("03".equals(addressM.getAddressType())){
				if(!OrigUtil.isEmptyString(addressM.getCompanyName())){
//					String name = "";
//					if(!OrigUtil.isEmptyString(addressM.getCompanyTitle())){
//						ORIGCacheUtil cache = new ORIGCacheUtil();
//						String value = cache.getDisplayNameCache(17, addressM.getCompanyTitle());
//						if(null != value){
//							name = (value).trim();
//						}
//					}
//					name += addressM.getCompanyName();
					STR.append(addressM.getCompanyName().trim()).append(" ");
				}
				
				if(!OrigUtil.isEmptyString(addressM.getBuilding())){
					STR.append(addressM.getBuilding().trim()).append(" ");
				}
							
				if(!OrigUtil.isEmptyString(department)){
					STR.append(department.trim()).append(" ");
				}
			}
			
//			if(!OrigUtil.isEmptyString(addressM.getAddressNo())){
//				STR.append(HTMLRenderUtil.replaceNull(addressM.getAddressNo()));
//				STR.append(" ");
//			}
			
//			if(!OrigUtil.isEmptyString(addressM.getBuilding())){
//				addressResult.append(MessageResourceUtil.getTextDescription(request, "BUILDING"));
//				STR.append(HTMLRenderUtil.replaceNull(addressM.getBuilding()));
//				STR.append(" ");
//				
//			}
			
//			if(!OrigUtil.isEmptyString(addressM.getFloor())){
//				addressResult.append(MessageResourceUtil.getTextDescription(request, "FLOOR")).append(" ");
//				addressResult.append(HTMLRenderUtil.replaceNull(addressM.getFloor()));
//				addressResult.append(" ");
//			}
			
//			if(!OrigUtil.isEmptyString(addressM.getRoom())){
//				addressResult.append(MessageResourceUtil.getTextDescription(request, "ROOM")).append(" ");
//				addressResult.append(HTMLRenderUtil.replaceNull(addressM.getRoom()));
//				addressResult.append(" ");
//			}
			
//			if(!OrigUtil.isEmptyString(addressM.getMoo())){
//				STR.append(MessageResourceUtil.getTextDescription(request, "MOO_M"));
//				STR.append(HTMLRenderUtil.replaceNull(addressM.getMoo()));
//				STR.append(" ");
//			}
			
//			if(!OrigUtil.isEmptyString(addressM.getHousingEstate())){
//				addressResult.append(MessageResourceUtil.getTextDescription(request, "VILLAGE_SHORT"));
//				addressResult.append(HTMLRenderUtil.replaceNull(addressM.getHousingEstate()));
//				addressResult.append(" ");
//				
//			}
			
			if(!OrigUtil.isEmptyString(addressM.getMoo())){
				if("01".equals(addressM.getAddressType())){
					if(!OrigUtil.isEmptyString(addressM.getAddressNo())){
						STR.append(addressM.getAddressNo().trim()).append(" ");
					}
					STR.append((addressM.getMoo()).trim()).append(" ");
				}else{
					if(null != addressM.getAddressNo() && ((addressM.getAddressNo()).trim()).length() < 7 && null != addressM.getMoo() && ((addressM.getMoo()).trim()).length() > 1 ){
						if(!OrigUtil.isEmptyString(addressM.getAddressNo())){
							STR.append(addressM.getAddressNo().trim()).append(" ").append("\u0E21").append(" ");
						}else{
							if(null != addressM.getMoo() && ((addressM.getMoo()).trim()).length() == 2){
								STR.append("\u0E21").append(" ");
							}
						}
						STR.append((addressM.getMoo()).trim()).append(" ");
					}else{	
						if(!OrigUtil.isEmptyString(addressM.getAddressNo())){
							STR.append(addressM.getAddressNo().trim()).append(" ");
						}
						if(null != addressM.getMoo() && ((addressM.getMoo()).trim()).length() == 1){
							STR.append("\u0E21").append((addressM.getMoo()).trim()).append(" ");
						}else{
							if(OrigUtil.isEmptyString(addressM.getAddressNo())){
								if(null != addressM.getMoo() && ((addressM.getMoo()).trim()).length() == 2){
									STR.append("\u0E21").append(" ");
								}
								STR.append((addressM.getMoo()).trim()).append(" ");
							}else{
								STR.append((addressM.getMoo()).trim()).append(" ");
							}
						}
					}
				}
			}else{			
				if(!OrigUtil.isEmptyString(addressM.getAddressNo())){
					STR.append(addressM.getAddressNo().trim()).append(" ");
				}			
			}
			
			if(!OrigUtil.isEmptyString(addressM.getSoi())){
//				STR.append(MessageResourceUtil.getTextDescription(request, "SOI_M"));
				STR.append(HTMLRenderUtil.replaceNull(addressM.getSoi()));
				STR.append(" ");
			}
			
			if(!OrigUtil.isEmptyString(addressM.getRoad())){
//				STR.append(MessageResourceUtil.getTextDescription(request, "ROAD_M"));
				STR.append(HTMLRenderUtil.replaceNull(addressM.getRoad()));
				STR.append(" ");				
			}
			
			if(!OrigUtil.isEmptyString(addressM.getTambol())){
				if(!OrigConstant.Province.BANGKOK.equals(addressM.getProvince())){
					STR.append(MessageResourceUtil.getTextDescription(request, "TAMBOL_M"));
				}
				STR.append(origc.getManualORIGCache(addressM.getTambol(),"Tambol"));
				STR.append(" ");
			}
			
			if(!OrigUtil.isEmptyString(addressM.getAmphur())){
				if(!OrigConstant.Province.BANGKOK.equals(addressM.getProvince())){
					STR.append(MessageResourceUtil.getTextDescription(request, "AMPHUR_M"));
				}
				STR.append(origc.getManualORIGCache(addressM.getAmphur(),"Amphur"));
				STR.append(" ");
			}
			
			if(!OrigUtil.isEmptyString(addressM.getProvince())){
				STR.append(origc.getManualORIGCache(addressM.getProvince(),"Province"));
				STR.append(" ");
			}
			
		return (STR.toString()).trim();		
	}
	
	public static boolean ValidateMatchesAddress(AddressDataM address){
		if(!Util.empty(address) && !Util.empty(address.getZipcode()) && !Util.empty(address.getProvinceDesc()) 
				&& !Util.empty(address.getAmphur()) && !Util.empty(address.getTambol())){
			ArrayList<HashMap<String,Object>> results = CacheControl.search("ZIPCODE", "ZIPCODE", address.getZipcode());
			if(!Util.empty(results)){
				for(HashMap<String,Object> result : results){
					String PROVINCE = CacheUtil.getValue(result, "PROVINCE");
					String AMPHUR = CacheUtil.getValue(result, "AMPHUR");
					String TAMBOL = CacheUtil.getValue(result, "TAMBOL");
					if(!Util.empty(PROVINCE) && !Util.empty(AMPHUR) && !Util.empty(TAMBOL) 
							&& PROVINCE.equals(address.getProvinceDesc()) 
							&& AMPHUR.equals(address.getAmphur()) 
							&& TAMBOL.equals(address.getTambol())){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static String GetCardLinkLine1(HttpServletRequest request, PLAddressDataM addressM, String department){		
		
		StringBuilder STR = new StringBuilder();
		
			if("03".equals(addressM.getAddressType())){
				if(!OrigUtil.isEmptyString(addressM.getCompanyName())){
//					#septem comment 
//					String name = "";
//					if(!OrigUtil.isEmptyString(addressM.getCompanyTitle())){
//						ORIGCacheUtil cache = new ORIGCacheUtil();
//						String value = cache.getDisplayNameCache(17, addressM.getCompanyTitle());
//						if(null != value){
//							name = (value).trim();
//						}
//					}
//					name += addressM.getCompanyName();
					STR.append(addressM.getCompanyName().trim()).append(" ");
				}
				
				if(!OrigUtil.isEmptyString(addressM.getBuilding())){
					STR.append(addressM.getBuilding().trim()).append(" ");
				}
							
				if(!OrigUtil.isEmptyString(department)){
					STR.append(department.trim()).append(" ");
				}
			}
			
//			if(!OrigUtil.isEmptyString(addressM.getPlaceName())){
//				addressResult.append(HTMLRenderUtil.replaceNull(addressM.getPlaceName()));
//				addressResult.append(" ");
//			}
			
//			if(!OrigUtil.isEmptyString(addressM.getAddressNo())){
//				STR.append(HTMLRenderUtil.replaceNull(addressM.getAddressNo()));
//				STR.append(" ");
//			}
			
//			if(!OrigUtil.isEmptyString(addressM.getBuilding())){
//				addressResult.append(MessageResourceUtil.getTextDescription(request, "BUILDING"));
//				STR.append(HTMLRenderUtil.replaceNull(addressM.getBuilding()));
//				STR.append(" ");				
//			}
			
//			if(!OrigUtil.isEmptyString(addressM.getFloor())){
//				addressResult.append(MessageResourceUtil.getTextDescription(request, "FLOOR")).append(" ");
//				addressResult.append(HTMLRenderUtil.replaceNull(addressM.getFloor()));
//				addressResult.append(" ");
//			}
//			
//			if(!OrigUtil.isEmptyString(addressM.getRoom())){
//				addressResult.append(MessageResourceUtil.getTextDescription(request, "ROOM")).append(" ");
//				addressResult.append(HTMLRenderUtil.replaceNull(addressM.getRoom()));
//				addressResult.append(" ");
//			}
			
//			if(!OrigUtil.isEmptyString(addressM.getMoo())){
//				STR.append(MessageResourceUtil.getTextDescription(request, "MOO_M"));
//				STR.append(HTMLRenderUtil.replaceNull(addressM.getMoo()));
//				STR.append(" ");
//			}
			
//			if(!OrigUtil.isEmptyString(addressM.getHousingEstate())){
//				addressResult.append(MessageResourceUtil.getTextDescription(request, "VILLAGE_SHORT"));
//				addressResult.append(HTMLRenderUtil.replaceNull(addressM.getHousingEstate()));
//				addressResult.append(" ");				
//			}
			
			if(!OrigUtil.isEmptyString(addressM.getMoo())){
				if("01".equals(addressM.getAddressType())){
					if(!OrigUtil.isEmptyString(addressM.getAddressNo())){
						STR.append(addressM.getAddressNo().trim()).append(" ");
					}
					STR.append((addressM.getMoo()).trim()).append(" ");
				}else{
					if(null != addressM.getAddressNo() && ((addressM.getAddressNo()).trim()).length() < 7 && null != addressM.getMoo() && ((addressM.getMoo()).trim()).length() > 1 ){
						if(!OrigUtil.isEmptyString(addressM.getAddressNo())){
							STR.append(addressM.getAddressNo().trim()).append(" ").append("\u0E21").append(" ");
						}else{
							if(null != addressM.getMoo() && ((addressM.getMoo()).trim()).length() == 2){
								STR.append("\u0E21").append(" ");
							}
						}
						STR.append((addressM.getMoo()).trim()).append(" ");
					}else{	
						if(!OrigUtil.isEmptyString(addressM.getAddressNo())){
							STR.append(addressM.getAddressNo().trim()).append(" ");
						}
						if(null != addressM.getMoo() && ((addressM.getMoo()).trim()).length() == 1){
							STR.append("\u0E21").append((addressM.getMoo()).trim()).append(" ");
						}else{
							if(OrigUtil.isEmptyString(addressM.getAddressNo())){
								if(null != addressM.getMoo() && ((addressM.getMoo()).trim()).length() == 2){
									STR.append("\u0E21").append(" ");
								}
								STR.append((addressM.getMoo()).trim()).append(" ");
							}else{
								STR.append((addressM.getMoo()).trim()).append(" ");
							}
						}
					}
				}
			}else{			
				if(!OrigUtil.isEmptyString(addressM.getAddressNo())){
					STR.append(addressM.getAddressNo().trim()).append(" ");
				}			
			}
			
			
			if(!OrigUtil.isEmptyString(addressM.getSoi())){
//				STR.append(MessageResourceUtil.getTextDescription(request, "SOI_M"));
				STR.append(HTMLRenderUtil.replaceNull(addressM.getSoi()));
				STR.append(" ");
			}
			
			if(!OrigUtil.isEmptyString(addressM.getRoad())){
//				STR.append(MessageResourceUtil.getTextDescription(request, "ROAD_M"));
				STR.append(HTMLRenderUtil.replaceNull(addressM.getRoad()));				
			}
			
		return (STR.toString()).trim();
	}
	
	public static String GetCardLinkLine2(HttpServletRequest request, PLAddressDataM addressM){		
		StringBuilder STR = new StringBuilder("");
		ORIGCacheUtil origc = new ORIGCacheUtil();		
			if(!OrigUtil.isEmptyString(addressM.getTambol())){
				if(!OrigConstant.Province.BANGKOK.equals(addressM.getProvince())){
					STR.append(MessageResourceUtil.getTextDescription(request, "TAMBOL_M"));
				}
				STR.append(origc.getManualORIGCache(addressM.getTambol(),"Tambol"));
				STR.append(" ");
			}
			
			if(!OrigUtil.isEmptyString(addressM.getAmphur())){
				if(!OrigConstant.Province.BANGKOK.equals(addressM.getProvince())){
					STR.append(MessageResourceUtil.getTextDescription(request, "AMPHUR_M"));
				}
				STR.append(origc.getManualORIGCache(addressM.getAmphur(),"Amphur"));
				STR.append(" ");
			}
			
			if(!OrigUtil.isEmptyString(addressM.getProvince())){
				STR.append(origc.getManualORIGCache(addressM.getProvince(),"Province"));
			}		
			
		return (STR.toString()).trim();
	}
	
}
