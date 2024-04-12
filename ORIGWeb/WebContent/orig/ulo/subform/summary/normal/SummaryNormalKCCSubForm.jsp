<%@page import="java.util.Arrays"%>
<%@page import="com.eaf.orig.ulo.model.app.PaymentMethodDataM"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalRelationDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/summary/normal/js/SummaryNormalKCCSubForm.js"></script>
<%
 Logger logger = Logger.getLogger(this.getClass());
 String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
 String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
 String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
 String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
 String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
 String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
 String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
 String SPECIAL_ADDITIONAL_SERVICE_ATM = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_ATM");
 String PAYMENT_METHOD_DEPOSIT_ACCOUNT = SystemConstant.getConstant("PAYMENT_METHOD_DEPOSIT_ACCOUNT");
 String[] DECISION_FINAL_DECISION_CONDITION = SystemConstant.getArrayConstant("DECISION_FINAL_DECISION_CONDITION");
 ArrayList<String> FINAL_DECISION_CONDITION_LIST = new ArrayList<String>(Arrays.asList(DECISION_FINAL_DECISION_CONDITION));
 FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);	
 ApplicationGroupDataM  applicationGroup =  ORIGForm.getObjectForm();
 
 int MAX_LIFE_CYCEL = applicationGroup.getMaxLifeCycleFromApplication();
 if(Util.empty(applicationGroup)){
	applicationGroup = new ApplicationGroupDataM();
 }
 ArrayList<ApplicationDataM> borrowerApplications = applicationGroup.filterApplicationCardType(PRODUCT_CRADIT_CARD,APPLICATION_CARD_TYPE_BORROWER);	
 PersonalInfoDataM personalInfoApplicant = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
 if(Util.empty(personalInfoApplicant)){
 	personalInfoApplicant = new PersonalInfoDataM();
 }
 ArrayList<ApplicationDataM> supplemantaryApplications  = new ArrayList<ApplicationDataM>();
 PersonalInfoDataM personalInfoSupplementary = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
 if(Util.empty(personalInfoSupplementary)){
 	personalInfoSupplementary  = new PersonalInfoDataM();
 }
