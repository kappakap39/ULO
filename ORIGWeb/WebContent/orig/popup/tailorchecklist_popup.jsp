<html><head><title>UNIVERSAL LOAN ORIGINATION SYSTEM</title></head>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.model.DocumentCheckListDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<% 
	ORIGUtility utility = new ORIGUtility();
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("CHANGE_NAME_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	
	
	PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
	//System.out.println("personalInfoDataM.getCustomerType() = "+personalInfoDataM.getCustomerType());
	Vector documentVect = utility.getDocumentListByCustomerType(personalInfoDataM.getCustomerType());
	Vector documentListVect = ORIGForm.getAppForm().getDocumentCheckListDataM();
	if(OrigConstant.ROLE_PD.equals(ORIGUser.getRoles().elementAt(0))){
		displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	}
	String enableForPD = "disabled";
	if(OrigConstant.ROLE_PD.equals(ORIGUser.getRoles().elementAt(0))){
		enableForPD = "";
	}
	
	String disabledBtn = "";
	if(!displayMode.equals(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT)){
		disabledBtn = ORIGDisplayFormatUtil.DISABLED;
	}
%>

<html:html>
<HEAD>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="../../theme/Master.css" rel="stylesheet" type="text/css">
<TITLE></TITLE>
<SCRIPT language="JavaScript">

function savedocumentlist(){
		//alert(document.appFormName);
		document.appFormName.action.value = "SaveDocumentList";
		document.appFormName.handleForm.value = "N";
		document.appFormName.submit();
	 	//window.location.href("<%//=request.getContextPath()%>/FrontController?action=SaveDocumentList");    
	 	//window.close();
}

</SCRIPT>
</HEAD>

<BODY leftMargin=0 topMargin=0 marginwidth="0" marginheight="0">
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
	                          	<td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "TAILOR_MADE_LST") %> </td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
											<input class="button" type="button" name="add" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onclick="savedocumentlist()" <%=disabledBtn %>>
										</td>
										<td><INPUT class="button" type=button value=" <%=MessageResourceUtil.getTextDescription(request, "EXIT") %> " onclick="window.close()">
					                    </td></tr>
	                                </table>
	                            </td></tr>
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>                        
						<tr class="sidebar10"> <td align="center">
							<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
								<tr><td>				
								<div id="TWTable">
									<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr><td class="TableHeader">
											<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
												<tr>
									                <td class="Bigtodotext3" width="15%" align="center"  valign="middle"><%=MessageResourceUtil.getTextDescription(request, "RECEIVE_DOCUMENT") %></td>
									                <td class="Bigtodotext3" width="15%" align="center"  valign="middle"><%=MessageResourceUtil.getTextDescription(request, "WAIVE") %></td>
									                <td class="Bigtodotext3" width="15%" align="center"  valign="middle"><%=MessageResourceUtil.getTextDescription(request, "NOT_RECEIVE_DOCUMENT") %></td>
									                <td class="Bigtodotext3" width="25%" align="center"  valign="middle"><%=MessageResourceUtil.getTextDescription(request, "DESCRIPTION") %></td>
									                <td class="Bigtodotext3" width="10%" align="center"  valign="middle"><%=MessageResourceUtil.getTextDescription(request, "REQUIRE") %></td>
									                <td class="Bigtodotext3" width="25%" align="center"  valign="middle"><%=MessageResourceUtil.getTextDescription(request, "REMARK") %></td>
									            </tr>
											</table> 
										</td></tr>
						               <% 
						               
						               //Wiroon
						               HashMap requiredDoc = (HashMap)request.getSession().getAttribute("requiredDocMap");
						               
						              if(documentVect!=null){
						              	//System.out.println(" v.size() = " + documentVect.size());
						              	DocumentCheckListDataM documentlistM;
						              	String receive = "";
						              	String remark;
						              	String waive = "";
						             	String receiveChk = "";
						             	String waiveChk = "";
						             	String notReceive = "";
						              	for (int i =0 ; i< documentVect.size() ; i++){ 
						              	  receive="";
						              	  remark ="";
						             	  waive = "";
						             	  receiveChk = "";
						             	  waiveChk = "";
						             	  notReceive = "";
							              //System.out.println("v.elementAt(i) = "+documentVect.elementAt(i));
							              documentlistM = (DocumentCheckListDataM)documentVect.get(i);
							              DocumentCheckListDataM checkListDataM;
							              //System.out.println("documentListVect = "+documentListVect);
							              System.out.println("documentlistM.getDocTypeId() = "+documentlistM.getDocTypeId());
			
							              //TODO				              
							              if(requiredDoc.get(String.valueOf(documentlistM.getDocTypeId())) != null){
							              		System.out.println("Set required >>"+documentlistM.getDocTypeId());
			       				              documentlistM.setRequire("C");
							              }
			
							              
							              if(documentListVect != null){
								              for(int j=0; j<documentListVect.size(); j++){
								              	checkListDataM = (DocumentCheckListDataM) documentListVect.get(j);
								              	if(documentlistM.getDocTypeId().equals(checkListDataM.getDocTypeId())){
								              		System.out.println("checkListDataM.getReceive() = "+checkListDataM.getReceive());
								              		System.out.println("checkListDataM.getWaive() = "+checkListDataM.getWaive());
								              		remark = checkListDataM.getRemark();
								              		if((OrigConstant.RECEIVE_DOC).equals(checkListDataM.getReceive())){
								              			receive = String.valueOf(documentlistM.getDocTypeId());
								              			receiveChk = "true";
								              			waiveChk = "false"; 
								              		}            		
								              		//break;
								              		if((OrigConstant.RECEIVE_DOC).equals(checkListDataM.getWaive())){
								              			waive = String.valueOf(documentlistM.getDocTypeId());
								              			receiveChk = "false";
								              			waiveChk = "true";
								              		}
								              		if(ORIGUtility.isEmptyString(checkListDataM.getWaive()) && ORIGUtility.isEmptyString(checkListDataM.getReceive())){
								              			notReceive = String.valueOf(documentlistM.getDocTypeId());
								              		}
								              	}			              		              	
								              }
								           }
								           if(ORIGUtility.isEmptyString(receiveChk) && ORIGUtility.isEmptyString(waiveChk)){
								              	 notReceive = documentlistM.getDocTypeId();
								           }
			              				%>
				                        <tr><td align="center" class="gumaygrey2">
											<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
												<tr> 
							                        <td align="center" class="jobopening2" width="15%"> 
							                          <%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(String.valueOf(documentlistM.getDocTypeId())+",Y",displayMode,"docListChk"+i,receive+",Y","","","") %></td>
							                        <td align="center" class="jobopening2" width="15%"> 
							                          <%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(String.valueOf(documentlistM.getDocTypeId())+",W",displayMode,"docListChk"+i,waive+",W","","","onClick=\"javascript:\" "+enableForPD) %></td>
							                        <td align="center" class="jobopening2" width="15%"> 
							                          <%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(String.valueOf(documentlistM.getDocTypeId())+",N",displayMode,"docListChk"+i,notReceive+",N","","","onClick=\"javascript:clearRemark("+i+")"+"\""+"\"")%></td>
							                        <td align="center" class="jobopening2" width="25%"> 
							                          <%=documentlistM.getDocTypeDesc()%></td>
							                        <td align="center" class="jobopening2" width="10%"> 
							                          <%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(OrigConstant.REQUIRE_DOC,displayMode,"require"+i,documentlistM.getRequire(),"","","disabled")%></td>
							                        <td align="center" class="jobopening2" width="25%"> 
							                          <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(remark,displayMode,"","remark","","onBlur=\"javascripe:change2Uppercase(this)\" ","350") %></td>
							                        
							                      </tr>
						                    </table>
						                 </td></tr> 
                      <% } 
               }%>
			    					</table>
			    					</div>
			    				</td>
								</tr>
							</table>
						</td></tr>
					</table>
				</td></tr>
			</table>
		</td></tr>		
	</table>
	</td></tr>
</table>
</BODY>
</html:html>

<script language="JavaScript">	

function clearRemark(i) {
		var docObj = eval("document.appFormName.docListChk"+i);
		if(!docObj[0].checked){
			appFormName.remark[i].value = "";
		}
}
function change2Uppercase(textFieldName){
	var textField = textFieldName;
	if(!isUndefined(textField)){
		var values = textField.value;
		textField.value = values.toUpperCase();
	}
}
</script>

<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>
