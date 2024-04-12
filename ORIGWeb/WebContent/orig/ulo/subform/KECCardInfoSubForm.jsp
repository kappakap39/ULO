<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.WisdomDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.orig.cache.properties.CardTypeProperties"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="js/KECCardInfoSubForm.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	CardDataM cardM = new CardDataM();
	String PERSONAL_TYPE_APPLICANT =SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE_APPLICANT);	
	ApplicationDataM appM = ORIGForm.getObjectForm().getApplicationProduct(MConstant.Product.K_EXPRESS_CASH);
	String cardLevel ="";
	WisdomDataM wisdomM = null;	
	ArrayList<LoanDataM> loanListM	= appM != null ? appM.getLoans():new ArrayList<LoanDataM>();
	if(!Util.empty(loanListM)){
		for(LoanDataM loanM:loanListM){
			cardM = loanM.getCardInfo(CardDataM.ApplicationCardType.BORROWER) ;
			CardTypeProperties cardTypeM = new CardTypeProperties();
			cardLevel = cardTypeM.getPropertiesById(cardM.getCardId()).getCardLevel();
			wisdomM = cardM.getWisdom();	
		}
	}
	if(Util.empty(cardM)){
		cardM = new CardDataM();
	}
	if(Util.empty(wisdomM)){
		wisdomM = new WisdomDataM();
	}
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}
	String displayMode = HtmlUtil.EDIT;
%>

<table>
	<tbody>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "NAME")%></td>
			<td><%=FormatUtil.display(personalInfo.getThFirstName()+personalInfo.getThLastName()) %></td>
			<td class="label"></td>
			<td></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "CARD_TYPE")%></td>
			<td><%=FormatUtil.display(cardM.getCardType()) %></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "REFERRAL_WISDOM_NO")%></td>
			<td><%=HtmlUtil.textBox("REFERRAL_WISDOM_NO",wisdomM.getReferralWisdomNo(),"","16","","",request)%></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "GROUP_NO")%></td>
			<td><%=HtmlUtil.textBox("GROUP_NO",cardM.getGangNo(),"","5","","",request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "NO_APP_IN_GANG")%></td>
			<td><%=HtmlUtil.numberBox("NO_APP_IN_GANG",cardM.getNoAppInGang(),"",false,"5","","",request)%></td>
		</tr>
		<tr>
				<td><%=HtmlUtil.getSubFormLabel(request, "CAMPAIGN")%></td>
				<td><%=HtmlUtil.dropdown("CAMPAIGN_CODE", cardM.getCampaignCode(), "", "", displayMode, HtmlUtil.elementTagId("CAMPAIGN_CODE"), request) %></td>
		</tr>
	</tbody>
</table>


