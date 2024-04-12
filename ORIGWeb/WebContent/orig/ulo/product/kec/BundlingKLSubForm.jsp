<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.sql.Date"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.BundleKLDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/product/js/BundlingKLInfoSubForm.js"></script>
<%
	String subformId = "KEC_BUNDLING_KL_SUBFORM";
	BundleKLDataM bundleKL = new BundleKLDataM();
	ApplicationDataM applicationM = ORIGForm.getObjectForm().getApplicationProduct(MConstant.Product.K_EXPRESS_CASH);
	if(!Util.empty(applicationM)){
		bundleKL=applicationM.getBundleKL();
	}
	String displayMode = HtmlUtil.EDIT;
	Date verfiedDate = null;
	if(!Util.empty(bundleKL.getVerifiedDate())){
		 verfiedDate =  new Date(bundleKL.getVerifiedDate().getTime());
	}
		
	FormUtil formUtil = new FormUtil(subformId,request);
%>
  <section class="table">
<table>
	<tbody>
		<tr class="tabletheme_header">
				<td ><%=HtmlUtil.getSubFormLabel(request, "BUNDLING_KL_INFO_SUBFORM")%></td>
						
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "VERFIED_INCOME")%></td>
			<td><%=HtmlUtil.currencyBox("KEC_VERFIED_INCOME",bundleKL.getVerifiedIncome(),false,"15","",HtmlUtil.elementTagId("KEC_VERFIED_INCOME"),request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "VERFIED_DATE")%></td>
			<td><%=HtmlUtil.calendar("KEC_VERFIED_DATE",verfiedDate,"","",HtmlUtil.elementTagId("KEC_VERFIED_DATE"),HtmlUtil.EN,request)%></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "ESTIMATED_INCOME")%></td>
			<td><%=HtmlUtil.currencyBox("KEC_ESTIMATED_INCOME",bundleKL.getEstimated_income(),false,"15","",HtmlUtil.elementTagId("KEC_ESTIMATED_INCOME"),request)%></td>
			<td class="label"></td>
			<td></td>
		</tr>
	</tbody>
</table>
</section>

