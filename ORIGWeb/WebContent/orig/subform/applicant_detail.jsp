<%@page import="com.eaf.orig.ulo.pl.service.PLORIGEJBService"%>
<%@page import="com.eaf.j2ee.pattern.util.DisplayFormatUtil"%>
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
<%@ page import="com.eaf.orig.shared.dao.utility.UtilityDAO" %>
<%-- <%@ page import="com.eaf.orig.shared.dao.ORIGDAOFactory" %> --%>


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
	System.out.println("displayMode==>"+displayMode);
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
		String fromMultiApp = (String)request.getSession().getAttribute("fromMultiApp");
		disableEditBtn = "disabled";		
		if ("Y".equals(fromMultiApp) && OrigConstant.ROLE_DE.equals(ORIGUser.getRoles().elementAt(0)) ) {
			disableEditBtn = "";
		}
	}
	System.out.println("personalInfoDataM.getIdNo()="+personalInfoDataM.getIdNo());
	String disabledForUW = "";
	System.out.println("applicationDataM.getCmrFirstId() = "+applicationDataM.getCmrFirstId());
	if((OrigConstant.ROLE_UW.equals(ORIGUser.getRoles().elementAt(0)) || (OrigConstant.ROLE_DE.equals(ORIGUser.getRoles().elementAt(0)) && !ORIGUtility.isEmptyString(applicationDataM.getCmrFirstId()))) && !ORIGUtility.isEmptyString(applicationDataM.getOfficeCode())){
		disabledForUW = "disabled";
	}
%>
<table cellpadding="0" cellspacing="0" width="100%" height="100%" align="center">
	<%if(OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalInfoDataM.getPersonalType())){ 
		String idnoScript = " onblur=\"javascript:checkCitizenID('identification_no'); copyCardID();\" ";
		if(!ORIGUtility.isEmptyString(personalInfoDataM.getIdNo())){
			idnoScript = " readOnly";
		}
	%>
		<tr>
			<td class="textColS" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"APPLICANT_SUBFORM","application_no")%></Font> :</td>
			<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(applicationDataM.getApplicationNo(),displayMode,"","application_no","textboxReadOnly","readOnly","50") %></td>
			<td class="textColS" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "IDENTIFICATION_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"APPLICANT_SUBFORM","identification_no")%></Font> :</td>
			<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getIdNo(),displayMode,"","identification_no","textbox",idnoScript,maxlength) %></td>
			<% if(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT.equals(displayMode)){%>
			<td class="textColS" colspan="2">
				<table align="left"> <tr> <td>
					<input type="button" name="retrieveBtn" value="<%=MessageResourceUtil.getTextDescription(request, "RETRIEVE_DATA") %>" onClick="javascript:retrieveData()" class="button_text" <%=disableEditBtn %> onMouseOver="<%=tooltipUtil.getTooltip(request,"retrieve_data")%>">
				</td><td>	
					<input type="button" name="editBtn" value="<%=MessageResourceUtil.getTextDescription(request, "CHANGE_ID") %>" onClick="javascript:editPersonal()" class="button_text" <%=disableEditBtn %> onMouseOver="<%=tooltipUtil.getTooltip(request,"edit_data")%>">
				</td></tr></table>
			</td>
			<%}else{ %>
				<td class="inputCol" colspan="2"></td>
			<%} %>
			<td class="textColS" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "GROUP_ID") %> :</td>
			<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(applicationDataM.getGroupAppID(),DisplayFormatUtil.DISPLAY_MODE_VIEW,"","group_id","textboxReadOnly","readOnly","50") %></td>
		</tr>
		<tr>
			<td class="textColS" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "OFFICE_CODE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"APPLICANT_SUBFORM","office_code")%></Font> :</td>
			<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(officeVect,applicationDataM.getOfficeCode(),"office_code",displayMode,"style=\"width:120px;\" "+disabledForUW) %></td>
			<td class="textColS" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "LOAN_TYPE") %> :</td>
			<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(cacheUtil.getORIGMasterDisplayNameDataM("Product",applicationDataM.getLoanType()),DisplayFormatUtil.DISPLAY_MODE_VIEW,"","loan_type","textboxReadOnly","readOnly","50") %></td>
			<td class="textColS" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "RELATED_APPS") %> :</td>
	<%
