<%@ page session="false" %>

<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>


	 total_mem <%= Runtime.getRuntime().totalMemory()%> <br>
	 free_mem <%= Runtime.getRuntime().freeMemory()%> <br>
	 free_mem/total_mem <%=(double)Runtime.getRuntime().freeMemory()/(double)Runtime.getRuntime().totalMemory()*100%>% <br>

<SCRIPT LANGUAGE="JavaScript"> 

setInterval("window.location.reload( false )",60000);

</script>