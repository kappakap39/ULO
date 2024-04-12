<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.sql.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ClosedEndFundDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/income/js/OthClosedFundPopup.js"></script>
<%
	String subformId = "OTH_CLOSED_FUND_POPUP";
	String formId = "POPUP_OTH_CLOSED_FUND_FORM";
	FormEffects formEffect = new FormEffects(formId,FormEffects.ConfigType.FORM,request);
	String displayMode = HtmlUtil.EDIT;
	String tagId = "";
	
	IncomeInfoDataM incomeM = (IncomeInfoDataM)ModuleForm.getObjectForm();
	ArrayList<IncomeCategoryDataM> closedEndList = incomeM.getAllIncomes();
	if(closedEndList == null) {
		closedEndList = new ArrayList<IncomeCategoryDataM>();
	}
	String BANK_CACHE = SystemConstant.getConstant("BANK_CODE_CACHE");
	
	FormUtil formUtil = new FormUtil(subformId, request);	
 %>
<%=HtmlUtil.hidden("subFormID",subformId)%>
<div class="panel panel-default">
<div class="panel-body">
	<table id="OthClosedFund" class="table table-striped table-hover">
	<tbody>
		<tr>
			<th></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subformId,"FIN_INSTITUTION","FIN_INSTITUTION")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subformId,"FUND_NAME","FUND_NAME")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subformId,"ACCOUNT_NO","ACCOUNT_NO")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subformId,"ACCOUNT_NAME","ACCOUNT_NAME")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subformId,"PORTION","PORTION")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subformId,"OUTSTANDING_BALANCE","OUTSTANDING_BALANCE")%></th>
			<th rowspan="9999" style="vertical-align: bottom; padding-bottom: 12px;">		
			<%=HtmlUtil.button("ADD_ROW_BTN", "", "", "btnsmall_add", "", formEffect)%></th>
<%-- 		<%=HtmlUtil.button("ADD_ROW_BTN","",displayMode,"btnsmall_add","",request) %></th> --%>
		</tr>
		<%
		if(!Util.empty(closedEndList)) {
			for(IncomeCategoryDataM incomeTypeObj : closedEndList) { 
				ClosedEndFundDataM othClosedFundM = (ClosedEndFundDataM)incomeTypeObj;
			%>
		<tr id="ROW_<%=othClosedFundM.getSeq()%>">
<%-- 			<td><%=HtmlUtil.icon("DELETE_BUTTON",displayMode,"btnsmall_delete BtnRemove","",request) %>	</td> --%>
			<td><%=HtmlUtil.icon("DELETE_BUTTON","","btnsmall_delete BtnRemove","",formEffect) %>	</td>		
			<td><%=HtmlUtil.dropdown("FIN_INSTITUTION_"+FormatUtil.getString(othClosedFundM.getSeq()),"","","",othClosedFundM.getBankCode(),"",BANK_CACHE,"","","",formUtil)%></td>	
			<td><%=HtmlUtil.textBox("FUND_NAME",FormatUtil.getString(othClosedFundM.getSeq()),"","",othClosedFundM.getFundName(),"","50","","",formUtil)%></td>
			<td><%=HtmlUtil.numberBox("ACCOUNT_NO",FormatUtil.getString(othClosedFundM.getSeq()),"","",FormatUtil.toBigDecimal(othClosedFundM.getAccountNo(),true),"","####################","","",true,"20","","",formUtil)%></td>
			<td><%=HtmlUtil.textBox("ACCOUNT_NAME",FormatUtil.getString(othClosedFundM.getSeq()),"","",othClosedFundM.getAccountName(),"","180","","",formUtil)%></td>
			<td><%=HtmlUtil.numberBox("PORTION",FormatUtil.getString(othClosedFundM.getSeq()),"","",othClosedFundM.getHoldingRatio(),"","#","","",true,"1","","",formUtil)%></td>
			<td><%=HtmlUtil.currencyBox("OUTSTANDING_BALANCE_"+FormatUtil.getString(othClosedFundM.getSeq()),"","",othClosedFundM.getAccountBalance(),"",true,"15","","",formUtil)%></td>
		</tr>
		<%	}	
		} else{%>
	 	<tr>
	 		<td colspan="9999" align="center"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
	 	</tr>
	<%}%>
	</tbody>
	</table>
</div>
</div>