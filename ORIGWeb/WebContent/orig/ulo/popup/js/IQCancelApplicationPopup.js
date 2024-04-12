function POPUP_IQ_CANCEL_APP_FORMAfterLoadActionJS() {

}

function POPUP_IQ_CANCEL_APP_FORMAfterSaveActionJS() {
	try{
		var formEl = $("#PopupFormHandlerManager #POPUP_IQ_CANCEL_APP_FORM");
		var formId = formEl.find("[name='formId']").val();
		closePopupDialog(formId,POPUP_ACTION_SAVE);
	}catch(exception){
		errorException(exception);
	}
}

function POPUP_CALLCENTER_CANCEL_FORMAfterCloseActionJS() {
	
}
function POPUP_IQ_CANCEL_APP_FORMAfterCloseActionJS(){
	try{
		refreshSearchControlActionJS();
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_CALLCENTER_CANCEL_FORMAfterAjaxDoneActionJS(action) {
	try{
		switch(action) {
			case "SAVE":
				alertBox(MSG_SAVED);
				break;
		}
	}catch(exception){
		errorException(exception);
	}	
}

function REASON_OTH_DESCInitJS(){
	try{
		targetDisplayHtml('REASON_OTH_DESC', MODE_VIEW,'REASON_OTH_DESC');
	}catch(exception){
		errorException(exception);
	}
}

function REASON_CODEActionJS() {
	try{
		var reasonOtherEl = $('input[name=REASON_OTH_DESC]');
		var reasonCodeValue = $('[name=REASON_CODE]');
		if (reasonCodeValue.val() == REASON_CODE_OTH) {
			targetDisplayHtml('REASON_OTH_DESC', MODE_EDIT, 'REASON_OTH_DESC');
		} else {
			targetDisplayHtml('REASON_OTH_DESC', MODE_VIEW, 'REASON_OTH_DESC');
			reasonOtherEl.val('');
		}
	}catch(exception){
		errorException(exception);
	}
}
