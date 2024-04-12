<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.view.webaction.AddressUtil"%>
<%@ page import="com.eaf.cache.data.CacheDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLAddressDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>

<script type="text/javascript" src="orig/js/subform/pl/address.js"></script>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>

<% 
	Logger log = Logger.getLogger(this.getClass());
	
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	
	if(null == applicationM){
		applicationM = new PLApplicationDataM();
	}
	
	
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	ORIGFormUtil formUtil = new ORIGFormUtil();	
	String displayMode = formUtil.getDisplayMode("ADDRESS_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	
	String personalType = personalM.getPersonalType();
	Vector vAddressVect = personalM.getAddressVect();
	Vector vAddressType = new Vector();
	
	if(!OrigUtil.isEmptyVector(vAddressVect)){
		for(int i =0;i<vAddressVect.size();i++){
			PLAddressDataM addressM = (PLAddressDataM)vAddressVect.get(i);
			if(!OrigConstant.ADDRESS_TYPE_IC.equals(addressM.getAddressType())){
				CacheDataM cacheDataM = new CacheDataM();
					cacheDataM.setCode(addressM.getAddressType());			
					cacheDataM.setThDesc(cacheUtil.getNaosCacheDisplayNameDataM(12,addressM.getAddressType()));
	 			vAddressType.add(cacheDataM);
 			}
		}
	}
	if(null == vAddressVect){
		vAddressVect = new Vector();
	}
	String addressDisplay="";
    String buttonDisplay = "";
    if(HTMLRenderUtil.DISPLAY_MODE_VIEW.equalsIgnoreCase(displayMode)){
   		 buttonDisplay = "disabled";
    }
%>

<table class="FormFrame">
	<tr align="left">
		<td>
			<input type="button" id="addHomeBnt" name="addHomeBnt" class="button" onclick="addAddress()" value="<%=PLMessageResourceUtil.getTextDescription(request, "ADD") %>" >
			&nbsp;
			<input type="button" name="deleteAddressBnt" class="button" value="<%=PLMessageResourceUtil.getTextDescription(request, "DELETE")%>" onClick="javascript:confirmdelete()"  <%=buttonDisplay%>>
		</td>
	</tr>
</table>		
	
<table class="TableFrame" id="addressResult">
	<tr class="Header" id="headerAddress">							
		<td width="3%" align="center"><%=HTMLRenderUtil.displayCheckBox("0","checkbox-address","",displayMode," onclick=\"ToolCheckboxAll('checkbox-address','checkbox-addresstype')\" ") %></td>
		<td width="5%" align="center"><%=PLMessageResourceUtil.getTextDescription(request, "NO") %></td>
		<td width="10%" align="center"><%=PLMessageResourceUtil.getTextDescription(request, "ADDRESS_TYPE") %></td>
		<td width="35%" align="center"><%=PLMessageResourceUtil.getTextDescription(request, "ADDRESS") %></td>
		<td width="10%" align="center"><%=PLMessageResourceUtil.getTextDescription(request, "TELEPHONE") %></td>		
		<td width="4%" align="center"><%=PLMessageResourceUtil.getTextDescription(request, "EXT_TEL") %></td>
		<td width="10%" align="center"><%=PLMessageResourceUtil.getTextDescription(request, "MOBILE_NO") %></td>
		<td width="15%" align="center"><%=PLMessageResourceUtil.getTextDescription(request, "PRE_SCORE_ADDRESS_STATUS") %></td>
		<td width="10%" align="center"><%=PLMessageResourceUtil.getTextDescription(request, "NOTE") %></td>
	</tr>
<%--
	<% 
		String addressType = null;
		String addressStatus = null;
		String disabledChk = null;
		String param = null;	
		String styleTr = null;
			
		if(!OrigUtil.isEmptyVector(vAddressVect)){								
			for(int i=0; i<vAddressVect.size(); i++){								
				disabledChk = "";
				addressM = new PLAddressDataM();			
				addressM = (PLAddressDataM) vAddressVect.get(i);
				
				addressType = cacheUtil.getORIGMasterDisplayNameDataM("AddressType", addressM.getAddressType());
				addressStatus = cacheUtil.getORIGMasterDisplayNameDataM("AddressStatus", addressM.getStatus()); 
				
				if(HTMLRenderUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
					disabledChk = "disabled";
				}	
																		
				addressDisplay = AddressUtil.getDisplayaddress(request, addressM, personalM.getDepartment());				 
				styleTr = (i%2==0)?"ResultEven":"ResultOdd";
				param = (addressM.getAddressSeq()+i+","+addressM.getAddressType()+","+personalType);	
								
	%>					
			<tr class="Result-Obj <%=styleTr%> addressResult" id="address_<%=addressM.getAddressType()%>" value="<%=param%>">
				<td width="3%" align='center'>
					<%=HTMLRenderUtil.displayCheckBox("","checkbox-addresstype",addressM.getAddressType(),displayMode," onclick=\"removecheckboxall('checkbox-address')\" ") %>
				</td>
				<td width="5%" align='center' class="showAddress" nowrap='nowrap' value="<%=param%>">
					<%=HTMLRenderUtil.displayHTML(DataFormatUtility.IntToString(i+1)) %>
				</td>
				<td width="10%" align='center' class="showAddress" value="<%=param%>">
					<%=HTMLRenderUtil.displayHTML(cacheUtil.getORIGCacheDisplayNameDataM(12,addressM.getAddressType()))%>
				</td>
				<td width="35%" align='left' class="showAddress TextTDLeft" value="<%=param%>">
					<%=HTMLRenderUtil.displayHTML(addressDisplay)%>
				</td>
				<td  width="10%" align='center' class="showAddress" value="<%=param%>">
					<%=HTMLRenderUtil.displayHTML(addressM.getPhoneNo1()) %>
				</td>
				<td width="4%" align='center' class="showAddress" value="<%=param%>">
					<%=HTMLRenderUtil.displayHTML(addressM.getPhoneExt1()) %>
				</td>
				<td width="10%" align='center' class="showAddress" value="<%=param%>">
					<%=HTMLRenderUtil.displayHTML(addressM.getMobileNo()) %>
				</td>
				<td width="15%"  align='center' class="showAddress" value="<%=param%>">
					<%=HTMLRenderUtil.displayHTML(cacheUtil.getORIGCacheDisplayNameDataM(14, addressM.getAdrsts()))%>
				</td>
				<td width="10%" align='center' class="showAddress" value="<%=param%>">
					<%=HTMLRenderUtil.displayHTML(addressM.getRemark()) %>
				</td>
			</tr>				
		<%}%>						
	<%}else{%>
		<tr class="ResultNotFound ResultEven" id="noRecordAddress">
			<td colspan="9" align="center">No record found</td>
		</tr>
	<%}%>
 --%>
 
 	<%if(!OrigUtil.isEmptyVector(vAddressVect)){%>
 		<%	
 			AddressUtil util = new AddressUtil();
 			for(int i=0; i<vAddressVect.size(); i++){
 			PLAddressDataM addressM = (PLAddressDataM) vAddressVect.get(i);
 				addressM.setAddressSeq(i);
 		%>
 			<%=util.CreatePLAddressM(addressM ,personalM.getPersonalType(), displayMode, request, personalM.getDepartment()) %>
 		<%} %>
 	<%}else{%>
 		<tr class="ResultNotFound ResultEven" id="noRecordAddress">
			<td colspan="9" align="center">No record found</td>
		</tr>
 	<%}%>
</table>	
<br>
<table id="table-mailling-address">
	<tr height="25">
		<td class="textR" nowrap="nowrap" width="30%" >
			<%=PLMessageResourceUtil.getTextDescription(request, "MAILING_ADDRESS",ORIGUser,personalM.getCustomerType(),"ADDRESS_SUBFORM","mailling_address")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_SUBFORM","mailling_address")%>&nbsp;:&nbsp;
		</td>
		<td class="inputL" id="mailling-address">
			<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vAddressType, personalM.getMailingAddress(),"mailling_address",displayMode," onChange='GetCardlink()' ")%>
		</td>
	</tr>
	<tr>
		<td class="textR" nowrap="nowrap">
			<%=PLMessageResourceUtil.getTextDescription(request, "CARD_LINK_ADDRESS1",ORIGUser,personalM.getCustomerType(),"ADDRESS_SUBFORM","card_link_address1")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_SUBFORM","card_link_address1")%>&nbsp;:&nbsp;
		</td>
		<td class="inputL">
			<%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getAddressDocLine1(), displayMode, "200", "card_link_address1", "textboxCardlink", "", "200")%>
		</td>
	</tr>
	<tr>
		<td class="textR" nowrap="nowrap">
			<%=PLMessageResourceUtil.getTextDescription(request, "CARD_LINK_ADDRESS2",ORIGUser,personalM.getCustomerType(),"ADDRESS_SUBFORM","card_link_address2")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_SUBFORM","card_link_address2")%>&nbsp;:&nbsp;
		</td>
		<td class="inputL">
			<%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getAddressDocLine2(), displayMode, "200", "card_link_address2", "textboxCardlink", "", "200")%>
		</td>
	</tr>
</table>

<%=HTMLRenderUtil.displayHiddenField(buttonDisplay, "button-address-display")%>
<%=HTMLRenderUtil.displayHiddenField(displayMode, "address_displayMode")%>
<%=HTMLRenderUtil.displayHiddenField(ORIGUser.getCurrentRole(), "address_currentrole")%>
