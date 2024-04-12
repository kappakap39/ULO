function errorException(exception){
	Pace.unblockFlag = true;
	Pace.unblock();
	try{
		console.log("error object:");
	    console.log(exception);
	    console.log();
	    console.log("error object toString():");
	    console.log("\t" + exception.toString());
	    console.log();
	    console.log("error object attributes: ");
	    console.log('\tname: ' + exception.name + ' message: ' + exception.message + ' at: ' + exception.at + ' text: ' + exception.text);
	    console.log();
	    console.log("error object stack: ");
	    console.log(exception.stack);
	}catch(e){}
}
function errorAjax(data,status,xhr){
	Pace.unblockFlag = true;
	Pace.unblock();
}
function errorResponse(data,status,xhr){
	var responseJSON = $.parseJSON(data);
	var resultDesc = responseJSON.responseDesc;
	errorForm(resultDesc);
}
function responseSuccess(data,status,xhr){
	var responseJSON = $.parseJSON(data);
	var resultCode = responseJSON.responseCode;
	if(resultCode == SUCCESS_CODE){
		return true;
	}else{
		errorResponse(data,status,xhr);
		return false;
	}
}
function getResponseData(data){
	var responseJSON = $.parseJSON(data);
	var responseData = responseJSON.data;
	return responseData;
}
function errorForm(errorMsg){
	Pace.unblockFlag = true;
	Pace.unblock();	
	$('.ErrorFormHandlerManager').html('');
	var errorElement = "<div class='alert alert-danger alert-dismissible fade in' role='alert'>";
		errorElement += "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>x</span></button>";
		errorElement += "<div>"+errorMsg+"</div>";
		errorElement += "</div>";
	$('.ErrorFormHandlerManager').append(errorElement);
	$('.ErrorFormHandlerManager')[0].scrollIntoView(true);
}
