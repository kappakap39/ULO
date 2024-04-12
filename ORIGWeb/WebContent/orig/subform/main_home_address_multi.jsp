<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.AddressDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = "VIEW";	

	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	PersonalInfoDataM personalInfoDataM;
	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	}else{
		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	}
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();
	}
	//Vector addressVect = utility.getVectorAddressByType(personalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);
	Vector addressVect = personalInfoDataM.getAddressVect();
%>

<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr> 
		<td>
			<div id="KLTable">
				<div id="Address">
					<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr><td class="TableHeader">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td width="5%" class="Bigtodotext3" align="center"></td>
									<td width="18%" class="Bigtodotext3" align="center"><%=MessageResourceUtil.getTextDescription(request, "ADDRESS_TYPE") %></td>
									<td width="5%" class="Bigtodotext3" align="center"><%=MessageResourceUtil.getTextDescription(request, "SEQ") %></td>
									<td width="15%" class="Bigtodotext3" align="center"><%=MessageResourceUtil.getTextDescription(request, "ADDRESS_FORMAT") %></td>
									<td width="12%" class="Bigtodotext3" align="center"><%=MessageResourceUtil.getTextDescription(request, "PHONE_NO1") %></td>
									<td width="5%" class="Bigtodotext3" align="center"><%=MessageResourceUtil.getTextDescription(request, "EXT1") %></td>
									<td width="15%" class="Bigtodotext3" align="center"><%=MessageResourceUtil.getTextDescription(request, "ADDRESS_STATUS") %></td>
									<td width="25%" class="Bigtodotext3" align="center"><%=MessageResourceUtil.getTextDescription(request, "CONTACT_PERSON") %></td>
								</tr>
							</table> 
						</td></tr>
						
						<%if(addressVect != null && addressVect.size() >0){
							AddressDataM addressDataM;
							String addressType = null;
							String addressStatus = null;
							String disabledChk = null;
							for(int i=0; i<addressVect.size(); i++){
								disabledChk = "";
								addressDataM = (AddressDataM) addressVect.get(i);
								addressType = cacheUtil.getORIGMasterDisplayNameDataM("AddressType", addressDataM.getAddressType());
								addressStatus = cacheUtil.getORIGMasterDisplayNameDataM("AddressStatus", addressDataM.getStatus());
								if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
										disabledChk = "disabled";
								}
						%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
							<tr>
								<td align="center" class="jobopening2" width="5%"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","addressSeq",String.valueOf(addressDataM.getAddressSeq()),disabledChk) %></td>
								<td class="jobopening2" width="18%"><a href="javascript:loadPopup('address','LoadAddressPopup','1000','300','200','10','<%=addressDataM.getAddressSeq() %>','<%=i+1 %>','<%=personalType%>')"><u><%=ORIGDisplayFormatUtil.displayHTML(addressType) %></u></a></td>
								<td class="jobopening2" align="center" width="5%"><%=(i+1) %></td>
								<td class="jobopening2" align="center" width="15%"><%=ORIGDisplayFormatUtil.displayHTML(utility.getAddressFormatDesc(addressDataM.getAddressFormat())) %></td>
								<td class="jobopening2" align="center" width="12%"><%=ORIGDisplayFormatUtil.displayHTML(addressDataM.getPhoneNo1()) %></td>
								<td class="jobopening2" align="center" width="5%"><%=ORIGDisplayFormatUtil.displayHTML(addressDataM.getPhoneExt1()) %></td>
								<td class="jobopening2" width="15%"><%=ORIGDisplayFormatUtil.displayHTML(addressStatus) %></td>
								<td class="jobopening2" width="25%"><%=ORIGDisplayFormatUtil.displayHTML(addressDataM.getContactPerson()) %></td>
							</tr>
							</table> 
						</td></tr>
						<%}
						}else{%>
							<tr><td align="center" class="gumaygrey2">
								<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td colspan="8" align="center" class="jobopening2">No Record</td>
								</tr>
								</table> 
							</td></tr>
					  <%} %>
					</table>
				</div>
			</div>
		</td>
	</tr>
	<tr><td height="20" colspan="5"></td></tr>
	<% if(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT.equals(displayMode)){
			String disabledView = "";
	%>
	<tr>
		<td width="3%"></td>
		<td id="foundAddress" width="15%" class="foundText"><%--<%=foundAddress%>--%></td>
		<td colspan="" width="10%"><INPUT type="button" name="viewAddressBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "VIEW") %> " onClick="javascript:loadPopup('viewAddress','LoadViewAddressPopup','1000','550','50','10','0','','<%=personalType%>')" class="button_text" <%=disabledView %>></td>
		<td align="right" width="69%"><INPUT type="button" name="addHomeBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "ADD") %> " onClick="javascript:loadPopup('address','LoadAddressPopup','1000','300','200','10','0','','<%=personalType%>')" class="button_text">
		<INPUT type="button" name="deleteAddressBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "DELETE") %> " onClick="javascript:deleteTableList('addressSeq','Address','DeleteAddressServlet')" class="button_text"></td>
		<td width="3%"></td>
	</tr>
	<%} %>
</table>