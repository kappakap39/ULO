<html><head><title>UNIVERSAL LOAN ORIGINATION SYSTEM</title></head>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.CorperatedDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.j2ee.pattern.util.ErrorUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" />

<%
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	
	ORIGUtility utility = new ORIGUtility();
	
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("CORPERATED_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	
	
	CorperatedDataM corperatedDataM = (CorperatedDataM) request.getSession().getAttribute("POPUP_DATA");
	System.out.print("<<<page corperated_popup.jsp>>> corperatedDataM ----> = " + corperatedDataM);
	
	Vector v = ORIGForm.getFormErrors();
	Vector vErrorFields = ORIGForm.getErrorFields();
	System.out.println("Error Size = " + v.size());
	for(int i=0; i<v.size();i++) {
		String errorMessage = (String)v.elementAt(i);
		out.println("<span class=\"TextWarningNormal\">*&nbsp;"+errorMessage+"</span><br>");
	}
	ORIGForm.setFormErrors(new Vector());
	
	String disabledBtn = "";
	if(!displayMode.equals(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT)){
		disabledBtn = ORIGDisplayFormatUtil.DISABLED;
	}
%>

<span id="errorMessage" class="TextWarningNormal"></span>
<table width="100%" border=0 cellpadding="0" cellspacing="0" align="left" onkeydown="testKeyDown()">
	<tr><td height="20">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" align="left" bgcolor="#FFFFFF">
			<tr><td colspan="3" class="sidebar8">
			<table cellSpacing="0" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr><td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "BALANCE_AND_INCOME") %> </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                              			<%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(MessageResourceUtil.getTextDescription(request, "SAVE"), displayMode,"button", "saveBnt", "button", "onClick=\"javascript:saveData()\" "+disabledBtn, "") %></td>
									<td><%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(MessageResourceUtil.getTextDescription(request, "CANCEL"), ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"button", "closeBnt", "button", "onClick=\"javascript:closePopup()\"", "") %>										
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
						<tr> <td  height="15"></td></tr>  
						<tr class="sidebar10"> <td align="center">
							<table cellpadding="0" cellspacing="0" width="100%" align="center">
							<tr>
								<td class="textColS" width="27%"></td>
								<td class="textColS" align="left" height="25"><b> <%= MessageResourceUtil.getTextDescription(request, "YEAR") %> </b> :</td>	
								<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(corperatedDataM.getYear(), displayMode, "5", "year", "textbox", "", "4")%></td>
							</tr>
							</table>
						</td></tr>
					</table>
				</td></tr>
				<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	
						<tr><td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "ASSET") %> </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table cellpadding="0" cellspacing="1" width="100%" align="center">	
								<tr>
									<td class="textColS" align="center"><b><%=MessageResourceUtil.getTextDescription(request, "DESCRIPTION") %></b></td>
									<td class="textColS" align="center"><b><%=MessageResourceUtil.getTextDescription(request, "AMOUNT") %></b></td>
									<td></td>
								</tr>
								<tr>
									<td class="textColS" bgcolor="#F4F4F4" align="left" colspan="3"><b><%=MessageResourceUtil.getTextDescription(request, "CURRENT_ASSET") %></b></td>									
								</tr>
								<tr>
									<td class="textColS" width="32%" align="right"><%=MessageResourceUtil.getTextDescription(request, "CASH_IN_HAND_AND_AT_BANK") %> :</td>
									<td class="inputCol" width="16%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getAsstCashInHandAtBank()), displayMode, "", "asstCashInHandAtBank", " textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
									<td></td>
								</tr>
								<tr>
									<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "ACCOUNT_RECEIVABLE_NET") %> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getAsstAccountReceivableNet()), displayMode, "", "asstAccountReceivableNet", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
									<td></td>
								</tr>
								<tr>
									<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "NOTES_RECEIVABLE") %> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getAsstNoteReceivable()), displayMode, "", "asstNoteReceivable", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
									<td></td>
								</tr>
								<tr>
									<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "ACCRUED_INCOME") %> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getAsstAccruedIncome()), displayMode, "", "asstAccruedIncome", "textboxCurrency","onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
									<td></td>
								</tr>				
								<tr>
									<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "INVENTORIES") %> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getAsstInventories()), displayMode, "", "asstInventories", "textboxCurrency","onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
									<td></td>
								</tr>
								<tr>
									<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "OTHER_CURRENT_ASSETS") %> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getAsstOtherCurAssets()), displayMode, "", "asstOtherCurAssets", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
									<td></td>
								</tr>
								<tr>
									<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "TOTAL_CURRENT_ASSETS") %> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getAsstTotalCurAssets()), displayMode, "", "asstTotalCurAssets", "textboxCurrencyReadOnly", "readonly", "15")%></td>
									<td></td>
								</tr>
								<tr>
									<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "DEPOSIT") %> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getAsstDeposit()), displayMode, "", "asstDeposit", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
									<td></td>
								</tr>
								<tr>
									<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "PROPERTY_PLANT_EQUIPMENT_NET") %> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getAsstPropertyPlantEquipment()), displayMode, "", "asstPropertyPlantEquipment", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
									<td></td>
								</tr>
								<tr>
									<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "OTHER_ASSETS") %> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getAsstOtherAssets()), displayMode, "", "asstOtherAssets", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
									<td></td>
								</tr>
								<tr>
									<td class="textColS" colspan="3" height="15"></td>			
								</tr>
								<tr>
									<td class="textColS" align="right"><b><%=MessageResourceUtil.getTextDescription(request, "TOTAL_ASSETS") %> :</b></td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getAsstTotalAssets()), displayMode, "", "asstTotalAssets", "textboxCurrencyReadOnly", "readOnly", "15")%></td>
									<td></td>
								</tr>
							</table>
						</td></tr>
					</table>
				</td></tr>
				<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	
						<tr><td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "LIABILITIES") %> </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table cellpadding="0" cellspacing="1" width="100%" align="center">	
							<tr>
								<td class="textColS" align="center"><b><%=MessageResourceUtil.getTextDescription(request, "DESCRIPTION") %></b></td>
								<td class="textColS" align="center"><b><%=MessageResourceUtil.getTextDescription(request, "AMOUNT") %></b></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" bgcolor="#F4F4F4" align="left" colspan="3"><b><%=MessageResourceUtil.getTextDescription(request, "CURRENT_LIABILITIES") %></b></td>					
							</tr>
							<tr>
								<td class="textColS" width="32%" align="right"><%=MessageResourceUtil.getTextDescription(request, "BANK_OVERDRAFT_AND_LOAN") %> :</td>
								<td class="inputCol" width="16%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getLbBankOverdraftAndLoan()), displayMode, "", "lbBankOverdraftAndLoan", "textboxCurrency",  "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "ACCOUNT_PAYABLE") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getLbAccountPayable()), displayMode, "", "lbAccountPayable", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "CURRENT_PORTION_OF_LONG_DEBT") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getLbCurrentPortionOfLongTermDebt()), displayMode, "", "lbCurrentPortionOfLongTermDebt", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "NOTES_PAYABLE") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getLbNotesPayable()), displayMode, "", "lbNotesPayable", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "LOANS_FROM_FINANCIAL_INSTITUTIONS") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getLbLoanFromFinanInstitution()), displayMode, "", "lbLoanFromFinanInstitution", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "ACCRUED_EXPENSES") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getLbAccruedExpense()), displayMode, "", "lbAccruedExpense", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "OTHER_CURRENT_LIABILITIES") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getLbOtherCurLb()), displayMode, "", "lbOtherCurLb", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "TOTAL_CURRENT_LIABILITIES") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getLbTotalCurLb()), displayMode, "", "lbTotalCurLb", "textboxCurrencyReadOnly", "readonly", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "LONG_TERM_DEBT") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getLbLongTermDebt()), displayMode, "", "lbLongTermDebt", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "PAYABLE_HIRE_PURCHASE") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getLbPayHirePurchase()), displayMode, "", "lbPayHirePurchase", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" colspan="3" height="15"></td>			
							</tr>
							<tr>
								<td class="textColS" align="right"><b><%=MessageResourceUtil.getTextDescription(request, "TOTAL_LIABILITIES") %> :</b></td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getLbTotalLb()), displayMode, "", "lbTotalLb", "textboxCurrencyReadOnly", "readonly", "15")%></td>
								<td></td>
							</tr>
							</table>
						</td></tr>
					</table>
				</td></tr>
				<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	
						<tr><td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "SHAREHOLDERS_EQUITY") %> </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table cellpadding="0" cellspacing="1" width="100%" align="center">
							<tr>
								<td class="textColS" align="center"><b><%=MessageResourceUtil.getTextDescription(request, "DESCRIPTION") %></b></td>
								<td class="textColS" align="center"><b><%=MessageResourceUtil.getTextDescription(request, "AMOUNT") %></b></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right" width="32%"><%=MessageResourceUtil.getTextDescription(request, "SHARE_CAPITY") %></td>	
								<td class="inputCol" width="16%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getShdEqShareCapital()), displayMode, "", "shdEqShareCapital", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>				
							</tr>				
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "ISSUED_AND_PAGE_UP_CAPITAL") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getShdEqIssuePaidUpCapital()), displayMode, "", "shdEqIssuePaidUpCapital", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "PREMIUM") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getShdEqPremium()), displayMode, "", "shdEqPremium", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" bgcolor="#F4F4F4" align="left" colspan="3"><b><%=MessageResourceUtil.getTextDescription(request, "RETAIN_EARNING") %></b></td>					
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "LEGAL_RESERVE") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getShdEqLegalReserve()), displayMode, "", "shdEqLegalReserve", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "UNAPPROPRIATED") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getShdEqUnappropriated()), displayMode, "", "shdEqUnappropriated", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>								
							<tr>
								<td class="textColS" colspan="3" height="15"></td>	
							</tr>
							<tr>
								<td class="textColS" align="right"><b><%=MessageResourceUtil.getTextDescription(request, "TOTAL_SHAREHOLDERS_EQUITY") %> :</b></td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getShdEqTotalShareHdEqity()), displayMode, "", "shdEqTotalShareHdEqity", "textboxCurrencyReadOnly", "readonly", "15")%></td>
								<td></td>
							</tr>
							</table>
						</td></tr>
					</table>
				</td></tr>
				<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	
						<tr><td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "INCOME_STATEMENT") %> </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table cellpadding="0" cellspacing="1" width="100%" align="center">
							<tr>	
								<td class="textColS" align="center"><b><%=MessageResourceUtil.getTextDescription(request, "DESCRIPTION") %></b></td>
								<td class="textColS" align="center"><b><%=MessageResourceUtil.getTextDescription(request, "AMOUNT") %></b></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" bgcolor="#F4F4F4" align="left" colspan="3"><b><%=MessageResourceUtil.getTextDescription(request, "REVENUE") %></b></td>					
							</tr>
							<tr>
								<td class="textColS" width="32%" align="right"><%=MessageResourceUtil.getTextDescription(request, "SALES") %> :</td>
								<td class="inputCol" width="16%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getIncStmtSales()), displayMode, "", "IncStmtSales", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "OTHER_INCOME") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getIncStmtOtherIncome()), displayMode, "", "IncStmtOtherIncome", "textboxCurrency","onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "TOTAL_REVENUE") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getIncStmtTotalRevenue()), displayMode, "", "IncStmtTotalRevenue", "textboxCurrencyReadOnly", "readonly", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" bgcolor="#F4F4F4" align="left" colspan="3"><b><%=MessageResourceUtil.getTextDescription(request, "EXPENSES") %></b></td>					
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "COST_OF_SALES") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getIncStmtCostofSale()), displayMode, "", "IncStmtCostofSale", "textboxCurrency","onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td> 
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "S_OR_A_EXPENSES") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getIncStmtSAExpense()), displayMode, "", "IncStmtSAExpense", "textboxCurrency", "onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "SHARE_OF_NET_LOSS_FROM_SUBSIDIARIES") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getIncStmtShareNetLossSubsidiaries()), displayMode, "", "IncStmtShareNetLossSubsidiaries", "textboxCurrency","onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "LOSS_FROM_GOOD_DETERIORATION") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getIncStmtLossGoodsDeterioration()), displayMode, "", "IncStmtLossGoodsDeterioration", "textboxCurrency","onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "TOTAL_EXPENSES") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getIncStmtTotalExpense()), displayMode, "", "IncStmtTotalExpense", "textboxCurrencyReadOnly", "readonly", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "EARNING_BEFORE_INTEREST_AND_TAX") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getIncStmtEarnBfInterestAndTax()), displayMode, "", "IncStmtEarnBfInterestAndTax", "textboxCurrencyReadOnly", "readonly", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "INTEREST_EXPENSES") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getIncStmtInterestExpense()), displayMode, "", "IncStmtInterestExpense", "textboxCurrency","onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "EARNING_BEFORE_TAX") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getIncStmtEarnBfTax()), displayMode, "", "IncStmtEarnBfTax", "textboxCurrencyReadOnly", "readonly", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "INCOME_TAX") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getIncStmtIncTax()), displayMode, "", "IncStmtIncTax", "textboxCurrency","onKeyPress=\"isNumberOnKeyPressNegative(this);\" onkeyup=\"isNumberOnkeyUp(this);\" onblur=\"javascript:calRatio();addComma(this);addDecimalFormat(this);\" onfocus=\"removeCommaField(this)\" ", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "NET_INCOME") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getIncStmtNetIncome()), displayMode, "", "IncStmtNetIncome", "textboxCurrencyReadOnly", "readonly", "15")%></td>
								<td></td>
							</tr>
							</table>
						</td></tr>
					</table>
				</td></tr>
				<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	
						<tr><td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "RATIOS") %> </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table cellpadding="0" cellspacing="1" width="100%" align="center">
							<tr>					
								<td class="textColS" align="center"><b><%=MessageResourceUtil.getTextDescription(request, "DESCRIPTION") %></b></td>
								<td class="textColS" align="center"><b><%=MessageResourceUtil.getTextDescription(request, "AMOUNT") %></b></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" width="32%" align="right"><%=MessageResourceUtil.getTextDescription(request, "CURRENT_RATIO") %> :</td>
								<td class="inputCol" width="16%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getRatCurrentRatio()), displayMode, "", "ratCurrentRatio", "textboxCurrencyReadOnly","readonly", "15")%></td>
								<td></td>
							</tr>				
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "QUICK_RATIO") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getRatQuickRatio()), displayMode, "", "ratQuickRatio", "textboxCurrencyReadOnly", "readonly", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "DEBT_TO_EQUITY") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getRatDebtToEquity()), displayMode, "", "ratDebtToEquity", "textboxCurrencyReadOnly", "readonly", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "GROSS_PROFIT_MARGIN") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getRatGrossProfitMargin()), displayMode, "", "ratGrossProfitMargin", "textboxCurrencyReadOnly", "readonly", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "NET_PROFIT_OR_SALES") %> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(corperatedDataM.getRatNetProfitSale()), displayMode, "", "ratNetProfitSale", "textboxCurrencyReadOnly", "readonly", "15")%></td>
								<td></td>
							</tr>
							<tr>
								<td class="textColS" colspan="3" height="15"></td>
							</tr>
							</table>
						</td></tr>
					</table>
				</td></tr>
			</table>
			</td></tr>
		</table>
	</td></tr>
