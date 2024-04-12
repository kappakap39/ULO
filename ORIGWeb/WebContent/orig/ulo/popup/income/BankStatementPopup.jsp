<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.sql.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.date.ApplicationDate"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.BankStatementDetailDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.BankStatementDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/income/js/BankStatementPopup.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String subformId = "BANK_STATEMENT_POPUP";
	String displayMode = HtmlUtil.EDIT;
	String tagId = "";
	Date applicationDate = ORIGForm.getObjectForm().getApplicationDate();
	if(Util.empty(applicationDate)) {
		applicationDate = ApplicationDate.getDate();
	}
	
	IncomeInfoDataM incomeM = (IncomeInfoDataM)ModuleForm.getObjectForm();
	ArrayList<IncomeCategoryDataM> statementList = incomeM.getAllIncomes();
	if(statementList == null) {
		statementList = new ArrayList<IncomeCategoryDataM>();
	}
	String BANK_CACHE = SystemConstant.getConstant("BANK_CODE_CACHE");
	String SALARY_CACHE = SystemConstant.getConstant("SALARY_CODE_CACHE");
	FormUtil formUtil = new FormUtil(subformId,request);
	FormEffects formEffect = new FormEffects(subformId,request);
 %>
<%-- <div class="panel panel-default">
	<table id="BankStatement" class="table table-striped table-hover">
	<tbody>
		<tr>
			<th></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subformId,"BANK_NAME","BANK_NAME")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subformId,"SALARY_CODE","SALARY_CODE")%></th>
			<th><%=HtmlUtil.getHeaderLabel(request,subformId,"ADDITIONAL","ADDITIONAL")%></th>
			<% for(int i = 0; i < BankStatementDetailDataM.MONTH_COUNT; i++) { %>
			<th><%=HtmlUtil.getCustomHeaderLabel(request,subformId,"HEADER_STATEMENT_AMT_"+i,FormatUtil.displayMonthYear(request, Util.getPreviousMonth(applicationDate, i+1)))%></th>
			<%} %>
			<th rowspan="9999" style="vertical-align: bottom; padding-bottom: 12px;"><%=HtmlUtil.button("ADD_ROW_BTN","ADD_BTN",displayMode,"","",request) %></th>
		</tr>
		<%
		if(!Util.empty(statementList)) {
			for(IncomeCategoryDataM statementM: statementList) { 
			BankStatementDataM monthlyM = (BankStatementDataM) statementM;
			logger.info("##Row id: ROW_"+monthlyM.getSeq());
			%>
			
		<tr id="ROW_<%=monthlyM.getSeq()%>">
			<td><%=HtmlUtil.icon("DELETE_BUTTON",displayMode,"btnsmall_delete BtnRemove","",request) %></td>
			<td><%=HtmlUtil.dropdown("BANK_NAME",FormatUtil.getString(monthlyM.getSeq()),"","","",monthlyM.getBankCode(),"",BANK_CACHE,"","","",formUtil)%></td>			
			<td><%=HtmlUtil.dropdown("SALARY_CODE",FormatUtil.getString(monthlyM.getSeq()),"","","SALARY_CODE",monthlyM.getStatementCode(),"",SALARY_CACHE,MConstant.SALARY_TYPE.SALARY,"","",formUtil)%></td>			
			<td><%=HtmlUtil.dropdown("ADDITIONAL",FormatUtil.getString(monthlyM.getSeq()),"","","SALARY_CODE",monthlyM.getAdditionalCode(),"",SALARY_CACHE,MConstant.SALARY_TYPE.ADDITIONAL,"","",formUtil)%></td>			
			
			<% // Monthly Details Data
				ArrayList<BankStatementDetailDataM> detailList = monthlyM.getBankStatementDetails();
				
			 	for(int i = 0; i < BankStatementDetailDataM.MONTH_COUNT; i++) {
				 	BigDecimal monthlyDetail = null;
				 	if(!Util.empty(detailList) && detailList.size()>= i+1) {
					 	BankStatementDetailDataM detailM = detailList.get(i);
					 	if(!Util.empty(detailM)) {
					 		monthlyDetail = detailM.getAmount();
					 	}
					 }
			 %>
			<td><%=HtmlUtil.currencyBox("AMOUNT",monthlyM.getSeq()+"_"+(i+1),"","",monthlyDetail,"",true,"15","","",formUtil)%></td>
			<%} %>
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
	<div class="panel-body">
	<div class="row form-horizontal">
		<div class="col-xs-12">
			<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"NOTE","NOTE","col-sm-2 col-md-3 control-label")%>
			<%=HtmlUtil.textBox("NOTE","","","",incomeM.getRemark(),"","200","","col-sm-8 col-md-7",formUtil)%>
			</div>
		</div>	
	</div>
</div>
</div> --%>


<div class="col-sm-12">
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="row form-horizontal">
				<div class="col-sm-12">
					<table id="BankStatement" class="table">
						<tbody>
							<tr class="tabletheme_header">
								<th></th>
								<th><%=HtmlUtil.getHeaderLabel(request,subformId,"BANK_NAME","BANK_NAME")%></th>
								<th><%=HtmlUtil.getHeaderLabel(request,subformId,"SALARY_CODE","SALARY_CODE")%></th>
								<th><%=HtmlUtil.getHeaderLabel(request,subformId,"ADDITIONAL","ADDITIONAL")%></th>
								<% for(int i = 0; i < BankStatementDetailDataM.MONTH_COUNT; i++) { %>
								<th><%=HtmlUtil.getCustomHeaderLabel(request,subformId,"HEADER_STATEMENT_AMT_"+i,FormatUtil.displayMonthYear(request, Util.getPreviousMonth(applicationDate, i)))%></th>
								<%} %>
								<td rowspan="9999" style="vertical-align: bottom; padding-bottom: 9px;"><%=HtmlUtil.button("ADD_ROW_BTN","ADD_ROW_BTN","","btnsmall_add",HtmlUtil.elementTagId("ADD_ROW_BTN"), formEffect) %></td>
							</tr>
							<tr>
								<%
						if(!Util.empty(statementList)) {
							for(IncomeCategoryDataM statementM: statementList) { 
							BankStatementDataM monthlyM = (BankStatementDataM) statementM;
							logger.info("##Row id: ROW_"+monthlyM.getSeq());
							%>
				
							<tr id="ROW_<%=monthlyM.getSeq()%>">
								<td><%=HtmlUtil.icon("DELETE_BUTTON", "DELETE_BUTTON","btnsmall_delete BtnRemove", "", formEffect) %></td>
								<td><%=HtmlUtil.dropdown("BANK_NAME",FormatUtil.getString(monthlyM.getSeq()),"","","",monthlyM.getBankCode(),"",BANK_CACHE,"","","",formUtil)%></td>			
								<td><%=HtmlUtil.dropdown("SALARY_CODE",FormatUtil.getString(monthlyM.getSeq()),"","","SALARY_CODE",monthlyM.getStatementCode(),"",SALARY_CACHE,MConstant.SALARY_TYPE.SALARY,"","",formUtil)%></td>			
								<td><%=HtmlUtil.dropdown("ADDITIONAL",FormatUtil.getString(monthlyM.getSeq()),"","","SALARY_CODE",monthlyM.getAdditionalCode(),"",SALARY_CACHE,MConstant.SALARY_TYPE.ADDITIONAL,"","",formUtil)%></td>			
								
								<% // Monthly Details Data
									ArrayList<BankStatementDetailDataM> detailList = monthlyM.getBankStatementDetails();
									
								 	for(int i = 0; i < BankStatementDetailDataM.MONTH_COUNT; i++) {
									 	BigDecimal monthlyDetail = null;
									 	if(!Util.empty(detailList) && detailList.size()>= i+1) {
										 	BankStatementDetailDataM detailM = detailList.get(i);
										 	if(!Util.empty(detailM)) {
										 		monthlyDetail = detailM.getAmount();
										 	}
										 }
								 %>
								<td><%=HtmlUtil.currencyBox("AMOUNT",monthlyM.getSeq()+"_"+(i+1),"","",monthlyDetail,"",true,"15","","",formUtil)%></td>
								<%} %>
							</tr>
						 <%	
							}	
						} else{%>
							<tr><td colspan="999" align="center" style="width: 100%"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td></tr>
						<%} %>
						</tbody>
					</table>
				</div>
			</div>
			<div class="row form-horizontal">
				<div class="col-sm-12">
					<div class="form-group">
						<%=HtmlUtil.getSubFormLabel(request,subformId,"NOTE","NOTE","col-sm-2 col-md-3 control-label")%>
						<%=HtmlUtil.textBox("NOTE","","NOTE","",incomeM.getRemark(),"","200","","col-sm-8 col-md-7",formUtil)%>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>