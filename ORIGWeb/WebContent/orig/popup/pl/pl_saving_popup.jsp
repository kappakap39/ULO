<%@page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.orig.shared.utility.ORIGUtility"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLFinancialInfoDataM"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLFinancialInfoDataM" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>
<script type="text/javascript" src="orig/js/subform/pl/pl_saving_popup.js"></script>

<% 
	Logger log = Logger.getLogger(this.getClass());
	ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
	
	
	PLApplicationDataM plapplicationDataM = PLORIGForm.getAppForm();
		
	PLPersonalInfoDataM plPersonalInfoDataM = plapplicationDataM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	
	Vector vFinanceDetails =(Vector)session.getAttribute("PL_SAVING_DETAIL"); //plPersonalInfoDataM.getFinanceVect();
	log.debug("vFinanceDetails.size()"+vFinanceDetails.size());
	Vector vBankName = (Vector)origc.getNaosCacheDataMs(plapplicationDataM.getBusinessClassId(), 25);
	Vector vType = (Vector)origc.getNaosCacheDataMs(plapplicationDataM.getBusinessClassId(), 75);
	 
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String displayMode = formUtil.getDisplayMode("OCCUPATION_SUBFORM", ORIGUser.getRoles(), plapplicationDataM.getJobState(), PLORIGForm.getFormID(), searchType);
	 
	String disabledButton="";
	if(HTMLRenderUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
	  disabledButton="disabled";
	}	
%>
<table class="FormFrame">
	<tr><td colspan="6"><div id="saving_pop_err_div" class="div-error-mandatory"></div></td></tr>
	<tr>
	<td class="sidebar10">
	<table class="TableFrame" width="100%">
	<tr>
		<td class="inputL" nowrap="nowrap" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "TYPE") %><font color="#FF0000">&nbsp;*</FONT></td>
		<td class="inputL" nowrap="nowrap" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "BANK") %><font color="#FF0000">&nbsp;*</FONT></td>
		<td class="inputL" nowrap="nowrap" width="18%"><%=PLMessageResourceUtil.getTextDescription(request, "FUND_ACCOUNT_NUMBER") %><font color="#FF0000">&nbsp;*</FONT></td>
		<td class="inputL" nowrap="nowrap" width="30%" colspan="2"><%=PLMessageResourceUtil.getTextDescription(request, "SAVING_AVG_CURRENT") %><font color="#FF0000">&nbsp;*</FONT></td>
	</tr>
	
	
	<tr id="saving_input">
		<td class="inputL" nowrap="nowrap" width=5%><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vType, "", "sav_type", displayMode," style=\" width='160'\" ")%> </td>
		<td class="inputL" nowrap="nowrap" width=5%><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vBankName, "", "sav_bankname", displayMode," style=\" width='160'\" ") %></td>
		<td class="inputL" nowrap="nowrap" width=5%><%=HTMLRenderUtil.displayInputTagScriptAction("", displayMode," 16", "sav_fund", "textbox", " onkeypress= \"return keyPressInteger(event)\" ","16") %></td>
		<td class="inputL" nowrap="nowrap" width=5%>
