<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.orig.ulo.pl.service.PLORIGEJBService"%>
<%@ page import="com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLProjectCodeDataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLOrigUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
 
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>

<script type="text/javascript" src="orig/js/subform/pl/project.js"></script>

<% 
	Logger log = Logger.getLogger(this.getClass());
			
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();	
	if(null == applicationM) applicationM = new PLApplicationDataM();
	
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

	ORIGFormUtil formUtil = new ORIGFormUtil();
	ORIGCacheUtil origc = new ORIGCacheUtil();
	
	String displayMode = formUtil.getDisplayMode("PROJECT_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);

	String buttonMode = displayMode;
	
	String supDisplaymode = PLOrigUtility.RoleSupDisplayMode(ORIGUser.getCurrentRole());
	PLProjectCodeDataM projectM  = null;	
	if(!ORIGUtility.isEmptyString(applicationM.getProjectCode())){				
		 displayMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
		 try{
	         ORIGDAOUtilLocal origDAOBean = PLORIGEJBService.getORIGDAOUtilLocal();
			 	projectM = origDAOBean.Loadtable(applicationM.getProjectCode());
		 }catch(Exception e){
		 	log.fatal("Exception ",e);
		 }
	}
	if(null == projectM) projectM = new PLProjectCodeDataM();
		
	String role = ORIGUser.getCurrentRole();
	
	//for ca sup #Vikrom 20121117
// 	if(OrigConstant.ROLE_CA.equals(role) && !OrigConstant.roleJobState.CA_I_SUP.equals(applicationM.getJobState())) { 
// 		buttonMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
// 	}
	if(OrigConstant.BusClass.FCP_KEC_IC.equals(applicationM.getBusinessClassId())
		|| OrigConstant.BusClass.FCP_KEC_DC.equals(applicationM.getBusinessClassId())){
		buttonMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
	}else if(OrigConstant.ROLE_CA.equals(role)){
		buttonMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
	}
%>
<div id="div-projectcode">
<table class="FormFrame" id="projectTable">	
	<tr> 
		<td class="textR" nowrap="nowrap" width="20%" >
			<%=PLMessageResourceUtil.getTextDescription(request, "PROJECT_CODE",ORIGUser,personalM.getCustomerType(),"PROJECT_SUBFORM","project_code")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"PROJECT_SUBFORM","project_code")%>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="25%" id="div_projectcode">
			<%=HTMLRenderUtil.DisplayPopUpProjectCode(applicationM.getProjectCode(),displayMode,"13","project_code","textbox-projectcode","13","...","button-popup")%>
		</td>
		<td class="textR" nowrap="nowrap" width="25%" ><%=PLMessageResourceUtil.getTextDescription(request, "PROMOTION") %>&nbsp;:&nbsp;</td>
		<td class="inputL" width="30%" id="div_promotion"><%=HTMLRenderUtil.replaceNull(projectM.getPromotion()) %></td> 
	</tr>
	<tr>
		<td class="textR" nowrap="nowrap" ><%=PLMessageResourceUtil.getTextDescription(request, "DETAIL") %>&nbsp;:&nbsp;</td>
		<td class="inputL" >
			<table>
				<tr>
					<td class="inputL" id="div_details"><%=HTMLRenderUtil.replaceNull(projectM.getProjectdesc()) %></td>
				</tr>
			</table>
		</td>
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "APPROVE_DATE") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap" id="div_approvedate"><%=DataFormatUtility.dateToString(projectM.getApprovedate(), "dd/MM/yyyy") %></td>	
	</tr>	
	<tr> 
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "START_PROJECT_DATE") %>&nbsp;:&nbsp;</td>
		<td class="inputL" id="div_startdate"><%=DataFormatUtility.dateToString(projectM.getStartprojectdate(), "dd/MM/yyyy")%></td> 
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "END_PROJECT_DATE") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap" id="div_enddate"><%=DataFormatUtility.dateToString(projectM.getEndprojectdate(), "dd/MM/yyyy")%></td>
	</tr>	
	<tr>
	 	<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "CUSTOMER_PROPERTY") %>&nbsp;:&nbsp;</td>
	 	<td class="inputL" nowrap="nowrap" id="application_property"><%=HTMLRenderUtil.replaceNull(origc.getORIGCacheDisplayNameDataM(110, projectM.getApplicationProperty())) %></td>
	 	
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "CUSTOMER_GROUP") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.replaceNull(applicationM.getCustomerGroup()) %></td>
	</tr>	
	<tr> 
		<td class="textR" nowrap="nowrap" align="right"><%=PLMessageResourceUtil.getTextDescription(request, "EXCEPTION_PROJECT") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap" align="left"><%=HTMLRenderUtil.displayCheckBoxTagDesc(applicationM.getExceptionProject(),"Y", supDisplaymode, "exception_project", "", "") %></td>
		<td class="textR" nowrap="nowrap" align="right"><%=PLMessageResourceUtil.getTextDescription(request, "EXCEPTION_MEMO") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap" align="left"><%=HTMLRenderUtil.displayInputTag(applicationM.getExceptionProjectMemo(), supDisplaymode, "100", "exception_memo", "textbox")%></td>
	</tr>				
</table>

<%=HTMLRenderUtil.displayHiddenField("", "project_code_desc") %>
<%=HTMLRenderUtil.displayHiddenField(applicationM.getProjectCode(), "projectCode") %>
<%=HTMLRenderUtil.displayHiddenField("", "startdate") %>
<%=HTMLRenderUtil.displayHiddenField("", "enddate") %>
<%=HTMLRenderUtil.displayHiddenField("", "details") %>
<%=HTMLRenderUtil.displayHiddenField("", "promotion") %>
<%=HTMLRenderUtil.displayHiddenField("", "approvedate") %>
<%=HTMLRenderUtil.displayHiddenField("", "customergroup") %>
<%=HTMLRenderUtil.displayHiddenField(projectM.getProfessionCode(), "professionCode") %>
<%=HTMLRenderUtil.displayHiddenField(applicationM.getPriority(), "Priority") %>
<%=HTMLRenderUtil.displayHiddenField(displayMode, "projectcode_displaymode") %>
<%=HTMLRenderUtil.displayHiddenField(buttonMode, "projectcode_buttonmode") %>
</div>