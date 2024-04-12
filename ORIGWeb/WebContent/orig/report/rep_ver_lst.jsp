<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.LoanDataM" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM"%>
<%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM"%>
<%@ page import="com.eaf.xrules.shared.model.XRulesBlacklistDataM "%>
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil"%>
<%@ page import="com.eaf.ncb.model.NCBReqRespConsentDataM"%>
 


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/> 
<html>
<HEAD>
<% 	
	ORIGFormUtil formUtil = new ORIGFormUtil();
    ORIGUtility utility = new ORIGUtility();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = "EDIT";
	MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();	
	//ORIGUtility utility = new ORIGUtility();
	//Vector officeVect = utility.loadCacheByName("OfficeInformation");
	 String appNo=request.getParameter( "rpt_ApplicationNo");
  	 ApplicationDataM applicationDataM=(ApplicationDataM)session.getAttribute("applicationVerification"); 	
	 if(applicationDataM == null){
		 applicationDataM = new ApplicationDataM();
  	 }
     //PersonalInfoDataM  personalInfoDataM=(PersonalInfoDataM)session.getAttribute("personalApplicatinVerification");
     //session.removeAttribute("personalApplicatinVerification");
     //XRulesVerificationResultDataM xRulesVerification=null;
     //if(personalInfoDataM!=null){
      // xRulesVerification=personalInfoDataM.getXrulesVerification();
     //} 
     String formSearh = (String) request.getSession().getAttribute("formSearh");
     OrigXRulesUtil oricXrulesUtil=OrigXRulesUtil.getInstance();
     String statusComplete="Complete"; 
     String statusIncomplete="Incomplete"; 
     String statusError="Error  ";
     String statusNA="N/A";
     Vector ncbUserLog=(Vector)session.getAttribute("ncbUserLog");
     if(ncbUserLog==null){ncbUserLog=new Vector();}
%>  
<TITLE></TITLE>
</HEAD>
<BODY>
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR>
		<TD vAlign=top>
			<TABLE  cellSpacing=0 cellPadding=0  width="100%" align=left border="0">
	          	<TR><TD class="sidebar8">
					<TABLE cellSpacing=0 cellPadding=0 width="100%" align="left"  border="0" bgcolor="FFFFFF">	
						<tr>
							<td class="sidebar9">
								<table cellspacing=0 cellpadding=0 width="100%" align=center border=0 height="20" >
									<tr><td align="right" width="97%">
										<input name="search" type="button" class="button_text" value="search" onclick="searchReport();">
									</td><td width="3%">&nbsp;
									</td></tr>
									<tr class="sidebar10"> 
										<td align="center" colspan="2">
											<table width="50%" align=center cellpadding="0" cellspacing="1" border="0">
											   <tr> 
													<td class="textColS" width="142">Application No. :<FONT color="red">*</FONT></td>
													<TD class="inputCol" width="552"><%=ORIGDisplayFormatUtil.displayInputTag(appNo,ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"25","rpt_ApplicationNo","textbox","") %></TD>
												</TR>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</TABLE>
				</td>
			</tr>
		</TABLE>
		</td>
	</tr>
</TABLE>
											

<!-- initial form param -->
<!-- form action="FrontController" method="post" name="frmrefresh">
	<input type="hidden" name="action" value="RefreshReportPage">
	<input type="hidden" name="P_CHANNEL" value=""> 
    <input type="hidden" name="P_PRODUCT" value=""> 
    <input type="hidden" name="P_ORG_ID" value=""> 
    <input type="hidden" name="page" value="RPT_VER_LST">
