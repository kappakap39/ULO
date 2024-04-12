function SAVE_POPUP_BTNActionJS(){
	try{
		var CALL_FICO_FLAG = $("[name='CALL_FICO_FLAG']").val();
		var formId = getPopupFormId();
		if(CALL_FICO_FLAG == FLAG_YES){
			applicationActionService(DECISION_IMPLEMENT_ACTION_CA,SaveApplicationSummaryForm,formId);
		}else{
			SaveApplicationSummaryForm();
		}
	}catch(exception){
		errorException(exception);
	}
}
function SaveApplicationSummaryForm(){
	try{
		$('[name="CLOSE_POPUP_BTN"]').click(); 
		var formId = $("#FormHandlerManager [name='formId']").val();
		var action = BUTTON_ACTION_SUBMIT;
		var handleForm = 'N';
		var validateForm = 'N';
		
		saveApplicationFormAction(formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}