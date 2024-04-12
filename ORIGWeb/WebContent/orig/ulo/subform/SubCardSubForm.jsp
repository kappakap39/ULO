<%@page import="com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="js/SubCardSubForm.js"></script>
<%
	SpecialAdditionalServiceDataM sas = new SpecialAdditionalServiceDataM();
 %>

<table>
	<tbody>
		<tr class="tabletheme_header">
				<td colspan="2"><%=LabelUtil.getText(request, "ADDITIONAL_SERVICE_SUBFORM")%></td>
		</tr>
		<tr>
			<td >&nbsp;</td>
			<td>
				<%=HtmlUtil.checkBox("SERVICE_TYPE",sas.getServiceType(),"","","","SPENDING_ALERT",request)%>
		 	</td>
		 </tr>
		 <tr>
			<td >&nbsp;</td>
			<td>
		 		<%=HtmlUtil.checkBox("SERVICE_TYPE",sas.getServiceType(),"","","","DUE_ALERT",request)%>
		 	</td>
		</tr>
		<tr>
			<td >&nbsp;</td>
			<td>
		 		<%=HtmlUtil.checkBox("SERVICE_TYPE",sas.getServiceType(),"","","","SAVE_CREDIT",request)%>	
		 	</td>
		</tr>
	</tbody>
</table>


