<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLReferencePersonDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="java.util.Date"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.shared.utility.TooltipResourceUtil" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.model.LoanDataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.cache.properties.TitleProperties"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<script type="text/javascript" src="orig/js/subform/pl/reference_personal.js"></script>
<% 
	Logger log = Logger.getLogger(this.getClass()); 
	PLApplicationDataM appM  = PLORIGForm.getAppForm();
	if(null == appM){
		appM = new PLApplicationDataM();	
	}	
	PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);	
	
	PLReferencePersonDataM referenceM = appM.getReferencePerson();
	if(null == referenceM){
		referenceM = new PLReferencePersonDataM();
	}		
	//Get Display Mode
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("REFERENCE_PERSONAL_SUBFORM", ORIGUser.getRoles(), appM.getJobState(), PLORIGForm.getFormID(), searchType);
%>

<!-- p>reference personal</p -->
<table width="100%" align= "center" cellpadding="0" cellspacing="1" border="0">
	<tr> 
		<td class="textR" nowrap="nowrap" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "NAME_ONLY") %>&nbsp;:&nbsp;</td>
		<td class="inputL"  width="25%" >		
		<%=HTMLRenderUtil.DisplayPopUpTextBoxDescFieldIDAndTextBox(referenceM.getThaiTitleCode(),3,displayMode,"r_titleThai","textbox-code","50"
						,referenceM.getThaiFirstName(),"r_name_th","textbox","120","r_title_thai"
							,"LoadTitleThaiNew" ,MessageResourceUtil.getTextDescription(request, "TITLE_THAI"))%>		
		<td class="textR" nowrap="nowrap" width="25%" ><%=PLMessageResourceUtil.getTextDescription(request, "SURNAME_ONLY") %>&nbsp;:&nbsp;</td>
		<td class="inputL" width="30%" ><%=HTMLRenderUtil.displayInputTagScriptAction(referenceM.getThaiLastName(), displayMode, "50", "r_surname_th","textbox","","50")%></td>
	</tr>		
	<tr> 
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "RELATION") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(referenceM.getRelation(), displayMode, "50", "r_relation","textbox","","50")%></td>
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "HOME_TELEPHONE") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(referenceM.getPhone(), displayMode, "9", "r_home_tel","textbox"," onkeypress= \"return keyPressInteger(event)\" ","9")%></td>			
	</tr>		
	<tr> 
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "OFFICE_TEL_NO") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap">
			<table align='left' cellpadding='0' cellspacing='0'>
				<tr>
					<td nowrap='nowrap'><%=HTMLRenderUtil.displayInputTagScriptAction(referenceM.getPhone2(), displayMode, "9", "r_office_tel","textbox"," onkeypress= \"return keyPressInteger(event)\" ","9")%></td>
					<td nowrap='nowrap'>&nbsp;<%=PLMessageResourceUtil.getTextDescription(request, "EXT_TEL") %>&nbsp;:&nbsp;</td>
					<td nowrap='nowrap'><%=HTMLRenderUtil.displayInputTagScriptAction(referenceM.getPhoneExt2(),displayMode,"6","ext_tel","textboxCode"," onkeypress= \"return keyPressInteger(event)\" ","6") %></td>
				</tr>
			</table>
		</td>
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "MOBILE_NO") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(referenceM.getMobile(), displayMode, "10", "r_cell_phone","textbox"," onkeypress= \"return keyPressInteger(event)\" ","10")%></td>
	</tr>
</table>