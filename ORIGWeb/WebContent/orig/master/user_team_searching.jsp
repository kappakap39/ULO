
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.master.shared.model.UserTeamM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.master.shared.model.UserTeamMemberM" %>

<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />


<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	boolean shw = false;
	
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	ORIGUtility utility = new ORIGUtility();
	
	Vector userNameVect =(Vector)request.getSession().getAttribute("USERNAME_SESSION");
	System.out.println("userNameVect = "+userNameVect);
	if(userNameVect==null){
		userNameVect = new Vector();
	}
	Vector selMemberVect =(Vector)request.getSession().getAttribute("SEL_MEMBER_SESSION");
	System.out.println("selMemberVect = "+selMemberVect);
	if(selMemberVect==null){
		selMemberVect = new Vector();
	}

	
	UserTeamM userTeamM =new UserTeamM(); 
	String teamName;
	String teamDesc;
	
	//Clear ValueList 
	if("add".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		VALUE_LIST.setResult(null);
	}
	//Chk to show edit data
	if("edit".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///fieldId_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		userTeamM = (UserTeamM)request.getSession().getAttribute("EDIT_USER_TEAM_DATAM");
		if(userTeamM == null){
			userTeamM = new UserTeamM();
		}
	}
	//Chk to show add data
	if("add".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///userTeam_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		userTeamM = (UserTeamM)request.getSession().getAttribute("ADD_USER_TEAM_DATAM");
		if(userTeamM == null){
			userTeamM = new UserTeamM();
		}
	}
	//Chk to show search field
	teamName = (String)request.getSession().getAttribute("FIRST_SEARCH_teamName");
	teamDesc = (String)request.getSession().getAttribute("FIRST_SEARCH_teamDesc");
	if(teamName==null){
		teamName = "";
	}
	if(teamDesc==null){
		teamDesc = "";
	}
	
	System.out.println("check userTeamM =" + userTeamM );
	
	Vector ChkValueList = VALUE_LIST.getResult(); 
%>

<input type="hidden" name="shwAddFrm" value=""><%//  'Add' or 'Edit'%>	
<input type="hidden" name="addOrRemove" value="">
<input type="hidden" name="teamIDEdit" value="">

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
				<TD class="textColS" width="13%">User Team Code :</TD>
				<TD class="inputCol" width="18%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(teamName,displayMode,"25","teamName","textbox","","50") %></TD>
				<TD class="textColS" width="20%">User Team Description :</TD>
                <TD class="inputCol" width="18%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(teamDesc,displayMode,"30","teamDesc","textbox","","300") %></TD>
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
					<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteUserTeam()" >
					<%}else{ %>
					<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteUserTeam()" disabled="disabled">
					<%} %>				
				</TD></TR>
				<tr><td class="TableHeader">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="Bigtodotext3" width="3%"></TD>
							<TD class="Bigtodotext3" width="10%">Selected</TD>
							<TD class="Bigtodotext3" width="25%">User Team Code</TD> 													
							<TD class="Bigtodotext3" width="62%">User Team Description</TD>					    
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
							<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","userTeamChk",(String)elementList.elementAt(3),"") %></td>
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

