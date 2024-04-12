<%@page import="java.util.Calendar"%>
<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.YearlyTawi50DataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/income/js/YearlyTawiPopup.js"></script>
<%
	String ROLE_CA = SystemConstant.getConstant("ROLE_CA");
	String displayMode = HtmlUtil.EDIT;
	String subformId = "YEARLY_TAWI_POPUP";
	String formId = "POPUP_YEARLY_TAWI_FORM";
	FormUtil formUtil = new FormUtil(subformId,request);
	FormEffects formEffect = new FormEffects(formId,FormEffects.ConfigType.FORM,request);
	String INC_TYPE_YEARLY_50TAWI = SystemConstant.getConstant("INC_TYPE_YEARLY_50TAWI");
	String tagId = "";
	String roldId = ORIGForm.getRoleId();
	String chk50Tawi = (String)request.getSession().getAttribute("chk50Tawi_YEAR");
	String tableDisplayMode = displayMode;
	String className = "BtnRemove";
	if(Util.empty(chk50Tawi) || !INC_TYPE_YEARLY_50TAWI.equals(chk50Tawi)){
		tableDisplayMode = HtmlUtil.VIEW;
		className = "";
	}
	IncomeInfoDataM incomeM = (IncomeInfoDataM)ModuleForm.getObjectForm();
	ArrayList<IncomeCategoryDataM> yearly = incomeM.getAllIncomeCategoryByType(INC_TYPE_YEARLY_50TAWI);
	if(yearly == null) {
		yearly = new ArrayList<IncomeCategoryDataM>();
	}
 %>
<%=HtmlUtil.hidden("subFormID",subformId)%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-12">
			<%=HtmlUtil.checkBoxInline("chk50Tawi", "YEAR","","CHK50TAWIYEAR", chk50Tawi, "", INC_TYPE_YEARLY_50TAWI, "", LabelUtil.getText(request, "YEARLY_TAWI"), "col-sm-1 col-md-3 control-label", formUtil) %>
<%-- 			<%=HtmlUtil.checkBoxInline("chk50Tawi", "YEAR", chk50Tawi, "", INC_TYPE_YEARLY_50TAWI, displayMode, "", LabelUtil.getText(request, "YEARLY_TAWI"), "col-sm-1 col-md-3 control-label", request) %> --%>
			</div>
		</div>
	</div>
	<br>
	<table id="YearlyTawi" class="table table-striped table-hover">
	<tbody>
		<tr>
			<th></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subformId,"COMPANY_NAME","COMPANY_NAME")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subformId,"YEAR","YEAR")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subformId,"ACCUMULATED_MONTH","ACCUMULATED_MONTH")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subformId,"INCOME_TYPE","INCOME_TYPE401")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subformId,"INCOME_TYPE","INCOME_TYPE402")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subformId,"SSO","SSO")%></th>
			<th rowspan="9999" style="vertical-align: bottom; padding-bottom: 12px;">
			<%=HtmlUtil.button("ADD_YEARLY_BTN","","ADD_YEARLY_BTN","","btnsmall_add","","",formEffect) %></th>
			<%-- <%=HtmlUtil.button("ADD_YEARLY_BTN","",tableDisplayMode,"btnsmall_add","",request) %> --%>
		</tr>
		<%
		if(!Util.empty(yearly)) {
			for(IncomeCategoryDataM incomeTypeObj : yearly) { 
				YearlyTawi50DataM yearlyM = null;
				if(incomeTypeObj instanceof YearlyTawi50DataM) {
					yearlyM = (YearlyTawi50DataM)incomeTypeObj;
				} else {
					continue;
				}
				if(Util.empty(yearlyM.getYear())){
					yearlyM.setYear((Calendar.getInstance().get(Calendar.YEAR)-1)+543);
				}
			%>
		<tr id="ROW_<%=yearlyM.getSeq()%>">
<%-- 			<td><%=HtmlUtil.icon("DELETE_BUTTON",tableDisplayMode,"btnsmall_delete "+className,"",request) %></td> --%>
<%-- 			<td><%=HtmlUtil.textBox("COMPANY_NAME",FormatUtil.getString(yearlyM.getSeq()),yearlyM.getCompanyName(),"","100",tableDisplayMode,"",request)%></td>	 --%>
<%-- 			<td><%=HtmlUtil.numberBox("YEAR", FormatUtil.getString(yearlyM.getSeq()),yearlyM.getYear(), "####","","", true, "4", tableDisplayMode, "", request)%></td> --%>
<%-- 			<td><%=HtmlUtil.dropdown("ACCUMULATED_MONTH",FormatUtil.getString(yearlyM.getSeq()),"",FormatUtil.getString(yearlyM.getMonth()),SystemConstant.getConstant("FIELD_ID_MONTH"),"",tableDisplayMode,"",request)%></td> --%>
<%-- 			<td><%=HtmlUtil.currencyBox("INCOME_TYPE401",FormatUtil.getString(yearlyM.getSeq()),yearlyM.getIncome401(),"",true,"15",tableDisplayMode,"",request)%></td> --%>
<%-- 			<td><%=HtmlUtil.currencyBox("INCOME_TYPE402",FormatUtil.getString(yearlyM.getSeq()),yearlyM.getIncome402(),"",true,"15",tableDisplayMode,"",request)%></td> --%>			
<%-- 			<td><%=HtmlUtil.currencyBox("SSO",FormatUtil.getString(yearlyM.getSeq()),yearlyM.getSumSso(),"",true,"15",tableDisplayMode,"",request)%></td> --%>				
			<td><%=HtmlUtil.icon("DELETE_BUTTON","","btnsmall_delete "+className,"","",formEffect) %></td>
			<td><%=HtmlUtil.textBox("COMPANY_NAME",FormatUtil.getString(yearlyM.getSeq()),"","",yearlyM.getCompanyName(),"","100","","",formUtil)%></td>	
			<td><%=HtmlUtil.numberBox("YEAR",FormatUtil.getString(yearlyM.getSeq()),"","",FormatUtil.toBigDecimal(FormatUtil.toString(yearlyM.getYear()),true),"","####","","",true,"4","","",formUtil)%></td>
			<td><%=HtmlUtil.dropdown("ACCUMULATED_MONTH",FormatUtil.getString(yearlyM.getSeq()),"","","",FormatUtil.getString(yearlyM.getMonth()),"",SystemConstant.getConstant("FIELD_ID_MONTH"),"","","",formUtil)%></td>
			<td><%=HtmlUtil.currencyBox("INCOME_TYPE401",FormatUtil.getString(yearlyM.getSeq()),"","",yearlyM.getIncome401(),"",true,"15","","",formUtil)%></td>
			<td><%=HtmlUtil.currencyBox("INCOME_TYPE402",FormatUtil.getString(yearlyM.getSeq()),"","",yearlyM.getIncome402(),"",true,"15","","",formUtil)%></td>
			<td><%=HtmlUtil.currencyBox("SSO",FormatUtil.getString(yearlyM.getSeq()),"","",yearlyM.getSumSso(),"",true,"15","","",formUtil)%></td>

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

