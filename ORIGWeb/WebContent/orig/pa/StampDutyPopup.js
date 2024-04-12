function CANCEL_BTNActionJS()
{
	closePopupDialog('POPUP_STAMP_DUTY_FORM',POPUP_ACTION_CLOSE);
}

function STAMP_DUTY_COMPLETE_BTNActionJS()
{
	//alert('STAMP_DUTY_COMPLETE_BTN is clicked');
	var formId = getPopupFormId();
	var appGroupNo = $("[name='appGroupNo']").val();
	var REQUESTOR_NAME = $("[name='REQUESTOR_NAME']").val();
	var REQUESTOR_POSITION = $("[name='REQUESTOR_POSITION']").val();
	var str = 'appGroupNo=' + appGroupNo + '&REQUESTOR_NAME=' + REQUESTOR_NAME + '&REQUESTOR_POSITION=' + REQUESTOR_POSITION + '&stampAction=Complete';
	savePopupFormAction(formId,str,'N','N');
}

function SAVE_POPUP_BTNActionJS()
{
	var formId = getPopupFormId();
	var appGroupNo = $("[name='appGroupNo']").val();
	var REQUESTOR_NAME = $("[name='REQUESTOR_NAME']").val();
	var REQUESTOR_POSITION = $("[name='REQUESTOR_POSITION']").val();
	var str = 'appGroupNo=' + appGroupNo + '&REQUESTOR_NAME=' + REQUESTOR_NAME + '&REQUESTOR_POSITION=' + REQUESTOR_POSITION;
	savePopupFormAction(formId,str,'N','N');
}

function POPUP_STAMP_DUTY_FORMAfterSaveActionJS()
{
	refreshSearchControlActionJS();
}