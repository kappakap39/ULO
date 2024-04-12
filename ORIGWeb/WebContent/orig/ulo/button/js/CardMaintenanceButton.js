function SAVE_CARD_MAINTENANCE_BTNActionJS(){
	var isValidate = true;
	
	if(!isEmpty($('[name=PERSONAL_VALIDATE]').val())){
		var personalValidate = $('[name=PERSONAL_VALIDATE]').val();
		var validateSplit = personalValidate.split(",");
		for (var i = 0; i<validateSplit.length;i++){
			var validate = validateSplit[i];
			var checkValidate = '[name='+validate+']';
			if(isEmpty($(checkValidate).val())){
				isValidate = false;
			}
		}
	}
	if(!isEmpty($('[name=CARD_VALIDATE]').val())){
		var cardValidate = $('[name=CARD_VALIDATE]').val();
		var validateSplit = cardValidate.split(",");
		for (var i = 0; i<validateSplit.length;i++){
			var validate = validateSplit[i];
			var checkValidate = '[name='+validate+']';
			if(isEmpty($(checkValidate).val())){
				isValidate = false;
			}
		}
	}
	if(isValidate){
		AFTER_SAVE_CARD_MAINTENANCE_BTNActionJS();
	}else{
		alertBox("Please select action in dropdown box");
	}
}

function AFTER_SAVE_CARD_MAINTENANCE_BTNActionJS(){
	try{
		Pace.block();
		$('#action').val('SaveCardMaintenance');
		$('#handleForm').val('N');
		$('#appFormName').submit();
	}catch(exception){
		errorException(exception);
	}
}

function CLOSE_CARD_MAINTENANCE_BTNActionJS(){
	try{
		Pace.block();
		$('#action').val('CloseApplication');
		$('#handleForm').val('N');										 			
		$('#appFormName').submit();
	}catch(exception){
		errorException(exception);
	}
}