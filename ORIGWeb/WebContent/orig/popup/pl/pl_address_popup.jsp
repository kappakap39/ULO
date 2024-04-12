<%@page import="com.eaf.orig.ulo.pl.model.app.PLAddressDataM"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.cache.properties.ORIGCacheDataM"%>
<%@ page import="com.eaf.orig.cache.CacheDataInf"%>
<%@ page import="com.eaf.cache.data.CacheDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>

<script type="text/javascript" src="orig/js/subform/pl/pl_address_popup.js"></script>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="AddressDataM" scope="session" class="com.eaf.orig.ulo.pl.model.app.PLAddressDataM"/>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>
<jsp:useBean id="currentPerson" scope="session" class="java.lang.String"/>

<% 	
	Logger log = Logger.getLogger(this.getClass());
	
	ORIGCacheUtil origc = new ORIGCacheUtil();
	 
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
		
	if(OrigUtil.isEmptyString(currentPerson)){
		currentPerson = PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT;
	}	
		
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(currentPerson);
	PLAddressDataM addressM = AddressDataM;
// 	ORIGFormUtil formUtil = new ORIGFormUtil();
//	String displayMode = formUtil.getDisplayMode("APPLICATION_DETAILS_SUBFORM", ORIGUser, plapplicationDataM.getJobState(), PLORIGForm.getFormID(), searchType);
	
	String displayMode = request.getParameter("mode");

	Vector addressTypeVect = (Vector) request.getSession().getAttribute("addressType");
	Vector addressTypeCacheVect = null;
	
	if(OrigUtil.isEmptyVector(addressTypeVect)){
		if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode)){
			addressTypeVect = (Vector)origc.getNaosCacheDataMs(applicationM.getBusinessClassId(), 12);
		}else{
			addressTypeVect = (Vector)origc.getNaosCacheDataMs(applicationM.getBusinessClassId(), 12, null);
		}
	}
	if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode)){
		addressTypeCacheVect = (Vector)origc.getNaosCacheDataMs(applicationM.getBusinessClassId(), 12);
	}else{
		addressTypeVect = (Vector)origc.getNaosCacheDataMs(applicationM.getBusinessClassId(), 12, null);
		addressTypeCacheVect = (Vector)origc.getNaosCacheDataMs(applicationM.getBusinessClassId(), 12, null);
	}
		
	Vector addressVect = personalM.getAddressVect();
	Vector addressTypeCopyVect = new Vector();
	
	String provinceDesc = origc.getManualORIGCache(addressM.getProvince(),"Province");
	String amphurDesc = origc.getManualORIGCache(addressM.getAmphur(),"Amphur");
	String tambolDesc = origc.getManualORIGCache(addressM.getTambol(), "Tambol");
	String disabledBtn = "";
	String seqDisplay = "";
	
	if(OrigUtil.isEmptyObject(personalM.getAddressVect()) || addressM.getAddressType()==null){
		seqDisplay = String.valueOf(addressVect.size()+1);
	}else{
		if(addressM.getSeq() != 0){
			seqDisplay = String.valueOf(addressM.getSeq());
		}else{
			seqDisplay = String.valueOf(addressVect.size());
		}
	}
	
	if(!displayMode.equals(HTMLRenderUtil.DISPLAY_MODE_EDIT)){
		disabledBtn = HTMLRenderUtil.DISABLED;
	}
	if(OrigUtil.isEmptyString(addressM.getCountry())){
		addressM.setCountry("01");
	}
