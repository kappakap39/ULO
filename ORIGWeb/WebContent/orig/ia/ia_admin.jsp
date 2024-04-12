<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="admin-tabs">
	<ul>
		<li><a href="#adm-view-tasks"><span>View All Tasks</span></a></li>
	</ul>
	<div id="adm-view-tasks">
		<div id="adm_credential">
			<form name="adm_credential_form" id="adm_credential_form">
				<button id="search-inbox">Search</button>
				<label>Username : </label><input type="text" name="username_to_inquire" id="username_to_inquire" value="bpm_admin" /> &nbsp;&nbsp;&nbsp; Password : <input type="text" name="password_to_inquire" id="password_to_inquire" value="password" />
				<button class="btn-user-auto-fill" data-user="ia">IA</button>
				<button class="btn-user-auto-fill" data-user="de1.1">DE1.1</button>
				<button class="btn-user-auto-fill" data-user="de1.2">DE1.2</button>
				<button class="btn-user-auto-fill" data-user="de2">DE2</button>
				<button class="btn-user-auto-fill" data-user="iq">IQ</button>
				<div id="adm-task-table"></div>

			</form>
		</div>
	</div>
</div>
<div id="adm-inbox-dialog" title="Re-assign Task">
	<form name="dialog_form" id="dialog_form">
		<div>
			Task ID : <span id="dialog_task_id"></span>
		</div>
		<div>
			<button id="reassgin_back">Reassign Back To Central Queue</button>
		</div>
		<div>
			<button type="button" id="reassgin_to_user">Reassign To</button>
			: <input name="user_to_reassign" id="user_to_reassign">
		</div>
		<div>
			<button type="button" id="set_task_priority">Set Priority</button>
			: <select name="dialog_priority" id="dialog_priority">
				<option value="Low">Normal (BPM Low - 4)</option>
				<option value="Normal">High (BPM Normal - 3)</option>
				<option value="High">Urgent (BPM High - 2)</option>
				<option value="Highest">Special (BPM Highest - 1)</option>
			</select>
		</div>
	</form>
</div>
<div id="status-dialog" title="Status"></div>
<style>
div#adm-inbox-dialog input,div#adm-inbox-dialog button {
	margin: 5px;
}
</style>
<script>
	$('#admin-tabs').tabs();
	$('#search-inbox').click(function() {
		searchInbox();
	});

	$('.btn-user-auto-fill').click(function(e) {
		e.preventDefault();
		var user = $(this).attr('data-user');
		var pass = $(this).attr('data-pass');
		if (pass == undefined || pass == '') {
			pass = "pw";
		}
		$('#username_to_inquire').val(user);
		$('#password_to_inquire').val(pass);
		$('#search-inbox').trigger('click');
	});

	function searchInbox() {
		showProgress();
		disableSearch();
		$.ajax({
			url : "/ORIGWeb/bpm/task/all",
			type : "GET",
			data : $('#adm_credential *').serialize(),
			success : function(data) {
				$('#adm-task-table').html(data);
			},
			error : function(xhr, status, error) {
				$("#adm-task-table").text("Search Inbox! " + xhr.responseText)
						.show("fast");
			},
			complete : function(data) {
				enableSearch();
				hideProgress();
			}
		});
	}

	function disableSearch() {
		$('#search-inbox').prop("disabled", true);
	}
	function enableSearch() {
		$('#search-inbox').prop("disabled", false);
	}
</script>

<script>
$('#adm-inbox-dialog').dialog({ autoOpen: false, modal: true, width: 400 });
$('#status-dialog').dialog({ autoOpen: false, modal: true, width: 700, close: function(){searchInbox();} });
$('#adm-task-table').on('click','.adm_view_app',function(){
	$this = $(this);
	var taskId = $this.find("input.adm-task-id").val();
	var taskType = $this.find("input.adm-task-q-type").val();
	$('#dialog_task_id').html(taskId);
	if(taskType == "Group"){
		$('#reassgin_back').prop("disabled", true);
	}else{
		$('#reassgin_back').prop("disabled", false);
	}
	$('#adm-inbox-dialog').dialog("open");
});

