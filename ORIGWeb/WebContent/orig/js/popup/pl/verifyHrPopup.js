/**
 * Rawi Songchaisin
 */

$(document).ready(function () {
	loadVerifyHrInformation();
	var dataString = "className=VerifyHRWaivedResult&packAge=3&returnType=0";
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,	
		dataType: "json",
		success : function(data ,status ,xhr){
			DisplayVerifyHR(data);	
		},
		error : function(response){
		}
	});
});
function DisplayVerifyHR(data){
	$.map(data, function(item){
		if(item.id == 'STYLE'){
			$('#result_1033').removeClass();
			$('#result_1033').addClass(item.value);
		}else{		
			if($('#'+item.id).attr('type') != undefined){
				switch ($('#'+item.id).attr('type')) {
					case 'text': $('#'+item.id).val(item.value); break;
					case 'textbox': $('#'+item.id).val(item.value); break;
					case "hidden": $('#'+item.id).val(item.value); break;
					default:break;
				}	
			}else{												
				$('#'+item.id).html(item.value);
			}
		}
	});	
}
function loadVerifyHrInformation(){
	var dataString = $("#div-verhr *").serialize();
	ajaxDisplayElementJsonAsync('LoadVerifyHR',packageOrigRules,dataString);	
	otherTelNumber();
}
function otherTelNumber(){
	DestoryTextBoxTelEngine('#verhr-other-phoneno');
	if($('#verhr-phoneno').val() == 99){
		$('#verhr-other-phoneno').removeClass("textbox-tel-view").addClass("textbox-tel");
		$('#verhr-other-phoneno').attr('readOnly',false);
		TextBoxTelEngine('#verhr-other-phoneno');
	}else{
		$('#verhr-other-phoneno').removeClass("textbox-tel").addClass("textbox-tel-view");
		$('#verhr-other-phoneno').attr('readOnly',true);
		$('#verhr-other-phoneno').val('');
	}
}
function validateVerifyHr(){
	$('#div-vercus-madatory').html('');
	var obj = [];
	if($('#verhr-phoneno').val() == null || $('#verhr-phoneno').val() == ''){		
		obj.push('<div>'+MSG_REQUIRE_PHONENO+'</div>');
	}
	if($('#verhr-phoneno').val() == '99' &&  $('#verhr-other-phoneno').val() == ''){
		obj.push('<div>'+MSG_REQUIRE_OTHER_PHONENO+'</div>');
	}
	if($('#verhr-phonestatus').val() == null || $('#verhr-phonestatus').val() == ''){		
		obj.push('<div>'+MSG_REQUIRE_PHONE_STATUS+'</div>');
	}
	if(obj != null && obj.length >0){
		$.map(obj, function(item){
			$('#div-verhr-madatory').append(item);
		});
		return false;
	}
	return true;
}
function addVerifyHRData(){
	$('#div-verhr-madatory').html('');
	if(!validateVerifyHr()) return;
	var dataString = $("#div-verhr *").serialize();
	$('#verhr-notfound').remove();
	ajaxDisplayElementJson('AddVerifyHR',packageOrigRules,dataString);
	ClearValueVerHr();
}
function MandatoryVerifyHR(){
	$('#div-verhr-madatory').html('');
	var dataString = "className=MandatoryVerifyHR&packAge=2&returnType=0&verhr-final-staus="+$('#verhr-final-staus').val();
	var result = false;
	jQuery.ajax( {
		type :"POST",
		url :'AjaxDisplayServlet',
		data :dataString,	
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){
			if(data != null && data.length > 0){
				$.map(data, function(item){
					$('#div-verhr-madatory').append('<div>'+item.value+'</div>');
				});
				result = false;		
			}else{
				result = true;
			}
		},
		error : function(data){			
		}
	});
	return result;
}
function SaveVerifyHR(){
	if($('#verhr-final-staus').val() != WEIVED){
		if(MandatoryVerifyHR()){
			var dataString = 'verhr-final-staus='+$('#verhr-final-staus').val();
			$AjaxFrontController('SaveVerifyHRWebAction','N',null,dataString,DisplayResultVerifyHR);
			ClearValueVerHr();
		}
	}else{
		CloseVerifyHR();
	}
}
function CloseVerifyHR(){
	$dialog.dialog("close");	
	closeDialog();
}
function DisplayResultVerifyHR(data){
	DisplayVerifyHR(data);	
	$dialog.dialog("close");	
	closeDialog();
}
function ClearValueVerHr(){
	$('#verhr-phonestatus').val('');
	$('#verhr-phoneno').val('');
	$('#verhr-remark').val('');
	$('#verhr-other-phoneno').val('');
	$('#div-verhr-madatory').html('');
	loadVerifyHrInformation();
}