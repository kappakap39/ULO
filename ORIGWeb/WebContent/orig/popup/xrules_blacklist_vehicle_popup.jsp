<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesBlacklistVehicleDataM" %>
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	//MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
		ORIGUtility utility = new ORIGUtility();
		ORIGCacheUtil  origCacheUtil=ORIGCacheUtil.getInstance();
		OrigXRulesUtil origXrulesUtil=OrigXRulesUtil.getInstance();
%>

<%
ApplicationDataM  appForm = ORIGForm.getAppForm();
//get Personal
	PersonalInfoDataM personalInfoDataM;
		String personalType = (String) request.getSession().getAttribute("PersonalType");
	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	}else{
		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	}
   String  displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
      System.out.println("viewFromReport "+request.getParameter("viewFromReport"));
   String chkDisable="";
    //=============================================
    if(request.getParameter("viewFromReport")!=null){   
    if(session.getAttribute("applicationVerification")!=null){ 
     ApplicationDataM applicationDataM=(ApplicationDataM)session.getAttribute("applicationVerification"); 	     
      String reportPersonalSeq=request.getParameter("reportPersonalSeq");     
      String reportPersonalType=request.getParameter("reportPersonalType");            
      int personalSeq=utility.stringToInt(reportPersonalSeq);
      personalInfoDataM=utility.getPersonalInfoByTypeAndSeq( applicationDataM,reportPersonalType,personalSeq);     
     } 
      displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
      chkDisable=" disabled";
    }
    //=============================================
   	
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();
	}
   XRulesVerificationResultDataM  xrulesVer=personalInfoDataM.getXrulesVerification();
   Vector  xRuesBlacklistVehicle=xrulesVer.getVXRulesBlacklistVehicleDataM();
   if(xRuesBlacklistVehicle==null){
   xRuesBlacklistVehicle=new Vector();
   }
   
    String blackistVehilceResult=xrulesVer.getBLVehicleResult();
     boolean showDescFlag=false;
    // String userPostition=origXrulesUtil.getUserPosition(ORIGUser.getUserName());
    String userPostition="";
   if(ORIGUser.getRoles()!=null &&ORIGUser.getRoles().size()>0){
      userPostition=(String)ORIGUser.getRoles().get(0);
   }
   if(OrigConstant.ROLE_UW.equalsIgnoreCase(userPostition)||OrigConstant.ROLE_DE.equalsIgnoreCase(userPostition)){
      showDescFlag=true;
   }
%>


