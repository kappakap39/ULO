<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="org.apache.poi.ss.usermodel.DataFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.List"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>
<%@page import="com.eaf.orig.shared.utility.ORIGUtility"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesBScoreDataM"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@page import="java.util.Collections" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<%
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/popup/pl/BscorePopup.jsp");
	
	PLApplicationDataM appM  = PLORIGForm.getAppForm();
	
	if(null == appM) appM = new PLApplicationDataM();	
	
	PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

	PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
	
	if(null == xrulesVerM) xrulesVerM = new PLXRulesVerificationResultDataM();
	
	Vector<PLXRulesBScoreDataM> bScoreVect = xrulesVerM.getxRulesBScoreVect();
	List<PLXRulesBScoreDataM>  list = new Vector<PLXRulesBScoreDataM>();
	if(OrigUtil.isEmptyVector(bScoreVect)){
		bScoreVect = new Vector<PLXRulesBScoreDataM>();
		xrulesVerM.setxRulesBScoreVect(bScoreVect);
	} 
	PLXRulesBScoreDataM bScoreLastM  = new PLXRulesBScoreDataM();
	if(!OrigUtil.isEmptyVector(bScoreVect)){
		bScoreLastM	 = (PLXRulesBScoreDataM) bScoreVect.lastElement();
	}
	
	for(int i=0; i<bScoreVect.size(); i++){
		list.add(bScoreVect.get(i));
	}
	
	if(null != list && list.size() > 0){
		Collections.sort(list, new PLXRulesBScoreDataM().LPM_PRODUCT_DESC_ORDER);
		Object obj = Collections.max(list);
	}
	
%>
<div class="PanelFirst">
	<div class="PanelSecond TextHeaderNormal">
		<div class="PanelThird">
			<table class="FormFrame">
				<tr>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "BSCORE_NAME_THAI") %>:</td>
					<td class="textL" width="20%"><%=HTMLRenderUtil.displayHTML((String) personalM.getThaiFirstName())%>&nbsp;&nbsp;<%=HTMLRenderUtil.displayHTML((String) personalM.getThaiLastName())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "BSCORE_ID_NO") %>:</td>
					<td class="textL" width="30%"><%=HTMLRenderUtil.displayHTML((String) personalM.getIdNo())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "BSCORE_CIS_NO") %>:</td>
					<td class="textL" width="20%"><%=HTMLRenderUtil.displayHTML((String) personalM.getCisNo())%></td>
					<td class="textR" nowrap="nowrap" width="25%"></td>
					<td class="textL" width="30%"></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "BSCORE_LASTUPDATE") %>:</td>
					<td class="textL" width="20%"><%=HTMLRenderUtil.displayHTML((String) bScoreLastM.getLastUpdateDate())%></td>

					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "BSCORE_VERSION") %>:</td>
					<td class="textL" width="30%"><%=HTMLRenderUtil.displayHTML((String) bScoreLastM.getMonthData())%></td>
				</tr>
			</table>	
			<table class="TableFrame">
				<tr class="Header">
					<td width="13%"><%=MessageResourceUtil.getTextDescription(request, "BSOCRE_LPM_NO") %></td>
					<td width="13%"><%=MessageResourceUtil.getTextDescription(request, "BSCORE_PRODUCT") %></td>
					<td width="9%"><%=MessageResourceUtil.getTextDescription(request, "BSCORE_MONTH01")%></td>
					<td width="9%"><%=MessageResourceUtil.getTextDescription(request, "BSCORE_MONTH02") %></td>
					<td width="9%"><%=MessageResourceUtil.getTextDescription(request, "BSCORE_MONTH03") %></td>
					<td width="9%"><%=MessageResourceUtil.getTextDescription(request, "BSCORE_MONTH04") %></td>
					<td width="9%"><%=MessageResourceUtil.getTextDescription(request, "BSCORE_MONTH05") %></td>	
					<td width="9%"><%=MessageResourceUtil.getTextDescription(request, "BSCORE_MONTH06") %></td>					
					<td width="20%"><%=MessageResourceUtil.getTextDescription(request, "BSCORE_RESULT") %></td>
				</tr>
				 <%
					if(null != list && list.size()>0){
						for(int i=0; i<list.size(); i++){						
						PLXRulesBScoreDataM listBscore = (PLXRulesBScoreDataM)list.get(i);
				 %>						
						<tr class="ResultData">
							<td><%=HTMLRenderUtil.displayHTML((String) listBscore.getLpmNo()) %></td>
							<td><%=HTMLRenderUtil.displayHTML((String) listBscore.getProductDesc()) %></td>
							<td><%=HTMLRenderUtil.displayHTML((String) listBscore.getRiskMonth1()) %></td>
							<td><%=HTMLRenderUtil.displayHTML((String) listBscore.getRiskMonth2()) %></td>
							<td><%=HTMLRenderUtil.displayHTML((String) listBscore.getRiskMonth3()) %></td>
							<td><%=HTMLRenderUtil.displayHTML((String) listBscore.getRiskMonth4()) %></td>
							<td><%=HTMLRenderUtil.displayHTML((String) listBscore.getRiskMonth5()) %></td>
							<td><%=HTMLRenderUtil.displayHTML((String) listBscore.getRiskMonth6()) %></td>
							<td><%=HTMLRenderUtil.displayHTML((String) listBscore.getbScoreResult()) %></td>
						</tr>
				 	<%}%>
				<%}else{%>
					<tr class="ResultNotFound">
						<td colspan="9" >No record found</td>
					</tr>
				<%}%>
			</table>			
		</div>
	</div>
</div>
<div class="PanelThird">
	<table class="FormFrame">
		<tr>
			<td class="textR" nowrap="nowrap" width="50%"><%=MessageResourceUtil.getTextDescription(request, "BSCORE_DECISCION") %>:</td>
			<td class="textL" width="50%"><%=HTMLRenderUtil.displayHTML(xrulesVerM.getBehavRiskGradeDecision())%></td>
		</tr>
	</table>
</div>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen(PLOrigFormHandler.PL_MAIN_APPFORM_SCREEN);
%>