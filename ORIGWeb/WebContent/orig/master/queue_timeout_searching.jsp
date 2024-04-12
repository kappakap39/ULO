<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.master.shared.model.QueueTimeOutM" %>
<%@ page import="com.eaf.orig.master.shared.model.QTimeOutLoanTypeM" %>
<%@ page import="com.eaf.orig.cache.CacheDataInf" %>

<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />


<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	boolean shw = false;
	
	QueueTimeOutM qTimeOutM =new QueueTimeOutM(); 
	QTimeOutLoanTypeM qTimeLoanTypeM = new QTimeOutLoanTypeM();
	String loanTypeDesc = "";
	
	// **** Get Loan Type
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	CacheDataInf obj = null;
	String value = null;
	String name = null;
	
	Vector stateVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",14);
	
	// **** End
	
	String qTimeOutID="";
	String qTimeOutDesc="";
	
	//Clear ValueList 
	if("add".equals(ORIGMasterForm.getShwAddFrm())){
		VALUE_LIST.setResult(null);
	}
	//Chk to show add or edit data
	if("edit".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///queueTime_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		qTimeOutM = (QueueTimeOutM)request.getSession().getAttribute("EDIT_Q_TIME_M");
	}else{
		System.out.println("///queueTime_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		qTimeOutM = (QueueTimeOutM)request.getSession().getAttribute("ADD_Q_TIME_M");
	}
	
	if(qTimeOutM == null){
		qTimeOutM = new QueueTimeOutM();
	}
	qTimeLoanTypeM = (QTimeOutLoanTypeM)request.getSession().getAttribute("QTimeOutLoanTypeM_SESSION");
	if(qTimeLoanTypeM == null){
		qTimeLoanTypeM = new QTimeOutLoanTypeM();
	}
	loanTypeDesc = (String)request.getSession().getAttribute("DB_CLICK_LOAN_TYPE");

	//Chk to show search field
	qTimeOutID = (String)request.getSession().getAttribute("FIRST_SEARCH_qTimeOutID");
	qTimeOutDesc = (String)request.getSession().getAttribute("FIRST_SEARCH_qTimeOutDesc");
	if(qTimeOutID==null){
		qTimeOutID = "";
	}
	if(qTimeOutDesc==null){
		qTimeOutDesc = "";
	}
	
	System.out.println("check qTimeOutM =" + qTimeOutM );
	
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
<input type="hidden" name="firstAdd" value="">
<input type="hidden" name="disableInputField" value="">
<input type="hidden" name="qTimeOutIdEdit" value="">

<!--  For Search: Filter   -->
<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td class="sidebar8"><table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
		<tr class="sidebar10"> <td align="center">
		<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
			<tr>
				<TD class="textColS" width="15%">Queue Timeout ID. :</TD>
				<TD class="inputCol" width="18%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(qTimeOutID,displayMode,"30","qTimeOutID","textbox","","30") %></TD>
				<TD class="textColS" width="15%">Description :</TD>
                <TD class="inputCol" width="18%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(qTimeOutDesc,displayMode,"50","qTimeOutDesc","textbox","","100") %></TD>
			    <TD class="textColS" width="34%"><input type="button"  class ="button_text" name="Search" value="Search" onclick="searchApp()" ></TD>
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
					<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteQTime()" >
					<%}else{ %>
					<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteQTime()" disabled="disabled">
					<%} %>
				</TD></TR>
				<tr><td class="TableHeader">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="Bigtodotext3" width="3%"></TD>
							<TD class="Bigtodotext3" width="10%">Selected</TD>
							<TD class="Bigtodotext3" width="20%">Queue Timeout ID</TD> 													
							<TD class="Bigtodotext3" width="67%">Description</TD>					    
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
							<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","qTimeoutChk",(String)elementList.elementAt(1),"") %></td>
							<td class="jobopening2" width="20%"><a href="javascript:gotoEditForm('<%=elementList.elementAt(1) %>')">
								<%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(1)) %></a></td>
							<td class="jobopening2" width="67%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(2)) %></td>
						</TR>
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
	if(ORIGMasterForm.getShwAddFrm().equalsIgnoreCase("add")){
		label = "Add";		
		strOnclick = "saveQTime()";
		cancelBtn = "cancelAdd";
		shw = true;
	}else if(ORIGMasterForm.getShwAddFrm().equalsIgnoreCase("edit")){
		label = "Edit";
		strOnclick = "updateQTime()";
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
                                    	<input type="button"  class ="button" name="SaveAll" value="Save" onclick="<%=strOnclick%>" >
									</td><td>
										<input type="reset"  class ="button" name="CancelAll" value="Cancel" onclick="cancelAddForm('<%=cancelBtn%>')">		
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
					              <TD class="textColS" width="17%">Queue Timeout ID.<FONT color = red>*</FONT> :</TD>
								  <TD class="inputCol" width="21%">
									<%if(label.equals("Add")){
									%>						
										<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(qTimeOutM.getQTimeOutID(),displayMode,"36","qTimeID","textbox","","30") %>
									<%}else{%>
										<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(qTimeOutM.getQTimeOutID(),displayMode,"36","qTimeID","textbox","readonly","30") %>
									<%}%>																			
									</TD>
									<TD class="textColS" width="10%">Description<FONT color = red>*</FONT> :</TD>
									<TD class="inputCol" width="33%">
										<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(qTimeOutM.getQTimeOutDesc(),displayMode,"50","qDesc","textbox","","100") %>
									</TD>
									<TD></TD>
								</TR>
								<TR>
									<TD class="textColS">Working Time<FONT color = red>*</FONT> :</TD>
									<TD class="inputCol" colspan="2">
										<table cellSpacing="1" width="100%" border="0">
										<tr><TD class="textColS">
											 <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(qTimeOutM.getHour(),displayMode,"2","hour","textbox","onblur=\"chkHour();\" onKeyPress=\"keyPressInteger();\"","2") %>hr.
										</TD><TD class="textColS">
											 <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(qTimeOutM.getMinute(),displayMode,"2","minute","textbox","onblur=\"chkMinute();\" onKeyPress=\"keyPressInteger();\"","2") %>min.
										</TD><TD class="textColS">
											 <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(qTimeOutM.getSecond(),displayMode,"2","second","textbox","onblur=\"chkSecond();\" onKeyPress=\"keyPressInteger();\"","2") %>sec.
										</TD></tr>
										</table>
									 </TD>
									<TD></TD>
								</TR>
								<TR>
									<TD class="textColS">Queue Timeout State :</TD>
									<TD class="inputCol"> 
						<%if(label.equals("Add")){
									%>
									<SELECT NAME="loanType" MULTIPLE SIZE=4 style="width:200" class=combobox ondblclick="enableInputField(this.value,'<%=label %>')" >
									<%if (stateVect != null) {
											for (int i = 0; i < stateVect.size(); i++) {
												obj = (CacheDataInf) stateVect.get(i);
												value = obj.getCode();
												name = obj.getThDesc();
									%>
									<OPTION value ="<%=value %>,<%=name %>" class=textbox ><%= name%></OPTION>	
									<%		}
									} %>
									</SELECT>
						<%}else{%>	
									<SELECT NAME="loanType" MULTIPLE SIZE=4 style="width:200" class=combobox ondblclick="enableInputField(this.value,'<%=label %>')" >
									<%if (stateVect != null) {
											for (int i = 0; i < stateVect.size(); i++) {
												obj = (CacheDataInf) stateVect.get(i);
												value = obj.getCode();
												name = obj.getThDesc();
									%>
									<OPTION value ="<%=value %>,<%=name %>" class=textbox ><%= name%></OPTION>	
									<%		}
									} %>
									</SELECT>
						<%}%>


									</TD>
									
									<TD  colspan="3"></TD>
								</TR>
								<TR height="15">
									<TD colspan="5"></TD>
								</TR>
								
								<TR>
									<TD class="textColS" colspan="5" align =center><FONT color = red>
										** </FONT>Please double click at Queue Timeout State field to input the details.
									</TD>
								</TR>
								
					<%if(loanTypeDesc != null ){
					%>										
								<TR>
									<TD class="textColS" colspan="5"><b><%=loanTypeDesc%></b></TD>
								</TR>
					<%}%>										
								<tr>
									<td colspan="5">
										<table cellSpacing="1" width="100%" border="0">
										<tr>
											<TD class="textColS" width="31%">Queue Time (pending)<FONT color = red>*</FONT> :</TD>
											<TD class="inputCol" width="15%">	
											<%if(ORIGMasterForm.getDisable() != null && "false".equalsIgnoreCase(ORIGMasterForm.getDisable())){
											%>																							
												<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(String.valueOf(ORIGDisplayFormatUtil.displayIntZeroToEmpty(qTimeLoanTypeM.getExpiry())),displayMode,"12","expiry","textbox","onKeyPress=\"keyPressInteger();\"","12") %>																					
											<%}else{%>												
												<INPUT class=textbox value="<%=ORIGDisplayFormatUtil.displayIntZeroToEmpty(qTimeLoanTypeM.getExpiry()) %>" name="expiry" size="12" readonly="readonly">			
											<%}%>											
											&nbsp;day(s)</TD>
											<TD class="textColS" width="11%">Reason Code<FONT color = red>*</FONT> :</TD>
											<TD class="inputCol" width="21%">
											<%if(ORIGMasterForm.getDisable() != null && "false".equalsIgnoreCase(ORIGMasterForm.getDisable())){
											%>											
												<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(qTimeLoanTypeM.getReasonCode(),displayMode,"30","reasonCode","textbox","","30") %>																					
											<%}else{%>		
												<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(qTimeLoanTypeM.getReasonCode(),displayMode,"30","reasonCode","textbox","readonly","30") %>																															
											<%}%>																							
											</td>
											<TD></TD>		
										</tr>		
										<TR>
											<TD></TD>
											<TD></TD>
											<TD></TD>
											<TD align="right">												
											<%if(ORIGMasterForm.getDisable() != null && "false".equalsIgnoreCase(ORIGMasterForm.getDisable())){
											%>			
													<input type="button" class=button_text value="Save" name="Save" onclick= "saveQTimeLoanType('<%=label %>')" >
													<input type="button"  class ="button_text" name="Clear" value="Clear" onclick="javascript:clearAll();" >																
											<%}else{%>												
													<input type="button" class=button_text value="Save" name="Save" onclick= "" disabled>
													<input type="reset"  class ="button_text" name="Clear" value="Clear" disabled>
											<%}%>
											</TD>
											<TD></TD>
										</TR>									
										</table>
									</td>										
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
	var qTimeOutID 	= appFormName.qTimeOutID.value;
	var qTimeOutDesc = appFormName.qTimeOutDesc.value;
	
	if( qTimeOutID=="" && qTimeOutDesc=="" ){
		alert("At Least One Field Must Be Filled");
		return false;
	}else{
		return true;
	}
}
function validateDelete(){
	var objSeq = appFormName.qTimeoutChk;
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
		appFormName.action.value="SearchQueueTime";
		appFormName.shwAddFrm.value = "search";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
	//}
}							
function submitShwAddForm() {
		appFormName.action.value = "ShowQueueTime";
		appFormName.shwAddFrm.value = "add";
		appFormName.firstAdd.value = "first";
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}	
function cancelAddForm(cancelAddOrEdit){
		appFormName.action.value = "ShowQueueTime";	
		appFormName.shwAddFrm.value = cancelAddOrEdit;		
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function enableInputField(value,AddOrEdit){
		appFormName.action.value = "ShowQueueTime";	
		appFormName.disableInputField.value = "false";
		appFormName.shwAddFrm.value = AddOrEdit;	
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function clearAll(){
		appFormName.expiry.value = "";	
		appFormName.reasonCode.value = "";
		//appFormName.reminder.value = "";
}
function saveQTimeLoanType(AddOrEdit){
		appFormName.action.value = "SaveQTimeLoanType";
		appFormName.disableInputField.value = "true";
		appFormName.shwAddFrm.value = AddOrEdit;
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function saveQTime(){
		appFormName.action.value = "SaveQTime";
		//appFormName.shwAddFrm.value = "save";		
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function gotoEditForm(qTimeOutIdEdit){
		appFormName.action.value="ShowQueueTime";
		appFormName.shwAddFrm.value = "edit";
		appFormName.qTimeOutIdEdit.value = qTimeOutIdEdit;
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function updateQTime(){
		appFormName.action.value="UpdateQTime";
		//appFormName.shwAddFrm.value = "update";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function deleteQTime(){
	if(validateDelete()){
		appFormName.action.value="DeleteQTime";
		appFormName.shwAddFrm.value = "delete";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
	}
}
function chkHour(){
	var hour = appFormName.hour.value;
	
	if(hour.length == 1){
		appFormName.hour.value = '0'+hour;
	}
}
function chkMinute(){
	var minute = appFormName.minute.value;
	
	if(minute.length == 1){
		appFormName.minute.value = '0'+minute;
	}
}
function chkSecond(){
	var second = appFormName.second.value;
	
	if(second.length == 1){
		appFormName.second.value = '0'+second;
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