$('#reassgin_back, #reassgin_to_user').click(function(e){	
	e.preventDefault();
	$('#adm-inbox-dialog').dialog("close");
	showProgress();
	if($(this).attr("id") == "reassgin_back")$('#user_to_reassign').val('');
	$.ajax({
		url: "/ORIGWeb/bpm/app?"+$('#dialog_form').serialize()+"&action=reassign&taskId="+$("#dialog_task_id").html()+"&sup_username=bpm_admin&sup_password=bpm_admin",
		type: "PUT",
		contentType: "application/json",
		success: function(data){
			if(data && data.resultCode && data.resultCode == "S"){
				$('#status-dialog').html("<pre>"+JSON.stringify(data, null, 4)+"</pre>");
				$('#status-dialog').prepend("<font color='green'>Reassign successfully</font><hr/>");
			}else{
				$('#status-dialog').html("<pre>"+JSON.stringify(data, null, 4)+"</pre>");
				$('#status-dialog').prepend("<font color='red'>Reassign failed!</font><hr/>");
			}
			$('#status-dialog').dialog("open");
		},
		error: function(xhr, status, error){
			$('#status-dialog').text("Reassign fail! "+xhr.responseText).show("fast");
			$('#status-dialog').dialog("open");
		},
		complete: function(data) {
			hideProgress();
		}
	});
});
$('#adm-task-table').on('click','.delete-instance',function(e){
	e.stopPropagation();
	var $this = $(this);
	var instanceId = $this.attr("data-value");
	$('#adm-inbox-dialog').dialog("close");
	showProgress();
	$.ajax({
		url: "/ORIGWeb/bpm/instance/del?instanceId="+instanceId,
		type: "PUT",
		contentType: "application/json",
		success: function(data){
			if(data && data.resultCode && data.resultCode == "S"){
				$('#status-dialog').html("<pre>"+JSON.stringify(data, null, 4)+"</pre>");
				$('#status-dialog').prepend("<font color='green'>Delete successfully</font><hr/>");
			}else{
				$('#status-dialog').html("<pre>"+JSON.stringify(data, null, 4)+"</pre>");
				$('#status-dialog').prepend("<font color='red'>Delete failed!</font><hr/>");
			}
			$('#status-dialog').dialog("open");
		},
		error: function(xhr, status, error){
			$('#status-dialog').text("Reassign fail! "+xhr.responseText).show("fast");
			$('#status-dialog').dialog("open");
		},
		complete: function(data) {
			hideProgress();
		}
	});
});
$('#adm-task-table').on('click','.move-instance',function(e){
	e.stopPropagation();
	var $this = $(this);
	var instanceId = $this.attr("data-value");
	var activity = $this.attr("data-act");
	$('#adm-inbox-dialog').dialog("close");
	showProgress();
	$.ajax({
		url: "/ORIGWeb/bpm/instance/move?instanceId="+instanceId+"&activity="+activity,
		type: "PUT",
		contentType: "application/json",
		success: function(data){
			if(data && data.resultCode && data.resultCode == "S"){
				$('#status-dialog').html("<pre>"+JSON.stringify(data, null, 4)+"</pre>");
				$('#status-dialog').prepend("<font color='green'>Moving Instance successfully</font><hr/>");
			}else{
				$('#status-dialog').html("<pre>"+JSON.stringify(data, null, 4)+"</pre>");
				$('#status-dialog').prepend("<font color='red'>Moving failed!</font><hr/>");
			}
			$('#status-dialog').dialog("open");
		},
		error: function(xhr, status, error){
			$('#status-dialog').text("Reassign fail! "+xhr.responseText).show("fast");
			$('#status-dialog').dialog("open");
		},
		complete: function(data) {
			hideProgress();
		}
	});
});
$('#set_task_priority').click(function(e){
	e.stopPropagation();
	var $this = $(this);
	$('#adm-inbox-dialog').dialog("close");
	showProgress();
	var priority = $('#dialog_priority').val();
	var taskId = $('#dialog_task_id').html();
	$.ajax({
		url: "/ORIGWeb/bpm/task/priority?taskId="+taskId+"&priority="+priority,
		type: "PUT",
		contentType: "application/json",
		success: function(data){
			if(data && data.resultCode && data.resultCode == "S"){
				$('#status-dialog').html("<pre>"+JSON.stringify(data, null, 4)+"</pre>");
				$('#status-dialog').prepend("<font color='green'>Update Priority successfully</font><hr/>");
			}else{
				$('#status-dialog').html("<pre>"+JSON.stringify(data, null, 4)+"</pre>");
				$('#status-dialog').prepend("<font color='red'>Update Priority failed!</font><hr/>");
			}
			$('#status-dialog').dialog("open");
		},
		error: function(xhr, status, error){
			$('#status-dialog').text("Reassign fail! "+xhr.responseText).show("fast");
			$('#status-dialog').dialog("open");
		},
		complete: function(data) {
			hideProgress();
		}
	});
});
</script>