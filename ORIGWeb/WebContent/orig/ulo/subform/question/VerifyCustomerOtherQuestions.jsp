<%@page import="com.eaf.orig.ulo.model.app.QuestionObjectDataM"%>
<%@page import="com.eaf.orig.ulo.control.util.ApplicationUtil"%>
<%@page import="java.util.Arrays"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.commons.beanutils.BeanComparator"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.CashTransferDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PaymentMethodDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionSetDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalRelationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.app.validation.AccountValidationHelper"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler" />

<%
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
	String personalId = request.getParameter("PERSONAL_ID");
	QuestionObjectDataM questionObject = new QuestionObjectDataM();
		questionObject.setPersonalId(personalId);
		questionObject.setApplicationGroup(applicationGroup);
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoId(personalId);
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}
	String FIELD_ID_COMPLETE_DATA_STATUS = SystemConstant.getConstant("FIELD_ID_COMPLETE_DATA_STATUS");
	String CALL_TO_APPLICANT = SystemConstant.getConstant("CALL_TO_APPLICANT");
	String CALL_TO_SUPPLEMENTARY = SystemConstant.getConstant("CALL_TO_SUPPLEMENTARY");
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String ACCT_VALIDATION_PASS = SystemConstant.getConstant("ACCT_VALIDATION_PASS");
 	String SPECIAL_ADDITIONAL_SERVICE_ATM = SystemConstant.getConstant("SPECIAL_ADDITIONAL_SERVICE_ATM");
	String QUESTION_SET_A = SystemConstant.getConstant("QUESTION_SET_A");
	String FLAG_ENABLED = SystemConstant.getConstant("FLAG_ENABLED");
	String[] QUESTION_SET_VERIFY_CUSTOMER_OTHER = SystemConstant.getArrayConstant("QUESTION_SET_VERIFY_CUSTOMER_OTHER");
	String SEND_DOCUMENT_ADDRESS_QUESTION = SystemConstant.getConstant("SEND_DOCUMENT_ADDRESS_QUESTION");
 	String[] QUESTION_CIS_IMPLEMENT_IDS = SystemConstant.getArrayConstant("VERIFY_CUTOMER_OTHER_QUESTION_CIS_IMPLEMENT_IDS");
 	
//  String CASH_DAY_1 = SystemConstant.getConstant("CASH_DAY_1");	
// 	String CASH_DAY_5 = SystemConstant.getConstant("CASH_DAY_5");
// 	String CASH_1_HOUR = SystemConstant.getConstant("CASH_1_HOUR");
	String CALL_FOR_CASH = SystemConstant.getConstant("CALL_FOR_CASH");
	 
	String []cashTransFerType = ApplicationUtil.getCashTranferTypes();
	
 	int seqNo = 0;
 	VerificationResultDataM verificationResult= personalInfo.getVerificationResult();
	ArrayList<IdentifyQuestionSetDataM> identifyQuestionSets = verificationResult.getVerifyCustomerQuestionSets(QUESTION_SET_VERIFY_CUSTOMER_OTHER);
	
	
	String callToDesc = "";
	if(!Util.empty(identifyQuestionSets)){
		String callTo =identifyQuestionSets.get(0).getCallTo();
		if(CALL_TO_APPLICANT.equals(callTo)){
			callToDesc=HtmlUtil.getText(request,"BORROWER");
		}else if(CALL_TO_SUPPLEMENTARY.equals(callTo)){
			callToDesc=HtmlUtil.getText(request,"SUPPLEMENTARY");
		} 
	}
