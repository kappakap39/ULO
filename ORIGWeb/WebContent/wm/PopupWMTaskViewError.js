function CLOSE_POPUP_BTNActionJS() {
	try {
		var formName = getPopupFormName();
		var formId = getPopupFormId();
		closePopupDialog(formId,POPUP_ACTION_CLOSE);
	} catch(exception) {
		errorException(exception);
	}	
}