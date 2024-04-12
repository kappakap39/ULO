<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.ORIGLogic"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.ajax.NotePadNotification"%>

<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@ page import="com.eaf.orig.shared.view.form.ORIGSubForm" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" %>
 
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>
<jsp:useBean id="PROPOSAL_MENU" scope="session" class="java.lang.String"/>
<jsp:useBean id="menuSequence" scope="session" class="java.lang.String"/>
<jsp:useBean id="PersonalType" scope="session" class="java.lang.String"/>
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="PLORIGForm"/>
<%
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	if(null == applicationM) applicationM = new PLApplicationDataM();
%>
<script language="javascript">
	var $LoadImg = false;
	var $CloseImage = <%=OrigUtil.isEmptyVector(applicationM.getApplicationImageVect())%>;
	var $ResizeFrame = true;
	var $CloseFrame = true;
</script>
<script type="text/javascript" src="js/ulo.mandatory.js"></script>
<script type="text/javascript" src="js/ulo.sensitivefield.engine.js"></script>
<script type="text/javascript" src="js/ulo.application.javascript.js"></script>
<script type="text/javascript" src="orig/js/appform/pl/application_main.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
%>
<input type="hidden" id="current-role" name="current-role" value="<%=ORIGUser.getCurrentRole()%>">
<div id="container">
	<div class="ui-layout-center frame-module" id="frame-module">		
		<div id="notification-message" style="display: none;">
			<div class="notification-frame-before"></div>	
			<div class="notification-frame-wrapper">
				<div class="notifications-header">
					<div class="inline-block">Notification Message</div>
					<div class="inline-block inline-block-close"><img class="close-img" src="images/close.png"/></div>	
				</div>
				<div class="notification-body" id="notification-body">
					<div class="notifications-notfound">Not found Notifications</div>
				</div>						
				<div class="notifications-footer"></div>
			</div>
		</div>
		<div id="notification-notepad" style="display: none;">
			<div class="notepad-frame-before"></div>
			<div class="notepad-frame-wrapper">
				<div class="notepad-header">
					<div class="inline-block">Notepad</div>
					<div class="inline-block inline-block-close"><img class="close-img" src="images/close.png"/></div>
					<div><textarea name="notepad-input" id="notepad-input" cols="54" rows="5" maxlength="300" onkeyup="return ismaxlength(this)"></textarea></div>
					<div align="right">
						<%if(!OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)){ %>
							<input type="button" class="button" value="add" id="add-notepad"/>
						<%}%>
					</div>				
				</div>				
				<div class="notepad-body" id="notepad-body">
					<%=NotePadNotification.createNotepadBody(applicationM)%>
				</div>
				<div class="notepad-footer"></div>
			</div>
		</div>
		
		<div class="center-center">
			<jsp:include page="notifications.jsp" flush="true"/>
		</div>
		<div class="center-south">
			<script type="text/javascript" src="js/popcalendar.js"></script>
				<% 	
// 					logger.debug("Begin..."); 
										
// 					com.eaf.j2ee.pattern.util.ErrorUtil error = new com.eaf.j2ee.pattern.util.ErrorUtil();	
					
// 					ORIGUtility utility = new ORIGUtility();	
				
// 					logger.debug("Job State "+applicationM.getJobState()); 	
				
// 					PLPersonalInfoDataM	personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);	
				
// 					String customerType = personalM.getCustomerType();
					
// 					String userRole = ORIGUser.getCurrentRole();
					
// 					ORIGFormUtil formUtil = ORIGFormUtil.getInstance();

					PLORIGForm.setErrors(null);
					
// 					HashMap clear = new HashMap();					
//					#SeptemWi 
// 					PLORIGForm.DestoryErrorFields();
					
// 					String currentHeaderForm= "";
// 						get All subForm from Hash
// 						check with current tab
// 						sort Subform put in Vector
// 						loop include file
						
						String currentTab = PLORIGForm.getCurrentTab();
// 						HashMap allSubForms = PLORIGForm.getSubForms();
							
// 						logger.debug("allSubForms="+allSubForms);
// 						logger.debug("currentTab="+currentTab);

// 						Vector allIncludedFiles = formUtil.getSortedFileNameByCurrentTab(currentTab,allSubForms);
// 						logger.debug("allIncludedFiles="+allIncludedFiles);
							
						Vector<ORIGSubForm> subFormVect = PLORIGForm.getSubForms();
						if(null == subFormVect){
							subFormVect = new Vector<ORIGSubForm>();
						}
							
				%>
				<table width="100%" border="0" cellpadding="0" cellspacing="0" align="left" onkeydown="testKeyDown()">
					<tr><td width="100%">
						<div class="PanelFirst">
								<%
									String includedFileName=null;
									String subformDesc=null;
									for(ORIGSubForm subForm:subFormVect){
										if(subForm.getLocatedTab().equals(currentTab)){
											if(subForm.getSubFormPosition().equals("HEADER")) {
												continue;
											}
											includedFileName = subForm.getFileName();
	// 										logger.debug("current file include = " + includedFileName );
											subformDesc = subForm.getSubformDesc();
								%>
											<div class="PanelSecond TextHeaderNormal">
													<%=MessageResourceUtil.getTextDescription(request, subformDesc)%>
													<div class="PanelThird">
														<jsp:include page="<%=includedFileName%>" flush="true" />
													</div>
											</div>
										<%}%>
									<%}// end for %>
								<%if(PLORIGForm.getButtonFile()!=null){%> 
									<div class="PanelButtonFirst">
										<div class="PanelButton">
											<jsp:include page="<%=PLORIGForm.getButtonFile()%>" flush="true" /> 
										</div>
									</div>
								<%}%>
						</div>
					</td></tr>
				</table>
			</div>
	</div>
	<div class="ui-layout-west" >
		<div class="west-center" id="image-layout"><jsp:include page="image_viewer.jsp" flush="true" /></div>
		<div class="west-south"><jsp:include page="image_thumbnail.jsp" flush="true" /></div>
	</div>
</div>	
<div id = "loadDiv"></div>
<%=ORIGLogic.CreateCaportMessage(request,applicationM,MessageResourceUtil.getTextDescription(request, "REMAIN")) %>
<script type="text/javascript" src="js/application.engine.js"></script>
