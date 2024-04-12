<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.sql.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.date.ApplicationDate"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.SavingAccountDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.SavingAccountDetailDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/income/js/OthSavingAcctPopup.js"></script>
<%
	String displayMode = HtmlUtil.EDIT;
	String subformId = "OTH_SAVING_ACCT_POPUP";
	String formId = "POPUP_OTH_SAVING_ACCT_FORM";
	FormEffects formEffect = new FormEffects(formId,FormEffects.ConfigType.FORM,request);
	String tagId = "";
	Date applicationDate = ORIGForm.getObjectForm().getApplicationDate();
	if(Util.empty(applicationDate)) {
		applicationDate = ApplicationDate.getDate();
	}
	
	IncomeInfoDataM incomeM = (IncomeInfoDataM)ModuleForm.getObjectForm();
	ArrayList<IncomeCategoryDataM> savAcctList = incomeM.getAllIncomes();
	if(savAcctList == null) {
		savAcctList = new ArrayList<IncomeCategoryDataM>();
	}
	String BANK_CACHE = SystemConstant.getConstant("BANK_CODE_CACHE");
	
	FormUtil formUtil = new FormUtil(subformId, request);
 %>
<%=HtmlUtil.hidden("subFormID",subformId)%>
<div class="panel panel-default">
<div class="panel-heading"><%=HtmlUtil.getSubFormLabel(request, "LIST")%></div>
<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-12">
		<div class="form-group">
		<table id="OthSavingAcct" class="table table-striped table-hover">
			<tbody>
			<tr>
			<th></th>
			<th width="90%"></th>
			<th></th>
			<th rowspan="9999" style="vertical-align: bottom; padding-bottom: 35px;">
			<%=HtmlUtil.button("ADD_ROW_BTN", "", "", "btnsmall_add", "", formEffect)%></th>
<%-- 		<%=HtmlUtil.button("ADD_ROW_BTN","",displayMode,"btnsmall_add","",request) %></th> --%>
			</tr>		
				<%
					if(!Util.empty(savAcctList)) {
						for(IncomeCategoryDataM incomeTypeObj : savAcctList) { 
							SavingAccountDataM othSavAcctM = (SavingAccountDataM)incomeTypeObj;
							ArrayList<SavingAccountDetailDataM> detailList = othSavAcctM.getSavingAccountDetails();
							if(detailList == null) {
								detailList = new ArrayList<SavingAccountDetailDataM>();
							}
				%>
				<tr id="ROW_<%=othSavAcctM.getSeq()%>">
					<td><%=HtmlUtil.icon("DELETE_BUTTON","","btnsmall_delete BtnRemove","",formEffect) %>	</td>
<%-- 				<td><%=HtmlUtil.icon("DELETE_BUTTON",displayMode,"btnsmall_delete BtnRemove","",request) %>	</td> --%>
					<td>
						<div class="row form-horizontal">
							<div class="col-sm-6">
								<div class="form-group">
								<%=HtmlUtil.getSubFormLabel(request,subformId,"FIN_INSTITUTION","FIN_INSTITUTION","col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.dropdown("FIN_INSTITUTION",FormatUtil.getString(othSavAcctM.getSeq()),"","","",othSavAcctM.getBankCode(),"",BANK_CACHE,"","","col-sm-8 col-md-7","",formUtil)%>
<%-- 							<%=HtmlUtil.dropdown("FIN_INSTITUTION",FormatUtil.getString(othSavAcctM.getSeq()),"",othSavAcctM.getBankCode(),"",BANK_CACHE,"",displayMode,"","col-sm-8 col-md-7",request)%> --%>
								
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
								<%=HtmlUtil.getSubFormLabel(request,subformId,"PORTION","PORTION","col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.numberBox("PORTION",FormatUtil.getString(othSavAcctM.getSeq()),"","",othSavAcctM.getHoldingRatio(),"","#","","",true,"1","","col-sm-8 col-md-7",formUtil)%>
								</div>
							</div>
							<div class="clearfix"></div>
							<div class="col-sm-6">
								<div class="form-group">
								<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NO","ACCOUNT_NO","col-sm-4 col-md-5 control-label")%>
<%-- 								<%=HtmlUtil.textBoxAccountNo("ACCOUNT_NO",FormatUtil.getString(othSavAcctM.getSeq()),"","",othSavAcctM.getAccountNo(),"", "", "col-sm-8 col-md-7", formUtil)%> --%>
<%-- 								<%=HtmlUtil.numberBox("ACCOUNT_NO",FormatUtil.getString(othSavAcctM.getSeq()),"","",FormatUtil.toBigDecimal(othSavAcctM.getAccountNo(),true),"","####################","","",true,"20","","col-sm-8 col-md-7",formUtil)%> --%>
							 	<%=HtmlUtil.textBoxAccountNoOthBank("ACCOUNT_NO", FormatUtil.getString(othSavAcctM.getSeq()), "", "", othSavAcctM.getAccountNo(), "", "", "col-sm-8 col-md-7", formUtil) %>
							 	</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
								<%=HtmlUtil.getSubFormLabel(request,subformId,"ACCOUNT_NAME","ACCOUNT_NAME","col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.textBox("ACCOUNT_NAME_"+FormatUtil.getString(othSavAcctM.getSeq()),"","",othSavAcctM.getAccountName(),"","180","","col-sm-8 col-md-7",formUtil)%>
<%-- 							<%=HtmlUtil.textBox("ACCOUNT_NAME",FormatUtil.getString(othSavAcctM.getSeq()),othSavAcctM.getAccountName(),"","180",displayMode,"","col-sm-8 col-md-7",request)%> --%>
								</div>
							</div>
						</div>
						<br>
						<%--  Monthly Detail List --%>
						<table class="table table-striped table-hover">
						<caption><%= LabelUtil.getText(request, "OPEN_FUND_TABLE_HEAD")%></caption>
						<thead>
							<tr>
							<% for(int i = 0; i<SavingAccountDetailDataM.MONTH_COUNT; i++) { %>
								<th><%=FormatUtil.displayMonthYear(request, Util.getPreviousMonth(applicationDate, i))%></th>
							<%} %>
							</tr>
						</thead>
						<tbody>
							<tr>
							<%
								for(int i = 0; i < SavingAccountDetailDataM.MONTH_COUNT; i++) {
								 	BigDecimal monthlyDetail = null;
								 	if(detailList.size()>= i+1) {
									 	SavingAccountDetailDataM detailM = detailList.get(i);
									 	if(!Util.empty(detailM)) {
									 		monthlyDetail = detailM.getAmount();
									 	}
									 }
							%>
								<td><%=HtmlUtil.currencyBox("AMOUNT_"+othSavAcctM.getSeq()+"_"+(i+1),"","",monthlyDetail,"",true,"15","","",formUtil)%></td>
<%-- 							<td><%=HtmlUtil.currencyBox("AMOUNT",othSavAcctM.getSeq()+"_"+(i+1),monthlyDetail,"",true,"15",displayMode,"",request)%></td> --%>
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
<!-- 	<div class="col-sm-12" style="text-align: right;" > -->
<%-- 			<%=HtmlUtil.button("ADD_ROW_BTN","",displayMode,"btnsmall_add","",request) %> --%>
<!-- 	</div> -->
	<div class="col-sm-12" style="text-align: right;" ></div>
</div>
</div>

</div>