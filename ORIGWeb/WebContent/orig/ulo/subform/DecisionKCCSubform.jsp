<%@page import="com.eaf.orig.ulo.model.app.PolicyRulesDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.security.encryptor.Encryptor"%>
<%@page import="com.eaf.core.ulo.security.encryptor.EncryptorFactory"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.eaf.orig.ulo.model.app.ReasonDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalRelationDataM"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
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
<%@page import="com.eaf.core.ulo.common.display.ValidateFormInf"%>
<%@page import="com.eaf.core.ulo.common.util.MandatoryUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript" src="orig/ulo/subform/js/DecisionSubform.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());	
	//String displayMode = HtmlUtil.VIEW;
		
	String MandatoryFlag = ValidateFormInf.VALIDATE_NO;
	String APPLICATION_TYPE_ADD_SUP =SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP").toUpperCase();
	String APPLICATION_TYPE_INCREASE =SystemConstant.getConstant("APPLICATION_TYPE_INCREASE").toUpperCase();
	String ROLE_CAMGR = SystemConstant.getConstant("ROLE_CAMGR");	
	String ROLE_VT = SystemConstant.getConstant("ROLE_VT");
	String FIELD_ID_POLICY_RULE_REASON =SystemConstant.getConstant("FIELD_ID_POLICY_RULE_REASON");
	String FIELD_ID_APPLICATION_CARD_TYPE =SystemConstant.getConstant("FIELD_ID_APPLICATION_CARD_TYPE");
	String FIELD_ID_FICO_RECOMMEND_DECISION =SystemConstant.getConstant("FIELD_ID_FICO_RECOMMEND_DECISION");
	String FIELD_ID_CARD_LEVEL =SystemConstant.getConstant("FIELD_ID_CARD_LEVEL");
	String FIELD_ID_CARD_TYPE =SystemConstant.getConstant("FIELD_ID_CARD_TYPE");
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
 	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
 	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
 	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
 	String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
 	String CONFIG_ID_FINAL_DECISION = SystemConstant.getConstant("CONFIG_ID_FINAL_DECISION");
 	String REC_RESULT_REFER = SystemConstant.getConstant("REC_RESULT_REFER");
 	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
 	String[] DECISION_FINAL_DECISION_CONDITION = SystemConstant.getArrayConstant("DECISION_FINAL_DECISION_CONDITION");
 	ArrayList<String> FINAL_DECISION_CONDITION_LIST = new ArrayList<String>(Arrays.asList(DECISION_FINAL_DECISION_CONDITION));
 	String MAIN_PERSONAL_TYPE = PERSONAL_TYPE_APPLICANT;
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if(Util.empty(applicationGroup)){
		applicationGroup = new ApplicationGroupDataM();
	}
	String APPLICATION_TYPE = applicationGroup.getApplicationType();
	if(!Util.empty(APPLICATION_TYPE)){
		APPLICATION_TYPE = APPLICATION_TYPE.toUpperCase();
	}
	if(APPLICATION_TYPE_ADD_SUP.equals(APPLICATION_TYPE)){
		MAIN_PERSONAL_TYPE =PERSONAL_TYPE_SUPPLEMENTARY;
	}	
	ArrayList<ApplicationDataM>  borrowerApplications = new ArrayList<ApplicationDataM>();
	if((APPLICATION_TYPE_ADD_SUP.equals(APPLICATION_TYPE)) || (APPLICATION_TYPE_INCREASE.equals(APPLICATION_TYPE))){
		borrowerApplications =  applicationGroup.filterApplicationLifeCycle(PRODUCT_CRADIT_CARD);
	}else{
		borrowerApplications = applicationGroup.filterApplicationCardTypeLifeCycle(PRODUCT_CRADIT_CARD,APPLICATION_CARD_TYPE_BORROWER);
	}
   ArrayList<String> SUFFIX_ID = new ArrayList<String>();
   Encryptor encryptor = EncryptorFactory.getDIHEncryptor();
   String subformId = request.getParameter("subFormId");
   logger.debug(">>subFormId >> "+subformId);
   FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel-heading"><%=LabelUtil.getText(request, "CC")%></div>
