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
td {
    text-align: center;
    vertical-align: middle;
}
</style>
<script type="text/javascript" src="/ServiceCenterWeb/js/pace.js"></script>
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
		function getDashBoardConfig()
		{
			Pace.block();
			$.ajax({
				url : "/ServiceCenterWeb/DashboardConfig",
				data : {mode:'search'
						},
				type : "post",
				success : function(data){
					//update dashboardConfigTable
					$("#dashboardConfigTable").empty();
					var obj = $.parseJSON(data);
					if(obj.jsonRs)
					{$("#dashboardConfigTable").html(JSON.stringify(obj.jsonRs));}
					else
					{$("#dashboardConfigTable").html('No configs found.');}
					$("#saveBtn").show();
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
		function saveDashboardConfig()
		{
			Pace.block();
			//var saveResults = '';
			var checkList = $('#dashboardConfigTable').find("input[name^='ENABLE_FLAG']");
			checkList.each(function() 
			{
				var checkbox = $(this);
				var name = $(this).attr('name');
				var check = checkbox.is(":checked");
				var dhbType = name.substring(12);
				//alert('check : ' + check);
				var enableStr = 'N';
				if(check)
				{
					enableStr = 'Y';
				}
				
				$.ajax({
					url : "/ServiceCenterWeb/DashboardConfig",
					async: false,
					data : {dhbType:dhbType,
							enableFlag:enableStr,
							interval:$("[name='INTERVAL_" + dhbType + "']").val(),
							resetInterval:$("[name='RESET_INTERVAL_" + dhbType + "']").val(),
							mode:'save'
							},
					type : "post",
					success : function(data)
					{
						//var obj = $.parseJSON(data);
						//saveResults += JSON.stringify(obj.jsonRs).replace(/\\n/g, '\n').replace(/"/g, '');
					},
					error: function(jqXHR, textStatus, errorThrown){
						displayErrorMsg(jqXHR, textStatus, errorThrown);
					}
				});
			});
			Pace.unblockFlag = true;
			Pace.unblock();
			
			//alert("Update Done. \n" + saveResults);
			alert("Update Done. ");
			getDashBoardConfig();
		}
		
		//Load Dashboard Config
		getDashBoardConfig();
</script>
</head>

<body>
	<Strong>Dashboard Config</Strong>
	<br>
	<table id="dashboardConfigTable" class="table table-bordered">
	</table>
	<button id="saveBtn" style="display: none;" type="button" class="btn btn-info" onclick="saveDashboardConfig()">Save</button>
	<br>
</body>
</html>