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
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/product/js/WisdomCardInfoSubForm.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	WisdomDataM wisdom = new WisdomDataM();
	CardDataM card = new CardDataM();
	 ApplicationDataM applicationM = ORIGForm.getObjectForm().getApplicationProduct(MConstant.Product.CREDIT_CARD);
	
	ArrayList<LoanDataM> loans	 = applicationM != null ? applicationM.getLoans():new ArrayList<LoanDataM>();
	if(!Util.empty(loans)){
		for(LoanDataM loan:loans){
	card=loan.getCardInfo(CardDataM.ApplicationCardType.BORROWER) ;
		}
		if(Util.empty(card)){
	card = new CardDataM();
		}
		wisdom=	card.getWisdom();
		if(null ==wisdom){
	wisdom =new WisdomDataM() ;
		}
	}
	String displayMode = HtmlUtil.EDIT;
%>
 
 <section class="table">
<table>
	<tbody>
		<tr class="tabletheme_header">
				<td colspan="4" class="subtoppic label"><%=LabelUtil.getText(request, "WISDOM_CARD_INFO_SUBFORM")%></td>				
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "INSURANCE_TYPE")%></td>
			<td><%=HtmlUtil.dropdown("KCC_INSURANCE_TYPE",wisdom.getInsuranceType(),"","",displayMode,HtmlUtil.elementTagId("KCC_INSURANCE_TYPE"),request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "PREMIUM_AMT")%></td>
			<td><%=HtmlUtil.currencyBox("KCC_PREMIUM_AMT",wisdom.getPremiumAmt(),false,"15",displayMode,HtmlUtil.elementTagId("KCC_PREMIUM_AMT"),request)%></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "TRANSFER_FROM")%></td>
			<td><%=HtmlUtil.textBox("KCC_TRANSFER_FROM",wisdom.getTransferFrom(),"","50",displayMode,HtmlUtil.elementTagId("KCC_TRANSFER_FROM"),request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "QOUTA_OF")%></td>
			<td><%=HtmlUtil.popupList("KCC_QOUTA_OF",wisdom.getQuotaOf(),"",displayMode,HtmlUtil.elementTagId("KCC_QOUTA_OF"),true,request)%></td>
		</tr>
	</tbody>
</table>
</section>

