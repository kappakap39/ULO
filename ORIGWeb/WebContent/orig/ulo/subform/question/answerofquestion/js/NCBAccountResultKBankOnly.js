function NCB_ACCOUNT_RESULT_KBANK_ONLYAfterLoadPopupFinishActionJS(){
	try{
		onloadNCBPopupBlackground();
	}catch(exception){
		errorException(exception);
	}
}