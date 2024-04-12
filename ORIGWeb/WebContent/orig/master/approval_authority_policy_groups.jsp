<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.master.shared.model.ApprovAuthorM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.master.shared.model.OrigPolicyLevelGroupDataM" %>
<%@ page import="com.eaf.orig.master.shared.model.OrigPolicyLevelMapDataM"%>
<%@ page import="com.eaf.orig.master.shared.model.OrigPolicyVersionLevelDataM"%>
<%@ page import="com.eaf.orig.master.shared.model.OrigPolicyLevelGroupTotalDataM"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />
<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	Vector vPolicyLevels=(Vector)session.getAttribute("POLICY_VERSION_LEVEL");
   // HashMap  hPolicyMap=(HashMap) session.getAttribute("POLICY_GROUP_MAP");
	//if(vPolicyGroups==null){vPolicyGroups=new Vector();}
	String mode="edit";
	Vector vPolicyGroupTotal=(Vector)session.getAttribute("POLICY_VERSION_GROUPS_TOTAL");
	Vector vSelectGroupName=(Vector)session.getAttribute("SELECT_GROUP_NAME");
	String selectGroup=(String)session.getAttribute("SELECT_GROUP");
	String groupAction=(String)session.getAttribute("GROUP_ACTION");
    OrigPolicyLevelGroupTotalDataM selectOrigPolicyGroupTotalDataM=(OrigPolicyLevelGroupTotalDataM)session.getAttribute("SELECT_GROUP_TOTAL");
	if(vPolicyGroupTotal==null){vPolicyGroupTotal=new Vector();}
	if(selectGroup==null){selectGroup="";}
	if(vSelectGroupName==null){vSelectGroupName=new Vector();}
	if(groupAction==null){groupAction="";}
	if(selectOrigPolicyGroupTotalDataM==null){
	selectOrigPolicyGroupTotalDataM=new OrigPolicyLevelGroupTotalDataM();
	}
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
<head><title>Policy Group</title></head>
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail">Group</td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                            		<tr height="30"><td>
                                    	<%if("add".equals(groupAction)||"edit".equals(groupAction)){%><input type="button" name="Button4" value="Update"  class="button" onclick="saveGroup()"/><%}%>
									</td><td>
										<INPUT type="button" name="btnClose" value="Close" class="button" onClick="javascrip:window.close()" >			
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
					              	<TD class="textColS" colspan="2" >Group
									   <select name="selectGroup"  class="combobox" >
									   <%for(int i=0;i<vPolicyGroupTotal.size();i++ )
									   {
									   //OrigPolicyLevelGroupDataM selectOrigPolicyLevelGroupDataM=(OrigPolicyLevelGroupDataM)vGroupName.get(i);
									    OrigPolicyLevelGroupTotalDataM origPolicyGroupTotalDataM=(OrigPolicyLevelGroupTotalDataM)vPolicyGroupTotal.get(i);
									    String groupName=origPolicyGroupTotalDataM.getGroupName();
									    String selected="";
									    if(selectGroup.equals(groupName)){
									      selected="selected";
									    }
									   %>
									   <OPTION value="<%=groupName%>"  <%=selected%> ><%=groupName%></OPTION>
									   <% }%>
									   </select> 
									 </td>
									 <td><INPUT type="button" name="btnLoadPolicyGroup" value="Load Group" onClick="loadGroup()" class="button_text" style="width:100"> </td>
									 <td><INPUT type="button" name="btnDeleteGroup" value="Delete Group" onClick="deleteGroup()" class="button_text" style="width:100"></td>
									 <td>&nbsp;</td>     
								</tr>  	
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>     
									    <td><INPUT type="button" name="btnAddGroup" value="Add Group" onClick="addGroup()" class="button_text" style="width:100"> </td>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
										</TR>
							</table>
						</td></tr>
						<tr class="sidebar10"> <td align="center">
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								<%if("add".equals(groupAction)||"edit".equals(groupAction)){
								   String groupReadOnly="";
								  // String groupName="";
								   String txtStyle="textbox";
								   if("edit".equals(groupAction)){
								   groupReadOnly=" ReadOnly ";
								   txtStyle="textboxReadonly";
								   }
								  
								 %>
								  <tr>
								    <td class="textColS" width="7%">Group</td>
								    <td class="textColS" width="23%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(selectGroup,displayMode,"10","policyGroup",txtStyle," "+groupReadOnly) %></td>
								    <td width="12%">&nbsp;</td>
								    <td width="50%">&nbsp;</td>
								    <td width="8%">&nbsp;</td>
								  </tr>
								    <tr>
								    <td>&nbsp;</td>
								    <td class="textColS"><B>Level Name</B></td>
								    <td class="textColS"><B>Amount</B></td>
								    <td class="textColS"><B>Description</B></td>
								    <td></td>
								  </tr>
								  <%  
								   for(int i=0;i<vSelectGroupName.size();i++ ){
								    OrigPolicyLevelGroupDataM origPolicyLevelGroup=(OrigPolicyLevelGroupDataM)vSelectGroupName.get(i);       
								    String name=origPolicyLevelGroup.getLevelName();
								  %> 
								  <tr>
								    <td>&nbsp;</td>
								    <td class="textColS"><%=origPolicyLevelGroup.getLevelName()%></td>
								    <td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(origPolicyLevelGroup.getLevelAmount()),displayMode,"15",name+"_amt","textboxCurrency"," onKeyPress=\"keyPressInteger();\" ")%></td>
								    <td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(origPolicyLevelGroup.getDescription()),displayMode,"15",name+"_desc","textbox","")%></td>
								    <td>&nbsp;</td>
								  </tr>
								  <%}%>
								  <tr>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								    <td>&nbsp;</td>
								  </tr>
								    <tr>
								    <td>&nbsp;</td>
								    <td class="textColS">Total</td>
								    <td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(selectOrigPolicyGroupTotalDataM.getToalLevelAmount()),displayMode,"15","total_amt","textboxCurrency"," onKeyPress=\"keyPressInteger();\" ")%></td>
								    <td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(selectOrigPolicyGroupTotalDataM.getDescription()),displayMode,"15","total_desc","textbox","")%></td>
								    <td>&nbsp;</td>
								  </tr>
								  <%} %>       
								</table>  
						</td></tr>
					</table>
				</td></tr>
			</table>
		</td></tr>		
	</table>
	</TD></TR>
