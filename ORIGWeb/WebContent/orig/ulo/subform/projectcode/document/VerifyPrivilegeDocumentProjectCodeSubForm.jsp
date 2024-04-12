<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalRelationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.app.view.form.privilegeprojectcode.util.PrivilegeUtil"%>
<script type="text/javascript" src="orig\ulo\subform\projectcode\document\js\VerifyPrivilegeDocumentProjectCode.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
String SUB_FORM_ID="VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM";
String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
String[] FINAL_DECISION_EXCEPTION =    SystemConstant.getArrayConstant("PRVLG_FINAL_DECISION_EXCEPTION");

 Logger logger = Logger.getLogger(this.getClass());
String displayMode = HtmlUtil.EDIT;
int PRVLG_PROJECT_CODE_INDEX=0;
int PRVLG_PROJECT_DOC_INDEX=0;
String[]  documentTypeList =   ListBoxControl.searchListBox(SystemConstant.getConstant("FIELD_ID_PRVLG_DOCUMENT_TYPE"), "CHOICE_NO", request);

PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)EntityForm.getObjectForm();		
if(Util.empty(privilegeProjectCode)){
	privilegeProjectCode = new PrivilegeProjectCodeDataM();
}		
PrivilegeProjectCodeDocDataM  prvlgProjectDoc = privilegeProjectCode.getPrivilegeProjectCodeDoc(PRVLG_PROJECT_DOC_INDEX);
if(Util.empty(prvlgProjectDoc)){
	prvlgProjectDoc = new PrivilegeProjectCodeDocDataM();
}

String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
int PRVLG_APP_INDEX=0;
ApplicationGroupDataM applicationGroup =  ORIGForm.getObjectForm();

ApplicationDataM applicationInfo = applicationGroup.filterFirstApplicationFinalDecision(PRODUCT_CRADIT_CARD, new ArrayList<String>(Arrays.asList(FINAL_DECISION_EXCEPTION)));
if(Util.empty(applicationInfo)){
	applicationInfo = new ApplicationDataM();
}
 
CardDataM  borrowerCard  = applicationInfo.getCard();
if(Util.empty(borrowerCard)){
	borrowerCard = new  CardDataM();
}
PersonalRelationDataM personalRelation = applicationGroup.getPersonalRelation(applicationInfo.getApplicationRecordId(), PERSONAL_TYPE_APPLICANT, APPLICATION_LEVEL);
if(Util.empty(personalRelation)){
	personalRelation = new PersonalRelationDataM();
}
PersonalInfoDataM  borrowerPersonalInfo = applicationGroup.getPersonalInfoId(personalRelation.getPersonalId());
if(Util.empty(borrowerPersonalInfo)){
	borrowerPersonalInfo = new  PersonalInfoDataM();
}
String cardType=borrowerCard.getCardType();
FormUtil formUtil = new FormUtil(SUB_FORM_ID,request);
 %> 
<div class="panel panel-default">
	<div class="panel-body">	
<%
 	ElementInf applicantElement = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFICATION,"CUSTOMER_INFO_APPLICANT");
 	applicantElement.writeElement(pageContext,null);
 %>
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "CARD_TYPE", "col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7"><%=PrivilegeUtil.getCardTypeDesc(applicationGroup.getApplicationTemplate())%></div> 
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "PROJECT_CODE_DESC", "col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7"><%=FormatUtil.display(applicationInfo.getProjectCode())%></div> 
				</div>
			</div>
		</div>
		<div class="row form-horizontal documentTypeSection">
			<div class="col-sm-12">
	<%for(int i=0;i<documentTypeList.length;i++){ 
		String documentType =documentTypeList[i];
		//String addTags = " data-toggle=\"collapse\" data-parent=\".documentTypeSection\" aria-expanded=\"false\" aria-controls=\"" + element.getImplementId() + "\" ";
		ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.DOCUMENT_OF_PROJECT,"DOCUMENT_OF_PROJECT_"+documentType);
		%>
				
				<div class="row">
					<div class="col-sm-1 privilege-nopadding-right">
						<%=HtmlUtil.radio("DOCUMENT_TYPE", "", "DOCUMENT_TYPE", privilegeProjectCode.getProjectType(), "", documentType, HtmlUtil.elementTagId(documentType, "DOCUMENT_TYPE"), "", "", formUtil)%>
					</div>
					<div class="col-sm-11">		
						<% element.writeElement(pageContext,null);%>									
					</div>
				</div>
	<%	} %>
			</div>
		</div>
	</div>
</div>
<%=HtmlUtil.hidden("PROFESSION", borrowerPersonalInfo.getProfession()) %>
<%=HtmlUtil.hidden("CARD_TYPE", cardType)%>
<%=HtmlUtil.hidden("SELECTED_DOCUMENT_TYPE", "")%>