</table>
<input type="hidden" name="seq" value="<%=corperatedDataM.getSeq() %>">
<input type="hidden" name="yearFromLink" value="<%=corperatedDataM.getYearTemp() %>">  

<script language="JavaScript">
function showZero(obj){
	if(obj.value == ''){
		obj.value = 0;
	}
}
function saveData(){
	if(validateData()){
		form = document.appFormName;
		form.action.value = "SaveCorperated";
		form.handleForm.value = "N";
		form.submit();
	}
}
function closePopup(){
	form = document.appFormName;
	form.action.value = "CloseCorperate";
	form.handleForm.value = "N";
	form.submit();
} 
function validateData(){
	var errStr = '';
	form = document.appFormName;
	
	form.asstTotalAssets.className = "textboxCurrencyReadOnly";
	form.lbTotalLb.className = "textboxCurrencyReadOnly";
	form.shdEqTotalShareHdEqity.className = "textboxCurrencyReadOnly";
	form.IncStmtCostofSale.className = "textboxCurrency";
	form.year.className = "textbox";
	
	if(form.asstTotalAssets.value!=null && form.asstTotalAssets.value!='' && !parseInt(form.asstTotalAssets.value)>0){
		form.asstTotalAssets.className = "textboxCurrencyReadOnlyRed";
		form.asstTotalAssets.focus();
		alert('<%=ErrorUtil.getShortErrorMessage(request,"TOTAL_ASSET_MUST_MORE_THAN_ZERO")%>');
		return false;
	}
	if(form.lbTotalLb.value!=null && form.lbTotalLb.value!='' && !parseInt(form.lbTotalLb.value)>0){
		form.lbTotalLb.className = "textboxCurrencyReadOnlyRed";
		form.lbTotalLb.focus();
		alert('<%=ErrorUtil.getShortErrorMessage(request,"TOTAL_LIABILITIES_MUST_MORE_THAN_ZERO")%>');
		return false;
	}
	if(form.shdEqTotalShareHdEqity.value!=null && form.shdEqTotalShareHdEqity.value!='' && !parseInt(form.shdEqTotalShareHdEqity.value)>0){
		form.shdEqTotalShareHdEqity.className = "textboxCurrencyReadOnlyRed";
		form.shdEqTotalShareHdEqity.focus();
		alert('<%=ErrorUtil.getShortErrorMessage(request,"TOTAL_SHARE_EQ_MUST_MORE_THAN_ZERO")%>');
		return false;
	}
	if(form.IncStmtCostofSale.value!=null && form.IncStmtCostofSale.value!='' && !parseInt(form.IncStmtCostofSale.value)>0){
		form.IncStmtCostofSale.className = "textboxCurrencyRed";
		form.IncStmtCostofSale.focus();
		alert('<%=ErrorUtil.getShortErrorMessage(request,"COST_OF_SALES_MUST_MORE_THAN_ZERO")%>');
		return false;
	}
	if(form.year.value!=null && form.year.value==''){
		form.year.className = "textboxRed";
		form.year.focus();
		alert('<%=ErrorUtil.getShortErrorMessage(request,"YEAR_IS_REQUIRED")%>');
		return false;
	}
	var totalAsset = removeCommas(form.asstTotalAssets.value);
	var totalLBL = removeCommas(form.lbTotalLb.value);
	var totalShdEq = removeCommas(form.shdEqTotalShareHdEqity.value);
	//alert( totalAsset+" "+totalLBL+" "+totalShdEq);
	if(Number(totalAsset)!= (Number(totalLBL)+Number(totalShdEq))){
		form.asstTotalAssets.className = "textboxCurrencyReadOnlyRed";
		form.lbTotalLb.className = "textboxCurrencyReadOnlyRed";
		form.shdEqTotalShareHdEqity.className = "textboxCurrencyReadOnlyRed";
		form.asstTotalAssets.focus();
		alert('<%=ErrorUtil.getShortErrorMessage(request,"AMOUNT_ASSETS_NOT_BALANCE")%>');
		return false;
	}
	return true;
}
function calAsset(){
	form = document.appFormName;
	var asstCashInHandAtBank = removeCommas(form.asstCashInHandAtBank.value);
	var asstAccountReceivableNet = removeCommas(form.asstAccountReceivableNet.value);
	var asstCashInHandAtBank = removeCommas(form.asstCashInHandAtBank.value);
	var asstNoteReceivable = removeCommas(form.asstNoteReceivable.value);
	var	asstAccruedIncome = removeCommas(form.asstAccruedIncome.value);
	var	asstInventories = removeCommas(form.asstInventories.value);
	var	asstOtherCurAssets = removeCommas(form.asstOtherCurAssets.value);
	var	asstDeposit = removeCommas(form.asstDeposit.value);
	var	asstPropertyPlantEquipment = removeCommas(form.asstPropertyPlantEquipment.value);
	var	asstOtherAssets = removeCommas(form.asstOtherAssets.value);
	
	if(asstCashInHandAtBank == ''){ 
		asstCashInHandAtBank = 0;
	}
	if(asstAccountReceivableNet == ''){
		asstAccountReceivableNet = 0;
	}
	if(asstCashInHandAtBank == ''){
		asstCashInHandAtBank = 0;
	}
	if(asstNoteReceivable == ''){
		asstNoteReceivable = 0;
	}
	if(asstAccruedIncome == ''){
		asstAccruedIncome = 0;
	}
	if(asstInventories == ''){
		asstInventories = 0;
	}
	if(asstOtherCurAssets == ''){
		asstOtherCurAssets = 0;
	}
	if(asstDeposit == ''){
		asstDeposit = 0;
	}
	if(asstPropertyPlantEquipment == ''){
		asstPropertyPlantEquipment = 0;
	}
	if(asstOtherAssets == ''){
		asstOtherAssets = 0;
	}
	//asstTotalCurAssets.toFixed(2)
	var asstTotalCurAssets = parseFloat(asstAccountReceivableNet) + parseFloat(asstCashInHandAtBank) 
							+ parseFloat(asstNoteReceivable) + parseFloat(asstAccruedIncome) 
							+ parseFloat(asstInventories) + parseFloat(asstOtherCurAssets);
	
	form.asstTotalCurAssets.value = Math.round(asstTotalCurAssets*100)/100;
	addCommaNotChkNumber('asstTotalCurAssets');
	var	asstTotalAssets = parseFloat(asstDeposit) + parseFloat(asstPropertyPlantEquipment) 
							+ parseFloat(asstOtherAssets) + parseFloat(asstTotalCurAssets);
	form.asstTotalAssets.value = Math.round(asstTotalAssets*100)/100;
	addCommaNotChkNumber('asstTotalAssets');
}
function calLiab(){
	form = document.appFormName;
	var lbBankOverdraftAndLoan = removeCommas(form.lbBankOverdraftAndLoan.value);
	var lbAccountPayable = removeCommas(form.lbAccountPayable.value);
	var lbCurrentPortionOfLongTermDebt = removeCommas(form.lbCurrentPortionOfLongTermDebt.value);
	var lbNotesPayable = removeCommas(form.lbNotesPayable.value);
	var	lbLoanFromFinanInstitution = removeCommas(form.lbLoanFromFinanInstitution.value);
	var	lbAccruedExpense = removeCommas(form.lbAccruedExpense.value);
	var	lbOtherCurLb = removeCommas(form.lbOtherCurLb.value);	

	var	lbLongTermDebt = removeCommas(form.lbLongTermDebt.value);
	var	lbPayHirePurchase = removeCommas(form.lbPayHirePurchase.value);	
	
	if(lbBankOverdraftAndLoan == ''){ 
		lbBankOverdraftAndLoan = 0;
	}
	if(lbAccountPayable == ''){
		lbAccountPayable = 0;
	}
	if(lbCurrentPortionOfLongTermDebt == ''){
		lbCurrentPortionOfLongTermDebt = 0;
	}
	if(lbNotesPayable == ''){
		lbNotesPayable = 0;
	}
	if(lbLoanFromFinanInstitution == ''){
		lbLoanFromFinanInstitution = 0;
	}
	if(lbAccruedExpense == ''){
		lbAccruedExpense = 0;
	}
	if(lbOtherCurLb == ''){
		lbOtherCurLb = 0;
	}
	if(lbLongTermDebt == ''){
		lbLongTermDebt = 0;
	}
	if(lbPayHirePurchase == ''){
		lbPayHirePurchase = 0;
	}
	
	var lbTotalCurLb = parseFloat(lbBankOverdraftAndLoan) + parseFloat(lbAccountPayable) 
						+ parseFloat(lbCurrentPortionOfLongTermDebt) + parseFloat(lbNotesPayable) 
						+ parseFloat(lbLoanFromFinanInstitution) + parseFloat(lbAccruedExpense)
						+ parseFloat(lbOtherCurLb);
	form.lbTotalCurLb.value = Math.round(lbTotalCurLb*100)/100;
	addCommaNotChkNumber('lbTotalCurLb');
	var	lbTotalLb = parseFloat(lbLongTermDebt) + parseFloat(lbPayHirePurchase) + parseFloat(lbTotalCurLb);
	form.lbTotalLb.value = Math.round(lbTotalLb*100)/100;
	addCommaNotChkNumber('lbTotalLb');
}   

