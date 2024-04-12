<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.view.form.ORIGSubForm" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGForm" />
<style type="text/css">
#container{
	height: 100%; 
	width: 100%;
}
.pane {
 	display: none; 
 }
</style>
<script language="javascript">	
	var isFirst = false;
// 	window.onbeforeunload = function(){
// 		if((window.event.clientX<0)||(window.event.clientY<0)){	
// 		}
// 	};
	
	var outerLayout,westLayout;
	$(document).ready(function () {		
		outerLayout = $('#container').layout({
				minSize: "35%"
			,	maxSize: "70%"		
			,	west__size:	"50%"	
			,	useStateCookie:	false
		});
		westLayout = $('div.ui-layout-west').layout({
				minSize: 155	
			,	maxSize: 220
			,	center__paneSelector: ".west-center"
			,	south__paneSelector: ".west-south"
			,	south__size: 155
			,	closable:	false
			,	spacing_open: 7
		});
		$(".slide-out").toggle(false);
		$('#action').splitbutton({plain:true});
	});

	
function toggleDiv(divId) {
	$(".slide-out").each(function(index) {
		if( $(this).attr("id")== divId){
			$(this).slideToggle("fast");
		}else{
			if( $(this).is(':visible'))
				$(this).toggle(false);
		}
	});
// 	$("#"+divId).slideToggle("slow");
// 	var showVar = $("#slide-out").css('display');
// 	if(showVar == 'none') {
// 	$("#slide-out").toggle(true);
// 		$("#slide-out").effect("slide",{direction:"up"},1000);
// 	} else {
// 		$("#slide-out").hide("slide",{direction:"down"},1000);
// 	}
}
</script>
<%System.out.println("[application_main.jsp]...Begin"); %>
<div id="container">
<!-- [Append SubForm Begin] ..... -->
<div class="pane ui-layout-center">
<script type="text/javascript" src="js/popcalendar.js"></script>
<span id="errorMessage" class="TextWarningNormal"></span>
<%
	com.eaf.j2ee.pattern.util.ErrorUtil error = new com.eaf.j2ee.pattern.util.ErrorUtil();

	Vector v = ORIGForm.getFormErrors();
	Vector vErrorFields = ORIGForm.getErrorFields();
	
	for(int i=0; i<v.size();i++) {
		String errorMessage = (String)v.elementAt(i);
		out.println("<span class=\"TextWarningNormal\">*&nbsp;"+errorMessage+"</span><br>");
	}


	String personalType = (String) request.getSession().getAttribute("PersonalType");
	ORIGUtility utility = new ORIGUtility();
    PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	}else{
		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	}
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();
	} 
	String customerType = personalInfoDataM.getCustomerType();
	String menu = (String)request.getSession(true).getAttribute("PROPOSAL_MENU");
	String userRole = (String) ORIGUser.getRoles().elementAt(0);
	String searchType = (String)request.getSession().getAttribute("searchType");
	
	String fromMultiApp = (String)request.getSession().getAttribute("fromMultiApp");
	String cancelOnClick = "onclick=\"javascript:cancelClaimApplication()\"";
	if(menu!=null&&menu.equals("Y")){
		cancelOnClick = "onclick=\"javascript:cancelClaimApp()\"";
	} else {
		if ("Y".equals(fromMultiApp)) {
			cancelOnClick ="";
		}	
	}
	ORIGFormUtil formUtil = ORIGFormUtil.getInstance();
	String displayMode = formUtil.getDisplayMode("APPLICANT_SUBFORM", ORIGUser.getRoles(), ORIGForm.getAppForm().getJobState(), ORIGForm.getFormID(), searchType);
	if(OrigConstant.ROLE_PD.equals(userRole)){
		displayMode = formUtil.getDisplayMode("PD_RESULT_SUBFORM", ORIGUser.getRoles(), ORIGForm.getAppForm().getJobState(), ORIGForm.getFormID(), searchType);
		if(personalInfoDataM.getPersonalType().equals(OrigConstant.PERSONAL_TYPE_GUARANTOR)){
			displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
		}
	}else if(OrigConstant.ROLE_XCMR.equals(userRole)){
		displayMode = formUtil.getDisplayMode("CMR_DECISION_SUBFORM", ORIGUser.getRoles(), ORIGForm.getAppForm().getJobState(), ORIGForm.getFormID(), searchType);
	}
	String disabledBtn = "";
	if(!displayMode.equals(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT)){
		disabledBtn = ORIGDisplayFormatUtil.DISABLED;
	}

	ApplicationDataM applicationDataM=ORIGForm.getAppForm();
	String cancelApp = (String)request.getSession().getAttribute("cancelApp");
    boolean showCancelApplication=false;
	if(OrigConstant.ORIG_FLAG_Y.equals(cancelApp)){
  		String jobState=applicationDataM.getJobState();
  	 	if(jobState!=null){
   			showCancelApplication=true;
      		String[] jobStateEndFlow=OrigConstant.JOBSTATE_ENDFLOW;
      		for(int i=0;i<jobStateEndFlow.length;i++){
        		if(jobState.equals(jobStateEndFlow[i])){
            		showCancelApplication=false;
            		break;
        		}
      		}      
      	}
    }
    
	ORIGForm.setErrors(null);
	HashMap clear = new HashMap();
	ORIGForm.setFormErrors(new Vector());
	ORIGForm.setErrorFields(new Vector());
	String currentHeaderForm= "";
			// get All subForm from Hash
			// check with current tab
			// sort Subform put in Vector
			// loop include file
		String currentTab = ORIGForm.getCurrentTab();
		HashMap allSubForms = ORIGForm.getSubForms();		
		Vector allIncludedFiles = formUtil.getSortedFileNameByCurrentTab(currentTab,allSubForms);
			
