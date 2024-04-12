<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.eaf.orig.ulo.model.app.BundleHLDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="js/BundlingHLInfoSubForm.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	BundleHLDataM bundleHL = new BundleHLDataM();
	ApplicationDataM applicationM = ORIGForm.getObjectForm().filterApplicationProductLifeCycle(MConstant.Product.CREDIT_CARD);
	if(!Util.empty(applicationM)){
		bundleHL = applicationM.getBundleHL();
	}	
	String displayMode = HtmlUtil.EDIT;
%>
<section class="table">
<table>
	<tbody>
		<tr class="tabletheme_header">
			<td ><%=HtmlUtil.getSubFormLabel(request, "BUNDLING_HL_INFO_SUBFORM")%></td>						
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "APPROVE_CREDIT_LINE")%></td>
			<td><%=HtmlUtil.currencyBox("APPROVE_CREDIT_LINE",bundleHL.getApproveCreditLine(),false,"15",displayMode,HtmlUtil.elementTagId("APPROVE_CREDIT_LINE"),request)%></td>
			
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "VERFIED_INCOME")%></td>
			<%--  <td><%=HtmlUtil.currencyBox("APPROVE_CREDIT_LINE",bundleHL.getVerifiedIncome(),false,"15","","",request)%></td>  --%>
		</tr>
	</tbody>
</table>
</section>

