<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.model.LoanDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<%
	com.eaf.orig.shared.model.ApplicationDataM applicationDataM = ORIGForm.getAppForm();	
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	ORIGUtility utility = new ORIGUtility();
	
	PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
	Vector personalVect = utility.getVectorPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_GUARANTOR); 
	Vector loanvect = applicationDataM.getLoanVect();
	
	LoanDataM loanDataM = new LoanDataM();
	if(loanvect!= null && loanvect.size() > 0){				
		loanDataM = (LoanDataM)loanvect.get(0);	
	}
	String searchType = (String)request.getSession().getAttribute("searchType");
	String proposalMenu = (String) request.getSession().getAttribute("PROPOSAL_MENU");
	
	String appDecision = applicationDataM.getAppDecision();
	String uwDecision = applicationDataM.getUwDecision();
	if(ORIGUtility.isEmptyString(appDecision)){
		appDecision = uwDecision;
	}
	XRulesVerificationResultDataM xrulesVerificationResultDataM=personalInfoDataM.getXrulesVerification();
	if(xrulesVerificationResultDataM==null){
	xrulesVerificationResultDataM=new XRulesVerificationResultDataM();
	}
	
	if(com.eaf.orig.wf.shared.ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION.equals(appDecision)){
		appDecision = "Sent to Exception CMR";
	}
%>

