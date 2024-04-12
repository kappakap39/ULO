/**
 * Rawi Songchaisin
 */
$(document).ready(function(){
	initVerCustomer();	
	var dataString = "className=VerifyCustomerWaivedResult&packAge=3&returnType=0";
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,	
		dataType: "json",
		success : function(data ,status ,xhr){
			DisplayVerifyCustomer(data);	
		},
		error : function(response){
		}
	});
	
});
function DisplayVerifyCustomer(data){
	$.map(data, function(item){
		if(item.id == 'STYLE'){
			$('#result_1032').removeClass();
			$('#result_1032').addClass(item.value);
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
function initVerCustomer(){
	loadInformation();
	loadTelephone();	
}
function loadInformation(){
	var dataString = $("#div-vercus-info *").serialize();
	ajaxDisplayElementJsonAsync('LoadVerifyCustomer',packageOrigRules,dataString);
}
function loadTelephone(){
	var dataString =  $("#div-vercus-info *").serialize();
	ajaxDisplayElementJsonAsync('LoadCustomerTel',packageOrigRules,dataString);
}
function validateVerCustomer(){
	$('#div-vercus-madatory').html('');
	var obj = [];
	if($('#vercus-personal-type').val() == ''){		
		obj.push('<div>'+MSG_REQUIRE_PERSONAL+'</div>');
	}
	if($('#vercus-address-type').val() == ''){		
		obj.push('<div>'+MSG_REQUIRE_ADDRESS_TYPE+'</div>');
	}
	if($('#vercus-phoneno').val() == ''){		
		obj.push('<div>'+MSG_REQUIRE_PHONENO+'</div>');
	}
	if($('#vercus-phonestatus').val() == ''){		
		obj.push('<div>'+MSG_REQUIRE_PHONE_STATUS+'</div>');
	}
	if(obj != null && obj.length >0){
		$.map(obj, function(item){
			$('#div-vercus-madatory').append(item);
		});
		return false;
	}
	return true;
}
function addVerifyCustomerData(){
	if(!validateVerCustomer()) return;
	var dataString = $("#div-vercus-info *").serialize();
	$('#verify-cutomer-notfound').remove();
	ajaxDisplayElementJson('AddVerifyCustomer',packageOrigRules,dataString);
	ClearVerifyCustomer();
}
function MandatoryVerifyCus(){
	$('#div-vercus-result-madatory').html('');
	$('#div-vercus-madatory').html('');
	if(($('#verdata-1:checked').val() == 'N' || $('#verdata-2:checked').val() == 'N'
			|| $('#verdata-5:checked').val() == 'N') && $('#vercus-final-status').val() == 'P'){
		$('#div-vercus-result-madatory').append('<div>'+MSG_REQUIRE_VERCUS_RESULT+'</div>');
		return false;
	}	
	var dataString = "className=MandatoryVerifyCustomer&packAge=2&returnType=0&vercus-final-status="+$('#vercus-final-status').val();
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
					$('#div-vercus-madatory').append('<div>'+item.value+'</div>');
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
function ClearVerifyCustomer(){
	$('#vercus-personal-type').val('A');
	$('#vercus-address-type').val('');
	$('#vercus-phonestatus').val('');
	$('#vercus-phoneno').val('');
	$('#vercus-remark').val('');
	$('#div-vercus-madatory').html('');
	initVerCustomer();
}
function SaveVerifyCustomerAction(){
	if($('#vercus-final-status').val() != WEIVED){
		if(MandatoryVerifyCus()){
			var dataString = $("#table-vercus-result *").serialize()+'&vercus-final-status='+$('#vercus-final-status').val();
			$AjaxFrontController('SaveVerifyCustomerWebAction','N',null,dataString,DisplayResultVerCustomer);
		}
	}else{
		 CloseVerCustomer();
	}
}
function CloseVerCustomer(){
	$dialog.dialog("close");	
	closeDialog();
}
function DisplayResultVerCustomer(data){
	DisplayVerifyCustomer(data);
	$dialog.dialog("close");	
	closeDialog();
}