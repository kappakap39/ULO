<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.master.shared.model.GeneralParamM" %>

<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />

<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	boolean shw = false;
	String genParamCde="";
	String genParamval="";
	
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	ORIGUtility utility = new ORIGUtility();
	Vector busVect = utility.loadCacheByName("BusinessClassCacheDataM");//BusClassID
//	BusinessClassCacheDataM

	GeneralParamM genParamM =new GeneralParamM(); 
	
	//Clear ValueList 
	if("add".equals(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///genParam_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		genParamM = (GeneralParamM)request.getSession().getAttribute("ADD_GEN_PARAM_M");
		if(genParamM==null){
			genParamM =new GeneralParamM(); 
		}
		VALUE_LIST.setResult(null);
	}
	//Chk to show edit data
	if("edit".equals(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///genParam_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		genParamM = (GeneralParamM)request.getSession().getAttribute("EDIT_GEN_PARAM_M");
		if(genParamM==null){
			genParamM =new GeneralParamM(); 
		}
	}
	//Chk to show search field
	genParamCde = (String)request.getSession().getAttribute("FIRST_SEARCH_paramCode");
	genParamval = (String)request.getSession().getAttribute("FIRST_SEARCH_paramValue");
	if(genParamCde==null){
		genParamCde = "";
	}
	if(genParamval==null){
		genParamval = "";
	}
	
	System.out.println("check genParamM =" + genParamM );
	
	Vector ChkValueList = VALUE_LIST.getResult(); 
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

<input type="hidden" name="shwAddFrm" value=""><%//  'Add' or 'Edit'%>	
<input type="hidden" name="genParamCde" value="">
<input type="hidden" name="busID" value="">

<!--  For Search: Filter   -->
<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td class="sidebar8"><table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
		<tr class="sidebar10"> <td align="center">
		<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
			<tr>
				<TD class="textColS" width="13%">Parameter Code :</TD>
				<TD class="inputCol" width="15%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(genParamCde,displayMode,"20","paramCode","textbox","","30") %></TD>
				<TD class="textColS" width="13%">Parameter Value :</TD>
                <TD class="inputCol" width="20%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(genParamval,displayMode,"40","paramValue","textbox","","300") %></TD>
			    <TD class="inputCol" width="39%"><input type="button"  class ="button_text" name="Search" value="Search" onclick="searchApp()" ></TD>
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
					<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteGenParam()" >
					<%}else{ %>
					<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteGenParam()" disabled="disabled">
					<%} %>
				</TD></TR>
				<tr><td class="TableHeader">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="Bigtodotext3" width="3%"></TD>
							<TD class="Bigtodotext3" width="10%" align="left">Selected</TD>
							<TD class="Bigtodotext3" width="20%" align="left">Parameter Code</TD>
						    <TD class="Bigtodotext3" width="25%" align="left">Parameter Value</TD>
						    <TD class="Bigtodotext3" width="42%" align="left">Business Class Description</TD>
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
							<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","genParamChk",""+(String)elementList.elementAt(1)+","+(String)elementList.elementAt(4)+"","") %></td>
							<td class="jobopening2" width="20%"><a href="javascript:gotoEditForm('<%=elementList.elementAt(1) %>','<%=elementList.elementAt(4) %>')"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(1)) %></a></td>
							<td class="jobopening2" width="25%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(2)) %></td>
							<td class="jobopening2" width="42%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(3)) %></td>
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
	String label = "Add";
	String cancelBtn = "";
	if(ORIGMasterForm.getShwAddFrm().equals("add")){
		label = "Add";		
		strOnclick = "saveGenParam()";
		cancelBtn = "cancelAdd";
		shw = true;
	}else if(ORIGMasterForm.getShwAddFrm().equals("edit")){
		label = "Edit";
		strOnclick = "updateGenParam()";
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
					              <TD class="textColS" width="18%">Parameter Code<FONT color = red>*</FONT>: </TD>
					              <TD class="inputCol" width="29%">
					                <%if(label.equals("Add")){
					                %>
					                <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(genParamM.getParamCode(),displayMode,"20","Code","textbox","","30") %> 
					                <%}else{%>
					                <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(genParamM.getParamCode(),displayMode,"12","Code","textbox","readonly","30") %>	
					                <input type="hidden" name="paramCode" value="<%//=Param_Form.getGpM().getParamCode()%>">
					                <%}%>
					                </TD>
					              <TD class="textColS" width="15%">Business Class<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol" width="18%"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(busVect,genParamM.getBusClassID(),"busClass",displayMode,"") %></TD>
					              <TD></TD>
					            </TR>
					            <TR> 
					              <TD class="textColS">Parameter Value<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(genParamM.getParamValue(),displayMode,"40","Value","textbox","","300") %> </TD>
					              <TD class="textColS">Parameter Value 2 :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(genParamM.getParamValue2(),displayMode,"40","Value2","textbox","","100") %></TD>
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
	var paramCode 	= appFormName.paramCode.value;
	var paramValue = appFormName.paramValue.value;
	
	if( paramCode=="" && paramValue=="" ){
		alert("At Least One Field Must Be Filled");
		return false;
	}else{
		return true;
	}
}
function validateDelete(){
	var objSeq = appFormName.genParamChk;
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
		appFormName.action.value="SearchGenParam";
		appFormName.shwAddFrm.value = "search";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
	//}
}							
function submitShwAddForm() {
		appFormName.action.value = "ShowGenParam";
		appFormName.shwAddFrm.value = "add";
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}	
function cancelAddForm(cancelAddOrEdit){
		appFormName.action.value = "ShowGenParam";		
		appFormName.shwAddFrm.value = cancelAddOrEdit;	
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}			
function saveGenParam(){
		appFormName.action.value = "SaveGenParam";		
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function gotoEditForm(genParamCde,busID){
		appFormName.action.value="ShowGenParam";
		appFormName.shwAddFrm.value = "edit";
		appFormName.genParamCde.value = genParamCde;
		appFormName.busID.value = busID;
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function updateGenParam(){
		appFormName.action.value="UpdateGenParam";
		//appFormName.shwAddFrm.value = "update";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function deleteGenParam(){
	if(validateDelete()){
		appFormName.action.value="DeleteGenParam";
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

