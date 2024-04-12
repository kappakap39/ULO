function EMAIL_PRIMARYActionJS(element,mode,name){
	
}
function PHONE_NUMBERActionJS(element,mode,name){
	
}
function ExtOnblurAction(element,mode,name){
	try{
		var id = element.substring(element.length-2,element.length);
		if("EXT1_"+id == element){
			var EXT1 = $("[name='"+element+"']").val();
			var PHONE1 = $("[name='PHONE1_"+id+"']").val();
			if(!isEmpty(EXT1) && isEmpty(PHONE1)){
				alertBox("MESSAGE_PHONE_ERROR", emptyFocusElementActionJS,"PHONE1_"+id);
				displayHtmlElement("EXT1_"+id, '');
			}
		}
	}catch(exception){
		errorException(exception);
	}
}