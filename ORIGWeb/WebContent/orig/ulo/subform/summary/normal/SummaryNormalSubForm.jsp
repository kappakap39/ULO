<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.WorkFlowDecisionM"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="org.apache.log4j.Logger"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/summary/normal/js/SummaryNormalSubForm.js"></script>
<%
 Logger logger = Logger.getLogger(this.getClass());	
 ApplicationGroupDataM  applicationGroup =  ORIGForm.getObjectForm();
 String[] elementSubForm = SystemConstant.getArrayConstant("SUMMARY_NORMAL_ELEMENT");
 ArrayList<WorkFlowDecisionM>  workFlowDecisions = applicationGroup.getWorkFlowDecisions(); 
 String CALL_FICO_FLAG = applicationGroup.getCallFicoFlag();
%>
<%=HtmlUtil.hidden("CALL_FICO_FLAG",CALL_FICO_FLAG)%>
<%if(!Util.empty(workFlowDecisions)){
	for(WorkFlowDecisionM workFlowDecision :workFlowDecisions){%>
<div class="row">
	<div class="col-xs-12">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row form-horizontal">
					<div class="col-sm-12">
						<div class="form-group">
							<div class=" col-sm-12 col-md-12" style="font-weight: bold;"><%=FormatUtil.display(workFlowDecision.getDecisionDesc())%></div>
						</div>
					</div>
					<div class="col-sm-12">
						<div class="form-group">
							<div class="col-sm-12 col-md-12"><%=FormatUtil.display(workFlowDecision.getMessage())%></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%}
} %>
<%	for(int i=0;i<elementSubForm.length;i++){

		ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.SUMMARY_NORMAL,elementSubForm[i]);%>
		<%element.writeElement(pageContext,applicationGroup); %> 

<%} %>