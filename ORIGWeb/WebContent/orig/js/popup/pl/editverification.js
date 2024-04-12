/**
 * @SepTemWi
 */
$(document).ready(function (){
	InitEditVerification();
	if($('#display-mode-worstblockcode').val() == DISPLAY_MODE_EDIT){
		$('#edit-cc-blockcode').change(function(e){
			BlockCodeRequire();
		});
	}
	if($('#display-mode-cisno').val() == DISPLAY_MODE_EDIT){
		SensitiveEngine('#edit-cis-no');
	}
	if($('#display-mode-classifylevel').val() == DISPLAY_MODE_EDIT){
		SensitiveEngine('#edit-classify-level');
	}
	if($('#display-mode-worstblockcode').val() == DISPLAY_MODE_EDIT){
		SensitiveEngine('#edit-cc-blockcode');
	}
	if($('#display-mode-npl').val() == DISPLAY_MODE_EDIT){
		SensitiveEngine('#edit-npl');
	}
	if($('#display-mode-amctamc').val() == DISPLAY_MODE_EDIT){
		SensitiveEngine('#edit-amctamc');
	}
	if($('#display-mode-bankruptcy').val() == DISPLAY_MODE_EDIT){
		SensitiveEngine('#edit-bankruptcy');
	}
	if($('#display-mode-cc-blockcode').val() == DISPLAY_MODE_EDIT){
		SensitiveEngine('#edit-cc-current-balance');
		SensitiveEngine('#edit-cc-lastpayment-date');
	}
	if($('#display-mode-kec-blockcode').val() == DISPLAY_MODE_EDIT){
		SensitiveEngine('#edit-kec-current-balance');
		SensitiveEngine('#edit-kec-lastpayment-date');
	}
});

function InitEditVerification(){
	if($('#display-mode-cc-blockcode').val() == DISPLAY_MODE_EDIT){
		TextBoxCurrencyEngine('#edit-cc-current-balance');
		DateEngine('#edit-cc-lastpayment-date');
	}
	if($('#display-mode-kec-blockcode').val() == DISPLAY_MODE_EDIT){
		TextBoxCurrencyEngine('#edit-kec-current-balance');
		DateEngine('#edit-kec-lastpayment-date');
	}
}
function SaveEditVerification(){
	if(MandatoryEditPopup()){
		var dataString = $("#edit-verfication-popup *").serialize();
		$AjaxFrontController('SaveEditPopupWebAction','N',null,dataString,displayEditVerification);	
	}
}
function BlockCodeRequire(){
	if($('#display-mode-worstblockcode').val() == DISPLAY_MODE_EDIT){
		var dataString = "className=EditBlockcodeValidate&packAge=2&returnType=0&"+$("#edit-verfication-popup *").serialize();
		jQuery.ajax( {
			type :"POST",
			url :'AjaxDisplayServlet',
			data :dataString,	
			async :false,	
			dataType: "json",
			success : function(data ,status ,xhr){
				if(data != null && data.length > 0){
					jsonDisplayElementById(data);
					if($('#display-mode-cc-blockcode').val() == DISPLAY_MODE_EDIT){
						TextBoxCurrencyEngine('#edit-cc-current-balance');
						DateEngine('#edit-cc-lastpayment-date');
					}
				}
			},
			error : function(data){			
			}
		});
	}
}
function MandatoryEditPopup(){
	$('#div-editverify-mandatory').html('');
	var dataString = "className=MandatoryEditPopup&packAge=2&returnType=0&"+$("#edit-verfication-popup *").serialize();
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
					$('#div-editverify-mandatory').append('<div>'+item.value+'</div>');
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
function displayEditVerification(data){
	jsonDisplayElementById(data);
	$dialog.dialog("close");	
	closeDialog();
}