<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.master.shared.model.ApprovAuthorM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>

<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />
<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	String versionSearch=(String)session.getAttribute("VERSION_SEARCH");
	if(versionSearch==null){ versionSearch="";}
%>
<%
com.eaf.j2ee.pattern.util.ErrorUtil error = new com.eaf.j2ee.pattern.util.ErrorUtil();
Vector v = ORIGMasterForm.getFormErrors();
System.out.println("Error Size = " + v.size());
for(int i=0; i<v.size();i++) {
	String errorMessage = (String)v.elementAt(i);
	out.println("<span class=\"TextWarningNormal\">*&nbsp;"+errorMessage+"</span><br>");
}
ORIGMasterForm.setFormErrors(new Vector());
%>
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail">Create Version </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                                    	<INPUT type="button" name="btnCreateNewCopy" value="Create"	class="button" onclick="createNewCopy()">
                                    </td><td>
                                    	<INPUT type="button" name="btnClose" value="Close" class="button" onclick="javascrip:window.close()">
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
			 						<td class="textColS" width="25%"> Version Name <FONT color="red">*</FONT>:</td>
								    <td class="inputCol" width="27%"><input type="text" name="newCopyVersion" class="textbox"/></td>
								    <td class="inputCol" width="12%">&nbsp;</td>
								    <td class="inputCol" width="15%"></td>
								    <td class="inputCol" width="21%"></td>
								</tr>
								<tr>
								    <td class="textColS">Effective Date <FONT color="red">*</FONT>:</td>
								    <td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName", "",displayMode,"","effectiveDate","textbox","right"," onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %>    
								    </td>
								    <td class="inputCol">&nbsp;</td>
								    <td class="textColS">ExpireDate <FONT color="red">*</FONT>:</td>
								    <td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName","",displayMode,"","expireDate","textbox","right"," onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
								 </tr>
							</table>
						</td></tr>
					</table>
				</td></tr>
			</table>
		</td></tr>		
	</table>
	</TD></TR>
</TABLE>
<input type="hidden" name="shwAddFrm" value=""><%//  'Add' or 'Edit'%>	

<script language="JavaScript">	
 
function createNewCopy(){
    if(appFormName.newCopyVersion.value==''){
         alert("Policy Version is required");
         return;
        }
         if(appFormName.effectiveDate.value==''){
         alert("Effective date is required");
         return;
        }
         if(appFormName.expireDate.value==''){
         alert("Expire date is required");
         return;
        }
		appFormName.action.value="SaveApprovAuthorCreateCopy";
		appFormName.handleForm.value = "N";
		appFormName.submit();	 
}								
 
</script>	
<div>
