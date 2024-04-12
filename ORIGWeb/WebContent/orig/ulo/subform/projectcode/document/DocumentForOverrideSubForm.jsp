 <%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeExceptionDocDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
 <%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<script type="text/javascript" src="orig\ulo\subform\projectcode\document\js\DocumentForOverrideSubForm.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<%
String subformId ="VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM";
String displayMode = HtmlUtil.EDIT;
int PRVLG_PROJECT_DOC_INDEX=0;
int PROJECT_DOC_EXC_INDEX=0;

PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)EntityForm.getObjectForm();		
if(Util.empty(privilegeProjectCode)){
	privilegeProjectCode = new PrivilegeProjectCodeDataM();
}
	
 PrivilegeProjectCodeDocDataM  prvlgProjectDoc = privilegeProjectCode.getPrivilegeProjectCodeDoc(PRVLG_PROJECT_DOC_INDEX);
if(Util.empty(prvlgProjectDoc)){
	prvlgProjectDoc = new PrivilegeProjectCodeDocDataM();
}

PrivilegeProjectCodeExceptionDocDataM  prvlgProjectException =prvlgProjectDoc.getPrivilegeProjectCodeExceptionDoc(PROJECT_DOC_EXC_INDEX);
if(Util.empty(prvlgProjectException)){
	prvlgProjectException  = new PrivilegeProjectCodeExceptionDocDataM();
}
FormUtil formUtil = new FormUtil(subformId,request);
 %>
 <div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-12">
				<div class="form-group">
					<div class="col-sm-12 bold"><label for="04_DOCUMENT_TYPE"><%=LabelUtil.getText(request, "DOCUMENT_FOR_OVERRIDE_MESSAGE")%></label></div>
				</div>
			</div>
		</div>
		<div class="row form-horizontal element-body collapse" aria-expanded="false">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request, subformId, "UNDER_STANDART", "UNDER_STANDART","col-sm-4 col-md-5 control-label") %>
					<%=HtmlUtil.dropdown("EXCEPT_POLICY", "", "EXCEPT_POLICY", "", prvlgProjectException.getExceptPolicy(), "", SystemConstant.getConstant("FIELD_ID_PRVLG_EXCEPT_POLICY"), "", "", "col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId, "OTHER_UNDER_STANDART","OTHER_UNDER_STANDART", "col-sm-4 col-md-5 control-label") %>
					<%=HtmlUtil.textBox("EXCEPT_POLICY_OTH", "", "EXCEPT_POLICY_OTH", prvlgProjectException.getExceptPolicyOth(), "", "100", "", "col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId, "DEFERENCE_DOCUMENT","DEFERENCE_DOCUMENT", "col-sm-4 col-md-5 control-label") %>
					<%=HtmlUtil.dropdown("EXCEPT_DOC_FROM", "", "EXCEPT_DOC_FROM", "", prvlgProjectException.getExceptDocFrom(), "", SystemConstant.getConstant("FIELD_ID_PRVLG_EXCEPT_DOC_FROM"), "", "", "col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>
		</div>
	</div>
</div>