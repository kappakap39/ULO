function VERIFY_HR_BTNActionJS(){
	try{
		loadNextTabAction("VERIFY_HR_FORM","EDIT","0","",'N','N');
	}catch(exception){
		errorException(exception);
	}
}

function VERIFY_CUSTOMER_BNTActionJS(){
	try{
		loadNextTabAction("VERIFY_CUSTOMER_FORM","EDIT","0","",'N','N');
	}catch(exception){
		errorException(exception);
	}
}