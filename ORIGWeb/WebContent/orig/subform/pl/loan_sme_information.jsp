<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLBundleSMEDataM"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<%
	Logger logger = Logger.getLogger(this.getClass());	

	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	if(OrigUtil.isEmptyObject(applicationM)){
		applicationM = new PLApplicationDataM();
	}
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");	
	String displayMode = formUtil.getDisplayMode("LOAN_SME_SUBFORM", ORIGUser.getRoles(),applicationM.getJobState(), PLORIGForm.getFormID(), searchType);

	PLBundleSMEDataM bundelSMEM =  applicationM.getBundleSMEM();
	if(OrigUtil.isEmptyObject(bundelSMEM)){
		bundelSMEM = new PLBundleSMEDataM();
	}
	
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
%>
<script type="text/javascript" src="orig/js/subform/pl/loan_sme_information.js"></script>
<table class="FormFrame">
	<tr>
		<td class="textR" width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "CASH_DEFINE_DATE")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"LOAN_SME_SUBFORM","cash_define_date")%>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="25%">
			<%=HTMLRenderUtil.displayInputTagJsDate("",DataFormatUtility.DateEnToStringDateTh(bundelSMEM.getSmeApproveDate(),1),displayMode,"8","cash_define_date","","right","")%>
		</td>
		<td class="textR" width="25%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "SME_NUMBER")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"LOAN_SME_SUBFORM","sme_number")%>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="30%">
			<%=HTMLRenderUtil.displayInputTagScriptAction(bundelSMEM.getSmeAppNo(),displayMode,"50","sme_number","textbox","","50") %>
		</td>
	</tr>
	
	<tr>
		<td class="textR" width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "GROSS_INCOME")%><span class="smecheck"><font color="#ff0000">*</font></span>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="25%">
			<%=HTMLRenderUtil.DisplayInputCurrency(bundelSMEM.getGrossIncome(), displayMode, "########0.##", "gross_income", "textbox-currency", "", "12", false) %>
		</td>
		<td class="textR" width="25%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "RISK_GRADE")%><span class="smecheck"><font color="#ff0000">*</font></span>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="30%">
		<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(127,applicationM.getBusinessClassId(),HTMLRenderUtil.displayHTML(bundelSMEM.getRiskGrade()),"risk_grade",displayMode,"") %>
		</td>

	</tr>
	
	<tr>
		<td class="textR">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "TOTAL_SME_LIMIT")%><span class="smecheck"><font color="#ff0000">*</font></span>&nbsp;:&nbsp;
		</td>
		<td class="inputL">
			<%=HTMLRenderUtil.DisplayInputCurrency(bundelSMEM.getTotalSMELimit(), displayMode, "########0.##", "total_sme_limit", "textbox-currency", "", "12", false) %>
		</td>
		<td class="textR"></td>
		<td class="inputL"></td>
	</tr>
	
	<tr>
		<td class="textR">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "KEC_PERMIT_RESULT")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"LOAN_SME_SUBFORM","kec_permit_result")%>&nbsp;:&nbsp;
		</td>
		<td class="inputL">
		<%--=HTMLRenderUtil.displayInputTagScriptAction(plBundelSMEDataM.getApproveStatus(),displayMode,"50","kec_permit_result","textbox","","50") --%>
        <%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(128,applicationM.getBusinessClassId(),HTMLRenderUtil.displayHTML(bundelSMEM.getApproveStatus()),"kec_permit_result",displayMode,"") %>	         	
		</td>
		<td class="textR">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "KEC_PERMIT_CASH")%><span class="smecheck"><font color="#ff0000">*</font></span>&nbsp;:&nbsp;
		</td>
		<td class="inputL">
			<%=HTMLRenderUtil.DisplayInputCurrency(bundelSMEM.getApproveCreditLine(), displayMode, "########0.##", "kec_permit_cash", "textbox-currency", "", "12", false) %>
		</td>
	</tr>
</table>