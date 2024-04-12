<%@page import="java.util.Collections"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbAccountReportDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbAccountDataM"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<%
Logger logger = Logger.getLogger(this.getClass());
String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
String VER_RESULT_FAIL = SystemConstant.getConstant("GUIDE_LINE_VER_RESULT_FAIL");
String NCB_ACC_STATUS_CACHE = SystemConstant.getConstant("NCB_ACC_STATUS_CACHE");
String NCB_ACC_TYPE_CACHE = SystemConstant.getConstant("NCB_ACC_TYPE_CACHE");
String NCB_RULE_ID_20 = SystemConstant.getConstant("NCB_RULE_ID_20");
String NCB_RULE_ID_31 = SystemConstant.getConstant("NCB_RULE_ID_31");
String NCB_RULE_RESULT_FAIL = SystemConstant.getConstant("NCB_RULE_RESULT_FAIL");
ArrayList<String> NCB_RULE_ID_31_CONDITION = SystemConstant.getArrayListConstant("NCB_RULE_ID_31_CONDITION");
String NCB_ACCOUNT_STATUS_CLOSE = SystemConstant.getConstant("NCB_ACCOUNT_STATUS_CLOSE");
String NCB_MAX_ACCOUNT_PASS = SystemConstant.getConstant("NCB_MAX_ACCOUNT_PASS");
String NCB_ACCOUNT_TYPE_CODE_CC = SystemConstant.getConstant("NCB_ACCOUNT_TYPE_CODE_CC");
String MEMBER_SHORT_NAME_KBANK = SystemConstant.getConstant("NCB_MEMBER_SHORT_NAME_KBANK");
int INTERVAL = FormatUtil.getInt(SystemConstant.getConstant("NCB_PAYMENT_HISTORY_FORMAT_INTERVAL"));
int GROUP = FormatUtil.getInt(SystemConstant.getConstant("NCB_PAYMENT_HISTORY_FORMAT_GROUP"));
ArrayList<String>  ncbExceptionRuleIds =SystemConstant.getArrayListConstant("NCB_EXCEPTION_RULES");
String TRACKING_CODE = request.getParameter("TRACKING_CODE");
String PERSONAL_ID = request.getParameter("PERSONAL_ID");
ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)ModuleForm.getObjectForm();
PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(PERSONAL_ID);
if(Util.empty(personalInfo)){
	personalInfo = new PersonalInfoDataM();
}
NcbInfoDataM ncbInfoDataM = personalInfo.getNCBInfo(TRACKING_CODE);
if(Util.empty(ncbInfoDataM)){
	ncbInfoDataM  =  new NcbInfoDataM();
}
ArrayList<NcbAccountDataM> ncbAccounts  = new ArrayList<NcbAccountDataM>();

String NCB_RESULT =MConstant.FLAG.YES;
boolean isFailsR20Condition = ncbInfoDataM.isContainAccountReportRuleCondition(NCB_RULE_ID_20, VER_RESULT_FAIL);
if(isFailsR20Condition){
	NCB_RESULT =MConstant.FLAG.NO;
	ncbAccounts=ncbInfoDataM.fillterNotPassNCBAccountList(ncbExceptionRuleIds,VER_RESULT_FAIL,NCB_ACCOUNT_STATUS_CLOSE,Integer.parseInt(NCB_MAX_ACCOUNT_PASS),NCB_ACCOUNT_TYPE_CODE_CC);
}else{
	ncbAccounts = ncbInfoDataM.fillterPassNCBAccountList(ncbExceptionRuleIds,VER_RESULT_FAIL,NCB_ACCOUNT_STATUS_CLOSE,Integer.parseInt(NCB_MAX_ACCOUNT_PASS),NCB_ACCOUNT_TYPE_CODE_CC);
}

