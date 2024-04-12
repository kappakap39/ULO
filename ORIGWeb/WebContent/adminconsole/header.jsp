<table width="100%" height="75" border="1" bordercolor="#003399" background="img/admin_header.jpg">
        <tr> 
          <td align="right" valign="bottom">
<%if(request.getRemoteUser()!=null && !request.getRemoteUser().trim().equals("")){%>
			<font class="header-text">User : </font>&nbsp;<font class="header-name-text"><%=request.getRemoteUser()%></font>
<%}%>
			&nbsp;</td>
        </tr>
</table>