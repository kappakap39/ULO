<%@page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript" src="orig/ulo/subform/js/FraudInfoSubForm.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<%
    String  FRAUD = SystemConstant.getConstant("FRAUD_FLAG");
    String  NO_FRAUD = SystemConstant.getConstant("NO_FRAUD");
    String  RETURN = SystemConstant.getConstant("RETURN");
    FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String ACTION_TYPE = flowControl.getActionType();
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM   applicationGroup = ORIGForm.getObjectForm();
	if(Util.empty(applicationGroup)){
		applicationGroup =  new ApplicationGroupDataM();
	}
	
	String displayMode = HtmlUtil.EDIT;
	if(OrigConstant.SEARCH_TYPE_ENQUIRY.equals(ACTION_TYPE)){
		displayMode = HtmlUtil.DISABLED;
	}
	String subform ="FRAUD_INFO_SUBFORM";
 	FormUtil formUtil = new FormUtil(subform,request);
 	if(Util.empty(applicationGroup.getFraudFinalDecision()) || !FRAUD.equals(applicationGroup.getFraudFinalDecision())){
 		applicationGroup.setFraudApplicantFlag("");
 		applicationGroup.setFraudCompanyFlag("");
 	}
%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-12">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subform,"FRAUD_REMARK","FRAUD_REMARK","col-sm-4 col-md-3 control-label")%>
					<%=HtmlUtil.textarea("FRAUD_REMARK", "", "", applicationGroup.getFraudRemark(), "10", "100", "1000",  HtmlUtil.elementTagId("FRAUD_REMARK"), "col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>	
			<div class="col-sm-12">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subform,"FINAL_DECISION","FINAL_DECISION","col-sm-4 col-md-3 control-label")%>
					<div class="col-sm-3">
						<%=HtmlUtil.radioInline("FINAL_DECISION", "", "", applicationGroup.getFraudFinalDecision(), "", FRAUD, HtmlUtil.elementTagId("FRAUD"), "FRAUD", "col-sm-12", formUtil)%>
						<div class="row form-horizontal">
							<div class="col-sm-3"></div>
							<%=HtmlUtil.checkBoxInline("FRAUD_APP_FLAG", "", applicationGroup.getFraudApplicantFlag(), SystemConstant.getConstant("FLAG_YES"), displayMode, "", "Applicant Fraud", "col-sm-9", request) %>
						</div>
						<div class="row form-horizontal">
							<div class="col-sm-3"></div>
							<%=HtmlUtil.checkBoxInline("FRAUD_COMPANY_FLAG", "", applicationGroup.getFraudCompanyFlag(), SystemConstant.getConstant("FLAG_YES"), displayMode, "", "Company Fraud", "col-sm-9", request) %>
						</div>
					</div>
					<div class="col-sm-3">		
						<%=HtmlUtil.radioInline("FINAL_DECISION", "", "", applicationGroup.getFraudFinalDecision(), "", NO_FRAUD, HtmlUtil.elementTagId("NO_FRAUD"), "NO_FRAUD", "col-sm-12", formUtil)%>	
					</div>
					<div class="col-sm-3">
						<%=HtmlUtil.radioInline("FINAL_DECISION", "", "", applicationGroup.getFraudFinalDecision(), "", RETURN, HtmlUtil.elementTagId("RETURN_FRAUD"), "RETURN_FRAUD", "col-sm-12", formUtil)%>	
					</div>	
				</div>
			</div>	
		</div>
	</div>
</div>s