function calShareEq(){
	form = document.appFormName;
	var shdEqShareCapital = removeCommas(form.shdEqShareCapital.value);
	var shdEqIssuePaidUpCapital = removeCommas(form.shdEqIssuePaidUpCapital.value);
	var shdEqPremium = removeCommas(form.shdEqPremium.value);
	var shdEqLegalReserve = removeCommas(form.shdEqLegalReserve.value);
	var shdEqUnappropriated = removeCommas(form.shdEqUnappropriated.value);	 	
	
	
	if(shdEqShareCapital == ''){ 
		shdEqShareCapital = 0;
	}
	if(shdEqIssuePaidUpCapital == ''){ 
		shdEqIssuePaidUpCapital = 0;
	}
	if(shdEqPremium == ''){
		shdEqPremium = 0;
	}
	if(shdEqLegalReserve == ''){
		shdEqLegalReserve = 0;
	}
	if(shdEqUnappropriated == ''){
		shdEqUnappropriated = 0;
	}
	
	var shdEqTotalShareHdEqity = parseFloat(shdEqShareCapital) + parseFloat(shdEqIssuePaidUpCapital) + parseFloat(shdEqPremium) 
								+ parseFloat(shdEqLegalReserve) + parseFloat(shdEqUnappropriated);								
	form.shdEqTotalShareHdEqity.value = Math.round(shdEqTotalShareHdEqity*100)/100;
	addCommaNotChkNumber('shdEqTotalShareHdEqity');
}  
      
