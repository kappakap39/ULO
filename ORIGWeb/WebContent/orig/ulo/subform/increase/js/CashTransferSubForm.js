function ACCOUNT_NOActionJS(element, mode, name) {
	try{
		accountValidation(element, mode, name);
		var subformId = $("#INCREASE_CASH_TRANSFER_SUBFORM [name='subformId']").val();
		refreshSubForm(subformId,'Y');
	}catch(exception){
		errorException(exception);
	}
}
function CASH_1_HOURActionJS(element, mode, name){
	try{
		var subformId = $("#INCREASE_CASH_TRANSFER_SUBFORM [name='subformId']").val();
		refreshSubForm(subformId,'Y');
	}catch(exception){
		errorException(exception);
	}
}