<%-- 		<%=HTMLRenderUtil.displayInputTagScriptAction("", displayMode," 12", "sav_balance", "textbox", " onblur=\"javascript:chcekMaxDecimalValue(this);addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" ","12") %> --%>
		<%=HTMLRenderUtil.DisplayInputCurrency(new BigDecimal(0.0),displayMode,"########0.00","sav_balance","textbox-currency","","12",false)%>
		</td>
		<td><%if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode)){ %>
		<input type="button" name="addSaving" class="button" <%=disabledButton%> value="<%=PLMessageResourceUtil.getTextDescription(request, "ADD") %>" onclick="javascript:addsaving()">
		<%}%>
		</td>
	 </tr>
	 </table> 
	 </td></tr>
	 
	<tr>
		<td align="center">
		   <div  id="plSavingResult">
			<table class="TableFrame" id="savingresult">
			<tr class="Header">
				<td class="textR" width="5%" align='center'><%=HTMLRenderUtil.displayCheckBox("","checkbox1","all",displayMode,"") %></td>
				<td class="textR" width="15%"><%=PLMessageResourceUtil.getTextDescription(request, "TYPE") %></td>
				<td class="textR" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "BANK") %></td>
				<td class="textR" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "FUND_ACCOUNT_NUMBER") %></td>
				<td class="textR" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "SAVING_AVG_CURRENT") %></td>
			</tr>
						
									<% 
									if(!ORIGUtility.isEmptyVector(vFinanceDetails)){
											PLFinancialInfoDataM financeDataM;					
											String addressType = null;
											String addressStatus = null;
											String disabledChk = null;
											String styleTr="0";			
											for(int i=0; i<vFinanceDetails.size(); i++){
											
												disabledChk = "";
												financeDataM = (PLFinancialInfoDataM) vFinanceDetails.get(i);								
												if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
														disabledChk = "disabled";
												}
												 styleTr = (i%2==0)?"ResultEven":"ResultOdd";				
									%>		
									<tr class="Result-Obj <%=styleTr%>" id="saving_index_<%=financeDataM.getSeq()%>">

										<td class="inputL" id="checkNo" width='5%' align='center'><%=HTMLRenderUtil.displayCheckBox("", "saving", "saving_index_"+financeDataM.getSeq(),displayMode, "")%></td>
										<td class="inputL" id="saving_type" width='15%'><%=HTMLRenderUtil.displayHTMLFieldIDDesc(financeDataM.getFinancialType(),75)%></td>
										<td class="inputL" id="saving_bank" width='20%'><%=HTMLRenderUtil.displayHTMLFieldIDDesc(financeDataM.getBank(),25)%></td>
								  		<td class="inputL" id='account_no' width='20%'><%=HTMLRenderUtil.displayHTML(financeDataM.getFinancialNo())%></td>
								  		<td class="textR" id="saving_avg_current" width='20%'><%=HTMLRenderUtil.displayHTML(DataFormatUtility.displayCommaNumber(financeDataM.getFinancialAmount()))%></td>
								  	</tr>
								  		<%}%>
								  	<%}else{%>
									  	<tr id="noSavingRecord" class="ResultNotFound ResultEven">
									  		<td align="center"colspan="5">No record found</td>
									  	</tr>
								  	<%}%>	
			</table> </div>
		</td>
	</tr>
	
	<tr>
		<td>  <%if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode)){ %>
				<INPUT type="button" name="delSaving" class="button" value=" <%=PLMessageResourceUtil.getTextDescription(request, "DELETE") %>"   onclick="javascript:removeSaving()"></td>
		       <%} %>  
		<td colspan="5"></td>
	</tr>
	
	<tr>
		<td>
			<table align="right">
				<tr align="right" >
					<td class="textR"  width="30%"><%=PLMessageResourceUtil.getTextDescription(request, "SUMMARY_SAVING") %></td>
					<td class="textR" id="saving_summary" width="30%"><%= HTMLRenderUtil.displayHTML(DataFormatUtility.displayCommaNumber(plPersonalInfoDataM.getSavingIncome()))%></td>
					<td class="textR" width="30%"><%=PLMessageResourceUtil.getTextDescription(request, "BATH") %></td>
					
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td>
			<table align="right">
				<tr align="right">
					<td colspan="3"></td>
<%-- 					<td><INPUT type="button" name="Saving" class="button" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onclick="javascript:displaysaving()"></td> --%>
<%-- 					<td><INPUT type="button" name="Closing" class="button" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" onclick="window.close()"></td> --%>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script>
    var   PL_SAVING_NOT_SELECT ="กรุณาเลือกข้อมูลสำหรับการลบ";
    var   PL_SAVING_CONFIRM="ยืนยันการลบข้อมูล";
</script>
<input type="hidden" name="plSavingPopupDisplayMode" id="plSavingPopupDisplayMode" value="<%=HTMLRenderUtil.displayHTML(displayMode)%>">
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen("PL_MAIN_APPFORM");
%>

