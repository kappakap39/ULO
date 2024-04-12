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
    	 function(){$(this).addClass("ResultOdd-haver-non-pointer");},
    	 function(){$(this).removeClass("ResultOdd-haver-non-pointer");}
    );
	$("Tr.ResultEven").hover(
    	 function(){$(this).addClass("ResultEven-haver-non-pointer");},
    	 function(){$(this).removeClass("ResultEven-haver-non-pointer");}
	);
	$('img.viewImg').click(function(e){
		e.preventDefault();
		var obj = $(this).attr('class').split(/\s+/);
		parent.leftFrame.closeMenuFrame();
		openNcbImage(obj[1]);
	});
	TotalJobCBCentralQueue();
});
function TotalJobCBCentralQueue(){
	var dataString = "className=TotalJobCentralQueue&packAge=3&returnType=0";
	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,
		success : function(data,status,xhr){
			$.map(data, function(item){
				$('#cb-total-job').html(item.value);				
			});
		},
		error : function(data){
		}
	});
}
function retrieveJob(){
	try{
		if(validateJob()){
			blockScreen();
			appFormName.action.value = "CBPullJobWebAction";
			appFormName.handleForm.value = "N";
			appFormName.submit();
		}
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function reAllocate(){
	try{
		blockScreen();
		appFormName.action.value = "CBAllocateJobWebAction";
		appFormName.handleForm.value = "N";
		appFormName.submit();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function validateJob(){
	var validate = true;
	if($('#totalJob').val() == ''){
		alertMassage("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01\u0E08\u0E33\u0E19\u0E27\u0E19\u0E07\u0E32\u0E19\u0E17\u0E35\u0E48\u0E15\u0E49\u0E2D\u0E07\u0E01\u0E32\u0E23");
		validate = false;
		return validate;
	}
	var dataString = "className=MandatoryCentralQueue&packAge=3&returnType=0";
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,
		success : function(data,status,xhr){
			$.map(data, function(item){
				if(item.value == 'error'){
					validate = false;
				}else{
					if(item.value == '0'){
						validate = false;
						alertMassage(EMPTY_QUEUE);
						$('#cb-total-job').html(item.value);
					}else{
						validate = true;
						$('#cb-total-job').html(item.value);
					}
				}				
			});
		},
		error : function(data){
			validate = false;
		}
	});
	return validate;
}
function CBsendBack(appRecId){
	var url = "/ORIGWeb/FrontController?action=CBSendBackPopupWebAction&appRecId="+appRecId;
	$('#appRecId').val(appRecId);
	$dialog = $dialogEmpty;
	var width = 680;
	var higth = 210;
	var title = 'Send Back';
	$(".plan-dialog").remove();
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
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
		        of: $(document.body)
    		},
		    buttons : {
			        "Ok":function(){
			        	saveSendBack();
			        },
			        "Cancel" : function() {
			        	$dialog.dialog("close");
			        	closeDialog();
			        }
		    	},
		    close: function(){
		    	closeDialog();
		    }
	});
   $dialog.dialog("open");
}
function displayRemark(reasonCode){
	if(reasonCode=='07'){
		$("#remark").removeAttr("disabled");
	}else{
		$("#remark").attr("disabled","true");
		$("#remark").removeAttr("value");
	}
}
function validateSendBack(){
	var reasonCode = $('#reasonCode').val();
	var remark = $('#remark').val();	
	$('#sendReasonCode').val(reasonCode);
	$('#sendRemark').val(remark);
	if(reasonCode==""){
		alertMassage(SELECT_REASON);
		return false;
	}else if((reasonCode=="07") && (remark=="")){
		alertMassage(REASON_DETAIL);
		return false;
	}	
	return true;
}
function saveSendBack(){
	try{
		if(validateSendBack()){
	    	blockScreen();
			$('#action').val('CBSendBackWebAction');
			$('#handleForm').val('N');		
			$('#appFormName').submit();
		}
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function CBsendToKBank(){
	if(validateSave()){
		alertMassageYesNoFunc(SEND_TO_KCBS,sendToKCBS, null);
	}
}
function sendToKCBS(){
	try{
		blockScreen();
		appFormName.action.value = "SendToKBankBureauWebAction";
		appFormName.handleForm.value = "N";
		appFormName.submit();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function validateSave(){
    var checkBoxApp = document.getElementsByName("checkAppRecId");
	if(checkBoxApp!=null && checkBoxApp.length>0){	
		var check = false;
		for( var i=0; i<checkBoxApp.length; i++){
			if(checkBoxApp[i].checked){
				check = true;
				break;
			}
		}
		if(!check){
			alertMassage(REQUEST_APP);
			return false;
		}
	} else {
		alertMassage(REQUEST_APP);
		return false;
	}
	return true;
	
}
function openNcbImage(appRedId){
	var url = "/ORIGWeb/FrontController?action=NCB_IMAGES&appRecId="+appRedId;
	$dialog = $dialogEmpty;
	var width = 1200;
	var higth = $(window).height()-71;
	var title = 'NCB';
	$(".plan-dialog").remove();
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
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
		        of: $(document.body)
    		},
		    buttons : {
			        "Close" : function() {
			        	$dialog.dialog("close");
			        	closeDialog();
			        }
		    	},
		    close: function(){
		    	closeDialog();
		    }
	});
   $dialog.dialog("open");
//	openDialog(url,width,higth,scrollbars=0,setPrefs);
}
function clickPageList(atPage){
	$("#atPage").val(atPage);
	$("#action").val('CBPLInbox');
	$("#handleForm").val('N');
	$("#appFormName").submit();
}