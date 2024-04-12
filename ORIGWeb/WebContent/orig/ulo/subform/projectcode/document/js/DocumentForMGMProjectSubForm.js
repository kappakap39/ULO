function REFER_CARD_NOActionJS(){
	try{
		var referCardNo =$("[name='REFER_CARD_NO']").val();
		if(referCardNo !=undefined && referCardNo !=''){
			var className = 'com.eaf.orig.ulo.app.view.util.dih.PrivilegeProjectCodeCardHolder';
			var $data = "&MAIN_CARD_NUMBER="+referCardNo;	
				$data+="&VERIFY_PRIVILEGE_DOC_PROJECT_CODE=Y";
			ajax(className,$data,  REFER_CARD_NOAfterActionJS);
		} else {
			ClearDescNoAlert();
		}
	}catch(exception){
		errorException(exception);
	}
}

function REFER_CARD_NOAfterActionJS(data){
	try{
		var DIHResponse = $.parseJSON(data);
		if(undefined !=DIHResponse && DIHResponse !=""){
			var referCardDesc = DIHResponse[0].value;
			$("[name='REFER_CARD_HOLDER_NAME']").val(referCardDesc);
			if(referCardDesc =="" || undefined==referCardDesc || referCardDesc == null){
				alertBox('ERROR_CARD', ClearDescNoAlert,'REFER_CARD_NO');
			}
		}else{
			alertBox('ERROR_CARD_NO_FORMAT',ClearDescNoAlert,'REFER_CARD_NO',DEFAULT_ALERT_BOX_WIDTH);
		}
	}catch(exception){
		errorException(exception);
	}	
}

function ClearDescNoAlert(){
	try{
		$("[name='REFER_CARD_HOLDER_NAME']").val('');
		$("[name='REFER_CARD_NO']").val('');
	}catch(exception){
		errorException(exception);
	}
}
