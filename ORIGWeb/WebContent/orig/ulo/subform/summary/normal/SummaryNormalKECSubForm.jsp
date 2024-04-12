<%@page import="java.util.Arrays"%>
<%@page import="com.eaf.orig.ulo.model.app.PaymentMethodDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalRelationDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.CashTransferDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/summary/normal/js/SummaryNormalKECSubForm.js"></script>
<%
 Logger logger = Logger.getLogger(this.getClass());
 String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
 String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
 String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
 String FIELD_ID_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
 String PAYMENT_METHOD_DEPOSIT_ACCOUNT = SystemConstant.getConstant("PAYMENT_METHOD_DEPOSIT_ACCOUNT");
 String FIELD_ID_PAYMENT_RATIO = SystemConstant.getConstant("FIELD_ID_PAYMENT_RATIO");
 String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
 String[] DECISION_FINAL_DECISION_CONDITION = SystemConstant.getArrayConstant("DECISION_FINAL_DECISION_CONDITION");
 ArrayList<String> FINAL_DECISION_CONDITION_LIST = new ArrayList<String>(Arrays.asList(DECISION_FINAL_DECISION_CONDITION));
 FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);	
  ApplicationGroupDataM  applicationGroup = ORIGForm.getObjectForm();
 if(Util.empty(applicationGroup)){
	applicationGroup = new ApplicationGroupDataM();
 }
  int MAX_LIFE_CYCEL = applicationGroup.getMaxLifeCycleFromApplication();
ArrayList<ApplicationDataM> applications = applicationGroup.getApplicationsProduct(PRODUCT_K_EXPRESS_CASH);
 %>
<%if(!Util.empty(applications)){%>
	<%
		for(ApplicationDataM applicationItem :applications){
		if(MAX_LIFE_CYCEL==applicationItem.getLifeCycle() && !FINAL_DECISION_CONDITION_LIST.contains(applicationItem.getFinalAppDecision())){
			CardDataM card =  applicationItem.getCard();
			if(Util.empty(card)){
				card = new CardDataM();
			}
			LoanDataM loan =  applicationItem.getLoan();
			if(Util.empty(loan)){
				loan = new LoanDataM();
			}
			CashTransferDataM  cashDay1Transfer = applicationItem.getCashTransfer(SystemConstant.getConstant("CASH_DAY_1"));
			if(Util.empty(cashDay1Transfer)){
				cashDay1Transfer = new CashTransferDataM();
			}
			CashTransferDataM callForcashTransfer = loan.getCashTransfer(SystemConstant.getConstant("CALL_FOR_CASH"));
			if(Util.empty(callForcashTransfer)){
				callForcashTransfer = new  CashTransferDataM();
			}
			
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoRelation(applicationItem.getApplicationRecordId(), PERSONAL_TYPE_APPLICANT, APPLICATION_LEVEL);
			if(Util.empty(personalInfo)){
				personalInfo = new PersonalInfoDataM();
			}
			PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalInfo.getPersonalId(), PRODUCT_K_EXPRESS_CASH);
			if(Util.empty(paymentMethod)){
				paymentMethod = new PaymentMethodDataM();
			}			
			String cardLevel=CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",card.getCardType(), "CARD_LEVEL");
		    String cardCode=CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",card.getCardType(), "CARD_CODE");
		
	%>
		<div class="panel panel-default">
			 <div class="panel-heading"><%=LabelUtil.getText(request, "SUMMARY_NORMAL_KEC_SUBFORM")%></div>
				<div class="panel-body">
					<div class="row form-horizontal">
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-12 col-md-12 "><%=FormatUtil.display(personalInfo.getEnFirstName())%>&nbsp;<%=FormatUtil.display(personalInfo.getEnLastName())%></div>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-12 col-md-12 "><%=CacheControl.getName(FIELD_ID_CARD_TYPE, cardCode)%></div>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-8 col-md-7 "><%=FormatUtil.display(loan.getLoanAmt(),FormatUtil.Format.CURRENCY_FORMAT)%></div>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<%=HtmlUtil.getLabel(request,"PAYMENT","col-sm-12 col-md-12")%>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-4 col-md-5"><%=HtmlUtil.getText(request,"ACCOUNT_NUMBER")%></div>
								<div class="col-sm-8 col-md-7 "><%=FormatUtil.getAccountNo(paymentMethod.getAccountNo())%></div>	
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-4 col-md-5"><%=HtmlUtil.getText(request,"ACC_ENG_NAME")%></div>
								<div class="col-sm-8 col-md-7 "><%=FormatUtil.display(paymentMethod.getAccountName())%></div>	
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-4 col-md-5"><%=HtmlUtil.getText(request,"PAY_COND")%></div>
								<div class="col-sm-8 col-md-7 "><%=CacheControl.getName(FIELD_ID_PAYMENT_RATIO,FormatUtil.display(paymentMethod.getPaymentRatio()))%></div>	
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-sm-6">
							<div class="form-group">
								<%=HtmlUtil.getLabel(request,"KEC_CASH_DAY1","col-sm-12 col-md-12")%>
							</div>
						</div>	
						<div class="clearfix"></div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-4 col-md-5"><%=HtmlUtil.getText(request, "MONEY_TRANFERING")%></div>
								<div class="col-sm-8 col-md-7 "><%=FormatUtil.display(cashDay1Transfer.getPercentTransfer()) %></div>					 
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-8 col-md-7"><%=HtmlUtil.getText(request,"ACCOUNT_NUMBER")%></div>
								<div class="col-sm-4 col-md-5 "><%=FormatUtil.display(cashDay1Transfer.getTransferAccount())%></div>	
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-8 col-md-7"><%=HtmlUtil.getText(request,"ACC_ENG_NAME")%></div>
								<div class="col-sm-4 col-md-5 "><%=FormatUtil.display(cashDay1Transfer.getAccountName())%></div>		
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-sm-6">
							<div class="form-group">
								<%=HtmlUtil.getLabel(request,"KEC_CALL_FOR_CASH","col-sm-12 col-md-12")%>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-4 col-md-5"><%=HtmlUtil.getText(request, "ACCOUNT_NUMBER")%></div>
								<div class="col-sm-8 col-md-7 "><%=FormatUtil.display(callForcashTransfer.getTransferAccount()) %></div>					 
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-4 col-md-5"><%=HtmlUtil.getText(request, "ACC_ENG_NAME")%></div>
								<div class="col-sm-8 col-md-7 "><%=FormatUtil.display(callForcashTransfer.getAccountName()) %></div>					 
							</div>
						</div>
					</div>
				</div>
			</div>
 	 <% }
  	}
}%>