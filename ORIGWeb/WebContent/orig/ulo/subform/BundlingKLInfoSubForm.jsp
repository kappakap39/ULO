<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.BundleKLDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="js/BundlingKLInfoSubForm.js"></script>
<%
	BundleKLDataM bundleKL = new BundleKLDataM();
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	ApplicationDataM applicationM = ORIGForm.getObjectForm().filterApplicationProductLifeCycle(PRODUCT_CRADIT_CARD);
	if(!Util.empty(applicationM)){
		bundleKL = applicationM.getBundleKL();
	}
	if(null == bundleKL){
		bundleKL = new BundleKLDataM();
	}
	String displayMode = HtmlUtil.EDIT;
%>
 <section class="table">
<table>
	<tbody>
		<tr class="tabletheme_header">
				<td ><%=HtmlUtil.getSubFormLabel(request, "BUNDLING_KL_INFO_SUBFORM")%></td>
						
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "APPROVE_CREDIT_LINE")%></td>
			<td><%=HtmlUtil.currencyBox("APPROVAL_LIMIT",null,false,"15",displayMode,HtmlUtil.elementTagId("APPROVAL_LIMIT"),request)%></td>
			<td class="label"></td>
			<td></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "VERFIED_INCOME")%></td>
			<td><%=HtmlUtil.currencyBox("VERFIED_INCOME",bundleKL.getVerifiedIncome(),false,"15",displayMode,HtmlUtil.elementTagId("VERFIED_INCOME"),request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "VERFIED_DATE")%></td>
			<td><%=HtmlUtil.calendar("VERFIED_DATE",null,"",displayMode,HtmlUtil.elementTagId("VERFIED_DATE"),HtmlUtil.EN,request)%></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "ESTIMATED_INCOME")%></td>
			<td><%=HtmlUtil.currencyBox("ESTIMATED_INCOME",bundleKL.getEstimated_income(),false,"15",displayMode,HtmlUtil.elementTagId("ESTIMATED_INCOME"),request)%></td>
			<td class="label"></td>
			<td></td>
		</tr>
	</tbody>
</table>

</section>
