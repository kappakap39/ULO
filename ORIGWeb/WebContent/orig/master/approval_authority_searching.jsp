<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.master.shared.model.ApprovAuthorM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>

<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />
<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	String versionSearch=(String)session.getAttribute("VERSION_SEARCH");
	if(versionSearch==null){ versionSearch="";}
	session.removeAttribute("VERSION_SEARCH");
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
<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td class="sidebar8"><table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
		<tr class="sidebar10"> <td align="center">
		<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
			<tr>
				<TD class="textColS" width="78">Version:</td>
			    <td class="inputCol" width="159"> <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(versionSearch,displayMode,"30","versionSearch","textbox","","20") %>
			      </td>
			    <td class="textColS" width="396"><input type="button" name="search" value="Search" class="button_text"  onClick="searchApp()" /></td>
			  </tr>
		</TABLE>
		</td></tr>
	</table></td></tr>
</table>
<br>

<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td>
		<div id="KLTable">
			<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
				<tr><td class="TableHeader">
					<input type="button" name="search" value="Add" class="button_text"  onClick="addNewVersion()" />
					<input type="button" name="search" value="Delete" class="button_text" onClick="deleteApprovAuthor()"  />				
				</TD></TR>
				<tr><td class="TableHeader">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="Bigtodotext3" width="9%">Select</td>
						    <td class="Bigtodotext3" width="21%"> Version</td>
						    <td class="Bigtodotext3" width="18%">Description</td>
						    <td class="Bigtodotext3" width="22%">Effective Date</td>
						    <td class="Bigtodotext3" width="30%"> Expire Date</td>
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
							<td class="jobopening2" width="9%"><%=ORIGDisplayFormatUtil.displayCheckBox("","approvAuthorChk",(String)elementList.elementAt(1),"" ) %></td>
						    <td class="jobopening2" width="21%"><a href="javascript:loadApprvForm('<%=elementList.elementAt(1) %>' )">
						    <%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(1)) %></a></td>
						    <td class="jobopening2" width="18%"><%=ORIGDisplayFormatUtil.displayHTML( (String)elementList.elementAt(4)) %></td>
						    <td class="jobopening2" width="22%"><%=ORIGDisplayFormatUtil.displayHTML( (String)elementList.elementAt(2)) %></td>
						    <td class="jobopening2" width="30%"><%=ORIGDisplayFormatUtil.displayHTML( (String)elementList.elementAt(3)) %></td>
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
		  <%} %>
  
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
 

 
<input type="hidden" name="displayType" value=""><%//  'Add' or 'Edit'%>	
<input type="hidden" name="policyVersion" value="">
<input type="hidden" name="queryApp" value="Y">
<script language="JavaScript">	
function validate(){
	var groupName 	= appFormName.groupName.value;
	var loanType = appFormName.loanType.value;
	var customerType = appFormName.customerType.value;
	
	if( groupName=="" && loanType=="" && customerType=="" ){
		alert("At Least One Field Must Be Filled");
		return false;
	}else{
		return true;
	}
}
function validateDelete(){
	var objSeq = appFormName.approvAuthorChk;
	if (isObject(objSeq) && !isUndefined(objSeq.length)) {
		if (objSeq.length > 0) {
			var isValid = false;
			for (var i = 0; i < objSeq.length ; i++) {
				if (objSeq[i].checked) {
				// alert("objSeq "+i+" "+objSeq[i].value);
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
		appFormName.action.value="SearchApprovAuthor";
		//appFormName.shwAddFrm.value = "search";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
	//}
}								
function submitShwAddForm() {
		appFormName.action.value = "ShowApprovAuthor";
		//appFormName.shwAddFrm.value = "add";
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}	
function cancelAddForm(){
		appFormName.action.value = "ShowApprovAuthor";		
		//appFormName.shwAddFrm.value = "cancel";
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function saveApprovAuthor(){
		appFormName.action.value = "SaveApprovAuthor";
		//appFormName.shwAddFrm.value = "save";		
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
/*function gotoEditForm(grpNm, lnTyp, cusTyp){
		appFormName.action.value="ShowApprovAuthor";
		appFormName.shwAddFrm.value = "edit";
		appFormName.grpNm.value = grpNm;
		appFormName.lnTyp.value = lnTyp;
		appFormName.cusTyp.value = cusTyp;
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}*/
function loadApprvForm( policyVersion){
  	    appFormName.action.value="LoadApprovAuthor";
		appFormName.displayType.value = "edit";
		appFormName.policyVersion.value = policyVersion;
		//appFormName.lnTyp.value = lnTyp;
		//appFormName.cusTyp.value = cusTyp;
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function addNewVersion(){
        appFormName.action.value="LoadApprovAuthor";
		appFormName.displayType.value = "new";
		appFormName.policyVersion.value = "";
		//appFormName.lnTyp.value = lnTyp;
		//appFormName.cusTyp.value = cusTyp;
		appFormName.handleForm.value = "N";
		appFormName.submit();	
}

function updateApprovAuthor(){
		appFormName.action.value="UpdateApprovAuthor";
		//appFormName.shwAddFrm.value = "update";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function deleteApprovAuthor(){
	if(validateDelete()){
		appFormName.action.value="DeleteAppAuthor";
		//appFormName.shwAddFrm.value = "delete";
		appFormName.handleForm.value = "N";
		
	   var objSeq = appFormName.approvAuthorChk;
	   var deleteVersion="";
	   if (isObject(objSeq) && !isUndefined(objSeq.length)) {
		if (objSeq.length > 0) {			 
			for (var i = 0; i < objSeq.length ; i++) {
				if (objSeq[i].checked) {
				// alert("objSeq "+i+" "+objSeq[i].value);
					deleteVersion=deleteVersion+" "+objSeq[i].value ;
				}
			}			 
		  }		
	   }
		if(confirm("Delete Version "+deleteVersion+" ?")){
		appFormName.submit();
		}
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
<div>
