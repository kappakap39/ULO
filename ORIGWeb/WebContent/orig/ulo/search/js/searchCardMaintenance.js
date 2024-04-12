function SEARCH_BTNActionJS(){
	try{
		var cardlinkFlag = $('[name=CM_CARDLINK_FLAG]').val();
		if (!isEmpty(cardlinkFlag)) {
				Pace.block();
				$('#action').val('SearchCardMaintenance');
				$('#handleForm').val('N');
				$('#appFormName').submit();
		}else {
			alertBox('Send cardlink flag criteria is required field');
		}
	}catch(exception){
		errorException(exception);
	}
}

function loadApplicationActionJS(applicationGroupId,cardlinkRefNo,regType){
	try{
		Pace.block();
		$('[name=APPLICATION_GROUP_ID]').val(applicationGroupId);
		$('[name=CARDLINK_REF_NO]').val(cardlinkRefNo);
		$('[name=REG_TYPE]').val(regType);
		$('#action').val('LoadCardMaintenanceForm');
		$('#handleForm').val('N');
		$('#appFormName').submit();
	}catch(exception){
		errorException(exception);
	}
}


function RESET_BTNActionJS(){
	try{
		clearForm($('#appFormName .work_area '));
	}catch(exception){
		errorException(exception);
	}
}

function rowSpanMiddle(){
	
}