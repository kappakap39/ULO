<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<div class="PanelFirst">
	<div class="PanelSecond TextHeaderNormal">
		<div class="PanelThird" id="confirm_follow_doc_email_div">
			<input type="hidden" name="emailType" value="<%=OrigConstant.EmailSMS.EMAIL_FOLLOW_DOC_TO_BRANCH%>">
			<table class="FormFrame" width="100%" align="center" id="confirm_follow_doc_email_table">
				<tr>
					<td class="textR" nowrap="nowrap" width="30%">CC Email : </td>
					<td class="textL" nowrap="nowrap" width="70%"><%=HTMLRenderUtil.displayInputTagScriptAction("",HTMLRenderUtil.DISPLAY_MODE_EDIT,"","confirm_cc_email","textbox","onblur=\"checkEmails('confirm_cc_email')\"","50")%></td>
				</tr>
				<tr>
					<td colspan="2" class="textC" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "CONFIRM_SEND_MAIL")%></td>
				</tr>
				<tr>
					<td colspan="2" class="textC" nowrap="nowrap">
					<%=HTMLRenderUtil.displayButtonTagScriptAction("Yes",HTMLRenderUtil.DISPLAY_MODE_EDIT,"button","btnEmailYes", "buttonNew", "id=btnEmailYes onclick='sendFollowDocEmail()'", "")%>
					&nbsp;&nbsp;
					<%=HTMLRenderUtil.displayButtonTagScriptAction("No",HTMLRenderUtil.DISPLAY_MODE_EDIT,"button","btnEmailYes", "buttonNew", "id=btnEmailYes onclick='window.close()'", "")%>
					</td>
				</tr>					
			</table>
		</div>
	</div>
</div>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen("PL_MAIN_APPFORM");
%>

<script type="text/javascript">
function sendFollowDocEmail(){
	var dataString = $("#confirm_follow_doc_email_div *").serialize();
	$AjaxFrontController('SendFollowDocEmail','N',null,dataString,followDocEmailResult);	
}

function followDocEmailResult(data){
	if(data == 'fail'){
		//alert(SEND_MAIL_ERROR);
		window.opener.closeDocListPopup();
		window.close();
	}else{
		//alert(SEND_MAIL_SUCCESS);
		window.opener.closeDocListPopup();
		window.close();
	}
}
</script>