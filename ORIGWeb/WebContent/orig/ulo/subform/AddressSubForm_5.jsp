<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<script type="text/javascript" src="js/AddressSubForm.js"></script>
<table>
	<tbody>
		<tr>	
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "ADDRESS_TYPE")%></td>
			<td colspan="3"><%=HtmlUtil.dropdown("ADDRESS_TYPE","","","","10","",request)%><%=HtmlUtil.button("ADD_BTN","ADD_BTN","","","",request) %></td>
		</tr>
		<tr>
			<td class="label" colspan="4">
			</td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "SEND_DOC")%></td>
			<td><%=HtmlUtil.dropdown("SEND_DOC","","","","10","",request)%></td>	
			<td></td>	
			<td></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "PLACE_RECEIVE_CARD")%></td>
			<td><%=HtmlUtil.dropdown("PLACE_RECEIVE_CARD","","","","10","",request)%></td>	
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "BRANCH_RECEIVE_CARD")%></td>
			<td><%=HtmlUtil.popupList("BRANCH_RECEIVE_CARD","","20","","",true,request)%></td>
		</tr>
		<tr class="tabletheme_header">
			<td colspan="4" ><%=LabelUtil.getText(request, "MOTHERLAND_ADDRESS")%></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "ADDRESS1")%></td>
			<td><%=HtmlUtil.textBox("ADDRESS1","","","10","","",request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "STATE")%></td>
			<td><%=HtmlUtil.textBox("STATE","","","10","","",request)%></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "COUNTRY")%></td>
			<td><%=HtmlUtil.popupList("COUNTRY","","20","","",request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "ZIPCODE")%></td>
			<td><%=HtmlUtil.textBox("ZIPCODE","","","10","","",request)%></td>
		</tr>
		<tr class="tabletheme_header">
			<td colspan="4" ><%=LabelUtil.getText(request, "VAT_DATA")%></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "VAT_REGIST_FLAG")%></td>
			<td><%=HtmlUtil.dropdown("VAT_REGIST_FLAG","","","","10","",request)%></td>	
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "ESTABLISHMENT_ADDR_FLAG")%></td>
			<td><%=HtmlUtil.dropdown("ESTABLISHMENT_ADDR_FLAG","","","","","",request)%>&nbsp;
			<%=HtmlUtil.textBox("ESTABLISHMENT_ADDR_FLAG_txt","","","10","","",request)%></td>
		</tr>
		<tr>
			<td colspan="4" class="label" ><%=HtmlUtil.getSubFormLabel(request, "ESTABLISHMENT_ADDR")%></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "ADDRESS1")%></td>
			<td><%=HtmlUtil.textBox("ADDRESS1","","","10","","",request)%></td>	
			<td></td>	
			<td></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "PROVINCE")%></td>
			<td><%=HtmlUtil.textBox("PROVINCE","","","10","","",request)%></td>	
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "ZIPCODE")%></td>
			<td><%=HtmlUtil.textBox("ZIPCODE","","","10","","",request)%></td>	
		</tr>
	</tbody>
</table>
