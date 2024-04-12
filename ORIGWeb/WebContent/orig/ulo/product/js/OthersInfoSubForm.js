function SAVING_ACC_NOActionJS(element, mode, name) {
	try{
		accountValidation(element, mode, name);
	}catch(exception){
		errorException(exception);
	}
}
function CURRENT_ACC_NOActionJS(element, mode, name) {
	try{
		accountValidation(element, mode, name);
	}catch(exception){
		errorException(exception);
	}
}
function rewriteForSavingCurrent(element, name, data, error) {
	try{
		if(name == "CURRENT_ACC_NO" || name == "SAVING_ACC_NO") {
			var errElementName = 'COMPLETE_DATA_SAVING';
			if(name == "CURRENT_ACC_NO") {
				errElementName = 'COMPLETE_DATA_SAVING';
			} else if(name == "SAVING_ACC_NO") {
				errElementName = 'COMPLETE_DATA_CURRENT';
			}
			errElementName = errElementName+element.replace(name,'');
			var origVal = $("[name='"+errElementName+"']").val();
			if(error == ERR_CODE_ACCT_SAME_BRANCH) {
				if(origVal == '' || origVal == INFO_IS_CORRECT || origVal == null || origVal == undefined) {				
					$("[name='"+errElementName+"']").val(error);
				}
			} else if(error == null ||  error == "" && error == undefined) {
				if(origVal == ERR_CODE_ACCT_SAME_BRANCH) {
					$("[name='"+errElementName+"']").val('');
				}
			}
		}
	}catch(exception){
		errorException(exception);
	}
}