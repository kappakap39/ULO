<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;"%>
<html lang="en">
<head>
<style>
th,td {
    text-align: center;
    vertical-align: middle;
}
</style>
<script type="text/javascript" src="/ServiceCenterWeb/js/moment.min.js"></script>
<script type="text/javascript" src="/ServiceCenterWeb/js/pace.js"></script>
<script>
		function getSetupDateData()
		{
			Pace.block();
			var appGroupNo = $("#appGroupNo_input").val();
			//alert(appGroupNo);
			$.ajax({
				url : "/ServiceCenterWeb/PatchSetupDate",
				data : 	{
							mode:'search',
							appGroupNo: appGroupNo
						},
				type : "post",
				success : function(data){
					//update setupDateTable
					$("#setupDateTable").empty();
					var obj = $.parseJSON(data);
					if(obj.jsonRs)
					{	
						$("#setupDateTable").html(JSON.stringify(obj.jsonRs));
						$("#patchBtn").show();
					}
					else
					{
						$("#setupDateTable").html('No results found.');
						$("#patchBtn").hide();
					}
				},
				error: function(data){
					alert(data);
				},
				complete: function(data){
					Pace.unblockFlag = true;
					Pace.unblock(); 
				}
			});
		}
		
		function patchSetupData()
		{
			Pace.block();
			var appRecordIdList = $('#setupDateTable').find("td[name^='appRecordId_']");
			var patchList = "";
			
			if(!validateDate(appRecordIdList))
			{
				Pace.unblockFlag = true;
				Pace.unblock();
				return;
			}
			
			appRecordIdList.each(function() 
			{
				var name = $(this).attr('name');
				var index = name.substring(12, name.length);
				//alert("index = " + index);
				var appRecordId = $(this).html();
				if(appRecordId)
				{
					var orgId = $('td[name="orgId_' + index + '"]').html();
					var setupDate = $('input[name="setupDate_' + index + '"]').val();
					if(setupDate)
					{
						patchList += index + ". " + appRecordId + " " + orgId + " " +setupDate + " \n";
						
						$.ajax({
						url : "/ServiceCenterWeb/PatchSetupDate",
						async: false,
						data : 	{
									mode: 'patch',
									appRecordId: appRecordId,
									orgId: orgId,
									setupDate: setupDate
								},
						type : "post",
						success : function(data)
						{},
						error: function(data)
						{alert(data);}
						});
					}
				}
			});
			
			Pace.unblockFlag = true;
			Pace.unblock();
			if(patchList)
			{
				alert("Patch Done. \n" + patchList);
				getSetupDateData();
			}
		}
		function validateDate(appRecordIdList)
		{
			var valid = true;
			var invalidList = "";
			appRecordIdList.each(function() 
			{
				var setupDate = $('input[name="setupDate_' + $(this).index() + '"]').val();
					
				if(setupDate != "null" && !moment(setupDate, 'DD/MM/YYYY HH:mm:ss',true).isValid())
				{
					invalidList += 'Invalid date ' + setupDate + " \n";
					valid = false;
				}
			});
			
			if(!valid)
			{
				alert(invalidList);
			}
			
			return valid;
		}
</script>
</head>
<body>
	<Strong>Patch Setup Date</Strong>
	<form class="form-inline well" id="patchSetupDateForm">
		<table>
			<tr>
				<td>QR No</td>
				<td><input id="appGroupNo_input" type="text" class="form-control input-sm" size="60" name="appGroupNo_input"></td>	
			</tr>
		</table>
	</form>
	<button type="button" class="btn btn-info" onclick="getSetupDateData()">Search</button>
	<br>
	<table id="setupDateTable" style="margin-top: 5px;" class="table table-bordered">
	</table>
	<button id="patchBtn" style="display: none;" type="button" class="btn btn-info" onclick="patchSetupData()">Patch Setup Date</button>
	<br>
</body>
</html>