
var valueAction;
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
    	 function(){$(this).addClass("ResultOdd-haver-non-pointer");},
    	 function(){$(this).removeClass("ResultOdd-haver-non-pointer");}
    );
	$("Tr.ResultEven").hover(
    	 function(){$(this).addClass("ResultEven-haver-non-pointer");},
    	 function(){$(this).removeClass("ResultEven-haver-non-pointer");}
    );
});

function doActionSS(appNo, accId){
	if(validateSS()){
		var checkSubmit = true;
		if(valueAction == 'RE_ISSUE_CARD_NO'){
			if(checkClaimAppSS(accId, 'ReissueCardWebAction', appNo)){
				checkSubmit = false;
			}
			appFormName.action.value = "ReissueCardWebAction";
		}else if(valueAction == 'RE_ISSUE_CUST_NO'){
			if(checkClaimAppSS(accId, 'ReissueCustomerNoWebAction', appNo)){
				checkSubmit = false;
			}
			appFormName.action.value = "ReissueCustomerNoWebAction";
		}else if(valueAction == 'CANCEL'){
			if(checkClaimAppSS(accId, 'CancelCardMaintenenaceDetail', appNo)){
				checkSubmit = false;
			}
			appFormName.action.value = "CancelCardMaintenenaceDetail";
		}
		appFormName.handleForm.value = "N";
		if(checkSubmit){
			appFormName.submit();
		}
	}
}
function validateSS(){
	var appRecId = document.getElementsByName("SSaction").length;
	for( var i=0; i<appRecId ; i++){
		if(document.getElementsByName("SSaction")[i].checked == true){
			valueAction = document.getElementsByName("SSaction")[i].value;
			return true;
		}
	}
	alertMassage(SELECT_ACTION);
	return false;
}
function closeButton(){
	appFormName.action.value = "pageCardMaintenanceWebAction";
	appFormName.handleForm.value = "N";
	appFormName.submit();
}
function notePad(appRecId){
	var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadNotepadWebAction&appRecId="+appRecId;
	
	var width = 800;
	var higth = 350;
	var title = "Notepad";
	
	$dialog = $dialogEmpty;	
	$(".plan-dialog").remove();
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	var frameModule = (document.getElementById('frame-module')== null)? document.body :'.frame-module';
		$dialog.dialog({
		    resizable : false,
		    modal : true,
		    autoOpen: false,
		    draggable: false,
		    width: width,
		    height: higth,
		    title:title,
		    position: { 
				        my: 'center',
				        at: 'center',
				        of: $(frameModule)
		    		},
		    buttons :{
			        "Save" : function() {
				        saveNotePadM(appRecId);
				        $dialog.dialog("close");
				        closeDialog();
			        }
		    	},
		    close: function(){
		    	$('.plan-dialog').remove();
		    }
	    });
	$dialog.dialog('open');
}
function saveNotePadM(appRecId){
	$.post('FrontController?action=SaveNotePadWebAction&handleForm=N&appRecId='+appRecId);
}
function checkClaimAppSS(accId, action, appNo){
	var dataString = "ClassPackage=com.eaf.orig.ulo.pl.ajax&ClassName=CheckClaimAppSS&accId="+accId;
	var uri = "AjaxServlet";
	var isClaim = false;
	$.ajax({
		type : "POST",
		url : uri,
		data : dataString,
		async :false,
		success : function(data ,status ,xhr) {
			if(data!="999"){
				if(data == 3 && action != 'CancelCardMaintenenaceDetail') {
					$('#msg').html('<table width="100%" style="background-color: white;"><tr><td class="testL">*'+APP_NO+" "+appNo+" "+RE_ISSUED+'</td></tr></table>');
					$('#msg').css('color','red');
					$('[name=SSaction]').attr("disabled", "disabled");
					$('[name=Submit]').hide();
					isClaim = true;
				}
			} else if(data == 999) {
				$('#msg').html('<table width="100%" style="background-color: white;"><tr><td class="testL">*'+APP_NO+" "+appNo+" "+CANCEL_APPLICATION+'</td></tr></table>');
				$('#msg').css('color','red');
				$('[name=SSaction]').attr("disabled", "disabled");
				$('[name=Submit]').hide();
				isClaim = true;
			}
		},
		error : function(data){
		}
	});
	return isClaim;
}