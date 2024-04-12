function VERIFY_INCOME_FORMAfterSaveActionJS(data){
	try{
		var response = $.parseJSON(data);
		var ficoStatus = response.ficoStatus;
		var resultCode = response.decisionResult;
		var resultDesc = response.decisionResultDesc;

		if(resultCode == SUCCESS_CODE || resultCode == NO_ACTION){
			if(resultDesc == WARNING_DIFF_DV){
				confirmIncomeBox(CONFIRM_COMPARE_INCOME, CONFIRM_COMPARE_INCOMEAfterConfirmActionJS,ficoStatus);
			}else{
				AfterConfirmSaveAppActionJS(ficoStatus);
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function AfterConfirmSaveAppActionJS(ficoStatus){
	try{
		var handleForm = 'N';
		if(FINAL_APP_DECISION_REJECT == ficoStatus) {
			var formId = $("#FormHandlerManager [name='formId']").val();
			var action = BUTTON_ACTION_SUBMIT;
			var validateForm = 'N';
			saveApplicationFormAction(formId,action,handleForm,validateForm);
		} else {
			var $data = '';
			backLastTabAction($data,handleForm);
		}
		refreshFormHeader();
	}catch(exception){
		errorException(exception);
	}
}