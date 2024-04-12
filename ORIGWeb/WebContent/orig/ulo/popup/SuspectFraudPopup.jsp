<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>

<script type="text/javascript" src="js/SuspectFraudPopup.js"></script>

<table>
	<tbody>
		<tr class="tabletheme_header">
			<td colspan="4"><%=LabelUtil.getText(request, "CALNCEL_REASON")%></td>
			<td><%=HtmlUtil.button("SAVE_BTN","Save","","","",request) %><%=HtmlUtil.button("CANCEL_BTN","CANCEL","","","",request) %></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "EQ_DETAIL")%></td>
			<td></td>
			<td><%=HtmlUtil.textarea("REMARK","","5","30","2000","","",request)%></td>
		</tr>

	</tbody>
</table>


