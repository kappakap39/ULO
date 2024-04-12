
function ITSI_REPORT_EXPORT_BUTTONActionJS(){
	var validateId = VALIDATE_ITSI_REPORT;
	var $data = $('#ITSI_REPORT *').serialize();
	validateFormAction(validateId,$data,getViewReport,DISPLAY_ERROR_SCREEN);
}

function getViewReport(){

	var DATE_NOW = $("[name='DATE_NOW']").val();
	var OUTPUT_FILENAME = $("[name='OUTPUT_FILENAME']").val();
	
	var DATE_TO_CALENDAR = $("[name='DATE_TO_CALENDAR']").val();
	var DATE_FROM_CALENDAR = $("[name='DATE_FROM_CALENDAR']").val();

	var DATE_TO_CALENDAR_EN = generateDateEN(DATE_TO_CALENDAR);
	var	DATE_FROM_CALENDAR_EN = generateDateEN(DATE_FROM_CALENDAR);
	var TRAN_DETAIL_BOX = $("[name='TRAN_DETAIL_BOX']").val();
	var OPERATE_BY_BOX = $("[name='OPERATE_BY_BOX']").val();

	var getdata =[{"FIELD_ID":"P_TRAN_DETAIL","FIELD_VALUE":TRAN_DETAIL_BOX},
	              {"FIELD_ID":"P_OPERATE_BY","FIELD_VALUE":OPERATE_BY_BOX},
	              {"FIELD_ID":"P_TRAN_DATE_FROM","FIELD_VALUE":DATE_FROM_CALENDAR_EN},
	              {"FIELD_ID":"P_TRAN_DATE_TO","FIELD_VALUE":DATE_TO_CALENDAR_EN},
	              ]; 
	var data = JSON.stringify(getdata , null , "");
	
	var url = "ViewReportServlet?FILE_NAME=RE_R016.jasper&OUTPUT_FILENAME="+OUTPUT_FILENAME+"&DATE_NOW="+DATE_NOW+"&JSON_DATA="+data;
	window.location.href = url;
}

function generateDateEN(value){
	var dateEN = "";
	try{
		if(!isEmpty(value)){
			var day = value.split("/")[0];
			var month = value.split("/")[1];
			var year = value.split("/")[2];
			dateEN = day + "/" + month + "/" + (parseInt(year) - 543).toString();
		}
	}catch(exception){
		errorException(exception);
	}
	return dateEN;
}

function ITSI_REPORT_RESET_BUTTONActionJS(search1,edit,search2){
//	clearForm($('#appFormName .work_area'));
	var DEAFULT_DATE_FROM = $("[name='DEAFULT_DATE_FROM']").val();
	var DEAFULT_DATE_TO = $("[name='DEAFULT_DATE_TO']").val();
	

	displayHtmlElement('TRAN_DETAIL_BOX','');
	displayHtmlElement('OPERATE_BY_BOX','');
	displayHtmlElement('DATE_FROM_CALENDAR',DEAFULT_DATE_FROM);
	displayHtmlElement('DATE_TO_CALENDAR',DEAFULT_DATE_TO);
}
function CALENDAROnblurAction(element, mode, name){
	var DATE_FROM_CALENDAR = $("[name='DATE_FROM_CALENDAR']").val();
	var DATE_TO_CALENDAR = $("[name='DATE_TO_CALENDAR']").val();
//	alert(DATE_FROM_CALENDAR);
	if(!isEmpty(DATE_FROM_CALENDAR) && !isEmpty(DATE_TO_CALENDAR)){
		if(!validateBetweenDate(DATE_FROM_CALENDAR,DATE_TO_CALENDAR)){
			alertBox(MESSAGE_DATE_MORE);
			focusElementActionJS(element, '');
		}
	}
}