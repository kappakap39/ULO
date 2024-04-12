
// init ID_NO_PIC BEFOR PUSH DATA
function ID_NO_CONSENTInitJS() {
	try{
		var CID_TYPE = $("[name='CID_TYPE']").val();
		if (CID_TYPE == CIDTYPE_IDCARD ||CID_TYPE== CIDTYPE_NON_THAI_NATINALITY) {
			$("[name='ID_NO_CONSENT']").attr("maxLength", 13);
		} else if (CID_TYPE == CIDTYPE_PASSPORT || CID_TYPE == CIDTYPE_MIGRANT) {
			$("[name='ID_NO_CONSENT']").attr("maxLength", 15);
		}
	}catch(exception){
		errorException(exception);
	}
}

// check ID_NO_PIC AFTER PUSH DATA
function ID_NO_CONSENTActionJS (element, mode, name){
	try{
		var CID_TYPE = $("[name='CID_TYPE']").val();
		var ID_NO_PIC = $("[name='ID_NO_CONSENT']").val();
		var errorFlag = 'N';
		if(!isEmpty(ID_NO_PIC)){
			if(CID_TYPE == CIDTYPE_IDCARD){
				var isNationalId = validateNationalId(ID_NO_PIC);
				if (!isNationalId) {
					errorFlag = 'Y';
					alertBox('ERROR_ID_NO_NATIONAL_ID_WRONG_FORMAT',emptyFocusElementActionJS, 'ID_NO_CONSENT');
				}
			}
			else{
				if(ID_NO_PIC.length < 2){
					errorFlag = 'Y';
					alertBox('ERROR_ID_NO_WRONG_FORMAT', emptyFocusElementActionJS,'ID_NO_CONSENT');
				}
			}
		}
	}catch(exception){
		errorException(exception);
	}
	
}

function TH_BIRTH_DATE_CONSENTInitJS(element, mode, name) {
	
	try{
		var TH_BIRTH_DATE_CONSENT = $("[name='TH_BIRTH_DATE_CONSENT']").val();
		//displayHtmlElement('TH_BIRTH_DATE_CONSENT', '');
		//targetDisplayHtml('TH_BIRTH_DATE_CONSENT',MODE_EDIT,'TH_BIRTH_DATE_CONSENT');
	}catch(exception){
		errorException(exception);
	}
}
function TH_BIRTH_DATE_CONSENTActionJS(element, mode, name) {
	try{
		var errorFlag = 'N';
		var TH_BIRTH_DATE_CONSENT = $("[name='TH_BIRTH_DATE_CONSENT']").val();
		var FORM_ID = $("[name='formId']").val();
		var PERSONAL_TYPE =$("[name='PERSONAL_USER_TYPE']").val();
		console.log("FORM_ID >>" ,FORM_ID);
		console.log("PERSONAL_TYPE >>" ,PERSONAL_TYPE);
		console.log("th_birth_date >>>",TH_BIRTH_DATE_CONSENT);
		if(!isEmpty(TH_BIRTH_DATE_CONSENT)){
			var day = TH_BIRTH_DATE_CONSENT.split("/")[0];
			var month = TH_BIRTH_DATE_CONSENT.split("/")[1];
			var year = TH_BIRTH_DATE_CONSENT.split("/")[2];
			var EN_BIRTH_DATE_CONSENT = day + "/" + month + "/" + (parseInt(year) - 543);
			console.log("EN_BIRTH_DATE_CONSENT >>>",EN_BIRTH_DATE_CONSENT);
			
			if (!validateCurrentDateToDate(TH_BIRTH_DATE_CONSENT, LOCAL_TH)) {
				errorFlag = 'Y';
				alertBox('ERROR_TH_BIRTH_DATE_MORETHAN_CURRENTDATE');
			}
			else {
				$("[name='EN_BIRTH_DATE_CONSENT']").val(EN_BIRTH_DATE_CONSENT);
		
			}
		}else{
			$("[name='EN_BIRTH_DATE_CONSENT']").val('');
		}
		
	}catch(exception){
		errorException(exception);
	}
}
function ERROR_TH_BIRTH_DATE_MORETHAN_CURRENTDATEAfterAlertActionJS() {
	try{
		$("[name='EN_BIRTH_DATE_CONSENT']").val('');
		focusElementActionJS('TH_BIRTH_DATE_CONSENT', '');
	}catch(exception){
		errorException(exception);
	}
}

