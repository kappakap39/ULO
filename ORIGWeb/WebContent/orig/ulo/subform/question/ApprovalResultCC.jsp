<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.RenderStyle"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");	
	String FIELD_ID_FINAL_RESULT = SystemConstant.getConstant("FIELD_ID_FINAL_RESULT");
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");	
	String APPLICATION_TYPE_NEW = SystemConstant.getConstant("APPLICATION_TYPE_NEW");
	String APPLICATION_TYPE_ADD = SystemConstant.getConstant("APPLICATION_TYPE_ADD");
	String APPLICATION_TYPE_UPGRADE = SystemConstant.getConstant("APPLICATION_TYPE_UPGRADE");
	ArrayList<String> normalType = new ArrayList<String>();
		normalType.add(APPLICATION_TYPE_NEW);
		normalType.add(APPLICATION_TYPE_ADD);
		normalType.add(APPLICATION_TYPE_UPGRADE);
	String FIELD_ID_APPLICATION_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_APPLICATION_CARD_TYPE");
	ArrayList<ApplicationDataM> applicationItems = new ArrayList<ApplicationDataM>();
	String applicationType = applicationGroup.getApplicationType();
	if(normalType.contains(applicationType)){		
		applicationItems = applicationGroup.filterFinalAppDecisionLifeCycle(PRODUCT_CRADIT_CARD,APPLICATION_CARD_TYPE_BORROWER);
	}else{	
		applicationItems = applicationGroup.filterFinalAppDecisionLifeCycle(PRODUCT_CRADIT_CARD);
	}
%>
<%		
if(!Util.empty(applicationItems)){
	for(ApplicationDataM applicationItem : applicationItems){
		CardDataM card = applicationItem.getCard();
		String applicationRecordId = applicationItem.getApplicationRecordId();
		String requestCardType = card.getRequestCardType();
		String cardType = card.getCardType();
		String applicationCardType = card.getApplicationType();
		String finalAppDecision = CacheControl.getName(FIELD_ID_FINAL_RESULT,applicationItem.getFinalAppDecision(),"DISPLAY_NAME");
		PersonalInfoDataM borrowerPersonalInfo = applicationGroup.getPersonalInfoRelation(applicationRecordId,
				PERSONAL_TYPE_APPLICANT, APPLICATION_LEVEL);
		if (null == borrowerPersonalInfo) {
			borrowerPersonalInfo = new PersonalInfoDataM();
		}
%>
		<tr class=<%=RenderStyle.getRecommendDecisionStyle(applicationItem.getRecommendDecision())%>>
			<td>
				<div class="col-sm-4">
					<div class="form-group">
						<%=CacheControl.displayName(FIELD_ID_APPLICATION_CARD_TYPE,applicationCardType)%>	
						<%=FormatUtil.display(borrowerPersonalInfo.getThName())%>					
					</div>
				</div>
				<div class="col-sm-8">
					<div class="form-group">
						<%=CacheControl.getName(CACHE_CARD_INFO,requestCardType)%>
					</div>
				</div>			
			</td>
			<td><%=CacheControl.getName(CACHE_CARD_INFO,cardType)%></td>
			<td><%=FormatUtil.display(finalAppDecision)%></td>
			<td></td>
		</tr>
	<%
		if(normalType.contains(applicationType)){
			ArrayList<ApplicationDataM> supplemantarys = applicationGroup.filterLinkMainCardFinalAppDecisionLifeCycle(applicationRecordId,APPLICATION_CARD_TYPE_SUPPLEMENTARY);	
			if(!Util.empty(supplemantarys)){
				for(ApplicationDataM  supplemantary:supplemantarys){
					CardDataM cardSup = supplemantary.getCard();
					String applicationRecordIdSup = supplemantary.getApplicationRecordId();
					String requestCardTypeSup = cardSup.getRequestCardType();
					String cardTypeSup = cardSup.getCardType();
					String applicationCardTypeSup = cardSup.getApplicationType();
					String finalAppDecisionSup = CacheControl.getName(FIELD_ID_FINAL_RESULT,supplemantary.getFinalAppDecision(),"DISPLAY_NAME");
					PersonalInfoDataM supPersonalInfo = applicationGroup.getPersonalInfoRelation(applicationRecordId,
							PERSONAL_TYPE_SUPPLEMENTARY, APPLICATION_LEVEL);
					if (null == supPersonalInfo) {
						supPersonalInfo = new PersonalInfoDataM();
					}				
	%>
		<tr class=<%=RenderStyle.getRecommendDecisionStyle(supplemantary.getRecommendDecision())%>>
			<td>
				<div class="col-sm-4">
					<div class="form-group">
						<%=CacheControl.displayName(FIELD_ID_APPLICATION_CARD_TYPE,applicationCardTypeSup)%>
						<%=FormatUtil.display(supPersonalInfo.getThName())%>
					</div>
				</div>
				<div class="col-sm-8">
					<div class="form-group">
						<%=CacheControl.getName(CACHE_CARD_INFO,requestCardTypeSup)%>
					</div>
				</div>			
			</td>
			<td><%=CacheControl.getName(CACHE_CARD_INFO,cardTypeSup)%></td>
			<td><%=FormatUtil.display(finalAppDecisionSup)%></td>
			<td></td>
		</tr>
		<%}}%>
	<%}%>
<%}%>
<%}else{%>
		<tr>
			<td colspan="4">
				<div class="col-sm-12">
					<div class="form-group" align="center"><%=HtmlUtil.getText(request, "NO_RECORD_FOUND")%></div>
				</div>
			</td>
		</tr>
<%}%>