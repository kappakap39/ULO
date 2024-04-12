<%@ page import="com.eaf.orig.shared.view.form.util.ORIGFormUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" />

<%
String userRoles = (String)ORIGUser.getRoles().get(0);
System.out.println(">>> userRoles  "+userRoles);
ORIGFormUtil nUtil = ORIGFormUtil.getInstance_SG();
String flag = "";
String disabled = "";
String jobState = "";

ApplicationDataM appForm = ORIGForm.getAppForm();
if(appForm==null){
	appForm = new ApplicationDataM();
}
jobState = appForm.getJobState();

%>

<TABLE height=1 cellSpacing=0 cellPadding=0 width="100%" bgColor=#FFFFFF border=0 align="center">   				  
	<TR><TD height="10" colspan="2"></TD></TR>
    <TR>
		<TD align="left">
			<INPUT class="button_text" onclick="" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "VIEW_IMAGE") %>" name="imageBtn">
			<INPUT class="button_text" onclick="" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "DOCUMENT_LIST") %>" name="documentListBtn">
			<INPUT class="button_text" onclick="" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "NOTEPAD") %> " name="notepadBtn">
			<INPUT class="button_text" onclick="" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "ACTION_FLOW") %> " name="actionFlowBtn">			
		</TD>
		<TD align="right">
			<INPUT class="button_text" onclick="" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "SEND") %> " name="sendBtn">
			<INPUT class="button_text" onclick="" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "DRAFT") %> " name="draftBtn">
			<INPUT class="button_text" onclick="" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "CANCEL") %> " name="cancelBtn">
		</TD>
       </TR>
	<TR><TD height="10"></TD></TR>
</TABLE>
<script language="JavaScript">

</script>