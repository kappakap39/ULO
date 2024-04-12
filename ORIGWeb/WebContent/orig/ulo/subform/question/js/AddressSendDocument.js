function EDIT_SEND_DOCActionJS(elementId,mode,name){
	try{
		var elementIdSuffix = elementId.split('_');
		var suffix = elementIdSuffix[elementIdSuffix.length-1];	
		var PERSONAL_ID =$("[name='PERSONAL_ID_"+suffix+"']").val();
		var ADDRESS_TYPE =$("[name='ADDRESS_SEND_DOCUMENT_"+PERSONAL_ID+"']").val();
		var PERSONAL_SEQ = $("[name='PERSONAL_SEQ_"+suffix+"']").val();
		var PERSONAL_TYPE = $("[name='PERSONAL_TYPE_"+suffix+"']").val();
		var $data = '&ADDRESS_TYPE='+ADDRESS_TYPE;
			$data += '&PERSONAL_SEQ='+PERSONAL_SEQ;
			$data += '&PERSONAL_TYPE='+PERSONAL_TYPE;
		var popupForm='';
		if(ADDRESS_TYPE==ADDRESS_TYPE_CURRENT){
			popupForm='POPUP_CURRENT_ADDRESS_FORM_2';
		}else if(ADDRESS_TYPE==ADDRESS_TYPE_WORK){
			popupForm='POPUP_OFFICE_ADDRESS_FORM_2';
		}else if(ADDRESS_TYPE==ADDRESS_TYPE_DOCUMENT){
			popupForm='POPUP_HOME_ADDRESS_FORM_2';
		}	
		loadPopupDialog(popupForm,'UPDATE','0',$data);
	}catch(exception){
		errorException(exception);
	}
}