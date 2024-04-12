<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="org.apache.log4j.pattern.LogEvent"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<script language="JavaScript">
$(document).ready(function(){
	$("Tr.ResultOdd").hover(
	    function(){$(this).addClass("ResultOdd-haver");},
	    function(){$(this).removeClass("ResultOdd-haver");}
	);
	$("Tr.ResultEven").hover(
    	 function(){$(this).addClass("ResultEven-haver");},
    	 function(){$(this).removeClass("ResultEven-haver");}
	);		
});
</script>
<%
	Logger logger = Logger.getLogger(this.getClass());
 %>
<div class="PanelFirst">
<div class="PanelSecond TextHeaderNormal">
	<table width="100%">
		<tr> 
			<td colspan="4">
				<div class="PanelThird">	
					<table class="TableFrame">
						<tr class="Header">
							<td align="center" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "CODE") %></td>
							<td align="center" width="80%"><%=PLMessageResourceUtil.getTextDescription(request, "DESCRIPTION") %></td>
						</tr> 
					<% Vector valueList = VALUE_LIST.getResult(); 
						if(valueList != null && valueList.size() > 1){
							for(int i=1;i<valueList.size();i++){
								Vector elementList = (Vector)valueList.get(i);
								String styleTr = ((i-1)%2==0)?"ResultEven":"ResultOdd";
						%>		  
							<%if(elementList.size()!=9 && (!("LoadTitleThaiNew").equals(VALUE_LIST.getSearchAction()))){%>													
								<tr class="Result-Obj <%=styleTr%>" onclick="javascript:setData('<%=elementList.elementAt(1)%>','<%=HTMLRenderUtil.displayText((String) elementList.elementAt(2))%>')">																			
							<%}else if(("LoadTitleThaiNew").equals(VALUE_LIST.getSearchAction())){ %>
								<tr class="Result-Obj <%=styleTr%>" onclick="javascript:setDataTitle('<%=elementList.elementAt(1)%>','<%=HTMLRenderUtil.displayText((String) elementList.elementAt(2))%>','<%=HTMLRenderUtil.displayText((String) elementList.elementAt(3))%>','<%=HTMLRenderUtil.displayText((String) elementList.elementAt(4))%>')">
							<%}%>
								<td width="20%"><%=elementList.elementAt(1)%></td>
								<td width="80%" class="TextTDLeft"><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(2))%></td>							
							</tr>
					<%}%>
					<%}else{%>
						<tr><td align="center" colspan="2">No record found</td></tr>
					<%}%>
							<tr><td colspan="2" align="left" height="50">
									<jsp:include page="ValuelistLayer1.jsp" flush="true" />
							</td></tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>
</div>
<input type="hidden" name="zipcode">
<input type="hidden" name="amphur">
<input type="hidden" name="tambol">
<input type="hidden" name="province">
<input type="hidden" name="channel">

<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen(VALUE_LIST.getScreenFlow());
%>