
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.BusinessClassM" %>
<%@ page import="com.eaf.orig.master.shared.model.ListBoxMasterM " %>


<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />

<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	boolean shw = false;
	
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	ORIGUtility utility = new ORIGUtility();
	Vector fieldIDVect = utility.loadCacheByName("FieldID");
	
	ListBoxMasterM listBoxM = new ListBoxMasterM();
	ListBoxMasterM searchListBoxM = new ListBoxMasterM();
	
	//Show BusID
	String busID = (String)request.getSession().getAttribute("BUS_ID");
	
	System.out.println("Show Form >>> "+ORIGMasterForm.getShwAddFrm());
	
	//Clear ValueList 
	if("add".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		VALUE_LIST.setResult(null);
	}
	//Chk to show edit data && add data
	if("edit".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///listBox_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		listBoxM = (ListBoxMasterM)request.getSession().getAttribute("EDIT_LIST_BOX_DATAM");
	}else if("add".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///listBox_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		listBoxM = (ListBoxMasterM)request.getSession().getAttribute("ADD_LIST_BOX_DATAM");
		if(listBoxM==null){
			listBoxM = new ListBoxMasterM();
		}
	}
	//Chk to show search field
	searchListBoxM = (ListBoxMasterM)request.getSession().getAttribute("First_Search_ListBoxM");
	if(searchListBoxM==null){
		searchListBoxM = new ListBoxMasterM();
	}
	/*if("add".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm()) || ("delete".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm()) && VALUE_LIST.getResult()==null)){
		searchListBoxM = new ListBoxMasterM();
	}*/
	
	System.out.println("check listBoxM =" + listBoxM );
	
	Vector ChkValueList = VALUE_LIST.getResult(); 
%>

<input type="hidden" name="shwAddFrm" value=""><%//  'Add' or 'Edit'%>
<input type="hidden" name="addOrRemove" value="">
<input type="hidden" name="listID" value="">

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
				<TD class="textColS" width="15%">Listbox Master ID : </TD>
				<TD class="inputCol" width="19%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(searchListBoxM.getListBoxID(),displayMode,"22","listBoxIDSearch","textbox","","50") %></TD>
				<TD class="textColS" width="13%">Display Name :</TD>
		        <TD class="inputCol" width="20%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(searchListBoxM.getDisplayName(),displayMode,"40","displayName","textbox","","300") %></TD>
		        <TD width="36%"></TD>
			</TR>
			<TR>
				<TD class="textColS">Field ID : </TD>
				<TD class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(fieldIDVect,searchListBoxM.getFieldID(),"fieldID",displayMode,"") %></TD>
				<TD class="textColS"></TD>
		        <TD class="inputCol"><input type="button"  class ="button_text" name="Search" value="Search" onclick="javascript:searchApp()" ></TD>
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
				<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteListBox()" >
				<%}else{ %>
				<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteListBox()" disabled="disabled">
				<%} %>											
				
			</TD></TR>
			<tr><td class="TableHeader">
				<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
					<tr>
						<td class="Bigtodotext3" width="3%"></TD>
						<TD class="Bigtodotext3" width="10%" align="left">Selected</TD>
						<TD class="Bigtodotext3" width="15%" align="left">Listbox Master ID</TD>
					    <TD class="Bigtodotext3" width="35%" align="left">Display Name</TD>
					    <TD class="Bigtodotext3" width="15%" align="left">Choice Number</TD>
						<TD class="Bigtodotext3" width="22%" align="left">Field Description</TD>
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
							<td class="jobopening2" width="10%" align="left"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","listboxChk",(String)elementList.elementAt(1),"") %></td>
							<td class="jobopening2" width="15%" align="left"><a href="javascript:gotoEditForm('<%=elementList.elementAt(1) %>')"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(1)) %></a></td>
							<td class="jobopening2" width="35%" align="left"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(2)) %></td>
							<td class="jobopening2" width="15%" align="left"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(3)) %></td>
							<td class="jobopening2" width="22%" align="left"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(4)) %></td>
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