// 			UtilityDAO dao = ORIGDAOFactory.getUtilityDAO();
// 			Vector vAppNos = dao.getAppNumberByGroupID(applicationDataM.getGroupAppID(),applicationDataM.getAppRecordID());
			Vector vAppNos =  PLORIGEJBService.getORIGDAOUtilLocal().getAppNumberByGroupID(applicationDataM.getGroupAppID(),applicationDataM.getAppRecordID());
 			String appNos = "";
 			for (int g=0;g < vAppNos.size();g++) {
 				appNos = appNos + (String)vAppNos.get(g) ;
 				if ((g+1) != vAppNos.size()) {
 					appNos = appNos + ",";
 				}
 			}
 
 	%>			
			<td class="inputCol" colspan="2"><%=appNos%></td>
			<td class="inputCol"></td>
		</tr>
	<%}else{ 
	    
	    String idnoScript = " onblur=\"javascript:checkCitizenID('identification_no'); checkDupIdno(); copyCardID();\" ";
		if(!ORIGUtility.isEmptyString(personalInfoDataM.getIdNo())){
			idnoScript = " readOnly";
		}
	   %>
		<tr>
			<td class="textColS" width="15%"><%=MessageResourceUtil.getTextDescription(request, "IDENTIFICATION_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"APPLICANT_SUBFORM","identification_no")%></Font> :</td>
			<td class="inputCol" width="15%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getIdNo(),displayMode,"","identification_no","textbox",idnoScript,"13") %></td>
			<% if(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT.equals(displayMode)){%>
			<td width="15%"><input type="button" name="retrieveBtn" value="<%=MessageResourceUtil.getTextDescription(request, "RETRIEVE_DATA") %>" onClick="javascript:retrieveData()" class="button_text" <%=disableEditBtn %> onMouseOver="<%=tooltipUtil.getTooltip(request,"retrieve_data")%>" ></td>
			<td width="15%"><input type="button" name="editBtn" value="<%=MessageResourceUtil.getTextDescription(request, "CHANGE_ID") %>" onClick="javascript:editPersonal()" class="button_text" <%=disableEditBtn %>  onMouseOver="<%=tooltipUtil.getTooltip(request,"edit_data")%>" ></td>
			<%}else{ %>
				<td width="15%" class="inputCol">&nbsp;</td>
			<%} %>
		</tr>
	<%} %>
	<%if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){ %>
	  <tr>  <td class="textColS">Guarantor/Co-Borrower :
	        <input type="hidden" name ="coborrowerFlag" value="<%=personalInfoDataM.getCoborrowerFlag()%>"></td>
           	<td class="inputCol">
           	<% 
           	 if(OrigConstant.COBORROWER_FLAG_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag())||OrigConstant.COBORROWER_FLAG_UN_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag())){%>
           	 	<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("Co-Borrower",DisplayFormatUtil.DISPLAY_MODE_VIEW,"","co_borrower","textboxReadOnly","readOnly","50") %> 
           	 <% 
           	 }else{
           	%>     
           		<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("Guarantor",DisplayFormatUtil.DISPLAY_MODE_VIEW,"","guarantor","textboxReadOnly","readOnly","50") %> 
           	<%} %> 
           	</td>
            <%if( OrigConstant.COBORROWER_FLAG_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag()) ||OrigConstant.COBORROWER_FLAG_UN_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag()) ){%>
				<td class="textColS">Active:</td>
				<td class="inputCol">		
			  	<%=ORIGDisplayFormatUtil.displayCheckBoxTag("Y",personalInfoDataM.getCoborrowerFlag(),displayMode,"coborower_acive_flag","","Active Flag" ) %>			
				</td>
  		    <%} else if(OrigConstant.COBORROWER_FLAG_NO.equals(personalInfoDataM.getCoborrowerFlag() )||"".equals(personalInfoDataM.getCoborrowerFlag()) ||personalInfoDataM.getCoborrowerFlag()==null) {%>
			<td class="textColS">Include DebtAmount</td>
			<td class="inputCol"> 
				<%=ORIGDisplayFormatUtil.displayCheckBoxTag("Y",personalInfoDataM.getDebtIncludeFlag(),displayMode,"debtIncludeFlag","","Include Debt" ) %>       
			</td>    	 
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
	
	function copyCardID() {
		var idno=document.appFormName.identification_no.value;
		document.getElementById('card_id').value = idno;		
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