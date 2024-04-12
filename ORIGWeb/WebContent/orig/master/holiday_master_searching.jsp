<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.master.shared.model.HolidayM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>



<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />

<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	boolean shw = false;
	
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	HolidayM holidayM =new HolidayM(); 
	String holDate;
	String holDesc="";
	
	//Clear ValueList 
	if("add".equals(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///holiday_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		holidayM = (HolidayM)request.getSession().getAttribute("ADD_HOLIDAY_DATAM");
		if(holidayM == null){
			holidayM = new HolidayM();
		}
		VALUE_LIST.setResult(null);
	}
	//Chk to show edit data
	if("edit".equals(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///holiday_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		holidayM = (HolidayM)request.getSession().getAttribute("EDIT_HOLIDAY_DATAM");
		if(holidayM == null){
			holidayM = new HolidayM();
		}
	}
	//Chk to show search field
	holDate = (String)request.getSession().getAttribute("FIRST_SEARCH_searchDate");
	holDesc = (String)request.getSession().getAttribute("FIRST_SEARCH_searchDesc");
	if(holDate==null){
		holDate = "";
	}
	if(holDesc==null){
		holDesc = "";
	}
	
	System.out.println("check holidayM =" + holidayM );
	
	Vector ChkValueList = VALUE_LIST.getResult(); 
%>

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

<input type="hidden" name="shwAddFrm" value=""><%//  'Add' or 'Edit'%>	
<input type="hidden" name="holDateEdit" value="">

<!--  For Search: Filter   -->
<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td class="sidebar8"><table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
		<tr class="sidebar10"> <td align="center">
		<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
			<tr>
				<TD class="textColS" width="15%">Holiday Date :</TD>
			    <TD class="inputCol" width="19%"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",holDate,displayMode,"","searchDate","textbox","right",
			    " onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></TD>
				<TD class="textColS" width="15%">Holiday Description :</TD>
                <TD class="inputCol" width="20%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(holDesc,displayMode,"50","searchDesc","textbox","","100") %></TD>
				<TD class="inputCol" width="31%"><input type="button"  class ="button_text" name="Search" value="Search" onclick="searchApp()" ></TD>
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
					<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteHoliday()" >
					<%}else{ %>
					<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteHoliday()" disabled="disabled">
					<%} %>
				</TD></TR>
				<tr><td class="TableHeader">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="Bigtodotext3" width="3%"></TD> 
		                    <TD class="Bigtodotext3" width="10%">Selected</TD>
		                    <TD class="Bigtodotext3" width="15%">Holiday Date</TD>
		                    <TD class="Bigtodotext3" width="30%">Holiday Description</TD>
		                    <TD class="Bigtodotext3" width="42%">Working Day</TD>
		               </TR>
					</table> 
				</td></tr>
                    
            <%
				Vector valueList = VALUE_LIST.getResult(); 
				if(valueList != null && valueList.size() > 1){
					for(int i=1;i<valueList.size();i++){
						Vector elementList = (Vector)valueList.get(i);
						String[] date = ((String)elementList.elementAt(1)).split(" ");
						System.out.println("date = "+date); 
						System.out.println("elementList.elementAt(1)) = "+elementList.elementAt(1)); 
			%>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="jobopening2" width="3%"></td>
							<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","holChk",date[0],"" )%></td>
							<td class="jobopening2" width="15%"><a href="javascript:gotoEditForm('<%=ORIGUtility.displayEngToThaiDate(
								ORIGDisplayFormatUtil.parseDate(ORIGDisplayFormatUtil.StringToDate(date[0],"mm/dd/yyyy")))  %>')">
								<%=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(ORIGDisplayFormatUtil.StringToDate(date[0],"mm/dd/yyyy"))) %></a></td>
							<td class="jobopening2" width="30%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(2))%></td>
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

<!--Add-Edit Section-->
<%
	String strOnclick = "";
	String label = "";
	String cancelBtn = "";	
	if("add".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		label = "Add";		
		strOnclick = "saveHolMaster()";
		cancelBtn = "cancelAdd";
		shw = true;
	}else if("edit".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		label = "Edit";
		strOnclick = "updateHoliday()";
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
					              <TD class="textColS" width="17%">Holiday Date <FONT color = red>* </FONT> :</TD>
					              <TD class="inputCol" width="33%"> 
					                <%if(label.equals("Add")){
					                	if(holidayM.getHolDate()!=null && !"".equals(holidayM.getHolDate())){
					                %>
					                <%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(holidayM.getHolDate())),displayMode,"","holidayDate","textbox","right"," onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %> 
					                <%
					                	}else{%>
					                <%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName","",displayMode,"","holidayDate","textbox","right"," onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %> 
					                <%	} %>
					                <%}else{%>
					                <%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(holidayM.getHolDate())),displayMode,"","holidayDate","textbox","right","readonly onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %> 
					                <%}%>
					                dd/mm/yyyy</TD>
					              <TD class="inputCol" width="10%">&nbsp;</TD>
					              <TD class="inputCol" width="15%">&nbsp;</TD>
					              <TD></TD>
					           </TR>
					           <TR> 
					              <TD class="textColS">Holiday Description<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(holidayM.getHolDesc(),displayMode,"50","holidayDesc","textbox","","100") %></TD>
					              <TD class="textColS">Working Day<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol">
					                <%
							if(holidayM.getWorkFlag() != null){
								if("Y".equals(holidayM.getWorkFlag())){
								%>
					                <INPUT type=radio CHECKED value="Y" name="workingFlag">
					                Yes 
					                <INPUT type=radio value="N" name="workingFlag">
					                No 
					                <%		}else{%>
					                <INPUT type=radio  value="Y" name="workingFlag">
					                Yes 
					                <INPUT type=radio CHECKED value="N" name="workingFlag">
					                No 
					                <%		}
							}else{%>
					                <INPUT type=radio CHECKED value="Y" name="workingFlag">
					                Yes 
					                <INPUT type=radio value="N" name="workingFlag">
					                No 
					                <%	}%>
					                </TD>
					                <TD class="inputCol"></TD>
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
function submitShwAddForm() {
		appFormName.action.value = "ShowHolidayMaster";
		appFormName.shwAddFrm.value = "add";
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}	
function cancelAddForm(cancelAddOrEdit){
		appFormName.action.value = "ShowHolidayMaster";	
		appFormName.shwAddFrm.value = cancelAddOrEdit;	
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}

function validate(){
	var searchDate 	= appFormName.searchDate.value;
	var searchDesc = appFormName.searchDesc.value;
	
	if( searchDate=="" && searchDesc=="" ){
		alert("At Least One Field Must Be Filled");
		return false;
	}else{
		return true;
	}
}
function searchApp(){
	//if( validate() ){
		appFormName.action.value="SearchHoliday";
		appFormName.handleForm.value = "N";
		appFormName.submit();	
	//}
}					

function saveHolMaster(){
		appFormName.action.value = "SaveHolMaster";
		appFormName.shwAddFrm.value = "save";		
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function gotoEditForm(holDateEdit){
		appFormName.action.value="ShowHolidayMaster";
		appFormName.shwAddFrm.value = "edit";
		appFormName.holDateEdit.value = holDateEdit;
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function updateHoliday(){
		appFormName.action.value="UpdateHolMaster";
		appFormName.shwAddFrm.value = "update";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function validateDelete(){
	var objSeq = appFormName.holChk;
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
function deleteHoliday(){
	if(validateDelete()){
		appFormName.action.value="DeleteHolMaster";
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