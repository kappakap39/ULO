function PERSONAL_CARD_MAINTENANCE_ACTION_AActionJS(){
	var elementValue = $("select[name='PERSONAL_CARD_MAINTENANCE_ACTION_A']").val();
	var cancelReasonDiv = $("div[name='PERSONAL_CARD_CANCEL_REASON_DIV_A']");
	var cancelReasonTextBox = $("textarea[name='PERSONAL_CARD_CANCEL_REASON_A']");
	var cancelReasonDivS = $("div[name='PERSONAL_CARD_CANCEL_REASON_DIV_S']");
	var cancelReasonTextBoxS = $("textarea[name='PERSONAL_CARD_CANCEL_REASON_S']");
	console.log(cancelReasonDiv);
	console.log(cancelReasonTextBox);
	if(elementValue == '03'){
		cancelReasonDiv.show();
		$("select[name='PERSONAL_CARD_MAINTENANCE_ACTION_S']")[0].selectize.setValue('03', true);	
		targetDisplayHtml('PERSONAL_CARD_MAINTENANCE_ACTION_S',MODE_VIEW);
		cancelReasonDivS.hide();
		cancelReasonTextBoxS.val("");
	}else{
		cancelReasonDiv.hide();
		cancelReasonTextBox.val("");
		if($("select[name='PERSONAL_CARD_MAINTENANCE_ACTION_S']").val() == '03'
			&& !cancelReasonTextBoxS.val())
		{
			cancelReasonDivS.hide();
			cancelReasonTextBoxS.val("");
			$("select[name='PERSONAL_CARD_MAINTENANCE_ACTION_S']")[0].selectize.setValue('', true);
		}
		targetDisplayHtml('PERSONAL_CARD_MAINTENANCE_ACTION_S',MODE_EDIT);
	}
}

function PERSONAL_CARD_MAINTENANCE_ACTION_SActionJS(){
	var elementValue = $("select[name='PERSONAL_CARD_MAINTENANCE_ACTION_S']").val();
	var cancelReasonDiv = $("div[name='PERSONAL_CARD_CANCEL_REASON_DIV_S']");
	var cancelReasonTextBox = $("textarea[name='PERSONAL_CARD_CANCEL_REASON_S']");
	if(elementValue == '03')
	{
		cancelReasonDiv.show();
	}
	else
	{
		cancelReasonDiv.hide();
		cancelReasonTextBox.val("");
	}
}

function CARD_MAINTENANCE_ACTION_BActionJS(element, mode, name){
	var elementValue = $("select[name='"+element+"']").val();
	var supCard = element.replace("B", "S");
	var cancelReasonDiv = $("div[name='"+element.replace("MAINTENANCE_ACTION", "CANCEL_REASON_DIV")+"']");
	var cancelReasonTextBox = $("textarea[name='"+element.replace("MAINTENANCE_ACTION", "CANCEL_REASON")+"']");
	var cancelReasonDivS = $("div[name='"+supCard.replace("MAINTENANCE_ACTION", "CANCEL_REASON_DIV")+"']");
	var cancelReasonTextBoxS = $("textarea[name='"+supCard.replace("MAINTENANCE_ACTION", "CANCEL_REASON")+"']");
	console.log(supCard);
	if(elementValue == '03'){
		cancelReasonDiv.show();
		$("select[name='"+supCard+"']")[0].selectize.setValue('03', true);	
		targetDisplayHtml(""+supCard,MODE_VIEW);
		cancelReasonDivS.hide();
		cancelReasonTextBoxS.val("");
	}else{
		cancelReasonDiv.hide();
		cancelReasonTextBox.val("");
		if($("select[name='"+supCard+"']").val() == '03'
			&& !cancelReasonTextBoxS.val())
		{
			cancelReasonDivS.hide();
			cancelReasonTextBoxS.val("");
			$("select[name='"+supCard+"']")[0].selectize.setValue('', true);
		}
		targetDisplayHtml(""+supCard,MODE_EDIT);
	}
}

function CARD_MAINTENANCE_ACTION_SActionJS(element, mode, name){
	var elementValue = $("select[name='"+element+"']").val();
	var cancelReasonDiv = $("div[name='"+element.replace("MAINTENANCE_ACTION", "CANCEL_REASON_DIV")+"']");
	var cancelReasonTextBox = $("textarea[name='"+element.replace("MAINTENANCE_ACTION", "CANCEL_REASON")+"']");
	if(elementValue == '03'){
		cancelReasonDiv.show();
	}else{
		cancelReasonDiv.hide();
		cancelReasonTextBox.val("");
	}
}