<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.sql.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.date.ApplicationDate"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.OpenedEndFundDetailDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.OpenedEndFundDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/income/js/OthOpenFundPopup.js"></script>
<%
	String subformId = "OTH_OPEN_FUND_POPUP";
	String formId = "POPUP_OTH_OPEN_FUND_FORM";
	FormEffects formEffect = new FormEffects(formId,FormEffects.ConfigType.FORM,request);
	String displayMode = HtmlUtil.EDIT;
	String tagId = "";
	Date applicationDate = ORIGForm.getObjectForm().getApplicationDate();
	if(Util.empty(applicationDate)) {
		applicationDate = ApplicationDate.getDate();
	}
	
	IncomeInfoDataM incomeM = (IncomeInfoDataM)ModuleForm.getObjectForm();
	ArrayList<IncomeCategoryDataM> openFundList = incomeM.getAllIncomes();
	if(openFundList == null) {
		openFundList = new ArrayList<IncomeCategoryDataM>();
	}
	String BANK_CACHE = SystemConstant.getConstant("BANK_CODE_CACHE");
	
	FormUtil formUtil = new FormUtil(subformId, request);
 %>
<%=HtmlUtil.hidden("subFormID",subformId)%>
<div class="panel panel-default">
<div class="panel-body">
	<table id="OthOpenFund" class="table table-striped table-hover">
	<tbody>
		<tr>
			<th></th>
			<th width="90%"><%=HtmlUtil.getSubFormLabel(request, "LIST")%></th>
			<th></th>
			<th rowspan="9999" style="vertical-align: bottom; padding-bottom: 12px;">
			<%=HtmlUtil.button("ADD_ROW_BTN", "", "", "btnsmall_add", "", formEffect)%></th>
			
		</tr>
		<%
		if(!Util.empty(openFundList)) {
			for(IncomeCategoryDataM incomeTypeObj : openFundList) { 
				OpenedEndFundDataM othOpnFundM = (OpenedEndFundDataM)incomeTypeObj;
				ArrayList<OpenedEndFundDetailDataM> detailList = othOpnFundM.getOpenEndFundDetails();
				if(detailList == null) {
					detailList = new ArrayList<OpenedEndFundDetailDataM>();
				}
			%>
		<tr id="ROW_<%=othOpnFundM.getSeq()%>">
<%-- 			<td><%=HtmlUtil.icon("DELETE_BUTTON",displayMode,"btnsmall_delete BtnRemove","",request) %>	</td> --%>
			<td><%=HtmlUtil.icon("DELETE_BUTTON","","btnsmall_delete BtnRemove","","",formEffect) %></td>
			<td>
				<div class="row form-horizontal">
					<div class="col-sm-6">
						<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"FIN_INSTITUTION","FIN_INSTITUTION","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("FIN_INSTITUTION_"+FormatUtil.getString(othOpnFundM.getSeq()),"","","",othOpnFundM.getBankCode(),"",BANK_CACHE,"","","col-sm-8 col-md-7",formUtil)%>
<%-- 						<%=HtmlUtil.dropdown("FIN_INSTITUTION",FormatUtil.getString(othOpnFundM.getSeq()),"",othOpnFundM.getBankCode(),"",BANK_CACHE,"",displayMode,"","col-sm-8 col-md-7",request)%> --%>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"FUND_NAME","FUND_NAME","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("FUND_NAME",FormatUtil.getString(othOpnFundM.getSeq()),"","",othOpnFundM.getFundName(),"","50","","col-sm-8 col-md-7",formUtil)%>
<%-- 					<%=HtmlUtil.textBox("FUND_NAME",FormatUtil.getString(othOpnFundM.getSeq()),othOpnFundM.getFundName(),"","50",displayMode,"","col-sm-8 col-md-7",request)%> --%>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-sm-6">
						<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NO","ACCOUNT_NO","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.numberBox("ACCOUNT_NO",FormatUtil.getString(othOpnFundM.getSeq()),"","",FormatUtil.toBigDecimal(othOpnFundM.getAccountNo(),true),"","####################","","",true,"20","","col-sm-8 col-md-7",formUtil)%>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NAME","ACCOUNT_NAME","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("ACCOUNT_NAME",FormatUtil.getString(othOpnFundM.getSeq()),"","",othOpnFundM.getAccountName(),"","180","","col-sm-8 col-md-7",formUtil)%>
<%-- 					<%=HtmlUtil.textBox("ACCOUNT_NAME",FormatUtil.getString(othOpnFundM.getSeq()),othOpnFundM.getAccountName(),"","180",displayMode,"","col-sm-8 col-md-7",request)%> --%>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-sm-6">
						<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"PORTION","PORTION","col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.numberBox("PORTION",FormatUtil.getString(othOpnFundM.getSeq()),"","",othOpnFundM.getHoldingRatio(),"","#","","",true,"1","","col-sm-8 col-md-7",formUtil)%>
						</div>
					</div>
			</div>
			<br>
				<%--  Monthly Detail List --%>
			<table>
				<caption><%=LabelUtil.getText(request, "OPEN_FUND_TABLE_HEAD")%></caption>
				<thead>
					<tr>
					<% for(int i = 0; i < OpenedEndFundDetailDataM.MONTH_COUNT; i++) { 
						String monthLabel = FormatUtil.displayMonthYear(request, Util.getPreviousMonth(applicationDate, i+1));
						String elementId = "MONTH_"+(i+1);
					%>
						<th><%=HtmlUtil.getCustomHeaderLabel(request, subformId, elementId, monthLabel)%></th>
					<%} %>
					</tr>
				</thead>
				<tbody>
					<tr>
					<%
						for(int i = 0; i < OpenedEndFundDetailDataM.MONTH_COUNT; i++) {
						 	BigDecimal monthlyDetail = null;
						 	if(detailList.size()>= i+1) {
							 	OpenedEndFundDetailDataM detailM = detailList.get(i);
							 	if(!Util.empty(detailM)) {
							 		monthlyDetail = detailM.getAmount();
							 	}
							 }
					%>
							<td><%=HtmlUtil.currencyBox("AMOUNT",othOpnFundM.getSeq()+"_"+(i+1),"","",monthlyDetail,"",true,"15","","","",formUtil)%></td>
<%-- 						<td><%=HtmlUtil.currencyBox("AMOUNT",othOpnFundM.getSeq()+"_"+(i+1),monthlyDetail,"",true,"15",displayMode,"",request)%></td> --%>
						
					<%
						}
					%>
					</tr>
				</tbody>
			</table>
		</td>
		<td></td>
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