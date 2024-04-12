function FRAUD_SEARCH_BTNActionJS(){
	try{
		var formElement = $('#appFormName .work_area');
		var checker = countFilledInput(formElement);
		//checker = 2; // FOR DEBUGGING PURPOSE
		if (checker >= SEARCH_FIELDS_REQUIRED) {
			if (validateElementInputChars('[name=TH_FIRST_NAME],[name=TH_LAST_NAME]')) {
				Pace.block();
				$('#action').val('SearchFraud');
				$('#handleForm').val('N');
				$('#appFormName').submit();
			} else {
				alertBox(PLEASE_INPUT_MORE_CHARACTER);
			}
		} else {
			alertBox(PLEASE_SELECT_LESS_THAN_1_CRITERIA);
		}
	}catch(exception){
		errorException(exception);
	}
}

function FRAUD_RESET_BTNActionJS(){
	try{
		clearForm($('#appFormName .work_area'));
	}catch(exception){
		errorException(exception);
	}
}

function loadApplicationActionJS(applicationGroupId){
	try{
		Pace.block();
		$('[name=APPLICATION_GROUP_ID]').val(applicationGroupId);
		$('#action').val('LoadApplication');
		$('#handleForm').val('N');
		$('#appFormName').submit();
	}catch(exception){
		errorException(exception);
	}
}

function APPLICATION_GROUP_NOActionJS(element,mode,name){
//	if(invalidLengthOfData(name,2) && ""!=$('[name="'+name+'"]').val()){
//		alertBox(ERROR_LENGTH_OF_FIELD,emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
//	}
}
function TH_FIRST_NAMEActionJS(element,mode,name){
	try{
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementValue = elementValue.toUpperCase();
		elementObject.val(elementValue);
//		if(invalidLengthOfData(name,2) && ""!=$('[name="'+name+'"]').val()){
//			alertBox(ERROR_LENGTH_OF_FIELD,emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
//		}
	}catch(exception){
		errorException(exception);
	}
}
function TH_LAST_NAMEActionJS(element,mode,name){
	try{
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementValue = elementValue.toUpperCase();
		elementObject.val(elementValue);	
//		if(invalidLengthOfData(name,2) && ""!=$('[name="'+name+'"]').val()){
//			alertBox(ERROR_LENGTH_OF_FIELD,emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
//		}
	}catch(exception){
		errorException(exception);
	}	
}
function ID_NOActionJS(element,mode,name){
	try{
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementValue = elementValue.toUpperCase();
		elementObject.val(elementValue);
//		if(invalidLengthOfData(name,2) && ""!=$('[name="'+name+'"]').val()){
//			alertBox(ERROR_LENGTH_OF_FIELD,emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
//		}
	}catch(exception){
		errorException(exception);
	}
}

function invalidLengthOfData(fieldName,limitLength){
	try{
		var value =  $('[name="'+fieldName+'"]').val();
		if(value.length<limitLength){
			return  true;
		}
	}catch(exception){
		errorException(exception);
	}
	return false;
}