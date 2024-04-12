function APPLY_DATEActionJS(element, mode, name) {
	try{
		var APPLY_DATE = $("[name='"+element+"']").val();
		var SCAN_DATE =  $("[name='SCAN_DATE']").val();
		if(!validateBetweenDate(APPLY_DATE,SCAN_DATE)){
			displayHtmlElement(element, SCAN_DATE);
			alertBox('APPLY_DATE_ERROR');
		}
	}catch(exception){
		errorException(exception);
	}
}