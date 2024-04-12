<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.master.shared.model.ApprovAuthorM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.master.shared.model.OrigPolicyVersionLevelDataM" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />
<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	Vector vPolicyLevels=(Vector)session.getAttribute("POLICY_VERSION_LEVEL");
	if(vPolicyLevels==null){vPolicyLevels=new Vector();}
	//String mode="edit";
	  String mode=(String)session.getAttribute("FORM_MODE");
  	  session.removeAttribute("FORM_MODE");
  	  OrigPolicyVersionLevelDataM policyVersionLevelDataM=(OrigPolicyVersionLevelDataM)session.getAttribute("POLICY_LEVEL_EDIT");
  	  if(policyVersionLevelDataM==null){
  	  policyVersionLevelDataM=new OrigPolicyVersionLevelDataM();
  	  }
	if(mode==null){mode="";}
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
<head><title>Policy Levels</title></head>
<body>
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "LEVEL") %> </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                                    	<%if("update".equals(mode)||"add".equals(mode)) {%><input type="button" name="btnSave" value="Update"  class="button" onclick="savePolicyLevel()"/><%} %>
                                    </td><td>
                                    	<input type="button" name="btnClose" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>"  class="button" onClick="javascrip:window.close()"/>
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
							<tr><td>
								<div id="KLTable">
									<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr><td class="TableHeader">
											<INPUT type="button" name="btnAdd" value="<%=MessageResourceUtil.getTextDescription(request, "ADD") %>" class="button_text"  onClick="addPolicyLevel()">&nbsp;
											<INPUT type="button" name="btnDelete" value="<%=MessageResourceUtil.getTextDescription(request, "DELETE") %>" class="button_text" onClick="deletePolicyLevel()">
										</TD></TR>
										<tr><td class="TableHeader">
											<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
												<tr>
													<td class="Bigtodotext3" width="12%"><%=MessageResourceUtil.getTextDescription(request, "SELECT") %></td>
												    <td class="Bigtodotext3" width="30%"><%=MessageResourceUtil.getTextDescription(request, "LEVEL") %> </td>
												    <td class="Bigtodotext3" width="58%"><%=MessageResourceUtil.getTextDescription(request, "DESCRIPTION") %></td>
												</tr>
											</table> 
										</td></tr>
									  <%for( int i=0;i <vPolicyLevels.size();i++ ){
									     OrigPolicyVersionLevelDataM   prmOrigPolicyVersionLevelDataM=(OrigPolicyVersionLevelDataM)vPolicyLevels.get(i);
									  
									  %>
									  <tr><td align="center" class="gumaygrey2">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr>
												<td class="jobopening2" width="12%" ><%=ORIGDisplayFormatUtil.displayCheckBoxTag(prmOrigPolicyVersionLevelDataM.getLevelName(),"",displayMode,"chkBox_levels_"+i,"","") %></td>
											    <td class="jobopening2" width="30%"><a href="#" onclick="updatePolicyLevel('<%=prmOrigPolicyVersionLevelDataM.getLevelName()%>')"><%=ORIGDisplayFormatUtil.displayHTML(prmOrigPolicyVersionLevelDataM.getLevelName()) %></a></td>
											    <td class="jobopening2" width="58%"><%=ORIGDisplayFormatUtil.displayHTML(prmOrigPolicyVersionLevelDataM.getDescription()) %></td>
											  </tr>
										</table> 
										</td></tr>
									  <% }%>  
									</table>
								</div>
							</td></tr>
						</table>
						</td></tr>
						<tr><td class="sidebar10">
							<%if("update".equals(mode)||"add".equals(mode)) {%>
							<fieldset><legend><%=mode%></legend>
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
							  <tr>     
							    <td class="textColS" width="22%"><%=MessageResourceUtil.getTextDescription(request, "LEVEL") %> </td>
							    <td class="textColS" width="31%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(policyVersionLevelDataM.getLevelName()),displayMode,"10","policyLevel","textbox","") %></td>
							    <td class="textColS" width="15%"><%=MessageResourceUtil.getTextDescription(request, "DESCRIPTION") %></td>
							    <td class="textColS" width="32%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(policyVersionLevelDataM.getDescription()),displayMode,"30","policyDescription","textbox","") %></td>
							  </tr>
						  	</table>
						  	</fieldset> 
						  	<%} %>
						</td></tr>
					</table>
				</td></tr>
			</table>
		</td></tr>		
	</table>
	</TD></TR>
</TABLE>

 </body>
<input type="hidden" name="frmMode" value="<%=mode%>"><%//  'Add' or 'Edit'%>	
<input type="hidden" name="selectLevel" value="<%=mode%>">
<script language="JavaScript">	
 
function addPolicyLevel(){
  
  var appForm=document.appFormName;
  appForm.action.value = "UpdateApprovAuthorPolicyLevels";
  appForm.frmMode.value='add';
  appForm.handleForm.value = "N";
  appFormName.submit(); 
}
function deletePolicyLevel(){      
  var appForm=document.appFormName;   
  var selected=false;
  var deleteLevel="";
  for(var index=0;index < <%=vPolicyLevels.size()%>; index++){
      if(eval("appForm.chkBox_levels_"+index+".checked")){
        //alert(index);
         selected=true;
        // alert(eval("appForm.chkBox_levels_"+index+".value"));
         deleteLevel=deleteLevel+"  "+eval("appForm.chkBox_levels_"+index+".value");   
      }
  
  }  
  if(!selected){
    alert("Please Select Item");
    return
  }
  appForm.action.value = "UpdateApprovAuthorPolicyLevels";
  appForm.frmMode.value='delete';
  appForm.handleForm.value = "N";
  if(confirm("Delete Level "+deleteLevel+" ?")){
   appFormName.submit();
  }
}
function updatePolicyLevel(selectLevel){
  var appForm=document.appFormName;
  appForm.action.value = "UpdateApprovAuthorPolicyLevels";   
  appForm.frmMode.value='update';  
  //alert('update '+selectLevel);
  appForm.selectLevel.value=selectLevel;
  appForm.handleForm.value = "N";
  appFormName.submit(); 

}
function savePolicyLevel(){
  var appForm=document.appFormName;
  if(appForm.policyLevel.value==''){
   alert("Please input Level Name");
   return;
  }
  appForm.action.value = "UpdateApprovAuthorPolicyLevels";
  // var appForm=document.appFormName;
  appForm.frmMode.value='save';
  appForm.handleForm.value = "N";
  appFormName.submit(); 

}

/* function gotoEditForm(grpNm, lnTyp, cusTyp){
		appFormName.action.value="ShowApprovAuthor";
		appFormName.shwAddFrm.value = "edit";
		appFormName.grpNm.value = grpNm;
		appFormName.lnTyp.value = lnTyp;
		appFormName.cusTyp.value = cusTyp;
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}*/
/* function updateApprovAuthor(){
		appFormName.action.value="UpdateApprovAuthor";
		//appFormName.shwAddFrm.value = "update";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}*/

function deleteApprovAuthor(){
	if(validateDelete()){
		appFormName.action.value="DeleteAppAuthor";
		appFormName.shwAddFrm.value = "delete";
		appFormName.handleForm.value = "Y";
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
