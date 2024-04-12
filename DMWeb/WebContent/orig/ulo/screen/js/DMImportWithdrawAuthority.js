var uploadObj;
function DM_IMPORT_BTNActionJS(){
	
	if($(".ajax-file-upload-filename").html()===undefined){
		alertBox("Please choose file before import.");
	}else{
		uploadObj.startUpload();
	}
}

$(document).ready(function (){
	uploadObj = $("#IMPORT_WITHDRAW_AUTHORITY").uploadFile({
					url:"UploadServlet",
					multiple:false,
					autoSubmit:false,
					fileName:"IMPORT_FILE",
					formData: {"IMPORT_ID":"IMPORT_WITHDRAW_AUTHORITY"},
					allowedTypes:"xls,xlsx",
					maxFileCount:1,
					maxFileSize:DM_MAX_FILE_SIZE,
					sizeErrorStr:ERROR_FILE_SIZE_IS_MAX,
					isOverideSizeErrorStr:true,
					showStatusAfterSuccess:true,
					showDone:false,
					showProgress:true,
					showPreview:true,
					onSuccess:function(files,data,xhr,pd){ 
						alertBox(MESSAGE_UPLOAD_SUCCESS);
						displayJSONHtml(data);	
					},
					onSelect: function (files) {
						$('[name="FILE_NAME"]').val(files[0].name);
					 	return true;
			        },
			        onCancel: function (files, pd) {
			        	$('[name="FILE_NAME"]').val('');
				    },
					onError: function (files, status, message, pd) {
						alertBox(MESSAGE_UPLOAD_ERROR+":"+message);
					}
				});
});

function displayJSONHtml(data){
	try{
		var $JSON = $.parseJSON(data);
		if($.isEmptyObject($JSON)){
			return;
		}
		$.map($JSON, function(item){
			var elementId = item.id;
			var elementValue = item.value;
			$("#"+elementId).append(elementValue);		 
		});
	}catch(e){		
	}
}

function DM_CANCEL_BTNActionJS(){ 
window.location.reload(true);
	/*alert("cancel");
	uploadObj.stopUpload();*/
}