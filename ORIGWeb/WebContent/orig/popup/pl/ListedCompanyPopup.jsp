<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>
<%@page import="com.eaf.orig.shared.utility.ORIGUtility"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesListedCompanyDataM"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<%
	PLApplicationDataM appM  = PLORIGForm.getAppForm();	
	if(null == appM){
		appM = new PLApplicationDataM();
	}	
	PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);	
	PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
	if(null == xrulesVerM){
		xrulesVerM = new PLXRulesVerificationResultDataM();
	}	
	Vector<PLXRulesListedCompanyDataM> listCompanyVect = xrulesVerM.getxRulesListedCompanyDataMs();	
 %>
<div id="verifyHr">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<div class="PanelThird">
				<table class="TableFrame">
					<tr class="Header">
						<td width="20%"><%=MessageResourceUtil.getTextDescription(request, "LIST_COMPANY_NAME") %></td>
						<td width="5%"><%=MessageResourceUtil.getTextDescription(request, "LIST_COMPANY_CATAGORY")%></td>
						<td width="20%"><%=MessageResourceUtil.getTextDescription(request, "LIST_COMPANY_ADDRESS") %></td>
						<td width="15%"><%=MessageResourceUtil.getTextDescription(request, "LIST_COMPANY_TEL") %></td>
						<td width="15%"><%=MessageResourceUtil.getTextDescription(request, "LIST_COMPANY_WEB") %></td>
						<td width="15%"><%=MessageResourceUtil.getTextDescription(request, "LIST_COMPANY_CANCEL_REASON") %></td>					
						<td width="10%"><%=MessageResourceUtil.getTextDescription(request, "LIST_COMPANY_CANCEL_DATE") %></td>													
					</tr>
					<%
					 if(!ORIGUtility.isEmptyVector(listCompanyVect)){						
						for(PLXRulesListedCompanyDataM listedCompanyM :listCompanyVect){
					%>
							<tr class="ResultData">
								<td><%=HTMLRenderUtil.displayHTML((String) listedCompanyM.getCompanyName()) %></td>
								<td><%=HTMLRenderUtil.displayHTML((String) listedCompanyM.getCompanyType()) %></td>
								<td><%=HTMLRenderUtil.displayHTML((String) listedCompanyM.getAddress()) %></td>
								<td><%=HTMLRenderUtil.displayHTML((String) listedCompanyM.getPhone()) %></td>
								<td><%=HTMLRenderUtil.displayHTML((String) listedCompanyM.getWebsite()) %></td>
								<td><%=HTMLRenderUtil.displayHTML((String) listedCompanyM.getRevocationReason()) %></td>
								<td><%=DataFormatUtility.DateEnToStringDateTh(listedCompanyM.getRevocationDate(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S)%></td>
							</tr>
						<%} %>
					<%}else{%>					
						<tr class="ResultNotFound">
							<td colspan="8">No record found</td>
						</tr>
					<%}%>
				</table>
			</div>
		</div>
	</div>
</div>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen(PLOrigFormHandler.PL_MAIN_APPFORM_SCREEN);
%>