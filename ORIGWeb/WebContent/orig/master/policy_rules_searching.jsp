<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.master.shared.model.PolicyRulesDataM" %>

<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />


<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	boolean shw = false;
	
	ORIGUtility utility = new ORIGUtility();

	PolicyRulesDataM policyRulesDataM =new PolicyRulesDataM(); 
	String policyCode="";
	//String policyType="";
	String policyDesc="";
	if("add".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		VALUE_LIST.setResult(null);
	}
	//Chk to show edit data
	if("edit".equals(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///runningParam_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		policyRulesDataM = (PolicyRulesDataM)request.getSession().getAttribute("EDIT_POLICY_RULES_DATAM");
		if(policyRulesDataM==null){
			policyRulesDataM = new PolicyRulesDataM();
		}
	}else if("add".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///listBox_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		policyRulesDataM = (PolicyRulesDataM)request.getSession().getAttribute("ADD_POLICY_RULES_DATAM");
		if(policyRulesDataM==null){
			policyRulesDataM = new PolicyRulesDataM();
		}
	}
	//Chk to show search field
	policyCode = (String)request.getSession().getAttribute("FIRST_SEARCH_policyCode");
	//policyType = (String)request.getSession().getAttribute("FIRST_SEARCH_policyType");
	policyDesc = (String)request.getSession().getAttribute("FIRST_SEARCH_policyDesc");
	if(policyCode==null){
		policyCode = "";
	}
	if(policyDesc==null){
		policyDesc = "";
	}
	Vector ChkValueList = VALUE_LIST.getResult(); 
	System.out.println("check polcyRulesDataM =" + policyRulesDataM );
%>

<input type="hidden" name="shwAddFrm" value=""><%//  'Add' or 'Edit'%>	
<input type="hidden" name="policyCodeEdit" value="">
<input type="hidden" name="policyDescEdit" value="">

<!--  For Search: Filter   -->
<%
Vector v = ORIGMasterForm.getFormErrors();
if(v!=null && v.size()>0) {
	for(int i=0; i<v.size();i++) {
		String errorMessage = (String)v.elementAt(i);
		out.println("<span class=\"TextWarningNormal\">*&nbsp;"+errorMessage+"</span><br>");
	}
}
ORIGMasterForm.setFormErrors(new Vector());
%>
<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td class="sidebar8"><table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
		<tr class="sidebar10"> <td align="center">
		<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
			<tr>
				<TD class="textColS" width="13%">Policy Code : </TD>
				<TD class="inputCol" width="15%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(policyCode,displayMode,"20","policyCode","textbox","","50") %></TD>
				<TD class="textColS" width="17%">Policy Description :</TD>
                <TD class="inputCol" width="15%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(policyDesc,displayMode,"30","policyDesc","textbox","","300") %></TD>
				<TD class="inputCol" width="6%"><input type="button"  class ="button_text" name="Search" value="Search" onclick="searchApp()" ></TD>
				<TD ></TD>
			</TR>
		</TABLE>
		</td></tr>
	</table></td></tr>
</table>
<br>
					
<!--  End Search: Filter   -->	

<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td>
		<div id="KLTable">
			<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
				<tr><td class="TableHeader">
					<input type="button"  class ="button_text" name="Add" value="Add" onclick="submitShwAddForm()" >
					<%if(ChkValueList != null && ChkValueList.size() > 1){
					 %>
					<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteRunParam()" >
					<%}else{ %>
					<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteRunParam()" disabled="disabled">
					<%} %>
				</TD></TR>
				<tr><td class="TableHeader">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="Bigtodotext3" width="10%">Selected</TD>
				            <TD class="Bigtodotext3" width="10%">Policy Code</TD>
				            <TD class="Bigtodotext3" width="10%">Policy Type</TD>
				            <TD class="Bigtodotext3" width="60%">Policy Description</TD>
				       	</tr>
					</table> 
				</td></tr>
		            		            
		            <%
						Vector valueList = VALUE_LIST.getResult(); 
						if(valueList != null && valueList.size() > 1){
							for(int i=1;i<valueList.size();i++){
								Vector elementList = (Vector)valueList.get(i);
					%>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","policyRulesChk",""+(String)elementList.elementAt(1)+","+(String)elementList.elementAt(2)+"","") %></td>
							<td class="jobopening2" width="10%"><a href="javascript:gotoEditForm('<%=elementList.elementAt(1) %>','<%=elementList.elementAt(2) %>')"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(1)) %></a></td>
							<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(2)) %></td>
							<td class="jobopening2" width="60%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(3)) %></td>
						</tr>
					</table> 
				</td></tr>
					<% 
							}
						}else{
					%>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
				  			<td class="jobopening2" align="center">Results Not Found.</td>
				  		</tr>
					</table> 
				</td></tr>
					<%
						}
					%>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td align="right" height="50">
								<div align="center"><span class="font2">
									<jsp:include page="../appform/valuelist.jsp" flush="true" />
								</span></div>
							</td>
						</tr>
					</table> 
				</td></tr>
			</TABLE>
		</div>
	</TD></TR>
