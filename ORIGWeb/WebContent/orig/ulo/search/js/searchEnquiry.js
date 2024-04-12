$(function() {
	$('.enqall').click(function(e) {
		try{
		    $('.enqtable input.enqlist:not(:disabled)').prop('checked', $(this).prop('checked'));
		}catch(exception){
			errorException(exception);
		}
	});
	
	$('.enqlist').click(function(e) {
		try{
			e.stopPropagation(); // This will not touch parent
			var container = $(this).parent().parent().parent();
			var checkboxCount = container.find('input.enqlist:not(:disabled)').length;
			var checkedboxCount = container.find('input.enqlist:checked').length;
			$('.enqall').prop({
				'indeterminate': (checkedboxCount > 0) ^ (checkboxCount == checkedboxCount),
				'checked': (checkboxCount == checkedboxCount)
			});
		}catch(exception){
			errorException(exception);
		}
	});
	
	$('.enqtable td:first-child').click(function(e) {
		$(this).find('input.enqlist').trigger('click');
	});
});
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
function getSearchEnquiryPage() {
	return $('[name=searchEnquiryPage]').val();
}

//function POPUP_SEND_APP_TO_FRAUD_FORMAfterSaveActionJS(data) {
//	Pace.block();
//	closePopupDialog('POPUP_SEND_APP_TO_FRAUD_FORM',POPUP_ACTION_SAVE);
//	$('#action').val(getSearchEnquiryPage());
//	$('#handleForm').val('N');
//	$('#appFormName').submit();
//}

function  POPUP_SEND_APP_TO_FRAUD_FORMAfterSaveActionJS() {
	try{
		closePopupDialog('POPUP_SEND_APP_TO_FRAUD_FORM',POPUP_ACTION_SAVE);
	}catch(exception){
		errorException(exception);
	}
}

function POPUP_SEND_APP_TO_FRAUD_FORMAfterCloseActionJS(action){
	try{
		if(action == POPUP_ACTION_SAVE){
			refreshSearchControlActionJS();
		}
	}catch(exception){
		errorException(exception);
	}	
}
function EQ_SEARCH_BTNActionJS(element,mode,name){
	if(mode == MODE_VIEW){
		return;
	}else if(mode == MODE_EDIT){
		EQ_SEARCH_AfterAction();	
	}
}
function EQ_SEARCH_AfterAction(){
	try{	
//		var scanDateFromElement = $('#SCAN_DATE_FROM');
//		var scanDateFrom = scanDateFromElement.val();
//		alert(scanDateFrom);
		var formElement = $('#appFormName .work_area');
		var checker = countFilledInput(formElement);
//		checker = 2;  FOR DEBUGGING PURPOSE
			if (checker >= SEARCH_FIELDS_REQUIRED) {
			if (validateElementInputChars('[name=TH_FIRST_NAME],[name=TH_LAST_NAME],[name=COMPANY]')) {
				Pace.block();
				$('#action').val(getSearchEnquiryPage());
				$('#handleForm').val('N');
				$('#appFormName').submit();
			} else {
//				alertBox(PLEASE_INPUT_MORE_CHARACTER);
				alertBox(ERROR_LENGTH_OF_FIELD);
			}
		} else {
			alertBox('ERROR_REQUIRED_ONE_CRITERIA');
		}
	}catch(exception){
		errorException(exception);
	}

}
function EQ_RESET_BTNActionJS(){
	try{
		clearForm($('#appFormName .work_area'));
	}catch(exception){
		errorException(exception);
	}
}

function EQ_SUSPECT_FRAUDActionJS(appGroupId) {
	try{
		var serialized = "";
		if ($('#'+appGroupId).attr('type') == 'button') {
			// Get ALL IDs
			var serializedChecked = $('.enqlist :checked').serialize();
			serialized = serializedChecked;
			
		} else {
			serialized = "application_group_ids=" + appGroupId;
		}
		
		if (serialized == "") {
			alertBox(ERROR_MUST_SELECT_ONE);
		} else {
			loadPopupDialog('POPUP_SEND_APP_TO_FRAUD_FORM','INSERT','0',serialized, null, '650px', '320px');
		}
	}catch(exception){
		errorException(exception);
	}
}

function IQ_CANCEL_APPLICATION(appGroupId) {
	try{
		var serialized = "";
		serialized = "application_group_ids=" + appGroupId;
		loadPopupDialog('POPUP_IQ_CANCEL_APP_FORM','INSERT','0',serialized, null, '650px', '320px');
	}catch(exception){
		errorException(exception);
	}
}

function loadApplicationActionJS(applicationGroupId){
	try{
		Pace.block();
		$('[name=APPLICATION_GROUP_ID]').val(applicationGroupId);
		$('#action').val('LoadApplication');
		$('#handleForm').val('N');
		$('#appFormName').submit();
	}catch(exception){
		errorException(exception);
	}
}

function REASON_CODEActionJS() {
	try{
		var reasonOtherEl = $('input[name=REASON_OTH_DESC]');
		var reasonCodeValue = $('#REASON_CODE');
		if (reasonCodeValue.val() == REASON_CODE_OTH) {
			targetDisplayHtml('REASON_OTH_DESC', MODE_EDIT, 'REASON_OTH_DESC');
		} else {
			targetDisplayHtml('REASON_OTH_DESC', MODE_VIEW, 'REASON_OTH_DESC');
			reasonOtherEl.val('');
		}
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
//		scanDateToElement.val(scanDateFrom);
	}else{
		CLEAR_VIEWEQActionJS();
	}
	}catch(exception){
		errorException(exception);
	}
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
//		scanDateFromElement.val(scanDateTo);
	}else{
		CLEAR_VIEWEQActionJS();
	}
	}catch(exception){
		errorException(exception);
	}
}

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
function REPROCESSActionJS(appGroupId){
	try{
		var serialized = "";
		if($('#'+appGroupId).attr('type') == 'button'){
			// Get ALL IDs
			var serializedChecked = $('.enqlist :checked').serialize();
			serialized = serializedChecked;
		}else{
			serialized = "application_group_ids=" + appGroupId;
		}
		if(serialized == ""){
			alertBox(ERROR_MUST_SELECT_ONE);
		}else{
			loadPopupDialog('POPUP_SUBMIT_REPROCESS_FORM','INSERT','0',serialized, null, '650px', '280px');
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
function APPLICATION_GROUP_NOActionJS(element,mode,fieldName)
{
	try{
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementValue = elementValue.toUpperCase();
		if(elementValue && /^[0-9]*$/.test(elementValue))
		{
			elementValue = 'QR-' + elementValue;
		}
		elementObject.val(elementValue);
	}catch(exception){
		errorException(exception);
	}
}