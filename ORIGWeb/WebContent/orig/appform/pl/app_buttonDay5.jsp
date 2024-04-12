<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.wf.shared.ORIGWFConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="PLORIGForm" />
<script type="text/javascript" src="orig/js/appform/pl/app_buttonDay5.js"></script>
<%  
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/appform/pl/app_buttonDay5.jsp");	

	ORIGUtility utility = new ORIGUtility();
	PLApplicationDataM plAppM = PLORIGForm.getAppForm();
	
	if(plAppM == null) plAppM = new PLApplicationDataM();
	
	PLPersonalInfoDataM personalM = plAppM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);

	String customerType = personalM.getCustomerType();

	String userRole = ORIGUser.getCurrentRole();
	String searchType = (String)request.getSession().getAttribute("searchType");
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	
	String displayMode = formUtil.getDisplayMode("APPLICANT_SUBFORM", ORIGUser.getRoles(), plAppM.getJobState(), PLORIGForm.getFormID(), searchType);

	String disabledBtn = "";
	if(!HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode)){
		disabledBtn = ORIGDisplayFormatUtil.DISABLED;
	}
	disabledBtn = "";
	String btSubmitDesc = PLMessageResourceUtil.getTextDescription(request, "SUBMIT");
	String btSaveDraft = PLMessageResourceUtil.getTextDescription(request, "SAVE");
	
	if(OrigConstant.ROLE_DF.equals(userRole)){
		btSubmitDesc = PLMessageResourceUtil.getTextDescription(request, "FINISH");
		btSaveDraft = PLMessageResourceUtil.getTextDescription(request, "SAVE");
	}
	
%>
<table class="FormFrame">
	<tr>   
		<td width="20%" class="TextL">
			<table class="FormFrame">
				<tr>
					<td width="10%" class="TextC">
						<input type="button" name="History Action" value="<%=PLMessageResourceUtil.getTextDescription(request, "HISTORY_ACTION") %>" 
							class="button" onclick="javascript:loadHistoryAction(<%=plAppM.getAppRecordID()%>)" >
					</td>
					<td width="10%" class="TextC">
						<input type="button" name="Audit Trail" value="<%=PLMessageResourceUtil.getTextDescription(request, "AUDIT_TRAIL") %>" 
							class="button" onclick="auditButton(<%=plAppM.getAppRecordID()%>)">
					</td>
				</tr>
			</table>			
        <td>
        <td width="55%" class="TextC"><td>	
        <td width="15%" class="TextR">
        	 <table class="FormFrame">
        		<tr><!-- 
        			<td width="5%" class="TextR">        			
        				<input type="button" name="Submit" value="<%=btSubmitDesc%>" class="button" onclick="javascript:validateSubmitApp()" <%=disabledBtn%> >
        			</td> -->
        			<td width="5%" class="TextR">
        				<input type="button" name="Save" value="<%=btSubmitDesc%>" class="button" onclick="javascript:validateSaveApp()"  <%=disabledBtn%>>
        			</td>
        			<td width="5%" class="TextR">        			
        				<input type="button" name="Cancel" value="<%=PLMessageResourceUtil.getTextDescription(request, "CANCEL") %>" 
        							class="button" onclick="javascript:cancelButton()" >
        			</td>
        		</tr>
        	</table>
        </td>		
	</tr> 
</table>			
<input type="hidden" name="searchType"  id="searchType" value="<%=searchType%>">
<input type="hidden" name="role" id="role" value="<%=userRole%>">
<input type="hidden" name="requestID" id="requestID" value="<%=plAppM.getRequestID()%>"> 
<input type="hidden" name="attachID" id="attachID" value="<%=plAppM.getAttachId()%>">
<input type="hidden" name="exceptionFlag" id="exceptionFlag" value="N">
<input type="hidden" name="submitType" id="submitType" value="">
<input type="hidden" name="mandatoryType" id="mandatoryType" value="">
<input type="hidden" name="busClass" id="busClass" value="<%=plAppM.getBusinessClassId()%>">
