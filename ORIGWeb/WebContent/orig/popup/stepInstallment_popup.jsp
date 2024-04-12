<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.math.*"%>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil"%>
<%@page import="com.eaf.orig.shared.utility.ORIGUtility"%>
<%@page import="com.eaf.orig.shared.model.LoanDataM"%>
<%@page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@page import="com.eaf.orig.shared.model.ApplicationDataM"%>
<%@page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@page import="com.eaf.orig.shared.model.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.shared.model.InstallmentDataM"%>

<jsp:useBean id="ORIGUser" scope="session"
	class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session"
	class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" />
<SPAN id="errorMessage" class="TextWarningNormal"></SPAN>
<html><head><title>UNIVERSAL LOAN ORIGINATION SYSTEM</title></head>
<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("LOAN_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	LoanDataM loanDataM = (LoanDataM) request.getSession().getAttribute("POPUP_DATA");
	if(loanDataM == null){
		loanDataM = new LoanDataM();
	}
	BigDecimal vat = cacheUtil.getVatByCode(loanDataM.getVAT());
	BigDecimal vatTmp = (vat.divide((new BigDecimal(100)),2,0)).add(new BigDecimal(1));
    Vector vStepInstallment=(Vector)session.getAttribute("STEP_INSTALLMENT");
    //loanDataM.getStepInstallmentVect();
    if(vStepInstallment==null){vStepInstallment=new Vector();}	
    
    Vector v = ORIGForm.getFormErrors();
	Vector vErrorFields = ORIGForm.getErrorFields();
	System.out.println("Error Size = " + v.size());
	for(int i=0; i<v.size();i++) {
		String errorMessage = (String)v.elementAt(i);
		out.println("<span class=\"TextWarningNormal\">*&nbsp;"+errorMessage+"</span><br>");
	}
	ORIGForm.setFormErrors(new Vector());
%> 
<table width="100%" border=0 cellpadding="0" cellspacing="0">
	<TR><TD height="5" colspan="3"></TD></TR>
	<TR><TD height="2" bgcolor="#3399cc" colspan="3"></TD></TR>
</table>
<div style="background-color: #F4F4F4">
	<SPAN style="background-color: #3399cc; vertical-align: top; height: 16">&nbsp;&nbsp;<font color="#FFFFFF"><b><%=MessageResourceUtil.getTextDescription(request, "STEP_INSTALLMENT") %></b></font>&nbsp;</SPAN><SPAN><img src="en_US/images/SFCorner.gif"></SPAN>&nbsp;
</div>
<TABLE cellpadding="" cellspacing="1" width="100%" align="center">
	<TBODY>
		<TR>
			<TD width="99" class="textColS"></TD>
			<TD width="127" class="textColS" align="right">&nbsp;<%=MessageResourceUtil.getTextDescription(request, "CURRENT_TERM") %> :</TD>
			<TD width="120" class="textColS"  align="right"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(String.valueOf(loanDataM.getInstallment1())),ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW,"18","currentTerm","textboxCurrencyReadonly","","") %></TD>
			<TD class="textColS" colspan="2" nowrap="nowrap" align="right">&nbsp;Current Total Hire Purchase Amount :</TD>
			<TD class="textColS" align="right" width="141"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfHairPurchaseAmt()),ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW,"18","currentHirePurchase","textboxCurrencyReadonly","","") %></TD>
		</TR>
		<TR>
			<TD width="99" class="textColS"></TD>
			<TD width="127" class="textColS" align="right"></TD>
			<TD width="120" class="textColS" align="right"></TD>
			<TD class="textColS" nowrap="nowrap" align="right" colspan="2"></TD>
			<TD class="textColS" align="right" width="141"></TD>
		</TR>
		<TR>
			<TD width="99" class="textColS"></TD>
			<TD width="127" class="textColS" align="right"></TD>
			<TD width="120" class="textColS" align="right"></TD>
			<TD class="textColS" nowrap="nowrap" align="right" colspan="2"></TD>
			<TD class="textColS" align="right" width="141"></TD>
		</TR>
		<TR>
			<TD width="99" class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "TERM_DURATION") %> :</TD>
			<TD width="127" class="textColS" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"18","term_duration","textboxCurrency","onBlur=\"javascript:returnZero(this);\" onKeyPress=\"javaScript:keyPressInteger()\"","") %></TD>
			<TD width="120" class="textColS" align="right"><%=MessageResourceUtil.getTextDescription(request, "INSTALLMENT") %> :</TD>
			<TD class="textColS" width="165"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"18","installment","textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"","") %></TD>
			<TD class="textColS" width="163">&nbsp;<INPUT type="button"
				name="btnAdd" value="<%=MessageResourceUtil.getTextDescription(request, "ADD") %>" class="button_text"  onClick="addStepInstallment()" >&nbsp;<INPUT type="button"
				name="bntDelente" value="<%=MessageResourceUtil.getTextDescription(request, "DELETE") %>" class="button_text" onClick="removeStepInstallment()"; ></TD>
			<TD class="textColS" width="141">&nbsp;</TD>
		</TR>
	</TBODY>
