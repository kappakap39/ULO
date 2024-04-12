function NCB_ACCOUNT_RESULTAfterLoadPopupFinishActionJS(){
	try{
		onloadNCBPopupBlackground();
	}catch(exception){
		errorException(exception);
	}
}