<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.sql.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PayrollFileDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<script type="text/javascript" src="orig/ulo/popup/income/js/PayrollFilePopup.js"></script>

<%
	String subformId = "PAYROLL_FILE_POPUP";
	String displayMode = HtmlUtil.EDIT;
	String tagId = "";
	
	IncomeInfoDataM incomeM = (IncomeInfoDataM)ModuleForm.getObjectForm();
	ArrayList<IncomeCategoryDataM> payrollList = incomeM.getAllIncomes();
	
	FormUtil formUtil = new FormUtil(subformId,request);
 %>
<%=HtmlUtil.hidden("subFormID",subformId)%>
<div class="panel panel-default">
	<table id="PayrollFile" class="table table-striped">
	<tbody>
		<tr>
			<th style="width: 50px"><%=HtmlUtil.getHeaderLabel(request,subformId,"NUMBER_ABBR","NUMBER_ABBR")%></th>
			<th style="width: 250px;"><%=HtmlUtil.getHeaderLabel(request,subformId,"MONTH_YEAR","MONTH_YEAR")%></th>
			<th style="width: 250px;"><%=HtmlUtil.getHeaderLabel(request,subformId,"PAYROLL_INCOME","PAYROLL_INCOME")%></th>
		</tr>
		<%if(!Util.empty(payrollList)) { 
			for(IncomeCategoryDataM incomeCategoryM : payrollList) {
				PayrollFileDataM payrollM = (PayrollFileDataM)incomeCategoryM; 
				if(!MConstant.FLAG.YES.equals(payrollM.getFromFileFlag())) {
					displayMode = HtmlUtil.EDIT;
				} else {
					displayMode = HtmlUtil.VIEW;
				}
		%>
		<tr>
			<td><%=FormatUtil.display(payrollM.getSeq())%></td>
			<td><%=FormatUtil.display(payrollM.getMonth()+"/"+payrollM.getYear())%></td>
			<td><%=HtmlUtil.currencyBox("AMOUNT",""+payrollM.getSeq(),payrollM.getAmount(),"",true,"15",displayMode,"","",request)%></td>
		</tr>
		<%	}
		} %>
	</tbody>
	</table>
</div>