</TABLE>
<br><br>
<div id="tableStepInstallment">
<TABLE cellpadding="" cellspacing="1" width="100%" align="center" bgcolor="black">
	<TBODY>
		<TR>
			<TD width="26" class="tableHeaderXrules" align="center">&nbsp;</TD>
			<TD width="65" class="tableHeaderXrules" align="center"><%=MessageResourceUtil.getTextDescription(request, "SEQ") %></TD>
			<TD width="44" class=tableHeaderXrules align="center"><%=MessageResourceUtil.getTextDescription(request, "FROM") %></TD>
			<TD width="45" class="tableHeaderXrules" align="center"><%=MessageResourceUtil.getTextDescription(request, "TO") %></TD>
			<TD class="tableHeaderXrules" width="87" align="center"><%=MessageResourceUtil.getTextDescription(request, "TERM_DURATION") %></TD>
			<TD class="tableHeaderXrules" width="114" align="center"><%=MessageResourceUtil.getTextDescription(request, "AMOUNT") %></TD>
			<TD class="tableHeaderXrules" width="81" align="center"><%=MessageResourceUtil.getTextDescription(request, "VAT") %></TD>
			<TD class="tableHeaderXrules" width="141" align="center"><%=MessageResourceUtil.getTextDescription(request, "INSTALL_AMT") %></TD>
			<TD class="tableHeaderXrules" width="173" align="center"><%=MessageResourceUtil.getTextDescription(request, "TOTAL_INSTALLMENT_AMT") %></TD>
		</TR>
		<% 
		   int totalTerm=0;
           BigDecimal sumTotalInstallment=new BigDecimal(0);	
		for(int i=0;i<vStepInstallment.size();i++){
		   InstallmentDataM  prmInstallmentdataM=(InstallmentDataM)vStepInstallment.get(i);
           totalTerm+=prmInstallmentdataM.getTermDuration();
           if(prmInstallmentdataM.getInstallmentTotal()!=null){
           sumTotalInstallment=sumTotalInstallment.add(prmInstallmentdataM.getInstallmentTotal());
           }
		%>
		<TR>
			<TD width="26" bgcolor="#FFFFFF" >&nbsp;<input type="checkbox"  name="chk_<%=prmInstallmentdataM.getSeq()%>"  value="Y"></TD>
			<TD width="65" bgcolor="#FFFFFF" align="right"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(prmInstallmentdataM.getSeq()),ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW,"2","seq_"+prmInstallmentdataM.getSeq(),"textboxCurrencyReadOnly","","") %>&nbsp;</TD>
			<TD width="44" bgcolor="#FFFFFF" align="right"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(prmInstallmentdataM.getInstallmentForm()),ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW,"6","from_"+prmInstallmentdataM.getSeq(),"textboxCurrencyReadOnly","","") %>&nbsp;</TD>
			<TD width="45"  bgcolor="#FFFFFF" align="right"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(prmInstallmentdataM.getInstallmentTo()),ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW,"6","to_"+prmInstallmentdataM.getSeq(),"textboxCurrencyReadOnly","","") %>&nbsp;</TD>
			<TD width="87" bgcolor="#FFFFFF" align="right"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(prmInstallmentdataM.getTermDuration()),displayMode,"6","term_duration_"+prmInstallmentdataM.getSeq(),"textboxCurrency","onBlur=\"javascript:returnZero(this);recalculateAll()\" onKeyPress=\"javaScript:keyPressInteger()\"","") %>&nbsp;</TD>
			<TD width="114" bgcolor="#FFFFFF" align="right"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(prmInstallmentdataM.getAmount()),ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW,"18","amount_"+prmInstallmentdataM.getSeq(),"textboxCurrencyReadOnly","","") %>&nbsp;</TD>
			<TD width="81" bgcolor="#FFFFFF" align="right"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(prmInstallmentdataM.getInstallmentVat()),ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW,"18","vat_"+prmInstallmentdataM.getSeq(),"textboxCurrencyReadOnly","","") %>&nbsp;</TD>
			<TD width="141" align="right" bgcolor="#FFFFFF"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(prmInstallmentdataM.getInstallmentAmount()),displayMode,"18","installment_amount_"+prmInstallmentdataM.getSeq(),"textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);recalculateAll()\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"","") %>&nbsp;</TD>
			<TD width="173" align="right" bgcolor="#FFFFFF"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(prmInstallmentdataM.getInstallmentTotal()),ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW,"18","installment_total_"+prmInstallmentdataM.getSeq(),"textboxCurrencyReadOnly","","") %>&nbsp;</TD>
		</TR>
		 <% }%>
		<TR>
			<TD width="26" colspan="4" bgcolor="#FFFFFF"></TD>
			<TD width="87"  bgcolor="#FFFFFF"  align="right"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(totalTerm),displayMode,"6","Sum_term_duration","textboxCurrencyReadOnly","","") %>&nbsp;</TD>
			<TD colspan="3" class="textColS"><%=MessageResourceUtil.getTextDescription(request, "GRAND_TOTAL_INSTALL_AMT") %></TD>
			<TD width="173" bgcolor="#FFFFFF"  align="right"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(sumTotalInstallment),displayMode,"18","sum_installment_total","textboxCurrencyReadOnly","","") %>&nbsp;</TD>
		</TR>
	</TBODY>
