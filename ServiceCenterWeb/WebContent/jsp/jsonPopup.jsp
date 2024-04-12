<!DOCTYPE html>
<%@page contentType="text/html; charset:UTF-8" %>
<head>
	<script>
		$(document).ready(function(){
// 			console.clear();
			if($("textarea[name=requestJson]").val()!=""){
				var requestJson = $("textarea[name=requestJson]").val();
				var responseJson = $("textarea[name=responseJson]").val();
			  	var jsonFormatRq = JSON.stringify(JSON.parse(requestJson), null, 4);
	  			var jsonFormatRs = JSON.stringify(JSON.parse(responseJson), null, 4);
	  			console.log(jsonFormatRq);
	  			console.log(jsonFormatRs);
				$("pre[name=requestJsonPopup]").text(jsonFormatRq);
	  			$("pre[name=responseJsonPopup]").text(jsonFormatRs);
  			}
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" href="#request">Request Data</a></li>
		<li><a data-toggle="tab" href="#response">Response Data</a></li>
	</ul>
	<div class="tab-content">
		<div class="tab-pane fade in active" id="request">
			<br>
			<pre name="requestJsonPopup">Not have a service</pre>
		</div>
		<div class="tab-pane fade" id="response">
			<br>
			<pre name="responseJsonPopup">Not have a service</pre>
		</div>
	</div>
</body>