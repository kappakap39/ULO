<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.master.shared.model.CarBlacklistM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>

<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />


<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	boolean shw = false;
	
	
	CarBlacklistM carBlacklistM =new CarBlacklistM(); 
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	ORIGUtility utility = new ORIGUtility();
	
	Vector carVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",9);
	String chassisNum="";
	String regisNum="";
	
	//Clear ValueList 
	if("add".equals(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///car_blacklist_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		carBlacklistM = (CarBlacklistM)request.getSession().getAttribute("ADD_CAR_BLACKLIST_DATAM");
		if(carBlacklistM == null){
			carBlacklistM = new CarBlacklistM();
		}
		VALUE_LIST.setResult(null);
	}
	//Chk to show edit data
	if("edit".equals(ORIGMasterForm.getShwAddFrm())){
		System.out.println("///car_blacklist_searching///ORIGMasterForm.getShwAddFrm() = " +ORIGMasterForm.getShwAddFrm());
		carBlacklistM = (CarBlacklistM)request.getSession().getAttribute("EDIT_CAR_BLACKLIST_DATAM");
		if(carBlacklistM == null){
			carBlacklistM = new CarBlacklistM();
		}
	}
	
	String brandDesc = cacheUtil.getORIGMasterDisplayNameDataM("CarBrand", carBlacklistM.getBrand());
	String modelDesc = cacheUtil.getORIGMasterDisplayNameDataMForCarModel("CarModel", carBlacklistM.getModel(), carBlacklistM.getBrand());
	String sourceDesc = cacheUtil.getNaosCacheDisplayNameDataM(23, carBlacklistM.getBlSource());
	String reasonDesc = cacheUtil.getNaosCacheDisplayNameDataM(22, carBlacklistM.getBlReason());
	
	//Chk to show search field
	chassisNum = (String)request.getSession().getAttribute("FIRST_SEARCH_chassisNum");
	regisNum = (String)request.getSession().getAttribute("FIRST_SEARCH_regisNum");
	if(chassisNum==null){
		chassisNum = "";
	}
	if(regisNum==null){
		regisNum = "";
	}
	
	System.out.println("check carBlacklistM =" + carBlacklistM );
	
	Vector ChkValueList = VALUE_LIST.getResult(); 
%>

<input type="hidden" name="shwAddFrm" value=""><%//  'Add' or 'Edit'%>	
<input type="hidden" name="blackVehEdit" value="">

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
				<TD class="textColS" width="10%"><%=MessageResourceUtil.getTextDescription(request, "CLASSIS_NO") %> :</TD>
				<TD class="inputCol" width="20%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(chassisNum,displayMode,"30","chassisNum","textbox","","20") %>
				</TD>
				<TD class="textColS" width="12%"><%=MessageResourceUtil.getTextDescription(request, "VEHICLE_REGIS_NO") %> :</TD>
	            <TD class="inputCol" width="20%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(regisNum,displayMode,"30","registrationNum","textbox","","20") %>
					</TD>
  				<TD class="inputCol"><input type="button"  class ="button_text" name="Search" value="<%=MessageResourceUtil.getTextDescription(request, "SEARCH") %>" onclick="searchApp()" ></TD>
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
					<input type="button"  class ="button_text" name="Add" value="<%=MessageResourceUtil.getTextDescription(request, "ADD") %>" onclick="submitShwAddForm(document.forms[0],'ShwPRQueTimeout','add')" >
					<%if(ChkValueList != null && ChkValueList.size() > 1){
					 %>
					<input type="button"  class ="button_text" name="Delete" value="<%=MessageResourceUtil.getTextDescription(request, "DELETE") %>" onclick="deleteCarBlacklist()" >
					<%}else{ %>
					<input type="button"  class ="button_text" name="Delete" value="<%=MessageResourceUtil.getTextDescription(request, "DELETE") %>" onclick="deleteCarBlacklist()" disabled="disabled">
					<%} %>				
				</td></tr>
				<tr><td class="TableHeader">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="Bigtodotext3" width="3%"></TD>
							<TD class="Bigtodotext3" width="10%">Selected</TD>
							<TD class="Bigtodotext3" width="25%"><%=MessageResourceUtil.getTextDescription(request, "CLASSIS_NO") %></TD> 													
							<TD class="Bigtodotext3" width="62%"><%=MessageResourceUtil.getTextDescription(request, "VEHICLE_REGIS_NO") %></TD>					    
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
							<td class="jobopening2" width="3%"></td>
							<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","carBlacklistChk",(String)elementList.elementAt(3),"") %></td>
							<td class="jobopening2" width="25%"><a href="javascript:gotoEditForm('<%=elementList.elementAt(3) %>')"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(1)) %></a></td>
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

