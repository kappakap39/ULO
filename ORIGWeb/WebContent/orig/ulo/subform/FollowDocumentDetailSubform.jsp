<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript" src="orig/ulo/subform/js/FollowDocumentDetailSubform.js"></script>
<%
String displayMode = HtmlUtil.EDIT; %>
<section class="table">
	<table width='100%'>
		<tr>
			<td class="label"><%=HtmlUtil.getFieldLabel(request, "DOCUMENT_FOLLOW")%></td>
			<td><%=HtmlUtil.dropdown("DOCUMENT_FOLLOW","","","","51","", displayMode,HtmlUtil.elementTagId("DOCUMENT_FOLLOW"), request) %></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getFieldLabel(request, "SELLER_NAME")%></td>
			<td><%=HtmlUtil.popupList("SELLER_NAME","","8","","",true,request)%></td>
			<td class="label"><%=HtmlUtil.getFieldLabel(request, "BRANCH_SUBJECTION")%></td>
			<td><%=HtmlUtil.textBox("BRANCH_SUBJECTION","","","","50",HtmlUtil.DISABLED,HtmlUtil.elementTagId("BRANCH_SUBJECTION"), request) %></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getFieldLabel(request, "SELLER_PHONE")%></td>
			<td><%=HtmlUtil.textBox("SELLER_PHONE","","","","10",displayMode,HtmlUtil.elementTagId("SELLER_PHONE"), request) %></td>
			<td class="label"><%=HtmlUtil.getFieldLabel(request, "CUSTOMER_TYPE")%></td>
			<td><%=FormatUtil.display("")%></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getFieldLabel(request, "RESULT_CHECKING")%></td>
			<td><%=HtmlUtil.dropdown("RESULT_CHECKING","","","","65","", displayMode,HtmlUtil.elementTagId("RESULT_CHECKING"), request) %></td>
			<td></td>
			<td></td>
		</tr>
	</table>
</section>