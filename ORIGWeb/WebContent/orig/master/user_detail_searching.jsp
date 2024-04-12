<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.profile.model.UserDetailM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.master.shared.model.UserBranchM" %>
<%@ page import="com.eaf.orig.master.shared.model.UserExceptActM" %>
<%@ page import="com.eaf.orig.master.shared.model.UserNameM" %>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />

<%
	
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	boolean shw = false;
	boolean shwProfile = false;
	
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	ORIGUtility utility = new ORIGUtility();
//	Vector positionVect = utility.loadCacheByName("Position");
	Vector positionVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",10);
	Vector CommuChannelVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",13);
	Vector officeCodeVect = utility.loadCacheByName("OfficeInformation");
	
	UserDetailM userDetailM = new UserDetailM();
	String searchUserName="";
	
	//Clear ValueList 
	if("add".equals(ORIGMasterForm.getShwAddFrm())){
		VALUE_LIST.setResult(null);
	}
	//Chk to show edit data
	if("edit".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///user_detail_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		userDetailM = (UserDetailM)request.getSession().getAttribute("EDIT_USER_DETAIL_DATAM");
		if(userDetailM==null){
			userDetailM = new UserDetailM();
		}
	}
	//Chk to show data after profile
	if("add".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///user_detail_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		userDetailM = (UserDetailM)request.getSession().getAttribute("ADD_USER_DETAIL_DATAM");
		if(userDetailM==null){
			userDetailM = new UserDetailM();
		}
	}
	//Chk to show search field
	searchUserName = (String)request.getSession().getAttribute("FIRST_SEARCH_USER_NAME");
	if(searchUserName==null){
		searchUserName="";
	}
	
	System.out.println("check userDetailM =" + userDetailM );
	
	String branchDescSearch = (String)request.getSession().getAttribute("BRANCH_DESC_SEARCH");
	String exceptDescSearch = (String)request.getSession().getAttribute("EXCEPT_ACT_DESC_SEARCH");
	String usernameDescSearch = (String)request.getSession().getAttribute("USER_NAME_DESC_SEARCH");
	
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
<input type="hidden" name="showProfile" value="">
<input type="hidden" name="userNameEdit" value="">
<input type="hidden" name="AddOrEdit" value="">
<input type="hidden" name="addOrRemove" value="">

