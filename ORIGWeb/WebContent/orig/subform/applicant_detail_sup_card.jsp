<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.shared.model.LoanDataM"%>
<%@ page import="com.eaf.orig.shared.utility.TooltipResourceUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ORIGUtility utility = new ORIGUtility();
	Vector officeVect = utility.loadCacheByName("OfficeInformation");
	Vector cmrCodeVect = utility.loadCacheByName("UserName");
    TooltipResourceUtil tooltipUtil=TooltipResourceUtil.getInstance();
	
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	
	//Get Display Mode
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("APPLICANT_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);
	
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
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
	
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	
	String formId = ORIGForm.getFormID();
	String loanType;
	Vector loanVect = applicationDataM.getLoanVect();
	if(loanVect != null && loanVect.size() > 0){
		loanType = ((LoanDataM) loanVect.elementAt(0)).getLoanType();
	}else{
		loanType = ORIGForm.getAppForm().getLoanType();
	}
	if(ORIGUtility.isEmptyString(applicationDataM.getOfficeCode())){
		applicationDataM.setOfficeCode(ORIGUser.getDefaultOfficeCode());
	}
	String maxlength = "13";
	if(OrigConstant.CUSTOMER_TYPE_FOREIGNER.equals(personalInfoDataM.getCustomerType())){
		maxlength = "15";
	}
	
	String disableEditBtn = "";
	//if(   applicationDataM.getApplicationNo()!=null && !"".equals(applicationDataM.getApplicationNo())){
	if(   personalInfoDataM.getIdNo()!=null&&!"".equals(personalInfoDataM.getIdNo())){
		disableEditBtn = "disabled";
	}
	System.out.println("personalInfoDataM.getIdNo()="+personalInfoDataM.getIdNo());
	String disabledForUW = "";
	System.out.println("applicationDataM.getCmrFirstId() = "+applicationDataM.getCmrFirstId());
	if((OrigConstant.ROLE_UW.equals(ORIGUser.getRoles().elementAt(0)) || (OrigConstant.ROLE_DE.equals(ORIGUser.getRoles().elementAt(0)) && !ORIGUtility.isEmptyString(applicationDataM.getCmrFirstId()))) && !ORIGUtility.isEmptyString(applicationDataM.getOfficeCode())){
		disabledForUW = "disabled";
	}
%>
<table cellpadding="" cellspacing="1" width="100%" align="center">
	<%if(OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalInfoDataM.getPersonalType())){ 
	String idnoScript = " onblur=\"javascript:checkCitizenID('identification_no');\" ";
	if(!ORIGUtility.isEmptyString(personalInfoDataM.getIdNo())){
		idnoScript = " readOnly";
	}
	%>
		<tr>
			<td class="textColS" width="15%"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"APPLICANT_SUBFORM","application_no")%></Font> :</td>
			<td class="inputCol" width="15%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(applicationDataM.getApplicationNo(),displayMode,"","application_no","textboxReadOnly","readOnly","50") %></td>
			<td class="textColS" width="15%"><%=MessageResourceUtil.getTextDescription(request, "IDENTIFICATION_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"APPLICANT_SUBFORM","identification_no")%></Font> :</td>
			<td class="inputCol" width="15%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getIdNo(),displayMode,"","identification_no","textbox",idnoScript,maxlength) %></td>
			<% if(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT.equals(displayMode)){%>
			<td align="left"><input type="button" name="retrieveBtn" value="<%=MessageResourceUtil.getTextDescription(request, "RETRIEVE_DATA") %>" onClick="javascript:retrieveData()" class="button_text" <%=disableEditBtn %> onMouseOver="<%=tooltipUtil.getTooltip(request,"retrieve_data")%>"></td>
			<td><input type="button" name="editBtn" value="<%=MessageResourceUtil.getTextDescription(request, "CHANGE_ID") %>" onClick="javascript:editPersonal()" class="button_text" <%=disableEditBtn %> onMouseOver="<%=tooltipUtil.getTooltip(request,"edit_data")%>"></td>
			<%}else{ %>
				<td class="inputCol">&nbsp;</td>
			<%} %>
		</tr>
	<%}else{ 
	    
	    String idnoScript = " onblur=\"javascript:checkCitizenID('identification_no'); checkDupIdno();\" ";
	if(!ORIGUtility.isEmptyString(personalInfoDataM.getIdNo())){
		idnoScript = " readOnly";
	}
	   %>
		<tr>
			<td class="textColS" width="15%"><%=MessageResourceUtil.getTextDescription(request, "IDENTIFICATION_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"APPLICANT_SUBFORM","identification_no")%></Font> :</td>
			<td class="inputCol" width="15%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getIdNo(),displayMode,"","identification_no","textbox",idnoScript,"13") %></td>
			<% if(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT.equals(displayMode)){%>
			<td><input type="button" name="retrieveBtn" value="<%=MessageResourceUtil.getTextDescription(request, "RETRIEVE_DATA") %>" onClick="javascript:retrieveData()" class="button_text" <%=disableEditBtn %> onMouseOver="<%=tooltipUtil.getTooltip(request,"retrieve_data")%>" ></td>
			<td><input type="button" name="editBtn" value="<%=MessageResourceUtil.getTextDescription(request, "CHANGE_ID") %>" onClick="javascript:editPersonal()" class="button_text" <%=disableEditBtn %>  onMouseOver="<%=tooltipUtil.getTooltip(request,"edit_data")%>" ></td>
			<%}else{ %>
				<td class="inputCol">&nbsp;</td>
			<%} %>
		</tr>
	<%} %>
</table>
<script language="JavaScript">
	if(document.appFormName.identification_no != null){
		if(document.appFormName.identification_no.value != ''){
			var iden = document.getElementById('identification_no');
			iden.className = "textboxReadOnly";
		}
	}
	<% Vector personalInfoVect=applicationDataM.getPersonalInfoVect();%>
	function checkDupIdno(){	    
	    var idnoCheck=document.appFormName.identification_no.value;
	   <% if(personalInfoVect!=null){
	      for(int i=0;i<personalInfoVect.size();i++){ 
	        PersonalInfoDataM personalDataM=(PersonalInfoDataM)personalInfoVect.get(i);	        
	   %>
	      if(idnoCheck=='<%=personalDataM.getIdNo()%>'){
	       alert('Duplicate Application No');
	       document.appFormName.identification_no.value='';
	       return;
	      }
	   <% }%>
	   <%}%>
	   if(opener.appFormName.identification_no){
	       if(opener.appFormName.identification_no.value==idnoCheck){
	        alert('Duplicate Application No');
	        document.appFormName.identification_no.value='';
	        return;
	       }
	    }
	}
</script>
<input type="hidden" name="appPersonalType" value="<%=personalType%>">