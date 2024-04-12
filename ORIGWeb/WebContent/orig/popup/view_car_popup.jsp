<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	Vector carVect = new Vector();
	Vector gearVect = new Vector();
%>
<table width="100%" border=0 cellpadding="0" cellspacing="0">
	<TR><TD height="5" colspan="3"></TD></TR>
	<TR><TD height="2" bgcolor="#3399cc" colspan="3"></TD></TR>
</table>
<div style="background-color: #F4F4F4">
	<SPAN style="background-color: #3399cc; vertical-align: top; height: 16">&nbsp;&nbsp;<font color="#FFFFFF"><b><%=msgUtil.getTextDescription(request, "CAR")%></b></font>&nbsp;</SPAN><SPAN><img src="en_US/images/SFCorner.gif"></SPAN>&nbsp;
</div>
<table cellpadding="" cellspacing="1" width="100%" align="center">
	<tr>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "CATEGORY") %> :</td>
		<td class="inputCol" width="20%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","car_category","textbox","","") %></td>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "ENGINE_NO") %> :</td>
		<td class="inputCol" width="15%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","car_engine_no","textbox","","") %></td>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "CAR") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(carVect,"","car",displayMode,"") %></td>
	</tr>
	<tr>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "BRAND") %> :</td>
		<td class="inputCol" width="20%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","car_brand","textbox","","") %></td>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "REGIS_NO") %> :</td>
		<td class="inputCol" width="15%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","car_register_no","textbox","","") %></td>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "GEAR") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(gearVect,"","car_gear",displayMode,"") %></td>
	</tr>
	<tr>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "MODEL") %> :</td>
		<td class="inputCol" width="20%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","car_model","textbox","","") %></td>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "CLASSIS_NO") %> :</td>
		<td class="inputCol" width="15%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","car_classis_no","textbox","","") %></td>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "WEIGHT") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","car_weight","textbox","","") %></td>
	</tr>
	<tr>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "LICENSE_TYPE") %> :</td>
		<td class="inputCol" width="20%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","car_license_type","textbox","","") %></td>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "CC") %> :</td>
		<td class="inputCol" width="15%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","car_cc","textbox","","") %></td>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "YEAR") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","car_year","textbox","","") %></td>
	</tr>
	<tr>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "PROVIVCE") %> :</td>
		<td class="inputCol" width="20%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","car_province","textbox","","") %></td>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "KILOMETER") %> :</td>
		<td class="inputCol" width="15%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","car_kilometers","textbox","","") %></td>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "REGIS_DATE") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName","",displayMode,"","car_register_date","textbox","right","") %></td>
	</tr>
	<tr>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "CAR_USER") %> :</td>
		<td class="inputCol" width="20%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","car_user","textbox","","") %></td>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "OCCUPY_DATE") %> :</td>
		<td class="inputCol" width="15%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName","",displayMode,"","car_occupy_date","textbox","right","") %></td>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "EXPIRY_DATE") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName","",displayMode,"","car_expiry_date","textbox","right","") %></td>
	</tr>
	<tr>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "OBJECTIVE") %> :</td>
		<td class="inputCol" width="20%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","car_objective","textbox","","") %></td>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "CAR_PARK") %> :</td>
		<td class="inputCol" width="15%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","car_park","textbox","","") %></td>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "TRAVALLING_ROUT") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","car_travaling","textbox","","") %></td>
	</tr>
</table>
<table cellpadding="" cellspacing="1" width="100%" align="center">
	<tr bgcolor="#26a846" height="18">
		<td><font color="white"><b>&nbsp;<%=msgUtil.getTextDescription(request, "INS") %></b></font>></td>
	</tr>
	<tr>
		<td class="textColS" width="20%"><%=msgUtil.getTextDescription(request, "OWNER_INS") %> :</td>
		<td class="inputCol" width="25%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","owner_ins","textbox","","") %></td>
		<td class="textColS" width="25%"><%=msgUtil.getTextDescription(request, "GUARANTEE_AMT") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","guarantee_amount","textbox","","") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "INS_TYPE") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","ins_type","textbox","","") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "POLICY_NO") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","policy_no","textbox","","") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "INS_COMPANY") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","ins_company","textbox","","") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "PREMIUM_AMT") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","premium_amount","textbox","","") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "GROSS_PREMIUM_AMT") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","gross_premium_amount","textbox","","") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "EXPIRY_DATE") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","expiry_date","textbox","","") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "EFFECTIVE_DATE") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName","",displayMode,"","effective_date","textbox","right","") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "ACC_POLICY_NO") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","acc_policy_no","textbox","","") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "ACC_INS") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","acc_insurence","textbox","","") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "ACC_PREMIUM_AMT") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","acc_promiun_amount","textbox","","") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "ACC_INS_COMPANY") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","acc_ins_company","textbox","","") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "ACC_EXPIRY_DATE") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName","",displayMode,"","acc_expiry_date","textbox","right","") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "NOTIFIACTION_NO") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","notification_no","textbox","","") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "PAYMENT_TYPE_INS") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","payment_type_ins","textbox","","") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "ACC_GROSS_PREMIUM_AMT") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","acc_gross_amount","textbox","","") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "COVERAGE_AMT") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","coverage_amount","textbox","","") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "ACC_EFFECTIVE_DATE") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName","",displayMode,"","acc_effective_date","textbox","right","") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "CONFIRM_PREMIUM_AMT") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","confirm_amount","textbox","","") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "NOTIFICATION_DATE") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName","",displayMode,"","notification_date","textbox","right","") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "ACC_CONFIRM_PREMIUN_AMT") %> :</td>
		<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","acc_confirm_amount","textbox","","") %></td>
	</tr>
</table>
<table>
	<tr>
		<td><%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(msgUtil.getTextDescription(request, "SAVE"), displayMode,"button", "saveBnt", "button_text", "", "") %>
			<%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(msgUtil.getTextDescription(request, "CLOSE"), displayMode,"button", "closeBnt", "button_text", "", "") %></td>
	</tr>
</table>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>