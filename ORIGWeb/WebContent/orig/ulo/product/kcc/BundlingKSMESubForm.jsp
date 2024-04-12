<%@page import="com.eaf.orig.ulo.model.app.BundleSMEDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/product/js/BundlingKSMEInfoSubForm.js"></script>
<%
	BundleSMEDataM bundleSME = new BundleSMEDataM();
	ApplicationDataM applicationM = ORIGForm.getObjectForm().getApplicationProduct(MConstant.Product.CREDIT_CARD);
	if(!Util.empty(applicationM)){
		bundleSME=applicationM.getBundleSME();
	}
	
String displayMode = HtmlUtil.EDIT;
%>
 <section class="table">
<table>
	<tbody>
		<tr class="tabletheme_header">
				<td ><%=HtmlUtil.getSubFormLabel(request, "BUNDLING_KSME_INFO_SUBFORM")%></td>
						
		</tr>
		<tr>			
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "BUSINESS_OWNER_FLAG")%></td>
			<td><%=HtmlUtil.radio("KCC_BUSINESS_OWNER_FLAG",bundleSME.getBusOwnerFlag(),"",displayMode,"","YES",request)%>
		 		<%=HtmlUtil.radio("KCC_BUSINESS_OWNER_FLAG",bundleSME.getBusOwnerFlag(),"",displayMode,"","NO",request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "APPLICANT_QUALITY")%></td>
			<td><%=HtmlUtil.radio("KCC_APPLICANT_QUALITY",bundleSME.getApplicantQuality(),"",displayMode,"","YES",request)%>
		 		<%=HtmlUtil.radio("KCC_APPLICANT_QUALITY",bundleSME.getApplicantQuality(),"",displayMode,"","NO",request)%></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "G-TOTAL-EXIST-PAYMENT")%></td>
			<td><%=HtmlUtil.currencyBox("KCC_G-TOTAL-EXIST-PAYMENT",bundleSME.getgTotExistPayment(),false,"15",displayMode,HtmlUtil.elementTagId("KCC_G-TOTAL-EXIST-PAYMENT"),request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "APPROVE_CREDIT_LINE")%></td>
			<td><%=HtmlUtil.currencyBox("KCC_APPROVAL_LIMIT",bundleSME.getApprovalLimit(),false,"15",displayMode,HtmlUtil.elementTagId("KCC_APPROVAL_LIMIT"),request)%></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "G-TOTAL-NEW_PAY-REQ")%></td>
			<td><%=HtmlUtil.currencyBox("KCC_G-TOTAL-NEW_PAY-REQ",bundleSME.getgTotNewPayReq(),false,"15",displayMode,HtmlUtil.elementTagId("KCC_G-TOTAL-NEW_PAY-REQ"),request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "INDIVIDUAL_RATIO")%></td>
			<td><%=HtmlUtil.numberBox("KCC_INDIVIDUAL_RATIO",bundleSME.getIndividualRatio(),"",false,"3",displayMode,HtmlUtil.elementTagId("KCC_INDIVIDUAL_RATIO"),request)%></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "G-DSCR-REQ")%></td>
			<td><%=HtmlUtil.currencyBox("KCC_G-DSCR-REQ", bundleSME.getgDscrReq(),false, "15", displayMode, HtmlUtil.elementTagId("KCC_G-DSCR-REQ"), request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "CORPORATE_RATIO")%></td>
			<td><%=HtmlUtil.numberBox("KCC_CORPORATE_RATIO",bundleSME.getCorporateRatio(),"",false,"3",displayMode,HtmlUtil.elementTagId("KCC_CORPORATE_RATIO"),request)%></td>
		</tr>
	</tbody>
</table>
</section>

