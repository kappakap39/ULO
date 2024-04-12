function EXCEPT_POLICY_OTHInitJS(){
	try{
		targetDisplayHtml('EXCEPT_POLICY_OTH', MODE_VIEW,'EXCEPT_POLICY_OTH');
	}catch(exception){
		errorException(exception);
	}
}

function EXCEPT_POLICYActionJS(){
	try{
		if(PRVLG_EXCEPT_POLICY_OTHER==$('[name="EXCEPT_POLICY"]').val()){
			targetDisplayHtml('EXCEPT_POLICY_OTH', MODE_EDIT,'EXCEPT_POLICY_OTH');
		}else{
			targetDisplayHtml('EXCEPT_POLICY_OTH', MODE_VIEW,'EXCEPT_POLICY_OTH');
			$('[name="EXCEPT_POLICY_OTH"]').val('');
		}
	}catch(exception){
		errorException(exception);
	}
}