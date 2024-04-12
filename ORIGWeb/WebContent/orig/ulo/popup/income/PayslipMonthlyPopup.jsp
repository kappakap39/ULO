<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.sql.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.date.ApplicationDate"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PayslipDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PayslipMonthlyDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PayslipMonthlyDetailDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/income/js/PayslipPopup.js"></script>
<%
	String subformId = "PAYSLIP_MONTHLY_POPUP";
	String formId = "POPUP_PAYSLIP_FORM";
	FormEffects formEffect = new FormEffects(formId,FormEffects.ConfigType.FORM,request);
	Logger logger = Logger.getLogger(this.getClass());
	String displayMode = HtmlUtil.EDIT;
	String tagId = "";
	//Date applicationDate = ORIGForm.getObjectForm().getApplicationDate();
	Date applicationDate = ORIGForm.getObjectForm().getApplyDate();
	if(Util.empty(applicationDate)) {
		applicationDate = ApplicationDate.getDate();
	}
	
	IncomeInfoDataM incomeM = (IncomeInfoDataM)ModuleForm.getObjectForm();
	ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
	PayslipDataM payslipM = null;
	
	if(incomeList.size() > 0){
		payslipM = (PayslipDataM)incomeList.get(0);
	} else {
		payslipM = new PayslipDataM();
		payslipM.setSeq(incomeList.size() + 1);
		incomeList.add(payslipM);
	}
	
	ArrayList<PayslipMonthlyDataM> monthly = payslipM.getPayslipMonthlys();
	if(monthly == null) {
		monthly = new ArrayList<PayslipMonthlyDataM>();
		payslipM.setPayslipMonthlys(monthly);
	}	
	String FIELD_ID_PAYSLIP_INCOME_TYPE_FIX = SystemConstant.getConstant("FIELD_ID_PAYSLIP_INCOME_TYPE_FIX");
	String FIELD_ID_PAYSLIP_INCOME_TYPE_VARIABLE = SystemConstant.getConstant("FIELD_ID_PAYSLIP_INCOME_TYPE_VARIABLE");
	String PAYSLIP_INCOME_SSO = SystemConstant.getConstant("PAYSLIP_INCOME_SSO");
	String PAYSLIP_INCOME_FIXED = SystemConstant.getConstant("PAYSLIP_INCOME_FIXED");
	String PAYSLIP_INCOME_VARIABLE = SystemConstant.getConstant("PAYSLIP_INCOME_VARIABLE");
	FormUtil formUtil = new FormUtil(subformId,request);
	String monthlyDisplayMode = displayMode;
	String bonusMonth = payslipM.getBonusMonth();
	String bonusYear = payslipM.getBonusYear();
	Date salaryDate = payslipM.getSalaryDate();
	BigDecimal bonus = payslipM.getBonus();
	BigDecimal pension = payslipM.getSpacialPension();
	if(payslipM.getNoMonth() != 0) {
		monthlyDisplayMode = HtmlUtil.VIEW;
		bonusMonth = "";
		bonusYear = "";
		salaryDate = null;
		bonus = null;
		pension = null;
	}
	int nonSSOCounter = 0;
	String  sylePadding = "100";
	if(monthly.size()>0){
		sylePadding="55";
	}
 %>

<div class="panel panel-default">
 <div class="panel-heading"><%=LabelUtil.getText(request,"PAYSLIP_MONTHLY_POPUP")%></div>
	<table id="PayslipMonthly" class="table table-striped">
		<tbody>
			<tr>
				<th></th>
				<th style="min-width: 190px"><%=HtmlUtil.getHeaderLabel(request,subformId,"INCOME_TYPE","INCOME_TYPE")%></th>
				<th style="width: 200px;"><%=HtmlUtil.getHeaderLabel(request,subformId,"INCOME_DESC","INCOME_DESC")%></th>
				<% for(int i = 0; i < PayslipMonthlyDetailDataM.MONTH_COUNT; i++) { %>
				<th><%=FormatUtil.displayMonthYear(request, Util.getPreviousMonth(applicationDate, i))%></th>
				<%} %>
				<th rowspan="9999" style="vertical-align: bottom; padding-bottom: <%=sylePadding%>px;">
