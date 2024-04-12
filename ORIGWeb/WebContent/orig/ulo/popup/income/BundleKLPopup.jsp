<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.BundleKLDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/income/js/BundleKLPopup.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	BundleKLDataM bundleKL = (BundleKLDataM)ModuleForm.getObjectForm();
	
	String subformId = "BUNDLING_KL_POPUP";
	FormUtil formUtil = new FormUtil(subformId, request);
%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"VERIFIED_INCOME_KL","VERIFIED_INCOME_KL","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.currencyBox("VERIFIED_INCOME_KL","VERIFIED_INCOME_KL","",bundleKL.getVerifiedIncome(),"",true,"15","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"VERIFIED_DATE","VERIFIED_DATE","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.calendar("VERIFIED_DATE","VERIFIED_DATE","VERIFIED_DATE",bundleKL.getVerifiedDate(),"","",HtmlUtil.TH,"col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"ESTIMATED_INCOME","ESTIMATED_INCOME","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.currencyBox("ESTIMATED_INCOME","ESTIMATED_INCOME","",bundleKL.getEstimated_income(),"",true,"15","readonly","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
		</div>
	</div>
</div>