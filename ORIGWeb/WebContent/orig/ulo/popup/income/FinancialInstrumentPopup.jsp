<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.sql.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.FinancialInstrumentDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/income/js/FinInstrumentPopup.js"></script>
<%
	String subformId = "FIN_INSTRUMENT_POPUP";
	String formId = "POPUP_FIN_INSTRUMENT_FORM";
	FormEffects formEffect = new FormEffects(formId,FormEffects.ConfigType.FORM,request);
	String displayMode = HtmlUtil.EDIT;
	String tagId = "";
	IncomeInfoDataM incomeM = (IncomeInfoDataM)ModuleForm.getObjectForm();
	ArrayList<IncomeCategoryDataM> finInstrumentList = incomeM.getAllIncomes();
	if(finInstrumentList == null) {
		finInstrumentList = new ArrayList<IncomeCategoryDataM>();
	}
	String BANK_CACHE = SystemConstant.getConstant("BANK_CODE_CACHE");
	String INC_TYPE_FIN_INSTR_KBANK = SystemConstant.getConstant("INC_TYPE_FIN_INSTR_KBANK");
	String FIELD_ID_INSTRUMENT_TYPE = SystemConstant.getConstant("FIELD_ID_INSTRUMENT_TYPE");
	String ISSUER_NAME_KBANK = SystemConstant.getConstant("ISSUER_NAME_KBANK");
	
	String issuerDisplayMode = displayMode;
	if(INC_TYPE_FIN_INSTR_KBANK.equals(incomeM.getIncomeType())) {
		issuerDisplayMode = HtmlUtil.VIEW;
	}
	FormUtil formUtil = new FormUtil(subformId, request);
 %>
<%=HtmlUtil.hidden("subFormID",subformId)%>
<div class="panel panel-default">
<div class="panel-body">
	<table id="FinInstrument" class="table table-striped table-hover">
	<tbody>
		<tr>
			<th width="1%"></th>
			<th width="12%"><%=HtmlUtil.getHeaderLabel(request,subformId,"START_DATE","INSTR_START_DATE")%></th>
			<th width="12%"><%=HtmlUtil.getHeaderLabel(request,subformId,"END_DATE","INSTR_END_DATE")%></th>
			<th width="12%"><%=HtmlUtil.getHeaderLabel(request,subformId,"INSTR_ISSUED_NAME","INSTR_ISSUED_NAME")%></th>
			<th width="25%"><%=HtmlUtil.getHeaderLabel(request,subformId,"INSTRUMENT_TYPE","INSTRUMENT_TYPE")%></th>
			<th width="12%"><%=HtmlUtil.getHeaderLabel(request,subformId,"HOLDER_NAME","HOLDER_NAME")%></th>
			<th width="12%"><%=HtmlUtil.getHeaderLabel(request,subformId,"PORTION","PORTION")%></th>
			<th width="12%"><%=HtmlUtil.getHeaderLabel(request,subformId,"FIN_INSTRUMENT_AMT","FIN_INSTRUMENT_AMT")%></th>
			<th width="2%" rowspan="9999" style="vertical-align: bottom; padding-bottom: 12px;">
			<%=HtmlUtil.button("ADD_ROW_BTN", "", "", "btnsmall_add", "", formEffect)%></th>
