<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductCCADataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<script type="text/javascript" src="orig\ulo\subform\projectcode\product\js\CCAInformationSubForm.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
String subformId="VERIFY_PRIVILEGE_PRODUCT_PROJECT_CODE_SUBFORM";
String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
String[] FINAL_DECISION_EXCEPTION =    SystemConstant.getArrayConstant("PRVLG_FINAL_DECISION_EXCEPTION");
String PROJECT_CODE_OF_AUM = SystemConstant.getConstant("PROJECT_CODE_OF_NO_DOC_07_WISDOM");
String displayMode = HtmlUtil.EDIT;
int PRVLG_PROJECT_PRODUCT_CCA_INDEX=0;
ApplicationGroupDataM applicationGroup =  ORIGForm.getObjectForm();

ApplicationDataM applicationInfo = applicationGroup.filterFirstApplicationFinalDecision(PRODUCT_CRADIT_CARD, new ArrayList<String>(Arrays.asList(FINAL_DECISION_EXCEPTION)));
if(Util.empty(applicationInfo)){
	applicationInfo = new ApplicationDataM();
}

PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)EntityForm.getObjectForm();		
if(Util.empty(privilegeProjectCode)){
	privilegeProjectCode = new PrivilegeProjectCodeDataM();
}

PrivilegeProjectCodeProductCCADataM prvlgProjectCodeProduct =	privilegeProjectCode.getPrivilegeProjectCodeProductCCAs(PRVLG_PROJECT_PRODUCT_CCA_INDEX);
if(Util.empty(prvlgProjectCodeProduct)){
	prvlgProjectCodeProduct = new PrivilegeProjectCodeProductCCADataM();
}
if(Util.empty(prvlgProjectCodeProduct.getCcaProduct())){
displayMode = HtmlUtil.VIEW;
}
FormUtil formUtil = new FormUtil(subformId,request);
String CCA_PRODUCT = prvlgProjectCodeProduct.getCcaProduct();
String CCA_RESULT_LABEL = HtmlUtil.getSubFormLabel(request, subformId, "CCA_RESULT", "CCA_RESULT","col-sm-4 col-md-5 control-label");
// if(!Util.empty(CCA_PRODUCT)){
// 	CCA_RESULT_LABEL = HtmlUtil.getMandatoryLabel(request, "CCA_RESULT", "col-sm-4 col-md-5 control-label");
// }
 %>
 <div class="panel panel-default">
	 <div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-md-12">
				<div class="form-group">
					<%=HtmlUtil.getLabel(request, "CCA_INFORMATION", "col-sm-4 col-md-5 control-label") %>		
				</div>
			</div>
			<div class="col-md-12">
				<div class="form-group">
					<div class="col-sm-6">
						<div class="form-group">	
							<%=HtmlUtil.getFieldLabel(request, "CCA_PRODUCT", "col-sm-4 col-md-5 control-label") %>
							 <div class="col-sm-8 col-md-7"><%=FormatUtil.display(CCA_PRODUCT)%><%=HtmlUtil.hidden("CCA_PRODUCT", CCA_PRODUCT) %></div>
						</div>
					</div>	
					<div class="col-sm-6">
						<div class="form-group">	
							<%=CCA_RESULT_LABEL%>
							<%=HtmlUtil.dropdown("RESULT", "", "RESULT", "", prvlgProjectCodeProduct.getResult(), "", SystemConstant.getConstant("FIELD_ID_PRVLG_RESULT"), "", "", "col-sm-8 col-md-7", formUtil)%>
						</div>
					</div>	
					
					<% if(PROJECT_CODE_OF_AUM.equals(applicationInfo.getProjectCode())){ %>
					<div class="col-sm-12">
						<div class="form-group">	
							<%=HtmlUtil.getText(request, "CCA_AUM_DESC", "col-sm-4 col-md-12 control-label","margin-top:-20px") %>
						</div>
					</div>
					<% } %>
				</div>
			</div>
		</div>
	</div>
</div>