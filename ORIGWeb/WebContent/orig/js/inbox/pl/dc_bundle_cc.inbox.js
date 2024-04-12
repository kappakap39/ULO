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
			form.formID.value = "KEC_FORM";
			form.currentTab.value = "MAIN_TAB";
			form.action.value = "LoadPLAppForm";
			form.handleForm.value = "N";
			form.appRecordID.value = obj[0];
			form.roleElement.value = "DC";
			form.submit();
		}catch(e){
			unblockScreen();
			var msg = ERROR_JS+e.message;
			alertMassageERROR(msg);
		}
	});
	GetTotalJobAndTarget();
});

function loadApplication(appRecordID,appStatus,jobState){
    var form=document.appFormName;
    //alert(appRecordID);
	//displayLoading('Loading...');
	form.formID.value="KEC_FORM";
	form.currentTab.value="MAIN_TAB";
	form.action.value="LoadPLAppForm";
	form.handleForm.value = "N";
	form.appRecordID.value = appRecordID;
	form.appStatus.value = appStatus;
	form.jobState.value = jobState;
	//window.parent.target = "_parent";
	form.submit();	
}

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
function clickPageList(atPage){
	$("#atPage").val(atPage);
	$("#action").val('DCBundleInbox');
	$("#handleForm").val('N');
	$("#appFormName").submit();
}