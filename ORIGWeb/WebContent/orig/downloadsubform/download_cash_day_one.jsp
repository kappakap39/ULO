<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.profile.model.UserDetailM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>

<script type="text/javascript" src="orig/js/downloadsubform/download_cash_day_one.js"></script>

<!-- <div class="PanelFirst"> -->
	<div class="PanelSecond TextHeaderNormal">
		<%=PLMessageResourceUtil.getTextDescription(request, "CASH_DAY_ONE_INTERBANK_SCREEN") %>
			<div class="PanelThird">
			<table class="TableFrame" id="addressResult">
				<tr class="Header" id="header">
						<td width="5%" align="center"></td>
						<td width="35%" align="center"><%=PLMessageResourceUtil.getTextDescription(request, "DL_FILENAME") %></td>
						<td width="20%" align="center"><%=PLMessageResourceUtil.getTextDescription(request, "DL_LAST_UPDATE") %></td>
				</tr>
				<tr class="Result-Obj ResultOdd" id="cash_content" bgcolor="white">
						<td><%=HTMLRenderUtil.DisplayButton("cs_download","Download","EDIT","buttonNew") %></td>
						<td width="35%" align="center"><%=HTMLRenderUtil.displayHTML("Cash Day One Report") %></td>
						<td width="20%" align="center"><%=HTMLRenderUtil.displayHTML("Location (TEMP)") %></td>
				</tr>
								
			</table>
 			</div> 
	</div>
<!--</div>-->