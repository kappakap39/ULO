function SAVE_POPUP_BTNActionJS(){
	try{
		var CALL_FICO_FLAG = $("[name='CALL_FICO_FLAG']").val();
		var formId = getPopupFormId();
		if(CALL_FICO_FLAG == FLAG_YES){
			applicationActionService(DECISION_IMPLEMENT_ACTION_CA,SummaryPageActionAfterCallDecision,formId);
		}else{
			SaveApplicationSummaryForm();
		}
	}catch(exception){
		errorException(exception);
	}
}