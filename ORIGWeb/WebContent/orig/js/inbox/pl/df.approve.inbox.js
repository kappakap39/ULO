/**
 * 
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
function buttonStatusOnJob(){
	var curStatusJob = $("#StatusOnJob").val();
	if("Off"==curStatusJob){
		curStatusJob = "Y";
	}else{
		curStatusJob = "N";
	}
	var dataString = "ClassPackage=com.eaf.orig.ulo.pl.ajax&ClassName=ButtonOnJobAction&status="+curStatusJob; 
	var uri = "AjaxServlet";
		
	jQuery.ajax( {
	type :"POST",
	url :uri,
	data :dataString,
	async :false,
		
		success : function(data ,status ,xhr) {
			if("N"==data){
				data = "Off";
				$("#StatusOnJob").removeClass("button");
				$("#StatusOnJob").addClass("button-red");
			}else{
				data = "On";
				$("#StatusOnJob").removeClass("button-red");
				$("#StatusOnJob").addClass("button");
			}
			$("#StatusOnJob").val(data);
 		},
		error : function(data){
			alert("error"+data);
		}
	});
}
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
	var dataString ="statusButton="+statusButton;
	ajaxHtmlManual('ButtonOnJobAction',packageDefault,dataString,DisplayButton);
}

function clickPageList(atPage){
	$("#atPage").val(atPage);
	$("#action").val('DFPLInbox');
	$("#handleForm").val('N');
	$("#appFormName").submit();
}
function GetTotalJobAndTarget(){
	var username = $('#currentUser').val();
	var role = $('#CurrentRole').val();
//	ajaxDisplayElementJson('GetTargetCurrent',packageDefault,'&username='+username+'&role='+role);
//	$('#target-point-day').css("color", "green");
//	var color = "red";
//	if($('#target-point-day').text()==$('#total-job-done').text()){color="green";}
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