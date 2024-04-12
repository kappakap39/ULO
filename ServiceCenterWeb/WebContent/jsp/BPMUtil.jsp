<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;"%>
<html lang="en">
<head>
<style>
th,td {
    text-align: center;
    vertical-align: middle;
}
pre {
	display: inline-block;
	text-align: initial;
    white-space: pre;
}
</style>
<script type="text/javascript" src="/ServiceCenterWeb/js/pace.js"></script>
<script>
		function doAction()
		{
			var mode = $('#mode').val();
			var appGrpNo = $('#appGrpNo').val();
			var getTaskData = null;
			
			if(!appGrpNo)
			{
				alert('Please input QR No.');
				return;
			}
			
			if(mode == 'task' && $('#getTaskData').is(":checked"))
			{
				getTaskData = 'Y';
			}
			
			Pace.block();
			
			$.ajax({
				url : "/ServiceCenterWeb/BPMUtil",
				data : {mode:mode,
						appGrpNo:appGrpNo,
						getTaskData:getTaskData
				},
				type : "post",
				success : function(data)
				{
					$("#rsTable").empty();
					if(data)
					{
						//$("#rsTable").html(JSON.stringify(data, null, 2));
						$("#rsTable").append(data);
					}
				},
				error: function(xhr, status, error)
				{
					if(xhr.responseText)
					{alert(xhr.responseText);}
					else
					{alert(error);}
				},
				complete: function(data){
					Pace.unblockFlag = true;
					Pace.unblock(); 
				}
			});
		}
</script>
<script>
		function displayDiv(ele)
		{
			if($(ele).css('float') != 'left')
			{
				$(ele).css('float','left');
			}
			else
			{
				$(ele).css('float','none');
			}
			var divId = 'div' + ele.id;
			var div = $('#' + divId);
		    div.toggle();
		}
		$("#mode").change(function()
		{
			if($(this).val() == 'task')
			{
				$("#getTaskDataTD").show();
			}
			else
			{
				$("#getTaskDataTD").hide();
			}
		});
</script>
</head>
<body>
	<Strong>BPMUtil</Strong>
	<form class="form-inline well">
		<table>
			<tr>
				<td>Mode :
					<select id="mode">
						<option value="" selected disabled>Select Mode..</option>
						<option value="process" >Instance Data</option>
						<option value="task" >Task info</option>
					</select>
				</td>
				<td id="getTaskDataTD" style="display: none;">
					<input id="getTaskData" type="checkbox" checked> Task Data
				</td>
			</tr>
		</table>
		<br>
		<table>
			<tr>
				<td>QR No : </td>
				<td><input id="appGrpNo" type="text" size="60" name="appGrpNo"></td>
			</tr>
		</table>
	</form>	
	<div class="row" style="margin-left: 0px;">
		<button id="browseBtn" type="button" class="btn btn-info col-sm-1" onclick="doAction();">Browse</button>
	</div>
	<table id="rsTable" style="margin-top: 5px;" class="table table-bordered">
	</table>
	<br>
</body>
</html>