%>
<div class="htme-body" >
<div class="plMainDiv">
<table width="100%" border=0 cellpadding="0" cellspacing="0" align="left" onkeydown="testKeyDown()">
	<tr>
		<td valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr class="sidebar4bg1">
			<!-- Header panel labels start -->
				<%
			for(int i=0;i<allIncludedFiles.size();i++ ){ // for 1
				ORIGSubForm subForm = (ORIGSubForm) allIncludedFiles.get(i);
				if(!subForm.getSubFormPosition().equals("HEADER")) {
					continue;
				}
			%> 
				<td nowrap="nowrap" width="145" align="left">				
					<a href="#" class="actionbar_2" onclick="toggleDiv('<%=subForm.getSubFormID() %>')" style="width:145" 
						onmouseover="document.img<%=subForm.getSubFormID() %>.src='images/arrowactionwhite_1.png'" 
						onmouseout="document.img<%=subForm.getSubFormID() %>.src='images/arrowactionwhite.png'">
						<%=MessageResourceUtil.getTextDescription(request, subForm.getSubformDesc()) %>&nbsp;
						<img src="images/arrowactionwhite.png" name='img<%=subForm.getSubFormID() %>' width="9" height="8"/>
					</a>
				</td>
			<%} %>
			<!-- Header panel labels ends -->
				<td>&nbsp;</td>
				<!-- Action button on right side -->
				<td align="right" class="greybtn1" width="180">
					<a href="javascript:void(0)" id="action" class="easyui-splitbutton" menuBtn="#dropMenu" style="text-decoration:none;">Action</a>
				</td>
			</tr>
		</table>
		<!-- Action button menu -->
		<div id="dropMenu" style="width:80px;">
		<%if(!OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){ %>
		
			<%if(showCancelApplication){%>
				<div onclick="javascript:ManualCancelApplication();">
					<img src="images/cancel.png" width="16" height="16" align="middle"/>&nbsp;&nbsp;
					<span style="vertical-align: middle;"><%=MessageResourceUtil.getTextDescription(request, "CANCEL_APP") %>
					</span>
				</div>
				<div class="menu-sep"></div>
			<%}%>
			<%if(OrigConstant.SEARCH_TYPE_REVERSE.equals(searchType)){ %>
				<div onclick="javascript:reverseApp();">
					<img src="images/reset1.png" width="16" height="16" align="middle"/>&nbsp;&nbsp;
					<span style="vertical-align: middle;"><%=MessageResourceUtil.getTextDescription(request, "REVERSE") %>
					</span>
				</div>
				<div class="menu-sep"></div>
			<%}%>			
			<div onclick="javascript:mandatoryField('<%=customerType%>', '<%=userRole%>')">
				<img src="images/save.png" width="16" height="16" align="middle"/>&nbsp;&nbsp;
				<span style="vertical-align: middle;"><%=MessageResourceUtil.getTextDescription(request, "SEND") %>
				</span>
			</div>
			<div class="menu-sep"></div>
			<div onclick="javascript:mandatoryFieldSaveNewApp('<%=customerType %>', '<%=userRole %>', 'save')">
				<img src="images/pending.png" width="16" height="16" align="middle" />&nbsp;&nbsp;
				<span style="vertical-align: middle;"><%=MessageResourceUtil.getTextDescription(request, "DRAFT") %>
				</span>
			</div>
			<div class="menu-sep"></div>			
			<div <%=cancelOnClick%>>
				<img src="images/cancel.png" width="16" height="16" align="middle"/>&nbsp;&nbsp;
				<span style="vertical-align: middle;"><%=MessageResourceUtil.getTextDescription(request, "CANCEL") %>
				</span>
			</div>	
		<%}else{%>
			<div onclick="javascript:mandatoryFieldGuarantor('<%=customerType %>', '<%=userRole %>')" <%=disabledBtn %>>
				<img src="images/save.png" width="16" height="16" align="middle"/>&nbsp;&nbsp;
				<span style="vertical-align: middle;"><%=MessageResourceUtil.getTextDescription(request, "SAVE") %>
				</span>
			</div>
			<div onclick="javascript:closeGuarantor()">
				<img src="images/cancel.png" width="16" height="16" align="middle"/>&nbsp;&nbsp;
				<span style="vertical-align: middle;"><%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>
				</span>
			</div>
		<%} %>		
		</div>  
		<!-- Action button menu ends -->
		
		
		<!-- Header sliding panels start -->
		<%
			for(int i=0;i<allIncludedFiles.size();i++ ){ // for 1
				ORIGSubForm subForm = (ORIGSubForm) allIncludedFiles.get(i);
				if(!subForm.getSubFormPosition().equals("HEADER")) {
					continue;
				}
		%> 
		<div id="<%=subForm.getSubFormID()%>" class="slide-out">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">				
					<tr class="sidebar5bg1"><td align="center">
						<jsp:include page="<%=subForm.getFileName()%>" flush="true" />
					</TD></TR>
				</table>
		</div>
		<%} %> 
		<!-- Header sliding panels ends -->
	</td></tr>
	<tr>
		<td height="20">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" align="left" bgcolor="#FFFFFF">	
			
			<%
			String includedFileName=null;
			String subformDesc=null;
			for(int i=0;i<allIncludedFiles.size();i++ ){ // for 1
				ORIGSubForm subForm = (ORIGSubForm) allIncludedFiles.get(i);
				if(subForm.getSubFormPosition().equals("HEADER")) {
					continue;
				}
				//includedFileName = (String)allIncludedFiles.get(i);
				includedFileName = subForm.getFileName();
				System.out.println("current file include = " + includedFileName );
				subformDesc = subForm.getSubformDesc();
			%>
			<tr><td colspan="3" class="sidebar8">
			<table cellSpacing="0" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, subformDesc) %> </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td> &nbsp;
                                    <!-- 	<input type="button" name="SearchBtn" value="<%=MessageResourceUtil.getTextDescription(request, "ADD") %>" onClick="" class="button"> -->
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>  
						<tr class="sidebar10"> <td align="center">
							<jsp:include page="<%=includedFileName%>" flush="true" />
						</TD></TR>
					</table>
				</td></tr>
			</table>		
			</td></tr>				
			<%
				}// end for
			%>
			<TR>
				<TD colspan="3">
				<TABLE border=0 cellSpacing=0 cellPadding=0 width=100% bgColor=#FFFFFF height="1">
					<tr>
						<td>
							<%String menuSequence = (String)session.getAttribute("menuSequence");%> 
						</td>
						<td>
							<%if(ORIGForm.getButtonFile()!=null){%> 
								<jsp:include page="<%=ORIGForm.getButtonFile()%>" flush="true" /> 
							<%}%>
						</td>
					</tr>
				</TABLE>
				</TD>
			</TR>
		</table>
			
		</td>
	</tr>
</table>
<%
	System.out.println("[application_main.jsp]....Display SubForm End ");
%>
</div>
</div>
</div>
<!-- [Append SubForm End]... -->
<div class="pane ui-layout-west" >
	<div class="west-center" id="image_Viewers" ><jsp:include page="image_viewer.jsp" flush="true" /></div>
	<div class="west-south"><jsp:include page="image_thumbnail.jsp" flush="true" /></div>
</div>
</div>
<div id = "loadDiv"></div>

<%System.out.println("[application_main.jsp]...End"); %>
<script language="javascript">
function refreshApp(){
	alert('refresh');
	appFormName.action.value="loadAppForm";
	appFormName.handleForm.value = "N";
	appFormName.submit();
}
</script>

