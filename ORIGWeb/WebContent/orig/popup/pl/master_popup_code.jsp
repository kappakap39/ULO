<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<script type="text/javascript" src="orig/js/popup/pl/master-popup-code.js"></script>
<% 
	Logger log = Logger.getLogger(this.getClass());
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
								<tr class="Result-Obj tr-master-popup <%=styleTr%>" id="<%=HTMLRenderUtil.displayHTML(elementList.elementAt(1))%>">
									<td width="20%">
										<%=HTMLRenderUtil.displayHTML(elementList.elementAt(1))%>
									</td>
									<td width="80%" class="TextTDLeft">
										<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(2))%>
										<%=HTMLRenderUtil.displayHiddenField((String)elementList.elementAt(2) ,"code_"+HTMLRenderUtil.displayHTML(elementList.elementAt(1)))%>	
									</td>
								</tr>
						<%}%>
					<%}else{%>
							<tr class="ResultNotFound">
								<td colspan="2">Results Not Found.</td>
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
	screenFlowManager.setCurrentScreen(VALUE_LIST.getScreenFlow());
%>