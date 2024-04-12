function ClosePopup(){
	try{
		var formId = $("#PopupFormHandlerManager [name='formId']").val();
		closePopupDialog(formId,POPUP_ACTION_SAVE);
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_HIS_HER_FORM_KCCAfterSaveActionJS() {
	try{
		var subformId = $("#K_CREDIT_CARD_SUBFORM [name='subformId']").val();
		ClosePopup();
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_HIS_HER_FORM_SUP_KCCAfterSaveActionJS() {
	try{
		var subformId = $("#SUP_CREDIT_CARD_SUBFORM [name='subformId']").val();
		ClosePopup();
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function MUANG_THAI_PRODUCT_FORM_KCCAfterSaveActionJS() {
	try{
		var subformId = $("#K_CREDIT_CARD_SUBFORM [name='subformId']").val();
		ClosePopup();
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_BOT_PRODUCT_FORM_KCCAfterSaveActionJS() {
	try{
		var subformId = $("#K_CREDIT_CARD_SUBFORM [name='subformId']").val();
		ClosePopup();
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_BOT_FORM_KCC_SUPAfterSaveActionJS() {
	try{
		var subformId = $("#SUP_CREDIT_CARD_SUBFORM [name='subformId']").val();
		ClosePopup();
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_CGA_PRODUCT_FORM_KCCAfterSaveActionJS() {
	try{
		var subformId = $("#K_CREDIT_CARD_SUBFORM [name='subformId']").val();
		ClosePopup();
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_CGA_PRODUCT_FORM_KCC_SUPAfterSaveActionJS() {
	try{
		var subformId = $("#SUP_CREDIT_CARD_SUBFORM [name='subformId']").val();
		if(isEmpty(subformId)){
			subformId = $("#CARD_REQUEST_INFO_SUBFORM [name='subformId']").val();
		}
		ClosePopup();
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_MERCEDE_PRODUCT_FORM_KCCAfterSaveActionJS() {
	try{
		var subformId = $("#K_CREDIT_CARD_SUBFORM [name='subformId']").val();
		ClosePopup();
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_MERCEDE_FORM_KCC_SUPAfterSaveActionJS() {
	try{
		var subformId = $("#SUP_CREDIT_CARD_SUBFORM [name='subformId']").val();
		if(isEmpty(subformId)){
			subformId = $("#CARD_REQUEST_INFO_SUBFORM [name='subformId']").val();
		}
		ClosePopup();
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_BOT_FORM_KCC_SUPAfterSaveActionJS() {
	try{
		var subformId = $("#SUP_CREDIT_CARD_SUBFORM [name='subformId']").val();
		if(isEmpty(subformId)){
			subformId = $("#CARD_REQUEST_INFO_SUBFORM [name='subformId']").val();
		}
		ClosePopup();
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function PERCENT_LIMIT_MAINCARDInitJS(element, mode, name){
	try{
//		alert("element="+element+" : name="+name );
		var suffix = $("[name='SUFFIX']").val();  
		var value = $("[name='"+element+"']").val();    
//		alert(value);
		if(!isEmpty(value) && PERCENT_LIMIT_MAINCARD_MANUAL == value){
//			targetDisplayHtml('PERCENT_LIMIT', MODE_EDIT, 'PERCENT_LIMIT_'+suffix);
		}else{
//			targetDisplayHtml('PERCENT_LIMIT', MODE_VIEW);
			$("[name='PERCENT_LIMIT']").val('');
		}
	}catch(exception){
		errorException(exception);
	}
}

function PERCENT_LIMIT_MAINCARDActionJS(element, mode, name){
	try{
		PERCENT_LIMIT_MAINCARDInitJS(element, mode, name);
		var subformId = $("#SUB_CARD_INFO_SUBFORM [name='subformId']").val();
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}

function POPUP_AMWAY_FORM_KCC_SUPAfterSaveActionJS() {
	console.log('POPUP_AMWAY_FORM_KCC_SUPAfterSaveActionJS');
	try{
		var subformId = $("#SUP_CREDIT_CARD_SUBFORM [name='subformId']").val();
		if(isEmpty(subformId)){
			subformId = $("#CARD_REQUEST_INFO_SUBFORM [name='subformId']").val();
		}
		ClosePopup();
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}