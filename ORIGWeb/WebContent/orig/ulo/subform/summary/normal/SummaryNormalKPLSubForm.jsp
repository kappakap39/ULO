<%@page import="java.util.Arrays"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalRelationDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.CashTransferDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanTierDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PaymentMethodDataM"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/summary/normal/js/SummaryNormalKPLSubForm.js"></script>
<%
 Logger logger = Logger.getLogger(this.getClass());
 String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");	
 String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
 String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
 String[] DECISION_FINAL_DECISION_CONDITION = SystemConstant.getArrayConstant("DECISION_FINAL_DECISION_CONDITION");
 ArrayList<String> FINAL_DECISION_CONDITION_LIST = new ArrayList<String>(Arrays.asList(DECISION_FINAL_DECISION_CONDITION));
 FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);	
  ApplicationGroupDataM  applicationGroup = ORIGForm.getObjectForm();
 if(Util.empty(applicationGroup)){
	applicationGroup = new ApplicationGroupDataM();
 }
 
ArrayList<ApplicationDataM> applications = applicationGroup.getApplicationsProduct(PRODUCT_K_PERSONAL_LOAN);
int MAX_LIFE_CYCLE = applicationGroup.getMaxLifeCycleFromApplication();
 %>
<%if(!Util.empty(applications)){%>
<%
 for(ApplicationDataM applicationItem :applications){
	if(MAX_LIFE_CYCLE==applicationItem.getLifeCycle() && !FINAL_DECISION_CONDITION_LIST.contains(applicationItem.getFinalAppDecision())){
	LoanDataM loan =  applicationItem.getLoan();
	if(Util.empty(loan)){
		loan = new LoanDataM();
	}
	CashTransferDataM cashTransfer = applicationItem.getCashTransfer(SystemConstant.getConstant("CALL_FOR_CASH"));
	if(Util.empty(cashTransfer)){
		cashTransfer = new  CashTransferDataM();
	}
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoByRelation(applicationItem.getApplicationRecordId());
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}
	
	//Add fecth payment method data for account no fields
	 PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalInfo.getPersonalId(),PRODUCT_K_PERSONAL_LOAN);
	 if(Util.empty(paymentMethod))
	 {
			paymentMethod = new PaymentMethodDataM();
	 }
	 
	 BigDecimal interestRate = BigDecimal.ZERO;
	 if(null != loan.getLoanTiers())
	 {
		for (LoanTierDataM loanTier : loan.getLoanTiers()) 
		{
			interestRate = loanTier.getIntRateAmount();
		}
	 }
	 
	 String accountNo = (Util.empty(paymentMethod.getAccountNo())) ? "         " : FormatUtil.display(paymentMethod.getAccountNo());
%>
<div class="panel panel-default">
<div class="panel-heading"><%=LabelUtil.getText(request, "SUMMARY_KPL_SUBFORM")%></div>
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-8">
				<div class="form-group">
					<div class="col-sm-4 col-md-5 text_left"><%=FormatUtil.display(personalInfo.getEnFirstName())%>&nbsp;<%=FormatUtil.display(personalInfo.getEnLastName())%></div>
					<div class="col-sm-8 col-md-7 text_left"><%=FormatUtil.display(personalInfo.getThFirstName())%>&nbsp;<%=FormatUtil.display(personalInfo.getThLastName())%></div>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-8">
				<div class="form-group">
					<%=HtmlUtil.getLabel(request, "KPL_FINAL_AMT","col-sm-4 col-md-5 ") %>		
					<div class="col-sm-8 col-md-7 text_left"><%=FormatUtil.display(loan.getLoanAmt(),FormatUtil.Format.CURRENCY_FORMAT)%></div>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-8">
				<div class="form-group">
					<%=HtmlUtil.getLabel(request, "KPL_FINAL_TERM","col-sm-4 col-md-5") %>	
					<div class="col-sm-8 col-md-7 text_left"><%=FormatUtil.display(loan.getTerm())%></div>
				</div>
			</div>
			<div class="clearfix"></div>
			</br>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getLabel(request, "KPL_PAYMENT_CONDITION","col-sm-12 col-md-12 ") %>		
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-8">
				<div class="form-group">
					<div class="col-sm-4 col-md-5 "><%=HtmlUtil.getText(request, "KPL_RATE") %></div>			
					<div class="col-sm-8 col-md-7 text_left"><%=FormatUtil.display(interestRate,FormatUtil.Format.CURRENCY_FORMAT,false)%><%=LabelUtil.getText(request, "PERCENT") %></div>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-8">
				<div class="form-group">
					<div class="col-sm-4 col-md-5 "><%=HtmlUtil.getText(request, "KPL_INSTALLMENT") %></div>			
					<div class="col-sm-8 col-md-7 text_left"><%=FormatUtil.display(loan.getInstallmentAmt(),FormatUtil.Format.CURRENCY_FORMAT)%></div>
				</div>
			</div>
			<div class="clearfix"></div>
			</br>
			<div class="col-sm-8">
				<div class="form-group">
					<%=HtmlUtil.getLabel(request, "LOAN_FINANCE","col-sm-4 col-md-5 ") %>
					<div name="ACCOUNT_NO" class="col-sm-6 col-md-5 text_left"><b><%=LabelUtil.getText(request, "ACC_NUM")%></b>&nbsp;&nbsp;&nbsp;<%=accountNo%></div>
				    <%=HtmlUtil.checkBoxInline("SAVING_PLUS", "", applicationItem.getSavingPlusFlag(), "", MConstant.FLAG.ACTIVE, HtmlUtil.VIEW, "", LabelUtil.getText(request, "SAVING_PLUS"), "", request)%>
				 </div>
			</div>
		</div>
	</div>
</div>
  <%}
  }%>
<%} %>