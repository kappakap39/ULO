<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.cache.CacheDataInf" %>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<% 
	MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
%>

<table cellpadding="" cellspacing="1" width="100%" align="center">
	<tr>
		<td><br>
			<fieldset><LEGEND><font class="font2"><strong>Master Data</strong></font></Legend><br>
			<div id="KLTable">
				<table cellpadding="0" cellspacing="0" width="95%" align="center" border="1">
					<tr>
						<td class="TableHeader" width="30%"><%=MessageResourceUtil.getTextDescription(request, "CODE") %></td>
						<td class="TableHeader" width="70%"><%=MessageResourceUtil.getTextDescription(request, "DESCRIPTION") %></td>
					</tr>
					<% 
						Vector valueList = VALUE_LIST.getResult(); 
						if(valueList != null && valueList.size() > 0){
							CacheDataInf cacheDataInf;
							for(int i=0;i<valueList.size();i++){
								cacheDataInf = (CacheDataInf) valueList.get(i);
					%>
							<tr bgcolor="#F4F4F4" onmouseover="this.style.cursor='hand'; this.style.background='#CCFFDD';" onmouseout=" this.style.background='#F4F4F4'"onclick="javascritp:setData('<%=cacheDataInf.getCode()%>','<%=cacheDataInf.getThDesc()%>')">
								<td><%=cacheDataInf.getCode()%></td>
								<td><%=cacheDataInf.getThDesc()%></td>
							</tr>
					<% 
							}
						}else{
					%>
							<tr>
								<td colspan="2" align="center"><font color="#FF0000"><b>Results Not Found.</b></font></td>
							</tr>
					<%
						}
					%>
							<tr>
								<td colspan="2" align="right" height="50">
									<div align="center"><span class="font2">
										<jsp:include page="valuelist_cache.jsp" flush="true" />
									</span></div>
								</td>
							</tr>
				</table>
			</div>
			</fieldset>
		</td>
	</tr>
</table>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>