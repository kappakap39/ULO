/**
 * 
 */
function validate(){
	var date = appFormName.dataDate.value;
	var fileImport = appFormName.fileName.value;
	
	if(date == "") {
		alertMassage(SELECT_DATE);
		appFormName.dataDate.focus();
		return false;
	}
	if(fileImport == "") {
		alertMassage(SELECT_FILE);
		appFormName.fileName.focus();
		return false;
	}
	
	return true;
}
function cancel(){
	$('#dataDate').val('');
	document.getElementById("iFile").innerHTML = document.getElementById("iFile").innerHTML;
	$('#resporn').remove();
}
function checkFile(){
	var fileName = appFormName.fileName.value;
	var fileType = fileName.substr(fileName.lastIndexOf(".")+1, fileName.length);
	if("XLS" != fileType.toUpperCase() && "XLSX" != fileType.toUpperCase()){
		alertMassage('Wrong file type');
		return false;
	}
	return true;
}
function exportExcel(){
// 	if(validate()){
	    var form = document.reportForm;
	    var oldTargert = form.target;   
    	form.action = "PLExcelServlet";
    	var params = "<input type=\"hidden\" name=\"requestType\" value=" + EXPORT_REJECT_SPECIAL_POINT+" >";
    	params = params + "<iframe name=\"ExportXLSFormFrm\" src =\"\" width=\"0\" height=\"0\"></iframe>";
   	    document.getElementById("reportParam").innerHTML = params; 
   	    form.target = "ExportXLSFormFrm";
   	    form.submit();
   	    form.target = oldTargert;
// 	}
}
function importExcelfile(requestType, form){
	try{
		if(validate()){
			blockScreen();
			$('#resporn').remove();
		    form.setAttribute("action", "PLExcelServlet?requestType=" + requestType);
		    form.setAttribute("method", "post");
		    form.setAttribute("enctype", "multipart/form-data");
		    form.setAttribute("encoding", "multipart/form-data");
		    form.submit();
		}
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
$(document).ready(function (){
	var height = $('.PanelSecond').outerHeight();
	if(height < $(window).height()-60){
		$('.PanelSecond').css({
			'height': $(window).height()-60
		});
	}
	$('.nav-inbox').slimScroll({
		height: $(window).height()+'px'
	});	
	$("Tr.ResultOdd").hover(
		function(){$(this).addClass("ResultOdd-haver-non-pointer");},
		function(){$(this).removeClass("ResultOdd-haver-non-pointer");}
    );
	$("Tr.ResultEven").hover(
	    function(){$(this).addClass("ResultEven-haver-non-pointer");},
	    function(){$(this).removeClass("ResultEven-haver-non-pointer");}
	);
});