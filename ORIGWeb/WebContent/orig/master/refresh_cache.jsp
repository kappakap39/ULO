<%@page import="com.eaf.orig.ulo.pl.config.ORIGConfig"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />
<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.16.js"></script>
<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td class="sidebar8"><table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
		<tr class="sidebar10"> <td align="center">
		<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
			<tr>
				<td class="textColS" width="12%">Refresh Cache :&nbsp;</td>
				<td class="inputCol" width="15%">
					<input type="button"  class ="button" name="Refresh" value="Refresh" onclick="refreshCache()" >
				</td>			   
			   	<td  align="left"><FONT color = red><b id="result-cache"></b></FONT></td>
			</tr>
		</table>
		</td></tr>
	</table></td></tr>
</table>
<script language="JavaScript">	
var REFRESH_CACHE_IMG_URL = '<%=ORIGConfig.REFRESH_CACHE_IMG_URL%>';
function refreshCache(){
	try{
		blockScreen();
		$('#result-cache').html('');
		$.post('/ORIGWeb/ORIGCache',function(data,status,xhr){
			$.post('/NCBWeb/NCBCache',function(data,status,xhr){
				$.post('/XRulesWeb/XRulesCache',function(data,status,xhr){
					unblockScreen();			
					$('#result-cache').html('Refresh Cache Completed');
					try{
						$.post(REFRESH_CACHE_IMG_URL
							,function(data,status,xhr){						
						}).error(function(){
							unblockScreen();	
						});
					}catch(e){	
						unblockScreen();					
					}
				}).error(function(){
					unblockScreen();	
				});
			}).error(function(){
				unblockScreen();	
			});
		}).error(function(){
			unblockScreen();	
		});
	}catch(e){
		unblockScreen();	
	}
}
</script>
