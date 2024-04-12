var uploadObj;

$(document).ready( function() {
	$("#DOWNLOAD_EXCEL").hide();
    uploadObj = $("#IMPORT_OT_DATA_FILE").uploadFile({
		url:"UploadServlet",
		multiple:false,
		autoSubmit:false,
		fileName:"IMPORT_FILE",
		formData: {"IMPORT_ID":"IMPORT_OT_DATA"},
		dynamicFormData: function(){
			return {"IMPORT_DATE_CALENDAR":$("input[name=IMPORT_DATE_CALENDAR]").val()};
//			return $("#IMPORT_OT_DATA_FORM").serialize();
		},
		allowedTypes:"xls,xlsx",
		maxFileCount:1,
		maxFileSize:IMPORT_FILE_LIMIT_SIZE,
		sizeErrorStr:ERROR_FILE_SIZE_IS_MAX,
		isOverideSizeErrorStr:true,
		showStatusAfterSuccess:true,
		showDone:false,
		showProgress:true,
		showPreview:true,
		onSuccess:function(files,data,xhr,pd){ 
			var obj = JSON.parse(data);
			if(obj.responseCode == SUCCESS_CODE){
				alertBox(MESSAGE_UPLOAD_SUCCESS);
			}else{
				alertBox(POP_UP_FAIL);
			}
			var objData = JSON.parse(obj.data);
			console.log(objData);
			var total = parseInt(objData.SUCCESS)+parseInt(objData.ERROR_LIST);
			$("#TOTAL").empty();
			$("#SUCCESS").empty();
			$("#REJECT").empty();
			$("#TOTAL").append(total);
			$("#SUCCESS").append(objData.SUCCESS);
			$("#REJECT").append(objData.ERROR_LIST);
			if(obj.ERROR_LIST==0){
				$("#DOWNLOAD_EXCEL").hide();
			}else{
				$("#DOWNLOAD_EXCEL").show();
			}
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

function IMPORT_OT_DATA_IMPORT_BUTTONActionJS(){
	if($(".ajax-file-upload-filename").html()===undefined){
		alert("Please choose file before import.");
	}else if($("input[name=IMPORT_DATE_CALENDAR]").val()===undefined||$("input[name=IMPORT_DATE_CALENDAR]").val()==''){
		alert("Please insert date of data.");
	}else{
		uploadObj.startUpload();
	}
}

function downloadExcel(){
	window.open("/ORIGWeb/DownloadServlet?DOWNLOAD_ID=DOWNLOAD_EXCEL&FILE_NAME=ImportOTDataErrorList.xls");
}

function IMPORT_OT_DATA_CANCEL_BUTTONActionJS(){
	window.location.reload(true);
}