var uploadObj;

$(document).ready(function(){
	$("input[name=FILE_NAME]").change(function(){
	    readURL(this);
	});
	
	uploadObj = $("#UPLOAD_PRODUCT_IMAGE_PRODUCT_IMAGE").uploadFile({
		url:"UploadServlet",
		multiple:false,
		autoSubmit:false,
		fileName:"IMPORT_FILE",
		formData: {"IMPORT_ID":"UPDATE_PRODUCT_IMAGE"},
		dynamicFormData: function(){
			return {"productId":$("select[name=PRODUCT_DROPDOWN]").val(),"description":$("input[name=DESCRIPTION_BOX]").val()};
		},
		allowedTypes:"jpeg,jpg,png",
		maxFileCount:1,
		maxFileSize:IMPORT_FILE_LIMIT_SIZE,
		sizeErrorStr:ERROR_FILE_SIZE_IS_MAX,
		isOverideSizeErrorStr:true,
		showStatusAfterSuccess:true,
		showDone:false,
		showProgress:true,
		showPreview:true,
		onSuccess:function(files,data,xhr,pd){ 
			alertBox(MESSAGE_UPLOAD_SUCCESS);
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

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#PREVIEW_IMAGE').attr('src', e.target.result);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

function UPLOAD_PRODUCT_IMAGE_UPDATE_BUTTONActionJS(){
	if($(".ajax-file-upload-filename").html()===undefined){
		alert("Please choose file before import.");
	}else{
		uploadObj.startUpload();
	}
//	var fileSize = $("input[name=IMPORT_FILE]")[0].files[0].size;
//	var limitSize = 1024*1024;
//	console.log("File Size : "+fileSize);
//	console.log("Limit Size : "+limitSize);
//	if(fileSize<=limitSize){
//		var oMyForm = new FormData($("form")[0]);
//		var productId = $("select[name=PRODUCT_DROPDOWN]").val();
//		var description = $("input[name=DESCRIPTION_BOX]").val();
//		
//		console.log("form : "+oMyForm);
//		$.ajax({
//			url : "/ORIGWeb/UploadServlet?IMPORT_ID=UPDATE_PRODUCT_IMAGE&productId="+productId+"&description="+description,
//			type : 'POST',
//			data : oMyForm,
//			processData : false,
//			contentType : false,
//			mimeType : "multipart/form-data",
//			cache : false,
//			success : function(data) {
//				alert("Update Success");
//			}
//		});
//	}else{
//		alert("File bigger than 1024 kb.");
//	}
}

function UPLOAD_PRODUCT_IMAGE_CANCEL_BUTTONActionJS(){
	location.reload();
}