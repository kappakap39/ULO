<%@page import="com.eaf.orig.ulo.model.app.PolicyRulesDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.security.encryptor.EncryptorFactory"%>
<%@page import="com.eaf.core.ulo.security.encryptor.Encryptor"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.eaf.orig.ulo.model.app.ReasonDataM"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalRelationDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
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
	String FIELD_ID_POLICY_RULE_REASON =SystemConstant.getConstant("FIELD_ID_POLICY_RULE_REASON");
	String APPLICATION_TYPE_INCREASE =SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
	String FIELD_ID_FICO_RECOMMEND_DECISION =SystemConstant.getConstant("FIELD_ID_FICO_RECOMMEND_DECISION");
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String CONFIG_ID_FINAL_DECISION = SystemConstant.getConstant("CONFIG_ID_FINAL_DECISION");
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	String[] DECISION_FINAL_DECISION_CONDITION = SystemConstant.getArrayConstant("DECISION_FINAL_DECISION_CONDITION");
 	ArrayList<String> FINAL_DECISION_CONDITION_LIST = new ArrayList<String>(Arrays.asList(DECISION_FINAL_DECISION_CONDITION));
 	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if(Util.empty(applicationGroup)){
		applicationGroup = new ApplicationGroupDataM();
	}

	//String displayMode = HtmlUtil.EDIT;	
	ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_EXPRESS_CASH);
	if(Util.empty(applications)){
		applications = new ArrayList<ApplicationDataM>();
	}
	 String APPLICATION_TYPE = applicationGroup.getApplicationType();
	 int MAX_LIFE_CYCLE = applicationGroup.getMaxLifeCycleFromApplication(); 
	 String FIRST_FINAL_DECISION ="";
	 BigDecimal FIRST_FINAL_CREDIT_LIMIT = null;
	 Encryptor encryptor = EncryptorFactory.getDIHEncryptor();
	 String subformId = request.getParameter("subFormId");
	 logger.debug("subformId=="+subformId);
   	 FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel-heading"><%=LabelUtil.getText(request, "KEC")%></div>

	<table class="table table-striped table-hover">
		<tr>
			<!-- <th></th>  -->
			<th colspan="3"><%=LabelUtil.getText(request,"CA_PRODUCT_NAME") %></th>	
			<th colspan="2" style="text-align: left !important;"><%=LabelUtil.getText(request,"CA_RESULT_DECISION") %></th>		 
			<th><%=LabelUtil.getText(request,"REASON") %></th>
		</tr>
		<%if(!Util.empty(applications)){ 
			int count=0;
			for(ApplicationDataM  applicationItem :applications){
			if(MAX_LIFE_CYCLE==applicationItem.getLifeCycle()){
				count++;
/* 				if(applicationItem.getLifeCycle() > 1){
				displayMode = HtmlUtil.EDIT;
				}else{
				if(SystemConstant.getConstant("REC_RESULT_REFER").equals(applicationItem.getRecommendDecision())){
					displayMode = HtmlUtil.EDIT;
				}else{
					displayMode = HtmlUtil.VIEW;
				}
			} */
			LoanDataM loan = applicationItem.getLoan();
			CardDataM card = applicationItem.getCard();
			String cardTypeId =card.getCardType();
			String cardTypeCode =CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",cardTypeId, "CARD_CODE");
			String applicationRecordID = applicationItem.getApplicationRecordId();
			PersonalRelationDataM personalRelation = applicationGroup.getPersonalRelation(applicationRecordID,PERSONAL_TYPE_APPLICANT,APPLICATION_LEVEL);
			if(Util.empty(personalRelation)){
				personalRelation = new PersonalRelationDataM();
			}
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoId(personalRelation.getPersonalId());
			if(Util.empty(personalInfo)){
				personalInfo = new PersonalInfoDataM();
			}
			//String displayValue1="";
			//String displayValue2 =FormatUtil.display(personalInfo.getThFirstName())+"&nbsp;"+FormatUtil.display(personalInfo.getThLastName());
			String displayValue1 =FormatUtil.display(personalInfo.getThFirstName())+"&nbsp;"+FormatUtil.display(personalInfo.getThLastName());
			String displayValue2= "";
			if(APPLICATION_TYPE_INCREASE.equals(APPLICATION_TYPE)){
				//displayValue1 =FormatUtil.display(personalInfo.getThFirstName())+"&nbsp;"+FormatUtil.display(personalInfo.getThLastName());
				String CARD_ENCRP = card.getCardNo();
				if(!Util.empty(CARD_ENCRP)){
					try{
						displayValue2 = FormatUtil.getCardNo(encryptor.decrypt(CARD_ENCRP));
					}catch(Exception e){
					logger.debug("ERROR",e);
					}
				}
			}
			
			 if(1==count){
				FIRST_FINAL_DECISION =applicationItem.getFinalAppDecision();
				FIRST_FINAL_CREDIT_LIMIT =loan.getLoanAmt();
			}
			 String KEC_FINAL_RESULT_ELEMENT="";
			 String FINAL_APP_DECISION =applicationItem.getFinalAppDecision();
			 BigDecimal  FINAL_CREDIT_LIMIT= loan.getLoanAmt();
			 
			 if(Util.empty(FINAL_APP_DECISION)){
			 	FINAL_APP_DECISION = FIRST_FINAL_DECISION;
			 	FINAL_CREDIT_LIMIT = FIRST_FINAL_CREDIT_LIMIT;
			 }			 	
			  
			 if(DECISION_FINAL_DECISION_REJECT.equals(FINAL_APP_DECISION)){
			 	FINAL_CREDIT_LIMIT =BigDecimal.ZERO;
			 }	 
		 
			if(DECISION_FINAL_DECISION_CANCEL.equals(applicationItem.getFinalAppDecision())){
			 	KEC_FINAL_RESULT_ELEMENT = HtmlUtil.hidden("KEC_FINAL_RESULT_"+applicationItem.getApplicationRecordId(), applicationItem.getFinalAppDecision());
			 	KEC_FINAL_RESULT_ELEMENT += CacheControl.getName(SystemConstant.getConstant("FIELD_ID_FINAL_RESULT"), applicationItem.getFinalAppDecision());			 
			 }else{
			 	KEC_FINAL_RESULT_ELEMENT =HtmlUtil.dropdown("KEC_FINAL_RESULT", applicationItem.getApplicationRecordId(), "KEC_FINAL_RESULT", "KEC_FINAL_RESULT", CONFIG_ID_FINAL_DECISION, FINAL_APP_DECISION, "", SystemConstant.getConstant("FIELD_ID_FINAL_RESULT"), "", "", "", applicationItem,formUtil);
			 }			
			%>			
			<tr>
				<td style="width: 10%;">
					<div class="inboxitemcard text_center"><%=displayValue1%></div>
				</td>
				<td style="width: 15%;">
					<div class="inboxitemcard"><%=FormatUtil.display(CacheControl.getName(SystemConstant.getConstant("FIELD_ID_CARD_TYPE"), cardTypeCode)) %></div>
					<div class="inboxitemcard"><%=displayValue2%></div>
				</td>
				
				<%if(APPLICATION_TYPE_INCREASE.equals(APPLICATION_TYPE)){ %>
				<td style="width: 15%;"><div class="inboxitemcard">
					<table>
						<tr><td><%=HtmlUtil.getFieldLabel(request, "REQ_CL")%></td> <td class="align-right"><%=FormatUtil.display(loan.getRequestLoanAmt(),FormatUtil.Format.CURRENCY_FORMAT)%></td></tr>
					</table>
				</div></td>
				<%} else { %>
				<td style="width: 15%;"></td>
				<% } %>
				<td><div class="inboxitemcard"><table>
					<tr><td><%=HtmlUtil.getFieldLabel(request,"CA_REC_MAX_CREDIT") %></td>
						<td class="align-right"><%=FormatUtil.display(loan.getRecommendLoanAmt(),FormatUtil.Format.CURRENCY_FORMAT)+"/"+FormatUtil.display(loan.getMaxCreditLimit(),FormatUtil.Format.CURRENCY_FORMAT)%></td></tr>
					<tr><td><%=HtmlUtil.getFieldLabel(request,"FINAL_CREDIT") %></td>
						<td><%=HtmlUtil.currencyBox("KEC_FINAL_CREDIT_LIMIT", applicationItem.getApplicationRecordId(), "KEC_FINAL_CREDIT_LIMIT", "KEC_FINAL_CREDIT_LIMIT", FINAL_CREDIT_LIMIT,  FormatUtil.Format.CURRENCY_FORMAT, false, "12", "style='text-align: right !important'", "",applicationItem, formUtil) %></td>	
						</tr>
					</table></div>
				</td>				 
				<td><div class="inboxitemcard"><table>
					<tr><td><%=HtmlUtil.getFieldLabel(request,"CA_REC") %></td>
						<td><%=CacheControl.getName(FIELD_ID_FICO_RECOMMEND_DECISION, applicationItem.getRecommendDecision())%><%=HtmlUtil.hidden("KEC_REC_DECISION_"+applicationItem.getApplicationRecordId(), applicationItem.getRecommendDecision())%></td></tr>
					<tr><td><%=HtmlUtil.getFieldLabel(request,"FINAL_CREDIT") %></td>
						<td><%=KEC_FINAL_RESULT_ELEMENT%></td></tr></table>
						</div></td>
				<td>
					<div class="inboxitemcard">
						<%VerificationResultDataM verResult= applicationItem.getVerificationResult();
						  if(Util.empty(verResult)){
								verResult = new VerificationResultDataM();
			  				}
							ArrayList<PolicyRulesDataM> policyRules=verResult.getPolicyRules();
						
						%>
						<%=HtmlUtil.multipleSelectPolicyReason("KEC_REASON", "KEC_REASON", "KEC_REASON", "form-control col-sm-12 col-md-12 overflowdata-x", policyRules,FIELD_ID_POLICY_RULE_REASON,"-&nbsp;",formUtil) %>
					</div>
				</td>
			</tr>				
			<%}
			 }
			}else{ %>
			<tr>
				<td colspan="999" align="center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td>
			</tr>
		<%} %>
	</table>