function VERIFY_HR_FORMAfterSaveActionJS(){
	try{
		var functionId =DECISION_IMPLEMENT_ACTION_VER_HR;
		var formId = $("#FormHandlerManager [name='formId']").val();
		applicationActionService(functionId, VERIFY_HR_DECISIONAfterActionJS,formId);
	}catch(exception){
		errorException(exception);
	}
}
function VERIFY_HR_DECISIONAfterActionJS(data){
	try{
		var handleForm = 'N';
		var decisionApplication = $.parseJSON(data);
		if(decisionApplication != undefined){
			var resultCode = decisionApplication.decision;
			if(resultCode == FINAL_APP_DECISION_REJECT){
				alertBox(MSG_FICO_REJECT,VERIFY_HR_DECISIONSubmitActionJS);
			}
			else{
				backLastTabAction(data,handleForm);
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function VERIFY_HR_DECISIONSubmitActionJS(){
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



function ADD_OTHERINCOME_BTNActionJS(){
	try{
		var subformId="IDENTIFY_QUESTION_SUBFORM";
		createRow('com.eaf.orig.ulo.app.view.form.subform.verifyhr.manual.AddIncomeCategory',""
				,refreshSubForm,subformId,'Y');
	}catch(exception){
		errorException(exception);
	}
}
function DELETE_PRODUCT_BTNActionJS(seq){
	try{
	    var $data = '&SEQ='+seq;
	    var subformId="IDENTIFY_QUESTION_SUBFORM";
	    deleteRow('com.eaf.orig.ulo.app.view.form.subform.verifyhr.manual.DelIncomeCategory'
	    		,$data,refreshSubForm,subformId,'Y');
	}catch(exception){
		errorException(exception);
	}
}
function validateMonth(obj){
	try{
		if(obj.value>11){
			alertBox(ERROR_INVALID_TOT_WORK_MONTH);
			obj.value='';
		}
	}catch(exception){
		errorException(exception);
	}
}



	

