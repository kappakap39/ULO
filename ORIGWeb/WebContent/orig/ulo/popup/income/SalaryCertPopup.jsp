<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.sql.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.SalaryCertDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/income/js/SalaryCertPopup.js"></script>
<%
	String displayMode = HtmlUtil.EDIT;
	String subformId = "SALARY_CERT_POPUP";
	String formId = "POPUP_SALARY_CERT_FORM";
	FormUtil formUtil = new FormUtil(subformId,request);
	FormEffects formEffect = new FormEffects(formId,FormEffects.ConfigType.FORM,request);
	String tagId = "";
	IncomeInfoDataM incomeM = (IncomeInfoDataM)ModuleForm.getObjectForm();
	ArrayList<IncomeCategoryDataM> salaryList = incomeM.getAllIncomes();
	if(salaryList == null) {
		salaryList = new ArrayList<IncomeCategoryDataM>();
	}
		
 %>
<%=HtmlUtil.hidden("subFormID",subformId)%>
<div class="panel panel-default">
	<table id="SalaryCert" class="table table-striped">
	<tbody>
		<tr>
			<th></th>
			<th style="min-width: 190px"><%=HtmlUtil.getHeaderLabel(request,subformId,"COMPANY_NAME","COMPANY_NAME_LTD")%></th>
			<th style="min-width: 190px"></th>
			<th rowspan="9999" style="vertical-align: bottom; padding-bottom: 10px;">
				<%=HtmlUtil.button("ADD_ROW_BTN","","","","btnsmall_add","","",formEffect) %>
<%-- 			<%=HtmlUtil.button("ADD_ROW_BTN","",displayMode,"btnsmall_add","",request) %> --%>
			</th>
		</tr>
		<%
		if(!Util.empty(salaryList)) {
			for(IncomeCategoryDataM incomeTypeObj : salaryList) { 
				SalaryCertDataM salaryM = (SalaryCertDataM)incomeTypeObj;
				
			%>
		<tr id="ROW_<%=salaryM.getSeq()%>">
<%-- 		<td><%=HtmlUtil.icon("DELETE_BUTTON",displayMode,"btnsmall_delete BtnRemove","",request) %></td> --%>
<%-- 		<td><%=HtmlUtil.textBox("COMPANY_NAME",FormatUtil.getString(salaryM.getSeq()),salaryM.getCompanyName(),"","100",displayMode,"",request)%></td>			 --%>
<%-- 		<td><%=HtmlUtil.currencyBox("REVENUE",FormatUtil.getString(salaryM.getSeq()),salaryM.getIncome(),"",true,"15",displayMode,"",request)%></td> --%>
			<td><%=HtmlUtil.icon("DELETE_BUTTON","","btnsmall_delete BtnRemove","","",formEffect) %></td>
			<td><%=HtmlUtil.textBox("COMPANY_NAME",FormatUtil.getString(salaryM.getSeq()),"","",salaryM.getCompanyName(),"","100","","",formUtil)%></td>
			<td>
				<div class="row">
					<%=HtmlUtil.getMandatoryLabel(request,"SALARY_CERT_REVENUE_AND_OTHER_INCOME","col-sm-4 col-md-4")%>
					<%=HtmlUtil.currencyBox("TOTAL_INCOME",FormatUtil.getString(salaryM.getSeq()),"","",salaryM.getTotalIncome(),"",true,"15","style='display: inline;'","col-sm-6 col-md-6",formUtil)%>
				</div><br>
				<div class="row">
					<%=HtmlUtil.getMandatoryLabel(request,"REVENUE","col-sm-4 col-md-4")%>
					<%=HtmlUtil.currencyBox("REVENUE",FormatUtil.getString(salaryM.getSeq()),"","",salaryM.getIncome(),"",true,"15","style='display: inline;margin-bottom: 5px;'","col-sm-6 col-md-6",formUtil)%>
				</div>
				<div class="row">	
					<%=HtmlUtil.getMandatoryLabel(request,"SALARY_CERT_OTHER_INCOME","col-sm-4 col-md-4")%>
					<%=HtmlUtil.currencyBox("OTHER_INCOME",FormatUtil.getString(salaryM.getSeq()),"","",salaryM.getOtherIncome(),"",true,"15","style='display: inline;'","col-sm-6 col-md-6",formUtil)%>
				</div>
			</td>
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