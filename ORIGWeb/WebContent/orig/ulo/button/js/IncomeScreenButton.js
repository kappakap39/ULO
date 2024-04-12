function CLOSE_TAB_INCOME_SCREEN_BTNActionJS(){
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

function SAVE_TAB_INCOME_SCREEN_BTNActionJS(){
	try{
		console.log("SAVE_TAB_INCOME_SCREEN_BTNActionJS");
		var	 className ='com.eaf.orig.ulo.app.view.util.ajax.IncomeFlagSave';
		var data = '';
		ajax(className,data,AfterSAVE_TAB_INCOME_SCREEN_BTNActionJS);
	}catch(exception){
		errorException(exception);
	}
}

function AfterSAVE_TAB_INCOME_SCREEN_BTNActionJS(data){
	if(FLAG_YES == data){
		confirmBox(CONFIRM_INCOME_SCREEN,AfterConfirmSAVE_TAB_INCOME_SCREEN_BTNActionJS);
	}else{
		AfterConfirmSAVE_TAB_INCOME_SCREEN_BTNActionJS('Y');
	}
}

function AfterConfirmSAVE_TAB_INCOME_SCREEN_BTNActionJS(data){
	var $data = '';
	var handleForm = 'Y';
	var validateForm = 'Y';
	if(FLAG_YES == data){
		saveTabForm($data,handleForm,validateForm);
	}
}