<%-- 			<%=HtmlUtil.button("ADD_ROW_BTN","",monthlyDisplayMode,"btnsmall_add","",request) %> --%>
				<%=HtmlUtil.button("ADD_ROW_BTN", "", "", "btnsmall_add", "", formEffect)%>
				</th>
			</tr>
			<%
			if(!Util.empty(monthly)) {
	 			for(PayslipMonthlyDataM monthlyData: monthly) { 
	 				if(PAYSLIP_INCOME_SSO.equals(monthlyData.getIncomeType())) {
	 					continue;
	 				}
	 				nonSSOCounter++;
				logger.info("##Row id: ROW_"+monthlyData.getSeq()+" "+nonSSOCounter);
				%>
				
			<tr id="ROW_<%=monthlyData.getSeq()%>">
			
				<td><%=HtmlUtil.icon("DELETE_BUTTON","","btnsmall_delete BtnRemove","",formEffect) %>	</td>
<%-- 				<td><%=HtmlUtil.icon("DELETE_BUTTON",monthlyDisplayMode,"btnsmall_delete BtnRemove","",request) %>	</td> --%>
				<td>
					<%=HtmlUtil.radioInline("TYPE",FormatUtil.getString(monthlyData.getSeq()),"","",monthlyData.getIncomeType(),"",PAYSLIP_INCOME_FIXED,"",LabelUtil.getText(request, "FIX"),"",formUtil)%>
			 		<%=HtmlUtil.radioInline("TYPE",FormatUtil.getString(monthlyData.getSeq()),"","",monthlyData.getIncomeType(),"",PAYSLIP_INCOME_VARIABLE,"",LabelUtil.getText(request, "VARIABLE"),"",formUtil)%>
			 	</td>			
				<td>
					<div class="input-group col-md-8 col-xs-12 form-inline">
						<div class="input-group">
							<%=HtmlUtil.dropdown("INCOME_DESC",FormatUtil.getString(monthlyData.getSeq()), "", "PAYSLIP_INCOME_DESC_LISTBOX", "PAYSLIP_INCOME_DESC_LISTBOX", monthlyData.getIncomeDesc(), "", "", "ALL_ALL_ALL", "", "input-group-btn col-xs-padding", monthlyData, formUtil) %>
							<%=HtmlUtil.textBox("INCOME_OTH_DESC",FormatUtil.getString(monthlyData.getSeq()),"","",monthlyData.getIncomeOthDesc(),"","50","","input-group-btn ",formUtil)%>
						</div>
	    			</div>
				</td>
				<% // Monthly Details Data
					ArrayList<PayslipMonthlyDetailDataM> detailList = monthlyData.getPayslipMonthlyDetails();
					
				 	for(int i = 0; i < PayslipMonthlyDetailDataM.MONTH_COUNT; i++) {
					 	BigDecimal monthlyDetail = null;
					 	if(!Util.empty(detailList) && detailList.size()>= i+1) {
						 	PayslipMonthlyDetailDataM detailM = detailList.get(i);
						 	if(!Util.empty(detailM)) {
						 		monthlyDetail = detailM.getAmount();
						 	}
						 }
				 %>
				<td><%=HtmlUtil.currencyBox("AMOUNT",monthlyData.getSeq()+"_"+(i+1),"","",monthlyDetail,"",true,"15","","", formUtil)%></td>
				<%	} %>
			</tr>
			<%	
				}	
			} if(nonSSOCounter == 0){%>
			 	<tr>
			 		<td colspan="9999" align="center"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
			 	</tr>
			 <%}%>
			 <%
			 	monthly = payslipM.getPayslipMonthlys();
			 	PayslipMonthlyDataM ssoPayslip = null;
				if(!Util.empty(monthly)) {
	 				ArrayList<PayslipMonthlyDataM> ssoList = payslipM.getPayslipMonthlyByType(PAYSLIP_INCOME_SSO);
	 				if(!Util.empty(ssoList)) {
	 					ssoPayslip = ssoList.get(0);
	 				}
				}
				if(Util.empty(ssoPayslip)) {
					ssoPayslip = new PayslipMonthlyDataM();
					ssoPayslip.setSeq(monthly.size()+1);
					monthly.add(ssoPayslip);
					ssoPayslip.setIncomeType(PAYSLIP_INCOME_SSO);
				}
				ArrayList<PayslipMonthlyDetailDataM> detailList = ssoPayslip.getPayslipMonthlyDetails();
			 %>
			<tr>
				<td></td>
				<td></td>
				<td><%=HtmlUtil.getSubFormLabel(request, "TOTAL_SSO")%></td>
				<%		
			 	for(int i = 0; i < PayslipMonthlyDetailDataM.MONTH_COUNT; i++) {
				 	BigDecimal monthlyDetail = null;
				 	if(!Util.empty(detailList) && detailList.size()>= i+1) {
					 	PayslipMonthlyDetailDataM detailM = detailList.get(i);
					 	if(!Util.empty(detailM)) {
					 		monthlyDetail = detailM.getAmount();
					 	}
					 }
				 %>
				<td><%=HtmlUtil.currencyBox("AMOUNT",ssoPayslip.getSeq()+"_"+(i+1),"","",monthlyDetail,"",true,"15","","", formUtil)%></td>
				<%	} %>
			</tr>
		</tbody>
	</table>
	<br>
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"MONTH2_BONUS","MONTH_YR_BONUS","col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7">
						<div class="input-group">
						<%=HtmlUtil.dropdown("MONTH2_BONUS","","","",bonusMonth,"",SystemConstant.getConstant("FIELD_ID_MONTH"),"","","input-group-btn",formUtil)%>
						<span class="input-group-btn text-center">/</span>
						<%=HtmlUtil.dropdown("YEAR2_BONUS","","","YEAR_TYPE",bonusYear,"","","","","input-group-btn",formUtil)%>
						</div>
					</div>
				</div>
			</div>			
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"BONUS","BONUS","col-sm-4 col-md-5 control-label")%>
					<div class="input-group">
						<%=HtmlUtil.currencyBox("BONUS","BONUS","",bonus,"",true,"15","","col-sm-8 col-md-7", formUtil)%>
						<%=LabelUtil.getText(request, "BAHT") %>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"SALARY_DATE2","SALARY_DATE","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.calendar("SALARY_DATE2","SALARY_DATE2","SALARY_DATE2",salaryDate,"","",HtmlUtil.TH,"col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"KBANK_PENSION2","KBANK_PENSION","col-sm-4 col-md-5 control-label")%>
					<div class="input-group">
						<%=HtmlUtil.currencyBox("KBANK_PENSION2","KBANK_PENSION","KBANK_PENSION2",pension,"",true,"15","","col-sm-8 col-md-7",formUtil)%>			
						<%=LabelUtil.getText(request, "BAHT") %>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>