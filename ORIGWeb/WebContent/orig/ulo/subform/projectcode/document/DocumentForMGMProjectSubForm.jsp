<%@page import="com.eaf.core.ulo.security.exception.EncryptionException"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeMGMDocDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM"%>
<%@page import="com.eaf.core.ulo.security.encryptor.EncryptorFactory"%>
<%@page import="com.eaf.core.ulo.security.encryptor.Encryptor"%>

<script type="text/javascript" src="orig\ulo\subform\projectcode\document\js\DocumentForMGMProjectSubForm.js"></script>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<%
String subformId ="VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM";
String displayMode = HtmlUtil.EDIT;
int PRVLG_PROJECT_DOC_INDEX=0;
int PRVLG_PROJECT_DOC_MGM_INDEX=0;
	
PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)EntityForm.getObjectForm();	
if(Util.empty(privilegeProjectCode)){
	privilegeProjectCode = new PrivilegeProjectCodeDataM();
}	
	
PrivilegeProjectCodeDocDataM  prvlgProjectDoc = privilegeProjectCode.getPrivilegeProjectCodeDoc(PRVLG_PROJECT_DOC_INDEX);
if(Util.empty(prvlgProjectDoc)){
	prvlgProjectDoc = new PrivilegeProjectCodeDocDataM();
}
 
PrivilegeProjectCodeMGMDocDataM prvlgProjectMGM = prvlgProjectDoc.getPrivilegeProjectCodeMGMDoc(PRVLG_PROJECT_DOC_MGM_INDEX);
if(Util.empty(prvlgProjectMGM)){
	prvlgProjectMGM = new PrivilegeProjectCodeMGMDocDataM();
}
Encryptor dihEnc = EncryptorFactory.getDIHEncryptor();
String REFER_CARD_NO = null;
try {
	if(!Util.empty(prvlgProjectMGM.getReferCardNo())) {
		REFER_CARD_NO = dihEnc.decrypt(prvlgProjectMGM.getReferCardNo());
	}
} catch (EncryptionException e) {

}
FormUtil formUtil = new FormUtil(subformId,request);
 %>
 <div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-12 col-md-6">
				<div class="form-group">
					<div class="col-sm-12 bold"><label for="03_DOCUMENT_TYPE"><%=LabelUtil.getText(request, "DOCUMENT_FOR_MGM_PROJECT")%></label></div>
				</div>
			</div>
		</div>
		<div class="row form-horizontal element-body collapse" aria-expanded="false">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId, "REF_CREDIT_CARD_NO","REF_CREDIT_CARD_NO", "col-sm-4 col-md-5 control-label") %>
					<%=HtmlUtil.textBoxCardNo("REFER_CARD_NO", "", "", "REFER_CARD_NO", REFER_CARD_NO, "", "", "col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request, subformId,"CUSTOMER_CARD_NAME","CUSTOMER_CARD_NAME", "col-sm-4 col-md-5 control-label") %>
					<%=HtmlUtil.textBox("REFER_CARD_HOLDER_NAME", "", "REFER_CARD_HOLDER_NAME", prvlgProjectMGM.getReferCardHolderName(), "", "16", "", "col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>
			
		</div>
	</div>
</div>