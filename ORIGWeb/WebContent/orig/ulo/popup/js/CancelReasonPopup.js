function REASON_OTH_DESCInitJS(){
	try{
		targetDisplayHtml('REASON_OTH_DESC', MODE_VIEW,'REASON_OTH_DESC');
	}catch(exception){
		errorException(exception);
	}
}
function REASON_CODEActionJS(){
	try{
		if(REASON_CODE_OTH==$('[name="REASON_CODE"]').val()){
			targetDisplayHtml('REASON_OTH_DESC', MODE_EDIT,'REASON_OTH_DESC');
		}else{
			targetDisplayHtml('REASON_OTH_DESC', MODE_VIEW,'REASON_OTH_DESC');
			$('[name="REASON_OTH_DESC"]').val('');
		}
	}catch(exception){
		errorException(exception);
	}
}

//function SUBMIT_APPLICATION_BTNActionJS(){
//	var formId = "POPUP_CANCEL_REASON_FORM";
//	processApplicationFormAction(formId,"","Y","Y",SAVE_POPUP_BTNActionAfterManadatoryFormActionJS);
//}
//
//function SAVE_POPUP_BTNActionAfterManadatoryFormActionJS(data,status,xhr){
//	confirmBox(MSG_CANCEL_APPLICATION,submitCancelApplication);
//}
// 
//function submitCancelApplication(confirmFlag){
//	 if(confirmFlag==FLAG_YES){
//			var $data = '';
//			var handleForm = 'N';
//			var validateForm = 'N';
//			var formId = getPopupFormId();	
//			
//			savePopupFormAction(formId,$data,handleForm,validateForm);
//	 }
//}
//
//function POPUP_CANCEL_REASON_FORMAfterSaveActionJS(data,formId){
//	closePopupDialog(formId,POPUP_ACTION_SAVE);
//}
//function POPUP_CANCEL_REASON_FORMAfterCloseActionJS(action){
//	if(BUTTON_ACTION_SAVE==action){
//		var action = BUTTON_ACTION_SUBMIT;
//		var handleForm = 'Y';
//		var validateForm = 'N';
//		var formId = $("#FormHandlerManager [name='formId']").val();
//		saveApplicationForm(formId,action,handleForm,validateForm);
//	}
//}