<table class="table table-striped table-hover">
	<tr >
		<th colspan="3"><%=LabelUtil.getText(request,"CA_PRODUCT_NAME") %></th>
		<th colspan="3" style="text-align: left !important;"><%=LabelUtil.getText(request,"CA_RESULT_DECISION") %></th>
		<th><%=LabelUtil.getText(request,"REASON") %></th>
	</tr>
	<%if(!Util.empty(borrowerApplications)){
		for(ApplicationDataM borrowerApplication :borrowerApplications){
		/* if(borrowerApplication.getLifeCycle() > 1){
			displayMode = HtmlUtil.EDIT;
		}else{
			String finalAppDicision = FormatUtil.display(borrowerApplication.getFinalAppDecision());
			if(REC_RESULT_REFER.equals(borrowerApplication.getRecommendDecision()) 
			&& !DECISION_FINAL_DECISION_CANCEL.toUpperCase().equals(finalAppDicision.toUpperCase())){
				displayMode = HtmlUtil.EDIT;
			}else{
				displayMode = HtmlUtil.VIEW;
			}
		} */
			
		if(ROLE_CAMGR.equals(ORIGForm.getRoleId()) || ROLE_VT.equals(ORIGForm.getRoleId())){
				MandatoryFlag = ValidateFormInf.VALIDATE_NO;
			}else{			
				if(!Util.empty(borrowerApplication)){
					int lifeCycle = borrowerApplication.getLifeCycle();
					logger.debug("lifeCycle>>"+lifeCycle);
					logger.debug("getApplicationRecordId>>"+borrowerApplication.getApplicationRecordId());
					if(lifeCycle > 1){
						MandatoryFlag =ValidateFormInf.VALIDATE_SUBMIT;
					}else{
						String finalAppDicision = FormatUtil.display(borrowerApplication.getFinalAppDecision());
						String recommendDecision=borrowerApplication.getRecommendDecision();
						if(REC_RESULT_REFER.equals(recommendDecision)
						&& !DECISION_FINAL_DECISION_CANCEL.toUpperCase().equals(finalAppDicision.toUpperCase())){
							MandatoryFlag = ValidateFormInf.VALIDATE_SUBMIT;
						}else{
							MandatoryFlag = ValidateFormInf.VALIDATE_NO;
						}
					}
				}
			 }
		
		CardDataM borrowerCard = borrowerApplication.getCard();
		if(Util.empty(borrowerCard)){
			borrowerCard = new CardDataM();
		}
		LoanDataM borrowerLoan = borrowerApplication.getLoan();
		if(Util.empty(borrowerLoan)){
			borrowerLoan = new LoanDataM();
		}
		String borrowerUniqueId = borrowerApplication.getApplicationRecordId();
		PersonalRelationDataM borrowerPersonalRelation = applicationGroup.getPersonalRelation(borrowerUniqueId,MAIN_PERSONAL_TYPE,APPLICATION_LEVEL);
		if(Util.empty(borrowerPersonalRelation)){
			borrowerPersonalRelation = new PersonalRelationDataM();
		}
		String borrowerPersonalId = borrowerPersonalRelation.getPersonalId();
		PersonalInfoDataM borrowerPersonalInfo = applicationGroup.getPersonalInfoId(borrowerPersonalId);
		if(Util.empty(borrowerPersonalInfo)){
			borrowerPersonalInfo = new PersonalInfoDataM();
		}

		BigDecimal LOAN_AMT=borrowerLoan.getLoanAmt();
		 if(SystemConstant.getConstant("REC_DECISION_REJECT").equals(borrowerApplication.getRecommendDecision())){
		 	LOAN_AMT = BigDecimal.ZERO;
		 }
		 String borrowerCardLevel=CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",borrowerCard.getRequestCardType(), "CARD_LEVEL");
		 String borrowerCardCode=CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",borrowerCard.getRequestCardType(), "CARD_CODE");
		 String flpBorrowerCardCode=CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",borrowerCard.getRecommendCardCode(), "CARD_CODE");
		 String flpBorrowerCardLevel=CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",borrowerCard.getRecommendCardCode(), "CARD_LEVEL");
		 String slash="";
		 if(!Util.empty(borrowerCardLevel)){
		 	slash="&nbsp;/";
		 }
		 String flpSlash="";
		 if(!Util.empty(flpBorrowerCardCode)){
		 	flpSlash="&nbsp;/";
		 }
		 String applicantName = FormatUtil.display(borrowerPersonalInfo.getThFirstName())+"&nbsp;"+FormatUtil.display(borrowerPersonalInfo.getThLastName());
		 SUFFIX_ID.add(borrowerApplication.getApplicationRecordId());
		 
		String FINAL_RESULT ="";
		if(DECISION_FINAL_DECISION_CANCEL.equals(borrowerApplication.getFinalAppDecision())){
			FINAL_RESULT = HtmlUtil.hidden("KCC_FINAL_RESULT_"+borrowerApplication.getApplicationRecordId(), borrowerApplication.getFinalAppDecision());
			FINAL_RESULT += CacheControl.getName(SystemConstant.getConstant("FIELD_ID_FINAL_RESULT"), borrowerApplication.getFinalAppDecision());
		}else{
			FINAL_RESULT = HtmlUtil.dropdown("KCC_FINAL_RESULT", borrowerApplication.getApplicationRecordId(), "KCC_FINAL_RESULT", "KCC_FINAL_RESULT", CONFIG_ID_FINAL_DECISION, borrowerApplication.getFinalAppDecision(), "", SystemConstant.getConstant("FIELD_ID_FINAL_RESULT"), "", "", "", borrowerApplication,formUtil);
		}
		String TITLE_CARD_TYPE = CacheControl.getName(FIELD_ID_APPLICATION_CARD_TYPE, borrowerCard.getApplicationType());
		
		String MAIN_CARD_ENCRP = borrowerCard.getCardNo();
		String MAIN_CARD_NO = "";
		if(!Util.empty(MAIN_CARD_ENCRP)){
			try{
				MAIN_CARD_NO = encryptor.decrypt(MAIN_CARD_ENCRP);
			}catch(Exception e){
				logger.debug("ERROR",e);
			}
		}
		%>
	<%if(APPLICATION_TYPE_ADD_SUP.equals(APPLICATION_TYPE)){%>
		<tr>
			<td><div class="textdisplay"><%=HtmlUtil.getLabel(request, "DECISION_MAIN_CARD", "text_center") %></div></td>
			<td><%=HtmlUtil.getLabel(borrowerCard.getMainCardHolderName(), "text_center") %></td>
			<td colspan="7"></td>
		</tr>
	<%} %>		
	<tr>		 
		<td style="width: 10%;"><div class="textdisplay"><%=HtmlUtil.getLabel(TITLE_CARD_TYPE, "text_center")%></div>
			<div class="textdisplay text_center"><%=applicantName%></div></td>
		<%if(APPLICATION_TYPE_INCREASE.equals(APPLICATION_TYPE)){ %>
		<td style="width: 15%;"><div class="textdisplay"><%=FormatUtil.getCardNo(MAIN_CARD_NO)%></div></td>
		<td style="width: 15%;"><div class="inboxitemcard"><table>
			<tr><td><%=HtmlUtil.getFieldLabel(request, "REQ_CL")%></td> <td class="align-right"><%=FormatUtil.display(borrowerLoan.getRequestLoanAmt(),FormatUtil.Format.CURRENCY_FORMAT)%></td></tr></table></div></td>
		<%}else{ %>
		<td style="width: 15%;"><div class="textdisplay"><%=CacheControl.getName(FIELD_ID_CARD_TYPE, borrowerCardCode)%></div><div class="textdisplay"><%=slash %><%=CacheControl.getName(FIELD_ID_CARD_LEVEL,borrowerCardLevel)%></div></td>
		<td style="width: 15%;"><div class="textdisplay"><%=CacheControl.getName(FIELD_ID_CARD_TYPE, flpBorrowerCardCode)%></div><div class="textdisplay"><%=flpSlash %><%=CacheControl.getName(FIELD_ID_CARD_LEVEL, flpBorrowerCardLevel)%></div></td>
		<%} %>
		<td><div class="inboxitemcard"><table>
			<tr><td><%=HtmlUtil.getFieldLabel(request,"CA_REC_MAX_CREDIT") %></td><td class="align-right"><%=FormatUtil.display(borrowerLoan.getRecommendLoanAmt(),FormatUtil.Format.CURRENCY_FORMAT,false)+"/"+FormatUtil.display(borrowerLoan.getMaxCreditLimit(),FormatUtil.Format.CURRENCY_FORMAT,false)%></td></tr>
			<tr><td><%=HtmlUtil.getFieldLabel(request,"FINAL_CREDIT") %></td><td><%=HtmlUtil.currencyBox("KCC_FINAL_CREDIT_LIMIT",borrowerApplication.getApplicationRecordId(),"KCC_FINAL_CREDIT_LIMIT","KCC_FINAL_CREDIT_LIMIT",LOAN_AMT, FormatUtil.Format.CURRENCY_FORMAT, true, "12"," style='text-align: right !important'", "",borrowerApplication, formUtil) %></td></tr></table></div></td>
		<td><div class="inboxitemcard"><table>
			<tr><td><%=HtmlUtil.getFieldLabel(request,"CA_REC") %></td><td><%=CacheControl.getName(FIELD_ID_FICO_RECOMMEND_DECISION, borrowerApplication.getRecommendDecision())%><%=HtmlUtil.hidden("KCC_REC_DECISION_"+borrowerApplication.getApplicationRecordId(), borrowerApplication.getRecommendDecision()) %></td></tr>
			<tr><td><%=(MandatoryUtil.Mandatory(MandatoryFlag))?HtmlUtil.getMandatoryLabel(request,"FINAL_CREDIT"):HtmlUtil.getSubFormLabel(request,"FINAL_CREDIT")%></td><td><%=FINAL_RESULT%></td></tr></table></div></td>
		<td colspan="2">
			<%VerificationResultDataM borrowerVerResult= borrowerApplication.getVerificationResult();
			  if(Util.empty(borrowerVerResult)){
					borrowerVerResult = new VerificationResultDataM();
			  }
			ArrayList<PolicyRulesDataM> borrowerPolicyRules=borrowerVerResult.getPolicyRules();%>
			<%=HtmlUtil.multipleSelectPolicyReason("KCC_REASON", "KCC_REASON", "KCC_REASON", "form-control col-sm-12 col-md-12 overflowdata-x", borrowerPolicyRules,FIELD_ID_POLICY_RULE_REASON, "-&nbsp;",formUtil)%>
		</td>
	</tr>
	<%
	if(!APPLICATION_TYPE_ADD_SUP.equals(APPLICATION_TYPE) &&  !APPLICATION_TYPE_INCREASE.equals(APPLICATION_TYPE)){
	ArrayList<ApplicationDataM> supplemantaryApplications = applicationGroup.filterMaincardRecordIdCardTypeLifeCycle(borrowerApplication.getMaincardRecordId(),APPLICATION_CARD_TYPE_SUPPLEMENTARY);
	  if(!Util.empty(supplemantaryApplications)){
	  	for(ApplicationDataM supplemantaryItem : supplemantaryApplications){
		  	/* if(SystemConstant.getConstant("REC_RESULT_REFER").equals(supplemantaryItem.getRecommendDecision())){
				displayMode = HtmlUtil.EDIT;
			}else{
				displayMode = HtmlUtil.VIEW;
			} */
		  	CardDataM supplemantaryCard = supplemantaryItem.getCard();
		  	if(Util.empty(supplemantaryCard)){
				supplemantaryCard = new CardDataM();
			}
			LoanDataM supplemantaryLoan =supplemantaryItem.getLoan();
			if(Util.empty(supplemantaryLoan)){
				supplemantaryLoan = new LoanDataM();
			}
			String supplemantaryUniqueId = supplemantaryItem.getApplicationRecordId();
			PersonalRelationDataM supplemantaryPersonalRelation = applicationGroup.getPersonalRelation(supplemantaryUniqueId,PERSONAL_TYPE_SUPPLEMENTARY,APPLICATION_LEVEL);
			if(Util.empty(supplemantaryPersonalRelation)){
				supplemantaryPersonalRelation = new PersonalRelationDataM();
			}
			String supplemantaryPersonalId = supplemantaryPersonalRelation.getPersonalId();
			PersonalInfoDataM supplemantaryPersonalInfo = applicationGroup.getPersonalInfoId(supplemantaryPersonalId);
			if(Util.empty(supplemantaryPersonalInfo)){
				supplemantaryPersonalInfo = new PersonalInfoDataM();
			}
			String supplemantaryapplicantName = FormatUtil.display(supplemantaryPersonalInfo.getThFirstName())+"&nbsp;"+FormatUtil.display(supplemantaryPersonalInfo.getThLastName());
			BigDecimal SUB_LOAN_AMT=supplemantaryLoan.getLoanAmt();
			if(SystemConstant.getConstant("REC_DECISION_REJECT").equals(supplemantaryItem.getRecommendDecision())){
				SUB_LOAN_AMT = BigDecimal.ZERO;
			}
			String supplemantaryCardLevel=CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",supplemantaryCard.getRequestCardType(), "CARD_LEVEL");
			String supplemantaryCardCode=CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",supplemantaryCard.getRequestCardType(), "CARD_CODE");
			String flpsupplemantaryCardCode=CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",supplemantaryCard.getRecommendCardCode(), "CARD_CODE");
			SUFFIX_ID.add(supplemantaryItem.getApplicationRecordId());
			 
			String supplemantarySlash="";
			if(!Util.empty(borrowerCardLevel)){
				supplemantarySlash="&nbsp;/";
			}
			String supplemantaryflpSlash="";
			if(!Util.empty(flpBorrowerCardCode)){
				supplemantaryflpSlash="&nbsp;/";
			}
			 
			String FINAL_RESULT_SUP ="";
			if(DECISION_FINAL_DECISION_CANCEL.equals(supplemantaryItem.getFinalAppDecision())){
			   FINAL_RESULT_SUP= HtmlUtil.hidden("SUP_KCC_FINAL_RESULT_"+supplemantaryItem.getApplicationRecordId(), supplemantaryItem.getFinalAppDecision());
			   FINAL_RESULT_SUP += CacheControl.getName(SystemConstant.getConstant("FIELD_ID_FINAL_RESULT"), supplemantaryItem.getFinalAppDecision());		
			}else{
				FINAL_RESULT_SUP = HtmlUtil.dropdown("SUP_KCC_FINAL_RESULT", supplemantaryItem.getApplicationRecordId(), "SUP_KCC_FINAL_RESULT", "SUP_KCC_FINAL_RESULT", CONFIG_ID_FINAL_DECISION, supplemantaryItem.getFinalAppDecision(), "", SystemConstant.getConstant("FIELD_ID_FINAL_RESULT"), "", "", "",supplemantaryItem, formUtil);
			}
			String TITLE_SUP_CARD_TYPE = CacheControl.getName(FIELD_ID_APPLICATION_CARD_TYPE, supplemantaryCard.getApplicationType());
			String SUP_CARD_ENCRP = supplemantaryCard.getCardNo();
			String SUP_CARD_NO = "";
			if(!Util.empty(SUP_CARD_ENCRP)){
				try{
					SUP_CARD_NO = encryptor.decrypt(SUP_CARD_ENCRP);
				}catch(Exception e){
					logger.debug("ERROR",e);
				}
			}
			%>
			<tr>
				<td><div class="textdisplay"><%=HtmlUtil.getLabel(TITLE_SUP_CARD_TYPE,"text_center")%></div>
					<div class="textdisplay text_center"><%=supplemantaryapplicantName%></div></td>
				<%if(APPLICATION_TYPE_INCREASE.equals(APPLICATION_TYPE)){ %>
				<td><div class="textdisplay"><%=FormatUtil.getCardNo(SUP_CARD_NO)%></div></td>
				<td><div class="inboxitemcard"><table>
					<tr><td><%=HtmlUtil.getFieldLabel(request, "REQ_CL")%></td><td class="align-right"><%=FormatUtil.display(supplemantaryLoan.getRequestLoanAmt(),FormatUtil.Format.CURRENCY_FORMAT)%></td></tr></table></div></td>
				<%}else{ 
						String flpSupCardLevel = CacheControl.getName(CACHE_CARD_INFO, "CARD_TYPE_ID",supplemantaryCard.getRecommendCardCode(), "CARD_LEVEL");
				%>
				<td><div class="textdisplay"><%=CacheControl.getName(FIELD_ID_CARD_TYPE, supplemantaryCardCode)%></div><div class="textdisplay"><%=supplemantarySlash %><%=CacheControl.getName(FIELD_ID_CARD_LEVEL,supplemantaryCardLevel)%></div></td>
				<td><div class="textdisplay"><%=CacheControl.getName(FIELD_ID_CARD_TYPE, flpsupplemantaryCardCode)%></div><div class="textdisplay"><%=supplemantaryflpSlash %><%=CacheControl.getName(FIELD_ID_CARD_LEVEL, flpSupCardLevel)%></div></td>
				<%} %>
				<td>
					<div class="inboxitemcard">
						<table>
							<tr>
								<td><%=HtmlUtil.getFieldLabel(request,"CA_REC_MAX_CREDIT") %></td>
								<td class="align-right"><%=FormatUtil.display(supplemantaryLoan.getRecommendLoanAmt(),FormatUtil.Format.CURRENCY_FORMAT,false)+"/"+FormatUtil.display(supplemantaryLoan.getMaxCreditLimit(),FormatUtil.Format.CURRENCY_FORMAT,false)%></td>
							</tr>
							<tr>
								<td><%=HtmlUtil.getFieldLabel(request,"FINAL_CREDIT","col-sm-4 col-md-5") %></td>
								<td><%=HtmlUtil.currencyBox("SUP_KCC_FINAL_CREDIT_LIMIT",supplemantaryItem.getApplicationRecordId(),"SUP_KCC_FINAL_CREDIT_LIMIT","KCC_FINAL_CREDIT_LIMIT",SUB_LOAN_AMT, "", true, "12"," style='text-align: right !important'", "",supplemantaryItem, formUtil) %></td>
							</tr>
						</table>
					</div>
				</td>			
				<td><div class="inboxitemcard"><table>
					<tr><td><%=HtmlUtil.getFieldLabel(request,"CA_REC") %></td><td><%=CacheControl.getName(FIELD_ID_FICO_RECOMMEND_DECISION, supplemantaryItem.getRecommendDecision())%><%=HtmlUtil.hidden("KCC_REC_DECISION_"+supplemantaryItem.getApplicationRecordId(), supplemantaryItem.getRecommendDecision()) %></td></tr>
					<tr><td><%=HtmlUtil.getFieldLabel(request,"FINAL_CREDIT","col-sm-4 col-md-5") %></td><td><%=FINAL_RESULT_SUP %></td></tr></table></div></td>
				<td colspan="2">
					<div class="textdisplay">
						<table style="width: 100%">
							<tr>
								<td>
									<%VerificationResultDataM supplementaryVerResult= supplemantaryItem.getVerificationResult();
										if(Util.empty(supplementaryVerResult)){
											supplementaryVerResult = new VerificationResultDataM();
										}
									ArrayList<PolicyRulesDataM> supplementaryPolicyRules=supplementaryVerResult.getPolicyRules();
									%>
									<%=HtmlUtil.multipleSelectPolicyReason("KCC_REASON", "KCC_REASON", "KCC_REASON", "form-control col-sm-12 col-md-12 overflowdata-x", supplementaryPolicyRules,FIELD_ID_POLICY_RULE_REASON,"-&nbsp;", formUtil) %>	
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
	  	<%  }
	  	 }%>	
	 <%}
	 }	
	}else{ %>
		<tr>
			<td colspan="6" align="center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td>
		</tr>
	<%} %>
</table>
<%=HtmlUtil.hidden("SUFFIX_ID",!Util.empty(SUFFIX_ID)?SUFFIX_ID.toString().replace("[","").replace("]", ""):"")%>