<%@page import="java.sql.Date"%>
<%@page import="com.eaf.orig.ulo.model.app.BundleKLDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/product/js/BundlingKLInfoSubForm.js"></script>
<%
	BundleKLDataM bundleKL = new BundleKLDataM();
	ApplicationDataM applicationM = ORIGForm.getObjectForm().getApplicationProduct(MConstant.Product.CREDIT_CARD);
	if(!Util.empty(applicationM)){
		bundleKL=applicationM.getBundleKL();
	}
	String displayMode = HtmlUtil.EDIT;
	Date verfiedDate = null;
	if(!Util.empty(bundleKL.getVerifiedDate())){
		 verfiedDate =  new Date(bundleKL.getVerifiedDate().getTime());
	}
%>
<table>
	<tbody>
		<tr class="tabletheme_header">
				<td ><%=HtmlUtil.getSubFormLabel(request, "BUNDLING_KL_INFO_SUBFORM")%></td>
						
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "APPROVE_CREDIT_LINE")%></td>
			<td><%=HtmlUtil.currencyBox("KCC_APPROVAL_LIMIT",null,false,"15",displayMode,HtmlUtil.elementTagId("KCC_APPROVAL_LIMIT"),request)%></td>
			<td class="label"></td>
			<td></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "VERFIED_INCOME")%></td>
			<td><%=HtmlUtil.currencyBox("KCC_VERFIED_INCOME",bundleKL.getVerifiedIncome(),false,"15",displayMode,HtmlUtil.elementTagId("KCC_VERFIED_INCOME"),request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "VERFIED_DATE")%></td>
			<td><%=HtmlUtil.calendar("KCC_VERFIED_DATE",verfiedDate,"",displayMode,HtmlUtil.elementTagId("KCC_VERFIED_DATE"),HtmlUtil.TH,request)%></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "ESTIMATED_INCOME")%></td>
			<td><%=HtmlUtil.currencyBox("KCC_ESTIMATED_INCOME",bundleKL.getEstimated_income(),false,"15",displayMode,HtmlUtil.elementTagId("KCC_ESTIMATED_INCOME"),request)%></td>
			<td class="label"></td>
			<td></td>
		</tr>
	</tbody>
</table>