<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "SUMMARYPAGE") %> </td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
	                                    	<INPUT type="button" name="pritBtn" value=" <%=MessageResourceUtil.getTextDescription(request, "PRINT") %> " onClick="javascript:print()" class="button">
	                                    </td><td>
	                                    	<INPUT type="button" name="okayBtn" value=" <%=MessageResourceUtil.getTextDescription(request, "OK") %> " onClick="javascript:backToSearchType()" class="button">
	                                    </td></tr>
	                                </table>
	                            </td>
	                        </tr>
	                        <tr>
                            	<td class="Bigtodotext3" colspan="2" align="center">This application has been changed to status "<%=appDecision%>".</td>
                            </tr>	
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>                        
						<tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
									<td class="textColS" width="23%"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO") %> :</td>
									<td class="inputCol" width="15%"><%= applicationDataM.getApplicationNo()%></td>
									<td class="textColS" width="18%"><%=MessageResourceUtil.getTextDescription(request, "CITIZEN_ID") %> :</td>
									<td class="inputCol" ><%= personalInfoDataM.getIdNo()%></td>
								</tr>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_NAME") %> :</td>
									<td class="inputCol" colspan="3" nowrap="nowrap"><%= cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM.getThaiTitleName()) %>
									<%= ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getThaiFirstName()) %>
									<%= ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getThaiLastName()) %>
									</td>
								</tr>
								<tr>
									<td class="textColS" valign="top"><%=MessageResourceUtil.getTextDescription(request, "GUARANTOR_NAME") %> :</td>
									<td class="inputCol" colspan="3">
									<%	
										if(personalVect!= null){
											PersonalInfoDataM personalInfoDataM2;
											for (int i =0 ; i< personalVect.size() ; i++){ 
												personalInfoDataM2 = (PersonalInfoDataM)personalVect.get(i);
									%>			
											<%= cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM2.getThaiTitleName()) %>
											<%= ORIGDisplayFormatUtil.displayHTML(personalInfoDataM2.getThaiFirstName()) %>
											<%= ORIGDisplayFormatUtil.displayHTML(personalInfoDataM2.getThaiLastName()) %>
												<%if((i+1)!=personalVect.size()){
												 %>,&nbsp;<br> 	
												<%}
										 	}
										}
									 %>
									</td>
								</tr>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "UPDATE_BY") %> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.forHTMLTag(ORIGUser.getFirstName())%>  <%=ORIGDisplayFormatUtil.forHTMLTag(ORIGUser.getLastName())%></td>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "UPDATE_DATE") %> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.dateTimetoStringForThai(new java.util.Date())%></td>
								</tr>
							</table>
						</td>
					</tr>
				<%if(!OrigConstant.SEARCH_TYPE_PROPOSAL.equals(searchType) && !("Y").equals(proposalMenu)){ %>
					<tr class="sidebar10"> <td align="center">
						<fieldset><LEGEND><font class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "LOAN_DETAIL") %></font></Legend><br>
						<table cellpadding="0" cellspacing="1" border="0" width="99%" align="center" >		
							<tr bgcolor="#F4F4F4">
								<td class="textColS" width="22%"><%=MessageResourceUtil.getTextDescription(request, "LOAN_AMOUNT_APPLIED") %> :</td>
								<td class="inputCol" width="14%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getLoanAmt())%></td>
								<td class="textColS" width="22%">&nbsp;</td>
								<td class="inputCol" width="10%" align="right">&nbsp;</td>
								<td class="textColS" width="22%">&nbsp;</td>
								<td class="inputCol" width="10%" align="right">&nbsp;</td>
							</tr>
							<tr>
								<td class="textColS" >1st <%=MessageResourceUtil.getTextDescription(request, "MONTHLY_INSTALMENT_AMOUNT") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getMonthlyIntalmentAmtOne())%></td>
								<td class="textColS" >&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "FIRST_INTEREST_RATE") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getFirstTierRate())%></td>
								<td class="textColS" >&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "FIRST_TIER_TERM") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getFirstTierTerm())%></td>
								
							</tr>
							<tr>
								<td class="textColS" >2nd <%=MessageResourceUtil.getTextDescription(request, "MONTHLY_INSTALMENT_AMOUNT") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getMonthlyIntalmentAmtTwo())%></td>
								<td class="textColS" >&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "SECOND_INTEREST_RATE") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getSecondTierRate())%></td>
								<td class="textColS" >&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "SECOND_TIER_TERM") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getSecondTierTerm())%></td>
								
							</tr>
							<tr>
								<td class="textColS" >3nd <%=MessageResourceUtil.getTextDescription(request, "MONTHLY_INSTALMENT_AMOUNT") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getMonthlyIntalmentAmtThree())%></td>
								<td class="textColS" >&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "THIRD_INTEREST_RATE") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getThirdTierRate())%></td>
								<td class="textColS" >&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "THIRD_TIER_TERM") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getThirdTierTerm())%></td>
							</tr>
							<tr>
								<td class="textColS" >4nd <%=MessageResourceUtil.getTextDescription(request, "MONTHLY_INSTALMENT_AMOUNT") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getMonthlyIntalmentAmtFour())%></td>
								<td class="textColS" >&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "FORTH_INTEREST_RATE") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getForthTierRate())%></td>
								<td class="textColS" >&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "FORTH_TIER_TERM") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getForthTierTerm())%></td>
							</tr>
							<tr>
								<td colspan="7" height="3"></td>
							</tr>			
						</table>
						</fieldset>
					</td></tr>
				<%} else { %>
				<%// Auto Loan %>
					<tr class="sidebar10"> <td align="center">
						<fieldset><LEGEND><font class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "LOAN_DETAIL") %></font></Legend><br>
						<table cellpadding="0" cellspacing="1" border="0" width="99%" align="center" >		
							<tr>
								<td class="textColS" width="23%"><%=MessageResourceUtil.getTextDescription(request, "LOAN_AMOUNT_APPLIED") %> :</td>
								<td class="inputCol" width="15%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getLoanAmt())%></td>
								<td class="textColS" width="18%"><%=MessageResourceUtil.getTextDescription(request, "TERM") %> :</td>
								<td class="inputCol" width="15%" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getInstallment1())%></td>
								<td class="textColS"></td>
							</tr>
							<tr>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "DOWN_PAYMENT") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfDownPayment())%></td>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "EFFECTIVE_RATE") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getEffectiveRate())%></td>
								<td class="textColS"></td>
							</tr>
							<tr>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "FINANCIAL_AMT") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfFinancialAmt())%></td>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "IRR_RATE") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getIRRRate())%></td>
								<td class="textColS"></td>
							</tr>
							<tr>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "BALLOON_AMT") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfBalloonAmt())%></td>
								<td class="textColS" colspan="3"></td>
							</tr>
							<tr>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "RATE") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getRate1())%></td>
								<td class="textColS" colspan="3"></td>
							</tr>
							<tr>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "INSTALLMENT") %> :</td>
								<%if(com.eaf.orig.shared.constant.OrigConstant.INSTALLMENT_TYPE_STEP.equals(loanDataM.getInstallmentFlag())){%>
								    <td class="inputCol" align="right" colspan="3"><%=utility.getStepInstallmentTable(loanDataM.getStepInstallmentVect(),request)%></td>
								    <td class="textColS">&nbsp;</td>
							  	<%}else{ %>
									<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfInstallment1())%></td>
									<td class="textColS" colspan="3"></td>
								<%}%>
							</tr>
							<tr>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "RV") %> :</td>
								<td class="inputCol" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfRV()) %></td>
								<td class="textColS" colspan="3"></td>
							</tr>
							<tr>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "HIRE_PURCHASE_AMT") %>/<%=MessageResourceUtil.getTextDescription(request, "LEASE_AMT") %> :</td>
								<td class="inputCol" align="right"><%= ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfHairPurchaseAmt())%></td>
								<td class="textColS" colspan="3"></td>
							</tr>
							<tr>
								<td colspan="5" height="3"></td>
							</tr>			
						</table>
						</fieldset>
					</td></tr>
				<%} %>
					<tr class="sidebar10"> <td align="center">	
						<fieldset><LEGEND><font class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "VERIFICATION_LIST_RESULT") %></font></Legend><br>
						<table cellpadding="0" cellspacing="1" border="0" width="99%" align="center" >		
							<tr>
								<td class="textColS" width="23%"><%=MessageResourceUtil.getTextDescription(request, "SCORING_RESULT") %> :</td>
								<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayHTML(	applicationDataM.getScorringResult())%></td>			
							</tr>
							<tr>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "APP_DEBT_BURDEN") %> :</td>
								<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayHTML(xrulesVerificationResultDataM.getDEBT_BDResult() )%></td>			
							</tr>
							<tr>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "POLICY_RULE_HIT_RESULT") %> :</td>
								<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayHTML(xrulesVerificationResultDataM.getPolicyRulesResult())%></td>			
							</tr>
							<tr>
								<td colspan="4" height="3"></td>
							</tr>
						</table>
						</fieldset>
					</td></tr>
				</table>
			</td></tr>
		</table>
		</td></tr>
	</table>
	</TD></TR>
</table>

<script language="JavaScript">
<% System.out.println(">>> searchType="+searchType);%>
function backToSearchType(){
	<%if(("Y").equals(proposalMenu)){%>
		appFormName.action.value="FirstAccess";
	<%}else{%>
		appFormName.action.value="FristApp";
	<%}%>
	appFormName.handleForm.value = "N";
	appFormName.submit(); 
}
</script>
