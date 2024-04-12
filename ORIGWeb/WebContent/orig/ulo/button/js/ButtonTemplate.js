function SEND_BACK_APPLICATION_BTNActionJS(){
	try{
		confirmBox(MSG_CANCEL_SEND_BACK_APPLICATION,function(confirmFlag){
			if(FLAG_YES==confirmFlag){
				var formId = $("#FormHandlerManager [name='formId']").val();
				var action = BUTTON_ACTION_SEND_BACK;
				var handleForm = 'Y';
				var validateForm = 'N';
				saveApplicationForm(formId,action,handleForm,validateForm);
			}		
		});	
	}catch(exception){
		errorException(exception);
	}
}
function SAVE_APPLICATION_BTNActionJS(){
	try{
		var formId = $("#FormHandlerManager [name='formId']").val();
		var action = BUTTON_ACTION_SAVE;
		var handleForm = 'Y';
		var validateForm = 'Y';
		saveApplicationForm(formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}

function SUBMIT_APPLICATION_BTNActionJS(){
	try{
		var formId = $("#FormHandlerManager [name='formId']").val();
		var action = BUTTON_ACTION_SUBMIT;
		var handleForm = 'Y';
		var validateForm = 'Y';
		saveApplicationForm(formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}

function SUBMIT_DECISION_APPLICATION_BTNActionJS(){
	try{
		var formId = $("#FormHandlerManager [name='formId']").val();
		var action = BUTTON_ACTION_DECISION;
		var handleForm = 'Y';
		var validateForm = 'Y';
		saveApplicationForm(formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}

function CLOSE_APPLICATION_BTNActionJS(){
//	if(!authorizedApplicationAction()&&!lookup(ACTION_TYPE_ENQUIRY,FLOW_ACTION_TYPE)){
//		$('#action').val('CloseApplication');
//		$('#handleForm').val('N');										 			
//		$('#appFormName').submit();
//	}
	try{
		Pace.block();
		var formId = $("#FormHandlerManager [name='formId']").val();
		var handleForm = 'N';
		var validateForm = 'N';
		if(!authorizedApplicationAction(formId,BUTTON_ACTION_CLOSE,handleForm,validateForm)){		
			$('#action').val('CloseApplication');
			$('#handleForm').val('N');										 			
			$('#appFormName').submit();
		}
	}catch(exception){
		errorException(exception);
	}
}
function CANCEL_APPLICATION_BTNActionJS(){
	try{
//		var action = BUTTON_ACTION_CANCEL_APPLICATION;
		loadPopupDialog(POPUP_CANCEL_REASON_FORM,"INSERT","0","","","650","350");
	}catch(exception){
		errorException(exception);
	}
}

function SEND_TO_FOLLOW_UP_BTNActionJS(){ 
	
}

function SEND_TO_FU_BTNActionJS(action){
	try{
		console.log('SEND_TO_FU_BTNActionJS >>> constant ROLE_IA : '+ROLE_IA);
		console.log('SEND_TO_FU_BTNActionJS >>> ROLE_ID : '+ROLE_ID);
		if(ROLE_IA == ROLE_ID){
			var formId = $("#FormHandlerManager [name='formId']").val();
			var action = BUTTON_ACTION_SEND_TO_FU;
			var handleForm = 'Y';
			var validateForm = 'Y';
			processApplicationFormAction(formId, action, handleForm, validateForm, IA_SEND_TO_FU_BTNAfterAjaxActionJS);
		}else if(ROLE_DE1_1 == ROLE_ID){
			var formId = $("#FormHandlerManager [name='formId']").val();
			var subformId = $('#DOCUMENT_CHECK_LIST input[name="subformId"]').val();
			var action = BUTTON_ACTION_SEND_TO_FU;
			var handleForm = 'Y';
			var validateForm = 'Y';
			var externalData = $('#SECTION_'+subformId+' *').serialize();
			processHeaderSectionAction(formId,action,handleForm,validateForm,externalData,DE1_1_SEND_TO_FU_BTNAfterAjaxActionJS);
		}else{
			var subformId = $('#DOCUMENT_CHECK_LIST input[name="subformId"]').val();
			var $data  = '&subformId='+subformId;
				$data += '&'+$('#SECTION_'+subformId+' *').serialize();
				$data += '&'+$('#PERSONAL_INFO_SUBFORM section *').serialize();
			var className = 'com.eaf.orig.ulo.app.view.util.ajax.ValidateSendToFu';
			ajax(className,$data,SEND_TO_FU_BTNAfterAjaxActionJS,action);
		}
	}catch(exception){
		errorException(exception);
	}
}
function SEND_TO_FU_BTNAfterAjaxActionJS(data,status,xhr,action){
	console.log('data : '+data);
//	if(responesData.ERROR != undefined){
//		alertBox('ERROR_PLEASE_SELECT_REASON_CHECK_LIST');
//	}else{
//		var formId = $("#FormHandlerManager [name='formId']").val();
//		var handleForm = 'Y';
//		var validateForm = 'N';
//		saveApplicationForm(formId,action,handleForm,validateForm);
//	}
	if(data == '' || data == undefined){
		var formId = $("#FormHandlerManager [name='formId']").val();
		var handleForm = 'Y';
		var validateForm = 'N';
		saveApplicationForm(formId,action,handleForm,validateForm);
	}else{
		try{
			var responseData = $.parseJSON(data);
			console.log('responseData : '+responseData);
			if(validateFormDataObject(responseData,'ERROR')){
				displayValidateSendToFU(data);
			}
		}catch(exception){
			errorException(exception);
		}
	}
}
function IA_SEND_TO_FU_BTNAfterAjaxActionJS(data,status,xhr,action){
	try{
		var formId = $("#FormHandlerManager [name='formId']").val();
		var handleForm = 'Y';
		var validateForm = 'N';
		saveApplicationForm(formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}
function DE1_1_SEND_TO_FU_BTNAfterAjaxActionJS(data,status,xhr,action){
	try{
		var formId = $("#FormHandlerManager [name='formId']").val();
		var handleForm = 'Y';
		var validateForm = 'N';
		saveApplicationForm(formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}
function displayValidateSendToFU(data){
	try{
		var responseData = $.parseJSON(data);
		var formId = $("#FormHandlerManager [name='formId']").val();
		var headerFormTemplate = $('#DOCUMENT_CHECK_LIST input[name=headerFormTemplate]').val();
		$('#'+formId+'ErrorFormHandlerManager').html('');
		$('#'+formId+' .form-group').removeClass('has-error');
		$('#'+formId+' .InputField').removeClass('has-error');
		$('#DOCUMENT_CHECK_LISTErrorFormHandlerManager').html('');
		$.map(responseData, function(item){
			var type = item.id;
			if(type == 'ERROR'){
				var errorElement = "<div class='alert alert-danger alert-dismissible fade in' role='alert'>";
					errorElement += "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>x</span></button>";
					errorElement += "<div>"+item.value+"</div>";
					errorElement += "</div>";
				var errorDiv = "<div id='DOCUMENT_CHECK_LISTErrorFormHandlerManager' class='ErrorFormHandlerManager' >";
					errorDiv += errorElement;
					errorDiv += "</div>";
				if(headerFormTemplate == undefined || headerFormTemplate == ''){
					$('#'+formId+'ErrorFormHandlerManager').append(errorElement);
					$('#'+formId+'ErrorFormHandlerManager')[0].scrollIntoView(true);
				}else if(headerFormTemplate == 'Y'){
					if($('#DOCUMENT_CHECK_LISTErrorFormHandlerManager')[0]){
						$('#DOCUMENT_CHECK_LISTErrorFormHandlerManager').append(errorElement);
					}else{
						var html = $('#DOCUMENT_CHECK_LIST').html();
						$('#DOCUMENT_CHECK_LIST').html(errorDiv+html);
					}
					$('#DOCUMENT_CHECK_LISTErrorFormHandlerManager')[0].scrollIntoView(true);
				}
			}
		});
	}catch(exception){
		errorException(exception);
	}
}

function CALL_DECISION_SERVICE_BTNActionJS(){
	try{
		var formId = $("#FormHandlerManager [name='formId']").val();
		var action = "DECISION_SERVICE_BTN";
		var functionId=DECISION_SERVICE_ACTION;
		var handleForm = 'Y';
		var validateForm = 'Y';
		Pace.block();
		var $data = 'className=com.eaf.orig.ulo.app.view.util.decisionservice.DecisionApplicationAction';
			if(formId != undefined && formId != ''){
				$data += '&' + $('#'+formId+' *').serialize();
				$data += '&formId='+formId;
			}
			$data += '&validateForm='+validateForm;
			$data += '&buttonAction='+action;
			$data += '&functionId='+functionId;
		var url = CONTEXT_PATH+'/Ajax';
		$.post(url,$data,function(data,status,xhr){
			try{
				if (responseSuccess(data)) {
					var responseData = getResponseData(data);
					var decisionApplication = $.parseJSON(responseData); 
					if(decisionApplication != undefined){
						var resultCode = decisionApplication.resultCode;
						if(resultCode == SUCCESS_CODE || resultCode == NO_ACTION){
							 LoadRefreshFormAction('');
						}else{
							var errorMsg = decisionApplication.errorMsg;
							displayErrorMsg(formId,errorMsg);
						}
					}
				}
			}catch(e){Pace.unblockFlag = true;Pace.unblock();}
		});
		
		
	}catch(exception){
		errorException(exception);
	}
}

//For CR0216
function CALL_RECALCULATE_DEBT_BURDEN_BTNActionJS(){
	try{
		var formId = $("#FormHandlerManager [name='formId']").val();
		var action = "RECALCULATE_DEBT_BURDEN_BTN";
		var functionId=DECISION_SERVICE_ACTION;
		var handleForm = 'Y';
		var validateForm = 'Y';
		decisionApplicationAction(functionId,null,formId,action,handleForm,validateForm);
		
	}catch(exception){
		errorException(exception);
	}
}