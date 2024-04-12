function MORE_INFO_BTNActionJS(element,mode,fieldName){
	try{
		var requestData = 'functionId=LoadFullForm';
		LoadRefreshFormAction(requestData,AfterMORE_INFO_BTNActionJS);
	}catch(exception){
		errorException(exception);
	}
}

function AfterMORE_INFO_BTNActionJS(){
	refreshMasterForm('',refreshFormHeader);
}

function LinkText_NCB_REPORT_BTNActionJS(){
	try{
		loadPopupDialog("POPUP_NCB_REPORT_FORM",'VIEW','0','');
	}catch(exception){
		errorException(exception);
	}
}