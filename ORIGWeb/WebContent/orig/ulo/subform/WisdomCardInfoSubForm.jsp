<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.model.app.WisdomDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/js/WisdomCardInfoSubForm.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	ApplicationDataM applicationM = ORIGForm.getObjectForm().getApplicationProduct(PRODUCT_CRADIT_CARD);
	if(null == applicationM){
		applicationM = new ApplicationDataM();
	}
	CardDataM cardM = applicationM.getCard();
	if(null == cardM){
		cardM = new CardDataM();
	}
	WisdomDataM wisdom = cardM.getWisdom();
	if(null == wisdom){
		wisdom = new WisdomDataM();
	}
	String displayMode = HtmlUtil.EDIT;
%>
<table>
	<tbody>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "INSURANCE_TYPE")%></td>
			<td><%=HtmlUtil.dropdown("INSURANCE_TYPE",wisdom.getInsuranceType(),"","",displayMode,HtmlUtil.elementTagId("INSURANCE_TYPE"),request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "PREMIUM_AMT")%></td>
			<td><%=HtmlUtil.currencyBox("PREMIUM_AMT",wisdom.getPremiumAmt(),false,"15",displayMode,HtmlUtil.elementTagId("PREMIUM_AMT"),request)%></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "TRANSFER_FROM")%></td>
			<td><%=HtmlUtil.textBox("TRANSFER_FROM",wisdom.getTransferFrom(),"","50",displayMode,HtmlUtil.elementTagId("TRANSFER_FROM"),request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "QOUTA_OF")%></td>
			<td><%=HtmlUtil.popupList("QOUTA_OF",wisdom.getQuotaOf(),"",displayMode,HtmlUtil.elementTagId("QOUTA_OF"),true,request)%></td>
		</tr>
	</tbody>
</table>


