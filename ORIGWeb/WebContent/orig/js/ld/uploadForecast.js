var uploadObj;

$(document).ready( function() {
	$("#DOWNLOAD_EXCEL").hide();
    uploadObj = $("#UPLOAD_FORECAST_FILENAME").uploadFile({
		url:"UploadServlet",
		multiple:false,
		autoSubmit:false,
		fileName:"IMPORT_FILE",
		formData: {"IMPORT_ID":"UPLOAD_FORECAST"},
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
			alertBox(MESSAGE_UPLOAD_SUCCESS);
			var obj = JSON.parse(data);
			var obj = JSON.parse(obj.data);
			console.log(obj);
			var total = parseInt(obj.SUCCESS)+parseInt(obj.ERROR_LIST.length);
			$("#TOTAL").empty();
			$("#SUCCESS").empty();
			$("#REJECT").empty();
			$("#TOTAL").append(total);
			$("#SUCCESS").append(obj.SUCCESS);
			$("#REJECT").append(obj.ERROR_LIST.length);
			if(obj.ERROR_LIST.length==0){
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

function UPLOAD_FORECAST_IMPORT_BUTTONActionJS(){
	if($(".ajax-file-upload-filename").html()===undefined){
		alert("Please choose file before import.");
	}else{
		uploadObj.startUpload();
	}
}

function downloadExcel(){
	window.open("/ORIGWeb/DownloadServlet?DOWNLOAD_ID=DOWNLOAD_EXCEL&FILE_NAME=ForecastErrorList.xls");
}