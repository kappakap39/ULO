<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>

<script type="text/javascript" src="js/CallCenterPopup.js"></script>

<table>
	<tbody>
		<tr class="tabletheme_header">
			<td colspan="4"><%=LabelUtil.getText(request, "CALNCEL_REASON")%></td>
			<td><%=HtmlUtil.button("SAVE_BTN","Save","","","",request) %><%=HtmlUtil.button("CANCEL_BTN","CANCEL","","","",request) %></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "REASON")%></td>
			<td></td>
			<td><%=HtmlUtil.dropdown("REASON","","","","10","",request)%>&nbsp;
				<%=HtmlUtil.textBox("REASON_OTHER","","","100","","",request)%></td>

		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "DM_REMARK")%></td>
			<td></td>
			<td><%=HtmlUtil.textarea("REMARK","","5","30","200","","",request)%></td>
		</tr>

	</tbody>
</table>


