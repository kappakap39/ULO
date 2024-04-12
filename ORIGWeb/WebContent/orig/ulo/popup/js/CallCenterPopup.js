function SAVE_BTNActionJS() {
	try{
		var reason = $('#REASON selected').val();
		var reason_other = $('REASON_OTHER').val();
		var msg ="";
		if(reason != null){
			
		}
		else if(reason == null && reason_other != null){
			
		}
		else{
			return false;
		}
		if($.trim(msg).length()>0 && confirm("text")){
			
		}
	}catch(exception){
		errorException(exception);
	}	
}