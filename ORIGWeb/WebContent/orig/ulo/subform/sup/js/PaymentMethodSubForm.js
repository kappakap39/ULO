function setAccount() {
	try{
		var formName = $("[name='formName']").val();
		var ACCOUNT_NO = $("[name='ACCOUNT_NO']").val();
		
		if(!isEmpty(ACCOUNT_NO)){
			var className = 'com.eaf.orig.ulo.app.view.util.dih.AccountInformation';
			var data = "FUNCTION=" + TYPE_ACCOUNT;
			data += "&FORM_NAME="+formName;
			data += "&ACCOUNT_NO="+ACCOUNT_NO;
			ajax(className, data, displayJSON);
		}
	}catch(exception){
		errorException(exception);
	}
}
function ACCOUNT_NOActionJS(element, mode, name) {
	try{
		accountValidation(element, mode, name);
	}catch(exception){
		errorException(exception);
	}
}
function PAYMENT_METHODActionJS(){
	try{
		var subformId = $("#SUP_PRODUCT_FORM [name='subformId']").val();
		if(subformId == null || subformId == '' || subformId == undefined) {
			subformId = 'SUP_MAIN_PAYMENT_METHOD_SUBFORM';
		}
		refreshSubForm(subformId,'Y');
	}catch(exception){
		errorException(exception);
	}
}