function CBS1215_DATA_FORMAfterSaveActionJS() {
	try{
		CLOSE_POPUP_BTNActionJS();
	}catch(exception){
		errorException(exception);
	}
}
function CBS1215_DATA_FORMAfterCloseActionJS(){
	try{
		refreshSubForm("FIXED_GUARANTEE_POPUP");
	}catch(exception){
		errorException(exception);
	}
}
function FIXED_DATA_SEQActionJS(){
}