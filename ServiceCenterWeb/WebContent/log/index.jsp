<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="ISO-8859-1">
	<title>Monitor Transaction Log UI</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- 	<link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css" media="screen"> -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-datetimepicker.min.css" media="screen">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/all.min.css" media="screen">
<!-- 	<script src="../js/jquery-3.3.1.min.js"></script> -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/moment.min.js"></script>
<!-- 	<script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"> </script> -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
	<script>
		var contextPath = "<%=request.getContextPath()%>";
	</script>
	<style>
		.table {
			font-family: Open Sans,sans-serif;
			color: #3b4151;
			font-size: 13px;
		}
		.container-fluid {
			width: 100%;
			padding-right: 50px;
			padding-left: 50px;
			margin-right: auto;
			margin-left: auto;
			font-size: 14px;
		}
		.modal-lg {
			max-width: 90%;
		}
	</style>
</head>
<body>
<div class="container-fluid">
	<br/>
	<form>
	<!--
		<div class="form-group row">
			<label  class="col-sm-1 col-form-label" for="target_url">Target URL</label>
			<div class="col-sm-7">
				<input type="text" class="form-control form-control-sm" id="target_url" value="http://172.30.138.26:7802/monitor_rest/v1"/>
			</div>
		</div>
	-->
		<div class="row">
			<div class="col-sm-4 form-group">
				<label class="col-form-label" for="ref_code">Application No.</label>
				<input class="form-control form-control-sm" type="text" id="ref_code" placeholder="Application No."/>
			</div>
			<div class="col-sm-4 form-group">
				<label class="col-form-label" for="transaction_id">Transaction Id</label>
				<input class="form-control form-control-sm" type="text" id="transaction_id" placeholder="rqUID"/>
			</div>
			<div class="col-sm-4 form-group">
				<label  class="col-form-label" for="id_no">ID No.</label>
				<input class="form-control form-control-sm" type="text" id="id_no" placeholder="ID No."/>
			</div>
		</div>
		<div class="row">
				<div class="col-sm-4 form-group">
						<label class="col-form-label" for="decision_service">Decision Service</label>
						<select class="form-control form-control-sm" type="text" id="decision_service"></select>
				</div>
				<div class="col-sm-4 form-group">
						<label class="col-form-label" for="service_id">Servic Id</label>
						<select class="form-control form-control-sm" type="text" id="service_id"></select>
				</div>
		</div>
		<div class="row">
			<div class="col-sm-4 form-group">
				<button class="btn btn-outline-dark btn-sm" id="search_btn">Search</button>&nbsp;&nbsp;
				<button class="btn btn-outline-danger btn-sm" id="clear_btn">Clear</button>&nbsp;&nbsp;
				<button class="btn btn-outline-success btn-sm" id="clear_table_btn">Clear Table</button>
			</div>
		</div>
	</form>
	<div class="progress"></div>
	<table class="table table-sm" id="table-result">
			<thead>
				<th>Decision Point</th>
				<th>Ref Code</th>
				<th>Transaction Id</th>
				<th>Service Id</th>
				<th>Activity Type</th>
				<th>Id No.</th>
				<th>Terminal Type</th>
				<th>Create Date</th>
				<th></tr>
			</thead>
			<tbody>
				
			</tbody>
		</table>
</div>

<div class="container-fluid">
	<div class="row">
		<div id="exampleModalLong" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-lg" style="height: 90%;">
			<div class="modal-content" style="height: 90%;">
			  <div class="modal-header" style="background-color: #46A9FF; color: #FFFFFF">
				<h5 class="modal-title" id="modal_title"></h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				  <span aria-hidden="true" style="color: #FFFFFF">&times;</span>
				</button>
			  </div>
			  <div class="modal-body" id="modal_body" style="max-height: calc(100% - 10px);overflow-y: scroll;font-size: 14px;">
				<div style="word-break: break-all;" id="json"></div>
			  </div>
			</div>
		  </div>
		</div>
	</div>
</div>

<script src="<%=request.getContextPath()%>/log/script.js"> </script>
</body>
</html>