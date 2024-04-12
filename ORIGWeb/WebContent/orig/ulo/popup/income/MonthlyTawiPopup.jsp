<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.sql.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.date.ApplicationDate"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.MonthlyTawi50DataM"%>
<%@page import="com.eaf.orig.ulo.model.app.MonthlyTawi50DetailDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/income/js/MonthlyTawiPopup.js"></script>
<%
	String displayMode = HtmlUtil.EDIT;
	String INC_TYPE_MONTHLY_50TAWI = SystemConstant.getConstant("INC_TYPE_MONTHLY_50TAWI");
	String INC_TYPE_YEARLY_50TAWI = SystemConstant.getConstant("INC_TYPE_YEARLY_50TAWI");
	String ROLE_CA = SystemConstant.getConstant("ROLE_CA");
	String subformId = "MONTHLY_TAWI_POPUP";
	String formId = "POPUP_YEARLY_TAWI_FORM";
	FormUtil formUtil = new FormUtil(subformId,request);
	FormEffects formEffect = new FormEffects(formId,FormEffects.ConfigType.FORM,request);
	String tagId = "";
	String chk50Tawi = (String) request.getSession().getAttribute("chk50Tawi_MON");
	Date applicationDate = ORIGForm.getObjectForm().getApplicationDate();
	String roldId = ORIGForm.getRoleId();
	if(Util.empty(applicationDate)) {
		applicationDate = ApplicationDate.getDate();
	}
	String tableDisplayMode = displayMode;
	String className = "BtnRemove";
	if(Util.empty(chk50Tawi) || !INC_TYPE_MONTHLY_50TAWI.equals(chk50Tawi)) {
		tableDisplayMode = HtmlUtil.VIEW;
		className = "";
	}
	IncomeInfoDataM incomeM = (IncomeInfoDataM)ModuleForm.getObjectForm();
	ArrayList<IncomeCategoryDataM> monthlyList = incomeM.getAllIncomeCategoryByType(INC_TYPE_MONTHLY_50TAWI);
	if(monthlyList == null) {
		monthlyList = new ArrayList<IncomeCategoryDataM>();
	}
		
 %>
<%--=HtmlUtil.hidden("subFormID","MONTHLY_TAWI_POPUP")--%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-12">
<!-- 			#Move DisplayMode Form String To DB SC_DISPLAY_MOD  -->
<%-- 		<%=HtmlUtil.checkBoxInline("chk50Tawi", "MON", chk50Tawi, "", INC_TYPE_MONTHLY_50TAWI, HtmlUtil.EDIT, "", LabelUtil.getText(request, "MONTHLY_TAWI"), "col-sm-1 col-md-3 control-label", request) %> --%>
			<%=HtmlUtil.checkBoxInline("chk50Tawi", "MON","","CHK50TAWIMONTHLY", chk50Tawi, "", INC_TYPE_MONTHLY_50TAWI, "", LabelUtil.getText(request, "MONTHLY_TAWI"), "col-sm-1 col-md-3 control-label", formUtil) %>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	<table  id="MonthlyTawi" class="table table-striped table-hover">
	<tbody>
		<tr>
			<th></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subformId,"COMPANY_NAME","COMPANY_NAME")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subformId,"INCOME_DESC","INCOME_DESC")%></th>
			<% for(int i = 0; i < MonthlyTawi50DetailDataM.MONTH_COUNT; i++) { %>
			<th><%=FormatUtil.displayMonthYear(request, Util.getPreviousMonth(applicationDate, i))%></th>
			<% } %>
			<th rowspan="9999" style="vertical-align: bottom; padding-bottom: 10px;">
<%-- 		<%=HtmlUtil.button("ADD_MONTHLY_BTN","",tableDisplayMode,"btnsmall_add","",request) %></th> --%>
			<%=HtmlUtil.button("ADD_MONTHLY_BTN","","ADD_MONTHLY_BTN","","btnsmall_add","","",formEffect) %></th>
		</tr>
		<%
		if(!Util.empty(monthlyList)) {
			for(IncomeCategoryDataM incomeTypeObj : monthlyList) {
				MonthlyTawi50DataM monthlyM = null; 
				if(incomeTypeObj instanceof MonthlyTawi50DataM) {
					monthlyM = (MonthlyTawi50DataM)incomeTypeObj;
				} else {
					continue;
				}
			%>
		<tr id="ROW_<%=monthlyM.getSeq()%>">
<%-- 			<td><%=HtmlUtil.icon("DELETE_BUTTON",tableDisplayMode,"btnsmall_delete "+className,"",request) %></td> --%>
			<td><%=HtmlUtil.icon("DELETE_BUTTON","DELETE_BUTTON","btnsmall_delete "+className,"","",formEffect) %></td>
<%-- 		<td><%=HtmlUtil.textBox("COMPANY_NAME",FormatUtil.getString(monthlyM.getSeq()),monthlyM.getCompanyName(),"","100",tableDisplayMode,"",request)%></td> --%>
			<td><%=HtmlUtil.textBox("COMPANY_NAME",FormatUtil.getString(monthlyM.getSeq()),"","",monthlyM.getCompanyName(),"","100","","",formUtil)%></td>			
<%-- 		<td><%=HtmlUtil.dropdown("INCOME_DESC",FormatUtil.getString(monthlyM.getSeq()),"",Util.empty(monthlyM.getTawiIncomeType())?INC_TYPE_YEARLY_50TAWI:monthlyM.getTawiIncomeType(),SystemConstant.getConstant("FIELD_ID_MONTHLY_DESC"),"",tableDisplayMode,"",request)%></td> --%>
			<td><%=HtmlUtil.dropdown("INCOME_DESC",FormatUtil.getString(monthlyM.getSeq()),"","","",Util.empty(monthlyM.getTawiIncomeType())?INC_TYPE_YEARLY_50TAWI:monthlyM.getTawiIncomeType(),"",SystemConstant.getConstant("FIELD_ID_MONTHLY_DESC"),"","","",formUtil)%></td>
			<% // Monthly Details Data
				ArrayList<MonthlyTawi50DetailDataM> detailList = monthlyM.getMonthlyTaxi50Details();
				
				for(int i = 0; i < MonthlyTawi50DetailDataM.MONTH_COUNT; i++) {
				 	BigDecimal monthlyDetail = null;
				 	if(!Util.empty(detailList) && detailList.size() >= i+1) {
					 	MonthlyTawi50DetailDataM detailM = detailList.get(i);
					 	if(!Util.empty(detailM)) {
					 		monthlyDetail = detailM.getAmount();
					 	}
					 }
			 %>
<%-- 		<td><%=HtmlUtil.currencyBox("AMOUNT",monthlyM.getSeq()+"_"+(i+1),monthlyDetail,"",true,"15",tableDisplayMode,"",request)%></td> --%>
			<td><%=HtmlUtil.currencyBox("AMOUNT",monthlyM.getSeq()+"_"+(i+1),"","",monthlyDetail,"",true,"15","","",formUtil)%></td>
			<%	} %>
		</tr>
		<%	
			}
		} else{%>
	 	<tr>
	 		<td colspan="9999" align="center"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
	 	</tr>
	 	<%}%>
	</tbody>
	</table>
</div>