<%
	String strOnclick = "";
	String label = "";
	String cancelBtn = "";	
	if(ORIGMasterForm.getShwAddFrm().equals("add")){
		label = "Add";		
		strOnclick = "saveCarBlacklist()";
		cancelBtn = "cancelAdd";
		shw = true;
	}else if(ORIGMasterForm.getShwAddFrm().equals("edit")){
		label = "Edit";
		strOnclick = "updateCarBlacklist()";
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
                                    	<input type="button"  class ="button" name="Save" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onclick="<%=strOnclick%>" >
									</td><td>
										<input type="reset"  class ="button" name="Cancel" value="<%=MessageResourceUtil.getTextDescription(request, "CANCEL") %>" onclick="cancelAddForm()" >			
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
					              <TD class="textColS" width="15%"><%=MessageResourceUtil.getTextDescription(request, "CLASSIS_NO") %><FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol" width="30%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(carBlacklistM.getChassisNum(),displayMode,"60","classNum","textbox","","20") %></TD>
					              <TD class="textColS" width="15%"><%=MessageResourceUtil.getTextDescription(request, "ENGINE_NO") %><FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol" width="40%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(carBlacklistM.getEngineNum(),displayMode,"60","engineNum","textbox","","50") %></TD>
					            </TR>
					            <TR> 
					              <TD class="textColS"><%=MessageResourceUtil.getTextDescription(request, "VEHICLE_REGIS_NO") %><FONT color = red>*</FONT> :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(carBlacklistM.getRegistrationNum(),displayMode,"60","regisNum","textbox","","20") %></TD>
					              <TD class="textColS"><%=MessageResourceUtil.getTextDescription(request, "BLAKCLIST_SOURCE") %> :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(carBlacklistM.getBlSource(),displayMode,"5","blckSource","46","blckSource_desc","textbox","","30","...","button_text","LoadBlackListSource",sourceDesc,"BlackListSource") %></TD>             
					            </TR>            
					            <TR> 
					              <TD class="textColS"><%=MessageResourceUtil.getTextDescription(request, "BLACKLIST_REASON") %> :</TD>
					              <TD class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(carBlacklistM.getBlReason(),displayMode,"5","blckReason","46","blckReason_desc","textbox","","30","...","button_text","LoadBlackListReason",reasonDesc,"BlackListReason") %></TD>
					            </TR> 
					            <TR> 
					              <TD class="textColS"><%=MessageResourceUtil.getTextDescription(request, "BRAND") %> :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(carBlacklistM.getBrand(),displayMode,"5","car_brand","46","car_brand_desc","textbox","","50","...","button_text","LoadCarBrand",brandDesc,"CarBrand") %></TD>
					              <TD class="textColS"><%=MessageResourceUtil.getTextDescription(request, "MODEL") %> :</TD>
					              <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(carBlacklistM.getModel(),displayMode,"5","car_model","46","car_model_desc","textbox","","50","...","button_text","LoadCarModel",new String[] {"car_model", "car_brand"},"",modelDesc,"CarModel") %></TD>                         
					            </TR>   
					            <TR>
					            	<TD class="textColS"><%=MessageResourceUtil.getTextDescription(request, "BLACKLIST_DESC") %> :</TD>
					              	<TD class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayInputTextAreaTag(ORIGDisplayFormatUtil.displayHTML(carBlacklistM.getBlNote()),"blckNote","5","50", displayMode, "") %></TD>
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
	var chassisNum 	= appFormName.chassisNum.value;
	var registrationNum = appFormName.registrationNum.value;
	
	if( chassisNum=="" && registrationNum=="" ){
		alert("At Least One Field Must Be Filled");
		return false;
	}else{
		return true;
	}
}
function validateDelete(){
	var objSeq = appFormName.carBlacklistChk;
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
		appFormName.action.value="SearchCarBlacklist";
		appFormName.shwAddFrm.value = "search";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
	//}
}						
function submitShwAddForm() {
		appFormName.action.value = "ShowCarBlacklist";
		appFormName.shwAddFrm.value = "add";
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}	
function cancelAddForm(){
		appFormName.action.value = "ShowCarBlacklist";		
		appFormName.shwAddFrm.value = "cancel";
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function saveCarBlacklist(){
		appFormName.action.value = "SaveCarBlacklist";
		//appFormName.shwAddFrm.value = "save";		
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function gotoEditForm(blackVehEdit){
		appFormName.action.value="ShowCarBlacklist";
		appFormName.shwAddFrm.value = "edit";
		appFormName.blackVehEdit.value = blackVehEdit;
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function updateCarBlacklist(){
		appFormName.action.value="UpdateCarBlacklist";
		//appFormName.shwAddFrm.value = "update";
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function deleteCarBlacklist(){
	if(validateDelete()){
		appFormName.action.value="DeleteCarBlacklist";
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
