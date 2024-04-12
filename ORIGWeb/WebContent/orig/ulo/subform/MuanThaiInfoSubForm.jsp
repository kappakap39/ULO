<%@page import="com.eaf.orig.ulo.model.app.SaleInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>


<script type="text/javascript" src="js/MuanThaiInfoSubForm.js"></script>
<%
	SaleInfoDataM saleInfo = new SaleInfoDataM();
 %>
<table>
	<tbody>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "MTL_SALES_ID")%></td>
			<td><%=HtmlUtil.textBox("MTL_SALES_ID",saleInfo.getSalesId(),"","50","","",request)%></td>	
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "MTL_SALES_PHONE_NO")%></td>
			<td><%=HtmlUtil.textBoxTel("MTL_SALES_PHONE_NO", saleInfo.getSalesPhoneNo(), "9", "", "", request)%></td>	
		</tr>
	</tbody>
</table>


