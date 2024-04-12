function EXCUTE_BTNActionJS(){
	try{
		var formId="VERIFY_PRVLG_PROJECT_CODE_FORM";	
		processApplicationFormAction(formId,"","Y","Y",excutePrivilegeProjectCode);
	}catch(exception){
		errorException(exception);
	}
}
function excutePrivilegeProjectCode(){
	try{
		var className = 'com.eaf.orig.ulo.app.view.util.ajax.VerifyPrivilegeProjectCodeAjax';
		var $data ="&DOCUMENT_TYPE="+getRadioCheckedValue('DOCUMENT_TYPE')+"&CARD_TYPE="
		+$('[name="CARD_TYPE"]').val()+"&PROFESSION="+$('[name="PROFESSION"]').val();
		createRow(className,$data,refreshMasterForm,'VERIFY_PRVLG_PROJECT_CODE_FORM','Y');		
	}catch(exception){
		errorException(exception);
	}	
}
function EDIT_BTNActionJS(){}

function getRadioCheckedValue(obj_name)
{
	try{
		var radioValue = document.forms[0].elements[obj_name];

	   for(var i = 0; i < radioValue.length; i++)
	   {
	      if(radioValue[i].checked)
	      {
	         return radioValue[i].value;
	      }
	   }
	}catch(exception){
		errorException(exception);
	}
   
   return '';
}

function EDIT_BTNActionJS(){
	try{
		var className = 'com.eaf.orig.ulo.app.view.util.ajax.ResetPrivilegeProjectCodeAjax';
		var $data ="";
		createRow(className,$data,refreshMasterForm,'VERIFY_PRVLG_PROJECT_CODE_FORM','Y');	
	}catch(exception){
		errorException(exception);
	}
}