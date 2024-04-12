<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLBOT5XDataM"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.BOT5X"%>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript" src="orig/js/subform/pl/bot5x.js"></script>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" /> 
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>
<%
	org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("orig/subform/pl/bot5x.jsp");
	
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	if(null == applicationM) applicationM = new PLApplicationDataM();	
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String displayMode = formUtil.getDisplayMode("BOT5X_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);
	
	BOT5X bot5x = new BOT5X();
		Vector<PLBOT5XDataM> bot5XVect = bot5x.getDisplay(applicationM);
%>
<div align="center">
	<div style="width:650px;" id="div-bot5x">
		<table class="TableFrame">
			<tr class="Header">
				<td align="center" width="60%"><%=MessageResourceUtil.getTextDescription(request, "BOT5X_PRODUCT_NAME") %></td>
				<td align="center"><%=MessageResourceUtil.getTextDescription(request, "BOT5X_AMOUNT") %></td>
			</tr>
			<%if(null != bot5XVect && bot5XVect.size() >0){%>
				<%
				for(int i=0; i<bot5XVect.size(); i++){
					PLBOT5XDataM bot5xM = (PLBOT5XDataM) bot5XVect.get(i); 
					String styleTr = (i%2==0)?"RowEven":"RowOdd";
				%>
					<tr class="<%=styleTr%>">
						<td align="center" width="60%"><div class='textL'><%=HTMLRenderUtil.displayHTML(bot5xM.getProductName())%></div></td>
						<td align="center"><%=HTMLRenderUtil.DisplayInputCurrency(bot5xM.getAmount(), displayMode, "########0.00", bot5xM.getProductID(),"textbox-currency textbox-bot5x","","12",false)%></td>
					</tr>
				<%}%>
				<tr>
					<td align="center" width="60%"><div class='textR'><%=MessageResourceUtil.getTextDescription(request, "BOT5X_TOTOL") %></div></td>
					<td align="center"><%=HTMLRenderUtil.DisplayInputCurrency(applicationM.getBot5xTotal(), HTMLRenderUtil.DISPLAY_MODE_VIEW, "########0.00", "bot5x_total","textbox-currency","","12",false)%></td>
				</tr>
			<%}else{%>
				<tr class="Header">
					<td align="center" colspan="2">No record found</td>
				</tr>
			<%}%>
		</table>	
	</div>
</div>
<%=HTMLRenderUtil.displayHiddenField(displayMode,"displaymode-bot5x")%>