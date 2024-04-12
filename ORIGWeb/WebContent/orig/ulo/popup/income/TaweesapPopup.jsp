<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.TaweesapDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/income/js/TaweesapPopup.js"></script>
<%
	String subformId = "TAWEESAP_POPUP";
	FormUtil formUtil = new FormUtil(subformId,request);
	String displayMode = HtmlUtil.EDIT;
	String tagId = "";
	IncomeInfoDataM incomeM = (IncomeInfoDataM)ModuleForm.getObjectForm();
	ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
	TaweesapDataM taweesapM = null;
	if(incomeList.size() > 0){
		taweesapM = (TaweesapDataM)incomeList.get(0);
	} else {
		taweesapM = new TaweesapDataM();
		taweesapM.setSeq(incomeList.size() + 1);
		incomeList.add(taweesapM);
	}
 %>
<div class="panel panel-default">
<div class="panel-body">
	<div class="row form-horizontal">
		<div class="col-sm-12">
			<div class="form-group">
				<%=HtmlUtil.getSubFormLabel(request,subformId,"AUM","AUM","col-sm-1 col-md-2 control-label")%>
				<div class="input-group">
				<%=HtmlUtil.currencyBox("AUM","","","",taweesapM.getAum(),"",true,"15","","col-sm-11 col-md-10","",formUtil)%>
<%-- 			<%=HtmlUtil.currencyBox("AUM","",taweesapM.getAum(),"",true,"15",displayMode,"","col-sm-11 col-md-10",request)%> --%>
				<%=LabelUtil.getText(request, "BAHT") %>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
