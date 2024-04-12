function ACCOUNT_NOActionJS(element, mode, name) {
	try{
		accountValidation(element, mode, name);
	}catch(exception){
		errorException(exception);
	}
}
function rewriteForCashTransfer(element, data) {
	try{
		if(element.indexOf(ACCT_TYPE_CASH_TRANSFER) > -1){
			var CASH_TRANSFER_TYPE = $("[name='CASH_TRANSFER_TYPE_"+PRODUCT_K_EXPRESS_CASH+"_CASH_TRANSFER']").val();
			if(CASH_DAY_1 == CASH_TRANSFER_TYPE || CASH_1_HOUR == CASH_TRANSFER_TYPE ) {
				var accountNumber = $.trim($("[name='"+element+"']").val());
				var callForCashObject = $("[name='ACCOUNT_NO_"+PRODUCT_K_EXPRESS_CASH+"_"+CALL_FOR_CASH+"']");
				if(callForCashObject.val() == null || callForCashObject.val() == '') {
					callForCashObject.val(accountNumber);
					callForCashObject.focus();
				}
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function CASH_TRANSFER_TYPEActionJS(element, mode, name){
	try{
		var subformId = $("#PRODUCT_FORM [name='subformId']").val();
		var elementValue = $("[name='"+element+"']").val();
		if(isEmpty(elementValue)){
			$("[name='CASH_1_HOUR_KEC_04']").val('N');
		}
		refreshSubForm(subformId,'Y');
	}catch(exception){
		errorException(exception);
	}
}
function CASH_1_HOURActionJS(element, mode, name){
	try{
		var subformId = $("#PRODUCT_FORM [name='subformId']").val();
		refreshSubForm(subformId,'Y');
	}catch(exception){
		errorException(exception);
	}
}