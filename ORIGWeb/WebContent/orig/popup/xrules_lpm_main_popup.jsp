<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesLPMDataM" %>
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>
<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil"%> 
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<% 
	//MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
		ORIGUtility utility = new ORIGUtility();
		OrigXRulesUtil origXrulesUtil=OrigXRulesUtil.getInstance();
		ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();		
%>
<%
String txtResultName=request.getParameter("txtResultName");
ApplicationDataM  appForm = ORIGForm.getAppForm();
//get Personal
	PersonalInfoDataM personalInfoDataM;
		String personalType = (String) request.getSession().getAttribute("PersonalType");
	 System.out.print(personalType);
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
     String personalIndex=request.getParameter("reportPersonalIndex");     
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
   Vector vXRulesLPMDataMs=xrulesVer.getVXRulesLMPDataM();
   if(vXRulesLPMDataMs==null){
   vXRulesLPMDataMs=new Vector();
   }
  String result=xrulesVer.getLPMResult();
  String lpmComment=xrulesVer.getLPMcomment();
%>

<HEAD>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<TITLE>Custom :LPM System</TITLE>
<script language="javascript"> 
var maxCheckbox=<%=vXRulesLPMDataMs.size()%>;
function getPostionPopupX(popupWidth){   	 
    var frameWidth =  screen.width;
    var frameHeight =  screen.height;	 
	var result=(frameWidth/2)-(popupWidth/2);
	return result;	
 }
function addInputScreen(){
    var lpmItemIndex=<%=vXRulesLPMDataMs.size()%>;
	var popupWebAction='LoadXrulesLPMItemPopup'+'&lpmItemIndex='+lpmItemIndex;
	var popupWidth='800';
	var popupHeight='280';
  loadPopup('xruleLPMPopup',popupWebAction,popupWidth,popupHeight,'100', getPostionPopupX(popupWidth),'','','<%=personalType%>');
  }
function loadInputScreen( lpmItem){
    var lpmItemIndex=lpmItem;
	var popupWebAction='LoadXrulesLPMItemPopup'+'&lpmItemIndex='+lpmItemIndex;
	var popupWidth='800';
	var popupHeight='280';
  loadPopup('xruleLPMPopup',popupWebAction,popupWidth,popupHeight,'100',getPostionPopupX(popupWidth),'','','<%=personalType%>');  
  } 
 function saveLPMVerification() {
    
    form = document.appFormName;
	form.action.value = "SaveXrulesLPMmainPopup";
	form.handleForm.value = "N";	
	if(form.lpmFinalResult[0].checked ||form.lpmFinalResult[1].checked){
	form.submit();
	}else{
	 alert("Please Verify Result");
	}
 }
 function reloadPage(){
    form = document.appFormName;
	form.action.value = "LoadXrulesLPMmainPopup";
	form.handleForm.value = "N";	
	form.submit();
 }
 function deleteLPMItem(){
    form = document.appFormName;
	form.action.value = "DeleteXrulesLPMItemPopup";
	form.handleForm.value = "N";
	//if(form.lpmFinalResult[0].checked ||form.lpmFinalResult[1].checked){
	form.submit();
	//}else{
	// alert("Please Verify Result");
	//} */
 }
 function checkAllLPMClick(){ 
   var   form = document.appFormName;      
   for(var i=0;i< maxCheckbox;i++){    
     var obj=eval("document.appFormName."+"LPM_"+i); 
      if(obj){
      obj.checked=form.LPM_All.checked;                  
       }
    }     
    vaildateCheckBox(); 
}
function vaildateCheckBox(){   
   var   form = document.appFormName;      
   var  lpmCheck=false;   
    for(var i=0;i< maxCheckbox;i++){    
     var obj=eval("document.appFormName."+"LPM_"+i);       
      if(obj){
          if(obj.checked ){
          lpmCheck=true;
          break;
          }
       }
    }      
    if(lpmCheck){
    form.lpmFinalResult[0].disabled=true;
    form.lpmFinalResult[1].checked=true;
    }  else{
    form.lpmFinalResult[0].disabled=false;
    } 
}
function upperCaseFiled(fieldName){
  fieldName.value=fieldName.value.toUpperCase();
}
</script>
</HEAD>
<BODY >
 
