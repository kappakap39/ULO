function DM_SEARCH_BTNActionJS(){
	var formElement = $('#appFormName');
	var checker = countFilledInput(formElement);
	if (checker >= SEARCH_FIELDS_REQUIRED) {
		if (validateElementInputChars('[name=DM_FIRST_NAME],[name=DM_LAST_NAME]')) {
			parent.Pace.block();
			$('#action').val('SearchDmDocmanagement');
			$('#handleForm').val('N');
			$('#appFormName').submit();
		} else {
//			alertBox(PLEASE_INPUT_MORE_CHARACTER);
			alertBox(ERROR_LENGTH_OF_FIELD);
		}		
	} else {
		alertBox('ERROR_REQUIRED_ONE_CRITERIA');
	}
}

function loadDMDocManagement(dmId){
	try{
		parent.Pace.block();
	}catch(e){}	
	$('[name=DM_ID]').val(dmId);
	$('#action').val('loadDmDocManagement');
	$('#hanndleForm').val('N');
	$('#appFormName').submit();
}

function DM_RESET_BTNActionJS(){
	clearForm($('#appFormName .work_area'));
}
