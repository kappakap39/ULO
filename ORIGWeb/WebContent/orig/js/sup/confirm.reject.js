/**
 * 
 */
$(document).ready(function(){
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
	
	LoadButtonInbox();
	countJobComplete();
	countAutoQueue();	
	
});

function loadRejectApplication(appRedID){
	try{
		blockScreen();
		var form = document.appFormName;
		form.formID.value = "KEC_FORM";
		form.currentTab.value = "MAIN_TAB";
		form.action.value = "LoadPLAppForm";
		form.handleForm.value = "N";
		form.appRecordID.value = appRedID;
		form.submit();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);	
	}
}

function validateSave(){
    var checkBoxApp = document.getElementsByName("check");
	if(checkBoxApp != null && checkBoxApp.length > 0){	
		var check = false;
		for(var i=0; i<checkBoxApp.length; i++){
			if(checkBoxApp[i].checked){
				check = true;
				break;
			}
		}
		if(!check){
			alertMassage(SELECT_APP);
			return false;
		}
	}else{
		alertMassage(SELECT_APP);
		return false;
	}
	return true;
}
function confirmReject(){
	if(validateSave()){
		alertMassageYesNoFunc(CONFIRM_REJECT, submitConfirmReject, null);
	}
}
function submitConfirmReject(){
	try{
		blockScreen();
		appFormName.action.value = "ConfirmRejectWebAction";
		appFormName.handleForm.value = "N";
		appFormName.submit();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function detail(appRecId){
	try{
		blockScreen();
		appFormName.appRecordID.value = appRecId;
		appFormName.action.value = "LoadPLAppForm";
		appFormName.handleForm.value = "N";
		appFormName.submit();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function countAutoQueue(){	
	var dataString = "ClassPackage=com.eaf.orig.ulo.pl.product.ajax&ClassName=TrackingQueryDB&method=countInboxAutoQueueSup"; 
	var uri = "AjaxServlet";		
	jQuery.ajax({
		type :"POST",
		url :uri,
		data :dataString,
		async :true,		
		success : function(data ,status ,xhr) {
			$("#autoQueue").html(data);
		},
		error : function(data){
		}
	});
}
function countJobComplete(){	
	var dataString = "ClassPackage=com.eaf.orig.ulo.pl.product.ajax&ClassName=CountJobComplete"; 
	var uri = "AjaxServlet";		
	jQuery.ajax({
		type :"POST",
		url :uri,
		data :dataString,
		async :true,		
		success : function(data ,status ,xhr) {
			$("#countJobComplete").html(data);
		},
		error : function(data){
		}
	});
}
function LoadButtonInbox(){
	ajaxHtmlManualAsync('GetButtonWfInbox', packageDefault, '', DisplayButton);
}
function DisplayButton(data){
	if(data == null || data == ''){
		return;
	}
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
	ajaxHtmlManual('ButtonOnJobAction', packageDefault, dataString, DisplayButton);
}
function clickPageList(atPage){
	try{
		blockScreen();
		var form = window.document.appFormName;
		form.atPage.value = atPage;
		form.itemsPerPage.value = "";
		form.action.value = "InboxSupWebAction";
		form.handleForm.value = "N";
		form.submit();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
