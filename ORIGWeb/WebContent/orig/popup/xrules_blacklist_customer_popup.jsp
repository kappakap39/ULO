<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesBlacklistDataM" %>
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	//MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
		ORIGUtility utility = new ORIGUtility();
		ORIGCacheUtil cacheUtil=ORIGCacheUtil.getInstance();
	    OrigXRulesUtil oricXrulesUtil=OrigXRulesUtil.getInstance();
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
   Vector  xRuesBlacklistCustomer=xrulesVer.getVXRulesBlacklistDataM();
   if(xRuesBlacklistCustomer==null){
   xRuesBlacklistCustomer=new Vector();
   }
   boolean showDescFlag=false;
   String blackistResult=xrulesVer.getBLResult();   
   String userPostition="";
   if(ORIGUser.getRoles()!=null &&ORIGUser.getRoles().size()>0){
      userPostition=(String)ORIGUser.getRoles().get(0);
   }
   //oricXrulesUtil.getUserPosition(ORIGUser.getUserName());
   if(OrigConstant.ROLE_UW.equalsIgnoreCase(userPostition)||OrigConstant.ROLE_DE.equalsIgnoreCase(userPostition)){
      showDescFlag=true;
   }
%>
<HEAD><TITLE>Blacklist : Customer Result</TITLE> 
<SCRIPT language="JavaScript">
window.onBlur = self.focus(); 
var maxCheckbox=<%=xRuesBlacklistCustomer.size()%>;
function saveBlacklistCutomerVerification(){
    form = document.appFormName;
	form.action.value = "SaveXruleBlacklistCustomerPopup";
	form.handleForm.value = "N";
	if(form.blacklistFinalResult[0].checked ||form.blacklistFinalResult[1].checked){
	form.submit();
	}else{
	 alert("Please Verify Result");
	}
}

