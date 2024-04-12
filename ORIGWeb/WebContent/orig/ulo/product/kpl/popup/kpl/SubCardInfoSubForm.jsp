<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/orig/ulo/product/kcc/popup/js/SubCardInfoSubForm.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	CardDataM cardM = (CardDataM)ModuleForm.getObjectForm();
	logger.debug("cardM >> "+cardM);
	if(Util.empty(cardM)){
		cardM = new CardDataM();
	}
	
	String displayMode = HtmlUtil.EDIT;
 %>
<table>
	<tbody>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "M_TH_NAME")%></td>
			<td><%=HtmlUtil.dropdown("KPL_PERSONAL_ID",cardM.getPersonalId(),"","",displayMode,HtmlUtil.elementTagId("KCC_PERSONAL_ID"),request)%></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "CARD_LEVEL")%></td>
			<td><%=HtmlUtil.dropdown("KPL_CARD_LEVEL",cardM.getCardId(),"","",displayMode,HtmlUtil.elementTagId("KCC_CARD_LEVEL"),request)%></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "PERCENT_LIMIT_MAINCARD")%></td>
			<td><%=HtmlUtil.dropdown("KPL_PERCENT_LIMIT","","","",displayMode,HtmlUtil.elementTagId("KPL_PERCENT_LIMIT"),request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "PERCENT_LIMIT")%></td>
			<td><%=HtmlUtil.textBox("KPL_PERCENT_LIMIT_MAINCARD",cardM.getPercentLimitMaincard(),"","15",displayMode,HtmlUtil.elementTagId("KPL_PERCENT_LIMIT_MAINCARD"),request)%></td>
		</tr>
	</tbody>
</table>