/** Variable use for copy address vector*/
	if(!OrigUtil.isEmptyVector(addressVect)){
		for(int i=0;i<addressVect.size();i++){
			PLAddressDataM addressM1 = (PLAddressDataM)addressVect.get(i);
			if(null != addressM1){
				String addressTypeDesc = "";				
				for(int j=0; j<addressTypeCacheVect.size(); j++){
					ORIGCacheDataM addrCache = (ORIGCacheDataM)addressTypeCacheVect.get(j);
					if(addrCache.getCode().equals(addressM1.getAddressType())){
						addressTypeDesc = addrCache.getThDesc();
						break;
					}
				}
			
				CacheDataM cacheM =  new CacheDataM();
					cacheM.setCode(addressM1.getAddressType());
					cacheM.setThDesc(addressTypeDesc);
				addressTypeCopyVect.add(cacheM);
			}

		}
	}

	if(!OrigUtil.isEmptyVector(addressM.getAddressCheck()) && displayMode.equals(HTMLRenderUtil.DISPLAY_MODE_EDIT)){
		addressTypeVect = addressM.getAddressCheck();
	}
	
	ORIGCacheDataM countryM = origc.GetListboxCacheDataM(104, applicationM.getBusinessClassId(), addressM.getCountry());
	
	String department = "";	
	if("03".equals(addressM.getAddressType())){
		department = personalM.getDepartment();
	}	
%>

