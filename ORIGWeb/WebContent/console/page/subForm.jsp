<!DOCTYPE html>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="com.eaf.core.ulo.common.model.SQLDataM"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache,no-store");
	response.setDateHeader("Expires", 0);
	Logger logger = Logger.getLogger(this.getClass());	
	SQLQueryEngine QueryEngine = new SQLQueryEngine();
%>
<html>
<head>	
	<link rel="StyleSheet" href="../css/jquery-ui.css" type="text/css">
	<link rel="StyleSheet" href="../css/jquery-ui.structure.css" type="text/css">
	<link rel="StyleSheet" href="../css/jquery-ui.theme.css" type="text/css">
	<link rel="StyleSheet" href="../css/ulo.kbank.css" type="text/css">
	<script type="text/javascript" src="../js/jquery-2.1.3.js"></script>
	<script type="text/javascript" src="../js/jquery-ui.js"></script>
</head>
<body>
	<article class="warpbody">
		<article class="warpcontent">
		<article>
			<section class="work_tools_area">			
			<section class="table mg_bottom10">
				<form action="subForm.jsp" name="subForm">
					<table>
						<tr>
								<td>SubForm:</td>
								<td>
									<select id="SUBFORM_ID" name="SUBFORM_ID">
									<%
										Vector<HashMap> vSubForm = QueryEngine.LoadModuleList("SELECT * FROM SC_SUBFORM");
									%>
									<%
										if(!Util.empty(vSubForm)){
									%>
										<%
											for(HashMap Sform:vSubForm){
												String CODE = SQLQueryEngine.display(Sform,"SUBFORM_ID");
												String VALUE = HtmlUtil.getSubFormLabel(request,SQLQueryEngine.display(Sform,"SUBFORM_DESC"));
										%>
									  		<option value="<%=CODE%>"><%=VALUE%></option>
									  	<%
									  		}
									  	%>
									<%
										}
									%>
									</select>			
								</td>
								<td><div><input type="submit" value="LoadSubForm"/></div></td>
							</tr>
					</table>
				</form>
			</section>	
						
			<%
				String REQ_SUBFORM = request.getParameter("SUBFORM_ID");
				logger.debug("SUBFORM_ID >> "+REQ_SUBFORM);
				HashMap subForm = QueryEngine.LoadModule("SELECT * FROM SC_SUBFORM WHERE SUBFORM_ID = ?",REQ_SUBFORM);
				String SUBFORM_FILE_NAME = SQLQueryEngine.display(subForm,"SUBFORM_FILE_NAME");
				String SUBFORM_ID = SQLQueryEngine.display(subForm,"SUBFORM_ID");
			%>
			<%
				if(!Util.empty(SUBFORM_ID)){
			%>
			<header class="titlecontent"><%=HtmlUtil.getSubFormLabel(request,SQLQueryEngine.display(subForm,"SUBFORM_DESC"))%></header>
			<section class="table mg_bottom10">
				<table>	
					<tr>
						<td class="label">SUBFORM_ID:</td>
						<td><%=SQLQueryEngine.display(subForm,"SUBFORM_ID")%></td>
					</tr>
					<tr>
						<td class="label">SUBFORM_DESC:</td>
						<td><%=SQLQueryEngine.display(subForm,"SUBFORM_DESC")%></td>
					</tr>			
					<tr>
						<td class="label">SUBFORM_FILE_NAME:</td>
						<td><%=SQLQueryEngine.display(subForm,"SUBFORM_FILE_NAME")%></td>
					</tr>
					<tr>
						<td class="label">SUBFORM_CLASS_NAME:</td>
						<td<%=SQLQueryEngine.display(subForm,"SUBFORM_CLASS_NAME")%>></td>
					</tr>
					<tr>
						<td class="label">FORM_TAB_ID:</td>
						<td><%=SQLQueryEngine.display(subForm,"FORM_TAB_ID")%></td>
					</tr>
					<tr>
						<td class="label">SUBFORM_POSITION:</td>
						<td><%=SQLQueryEngine.display(subForm,"SUBFORM_POSITION")%></td>
					</tr>
				</table>
			</section>
			<div id="SUBFORM_ID_DISPLAY">
				<section class="table mg_bottom10">
					<jsp:include page="<%=SUBFORM_FILE_NAME%>" flush="true"/>
				</section>
			</div>
			<%}%>	
			</section>	
		</article>	
		</article>
	</article>
</body>
</html>