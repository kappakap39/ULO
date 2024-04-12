<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil"%>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM"%>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil"%>
<%@ page
	import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM"%>
<%@ page import="com.eaf.xrules.shared.model.XRulesFICODataM"%>
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>

<jsp:useBean id="ORIGUser" scope="session"
	class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session"
	class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" />
<%//MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
            ORIGUtility utility = new ORIGUtility();

            %>

<%        ApplicationDataM appForm = ORIGForm.getAppForm();
            //get Personal
            PersonalInfoDataM personalInfoDataM;
            String personalType = (String) request.getSession().getAttribute(
                    "PersonalType");
            if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
                personalInfoDataM = (PersonalInfoDataM) request
                        .getSession(true).getAttribute("MAIN_POPUP_DATA");
            }else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
				personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
			} else {
                personalInfoDataM = utility.getPersonalInfoByType(ORIGForm
                        .getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
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
            if (personalInfoDataM == null) {
                System.out.println("==> personalInfoDataM is Null");
                personalInfoDataM = new PersonalInfoDataM();
            }
            XRulesVerificationResultDataM xrulesVer = personalInfoDataM
                    .getXrulesVerification();
               XRulesFICODataM prmXRulesFICODataM=xrulesVer.getXrulesFICODataM();
            if (prmXRulesFICODataM == null) {
                prmXRulesFICODataM = new XRulesFICODataM();
            }
            OrigXRulesUtil origXrulesUtil=OrigXRulesUtil.getInstance();
        %>
<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<TITLE>FICO Result Screen</TITLE>
<SCRIPT language="JavaScript">
window.onBlur = self.focus();
</SCRIPT>
</HEAD>
<BODY>
<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td colspan="2" class="sidebar8">
		<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
	    	<tr><td class="sidebar9">
				<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
		 			<tr> <td  height="10">
		 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr>
	                          	<td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "XRULES_BUTTON_10") %> </td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
	                              			<INPUT class="button" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" onClick="window.close()">
	                                    </td></tr>
	                                </table>
	                            </td></tr>
                        </table>
                    </td></tr>
                    <tr> <td  height="15"></td></tr>                        
					<tr class="sidebar10"> <td align="center">
					 	 <table cellpadding="" cellspacing="0" width="100%" align="center">
					 	 	<tr><td>
					 	 	
								<TD width="200" class="Bigtodotext3" align="center" nowrap>IDNo&nbsp;
								 <%=ORIGDisplayFormatUtil.displayHTMLNoTrim(personalInfoDataM.getIdNo()) %></TD>
								<TD width="200" class="Bigtodotext3" align="center" nowrap>Score&nbsp;
									<% String ficoResult=xrulesVer.getFicoResult();
									if(ficoResult==null||"".equals(ficoResult)){ficoResult="N/A";}
									%>
									 <%=ficoResult%> (<%=prmXRulesFICODataM.getScore()%>)
								</TD>
							</TR>
							
							<%if(prmXRulesFICODataM.getErrorCode()!=null&&!"".equals(prmXRulesFICODataM.getErrorCode())){ %>
						     <TR>
								<TD  colspan="2" class="Bigtodotext3" align="center" nowrap>Error Message:<%=ORIGDisplayFormatUtil.displayHTML(prmXRulesFICODataM.getErrorMessage())%></TD>						 		 
							</TR>
							<TR>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>		 
							</TR>
							<%}%>	
						</table>
					</td></tr>
					<tr class="sidebar10"> <td align="center">
			 	 		<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
							<tr><td class="TableHeader">
								<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									<tr> 
										<td class="Bigtodotext3" width="40%"><%=MessageResourceUtil.getTextDescription(request, "REASON_CODE") %></td>
										<td class="Bigtodotext3" width="60%"><%=MessageResourceUtil.getTextDescription(request, "DESCRIPTION") %></td> 
									</tr>
								</table>
							</td></tr>
							
						<%if(prmXRulesFICODataM.getReasonCode1()!=null){%>
							<tr><td align="center" class="gumaygrey2">
								<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									<tr>
										<td class="Bigtodotext3"><%=ORIGDisplayFormatUtil.displayHTML( prmXRulesFICODataM.getReasonCode1())%></td>
										<td class="Bigtodotext3"><%=ORIGDisplayFormatUtil.displayHTML(origXrulesUtil.getFicoErrorDesciption(prmXRulesFICODataM.getReasonCode1()))%>&nbsp;</td> 
									</tr>
								</table>
							</td></tr>
										 	 
						<%}%>
						<%if(prmXRulesFICODataM.getReasonCode2()!=null){%>
							<tr><td align="center" class="gumaygrey2">
								<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									<tr>
										<td class="Bigtodotext3"><%=ORIGDisplayFormatUtil.displayHTML( prmXRulesFICODataM.getReasonCode2())%> </td>
										<td bgcolor="#FFFFFF">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(origXrulesUtil.getFicoErrorDesciption(prmXRulesFICODataM.getReasonCode2()))%> &nbsp;</td> 
									</tr>
								</table>
							</td></tr>	
						<%}%> 
						<%if(prmXRulesFICODataM.getReasonCode3()!=null){%>	 
							<tr><td align="center" class="gumaygrey2">
								<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									<tr>
										<td bgcolor="#FFFFFF">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML( prmXRulesFICODataM.getReasonCode3())%> </td>
										<td bgcolor="#FFFFFF">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(origXrulesUtil.getFicoErrorDesciption(prmXRulesFICODataM.getReasonCode3()))%> &nbsp;</td> 
									</tr>
								</table>
							</td></tr>	
						<%}%> 	
						<%if(prmXRulesFICODataM.getReasonCode4()!=null){%> 
							<tr><td align="center" class="gumaygrey2">
								<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									<tr>
										<td bgcolor="#FFFFFF">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML( prmXRulesFICODataM.getReasonCode4())%> </td>
										<td bgcolor="#FFFFFF">&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(origXrulesUtil.getFicoErrorDesciption(prmXRulesFICODataM.getReasonCode4()))%> &nbsp;</td> 
									</tr>
								</table>
							</td></tr>
						<%}%>	 	 
							<tr><td align="center" class="gumaygrey2">
								<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									<tr>
										<td colspan="2" align="center" bgcolor="#FFFFFF">&nbsp; </td>
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

<%//set current screen to main Form
			com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager = (com.eaf.j2ee.pattern.control.ScreenFlowManager) request
					.getSession(true).getAttribute("screenFlowManager");
			screenFlowManager.setCurrentScreen("MAIN_APPFORM");

		%>
</BODY>
</HTML>