</TABLE>


<input type="hidden" name="groupAction" value=""><%//  'Add' or 'Edit'%>	

<script language="JavaScript">	
 

function loadGroup(){
        appFormName.action.value = "LoadApprovAuthorPolicyGroups";
		appFormName.groupAction.value = "edit";
		appFormName.handleForm.value = "N";
		appFormName.submit(); 
}
function saveGroup(){
     if(appFormName.policyGroup.value==''){
        alert("Group is required");
        return;
       }
       <%if(vSelectGroupName.size()==0){
         %>
         alert("Please add Policy Level");
         return;
        <%}%>                   
        appFormName.action.value = "UpdateApprovAuthorPolicyGroups";
		appFormName.groupAction.value = "save";
		appFormName.handleForm.value = "N";
		appFormName.submit(); 
}
function addGroup(){
        appFormName.action.value = "LoadApprovAuthorPolicyGroups";
		appFormName.groupAction.value = "add";
		appFormName.handleForm.value = "N";
		appFormName.submit()
}
function deleteGroup(){
      if(appFormName.selectGroup.value==''){
        alert("Please Select Group");
        return;
       }
        appFormName.action.value = "UpdateApprovAuthorPolicyGroups";
		appFormName.groupAction.value = "delete";
		appFormName.handleForm.value = "N";
		if(confirm("Delete group "+appFormName.selectGroup.value+" ?")){
		appFormName.submit(); 
		}
}
function clickPageList(atPage){
	var form=document.appFormName;
	form.atPage.value = atPage;
	form.action.value = "ValueListWebAction";
	form.handleForm.value = "N";
	form.submit();
}
function clickItemPerPageList(atItem){
	var form=document.appFormName;
	form.itemsPerPage.value = atItem;
	form.action.value = "ValueListWebAction";
	form.handleForm.value = "N";
	form.submit();  
}				
</script>	
<div>
