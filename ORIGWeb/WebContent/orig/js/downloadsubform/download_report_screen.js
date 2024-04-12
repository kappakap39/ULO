
$(document).ready(function(){
	$('.report_file').click(function(){
		var reportId = $(this).attr("id");
		var tokenId = $('#CSRF_TOKEN').val();
		var url = 'DownloadServlet?DOWNLOAD_ID=DOWNLOAD_LINK_REPORT&type=REPORT&reportId='+encodeURIComponent(reportId)+'&tokenId='+encodeURIComponent(tokenId);
		downloadReportAction(url);
	});
});
var downloadReportAction = function downloadReportAction(url) {
    var downloadReportElment = 'downloadReportElment',
        iframe = document.getElementById(downloadReportElment);
    if (iframe === null) {
        iframe = document.createElement('iframe');
        iframe.id = downloadReportElment;
        iframe.style.display = 'none';
        document.body.appendChild(iframe);
    }
    iframe.src = url;
};

function SEARCH_BUTTON_REPORTActionJS(search1,edit,search2){
	var validateId = VALIDATE_DOWNLOAD_REPORT;
	var $data = $('#DOWNLOAD_REPORT *').serialize();
	validateFormAction(validateId, $data, searchDownloadReport, DISPLAY_ERROR_ALERT);
}

function searchDownloadReport(){
	appFormName.action.value = "SearchDownloadReportWebAction"; 
	appFormName.handleForm.value = "N";
	appFormName.submit();
}