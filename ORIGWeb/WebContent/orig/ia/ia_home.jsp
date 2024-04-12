<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache,no-store");
	response.setDateHeader("Expires", 0);
%>
<%@page contentType="text/html;charset=UTF-8"%>
<%-- <%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%> --%>
<%-- <%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %> --%>
<style>
	.pace-progress{
		transition : none!important;
	}
</style>
<script type="text/javascript">
// window.Pace.options.ajax.trackMethods = ['GET','POST','PUT','DELETE'];
// window.Pace.options.easeFactor = 0.2;
function showProgress(){
// 	$("#progressbar").show();
// 	$('#prog-close-button').hide();
// 	$("#progressbar").progressbar({
// 	  value: false
// 	});
// 	setTimeout(function(){
// 		$('#prog-close-button').show();
// 	}, 3000);
// 	Pace.restart();
}
function hideProgress(){
// 	$("#progressbar").hide("fast");
}
function displayInbox(fieldToPlace, func){
// 	showProgress();
	$(fieldToPlace).html("").hide("fast");
	var input = {};
	if($('#username').val()){
		input = {username: $('#username').val(), password : $('#password').val() };
	}
	var requestUrl = "/ORIGWeb/bpm/inbox";
	$.ajax({
		url: requestUrl,
		cache: false,
		data: input,
		success: function(data){
			$(fieldToPlace).html(data).show("fast");
			if(func){func();}
		},
		error: function(xhr, status, error){
			$(fieldToPlace).html("Getting inbox error! "+xhr.responseText).show("fast");
		},
		complete: function(data){
			hideProgress();
		}
	});
}
function getPrettyJson(json){
	var jsonObj = (typeof json == 'string'?JSON.parse(json):json);
	var jsonPretty = JSON.stringify(jsonObj, null, '\t');
	if(jsonPretty)jsonPretty = jsonPretty.replace(/\\/g,'');
	return jsonPretty;
}	
$(function(){	
	var unlockCredential = function(){$('#username').prop("readonly",false); $('#password').prop("readonly",false);};
// 	
// 	$('#start_new_app').click(function(){ 
// 		$this = $(this);
// 		$this.prop("disabled", true);
// 		if(validateStartAppForm()){
// 		var $data=$('#create-application *').serialize();
// 		ajax('com.eaf.orig.rest.controller.CreateIAApplication',$data,afterStartApp);
		/* 	showProgress();
			var requestUrl = "/ORIGWeb/ia?action=start&"+$('#create-application *').serialize();
			$.ajax({
				url: requestUrl,
				type: "PUT",
				contentType: "application/json",
				success: function(data){
					hideProgress();
					$('#displayFormApp').load('orig/ia/ia_start_app.jsp');
					displayResult(data, requestUrl);
				},
				error: function(xhr, status, error){
					hideProgress();
					$("#start_app_result").text("Start new app error! "+xhr.responseText).show("fast");
				},
				complete: function(data) {
					$this.prop("disabled", false);
					hideProgress();
				}
			}); */
// 		} 
// 	});
		
	$('#inbox-link').click(function(){
		var func = function(){unlockCredential();$('#credential').show("fast");};
		displayInbox($('#ia-inbox-content'),func);	
	});
	$('#refresh-inbox-task').click(function(){
        displayInbox($('#ia-inbox-content'),unlockCredential);
	});
	$('#prog-close-button').click(function(){
		hideProgress();
	});
});

// function afterStartApp(data){
//   	hideProgress();
// 	$('#displayFormApp').load('orig/ia/ia_start_app.jsp');
// }
// function validateStartAppForm(){
// 	$('#starterrorForm').html('');	
// 	var SCAN_DATE = $("[name='SCAN_DATE']").val();	
// 	var COVERPAGE_TYPE = $("[name='COVERPAGE_TYPE']").val();
// 	var DOC_SET_NO = $("[name='DOC_SET_NO']").val();
// 	var TEMPLATE = $("[name='TEMPLATE']").val();
// 	var CHANNEL = $("[name='CHANNEL']").val();
// 	var BRANCH_NO = $("[name='BRANCH_NO']").val();
// 	if(isEmpty(DOC_SET_NO)||isEmpty(COVERPAGE_TYPE)||isEmpty(SCAN_DATE)
// 		||isEmpty(TEMPLATE)||isEmpty(CHANNEL)||isEmpty(BRANCH_NO)){
// 		$('#starterrorForm').html('Please Input Require Field.');
// 		return false;
// 	}
// 	return true;
// }
	
