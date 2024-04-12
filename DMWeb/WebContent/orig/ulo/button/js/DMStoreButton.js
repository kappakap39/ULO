function SAVE_DMSTORE_BTNActionJS(){
	if(isIncomplete()){
		confirmBox(MESSAGE_DOCUMENT_NOT_COMPLETE,MESSAGE_DOCUMENT_NOT_COMPLETEAfterConfirmActionJS);
	}else{
		saveDMStoreProcess();
	}
}


function MESSAGE_DOCUMENT_NOT_COMPLETEAfterConfirmActionJS(flag){
	if(FLAG_YES==flag){
		saveDMStoreProcess();
	}	
}

function saveDMStoreProcess(){
	$('#action').val('SaveDmStore');
	var formId = $("#FormHandlerManager [name='formId']").val();
	var action = '';
	var handleForm = 'Y';
	var validateForm = 'Y';
	saveApplicationForm(formId,action,handleForm,validateForm);
}

function CLOSE_DMSTORE_BTNActionJS(){ 
	$('#page').val('SEARCH_WAREHOUSE_SCREEN');
	$('#handleForm').val('N');
	$('#appFormName').submit();
}

function isIncomplete(){
	var isIncomplete =false;
	$('.table #DM_DOC_CHECKLIST').each(function(index){
		if($(this).find('input:checked').is(':checked')){
			isIncomplete = true;
		}
		
	});
	return isIncomplete;
}
