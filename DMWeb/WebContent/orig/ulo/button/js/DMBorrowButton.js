function SAVE_DMBORROW_BTNActionJS(){
	$('#action').val('SaveDmBorrow');
	var formId = $("#FormHandlerManager [name='formId']").val();
	var action = '';
	var handleForm = 'Y';
	var validateForm = 'Y';
	saveApplicationForm(formId,action,handleForm,validateForm);
}

function CLOSE_DMBORROW_BTNActionJS(){
	$('#page').val('SEARCH_DOC_MANAGEMENT');
	$('#hanndleForm').val('N');
	$('#appFormName').submit();
}