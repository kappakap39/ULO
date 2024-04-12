<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.cache.data.CacheDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.search.PLSearchDataM" %>
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.FileInputStream"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<%
	PLSearchDataM searchReopenDataM = (PLSearchDataM) request.getSession().getAttribute("SEARCH_REOPEN_DATAM");
	if(searchReopenDataM == null){
		searchReopenDataM = new PLSearchDataM();
	}
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	String errMsg = (String) request.getSession(true).getAttribute(OrigConstant.ERR_MESSAGE_SESSION);
	if (errMsg != null && !"".equals(errMsg)) {
		out.println("<font class=TextWarningNormal>*&nbsp;"+errMsg+"</font>");
		request.getSession(true).removeAttribute(OrigConstant.ERR_MESSAGE_SESSION);
	}
	
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	
	Vector productVT = cacheUtil.loadCacheByActive("MainProduct");
	Vector priorityVT = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",61);
	String defaultProduct = searchReopenDataM.getProductCode();
	if(defaultProduct == null || "".equals(defaultProduct)){
		defaultProduct = OrigConstant.PRODUCT_KEC;
	}
	Vector saleTypeVT = cacheUtil.loadCacheByActive("MainSaleType");
%>
<input name="appRecordID" type="hidden" value="">
<input name="appStatus" type="hidden" value="">
<input name="jobState" type="hidden" value="">
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td colspan="2" class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="BigtodotextGreenLeft">Search Criteria</td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                                    	<input type="button" name="SearchBtn" value="<%=PLMessageResourceUtil.getTextDescription(request, "SEARCH") %>" onClick="searchApp()" class="button"></td>
                                    </tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
			 					<tr>
			 						<td class="textColS" width="16.5%"><%=PLMessageResourceUtil.getTextDescription(request, "APPLICATION_NO_TH") %> :</td>
			 						<td align="left" class="inputCol" width="16.7%"><%=HTMLRenderUtil.displayInputTagScriptAction(searchReopenDataM.getApplicationNo(), displayMode, "", "application_no", "textbox", "", "")%></td>
			 						<td class="textColS" width="16.7%"><%=PLMessageResourceUtil.getTextDescription(request, "REFERENCE_NO") %>:</td>
			 						<td align="left" class="inputCol" width="16.7%"><%=HTMLRenderUtil.displayInputTagScriptAction(searchReopenDataM.getRefNo(), displayMode, "", "reference_no", "textbox200", "", "30")%></td>
			 						<td class="textColS" width="16.7%"><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT_TH") %><font color="red">*</font>:</td>
			 						<td align="left" class="inputCol" width="16.7%"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(productVT,defaultProduct,"product",displayMode," onchange=\"javascript:getSaleType()\" style='width:165px'")%></td>
			 					</tr>
			 					<tr>
								 	<td class="textColS" ><%=PLMessageResourceUtil.getTextDescription(request, "FIRST_NAME_CUST_TH") %> :</td>
								 	<td align="left" class="inputCol" ><%=HTMLRenderUtil.displayInputTagScriptAction(searchReopenDataM.getFirstName(), displayMode, "", "th_firstname", "textbox", "", "")%></td>
								 	<td class="textColS" ><%=PLMessageResourceUtil.getTextDescription(request, "LAST_NAME_CUST_TH") %> :</td>
								 	<td align="left" class="inputCol" ><%=HTMLRenderUtil.displayInputTagScriptAction(searchReopenDataM.getLastName(), displayMode, "", "th_lastname", "textbox", "", "")%></td>								 	
								 	<td class="textColS" ><%=PLMessageResourceUtil.getTextDescription(request, "CITIZEN_ID_TH") %> :</td>
								 	<td align="left" class="inputCol" ><%=HTMLRenderUtil.displayInputTagScriptAction(searchReopenDataM.getCitizenID(), displayMode, "", "citizen_id", "textbox", "", "")%></td>			 				 	
								 </tr>
								 <tr>
								 	<td class="textColS"><%=PLMessageResourceUtil.getTextDescription(request, "TYPE_OF_SALE_TH") %> :</td>
								 	<td align="left" class="inputCol" id="div_saleType"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(saleTypeVT,searchReopenDataM.getSaleType(),"saleType",displayMode," style='width:165px' ")%></td>			 				 				 	
								 	<td class="textColS"><%=PLMessageResourceUtil.getTextDescription(request, "PRIORITY_TH") %> :</td>
								 	<td align="left" class="inputCol"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(priorityVT,searchReopenDataM.getPriority(),"priority",displayMode," style='width:165px' ")%></td>
								 	<td></td>
								 	<td></td>
								 </tr>
			 				</table>
						</td></tr>
					</table>
				</td></tr>
			</table>
		</td></tr>		
	</table>
	</TD></TR>
</table>
<input type="hidden" name="fromSearch" value="N">
<input type="hidden" name="itemsPerPage" value="">
<input type="hidden" name="atPage" value="">		
<table id="resultTable" height="100%" width="100%">
	<tr height="100%">
		<td height="90%" valign="top"><iframe src="orig/ca/search_reopen_result_screen.jsp" width="100%" height="90%" name="MainAppFrame" allowTransparency="true" style="background-color: #FFFFFF" frameborder="0"></iframe></td>
	</tr>
</table>
<script language="JavaScript">
//getSaleType();
resultTable.height = parseInt(window.screen.height) - 225;
function validate(){
	var application_no 	=  appFormName.application_no.value;
	var reference_no = appFormName.reference_no.value;
	var product = appFormName.product.value;
	var th_firstname = appFormName.th_firstname.value;
	var th_lastname = appFormName.th_lastname.value;
	var citizen_id 	= appFormName.citizen_id.value;
	var saleType = appFormName.saleType.value;
	var priority 	= appFormName.priority.value;
	if( (application_no=="") && (reference_no=="") && (product=="") && (th_firstname=="") && (th_lastname=="") && (citizen_id=="") && (saleType=="") && (priority=="")){
		alert("At Least One Criteria Must Be Selected.");
		return false;
	}else{
		return true;
	}
}
function searchApp(){
	if(validate()){
		//appFormName.formID.value="CA_FORM";
		//appFormName.currentTab.value="MAIN_TAB";
		appFormName.action.value="SearchReopen";
		appFormName.handleForm.value = "N";
		appFormName.fromSearch.value = "Y";
		appFormName.submit();	
	}
}
function getSaleType(){
	var product = appFormName.product.value;
	var dataString = "ClassPackage=com.eaf.orig.ulo.pl.product.ajax&ClassName=GetSaleTypeFromProduct&product="+product; 
	var uri = "AjaxServlet";
	jQuery.ajax( {
	type :"POST",
	url :uri,
	data :dataString,	
	async :false,
	success : function(data ,status ,xhr) {
		$("#div_saleType").html(data);
	},
	error : function(data){
		alert("error"+data);
	}
	});	

}
</script>
