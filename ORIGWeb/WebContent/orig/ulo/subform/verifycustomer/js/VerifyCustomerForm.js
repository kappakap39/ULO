function VERIFY_CUSTOMER_FORMAfterSaveActionJS(){
	try{
		
		//calculate button
		var calcu = $("[name='calcu']").val();
		if(calcu == 'Y')
		{
			applicationActionService("DECISION_DV2",DIFF_REQ_CALCU_AfterActionJS,formId);
		}
		else
		{
			var formId = $("#FormHandlerManager [name='formId']").val();
			var functionId =DECISION_IMPLEMENT_ACTION_VER_CUST;
			applicationActionService(functionId, VERIFY_CUSTOMER_DECISIONAfterActionJS,formId);
		}
		
	}catch(exception){
		errorException(exception);
	}
}

function DIFF_REQ_CALCU_AfterActionJS(data)
{
	var decisionApplication = $.parseJSON(data);
	var diffRequestFlag = decisionApplication.diffRequestFlag;
	//PROD Incident 1239600
	//if(diffRequestFlag == FLAG_YES)
	//{
		alertBox('Calculate Completed',LoadRefreshFormAction,"",DEFAULT_ALERT_BOX_WIDTH);
	//}
	
}

function VERIFY_CUSTOMER_DECISIONAfterActionJS(data){
	try{
		var decisionApplication = $.parseJSON(data);
		var handleForm = 'N';
		var validateForm = 'Y';
		if(decisionApplication != undefined){
			var resultCode = decisionApplication.decision;
		//	var resultCode = FINAL_APP_DECISION_REJECTED;// test
			if(resultCode == FINAL_APP_DECISION_REJECT){
				alertBox(MSG_FICO_REJECT,VERIFY_CUSTOMER_DECISIONSubmitActionJS);
			}
			else{
				validateTabForm(data,handleForm,validateForm,backLastTabAction);
//				backLastTabAction(data,handleForm);
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function VERIFY_CUSTOMER_DECISIONSubmitActionJS(){
	try{
		var formId = $("#FormHandlerManager [name='formId']").val();
		var action = BUTTON_ACTION_SUBMIT;
		var handleForm = 'N';
		var validateForm = 'N';
		saveApplicationFormAction(formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}
function ACCOUNT_NOActionJS(element, mode, name) {
	try{
		accountValidation(element, mode, name);
	}catch(exception){
		errorException(exception);
	}
}

function rewriteForSavingCurrent(element, name, data, error) {
	try{
		 if("ACCOUNT_NO_KPL"==element && error != null &&  error != "" && error != undefined){
				displayHtmlElement(element, "");
			 }
	}catch(exception){
		errorException(exception);
	}
}

function rewriteForCashTransfer(element, dataVal){}