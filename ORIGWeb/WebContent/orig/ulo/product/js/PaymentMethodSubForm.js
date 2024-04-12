function ACCOUNT_NOActionJS(element, mode, name) {
	try{
		accountValidation(element, mode, name);
	}catch(exception){
		errorException(exception);
	}
}
function PAYMENT_METHODActionJS(){
	try{
		var subformId = $("#PRODUCT_FORM [name='subformId']").val();
		refreshSubForm(subformId,'Y');
	}catch(exception){
		errorException(exception);
	}
}
