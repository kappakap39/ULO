//Function INCREASE_CARD_REQUEST
function BTN_ADD_CARDActionJS(){
	try{
		var validateId = VALIDATE_INCREASE_CARD_REQUEST;
		var $data =  '&CIS_NUMBER=' + $("[name='CIS_NUMBER']").val();
			$data += '&CARD_NO=' + $("[name='CARD_NO']").val();
		validateFormAction(validateId,$data,INCREASE_CARD_REQUESTActionJS);
	}catch(exception){
		errorException(exception);
	}
}
function INCREASE_CARD_REQUESTActionJS(){
	try{
		var className = 'com.eaf.orig.ulo.app.view.util.dih.IncreaseCardRequestInformation';
		var $data =  $("#CARD_REQUEST_INFO_SUBFORM *").serialize();
			$data += '&CIS_NUMBER=' + $("[name='CIS_NUMBER']").val();
		ajax(className,$data, INCREASE_CARD_REQUESTAfterActionJS);
	}catch(exception){
		errorException(exception);
	}
}
function INCREASE_CARD_REQUESTAfterActionJS(data){
	try{
		if(ERROR_CIS == data){
			alertBox('ERROR_CIS_NO_MAT');
			focusElementActionJS('CARD_NO', '');
		}else if(ERROR_MAXIMUM_CARD == data){
			alertBox('ERROR_MAXIMUM_CARD_BORROWER');
			focusElementActionJS('CARD_NO', '');
		}if(ERROR_SUP_CARD_REQUEST == data){
			alertBox('ERROR_SUP_CARD_REQUEST_INCREASE');
			focusElementActionJS('CARD_NO', '');
		}else{
			var subformIdCARD_REQUEST =$("#CARD_REQUEST_INFO_SUBFORM [name='subformId']").val();
			var subformIdPERSONAL =$("#PERSONAL_INFO_SUBFORM [name='subformId']").val();
			var subformIdCONSENT =$("#NCB_INFO_SUBFORM [name='subformId']").val();
			displayJSON(data);
			var className = 'com.eaf.orig.ulo.app.view.util.ajax.SetPropertyCardRequestSection';
			var $data = "subformId="+subformIdCARD_REQUEST;
				$data += "&subformId="+subformIdPERSONAL;
				$data += "&subformId="+subformIdCONSENT;
				$data += '&'+$('#SECTION_'+subformIdCARD_REQUEST+' *').serialize();
				$data += '&'+$('#SECTION_'+subformIdPERSONAL+' *').serialize();
				$data += '&'+$('#SECTION_'+subformIdCONSENT+' *').serialize();
			ajax(className,$data,refreshMasterForm);
			//FUT Defect : 802 #Fix Save Properties in one call ajax
//			refreshSubForm(subformIdCARD_REQUEST,'Y');
//			if(manualRefreshSubForm(subformIdCARD_REQUEST,'Y')){
//				setPropertiesSubform(subformIdPERSONAL,refreshMasterForm);
//			}
//			refreshSubForm(subformIdPERSONAL,'Y');
			refreshFormHeader();
		}
	}catch(exception){
		errorException(exception);
	}
}
function DELETE_INCREASE_CARDActionJS(applicationRecordId){
	try{
		var subformId =$("#CARD_REQUEST_INFO_SUBFORM [name='subformId']").val();
		var $data = '&APP_RECORD_ID='+applicationRecordId;
		deleteRow('com.eaf.orig.ulo.app.view.form.subform.product.manual.DeleteCardRequestInfo',
				$data,INCREASE_CARD_REQUESTAfterActionJS,subformId,'Y');	
	}catch(exception){
		errorException(exception);
	}
}
function BTN_EXECUTEActionJS(){
	try{
		var formId = $("#FormHandlerManager [name='formId']").val();
		var action = BUTTON_ACTION_EXECUTE;
		var handleForm = 'Y';
		var validateForm = 'Y';
		processApplicationFormAction(formId,action,handleForm,validateForm,DECISION_IA_EXECUTEAfterProcessActionJS);
	}catch(exception){
		errorException(exception);
	}
}
function DECISION_IA_EXECUTEAfterProcessActionJS(){
	try{
		var functionId = DECISION_IMPLEMENT_ACTION_IA;
		var formId = $("#FormHandlerManager [name='formId']").val();
		applicationActionService(functionId, DECISION_IA_EXECUTEAfterActionJS,formId,BUTTON_ACTION_EXECUTE);
	}catch(exception){
		errorException(exception);
	}
}
function DECISION_IA_EXECUTEAfterActionJS(data){
	try{
		var decisionApplication = $.parseJSON(data);
		var subformId =$("#DOCUMENT_CHECK_LIST [name='subformId']").val();
		var personalSubformId =$("#PERSONAL_INFO_SUBFORM [name='subformId']").val();
		refreshSubForm(subformId);
		refreshSubForm(personalSubformId);
		refreshFormHeader();
	}catch(exception){
		errorException(exception);
	}
}
function DECISION_IAAfterBusinessExceptionDecisionActionJS(data,formId,action){
	if(BUTTON_ACTION_EXECUTE == action){
		var subformId =$("#DOCUMENT_CHECK_LIST [name='subformId']").val();
		refreshSubForm(subformId);
		refreshFormHeader();
	}
}