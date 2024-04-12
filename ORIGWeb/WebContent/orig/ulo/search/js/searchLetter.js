function RESEND_BTNActionJS()
{
	//alert('RESEND_BTN is clicked');
	//get all checked item and add to database to be fetch by EOD batch (E-Doc Gen Batch)
	//Pace.block();
	var errorList = '';
	var count = 0;
	$('#resultsBody').find("input[name^='CHECK_BOX']").each(function() 
	{
		var name = $(this).attr('name');
		var letterId = name.substring(10);
		var checked =  $(this).is(":checked");
		var resendType = $("[name='RESEND_TYPE_" + letterId.substring(letterId.indexOf('N')).replace('/','_') +"']")[0].selectize.getValue();
		var resendEmail = $("[name='RESEND_EMAIL_" + letterId +"']").val();
		if(checked)
		{
			count++;
			var $data = 'letterId=' +  encodeURIComponent(letterId) + '&resendType=' + resendType + '&resendEmail=' + resendEmail;
			
			//alert($data);
			var errorTxt = validateResendData(letterId, resendType, resendEmail);
			errorList += errorTxt + "<br/>";
			
			try
			{
				if(!errorTxt)
				{
					ajax('com.eaf.orig.ulo.app.view.util.pa.ResendLetterAjax',$data,function(){});
				}
			}catch(exception)
			{
				Pace.unblockFlag = true;
				Pace.unblock();
				errorException(exception);
			}
		}
	});
	
	Pace.unblockFlag = true;
	Pace.unblock();
	
	if(count == 0)
	{
		alertBox('ERROR_SELECT_ONE');
	}
	else if(errorList.replace('<br/>','').trim())
	{
		alertBox(errorList.trim());
	}
	else
	{
		refreshSearchControlActionJS();
	}
}

function validateResendData(letterId, resendType, resendEmail)
{
	var errorTxt = '';
	//alert('resendType = ' + resendType + " resendEmail = " + resendEmail);
	var eirt = eval('ERROR_INPUT_RESEND_TYPE');
	var eie = eval('ERROR_INPUT_EMAIL_SM');
	if(!resendType)
	{
		//errorTxt = 'Please select resend type for letter : ' + letterId;
		errorTxt = eirt + " - " + letterId;
	}
	else if(resendType == 'EMAIL' && !resendEmail)
	{
		//errorTxt = 'Please input email for letter : ' + letterId;
		errorTxt = eie + " - " + letterId;
	}

	//alert('errorText = ' + errorTxt);
	return errorTxt;
}

function CLEAR_VIEWEQActionJS(){
	var elementObject =$("[name='LETTER_SEARCH_BTN']");
	var onclickActionJS = elementObject.attr('onclick').replace('VIEW','EDIT');
	elementObject.attr('onclick',onclickActionJS);
}

function getSearchLetterPage() {
	return $("[name='searchLetterPage']").val();
}

function LETTER_SEARCH_BTNActionJS(element,mode,name){
	if(mode == MODE_VIEW){
		return;
	}else if(mode == MODE_EDIT){
		LETTER_SEARCH_AfterAction();	
	}
}
function LETTER_SEARCH_AfterAction(){
	try{	
		var formElement = $('#appFormName .work_area');
		var checker = countFilledInput(formElement);
			if (checker >= SEARCH_FIELDS_REQUIRED) {
			if (validateElementInputChars('[name=TH_FIRST_NAME],[name=TH_LAST_NAME],[name=COMPANY]')) {
				Pace.block();
				$('#action').val(getSearchLetterPage());
				$('#handleForm').val('N');
				$('#appFormName').submit();
			} else {
				alertBox(ERROR_LENGTH_OF_FIELD);
			}
		} else {
			alertBox('ERROR_REQUIRED_ONE_CRITERIA');
		}
	}catch(exception){
		errorException(exception);
	}

}
function LETTER_RESET_BTNActionJS(){
	try{
		clearForm($('#appFormName .work_area'));
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
function CHECK_BOXActionJS()
{}

function DE2_SUBMIT_DATE_FROMOnFucusJS(){
	var elementObject =$("[name='LETTER_SEARCH_BTN']");
	var onclickActionJS = elementObject.attr('onclick').replace('EDIT','VIEW');
	elementObject.attr('onclick',onclickActionJS);
}
function DE2_SUBMIT_DATE_TOOnFucusJS(){
	var elementObject =$("[name='LETTER_SEARCH_BTN']");
	var onclickActionJS = elementObject.attr('onclick').replace('EDIT','VIEW');
	elementObject.attr('onclick',onclickActionJS);
}

function DE2_SUBMIT_DATE_FROMActionJS() {
	try{	
	// Initial Datas
	var scanDateToElement = $('#DE2_SUBMIT_DATE_TO');
	var scanDateTo = scanDateToElement.val();
	// Check null string
	if (scanDateTo == "") {
		CLEAR_VIEWEQActionJS();
		return;
	}
	
	var scanDateFromElement = $('#DE2_SUBMIT_DATE_FROM');
	var scanDateFrom = scanDateFromElement.val();

	// Parse String date to Objects
	var dateFrom = new Date(makeDate(scanDateFrom));
	var dateTo = new Date(makeDate(scanDateTo));
	
	// Comparing dates
	if (dateFrom > dateTo) {
		alertBox('ERROR_DE2_SUBMIT_DATE',CLEAR_VIEWEQActionJS);
		scanDateFromElement.val(scanDateTo);
	}else{
		CLEAR_VIEWEQActionJS();
	}
	}catch(exception){
		errorException(exception);
	}
}

function DE2_SUBMIT_DATE_TOActionJS() {
	try{
	// Initial Datas
	var scanDateFromElement = $('#DE2_SUBMIT_DATE_FROM');
	var scanDateFrom = scanDateFromElement.val();
	// Check null string
	if (scanDateFrom == "") {
		scanDateFromElement.val($('#DE2_SUBMIT_DATE_TO').val());
		CLEAR_VIEWEQActionJS();
	}
	
	var scanDateToElement = $('#DE2_SUBMIT_DATE_TO');
	var scanDateTo = scanDateToElement.val();

	// Parse String date to Objects
	var dateTo = new Date(makeDate(scanDateTo));
	var dateFrom = new Date(makeDate(scanDateFrom));
	// Comparing dates
	if (dateTo < dateFrom) {
		alertBox('ERROR_DE2_SUBMIT_DATE',CLEAR_VIEWEQActionJS);
		scanDateToElement.val('');
	}else{
		CLEAR_VIEWEQActionJS();
	}
	}catch(exception){
		errorException(exception);
	}
}

function showHideEmail(ele)
{
	var eleLetterId = $(ele).attr('name').substring(12);
	var letterId = eleLetterId.replace('_','/');
	//alert('letterId = ' + letterId);
	var resendType = $(ele).val();
	$('#resultsBody').find("input[name^='RESEND_EMAIL_']").each(function() 
	{
		
		if($(this).attr('name').includes(letterId))
		{
			//alert('reemail letterId : ' + $(this).attr('name'));
			if(resendType == 'PAPER')
			{
				$(this).attr("readonly", "readonly");
				$(this).val('');
			}
			else
			{
				$(this).removeAttr("readonly");
				$(this).val($("[name='RESEND_EMAIL_TEMP_" + eleLetterId + "']").val());
			}
		}
	});
}

/*function PRODUCTInitJS(){
	try{
		listBoxFilterAction('PRODUCT',FIELD_ID_PRODUCT_TYPE,'ACTIVE',KPL_PRODUCT_LISTBOX_FILTER,'','');
	}catch(exception){
		errorException(exception);
	}
}*/