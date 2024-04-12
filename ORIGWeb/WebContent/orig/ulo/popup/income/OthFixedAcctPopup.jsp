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
<%@page import="com.eaf.orig.ulo.model.app.FixedAccountDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/income/js/OthFixedAcctPopup.js"></script>
<%
	String subformId = "OTH_FIXED_ACCT_POPUP";
	String formId = "POPUP_OTH_FIXED_ACCT_FORM";
	FormEffects formEffect = new FormEffects(formId,FormEffects.ConfigType.FORM,request);
	String displayMode = HtmlUtil.EDIT;
	String tagId = "";
	
	IncomeInfoDataM incomeM = (IncomeInfoDataM)ModuleForm.getObjectForm();
	ArrayList<IncomeCategoryDataM> fixAcctList = incomeM.getAllIncomes();
	if(fixAcctList == null) {
		fixAcctList = new ArrayList<IncomeCategoryDataM>();
	}
	String BANK_CACHE = SystemConstant.getConstant("BANK_CODE_CACHE");
	
	FormUtil formUtil = new FormUtil(subformId, request);
 %>
<%=HtmlUtil.hidden("subFormID",subformId)%>
<div class="panel panel-default">
<%-- <div class="panel-heading"><%=HtmlUtil.getSubFormLabel(request, "LIST")%></div> --%>
<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-12" style="text-align: right;" >
<%-- 			<%=HtmlUtil.button("ADD_ROW_BTN","ADD_BTN",displayMode,"","",request) %> --%>
		<table id="OthFixedAcct" class="table table-striped table-hover">
			<tbody>
				<tr>
					<th></th>
					<th><%=HtmlUtil.getHeaderLabel(request,subformId,"FIN_INSTITUTION","FIN_INSTITUTION")%></th>
					<th><%=HtmlUtil.getHeaderLabel(request,subformId,"ACCOUNT_NO","ACCOUNT_NO")%></th>
					<th><%=HtmlUtil.getHeaderLabel(request,subformId,"ACCOUNT_NAME","ACCOUNT_NAME")%></th>
					<th><%=HtmlUtil.getHeaderLabel(request,subformId,"PORTION","PORTION")%></th>
					<th><%=HtmlUtil.getHeaderLabel(request,subformId,"OUTSTANDING_BALANCE","OUTSTANDING_BALANCE")%></th>
					<th rowspan="9999" style="vertical-align: bottom; padding-bottom: 12px;">
						<%=HtmlUtil.button("ADD_ROW_BTN", "", "", "btnsmall_add", "", formEffect)%></th>
<%-- 					<%=HtmlUtil.button("ADD_ROW_BTN","",displayMode,"btnsmall_add","",request) %></th> --%>
				</tr>
				<%if(!Util.empty(fixAcctList)) {
					for(IncomeCategoryDataM incomeTypeObj : fixAcctList) { 
						FixedAccountDataM othFixedAcctM = (FixedAccountDataM)incomeTypeObj;
					%>
				<tr id="ROW_<%=othFixedAcctM.getSeq()%>">
					<td><%=HtmlUtil.icon("DELETE_BUTTON","","btnsmall_delete BtnRemove","",formEffect) %>	</td>
					<td><%=HtmlUtil.dropdown("FIN_INSTITUTION_"+FormatUtil.getString(othFixedAcctM.getSeq()),"","","",othFixedAcctM.getBankCode(),"",BANK_CACHE,"","","",formUtil)%></td>	
					<td><%=HtmlUtil.textBoxAccountNoOthBank("ACCOUNT_NO", FormatUtil.getString(othFixedAcctM.getSeq()), "", "", othFixedAcctM.getAccountNo(), "", "", "", formUtil) %></td>
<%-- 					<td><%=HtmlUtil.numberBox("ACCOUNT_NO",FormatUtil.getString(othFixedAcctM.getSeq()),"","",FormatUtil.toBigDecimal(othFixedAcctM.getAccountNo(),true),"","####################","","",true,"20","","",formUtil)%></td> --%>
					<td><%=HtmlUtil.textBox("ACCOUNT_NAME",FormatUtil.getString(othFixedAcctM.getSeq()),"","",othFixedAcctM.getAccountName(),"","180","","",formUtil)%></td>
					<td><%=HtmlUtil.numberBox("PORTION",FormatUtil.getString(othFixedAcctM.getSeq()),"","",othFixedAcctM.getHoldingRatio(),"","#","","",true,"1","","",formUtil)%></td>
					<td><%=HtmlUtil.currencyBox("OUTSTANDING_BALANCE",FormatUtil.getString(othFixedAcctM.getSeq()),"","",othFixedAcctM.getAccountBalance(),"",true,"15","","","",formUtil)%></td>
<%-- 				<td><%=HtmlUtil.icon("DELETE_BUTTON",displayMode,"btnsmall_delete BtnRemove","",request) %>	</td> --%>
<%-- 				<td><%=HtmlUtil.dropdown("FIN_INSTITUTION",FormatUtil.getString(othFixedAcctM.getSeq()),"",othFixedAcctM.getBankCode(),BANK_CACHE,"",displayMode,"",request)%></td>			 --%>
<%-- 				<td><%=HtmlUtil.textBoxAccountNo("ACCOUNT_NO",FormatUtil.getString(othFixedAcctM.getSeq()),"","",othFixedAcctM.getAccountNo(),"", "", "", formUtil)%></td> --%>
<%-- 				<td><%=HtmlUtil.textBox("ACCOUNT_NAME",FormatUtil.getString(othFixedAcctM.getSeq()),othFixedAcctM.getAccountName(),"","180",displayMode,"",request)%></td> --%>
<%-- 				<td><%=HtmlUtil.currencyBox("OUTSTANDING_BALANCE",FormatUtil.getString(othFixedAcctM.getSeq()),othFixedAcctM.getAccountBalance(),"",true,"15",displayMode,"",request)%></td> --%>
				</tr>
				<%	}	
			} else{%>
			 	<tr>
			 		<td colspan="6" align="center"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
			 	</tr>
			<%}%>
<!-- 				<tr> -->
<!-- 					<td colspan="6" align="right"> -->
<%-- 						<%=HtmlUtil.button("ADD_ROW_BTN","",displayMode,"btnsmall_add","",request) %> --%>
<!-- 					</td> -->
<!-- 				</tr> -->
			</tbody>
		</table>
	</div>
	
</div>
</div>
</div>
