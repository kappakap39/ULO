<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
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
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
	String FIELD_ID_FINAL_RESULT = SystemConstant.getConstant("FIELD_ID_FINAL_RESULT");
	ArrayList<ApplicationDataM> applications = applicationGroup.filterFinalAppDecisionLifeCycle(PRODUCT_K_EXPRESS_CASH);
%>
<%		
if(!Util.empty(applications)){
	for(ApplicationDataM applicationData : applications){
		CardDataM card = applicationData.getCard();
		String requestCardType = card.getRequestCardType();
		String cardType = card.getCardType();
		String finalAppDecision = CacheControl.getName(FIELD_ID_FINAL_RESULT,applicationData.getFinalAppDecision(),"DISPLAY_NAME");
%>
		<tr class=<%=RenderStyle.getRecommendDecisionStyle(applicationData.getRecommendDecision())%>>
			<td><%=CacheControl.getName(CACHE_CARD_INFO,requestCardType)%></td>
			<td><%=CacheControl.getName(CACHE_CARD_INFO,cardType)%></td>
			<td><%=FormatUtil.display(finalAppDecision)%></td>
			<td></td>
		</tr>
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