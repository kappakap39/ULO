<%@page contentType="text/html;charset=UTF-8"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache,no-store");
	response.setDateHeader("Expires", 0);
%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="org.apache.log4j.Logger"%>
<%
	Logger logger = Logger.getLogger(this.getClass());
%>
<div style="line-height:30px;color:#fff;">.</div>
<div id="tabs">
  <ul>
    <li><a href="#RefreshCache">RefreshCache</a></li>
    <li><a href="#Notification">Notification</a></li>
    <li><a href="#Encryption_Decryption">Encryption-Decryption</a></li>
  </ul>
   <div id="RefreshCache">    	
  		<div class="bs-callout bs-callout-warning" id="callout-navbar-overflow">
   			<h4 id="refreshCacheTemplate">
   				<button type="button" class="btn btn-warning" onclick="refreshOrigCache();">Refresh Orig Cache</button>
   				<span style='color:#00A950;' id="refreshCacheMsg"></span>
   			</h4>
  		</div>  	
   </div>
  <div id="Notification">  
    	 Notification
  </div>
  <div id="Encryption_Decryption">  
  
		<button type="button" class="btn btn-default" onclick="clearForm($('#Encryption_Decryption'));">Reset</button>
    	<div class="panel panel-default">
			<div class="panel-body">
				<div class="row form-horizontal padding-top">
					<div class="col-sm-3">
					<div class="form-group">
							<%=HtmlUtil.textBox("KM_ENCRYPTION","","","","","100","","col-sm-12 col-md-12",request)%>
						</div>
					</div>
					<div class="col-sm-1">
						<div class="form-group">
							<button type="button" class="btn btn-default" onclick="kmEncryption($('[name=KM_ENCRYPTION]').val());">KM-Encryption</button>
						</div>
					</div>
					<div class="col-sm-8">
						<div class="form-group">
							<%=HtmlUtil.textBox("KM_ENCRYPTION_RESULT","","","","100",HtmlUtil.VIEW,"","col-sm-12 col-md-12",request)%>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-sm-3">
						<div class="form-group">
							<%=HtmlUtil.textBox("KM_DECRYPTION","","","","","100","","col-sm-12 col-md-12",request)%>
						</div>
					</div>
					<div class="col-sm-1">
						<div class="form-group">
							<button type="button" class="btn btn-default" onclick="kmDecryption($('[name=KM_DECRYPTION]').val());">KM-Decryption</button>
						</div>
					</div>
					<div class="col-sm-8">
						<div class="form-group">
							<%=HtmlUtil.textBox("KM_DECRYPTION_RESULT","","","","100",HtmlUtil.VIEW,"","col-sm-12 col-md-12",request)%>
						</div>
					</div>
				</div>
			</div>
		</div>	
			
		<div class="panel panel-default">
			<div class="panel-body">
			<div class="row form-horizontal padding-top">

					<div class="col-sm-3">
					<div class="form-group">
							<%=HtmlUtil.textBox("DIH_ENCRYPTION","","","","","100","","col-sm-12 col-md-12",request)%>
						</div>
					</div>
					<div class="col-sm-1">
						<div class="form-group">
							<button type="button" class="btn btn-default" onclick="dihEncryption($('[name=DIH_ENCRYPTION]').val());">DIH-Encryption</button>
						</div>
					</div>
					<div class="col-sm-8">
						<div class="form-group">
							<%=HtmlUtil.textBox("DIH_ENCRYPTION_RESULT","","","","100",HtmlUtil.VIEW,"","col-sm-12 col-md-12",request)%>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="col-sm-3">
						<div class="form-group">
							<%=HtmlUtil.textBox("DIH_DECRYPTION","","","","","100","","col-sm-12 col-md-12",request)%>
						</div>
					</div>
					<div class="col-sm-1">
						<div class="form-group">
							<button type="button" class="btn btn-default" onclick="dihDecryption($('[name=DIH_DECRYPTION]').val());">DIH-Decryption</button>
						</div>
					</div>
					<div class="col-sm-8">
						<div class="form-group">
							<%=HtmlUtil.textBox("DIH_DECRYPTION_RESULT","","","","100",HtmlUtil.VIEW,"","col-sm-12 col-md-12",request)%>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row form-horizontal padding-top">
					<div class="col-sm-3">
					<div class="form-group">
							<%=HtmlUtil.textBox("HASHER","","","","","100","","col-sm-12 col-md-12",request)%>
						</div>
					</div>
					<div class="col-sm-1">
						<div class="form-group">
							<button type="button" class="btn btn-default" onclick="hasher($('[name=HASHER]').val());">Hasher</button>
						</div>
					</div>
					<div class="col-sm-8">
						<div class="form-group">
							<%=HtmlUtil.textBox("HASHER_RESULT","","","","100",HtmlUtil.VIEW,"","col-sm-12 col-md-12",request)%>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
	</div>
  </div>
</div>
<script type="text/javascript">
$(function(){	
	$("#tabs").tabs();
});
function refreshOrigCache(){
	$('#refreshCacheMsg').html("Refreshing caches, PLEASE WAIT...").addClass('blink');
	ajax('com.eaf.orig.ulo.app.view.util.manual.RefreshOrigCache','',displayRefreshCache);
}
function displayRefreshCache(data){
	$('#refreshCacheMsg').html(data).removeClass('blink');
}
function dihDecryption(value){
ajax('com.eaf.orig.ulo.app.view.util.manual.DecryptionDIHAction',$('#Encryption_Decryption *').serializeArray(),displayValueFromDecryptionDIHAction);	
}
function displayValueFromDecryptionDIHAction(data){
$('[name="DIH_DECRYPTION_RESULT"]').val(data);
}
function kmDecryption(value){
ajax('com.eaf.orig.ulo.app.view.util.manual.DecryptionKMAction',$('#Encryption_Decryption *').serializeArray(),displayValueFromDecryptionKMAction);	
}
function displayValueFromDecryptionKMAction(data){
$('[name="KM_DECRYPTION_RESULT"]').val(data);
}
function kmEncryption(value){
ajax('com.eaf.orig.ulo.app.view.util.manual.EncryptionKMAction',$('#Encryption_Decryption *').serializeArray(),displayValeFromKmEncryptionAction);	
}
function displayValeFromKmEncryptionAction(data){
$('[name="KM_ENCRYPTION_RESULT"]').val(data);
}
function dihEncryption(value){
ajax('com.eaf.orig.ulo.app.view.util.manual.EncryptionDIHAction',$('#Encryption_Decryption *').serializeArray(),displayValeFromDIHEncryptionAction);	
}
function displayValeFromDIHEncryptionAction(data){
$('[name="DIH_ENCRYPTION_RESULT"]').val(data);
}
function hasher(value){
ajax('com.eaf.orig.ulo.app.view.util.manual.HasherAction',$('#Encryption_Decryption *').serializeArray(),displayValeFromHasherAction);	
}
function displayValeFromHasherAction(data){
$('[name="HASHER_RESULT"]').val(data);
}
</script>