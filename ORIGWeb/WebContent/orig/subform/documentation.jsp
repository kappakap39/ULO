

<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("DOCUMENTATION_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

	MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
//	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
%>

<table cellpadding="" cellspacing="1" width="100%" align="center">
	<tr>
		<td><%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(msgUtil.getTextDescription(request, "DOCUMENT_LIST"), displayMode,"button", "documentBnt", "button_text", "", "") %></td>
	</tr>
</table>