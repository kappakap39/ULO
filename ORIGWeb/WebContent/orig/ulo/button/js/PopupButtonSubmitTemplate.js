
function CLOSE_POPUP_BTNActionJS(){
	try{
		var formName = getPopupFormName();
		var formId = getPopupFormId();
		closePopupDialog(formId,POPUP_ACTION_CLOSE);
		
	}catch(exception){
		errorException(exception);
	}	
}
function SAVE_POPUP_BTNActionJS(){
	try{
		var $data = '';
		var formName = getPopupFormName();
		var formId = getPopupFormId();
//		if(formId == 'REKEY_SENSITIVE_FORM'){
//			Pace.unblockFlag = false;
//		}
		savePopupDialog(formId,$data);
	}catch(exception){
		errorException(exception);
	}
}