
function POPUP_CALLCENTER_CANCEL_FORMAfterLoadActionJS() {

}

function POPUP_CALLCENTER_CANCEL_FORMAfterSaveActionJS() {
	try{
		var formEl = $("#PopupFormHandlerManager #POPUP_CALLCENTER_CANCEL_FORM");
		var formId = formEl.find("[name='formId']").val();
		closePopupDialog(formId,POPUP_ACTION_SAVE);
	}catch(exception){
		errorException(exception);
	}
}

function POPUP_CALLCENTER_CANCEL_FORMAfterCloseActionJS(ACTION) {
	try{
		if(BUTTON_ACTION_SAVE==ACTION){
			$('[name="EQ_SEARCH_BTN"]').click();
		}
	}catch(exception){
		errorException(exception);
	}
}

function POPUP_CALLCENTER_CANCEL_FORMAfterAjaxDoneActionJS(action) {
	try{
		switch(action) {
		case "SAVE":break;
		}
	}catch(exception){
		errorException(exception);
	}	
}

function REASON_CODEInitJS() {
	try{
		REASON_CODEActionJS();
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
