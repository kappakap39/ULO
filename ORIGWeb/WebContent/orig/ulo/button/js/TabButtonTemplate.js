function CLOSE_TAB_BTNActionJS(){
//	try {
//		if(SmartDataEntry) {
//			if(SmartDataEntry.isShow) {
//				SmartDataEntry.show();
//			}
//		}
//	} catch(e) {}
	try{
		var $data = '';
		var handleForm = 'N';
		backLastTabAction($data,handleForm);
	}catch(exception){
		errorException(exception);
	}
}
function SAVE_TAB_BTNActionJS(){
	try{
		var $data = '';
		var handleForm = 'Y';
		var validateForm = 'Y';
		saveTabForm($data,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}