<!--  For Search: Filter   -->
<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td class="sidebar8"><table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
		<tr class="sidebar10"> <td align="center">
		<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
			<tr>
				<TD class="textColS" width="13%" align="right">User Name :</TD>
			    <TD class="inputCol" width="15%" align="left"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(searchUserName,displayMode,"25","user_name","textbox","","50") %></TD>
			  	<TD class="textColS" width="6%"><input type="button" name="SearchBtn" value="<%=MessageResourceUtil.getTextDescription(request, "SEARCH") %>" onClick="searchApp()" class="button_text"></TD>
				  	<TD class="inputCol" width="66%"></TD>
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
						<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteUsrDetail()" >
						<%}else{ %>
						<input type="button"  class ="button_text" name="Delete" value="Delete Items" onclick="deleteUsrDetail()" disabled="disabled">
						<%} %>										
				</TD></TR>
				<tr><td class="TableHeader">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="Bigtodotext3" width="3%"></TD>
							<TD class="Bigtodotext3" width="10%" align="left">Selected</TD>
							<TD class="Bigtodotext3" width="24%" align="left">User Name</TD>
						    <TD class="Bigtodotext3" width="24%" align="left">User First Name</TD>
						    <TD class="Bigtodotext3" width="24%" align="left">User Last Name</TD>											
						    <TD class="Bigtodotext3" width="15%" align="left">Active</TD>
						</TR>
					</table> 
				</td></tr>	
							<%
						Vector valueList = VALUE_LIST.getResult(); 
						if(valueList != null && valueList.size() > 1){
							for(int i=1;i<valueList.size();i++){
								Vector elementList = (Vector)valueList.get(i);//								
					%>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="jobopening2" width="3%"></td>
							<td class="jobopening2" width="10%" align="left"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","userDetailChk",(String)elementList.elementAt(1),"") %></td>
							<td class="jobopening2" width="24%" align="left"><a href="javascript:gotoEditForm('<%=elementList.elementAt(1) %>')"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(1)) %></a></td>
							<td class="jobopening2" width="24%" align="left"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(2)) %></td>
							<td class="jobopening2" width="24%" align="left"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(3)) %></td>
							<td class="jobopening2" width="15%" align="left"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(4)) %></td>
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
	String label = "";
	String cancelBtn = "";	
	if("add".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		label = "Add";		
		strOnclick = "saveUserDetail()";
		cancelBtn = "cancelAdd";
		shw = true;
	}else if("edit".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		label = "Edit";
		strOnclick = "updateUserDetail()";
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
                                    	<input type="button" class ="button" name="Save" value="Save" onclick="<%=strOnclick%>" >
									</td><td>
										<input type="reset" class ="button" name="Cancel" value="Cancel" onclick="cancelUserDetail('<%=cancelBtn%>')">
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
					              <TD class="textColS" width="13%">User Name<FONT color = red>*</FONT> : </TD>
					              <TD class="inputCol" width="36%" colspan="2"> 
						                <%if(label.equals("Add")){
						                %>
						                <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(userDetailM.getUserName(),displayMode,"25","userName","textbox","","50") %> 
						                <%}else{%>
						                <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(userDetailM.getUserName(),displayMode,"25","userName","textbox","readonly","50") %>	
						                <%}%>
					              </TD>
					              <TD class="textColS">User Description<FONT color = red>*</FONT> : </TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(userDetailM.getDescription(),displayMode,"25","description","textbox","","100") %></TD>
					            </TR>            
					            <TR> 
					              <TD class="textColS">Name<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(userDetailM.getFirstName(),displayMode,"25","firstName","textbox","","50") %></td>
					              <TD class="inputCol">
					              	<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(userDetailM.getLastName(),displayMode,"25","lastName","textbox","","50") %></TD>
					              <TD class="textColS">Position :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(positionVect,userDetailM.getPosition(),"position",displayMode,"50") %></TD>
					            </TR>
					             <TR> 
					              <TD class="textColS">Department :</TD>
					              <TD class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(userDetailM.getDepartment(),displayMode,"25","department","textbox","","50") %></TD>
					              <TD class="textColS">Job Description :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(userDetailM.getJobDescription(),displayMode,"48","jobDescription","textbox","","300") %></TD>
					            </TR>
					            <TR> 
					              <TD class="textColS">Region :</TD>
					              <TD class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(userDetailM.getRegion(),displayMode,"25","region","textbox","","10") %></TD>
					              <TD class="textColS">Zone :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(userDetailM.getZoneID(),displayMode,"25","zone","textbox","","50") %></TD>
					            </TR>
					            <TR> 
					              <TD class="textColS">Telephone :</TD>
					              <TD class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(userDetailM.getTelephone(),displayMode,"25","telephone","textbox","","100") %></TD>
					              <TD class="textColS">Mobile Phone :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(userDetailM.getMobilePhone(),displayMode,"25","mobilePhone","textbox","onKeyPress=\"keyPressInteger();\"","100") %> </TD>
					            </TR>
								<tr>
									<td class="textColS">Email :</td>
									<td class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(userDetailM.getEmail(),displayMode,"25","email","textbox","onblur=\"javascript:checkEmailAddress('email')\"","100") %></td>
									<TD class="textColS">Communication Channel<FONT color = red>*</FONT> :</TD>
					            	<TD class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(CommuChannelVect,userDetailM.getCommunicate_channel(),"communicationChannel",displayMode,"") %></TD>
								</tr>
								<TR> 
					              <TD class="textColS">Default Office Code :</TD>
					              <TD class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(officeCodeVect,userDetailM.getDefaultOfficeCode(),"officeCode",displayMode,"") %></TD>
					              <TD class="textColS">Skip IP :</TD>
					              <TD class="inputCol"> 
					                <%if(userDetailM.getStatus()!=null && !"".equals(userDetailM.getStatus())){	
										System.out.println("state====>" + userDetailM.getSkipIP() );											
											if(("Y").equalsIgnoreCase(userDetailM.getSkipIP())){
									%>
					                <INPUT type=radio CHECKED value="Y" name="skipIP">
					                Yes 
					                <INPUT type=radio value="N" name="skipIP">
					                No 
					                <%		}else{%>
					                <INPUT type=radio value="Y" name="skipIP">
					                Yes 
					                <INPUT type=radio CHECKED value="N" name="skipIP">
					                No 
					                <%		}
									}else{
										%>
					                <INPUT type=radio CHECKED value="Y" name="skipIP">
					                Yes 
					                <INPUT type=radio value="N" name="skipIP">
					                No 
					                <%	}%>
					                
					              </TD>
					            </TR>
					            <TR> 
					              <TD class="textColS">Active<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol" colspan="2">
					                <%if(userDetailM.getStatus()!=null && !"".equals(userDetailM.getStatus()) ){	
										System.out.println("userDetailM.getStatus()====>" + userDetailM.getStatus() );											
											if(("Y").equalsIgnoreCase(userDetailM.getStatus())){ 
									%>
					                <INPUT type=radio CHECKED value="Y" name="active">
					                Yes 
					                <INPUT type=radio value="N" name="active">
					                No 
					                <%		}else{%>
					                <INPUT type=radio  value="Y" name="active">
					                Yes 
					                <INPUT type=radio CHECKED value="N" name="active">
					                No 
					                <%	}
									}else{
										%>
					                <INPUT type=radio CHECKED value="Y" name="active">
					                Yes 
					                <INPUT type=radio value="N" name="active">
					                No 
					              <%}%>
					                </TD>
					                <TD class="textColS">Unlocked User :</TD>
					             	<TD align="left" class="inputCol"><%=ORIGDisplayFormatUtil.displayCheckBox(userDetailM.getLogOnFlag(),"unlockChk","Y","") %></TD>
					            </TR>
					            <tr>
									<TD class="textColS">Image Path :</TD>
					                <TD class="inputCol" colspan="2"> <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(userDetailM.getImgPath(),displayMode,"25","imagePath","textbox","","100") %>
					                <!--<input class="textColS" type="file" name="imagePath" value="" >--></TD>
									
									<% 
									String profileDisable="";
									if(com.eaf.orig.shared.constant.OrigConstant.ORIG_FLAG_Y.equalsIgnoreCase(ORIGUser.getHelpDeskFlag())){
									 profileDisable=" disabled";
									}
									%>				
					            	<TD align="left"><INPUT class=button_text type="button" value="Profile" name="profile" onclick="showprofile('<%=label %>')" <%=profileDisable%> ></TD>
					            	<TD></TD>
								</tr>
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
	String AddOrEdit=(String)request.getSession().getAttribute("AddOrEdit_SESSION");
	if("show".equalsIgnoreCase(ORIGMasterForm.getShwProfileFrm())){
//			ORIGMasterForm.setShwProfileFrm("");	
			shwProfile = true;
	}
	if(shwProfile){		
%>
<!--profile Section-->
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
                                    	<INPUT type="button"  class ="button" name="Save" value="Save" onclick = "saveProfile('<%=AddOrEdit%>')">
                                    </td><td>					
										<INPUT type="button"  class ="button" name="Cancle" value="Cancel" onclick = "cancelProfile('<%=AddOrEdit%>')">
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
		           		<tr class="sidebar10"> <td align="center">
					<!-- ********   Profile   ********* !!! Username is Profile !!! --> 
				     	<table cellSpacing="1" cellPadding="0" width="100%" align="center" border="0">
				        	<TR> 
				               <TD width="16%"></TD>
				               <TD width="24%"></TD>
				               <TD width="18%"></TD>
				               <TD width="24%"></TD>
				               <TD width="18%"></TD>
				             </TR>
				             <TR> 
				               <TD class="textColS">Group Name : </TD>
				               <TD><FONT class=font8><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(usernameDescSearch,displayMode,"36","searchUserNameDesc","textbox","","50") %> </FONT></TD>
						       <TD><input type="button"  class ="button_text" name="userNameBtn" value="Search" onclick="searchUserNameByDesc()" ></input></TD>
						       <TD></TD>
						       <TD></TD>
				             </TR>
				            <TR> 
				              <TD class="textColS">Profile :</TD>
				              <TD rowspan="5" align="left"> <SELECT NAME="UsrName" SIZE="4" style="width:200" class="combobox">
				                  <%
												Vector userNameSearchVect = (Vector)request.getSession().getAttribute("SEARCH_USER_NAME");
		                  							System.out.println("userNameSearchVect = " + userNameSearchVect);
		                  							if(userNameSearchVect==null){
		                  								userNameSearchVect = new Vector();
		                  							}
		                  							UserNameM userNameM;
		                  							System.out.println("userNameSearchVect size = " + userNameSearchVect.size());
		                  							if(userNameSearchVect.size() > 0){ // if1
														for(int i =0;i<userNameSearchVect.size();i++){ // for1
															userNameM = (UserNameM)userNameSearchVect.get(i);
				
							%>
				                  <OPTION value = "<%=userNameM.getGroupName()%>" class="textbox" ><%=userNameM.getGroupName()%></OPTION>
				                  <%							 
													} // end for1
												}// end if1
							%>
				                </SELECT> </TD>	
				              <TD align="left" class="textColS">Selected  Profile :</TD>	              
				              <TD rowspan="5" align="left"> <SELECT NAME="selectedUsrName" MULTIPLE SIZE="4" class="combobox" style="width:200" ondblclick = "submitSelForm(document.forms[0],'ShwSelectUserProfile','N','')"  >
				                  <%
												Vector userNameSelVect = (Vector)request.getSession().getAttribute("SELECTED_USER_NAME");
		                  							System.out.println("userNameSelVect = " + userNameSelVect);
		                  							if(userNameSelVect==null){
		                  								userNameSelVect = new Vector();
		                  							}
		                  							UserNameM selUserNameM;
		                  							System.out.println("userNameSelVect size = " + userNameSelVect.size());
		                  							if(userNameSelVect.size() > 0){ // if1
														for(int i =0;i<userNameSelVect.size();i++){ // for1
															selUserNameM = (UserNameM)userNameSelVect.get(i);
				
							%>
				                 <OPTION value = "<%=selUserNameM.getGroupName()%>" class="textbox" ><%=selUserNameM.getGroupName()%></OPTION>
				                  <%							 
													} // end for1
												}// end if1
							%>
				                </SELECT> </TD>
				                 <TD align="center">&nbsp;</TD>
				            </TR>
				            <TR> 
				              <TD align="center">&nbsp;</TD> 
		            		   
				           	  <TD align="center"> <input type="button"  class ="button_text" name="go" value="&gt;&gt;" onclick= "addOrRemoveUserName('add')"></TD>                                                 
				              <TD align="center">&nbsp;</TD>
				               <TD align="center">&nbsp;</TD>
				            </TR>
				            <TR> 
				              <TD align="center">&nbsp;</TD>            		       
				              <TD align="center"> &nbsp;<input type="button"  class ="button_text" name="back" value="&lt;&lt;" onclick= "addOrRemoveUserName('remove')"></TD>
				              <TD align="center">&nbsp;</TD>		                
				            </TR>   		             
				           </table>
			         	</td></tr>
			         
			         <!-- ******** Branch ********* -->
				     <tr class="sidebar10"> 
				     	<TD align="center">
				  			<table cellSpacing=1 cellPadding=0 width=100% align=center border=0>
				             <TR> 
				               <TD width="16%"></TD>
				               <TD width="24%"></TD>
				               <TD width="18%"></TD>
				               <TD width="24%"></TD>
				               <TD width="18%"></TD>
				             </TR>
				             <TR> 
				               <TD class="textColS">Branch : </TD>
				               <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(branchDescSearch,displayMode,"36","searchBranchDesc","textbox","","30") %></TD>
						       <TD><input type="button"  class ="button_text" name="SearchBranch" value="Search" onclick="searchBranchByDesc()" ></input></TD>
						       <TD></TD>
						       <TD></TD>
				             </TR>
				             <TR> 
				              <TD class="textColS" align="left"></TD>
				              <TD rowspan="5" align="left"> <SELECT NAME="branch" MULTIPLE SIZE="4" style="width:200" class="combobox">
				                  <%
												Vector branchSearchVect = (Vector)request.getSession().getAttribute("SEARCH_BRANCH");
		                  							System.out.println("branchSearchVect = " + branchSearchVect);
		                  							if(branchSearchVect==null){
		                  								branchSearchVect = new Vector();
		                  							}
		                  							UserBranchM userBranchM;
		                  							System.out.println("branchSearchVect size = " + branchSearchVect.size());
		                  							if(branchSearchVect.size() > 0){ // if1
														for(int i =0;i<branchSearchVect.size();i++){ // for1
															userBranchM = (UserBranchM)branchSearchVect.get(i);
				
							%>
				                  <OPTION value = "<%=userBranchM.getCmpCde()%>,<%=userBranchM.getArea()%>,<%=userBranchM.getBranchDesc()%>" class=textbox ><%=userBranchM.getArea()%> - <%=userBranchM.getBranchDesc()%></OPTION>
				                  <%							 
													} // end for1
												}// end if1
							%>
				                </SELECT> </TD>	
				              <TD align="left"></TD>	              
				              <TD rowspan="5" align="left"> <SELECT NAME="selectedBranch" MULTIPLE SIZE="4" style="width:200" class="combobox" ondblclick = "submitSelForm(document.forms[0],'ShwSelectUserProfile','N','')" class="inputCol">
				                  <%
												Vector branchSelVect = (Vector)request.getSession().getAttribute("SELECTED_BRANCH");
		                  							System.out.println("branchSelVect = " + branchSelVect);
		                  							if(branchSelVect==null){
		                  								branchSelVect = new Vector();
		                  							}
		                  							UserBranchM selUserBranchM;
		                  							System.out.println("branchSelVect size = " + branchSelVect.size());
		                  							if(branchSelVect.size() > 0){ // if1
														for(int i =0;i<branchSelVect.size();i++){ // for1
															selUserBranchM = (UserBranchM)branchSelVect.get(i);
				
							%>
				                  <OPTION value = "<%=selUserBranchM.getCmpCde()%>,<%=selUserBranchM.getArea()%>,<%=selUserBranchM.getBranchDesc()%>" class=textbox ><%=selUserBranchM.getArea()%> - <%=selUserBranchM.getBranchDesc()%></OPTION>
				                  <%							 
													} // end for1
												}// end if1
							%>
				                </SELECT> </TD>
				                <TD align="center">&nbsp;</TD>
				            </TR>
				            <TR> 
				              <TD class="textColS">Branch :</TD>
				              <TD class="textColS">Selected Branch :</TD>
				              <TD ></TD>		             
				              <TD ></TD>
				            </TR>
				            <TR> 
				              <TD align="center"></TD> 		               
				           	  <TD align="center"> <input type="button"  class ="button_text" name="go" value="&gt;&gt;" onclick= "addOrRemoveBranch('add')"></TD>                                                 
				              <TD align="center" colspan="2"></TD>
				            </TR>
				            <TR> 
				              <TD align="center"></TD> 		             
				              <TD align="center"> <input type="button"  class ="button_text" name="back" value="&lt;&lt;" onclick= "addOrRemoveBranch('remove')"></TD>
				              <TD align="center" colspan="2"></TD>
				            </TR>   
				           </table>
			         </TD>
			         </TR>
			         
			         <!-- ********Exceptional Action********* -->
				     <tr class="sidebar10">
				     <TD align="center">
				  			<table cellSpacing=1 cellPadding=0 width=100% align=center border=0>
				             <TR> 
				               <TD width="16%"></TD>
				               <TD width="24%"></TD>
				               <TD width="18%"></TD>
				               <TD width="24%"></TD>
				               <TD width="18%"></TD>
				             </TR>
				             <TR> 
				               <TD class="textColS">Exceptional Action : </TD>
				               <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(exceptDescSearch,displayMode,"36","searchExceptActDesc","textbox","","100") %></TD>
						        <TD><input type="button"  class ="button_text" name="SearchExceptAct" value="Search" onclick="searchExceptActByDesc()" ></input></TD>
						       <TD></TD>
						       <TD></TD>
				             </TR>
				            <TR> 
				              <TD align="left"></TD>
				              <TD rowspan="5" align="left"> <SELECT NAME="exceptAct" MULTIPLE SIZE="4" style="width:200" class="combobox">
				                  <%
											Vector exceptSearchVect = (Vector)request.getSession().getAttribute("SEARCH_EXCEPT");
		                  							System.out.println("exceptSearchVect = " + exceptSearchVect);
		                  							if(exceptSearchVect==null){
		                  								exceptSearchVect = new Vector();
		                  							}
		                  							UserExceptActM userExceptActM;
		                  							System.out.println("exceptSearchVect size = " + exceptSearchVect.size());
		                  							if(exceptSearchVect.size() > 0){ // if1
														for(int i =0;i<exceptSearchVect.size();i++){ // for1
															userExceptActM = (UserExceptActM)exceptSearchVect.get(i);
				
							%>
				                  <OPTION value = "<%=userExceptActM.getExceptID()%>,<%=userExceptActM.getExceptDesc()%>" class=textbox ><%=userExceptActM.getExceptDesc()%></OPTION>
				                  <%						 
													} // end for1
												}// end if1
							%>
				                </SELECT> </TD>	
				              <TD align="left"></TD>	              
				              <TD rowspan="5" align="left"> <SELECT NAME="selectedExceptAct" MULTIPLE SIZE="4" style="width:200" class="combobox">
				                  <%
												Vector exceptSelVect = (Vector)request.getSession().getAttribute("SELECTED_EXCEPT_ACT");
		                  							System.out.println("exceptSelVect = " + exceptSelVect);
		                  							if(exceptSelVect==null){
		                  								exceptSelVect = new Vector();
		                  							}
		                  							UserExceptActM selUserExceptActM;
		                  							System.out.println("exceptSelVect size = " + exceptSelVect.size());
		                  							if(exceptSelVect.size() > 0){ // if1
														for(int i =0;i<exceptSelVect.size();i++){ // for1
															selUserExceptActM = (UserExceptActM)exceptSelVect.get(i);
				
							%>
				                  <OPTION value = "<%=selUserExceptActM.getExceptID()%>,<%=selUserExceptActM.getExceptDesc()%>" class=textbox ><%=selUserExceptActM.getExceptDesc()%></OPTION>
				                  <%							 
													} // end for1
												}// end if1
							%>
				                </SELECT> </TD>
				                <TD align="center">&nbsp;</TD>
				            </TR>
				            <TR> 
				              <TD class="textColS">Exceptional Action :</TD>
				              <TD class="textColS">Selected Exceptional Action :</TD>
				              <TD ></TD>		             
				              <TD ></TD>
				              <td></td>
				            </TR>
				            <TR> 
				              <TD align="center"></TD> 		    
				           	  <TD align="center"> <input type="button"  class ="button_text" name="go" value="&gt;&gt;" onclick= "addOrRemoveExcept('add')"></TD>                                                 
				              <TD align="center" colspan="2"></TD>
				            </TR>
				            <TR> 
				              <TD align="center"></TD> 		         
				              <TD align="center"> <input type="button"  class ="button_text" name="back" value="&lt;&lt;" onclick= "addOrRemoveExcept('remove')"></TD>
				              <TD align="center" colspan="2"></TD>
				            </TR>   
				           </table>
			         </TD>
			         </TR>
					</table>
				</td></tr>
			</table>
		</td></tr>		
	</table>
	</TD></TR>
</TABLE>
	
<%} %>
<!--END profile Section-->

<%
	//clear ORIGMasterFormHandler value
	//ORIGMasterForm.setShwAddFrm("");
	ORIGMasterForm.setShwProfileFrm("");	
		
 %> 

<script language="JavaScript">
function validate(){
	var user_name 	= appFormName.user_name.value;
	
	if( user_name=="" ){
		alert("Please Input User Name");
		return false;
	}else{
		return true;
	}
}
function validateDelete(){
	var objSeq = appFormName.userDetailChk;
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
		appFormName.action.value="SearchUserDetail";
		appFormName.shwAddFrm.value = "search";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
	//}
}
function submitShwAddForm() {
		appFormName.action.value = "ShowUserDetail";
		appFormName.shwAddFrm.value = "add";
		appFormName.handleForm.value = "Y";	
		appFormName.submit();   
}
function saveUserDetail(){
		appFormName.action.value = "SaveUserDetail";	
//		appFormName.shwAddFrm.value = "save";		
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function cancelUserDetail(cancelAddOrEdit){
		appFormName.action.value = "ShowUserDetail";		
		appFormName.shwAddFrm.value = cancelAddOrEdit;	
		appFormName.handleForm.value = "Y";
		appFormName.submit(); 
}
function showprofile(AddOrEdit){
		appFormName.action.value = "ShowProfile";	
		appFormName.showProfile.value = "show";	
		appFormName.AddOrEdit.value = AddOrEdit;	
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function cancelProfile(AddOrEdit){
//alert(AddOrEdit);
		appFormName.action.value = "ShowProfile";	
		appFormName.handleForm.value = "Y";
		appFormName.showProfile.value = "cancelFromProf";	
		appFormName.AddOrEdit.value = AddOrEdit;
		appFormName.shwAddFrm.value = AddOrEdit;
		appFormName.submit();   
}
function gotoEditForm(userNameEdit){
		appFormName.action.value="ShowUserDetail";
		appFormName.shwAddFrm.value = "edit";
		appFormName.userNameEdit.value = userNameEdit;
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function updateUserDetail(){
		appFormName.action.value="UpdateUsrDetail";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function deleteUsrDetail(){
	if(validateDelete()){
		appFormName.action.value="DeleteUsrDetail";
		appFormName.shwAddFrm.value = "delete";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
	}
}
function searchBranchByDesc(){
		appFormName.action.value = "SearchBranch";	
		appFormName.showProfile.value = "show";	
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function searchExceptActByDesc(){
		appFormName.action.value = "SearchExceptAct";	
		appFormName.showProfile.value = "show";	
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function searchUserNameByDesc(){
		appFormName.action.value = "SearchUsername";	
		appFormName.showProfile.value = "show";	
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function addOrRemoveExcept(addOrRemove){
		appFormName.action.value = "AddRemoveUserExceptAct";	
		appFormName.showProfile.value = "show";	
//		appFormName.shwAddFrm.value = addOrEditFrm;	
		appFormName.addOrRemove.value = addOrRemove;	
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function addOrRemoveBranch(addOrRemove){
		appFormName.action.value = "AddRemoveUserBranch";	
		appFormName.showProfile.value = "show";	
//		appFormName.shwAddFrm.value = addOrEditFrm;	
		appFormName.addOrRemove.value = addOrRemove;	
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function addOrRemoveUserName(addOrRemove){
		appFormName.action.value = "AddRemoveUserName";	
		appFormName.showProfile.value = "show";	
//		appFormName.shwAddFrm.value = addOrEditFrm;	
		appFormName.addOrRemove.value = addOrRemove;	
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function saveProfile(AddOrEdit) {
		appFormName.action.value = "SaveProfile";
		appFormName.shwAddFrm.value = AddOrEdit;
		appFormName.handleForm.value = "Y";	
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
function checkFileFormat(){
  	var form=document.appFormName;
  	var vfileName=form.imagePath.value;
  	if(vfileName!=''){
   		var extension=vfileName.substring(vfileName.lastIndexOf('.')+1,vfileName.length);
   		if(extension!='' && !(extension.toLowerCase()=='png'||extension.toLowerCase()=='jpg' )){
      		alert("Extension of image file must be .jpg/.png only.");
      		form.reset();
      	}        
   	} 
}
</script>