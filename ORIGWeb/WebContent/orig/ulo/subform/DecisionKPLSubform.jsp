<%@page import="com.eaf.orig.ulo.model.app.PolicyRulesDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.app.ReasonDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="java.text.Normalizer.Form"%>
<%@page import="org.apache.tools.ant.taskdefs.Javadoc.Html"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript" src="orig/ulo/subform/js/DecisionSubform.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if(Util.empty(applicationGroup)){
		applicationGroup = new ApplicationGroupDataM();
	}	
	//String displayMode = HtmlUtil.EDIT;
	String FIELD_ID_POLICY_RULE_REASON =SystemConstant.getConstant("FIELD_ID_POLICY_RULE_REASON");
	String FIELD_ID_FICO_RECOMMEND_DECISION =SystemConstant.getConstant("FIELD_ID_FICO_RECOMMEND_DECISION");
	String CACHE_BUSINESS_CLASS =SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
	String CONFIG_ID_FINAL_DECISION = SystemConstant.getConstant("CONFIG_ID_FINAL_DECISION");
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	ApplicationDataM kplApplication=  applicationGroup.filterApplicationProductLifeCycle(MConstant.Product.K_PERSONAL_LOAN);	
	String KPLDesc = CacheControl.getName(CACHE_BUSINESS_CLASS, "BUS_CLASS_ID", kplApplication.getBusinessClassId(), "BUS_CLASS_DESC") ;
	int MAX_LIFE_CYCLE = applicationGroup.getMaxLifeCycleFromApplication(); 
	String subformId = request.getParameter("subFormId");
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel-heading"><%=LabelUtil.getText(request, "KPL")%></div>
	<table class="table table-striped table-hover">
		<tr>
			<th><%=LabelUtil.getText(request,"CA_PRODUCT_NAME") %></th>
			<th></th>
			<th></th>
			<th></th>	
			<th><%=LabelUtil.getText(request,"CA_RESULT_DECISION") %></th>		 
			<th><%=LabelUtil.getText(request,"REASON") %></th>
		</tr>
	<%if(!Util.empty(kplApplication)){
/* 		if(kplApplication.getLifeCycle() > 1){
			displayMode = HtmlUtil.EDIT;
		}else{
			String finalAppDicision = FormatUtil.display(kplApplication.getFinalAppDecision());
			if(SystemConstant.getConstant("REC_RESULT_REFER").equals(kplApplication.getRecommendDecision()) 
			&& !DECISION_FINAL_DECISION_CANCEL.toUpperCase().equals(finalAppDicision.toUpperCase())){
				displayMode = HtmlUtil.EDIT;
			}else{
				displayMode = HtmlUtil.VIEW;
			}
		} */
		LoanDataM loan = kplApplication.getLoan();	
		if(Util.empty(loan)) {
			loan = new  LoanDataM();
		}
		
		String KPL_FINAL_DECISION = "";
		if(DECISION_FINAL_DECISION_CANCEL.equals(kplApplication.getFinalAppDecision())){
			KPL_FINAL_DECISION = HtmlUtil.hidden("KPL_FINAL_RESULT_"+kplApplication.getApplicationRecordId(), kplApplication.getFinalAppDecision());
			KPL_FINAL_DECISION += CacheControl.getName(SystemConstant.getConstant("FIELD_ID_FINAL_RESULT"), kplApplication.getFinalAppDecision());			 
		}else{
			KPL_FINAL_DECISION = HtmlUtil.dropdown("KPL_FINAL_RESULT", kplApplication.getApplicationRecordId(), "KPL_FINAL_RESULT", "KPL_FINAL_RESULT", CONFIG_ID_FINAL_DECISION, kplApplication.getFinalAppDecision(), "", SystemConstant.getConstant("FIELD_ID_FINAL_RESULT"), "", "", "",kplApplication,formUtil);
		}
		Boolean loanAmtEmpty = Util.empty(loan.getLoanAmt());
		String loanAmt = (!loanAmtEmpty) ? FormatUtil.display(loan.getLoanAmt(),FormatUtil.Format.CURRENCY_FORMAT) : "-" ; 
		String installmentAmt = (!loanAmtEmpty) ? FormatUtil.display(loan.getInstallmentAmt(),FormatUtil.Format.CURRENCY_FORMAT) : "-" ; 
		String term = (!loanAmtEmpty) ? FormatUtil.display(loan.getTerm()) : "-" ; 
		String intRate = (!loanAmtEmpty) ? FormatUtil.display(loan.getInterestRate(),FormatUtil.Format.DECIMAL_TREE_POINT_FORMAT,false)+ LabelUtil.getText(request, "PERCENT") : "-" ; 
	%>
		<tr>
			<td rowspan="2"><div class="inboxitemcard"><%=FormatUtil.display(KPLDesc) %></div></td>
			<td rowspan="2"><div class="inboxitemcard">
				<table>
					<tr>
						<td><%=HtmlUtil.getFieldLabel(request, "REQ_AMT") %></td>
						<td class="align-right"><%=FormatUtil.display(loan.getRequestLoanAmt(),FormatUtil.Format.CURRENCY_FORMAT) %></td>
					</tr>
					<tr>
						<td><%=HtmlUtil.getFieldLabel(request, "REQ_TENOR") %></td>
						<td class="align-right"><%=FormatUtil.display(loan.getRequestTerm()) %></td>
					</tr>
				</table></div>				 
			</td>
			<td rowspan="2"><div class="inboxitemcard">
				<table>
					<tr>
						<td><%=HtmlUtil.getFieldLabel(request,"INSTALLMENT") %></td>
						<td class="align-right"><%=installmentAmt%></td>
					</tr>
					<tr>
						<td><%=HtmlUtil.getFieldLabel(request, "INT") %></td>
						<td class="align-right"><%=intRate%></td>
					</tr>
				</table></div>
			</td>
			<td rowspan="2"><div class="inboxitemcard">
				<table>
					<tr>	
						<td><%=HtmlUtil.getFieldLabel(request,"FINAL_AMT") %></td>
						<td class="align-right"><%=loanAmt%></td>
					</tr>
					<tr>
						<td><%=HtmlUtil.getFieldLabel(request,"FINAL_TENOR") %></td>
						<td class="align-right"><%=term%></td>
					</tr>
				</table>
			</div>
			</td>
			<td rowspan="2"><div class="inboxitemcard">
				<table>
					<tr>
						<td><%=HtmlUtil.getFieldLabel(request,"CA_REC") %></td>
						<td><%=CacheControl.getName(FIELD_ID_FICO_RECOMMEND_DECISION, kplApplication.getRecommendDecision())%><%=HtmlUtil.hidden("KPL_REC_DECISION_"+kplApplication.getApplicationRecordId(), kplApplication.getRecommendDecision())%></td>
					</tr>
					<tr>
						<td><%=HtmlUtil.getFieldLabel(request,"FINAL_CREDIT","col-sm-4 col-md-5") %></td>
						<td><%=KPL_FINAL_DECISION%></td>
					</tr>
				</table></div>
			</td>
			<td rowspan="2">
				<div class="inboxitemcard"> 
					<%VerificationResultDataM verResult= kplApplication.getVerificationResult();
					 	if(Util.empty(verResult)){
								verResult = new VerificationResultDataM();
			  				}
						ArrayList<PolicyRulesDataM> policyRules=verResult.getPolicyRules();
					%>
					<%=HtmlUtil.multipleSelectPolicyReason("KPL_REASON", "KPL_REASON", "KPL_REASON", "form-control col-sm-12 col-md-12 overflowdata-x", policyRules,FIELD_ID_POLICY_RULE_REASON,"-&nbsp;",formUtil) %>
				</div>
			</td>
		</tr>
	<% }else{ %>
		<tr>
			<td colspan="5" align="center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td>
		</tr>
	<%} %>
</table>