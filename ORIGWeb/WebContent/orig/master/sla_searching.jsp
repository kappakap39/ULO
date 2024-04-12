<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.model.SLADataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>

<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />

<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	boolean shw = false;
	
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	ORIGUtility utility = new ORIGUtility();
	Vector slaQueueTmp = utility.loadCacheByName("SLA");
	Vector slaQueue = new Vector();
	SLADataM dataM = new SLADataM();
	boolean isMatch = false;
	for(int i=0; i<slaQueueTmp.size(); i++){
		dataM = (SLADataM) slaQueueTmp.get(i);
		SLADataM dataM2 = new SLADataM();
		for(int j=0; j<slaQueue.size(); j++){
			dataM2 = (SLADataM) slaQueue.get(j);
			if(dataM2.getQName().equals(dataM.getQName())){
				isMatch = true;
				break;
			}
		}
		if(!isMatch){
			slaQueue.add(dataM);
		}
		isMatch = false;
	}
	
	String slaSearch = (String) request.getSession(true).getAttribute("FIRST_SEARCH_slaQueue");
	
	
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

<!--  For Search: Filter   -->
<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td class="sidebar8"><table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
		<tr class="sidebar10"> <td align="center">
		<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
			<tr>
				<TD class="textColS" width="12%">SLA Queue Name :</TD>
				<TD class="inputCol" width="20%"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_SLA(slaQueue,slaSearch ,"slaQueue",displayMode," ") %></TD>
				<TD class="inputCol"><input type="button"  class ="button_text" name="Search" value="Search" onclick="searchApp()" ></TD>
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
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="Bigtodotext3" width="20%">Queue Name</TD>
							<TD class="Bigtodotext3" width="20%">Application Status</TD> 													
							<TD class="Bigtodotext3" width="20%">Application Action</TD>					    
							<TD class="Bigtodotext3" width="15%">Job State</TD>
							<TD class="Bigtodotext3" width="15%">Role</TD>
							<TD class="Bigtodotext3" width="10%">Time(Hr)</TD>
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
							<td class="jobopening2" width="20%"><a href="javascript:editApp('<%=elementList.elementAt(1) %>','<%=elementList.elementAt(5) %>')"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(1)) %></a></td>
							<td class="jobopening2" width="20%" align="center"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(2)) %></td>
							<td class="jobopening2" width="20%" align="center"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(3)) %></td>
							<td class="jobopening2" width="15%" align="center"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(4)) %></td>
							<td class="jobopening2" width="15%" align="center"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(5)) %></td>
							<td class="jobopening2" width="10%" align="center"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(6)) %></td>
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
	if(ORIGMasterForm.getShwAddFrm().equals("edit")){
		shw = true;		
	}
	
	if(shw){  //===> if shw 
		dataM = ORIGMasterForm.getSlaDataM();
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
                                    	<input type="button"  class ="button" name="Save" value="Save" onclick="saveApp()" >
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
					              <TD class="textColS" width="20%">SLA Queue Name :</TD>
					              <TD class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(dataM.getQName(),displayMode,"60","qname","textboxReadOnly","readOnly","20") %></TD>
					              <TD class="textColS">Role:</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(dataM.getRole(),displayMode,"60","appRole","textboxReadOnly","readOnly","50") %></TD>
					            </TR>
					            <TR> 
					              <TD class="textColS">Application Status  :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(dataM.getAppStatus(),displayMode,"60","status","textboxReadOnly","readOnly","20") %></TD>
					              <TD class="textColS">Application Action :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(dataM.getAction(),displayMode,"60","appAction","textboxReadOnly","readOnly","50") %></TD>
					            </TR>
					            <TR> 
					              <TD class="textColS">Job State :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(dataM.getJobState(),displayMode,"60","jobState","textboxReadOnly","readOnly","20") %></TD>
					              <TD class="textColS">Time(Hr) :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(dataM.getTime()),displayMode,"60","time","textbox",
					              "onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" ","50") %></TD>
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
<input type="hidden" name="qname">
<input type="hidden" name="role">
<script language="JavaScript">	
function validate(){
	var slaQueue = appFormName.slaQueue.value;
	
	if( slaQueue==""){
		alert("Please select SLA Queue Nmae.");
		return false;
	}else{
		return true;
	}
}
function searchApp(){
	//if( validate() ){
		appFormName.action.value="SearchSLA";
		appFormName.shwAddFrm.value = "search";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
	//}
}									
function cancelAddForm(){
		appFormName.action.value = "CancelSLA";		
		appFormName.shwAddFrm.value = "cancel";
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function saveApp(){
		appFormName.action.value = "SaveSLA";
		//appFormName.shwAddFrm.value = "save";		
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function editApp(qname, role){
		appFormName.action.value="EditSLA";
		appFormName.shwAddFrm.value = "edit";
		appFormName.qname.value = qname;
		appFormName.role.value = role;
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
</script>	