</form-->
<% if ( formSearh!=null){
   String xrulesUpdateUser="";
   String xrulesPostion=""; 
%>        
<%    
  Vector  vPersonalInfos=new Vector();   
  PersonalInfoDataM personaApplicant=utility.getPersonalInfoByType(applicationDataM,OrigConstant.PERSONAL_TYPE_APPLICANT);   
   if(personaApplicant!=null){    
    vPersonalInfos.add(personaApplicant);
   Vector VPersonalGuarantor=utility.getVectorPersonalInfoByType(applicationDataM,OrigConstant.PERSONAL_TYPE_GUARANTOR);
   if(VPersonalGuarantor!=null&&VPersonalGuarantor.size()>0){
   vPersonalInfos.addAll(VPersonalGuarantor);
   }
   for(int personalIndex=0;personalIndex<vPersonalInfos.size();personalIndex++){
   PersonalInfoDataM personalIfoDataM=(PersonalInfoDataM)vPersonalInfos.get(personalIndex); 
   int personalSeq=personalIfoDataM.getPersonalSeq();
   XRulesVerificationResultDataM  xRulesVerification=personalIfoDataM.getXrulesVerification();
%>

<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
	<tr><td class="TableHeader">
		<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
			<tr>
				<td class="Bigtodotext3" width="25%"><B><%=utility.getPersonalTypeDesc( personalIfoDataM.getPersonalType()) %>&nbsp;</b></td> 
				<td class="Bigtodotext3"><b>&nbsp;<%=personalIfoDataM.getThaiFirstName() %>&nbsp;<%=personalIfoDataM.getThaiLastName() %></b></td>
			</tr>
		</table>
	</td></tr>
	<tr><td class="TableHeader">
		<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
			<tr>
				<td class="Bigtodotext3" width="15%" align="center">Verification List </td>
			    <td class="Bigtodotext3" width="13%" align="center">Verification List Status </td>
			    <td class="Bigtodotext3" width="10%" align="center">Result</td>
			    <td class="Bigtodotext3" width="13%" align="center"> Date/Time </td>
			    <td class="Bigtodotext3" width="12%" align="center">CMR Code </td>
			    <td class="Bigtodotext3" width="12%" align="center">DE Code </td>
			    <td class="Bigtodotext3" width="12%" align="center">UW Code </td>
			    <td class="Bigtodotext3" width="13%" align="center">&nbsp;</td>
			</tr>
		</table>
	</td></tr>
  <%if(xRulesVerification !=null){ 
  %>
  <!-- NCB ------------------------------------------------------------------------>
  <tr><td align="center" class="gumaygrey2">
	<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
		<tr bgcolor="white">
			<td class="jobopening2" width="15%">NCB</td>
    		<td class="jobopening2" width="13%"><%
			    boolean ncbEnable=false;
			    if(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalIfoDataM.getCustomerType())&&OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalIfoDataM.getPersonalType()) ){
			      ncbEnable=true;
			    }
			    if(ncbEnable){
				    if(xRulesVerification.getNCBColor()!=null){
				      if( OrigConstant.NCBcolor.RED.equals(xRulesVerification.getNCBResult())
				      ||OrigConstant.NCBcolor.ORANGE.equals(xRulesVerification.getNCBResult())
				      ||OrigConstant.NCBcolor.GREEN.equals(xRulesVerification.getNCBResult())
				      ||OrigConstant.NCBcolor.BLACK.equals(xRulesVerification.getNCBResult())             
				      ||OrigConstant.NCBcolor.DARKBLUE.equals(xRulesVerification.getNCBResult())
				       ){
				       out.print(statusComplete);
				      }else{
				       out.print(statusError+xRulesVerification.getNCBResult());
				      }
				      
				    }else{
				      out.print(statusIncomplete);
				    }    
				 }else{
			     	out.print(statusNA);
			     }
     %>    </td>
		    <td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayHTML(xRulesVerification.getNCBColor()) %></td>
		    <td class="jobopening2" width="13%"><%=ORIGDisplayFormatUtil.dateTimetoString(ORIGDisplayFormatUtil.parseEngToThaiDate( xRulesVerification.getNcbUpdateDate())) %> </td>
		    <% 
		     //xrulesUpdateUser=xRulesVerification.getNcbUpdateBy();
		     //xrulesPostion=oricXrulesUtil.getUserPosition(xrulesUpdateUser);
		      %>
		    <td class="jobopening2" width="12%"><%
		     int userCount=0;
		    if(OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalIfoDataM.getPersonalType())){       
		    for(int i=0;i<ncbUserLog.size();i++){
		        NCBReqRespConsentDataM  prmNCBReqRespConsentDataM=(NCBReqRespConsentDataM)ncbUserLog.get(i);         
		         xrulesUpdateUser=prmNCBReqRespConsentDataM.getUpdateBy();
		         xrulesPostion=prmNCBReqRespConsentDataM.getUpdateRole(); 
		        if( OrigConstant.ROLE_CMR.equals(xrulesPostion)){
		           if(userCount>0){out.print(",");}
		           out.print(xrulesUpdateUser) ;
		           userCount++;     
		        }
		    }
		    }
		    %></td>
		    <td class="jobopening2" width="12%"><%
		        if(OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalIfoDataM.getPersonalType())){
		        userCount=0;
		        for(int i=0;i<ncbUserLog.size();i++){
		        NCBReqRespConsentDataM  prmNCBReqRespConsentDataM=(NCBReqRespConsentDataM)ncbUserLog.get(i);         
		        xrulesUpdateUser=prmNCBReqRespConsentDataM.getUpdateBy();
		        xrulesPostion=prmNCBReqRespConsentDataM.getUpdateRole();//oricXrulesUtil.getUserPosition(xrulesUpdateUser);
		       if( OrigConstant.ROLE_DE.equals(xrulesPostion)){
		          if(userCount>1){out.print(",");}
		          out.print(xrulesUpdateUser); 
		          userCount++;
		          }
		       }
		       }
		       %></td>
		    <td class="jobopening2" width="12%"><%
		        userCount=0;
		        if(OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalIfoDataM.getPersonalType())){
		        for(int i=0;i<ncbUserLog.size();i++){
		        NCBReqRespConsentDataM  prmNCBReqRespConsentDataM=(NCBReqRespConsentDataM)ncbUserLog.get(i);         
		        xrulesUpdateUser=prmNCBReqRespConsentDataM.getUpdateBy();
		        //xrulesPostion=oricXrulesUtil.getUserPosition(xrulesUpdateUser);
		         xrulesPostion=prmNCBReqRespConsentDataM.getUpdateRole();
		        if( OrigConstant.ROLE_UW.equals(xrulesPostion)){
		           if(userCount>1){out.print(",");}
		           out.print(xrulesUpdateUser);
		                userCount++;
		         }
		        }
		        }
		        %></td>
		    <td class="jobopening2" width="13%">
		    <%if(ncbEnable){ %>
		    	<input type="button" name="viewResult" value="View Result"  class="button_text" 
		    		onClick="openXrulesPopup(<%=XRulesConstant.ServiceID.NCB%>,'<%=personalIfoDataM.getPersonalType()%>',<%=personalSeq%>)" >
		    <%} %>
		    </td>
		</tr>
  <!-- Blackist Customer ------------------------------------------------------------->
    	<tr bgcolor="#F4F4F4">
		    <td class="jobopening2">Blacklist Customer</td>
		    <td class="jobopening2"><% 
		    if(xRulesVerification.getBLResult()!=null){
		       if(XRulesConstant.ExecutionResultString.RESULT_PASS.equals(xRulesVerification.getBLResult())
		         ||XRulesConstant.ExecutionResultString.RESULT_FAIL.equals(xRulesVerification.getBLResult())
		         )
		       {
		        out.print(statusComplete);
		        }else{
		        out.print(statusError+xRulesVerification.getBLResult());
		        }
		     }else{
		      out.print(statusIncomplete);
		     }    
		     %></td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.displayHTML(xRulesVerification.getBLResult()) %></td>     
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.dateTimetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(xRulesVerification.getBlacklistUpdateDate())) %></td>
		       <% 
		     xrulesUpdateUser=xRulesVerification.getBlacklistUpdateBy();      
		     xrulesUpdateUser=ORIGDisplayFormatUtil.displayHTML(xrulesUpdateUser);
		      %>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_CMR.equals(xRulesVerification.getBlacklistCustomerUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_DE.equals(xRulesVerification.getBlacklistCustomerUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_UW.equals(xRulesVerification.getBlacklistCustomerUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2">
		    	<input type="button" name="viewResult" value="View Result"  class="button_text" 
		    		onClick="openXrulesPopup(<%=XRulesConstant.ServiceID.BLACKLIST%>,'<%=personalIfoDataM.getPersonalType()%>',<%=personalSeq%>)"></td>
	  	</tr>
   <!-- Blackist Car --------------------------------------------------------------------->
	    <tr bgcolor="white">
		    <td class="jobopening2">Blacklist Car</td>
		    <td class="jobopening2"><%
		    if(OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalIfoDataM.getPersonalType()) ){
		    if(xRulesVerification.getBLVehicleResult()!=null){
		      if(XRulesConstant.ExecutionResultString.RESULT_PASS.equals(xRulesVerification.getBLVehicleResult())
		         ||XRulesConstant.ExecutionResultString.RESULT_FAIL.equals(xRulesVerification.getBLVehicleResult())
		         )
		       {
		        out.print(statusComplete);
		        }else{
		        out.print(statusError+xRulesVerification.getBLVehicleResult());
		        }
		     }else{
		      out.print(statusIncomplete);
		     }    
		     } else{
		       out.print(statusNA);
		     }
		     %>    
		    </td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.displayHTML(xRulesVerification.getBLVehicleResult()) %>&nbsp;</td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.dateTimetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(xRulesVerification.getBlacklistVehcieUpdateDate()))%></td>
		       <% 
		        xrulesUpdateUser=xRulesVerification.getBlacklistVehicleUpdateBy();
		        xrulesUpdateUser=ORIGDisplayFormatUtil.displayHTML(xrulesUpdateUser);   
		      %>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_CMR.equals(xRulesVerification.getBlacklistVehicleUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_DE.equals(xRulesVerification.getBlacklistVehicleUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_UW.equals(xRulesVerification.getBlacklistVehicleUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if(OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalIfoDataM.getPersonalType()) ){ %><input type="button" name="viewResult" value="View Result"  class="button_text" onClick="openXrulesPopup(<%=XRulesConstant.ServiceID.BLACKLIST_VEHICLE%>,'<%=personalIfoDataM.getPersonalType()%>',<%=personalSeq%>)"><%} %></td>
	  	</tr>
	     <!-- Dedup ---------------------------------------------------------------------------->
	    <tr bgcolor="#F4F4F4">
		    <td class="jobopening2">Dedup</td>
		    <td class="jobopening2"><%if(xRulesVerification.getDedupResult()!=null){
		        if(XRulesConstant.ExecutionResultString.RESULT_PASS.equals(xRulesVerification.getDedupResult())
		         ||XRulesConstant.ExecutionResultString.RESULT_FAIL.equals(xRulesVerification.getDedupResult())
		         )
		       {
		        out.print(statusComplete);
		        }else{
		        out.print(statusError+xRulesVerification.getDedupResult());
		        }
		     }else{
		      out.print(statusIncomplete);
		     }    
		     %>    </td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.displayHTML(xRulesVerification.getDedupResult()) %>&nbsp;</td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.dateTimetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(xRulesVerification.getDedupUpdateDate())) %></td>
		        <% 
		     xrulesUpdateUser=xRulesVerification.getDedupUpdateBy();   
		     xrulesUpdateUser=ORIGDisplayFormatUtil.displayHTML(xrulesUpdateUser);  
		      %>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_CMR.equals(xRulesVerification.getDedupUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_DE.equals(xRulesVerification.getDedupUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_UW.equals(xRulesVerification.getDedupUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><input type="button" name="viewResult" value="View Result"  class="button_text" onClick="openXrulesPopup(<%=XRulesConstant.ServiceID.DEDUP%>,'<%=personalIfoDataM.getPersonalType()%>',<%=personalSeq%>)"></td>
	  	</tr>
	     <!-- Dedup Vehicle ------------------------------------------------------------->
	    <tr bgcolor="white">
		    <td class="jobopening2">Vehicle Duplicate</td>
		    <td class="jobopening2"><%
		     if(OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalIfoDataM.getPersonalType()) ){
		    	if(xRulesVerification.getDedupVehicleResult()!=null){
			        if(XRulesConstant.ExecutionResultString.RESULT_PASS.equals(xRulesVerification.getDedupVehicleResult())
		        		 ||XRulesConstant.ExecutionResultString.RESULT_FAIL.equals(xRulesVerification.getDedupVehicleResult())
		    	    )  {
		    		    out.print(statusComplete);
			        }else{
		    	    out.print(statusError+xRulesVerification.getDedupVehicleResult());
		        	}
			     }else{
		    	  out.print(statusIncomplete);
			     }    
		     }else{
		      out.print(statusNA);
		     }
		     %>    </td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.displayHTML(xRulesVerification.getDedupVehicleResult()) %>&nbsp;</td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.dateTimetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(xRulesVerification.getDupVehicleUpdateDate())) %></td>
		      <% 
		     xrulesUpdateUser=xRulesVerification.getDupVehicleUpdateBy();
		     xrulesUpdateUser=ORIGDisplayFormatUtil.displayHTML(xrulesUpdateUser);
		      %>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_CMR.equals(xRulesVerification.getDedupVehicleUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_DE.equals(xRulesVerification.getDedupVehicleUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_UW.equals(xRulesVerification.getDedupVehicleUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><% if(OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalIfoDataM.getPersonalType()) ){%><input type="button" name="viewResult" value="View Result"  class="button_text" onClick="openXrulesPopup(<%=XRulesConstant.ServiceID.DUP_VEHICLE%>,'<%=personalIfoDataM.getPersonalType()%>',<%=personalSeq%>)"><%} %></td>
	  	</tr>
	     <!-- LPM ----------------------------------------------------------------------------->
	    <tr bgcolor="#F4F4F4">
		    <td class="jobopening2">LPM</td>
		    <td class="jobopening2"><%if(xRulesVerification.getLPMResult()!=null){
		        if(XRulesConstant.ExecutionResultString.RESULT_PASS.equals(xRulesVerification.getLPMResult())
		         ||XRulesConstant.ExecutionResultString.RESULT_FAIL.equals(xRulesVerification.getLPMResult())
		         )   {
		       		out.print(statusComplete);
		        }else{
		        	out.print(statusError+xRulesVerification.getDedupVehicleResult());
		        }
		     }else{
		      out.print(statusIncomplete);
		     }    
		     %>    </td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.displayHTML(xRulesVerification.getLPMResult()) %>&nbsp;</td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.dateTimetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(xRulesVerification.getLpmUpdateDate())) %></td>
		        <% 
		     xrulesUpdateUser=xRulesVerification.getLpmUpdateBy();
		     xrulesUpdateUser=ORIGDisplayFormatUtil.displayHTML(xrulesUpdateUser);
		      %>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_CMR.equals(xRulesVerification.getLpmUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_DE.equals(xRulesVerification.getLpmUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_UW.equals(xRulesVerification.getLpmUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><input type="button" name="viewResult" value="View Result"  class="button_text" 
		    	onClick="openXrulesPopup(<%=XRulesConstant.ServiceID.LPM%>,'<%=personalIfoDataM.getPersonalType()%>',<%=personalSeq%>)"></td>
	       <!-- Policy Rule ---------------------------------------------------------------------->
	    </tr>
	    <tr bgcolor="white">
		    <td class="jobopening2">Policy Rules</td>
		    <td class="jobopening2"><%  if(OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalIfoDataM.getPersonalType()) ){
				    if(xRulesVerification.getPolicyRulesResult()!=null){
				       if(XRulesConstant.ExecutionResultString.RESULT_PASS.equals(xRulesVerification.getPolicyRulesResult())
					         ||XRulesConstant.ExecutionResultString.RESULT_FAIL.equals(xRulesVerification.getPolicyRulesResult())
				         )   {
				        out.print(statusComplete);
				        }else{
				        out.print(statusError+xRulesVerification.getPolicyRulesResult());
					        }
					     }else{
					      out.print(statusIncomplete);
					     }    
					}else{
					   out.print(statusNA);
					}     
		     %>    </td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.displayHTML(xRulesVerification.getPolicyRulesResult()) %>&nbsp;</td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.dateTimetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(xRulesVerification.getPolicyRulesUpdateDate())) %></td>
		        <% 
		     xrulesUpdateUser=xRulesVerification.getPolicyRulesUpdateBy();
		
		      %>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_CMR.equals(xRulesVerification.getPolicyRulesUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_DE.equals(xRulesVerification.getPolicyRulesUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_UW.equals(xRulesVerification.getPolicyRulesUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><input type="button" name="viewResult" value="View Result"  class="button_text" onClick="openXrulesPopup(<%=XRulesConstant.ServiceID.POLICYRULES%>,'<%=personalIfoDataM.getPersonalType()%>','<%=personalIfoDataM.getPersonalType()%>',<%=personalSeq%>)"></td>
	  	</tr>    
	  <!-- Debt Amount ------------------------------------------------------------------------>
	    <tr bgcolor="#F4F4F4">
		    <td class="jobopening2">Debt Amount</td>
		    <td class="jobopening2"><%
		      if(OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalIfoDataM.getPersonalType()) ){
		    if(xRulesVerification.getDebtAmountResult()!=null){
		      //if( xRulesVerification.getDebtAmountResult().length()<10) {
		        out.print(statusComplete);
		        //}else{
		       // out.print(statusError+xRulesVerification.getDEBT_BDResult());
		       // }
		     }else{
		      out.print(statusIncomplete);
		     }    
		     }else{
		      out.print(statusNA);
		     }
		     %>    </td>
		    <td class="jobopening2"><% if(OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalIfoDataM.getPersonalType()) ){
		        out.print(ORIGDisplayFormatUtil.displayHTML( ORIGDisplayFormatUtil.displayCommaNumber(xRulesVerification.getDebtAmountResult()))); 
		       }%>&nbsp;</td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.dateTimetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(xRulesVerification.getDebtAmountUpdateDate())) %></td>
		        <% 
		     xrulesUpdateUser=xRulesVerification.getDebtAmountUpdateBy();     
		     xrulesUpdateUser=ORIGDisplayFormatUtil.displayHTML(xrulesUpdateUser); 
		      %>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_CMR.equals(xRulesVerification.getDebtAmtUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_DE.equals(xRulesVerification.getDebtAmtUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_UW.equals(xRulesVerification.getDebtAmtUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2">&nbsp;</td>
	  	</tr> 
	     <!-- Debt Burdent ----------------------------------------------------------------------->
	    <tr bgcolor="white">
		    <td class="jobopening2">DSCR</td>
		    <td class="jobopening2"><%
		      if(OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalIfoDataM.getPersonalType()) ){
		    if(xRulesVerification.getDEBT_BDResult()!=null){
		      if( xRulesVerification.getDEBT_BDResult().length()<10)    {
		        out.print(statusComplete);
		        }else{
		        out.print(statusError+xRulesVerification.getDEBT_BDResult());
		        }
		     }else{
		      out.print(statusIncomplete);
		     }    
		     }else{
		      out.print(statusNA);
		     }
		     %>    </td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.displayHTML(xRulesVerification.getDEBT_BDResult()) %>&nbsp;</td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.dateTimetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(xRulesVerification.getDebtBdUpdateDate())) %></td>
		        <%   xrulesUpdateUser=xRulesVerification.getDebtBdUpdateBy(); 
		     		 xrulesUpdateUser=ORIGDisplayFormatUtil.displayHTML(xrulesUpdateUser);
		        %>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_CMR.equals(xRulesVerification.getDebtbdUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_DE.equals( xRulesVerification.getDebtbdUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_UW.equals(xRulesVerification.getDebtbdUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2">&nbsp;</td>
	  	</tr>
	     <!-- Existing Customer -------------------------------------------------------------------------->
	    <tr bgcolor="#F4F4F4">
		    <td class="jobopening2">Existing Customer</td>
		    <td class="jobopening2"><%if(xRulesVerification.getExistCustResult()!=null){
		      if(XRulesConstant.ExecutionResultString.RESULT_FOUND.equals(xRulesVerification.getExistCustResult())
		         ||XRulesConstant.ExecutionResultString.RESULT_NOT_FOUND.equals(xRulesVerification.getExistCustResult())
		         )
		       {
		        out.print(statusComplete);
		        }else{
		        out.print(statusError+xRulesVerification.getExistCustResult());
		        }
		     }else{
		      out.print(statusIncomplete);
		     }    
		     %>    </td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.displayHTML(xRulesVerification.getExistCustResult()) %>&nbsp;</td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.dateTimetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(xRulesVerification.getExistingCustUpdateDate())) %></td>
		        <% 
		     xrulesUpdateUser=xRulesVerification.getExistCustUpdateBy();  
		     xrulesUpdateUser=ORIGDisplayFormatUtil.displayHTML(xrulesUpdateUser);  
		      %>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_CMR.equals(xRulesVerification.getExistingCustomerUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_DE.equals(xRulesVerification.getExistingCustomerUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_UW.equals(xRulesVerification.getExistingCustomerUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><input type="button" name="viewResult" value="View Result"  class="button_text" onClick="openXrulesPopup(<%=XRulesConstant.ServiceID.EXIST_CUSTOMER%>,'<%=personalIfoDataM.getPersonalType()%>',<%=personalSeq%>)"></td>
	  </tr>  
	    <!-- FICO ---------------------------------------------------------------------->
	    <tr bgcolor="white">
		    <td class="jobopening2">FICO</td>
		    <td class="jobopening2"><%
		     if(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalIfoDataM.getCustomerType())&&OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalIfoDataM.getPersonalType()) ){
		    if(xRulesVerification.getFicoResult()!=null){       
		    if(OrigConstant.Scoring.SCORING_ACCEPT.equals(xRulesVerification.getFicoResult())
		         ||OrigConstant.Scoring.SCORING_REFER.equals(xRulesVerification.getFicoResult())
		         ||OrigConstant.Scoring.SCORING_REJECT.equals(xRulesVerification.getFicoResult()) )         
		         {      
		           out.print(statusComplete);      
		         }else{
		           out.print(statusError+xRulesVerification.getExistCustResult());
		         }      
		        } else{
		      out.print(statusIncomplete);
		     }    
		     }else{
		      out.print(statusNA);
		     }
		     %>    </td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.displayHTML(xRulesVerification.getFicoResult()) %>&nbsp;</td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.dateTimetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(xRulesVerification.getFicoUpdateDate())) %></td>    
		        <% 
		     xrulesUpdateUser=xRulesVerification.getFicoUpdateBy();
		     xrulesUpdateUser=ORIGDisplayFormatUtil.displayHTML(xrulesUpdateUser);    
		      %>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_CMR.equals(xRulesVerification.getFicoUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_DE.equals(xRulesVerification.getFicoUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_UW.equals(xRulesVerification.getFicoUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%  if(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalIfoDataM.getCustomerType())&&OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalIfoDataM.getPersonalType()) ){ %><input type="button" name="viewResult" value="View Result"  class="button_text" onClick="openXrulesPopup(<%=XRulesConstant.ServiceID.FICO%>,'<%=personalIfoDataM.getPersonalType()%>',<%=personalSeq%>)"><%} %></td>
	  	</tr>  
	  <!-- Phone Ver ---------------------------------------------------------------------->
	    <tr bgcolor="#f4f4f4">
		    <td class="jobopening2">PhoneVerification</td>
		    <td class="jobopening2"><%
		     if(OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalIfoDataM.getPersonalType()) ){
		    if(xRulesVerification.getPhoneVerResult()!=null){            
		           out.print(statusComplete);                   
		        } else{
		      out.print(statusIncomplete);
		     }    
		     }else{
		      out.print(statusNA);
		     }
		     %>    </td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.displayHTML(xRulesVerification.getPhoneVerResult()) %>&nbsp;</td>
		    <td class="jobopening2"><%=ORIGDisplayFormatUtil.dateTimetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(xRulesVerification.getPhoneVerUpdateDate())) %></td>    
		        <% 
		          xrulesUpdateUser=xRulesVerification.getPhoneVerUpdateBy(); 
		          xrulesUpdateUser=ORIGDisplayFormatUtil.displayHTML(xrulesUpdateUser);
		      %>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_CMR.equals(xRulesVerification.getPhoneVerUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_DE.equals(xRulesVerification.getPhoneVerUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if( OrigConstant.ROLE_UW.equals(xRulesVerification.getPhoneVerUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"><%if(OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(personalIfoDataM.getPersonalType()) ){ %><input type="button" name="viewResult" value="View Result"  class="button_text" onClick="openXrulesPopup(<%=XRulesConstant.ServiceID.PHONE_VER%>,'<%=personalIfoDataM.getPersonalType()%>',<%=personalSeq%>)"><% }%></td>
	  </tr>         
	   <!-- Khon thai ---------------------------------------------------------------------->
	    <tr bgcolor="white">
		    <td class="jobopening2"> Khothai</td>
		    <td class="jobopening2"> <% if(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalIfoDataM.getCustomerType())){
		  			  if(xRulesVerification.getKhonThaiResult()!=null){            
		      		     out.print(statusComplete);                   
		     		   } else{
		   			   out.print(statusIncomplete);
		   			  }    
		             }else{
		    		  out.print(statusNA);     
		   		  }
		     %>    </td>
		    <td class="jobopening2"> <%=ORIGDisplayFormatUtil.displayHTML(xRulesVerification.getKhonThaiResult()) %> </td>
		    <td class="jobopening2"> <%=ORIGDisplayFormatUtil.dateTimetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(xRulesVerification.getKhonthaiUpdateDate())) %></td>    
		        <% 
		     xrulesUpdateUser=xRulesVerification.getKhonthaiUpdateBy();   
		     xrulesUpdateUser=ORIGDisplayFormatUtil.displayHTML(xrulesUpdateUser);   
		      %>
		    <td class="jobopening2"> <%if( OrigConstant.ROLE_CMR.equals(xRulesVerification.getKhonthaiUpdteRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"> <%if( OrigConstant.ROLE_DE.equals(xRulesVerification.getKhonthaiUpdteRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"> <%if( OrigConstant.ROLE_UW.equals(xRulesVerification.getKhonthaiUpdteRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2">&nbsp;<!-- <input type="button" name="viewResult" value="View Result"  class="button_text" onClick="openXrulesPopup(<%=XRulesConstant.ServiceID.FICO%>)"> --></td>
	  </tr>  
	  <!-- Thai registragion ---------------------------------------------------------------------->
	    <tr bgcolor="#f4f4f4">
		    <td class="jobopening2"> Thai Registration</td>
		    <td class="jobopening2"> <%
		     if(OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(personalIfoDataM.getCustomerType())){
		     	if(xRulesVerification.getThaiRegistrationResult()!=null){            
		           out.print(statusComplete);                   
		    	    } else{
		   		   out.print(statusIncomplete);
		   		  }    
		    		 }else{
		    		  out.print(statusNA);     
		   			  }
		     
		     %>    </td>
		    <td class="jobopening2"> <%=ORIGDisplayFormatUtil.displayHTML(xRulesVerification.getThaiRegistrationResult()) %> </td>
		    <td class="jobopening2"> <%=ORIGDisplayFormatUtil.dateTimetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(xRulesVerification.getThaiRegistrationUpdateDate())) %></td>    
		        <% 
		          xrulesUpdateUser=xRulesVerification.getThaiRegistrationUpdateBy();    
		          xrulesUpdateUser=ORIGDisplayFormatUtil.displayHTML(xrulesUpdateUser);      
		      %>
		    <td class="jobopening2"> <%if( OrigConstant.ROLE_CMR.equals(xRulesVerification.getKhonthaiUpdteRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"> <%if( OrigConstant.ROLE_DE.equals(xRulesVerification.getKhonthaiUpdteRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"> <%if( OrigConstant.ROLE_UW.equals(xRulesVerification.getKhonthaiUpdteRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2">&nbsp;<!--<input type="button" name="viewResult" value="View Result"  class="button_text" onClick="openXrulesPopup(<%=XRulesConstant.ServiceID.FICO%>)">--></td>
	  </tr>  
	    <!-- BOL ---------------------------------------------------------------------->
	    <tr bgcolor="white">
		    <td class="jobopening2"> BOL</td>
		    <td class="jobopening2"> <%
		     if(OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(personalIfoDataM.getCustomerType())){
		   		 if(xRulesVerification.getBOLResult()!=null){            
		       	    out.print(statusComplete);                   
		     		   } else{
		     		 out.print(statusIncomplete);
		    		 }
		         }else{
		           out.print(statusNA);     
		         }
		     
		     %>    </td>
		    <td class="jobopening2"> <%=ORIGDisplayFormatUtil.displayHTML(xRulesVerification.getBOLResult()) %> </td>
		    <td class="jobopening2"> <%=ORIGDisplayFormatUtil.dateTimetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(xRulesVerification.getBolUpdateDate())) %></td>    
		        <% 
		          xrulesUpdateUser=xRulesVerification.getBolUpdateBy();  
		          xrulesUpdateUser=ORIGDisplayFormatUtil.displayHTML(xrulesUpdateUser);         
		      %>
		    <td class="jobopening2"> <%if( OrigConstant.ROLE_CMR.equals(xRulesVerification.getBolUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"> <%if( OrigConstant.ROLE_DE.equals(xRulesVerification.getBolUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"> <%if( OrigConstant.ROLE_UW.equals(xRulesVerification.getBolUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2">&nbsp;<!--<input type="button" name="viewResult" value="View Result"  class="button_text" onClick="openXrulesPopup(<%=XRulesConstant.ServiceID.FICO%>)"--></td>
	  </tr>   
	  <!-- Yellows Pages ---------------------------------------------------------------------->
	    <tr bgcolor="#f4f4f4">
		    <td class="jobopening2"> YellowPages</td>
		    <td class="jobopening2"> <%if(xRulesVerification.getYellowPageResult()!=null){            
		           out.print(statusComplete);                   
		        } else{
		      out.print(statusIncomplete);
		     }    
		     %>    </td>
		    <td class="jobopening2"> <%=ORIGDisplayFormatUtil.displayHTML(xRulesVerification.getYellowPageResult()) %> </td>
		    <td class="jobopening2"> <%=ORIGDisplayFormatUtil.dateTimetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(xRulesVerification.getYellowsPagesUpdateDate())) %></td>    
		        <% 
		          xrulesUpdateUser=xRulesVerification.getYellowsPagesUpdateBy();
		          xrulesPostion=oricXrulesUtil.getUserPosition(xrulesUpdateUser);
		          xrulesUpdateUser=ORIGDisplayFormatUtil.displayHTML(xrulesUpdateUser);
		      %>
		    <td class="jobopening2"> <%if( OrigConstant.ROLE_CMR.equals(xRulesVerification.getYellowsPageUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"> <%if( OrigConstant.ROLE_DE.equals(xRulesVerification.getYellowsPageUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"> <%if( OrigConstant.ROLE_UW.equals(xRulesVerification.getYellowsPageUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2">&nbsp;<!--<input type="button" name="viewResult" value="View Result"  class="button_text" onClick="openXrulesPopup(<%=XRulesConstant.ServiceID.FICO%>)"--></td>
	  </tr>     
	   <!-- Phone Book ---------------------------------------------------------------------->
	    <tr bgcolor="white">
		    <td class="jobopening2"> PhoneBook</td>
		    <td class="jobopening2"> <%if(xRulesVerification.getPhoneBookResult()!=null){            
		           out.print(statusComplete);                   
		        } else{
		      out.print(statusIncomplete);
		     }    
		     %>    </td>
		    <td class="jobopening2"> <%=ORIGDisplayFormatUtil.displayHTML(xRulesVerification.getPhoneBookResult()) %> </td>
		    <td class="jobopening2"> <%=ORIGDisplayFormatUtil.dateTimetoString(ORIGDisplayFormatUtil.parseEngToThaiDate(xRulesVerification.getPhonebookUpdateDate())) %></td>    
		        <% 
		          xrulesUpdateUser=xRulesVerification.getPhonebookUpdateBy();
		          xrulesPostion=oricXrulesUtil.getUserPosition(xrulesUpdateUser);
		      %>
		    <td class="jobopening2"> <%if( OrigConstant.ROLE_CMR.equals(xRulesVerification.getPhoneBookUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"> <%if( OrigConstant.ROLE_DE.equals(xRulesVerification.getPhoneBookUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2"> <%if( OrigConstant.ROLE_UW.equals(xRulesVerification.getPhoneBookUpdateRole())){out.print(xrulesUpdateUser) ;}%></td>
		    <td class="jobopening2">&nbsp;<!--<input type="button" name="viewResult" value="View Result"  class="button_text" onClick="openXrulesPopup(<%=XRulesConstant.ServiceID.FICO%>)"--></td>
	  	</tr>
	  </table>
	</td></tr>              
  <%}else{ %>  
  	<tr><td align="center" class="gumaygrey2">
		<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
		<tr>
	  		<td class="jobopening2" align="center">Results Not Found.</td>
	  	</tr>
		</table> 
	</td></tr>
  <%} %>   
</table>
<%} %> 
 <%}else{%>
<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
	<tr><td align="center" class="gumaygrey2">
		<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
		<tr>
	  		<td class="jobopening2" align="center">Results Not Found.</td>
	  	</tr>
		</table> 
	</td></tr>
</table>  
<%} %> 
<%} %>

<script language="javaScript" type=text/JavaScript>
function searchReport(){
    //alert(appFormName.action);
    if(document.appFormName.rpt_ApplicationNo.value!=""){
	var appForm=document.appFormName;
	appForm.action.value="SearchVerificationListReport";
	appFormName.handleForm.value = "N";
    appFormName.fromSearch.value = "Y";
	appForm.submit();
	}else{
	alert("Please input Application No.");
	}
	//verifyForm();
	//reportForm.target='reportWindow';
	//reportForm.foffice_code.value = appFormName.foffice_code.value;
	//reportForm.submit();
}

var reportWindow;
//-- check fill in form for * --
function verifyForm()
{

	var sourceFrm = document.appFormName;
	if (sourceFrm.f_book_date == null || sourceFrm.f_book_date.value == '' ) 
	{
		alert('Please Select Booking Date From !');
		sourceFrm.f_book_date.focus();
	} 
	else if(sourceFrm.t_book_date == null || sourceFrm.t_book_date.value == '' ) 
	{
		alert('Please Select Booking Date To !');
		sourceFrm.t_book_date.focus();	
	}
	else if(sourceFrm.t_book_date != null && sourceFrm.f_book_date != null)
	{
		var nStart = date2Number(sourceFrm.f_book_date.value); 
		var nEnd = date2Number(sourceFrm.t_book_date.value);
		if (nEnd<nStart && nEnd!=0 && nStart!=0)
		{
			alert('Please Select Booked Date To Equal or Greater Than Booked Date From !');
		}
		else 
		{
			var url = "";
 			var feat = "height=650px,width=850px,top=10px,left=50px,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars=yes";
			window.open(url, "reportWindow", feat);	
			return true;
		}
	}
	return false;
}
function openXrulesPopup( serviceId,reportPersonalType,reportPersonalSeq){
   
 
    var popupWebAction='';
    var popupWidth='1024';
    var popupHeight='300';
    var executeService=serviceId;  
   //alert(executeService);
   switch(executeService)
   {
    case <%=XRulesConstant.ServiceID.NCB%>:
		  popupWebAction='LoadXruleNCBSummaryPopup';
		   popupHeight='655';
      	  break;	  	      
	case <%=XRulesConstant.ServiceID.BLACKLIST%>:
	      popupWebAction='LoadXruleBlacklistCustomerPopup';
      	  break;	  	      	      
	case <%=XRulesConstant.ServiceID.BLACKLIST_VEHICLE%>:
	      popupWebAction='LoadXruleBlacklistVehiclePopup';
      	  break;	  	      	         
	case <%=XRulesConstant.ServiceID.EXIST_CUSTOMER%>:
	      popupWebAction='LoadXruleExistingCustomerPopup';
      	  break;	  	  
    case <%=XRulesConstant.ServiceID.DEDUP%>:        
	      popupWebAction='LoadXrulesDedupPopup';
      	  break;	  	  
	case <%=XRulesConstant.ServiceID.DUP_VEHICLE%>:        
	      popupWebAction='LoadXrulesDupVehiclePopup'
      	  break;	  	  
	case <%=XRulesConstant.ServiceID.LPM%>:
	      popupWebAction='LoadXrulesLPMmainPopup';
      	  break;	  	  
	case <%=XRulesConstant.ServiceID.POLICYRULES%>:
    	  popupHeight='550';
	      popupWebAction='LoadXrulesPolicyRulesPopup';  //&serviceid='+serviceId+'&txtResultName='+resName;
      	  break;	  	  
	case <%=XRulesConstant.ServiceID.DEBTBURDEN%>:	      
	      popupWebAction='LoadXrulesDebtBurdenPopup';
      	  break;	  	  
	case <%=XRulesConstant.ServiceID.FICO%>:
	      popupWebAction='LoadXrulesFICOPopup';
      	  break;	  	  	 
   	case <%=XRulesConstant.ServiceID.PHONE_VER%>:
	      popupWebAction='LoadXrulesPhoneVerPopup';
      	  break;	  	    	  
	default:
      //alert("Not in Case"+executeService)
	}   	 
    loadPopup('xrulePopup',popupWebAction+'&viewFromReport=Y&reportPersonalType='+reportPersonalType+'&reportPersonalSeq='+reportPersonalSeq,popupWidth,popupHeight+",status",'100',getPostionPopupX(popupWidth),'','','');
     
 }
 function getPostionPopupX(popupWidth){   	 
    var frameWidth =  screen.width;
    var frameHeight =  screen.height;	 
	var result=(frameWidth/2)-(popupWidth/2);
	return result;	
 }
</script>

<input type="hidden" name="fromSearch" value="N">


</BODY>
</html>
