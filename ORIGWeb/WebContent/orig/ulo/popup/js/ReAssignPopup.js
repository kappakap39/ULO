$(document).ready(function(){
});



function OK_BTNActionJS(){
	try{
		if(""!=$('[name="REASSIGN_TO"]').val()){
			Pace.block();
			var $data =getCheckBoxValueList();
				$data +="&REASSIGN_TO="+$('[name="REASSIGN_TO"]').val(); 
			ajax('com.eaf.orig.ulo.app.view.util.ajax.ReAssignTaskToUser',$data,afterReassign);
		}else{
			alertBox(PLEASE_SELECT_STAFF);
		}
	}catch(exception){
		errorException(exception);
	}
}
 
function CANCEL_BTNActionJS(){
	var formId = $("#PopupFormHandlerManager [name='formId']").val();
	closePopupDialog(formId,POPUP_ACTION_CLOSE);
}

function getCheckBoxValueList(){
	var valList="";
	try{
		$(':checkbox').each(function() {
			if(this.checked==true && "ALL"!=this.value){
				valList+="&CHECK_BOX_VALUE="+this.value;
			}
	    });	
	}catch(exception){
		errorException(exception);
	}
	return valList;
}

function afterReassign(data,status,xhr){
	try{
		var formId = $("#PopupFormHandlerManager [name='formId']").val();
		closePopupDialog(formId,POPUP_ACTION_SAVE);
		$('[name="SEARCH_BTN"]').click();
	}catch(exception){
		errorException(exception);
	}
}

function REASSIGN_TOActionJS(){}