%>
	<div class="section-border">
		<div class="form-horizontal">
			<div class="form-group" id = "OTHER_QUESTIONS_HEADER_TITLE">
				<div class="col-sm-7">
					<%=HtmlUtil.getLabel(request, "OTHER_QUESTIONS_SUBFORM","")%>
				</div>
				<div class="col-sm-5">
					<%=HtmlUtil.getText(request, "TEL_VERIFY")%>
					<%=FormatUtil.display(callToDesc) %>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<table class="table table-hover">
				<tbody>
		<%	
		if(!Util.empty(identifyQuestionSets)){
			for(IdentifyQuestionSetDataM identifyQuestionSet : identifyQuestionSets){
				String QuestionSetItem = identifyQuestionSet.getQuestionSetCode();
				ArrayList<HashMap<String,Object>> questions = CacheControl.search("QuestionCacheDataM", "SET_TYPE", QuestionSetItem);
				for(HashMap<String,Object> question : questions){
					String QUESTION_NO = (String)question.get("CODE");
					String ENABLED = (String)question.get("ENABLED");
					boolean isShow = false;
					if(FLAG_ENABLED.equals(ENABLED)){
						isShow = true;
					}
					IdentifyQuestionDataM identifyQuestionsDisplay = verificationResult.getIndentifyQuesitionNo(QUESTION_NO);
					if(!Util.empty(identifyQuestionsDisplay) && isShow){
					logger.debug("QUESTION_NO >>>"+QUESTION_NO);
						ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_QUESTION_CUSTOMER, QUESTION_NO);
						elementInf.setObjectRequest(questionObject);
						seqNo ++;
					%>
					<tr>
						<td colspan="2">
						<%=FormatUtil.getString(seqNo) %>. <%=CacheControl.getName("QuestionCacheDataM", identifyQuestionsDisplay.getQuestionNo()) %>
	 						<%elementInf.writeElement(pageContext, identifyQuestionsDisplay);%>
						</td>
					</tr>
					<%			
					}
				}
			}
		}
		%>
		<%-- Verify Customer CIS Section--%>
		<% 
			ArrayList<String> implementIds = new ArrayList<String>(Arrays.asList(QUESTION_CIS_IMPLEMENT_IDS));
			for(String implementId :implementIds){
			ElementInf elementCIS = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_CUSTOMER_CIS,implementId);
		%>
			<%if(MConstant.FLAG.YES.equals(elementCIS.renderElementFlag(request, applicationGroup))){			
				seqNo++;
			%>	
				<tr>
					<td colspan="8">
						<%=FormatUtil.getString(seqNo)%>. <%=LabelUtil.getText(request,implementId)%>
					</td>
				</tr>			
			<%} %>
		<%
			}
		%>
		
		<%-- Verify Customer Payment Section--%>
		<%
		ArrayList<String> products = applicationGroup.filterProductPersonal(personalInfo.getPersonalId(), PERSONAL_RELATION_APPLICATION_LEVEL);
		if(!Util.empty(products)){		
		
			for (String product : products) {
				PaymentMethodDataM paymentMethod = applicationGroup.getPaymentMethodLifeCycleByPersonalId(personalId,product); 
				if(!Util.empty(paymentMethod)) {
					ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_CUSTOMER_PAYMENTS, product);
				
					if(MConstant.FLAG.YES.equals(element.renderElementFlag(request, personalId))){
				 		if(AccountValidationHelper.isJointAccount(personalInfo, paymentMethod)){
					 		seqNo ++;%>
							<tr>
								<td colspan="2">
								<%=FormatUtil.getString(seqNo)%>. <%=FormatUtil.display(ListBoxControl.getName(FIELD_ID_COMPLETE_DATA_STATUS,paymentMethod.getCompleteData(),"DISPLAY_NAME")) %>
								</td>
							</tr>
							<tr>
								<td>
									<%										
										element.writeElement(pageContext,personalInfo.getPersonalId());
									%>
								</td>
							</tr>
						<%}else if (!AccountValidationHelper.isAccountValid(paymentMethod)){
								seqNo ++;%>
							<tr>
								<td>
										<%=FormatUtil.getString(seqNo)%>. <%=FormatUtil.display(ListBoxControl.getName(FIELD_ID_COMPLETE_DATA_STATUS,paymentMethod.getCompleteData(),"DISPLAY_NAME")) %>
								</td>
							</tr>
				
				  		<%} 
				  	 }
				 }
			}%>
				<%-- Verify Customer Additional Service Email --%>
			<%	
					ElementInf elementEmailStatement = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_CUSTOMER_EMAIL, "KEMailStatement");
					if(MConstant.FLAG.YES.equals(elementEmailStatement.renderElementFlag(request,personalInfo.getPersonalId()))){
						seqNo +=1;
						elementEmailStatement.setObjectRequest(FormatUtil.display(seqNo));%>
						<tr>
							<td>
						  		<%elementEmailStatement.writeElement(pageContext,personalInfo.getPersonalId());%>
						  	</td>
					  	</tr>
								
				<%-- Verify Customer  Additional Service Section--%>
			<%	}for (String product : products) {
						SpecialAdditionalServiceDataM specialAdditionalServiceProduct = applicationGroup.getSpecialAdditionalServiceLifeCycleByPersonalId(personalId, product, SPECIAL_ADDITIONAL_SERVICE_ATM);
						if(!Util.empty(specialAdditionalServiceProduct) && !Util.empty(specialAdditionalServiceProduct.getCompleteData())){
						
							if(!specialAdditionalServiceProduct.getCompleteData().equals(ACCT_VALIDATION_PASS)){  
							seqNo +=1;
							%>
						<tr>
							<td>
								<%=FormatUtil.getString(seqNo)%>. <%=FormatUtil.display(ListBoxControl.getName(FIELD_ID_COMPLETE_DATA_STATUS,specialAdditionalServiceProduct.getCompleteData(),"DISPLAY_NAME")) %>
							</td>
						</tr>
			
						<%} 
						
						if(!specialAdditionalServiceProduct.getCompleteDataSaving().equals(ACCT_VALIDATION_PASS)){  
							seqNo +=1;
							%>
						<tr>
							<td>
								<%=FormatUtil.getString(seqNo)%>. <%=FormatUtil.display(ListBoxControl.getName(FIELD_ID_COMPLETE_DATA_STATUS,specialAdditionalServiceProduct.getCompleteDataSaving(),"DISPLAY_NAME")) %>
							</td>
						</tr>
			
					<%	}
					 }
					} 
			    } %>	
				
				<%-- Verify Customer Cash Trnsfer --%>
				<% 
				ApplicationDataM applicationItem = applicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
				if(!Util.empty(applicationItem)) {
					PersonalRelationDataM relationM = applicationGroup.getPersonalRelation(applicationItem.getApplicationRecordId(), personalInfo.getPersonalType(), PERSONAL_RELATION_APPLICATION_LEVEL);
					if(!Util.empty(relationM)) {
						CashTransferDataM cashTransfer = applicationItem.getCashTransfer(cashTransFerType);
				 	 	if(!Util.empty(cashTransfer) && !Util.empty( cashTransfer.getCompleteData())){
					 		if(cashTransfer.getCompleteData().equals(ACCT_VALIDATION_PASS) ){
								seqNo +=1;
				 	%>
							<tr>
								<td>
									<%=FormatUtil.getString(seqNo)%>. <%=FormatUtil.display(ListBoxControl.getName(FIELD_ID_COMPLETE_DATA_STATUS,cashTransfer.getCompleteData(),"DISPLAY_NAME")) %>
								</td>
							</tr>
							<tr>
								<td>
											<%
												ElementInf elementCashTransfer = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_CUSTOMER_CASH_TRANSFER, "CASH_DAY_ONE");
												elementCashTransfer.writeElement(pageContext,personalInfo.getPersonalId());
											%>

								</td>
							</tr>
						<% }else if(!cashTransfer.getCompleteData().equals(ACCT_VALIDATION_PASS)){
							seqNo +=1;
						%>
							<tr>
								<td>
									<%=FormatUtil.getString(seqNo)%>. <%=FormatUtil.display(ListBoxControl.getName(FIELD_ID_COMPLETE_DATA_STATUS,cashTransfer.getCompleteData(),"DISPLAY_NAME")) %>
								</td>
							</tr>
							<%
							}
						}
						
						CashTransferDataM cashTransferCallForCash = applicationItem.getCashTransfer(CALL_FOR_CASH);
						if(!Util.empty(cashTransferCallForCash) && !Util.empty(cashTransferCallForCash.getCompleteData())){
						if(cashTransferCallForCash.getCompleteData().equals(ACCT_VALIDATION_PASS)){
							seqNo +=1;								 		
				 		%>
							<tr>
								<td colspan="8">
									<div class="col-sm-12">
										<div class="form-group"><%=FormatUtil.getString(seqNo)%>. <%=FormatUtil.display(ListBoxControl.getName(FIELD_ID_COMPLETE_DATA_STATUS,cashTransferCallForCash.getCompleteData(),"DISPLAY_NAME")) %></div>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="8">
									<div class="col-sm-12">
										<div class="form-group">
											<%
												ElementInf elementCashTransfer = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_CUSTOMER_CASH_TRANSFER, "CALL_FOR_CASH");
												elementCashTransfer.writeElement(pageContext,personalInfo.getPersonalId());
											%>
										</div>
									</div>
								</td>
							</tr>
						<% }else  if(!cashTransferCallForCash.getCompleteData().equals(ACCT_VALIDATION_PASS)){
							seqNo +=1;
						%>
							<tr>
								<td colspan="8">
									<div class="col-sm-12">
										<div class="form-group"><%=FormatUtil.getString(seqNo)%>. <%=FormatUtil.display(ListBoxControl.getName(FIELD_ID_COMPLETE_DATA_STATUS,cashTransferCallForCash.getCompleteData(),"DISPLAY_NAME")) %></div>
									</div>
								</td>
							</tr>
							<%
						}
					  }
					}
				}
				%>
				</tbody>
			</table>
		</div>
</div>