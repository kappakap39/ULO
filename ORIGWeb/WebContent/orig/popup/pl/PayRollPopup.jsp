<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPayRollDetailDataM"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPayRollDataM"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<%
	PLApplicationDataM applicationM  = PLORIGForm.getAppForm();	
	if(null == applicationM) applicationM = new PLApplicationDataM();	
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	
	PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
	if(null == xrulesVerM){
		xrulesVerM = new PLXRulesVerificationResultDataM();
	}
	
	PLXRulesPayRollDataM payrollM = xrulesVerM.getxRulesPayRolls();
	
	if(null == payrollM){
		payrollM = new PLXRulesPayRollDataM();
	}		
 %>
<div class="PanelFirst">
	<%	if(!OrigUtil.isEmptyVector(payrollM.getxRulesPayRollDetailVect())){ %>
		<div class="PanelSecond TextHeaderNormal">
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_ID_NO") %>&nbsp;:</td>
						<td class="textL" width="15%"><%=HTMLRenderUtil.displayHTML((String) payrollM.getIdno())%></td>
						<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_CUSTOMER_NUMBER") %>&nbsp;:</td>
						<td class="textL" width="15%"><%=HTMLRenderUtil.displayHTML((String) payrollM.getCusNo())%></td>												
						<td class="textR" nowrap="nowrap"  width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_NAME_THAI") %>&nbsp;:</td>
						<td class="textL" width="15%">
							<%=HTMLRenderUtil.displayHTML((String) payrollM.getFirstName())%>&nbsp;&nbsp;
							<%=HTMLRenderUtil.displayHTML((String) payrollM.getLastName())%>
						</td>
					</tr>
					<tr>
						<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_SUMMARY_INCOME") %>&nbsp;:</td>
						<td class="textL" width="15%"><%=DataFormatUtility.displayCommaNumber(payrollM.getSumSalary())%></td>
						<td class="textR" nowrap="nowrap" width="14%"></td>
						<td class="textL" width="15%"></td>												
						<td class="textR" nowrap="nowrap" width="14%"></td>
						<td class="textL" width="15%"></td>
					</tr>
				</table>
			 <%	if(!OrigUtil.isEmptyVector(payrollM.getxRulesPayRollDetailVect())){ 
			 		int i = 0;
			 		for(PLXRulesPayRollDetailDataM payrollDetailM :payrollM.getxRulesPayRollDetailVect()){
			 			i++;
			 %>	
			            <fieldset class="field-set">
			            <legend><%=i%></legend>
				 		<table class="FormFrame">
							<tr>
								<td class="textL" nowrap="nowrap" width="14%">&nbsp;&nbsp;&nbsp;<b><%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_INFORMATION") %></b></td>
								<td class="textL" width="15%"></td>
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>												
								<td class="textR" nowrap="nowrap"  width="14%"></td>
								<td class="textL" width="15%"></td>
							</tr>
							<tr>
								<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_CUSTOMER_ACCOUNT_NO") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getCustomerAccount())%></td>
								<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_AGE_PAYROLL") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=DataFormatUtility.displayIntToString(payrollDetailM.getAge())%></td>												
								<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_NAME_OF_WORK") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getCompanyName())%></td>
							</tr>	
							<tr>
								<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_COMPANY_ACCOUNT_NO") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getCompanyAccount())%></td>
								<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_PAYROLL_TYPE") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getPayrollType())%></td>												
								<td class="textR" nowrap="nowrap"  width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_INCOME_DATE") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=DataFormatUtility.IntToString(payrollDetailM.getSalaryDate())%></td>
							</tr>	
							<tr>
								<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_BILL_ON_DATE") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getCycleCut())%></td>
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>												
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>
							</tr>
							<tr>
								<td class="textL" nowrap="nowrap" width="14%">&nbsp;&nbsp;&nbsp;<b><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_OTHER_CUSTOMER_ACCT") %></b></td>
								<td class="textL" width="15%"></td>
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>												
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>
							</tr>
							<tr>
								<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_CUSTOMER_ACCOUNT_NO2") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getCustomerAcct2())%></td>	
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>												
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>
							</tr>	
							<tr>
								<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_CUSTOMER_ACCOUNT_NO3") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getCustomerAcct3())%></td>	
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>												
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>
							</tr>	
							<tr>
								<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_CUSTOMER_ACCOUNT_NO4") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getCustomerAcct4())%></td>	
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>												
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>
							</tr>	
							<tr>
								<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_CUSTOMER_ACCOUNT_NO5") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getCustomerAcct5())%></td>	
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>												
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>
							</tr>
							<tr>
								<td class="textL" nowrap="nowrap" width="14%">&nbsp;&nbsp;&nbsp;<b><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_KEC_PAYROLL_PROJECT") %></b></td>
								<td class="textL" width="15%"></td>
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>												
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>
							</tr>	
							<tr>
								<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_NORMAL_INTERRATE") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getProjectCode1())%></td>
								<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_LOWER_INTERRATE_1") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getProjectCode2())%></td>												
								<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_LOWER_INTERRATE_2") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getProjectCode3())%></td>
							</tr>
							<tr>
								<td class="textL" nowrap="nowrap" width="14%">&nbsp;&nbsp;&nbsp;<b><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_MONTH_SALARY_DATA") %></b></td>
								<td class="textL" width="15%"></td>
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>												
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>
							</tr>
				 		</table>
				 		<table class="TableFrame">
			 				<tr class="Header">
								<td width="7%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getSalary01Desc()) %></td>
								<td width="7%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getSalary02Desc()) %></td>
								<td width="7%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getSalary03Desc()) %></td>
								<td width="7%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getSalary04Desc()) %></td>
								<td width="7%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getSalary05Desc()) %></td>
								<td width="7%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getSalary06Desc()) %></td>
			 				</tr>
			 				<tr class="ResultData">
								<td><%=DataFormatUtility.displayCommaNumber(payrollDetailM.getSalary01())%></td>
								<td><%=DataFormatUtility.displayCommaNumber(payrollDetailM.getSalary02())%></td>
								<td><%=DataFormatUtility.displayCommaNumber(payrollDetailM.getSalary03())%></td>
								<td><%=DataFormatUtility.displayCommaNumber(payrollDetailM.getSalary04())%></td>
								<td><%=DataFormatUtility.displayCommaNumber(payrollDetailM.getSalary05())%></td>
								<td><%=DataFormatUtility.displayCommaNumber(payrollDetailM.getSalary06())%></td>		
			 				</tr>
			 				<tr class="Header">
								<td width="7%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getSalary07Desc()) %></td>
								<td width="7%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getSalary08Desc()) %></td>
								<td width="7%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getSalary09Desc()) %></td>
								<td width="7%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getSalary10Desc()) %></td>
								<td width="7%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getSalary11Desc()) %></td>
								<td width="7%"><%=HTMLRenderUtil.displayHTML((String) payrollDetailM.getSalary12Desc()) %></td>
			 				</tr>
			 				<tr class="ResultData">
			 					<td><%=DataFormatUtility.displayCommaNumber(payrollDetailM.getSalary07())%></td>
								<td><%=DataFormatUtility.displayCommaNumber(payrollDetailM.getSalary08())%></td>
								<td><%=DataFormatUtility.displayCommaNumber(payrollDetailM.getSalary09())%></td>
								<td><%=DataFormatUtility.displayCommaNumber(payrollDetailM.getSalary10())%></td>
								<td><%=DataFormatUtility.displayCommaNumber(payrollDetailM.getSalary11())%></td>
								<td><%=DataFormatUtility.displayCommaNumber(payrollDetailM.getSalary12())%></td>
			 				</tr>
			 				<tr class="ResultData">
			 					<td colspan="5"><div class='textR'><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_SUMMARY") %></div></td>			 					
							<%
								BigDecimal sumSalary = new BigDecimal(0);
								if(null != payrollDetailM.getSalary01()){sumSalary = sumSalary.add(payrollDetailM.getSalary01());}
								if(null != payrollDetailM.getSalary02()){sumSalary = sumSalary.add(payrollDetailM.getSalary02());}
								if(null != payrollDetailM.getSalary03()){sumSalary = sumSalary.add(payrollDetailM.getSalary03());}
								if(null != payrollDetailM.getSalary04()){sumSalary = sumSalary.add(payrollDetailM.getSalary04());}
								if(null != payrollDetailM.getSalary05()){sumSalary = sumSalary.add(payrollDetailM.getSalary05());}
								if(null != payrollDetailM.getSalary06()){sumSalary = sumSalary.add(payrollDetailM.getSalary06());}
								if(null != payrollDetailM.getSalary07()){sumSalary = sumSalary.add(payrollDetailM.getSalary07());}
								if(null != payrollDetailM.getSalary08()){sumSalary = sumSalary.add(payrollDetailM.getSalary08());}
								if(null != payrollDetailM.getSalary09()){sumSalary = sumSalary.add(payrollDetailM.getSalary09());}
								if(null != payrollDetailM.getSalary10()){sumSalary = sumSalary.add(payrollDetailM.getSalary10());}
								if(null != payrollDetailM.getSalary11()){sumSalary = sumSalary.add(payrollDetailM.getSalary11());}
								if(null != payrollDetailM.getSalary12()){sumSalary = sumSalary.add(payrollDetailM.getSalary12());}
							 %>															
								<td><%=DataFormatUtility.displayCommaNumber(sumSalary)%></td>	
			 				</tr>
				 		</table>
				 		<table class="FormFrame">
				 			<tr></tr>
							<tr>
								<td class="textL" nowrap="nowrap" width="14%">&nbsp;&nbsp;&nbsp;<b><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_AVERAGE_3_MONTH") %></b></td>
								<td class="textL" width="15%"></td>
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>												
								<td class="textR" nowrap="nowrap"  width="14%"></td>
								<td class="textL" width="15%"></td>
							</tr>
							<tr>
								<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_AVERAGE_INCOME") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=DataFormatUtility.displayCommaNumber(payrollDetailM.getAvgIncome3M())%></td>
								<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_SOCIAL_SECURITY") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=DataFormatUtility.displayCommaNumber(payrollDetailM.getSso())%></td>												
								<td class="textR" nowrap="nowrap"  width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_FOND") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=DataFormatUtility.displayCommaNumber(payrollDetailM.getPvf())%></td>
							</tr>
							<tr>
								<td class="textR" nowrap="nowrap" width="14%"><%=MessageResourceUtil.getTextDescription(request, "PAYROLL_CROSS_INCOME") %>&nbsp;:</td>
								<td class="textL" width="15%"><%=DataFormatUtility.displayCommaNumber(payrollDetailM.getGrossIncome())%></td>
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>												
								<td class="textR" nowrap="nowrap" width="14%"></td>
								<td class="textL" width="15%"></td>
							</tr>			 			
				 		</table>
				 		</fieldset>			 					 					
				 	<%} %>
				 <%} %>
			</div>
		</div>
	<%}else{%>
		<table class="TableFrame">
			<tr class="ResultNotFound">
	        	<td>No Data Found</td>
	        </tr>
		</table>
	<%} %>
</div>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen(PLOrigFormHandler.PL_MAIN_APPFORM_SCREEN);
%>