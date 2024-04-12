function VERIFY_PRIVILEGE_BTNActionJS(){
	try{
		var $data ='&PROJECT_CODE_SEQ=0';
			$data += '&PERSONAL_SEQ=1';
		var handleForm = 'Y';
		var validateForm = 'N';
		loadNextTabAction('VERIFY_PRVLG_PROJECT_CODE_FORM','UPDATE','0',$data,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}

function VERIFY_INCOME_BTNActionJS(){
	try{
		var $data ='';
		var handleForm = 'Y';
		var validateForm = 'N';
		loadNextTabAction('VERIFY_INCOME_FORM','EDIT','0',$data,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}

function VERIFY_WEBSITE_BTNActionJS(){
	try{
		var $data ='';
		var handleForm = 'Y';
		var validateForm = 'N';
		loadNextTabAction('VERIFY_WEBSITE_FORM','EDIT','0',$data,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}


function VERIFY_HR_BTNActionJS(){
	try{
		loadNextTabAction("VERIFY_HR_FORM","EDIT","0","",'Y','N');
	}catch(exception){
		errorException(exception);
	}
}

function VERIFY_CUSTOMER_BTNActionJS(){
	try{
		loadNextTabAction("VERIFY_CUSTOMER_FORM","EDIT","0","",'Y','N');
	}catch(exception){
		errorException(exception);
	}
}


function VERIFY_WEBSITE_MANUALActionJS(){
	try{
		if($('[name="VERIFY_WEBSITE_MANUAL"]').prop("checked") == true){
			  targetDisplayHtml('VERIFY_WEBSITE_BTN', MODE_EDIT,'VERIFY_WEBSITE_BTN');
		  }else{
			  targetDisplayHtml('VERIFY_WEBSITE_BTN', MODE_VIEW,'VERIFY_WEBSITE_BTN');
		  }
	}catch(exception){
		errorException(exception);
	}
}
function VERIFY_HR_MANUALActionJS(){
	try{
		if($('[name="VERIFY_HR_MANUAL"]').prop("checked") == true){
			  targetDisplayHtml('VERIFY_HR_BTN', MODE_EDIT,'VERIFY_HR_BTN');
		  }else{
			  targetDisplayHtml('VERIFY_HR_BTN', MODE_VIEW,'VERIFY_HR_BTN');
		  }
	}catch(exception){
		errorException(exception);
	}
}
function VERIFY_CUSTOMER_MANUALActionJS(){
	try{
		if($('[name="VERIFY_CUSTOMER_MANUAL"]').prop("checked") == true){
			  targetDisplayHtml('VERIFY_CUSTOMER_BTN', MODE_EDIT,'VERIFY_CUSTOMER_BTN');
		  }else{
			  targetDisplayHtml('VERIFY_CUSTOMER_BTN', MODE_VIEW,'VERIFY_CUSTOMER_BTN');
		  }
	}catch(exception){
		errorException(exception);
	}
}