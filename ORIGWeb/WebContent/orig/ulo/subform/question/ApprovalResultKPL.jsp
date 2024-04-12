<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.RenderStyle"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");	
	String FIELD_ID_CONTACT_RESULT = SystemConstant.getConstant("FIELD_ID_CONTACT_RESULT");
	String FIELD_ID_DIFF_REQUEST_CONTACT_RESULT = SystemConstant.getConstant("FIELD_ID_DIFF_REQUEST_CONTACT_RESULT");
	String FIELD_ID_FINAL_RESULT = SystemConstant.getConstant("FIELD_ID_FINAL_RESULT");
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
	ArrayList<ApplicationDataM> applications = applicationGroup.filterFinalAppDecisionLifeCycle(PRODUCT_K_PERSONAL_LOAN);
	String subformId ="IDENTIFY_QUESTION_CUSTOMER_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId,request);
%>	
<%
if(!Util.empty(applications)){
	for(ApplicationDataM applicationData : applications){
		String applicationRecordId = applicationData.getApplicationRecordId();
		String requestLoanType = CacheControl.getName(SystemConstant.getConstant("CACHE_BUSINESS_CLASS"),applicationData.getBusinessClassId());
		String loanType = CacheControl.getName(SystemConstant.getConstant("CACHE_BUSINESS_CLASS"),applicationData.getBusinessClassId());
		String finalAppDecision = CacheControl.getName(FIELD_ID_FINAL_RESULT,applicationData.getFinalAppDecision(), "DISPLAY_NAME");
		LoanDataM loan = applicationData.getLoan();
%>
<tr class=<%=RenderStyle.getRecommendDecisionStyle(applicationData.getRecommendDecision())%>>
	<td><%=FormatUtil.display(requestLoanType)%><br>
		<%=LabelUtil.getText(request, "LOAN_AMOUNT_TEXT")%>&nbsp;
		<%=FormatUtil.display(loan.getRequestLoanAmt(),FormatUtil.Format.CURRENCY_FORMAT) %>/
		<%=FormatUtil.display(loan.getRequestTerm()) %>&nbsp;<%=LabelUtil.getText(request, "LOAN_TERM_TEXT")%>
	</td>
	<td>
		<div class="col-sm-8">
			<%=FormatUtil.display(loanType) %><br>
			<%=LabelUtil.getText(request, "LOAN_AMOUNT_TEXT")%>&nbsp;
			<%=FormatUtil.display(loan.getLoanAmt(),FormatUtil.Format.CURRENCY_FORMAT)%>/
			<%=FormatUtil.display(loan.getTerm()) %>&nbsp;<%=LabelUtil.getText(request, "LOAN_TERM_TEXT")%>
		</div>
		<div class="col-sm-4">
			<%=LabelUtil.getText(request, "INT_RATE") %>&nbsp;
			<%=FormatUtil.display(loan.getInterestRate(),FormatUtil.Format.CURRENCY_FORMAT,false)+LabelUtil.getText(request, "PERCENT")%><br>
			<%=LabelUtil.getText(request, "KPL_INSTALLMENT_TH") %>&nbsp;
			<%=FormatUtil.display(loan.getInstallmentAmt(),FormatUtil.Format.CURRENCY_FORMAT)%>
		</div>
	</td>
	<td><%=FormatUtil.display(finalAppDecision)%></td>
	<td>
		<%=HtmlUtil.dropdown("APPROVAL_RESULT", PRODUCT_K_PERSONAL_LOAN+"_"+applicationRecordId, "", "","", applicationData.getDiffRequestResult(), "", FIELD_ID_DIFF_REQUEST_CONTACT_RESULT, "", "", "", formUtil)%>
	</td>
</tr>
<%}%>
<%}else{%>
	<tr>
		<td colspan="4">
			<div class="col-sm-12">
				<div class="form-group" align="center"><%=HtmlUtil.getText(request, "NO_RECORD_FOUND")%></div>
			</div>
		</td>
	</tr>
<%}%>		