<INPUT type="hidden" name="txtResultName" value="<%=txtResultName%>">
<INPUT type="hidden" name="reloadFlag" value="N">
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
	                          	<td class="text-header-detail">Customer :  LPM Result Detail</td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
	                              			<% if("".equalsIgnoreCase(chkDisable) ){%>
												<INPUT class="button" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onClick="saveLPMVerification()">
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
								<tr><td colspan="4">
									<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr><td class="TableHeader">
											<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
												<tr>
													<td class="Bigtodotext3" align="right" width="50%"><%=MessageResourceUtil.getTextDescription(request, "FINAL_RESULT") %></td>
													<td class="Bigtodotext3" width="20%">&nbsp;</td>
													<td class="Bigtodotext3" align="left" width="30%">
														 <%if("".equals(chkDisable)) {%>
														 	<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
														 	<tr><td><font class="Bigtodotext3">
													           Pass</font> </td> 
													        <td>             
													        <%=ORIGDisplayFormatUtil.displayRadioTag( XRulesConstant.ExecutionResultString.RESULT_PASS,ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"lpmFinalResult",result ,XRulesConstant.ExecutionResultString.RESULT_PASS,XRulesConstant.ExecutionResultString.RESULT_FAIL ) %>
													        </td><td><font class="Bigtodotext3">
													        	Fail</font>
													        </td><td>
													        	 <%=ORIGDisplayFormatUtil.displayRadioTag( XRulesConstant.ExecutionResultString.RESULT_FAIL,ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"lpmFinalResult",result ,XRulesConstant.ExecutionResultString.RESULT_FAIL,XRulesConstant.ExecutionResultString.RESULT_PASS ) %>
													        </td></tr></table> 
													     <%}else{ %>
													     	<%=ORIGDisplayFormatUtil.displayHTML(result) %>
													     <%} %> 
													</td>
												</tr>
											</table>
										</td></tr>
										<tr> <td  height="15"></td></tr>                        
										<tr class="sidebar10"> <td align="center">
											<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
												<tr><td class="TableHeader">
													<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
														<tr>
															<td class="Bigtodotext3" align="center" width="20"><%=ORIGDisplayFormatUtil.displayCheckBox("" ,"LPM_All","lpm","  onClick='checkAllLPMClick()'"+chkDisable)%></td>
														    <td class="Bigtodotext3" width="140" align="center"><%=MessageResourceUtil.getTextDescription(request, "LOAN_TYPE") %></td>
														    <td class="Bigtodotext3" width="150" align="center"><%=MessageResourceUtil.getTextDescription(request, "CREDIT_LIMIT") %></td>
														    <td class="Bigtodotext3" width="150" align="center"><%=MessageResourceUtil.getTextDescription(request, "OS_BALANCE") %>(Baht)</td>
														    <td class="Bigtodotext3" width="200" align="center"><%=MessageResourceUtil.getTextDescription(request, "STATUS") %></td> 
														</tr>
													</table>
												</td></tr>
											  <%   
											   for(int i=0;i<vXRulesLPMDataMs.size();i++){
											    XRulesLPMDataM  prmXRulesLPMDataM=(XRulesLPMDataM)vXRulesLPMDataMs.get(i);
											    String lpmLoneType=prmXRulesLPMDataM.getLoanType();
											    if(lpmLoneType==null){lpmLoneType="";}
											   %>
				 								<tr><td align="center" class="gumaygrey2">
													<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
														<tr>
														    <td class="jobopening2" nowrap="nowrap" width="20"><%=ORIGDisplayFormatUtil.displayCheckBox(prmXRulesLPMDataM.getLpmFlag() ,"LPM_"+i,OrigConstant.ORIG_FLAG_Y," onClick='vaildateCheckBox()'"+chkDisable)%></td>																     
														    <td class="jobopening2" width="140" nowrap="nowrap"><%=ORIGDisplayFormatUtil.displayHTML( cacheUtil.getORIGCacheDisplayNameDataM(24,lpmLoneType))%></td>
														    <td class="jobopening2" width="150" nowrap="nowrap"  align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(prmXRulesLPMDataM.getCreditLimit().intValue())%></td>
														    <td class="jobopening2" width="150" nowrap="nowrap" align="right"><%=ORIGDisplayFormatUtil.displayCommaNumber(prmXRulesLPMDataM.getOSBalnace().intValue())%></td>
														    <td class="jobopening2" width="200" nowrap="nowrap"><%=ORIGDisplayFormatUtil.displayHTML(origXrulesUtil.getNCBAccountStatusDescription(prmXRulesLPMDataM.getStatus()))%></td> 
														</tr>
													</table>
												</td></tr>
												<% }%>
											</table>
										</td></tr>
									</table>
								</td></tr>
									
								 <% if("".equals(chkDisable)){ %>
								  <tr height="30" >
								  	<td width="73%">&nbsp;</td>	
									<td width="7%">		  
								      	<INPUT class="button_text" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "ADD") %>" onClick="addInputScreen()"/>
								    </td><td width="7%">
										<INPUT class="button_text" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "DELETE") %>" onClick=" deleteLPMItem()"/>	      	    
									 </td>	
									 <td width="3%">&nbsp;</td>				
								  </tr>
								   <tr height="30" >
										<td colspan="4" align="left"> 
										    <TEXTAREA name="lpmComment" cols="40" rows="5" onkeyup="upperCaseFiled(this)"><%=ORIGDisplayFormatUtil.displayHTML(lpmComment)%></TEXTAREA>
									  </td>				
								  </tr>
								  <% }%>
								 </table>
								</td>
							</tr> 
							</table>
						</td>
					</tr>
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
 