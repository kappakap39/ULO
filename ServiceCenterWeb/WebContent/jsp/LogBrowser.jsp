<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;"%>
<%@page import="java.io.File"%>
<html lang="en">
<head>
<style>
.glyphicon::before {
  margin-right: 6px;
}
.btn-default {
  margin-left: 15px;
}
.nested {
  display: none;
}
.expand {
  display: block;
  position: relative;
}
.expand:nth-child(2) {
  display: block;
  position: relative;
  left: 3%;
}
</style>
<script type="text/javascript" src="/ServiceCenterWeb/js/pace.js"></script>
<%
	//browse log files 
	//place it in display UI with button to view or download
%>
<script>
		function displayErrorMsg(jqXHR, textStatus, errorThrown)
		{
			var msg = '';
			if (jqXHR.status === 0) 
			{
				msg = 'Not connect.\n Verify Network.';
			} else if (jqXHR.status == 404) {
				msg = 'Requested page not found. [404]';
			} else if (jqXHR.status == 500) {
				msg = 'Internal Server Error [500].';
			} else if (errorThrown === 'parsererror') {
				msg = 'Requested JSON parse failed.';
			} else if (errorThrown === 'timeout') {
				msg = 'Time out error.';
			} else if (errorThrown === 'abort') {
				msg = 'Ajax request aborted.';
			} else
			{
				msg = jqXHR.responseText;
	        }
	        var $dialog = $('<div></div>').html(msg).dialog({
				                autoOpen: false,
				                title: 'Error',
				                modal: true,
				                height: 600,
				                width: 800
				            });
			$dialog.css("white-space","pre-wrap");
			$dialog.dialog('open');
		}
		function browseLog()
		{
			var logType = $('#logType').find(":selected").val();
			if(!(logType))
			{
				alert('Please select log type.');
			}
			else
			{
				Pace.block();
				$('#blockUI').css('position','fixed');
				$.ajax( {
					url : "/ServiceCenterWeb/LogBrowser",
					data : {logType:$("#logType").val(),
							mode:'browse',
							serverIndex:$("#serverIndex").val()
							},
					type : "post",
					success : function(data){
						$("#logDirectory").empty();
						var obj = $.parseJSON(data);
						if(obj.responseData)
						{$("#logDirectory").html(obj.responseData);}
						else
						{$("#logDirectory").html('No log found.');}
					},
					error: function(jqXHR, textStatus, errorThrown){
						displayErrorMsg(jqXHR, textStatus, errorThrown);
					},
					complete: function(data){
						Pace.unblockFlag = true;
			            Pace.unblock(); 
			        }
				});
			}
		}
		function viewFile(filePath)
		{
			//alert('viewFile : ' + filePath);
			Pace.block();
			$('#blockUI').css('position','fixed');
				$.ajax({
					url : "/ServiceCenterWeb/LogBrowser",
					data : {mode:'viewFile',
							filePath:filePath,
							serverIndex:$("#serverIndex").val()
					},
					type : "post",
					success : function(data, status ,request)
					{
						var fileName = request.getResponseHeader('fileName');
						var fileSize = request.getResponseHeader('fileSize');
						if(data)
						{
							//alert('fileSize = ' + fileSize);
							if(fileSize > 10485760)
							{
								var r = confirm("File size is larger than 10Mb, confirm open?");
								if (r == false) 
								{
									return;
								}
							}
							
							var $dialog = $('<div></div>').html(data).dialog({
				                autoOpen: false,
				                title: fileName,
				                modal: true,
				                height: 600,
				                width: 1000
				            });
				            $dialog.css("white-space","pre-wrap");
				            $dialog.dialog('open');
						}
						
					},
					error: function(jqXHR, textStatus, errorThrown){
					    displayErrorMsg(jqXHR, textStatus, errorThrown);
					},
					complete: function(data){
						Pace.unblockFlag = true;
			            Pace.unblock(); 
			        }
				});
		}
		function downloadFile(filePath)
		{
			//alert('downloadFile : ' + filePath);
			Pace.block();
			
			var xhr = new XMLHttpRequest();
			var url = "/ServiceCenterWeb/LogBrowser";
			var params = "mode=downloadFile&filePath=" + encodeURIComponent(filePath) + "&serverIndex=" + $("#serverIndex").val();
			xhr.open('POST', url, true);
			xhr.responseType = 'arraybuffer';
			xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
			
			xhr.onreadystatechange = function() 
			{	
				if(xhr.readyState == 4 && xhr.status == 200)
				{
					var fileName = xhr.getResponseHeader('fileName');	
					var fileType = xhr.getResponseHeader('fileType');
					var blob = new Blob([xhr.response], {type: fileType});
					var eleA = window.document.createElement('a');
					eleA.href = window.URL.createObjectURL(blob);
					eleA.download = fileName;          
					document.body.appendChild(eleA);
					eleA.click();        
					document.body.removeChild(eleA);
			    }
				else
				{
					displayErrorMsg(xhr, xhr.statusText, xhr.responseText);
				}
				
				Pace.unblockFlag = true;
			    Pace.unblock(); 
			};
			xhr.send(params);
		}
</script>
</head>

<body>
	<Strong>LogBrowser</Strong>
	<form class="form-inline well">
		<table>
			<tr>
				<td>Log Type :
					<select id="logType">
						<option value="" selected disabled>Select Log Type...</option>
						<option value="Batch" >Batch</option>
						<option value="WebServer" >WebServer</option>
						<option value="WebArchive" >WebArchive</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>Server :
					<select id="serverIndex">
						<option value="" selected disabled>Select Server...</option>
						<option value="01" >ORIGServiceServer01</option>
						<option value="02" >ORIGServiceServer02</option>
					</select>
				</td>
			</tr>
		</table>
	</form>
	<button type="button" class="btn btn-info" onclick="browseLog()">Browse Log</button>
	<br><br>
	<div id="logDirectory">
	</div>
<script>
$('#logDirectory').on('click','.directory',function(){
	var cl = $(this).children('i').attr("class");
	if(cl == 'glyphicon glyphicon-folder-close')
	{
		$(this).children('i').attr('class','glyphicon glyphicon-folder-open');
	}
	else
	{
		$(this).children('i').attr('class','glyphicon glyphicon-folder-close');
	}
	this.parentElement.querySelector(".nested").classList.toggle("expand");
});
</script>
</body>
</html>