function calIncomeSt(){
	form = document.appFormName;	
	var IncStmtSales = removeCommas(form.IncStmtSales.value);
	var IncStmtOtherIncome = removeCommas(form.IncStmtOtherIncome.value);	
	var IncStmtCostofSale = removeCommas(form.IncStmtCostofSale.value);
	var IncStmtSAExpense = removeCommas(form.IncStmtSAExpense.value);
	var IncStmtShareNetLossSubsidiaries = removeCommas(form.IncStmtShareNetLossSubsidiaries.value);
	var IncStmtLossGoodsDeterioration = removeCommas(form.IncStmtLossGoodsDeterioration.value);	
	var IncStmtInterestExpense = removeCommas(form.IncStmtInterestExpense.value);
	var IncStmtIncTax = removeCommas(form.IncStmtIncTax.value);		
	
	if(IncStmtSales == ''){ 
		IncStmtSales = 0;
	}
	if(IncStmtOtherIncome == ''){
		IncStmtOtherIncome = 0;
	}
	if(IncStmtCostofSale == ''){
		IncStmtCostofSale = 0;
	}
	if(IncStmtSAExpense == ''){
		IncStmtSAExpense = 0;
	}
	if(IncStmtShareNetLossSubsidiaries == ''){
		IncStmtShareNetLossSubsidiaries = 0;
	}
	if(IncStmtLossGoodsDeterioration == ''){
		IncStmtLossGoodsDeterioration = 0;
	}
	if(IncStmtInterestExpense == ''){
		IncStmtInterestExpense = 0;
	}
	if(IncStmtIncTax == ''){
		IncStmtIncTax = 0;
	}
	
	var IncStmtTotalRevenue = parseFloat(IncStmtSales) + parseFloat(IncStmtOtherIncome);
	//alert(IncStmtTotalRevenue);
	form.IncStmtTotalRevenue.value = Math.round(IncStmtTotalRevenue*100)/100;
	addCommaNotChkNumber('IncStmtTotalRevenue');	
	var IncStmtTotalExpense = parseFloat(IncStmtCostofSale) + parseFloat(IncStmtSAExpense) 
								+ parseFloat(IncStmtShareNetLossSubsidiaries) + parseFloat(IncStmtLossGoodsDeterioration);								
	form.IncStmtTotalExpense.value = Math.round(IncStmtTotalExpense*100)/100;
	//alert(IncStmtTotalExpense);
	addCommaNotChkNumber('IncStmtTotalExpense');	
	var IncStmtEarnBfInterestAndTax = parseFloat(IncStmtTotalRevenue) - parseFloat(IncStmtTotalExpense); 															
	form.IncStmtEarnBfInterestAndTax.value = Math.round(IncStmtEarnBfInterestAndTax*100)/100;
//	alert("1 : "+IncStmtEarnBfInterestAndTax);
	addCommaNotChkNumber('IncStmtEarnBfInterestAndTax');
//	alert("2 : "+IncStmtEarnBfInterestAndTax);
	var IncStmtEarnBfTax = parseFloat(IncStmtEarnBfInterestAndTax) - parseFloat(IncStmtInterestExpense);						
	form.IncStmtEarnBfTax.value = Math.round(IncStmtEarnBfTax*100)/100;
	//alert(IncStmtEarnBfTax);
	addCommaNotChkNumber('IncStmtEarnBfTax');
	var IncStmtNetIncome =parseFloat(IncStmtEarnBfTax) - parseFloat(IncStmtIncTax); 
	form.IncStmtNetIncome.value = Math.round(IncStmtNetIncome*100)/100;
	//alert(IncStmtNetIncome);
	addCommaNotChkNumber('IncStmtNetIncome');	
}   
//function calAsset(){}
//function calLiab(){}
//function calShareEq(){} 
//function calIncomeSt(){} 
function calRatio(){
	form = document.appFormName;	
	calAsset();
	//alert("1 : ");
	calLiab();
	//alert("2 : ");
	calShareEq();	
	//alert("3 : ");
	calIncomeSt();
	//alert("4 : ");
	
	var totalCA = parseFloat(removeCommas(form.asstTotalCurAssets.value)); //592483755.05;
	//alert("1 : "+totalCA);
	var totalCL = parseFloat(removeCommas(form.lbTotalCurLb.value));	//295190265.61;
	//alert("2 : "+totalCL);
	var inventories = parseFloat(removeCommas(form.asstInventories.value));	//520642620.72;
	//alert("3 : "+inventories);
	var totalLiab = parseFloat(removeCommas(form.lbTotalLb.value));	//521348511.9;
	//alert("4 : "+totalLiab);
	var totalEq = parseFloat(removeCommas(form.shdEqTotalShareHdEqity.value));	//298359610.76;
	//alert("5 : "+totalEq);
	var sales = parseFloat(removeCommas(form.IncStmtSales.value));	//731644406.52;
	//alert("6 : "+sales);
	var costOfSales = parseFloat(removeCommas(form.IncStmtCostofSale.value));	//571457390.92;
	//alert("7 : "+costOfSales);
	var totalRevenue = parseFloat(removeCommas(form.IncStmtTotalRevenue.value));	//737562596.51;
	//alert("8 : "+totalRevenue);
	var netIncome = parseFloat(removeCommas(form.IncStmtNetIncome.value));	//13750726.76;
	//alert("9 : "+netIncome);

	var ratCurrentRatio = new Number();
	if(totalCL != 0){
		 ratCurrentRatio = totalCA/totalCL;	
	}	
	form.ratCurrentRatio.value = ratCurrentRatio.toFixed(2);
	addCommaNotChkNumber('ratCurrentRatio');
//	alert(ratCurrentRatio);	
	
	var ratQuickRatio = new Number();
	if(parseFloat(totalCL) != 0){
		 ratQuickRatio = (totalCA-inventories)/totalCL;	
	}
	form.ratQuickRatio.value = ratQuickRatio.toFixed(2);
	addCommaNotChkNumber('ratQuickRatio');
//	alert(ratQuickRatio);
	
	var ratDebtToEquity = new Number();
	if(totalEq != 0){
		 ratDebtToEquity = totalLiab/totalEq;	
	}	
	form.ratDebtToEquity.value = ratDebtToEquity.toFixed(2);
	addCommaNotChkNumber('ratDebtToEquity');
//	alert(ratDebtToEquity);
	
	var ratGrossProfitMargin = new Number();
	if(sales != 0){
		 ratGrossProfitMargin = ((sales-costOfSales)/sales)*100;	
	}
	form.ratGrossProfitMargin.value = ratGrossProfitMargin.toFixed(2);	
	addCommaNotChkNumber('ratGrossProfitMargin');
//	alert(ratGrossProfitMargin);
	
	var	ratNetProfitSale = new Number();
	if(totalRevenue != 0){
		 ratNetProfitSale = (netIncome/totalRevenue)*100;			 
	}
	form.ratNetProfitSale.value = ratNetProfitSale.toFixed(2);	
	addCommaNotChkNumber('ratNetProfitSale');
//	alert(ratNetProfitSale);
}  
</script>
<%	//set current screen to main Form //ratCurrentRatio ratQuickRatio ratDebtToEquity ratGrossProfitMargin ratNetProfitSale
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>