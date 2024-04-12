<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.cache.TableLookupCache" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.utility.TooltipResourceUtil" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<%
	System.out.println("+++++++++++ mail in screen +++++++++++++++");
    TooltipResourceUtil tooltipUtil=TooltipResourceUtil.getInstance();
	String searchType = "";
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	ORIGUtility utility = new ORIGUtility();
	Vector organizationVect = utility.loadCacheByName("Organization");
	Vector customerTypeVect = utility.loadCacheByName("CustomerType");
	HashMap hash=TableLookupCache.getCacheStructure();
%>
<%
	Vector userRoles = ORIGUser.getRoles();
	String role = (String) userRoles.get(0);
%>
		
<TABLE cellSpacing=0 cellPadding=0 width="50%" align=center  border="0">	
	<tr><td>
		<input type="hidden" name="fromMultiMailIn" value ="Y">
	</td></tr>	<tr><td>
	<tr> 
		<td align="right" class="textColS" colspan="2" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_TYPE") %>&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;</td>
		<td class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(organizationVect,"","orgType",displayMode," style=\"width:80%;\" onChange=\"javascript:getProduct()\"")%></td>
	</tr>
	<TR> 
		<TD align="right" class="textColS" colspan="2" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "LOAN_TYPE") %><font color="#FF0000">*&nbsp;</FONT>:&nbsp;&nbsp;&nbsp;</TD>
		<TD class="inputCol" colspan="2" id="div_product"><%=ORIGDisplayFormatUtil.displaySelectTagScriptActionAndCode_ORIG(null,"" ,"loanType",displayMode," style=\"width:80%;\" ") %></TD>
	</TR>
	<TR> 
		<TD align="right" class="textColS" colspan="2" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "CHANNEL1") %><font color="#FF0000">*&nbsp;</FONT>:&nbsp;&nbsp;&nbsp;</TD>
		<TD class="inputCol" colspan="2" id="div_channel"><%=ORIGDisplayFormatUtil.displaySelectTagScriptActionAndCode_ORIG(null,"" ,"channel",displayMode," style=\"width:80%;\" ") %></TD>
	</TR>
	<TR>
		<TD align="right" class="textColS" colspan="2" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "MAIN_CUSTOMER_TYPE") %><font color="#FF0000">*&nbsp;</FONT>:&nbsp;&nbsp;&nbsp;</TD>
		<TD class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(customerTypeVect,"","customerType",displayMode," style=\"width:80%;\" ")%></TD>
	</TR>
	<TR>
		<TD align=right class="textColS" colspan="2" nowrap="nowrap" id="labelText"></TD>
		<TD class="inputCol" colspan="4" height="15" id="idNoText"></TD>
	</TR>
	<tr> 
		<td class="textColS" align="right" width="20%"><%=ORIGDisplayFormatUtil.displayCheckBox("","drawDown","Y","onClick=\"javascript:createDrawdown()\" "+ "onMouseOver=\""+tooltipUtil.getTooltip(request,"app_draw_down")+"\" " ) %></td>
		<td class="textColS" width="10%">
			<%=MessageResourceUtil.getTextDescription(request, "DRAW_DOWN") %></td>
		<td class="inputCol" width="6%">
			<input type="button" name="okBtn" value=" <%=MessageResourceUtil.getTextDescription(request, "OK") %> " class="button_text" onClick="javascript:gotoAppForm()"></td>
    	<td class="textColS" width="25%">&nbsp;</td>
    </tr>										                          
</table>															

<input type="hidden" name="searchType" value="<%=searchType%>">
<input type="hidden" name="appRecID" value="">

<script language="JavaScript" type="text/JavaScript">
function validate(){
	var customerType = appFormName.customerType.value;
	var loanType =  appFormName.loanType.value;
	if(customerType==""){
		alert("You Must Select Main Customer Type");
		return true;
	}
	if(loanType==""){
		alert("You Must Select Loan Type");
		return true;
	}
}
function gotoAppForm(){
	appFormName.formID.value="CARD_FORM";
	appFormName.currentTab.value="MAIN_TAB";
	appFormName.action.value="LoadAppForm";
	appFormName.handleForm.value = "N";
	if(appFormName.drawDown.checked){
		var idNo =  appFormName.idNo.value;
		if(idNo==""){
			alert("You Must Specify Identification No.");
			return false;
		}else{
			var req = new DataRequestor();
			var url = "<%="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>/CheckIDNumberServlet"; 
			
			req.addArg(_POST, "idNo", idNo);
			req.getURL(url);
			req.onload = function (data, obj) {
					if(data=='FOUND'){
						appFormName.submit();
					}else if(data=='NOT_FOUND'){
						alert('This Identification No. '+idNo+' cannot found');
						return false;
					}else{
						alert(data);
						return false;
					}
				}
		}
	}else{
		if(validate())
			return false;
		//appFormName.target = "_top";
		appFormName.submit();
	}	
}
function createDrawdown(){
	var obj = document.getElementById('idNoText');
	var obj1 = document.getElementById('labelText');
	
	var idNoStr = "";
	var label = "";
	if(appFormName.drawDown.checked){
		label = "Identification No.<font color=\"#FF0000\">*&nbsp;</FONT>:";
		idNoStr = "<input type=\"text\" name=\"idNo\" value=\"\" class=\"textbox\" style=\"width:60%;\" >";
          
		appFormName.customerType.value = '';
		appFormName.loanType.value = '';
		appFormName.customerType.disabled = true;
		appFormName.loanType.disabled = true;
	}else{
		idNoStr = "";
		label = "";
		appFormName.customerType.disabled = false;
		appFormName.loanType.disabled = false;
	}
	obj.innerHTML = idNoStr;
	obj1.innerHTML = label;
	return false;
}
function getProduct(){
	var orgType = appFormName.orgType.value;
	var server = '<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>';
	ajax(server+"/AjaxServlet?ClassName=GetProductFromOrg&orgType="+orgType, displayInnerProductHtml, "GET");
}
function displayInnerProductHtml(xmlDoc){
	displayInnerHtml(xmlDoc);
	var replaceObj = document.getElementById('div_channel');
	replaceObj.innerHTML = '<%=ORIGDisplayFormatUtil.displaySelectTagScriptActionAndCode_ORIG(null,"" ,"channel",displayMode," style=\"width:80%;\" ") %>';
}
function getChannel(){
	var loanType = appFormName.loanType.value;
	var server = '<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>';
	ajax(server+"/AjaxServlet?ClassName=GetChannelFromProduct&loanType="+loanType, displayInnerHtml, "GET");
}

function linkAction() {
	window.location="/ORIGWeb/FrontController?action=loadMultiApp&handleForm=N";
}

</script>	  		