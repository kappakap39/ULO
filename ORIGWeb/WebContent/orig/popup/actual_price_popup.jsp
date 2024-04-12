<html><head><title>Actual Price</title></head>
<%@ page import="com.eaf.orig.shared.model.LoanDataM" %>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.model.VehicleDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<%
   
	 /* LoanDataM loanDataM =null;
	if(ORIGForm.getAppForm().getLoanVect()!=null){
	 loanDataM=(LoanDataM)ORIGForm.getAppForm().getLoanVect().get(0); 
	 }
	if(loanDataM == null){
		loanDataM = new LoanDataM();
	}*/
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	String displayMode = formUtil.getDisplayMode("LOAN_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	
	VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
	if(vehicleDataM == null){
		vehicleDataM = new VehicleDataM();
	}else{
		if(!"NEW".equals(vehicleDataM.getDrawDownStatus()) && !ORIGUtility.isEmptyString(vehicleDataM.getDrawDownStatus())){
			displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
		}
	} 
%>
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR>
		<TD vAlign=top>
			<TABLE  cellSpacing=0 cellPadding=0  width="100%" align=left border="0">
	          	<TR><TD class="sidebar8">
					<TABLE cellSpacing=0 cellPadding=0 width="100%" align="left"  border="0" bgcolor="FFFFFF">	
							<tr><td height="20"></td></tr>
							<tr>
								<td class="sidebar9">
									<table cellspacing=0 cellpadding=0 width="100%" align=center border=0 height="20" >
									<tr> <td  height="10">
						 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
			                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "ACTUAL_CAR_PRICE") %></td>
			                            <td width="330">
			                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
			                              		<tr height="30"><td>
			                                    	<td align="right"><input type="button" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" class="button" onclick="saveActual()">
				        							<input type="button" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" class="button" onclick="closeWindow()">
			                                    </td></tr>
			                                </table>
			                            </td></tr>	
			                            </table>
			                        </td></tr>
			                        <tr> <td  height="15"></td></tr>  
									<tr class="sidebar10"> <td align="center">
										<table cellpadding="0" cellspacing="0" width="100%" align="center">
											<tr>
												<td height="10" class="inputCol" colspan="2"></td>
											</tr>
											<tr> 
												<td class="textColS" width="40%"><%=MessageResourceUtil.getTextDescription(request, "ACTUAL_CAR_PRICE") %> :</td>
												<td class="inputCol" width="60%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"15","actual_car_price","textbox","onblur=\"javascript:addComma(this);addDecimalFormatNotDefaultZero(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" ","") %></td>
											</tr>
											<tr> 
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACTUAL_DOWN") %> :</td>
												<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"15","actual_down","textbox","onblur=\"javascript:addComma(this);addDecimalFormatNotDefaultZero(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" ","") %></td>
											</tr>	
										</table>
									</td></tr>
								</table>
							</td>
						</tr>
					</table>
				</td></tr>
			</table>
		</td> 
	</tr>
</table>									

<SCRIPT language="JavaScript">
//<!--
function closeWindow() {
	window.close();
}
function loadData(){
   if(opener.appFormName.actual_car_price){
   document.appFormName.actual_car_price.value=opener.appFormName.actual_car_price.value;
   }
   if(opener.appFormName.actual_down){
   document.appFormName.actual_down.value=opener.appFormName.actual_down.value;
   }
}
function saveActual(){
	form = document.appFormName;
	var carPrice = form.actual_car_price.value;
	var down = form.actual_down.value;
	var isValid = false;
	if(carPrice == '' || down == ''){
		alert("Actual Car Price and Actual Down are required");
		isValid = true;
	}else if(parseFloat(carPrice) == 0){
		alert("Actual Car Price must more than zero");
		isValid = true;
	}else{
		//form.action.value = "SaveActualPopup";
		//form.handleForm.value = "N";
		if(opener.appFormName.actual_car_price){
		opener.appFormName.actual_car_price.value=carPrice;
		}
		if(opener.appFormName.actual_down){
		opener.appFormName.actual_down.value=down;
		}
		//form.submit();
		window.close();
	}
}

loadData();
//-->
</SCRIPT>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>