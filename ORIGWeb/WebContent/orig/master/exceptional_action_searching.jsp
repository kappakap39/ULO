<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.master.shared.model.ExceptActionM" %>

<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />

<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	boolean shw = false;
	
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	ExceptActionM exceptActM =new ExceptActionM(); 
	String exceptionName="";
	String exceptionDesc="";
	
	//Clear ValueList 
	if("add".equals(ORIGMasterForm.getShwAddFrm())){
		VALUE_LIST.setResult(null);
		exceptActM = (ExceptActionM)request.getSession().getAttribute("ADD_EXCEPT_ACT_DATAM");
		if(exceptActM==null){
			exceptActM = new ExceptActionM();
		}
	}
	//Chk to show edit data
	if("edit".equals(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///fieldId_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		exceptActM = (ExceptActionM)request.getSession().getAttribute("EDIT_EXCEPT_ACT_DATAM");
		if(exceptActM==null){
			exceptActM =new ExceptActionM(); 
		}
	}
	//Chk to show search field
	exceptionName = (String)request.getSession().getAttribute("FIRST_SEARCH_exceptionName");
	exceptionDesc = (String)request.getSession().getAttribute("FIRST_SEARCH_exceptionDesc");
	if(exceptionName==null){
		exceptionName = "";
	}
	if(exceptionDesc==null){
		exceptionDesc = "";
	}
	
	System.out.println("check exceptActM =" + exceptActM );
	
	Vector ChkValueList = VALUE_LIST.getResult(); 
%>

<input type="hidden" name="shwAddFrm" value=""><%//  'Add' or 'Edit'%>	
<input type="hidden" name="exceptIDEdit" value="">

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
				<TD class="textColS" width="18%">Exceptional Action Name :</TD>
				<TD class="inputCol" width="15%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(exceptionName,displayMode,"30","name","textbox","","30") %></TD>
				<TD class="textColS" width="20%">Exceptional Action Description :</TD>
	            <TD class="inputCol" width="17%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(exceptionDesc,displayMode,"30","desc","textbox","","100") %></TD>
				<TD class="inputCol" width="30%"><input type="button"  class ="button_text" name="Search" value="Search" onclick="searchApp()" ></TD>
				<TD></TD>
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
					<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteExceptAct()" >
					<%}else{ %>
					<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteExceptAct()" disabled="disabled">
					<%} %>				
				</TD></TR>
				<tr><td class="TableHeader">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="Bigtodotext3" width="3%"></TD>
							<td class="Bigtodotext3" width="10%">Selected</TD>
							<td class="Bigtodotext3" width="25%">Exceptional Action Name</TD> 													
							<td class="Bigtodotext3" width="62%">Exceptional Action Description</TD>					    
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
							<td class="jobopening2" width="3%"></td>
							<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","exceptActionChk",(String)elementList.elementAt(3),"") %></td>
							<td class="jobopening2" width="25%"><a href="javascript:gotoEditForm('<%=elementList.elementAt(3) %>')">
								<%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(1)) %></a></td>
							<td class="jobopening2" width="62%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(2)) %></td>
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
<!-- End Show All -->	

<%
	String strOnclick = "";
	String label = "";
	String cancelBtn = "";	
	if(ORIGMasterForm.getShwAddFrm().equals("add")){
		label = "Add";		
		strOnclick = "saveExceptAction()";
		cancelBtn = "cancelAdd";
		shw = true;
	}else if(ORIGMasterForm.getShwAddFrm().equals("edit")){
		label = "Edit";
		strOnclick = "updateExceptAct()";
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
					              <TD class="textColS" width="25%">Exceptional Action Name<FONT color = red>*</FONT>  :</TD>
					              <TD class="inputCol" width="20%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(exceptActM.getExceptionName(),displayMode,"40","exceptionName","textbox","","30") %> </TD>
					              <TD class="textColS" width="25%">Exceptional Action Description :</TD>
					              <TD class="inputCol" width="20%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(exceptActM.getExceptionDesc(),displayMode,"40","exceptionDesc","textbox","","100") %></TD>
					              <TD></TD>
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
	var exceptionName 	= appFormName.name.value;
	var exceptionDesc = appFormName.desc.value;
	
	if( exceptionName=="" && exceptionDesc=="" ){
		alert("At Least One Field Must Be Filled");
		return false;
	}else{
		return true;
	}
}
function validateDelete(){
	var objSeq = appFormName.exceptActionChk;
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
		appFormName.action.value="SearchExceptAction";
		appFormName.shwAddFrm.value = "search";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
	//}
}								
function submitShwAddForm() {
		appFormName.action.value = "ShowExceptAction";
		appFormName.shwAddFrm.value = "add";
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}	
function cancelAddForm(cancelAddOrEdit){
		appFormName.action.value = "ShowExceptAction";	
		appFormName.shwAddFrm.value = cancelAddOrEdit;		
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function saveExceptAction(){
		appFormName.action.value = "SaveExceptAction";
		//appFormName.shwAddFrm.value = "save";		
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function gotoEditForm(exceptIDEdit){
		appFormName.action.value="ShowExceptAction";
		appFormName.shwAddFrm.value = "edit";
		appFormName.exceptIDEdit.value = exceptIDEdit;
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function updateExceptAct(){
		appFormName.action.value="UpdateExceptAction";
		//appFormName.shwAddFrm.value = "update";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function deleteExceptAct(){
	if(validateDelete()){
		appFormName.action.value="DeleteExceptAction";
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