<div class="PanelFirst">
	<div class="PanelSecond TextHeaderNormal">
		<div id="address_pop_err_div" class="div-error-mandatory"></div>
		<div class="PanelThird">
			<table class="FormFrame" width="100%" align="center" id="address_popup_table">	
				<tr>
					<td class="textR" nowrap="nowrap" width="5%">
						<%=PLMessageResourceUtil.getTextDescription(request, "DUP_NUM") %><Font color="#ff0000"></Font> :
					</td>
					<td class="inputL" width="30%"  align="left" id="td_address_seq"><%=HTMLRenderUtil.displayHTML(seqDisplay)%></td>
					<td class="textR" nowrap="nowrap" width="10%">
						<%=PLMessageResourceUtil.getTextDescription(request, "ADDRESS_TYPE") %><font color="#ff0000">*</font> :
					</td>
					<td class="inputL" width="35%" valign="bottom"  align="left" id="td_address_type">
						<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(addressTypeVect,addressM.getAddressType(),"address_type",displayMode,"") %>
					</td>
					<td class="textR" nowrap="nowrap" width="10%"><%=PLMessageResourceUtil.getTextDescription(request, "COPY_ADDRESS_TYPE") %> :</td>
					<td class="inputL" width="25%" nowrap="nowrap">
						<table cellspacing=0 cellpadding=0 width='100%' border='0'>
							<tr>
								<td id="div_address_type_copy">
									<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(addressTypeCopyVect,"","address_type_copy",displayMode,"") %>
								</td>
								<td valign="top">
									<input type="button" name="Copy" value="<%=PLMessageResourceUtil.getTextDescription(request, "COPY") %>" onClick="copyAddress()" class="buttonNew" <%=disabledBtn %>>
								</td>
							</tr>
						</table>
					</td>
				</tr>					
				<tr>
					<td class="textR" nowrap="nowrap">
						<%=PLMessageResourceUtil.getTextDescription(request, "ADDRESS_STYLE") %><Font color="#ff0000"></Font> :
					</td>
					<td class="inputL"  align="left" id="td_address_style">
						<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(13,applicationM.getBusinessClassId(),addressM.getBuildingType(),"address_style",displayMode,"") %>
					</td>
					<td class="textR" nowrap="nowrap" width="10%"><%=PLMessageResourceUtil.getTextDescription(request, "ADDRESS_STATUS") %><span>
							<font color="red"  id="error_address_status">*</font></span> :
					</td>
					<td class="inputL"  align="left">
						<table cellspacing=0 cellpadding=0 width='100%' border='0'>
							<tr>
								<td id="td_address_status"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(14,applicationM.getBusinessClassId(),addressM.getAdrsts(), "address_status",displayMode,"") %></td>
								<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "RENT_PAYMENT") %> :</td>	
								<td>
									<%=HTMLRenderUtil.DisplayInputCurrency(addressM.getRents(), displayMode, "########0.00","returndchecks","textbox-currency-small","","10",true)%>
								</td>
								<td class="textR"  align="left" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "BATH_PER_MONTH") %></td>
							</tr>
						</table>
					</td>
					
					<td class="textR" nowrap="nowrap">
						<%=PLMessageResourceUtil.getTextDescription(request, "TIME_IN_CURRENT_ADDRESS") %><Font color="#ff0000"></Font> :
					</td>
					<td class="inputL"  align="left">
						<table cellspacing=0 cellpadding=0 width='100%' border='0' align='left'>
							<tr>
								<td nowrap="nowrap" width="100px">
									<%=HTMLRenderUtil.displayInputTagScriptAction(DataFormatUtility.IntToString(addressM.getResidey()),displayMode,"2","time_current_address_year","textbox-code"," onkeypress= \"return keyPressInteger(event)\" ","2") %>
									<%=PLMessageResourceUtil.getTextDescription(request, "YEAR") %>
								</td>
								<td nowrap="nowrap">
									<%=HTMLRenderUtil.displayInputTagScriptAction(DataFormatUtility.IntToString(addressM.getResidem()),displayMode,"2","time_current_address_month","textbox-code"," onkeypress= \"return keyPressInteger(event)\" ","2") %>
									<%=PLMessageResourceUtil.getTextDescription(request, "MONTH") %>
								</td>
							</tr>
						</table>
					</td>
				</tr>				
				<tr>
					<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "WORKPLACE_NAME") %><font color="red"  id="error_work_place_name"></font> : </td>
					<td class="inputL" align="left" id="tr-address-company">
						<%=HTMLRenderUtil.displayInputTagScriptAction(addressM.getCompanyName(), HTMLRenderUtil.DISPLAY_MODE_VIEW, "30", "address_company_name", "textbox-workplace","","30")%>
					</td>
					<td class="textR" nowrap="nowrap" width="10%"><%=PLMessageResourceUtil.getTextDescription(request, "BUILDING_FLOOR_ROOM") %> :</td>
					<td class="inputL" align="left" id="td-address-building"><%=HTMLRenderUtil.displayInputTagScriptAction(addressM.getBuilding(),HTMLRenderUtil.DISPLAY_MODE_VIEW,"30","building","textbox-address-building","","30") %></td>					
					<td colspan="2"></td>
				</tr>	
				<tr>
					<td class="textR" nowrap="nowrap" width="5%"><%=PLMessageResourceUtil.getTextDescription(request, "PLACE_NAME") %><Font color="#ff0000"></Font> :</td>
					<td class="inputL" align="left"><%=HTMLRenderUtil.displayInputTagScriptAction(addressM.getPlaceName(),HTMLRenderUtil.DISPLAY_MODE_VIEW,"50","place_name","textbox","","50") %></td>
					<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "DEPARTMENT") %><Font color="red" id="error_department"></Font> :</td>
					<td class="inputL" align="left" nowrap="nowrap" id="td-address-department"><%=HTMLRenderUtil.displayInputTagScriptAction(department, HTMLRenderUtil.DISPLAY_MODE_VIEW, "20", "occ_department","textbox-workplace","","20")%></td>	
					<td class="textR" nowrap="nowrap" width="10%"><%=PLMessageResourceUtil.getTextDescription(request, "NUMBER") %><Font color="#ff0000"></Font> :</td>
					<td class="inputL" align="left"><%=HTMLRenderUtil.displayInputTagScriptAction(addressM.getAddressNo(),displayMode,"8","number","textbox-address-number","","8") %></td>
				</tr>					
				<tr>
					<td class="textR" nowrap="nowrap" width="5%"><%=PLMessageResourceUtil.getTextDescription(request, "FLOOR") %><Font color="#ff0000"></Font> :</td>
					<td class="inputL"  align="left"><%=HTMLRenderUtil.displayInputTagScriptAction(addressM.getFloor(),HTMLRenderUtil.DISPLAY_MODE_VIEW,"50","floor","textbox","","8") %></td>
					<td class="textR" nowrap="nowrap" width="10%"><%=PLMessageResourceUtil.getTextDescription(request, "ROOM") %><Font color="#ff0000"></Font> :</td>
					<td class="inputL"  align="left"><%=HTMLRenderUtil.displayInputTagScriptAction(addressM.getRoom(),HTMLRenderUtil.DISPLAY_MODE_VIEW,"50","room","textbox","","10") %></td>
					<td class="textR" nowrap="nowrap" width="10%"><%=PLMessageResourceUtil.getTextDescription(request, "MOO") %> :</td>
					<td class="inputL"  align="left"><%=HTMLRenderUtil.displayInputTagScriptAction(addressM.getMoo(),displayMode,"2","moo","textbox-address-moo","","2") %></td>
				</tr>					
				<tr>
					<td class="textR" nowrap="nowrap" width="5%"><%=PLMessageResourceUtil.getTextDescription(request, "VILLAGE") %><Font color="#ff0000"></Font> :</td>
					<td class="inputL" align="left"><%=HTMLRenderUtil.displayInputTagScriptAction(addressM.getHousingEstate(),HTMLRenderUtil.DISPLAY_MODE_VIEW,"50","village","textbox","","50") %></td>
					<td class="textR" nowrap="nowrap" width="10%"><%=PLMessageResourceUtil.getTextDescription(request, "SOI_CONDO") %><Font color="#ff0000"></Font> :</td>
					<td class="inputL" align="left"><%=HTMLRenderUtil.displayInputTagScriptAction(addressM.getSoi(),displayMode,"15","soi","textbox-address-soi","","15")%></td>
					<td class="textR" nowrap="nowrap" width="10%"><%=PLMessageResourceUtil.getTextDescription(request, "ROAD")%>:</td>
					<td class="inputL" align="left"><%=HTMLRenderUtil.displayInputTagScriptAction(addressM.getRoad(),displayMode,"20","road","textbox-address-road","","20")%></td>
				</tr>					
				<tr>
					<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "PROVINCE") %> :</td>
					<td class="inputL" nowrap="nowrap" valign="top">
						<%=HTMLRenderUtil.displayPopUpWith2TextboxNotTagScriptAction(addressM.getProvince(),"2","province","2","textbox-code",provinceDesc,"20","province_desc","20","textbox","...","button",displayMode) %>
					</td>
					<td class="textR" nowrap="nowrap" width="10%"><%=PLMessageResourceUtil.getTextDescription(request, "AMPHUR") %> :</td>
					<td class="inputL" nowrap="nowrap" valign="top">
						<%=HTMLRenderUtil.displayPopUpWith2TextboxNotTagScriptAction(addressM.getAmphur(),"5","amphur","5","textbox-code",amphurDesc,"20","amphur_desc","20","textbox","...","button",displayMode) %>									
					</td>
					<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "TAMBOL") %> :</td>
					<td class="inputL" nowrap="nowrap" valign="top">
						<%=HTMLRenderUtil.displayPopUpWith2TextboxNotTagScriptAction(addressM.getTambol(),"5","tambol","5","textbox-code",tambolDesc,"20","tambol_desc","20","textbox","...","button",displayMode) %>
					</td>
				</tr>
						
				<tr>
					<td class="textR" nowrap="nowrap" width="10%"><%=PLMessageResourceUtil.getTextDescription(request, "ZIP_CODE") %><font color="#ff0000">*</font> :</td>
					<td class="inputL" align="left"><%=HTMLRenderUtil.displayPopUpTagNotSetScriptAction(addressM.getZipcode(),displayMode,"5","zipcode","textbox-code","","5","...","button") %>	</td>
					<td class="textR" nowrap="nowrap" width="10%"><%=PLMessageResourceUtil.getTextDescription(request, "COUNTRY") %><font color="#ff0000">*</font> :</td>
					<td class="inputL" align="left">
						<%=HTMLRenderUtil.displayHiddenField(addressM.getCountry(),"country_no") %>
						<%=HTMLRenderUtil.displayPopUpTagNotSetScriptAction(countryM.getThDesc(), displayMode, "100","country_desc","textbox-code-big","", "100","...","button") %>
					</td>
					<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "MOBILE_NO") %><Font color="#ff0000"></Font> :</td>
					<td class="inputL" align="left"  nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(addressM.getMobileNo(),displayMode,"10","mobile_no","textbox"," onkeypress= \"return keyPressInteger(event)\" ","10") %></td> 
				</tr>					
				<tr>
					<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "TELEPHONE1") %>:</td>
					<td class="inputL" align="left">
						<table cellspacing=0 cellpadding=0 width='100%' border='0' align='left'>
							<tr>
								<td width="152px"  nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(addressM.getPhoneNo1(),displayMode,"9","telephone1","textbox"," onkeypress= \"return keyPressInteger(event)\" ","9") %></td>
								<td  nowrap="nowrap">
									<%=PLMessageResourceUtil.getTextDescription(request, "EXT_TEL") %>
									<%=HTMLRenderUtil.displayInputTagScriptAction(addressM.getPhoneExt1(),displayMode,"6","ext_tel_1","textbox-code"," onkeypress= \"return keyPressInteger(event)\" ","6") %>
								</td>
							</tr>
						</table>
					</td>
					<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "TELEPHONE2") %><Font color="#ff0000"></Font> :</td>
					<td class="inputL" align="left">
						<table cellspacing=0 cellpadding=0 width='100%' border='0' align='left'>
							<tr>
								<td width="152px"  nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(addressM.getPhoneNo2(),displayMode,"9","telephone2","textbox"," onkeypress= \"return keyPressInteger(event)\" ","9") %></td>
								<td  nowrap="nowrap">
									<%=PLMessageResourceUtil.getTextDescription(request, "EXT_TEL") %>
									<%=HTMLRenderUtil.displayInputTagScriptAction(addressM.getPhoneExt2(),displayMode,"6","ext_tel_2","textbox-code"," onkeypress= \"return keyPressInteger(event)\" ","6") %>
								</td>
							</tr>
						</table>
					</td>
					<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "FAX_NO") %><Font color="#ff0000"></Font> :</td>
					<td class="inputL" align="left"><%=HTMLRenderUtil.displayInputTagScriptAction(addressM.getFaxNo(),displayMode,"9","fax_no","textbox"," onkeypress= \"return keyPressInteger(event)\" ","9") %></td>
				</tr>					
				<tr>
					<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "NOTE") %><Font color="#ff0000"></Font> :</td>
					<td class="inputL" align="left"><%=HTMLRenderUtil.displayInputTagScriptAction(addressM.getRemark(),displayMode,"50","note","textbox","","50") %></td>
					<td colspan="4"></td>
				</tr>
			</table>
		</div>
		<div class="PanelThird">
			<table class="FormFrame" width="100%">
				<tr>
					<td width="30%" class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "CARD_LINK_LINE1") %>&nbsp;:&nbsp;</td>
					<td class="textL" width="420px"><%=HTMLRenderUtil.displayInputTagScriptAction("", HTMLRenderUtil.DISPLAY_MODE_VIEW, "50", "cardlink_line1", "textbox-cardlinkline", "", "50")%></td>
					<td class="textL" id="countLine1"><div style="color: green;" >0</div></td>
				</tr>
				<tr>
					<td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "CARD_LINK_LINE2") %>&nbsp;:&nbsp;</td>
					<td class="textL"><%=HTMLRenderUtil.displayInputTagScriptAction("", HTMLRenderUtil.DISPLAY_MODE_VIEW, "40", "cardlink_line2", "textbox-cardlinkline", "", "40")%></td>
					<td class="textL" id="countLine2"><div style="color: green;" >0</div></td>
				</tr>
			</table>
		</div>
	</div>
</div>
<%=HTMLRenderUtil.displayHiddenField(addressM.getAddressType(), "old-address-type") %>
<%
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen("PL_MAIN_APPFORM");
%>
