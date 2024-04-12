<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.master.shared.model.ApprovAuthorM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>

<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<HTML>
<jsp:setProperty name="formHandlerManager" property="currentFormHandler"
	value="ORIGMasterForm" />
<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	Vector  vGroupName=null;
	if(session.getAttribute("APPRV_GROUP_NAME")!=null){
	  vGroupName=(Vector)session.getAttribute("APPRV_GROUP_NAME");
	}
	if(vGroupName==null){vGroupName=new Vector();}
	ORIGUtility utility = new ORIGUtility();
	Vector customerTypeVect = utility.loadCacheByName("CustomerType");
	Vector loanTypeVect = utility.loadCacheByName("LoanType");
	String apprvAction=(String)session.getAttribute("APPRV_ACTION"); 
	String groupName=(String)session.getAttribute("APPRV_SELECT_GROUP_NAME");
	String customerType=(String)session.getAttribute("APPRV_SELECT_CUSTOMER_TYPE");
	String loanType=(String)session.getAttribute("APPRV_SELECT_LOAN_TYPE");
   ORIGCacheUtil cacheUtil=ORIGCacheUtil.getInstance();
    
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
<HEAD>
<TITLE>Policy Approval Authority</TITLE>
</HEAD>
<BODY>
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail">Approval Authority Select </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                                    	<INPUT type="button" name="btnCreateNewCopy" value="Create"	class="button" onclick="createNewCopy()">
                                    </td><td>
                                    	<INPUT type="button" name="btnClose" value="Close" class="button" onclick="javascrip:window.close()">
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
			 						<td class="textColS"></TD>
									<TD class="textColS" width="120"><%=MessageResourceUtil.getTextDescription(request, "SELECT") %></TD>
									<TD width="276"></TD>									 
									<TD width="512"></TD>
								</TR>
								<TR>
									<TD></TD>
									<TD class="textColS"><%=MessageResourceUtil.getTextDescription(request, "GROUP_NAME") %></TD>
									<TD>	
										<SELECT name="groupName" class="comboBox">
										  <%for(int i=0;i<vGroupName.size();i++){
										    String strGroupName=(String)vGroupName.get(i);		    
										    if(strGroupName==null){strGroupName="";}
										    strGroupName.trim();
										    String selected="";
										    if(strGroupName.equals(groupName)){
										    selected=" selected ";
										    }		    
										   %>
											<OPTION value="<%=strGroupName%>" <%=selected%> ><%=strGroupName%></OPTION>
										  <%} %>
										</SELECT>		
									</TD>								 
									<TD><INPUT type="button" name="btnDeleteGroup" value="Delete Group" class="button_text" onclick="deleteGroup()" ></TD>
								</TR>
								<TR>
									<TD></TD>
									<TD class="textColS"><%=MessageResourceUtil.getTextDescription(request, "LOAN_TYPE") %></TD>
									<TD class="textColS"><%=ORIGDisplayFormatUtil.displaySelectTagScriptActionAndCode_ORIG(loanTypeVect,loanType ,"loanType",displayMode," style=\"width:80%;\" ") %></TD>									 
									<TD></TD>
								</TR>
								<TR>
									<TD></TD>
									<TD class="textColS"nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_TYPE") %></TD>
									<TD class="textColS"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(customerTypeVect,customerType,"customerType",displayMode," style=\"width:80%;\" ")%></TD>
									<TD><INPUT type="button" name="btnLoad" value="Load" class="button_text" onclick="loadApprovalDetail()"></TD>							 
								</TR>
							</table>
						</td></tr>
						<tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
			 						<td class="textColS" width="66">&nbsp;</TD>
									<TD width="160"><INPUT type="text" name="createGroup" size="2" class="textbox" value="" maxlength="2"></TD>
									<TD width="749"><INPUT type="button" name="btnCreateGroup" value="Create Group" class="button_text" onclick="javaScript:createAuthorDetailGroup()" ></TD>
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
<br>						
 <% if("load".equals(apprvAction)||"edit".equals(apprvAction)){ 
      String descCustomerType=cacheUtil.getORIGMasterDisplayNameDataM("CustomerType",customerType);
      String descLoanType=cacheUtil.getORIGMasterDisplayNameDataM("LoanType",loanType);
       Vector  vSelectApprvGroup=(Vector)session.getAttribute("SELECT_APPRV_GROUP");  
     if(vSelectApprvGroup==null){vSelectApprvGroup=new Vector();}
     int selectIndex=-1;
     if("edit".equals(apprvAction)){
        String selectItem=(String)session.getAttribute("SELECT_ITEM");
        selectIndex=utility.stringToInt(selectItem);
     }
    
  %>
<table cellpadding="0" cellspacing="0" width="100%" align="center">
	<tr><td>
		<div id="KLTable">
			<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
				<tr><td class="TableHeader">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="Bigtodotext3" width="15%"><%=MessageResourceUtil.getTextDescription(request, "GROUP_NAME") %></TD>
							<TD class="Bigtodotext3" width="30%"><%=MessageResourceUtil.getTextDescription(request, "LOAN_TYPE") %></TD>
							<TD class="Bigtodotext3" width="25%"><%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_TYPE") %></TD>
							<!--Comment for TMB POC
							<TD background="tableH.gif">Car Type</TD>-->
							<TD class="Bigtodotext3" width="15%"><%=MessageResourceUtil.getTextDescription(request, "PROPERTY_TYPE") %></TD>
							<TD class="Bigtodotext3" width="15%"><%=MessageResourceUtil.getTextDescription(request, "SCORING_RESULT") %></TD>
						</tr>
					</table> 
				</td></tr>
				<% for(int i=0;i<vSelectApprvGroup.size();i++){
				  ApprovAuthorM  approvAuthorM=(ApprovAuthorM)vSelectApprvGroup.get(i);
				  String bgColor="#e9edf6";
				  if(i==selectIndex){
				    bgColor="#CCFFCC";
				  }
				 %>
				 <tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="jobopening2" width="15%"><a href="#" onclick="javascript:editData(<%=i%>)"><%=approvAuthorM.getGroupName()%></a></TD>
						    <TD class="jobopening2" width="30%"><%=descLoanType%></TD>
							<TD class="jobopening2" width="25%"><%=descCustomerType%></TD>
							<TD class="jobopening2" width="15%"><%=approvAuthorM.getCarType()%></TD>
							<TD class="jobopening2" width="15%"><%=approvAuthorM.getScoringResult()%></TD>
						</tr>
					</table> 
				</td></tr>
						
				<%} %>
			</TABLE>
		</div>
	</TD></TR>
</TABLE>
<br>		
		
		<%}%>	
		<% if("edit".equals(apprvAction)){ 		
		 ApprovAuthorM selectApprv=(ApprovAuthorM)session.getAttribute("SELECT_APPRV_DETAIL");
		 String descCustomerType=cacheUtil.getORIGMasterDisplayNameDataM("CustomerType",customerType);
         String descLoanType=cacheUtil.getORIGMasterDisplayNameDataM("LoanType",loanType);
		
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
                          	<tr><td class="text-header-detail">Edit Data</td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                                    	<% if("edit".equals(apprvAction)){%><INPUT type="button" name="btnSave" value="Update" class="button" onClick="saveApprovalDetail()"><%}%>
                                    </td><td>
                                    	<INPUT type="button" name="btnClose" value="Close" class="button" onclick="javascrip:window.close()">
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
									<TD class="textColS" width="183"><%=MessageResourceUtil.getTextDescription(request, "GROUP_NAME") %></TD>
									<TD class="inputCol" width="227"><%=selectApprv.getGroupName() %></TD>
									<TD class="textColS" width="192"><%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_TYPE") %></TD>
									<TD class="inputCol" width="374"><%=descCustomerType%></TD>
								</TR>
								<TR>
									<TD class="textColS"><%=MessageResourceUtil.getTextDescription(request, "LOAN_TYPE") %></TD>
									<TD class="inputCol"><%=descLoanType%></TD>
									<TD>&nbsp;</TD>
									<TD></TD>
								</TR>
								<TR>
									<!--Comment for TMB POC
									<TD width="183">&nbsp;<b>CarType</b></TD>-->
									<TD class="textColS"><%=MessageResourceUtil.getTextDescription(request, "PROPERTY_TYPE") %></TD>
									<TD class="inputCol"><%=selectApprv.getCarType() %></TD>
									<TD class="textColS"><%=MessageResourceUtil.getTextDescription(request, "SCORING_RESULT") %></TD>
									<TD class="inputCol"><%=selectApprv.getScoringResult()%></TD>
								</TR>
							</TABLE>
						</td></tr>
						
						<tr class="sidebar10"> <td align="center">
						<FIELDSET><LEGEND class="textColS"><b>Pass Policy</b></LEGEND>
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD class="textColS" width="321">Credit Approval <FONT color="red">*</FONT>&nbsp;:</TD>
									<TD class="textColS" width="214"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(selectApprv.getCreditApproval()),displayMode,"20","credit_apprv_pass","textboxCurrency",
										"onblur=\"javascript:addComma(this);addDecimalFormat(this)\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"") %></TD>
									<TD width="291">&nbsp;</TD>
									<TD width="145">&nbsp;</TD>
								</TR>
								<TR>
									<TD class="textColS">Minimum Loan Amount -Have Guarantor <FONT color="red">*</FONT>&nbsp;:</TD>
									<TD class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(selectApprv.getMinDownGua()),displayMode,"20","minimum_down_g_pass","textboxCurrency",
										"onblur=\"javascript:addComma(this);addDecimalFormat(this)\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"") %></TD>
									<TD>&nbsp;</TD>
									<TD>&nbsp;</TD>
								</TR>
								<TR>
									<TD class="textColS">Minimum Term -Have Guarantor <FONT color="red">*</FONT>&nbsp;:</TD>
									<TD class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumberInterger(selectApprv.getMinTermGua()),displayMode,"20","minimum_term_g_pass","textboxCurrency",
										"onBlur=\"javascript:returnZero(this)\" onKeyPress=\"javaScript:keyPressInteger()\"") %></TD>
									<TD class="textColS">Maximum Term -Have Guarantor <FONT color="red">*</FONT>&nbsp;:</TD>
									<TD class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumberInterger(selectApprv.getMaxTermGua()),displayMode,"20","maximum_term_g_pass","textboxCurrency",
										"onBlur=\"javascript:returnZero(this)\" onKeyPress=\"javaScript:keyPressInteger()\"") %></TD>
								</TR>
								<TR>
									<TD>&nbsp;</TD>
									<TD>&nbsp;</TD>
									<TD>&nbsp;</TD>
									<TD>&nbsp;</TD>
								</TR>
							    <TR>
									<TD class="textColS">Minimum Loan Amount -No Guarantor <FONT	color="red">*</FONT>&nbsp;:</TD>
									<TD class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(selectApprv.getMinDownNoGua()),displayMode,"20","minimum_down_ng_pass","textboxCurrency",
										"onblur=\"javascript:addComma(this);addDecimalFormat(this)\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"") %></TD>
									<TD>&nbsp;</TD>
									<TD>&nbsp;</TD>
								</TR>
								<TR>
									<TD class="textColS">Minimum Term -No Guarantor <FONT color="red">*</FONT>&nbsp;:</TD>
									<TD class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumberInterger(selectApprv.getMinTermNoGua()),displayMode,"20","minimum_term_ng_pass","textboxCurrency",
										"onBlur=\"javascript:returnZero(this)\" onKeyPress=\"javaScript:keyPressInteger()\"") %></TD>
									<TD class="textColS">Maximum Term -No Guarantor <FONT color="red">*</FONT>&nbsp;:</TD>
									<TD class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumberInterger(selectApprv.getMaxTermNoGua()),displayMode,"20","maximum_term_ng_pass","textboxCurrency",
										"onBlur=\"javascript:returnZero(this)\" onKeyPress=\"javaScript:keyPressInteger()\"") %></TD>
								</TR>
							</TABLE>
						</FIELDSET>	
						</td></tr>
						
						<tr class="sidebar10"> <td align="center">
						<FIELDSET><LEGEND class="textColS"><b>Fail Policy</b></LEGEND>
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD class="textColS" width="321">Credit Approval <FONT color="red">*</FONT>&nbsp;:</TD>
							<TD class="textColS" width="214"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(selectApprv.getFailPolicyCreditApproval()),displayMode,"20","credit_apprv_fail","textboxCurrency",
								"onblur=\"javascript:addComma(this);addDecimalFormat(this)\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"") %></TD>
							<TD width="291">&nbsp;</TD>
							<TD width="145">&nbsp;</TD>
						</TR>
						<TR>
							<TD class="textColS">Minimum Loan Amount -Have Guarantor <FONT color="red">*</FONT>&nbsp;:</TD>
							<TD class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(selectApprv.getFailPolicyMinDownGua()),displayMode,"20","minimum_down_g_fail","textboxCurrency",
								"onblur=\"javascript:addComma(this);addDecimalFormat(this)\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"") %></TD>
							<TD>&nbsp;</TD>
							<TD>&nbsp;</TD>
						</TR>
						<TR>
							<TD class="textColS">Minimum Term -Have Guarantor <FONT color="red">*</FONT>&nbsp;:</TD>
							<TD class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumberInterger(selectApprv.getFailPolicyMinTermGua()),displayMode,"20","minimum_term_g_fail","textboxCurrency",
								"onBlur=\"javascript:returnZero(this)\" onKeyPress=\"javaScript:keyPressInteger()\"") %></TD>
							<TD class="textColS">Maximum Term -Have Guarantor <FONT color="red">*</FONT>&nbsp;:</TD>
							<TD class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumberInterger(selectApprv.getFailPolicyMaxTermGua()),displayMode,"20","maximum_term_g_fail","textboxCurrency",
								"onBlur=\"javascript:returnZero(this)\" onKeyPress=\"javaScript:keyPressInteger()\"") %></TD>
						</TR>
						<TR>
							<TD>&nbsp;</TD>
							<TD>&nbsp;</TD>
							<TD>&nbsp;</TD>
							<TD>&nbsp;</TD>
						</TR>
					    <TR>
							<TD class="textColS">Minimum Loan Amount -No Guarantor<FONT	color="red">*</FONT>&nbsp;:</TD>
							<TD class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(selectApprv.getFailPolicyMinDownNoGua()),displayMode,"20","minimum_down_ng_fail","textboxCurrency",
								"onblur=\"javascript:addComma(this);addDecimalFormat(this)\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"") %></font></TD>
							<TD>&nbsp;</TD>
							<TD>&nbsp;</TD>
						</TR>
						<TR>
							<TD class="textColS">Minimum Term -No Guarantor <FONT color="red">*</FONT>&nbsp;:</TD>
							<TD class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumberInterger(selectApprv.getFailPolicyMinTermNoGua()),displayMode,"20","minimum_term_ng_fail","textboxCurrency",
								"onBlur=\"javascript:returnZero(this)\" onKeyPress=\"javaScript:keyPressInteger()\"") %></font></TD>
							<TD class="textColS">Maximum Term -No Guarantor <FONT color="red">*</FONT>&nbsp;:</TD>
							<TD class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumberInterger(selectApprv.getFailPolicyMaxTermNoGua()),displayMode,"20","maximum_term_ng_fail","textboxCurrency",
								"onBlur=\"javascript:returnZero(this)\" onKeyPress=\"javaScript:keyPressInteger()\"") %></TD>
						</TR>
						</TABLE>
						</FIELDSET>
						</td></tr>
					</table>
				</td></tr>
			</table>
		</td></tr>		
	</table>
	</TD></TR>
