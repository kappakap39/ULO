<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.master.shared.model.RunningParamM" %>

<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />


<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	boolean shw = false;
	
//	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	ORIGUtility utility = new ORIGUtility();

	RunningParamM runParamM =new RunningParamM(); 
	String paramID="";
	String paramDesc="";
	if("add".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		VALUE_LIST.setResult(null);
	}
	//Chk to show edit data
	if("edit".equals(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///runningParam_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		runParamM = (RunningParamM)request.getSession().getAttribute("EDIT_RUN_PARAM_DATAM");
		if(runParamM==null){
			runParamM = new RunningParamM();
		}
	}else if("add".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///listBox_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		runParamM = (RunningParamM)request.getSession().getAttribute("ADD_RUN_PARAM_DATAM");
		if(runParamM==null){
			runParamM = new RunningParamM();
		}
	}
	//Chk to show search field
	paramID = (String)request.getSession().getAttribute("FIRST_SEARCH_paramID");
	paramDesc = (String)request.getSession().getAttribute("FIRST_SEARCH_paramDesc");
	if(paramID==null){
		paramID = "";
	}
	if(paramDesc==null){
		paramDesc = "";
	}
	Vector ChkValueList = VALUE_LIST.getResult(); 
	System.out.println("check runParamM =" + runParamM );
%>

<input type="hidden" name="shwAddFrm" value=""><%//  'Add' or 'Edit'%>	
<input type="hidden" name="runParamIDEdit" value="">
<input type="hidden" name="runParamType" value="">

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
				<TD class="textColS" width="13%">Parameter Code :</TD>
				<TD class="inputCol" width="18%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(paramID,displayMode,"20","paramID","textbox","","50") %></TD>
				<TD class="textColS" width="17%">Parameter Description :</TD>
                <TD class="inputCol" width="18%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(paramDesc,displayMode,"30","paramDesc","textbox","","300") %></TD>
				<TD class="inputCol" width="34%"><input type="button"  class ="button_text" name="Search" value="Search" onclick="searchApp()" ></TD>
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
							<td class="Bigtodotext3" width="10%" align="left">Selected</TD>
				            <TD class="Bigtodotext3" width="20%">Parameter Code</TD>
				            <TD class="Bigtodotext3" width="20%">Parameter Type</TD>
				            <TD class="Bigtodotext3" width="60%">Parameter Description</TD>
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
							<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","runningParamChk",""+(String)elementList.elementAt(1)+","+(String)elementList.elementAt(2)+"","") %></td>
							<td class="jobopening2" width="20%"><a href="javascript:gotoEditForm('<%=elementList.elementAt(1) %>','<%=elementList.elementAt(2) %>')"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(1)) %></a></td>
							<td class="jobopening2" width="20%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(2)) %></td>
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
		strOnclick = "saveRunParam()";
		cancelBtn = "cancelAdd";
		shw = true;
	}else if(ORIGMasterForm.getShwAddFrm().equalsIgnoreCase("edit")){
		label = "Edit";
		strOnclick = "updateRunParam()";
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
					              <TD class="textColS" width="16%">Parameter Code<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol" width="15%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(runParamM.getParamID(),displayMode,"20","prmID","textbox"," "+readOnly,"30") %></TD>
					              <TD class="textColS" width="14%">Parameter Description  :</TD>
					              <TD class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(runParamM.getParamDesc(),displayMode,"50","prmdesc","textbox","","300") %></TD>
					            </TR>
					            <TR> 
					              <TD class="textColS">Parameter Type <FONT color = red>*</FONT>  :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(runParamM.getParamType(),displayMode,"20","paramType","textbox"," "+readOnly,"30") %></TD>
					              <TD class="inputCol" colspan="3"></TD>
					            </TR>
					            <TR> 
					              <TD class="textColS">Running From <FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction( ORIGDisplayFormatUtil.formatNumber("#,###",runParamM.getValueFrom()),displayMode,"20","runningFrom","textbox",
					              "onKeyPress=\"keyPressInteger();\" onblur=\"javascript:addComma(this);returnZero(this)\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"","30") %></TD>
					              <TD class="textColS">Running To <FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol" width="15%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction( ORIGDisplayFormatUtil.formatNumber("#,###",runParamM.getValueTo()),displayMode,"20","runningTo","textbox",
					              "onKeyPress=\"keyPressInteger();\" onblur=\"javascript:addComma(this);returnZero(this)\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"","30") %></TD>
					              <TD></TD>
					            </TR>
					            <TR> 
					              <TD class="textColS">Value1<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction( ORIGDisplayFormatUtil.formatNumber("#,###",runParamM.getValue1()),displayMode,"20","value1","textbox",
					              "onKeyPress=\"keyPressInteger();\" onblur=\"javascript:addComma(this);returnZero(this)\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"","30") %></TD>
					              <TD class="textColS">Value2<FONT color = red></FONT> :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayDoubleZeroToEmpty(ORIGDisplayFormatUtil.formatNumber("#,###",runParamM.getValue2())),displayMode,"20","value2","textbox",
					              "onKeyPress=\"keyPressInteger();\" onblur=\"javascript:addComma(this);returnZero(this)\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"","30") %></TD> 
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
	var paramID 	= appFormName.paramID.value;
	var paramDesc = appFormName.paramDesc.value;
	
	if( paramID=="" && paramDesc=="" ){
		alert("At Least One Field Must Be Filled");
		return false;
	}else{
		return true;
	}
}
function saveRunParam(){
		appFormName.action.value = "UpdateRunParam";
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function searchApp(){
	//if( validate() ){
		appFormName.action.value="SearchRunningParam";
		appFormName.shwAddFrm.value = "search";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
	//}
}							
function cancelAddForm(cancelAddOrEdit){
		appFormName.action.value = "ShowRunningParam";	
		appFormName.shwAddFrm.value = cancelAddOrEdit;	
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}	
function gotoEditForm(runParamIDEdit, runParamType){
		appFormName.action.value="ShowRunningParam";
		appFormName.shwAddFrm.value = "edit";
		appFormName.runParamIDEdit.value = runParamIDEdit;
		appFormName.runParamType.value = runParamType;
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function updateRunParam(){
		appFormName.action.value="UpdateRunParam";
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
		appFormName.action.value = "ShowRunningParam";
		appFormName.shwAddFrm.value = "add";
		appFormName.handleForm.value = "Y";	
		appFormName.submit();   
}
function deleteRunParam(){
	if(validateDelete()){
		appFormName.action.value="DeleteRunParam";
		appFormName.shwAddFrm.value = "delete";
		appFormName.handleForm.value = "Y";		 
		appFormName.submit();	
	}
}
function validateDelete(){
	var objSeq = appFormName.runningParamChk;
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
		
		            
		            
		            
		            
		            
		            


