<link rel="StyleSheet" href="../css/MainStylesheet.css" type="text/css">

<script type="text/javascript" src="../js/template/jquery-1.7.1.js"></script>
<script type="text/javascript" src="../js/template/jquery-ui-1.8.16.js"></script>

<script>
function createScheduler(){
	if((document.createSchedulerForm.schedulerName.value=='' ||
		document.createSchedulerForm.startInterval.value=='' ||
		document.createSchedulerForm.repeat.value=='' ||
		document.createSchedulerForm.repeatInterval.value=='') && document.createSchedulerForm.schedulerType.value=='INTERVAL'){
		alert('Mandatory fields is required.');
	}else if((document.createSchedulerForm.runDay.value=='' ||
		document.createSchedulerForm.startHours.value=='' ||
		document.createSchedulerForm.startMinute.value=='') && document.createSchedulerForm.schedulerType.value=='CRON'){
		alert('Mandatory fields is required.');
	}else{
		//alert('create');
		document.createSchedulerForm.submit();
	}
}
function cancelScheduler(){
	$('#processType').val('cancel');
	document.createSchedulerForm.action = "scheduler.jsp";
	document.createSchedulerForm.submit();
}
</script>

<form name="createSchedulerForm" action="scheduler.jsp">
<input type="hidden" id="processType" name="processType" value="create">
<input type="hidden" name="pageProcess" value="1">
<div class="TextHeaderNormal">Task</div>
<div class="PanelThird">
<table class="TableFrame">
  <tr> 
    <td class="textR" width="20%">Name&nbsp;:&nbsp;</td>
    <td class="inputL" width="25%">
    	<select class="combobox" name="schedulerName">
    		<option value="Auto Send Email Queue">Auto Send Email Queue</option>
    		<option value="Auto Send SMS Queue">Auto Send SMS Queue</option>
    	</select>
    </td>
    <td class="textR" width="25%">Type&nbsp;:&nbsp;</td>
    <td class="inputL" width="30%">
    	<select class="combobox" name="schedulerType" id="schedulerType" onchange="enableField(this)">
    		<option value="INTERVAL">Interval</option>
    	    <!--<option value="CRON">Fix Time</option>-->
    	</select>
    </td>
  </tr>
</table>
</div>
<div class="TextHeaderNormal">Details</div>
<div class="PanelThird">
<table class="TableFrame">
  <tr> 
    <td class="textR" width="20%">Start Interval<span style="color: red" id="start_interval_star">*</span>&nbsp;:&nbsp;</td>
    <td class="inputL" width="25%"><input type="text" class="textbox" name="startInterval">&nbsp;minutes&nbsp;</td>
    <td class="textR" width="25%">Day to Run<span style="color: red" id="day_to_run_star"></span>&nbsp;:&nbsp;</td>
    <td class="inputL" width="30%">
    	<select class="combobox" name="runDay" disabled>
    	    <option value="?">Every Days</option>
    		<option value="SUN">Sunday</option>
    		<option value="MON">Monday</option>
    		<option value="TUE">Tuesday</option>
    		<option value="WED">Wednesday</option>
    		<option value="THU">Thursday</option>
    		<option value="FRI">Friday</option>
    		<option value="SAT">Saturday</option>
    	</select>
    </td>
  </tr>
  <tr> 
    <td class="textR">Repeat<span style="color: red" id="repeat_star">*</span>&nbsp;:&nbsp;</td>
    <td class="inputL"><input type="text" class="textbox" name="repeat">&nbsp;times&nbsp;</td>
    <td class="textR">Start hours<span style="color: red" id="start_hours_star"></span>&nbsp;:&nbsp;</td>
    <td class="inputL"><input type="text" class="textbox" name="startHours" disabled>&nbsp;Hours(0-23)&nbsp;</td>
  </tr>
  <tr> 
    <td class="textR">Repeat Interval<span style="color: red" id="repeat_interval_star">*</span>&nbsp;:&nbsp;</td>
    <td class="inputL"><input type="text" class="textbox" name="repeatInterval">&nbsp;minutes&nbsp;</td>
    <td class="textR">Start minute<span style="color: red" id="start_minute_star"></span>&nbsp;:&nbsp;</td>
    <td class="inputL"><input type="text" class="textbox" name="startMinute" disabled>&nbsp;minutes(0-59)&nbsp;</td>
  </tr>
</table>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr height="20"></tr>
  <tr> 
    <td align="right">
		<input type="button" class="button" name="create" value="Create" onclick="createScheduler();">&nbsp;&nbsp;
		<input type="button" class="button" name="cancel" value="Cancel" onclick="cancelScheduler();">&nbsp;&nbsp;
		<input type="reset" class="button" name="reset" value="Reset">
  </tr>
</table>
</form>
<script>
function enableField(obj){
	if(obj.value == "CRON"){
		document.createSchedulerForm.startInterval.disabled = true;
		$('#start_interval_star').html('');
		document.createSchedulerForm.repeat.disabled = true;
		$('#repeat_star').html('');
		document.createSchedulerForm.repeatInterval.disabled = true;
		$('#repeat_interval_star').html('');
		document.createSchedulerForm.runDay.disabled = false;
		$('#day_to_run_star').html('*');
		document.createSchedulerForm.startHours.disabled = false;
		$('#start_hours_star').html('*');
		document.createSchedulerForm.startMinute.disabled = false;
		$('#start_minute_star').html('*');
	}else{
		document.createSchedulerForm.startInterval.disabled = false;
		$('#start_interval_star').html('*');
		document.createSchedulerForm.repeat.disabled = false;
		$('#repeat_star').html('*');
		document.createSchedulerForm.repeatInterval.disabled = false;
		$('#repeat_interval_star').html('*');
		document.createSchedulerForm.runDay.disabled = true;
		$('#day_to_run_star').html('');
		document.createSchedulerForm.startHours.disabled = true;
		$('#start_hours_star').html('');
		document.createSchedulerForm.startMinute.disabled = true;
		$('#start_minute_star').html('');
	}
}
</script>
