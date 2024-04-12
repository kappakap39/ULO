<%@page contentType="text/html; charset=UTF-8"%>
<script>
	function ImportData(){
		console.log("IMPORT DATA");
		var myForm = new FormData($("#import_export_form")[0]);
		$.ajax({
			url : "/ServiceCenterWeb/ImportExportServlet?process=import",
			type : "post",
			data : myForm,
			processData : false,
			contentType : false,
			mimeType : "multipart/form-data",
			cache : false,
			success : function(data){
				console.log("IMPORT DATA SUCCESS");
			},
			error : function(data){
				console.log("IMPORT DATA ERROR");
			}
		});
	}
	
	function ExportData(){
		var url = "/ServiceCenterWeb/ImportExportServlet?process=export&"+$("#import_export_form").serialize();
		console.log(url);
		window.open(url);
// 		$.ajax({
// 			url : "/ServiceCenterWeb/ImportExportServlet?process=export",
// 			type : "post",
// 			data : $("#import_export_form").serialize(),
// 			success : function(data){
// 				console.log("download");
// 				console.log(data);
// 			},
// 			error : function(data){
// 				console.log("download error");
// 			}
// 		});
	}
	
	function loadData(){
		$.ajax({
			url : "/ServiceCenterWeb/ImportExportServlet?process=loadData",
			type : "get",
			data : $("#import_export_form").serialize(),
			success : function(data){
				$("#ApplicationGroupList").empty();
				var obj = $.parseJSON(data);
				console.log(obj);
				for(var i=0;i<obj.applicationGroupDataList.length;i++){
					var applicationGroupId = obj.applicationGroupDataList[i].applicationGroupId;
					var applicationGroupNo = obj.applicationGroupDataList[i].applicationGroupNo;
					var htmlBuild = "<tr>";
						htmlBuild += "<td><input name='applicationGroupIdBox' type='checkbox' value='"+applicationGroupId+"'></td>";
						htmlBuild += "<td>"+applicationGroupId+"</td>";
						htmlBuild += "<td>"+applicationGroupNo+"</td>";
						htmlBuild += "</tr>"; 
					$("#ApplicationGroupList").append(htmlBuild);
				}
			}
		});
	}
</script>

<h3>Import/Export Data</h3>
<form class="form-inline" id="import_export_form">
	<table>
		<tr>
			<td>Import File : </td> 
			<td><input type="file" class="input-sm form-control" name="IMPORT_FILE" multiple></td>
			<td><button type="button" class="btn btn-info btn-sm" onclick="ImportData();">Import</button></td>
		</tr>
		<tr>
			<td>Application Group Id : </td> 
			<td><textarea class="form-control" rows="3" style="width:300px;" name="applicationGroupId"></textarea></td>
			<td><button type="button" class="btn btn-info btn-sm" onclick="loadData();">Load Data</button></td>
		</tr>
	</table>
	<table class="table table-srtiped">
		<thead>
			<tr>
				<th>Select</th>
				<th>Application Group Id</th>
				<th>application Group No</th>
			</tr>
		</thead>
		<tbody id="ApplicationGroupList">
		</tbody>
	</table>
	<button type="button" class="btn btn-info btn-sm" onclick="ExportData();">Export</button>
</form>