// boolean isFailsR31Condition = ncbInfoDataM.isContainAccountReportRuleCondition(NCB_RULE_ID_31, VER_RESULT_FAIL);
boolean isFailsR31Condition = false;
for(String ruleId31 : NCB_RULE_ID_31_CONDITION){
	if(ncbInfoDataM.isContainAccountReportRuleCondition(ruleId31, VER_RESULT_FAIL)){
		isFailsR31Condition = true;
		break;
	}
}

ArrayList<NcbAccountDataM> ncbAccR31List  = new ArrayList<NcbAccountDataM>();
if(isFailsR31Condition){
for(String ruleId31 : NCB_RULE_ID_31_CONDITION){
if(!Util.empty(ncbAccR31List=ncbInfoDataM.filterNCBAccountByRule(ruleId31,VER_RESULT_FAIL))){
  	 ncbAccR31List = ncbAccR31List=ncbInfoDataM.filterNCBAccountByRule(ruleId31,VER_RESULT_FAIL);
  	 break;
  	 }
   }
}


// if(isFailsR31Condition){
//    ncbAccR31List=ncbInfoDataM.filterNCBAccountByRule(NCB_RULE_ID_31,VER_RESULT_FAIL);
// }
 
 %>
<% if(!Util.empty(ncbAccounts) || !Util.empty(ncbAccR31List)){%>
<div class="panel-heading"><%=LabelUtil.getText(request, "CC")%></div>
<div class="panel-body">
	<div class="row form-horizontal">
		<div class="col-sm-12">
				<div class="form-group">
					<div class="col-sm-2">
						<%=HtmlUtil.radioInline("NCB_PASS", ncbInfoDataM.getTrackingCode()+"_"+PRODUCT_CRADIT_CARD, NCB_RESULT,  MConstant.FLAG.YES, HtmlUtil.VIEW, HtmlUtil.elementTagId("NCB_PASS"), "NCB_PASS", request) %>		
					</div>
					<div class="col-sm-3S">
						<%=HtmlUtil.radioInline("NCB_NOT_PASS", ncbInfoDataM.getTrackingCode()+"_"+PRODUCT_CRADIT_CARD, NCB_RESULT,  MConstant.FLAG.NO, HtmlUtil.VIEW, HtmlUtil.elementTagId("NCB_NOT_PASS"), "NCB_NOT_PASS", request) %>		
					</div>
				</div>
		</div>
<!-- 		or 20 fail or pass -->
		<div class="col-sm-12">
			<table class="table table-bordered table-hover">
				<tbody>
					<tr class="tabletheme_header ">
						<th><%=HtmlUtil.getLabel(request, "NCB_ACCOUNT_TYPE", "")%></th>
						<th><%=HtmlUtil.getLabel(request, "NCB_DATE_OF_LAST_PAYMENT","") %></th>
						<th><%=HtmlUtil.getLabel(request, "NCB_CREDIT_LIMIT","") %></th>
						<th><%=HtmlUtil.getLabel(request, "NCB_AMOUNT_OWED","") %></th>
						<th><%=HtmlUtil.getLabel(request, "NCB_ACCOUNT_STATUS","") %></th>
						<th><%=HtmlUtil.getLabel(request, "NCB_AS_OF_DATE","") %></th>
						<th><%=HtmlUtil.getLabel(request, "NCB_PAYMENT_HISTORY","") %></th>
						<%if(isFailsR20Condition){%>
						<th><%=HtmlUtil.getLabel(request, "NCB_MEMBER_SHORT_NAME","") %></th> <!-- display by condition -->
						<th><%=HtmlUtil.getLabel(request, "NCB_DATE_OF_ACCOUNT_CLOSE","") %></th> <!-- display by condition -->
						<%} %>
					</tr>
				<%if(!Util.empty(ncbAccounts)) {
// 					Collections.sort(ncbAccounts, new NcbAccountDataM());
					for(NcbAccountDataM  ncbAcount :ncbAccounts){ 
						boolean isNPL =  ncbAcount.isAccountReportNPL(NCB_RULE_ID_20, NCB_RULE_RESULT_FAIL);
						String NCB_RULEID_DESC="";
						if(isNPL){
							NCB_RULEID_DESC = LabelUtil.getText(request, "NCB_NPL");
						}	
					%>
					<tr>
						<td><%=CacheControl.getName(NCB_ACC_TYPE_CACHE, ncbAcount.getAccountType())%><%=MEMBER_SHORT_NAME_KBANK.equalsIgnoreCase(ncbAcount.getMemberShortName())?"("+LabelUtil.getText(request, "NCB_IS_KBANK_ACCOUNT")+")":""%></td>	
						<td><%=FormatUtil.display(ncbAcount.getDateOfLastPmt(), FormatUtil.TH,FormatUtil.Format.ddMMyyyy) %></td>	
						<td><%=FormatUtil.displayCurrency(ncbAcount.getCreditlimOrloanamt(), true)%></td>	
						<td><%=FormatUtil.displayCurrency(ncbAcount.getAmtOwed(), true) %></td>	
						<td><%=FormatUtil.display(ncbAcount.getAccountStatus()) %>&nbsp;:&nbsp;<%=CacheControl.getName(NCB_ACC_STATUS_CACHE, ncbAcount.getAccountStatus()) %></td>
						<td><%=FormatUtil.display(ncbAcount.getAsOfDate(), FormatUtil.TH, FormatUtil.Format.ddMMyyyy) %></td>		
						<td><%=FormatUtil.displayNCBPaymentHistory(FormatUtil.displayText(ncbAcount.getPmtHist1())+FormatUtil.displayText(ncbAcount.getPmtHist2()),INTERVAL, GROUP)%>&nbsp;</td>
						<%if(isFailsR20Condition){%>
						<td><%=FormatUtil.display(ncbAcount.getMemberShortName())%></td> <!-- display by condition -->
						<td><%=FormatUtil.display(ncbAcount.getDateAccClosed(), FormatUtil.TH,FormatUtil.Format.ddMMyyyy) %></td>	 <!-- display by condition -->			
						<%} %>
					</tr>
					<%}
				}else{ %>	
					<tr><td colspan="999" align="center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td></tr>
				<%} %>
				</tbody>
			</table>
		</div>
<!-- 		or 31 fails -->
		<%if(!Util.empty(ncbAccR31List)){%>
			<div class="col-sm-12">
				<table class="table table-bordered table-hover">
					<tbody>
						<tr class="tabletheme_header">
							<th><%=HtmlUtil.getLabel(request, "NCB_DATE_OF_LAST_DEB", "")%></th>
							<th><%=HtmlUtil.getLabel(request, "NCB_PREVIOUS_INFO", "")%></th>
							<th><%=HtmlUtil.getLabel(request, "NCB_ACCOUNT_STATUS", "")%></th>
							<th><%=HtmlUtil.getLabel(request, "NCB_ACCOUNT_TYPE", "")%></th>
							<th><%=HtmlUtil.getLabel(request, "NCB_CREDIT_LIMIT","") %></th>
							<th><%=HtmlUtil.getLabel(request, "NCB_AMOUNT_OWED","") %></th>
							<th><%=HtmlUtil.getLabel(request, "NCB_DATE_OF_LAST_PAYMENT","") %></th>
							<th><%=HtmlUtil.getLabel(request, "NCB_AS_OF_DATE","") %></th>
							<th><%=HtmlUtil.getLabel(request, "NCB_DATE_OF_ACCOUNT_CLOSE","") %></th>
							<th><%=HtmlUtil.getLabel(request, "NCB_PAYMENT_HISTORY","") %></th>
							
						</tr>
					<%if(!Util.empty(ncbAccR31List)) {
// 						Collections.sort(ncbAccR31List, new NcbAccountDataM());
						for(NcbAccountDataM  ncbAcountR31 :ncbAccR31List){
						    boolean isGreaterThan1Year =ncbAcountR31.isAccountReportGreaterThan1Year(MConstant.FLAG.YES);
						    String PRE_VIOUS_DESC =LabelUtil.getText(request, "NCB_LESS_THAN_1_YEAR");
						   	 if(isGreaterThan1Year){
						   	 	PRE_VIOUS_DESC = LabelUtil.getText(request, "NCB_GREATER_THAN_1_YEAR");
						   	 }
						    %>
						<tr>
							<td><%=FormatUtil.display(ncbAcountR31.getLastDebtRestDate(), FormatUtil.TH,FormatUtil.Format.ddMMyyyy)%></td>	
							<td><%=PRE_VIOUS_DESC%></td>	
							<td><%=FormatUtil.display(ncbAcountR31.getAccountStatus()) %>&nbsp;:&nbsp;<%=CacheControl.getName(NCB_ACC_STATUS_CACHE, ncbAcountR31.getAccountStatus()) %></td>	
							<td><%=CacheControl.getName(NCB_ACC_TYPE_CACHE, ncbAcountR31.getAccountType())%><%=MEMBER_SHORT_NAME_KBANK.equalsIgnoreCase(ncbAcountR31.getMemberShortName())?"("+LabelUtil.getText(request, "NCB_IS_KBANK_ACCOUNT")+")":""%></td>	
							<td><%=FormatUtil.displayCurrency(ncbAcountR31.getCreditlimOrloanamt(), true)%></td>	
							<td><%=FormatUtil.displayCurrency(ncbAcountR31.getAmtOwed(), true) %></td>	
							<td><%=FormatUtil.display(ncbAcountR31.getDateOfLastPmt(), FormatUtil.TH,FormatUtil.Format.ddMMyyyy) %></td>	
							<td><%=FormatUtil.display(ncbAcountR31.getAsOfDate(), FormatUtil.TH, FormatUtil.Format.ddMMyyyy) %></td>	
							<td><%=FormatUtil.display(ncbAcountR31.getDateAccClosed(), FormatUtil.TH, FormatUtil.Format.ddMMyyyy)%></td>	
							<td><%=FormatUtil.displayNCBPaymentHistory(ncbAcountR31.getPmtHist1()+ncbAcountR31.getPmtHist2(),INTERVAL, GROUP)%>&nbsp;</td>	
						</tr>
						<%}
					} %>	
					</tbody>
				</table>
			</div>
		<%}%>
<!-- 		<div class="col-sm-12"> -->
<!-- 			<div class="col-sm-5"> -->
<!-- 				<div class="form-group"> -->
<%-- 					<%=HtmlUtil.getLabel(request, "NCB_NO_OF_CARD", "col-sm-4 col-md-5 control-label")%> --%>
<%-- 					<label class="LabelField col-sm-5 col-md-5 control-label"><%=String.valueOf(ncbInfoDataM.getNoOfCCCard())%></label> --%>
<%-- 					<%=HtmlUtil.getLabel(request, "NCB_CARD_UNIT", "col-sm-3 col-md-2 control-label")%> --%>
<!-- 				</div> -->
<!-- 		   </div> -->
<!-- 			<div class="col-sm-5"> -->
<!-- 				<div class="form-group"> -->
<%-- 					<%=HtmlUtil.getLabel(request, "NCB_AMOUNT_OWED", "col-sm-4 col-md-5 control-label")%>				 --%>
<%-- 					<label class="LabelField col-sm-5 col-md-5 control-label"><%=FormatUtil.displayCurrency(0!=ncbInfoDataM.getTotCCOutStanding()?new BigDecimal(ncbInfoDataM.getTotCCOutStanding()):BigDecimal.ZERO, true)%></label> --%>
<%-- 					<%=HtmlUtil.getLabel(request, "NCB_BAHT_UNIT", "col-sm-3 col-md-2 control-label")%> --%>
<!-- 				</div> -->
<!-- 		   </div> -->
<!--  	 </div> -->
  </div>
</div>
<%}%>