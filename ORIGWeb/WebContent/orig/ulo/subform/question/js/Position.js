function POSITIONActionJS(){		
	try{
		var POSITION = $('[name="POSITION_Q22"]').val();
		if(null !=POSITION && POSITION ==OCCUPATION_OTHER){
			targetDisplayHtml('POSITION_OTH_Q22', MODE_EDIT,'POSITION_OTH');
			
		}
		else{
			$('[name="POSITION_OTH_Q22"]').val('');
			targetDisplayHtml('POSITION_OTH_Q22', MODE_VIEW,'POSITION_OTH');
		}
	}catch(exception){
		errorException(exception);
	}
}
function POSITION_OTHInitJS(){
	try{
		var POSITION = $('[name="POSITION_Q22"]').val();
		if(null !=POSITION && POSITION ==OCCUPATION_OTHER){
			targetDisplayHtml('POSITION_OTH_Q22', MODE_EDIT,'POSITION_OTH');
		}
		else{
			targetDisplayHtml('POSITION_OTH_Q22', MODE_VIEW,'POSITION_OTH');
		}
	}catch(exception){
		errorException(exception);
	}
}

