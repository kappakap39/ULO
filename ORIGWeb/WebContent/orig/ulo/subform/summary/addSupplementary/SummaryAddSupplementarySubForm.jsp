<%@page import="com.eaf.core.ulo.security.encryptor.EncryptorFactory"%>
<%@page import="com.eaf.core.ulo.security.encryptor.Encryptor"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.eaf.orig.ulo.model.app.WorkFlowDecisionM"%>
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

<script type="text/javascript" src="orig/ulo/subform/summary/addSupplementary/js/SummaryAddSupplementarySubForm.js"></script>
<%
 Logger logger = Logger.getLogger(this.getClass());
 String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
 String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
  String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
 String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
 String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
 String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
 String SPECIAL_ADDITIONAL_SERVICE_ATM = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_ATM");
 String PAYMENT_METHOD_DEPOSIT_ACCOUNT = SystemConstant.getConstant("PAYMENT_METHOD_DEPOSIT_ACCOUNT");
 String[] DECISION_FINAL_DECISION_CONDITION = SystemConstant.getArrayConstant("DECISION_FINAL_DECISION_CONDITION");
 ArrayList<String> FINAL_DECISION_CONDITION_LIST = new ArrayList<String>(Arrays.asList(DECISION_FINAL_DECISION_CONDITION));
 
 FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);	
 ApplicationGroupDataM  applicationGroup =  ORIGForm.getObjectForm();
 if(Util.empty(applicationGroup)){
	applicationGroup = new ApplicationGroupDataM();
 }
 int MAX_LIFE_CYCEL = applicationGroup.getMaxLifeCycleFromApplication();
 ArrayList<ApplicationDataM> supplementaryApplications = applicationGroup.filterApplicationCardType(PRODUCT_CRADIT_CARD,APPLICATION_CARD_TYPE_SUPPLEMENTARY);	

 ArrayList<WorkFlowDecisionM>  workFlowDecisions = applicationGroup.getWorkFlowDecisions(); 
 String CALL_FICO_FLAG = applicationGroup.getCallFicoFlag();
 
 Encryptor encryptor = EncryptorFactory.getDIHEncryptor();
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
<%if(!Util.empty(supplementaryApplications)) {%>
	<%
		for(ApplicationDataM supplementaryItem:supplementaryApplications){
		if(MAX_LIFE_CYCEL==supplementaryItem.getLifeCycle() && !FINAL_DECISION_CONDITION_LIST.contains(supplementaryItem.getFinalAppDecision())){
			String supplementaryApplicationRecordId = supplementaryItem.getApplicationRecordId();
			CardDataM supplementaryCard = supplementaryItem.getCard();
			if(Util.empty(supplementaryCard)){
				supplementaryCard = new CardDataM();
			}
			LoanDataM supplementaryLoan = supplementaryItem.getLoan();
			if(Util.empty(supplementaryLoan)){
				supplementaryLoan = new LoanDataM();
			}
			PersonalInfoDataM supplementaryPersonalInfo = applicationGroup.getPersonalInfoRelation(supplementaryApplicationRecordId, PERSONAL_TYPE_SUPPLEMENTARY, APPLICATION_LEVEL);
			if(Util.empty(supplementaryPersonalInfo)){
				supplementaryPersonalInfo = new PersonalInfoDataM();
			}
			PaymentMethodDataM supplementaryPaymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(supplementaryPersonalInfo.getPersonalId(), PRODUCT_CRADIT_CARD);
			if(Util.empty(supplementaryPaymentMethod)){
				supplementaryPaymentMethod = new PaymentMethodDataM();
			}
			SpecialAdditionalServiceDataM  supplementaryAdditionNal = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(supplementaryPersonalInfo.getPersonalId(), PRODUCT_CRADIT_CARD, SPECIAL_ADDITIONAL_SERVICE_ATM);
			if(Util.empty(supplementaryAdditionNal)){
				supplementaryAdditionNal = new  SpecialAdditionalServiceDataM();
			}
			String supplementaryCardLevel=CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",supplementaryCard.getCardType(), "CARD_LEVEL");
			String supplementaryCardCode=CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",supplementaryCard.getCardType(), "CARD_CODE");
	%>
			<div class="panel panel-default">
			<div class="panel-heading"><%=LabelUtil.getText(request, "DECISION_SUP_CARD")%></div>
				<div class="panel-body">
					<div class="row form-horizontal">
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-12 col-md-12 "><%=FormatUtil.display(supplementaryPersonalInfo.getEnFirstName())%>&nbsp;<%=FormatUtil.display(supplementaryPersonalInfo.getEnLastName())%></div>						
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-12 col-md-12 "><%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_CARD_TYPE"), supplementaryCardCode)%></div>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-8 col-md-7"><%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_CARD_LEVEL"),supplementaryCardLevel)%></div>
								<div class="col-sm-4 col-md-5"><%=FormatUtil.display(supplementaryLoan.getLoanAmt(),FormatUtil.Format.CURRENCY_FORMAT)%></div>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<%=HtmlUtil.getLabel(request,"PAYMENT", "col-sm-12 col-md-12")%>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-8 col-md-7"><%=HtmlUtil.getText(request,"ACCOUNT_NUMBER")%></div>
								<div class="col-sm-4 col-md-5 "><%=FormatUtil.getAccountNo(supplementaryPaymentMethod.getAccountNo())%></div>	
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-8 col-md-7"><%=HtmlUtil.getText(request,"ACC_ENG_NAME")%></div>
								<div class="col-sm-4 col-md-5 "><%=FormatUtil.display(supplementaryPaymentMethod.getAccountName())%></div>		
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-8 col-md-7"><%=HtmlUtil.getText(request,"PAY_COND")%></div>
								<div class="col-sm-4 col-md-5 "><%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_PAYMENT_RATIO"),FormatUtil.display(supplementaryPaymentMethod.getPaymentRatio()))%></div>	
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
								<div class="col-sm-8 col-md-7"><%=FormatUtil.display(supplementaryAdditionNal.getCurrentAccNo())%></div>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-8 col-md-7"><%=HtmlUtil.getText(request,"ACC_ENG_NAME")%></div>
								<div class="col-sm-4 col-md-5"><%=FormatUtil.display(supplementaryAdditionNal.getCurrentAccName())%></div>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-4 col-md-5"><%=HtmlUtil.getText(request,"SA")%></div>
								<div class="col-sm-8 col-md-7"><%=FormatUtil.display(supplementaryAdditionNal.getSavingAccNo())%></div>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<div class="col-sm-8 col-md-7"><%=HtmlUtil.getText(request,"ACC_ENG_NAME")%></div>
								<div class="col-sm-4 col-md-5"><%=FormatUtil.display(supplementaryAdditionNal.getSavingAccName())%></div>
							</div>
						</div>
				  <%
				  	 
				  		String CARD_ENCRP = supplementaryCard.getMainCardNo();
				  		String CARD_NO="";
				  		if(!Util.empty(CARD_ENCRP)){
			 			try{
							CARD_NO = encryptor.decrypt(CARD_ENCRP);
						}catch(Exception e){
							logger.debug("ERROR",e);
						}
			 		}
				  %>
						<div class="col-sm-12">
							<div class="form-group">
								<div class="col-sm-12 col-md-12">**<%=FormatUtil.display(supplementaryCard.getMainCardHolderName()) %>**</div>
							</div>
						</div>
						<div class="col-sm-12">
							<div class="form-group">
								
								<div class="col-sm-12 col-md-12">**<%=HtmlUtil.getText(request, "SUMMARY_MAIN_CARD_NUMBER")%>&nbsp;<%=FormatUtil.getCardNo(CARD_NO)%></div>
							</div>
						</div>
					<%} %>
					</div>
				</div>
			</div>
	 <%}
	 }%>