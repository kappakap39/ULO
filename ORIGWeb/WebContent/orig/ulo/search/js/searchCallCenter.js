function EQ_SEARCH_BTNActionJS(element,mode,name) {
	if(mode == MODE_VIEW){
		return;
	}else if(mode == MODE_EDIT){
		EQ_SEARCH_AfterAction();	
	}
}

function EQ_SEARCH_AfterAction(){
	try{
		var formElement = $('#appFormName .working_area');
		var checker = countFilledInput(formElement);
		// checker = 2; // FOR DEBUGGING PURPOSE
		if (checker >= SEARCH_FIELDS_REQUIRED) {
			if (validateElementInputChars('[name=TH_FIRST_NAME],[name=TH_LAST_NAME]')) {
				Pace.block();
				$('#action').val('SearchCallCenter');
				$('#handleForm').val('N');
				$('#appFormName').submit();
			} else {
				alertBox(PLEASE_INPUT_MORE_CHARACTER);
			}
		} else {
			alertBox('PLEASE_SELECT_LESS_THAN_1_CRITERIA');
		}
	}catch(exception){
		errorException(exception);
	}
}

function EQ_RESET_BTNActionJS() {
	try{
		clearForm($('#appFormName .working_area'));
	}catch(exception){
		errorException(exception);
	}
}

$(function() {
	$('.btncancelaudit').click(function(e) {
		try{
			e.preventDefault();
//			var app_id = $(this).attr('href').replace('#', '');
			var $data = 'rowData='+$(this).attr('row-data');
			loadPopupDialog('POPUP_CALLCENTER_CANCEL_FORM', 'INSERT', '0',$data,'','',POPUP_WIDTH_DEFUALT);
		}catch(exception){
			errorException(exception);
		}
	});
	$('.btncancelapp').click(function(e) {
		try{
			e.preventDefault();
//			var app_group_id = $(this).attr('href').replace('#', '');
			var $data = 'rowData='+$(this).attr('row-data');
			loadPopupDialog('POPUP_CALLCENTER_CANCEL_FORM', 'INSERT', '0',$data,'','',POPUP_WIDTH_DEFUALT);
		}catch(exception){
			errorException(exception);
		}
	});
});
function TH_FIRST_NAMEActionJS(element,mode,fieldName){
	try{
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementValue = elementValue.toUpperCase();
		elementObject.val(elementValue);
	}catch(exception){
		errorException(exception);
	}
}
 
function TH_LAST_NAMEActionJS(element,mode,fieldName){
	try{
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementValue = elementValue.toUpperCase();
		elementObject.val(elementValue);
	}catch(exception){
		errorException(exception);
	}
}
function IDNOActionJS(element,mode,fieldName){
	try{
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementValue = elementValue.toUpperCase();
		elementObject.val(elementValue);
	}catch(exception){
		errorException(exception);
	}
}
function SCAN_DATE_FROMActionJS() {
	try{	
		// Initial Datas
		var scanDateToElement = $('#SCAN_DATE_TO');
		var scanDateTo = scanDateToElement.val();
		// Check null string
		if (scanDateTo == "") {
			CLEAR_VIEWEQActionJS();
			return;
		}
		
		var scanDateFromElement = $('#SCAN_DATE_FROM');
		var scanDateFrom = scanDateFromElement.val();

		// Parse String date to Objects
		var dateFrom = new Date(makeDate(scanDateFrom));
		var dateTo = new Date(makeDate(scanDateTo));
		
		// Comparing dates
		if (dateFrom > dateTo) {
			// From Greater than To. !  It can't be.
			// Set From date -> To date.
			// DF000000000594 ERR MSG : Incorrect error message
			alertBox('ERROR_SCAN_DATE',CLEAR_VIEWEQActionJS);
			scanDateFromElement.val('');
//			scanDateToElement.val(scanDateFrom);
		}else{
			CLEAR_VIEWEQActionJS();
		}
		}catch(exception){
			errorException(exception);
		}
}

