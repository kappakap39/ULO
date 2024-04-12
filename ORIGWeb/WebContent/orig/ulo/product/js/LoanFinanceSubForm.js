function ACCOUNT_NOActionJS(element, mode, name) {
	try{
		accountValidation(element, mode, name);
	}catch(exception){
		errorException(exception);
	}
}

function SAVING_PLUSInitJS()
{
	var role = $("[name='ROLE_ID']").val();
	if($("[name='SAVING_PLUS']").is(":checked"))
	{
		if(role == 'DE1_1' || role == 'DE1_2')
		{
			targetDisplayHtml('ACCOUNT_NO_KPL',MODE_VIEW,'ACCOUNT_NO','Y');
			$("#ACCOUNT_NAME_KPL").empty();
			$("[name='COMPLETE_DATA_KPL']").val('');
		}
		else if(role != 'DV' && role != 'DE2')
		{
			targetDisplayHtml('ACCOUNT_NO_KPL',MODE_VIEW,'ACCOUNT_NO','N');
		}
	}
}