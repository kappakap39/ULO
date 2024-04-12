<%@page import="com.eaf.core.ulo.common.util.FormDisplayModeUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.app.view.form.privilegeprojectcode.util.PrivilegeUtil"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeRccmdPrjCdeDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
 <script type="text/javascript" src="orig\ulo\subform\projectcode\projectcode\js\VerifyPrivilegeProjectCodeSubForm.js"></script>
 <jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
String SUB_FORM_ID="VERIFY_PRIVILEGE_PROJECT_CODE_SUBFORM";
FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
String ACTION_TYPE = flowControl.getActionType();
String displayMode = HtmlUtil.EDIT;
if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",ACTION_TYPE)){
	displayMode = HtmlUtil.DISABLED;
}else{
	FormUtil formUtil = new FormUtil(SUB_FORM_ID,request);
	displayMode = FormDisplayModeUtil.getDisplayMode("", "", formUtil);
}

int PERSONAL_SEQ = 1;
int PRVLG_PROJECT_CODE_INDEX=0;
int PRVLG_PROJECT_DOC_INDEX=0;
int PRVLG_APP_INDEX=0;
String[] FINAL_DECISION_EXCEPTION =    SystemConstant.getArrayConstant("PRVLG_FINAL_DECISION_EXCEPTION");
String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
ApplicationGroupDataM applicationGroup =  ORIGForm.getObjectForm();
ApplicationDataM applicationInfo = applicationGroup.filterFirstApplicationFinalDecision(PRODUCT_CRADIT_CARD, new ArrayList<String>(Arrays.asList(FINAL_DECISION_EXCEPTION)));
if(Util.empty(applicationInfo)){
	applicationInfo = new ApplicationDataM();
}
 
CardDataM  borrowerCard  = applicationInfo.getCard();
if(Util.empty(borrowerCard)){
	borrowerCard = new  CardDataM();
}
PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)EntityForm.getObjectForm();		
if(Util.empty(privilegeProjectCode)){
	privilegeProjectCode = new PrivilegeProjectCodeDataM();
}	
ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM>  projectRccList = privilegeProjectCode.getPrivilegeProjectCodePrjCdes();

String EDIT_FLAG = !Util.empty(projectRccList)?MConstant.FLAG_Y:MConstant.FLAG_N;
FormEffects formEffect = new FormEffects("VERIFY_PRIVILEGE_PROJECT_CODE_SUBFORM",request);
String BUTTON_PROCESS =HtmlUtil.button("EXCUTE_BTN", "EXCUTE_BTN", "EXECUTE_BTN", "btn btn-primary", "", formEffect);
					//HtmlUtil.button("EXCUTE_BTN", "EXECUTE_BTN", displayMode, "btn btn-primary",HtmlUtil.tagId("EXCUTE_BTN"), request);
if(EDIT_FLAG.equals(MConstant.FLAG_Y)){
	BUTTON_PROCESS = HtmlUtil.button("EDIT_BTN","EDIT_BTN", "EDIT_BTN", "btn btn-primary", "", formEffect);
					//HtmlUtil.button("EDIT_BTN", "EDIT_BTN", displayMode, "btn btn-primary",HtmlUtil.tagId("EDIT_BTN"), request);
}
%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6 col-md-offset-5">
				<div class="form-group">
					 <%=BUTTON_PROCESS%>
				</div>
			</div>
		</div>
	</div>
	<table class="table table-striped table-hover">
		<tr>
			<th><%=LabelUtil.getText(request,"PROJECT_CODE_BY_APP")%></th>
			<th><%=LabelUtil.getText(request,"PROJECT_CODE_SUGGESTION")%></th>
		</tr>
	<%String appProjectCode = applicationInfo.getProjectCode();%>
	<%if(Util.empty(appProjectCode) && Util.empty(projectRccList)){ %>
		<tr><td colspan="2" align="center"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td></tr>
	<%}else{%>
		<tr>
			<td><%if(!Util.empty(appProjectCode)){%>
				<div class="inboxitemcard"><%=FormatUtil.display(applicationInfo.getProjectCode())%>/<%=PrivilegeUtil.getCardTypeDesc(borrowerCard.getCardType())%></div>
			<%} %></td>
			<td>
			<%if(!Util.empty(projectRccList)){
				for(PrivilegeProjectCodeRccmdPrjCdeDataM projectRcc :projectRccList){%>
	  			<div class="inboxitemcard">
	  				<table>
	  					<tr>
	  						<td><%=HtmlUtil.radio("RCC_PROJECT_CODE", "", appProjectCode, projectRcc.getProjectCode(),  displayMode, "", "", request)%></td>
	  						<td><%=FormatUtil.display(projectRcc.getProjectCode())%>/<%=PrivilegeUtil.getCardTypeDesc(borrowerCard.getCardType())%></td>
	  					</tr>
	  				</table>	  				 
	  			</div>
			<%} 
			}%>
			</td>
		</tr>
	<%} %>
	</table>
</div>
 