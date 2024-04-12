<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.ChangeNameDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
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
	String displayMode = formUtil.getDisplayMode("CHANGE_NAME_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	
	ORIGUtility utility = new ORIGUtility();
//	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
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
	Vector changeNameVect = personalInfoDataM.getChangeNameVect();
%>

<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr> 
		<td colspan="2">
			<div id="KLTable">
				<div id="ChangeName">
					<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr><td class="TableHeader">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td class="Bigtodotext3" align="center" width="5%"><%=MessageResourceUtil.getTextDescription(request, "SEQ") %></td>
									<td class="Bigtodotext3" align="center" width="19%"><%=MessageResourceUtil.getTextDescription(request, "CHANGE_DATE") %></td>
									<td class="Bigtodotext3" align="center" width="19%"><%=MessageResourceUtil.getTextDescription(request, "OLD_NAME") %></td>
									<td class="Bigtodotext3" align="center" width="19%"><%=MessageResourceUtil.getTextDescription(request, "OLD_SURNAME") %></td>
									<td class="Bigtodotext3" align="center" width="19%"><%=MessageResourceUtil.getTextDescription(request, "NEW_NAME") %></td>
									<td class="Bigtodotext3" align="center" width="19%"><%=MessageResourceUtil.getTextDescription(request, "NEW_SURNAME") %></td>
								</tr>
							</table> 
						</td></tr>
						<%if(changeNameVect != null && changeNameVect.size() > 0){
							ChangeNameDataM changeNameDataM;
							String sDate;
							for(int i=0; i<changeNameVect.size(); i++){
								changeNameDataM = (ChangeNameDataM) changeNameVect.get(i);
								if(changeNameDataM.getChangeDate()==null){
									sDate="";
								}else{
									sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(changeNameDataM.getChangeDate()));
								}
						%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
							<tr>
								<td class="jobopening2" align="center" width="5%"><%=(i+1) %></td>
								<td class="jobopening2" align="center"><a href="javascript:loadPopup('changeName','LoadChangeNamePopup','900','275','300','80','<%=changeNameDataM.getSeq() %>','','<%=personalType%>')"><u><%=sDate %></u></a></td>
								<td class="jobopening2" width="19%"><%=ORIGDisplayFormatUtil.displayHTML(changeNameDataM.getOldName()) %></td>
								<td class="jobopening2" width="19%"><%=ORIGDisplayFormatUtil.displayHTML(changeNameDataM.getOldSurName()) %></td>
								<td class="jobopening2" width="19%"><%=ORIGDisplayFormatUtil.displayHTML(changeNameDataM.getNewName()) %></td>
								<td class="jobopening2" width="19%"><%=ORIGDisplayFormatUtil.displayHTML(changeNameDataM.getNewSurname()) %></td>
							</tr>
							</table> 
						</td></tr>
						<%	} 
						  }else{%>
							<tr><td align="center" class="gumaygrey2">
								<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
						  			<td class="jobopening2" colspan="6" align="center">No Record</td>
						  		</tr>
								</table> 
							</td></tr>
						<%}%>
					</table>
				</div>
			</div>
		</td>
	</tr>
	<tr><td height="20"></td></tr>
	<% if(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT.equals(displayMode)){%>
	<tr>
		<td align="right" width="98%"><INPUT type="button" name="addChangeNameBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "ADD") %> " onClick="javascript:loadPopup('address','LoadChangeNamePopup','700','275','300','180','','','<%=personalType%>')" class="button_text">
		 <!-- INPUT type="button" name="deleteChangeNameBnt" value=" Delete " onClick="javascript:deleteTableList('changeNameSeq','ChangeName','DeleteChangeNameServlet')" class="button_text"></td-->
		</td>
		<td width="2%"></td>
	</tr>
	<%} %>
</table>