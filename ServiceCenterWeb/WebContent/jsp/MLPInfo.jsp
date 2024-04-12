<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;"%>
<html lang="en">
<head>
<script type="text/javascript" src="/ServiceCenterWeb/js/pace.js"></script>
<script>
		function searchMLPInfo()
		{
			if(!$("#qrNo").val() && !$("#idNo").val() 
			&& !$("#appStatus").val()
			&& !$("#cardlinkFlag").val() && !$("#createDateFrom").val() )
			{
				alert('Please input atleast 1 criteria.');
			}
			else
			{
				Pace.block();
				$.ajax({
					url : "/ServiceCenterWeb/MLPInfo",
					data : {qrNo:$("#qrNo").val(),
							idNo:$("#idNo").val(),
							appStatus:$("#appStatus").val(),
							cardlinkFlag:$("#cardlinkFlag").val(),
							createDateFrom:$("#createDateFrom").val(),
							createDateTo:$("#createDateTo").val(),
							mode:'search'
							},
					type : "post",
					success : function(data){
						//update searchResultsTable
						$("#searchResults").empty();
						var obj = $.parseJSON(data);
						//alert(JSON.stringify(obj.jsonRs));
						if(obj.jsonRs)
						{$("#searchResults").html(obj.jsonRs);}
						else
						{$("#searchResults").html('No results found.');}
					},
					complete: function(data){
						Pace.unblockFlag = true;
			            Pace.unblock(); 
			        }
				});
			}
		}
		$( function() 
		{
    		$("#createDateFrom").datepicker();
  		});
  		$( function() 
		{
    		$("#createDateTo").datepicker();
  		});
</script>
</head>

<body>
	<Strong>MLPInfo</Strong>
	<form class="form-inline well" id="idNoForm">
		<table>
			<tr>
				<td>QR No</td>
				<td><input id="qrNo" type="text" class="form-control input-sm" size="20" ></td>
			</tr>
			<tr>
				<td>Id No</td>
				<td><input id="idNo" type="text" class="form-control input-sm" size="50" ></td>
			</tr>
			<tr>
				<td>Application Status</td>
				<td><input id="appStatus" type="text" class="form-control input-sm" size="50" ></td>
			</tr>
			<tr>
				<td>CardLink Flag</td>
				<td><input id="cardlinkFlag" type="text" class="form-control input-sm" size="30" ></td>
			</tr>
			<tr>
				<td>Create Date From</td>
				<td><input id="createDateFrom" type="text"></td>
			</tr>
			<tr>
				<td>Create Date To</td>
				<td><input id="createDateTo" type="text"></td>
			</tr>
		</table>
	</form>
	<button type="button" class="btn btn-info" onclick="searchMLPInfo()">Search</button>
	<br><br>
	<div id="searchResults">
	</div>
</body>
</html>