<!--Add-Edit Section-->
<%
	String strOnclick = "";
	String label = "Add";
	String cancelBtn = "";	
	if(ORIGMasterForm.getShwAddFrm().equalsIgnoreCase("add")){
		label = "Add";		
		strOnclick = "saveListBox()";
		cancelBtn = "cancelAdd";
		shw = true;
	}else if(ORIGMasterForm.getShwAddFrm().equalsIgnoreCase("edit")){
		label = "Edit";
		strOnclick = "updateListBox()";
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
					              <TD class="textColS" width="18%">Listbox Master ID<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol" width="20%"> 
					                <%if(label.equals("Add")){
									
									}else{
					                	System.out.println("label = "+label);
					                	System.out.println("listBoxID = "+listBoxM.getListBoxID());
					                %>
					                <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(listBoxM.getListBoxID(),displayMode,"12","lstID","textbox","readonly","50") %>
					                <%}%>
					               </TD>
					              <TD class="textColS" width="18%">Display Name<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol" width="44%" colspan="2"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(listBoxM.getDisplayName(),displayMode,"55","displayNameAdd","textbox","","300") %></TD>
					            </TR>
								
					          	<TR> 
					              <TD class="textColS">Choice Number<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(listBoxM.getChoiceNo(),displayMode,"12","choiceNo","textbox","","50") %></TD>
					              <TD class="textColS">Field<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol" width="24%"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(fieldIDVect,listBoxM.getFieldID(),"fieldIDAdd",displayMode,"") %></TD>
					              <TD width="20%"></TD>
					            </TR>			
					            <TR> 
					              <TD class="textColS">Business Class :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(busID,displayMode,"36","searchBusID","textbox","","300") %></TD>
					              <TD align="left"><input type="button"  class ="button_text" name="SearchBusClass" value="Search" onclick="searhBusByID('<%=label %>')" ></TD>
					              <TD></TD>
					              <TD></TD>
					            </TR>
					            <TR> 
					              <TD class="textColS"></TD>
					              <TD rowspan="5"> <SELECT NAME="businessClass" MULTIPLE SIZE="4" style="width:200" class="combobox">
					                  <%
					                  							Vector busSearchVect = (Vector)request.getSession().getAttribute("SEARCH_BUS");
					                  							System.out.println("busSearchVect = " + busSearchVect);
					                  							if(busSearchVect==null){
					                  								busSearchVect = new Vector();
					                  							}
					                  							BusinessClassM busM;
					                  							System.out.println("busSearchVect size = " + busSearchVect.size());
					                  							if(busSearchVect.size() > 0){ // if1
																	for(int i =0;i<busSearchVect.size();i++){ // for1
																		busM = (BusinessClassM)busSearchVect.get(i);
											%>
					                  <OPTION value = "<%=busM.getId()%>" class="textbox" ><%=busM.getId()%></OPTION>
					                  <%							 
																	} // end for1
																}// end if1
											%>
					                </SELECT> 
					              <TD class="textColS"></TD>
					              <TD rowspan="5"> <SELECT NAME="selectedBusinessClass" MULTIPLE SIZE="4" style="width:200" class="combobox">
					                  <%
	                  							Vector busSelVect = (Vector)request.getSession().getAttribute("SELECTED_BUS");
	                  							System.out.println("busSelVect = " + busSelVect);
	                  							if(busSelVect==null){
	                  								busSelVect = new Vector();
	                  							}
	                  							BusinessClassM selBusM;
	                  							System.out.println("busSelVect size = " + busSelVect.size());
	                  							if(busSelVect.size() > 0){ // if1
													for(int i =0;i<busSelVect.size();i++){ // for1
														selBusM= (BusinessClassM)busSelVect.get(i);
					
											%>
					                  <OPTION value = "<%=selBusM.getId()%>" class="textbox" ><%=selBusM.getId()%></OPTION>
					                  <%							 
																	} // end for1
																}// end if1
											%>
					                </SELECT> </TD>
					                <TD></TD>
					            </TR>
					            <TR> 
					              <TD class="textColS" rowspan="3" valign="top">Business Class :</TD>
					              <TD class="textColS" rowspan="3">Selected Business Class<FONT color = red>*</FONT> :
					              	<div align="center"><input type="button"  class ="button_text" name="go" value="&gt;&gt;" onclick= "addOrRemoveSelBus('add','<%=label %>')"></div><br>
					              	<div align="center"><input type="button"  class ="button_text" name="back" value="&lt;&lt;" onclick= "addOrRemoveSelBus('remove','<%=label %>')"></div>
					              </TD>
					              <TD rowspan="3"></TD>
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
function validateSearch(){
	var listBoxID 	= appFormName.listBoxIDSearch.value;
	var displayName = appFormName.displayName.value;
	var fieldID 	= appFormName.fieldID.value;
	if( listBoxID=="" && displayName=="" && fieldID==""){
		alert("At Least One Field Must be Filled.");
		return false;
	}else{
		return true;
	}
}
function validateDelete(){
	var objSeq = appFormName.listboxChk;
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
	if( validateSearch() ){
		appFormName.action.value="SearchListBox";
		appFormName.shwAddFrm.value = "search";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
	}
}
function submitShwAddForm() {
		appFormName.action.value = "ShowListBoxMaster";
		appFormName.shwAddFrm.value = "add";
		appFormName.handleForm.value = "Y";	
		appFormName.submit();   
}

function cancelAddForm(cancelAddOrEdit){
		appFormName.action.value = "ShowListBoxMaster";	
		appFormName.shwAddFrm.value = cancelAddOrEdit;		
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function searhBusByID(addOrEdit){
		appFormName.action.value = "SearchBusinessClass";	
	//	alert(addOrEdit);
		appFormName.shwAddFrm.value = addOrEdit;	
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function addOrRemoveSelBus(addOrRemove,addOrEditFrm){
//	if(addOrRemove=='add'){
		appFormName.action.value = "AddRemoveBus";	
		appFormName.shwAddFrm.value = addOrEditFrm;	
		appFormName.addOrRemove.value = addOrRemove;	
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
/*	}
	else if(addOrRemove=='remove'){
		appFormName.action.value = "AddRemoveBus";	
		appFormName.shwAddFrm.value = addOrEditFrm;	
		appFormName.addOrRemove.value = "remove";	
		appFormName.handleForm.value = "Y";
		appFormName.submit(); 
	}*/
}
function saveListBox(){
		appFormName.action.value = "SaveListBox";
		appFormName.handleForm.value = "N";
		appFormName.submit();   
}
function gotoEditForm(listID){
		appFormName.action.value="ShowListBoxMaster";
		appFormName.shwAddFrm.value = "edit";
		appFormName.listID.value = listID;
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function updateListBox(){
		appFormName.action.value="UpdateListBox";
		//appFormName.shwAddFrm.value = "update";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function deleteListBox(){
	if( validateDelete() ){
		appFormName.action.value="DeleteListBox";
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
					