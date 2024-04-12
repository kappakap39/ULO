<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;"%>
<html lang="en">
<head>
<style>
input[type=checkbox] {
    transform: scale(2);
    -ms-transform: scale(2);
    -webkit-transform: scale(2);
}
th,td {
    text-align: center;
    vertical-align: middle;
}
</style>
<script type="text/javascript" src="/ServiceCenterWeb/js/pace.js"></script>
<script>
		function getSubmitIAList()
		{
			Pace.block();
			$.ajax({
				url : "/ServiceCenterWeb/UnlockIADupSubmit",
				data : {mode:'search'},
				type : "post",
				success : function(data){
					//update submitIATable
					$("#submitIATable").empty();
					var obj = $.parseJSON(data);
					if(obj.jsonRs)
					{
						$("#submitIATable").html(JSON.stringify(obj.jsonRs));
						$("#unlockBtn").show();
					}
					else
					{
						$("#submitIATable").html('No submit found.');
						$("#unlockBtn").hide();
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
		function unlockSubmit()
		{
			Pace.block();
			var checkList = $('#submitIATable').find("input[name^='ENABLE_FLAG']");
			var unlockList = "";
			checkList.each(function() 
			{
				
				var checkbox = $(this);
				var checkboxIndex = $(this).attr('name').substring(12);
				var idNo = $('td[name=idNo_' + checkboxIndex + ']').html();
				var check = checkbox.is(":checked");
				if(check)
				{
					unlockList += idNo + " ";
					$.ajax({
					url : "/ServiceCenterWeb/UnlockIADupSubmit",
					async: false,
					data : {mode: 'unlock',
							idNo: idNo
							},
					type : "post",
					success : function(data)
					{},
					error: function(data)
					{
						alert(data);
					},
					});
				}
			});
			
			Pace.unblockFlag = true;
			Pace.unblock();
			
			alert("Unlock Done. ");
			getSubmitIAList();
		}
		
		//Load Submit IA List
		getSubmitIAList();
</script>
</head>

<body>
	<Strong>Submit IA List</Strong>
	<button type="button" style="margin-left: 5px;" class="btn btn-default btn-sm" onclick="getSubmitIAList();">
	<span class="glyphicon glyphicon-refresh"></span>
	</button>
	<br>
	<table id="submitIATable" style="margin-top: 5px;" class="table table-bordered">
	</table>
	<button id="unlockBtn" style="display: none;" type="button" class="btn btn-info" onclick="unlockSubmit()">Unlock</button>
	<br>
</body>
</html>