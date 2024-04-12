function VERIFY_PRVLG_PROJECT_CODE_FORMAfterSaveActionJS(){
	try{
		var functionId = DECISION_IMPLEMENT_ACTION_PRIVILEGE;
		var formId = $("#FormHandlerManager [name='formId']").val();
		applicationActionService(functionId, VERIFY_PRIVILEGE_DECISIONAfterActionJS,formId);
	}catch(exception){
		errorException(exception);
	}
}

function VERIFY_PRIVILEGE_DECISIONAfterActionJS(data){
try{
	var handleForm = 'N';
	var decisionApplication = $.parseJSON(data);
	if(decisionApplication != undefined){
		var resultCode = decisionApplication.decision;
//		if(resultCode == FINAL_APP_DECISION_REJECT){
//			alertBox(MSG_FICO_REJECT,VERIFY_HR_DECISIONSubmitActionJS);
//		}
//		else{
			backLastTabAction(data,handleForm);
//		}
	}
}catch(exception){
	errorException(exception);
}
}

function CLOSE_TAB_PRVG_PROJECT_CODE_BTNActionJS(){
//	try {
//		if(SmartDataEntry) {
//			if(SmartDataEntry.isShow) {
//				SmartDataEntry.show();
//			}
//		}
//	} catch(e) {}
	try{
		var $data = '';
		var handleForm = 'N';
		backLastTabAction($data,handleForm);
	}catch(exception){
		errorException(exception);
	}
}
function SAVE_TAB_PRVG_PROJECT_CODE_BTNActionJS(){
	try{
//		var RCC_PROJECT_CODE =  $("input[name='RCC_PROJECT_CODE']:checked").val();
//		if(RCC_PROJECT_CODE == undefined ||  ""==RCC_PROJECT_CODE || null==RCC_PROJECT_CODE){
//			confirmBox(CONFIRM_MESSAGE_PRIVILEGE,AfterConfirmSAVE_TAB_PRVG_PROJECT_CODE_BTNActionJS);
//		}else{
//			SAVE_TAB_PROJECT_CODE_ACTIONJS("Y");
//		}
//		var formId="VERIFY_PRVLG_PROJECT_CODE_FORM";	
//		processApplicationFormAction(formId,"","Y","Y","");
		var $data = '';
		var handleForm = 'Y';
		var validateForm = 'Y';
		saveTabForm($data,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}

function AfterConfirmSAVE_TAB_PRVG_PROJECT_CODE_BTNActionJS(flag){
	try{
		if('Y'==flag){
			SAVE_TAB_PROJECT_CODE_ACTIONJS("N");
		}
	}catch(exception){
		errorException(exception);
	}
}

function SAVE_TAB_PROJECT_CODE_ACTIONJS(validateForm){
	try{
		var $data = '';
		var handleForm = 'Y';
		saveTabForm($data,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}