<%@page import="com.eaf.orig.ulo.control.util.ApplicationUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.sql.Date" %>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler" />
<script type="text/javascript" src="orig/ulo/subform/js/ApplicationInfoSubForm.js"></script>
<%
	String subformId = "APPLICATION_INFO_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if (null == applicationGroup) {
		applicationGroup = new ApplicationGroupDataM();
	}	
	PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String PERSONAL_TYPE = personalInfo.getPersonalType();
	
	String displayMode = HtmlUtil.EDIT;
	String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");
	String SEARCH_BRANCH_INFO = SystemConstant.getConstant("SEARCH_BRANCH_INFO");
	String CACHE_APPLY_CHANNEL = SystemConstant.getConstant("CACHE_APPLY_CHANNEL");
	
	FormUtil formUtil = new FormUtil(subformId,request);
	
	Date ApplyDate = applicationGroup.getApplyDate();
	if(Util.empty(ApplyDate)){
		ApplyDate = applicationGroup.getApplicationDate();
	}
	
	boolean isKPL = KPLUtil.isKPL(applicationGroup);
	String appGroupNo = ApplicationUtil.getApplicationGroupNo(applicationGroup);
%>
<div class="panel panel-default">
<div class="panel-body">
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "APPLICATION_NO", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=FormatUtil.display(appGroupNo)%></div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "APPLICATION_TEMPLATE", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=CacheControl.getName(CACHE_TEMPLATE, applicationGroup.getApplicationTemplate(), request)%></div>
		</div>
			<%=HtmlUtil.hidden("APPLICATION_TEMPLATE_ID", applicationGroup.getApplicationTemplate()) %>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "BRANCH_NO", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=FormatUtil.displayText(applicationGroup.getBranchNo())+" : "+FormatUtil.displayText(applicationGroup.getBranchName())%></div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "APPLY_CHANNEL", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=CacheControl.getName(CACHE_APPLY_CHANNEL, applicationGroup.getApplyChannel(), request)%></div>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
		<!-- Fix for suppost SmartData -->
			<%=HtmlUtil.getSubFormLabel(request,subformId, "APPLY_DATE", "APPLY_DATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("APPLY_DATE", "APPLY_DATE", "APPLY_DATE", 
				ApplyDate, "", "", HtmlUtil.TH, "col-sm-8 col-md-7", formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "SCAN_DATE", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=FormatUtil.display(applicationGroup.getApplicationDate(), FormatUtil.TH, FormatUtil.Format.ddMMyyyy)%></div>
			<%=HtmlUtil.hidden("SCAN_DATE" ,FormatUtil.display(applicationGroup.getApplicationDate(), FormatUtil.TH, FormatUtil.Format.ddMMyyyy)) %>
		</div>
	</div>
	<div class="clearfix"></div>
	<% if(isKPL){%>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "CLAIM_SETUP_STATUS", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=FormatUtil.display(KPLUtil.getAccountSetupClaimStatus(applicationGroup.getApplicationGroupNo()))%></div>
		</div>
	</div>
	<% } %>
</div>
</div>
</div>