<!--Add-Edit Section-->
<%
	String strOnclick = "";
	String label = "";
	String cancelBtn = "";	
	if("add".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		label = "Add";		
		strOnclick = "saveUserTeam()";
		cancelBtn = "cancelAdd";
		shw = true;
	}else if("edit".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		label = "Edit";
		strOnclick = "updateUserTeam()";
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
										<input type="reset"  class ="button" name="Cancel" value="Cancel" onclick="cancelAddForm()">		
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
					              <TD class="textColS" width="20%">User Team Code<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol" width="18%"> 
					                <%if("Add".equalsIgnoreCase(label)){
									%>
					                <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(userTeamM.getTeamName(),displayMode,"12","tmNm","textbox","","50") %> 
					                <%
									  }else{%>
					                <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(userTeamM.getTeamName(),displayMode,"12","tmNm","textbox","","50") %> 
					                <%}%>
					                </TD>
					              <TD class="textColS" width="22%">User Team Description<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol" width="40%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(userTeamM.getTeamDesc(),displayMode,"47","tmDesc","textbox","","300") %> 
					               </TD>
					            </TR>
					            <TR> 
					              <TD class="textColS">User Name :</TD>
					              <TD class="inputCol"> 
					                <SELECT NAME="userName" MULTIPLE SIZE=15 style="width:200" class="combobox"  >
											<%if (userNameVect != null && userNameVect.size()>0) {
													for (int i = 0; i < userNameVect.size(); i++) {
														String userName = (String) userNameVect.get(i);
											%>
											<OPTION value ="<%=userName %>" class=textbox ><%= userName%></OPTION>	
											<%		}
											} %>
									</SELECT>
					              <TD class="inputCol" align="center"><input type="button"  class ="button_text" name="go" value="&gt;&gt;" onclick= "addOrRemoveMember('add','<%=label %>')"></TD>
					              <TD class="inputCol">
					              	<%
										if(selMemberVect != null && selMemberVect.size() > 0){
									%>
					              	<input type="button"  class ="button_text" name="Del" value="Delete" onclick="addOrRemoveMember('remove','<%=label %>')" ><br><br>	
					              	<%}else{ %>
					              	<input type="button"  class ="button_text" name="Del" value="Delete" disabled="disabled" ><br><br>	
					              	<%} %>
					              	<div id="KLTable">
								  	    <table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									    <tr><td class="TableHeader">
									    	<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr>
												<td class="Bigtodotext3" width="15%"></td>
							                    <td width="30%" class="Bigtodotext3">Team Lead</td>
							                    <td width="55%" class="Bigtodotext3">User Name</td>
							                </tr>
											</table> 
										</td></tr>
					                  <%
										if(selMemberVect != null && selMemberVect.size() > 0){
										UserTeamMemberM memberM;
											for(int i=0;i<selMemberVect.size();i++){
												memberM = (UserTeamMemberM)selMemberVect.get(i);
									%>
										<tr><td align="center" class="gumaygrey2">
											<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
												<tr>													
												<%
												String[] selMemStrArry = (String[])request.getSession().getAttribute("selMemStrArry_SESSION");
												boolean isMemberToDel = false;
												if (selMemStrArry !=null && selMemStrArry.length>0) {
													System.out.println("selMemStrArry.length = "+selMemStrArry.length);
													for (int j=0; j<selMemStrArry.length;j++) {
														String memberToDel= selMemStrArry[j];
														if(memberToDel.equals(memberM.getMemberID())){
															isMemberToDel = true;
														}
													}
												}
												 %>
													<td class="jobopening2" width="15%">
													<%if(isMemberToDel){ %>
														<%=ORIGDisplayFormatUtil.displayCheckBox(memberM.getMemberID(),"memberChk",memberM.getMemberID(),"") %>
													<%}else{ %>
														<%=ORIGDisplayFormatUtil.displayCheckBox("delete","memberChk",memberM.getMemberID(),"") %>
													<%} %>
													</td>
													<td class="jobopening2" width="30%">
													<%
													String member_leader = (String)request.getSession().getAttribute("member_leader_SESSION");
													if(memberM.getMemberID().equals(member_leader)){%>
															<INPUT type=radio checked="checked" value="<%=memberM.getMemberID()%>" name="leaderFlag">
													<%}else{ %>
															<INPUT type=radio value="<%=memberM.getMemberID()%>" name="leaderFlag">
													<%}	%>
													</td>
													<td class="jobopening2" width="55%"><%=memberM.getMemberID()%></td>
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
										  			<td class="jobopening2" align="center">No Member</td>
										  		</tr>
											</table> 
										</td></tr>
									<%
										}
									%>
									</TABLE>
								</div>
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
	var teamName 	= appFormName.teamName.value;
	var teamDesc = appFormName.teamDesc.value;
	
	if( teamName=="" && teamDesc=="" ){
		alert("At Least One Field Must Be Filled");
		return false;
	}else{
		return true;
	}
}
function validateDelete(){
	var objSeq = appFormName.userTeamChk;
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
		appFormName.action.value="SearchUserTeam";
		appFormName.handleForm.value = "N";
		appFormName.submit();	
	//}
}										
function submitShwAddForm() {
		appFormName.action.value = "ShowUserTeam";
		appFormName.shwAddFrm.value = "add";
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}	
function cancelAddForm(){
		appFormName.action.value = "ShowUserTeam";	
		appFormName.shwAddFrm.value = "cancel";	
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}

function addOrRemoveMember(addOrRemove, addOrEditFrm){
		appFormName.action.value = "AddRemoveUsrTeamMember";	
		appFormName.shwAddFrm.value = addOrEditFrm;	
		appFormName.addOrRemove.value = addOrRemove;	
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}

function saveUserTeam(){
		appFormName.action.value = "SaveUserTeam";
//		appFormName.shwAddFrm.value = "save";		
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function gotoEditForm(teamIDEdit){
		appFormName.action.value="ShowUserTeam";
		appFormName.shwAddFrm.value = "edit";
		appFormName.teamIDEdit.value = teamIDEdit;
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function updateUserTeam(){
		appFormName.action.value="UpdateUserTeam";
		appFormName.shwAddFrm.value = "update";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function deleteUserTeam(){
	if(validateDelete()){
		appFormName.action.value="DeleteUserTeam";
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