function checkAllBlacklistClick(){ 
    var   form = document.appFormName;      
    for(var i=0;i< maxCheckbox;i++){    
     var obj=eval("document.appFormName."+"blCustomer_"+i); 
      if(obj){
      obj.checked=form.bl_All.checked;                  
       }
    }     
    vaildateCheckBox(); 
}
function vaildateCheckBox(){
   var   form = document.appFormName;      
   var  blacklistCheck=false;
    for(var i=0;i< maxCheckbox;i++){    
     var obj=eval("document.appFormName."+"blCustomer_"+i); 
      if(obj){
          if(obj.checked ){
          blacklistCheck=true;
          break;
          }
       }
    }      
    if(blacklistCheck){
    form.blacklistFinalResult[0].disabled=true;
    form.blacklistFinalResult[1].checked=true;
    }  else{
    form.blacklistFinalResult[0].disabled=false;
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
	                          	<td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "XRULES_BUTTON_2") %> </td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
	                              			<% if("".equalsIgnoreCase(chkDisable) ){%>
												<INPUT class="button" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onClick="saveBlacklistCutomerVerification()">
											</td><td>	 
											<%}%>
												<INPUT class="button" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" onClick="window.close()">	
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
															<td class="Bigtodotext3" width="20%">&nbsp;</td>
															<td class="Bigtodotext3" align="left" width="30%">
																 <%if("".equals(chkDisable)){ %>
																 	<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
																 	<tr><td><font class="Bigtodotext3">
															           Pass</font> </td> 
															        <td>             
															        <%=ORIGDisplayFormatUtil.displayRadioTag(XRulesConstant.ExecutionResultString.RESULT_PASS,displayMode,"blacklistFinalResult",blackistResult,XRulesConstant.ExecutionResultString.RESULT_PASS,XRulesConstant.ExecutionResultString.RESULT_PASS) %>
															        </td><td><font class="Bigtodotext3">
															        	Fail</font>
															        </td><td>
															        	<%=ORIGDisplayFormatUtil.displayRadioTag(XRulesConstant.ExecutionResultString.RESULT_FAIL,displayMode,"blacklistFinalResult",blackistResult,XRulesConstant.ExecutionResultString.RESULT_FAIL,XRulesConstant.ExecutionResultString.RESULT_FAIL)%>
															        </td></tr></table> 
															     <%}else{ %>
															     	<%=ORIGDisplayFormatUtil.displayHTML(blackistResult) %>
															     <%} %> 
															</td>
														</tr>
													</table>
												</td></tr>
												<tr><td class="TableHeader">
													<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
														<tr>
															<td class="Bigtodotext3" width="2%" align="center"><%=ORIGDisplayFormatUtil.displayCheckBox("" ,"bl_All","blacklist"," onClick='checkAllBlacklistClick()' "+chkDisable)%></td>
														    <td class="Bigtodotext3" width="5%" align="center"><%=MessageResourceUtil.getTextDescription(request, "BL_IDNO")%></td>
														    <td class="Bigtodotext3" width="10%" align="center"><%=MessageResourceUtil.getTextDescription(request, "CIDNUM")%></td>
														    <td class="Bigtodotext3" width="5%" align="center"><%=MessageResourceUtil.getTextDescription(request, "NAME")%></td>
														    <td class="Bigtodotext3" width="12%" align="center"><%=MessageResourceUtil.getTextDescription(request, "LASTNAME")%></td>
														    <td class="Bigtodotext3" width="12%" align="center"><%=MessageResourceUtil.getTextDescription(request, "HOUSE_ID")%></td>
														    <td class="Bigtodotext3" width="12%" align="center"><%=MessageResourceUtil.getTextDescription(request, "BIRTH_DATE")%></td>
														    <td class="Bigtodotext3" width="15%" align="center"><%=MessageResourceUtil.getTextDescription(request, "BLAKCLIST_SOURCE")%></td>
														    <td class="Bigtodotext3" width="15%" align="center"><%=MessageResourceUtil.getTextDescription(request, "BLACKLIST_REASON")%></td>
														    <%  if(showDescFlag){ %>
														    <td class="Bigtodotext3" width="15%" align="center"><%=MessageResourceUtil.getTextDescription(request, "BLACKLIST_DETAIL")%></td> 
														    <%} %>
														</tr>
													</table> 
												</td></tr>

											  <%   
											  if(xRuesBlacklistCustomer.size()>0){
											   for(int i=0;i<xRuesBlacklistCustomer.size();i++){
											       XRulesBlacklistDataM  xRulesBlacklistingCustomerDataM=(XRulesBlacklistDataM)xRuesBlacklistCustomer.get(i);
											       String bgColor="#FFFFFF";
											     if(OrigConstant.ORIG_FLAG_Y.equals( xRulesBlacklistingCustomerDataM.getHLFlag())){
											        bgColor="#33CCFF";
											      } 
											      
											   %>
												<tr><td align="center" class="gumaygrey2">
													<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
														<tr>
														    <td class="jobopening2" width="2%"><%=ORIGDisplayFormatUtil.displayCheckBox(xRulesBlacklistingCustomerDataM.getBLFlag(),"blCustomer_"+i,"Y","  onClick='vaildateCheckBox()' "+chkDisable)%></td>
														    <td class="jobopening2" width="5%" nowrap><%=ORIGDisplayFormatUtil.displayHTML(xRulesBlacklistingCustomerDataM.getBlacklistIdNo())%></td>
														    <td class="jobopening2" width="10%" nowrap><%=ORIGDisplayFormatUtil.displayHTML(xRulesBlacklistingCustomerDataM.getCidnum())%></td>
														    <td class="jobopening2" width="5%" nowrap><%=ORIGDisplayFormatUtil.displayHTML(xRulesBlacklistingCustomerDataM.getThaiFirstName())%></td>
														    <td class="jobopening2" width="12%" nowrap><%=ORIGDisplayFormatUtil.displayHTML(xRulesBlacklistingCustomerDataM.getThaiLastName())%></td>
														    <td class="jobopening2" width="12%" nowrap><%=ORIGDisplayFormatUtil.displayHTML(xRulesBlacklistingCustomerDataM.getHouseIdNo())%></td>
														    <td class="jobopening2" width="12%" nowrap><%=ORIGDisplayFormatUtil.datetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(
														    					ORIGDisplayFormatUtil.parseDate(xRulesBlacklistingCustomerDataM.getBirthDate())))%></td>
														     <%  if(showDescFlag){ %>   
														    <td class="jobopening2" width="15%" nowrap><%=ORIGDisplayFormatUtil.displayHTML(cacheUtil.getORIGCacheDisplayNameDataMByType( 
														    			xRulesBlacklistingCustomerDataM.getBLSource(),OrigConstant.CacheName.CACHE_NAME_BLACKLIST_SOURCE))%></td>        
														    <td class="jobopening2" width="15%"><%=ORIGDisplayFormatUtil.displayHTML(cacheUtil.getORIGCacheDisplayNameDataMByType(
														    			xRulesBlacklistingCustomerDataM.getBLReason(),OrigConstant.CacheName.CACHE_NAME_BLACKLIST_REASON))%></td>
														    <td class="jobopening2" width="15%"><%=ORIGDisplayFormatUtil.displayHTML(xRulesBlacklistingCustomerDataM.getBLDetails())%></td>
														    <%} else {%>
														      <td class="jobopening2" width="15%"><%=ORIGDisplayFormatUtil.displayHTML(  xRulesBlacklistingCustomerDataM.getBLSource() )%></td>        
														      <td class="jobopening2" width="15%" nowrap><%=ORIGDisplayFormatUtil.displayHTML( xRulesBlacklistingCustomerDataM.getBLReason())%></td>
														    <%} %>
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

