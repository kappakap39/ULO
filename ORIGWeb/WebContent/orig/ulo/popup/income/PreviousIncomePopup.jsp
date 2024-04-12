<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PreviousIncomeDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/income/js/PreviousIncomePopup.js"></script>
<%
	String subformId = "PREVIOUS_INCOME_POPUP";
	FormUtil formUtil = new FormUtil(subformId,request);
	String displayMode = HtmlUtil.EDIT;
	String tagId = "";
	IncomeInfoDataM incomeM = (IncomeInfoDataM)ModuleForm.getObjectForm();
	ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
	PreviousIncomeDataM previousIncomeM = null;
	if(incomeList.size() > 0){
		previousIncomeM = (PreviousIncomeDataM)incomeList.get(0);
	} else {
		previousIncomeM = new PreviousIncomeDataM();
		previousIncomeM.setSeq(incomeList.size() + 1);
		incomeList.add(previousIncomeM);
	}
 %>
<div class="panel panel-default">
<div class="panel-body">
	<div class="row form-horizontal">
		<div class="col-sm-6">
			<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"INCOME_DATE","INCOME_DATE","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("INCOME_DATE","","INCOME_DATE",previousIncomeM.getIncomeDate(),"",HtmlUtil.VIEW,"",HtmlUtil.TH,"col-sm-8 col-md-7",request)%>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"PRODUCT","PREVIOUS_INCOME_PRODUCT","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("PRODUCT","",previousIncomeM.getProduct(),"","100",HtmlUtil.VIEW,"","col-sm-8 col-md-7",request)%>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="col-sm-6">
			<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"INCOME","INCOME","col-sm-4 col-md-5 control-label")%>
<%-- 		<%=HtmlUtil.currencyBox("INCOME","",previousIncomeM.getIncome(),"",true,"15",displayMode,"","col-sm-8 col-md-7",request)%> --%>
			<%=HtmlUtil.currencyBox("INCOME","","","",previousIncomeM.getIncome(),"",true,"15","","col-sm-8 col-md-7","",formUtil)%>
			</div>
		</div>
	</div>
</div>
</div>