<HEAD><TITLE>Blacklist : Car Result</TITLE> 
<SCRIPT language="JavaScript">
window.onBlur = self.focus();
var maxCheckbox=<%=xRuesBlacklistVehicle.size()%>;
function saveBlacklistVehicleVerification(){
  
    var  form = document.appFormName;
	form.action.value = "SaveXruleBlacklistVehiclePopup";
	form.handleForm.value = "N";
	if(form.blacklistVehicleFinalResult[0].checked ||form.blacklistVehicleFinalResult[1].checked){
	form.submit();
	}else{
	 alert("Please Verify Result");
	}
}
function checkAllBlacklistClick(){ 
    var   form = document.appFormName;      
    for(var i=0;i< maxCheckbox;i++){    
     var obj=eval("document.appFormName."+"blVehicle_"+i); 
      if(obj){
      obj.checked=form.blVehicle_All.checked;                  
       }
    }     
    vaildateCheckBox(); 
}
function vaildateCheckBox(){
   var   form = document.appFormName;      
   var  blacklistCheck=false;
    for(var i=0;i< maxCheckbox;i++){    
     var obj=eval("document.appFormName."+"blVehicle_"+i); 
      if(obj){
          if(obj.checked ){
          blacklistCheck=true;
          break;
          }
       }
    }      
    if(blacklistCheck){
    form.blacklistVehicleFinalResult[0].disabled=true;
    form.blacklistVehicleFinalResult[1].checked=true;
    }  else{
    form.blacklistVehicleFinalResult[0].disabled=false;
    }  
}

 
</SCRIPT>
</HEAD>
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td colspan="2" class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr>
	                          	<td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "XRULES_BUTTON_3") %> </td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
	                              			<% if("".equalsIgnoreCase(chkDisable) ){%>
												<INPUT class="button" type=button value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onClick="saveBlacklistVehicleVerification()">
											</td><td>	 
											<%}%>
												<INPUT class="button" type=button value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" onClick="window.close()">		
	                                    </td></tr>
	                                </table>
	                            </td></tr>
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>                        
						<tr class="sidebar10"> <td align="center">
							<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
								<tr><td>
									<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
												<tr><td class="TableHeader">
													<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
														<tr>
															<td class="Bigtodotext3" align="right" width="50%"><%=MessageResourceUtil.getTextDescription(request, "FINAL_RESULT") %></td>
															<td class="Bigtodotext3" width="2%">&nbsp;</td>
															<td class="Bigtodotext3" align="left" width="48%">
																<%if("".equals(chkDisable)){ %>
																 	<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
																 	<tr><td><font class="Bigtodotext3">
															           Pass</font> </td> 
															        <td>             
															        <%=ORIGDisplayFormatUtil.displayRadioTag(XRulesConstant.ExecutionResultString.RESULT_PASS,displayMode,"blacklistVehicleFinalResult",blackistVehilceResult,XRulesConstant.ExecutionResultString.RESULT_PASS,XRulesConstant.ExecutionResultString.RESULT_PASS) %> 
															        </td><td><font class="Bigtodotext3">
															        	Fail</font>
															        </td><td>
															        	<%=ORIGDisplayFormatUtil.displayRadioTag(XRulesConstant.ExecutionResultString.RESULT_FAIL,displayMode,"blacklistVehicleFinalResult",blackistVehilceResult,XRulesConstant.ExecutionResultString.RESULT_FAIL,XRulesConstant.ExecutionResultString.RESULT_FAIL) %>
															        </td></tr></table> 
															     <%}else{ %>
															     	<%=ORIGDisplayFormatUtil.displayHTML(blackistVehilceResult) %>
															     <%} %> 
															</td>
														</tr>
													</table>
												</td></tr> 
												<tr><td class="TableHeader">
													<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
														<tr>
															<td class="Bigtodotext3" width="2%" align="center"><%=ORIGDisplayFormatUtil.displayCheckBox("" ,"blVehicle_All","blacklist"," onClick='checkAllBlacklistClick()' "+chkDisable)%></td>
														    <td class="Bigtodotext3" width="9%" align="center"><%=MessageResourceUtil.getTextDescription(request, "CLASSIS_NO")%></td>
														    <td class="Bigtodotext3" width="10%" align="center"><%=MessageResourceUtil.getTextDescription(request, "BL_REGISTRATION_NO")%></td>
														    <td class="Bigtodotext3" width="10%" align="center"><%=MessageResourceUtil.getTextDescription(request, "ENGINE_NO")%></td>
														    <td class="Bigtodotext3" width="12%" align="center"><%=MessageResourceUtil.getTextDescription(request, "BRAND")%></td>
														    <td class="Bigtodotext3" width="12%" align="center"><%=MessageResourceUtil.getTextDescription(request, "MODEL")%></td>
														    <td class="Bigtodotext3" width="15%" align="center"><%=MessageResourceUtil.getTextDescription(request, "BLAKCLIST_SOURCE")%></td>
														    <td class="Bigtodotext3" width="15%" align="center"><%=MessageResourceUtil.getTextDescription(request, "BLACKLIST_REASON")%></td>
														     <%  if(showDescFlag){ %>
														    <td class="Bigtodotext3" width="15%" align="center"><%=MessageResourceUtil.getTextDescription(request, "BLACKLIST_DETAIL")%></td> 
														    <%} %>
														</tr>
													</table> 
												</td></tr>
													
											  <%   
											  if(xRuesBlacklistVehicle.size()>0){
											   for(int i=0;i<xRuesBlacklistVehicle.size();i++){
											       XRulesBlacklistVehicleDataM  xRulesBlacklsitVehicleDataM=(XRulesBlacklistVehicleDataM)xRuesBlacklistVehicle.get(i);
											   String blacklistSource = xRulesBlacklsitVehicleDataM.getBLSource();
											   String blacklistReason=xRulesBlacklsitVehicleDataM.getBLReason();
											        if(showDescFlag){          
											         if(blacklistSource==null){blacklistSource="";}
											         if(blacklistReason==null){blacklistReason="";}
											         String tempBlacklistSource= origCacheUtil.getNaosCacheDisplayNameDataM(23 ,blacklistSource.trim());
											         String tempBlacklistReason=origCacheUtil.getNaosCacheDisplayNameDataM(22 ,blacklistReason.trim());
											         if(tempBlacklistSource!=null&&!"".equals(tempBlacklistSource)){blacklistSource=tempBlacklistSource;}
											         if(tempBlacklistReason!=null&&!"".equals(tempBlacklistReason)){blacklistReason=tempBlacklistReason;}
											         }
											   %>
												<tr><td align="center" class="gumaygrey2">
													<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
														<tr>
														    <td class="jobopening2" width="2%"><%=ORIGDisplayFormatUtil.displayCheckBox(xRulesBlacklsitVehicleDataM.getBLFlag() ,"blVehicle_"+i,"Y","  onClick='vaildateCheckBox()'"+chkDisable)%></td>
														    <td class="jobopening2" width="9%"><%=ORIGDisplayFormatUtil.displayHTML(xRulesBlacklsitVehicleDataM.getChassisNo())%></td>
														    <td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayHTML(xRulesBlacklsitVehicleDataM.getRegistrationNo())%></td>
														    <td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayHTML(xRulesBlacklsitVehicleDataM.getEngineNo())%></td>
														    <td class="jobopening2" width="12%"><%=ORIGDisplayFormatUtil.displayHTML(origXrulesUtil.getCarBrandDesc(xRulesBlacklsitVehicleDataM.getBrand()))%></td>
														    <td class="jobopening2" width="12%"><%=ORIGDisplayFormatUtil.displayHTML(origXrulesUtil.getCarModel( xRulesBlacklsitVehicleDataM.getModel(),xRulesBlacklsitVehicleDataM.getBrand()))%></td>
														    <td class="jobopening2" width="15%"><%=ORIGDisplayFormatUtil.displayHTML(blacklistSource)%></td>
														    <td class="jobopening2" width="15%"><%=ORIGDisplayFormatUtil.displayHTML(blacklistReason)%></td>
														     <%  if(showDescFlag){ %>
														    <td class="jobopening2" width="15%"><%=ORIGDisplayFormatUtil.displayHTML(xRulesBlacklsitVehicleDataM.getBLDetail())%></td>
														    <% }%>
														  </tr>
													</table> 
												</td></tr>
												  <% }%>
												 <% }else {%> 
												<tr><td align="center" class="gumaygrey2">
													<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
														<tr>
													  		<td class="jobopening2" align="center">Results Not Found.</td>
													  	</tr>
													</table> 
												</td></tr>
												  <% } %>
  											</table>
										</td></tr>
									</table>
								</td>
							</tr>
						</table>
					</td></tr>
				</table>
			</td></tr>
		</table>
	</td></tr>		
</table>
 
 

<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>

