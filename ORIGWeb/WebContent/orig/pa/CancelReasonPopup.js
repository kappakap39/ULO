function REASONActionJS(){
	try{
		if(REASON_CODE_OTH==$('[name="REASON"]').val()){
			targetDisplayHtml('OTHER_REASON', MODE_EDIT,'OTHER_REASON');
		}else{
			targetDisplayHtml('OTHER_REASON', MODE_VIEW,'OTHER_REASON');
			$('[name="OTHER_REASON"]').val('');
		}
	}catch(exception){
		errorException(exception);
	}
}

function SAVE_POPUP_BTNActionJS()
{
	//alert('STAMP_DUTY_COMPLETE_BTN is clicked');
	var formId = getPopupFormId();
	var formName = getPopupFormName();
	var appGroupNo = $("[name='appGroupNo']").val();
	//var REASON = $("[name='REASON']").val();
	//var OTHER_REASON = $("[name='OTHER_REASON']").val();
	//var CANCEL_REMARK = $("[name='CANCEL_REMARK']").val();
	var str = 'appGroupNo=' + appGroupNo;
	var handleForm = 'N';
	var validateForm = 'N';
	savePopupFormAction(formId,str,handleForm,validateForm);
}