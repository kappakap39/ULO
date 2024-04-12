<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.eaf.orig.shared.view.form.ORIGSubForm" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGForm" />
<span id="errorMessage" class="TextWarningNormal"></span>
<script language="javascript">
// 	window.onbeforeunload = function(){
// 		if((window.event.clientX<0)||(window.event.clientY<0)){	
// 		}
// 	}
</Script>
<script language="JavaScript" src="/js/utility.js"></script>
<%
System.out.println("[view_application.jsp]....Start ");
com.eaf.j2ee.pattern.util.ErrorUtil error = new com.eaf.j2ee.pattern.util.ErrorUtil();
//if(ORIGForm.hasErrors()) {
Vector v = ORIGForm.getFormErrors();
Vector vErrorFields = ORIGForm.getErrorFields();
System.out.println("[view_application.jsp]....Error Size = " + v.size());
for(int i=0; i<v.size();i++) {
	String errorMessage = (String)v.elementAt(i);
	out.println("<span class=\"TextWarningNormal\">*&nbsp;"+errorMessage+"</span><br>");
}
ORIGForm.setErrors(null);
HashMap clear = new HashMap();
ORIGForm.setFormErrors(new Vector());
ORIGForm.setErrorFields(new Vector());
//}
MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
%>
<table width="100%" border=0 cellpadding="0" cellspacing="0" align="left" valign="top" onkeydown="testKeyDown()">
	<tr>
		<td>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
			<TR><TD height="20" colspan="3"></TD></TR>			
			<%
			// get All subForm from Hash
			// check with current tab
			// sort Subform put in Vector
			// loop include file
			com.eaf.orig.shared.utility.ORIGFormUtil formUtil=com.eaf.orig.shared.utility.ORIGFormUtil.getInstance();
			String currentTab = ORIGForm.getCurrentTab();
			HashMap allSubForms = ORIGForm.getSubForms();					
			System.out.println("[view_application.jsp]....allSubForms = " +allSubForms);
			System.out.println("[view_application.jsp]....ButtonFile = " +ORIGForm.getButtonFile() );
			System.out.println("[view_application.jsp]....currentTab = " +currentTab);			
			Vector allIncludedFiles = formUtil.getSortedFileNameByCurrentTab(currentTab,allSubForms);			
			System.out.println("[view_application.jsp]....allIncludedFiles = " +allIncludedFiles );	
			String includedFileName=null;
			String subformDesc=null;
			ORIGSubForm subForm;
			String personalType = (String) request.getSession().getAttribute("PersonalType");
			for(int i=0;i<allIncludedFiles.size();i++ ){ // for 1
				subForm = (ORIGSubForm) allIncludedFiles.get(i);
				//includedFileName = (String)allIncludedFiles.get(i);
				includedFileName = subForm.getFileName();
				System.out.println("[view_application.jsp]....current file include = " + includedFileName );
				subformDesc = subForm.getSubformDesc();
				
				if(true){// if 1
			%>
					<TR><TD height="2" bgcolor="#3399cc" colspan="3"></TD></TR>
					<TR valign="bottom" bgcolor="#F4F4F4">
						<TD colspan="2">
							<div>
								<SPAN style="background-color: #3399cc; vertical-align: top; height: 16">&nbsp;&nbsp;<font color="#FFFFFF"><b><%=msgUtil.getTextDescription(request, subformDesc) %></b></font>&nbsp;</SPAN><SPAN><img src="../../en_US/images/SFCorner.gif"></SPAN>&nbsp;
							</div>
						</TD>
						<TD align="right"><a href="#"><img src="../../en_US/images/backToTop.gif"></a>&nbsp;&nbsp;&nbsp;</TD>
					</TR>
					<TR><TD height="10" bgcolor="#F4F4F4" colspan="3"></TD></TR>
					<%//includedFileName%>
					<TR bgcolor="#FFFFFF">
						<TD colspan="3"><jsp:include page="<%=includedFileName%>" flush="true" /></TD>
					</TR>
					<TR><TD height="10" bgcolor="#F4F4F4" colspan="3"></TD></TR>
					<TR><TD HEIGHT="1" bgcolor="#cccccc" colspan="3"></TD></TR>
			<%
				}//end if 1
				
			  }// end for 1
			%>
			<TR>
				<TD colspan="3">
					<TABLE border=0 cellSpacing=0 cellPadding=0 width=100% bgColor=#FFFFFF height="1">
						<tr>
							<td>
								<%String menuSequence = (String)session.getAttribute("menuSequence");%> 
							</td>
							<td>
								<%if(ORIGForm.getButtonFile()!=null){%> 
									<jsp:include page="<%=ORIGForm.getButtonFile()%>" flush="true" /> 
								<%}%>
							</td>
						</tr>
					</TABLE>
				</TD>
			</TR>
		</table>
		</td>
	</tr>
</table>
<%
	System.out.println("[view_application.jsp]....End ");
%>
<SCRIPT language="JavaScript">
function refreshApp(){
	alert('refresh');
	appFormName.action.value="loadAppForm";
	appFormName.handleForm.value = "N";
	appFormName.submit();
}
</SCRIPT>