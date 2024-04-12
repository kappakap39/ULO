<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.model.SQLResultDataM"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.model.SQLDataM"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html; charset=UTF-8"%>
<script src="/ServiceCenterWeb/libs/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
function getPrettyJson(json){
	var jsonObj = (typeof json == 'string'?JSON.parse(json):json);
	var jsonPretty = JSON.stringify(jsonObj, null, '\t');
	if(jsonPretty)jsonPretty = jsonPretty.replace(/\\/g,'');
	return jsonPretty;
}	
</script>
<%
	Logger logger = Logger.getLogger(this.getClass());	
	String dbType = request.getParameter("dbType");
	String jndiName = request.getParameter("jndiName");
	String sql = request.getParameter("sql");
	logger.debug("dbType >> "+dbType);
	logger.debug("sql >> "+sql);	
	logger.debug("jndiName >> "+jndiName);
	if(!Util.empty(dbType) && !Util.empty(sql)){		
		SQLQueryEngine QueryEngine = new SQLQueryEngine(Integer.parseInt(dbType));
			SQLDataM sqlM = new SQLDataM();
			sqlM.setSQL(sql);
		SQLResultDataM sqlResult = QueryEngine.QueryResult(sqlM);
		Gson gson = new Gson();		
		out.println("<pre style='background-color: #fff;' id='result-form'></pre>");
		out.println("<script>$('#result-form').html(getPrettyJson("+gson.toJson(sqlResult)+")).show('fast');</script>");
	}else if(!Util.empty(jndiName) && !Util.empty(sql)){
		SQLQueryEngine QueryEngine = new SQLQueryEngine(jndiName);
			SQLDataM sqlM = new SQLDataM();
			sqlM.setSQL(sql);
		SQLResultDataM sqlResult = QueryEngine.QueryResult(sqlM);
		Gson gson = new Gson();		
		out.println("<pre style='background-color: #fff;' id='result-form'></pre>");
		out.println("<script>$('#result-form').html(getPrettyJson("+gson.toJson(sqlResult)+")).show('fast');</script>");
	}else{
		out.println("No Result Found.");
	}
%>
