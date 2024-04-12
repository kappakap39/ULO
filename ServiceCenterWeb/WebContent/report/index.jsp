<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
			font-size: 14px;
		}
		.container-fluid {
			width: 100%;
			padding-right: 50px;
			padding-left: 50px;
			margin-right: auto;
			margin-left: auto;
			font-size: 14px;
		}
	</style>
	<title>Report Transaction</title>
</head>
<body>
	<div class="container-fluid">
	<br/>
	<form>
		<div class="row">
			<div class="col-sm-4 form-group">
				<label class="col-form-label" for="ref_code">Application No.</label>
				<input class="form-control form-control-sm" type="text" id="ref_code" placeholder="Application No."/>
			</div>
			<div class="col-sm-4 form-group">
				<label class="col-form-label" for="transaction_id">Transaction Id</label>
				<input class="form-control form-control-sm" type="text" id="transaction_id" placeholder="rqUID"/>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-4 form-group">
				<label class="col-form-label" for="start_date">Start Date</label>
				<input class="form-control form-control-sm" type="text" id="start_date" data-format="dd-MM-yyyy hh:mm:ss"/>
<!-- 				<span class="input-group-addon"> -->
<!--                     <span class="glyphicon glyphicon-calendar"></span> -->
<!--                 </span> -->
			</div>
			<div class="col-sm-4 form-group">
				<label class="col-form-label" for="end_date">End Date</label>
				<input class="form-control form-control-sm" type="text" id="end_date" data-format="dd-MM-yyyy hh:mm:ss"/>
<!-- 				<span class="input-group-addon"> -->
<!--                     <span class="glyphicon glyphicon-calendar"></span> -->
<!--                 </span> -->
			</div>
		</div>
		<div class="row">
			<div class="col-sm-4 form-group">
				<button type="button" class="btn btn-outline-dark btn-sm" id="generate_report">Generate</button>&nbsp;&nbsp;
				<button type="button" class="btn btn-outline-danger btn-sm" id="clear_btn">Clear</button>&nbsp;&nbsp;
			</div>
		</div>
	</form>
	<div class="progress"></div><br/>
	<div class="tab-result"></div><br/>
	<div id="result-area"></div>
</div>

<script src="<%=request.getContextPath()%>/report/script.js"> </script>
</body>
</html>