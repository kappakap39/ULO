<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.cache.TableLookupCache" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<%
	
	String searchType = "";
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	ORIGUtility utility = new ORIGUtility();
	Vector customerTypeVect = utility.loadCacheByName("CustomerType");
	Vector loanTypeVect = utility.loadCacheByName("LoanType");
	HashMap hash=TableLookupCache.getCacheStructure();
%>
<%
	Vector userRoles = ORIGUser.getRoles();
	String role = (String) userRoles.get(0);
	String interanChk = utility.getInternalChecker(ORIGUser.getUserName());
	String errMsg="";
	if(!(null==interanChk||"".equals(interanChk))){				  			 	    
%>
		
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR>
		<TD vAlign=top>
			<TABLE  cellSpacing=0 cellPadding=0  width="100%" align=left border="0">
           		<TR><TD class="sidebar8">
					<TABLE cellSpacing=0 cellPadding=0 width="100%" align="left"  border="0" bgcolor="FFFFFF">	
							<tr><td height="20"></td></tr>
							<tr>
								<td class="sidebar9">
									<table cellspacing=0 cellpadding=0 width="100%" align=center border=0 height="20" >
										<tr> <td  height="20"></td></tr>
										<tr class="sidebar10"> 
											<td align="center">
												<table width="50%" align=center cellpadding="0" cellspacing="1" border="0">
												    <tr> 
							                            <td  align="left" class="textColS" >&nbsp;</td>
							                            <td class="inputCol" colspan="2"></td>
							                          </tr>
							                          <TR>
							                            <TD align=right class="textColS" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "MAIN_CUSTOMER_TYPE") %><font color="#FF0000">*&nbsp;</FONT>:&nbsp;&nbsp;&nbsp;</TD>
							                            <TD class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(customerTypeVect,"","customerType",displayMode,"")%></TD>
							                          </TR>
							                           
							                          <TR> 
							                            <TD align=right class="textColS" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "LOAN_TYPE") %><font color="#FF0000">*&nbsp;</FONT>:&nbsp;&nbsp;&nbsp;</TD>
							                            <TD class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displaySelectTagScriptActionAndCode_ORIG(loanTypeVect,"" ,"loanType",displayMode," ") %></TD>
							                          </TR>
							                          <tr> <td colspan="3" height="15"></td>
							                          </tr>
							                          <tr> 
							                            <td class="inputCol" colspan="3" align="center"><input type="button" name="okBtn" value="<%=MessageResourceUtil.getTextDescription(request, "OK") %>" class="button_text" onClick="javascript:gotoAppForm()"></td>
							                          </tr>
						                        </table>
											</td>
										</tr>
								</table>
							</td>
						</tr>
					</table>
				</td></tr>
			</table>
		</td> 
	</tr>
</table>
<%}else{ %>
<TABLE border="0" width="100%" cellpadding="0" cellspacing="0"> 
    <TBODY>		
        <TR>
            <TD valign="top" align="left">
 		    &nbsp;&nbsp;<span class="TextWarningNormal">* unauthorized Interal Checker! </span>
		   </TD>
        </TR>
    </TBODY>
</TABLE> 				
<%} %>
<input type="hidden" name="searchType" value="<%=searchType%>">
<input type="hidden" name="appRecID" value="">

<script language="JavaScript" type="text/JavaScript">
function validate(){
	var customerType = appFormName.customerType.value;
	var loanType =  appFormName.loanType.value;
	if(customerType==""){
		alert("You Must Select Main Customer Type");
		return true;
	}
	if(loanType==""){
		alert("You Must Select Loan Type");
		return true;
	}
}
function gotoAppForm(){
	if(validate())
		return false;
	appFormName.formID.value="PROPOSAL_FORM";
	appFormName.currentTab.value="MAIN_TAB";
	appFormName.action.value="LoadProposal";
	appFormName.handleForm.value = "N";
	//appFormName.target = "_top";
	appFormName.submit();	
}
</script>	  		