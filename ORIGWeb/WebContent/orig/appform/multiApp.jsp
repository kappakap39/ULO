<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGForm" />
<style type="text/css">
#container{
	height: 100%; 
	width: 100%;
}
.pane {
 	display: none; 
 }
</style>
<script language="javascript">
	$(document).ready(function () {
		$('#container').layout({
				minSize: "35%"
			,	maxSize: "70%"		
			,	west__size:	"50%"	
			,	useStateCookie:	true
		});
	});
</script>
<div id="container">
<!-- [Append SubForm Begin] ..... -->
<div class="pane ui-layout-center">	
<script type="text/javascript" src="js/popcalendar.js"></script>
<table width="100%" border=0 cellpadding="0" cellspacing="0" align="left" >
	<tr>
		<td>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
			<tr><td colspan="3" class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "APPLICANT_DETAIL") %> </td>
                            </tr>	
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>  
						<tr class="sidebar10"> <td align="center">
							<jsp:include flush="true" page="../subform/applicant_detail_multi.jsp"/> 
						</td></TR>
					</table>
				</td></tr>
			</table>
			</td></tr>	
			
			<tr><td colspan="3" class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_INFO") %> </td>
                            </tr>	
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>  
						<tr class="sidebar10"> <td align="center">
							<jsp:include flush="true" page="../subform/customer_information_multi.jsp"/>  
						</td></TR>
					</table>
				</td></tr>
			</table>
			</td></tr>	
			
			<tr><td colspan="3" class="sidebar8">
			<table cellSpacing="0" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "ADDRESS_HOME") %> </td>
                            </tr>	
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>  
						<tr> <td align="center">
							<jsp:include flush="true" page="../subform/main_home_address_multi.jsp"/> 
						</td></TR>
					</table>
				</td></tr>
			</table>
			</td></tr>	
			
			<tr><td colspan="3" class="sidebar8">
			<table cellSpacing="0" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "OCCUPATION") %></td>
                            </tr>	
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>  
						<tr class="sidebar10"> <td align="center">
							<jsp:include flush="true" page="../subform/occupation_multi.jsp"/> 
						</td></TR>
					</table>
				</td></tr>
			</table>
			</td></tr>	
			
			<tr><td colspan="3" class="sidebar8">
			<table cellSpacing="0" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "CREATE_APP") %></td>
                            </tr>	
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>  
						<tr class="sidebar10"> <td align="center">
							<jsp:include flush="true" page="mail_in_screen_multi.jsp"/> 
						</td></TR>
					</table>
				</td></tr>
			</table>
			</td></tr>
			
			<tr><td colspan="3" class="sidebar8">
			<table cellSpacing="0" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION") %></td>
                            </tr>	
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>  
						<tr class="sidebar10"> <td align="center">
							<jsp:include flush="true" page="../subform/all_application_multi.jsp"/> 
						</td></TR>
					</table>
				</td></tr>
			</table>
			</td></tr>			
												
			</table>
		</td>
	</tr>
	</table>	
</div>
</div>

