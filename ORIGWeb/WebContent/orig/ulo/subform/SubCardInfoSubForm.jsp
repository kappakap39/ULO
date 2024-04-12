<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.cache.properties.CardTypeProperties"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="js/SubCardInfoSubForm.js"></script>
<%
	CardDataM cardData = new CardDataM();
	CardTypeProperties cardTypeM = new CardTypeProperties();
	String cardLevel = cardTypeM.getPropertiesById(cardData.getCardId()).getCardLevel();
 %>

<table>
	<tbody>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "M_TH_NAME")%></td>
			<td><%=HtmlUtil.dropdown("PERSONAL_ID",cardData.getPersonalId(),"","","","",request)%></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "CARD_LEVEL")%></td>
			<td><%=HtmlUtil.dropdown("CARD_LEVEL",cardLevel,"","","","",request)%></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "PERCENT_LIMIT_MAINCARD")%></td>
			<td><%=HtmlUtil.dropdown("PERCENT_LIMIT_MAINCARD",cardData.getPercentLimitMaincard(),"","","","",request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "PERCENT_LIMIT")%></td>
			<td><%=HtmlUtil.textBox("PERCENT_LIMIT","","","15","","",request)%></td>
		</tr>
	</tbody>
</table>


