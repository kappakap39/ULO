<%@page import="com.eaf.orig.ulo.pl.app.utility.ManualMandatory"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLCashTransferDataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/> 
<script type="text/javascript" src="orig/js/subform/pl/cash_day_one.js" ></script>

<% 
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/subform/pl/cash_day_one.jsp");	
	
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	PLCashTransferDataM cashTransferM = applicationM.getCashTransfer();
	if(null == cashTransferM){
		cashTransferM = new PLCashTransferDataM();    
	}
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);

	ORIGCacheUtil origc = ORIGCacheUtil.getInstance();	
	String searchType = (String) request.getSession().getAttribute("searchType");   
//	Vector ratioVect = (Vector)origc.getNaosCacheDataMs(applicationM.getBusinessClassId(),36);
// 	#septemwi
// 	Vector vCashTransfer = (Vector)origc.getNaosCacheDataMs(applicationM.getBusinessClassId(), 91);
//	Vector vBankTransfer = (Vector)origc.getNaosCacheDataMs(applicationM.getBusinessClassId(), 92);
	Vector vRole = (Vector)ORIGUser.getRoles();
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String displayMode = formUtil.getDisplayMode("CASH_DAY_ONE_SUBFROM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);
	
	String displayModeRemark = displayMode;
	
	String displayModeCashDay5 = displayMode;
	if(OrigConstant.SEARCH_TYPE_CASH_DAY5.equals(searchType)){
   		displayMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
 	}
 	if(OrigConstant.ROLE_CA.equals(ORIGUser.getCurrentRole())){
 		displayModeCashDay5 = HTMLRenderUtil.DISPLAY_MODE_VIEW;
 	}
	
	String bank = cashTransferM.getBankTransfer();
	     
	if(OrigUtil.isEmptyString(bank)){
		bank = "004";
	}    
	     
%>
<table class="FormFrame">
	<tr height="25">
    	<td class="textR" align="right" width="20%"  nowrap="nowrap">
    		<%=PLMessageResourceUtil.getTextDescription(request, "CASH_DAY_ONE_LABEL",ORIGUser,personalM.getCustomerType(),"CASH_DAY_ONE_SUBFROM","cash_day1_day1Flag") %>
    		<%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CASH_DAY_ONE_SUBFROM","cash_day1_day1Flag")%>&nbsp;:&nbsp;
    	</td>
        <td class="inputL" width="25%"  nowrap="nowrap"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(91,applicationM.getBusinessClassId(),cashTransferM.getCashTransferType(), "cash_day1_day1Flag",displayMode,"onChange=\"setCashDay1Data()\"")%></td>
        <td class="textR" width="25%"  nowrap="nowrap">
        	<%=PLMessageResourceUtil.getTextDescription(request, "CASH_DAY_ONE_QUICK_CASH",ORIGUser,personalM.getCustomerType(),"CASH_DAY_ONE_SUBFROM","cash_day1_quick_cash") %>
        	<%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CASH_DAY_ONE_SUBFROM","cash_day1_quick_cash")%>&nbsp;:&nbsp;
        </td>
        <td class="inputL" width="30%" nowrap="nowrap"><%=HTMLRenderUtil.displayCheckBox(HTMLRenderUtil.displayHTML(cashTransferM.getExpressTrans()),"cash_day1_quick_cash","Y",displayMode,displayMode) %></td>
	</tr>
    <tr height="25">
    	<td class="textR"  nowrap="nowrap">
    		<%=PLMessageResourceUtil.getTextDescription(request, "CASH_DAY_ONE_RATIO",ORIGUser,personalM.getCustomerType(),"CASH_DAY_ONE_SUBFROM","cash_day1_percent") %>
    		<%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CASH_DAY_ONE_SUBFROM","cash_day1_percent")%>&nbsp;:&nbsp;
    	</td>
        <td class="inputL" id="td_cash_day1_percent" nowrap="nowrap"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(36, applicationM.getBusinessClassId(), DataFormatUtility.DisplayNumberNullEmpty(cashTransferM.getPercentTrans()), "cash_day1_percent",displayMode,"")%></td> <td class="textR">
        	<%=PLMessageResourceUtil.getTextDescription(request, "CASH_DAY_ONE_TRANSFER",ORIGUser,personalM.getCustomerType(),"CASH_DAY_ONE_SUBFROM","cash_day1_first_tranfer") %>
        	<%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CASH_DAY_ONE_SUBFROM","cash_day1_first_tranfer")%>&nbsp;:&nbsp;
        </td>
        <td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.DisplayInputCurrency(cashTransferM.getFirstTransAmount(),displayModeCashDay5,"########0.00","cash_day1_first_tranfer","textbox-currency"," ","9",true) %></td>
    	<td nowrap="nowrap"></td>
    </tr>
    <tr height="25">
    	<td class="textR" nowrap="nowrap">
    		<%=PLMessageResourceUtil.getTextDescription(request, "BANK_FOR_TRANSFER",ORIGUser,personalM.getCustomerType(),"CASH_DAY_ONE_SUBFROM","cash_day1_bank_transfer") %>
    		<%if(OrigConstant.SEARCH_TYPE_CASH_DAY5.equals(searchType)){%>
    			<%=ManualMandatory.getManadatory("CASH_DAY_ONE_SUBFROM","cash_day1_bank_transfer",request)%>&nbsp;:&nbsp;
    		<%}else{%>
    			<%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CASH_DAY_ONE_SUBFROM","cash_day1_bank_transfer")%>&nbsp;:&nbsp;
    		<%}%>
    	</td>
        <td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(92, applicationM.getBusinessClassId(), bank, "cash_day1_bank_transfer",displayModeCashDay5,"onChange=\"setCashAccount()\"")%></td>
        <td class="textR" nowrap="nowrap">
        	<%=PLMessageResourceUtil.getTextDescription(request, "CASH_DAY_ONE_ACCNO",ORIGUser,personalM.getCustomerType(),"CASH_DAY_ONE_SUBFROM","cash_day1_account_no") %>
        	<%if(OrigConstant.SEARCH_TYPE_CASH_DAY5.equals(searchType)){%>
        		<%=ManualMandatory.getManadatory("CASH_DAY_ONE_SUBFROM","cash_day1_account_no",request)%>&nbsp;:&nbsp;
        	<%}else{%>
        		<%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CASH_DAY_ONE_SUBFROM","cash_day1_account_no")%>&nbsp;:&nbsp;
        	<%}%>
        </td>
        <td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTag(cashTransferM.getTransAcc(),displayModeCashDay5,"20","cash_day1_account_no","textbox") %></td>
    </tr>
    <tr height="25">
    	<td class="textR" nowrap="nowrap">
    		<%=PLMessageResourceUtil.getTextDescription(request, "FOLLOW_REMARK",ORIGUser,personalM.getCustomerType(),"CASH_DAY_ONE_SUBFROM","cash_day1_remark") %>
    		<%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CASH_DAY_ONE_SUBFROM","cash_day1_remark")%>&nbsp;:&nbsp;
    	</td>
        <td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTag(cashTransferM.getRemark(),displayModeRemark,"250","cash_day1_remark","textbox") %></td>
        <td class="textR" nowrap="nowrap">
        	<%=PLMessageResourceUtil.getTextDescription(request, "CASH_DAY_ONE_ACCNAME",ORIGUser,personalM.getCustomerType(),"CASH_DAY_ONE_SUBFROM","cash_day1_account_name") %>
        	<%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"CASH_DAY_ONE_SUBFROM","cash_day1_account_name")%>&nbsp;:&nbsp;
        </td>
        <td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(cashTransferM.getAccName(),displayMode,"40","cash_day1_account_name","textboxReadOnly"," readonly ","60") %></td> 
    </tr>
</table>

<%=HTMLRenderUtil.displayHiddenField(ORIGUser.getCurrentRole(), "cashDay1_role") %>
<%=HTMLRenderUtil.displayHiddenField(displayMode, "cashdayone_displaymode") %>
<%=HTMLRenderUtil.displayHiddenField(searchType, "search_cash_day5") %>