<%-- 		<%=HtmlUtil.button("ADD_ROW_BTN","",displayMode,"btnsmall_add","",request) %></th> --%>
		</tr>
		<%
		if(!Util.empty(finInstrumentList)) {
			for(IncomeCategoryDataM incomeTypeObj : finInstrumentList) { 
				FinancialInstrumentDataM finInstrumentM = (FinancialInstrumentDataM)incomeTypeObj;
				if(INC_TYPE_FIN_INSTR_KBANK.equals(incomeM.getIncomeType())) {
					finInstrumentM.setIssuerName(CacheControl.getName(BANK_CACHE,ISSUER_NAME_KBANK));
				}
			%>
		<tr id="ROW_<%=finInstrumentM.getSeq()%>">
			<td><%=HtmlUtil.icon("DELETE_BUTTON","","btnsmall_delete BtnRemove","",formEffect) %>	</td>
			<td><%=HtmlUtil.calendar("START_DATE",FormatUtil.getString(finInstrumentM.getSeq()),"START_DATE_"+finInstrumentM.getSeq(),"",finInstrumentM.getOpenDate(),"","",HtmlUtil.TH,"", "",formUtil)%></td>
			<td><%=HtmlUtil.calendar("END_DATE",FormatUtil.getString(finInstrumentM.getSeq()),"END_DATE_"+finInstrumentM.getSeq(),"",finInstrumentM.getExpireDate(),"","",HtmlUtil.TH,"", "",formUtil)%></td>
			<td><%=HtmlUtil.textBox("INSTR_ISSUED_NAME_"+FormatUtil.getString(finInstrumentM.getSeq()),"","INSTR_ISSUED_NAME",finInstrumentM.getIssuerName(),"","80","","",formUtil)%></td>
<%-- 		<td><%=HtmlUtil.calendar("END_DATE",FormatUtil.getString(finInstrumentM.getSeq()),"END_DATE_"+finInstrumentM.getSeq(),finInstrumentM.getExpireDate(),"",displayMode,"",HtmlUtil.TH, "",request)%></td> --%>
<%-- 		<td><%=HtmlUtil.calendar("START_DATE",FormatUtil.getString(finInstrumentM.getSeq()),"START_DATE_"+finInstrumentM.getSeq(),finInstrumentM.getOpenDate(),"",displayMode,"",HtmlUtil.TH,"",request)%></td>			 --%>
<%-- 		<td><%=HtmlUtil.textBox("INSTR_ISSUED_NAME",FormatUtil.getString(finInstrumentM.getSeq()),finInstrumentM.getIssuerName(),"","80",issuerDisplayMode,"",request)%></td> --%>
			<td>
				<div class="input-group">
				<%=HtmlUtil.dropdown("INSTRUMENT_TYPE",FormatUtil.getString(finInstrumentM.getSeq()),"","","",finInstrumentM.getInstrumentType(),"",FIELD_ID_INSTRUMENT_TYPE,"","","input-group-btn","",formUtil)%>
				<%=HtmlUtil.textBox("INSTRUMENT_TYPE_OTH_"+FormatUtil.getString(finInstrumentM.getSeq()),"","",finInstrumentM.getInstrumentTypeDesc(),"","80","","input-group-btn",formUtil)%>
<%-- 			<%=HtmlUtil.dropdown("INSTRUMENT_TYPE",FormatUtil.getString(finInstrumentM.getSeq()),"",finInstrumentM.getInstrumentType(),"",FIELD_ID_INSTRUMENT_TYPE,"",displayMode,"","input-group-btn",request)%> --%>
<%-- 			<%=HtmlUtil.textBox("INSTRUMENT_TYPE_OTH",FormatUtil.getString(finInstrumentM.getSeq()),finInstrumentM.getInstrumentTypeDesc(),"","80",displayMode,"","input-group-btn",request)%> --%>
				</div>
			</td>
			<td width=""><%=HtmlUtil.textBox("HOLDER_NAME_"+FormatUtil.getString(finInstrumentM.getSeq()),"","",finInstrumentM.getHolderName(),"","180","","",formUtil)%></td>
			<td width=""><%=HtmlUtil.numberBox("PORTION",FormatUtil.getString(finInstrumentM.getSeq()),"","",finInstrumentM.getHoldingRatio(),"","#","","",true,"1","","",formUtil)%></td>
			<td width=""><%=HtmlUtil.currencyBox("FIN_INSTRUMENT_AMT_"+FormatUtil.getString(finInstrumentM.getSeq()),"","",finInstrumentM.getCurrentBalance(),"",true,"15","","",formUtil)%></td>
<%-- 		<td width=""><%=HtmlUtil.textBox("HOLDER_NAME",FormatUtil.getString(finInstrumentM.getSeq()),finInstrumentM.getHolderName(),"","180",displayMode,"",request)%></td> --%>
<%-- 		<td width=""><%=HtmlUtil.numberBox("PORTION",FormatUtil.getString(finInstrumentM.getSeq()),"","",finInstrumentM.getHoldingRatio(),"","#","","",true,"1","","",formUtil)%></td> --%>
<%-- 		<td width=""><%=HtmlUtil.currencyBox("FIN_INSTRUMENT_AMT",FormatUtil.getString(finInstrumentM.getSeq()),finInstrumentM.getCurrentBalance(),"",true,"15",displayMode,"",request)%></td> --%>
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


