<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.cache.data.CacheDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.master.shared.model.MandatoryM" %>

<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />


<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	boolean shw = false;
	
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	
	Vector flagVect = new Vector();
	CacheDataM cacheDataM = new CacheDataM();
	cacheDataM.setCode("Y");
	cacheDataM.setShortDesc("Yes");
	flagVect.add(cacheDataM);
	cacheDataM = new CacheDataM();
	cacheDataM.setCode("N");
	cacheDataM.setShortDesc("No");
	flagVect.add(cacheDataM);
	
	ORIGUtility utility = new ORIGUtility();
	Vector customerTypeVect = utility.loadCacheByName("CustomerType");
	
	MandatoryM mandatoryM =new MandatoryM(); 
	String formNameId="";
	String fieldName="";
	
	//Chk to show edit data
	if("edit".equals(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///mandfield_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		mandatoryM = (MandatoryM)request.getSession().getAttribute("EDIT_MandatoryM");
		if(mandatoryM == null){
			mandatoryM = new MandatoryM();
		}
	}
	//Chk to show search field
	formNameId = (String)request.getSession().getAttribute("FIRST_SEARCH_formNameId");
	fieldName = (String)request.getSession().getAttribute("FIRST_SEARCH_fieldName");
	if(formNameId==null){
		formNameId = "";
	}
	if(fieldName==null){
		fieldName = "";
	}
	
	System.out.println("check mandatoryM =" + mandatoryM );
%>

<input type="hidden" name="shwAddFrm" value=""><%//  'Add' or 'Edit'%>	
<input type="hidden" name="frmNameIDEdit" value="">
<input type="hidden" name="frmNameEdit" value="">
<input type="hidden" name="cusTypeEdit" value="">

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
<!--  For Search: Filter   -->
<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td class="sidebar8"><table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
		<tr class="sidebar10"> <td align="center">
		<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
			<tr>
				<TD class="textColS" width="15%">Form Name ID&nbsp;:</TD>
				<TD class="inputCol" width="18%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(formNameId,displayMode,"35","formNameId","textbox","","100") %></TD>
				<TD class="textColS" width="12%">Field Name&nbsp;:</TD>
				<TD class="inputCol" width="18%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(fieldName,displayMode,"50","fieldName","textbox","","50") %></TD>
				<TD class="inputCol" width="37%"><input type="button"  class ="button_text" name="Search" value="Search" onclick="searchApp()" ></TD>
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
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="Bigtodotext3" width="25%" align="left">Form Name ID</TD>																	
						  	<TD class="Bigtodotext3" width="25%" align="left">Field Name</TD>	
							<TD class="Bigtodotext3" width="5%" align="left">CMR</TD>													
						  	<TD class="Bigtodotext3" width="5%" align="left">DE</TD>
							<TD class="Bigtodotext3" width="5%" align="left">UW</TD>
							<TD class="Bigtodotext3" width="5%" align="left">PD</TD>
							<TD class="Bigtodotext3" width="5%" align="left">XCMR</TD>
							<TD class="Bigtodotext3" width="25%" align="left">Customer Type</TD>
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
							<td class="jobopening2" width="25%"><a href="javascript:gotoEditForm('<%=elementList.elementAt(1) %>','<%=elementList.elementAt(2) %>','<%=elementList.elementAt(8) %>')"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(1)) %></a></td>
							<td class="jobopening2" width="25%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(2)) %></td>
							<td class="jobopening2" width="5%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(3)) %></td>
							<td class="jobopening2" width="5%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(4)) %></td>
							<td class="jobopening2" width="5%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(5)) %></td>
							<td class="jobopening2" width="5%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(6)) %></td>
							<td class="jobopening2" width="5%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(7)) %></td>
							<td class="jobopening2" width="25%"><%=ORIGDisplayFormatUtil.displayHTML(cacheUtil.getORIGMasterDisplayNameDataM("CustomerType", (String)elementList.elementAt(8))) %></td>
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
	String label = "";
	
	if("edit".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		label = "Edit";
		strOnclick = "updateMandField()";
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
                                    	<input type="button"  class ="button" name="Save" value="Save" onClick="<%=strOnclick%>" >
									</td><td>
										<input type="reset"  class ="button" name="Cancel" value="Cancel" onclick="cancelAddForm()"></TD>		
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
					              <TD class="textColS" width="16%">Form Name ID<FONT color = red>&nbsp;</FONT> :</TD>
					              <TD class="inputCol" width="25%"><%=mandatoryM.getFormNameId()%> <input type="hidden" name="frmNameID" value="<%=mandatoryM.getFormNameId()%>"></TD>
					              <TD class="textColS" width="11%">Field Name<FONT color = red>&nbsp;</FONT> :</TD>
					              <TD class="inputCol"><%=mandatoryM.getFieldName()%>
					                <input type="hidden" name="fldName" value="<%=mandatoryM.getFieldName()%>">	</TD>
					              <TD class="inputCol"></TD>
					            </TR>
					            <TR> 
					              <TD class="textColS">CMR&nbsp;<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(flagVect,mandatoryM.getCmrFlag(),"cmrflag",displayMode,"")%></TD>
					              <TD class="textColS">UW&nbsp;<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(flagVect,mandatoryM.getUwFlag(),"uwflag",displayMode,"")%></TD>
					              <TD class="inputCol"></TD>
					           </TR>
					            <TR> 
					              <TD class="textColS">DE&nbsp;<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(flagVect,mandatoryM.getDeFlag(),"deflag",displayMode,"")%></TD>
								  <TD class="textColS">XCMR&nbsp;<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(flagVect,mandatoryM.getXcmrFlag(),"xcmrflag",displayMode,"")%></TD>
					              <TD class="inputCol"></TD>
					           </TR>
							   <TR> 
					              <TD class="textColS">PD&nbsp;<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(flagVect,mandatoryM.getPdFlag(),"pdflag",displayMode,"")%></TD>
								  <TD class="textColS">Customer Type&nbsp;<FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(customerTypeVect,mandatoryM.getCusType(),"customerType",displayMode,"")%></TD>
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
function validate(){
	var formNameId 	= appFormName.formNameId.value;
	var fieldName = appFormName.fieldName.value;
	
	if( formNameId=="" && fieldName=="" ){
		alert("At Least One Field Must Be Filled");
		return false;
	}else{
		return true;
	}
}
function searchApp(){
	//if( validate() ){
		appFormName.action.value="SearchMandField";
		appFormName.shwAddFrm.value = "search";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
	//}
}
function cancelAddForm(){
		appFormName.action.value = "ShowMandField";	
		appFormName.shwAddFrm.value = "cancel";
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function gotoEditForm(frmNameIDEdit, frmNameEdit, cusTypeEdit){
		appFormName.action.value="ShowMandField";
		appFormName.shwAddFrm.value = "edit";
		appFormName.frmNameIDEdit.value = frmNameIDEdit;
		appFormName.frmNameEdit.value = frmNameEdit;
		appFormName.cusTypeEdit.value = cusTypeEdit;
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function updateMandField(){
		appFormName.action.value="UpdateMandField";
//		appFormName.shwAddFrm.value = "update";
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




