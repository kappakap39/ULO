//#rawi comment change logic send to fraud Action
//$(function() {
//	var sendAppCallBack = SAVE_POPUP_BTNActionJS;
//
//	SAVE_POPUP_BTNActionJS = function() {
//		var formEl = $("#PopupFormHandlerManager #POPUP_SEND_APP_TO_FRAUD_FORM");
//		if (formEl.find('[name=FRAUD_REMARK]').val() == "") {
//			alertBox(ERROR_MUST_ENTER_REMARKS);
//		} else {
//			confirmBox(MSG_CONFIRM_SEND_FRAUD, function(choice) {
//				if ("Y" == choice) {
//					sendAppCallBack();
//				}
//			});
//		}
//	};
//});

function SAVE_POPUP_BTNActionJS(){
	try{
		var $data = '';
		var formName = getPopupFormName();
		var formId = getPopupFormId();
//		if(formId == 'REKEY_SENSITIVE_FORM'){
//			Pace.unblockFlag = false;
//		}
		confirmBox(MSG_CONFIRM_SEND_FRAUD, function(choice) {
			if ("Y" == choice) {
				savePopupDialog(formId,$data);
			}
		});
	}catch(exception){
		errorException(exception);
	}
}

function POPUP_SEND_APP_TO_FRAUD_FORMAfterSaveActionJS(data,formId){
	authorizedApplicationListAction(BUTTON_ACTION_SEND_TO_FRAUD);
	var formId = $("#PopupFormHandlerManager [name='formId']").val();
	closePopupDialog(formId,POPUP_ACTION_SAVE);
}


function POPUP_SEND_APP_TO_FRAUD_FORMAfterCloseActionJS(action){
	try{
		Pace.unblockFlag = true;
		Pace.unblock();
		if(POPUP_ACTION_SAVE==action){
			$('[name="EQ_SEARCH_BTN"]').click();
		}
	}catch(exception){
		errorException(exception);
	}
	
}