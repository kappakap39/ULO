<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="java.util.Date"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.model.LoanDataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.cache.properties.TitleProperties"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<script type="text/javascript" src="orig/js/subform/pl/spouse.js"></script>

<% 
	Logger log = Logger.getLogger(this.getClass()); 
	ORIGUtility utility = new ORIGUtility();
	
	PLApplicationDataM appM = PLORIGForm.getAppForm();
	if(OrigUtil.isEmptyObject(appM)){
		appM = new PLApplicationDataM();	
	}
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("SPOUSE_SUBFORM", ORIGUser.getRoles(), appM.getJobState(), PLORIGForm.getFormID(), searchType);

	PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);	
	
%>
<!--  p>spouse</p-->
<table width="100%" align= "center" cellpadding="0" cellspacing="1" border="0">
		<tr> 
			<td class="textR" nowrap="nowrap" width="20%" ><%=PLMessageResourceUtil.getTextDescription(request, "NAME_ONLY") %>&nbsp;:&nbsp;</td>
			<td class="inputL"  width="25%" >
				<%=HTMLRenderUtil.DisplayPopUpTextBoxDescFieldIDAndTextBox(personalM.getMThaiTitleName(),3,displayMode,"mtitleThai","textbox-code","50"
										,personalM.getMThaiFirstName(),"m_name_th","textbox","120","m_title_thai"
											,"LoadTitleThaiNew" ,MessageResourceUtil.getTextDescription(request, "TITLE_THAI"))%>
				
			</td>
			<td class="textR" nowrap="nowrap" width="25%" ><%=PLMessageResourceUtil.getTextDescription(request, "SURNAME_ONLY") %>&nbsp;:&nbsp;</td>
			<td class="inputL" width="30%" ><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getMThaiLastName(), displayMode, "50", "m_surname_th","textbox","","50")%></td>			
		</tr>		
		<tr> 
			<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "OLD_SURNAME_ONLY") %>&nbsp;:&nbsp;</td>
			<td class="inputL" ><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getmThaiOldLastName(), displayMode, "50", "m_old_surname_th","textbox","","50")%></td>
			<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "WORK_PLACE") %>&nbsp;:&nbsp;</td>
			<td class="inputL" ><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getMCompany(), displayMode, "50", "m_workplace","textbox","","50")%></td>
		</tr>		
		<tr> 
			<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "POSITION") %>&nbsp;:&nbsp;</td>
			<td class="inputL"><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getMPosition(), displayMode, "100", "m_position","textbox","","100")%></td>
			<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "SALARY") %>&nbsp;:&nbsp;</td>
			<td class="inputL">
				<%=HTMLRenderUtil.DisplayInputCurrency(personalM.getMIncome(), displayMode, "########0.00", "m_salary", "textbox-currency", "", "12", true) %>
			</td>
		</tr>		
		<tr> 
			<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "OFFICE_TEL_NO") %>&nbsp;:&nbsp;</td>
			<td class="inputL" >
			<table align='left' cellpadding='0' cellspacing='0'>
				<tr>
					<td nowrap='nowrap'><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getmOfficeTel(), displayMode, "9", "m_office_tel","textbox"," onkeypress= \"return keyPressInteger(event)\" ","9")%></td>
					<td nowrap='nowrap'>&nbsp;<%=PLMessageResourceUtil.getTextDescription(request, "EXT_TEL") %>&nbsp;:&nbsp;</td>
					<td nowrap='nowrap'><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getmOfficeTelExt(),displayMode,"6","m_ext_tel","textboxCode"," onkeypress= \"return keyPressInteger(event)\" ","6") %></td>
				</tr>
			</table>
			</td>
			<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "HOME_TELEPHONE") %>&nbsp;:&nbsp;</td>
			<td class="inputL" ><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getmHomeTel(), displayMode, "9", "m_home_tel","textbox"," onkeypress= \"return keyPressInteger(event)\" ","9")%></td>
		</tr>		
		<tr> 
			<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "MOBILE_NO") %>&nbsp;:&nbsp;</td>
			<td class="inputL" ><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getmPhoneNo(), displayMode, "10", "m_cell_phone","textbox"," onkeypress= \"return keyPressInteger(event)\" ","10")%></td>
		</tr>		
</table>