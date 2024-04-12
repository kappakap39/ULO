function loadCraditCardInfoActionJS(formId,uniqueId,width,height){
	try{
		var $data = 'uniqueId='+uniqueId;
		if(formId != ''){
			loadPopupDialog(formId,'INSERT','0',$data,null,width,height);
		}
	}catch(exception){
		errorException(exception);
	}
}
function deleteCraditCardActionJS(uniqueId,applicationType){
	var $data  = 'uniqueId='+uniqueId;
		$data += '&applicationType='+applicationType;
	ajax('com.eaf.orig.ulo.app.view.form.subform.product.manual.ConfirmMessageDeleteCard',$data,deleteCraditCardAferConfirmMessage);
}
function deleteCraditCardAferConfirmMessage(data){
	$JSON = $.parseJSON(data);
	var messageError = $JSON.conFirmMessage;
	var uniqueId = $JSON.uniqueId;
	try{
		var subformId =$("#PRODUCT_FORM [name='subformId']").val(); 
		var handleForm = 'Y';
		var $data  = 'uniqueId='+uniqueId;
		var	className ='com.eaf.orig.ulo.app.view.form.subform.product.manual.DeleteMainProductInfo';
		confirmBox(messageError,function(choice) {
			if (choice == "Y") {
					MSG_CONFIRM_DELETE_ROWAfterConfirmActionJS(className,$data,PRODUCT_FORMRefreshActionJS,subformId,handleForm);
			}
		});
	}catch(exception){
		errorException(exception);
	}	
}
function PRODUCT_FORMRefreshActionJS(data){
	try{
		var subformId = $("#PRODUCT_FORM [name='subformId']").val();
		refreshSubForm(subformId, 'Y');
		var referenceSubformId = $("#REFERENCE_PERSON_SUBFORM [name='subformId']").val();
		if(referenceSubformId != undefined){
			refreshSubForm(referenceSubformId,"Y");	
		}
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_AMWAY_FORM_KCC_SUPAfterSaveActionJS() {
	try{
		closePopupDialog('POPUP_AMWAY_FORM_KCC_SUP', POPUP_ACTION_SAVE);
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_AMWAY_PRODUCT_FORM_KCCAfterSaveActionJS() {
	try{
		var subformId = $("#K_CREDIT_CARD_SUBFORM [name='subformId']").val();
		closePopupDialog('POPUP_AMWAY_PRODUCT_FORM_KCC', POPUP_ACTION_SAVE);
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_SUPPLEMENTARY_FORM_KCCAfterSaveActionJS() {
	try{
		closePopupDialog('POPUP_SUPPLEMENTARY_FORM_KCC', POPUP_ACTION_SAVE);
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_SUPPLEMENTARY_FORM_KCC_2AfterSaveActionJS() {
	try{
		closePopupDialog('POPUP_SUPPLEMENTARY_FORM_KCC_2', POPUP_ACTION_SAVE);
	}catch(exception){
		errorException(exception);
	}
}