// function displayResult(data, requestUrl){
// 	$("#start_app_result").text(getPrettyJson(data)).show("fast");
// 	$("#start_app_result").prepend("<pre>Request URL = "+requestUrl+"</pre>");
// }
</script>
<style>
div#progressbar {
	display: none;
	width: 60%;
	position: fixed;
	top: 45%;
	left: 20%;
	z-inbox: 9e9;
}

.ui-progressbar {
	position: relative;
}

.progress-label {
	position: absolute;
	left: 50%;
	top: 4px;
	width: 50px;
	margin-left: -30px;
	font-weight: bold;
	text-shadow: 1px 1px 0 #fff;
}

.progress-modol {
	position: fixed;
	top: 0;
	left: 0;
	background-color: black;
	opacity: 0.1;
	width: 100%;
	height: 100%;
}
table.inbox-table{
	font-family: Tahoma;
	border-collapse: collapse;
	border: 1px solid #bbbaba;
	font-size: 12pt;
	color: #000000;
	width: 600px;
	margin: 20px 0;
}
table.inbox-table>thead td{
	border-bottom: 1px solid #bbbaba;
	font-size: 120%;
	font-weight: bolder;
}
tr.view_app:hover{
	background-color: yellow;
	cursor: pointer;
}
#prog-close-button{
	display: none;
	position: fixed;
	top : 20px;
	left: 100%;
	margin-left: -100px;
	z-index: 9e11;
	color: red;
	cursor: pointer;
	font-weight: bolder;
}
</style>
<div id="tabs">
  <ul>
    <li><a href="#start-new-app"><span>Create Case</span></a></li>
<!--     <li><a href="#ia-inbox" id="inbox-link"><span>IA Inbox</span></a></li> -->
    <li><a href="#admin-tool" id="admin-tool-link"><span>Admin Tool</span></a></li>
    <li><a href="#admin-instance" ><span>Instance Detail</span></a></li>
    <li><a href="#admin-instance-data" ><span>Instance Data</span></a></li>
  </ul>
  <div id="start-new-app">  	
<!--   	<button id="start_new_app">Start New App</button><p></p>	 -->
  	<div id='displayFormApp'><jsp:include page="/orig/ia/ia_start_app.jsp"></jsp:include></div>
<!-- 	<pre id="start_app_result" style="display:none;"></pre> -->
  </div>
<!--   <div id="ia-inbox"> -->
<!--   	<div id="credential" style="display:none;"> -->
<!--   		<input type='button' id="refresh-inbox-task" value='Refresh'/> -->
<!--   		Username : <input type="text" name="username" id="username" value="ia"/> &nbsp;&nbsp;&nbsp; -->
<!--   		Password : <input type="text" name="password" id="password" value="pw"/> -->
<!--   	</div> -->
<!-- 	<div id="ia-inbox-content" style="display:none;"></div> -->
<!--   </div> -->
  <div id="admin-tool">
	<jsp:include page="/orig/ia/ia_admin.jsp"></jsp:include>
  </div>
  <div id="admin-instance">
	<jsp:include page="/orig/ia/admin/ia_instance_detail.jsp"></jsp:include>
  </div>
  <div id="admin-instance-data">
	<jsp:include page="/orig/ia/admin/ia_instance_data.jsp"></jsp:include>
  </div>
</div>
<script>
	$("#tabs").tabs();
</script>
<div id="progressbar"><div class="progress-label">Loading...<div class="progress-modol"></div></div><button id='prog-close-button'>Close</button></div>
