<%@ page import="com.eaf.orig.ulo.pl.app.utility.ORIGLogic"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="PLORIGForm" />
<script type="text/javascript" src="orig/js/appform/pl/app_button.js"></script>
<%  
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/appform/pl/app_button.jsp");	
	
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	
	if(null == applicationM) applicationM = new PLApplicationDataM();
	
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);

	String customerType = personalM.getCustomerType();

	String userRole = ORIGUser.getCurrentRole();
	String searchType = (String)request.getSession().getAttribute("searchType");
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String displayMode = formUtil.getDisplayMode("MAIN_APPLICANT_SUBFORM",ORIGUser.getRoles(),applicationM.getJobState(),PLORIGForm.getFormID(),searchType);
	String disabledBtn = "";
	if(!HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode) && !OrigConstant.ROLE_PO.equals(userRole)){
		disabledBtn = "style='visibility: hidden;'";
	}
	String btSubmitDesc = PLMessageResourceUtil.getTextDescription(request, "SUBMIT");
	String btSaveDraft = PLMessageResourceUtil.getTextDescription(request, "SAVE");
	
	if(!OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)){
		if (OrigConstant.ROLE_DF.equals(userRole)||OrigConstant.ROLE_DF_REJECT.equals(userRole)){
			disabledBtn = "";
			btSubmitDesc = PLMessageResourceUtil.getTextDescription(request, "FINISH");
			btSaveDraft = PLMessageResourceUtil.getTextDescription(request, "SAVE");
		} else if(OrigConstant.ROLE_CA.equals(userRole) || OrigConstant.ROLE_DC.equals(userRole)){
			disabledBtn = "";
		}
	}
	
%>
<table class="FormFrame">
	<tr>   
		<td width="20%" class="TextL">
			<table class="FormFrame" style="width: 220px;">
				<tr>
					<td class="TextC">
						<input type="button" name="History Action" value="<%=PLMessageResourceUtil.getTextDescription(request, "HISTORY_ACTION") %>" 
							class="button" onclick="javascript:loadHistoryAction(<%=applicationM.getAppRecordID()%>)" >
					</td>
					<td class="TextC">
						<input type="button" name="Audit Trail" value="<%=PLMessageResourceUtil.getTextDescription(request, "AUDIT_TRAIL") %>" 
							class="button" onclick="auditButton(<%=applicationM.getAppRecordID()%>)">
					</td>
				</tr>
			</table>			
        </td>
        <td width="60%" class="TextC"></td>	
        <td width="20%" class="TextR">
        	 <table class="FormFrame" class="FormFrame" style=" width:190px;" align="right">
        		<tr>
					<td class="TextR" nowrap="nowrap">
	        			<input type="button" name="Submit" id="Submit" value="<%=btSubmitDesc%>" class="button" onclick="javascript:validateSubmitApp()" <%=disabledBtn%> >
	        			<%if(!OrigConstant.ROLE_PO.equals(userRole) && !OrigConstant.ROLE_DF.equals(userRole)){%>
	        				<input type="button" name="Save" id="Save" value="<%=btSaveDraft%>" class="button" onclick="javascript:validateSaveApp()"  <%=disabledBtn%>>
	        			<%}%>
	        			<input type="button" name="Cancel" value="<%=PLMessageResourceUtil.getTextDescription(request, "CANCEL") %>" class="button" onclick="javascript:cancelButton()" >
        			</td>
        		</tr>
        	</table>
        </td>
    </tr>
</table>			
<%=HTMLRenderUtil.displayHiddenField(searchType,"searchType")%>
<%=HTMLRenderUtil.displayHiddenField(userRole,"role")%>
<%=HTMLRenderUtil.displayHiddenField(applicationM.getRequestID(),"requestID")%>
<%=HTMLRenderUtil.displayHiddenField(applicationM.getAttachId(),"attachID")%>
<%=HTMLRenderUtil.displayHiddenField("","exceptionFlag")%>
<%=HTMLRenderUtil.displayHiddenField("","submitType")%>
<%=HTMLRenderUtil.displayHiddenField("","mandatoryType")%>
<%=HTMLRenderUtil.displayHiddenField(applicationM.getBusinessClassId(),"busClass")%>
<%=HTMLRenderUtil.displayHiddenField(applicationM.getJobState(),"jobState")%>
<%=HTMLRenderUtil.displayHiddenField(personalM.getPersonalType(),"personalType")%>
<%=HTMLRenderUtil.displayHiddenField(ORIGLogic.displayServerFLAG(applicationM),"server_flag")%>