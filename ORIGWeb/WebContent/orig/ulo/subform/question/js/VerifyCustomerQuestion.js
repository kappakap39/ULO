

function popUpResultQuestion(popupForm){
	try{
		loadPopupDialog(popupForm,'VIEW','0','');
	}catch(exception){
		errorException(exception);
	}
}