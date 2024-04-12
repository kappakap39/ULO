<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.master.shared.model.ApprovAuthorM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.master.shared.model.OrigPolicyVersionDataM" %>

<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />
<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	OrigPolicyVersionDataM origPolicyVersion=(OrigPolicyVersionDataM)session.getAttribute("POLICY_VERSION");
	if(origPolicyVersion==null){ origPolicyVersion=new OrigPolicyVersionDataM();}
	String effectiveDate=ORIGUtility.displayEngToThaiDate( origPolicyVersion.getEffectiveDate());
    String expireDate=ORIGUtility.displayEngToThaiDate( origPolicyVersion.getExpireDate());
    String displayType=(String)session.getAttribute("DISPLAY_TYPE");
   // session.removeAttribute("DISPLAY_TYPE");    
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
                          	<tr><td class="text-header-detail">Policy </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                                    	<input type="button" name="bntSave" value="Save"  class="button" onclick="updateApprovAuthor()">
                                    </td><td>
                                    	<input type="button" name="btnCacel" value="Cancel" class="button" onClick="cancelSetup()"/>
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
			 						<td class="textColS" width="141">Version <FONT color="red">*</FONT>:</td>
								    <td class="inputCol" width="141">
									    <%if("new".equals(displayType)){ %>
									    <%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"30","policyVersion","textbox","","20") %>
									    <%}else{ %>
									    <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(origPolicyVersion.getPolicyVersion(),displayMode,"30","policyVersion","textboxReadOnly"," Readonly","20") %>
									    <%} %>
								    </td>
								    <td class="textColS" width="141">Description:</td>
								    <td class="inputCol" width="378"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(origPolicyVersion.getDescription(),displayMode,"30","description","textbox"," ","20") %></td>
								  </tr>
								  <tr>
								    <td class="textColS">Effective Date  <FONT color="red">*</FONT>:</td>
								    <td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName", effectiveDate,displayMode,"","effectiveDate","textbox","right"," onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %>    
								    </td>
								    <td class="textColS">ExpireDate <FONT color="red">*</FONT>:</td>
								    <td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",expireDate,displayMode,"","expireDate","textbox","right"," onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
								  </tr>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								  <tr>
								    <td><input type="button" name="btnPolicyLevel" value="Policy Level" class="button_text" onClick="setupPolicyLevel()" style="width:140" /></td>
								    <td><input type="button" name="bntMapPolicyLevelRule" value="Map Level with rules"class="button_text" onClick="mapLevelandPolicyRule()" style="width:140" /></td>
								    <td>&nbsp;</td>
								    <td><input type="button" name="btnPolicyRulesGroup" value="Policy Rules Exception" class="button_text" onClick="setupPolicyReulseException()" style="width:140"/></td>
								  </tr>
								  <tr>
								    <td><input type="button" name="btnPolicyRulesGroup" value="Policy Rules Group" class="button_text" onClick="setupPolicyGroup()" style="width:140" /></td>
								    <td><input type="button" name="btnApproval" value="Approval Authority" class="button_text" onclick="setupApproval()" style="width:140" /></td>
								    <td>&nbsp;</td>
								    <td><input type="button" name="btnCreateNewCopy" value="Create New Copy" class="button_text" onclick="createNewCopy()"  style="width:140" /></td>
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
<input type="hidden" name="displayType" value="<%=displayType%>"><%//  'Add' or 'Edit'%>	
<script language="JavaScript">	
 
function updateApprovAuthor(){
        //alert('updateApprovAuthor()'); 
        if(appFormName.policyVersion.value==''){
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
		appFormName.action.value="SaveUpdateApprovAuthor";
		//appFormName.shwAddFrm.value = "update";
		appFormName.handleForm.value = "N";
		appFormName.submit();	
}
function setupPolicyLevel(){
//alert('setupPolicyLevel');
 //window.open('');
  var popupWebAction='LoadApprovAuthorPolicyLevels';   
  var popupWidth='640';
  var popupHeight='300';
 loadPopup('policyPopup',popupWebAction,popupWidth,popupHeight+",status=0",'100',getPostionPopupX(popupWidth),'','','');
} 	
function mapLevelandPolicyRule(){
//alert('mapLevelandPolicyRule()');
  var popupWebAction='LoadApprovAuthorPolicyMapRules';  
  popupWebAction+='&loadPolicy=loadNew';
  var popupWidth='900';
  var popupHeight='430';
 loadPopup('policyPopup',popupWebAction,popupWidth,popupHeight+",status=0",'100',getPostionPopupX(popupWidth),'','','');
} 
function setupPolicyGroup(){
//alert('setupPolicyGroup()');
  var popupWebAction='LoadApprovAuthorPolicyGroups';  
  var popupWidth='640';
  var popupHeight='300';
 loadPopup('policyPopup',popupWebAction,popupWidth,popupHeight+",status=0",'100',getPostionPopupX(popupWidth),'','','');
} 
function setupPolicyReulseException(){
//alert('setupPolicyGroup()');
  var popupWebAction='LoadApprovAuthorPolicyRulesException';  
  var popupWidth='800';
  var popupHeight='400';
 loadPopup('policyPopup',popupWebAction,popupWidth,popupHeight+",status=0",'100',getPostionPopupX(popupWidth),'','','');
} 
function setupApproval(){
//alert('setupApproval()');
  var popupWebAction='LoadApprovAuthorPolicyDetail';  
  var popupWidth='950';
  var popupHeight='600';
 loadPopup('policyPopup',popupWebAction,popupWidth,popupHeight+",status=0",'100',getPostionPopupX(popupWidth),'','','');
} 
function createNewCopy(){
  var popupWebAction='LoadApprovAuthorCreateCopy';  
  var popupWidth='640';
  var popupHeight='300';
  popupWebAction=popupWebAction+"&createType=copy";
  loadPopup('policyPopup',popupWebAction,popupWidth,popupHeight+",status=0",'100',getPostionPopupX(popupWidth),'','',''); 
}
function cancelSetup(){
  //alert('cancel');
   //window.close():
  // back to Mail;
  	var form = document.appFormName;
	form.action.value = "SearchApprovAuthor"; 
	form.handleForm.value = "N";
	form.submit(); 
}
function getPostionPopupX(popupWidth){   	 
    var frameWidth =  screen.width;
    var frameHeight =  screen.height;	 
	var result=(frameWidth/2)-(popupWidth/2);
	return result;	
 }
</script>	
<div>
