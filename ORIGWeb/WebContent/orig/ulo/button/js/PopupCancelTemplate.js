function CLOSE_POPUP_BTNActionJS(){
	try{
		var formName = getPopupFormName();
		var formId = getPopupFormId();
		closePopupDialog(formId,POPUP_ACTION_CLOSE);
	}catch(exception){
		errorException(exception);
	}
}
function CANCEL_POPUP_BTNActionJS (){
	try{
		var REASON_CODE = $('[name=REASON_CODE]').val();
		var REASON_OTH_DESC = $('[name=REASON_OTH_DESC]').val();
		var $data = 'REASON_CODE='+REASON_CODE+'&REASON_OTH_DESC='+REASON_OTH_DESC; 
		validateFormAction(VALIDATE_CANCEL_APPLICATION, $data, function() {
			confirmBox(CONFIRM_MESSAGE_IQ_CANCEL,AfterConfirmCANCEL_POPUP_BTNActionJS);
		},DISPLAY_ERROR_POPUP);
	}catch(exception){
		errorException(exception);
	}
}
function AfterConfirmCANCEL_POPUP_BTNActionJS(CONFIRM_FLAG){
	try{
		if(FLAG_YES == CONFIRM_FLAG) {
			var $data = '';
			var formId = getPopupFormId();
			savePopupDialog(formId,$data);
		}
	}catch(exception){
		errorException(exception);
	}
}