<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.master.shared.model.FieldIdM" %>

<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />

<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	boolean shw = false;
	
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	FieldIdM fieldIdM =new FieldIdM(); 
	String fieldID="";
	String fieldDesc="";
	System.out.println("Show Form >>> "+ORIGMasterForm.getShwAddFrm());
	
	//Clear ValueList 
	if("add".equals(ORIGMasterForm.getShwAddFrm())){
		fieldIdM = (FieldIdM)request.getSession().getAttribute("ADD_FIELD_ID_DATAM");
		if(fieldIdM==null){
			fieldIdM = new FieldIdM();
		}
		VALUE_LIST.setResult(null);
	}
	//Chk to show edit data
	if("edit".equals(ORIGMasterForm.getShwAddFrm())){
		fieldIdM = (FieldIdM)request.getSession().getAttribute("EDIT_FIELD_ID_DATAM");
		if(fieldIdM == null){
			fieldIdM = new FieldIdM();
		}
	}
	//Chk to show search field
	fieldID = (String)request.getSession().getAttribute("FIRST_SEARCH_FIELD_ID");
	fieldDesc = (String)request.getSession().getAttribute("FIRST_SEARCH_FIELD_DESC");
	if(fieldID==null){
		fieldID = "";
	}
	if(fieldDesc==null){
		fieldDesc = "";
	}
	
	System.out.println("check fieldIdM =" + fieldIdM );
	
	Vector ChkValueList = VALUE_LIST.getResult(); 
%>
<input type="hidden" name="shwAddFrm" value=""><%//  'Add' or 'Edit'%>	
<input type="hidden" name="fieldIdEdit" value="">

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
				<TD class="textColS" width="7%">Field ID :</TD>
				<TD class="inputCol" width="15%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(fieldID,displayMode,"20","fieldID","textbox","onKeyPress=\"keyPressInteger();\"","30") %></TD>
				<TD class="textColS" width="7%">Field Description :</TD>
                <TD class="inputCol" width="15%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(fieldDesc,displayMode,"50","fieldDesc","textbox","","300") %></TD>
  				<TD class="inputCol" width="20%"><input type="button"  class ="button_text" name="Search" value="Search" onclick="searchApp()" ></TD>
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
					<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteFieldId()" >
					<%}else{ %>
					<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteFieldId()" disabled="disabled">
					<%} %>
				</TD></TR>
				<tr><td class="TableHeader">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="Bigtodotext3" width="3%"></TD>
							<TD class="Bigtodotext3" width="10%" align="left">Selected</TD>
							<TD class="Bigtodotext3" width="10%" align="left">Field ID</TD>
						    <TD class="Bigtodotext3" width="77%" align="left">Field Description</TD>
						</TR>
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
							<td class="jobopening2" width="3%"></td>
							<td class="jobopening2" width="10%" align="left"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","fieldIdChk",(String)elementList.elementAt(1),"") %></td>
							<td class="jobopening2" width="10%" align="left"><a href="javascript:gotoEditForm('<%=elementList.elementAt(1) %>')"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(1)) %></a></td>
							<td class="jobopening2" width="77%" align="left"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(2)) %></td>
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
	String label = "";
	String cancelBtn = "";	
	if(ORIGMasterForm.getShwAddFrm().equals("add")){
		label = "Add";		
		strOnclick = "saveFieldId()";
		cancelBtn = "cancelAdd";
		shw = true;
	}else if(ORIGMasterForm.getShwAddFrm().equals("edit")){
		label = "Edit";
		strOnclick = "updateFieldId()";
		cancelBtn = "cancelEdit";
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
									<TD class="textColS" width="10%">Field ID<FONT color = red>*</FONT> :</TD>
									<%if("add".equalsIgnoreCase(label)){ %>
									<TD class="inputCol" width="15%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayIntZeroToEmpty(fieldIdM.getFieldID()),displayMode,"20","id","textbox","onKeyPress=\"keyPressInteger();\"","30") %></TD>
									<%}else if("edit".equalsIgnoreCase(label)){ %>
									<TD class="inputCol" width="15%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayIntZeroToEmpty(fieldIdM.getFieldID()),displayMode,"20","id","textbox","readonly","30") %></TD>
									<%} %>
									<TD class="textColS" width="15%">Field Description<FONT color = red>*</FONT> :</TD>
									<TD class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(fieldIdM.getFieldDesc(),displayMode,"50","description","textbox","","300") %></TD>
									<TD ></TD>
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
	var fieldID 	= appFormName.fieldID.value;
	var fieldDesc = appFormName.fieldDesc.value;
	
	if( fieldID=="" && fieldDesc=="" ){
		alert("At Least One Field Must Be Filled");
		return false;
	}else{
		return true;
	}
}
function validateDelete(){
	var objSeq = appFormName.fieldIdChk;
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
function searchApp(){
	//if( validate() ){
		appFormName.action.value="SearchFieldId";
		appFormName.shwAddFrm.value = "search";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
	//}
}						
function submitShwAddForm() {
		appFormName.action.value = "ShowFieldID";
		appFormName.shwAddFrm.value = "add";
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}	
function cancelAddForm(cancelAddOrEdit){
		appFormName.action.value = "ShowFieldID";	
		appFormName.shwAddFrm.value = cancelAddOrEdit;	
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function saveFieldId(){
		appFormName.action.value = "SaveFieldID";
		//appFormName.shwAddFrm.value = "save";		
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function gotoEditForm(fieldIdEdit){
		appFormName.action.value="ShowFieldID";
		appFormName.shwAddFrm.value = "edit";
		appFormName.fieldIdEdit.value = fieldIdEdit;
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function updateFieldId(){
		appFormName.action.value="UpdateFieldId";
		//appFormName.shwAddFrm.value = "update";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function deleteFieldId(){
	if(validateDelete()){
		appFormName.action.value="DeleteFieldId";
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




