function SAVE_POPUP_BTNActionJS(){
	try{
		var $data = '';
		var formName = getPopupFormName();
		var formId = getPopupFormId();
		confirmBox(MSG_CONFIRM_SUBMIT_REPROCESS,function(choice){
			if ("Y" == choice) {
				savePopupDialog(formId,$data);
			}
		});
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_SUBMIT_REPROCESS_FORMAfterSaveActionJS(data,formId){
	var formId = $("#PopupFormHandlerManager [name='formId']").val();
	closePopupDialog(formId,POPUP_ACTION_SAVE);
}
function POPUP_SUBMIT_REPROCESS_FORMAfterCloseActionJS(action){
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