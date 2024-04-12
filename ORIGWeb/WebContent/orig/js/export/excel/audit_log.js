
function AUDIT_LOG_EXPORT_BUTTONActionJS(){
	var validateId = VALIDATE_AUDIT_LOG_REPORT;
	var $data = $('#AUDIT_LOG_REPORT *').serialize();
	validateFormAction(validateId,$data,getViewReport,DISPLAY_ERROR_SCREEN);
}

function getViewReport(){
	var USER_NAME_BOX = $("[name='USER_NAME_BOX']").val();
	var ROLE_BOX = $("[name='ROLE_BOX']").val();
	var DATE_NOW = $("[name='DATE_NOW']").val();
	var OUTPUT_FILENAME = $("[name='OUTPUT_FILENAME']").val();
	var DATE_FROM_CALENDAR = $("[name='DATE_FROM_CALENDAR']").val();
	var	DATE_FROM_CALENDAR_EN = generateDateEN(DATE_FROM_CALENDAR);
	var TIME_FROM_HOUR_BOX = $("[name='TIME_FROM_HOUR_BOX']").val();
	var TIME_FROM_MIN_BOX = $("[name='TIME_FROM_MIN_BOX']").val();
	var DATE_TO_CALENDAR = $("[name='DATE_TO_CALENDAR']").val();
	var DATE_TO_CALENDAR_EN = generateDateEN(DATE_TO_CALENDAR);
	var TIME_TO_HOUR_BOX = $("[name='TIME_TO_HOUR_BOX']").val();
	var TIME_TO_MIN_BOX = $("[name='TIME_TO_MIN_BOX']").val();
	
	var REPORT_CONDITION = '';
	
	if(null!=USER_NAME_BOX && ""!=USER_NAME_BOX){
		REPORT_CONDITION += 'Username '+USER_NAME_BOX+'/';
	}
	if(null!=ROLE_BOX && ""!=ROLE_BOX){
		REPORT_CONDITION += 'Role '+ROLE_BOX+'/';
	}
	if(null!=DATE_FROM_CALENDAR_EN && ""!=DATE_FROM_CALENDAR_EN){
		REPORT_CONDITION += 'Date From '+DATE_FROM_CALENDAR_EN;
		if(null!=TIME_FROM_HOUR_BOX && ""!=TIME_FROM_HOUR_BOX && null!=TIME_FROM_MIN_BOX && ""!=TIME_FROM_MIN_BOX){
			REPORT_CONDITION += ' '+TIME_FROM_HOUR_BOX+':'+TIME_FROM_MIN_BOX;
		}
		REPORT_CONDITION += '/';
	}
	
	if(null!=DATE_TO_CALENDAR_EN && ""!=DATE_TO_CALENDAR_EN){
		REPORT_CONDITION += 'Date To '+DATE_TO_CALENDAR_EN;
		if(null!=TIME_TO_HOUR_BOX && ""!=TIME_TO_HOUR_BOX && null!=TIME_TO_MIN_BOX && ""!=TIME_TO_MIN_BOX){
			REPORT_CONDITION += ' '+TIME_TO_HOUR_BOX+':'+TIME_TO_MIN_BOX;
		}
	}
	
	console.log('CONDITION :: '+REPORT_CONDITION);

	var getdata =[{"FIELD_ID":"P_USER_NAME","FIELD_VALUE":USER_NAME_BOX},
	              {"FIELD_ID":"P_ROLE","FIELD_VALUE":ROLE_BOX},
	              {"FIELD_ID":"P_DATE_FORM","FIELD_VALUE":DATE_FROM_CALENDAR_EN},
	              {"FIELD_ID":"P_TIME_HH_FROM","FIELD_VALUE":TIME_FROM_HOUR_BOX},
	              {"FIELD_ID":"P_TIME_MM_FROM","FIELD_VALUE":TIME_FROM_MIN_BOX},
	              {"FIELD_ID":"P_DATE_TO","FIELD_VALUE":DATE_TO_CALENDAR_EN},
	              {"FIELD_ID":"P_TIME_HH_TO","FIELD_VALUE":TIME_TO_HOUR_BOX},
	              {"FIELD_ID":"P_TIME_MM_TO","FIELD_VALUE":TIME_TO_MIN_BOX},
	              {"FIELD_ID":"P_REPORT_CONDITION","FIELD_VALUE":REPORT_CONDITION}
	              ]; 
	var data = JSON.stringify(getdata , null , "");
	//alert(data);
	var url = "ViewReportServlet?FILE_NAME=RE_R013.jasper&OUTPUT_FILENAME="+OUTPUT_FILENAME+"&DATE_NOW="+DATE_NOW+"&JSON_DATA="+data;
	window.location.href = url; //window.open(url);
//	popUpWindow.setFramePosition(parent.$('#container'),1500,1500);
//	popUpWindow.document.write('<iframe height="1500" allowTransparency="true" frameborder="0" scrolling="yes" style="width:100%;" src="'+url+'" type= "text/javascript"></iframe>');
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

function AUDIT_LOG_RESET_BUTTONActionJS(search1,edit,search2){
//	clearForm($('#appFormName .work_area'));
	var DEAFULT_DATE_FROM = $("[name='DEAFULT_DATE_FROM']").val();
	var DEAFULT_DATE_TO = $("[name='DEAFULT_DATE_TO']").val();

	displayHtmlElement('USER_NAME_BOX','');
	displayHtmlElement('ROLE_BOX','');
	displayHtmlElement('DATE_FROM_CALENDAR',DEAFULT_DATE_FROM);
	displayHtmlElement('DATE_TO_CALENDAR',DEAFULT_DATE_TO);
	
	var data  ='';
	var className = 'com.eaf.orig.ulo.app.view.util.ajax.CurrentDateTime';
	ajax(className,data,resetTimeAuditLog);
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

function resetTimeAuditLog(data){
	var obj = $.parseJSON(data);
	console.log(obj);
	var CURRNET_HOUR = obj.hour;
	var CURRENT_MIN = obj.miniute;
	displayHtmlElement('TIME_FROM_HOUR_BOX',CURRNET_HOUR);
	displayHtmlElement('TIME_FROM_MIN_BOX',CURRENT_MIN);
	displayHtmlElement('TIME_TO_HOUR_BOX',CURRNET_HOUR);
	displayHtmlElement('TIME_TO_MIN_BOX',CURRENT_MIN);
}


