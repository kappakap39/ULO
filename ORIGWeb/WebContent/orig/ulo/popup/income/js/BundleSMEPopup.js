function BORROWING_TYPEActionJS(element,mode,name){
	try{
		var subformId = $('#BUNDLING_SME_POPUP input[name="subformId"]').val();
		refreshSubForm(subformId,'Y');
	}catch(exception){
		errorException(exception);
	}
}


function BUNDLE_ACCOUNT_NOActionJS(element,mode,name){
	try{
		if(mode == MODE_EDIT){
			var subformId = $("#BUNDLING_SME_POPUP [name='subformId']").val();
			var className = 'com.eaf.orig.ulo.app.view.util.ajax.ValidateBundleAccountNo';
			var $data  = $('#SECTION_'+subformId+' *').serialize();
				$data += '&elementFieldId='+element;
				$data += '&'+element+'='+$("[name='"+element+"']").val();
			ajax(className,$data,BUNDLE_ACCOUNT_NOAfterAjaxActionJS);
		}
	}catch(exception){
		errorException(exception);
	}
	
}

function TYPE_LIMITActionJS(element,mode,name){
	try{
		var subformId = $("#BUNDLING_SME_POPUP [name='subformId']").val();
		displayHtmlElement('BUNDLE_ACCOUNT_NO','');
		displayHtmlElement('ACCOUNT_NAME','');
		displayHtmlElement('ACCOUNT_DATE','');
		refreshSubForm(subformId,'Y');	
	}catch(exception){
		errorException(exception);
	}
}

