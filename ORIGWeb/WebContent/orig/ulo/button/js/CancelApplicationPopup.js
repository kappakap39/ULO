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
		var formId = "POPUP_CANCEL_REASON_FORM";
		processApplicationFormAction(formId,"","Y","Y",SAVE_POPUP_BTNActionAfterManadatoryFormActionJS);
	}catch(exception){
		errorException(exception);
	}
}

function SAVE_POPUP_BTNActionAfterManadatoryFormActionJS(data,status,xhr){
	try{
		confirmBox(MSG_CANCEL_APPLICATION,cancelApplicationFormActionJS);
	}catch(exception){
		errorException(exception);
	}
}
 
function cancelApplicationFormActionJS(confirmFlag){
	try{
		 if(confirmFlag==FLAG_YES){
			var $data = '';
			var handleForm = 'N';
			var validateForm = 'N';
			var formId = getPopupFormId();
			savePopupFormAction(formId,$data,handleForm,validateForm);
		 }
	}catch(exception){
		errorException(exception);
	}
}

function POPUP_CANCEL_REASON_FORMAfterSaveActionJS(data,formId){
	try{
		closePopupDialog(formId,POPUP_ACTION_SAVE);
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_CANCEL_REASON_FORMAfterCloseActionJS(popupAction){
	try{
		if(POPUP_ACTION_SAVE==popupAction){
			var formId = $("#FormHandlerManager [name='formId']").val();
			var action = BUTTON_ACTION_CANCEL;
			var handleForm = 'N';
			var validateForm = 'N';
			saveApplicationFormAction(formId,action,handleForm,validateForm);
		}
	}catch(exception){
		errorException(exception);
	}
}