function CLEAR_VIEWEQActionJS(){
	var elementObject =$("[name='EQ_SEARCH_BTN']");
	var onclickActionJS = elementObject.attr('onclick').replace('VIEW','EDIT');
	elementObject.attr('onclick',onclickActionJS);
}
function SCAN_DATE_FROMOnFucusJS(){
	var elementObject =$("[name='EQ_SEARCH_BTN']");
	var onclickActionJS = elementObject.attr('onclick').replace('EDIT','VIEW');
	elementObject.attr('onclick',onclickActionJS);
}
function SCAN_DATE_TOOnFucusJS(){
	var elementObject =$("[name='EQ_SEARCH_BTN']");
	var onclickActionJS = elementObject.attr('onclick').replace('EDIT','VIEW');
	elementObject.attr('onclick',onclickActionJS);
}

function SCAN_DATE_TOActionJS() {
	try{
		// Initial Datas
		var scanDateFromElement = $('#SCAN_DATE_FROM');
		var scanDateFrom = scanDateFromElement.val();
		// Check null string
		if (scanDateFrom == "") {
			scanDateFromElement.val(genDateThFromEn(formatDate(new Date(),"/")));
			CLEAR_VIEWEQActionJS();
		}
		
		var scanDateToElement = $('#SCAN_DATE_TO');
		var scanDateTo = scanDateToElement.val();

		// Parse String date to Objects
		var dateTo = new Date(makeDate(scanDateTo));
		var dateFrom = new Date(makeDate(scanDateFrom));
		// Comparing dates
		if (dateTo < dateFrom) {
			// To Greater than From. !  It can't be.
			alertBox('ERROR_SCAN_DATE',CLEAR_VIEWEQActionJS);
			scanDateToElement.val('');
//			scanDateFromElement.val(scanDateTo);
		}else{
			CLEAR_VIEWEQActionJS();
		}
		}catch(exception){
			errorException(exception);
		}
}

function FINAL_DECISION_DATE_FROMActionJS() {
	try{	
	// Initial Datas
	var fnlDateToElement = $('#FINAL_DECISION_DATE_TO');
	var fnlDateTo = fnlDateToElement.val();
	// Check null string
	if (fnlDateTo == "") {
		CLEAR_VIEWEQActionJS();
		return;
	}
	
	var fnlDateFromElement = $('#FINAL_DECISION_DATE_FROM');
	var fnlDateFrom = fnlDateFromElement.val();

	// Parse String date to Objects
	var dateFrom = new Date(makeDate(fnlDateFrom));
	var dateTo = new Date(makeDate(fnlDateTo));
	
	// Comparing dates
	if (dateFrom > dateTo) {
		// From Greater than To. !  It can't be.
		// Set From date -> To date.
		// DF000000000594 ERR MSG : Incorrect error message
		alertBox('ERROR_FINAL_DECISION_DATE',CLEAR_VIEWEQActionJS);
		fnlDateFromElement.val('');
//		fnlDateToElement.val(fnlDateFrom);
	}else{
		CLEAR_VIEWEQActionJS();
	}
	}catch(exception){
		errorException(exception);
	}
}

function FINAL_DECISION_DATE_TOActionJS() {
	try{
	// Initial Datas
	var fnlDateFromElement = $('#FINAL_DECISION_DATE_FROM');
	var fnlDateFrom = fnlDateFromElement.val();
	// Check null string
	if (fnlDateFrom == "") {
		fnlDateFromElement.val(genDateThFromEn(formatDate(new Date(),"/")));
		CLEAR_VIEWEQActionJS();
	}
	
	var fnlDateToElement = $('#FINAL_DECISION_DATE_TO');
	var fnlDateTo = fnlDateToElement.val();

	// Parse String date to Objects
	var dateTo = new Date(makeDate(fnlDateTo));
	var dateFrom = new Date(makeDate(fnlDateFrom));
	// Comparing dates
	if (dateTo < dateFrom) {
		// To Greater than From. !  It can't be.
		alertBox('ERROR_FINAL_DECISION_DATE',CLEAR_VIEWEQActionJS);
		fnlDateToElement.val('');
//		fnlDateFromElement.val(fnlDateTo);
	}else{
		CLEAR_VIEWEQActionJS();
	}
	}catch(exception){
		errorException(exception);
	}
}
