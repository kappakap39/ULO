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
	String displayMode = formUtil.getDisplayMode("MAIN_HOME_ADDRESS_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

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
	/**Vector addressVect = utility.getVectorAddressByType(personalInfoDataM, OrigConstant.ADDRESS_TYPE_HOME);*/
	Vector addressVect = personalInfoDataM.getAddressVect();
%>

<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr> 
		<td colspan="6">
			<div id="KLTable">
				<div id="Address">
					<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr><td class="TableHeader">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td class="Bigtodotext3" align="center" width="5%"></td>
									<td class="Bigtodotext3" align="center" width="18%"><%=MessageResourceUtil.getTextDescription(request, "ADDRESS_TYPE") %></td>
									<td class="Bigtodotext3" align="center" width="5%"><%=MessageResourceUtil.getTextDescription(request, "SEQ") %></td>
									<td class="Bigtodotext3" align="center" width="15%"><%=MessageResourceUtil.getTextDescription(request, "ADDRESS_FORMAT") %></td>
									<td class="Bigtodotext3" align="center" width="12%"><%=MessageResourceUtil.getTextDescription(request, "PHONE_NO1") %></td>
									<td class="Bigtodotext3" align="center" width="5%"><%=MessageResourceUtil.getTextDescription(request, "EXT1") %></td>
									<td class="Bigtodotext3" align="center" width="15%"><%=MessageResourceUtil.getTextDescription(request, "ADDRESS_STATUS") %></td>
									<td class="Bigtodotext3" align="center" width="25%"><%=MessageResourceUtil.getTextDescription(request, "CONTACT_PERSON") %></td>
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
									<td class="jobopening2" align="center" width="5%"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","addressSeq",String.valueOf(addressDataM.getAddressSeq()),disabledChk) %></td>
									<td class="jobopening2" width="18%"><a href="javascript:loadPopup('address','LoadAddressPopup','1000','450','200','10','<%=addressDataM.getAddressSeq() %>','<%=i+1 %>','<%=personalType%>')"><u><%=ORIGDisplayFormatUtil.displayHTML(addressType) %></u></a></td>
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
						  		<td class="jobopening2" align="center">No Record</td>
						  	</tr>
							</table> 
						</td></tr>
					  <%} %>
					</table>
				</div>
			</div>
		</td>
	</tr>
	<tr><td height="20" colspan="6"></td></tr>
	<% if(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT.equals(displayMode)){
			String disabledView = "";
			//String foundAddress = "";
			//if(personalInfoDataM.getAddressTmpVect() == null || personalInfoDataM.getAddressTmpVect().size() == 0){
			//	disabledView = "disabled";
			//}else{
			//	foundAddress = "Found "+personalInfoDataM.getAddressTmpVect().size()+" Records";
		//	}
	%>
	<tr>
		<td width="50%"></td>
		<td id="foundAddress" width="26%" class="foundText"><%--<%=foundAddress%>--%></td>
		<td align="right" width="7%"><INPUT type="button" name="viewAddressBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "VIEW") %> " onClick="javascript:loadPopup('viewAddress','LoadViewAddressPopup','1300','550','50','10','0','','<%=personalType%>')" class="button_text" <%=disabledView %>></td>
		<td align="right" width="7%"><INPUT type="button" name="addHomeBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "ADD") %> " onClick="javascript:loadPopup('address','LoadAddressPopup','1150','450','200','10','0','','<%=personalType%>')" class="button_text">
		</td>
		<td align="right" width="8%"><INPUT type="button" name="deleteAddressBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "DELETE") %> " onClick="javascript:deleteTableList('addressSeq','Address','DeleteAddressServlet')" class="button_text"></td>
		<td width="2%"></td>
	</tr>
	<%} %>
</table>