<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8;"%>
<html lang="en">
<head>
<script>
		function searchInfBatchLog()
		{
			if(!$("#interfaceCode").val() && !$("#appRecordId").val() 
			&& !$("#appGroupId").val()
			&& !$("#refId").val() && !$("#createDateFrom").val() )
			{
				alert('Please input atleast 1 criteria.');
			}
			else
			{
				$.ajax({
					url : "/ServiceCenterWeb/InfBatchLog",
					data : {interfaceCode:$("#interfaceCode").val(),
							appGroupId:$("#appGroupId").val(),
							appRecordId:$("#appRecordId").val(),
							refId:$("#refId").val(),
							infStatus:$("#infStatus").val(),
							createDateFrom:$("#createDateFrom").val(),
							createDateTo:$("#createDateTo").val(),
							mode:'search'
							},
					type : "post",
					success : function(data){
						//update searchResultsTable
						$("#searchResultsTable").empty();
						var obj = $.parseJSON(data);
						//alert(JSON.stringify(obj.jsonRs));
						if(obj.jsonRs)
						{$("#searchResultsTable").html(JSON.stringify(obj.jsonRs));}
						else
						{$("#searchResultsTable").html('No results found.');}
						$("#srText").show();
						$("#saveBtn").show();
					}
				});
			}
		}
		function saveInfBatchLog()
		{
			var saveResults = '';
			var checkList = $('#searchResultsTable').find("input[name^='CHECK_BOX']");
			checkList.each(function() 
			{
				var checkbox = $(this);
				var name = $(this).attr('name');
				var check = $(this).is(":checked");
				var interfaceLogId = name.substring(10);
				//alert('check : ' + check);
				if(check)
				{ 
					$.ajax({
						url : "/ServiceCenterWeb/InfBatchLog",
						async: false,
						data : {interfaceLogId:interfaceLogId,
								interfaceCode:$("[name='INTERFACE_CODE_" + interfaceLogId + "']").val(),
								interfaceStatus:$("[name='INTERFACE_STATUS_" + interfaceLogId + "']").val(),
								mode:'save'
								},
						type : "post",
						success : function(data){
							var obj = $.parseJSON(data);
							saveResults += JSON.stringify(obj.jsonRs).replace(/\\n/g, '\n');
							checkbox.removeAttr('checked');
						}
					});
				}
			});
			
			alert("Job Done. Results = " + saveResults);
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
	<Strong>InfBacthLog</Strong>
	<form class="form-inline well" id="idNoForm">
		<table>
			<tr>
				<td>Interface Code</td>
				<td><input id="interfaceCode" type="text" class="form-control input-sm" size="20" ></td>
			</tr>
			<tr>
				<td>Application Group Id</td>
				<td><input id="appGroupId" type="text" class="form-control input-sm" size="50" ></td>
			</tr>
			<tr>
				<td>Application Record Id</td>
				<td><input id="appRecordId" type="text" class="form-control input-sm" size="50" ></td>
			</tr>
			<tr>
				<td>Ref Id</td>
				<td><input id="refId" type="text" class="form-control input-sm" size="30" ></td>
			</tr>
			<tr>
				<td>Interface Status</td>
				<td><input id="infStatus" type="text" class="form-control input-sm" size="30" ></td>
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
	<button type="button" class="btn btn-info" onclick="searchInfBatchLog()">Search</button>
	<br><br>
	<div id="srText" style="display: none;" ><Strong>Search Results</Strong></div>
	<table id="searchResultsTable" class="table table-bordered">
	</table>
	<button id="saveBtn" style="display: none;" type="button" class="btn btn-info" onclick="saveInfBatchLog()">Save</button>
	<br><br>
</body>
</html>