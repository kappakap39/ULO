<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.CardInformationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("SUPCARD_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

	ORIGUtility utility = new ORIGUtility();
	Vector vecCardInformation = applicationDataM.getCardInformation();
	Vector vecPersonalInfo = applicationDataM.getPersonalInfoVect();
	//Vector personaTypeVect=new Vector();	
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	PersonalInfoDataM prmPersonalInfoDataM = new PersonalInfoDataM();
	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
		prmPersonalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
	}else if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)){
		prmPersonalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	}else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
		prmPersonalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	}
	if(prmPersonalInfoDataM == null){
		prmPersonalInfoDataM = new PersonalInfoDataM();		
	}
  String roles=(String)ORIGUser.getRoles().get(0);
%>

<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr> 
		<td colspan="4">
			<div id="KLTable">
				<div id="SupplementaryCard">
					<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr><td class="TableHeader">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td class="Bigtodotext3" align="center" width="5%"></td>
									<td class="Bigtodotext3" align="center" width="15%"><%=MessageResourceUtil.getTextDescription(request, "CARD_NO") %></td>
									<td class="Bigtodotext3" align="center" width="30%"><%=MessageResourceUtil.getTextDescription(request, "NAME") %></td>
									<td class="Bigtodotext3" align="center" width="15%"><%=MessageResourceUtil.getTextDescription(request, "CITIZEN_ID") %></td>
								</tr>
							</table> 
						</td></tr>
							
					<%boolean isNoRecord = true;
					if(vecCardInformation != null && vecCardInformation.size() > 0){
						PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
						CardInformationDataM cardInformationDataM;
						String titleName;
						
						for(int i=0; i<vecCardInformation.size(); i++){
							cardInformationDataM = (CardInformationDataM) vecCardInformation.get(i);
							if (OrigConstant.CARD_INFORMATION_APPLICATION_TYPE_SUB.equalsIgnoreCase(cardInformationDataM.getApplicationType())){
								for (int j=0; j<vecPersonalInfo.size(); j++){
									PersonalInfoDataM temp = (PersonalInfoDataM)vecPersonalInfo.get(j);
									if (temp.getIdNo()!=null && temp.getIdNo().equalsIgnoreCase(cardInformationDataM.getIdNo())){
										personalInfoDataM = temp;
										break;
									}
								}
								String disabledChk ="";
								if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equals(displayMode) ||(OrigConstant.COBORROWER_FLAG_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag())||OrigConstant.COBORROWER_FLAG_UN_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag()))){							
									disabledChk = " disabled";
								}
								titleName = cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM.getThaiTitleName());
								%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td class="jobopening2" align="center"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","supCardSeq",String.valueOf(personalInfoDataM.getPersonalSeq()),disabledChk) %></td>
									<td class="jobopening2" align="center"><%=ORIGDisplayFormatUtil.displayHTML(cardInformationDataM.getCardNo()) %></td>
									<td class="jobopening2"><a href="javascript:loadPopup('guarantor','LoadSupplementaryCardPopup','1150','500','150','40','<%=personalInfoDataM.getPersonalSeq() %>','<%=personalInfoDataM.getCustomerType() %>','')"><u><%=titleName + personalInfoDataM.getThaiFirstName() +" "+ ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getThaiLastName())%></u></a></td>
									<td class="jobopening2"><%=ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getCardID()) %></td>
								</tr>
							</table> 
						</td></tr>
							<%isNoRecord = false;
							}%>
					<%  }
					}
					if (isNoRecord){%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
							  		<td class="jobopening2" align="center">No Record</td>
							  	</tr>
							</table> 
						</td></tr>
					<%}%>
					</table>
				</div>
			</div>
		</td>
	</tr>
	<tr><td height="20" colspan="4"></td></tr>
	<%if(displayMode.equals(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT)){ %>
		<tr>
			<td align="right" width="84%"></td>
			<td align="right" width="7%"><INPUT type="button" name="addGuarantorBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "ADD") %> " onClick="javascript:addSupplementaryCard()" class="button_text"></td>
			<td align="right" width="7%"><INPUT type="button" name="deleteGuarantorBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "DELETE") %> " onClick="javascript:deleteTableList('supCardSeq','SupplementaryCard','DeleteSupplementaryCardServlet')" class="button_text"></td>
			<td width="3%"></td>
		</tr>
	<%} %>
</table>

<script language="JavaScript">
function addSupplementaryCard(){
    if( document.appFormName.identification_no.value==''){
      alert("Please Input Identification No.");
      return;
    }
	if(  checkFieldBeforeDoAction('type', '<%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_TYPE") %>')){
		loadPopup('guarantor','LoadSupplementaryCardPopup','1150','500','150','40','0','')
	}
}
</script>