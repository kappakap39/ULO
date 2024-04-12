var uploadObj;
function SAVE_POPUP_BTNActionJS(){
	try{
		var formId="POPUP_ATTACHMENT_FORM";	
		processApplicationFormAction(formId,"","Y","Y",POPUP_ATTACHMENT_FORMAfterManadatoryFormActionJS);
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_ATTACHMENT_FORMAfterManadatoryFormActionJS(){
	try{
		uploadObj.startUpload();
	}catch(exception){
		errorException(exception);
	}
}
function initAttachFileUploader() {
	uploadObj = $("#IMPORT_ATTACH_FILE").uploadFile({
					url:"UploadServlet",
					multiple:false,
					autoSubmit:false,
					checkAllowedTypes:true,
					fileName:"IMPORT_FILE",
					formData: {"IMPORT_ID":"IMPORT_ATTACH_FILE"},
					allowedTypes:ATTACHMENT_FILE_TYPE,
					acceptFiles: 'application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint, text/plain, application/pdf, .xps, image/*',
					statusBarWidth:375,
					maxFileCount:1,
					maxFileSize:MAX_FILE_SIZE,
					sizeErrorStr:ERROR_FILE_SIZE,
					dynamicFormData: function () {
//						#rawi comment change logic send dynamicFormData form javascript request
//		                return {"IMPORT_ID":"IMPORT_ATTACH_FILE","FILE_TYPE":$('[name="FILE_TYPE"]').val(),"OTHER_FILE_TYPE":decodeURIComponent($('[name="OTHER_FILE_TYPE"]').val())};
						return $('#ATTACHMENT_POPUP *').serialize();
		            },
					showStatusAfterSuccess:true,
					onSuccess:function(files,data,xhr){
						var formId = $("#PopupFormHandlerManager [name='formId']").val();						
						if(responseAttachmentSuccess(data,"",xhr)){
							closePopupDialog(formId,POPUP_ACTION_SAVE);	
						}						
					},
					onSelect: function (files) {
					 	$('[name="FILE_NAME"]').val(files[0].name);
			                return true;
			            },
			        onCancel: function (files, pd) {
			        	$('[name="FILE_NAME"]').val('');
			        }
				});
	$('.ajax-upload-dragdrop').attr('style','');
}

function POPUP_ATTACHMENT_FORMAfterCloseActionJS(action){
	try{
		if(action == POPUP_ACTION_SAVE){
			var subformId = 'ATTACHMENT_SUBFORM';
			refreshSubForm(subformId);
		}
	}catch(exception){
		errorException(exception);
	}
}
function CLOSE_POPUP_BTNActionJS(){
	try{
		var formId = $("#PopupFormHandlerManager [name='formId']").val();
		closePopupDialog(formId,POPUP_ACTION_CLOSE);
	}catch(exception){
		errorException(exception);
	}
}

 
function OTHER_FILE_TYPEInitJS(){
	try{
		$('[name="OTHER_FILE_TYPE"]').attr('readonly','true');
//		targetDisplayHtml('OTHER_FILE_TYPE', MODE_VIEW,'OTHER_FILE_TYPE');
	}catch(exception){
		errorException(exception);
	}
}
function FILE_TYPEActionJS(){
	try{
		if(ATTACHMENT_OTHER==$('[name="FILE_TYPE"]').val()){
			targetDisplayHtml('OTHER_FILE_TYPE', MODE_EDIT,'OTHER_FILE_TYPE');
		}else{
			targetDisplayHtml('OTHER_FILE_TYPE', MODE_VIEW,'OTHER_FILE_TYPE');
			$('[name="OTHER_FILE_TYPE"]').val('');
		}
	}catch(exception){
		errorException(exception);
	}
}


function responseAttachmentSuccess(data,status,xhr){
	var responseJSON = $.parseJSON(data);
	var resultCode = responseJSON.responseCode;
	if(resultCode == SUCCESS_CODE){
		return true;
	}else{
		errorAttachmentResponse(data,status,xhr);
		return false;
	}
}
function errorAttachmentResponse(data,status,xhr){
	var responseJSON = $.parseJSON(data);
	var resultDesc = responseJSON.responseDesc;
	errorAttachmentForm(resultDesc);
}

function errorAttachmentForm(errorMsg){
	Pace.unblockFlag = true;
	Pace.unblock();	
	$('#POPUP_ATTACHMENT_FORMErrorFormHandlerManager').html('');
	var errorElement = "<div class='alert alert-danger alert-dismissible fade in' role='alert'>";
		errorElement += "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>x</span></button>";
		errorElement += "<div>"+errorMsg+"</div>";
		errorElement += "</div>";
	$('#POPUP_ATTACHMENT_FORMErrorFormHandlerManager').append(errorElement);
	$('#POPUP_ATTACHMENT_FORMErrorFormHandlerManager')[0].scrollIntoView(true);
}