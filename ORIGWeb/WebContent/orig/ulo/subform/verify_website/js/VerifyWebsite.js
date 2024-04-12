function VERIFY_WEBSITE_FORMAfterSaveActionJS(){
	try{
		var functionId = DECISION_IMPLEMENT_ACTION_VWEBSITE;
		var formId = $("#FormHandlerManager [name='formId']").val();
		applicationActionService(functionId, VERIFY_WEBSITE_DECISIONAfterActionJS,formId);
	}catch(exception){
		errorException(exception);
	}
}
function VERIFY_WEBSITE_DECISIONAfterActionJS(data){
	try{
		var handleForm = 'N';
		var decisionApplication = $.parseJSON(data);
		if(decisionApplication != undefined){
			var resultCode = decisionApplication.decision;
			if(resultCode == FINAL_APP_DECISION_REJECT){
				alertBox(MSG_FICO_REJECT,VERIFY_WEBSITE_DECISIONSubmitActionJS);
			}
			else{
				backLastTabAction(data,handleForm);
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function VERIFY_WEBSITE_DECISIONSubmitActionJS(){
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

function VERIFICATION_RESULTInitJS(elementId, mode, name){
	try{
		if(MODE_EDIT==mode){
			var webCode = elementId.split("_");		
			
			if(RD_WEBSITE_CODE == webCode[2]) {
				var verResult = $("[name='"+name+"_"+NHSO_WEBSITE_CODE+"']").val();
				if(verResult == null || verResult == undefined) {
					targetDisplayHtml(elementId,MODE_VIEW,name);
					displayHtmlElement(elementId,'');
				} else if(verResult !== WEBSITE_SSO){
					targetDisplayHtml(elementId,MODE_EDIT,name);
				} else {
					targetDisplayHtml(elementId,MODE_VIEW,name);
					displayHtmlElement(elementId,'');
				}
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function VERIFICATION_RESULTActionJS(elementId, mode, name){
	try{
		var formId= $('#FormHandlerManager [name="formId"]').val(); 
		if("VERIFICATION_RESULT_SSO"==elementId || "VERIFICATION_RESULT_NHSO"==elementId){
			formHandleAction(formId,'',refreshMasterForm);
		}
	/*	var webCode = elementId.split("_");
		if(SSO_WEBSITE_CODE == webCode[2]) {
			var verResult = $("[name='"+elementId+"']").val();
			if(WEBSITE_UNAVAILABLE == verResult) {
				targetDisplayHtml(name+"_"+RD_WEBSITE_CODE,MODE_EDIT,name);
				targetDisplayHtml("REMARK_"+RD_WEBSITE_CODE,MODE_EDIT,"REMARK");
			} else {
				targetDisplayHtml(name+"_"+RD_WEBSITE_CODE,MODE_VIEW,name);
				displayHtmlElement(name+"_"+RD_WEBSITE_CODE,'');
				
				targetDisplayHtml("REMARK_"+RD_WEBSITE_CODE,MODE_VIEW,"REMARK");
				displayHtmlElement("REMARK_"+RD_WEBSITE_CODE,'');
			}
		}*/
	}catch(exception){
		errorException(exception);
	}
}
function REMARKInitJS(elementId, mode, name){
	try{
		if(MODE_EDIT==mode){
			var webCode = elementId.split("_");
			if(RD_WEBSITE_CODE == webCode[1]) {
				var verResult = $("[name='"+name+"_"+NHSO_WEBSITE_CODE+"']").val();
				if(verResult == null || verResult == undefined) {
					targetDisplayHtml(elementId,MODE_VIEW,name);
					displayHtmlElement(elementId,'');
				} else if(verResult !== WEBSITE_SSO){
					targetDisplayHtml(elementId,MODE_EDIT,name);
				} else {
					targetDisplayHtml(elementId,MODE_VIEW,name);
					displayHtmlElement(elementId,'');
				}
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function REMARKActionJS(elementId, mode, name){
}