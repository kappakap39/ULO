function SAVE_RE_KEY_BTNActionJS(){
	try{
		var formId = $("#PopupFormHandlerManager [name='formId']").val();
		closePopupDialog(formId,POPUP_ACTION_SAVE);	
	}catch(exception){
		errorException(exception);
	}
}
function CLOSE_RE_KEY_BTNActionJS(){
	try{
		var formId = $("#PopupFormHandlerManager [name='formId']").val();
		closePopupDialog(formId,POPUP_ACTION_CLOSE);
	}catch(exception){
		errorException(exception);
	}
}