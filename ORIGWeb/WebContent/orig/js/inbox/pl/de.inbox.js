/**
 * Pipe
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
    	 function(){
	 		 	if($(this).hasClass('text-red')){
	 		 		$(this).addClass("ResultOdd-haver");
	 		 		$(this).addClass("text-red-haver");
			 	}else{
			 		$(this).addClass("ResultOdd-haver");			 		 
			 	}
    		 },
    	 function(){
			 	$(this).removeClass("ResultOdd-haver");
			 	$(this).removeClass("text-red-haver");
			 }
    );
	$("Tr.ResultEven").hover(
    	 function(){
    		   if($(this).hasClass('text-red')){
	 		 		$(this).addClass("ResultEven-haver");
	 		 		$(this).addClass("text-red-haver");
			 	}else{
			 		$(this).addClass("ResultEven-haver");	
			 	}
    		 },
    	 function(){
    			 $(this).removeClass("ResultEven-haver");
    			 $(this).removeClass("text-red-haver");
    		 }
	);
	$("Tr.Result-Obj").click(function(e){	
		e.preventDefault();
		try{
			var obj = $(this).attr('id').split(/\s+/);			
			blockScreen();
			$("#formID").val('KEC_FORM');
			$("#currentTab").val('MAIN_TAB');
			$("#handleForm").val('N');
			$("#action").val('LoadPLAppForm');
			$("#appRecordID").val(obj[0]);
			$("#appFormName").submit();
		}catch(e){
			unblockScreen();
			var msg = ERROR_JS+e.message;
			alertMassageERROR(msg);
		}
	});
	LoadButtonInbox();
	GetTotalJobAndTarget();
});
function LoadButtonInbox(){
	ajaxHtmlManualAsync('GetButtonWfInbox',packageDefault,'',DisplayButton);
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
	var dataString = "statusButton=" + statusButton;
	ajaxHtmlManual('ButtonOnJobAction',packageDefault,dataString,DisplayButton);
}
//function GetTotalJobDoneCurrentDate(){
//	ajaxDisplayElementHtmlAsync('GetTotalJobDoneCurrentDate',packageDefault,'','total-job-done');
//}
//function GetTargetApplication(){
//	var dataString = 'busClassGroup=FCP_ALL_ALL';
//	ajaxDisplayElementHtmlAsync('GetTargetApplication',packageDefault,dataString,'target-point-day');
//}
function clickPageList(atPage){
	$("#atPage").val(atPage);
	$("#action").val('DEPLInbox');
	$("#handleForm").val('N');
	$("#appFormName").submit();
}

function GetTotalJobAndTarget(){
	var username = $('#currentUser').val();
	var role = $('#CurrentRole').val();	
//	ajaxDisplayElementJson('GetTargetCurrent',packageDefault,'&username='+username+'&role='+role);
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