%>
<%if(!Util.empty(borrowerApplications)) {%>
<div class="panel panel-default">
<div class="panel-heading"><%=LabelUtil.getText(request, "SUMMARY_NORMAL_KCC_SUBFORM")%></div>
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-xs-1 screen-width-auto">
				<div class="col-sm-6">
					<div class="form-group">
						<%=HtmlUtil.getLabel(request,"DECISION_MAIN_CARD", "col-sm-12 col-md-12")%>
					</div>
				</div>
				<div class="clearfix"></div>				
				<div class="col-sm-6">
					<div class="form-group">
						<div class="col-sm-12 col-md-12 "><%=FormatUtil.display(personalInfoApplicant.getEnFirstName())%>&nbsp;<%=FormatUtil.display(personalInfoApplicant.getEnLastName())%></div>						
					</div>
				</div>
				<div class="clearfix"></div>
			<%for(ApplicationDataM borrowerItem:borrowerApplications){
				if(MAX_LIFE_CYCEL==borrowerItem.getLifeCycle() && !FINAL_DECISION_CONDITION_LIST.contains(borrowerItem.getFinalAppDecision())){
					String borrowerApplicationRecordId = borrowerItem.getApplicationRecordId();
					String borrowerMainCardRecordId = borrowerItem.getMaincardRecordId();
					CardDataM borrowerCard = borrowerItem.getCard();
					if(Util.empty(borrowerCard)){
						borrowerCard = new CardDataM();
					}
					LoanDataM borrowerLoan = borrowerItem.getLoan();
					if(Util.empty(borrowerLoan)){
						borrowerLoan = new LoanDataM();
					}
					String borrowerCardLevel=CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",borrowerCard.getCardType(), "CARD_LEVEL");
					String borrowerCardCode=CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",borrowerCard.getCardType(), "CARD_CODE");
					
					ArrayList<ApplicationDataM> supplemantaryApp = applicationGroup.filterMaincardRecordIdCardType(borrowerMainCardRecordId,APPLICATION_CARD_TYPE_SUPPLEMENTARY);
					if(!Util.empty(supplemantaryApp)) {
							supplemantaryApplications.addAll(supplemantaryApp);									
					}
			%>
					<div class="col-sm-6">
						<div class="form-group">
							<div class="col-sm-12 col-md-12 "><%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_CARD_TYPE"), borrowerCardCode)%></div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<div class="col-sm-8 col-md-7"><%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_CARD_LEVEL"),borrowerCardLevel)%></div>
							<div class="col-sm-4 col-md-5"><%=FormatUtil.display(borrowerLoan.getLoanAmt(),FormatUtil.Format.CURRENCY_FORMAT)%></div>
						</div>
					</div>										 
				<%}
			 }%>
			   <div class="clearfix"></div>
			<%  PaymentMethodDataM paymentMethodApplicantDataM = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalInfoApplicant.getPersonalId(), PRODUCT_CRADIT_CARD);
				 if(Util.empty(paymentMethodApplicantDataM)){
				 	paymentMethodApplicantDataM = new PaymentMethodDataM();
				 }
				 SpecialAdditionalServiceDataM applicantAdditionNal =applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalInfoApplicant.getPersonalId(), PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_ATM);
				 if(Util.empty(applicantAdditionNal)){
				 	applicantAdditionNal = new SpecialAdditionalServiceDataM();
				 }%>	
								
				   <div class="col-sm-6">
						<div class="form-group">
							<%=HtmlUtil.getLabel(request,"PAYMENT", "col-sm-12 col-md-12")%>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-sm-6">
						<div class="form-group">
							<div class="col-sm-8 col-md-7"><%=HtmlUtil.getText(request,"ACCOUNT_NUMBER")%></div>
							<div class="col-sm-4 col-md-5 "><%=FormatUtil.getAccountNo(paymentMethodApplicantDataM.getAccountNo())%></div>	
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<div class="col-sm-8 col-md-7"><%=HtmlUtil.getText(request,"ACC_ENG_NAME")%></div>
							<div class="col-sm-4 col-md-5 "><%=FormatUtil.display(paymentMethodApplicantDataM.getAccountName())%></div>		
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<div class="col-sm-8 col-md-7"><%=HtmlUtil.getText(request,"PAY_COND")%></div>
							<div class="col-sm-4 col-md-5 "><%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_PAYMENT_RATIO"),FormatUtil.display(paymentMethodApplicantDataM.getPaymentRatio()))%></div>	
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-sm-6">
						<div class="form-group">
							<%=HtmlUtil.getLabel(request,"ATM", "col-sm-12 col-md-12")%>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-sm-6">
						<div class="form-group">
							<div class="col-sm-4 col-md-5"><%=HtmlUtil.getText(request,"CA")%></div>
							<div class="col-sm-8 col-md-7"><%=FormatUtil.display(applicantAdditionNal.getCurrentAccNo())%></div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<div class="col-sm-8 col-md-7"><%=HtmlUtil.getText(request,"ACC_ENG_NAME")%></div>
							<div class="col-sm-4 col-md-5"><%=FormatUtil.display(applicantAdditionNal.getCurrentAccName())%></div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<div class="col-sm-4 col-md-5"><%=HtmlUtil.getText(request,"SA")%></div>
							<div class="col-sm-8 col-md-7"><%=FormatUtil.display(applicantAdditionNal.getSavingAccNo())%></div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<div class="col-sm-8 col-md-7"><%=HtmlUtil.getText(request,"ACC_ENG_NAME")%></div>
							<div class="col-sm-4 col-md-5"><%=FormatUtil.display(applicantAdditionNal.getSavingAccName())%></div>
						</div>
					</div>				
				</div>						
		<%if(!Util.empty(supplemantaryApplications)) {%>
			  <div class="col-xs-1 screen-width-auto">		
					<div class="col-sm-6">
						<div class="form-group">
							<%=HtmlUtil.getLabel(request,"DECISION_SUP_CARD", "col-sm-12 col-md-12")%>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-sm-6">
						<div class="form-group">
							<div class="col-sm-12 col-md-12 "><%=FormatUtil.display(personalInfoSupplementary.getEnFirstName())%>&nbsp;<%=FormatUtil.display(personalInfoSupplementary.getEnLastName()) %></div>						
						</div>
					</div>
					<div class="clearfix"></div>	
						<%
							for(ApplicationDataM supplemantaryItem :supplemantaryApplications){
								if(MAX_LIFE_CYCEL==supplemantaryItem.getLifeCycle() && !FINAL_DECISION_CONDITION_LIST.contains(supplemantaryItem.getFinalAppDecision())){
								CardDataM supplemantaryCard = supplemantaryItem.getCard();
								if(Util.empty(supplemantaryCard)){
									supplemantaryCard =  new CardDataM();
								}
								LoanDataM supplemantaryLoan = supplemantaryItem.getLoan();
								if(Util.empty(supplemantaryLoan)){
									supplemantaryLoan = new LoanDataM();
								}
								String supplemantaryCardLevel=CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",supplemantaryCard.getCardType(), "CARD_LEVEL");
								String supplemantaryCardCode=CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",supplemantaryCard.getCardType(), "CARD_CODE");
					    %>	
	
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-12 col-md-12 "><%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_CARD_TYPE"), supplemantaryCardCode)%></div>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-8 col-md-7"><%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_CARD_LEVEL"),supplemantaryCardLevel)%></div>
								<div class="col-sm-4 col-md-5"><%=FormatUtil.display(supplemantaryLoan.getLoanAmt(),FormatUtil.Format.CURRENCY_FORMAT) %></div>
							</div>
						</div>	
						<%}
					}%>
					<div class="clearfix"></div>
					<%  
						  PaymentMethodDataM paymentMethodSupplementaryDataM = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalInfoSupplementary.getPersonalId(), PRODUCT_CRADIT_CARD);
						 if(Util.empty(paymentMethodSupplementaryDataM)){
						 	paymentMethodSupplementaryDataM = new PaymentMethodDataM();
						 }
						  SpecialAdditionalServiceDataM supplementaryAdditionNal =applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalInfoSupplementary.getPersonalId(), PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_ATM);
						 if(Util.empty(supplementaryAdditionNal)){
						 	supplementaryAdditionNal = new SpecialAdditionalServiceDataM();
						 }
						 %>	
								
					<div class="col-sm-6">
						<div class="form-group">
							<%=HtmlUtil.getLabel(request,"PAYMENT", "col-sm-12 col-md-12")%>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-sm-6">
						<div class="form-group">
							<div class="col-sm-8 col-md-7"><%=HtmlUtil.getText(request,"ACCOUNT_NUMBER")%></div>
							<div class="col-sm-4 col-md-5 "><%=FormatUtil.getAccountNo(paymentMethodSupplementaryDataM.getAccountNo())%></div>	
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<div class="col-sm-8 col-md-7"><%=HtmlUtil.getText(request,"ACC_ENG_NAME")%></div>
							<div class="col-sm-4 col-md-5 "><%=FormatUtil.display(paymentMethodSupplementaryDataM.getAccountName())%></div>		
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<div class="col-sm-8 col-md-7"><%=HtmlUtil.getText(request,"PAY_COND")%></div>
							<div class="col-sm-4 col-md-5 "><%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_PAYMENT_RATIO"),FormatUtil.display(paymentMethodSupplementaryDataM.getPaymentRatio()))%></div>	
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-sm-6">
						<div class="form-group">
							<%=HtmlUtil.getLabel(request,"ATM", "col-sm-12 col-md-12")%>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-sm-6">
						<div class="form-group">
							<div class="col-sm-4 col-md-5"><%=HtmlUtil.getText(request,"CA")%></div>
							<div class="col-sm-8 col-md-7"><%=FormatUtil.display(supplementaryAdditionNal.getCurrentAccNo()) %></div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<div class="col-sm-8 col-md-7"><%=HtmlUtil.getText(request,"ACC_ENG_NAME")%></div>
							<div class="col-sm-4 col-md-5"><%=FormatUtil.display(supplementaryAdditionNal.getCurrentAccName()) %></div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<div class="col-sm-4 col-md-5"><%=HtmlUtil.getText(request,"SA")%></div>
							<div class="col-sm-8 col-md-7"><%=FormatUtil.display(supplementaryAdditionNal.getSavingAccNo()) %></div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<div class="col-sm-8 col-md-7"><%=HtmlUtil.getText(request,"ACC_ENG_NAME")%></div>
							<div class="col-sm-4 col-md-5"><%=FormatUtil.display(supplementaryAdditionNal.getSavingAccName()) %></div>
						</div>
					</div>				
				</div>
			<% }%>
			</div>
		</div>
	</div>
<%}%>