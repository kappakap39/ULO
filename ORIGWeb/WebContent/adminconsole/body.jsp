<%if(request.getRemoteUser()!=null && !request.getRemoteUser().trim().equals("")){%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><jsp:include page="menu-bar.jsp" flush="true" /></td>
        </tr>
        <tr>
          <td><jsp:include page="workspace.jsp" flush="true" /></td>
        </tr>
      </table>
<%}else{%>
	<jsp:include page="logon.jsp" flush="true" />
<%}%>
