<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="java.util.Vector"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<script type="text/javascript" src="orig/js/popup/pl/projectcode-master.js"></script>
<% 
	Logger log = Logger.getLogger(this.getClass()); 
	ORIGCacheUtil origc = new  ORIGCacheUtil();
	PLApplicationDataM appM = PLORIGForm.getAppForm();
	if(OrigUtil.isEmptyObject(appM)){
		appM = new PLApplicationDataM();
	}
	String busClassID = appM.getBusinessClassId();
	int applicantFieldID = 110;
	
// 	#septemwi comment
// 	if(!OrigUtil.isEmptyString(busClassID) && busClassID.contains("XS")){
// 		applicantFieldID = 35;
// 	}

%>
<div class="PanelFirst">
	<div class="PanelSecond TextHeaderNormal">
			<div class="PanelThird">
				<table class="TableFrame">
						<tr class="Header">
							<td align="center" width="20%"><%=MessageResourceUtil.getTextDescription(request, "CODE") %></td>
							<td align="center" width="80%"><%=MessageResourceUtil.getTextDescription(request, "DESCRIPTION") %></td>
						</tr>
						<%
							Vector valueList = VALUE_LIST.getResult(); 
							if(valueList != null && valueList.size() > 1){
								for(int i=1; i<valueList.size(); i++ ){						
									Vector elementList = (Vector)valueList.get(i);
									String styleTr = ((i-1)%2==0)?"ResultEven":"ResultOdd";
						%>															
								<tr class="Result-Obj tr-project-code-obj <%=styleTr%>" id="<%=HTMLRenderUtil.displayHTML(elementList.elementAt(1))%>">
										<td width="20%">
											<%=HTMLRenderUtil.displayHTML(elementList.elementAt(1))%>
										</td>
										<td width="80%" class="TextTDLeft">
											<%=MessageResourceUtil.getTextDescription(request, "DETAIL")%>
											&nbsp;:&nbsp;
											<%=HTMLRenderUtil.displayHTML((String)elementList.elementAt(2))%>
											<br>
											<%=MessageResourceUtil.getTextDescription(request, "PROMOTION")%>
											&nbsp;:&nbsp;
											<%=HTMLRenderUtil.displayHTML((String)elementList.elementAt(3))%>
											<br>
											<%=MessageResourceUtil.getTextDescription(request, "DURATION_TIME")%>
											<%=HTMLRenderUtil.displayHTML(DataFormatUtility.StringDateENToStringDateTHFormatYYYY_MM_DD((String)elementList.elementAt(4)))%>
											&nbsp;,&nbsp;
											<%=HTMLRenderUtil.displayHTML(DataFormatUtility.StringDateENToStringDateTHFormatYYYY_MM_DD((String)elementList.elementAt(6)))%>
											<br>
											<%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_PROPERTY")%>&nbsp;:&nbsp;				
											<%=HTMLRenderUtil.displayHTML(origc.getORIGCacheDisplayNameDataM(applicantFieldID,(String)elementList.elementAt(7)))%>
										</td>
								</tr>
							<%}%>
					<%}else{%>
							<tr class="ResultNotFound">
								<td colspan="2">No record found</td>
							</tr>
					<%}%>
			</table>
			<table width="100%">
				<tr align="center">
					<td colspan="2" align="left" height="50">
						<jsp:include page="valuelist-master-popup.jsp" flush="true" />
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>

<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen("PL_MAIN_APPFORM");
%>