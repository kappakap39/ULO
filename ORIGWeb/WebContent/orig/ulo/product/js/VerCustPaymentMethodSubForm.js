function ACCOUNT_NOActionJS(element, mode, name) {
	try{
	
	}catch(exception){
		errorException(exception);
	}
}
function PAYMENT_METHODActionJS(){
	try{
		var PAYMENT_METHOD_KEC = $("[name='PAYMENT_METHOD_KEC']").val();
		if(PAYMENT_METHOD_KEC == "02"){
			targetDisplayHtml("ACCOUNT_NO_KEC",MODE_EDIT,"targetDisplayHtml");
			targetDisplayHtml("APPROVAL_LIMIT",MODE_EDIT,"targetDisplayHtml");			
		}else{
			targetDisplayHtml("ACCOUNT_NO_KEC",MODE_VIEW,"targetDisplayHtml","Y");
			targetDisplayHtml("PAYMENT_RATIO_KEC",MODE_VIEW,"targetDisplayHtml");
			targetDisplayHtml("APPROVAL_LIMIT",MODE_VIEW,"targetDisplayHtml");
		}
	}catch(exception){
		errorException(exception);
	}
}