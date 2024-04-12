function ATTACH_FILE_BTNInitJS(){
//	createPopupDialog('POPUP_ATTACHMENT_FORM');
}
function ATTACH_FILE_BTNActionJS(element,mode,name){
	try{
		$('[name="'+element+'"]').attr('disabled',true);
		setTimeout(function(){$('[name="'+element+'"]').attr('disabled',false);}, 150);
		loadPopupDialog("POPUP_ATTACHMENT_FORM","INSERT","0","","","650","400");
		clearTimeout(150);
	}catch(exception){
		errorException(exception);
	}
}
function DELETE_ATTACH_FILE_BTNActionJS(attachId){
	try{
		var $data  ='&ATTACH_ID='+attachId;
		deleteRow('com.eaf.orig.ulo.app.view.util.manual.DeleteAttachment',$data,function(data){
			try{
				refreshSubForm(data);
//				var responseData = $.parseJSON(data);
//				if(responseData.resultCode == SUCCESS_CODE){	
//					refreshSubForm(responseData.data);
//				}else{
//					var formId = $("#FormHandlerManager [name='formId']").val();
//					displayErrorMsg(formId,responseData.resultDesc);
//				}
			}catch(exception){
				errorException(exception);
			}
		});
	}catch(exception){
		errorException(exception);
	}
}
