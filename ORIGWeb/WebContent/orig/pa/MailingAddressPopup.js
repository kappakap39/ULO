var PERSONAL_SEQ = 1;

function MAILING_COMPLETE_BTNActionJS(element,mode,name)
{
	//alert('MailingAddressPopup - COMPLETE_BTN is clicked');
	var formId = getPopupFormId();
	var formName = getPopupFormName();
	var appGroupNo = $("[name='appGroupNo']").val();
	var str = 'appGroupNo=' + appGroupNo;
	var handleForm = 'N';
	var validateForm = 'N';
	savePopupFormAction(formId,str,handleForm,validateForm);
}

function POPUP_MAILING_ADDRESS_FORMAfterSaveActionJS()
{
	refreshSearchControlActionJS();
}

function CANCEL_BTNActionJS()
{
	closePopupDialog('POPUP_MAILING_ADDRESS_FORM',POPUP_ACTION_CLOSE);
}