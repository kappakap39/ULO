<form name="logonForm" method="post" action="/NaosWeb/logonProcess"> 
<table width="100%" border="1" bordercolor="#003399">
              <tr> 
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0" height="400">
                    <tr> 
                      <td height="25">&nbsp;</td>
                    </tr>
                    <tr>
                      <td align="center" valign="middle"><table width="300" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><table width="300" border="1" bordercolor="#003399" frame="hsides">
                                <tr> 
                                  <td bgcolor="#000033">&nbsp;<font class="workspace-header-text">Logon : </font></td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr>
                            <td><table width="300" cellpadding="0" cellspacing="0" bordercolor="#003399" frame="hsides">
                    <tr> 
                                  <td align="right"><font class="workspace-header-text">User Name :</font></td>
                                  <td align="left">&nbsp;<input name="j_username" type="text" class="input-text"></td>
                                </tr>
                                <tr> 
                                  <td align="right"><font class="workspace-header-text">Password :</font></td>
                                  <td align="left">&nbsp;<input name="j_password" type="password" class="input-text"></td>
                                </tr>
                                <tr> 
                                  <td colspan="2" align="center">
								  	<input type="submit" value="Logon" class="input-button">&nbsp;
								  	<input type="reset" value="Reset" class="input-button"></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td height="25">&nbsp;</td>
                    </tr>
                  </table></td>
              </tr>
            </table>
</form>
<%request.getSession().setAttribute("LOGON_SCREEN", "/NaosWeb/adminconsole/");%>

<script language="JavaScript">
 document.forms[0].j_username.focus();
</script>
