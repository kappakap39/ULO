<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
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
	String jndiName = request.getParameter("jndiName");
	String sql = request.getParameter("sql");		
	logger.debug("jndiName >> "+jndiName);	
	logger.debug("sql >> "+sql);
	if(!Util.empty(jndiName)&&!Util.empty(sql)){	
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ArrayList<HashMap<String,Object>> Results = new ArrayList<HashMap<String,Object>>();
			InitialContext ctx = new InitialContext();
				Object obj = ctx.lookup(jndiName);
			DataSource dataSrc = (DataSource) javax.rmi.PortableRemoteObject.narrow(obj, DataSource.class);
			conn = dataSrc.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();	
			int colCnt = rsmd.getColumnCount();
			while(rs.next()){
				HashMap<String, Object> Row = new HashMap<String, Object>();
				for(int index=1;index<=colCnt;index++){
					String CLASS_NAME = rsmd.getColumnClassName(index);
					if(java.lang.String.class.getName().endsWith(CLASS_NAME)){
						String result = rs.getString(index);
						if(null != result){
							result = result.trim();
						}
						Row.put((rsmd.getColumnLabel(index)).toUpperCase(), result);
					}else if(java.sql.Date.class.getName().endsWith(CLASS_NAME)){
						Row.put((rsmd.getColumnLabel(index)).toUpperCase(), rs.getDate(index));
					}else if(java.sql.Timestamp.class.getName().endsWith(CLASS_NAME)){
						Row.put((rsmd.getColumnLabel(index)).toUpperCase(), rs.getTimestamp(index));
					}else if(java.math.BigDecimal.class.getName().endsWith(CLASS_NAME)){
						Row.put((rsmd.getColumnLabel(index)).toUpperCase(), rs.getBigDecimal(index));
					}else if(java.lang.Double.class.getName().endsWith(CLASS_NAME)){
						Row.put((rsmd.getColumnLabel(index)).toUpperCase(), rs.getBigDecimal(index));
					}else if(java.lang.Long.class.getName().endsWith(CLASS_NAME)){
						Row.put((rsmd.getColumnLabel(index)).toUpperCase(), rs.getBigDecimal(index));
					}
				}
				Results.add(Row);
			}	
			Gson gson = new Gson();		
			out.println("<pre style='background-color: #fff;' id='result-form'></pre>");
			out.println("<script>$('#result-form').html(getPrettyJson("+gson.toJson(Results)+")).show('fast');</script>");				
		}catch(Exception e){
			logger.fatal("ERROR",e);
			out.println(e);
		}finally{
			if(null != conn){
				conn.close();
			}
			if(null != ps){
				ps.close();
			}
			if(null != rs){
				rs.close();
			}
		}
	}else{
		out.println("Please input parameter for search result. (jndiName,sql)");
	}
	
%>