</TABLE>
</div> <input type="hidden" name="stepInstallmentAction" value="">
<P align="right"><INPUT type="button" name="bntSave" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" class="button_text"   onclick="saveStepInstallment()">&nbsp;<INPUT
	type="button" name="btnCancelled" value="<%=MessageResourceUtil.getTextDescription(request, "CANCEL") %>" class="button_text" onClick="javascript:closePopup()" ></P>
<script language="javascript">
function closePopup(){
	window.close();
}
function  addStepInstallment(){
    form = document.appFormName;
	form.action.value = "SaveStepInstallmentPopup";
	form.handleForm.value = "N";
	form.stepInstallmentAction.value="Add";      
	form.submit();	 
}
function removeStepInstallment(){
   form = document.appFormName;
	form.action.value = "SaveStepInstallmentPopup";
	form.handleForm.value = "N";
	form.stepInstallmentAction.value="Remove";      
	form.submit();

}
function saveStepInstallment(){
   form = document.appFormName;
	form.action.value = "SaveStepInstallmentPopup";
	form.handleForm.value = "N";
	form.stepInstallmentAction.value="Save";      
	form.submit();

} 
function recalculateAll(){
  var loop=<%=vStepInstallment.size()%>
  var totalTerm=0;
  var sumTolalInstallment=0;
  var form=document.appFormName;
  var vat=Number(<%=vatTmp%>);
  for(var i=1;i<=loop;i++){  
   eval("form.from_"+i+".value='"+(totalTerm+1)+"'");   
   var termDuration=Number(eval("form.term_duration_"+i+".value"));
   eval("form.to_"+i+".value='"+(totalTerm+termDuration)+"'");
   totalTerm+=termDuration;
   var installment=Number(removeCommas(eval("form.installment_amount_"+i+".value")));
   var amount= Math.round(installment/vat *100)/100;     
   var installmentVat=installment-amount;
   installmentVat=Math.round(installmentVat *100)/100; 
   eval("form.amount_"+i+".value='"+amount+"'");
   eval("addComma(form.amount_"+i+")");
   eval("addDecimalFormat(form.amount_"+i+")");      
   eval("form.vat_"+i+".value='"+installmentVat+"'");
   eval("addComma(form.vat_"+i+")");
   eval("addDecimalFormat(form.vat_"+i+")");
   var totalInstallment=termDuration*installment;  
   eval("form.installment_total_"+i+".value='"+totalInstallment+"'");
   eval("addComma(form.installment_total_"+i+")");
   eval("addDecimalFormat(form.installment_total_"+i+")");
   sumTolalInstallment+=totalInstallment
  }
   eval("form.Sum_term_duration.value='"+totalTerm+"'");
   eval("form.sum_installment_total.value='"+sumTolalInstallment+"'");
   eval("addComma(form.sum_installment_total)");
   eval("addDecimalFormat(form.sum_installment_total)");
  
}
</script>	 
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>