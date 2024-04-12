var uploadObj;
if($(".ajax-file-upload-filename").html()===undefined){
	//alertBox("Please choose file before import.");
}else{
	uploadObj.startUpload();
}
function MF_UPLOAD_SELECTED_BTNActionJS(){
	if($(".ajax-file-upload-filename").html()===undefined){
		alertBox('POP_UP_NULL_INPUT');
	}else{
		uploadObj.startUpload();
		$('[name="MF_UPLOAD_SELECTED_BTN"]').attr('class','btn button red');
	}
}
$(document).ready(function (){
	uploadObj = $("#UPLOAD_FILE_MANUAL").uploadFile({
					url:"UploadServlet",
					multiple:false,
					autoSubmit:false,
					fileName:"IMPORT_FILE",
					formData: {"IMPORT_ID":$('[name="MF_IMPORT_ID"]').val()},
					allowedTypes:"xls,xlsx",
					isOverideExtErrorStr:true,
//					extErrorStr:"ERROR_FILE_EXTENSIONS",
					maxFileCount:1,
					maxFileSize:MF_MAX_FILE_SIZE,
//					sizeErrorStr:"MF_ERROR_MAX_FILE_SIZE",
					isOverideSizeErrorStr:true,
					showStatusAfterSuccess:true,
					showDone:false,
					showProgress:true,
					showPreview:true,
					onSuccess:function(files,data,xhr,pd){
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
						alertBox("Upload File Error:"+message);
					}
				});
	blockScreenCondition();
});

function blockScreenCondition(){
	try{
		var MF_IMPORT_ID = $("[name='MF_IMPORT_ID']").val();
		var $data = '&MF_IMPORT_ID='+MF_IMPORT_ID;
		console.log(MF_IMPORT_ID);
		
		ajax('com.eaf.orig.ulo.app.view.util.ajax.ValidateUploadFile',$data,function(data){
			var $JSON = $.parseJSON(data);
			if(FLAG_YES==$JSON.isBlock){
				$('#upload_file_msg').html($JSON.message);
				$('#page-wrapper').block({
					message:$('#block_screen_ui')
				});
			}
		});
	}catch(exception){
		errorException(exception);
	}
}

function afterValidate(data){
	
}

function displayJSONHtml(data){
	try{
		var isError=false;
		var $JSON = $.parseJSON(data);
		var $JSON_DATA = $.parseJSON($JSON.data);
		if($.isEmptyObject($JSON_DATA)){
			return;
		}
		$.map($JSON_DATA, function(item){
			var elementId = item.id;
			var elementValue = item.value;
			if(!isError){
				if(item.id=="ERROR"){
					isError=true;
				}else if(item.id=="MF_INVALID_RECORD"&&item.value=="0"){
					alertBox('POP_UP_SUCESS');
				}else if(item.id=="MF_INVALID_RECORD"){
					alertBox('POP_UP_FAIL');
				} 
				$("#"+elementId).append(elementValue);
			}
		});
	}catch(e){		
	}
}
function MF_CANCEL_BTNActionJS(){ 
	window.location.reload(true);
		/*alert("cancel");
		uploadObj.stopUpload();*/
	}

function MF_DOWNLOAD_EXISTING_BTNActionJS(elementName,mode,fieldName){
	var downLoadId = $('[name="MF_DOWNLOAD_ID"]').val();
//	window.onload("/ORIGWeb/DownloadServlet?DOWNLOAD_ID="+downLoadId+"&PROCESS_NAME=DOWNLOAD_EXISTING");
document.getElementById('my_iframe').src = "/ORIGWeb/DownloadServlet?DOWNLOAD_ID="+downLoadId+"&PROCESS_NAME=DOWNLOAD_EXISTING";
}


