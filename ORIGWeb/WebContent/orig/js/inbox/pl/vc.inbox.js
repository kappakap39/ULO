/**
 * Sankom
 */
$(document).ready(function (){
	var height = $('.PanelSecond').outerHeight();
	if(height < $(window).height()-60){
		$('.PanelSecond').css({
			'height': $(window).height()-60
		});
	}
	$('.nav-inbox').slimScroll({
		height: $(window).height()+'px'
	});	
	$("Tr.ResultOdd").hover(
    	 function(){$(this).addClass("ResultOdd-haver");},
    	 function(){$(this).removeClass("ResultOdd-haver");}
    );
	$("Tr.ResultEven").hover(
	     function(){$(this).addClass("ResultEven-haver");},
	     function(){$(this).removeClass("ResultEven-haver");}
	 );
	$("Tr.Result-Obj").click(function(e){	
		e.preventDefault();
		try{
			var obj = $(this).attr('id').split(/\s+/);
			blockScreen();
			var form=document.appFormName;
			form.formID.value="KEC_FORM";
			form.currentTab.value="MAIN_TAB";
			form.action.value="LoadPLAppForm";
			form.handleForm.value = "N";
			form.appRecordID.value = obj[0];
			form.roleElement.value="VC";
			form.submit();
		}catch(e){
			unblockScreen();
			var msg = ERROR_JS+e.message;
			alertMassageERROR(msg);
		}
	});
	LoadButtonInbox();
	GetTotalJobAndTarget();
});

function loadApplication(appRecordID,appStatus,jobState){
	try{
		blockScreen();
	    var form = document.appFormName; 
		form.formID.value="KEC_FORM";
		form.currentTab.value="MAIN_TAB";
		form.action.value="LoadPLAppForm";
		form.handleForm.value = "N";
		form.appRecordID.value = appRecordID;
		form.appStatus.value = appStatus;
		form.jobState.value = jobState;
		form.roleElement.value="VC";
		form.submit();	
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}

function DisplayButton(data){
	if(data == null || data == '')
		return;
	$('#button-job-status').html(data);	
	DestoryButtonNormalEngine('#button-job-wf');
	DestoryButtonRedEngine('#button-job-wf');
	var buttonVal = $("#button-job-wf").val();
	if(buttonVal == JOB_STATUS_ON){
		ButtonNormalEngine("#button-job-wf");
	}else{
		ButtonRedEngine("#button-job-wf");
	}
}
function ChangeButtonStatus(){
	var buttonVal = $("#button-job-wf").val();
	var statusButton = 'N';
	if(buttonVal == JOB_STATUS_OFF){
		statusButton = 'Y';
	}
	var dataString ="statusButton="+statusButton;
	ajaxHtmlManual('ButtonOnJobAction',packageDefault,dataString,DisplayButton);
}
function GetTotalJobAndTarget() {
	var username = $('#currentUser').val();
	var role = $('#CurrentRole').val();
//	ajaxDisplayElementJson('GetTargetCurrent', packageDefault, '&username='
//			+ username + '&role=' + role);
//	$('#target-point-day').css("color", "green");
//	var color = "red";
//	if ($('#target-point-day').text() == $('#total-job-done').text()) {
//		color = "green";
//	}
//	$('#total-job-done').css("color", color);
	ajaxJsonManualAsync('GetTargetCurrent',packageDefault,'&username='+username+'&role='+role,DisplayTotalAndTarget);
}
function DisplayTotalAndTarget(data){
	jsonDisplayElementById(data);
	$('#target-point-day').css("color", "green");
	var color = "red";
	if($('#target-point-day').text()==$('#total-job-done').text()){
		color="green";
	}
	$('#total-job-done').css("color", color);
}
function LoadButtonInbox() {
	ajaxHtmlManualAsync('GetButtonWfInbox', packageDefault, '', DisplayButton);
}
function DisplayButton(data){
	if(data == null || data == '')
		return;
	$('#button-job-status').html(data);	
	DestoryButtonNormalEngine('#button-job-wf');
	DestoryButtonRedEngine('#button-job-wf');
	var buttonVal = $("#button-job-wf").val();
	if(buttonVal == JOB_STATUS_ON){
		ButtonNormalEngine("#button-job-wf");
	}else{
		ButtonRedEngine("#button-job-wf");
	}
}
function clickPageList(atPage){
	$("#atPage").val(atPage);
	$("#action").val('VCPLInbox');
	$("#handleForm").val('N');
	$("#appFormName").submit();
}