</TABLE>

<% 		
	String strOnclick = "";
	String label = "Add";
	String cancelBtn = "";	
	String readOnly="";
	if(ORIGMasterForm.getShwAddFrm().equalsIgnoreCase("add")){
		label = "Add";		
		strOnclick = "savePolicyRules()";
		cancelBtn = "cancelAdd";
		shw = true;
	}else if(ORIGMasterForm.getShwAddFrm().equalsIgnoreCase("edit")){
		label = "Edit";
		strOnclick = "updatePolicyRules()";
		cancelBtn = "cancelEdit";
		readOnly="ReadOnly";
		shw = true;		
	}
	if(shw){  //===> if shw 	
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
                          	<tr><td class="text-header-detail">&nbsp;&nbsp;&nbsp;&nbsp; </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                            		<tr height="30"><td>
                                    	<input type="button"  class ="button" name="Save" value="Save" onclick="<%=strOnclick%>" >	
									</td><td>
										<input type="reset"  class ="button" name="Cancel" value="Cancel" onclick="cancelAddForm('<%=cancelBtn%>')">			
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
					              <TD class="textColS" width="16%">Policy Code<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol" width="15%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(policyRulesDataM.getPolicyCode(),displayMode,"20","policyRulesCode","textbox"," "+readOnly,"30") %></TD>
					              <TD class="textColS" width="14%">Policy Description  :</TD>
					              <TD class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(policyRulesDataM.getDesciption(),displayMode,"50","policyRulesDesc","textbox","","300") %></TD>
					            </TR>
					            <TR> 
					              <TD class="textColS">Policy Type <FONT color = red>*</FONT>  :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(policyRulesDataM.getPolicyType(),displayMode,"20","policyRulesType","textbox"," ","30") %></TD>
					              <TD class="inputCol" colspan="3"></TD>
					             </TR>
							</table>
						</td></tr>
					</table>
				</td></tr>
			</table>
		</td></tr>		
	</table>
	</TD></TR>
</TABLE>
<%} // end if shw%>	

<%
	//clear ORIGMasterFormHandler value
	//ORIGMasterForm.setShwAddFrm("");
 %> 

<script language="JavaScript">
function validate(){
	var paramID 	= appFormName.policy.value;
	var paramDesc = appFormName.paramDesc.value;
	
	if( paramID=="" && paramDesc=="" ){
		alert("At Least One Field Must Be Filled");
		return false;
	}else{
		return true;
	}
}
function savePolicyRules(){
		appFormName.action.value = "UpdatePolicyRules";
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function searchApp(){
	//if( validate() ){
		appFormName.action.value="SearchPolicyRules";
		appFormName.shwAddFrm.value = "search";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
	//}
}							
function cancelAddForm(cancelAddOrEdit){
		appFormName.action.value = "ShowPolicyRules";	
		appFormName.shwAddFrm.value = cancelAddOrEdit;	
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}	
function gotoEditForm(policyRulesCode, policyRulesDesc){
		appFormName.action.value="ShowPolicyRules";
		appFormName.shwAddFrm.value = "edit";		 
		appFormName.policyCodeEdit.value = policyRulesCode;
		appFormName.policyDescEdit.value = policyRulesDesc;
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function updatePolicyRules(){
		appFormName.action.value="UpdatePolicyRules";
		//appFormName.shwAddFrm.value = "update";
		appFormName.handleForm.value = "N";
		appFormName.submit();	
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
function submitShwAddForm() {
		appFormName.action.value = "ShowPolicyRules";
		appFormName.shwAddFrm.value = "add";
		appFormName.handleForm.value = "Y";	
		appFormName.submit();   
}
function deleteRunParam(){
	if(validateDelete()){
		appFormName.action.value="DeletePolicyRules";
		appFormName.shwAddFrm.value = "delete";
		appFormName.handleForm.value = "Y";		 
		appFormName.submit();	
	}
}
function validateDelete(){
	var objSeq = appFormName.policyRulesChk;
	if (isObject(objSeq) && !isUndefined(objSeq.length)) {
		if (objSeq.length > 0) {
			var isValid = false;
			for (var i = 0; i < objSeq.length ; i++) {
				if (objSeq[i].checked) {
					isValid = true;
				}
			}
			if (!isValid){
				alert("Please select at least one item.");
				return false;
			}
		}		
	} else {
		if (!objSeq.checked) {
			alert("Please select at least one item.");
			return false;
		}
	}
	
	return true;
}
</script>			
		
		            
		            
		            
		            
		            
		            


