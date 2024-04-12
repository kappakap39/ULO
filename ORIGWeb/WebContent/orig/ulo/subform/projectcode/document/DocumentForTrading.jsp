<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductTradingDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<script type="text/javascript" src="orig\ulo\subform\projectcode\document\js\DocumentForTrading.js"></script>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<%
String subformId ="VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM";
String displayMode = HtmlUtil.EDIT;

int PRVLG_PROJECT_DOC_INDEX=0;
int PROJECT_DOC_ASSET_INDEX=0;
	
PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)EntityForm.getObjectForm();		
if(Util.empty(privilegeProjectCode)){
	privilegeProjectCode = new PrivilegeProjectCodeDataM();
}	
 PrivilegeProjectCodeDocDataM  prvlgProjectDoc = privilegeProjectCode.getPrivilegeProjectCodeDoc(PRVLG_PROJECT_DOC_INDEX);
if(Util.empty(prvlgProjectDoc)){
	prvlgProjectDoc = new PrivilegeProjectCodeDocDataM();
}
	
PrivilegeProjectCodeProductTradingDataM	prvlgProjectCodeTrading = prvlgProjectDoc.getPrivilegeProjectCodeProductTradings(PROJECT_DOC_ASSET_INDEX);
if(Util.empty(prvlgProjectCodeTrading)){
	prvlgProjectCodeTrading = new PrivilegeProjectCodeProductTradingDataM();
}

FormUtil formUtil = new FormUtil(subformId,request);
%>


<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-12 col-md-6">
				<div class="form-group">
					<div class="col-sm-12 bold"><label for="09_DOCUMENT_TYPE"><%=LabelUtil.getText(request, "DOCUMENT_FOR_TRADING")%></label></div>
				</div>
			</div>
		</div>
		<div class="row form-horizontal element-body collapse" aria-expanded="false">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId, "DOCUMENT_FOR_TRADING_BATH","DOCUMENT_FOR_TRADING_BATH", "col-sm-4 col-md-5 control-label") %>
					<%=HtmlUtil.currencyBox("DOCUMENT_FOR_TRADING_BATH", "", "DOCUMENT_FOR_TRADING_BATH", "DOCUMENT_FOR_TRADING_BATH", FormatUtil.toBigDecimal(prvlgProjectCodeTrading.getPropertyValue(), true), "", true, "15", "", "col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>
		</div>
	</div>
</div>