function BUNDLE_ACCOUNT_NOAfterAjaxActionJS(data){
	try{
		var $json = $.parseJSON(data);
		if(!isEmpty($json.errorMsg)){
			alertBox($json.errorMsg,focusSelectElementActionJS,'BUNDLE_ACCOUNT_NO');
		}
		var accountName = ($json.accountName)?$json.accountName:'';
		var accountDate = ($json.accountDate)?$json.accountDate:'';
		displayHtmlElement('ACCOUNT_NAME',accountName);
		displayHtmlElement('ACCOUNT_DATE',accountDate);
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_BUNDLE_SME_FORM_CCAfterSaveActionJS() {
	try{
		BUNDLE_SMEDecisionServiceAction();
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_BUNDLE_SME_FORM_KECAfterSaveActionJS() {
	try{
		BUNDLE_SMEDecisionServiceAction();
	}catch(exception){
		errorException(exception);
	}
}
function BUNDLE_SMEDecisionServiceAction() {
	try{
		var functionId = DECISION_IMPLEMENT_ACTION_BSME;
		var formId = getPopupFormId();
		applicationActionService(functionId, POPUP_BUNDLE_SME_DECISIONAfterActionJS,formId);
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_BUNDLE_SME_DECISIONAfterActionJS(data){
	try{
		var decisionApplication = $.parseJSON(data);
		if(decisionApplication != undefined){
			var resultCode = decisionApplication.decision;
			if(resultCode == FINAL_APP_DECISION_REJECT){
				alertBox(MSG_FICO_REJECT,POPUP_BUNDLE_SME_DECISIONSubmitActionJS);
			}else{
				closeIncomeCategoryPopup();
			}
		}else{
			closeIncomeCategoryPopup();
		}
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_BUNDLE_SME_DECISIONSubmitActionJS(){
	try{
		var $data = '&ficoStatus='+FINAL_APP_DECISION_REJECT;
		var handleForm = 'N';
		var validateForm = 'N';
		saveTabFormAction($data, handleForm, validateForm);
	}catch(exception){
		errorException(exception);
	}
}
function APPLICANT_QUALITYInitJS(element,mode,name){
	try{
		var LOAN_TYPE = $("[name='BORROWING_TYPE']").val();
		if(LOAN_TYPE == null || LOAN_TYPE == "" || LOAN_TYPE == undefined) {
//			displayHtmlElement('APPLICANT_QUALITY','');
		} else if(LOAN_TYPE == LOAN_TYPE_SINGLE){
//			displayHtmlElement('APPLICANT_QUALITY','');
		}
	}catch(exception){
		errorException(exception);
	}
}
function APPLICANT_QUALITYActionJS(element,mode,name){
}
function APPROVAL_LIMITInitJS(element,mode,name){
	try{
		var LOAN_TYPE = $("[name='BORROWING_TYPE']").val();
		if(LOAN_TYPE == null || LOAN_TYPE == "" || LOAN_TYPE == undefined) {
			displayHtmlElement('APPROVAL_LIMIT','');
		} else if(LOAN_TYPE != LOAN_TYPE_SINGLE){
			displayHtmlElement('APPROVAL_LIMIT','');
		}
	}catch(exception){
		errorException(exception);
	}
}
function APPROVAL_LIMITActionJS(element,mode,name){
}
function INDIVIDUAL_RATIOInitJS(element,mode,name){
	try{
		var LOAN_TYPE = $("[name='BORROWING_TYPE']").val();
		if(LOAN_TYPE == null || LOAN_TYPE == "" || LOAN_TYPE == undefined) {
			displayHtmlElement('INDIVIDUAL_RATIO','');
		} else if(LOAN_TYPE == LOAN_TYPE_SINGLE){
			displayHtmlElement('INDIVIDUAL_RATIO',DEFALUT_INDIVIDUAL_RATIO);
		} else if(LOAN_TYPE == LOAN_TYPE_COBORROWER){
			displayHtmlElement('INDIVIDUAL_RATIO','');
		}
	}catch(exception){
		errorException(exception);
	}
}
function INDIVIDUAL_RATIOActionJS(element,mode,name){
	try{
		var subformId = $('#BUNDLING_SME_POPUP input[name="subformId"]').val();
		refreshSubForm(subformId,'Y');
	}catch(exception){
		errorException(exception);
	}
}
function CORPORATE_RATIOInitJS(element,mode,name){
	try{
		var LOAN_TYPE = $("[name='BORROWING_TYPE']").val();
		if(LOAN_TYPE == null || LOAN_TYPE == "" || LOAN_TYPE == undefined) {
			displayHtmlElement('CORPORATE_RATIO','');
		} else if(LOAN_TYPE != LOAN_TYPE_COBORROWER_JOINT){
			displayHtmlElement('CORPORATE_RATIO','');
		}
	}catch(exception){
		errorException(exception);
	}
}
function CORPORATE_RATIOActionJS(element,mode,name){
	try{
		var subformId = $('#BUNDLING_SME_POPUP input[name="subformId"]').val();
		refreshSubForm(subformId,'Y');
	}catch(exception){
		errorException(exception);
	}
}
function G_TOTAL_EXIST_PAYMENTInitJS(element,mode,name){
	try{
		var LOAN_TYPE = $("[name='BORROWING_TYPE']").val();
		if(LOAN_TYPE == null || LOAN_TYPE == "" || LOAN_TYPE == undefined) {
			displayHtmlElement('G_TOTAL_EXIST_PAYMENT','');
		} else if(LOAN_TYPE == LOAN_TYPE_COBORROWER){
			displayHtmlElement('G_TOTAL_EXIST_PAYMENT','');
		} 
	}catch(exception){
		errorException(exception);
	}
}
function G_TOTAL_EXIST_PAYMENTActionJS(element,mode,name){
}
function G_TOTAL_NEW_PAY_REQInitJS(element,mode,name){
	try{
		var LOAN_TYPE = $("[name='BORROWING_TYPE']").val();
		if(LOAN_TYPE == null || LOAN_TYPE == "" || LOAN_TYPE == undefined) {
			displayHtmlElement('G_TOTAL_NEW_PAY_REQ','');
		} else if(LOAN_TYPE == LOAN_TYPE_COBORROWER){
			displayHtmlElement('G_TOTAL_NEW_PAY_REQ','');
		} 
	}catch(exception){
		errorException(exception);
	}
}
function G_TOTAL_NEW_PAY_REQActionJS(element,mode,name){
}
function G_DSCR_REQInitJS(element,mode,name){
	try{
		var LOAN_TYPE = $("[name='BORROWING_TYPE']").val();
		if(LOAN_TYPE == null || LOAN_TYPE == "" || LOAN_TYPE == undefined) {
			displayHtmlElement('G_DSCR_REQ','');
		} else if(LOAN_TYPE == LOAN_TYPE_COBORROWER){
			displayHtmlElement('G_DSCR_REQ','');
		}
	}catch(exception){
		errorException(exception);
	}
}
function G_DSCR_REQActionJS(element,mode,name){
	
}
