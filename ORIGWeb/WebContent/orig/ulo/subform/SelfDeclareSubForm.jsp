<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl" %>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String subformId = "SELF_DECLARE_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId,request);
	String displayMode = HtmlUtil.EDIT;
	String actionType = flowControl.getActionType();
	if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",actionType)){
		displayMode = HtmlUtil.VIEW;
	}
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
%>
<div class="panel panel-default">
	<div class="panel-body">  		
		<div class="row form-horizontal">
			<div class="col-sm-12">
				<div class="form-group">
					<%=HtmlUtil.getLabel(request, "INCREASE_ISSUER_2_MONTH","col-sm-10 col-md-12 control-label")%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"NUMBER_OF_ISSUER","NUMBER_OF_ISSUER","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.numberBox("NUMBER_OF_ISSUER","NUMBER_OF_ISSUER","NUMBER_OF_ISSUER",FormatUtil.toBigDecimal(personalInfo.getNumberOfIssuer(),true),"","",true,"2","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"SELF_DECLARE_LIMIT","SELF_DECLARE_LIMIT","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.currencyBox("SELF_DECLARE_LIMIT","SELF_DECLARE_LIMIT","SELF_DECLARE_LIMIT",FormatUtil.toBigDecimal(personalInfo.getSelfDeclareLimit(),true),"",true,"12","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
		</div>
	</div>
</div>