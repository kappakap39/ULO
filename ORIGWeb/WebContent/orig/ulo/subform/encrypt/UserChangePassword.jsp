<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>

<div id="tabs">
	<h2>User Change Password</h2>
	<div id="Encryption_Decryption">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row form-horizontal padding-top">
	
						<div class="col-sm-3">
						<div class="form-group">
								<%=HtmlUtil.textBox("APP_ENCRYPTION","","","","","100","","col-sm-12 col-md-12",request)%>
							</div>
						</div>
						<div class="col-sm-1">
							<div class="form-group">
								<button type="button" class="btn btn-default" onclick="appEncryption($('[name=APP_ENCRYPTION]').val());">APP-Encryption</button>
							</div>
						</div>
						<div class="col-sm-8">
							<div class="form-group">
								<%=HtmlUtil.textBox("APP_ENCRYPTION_RESULT","","","","100",HtmlUtil.VIEW,"","col-sm-12 col-md-12",request)%>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-sm-3">
							<div class="form-group">
								<%=HtmlUtil.textBox("APP_DECRYPTION","","","","","100","","col-sm-12 col-md-12",request)%>
							</div>
						</div>
						<div class="col-sm-1">
							<div class="form-group">
								<button type="button" class="btn btn-default" onclick="appDecryption($('[name=APP_DECRYPTION]').val());">APP-Decryption</button>
							</div>
						</div>
						<div class="col-sm-8">
							<div class="form-group">
								<%=HtmlUtil.textBox("APP_DECRYPTION_RESULT","","","","100",HtmlUtil.VIEW,"","col-sm-12 col-md-12",request)%>
							</div>
						</div>
				</div>
			</div>
		</div>
		<button type="button" class="btn btn-default" onclick="clearForm($('#Encryption_Decryption'));">Reset</button>
	</div>
</div>
<script type="text/javascript">
	function appDecryption(value){
		console.log("test decrypt");
		ajax('com.eaf.orig.ulo.app.view.util.manual.DecryptionAPPAction',$('#Encryption_Decryption *').serializeArray(),displayValueFromDecryptionAPPAction);	
	}
	function displayValueFromDecryptionAPPAction(data){
		$('[name="APP_DECRYPTION_RESULT"]').val(data);
	}
	function appEncryption(value){
		console.log("test encrypt");
		ajax('com.eaf.orig.ulo.app.view.util.manual.EncryptionAPPAction',$('#Encryption_Decryption *').serializeArray(),displayValeFromAPPEncryptionAction);	
	}
	function displayValeFromAPPEncryptionAction(data){
		$('[name="APP_ENCRYPTION_RESULT"]').val(data);
	}
</script>