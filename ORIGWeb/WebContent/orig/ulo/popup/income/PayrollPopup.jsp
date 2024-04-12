<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.sql.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PayrollDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<script type="text/javascript" src="orig/ulo/popup/income/js/PayrollPopup.js"></script>

<%
	String subformId = "PAYROLL_POPUP";
	String displayMode = HtmlUtil.EDIT;
// 	String tagId = "";
	
	IncomeInfoDataM incomeM = (IncomeInfoDataM)ModuleForm.getObjectForm();
	ArrayList<IncomeCategoryDataM> payrollList = incomeM.getAllIncomes();
	PayrollDataM payrollM = null;
	if(payrollList.size() > 0){
		payrollM = (PayrollDataM)payrollList.get(0);
	} else {
		payrollM = new PayrollDataM();
		payrollM.setSeq(payrollList.size() + 1);
		payrollList.add(payrollM);
	}
	FormUtil formUtil = new FormUtil(subformId,request);
 %>
<%=HtmlUtil.hidden("subFormID",subformId)%>
<div class="panel panel-default">
<div class="panel-body">
	<div class="row form-horizontal">
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"NO_OF_EMPLOYEES","NO_OF_EMPLOYEES","col-sm-4 col-md-5 control-label")%>
				<%=HtmlUtil.numberBox("NO_OF_EMPLOYEES","","",payrollM.getNoOfEmployee(),"","",true,"9","","col-sm-8 col-md-7",formUtil) %>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"INCOME_MIN","INCOME_MIN","col-sm-4 col-md-5 control-label")%>
<%-- 			<%=HtmlUtil.currencyBox("INCOME_MIN","",payrollM.getIncome(),"",true,"15",displayMode,HtmlUtil.elementTagId("INCOME_MIN"),"col-sm-8 col-md-7",request)%> --%>
				<%=HtmlUtil.currencyBox("INCOME_MIN","","","",payrollM.getIncome(),"",true,"15",HtmlUtil.elementTagId("INCOME_MIN"),"col-sm-8 col-md-7","",formUtil)%>
			</div>
		</div>
	</div>
</div>

</div>