</TABLE>	
		
     <%} %>	

<INPUT type="hidden" name="apprvAction" value=""><%//  'Add' or 'Edit'%>
<INPUT type="hidden" name="selectItem" value="">
 <SCRIPT language="JavaScript">	 
function loadApprovalDetail(){
   var groupName=  appFormName.groupName.value;
   var custType=appFormName.customerType.value;
   var loanType=appFormName.loanType.value;
      if(groupName==""){alert('Please Select Group Name'); return}
      if(loanType==""){alert('Please Select Loan Type');return}
      if(custType==""){alert('Please Select Customer Type');return}
		appFormName.action.value="LoadApprovAuthorPolicyDetail";
		appFormName.apprvAction.value = "load";
		appFormName.handleForm.value = "N";
		appFormName.submit();	 
}								
function editData(itemIndex){
        appFormName.action.value="LoadApprovAuthorPolicyDetail";
		appFormName.apprvAction.value = "edit";
		appFormName.handleForm.value = "N";
		appFormName.selectItem.value=itemIndex;
		appFormName.submit();
} 

function createAuthorDetailGroup(){
        //alert('create'); 
        if(appFormName.createGroup.value==''){
        alert("Group is required");
        return;
       }
        appFormName.action.value="LoadApprovAuthorPolicyDetail";
		appFormName.apprvAction.value = "new";
		appFormName.handleForm.value = "N";
		// appFormName.selectItem.value=itemIndex;
		appFormName.submit();
} 
function deleteGroup(){
        appFormName.action.value="LoadApprovAuthorPolicyDetail";
		appFormName.apprvAction.value = "delete";
		appFormName.handleForm.value = "N";
		// appFormName.selectItem.value=itemIndex;
		 var groupName=  appFormName.groupName.value;
		if(groupName==''){
		alert('Please select Group Name');
		return;
		}
		if(confirm("Delete "+ groupName+" Group ?")){
		appFormName.submit();
		}
} 
function saveApprovalDetail(){
        appFormName.action.value="UpdateApprovAuthorPolicyDetail";
		appFormName.apprvAction.value = "save";
		appFormName.handleForm.value = "N";
		// appFormName.selectItem.value=index;
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
</SCRIPT>
<DIV></DIV>
</DIV>
</BODY>
</HTML>