function EN_BIRTH_DATEInitJS() {
	try{
		var EN_BIRTH_DATE = $("[name='EN_BIRTH_DATE_CONSENT']").val();
		//displayHtmlElement('EN_BIRTH_DATE_CONSENT', '');
		//targetDisplayHtml('EN_BIRTH_DATE_CONSENT',MODE_EDIT);
	}catch(exception){
		errorException(exception);
	}
}
function EN_BIRTH_DATE_CONSENTActionJS(element, mode, name) {
	try{
		var errorFlag = 'N';
		var EN_BIRTH_DATE_CONSENT = $("[name='EN_BIRTH_DATE_CONSENT']").val();
		if(!isEmpty(EN_BIRTH_DATE_CONSENT)){
			var day = EN_BIRTH_DATE_CONSENT.split("/")[0];
			var month = EN_BIRTH_DATE_CONSENT.split("/")[1];
			var year = EN_BIRTH_DATE_CONSENT.split("/")[2];
			var TH_BIRTH_DATE_CONSENT = day + "/" + month + "/" + (parseInt(year) + 543);
			if(!validateCurrentDateToDate(EN_BIRTH_DATE_CONSENT, LOCAL_EN)){
				errorFlag = 'Y';
				alertBox('ERROR_EN_BIRTH_DATE_CONSENT_MORETHAN_CURRENTDATE');
			}else{
				$("[name='TH_BIRTH_DATE_CONSENT']").val(TH_BIRTH_DATE_CONSENT);
			}
		}else{
			$("[name='TH_BIRTH_DATE_CONSENT']").val("");
		}
		
	}catch(exception){
		errorException(exception);
	}
}
function ERROR_EN_BIRTH_DATE_CONSENT_MORETHAN_CURRENTDATEAfterAlertActionJS() {
	try{
		$("[name='TH_BIRTH_DATE_CONSENT']").val('');
		focusElementActionJS('EN_BIRTH_DATE_CONSENT', '');
	}catch(exception){
		errorException(exception);
	}
}

function DATE_CONSENTInitJS() {
	try{
		var DATE_CONSENT = $("[name='DATE_CONSENT']").val();
		//displayHtmlElement('DATE_CONSENT', '');
		//targetDisplayHtml('DATE_CONSENT', MODE_EDIT);
	}catch(exception){
		errorException(exception);
	}
}
function DATE_CONSENTActionJS(element, mode, name) {
	try {
		var errorFlag = 'N';
		var DATE_CONSENT = $("[name='DATE_CONSENT']").val();
		if(!isEmpty(DATE_CONSENT)) {
			if(DATE_CONSENT == TH_CURRENT_DATE) {
				$("[name='DATE_CONSENT']").val(DATE_CONSENT);
			} else if(!validateCurrentDateToDate(DATE_CONSENT, LOCAL_TH) || !validateDateOldInDays(DATE_CONSENT, 90)) {
				errorFlag = 'Y';
				alertBox('ERROR_CONSENT_DATE_MORETHAN_CURRENTDATE');
			} else {
				$("[name='DATE_CONSENT']").val(DATE_CONSENT);
			}
			// If personal subform exists, check personal age with consent date
			var BIRTH_DATE_TH = $("[name='TH_BIRTH_DATE']").val();
			if(BIRTH_DATE_TH) {
				var day = BIRTH_DATE_TH.split("/")[0];
				var month = BIRTH_DATE_TH.split("/")[1];
				var year = BIRTH_DATE_TH.split("/")[2];
				var BIRTH_DATE_EN = day + "/" + month + "/" + (parseInt(year) - 543);
				getCalculateAge(BIRTH_DATE_EN);
			}
		} else {
			$("[name='DATE_CONSENT']").val('');
		}
	} catch(exception) {
		errorException(exception);
	}
}
function ERROR_CONSENT_DATE_MORETHAN_CURRENTDATEAfterAlertActionJS() {
	try{
		$("[name='DATE_CONSENT']").val('');
		focusElementActionJS('DATE_CONSENT', '');
	}catch(exception){
		errorException(exception);
	}
}

function validateDateOldInDays(date, day) {
	var dateTo = TH_CURRENT_DATE;
	var olderDate = null;
	try {
		var dateSplit = dateTo.split("/");
		olderDate = new Date(dateSplit[2], dateSplit[1] - 1, dateSplit[0]);
	} catch(e) {
		return false;
	}
	olderDate.setDate(olderDate.getDate() - day);
	var dd = olderDate.getDate();
	var mm = olderDate.getMonth() + 1;
	var yyyy = olderDate.getFullYear();
	if (dd < 10) {
	  dd = '0' + dd;
	} 
	if (mm < 10) {
	  mm = '0' + mm;
	}
	dateTo = dd + '/' + mm + '/' + yyyy;
	var dateFrom = date;
	return !validateBetweenDate(dateFrom, dateTo);
}

function getJsonData(data,propName){
	var valueProp ="";
	try{ 
		var $JSON = $.parseJSON(data);
		if($.isEmptyObject($JSON)){
			return valueProp;
		}
		$.map($JSON, function(item){
			var elementId = item.id;
			var elementValue = item.value;
			if(propName == elementId){
				if(elementValue != null && elementValue != "" && elementValue != undefined) {
					valueProp= elementValue;
				}
			}
		});
	}catch(exception){
		errorException(exception);
	}
	return valueProp;
}
