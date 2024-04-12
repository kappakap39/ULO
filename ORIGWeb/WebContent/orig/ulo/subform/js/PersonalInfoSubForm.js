$(document).ready(function(){
	try{
		IDNOblurAction('ID_NO','EDIT','ID_NO');
	}catch(exception){
		errorException(exception);
	}
});

function CID_TYPEInitJS(element, mode, name) {
//	try{
//		
//		var person_type =  $("[name='PERSONAL_USER_TYPE']").val();
//		if(person_type){
//		//alert("PERSONAL_USER_TYPE : "+person_type);
//		var className = "com.eaf.orig.ulo.app.view.util.ajax.GetDocumentPersonal";
//		var data = {
//			PERSON_TYPE:person_type
//		};
//		ajax(className, data, getDocumentPersonalfterActionJS);
//		}
//	}catch(exception){
//		errorException(exception);
//	}
	
}

function getDocumentPersonalfterActionJS(data) {
	//console.log("getDocumentPersonalfterActionJS >>>");
	try{
		var $JSON = $.parseJSON(data);
		var propName = 'check';
		var valueProp = '';
		if($.isEmptyObject($JSON)){
			return;
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
		//alert("checkDoc "+valueProp);
		// check has document 080
		if(valueProp == 'y'){ // has doc
			var CID_TYPE = $("[name='CID_TYPE']").val();
			if(CID_TYPE!=CIDTYPE_NON_THAI_NATINALITY){
				displayHtmlElement('CID_TYPE',CIDTYPE_NON_THAI_NATINALITY);
				ID_NOInitJS();
				CHECK_CIDTYPE_NON_THAINATINALITY();
			}
			
		}else{ // hasn't doc
		}
	}catch(exception){
		errorException(exception);
	}
	
}

function CID_TYPEActionJS(element,mode,name) {
//	try{TH_TITLE_DESCListBoxActionJS();}catch(e){}
//	try{
//		var CID_TYPE = $("[name='CID_TYPE']").val();
//		if(CID_TYPE == CIDTYPE_IDCARD){
//			$("[name='ID_NO']").attr("maxLength", 13);
//		}else if(CID_TYPE == CIDTYPE_PASSPORT || CID_TYPE == CIDTYPE_MIGRANT){
//			$("[name='ID_NO']").attr("maxLength", 15);
//		}
//	}catch(e){}
//	try{NATIONALITYInitJS();}catch(e){}
//	try{VISA_TYPEInitJS();}catch(e){}
//	try{VISA_EXPIRE_DATEInitJS();}catch(e){}
//	try{WORK_PERMIT_NOInitJS();}catch(e){}
//	try{WORK_PERMIT_EXPIRE_DATEInitJS();}catch(e){}
//	try{PASSPORT_EXPIRE_DATEInitJS();}catch(e){}
//	try{TH_FIRST_NAMEInitJS('TH_FIRST_NAME',mode,'TH_FIRST_NAME');}catch(e){}
//	try{TH_MID_NAMEInitJS('TH_MID_NAME',mode,'TH_MID_NAME');}catch(e){}
//	try{TH_LAST_NAMEInitJS('TH_LAST_NAME',mode,'TH_LAST_NAME');}catch(e){}
//	try{TH_BIRTH_DATEInitJS('TH_BIRTH_DATE',mode,'TH_BIRTH_DATE');}catch(e){}
//	try{EN_BIRTH_DATEInitJS('EN_BIRTH_DATE',mode,'EN_BIRTH_DATE');}catch(e){}

	// clear ID_NO_CONSENT
	 $("[name='ID_NO_CONSENT']").val('');
	try{
		getCisPersonalInfoData('CID_TYPE',mode);	
	}catch(exception){
		errorException(exception);
	}
}
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
		if(errorFlag == 'N'){
			if(!isEmpty(CID_TYPE)){
				getCisPersonalInfoData('ID_NO_CONSENT',mode);
			}
		}
	}catch(exception){
		errorException(exception);
	}
	
}



function ID_NOInitJS() {
	try{
		var CID_TYPE = $("[name='CID_TYPE']").val();
		
		if (CID_TYPE == CIDTYPE_IDCARD || CID_TYPE == CIDTYPE_NON_THAI_NATINALITY) {
			$("[name='ID_NO']").attr("maxLength", 13);
		} else if (CID_TYPE == CIDTYPE_PASSPORT || CID_TYPE == CIDTYPE_MIGRANT) {
			$("[name='ID_NO']").attr("maxLength", 15);
		}
	}catch(exception){
		errorException(exception);
	}
}



function ID_NOActionJS(element, mode, name) {
	try{
		var CID_TYPE = $("[name='CID_TYPE']").val();
		var ID_NO = $("[name='ID_NO']").val();
		var errorFlag = 'N';
		if(!isEmpty(ID_NO)){
			if(CID_TYPE == CIDTYPE_IDCARD ){
				var isNationalId = validateNationalId(ID_NO);
				if (!isNationalId) {
					errorFlag = 'Y';
					alertBox('ERROR_ID_NO_NATIONAL_ID_WRONG_FORMAT',emptyFocusElementActionJS, 'ID_NO');
				}
				
			}else if(CID_TYPE == CIDTYPE_NON_THAI_NATINALITY){
				var isNationalId = validateNationalId(ID_NO);
				if (!isNationalId) {
					errorFlag = 'Y';
					alertBox('ERROR_ID_NO_NATIONAL_ID_WRONG_FORMAT',emptyFocusElementActionJS, 'ID_NO');
				}else{
					CHECK_CIDTYPE_NON_THAINATINALITY(ID_NO);
				}
			}
			else{
				if(ID_NO.length < 2){
					errorFlag = 'Y';
					alertBox('ERROR_ID_NO_WRONG_FORMAT', emptyFocusElementActionJS,'ID_NO');
				}
			}
		}
		if(errorFlag == 'N'){
			if(!isEmpty(CID_TYPE)){
				getCisPersonalInfoData('ID_NO',mode);
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function CIS_NUMBERInitJS(element, mode, name) {
	
}
function TH_TITLE_DESCInitJS(element, mode, name) {
	try{
		
		var CIS_NUMBER = $("[name='CIS_NUMBER']").val();
		var ID_NO = $("[name='ID_NO']").val();
		if (isEmpty(ID_NO) && !isEmpty(CIS_NUMBER)){
			displayHtmlElement('TH_TITLE_CODE', '');
			displayHtmlElement('TH_TITLE_DESC', '');
		}
		var elementValue = $("[name='"+element+"']").val();
		var EN_TITLE_VALUE = $("[name='EN_TITLE_DESC']").val();
		if(!isEmpty(elementValue) && isEmpty(EN_TITLE_VALUE)){
			getTITLE_CODEActionJS(element,'TH_TITLE_CODE');
		}
	}catch(exception){
		errorException(exception);
	}
}
 

function TH_TITLE_DESCListBoxActionJS() {
	
}
function getTITLE_CODEActionJS(element,TITLE_CODE_NAME){
	try{
		var className = 'com.eaf.orig.ulo.app.view.form.manual.GetTitleName';
		var $data =  $("#PERSONAL_INFO_SUBFORM *").serialize();
			$data += '&TITLE_NAME='+element;
			$data += '&TITLE_CODE_NAME='+TITLE_CODE_NAME;
			$data += '&CID_TYPE='+$("[name='CID_TYPE']").val();
		ajax(className, $data,TITLE_CODEAfterActionJS);
	}catch(exception){
		errorException(exception);
	}
}
function TITLE_CODEAfterActionJS(data){
	try{
		displayJSON(data);
		var TITLE_CODE = "";
		try{
			var $JSON = $.parseJSON(data);
			if($.isEmptyObject($JSON)){
				return;
			}
			$.map($JSON, function(item){
				TITLE_CODE = item.id;
			});
		}catch(e){		
		}
		var EN_TITLE_CODE = $("[name='EN_TITLE_CODE']").val();
		var TH_TITLE_CODE = $("[name='TH_TITLE_CODE']").val();
		var TH_TITLE_CODE_Element = $("[name='"+TITLE_CODE+"']");
		//#Fix title code mapping flip en and th if en element not mapping th
		if(TITLE_CODE != "EN_TITLE_CODE" && ((TITLE_OTHER!=EN_TITLE_CODE) || (TITLE_OTHER!=TH_TITLE_CODE))){ 
			getGENDER(TH_TITLE_CODE_Element);
		}
	}catch(exception){
		errorException(exception);
	}
	
}
function TH_TITLE_DESCActionJS(element, mode, name) {
	try{
		if(TITLE_OTHER==$("[name='TH_TITLE_CODE']").val()){
			displayHtmlElement('EN_TITLE_CODE', '');
			displayHtmlElement('EN_TITLE_DESC', '');
			targetDisplayHtml('EN_TITLE_DESC', MODE_EDIT, 'EN_TITLE_DESC');
		}
		getTITLE_CODEActionJS(element,'TH_TITLE_CODE');
	}catch(exception){
		errorException(exception);
	}
}
function EN_TITLE_DESCActionJS(element, mode, name){
	try{
		getTITLE_CODEActionJS(element,'EN_TITLE_CODE');	

	}catch(exception){
		errorException(exception);
	}
}
function getGENDER(element) {
	try{
		var field = element.attr('name');
		var title_id = element.val();
		var CID_TYPE = $("[name='CID_TYPE']").val();
		var className = "com.eaf.orig.ulo.app.view.util.ajax.GetTitleGender";
		var data = {
			field: field,
			id: title_id,
			CID_TYPE:CID_TYPE
		};
		ajax(className, data, getGENDERAfterActionJS);
	}catch(exception){
		errorException(exception);
	}
}
function getGENDERAfterActionJS(data){
	try{
		displayJSON(data);
		EN_TITLE_DESCInitJS('',MODE_EDIT,'');
	}catch(exception){
		errorException(exception);
	}
}
function EN_TITLE_DESCInitJS(element, mode, name) {
	try{
		var CIS_NUMBER = $("[name='CIS_NUMBER']").val();
		var ID_NO = $("[name='ID_NO']").val();
		if (isEmpty(ID_NO) && !isEmpty(CIS_NUMBER)) {
			displayHtmlElement('EN_TITLE_CODE', '');
			displayHtmlElement('EN_TITLE_DESC', '');
		}
		var elementValue = $("[name='"+element+"']").val();
		var TH_TITLE_VALUE = $("[name='TH_TITLE_DESC']").val();
		if(!isEmpty(elementValue) && isEmpty(TH_TITLE_VALUE)){
			getTITLE_CODEActionJS(element,'EN_TITLE_CODE');
		}
		
		var EN_TITLE_CODE = $("[name='EN_TITLE_CODE']").val();
		var TH_TITLE_CODE = $("[name='TH_TITLE_CODE']").val();
		var TH_TITLE_DESC = $("[name='TH_TITLE_DESC']").val();
		if(mode == MODE_EDIT){
			if(!isEmpty(TH_TITLE_CODE) && !isEmpty(TH_TITLE_DESC) && (EN_TITLE_CODE == TH_TITLE_CODE) && TITLE_OTHER!=EN_TITLE_CODE){ 
				targetDisplayHtml('EN_TITLE_DESC', MODE_VIEW, 'EN_TITLE_DESC');
			}else{
				targetDisplayHtml('EN_TITLE_DESC', MODE_EDIT, 'EN_TITLE_DESC');
			}	
		}
	}catch(exception){
		errorException(exception);
	}
}
function TH_FIRST_NAMEInitJS(element, mode, name) {
	try{
		var CID_TYPE = $("[name='CID_TYPE']").val();
		if (CID_TYPE == CIDTYPE_PASSPORT) {
			$("[name='TH_FIRST_NAME']").attr('onblur',"NameENOnblurAction('" + element + "','" + mode + "','" + name+ "')");
			$("[name='TH_FIRST_NAME']").attr('onkeypress',"ENOnKeyPressAction('" + element + "','" + mode + "','" + name+ "',event)");
		} else {
			$("[name='TH_FIRST_NAME']").attr('onblur',"THOnblurAction('" + element + "','" + mode + "','" + name+ "')");
			$("[name='TH_FIRST_NAME']").attr('onkeypress',"THOnKeyPressAction('" + element + "','" + mode + "','" + name+ "',event)");
		}
	}catch(exception){
		errorException(exception);
	}
}

function TH_MID_NAMEInitJS(element, mode, name) {
	try{
		var CID_TYPE = $("[name='CID_TYPE']").val();
		if(CID_TYPE == CIDTYPE_PASSPORT){
			$("[name='TH_MID_NAME']").attr('onblur',"NameENOnblurAction('" + element + "','" + mode + "','" + name+ "')");
			$("[name='TH_MID_NAME']").attr('onkeypress',"ENOnKeyPressAction('" + element + "','" + mode + "','" + name+ "',event)");
		}else{
			$("[name='TH_MID_NAME']").attr('onblur',"THOnblurAction('" + element + "','" + mode + "','" + name+ "')");
			$("[name='TH_MID_NAME']").attr('onkeypress',"THOnKeyPressAction('" + element + "','" + mode + "','" + name+ "',event)");
		}
	}catch(exception){
		errorException(exception);
	}
}

function TH_LAST_NAMEInitJS(element, mode, name) {
	try{
		var CID_TYPE = $("[name='CID_TYPE']").val();
		if(CID_TYPE == CIDTYPE_PASSPORT){
			$("[name='TH_LAST_NAME']").attr('onblur',"NameENOnblurAction('" + element + "','" + mode + "','" + name+ "')");
			$("[name='TH_LAST_NAME']").attr('onkeypress',"ENOnKeyPressAction('" + element + "','" + mode + "','" + name+ "',event)");
		}else{
			$("[name='TH_LAST_NAME']").attr('onblur',"THOnblurAction('" + element + "','" + mode + "','" + name+ "')");
			$("[name='TH_LAST_NAME']").attr('onkeypress',"THOnKeyPressAction('" + element + "','" + mode + "','" + name+ "',event)");
		}
	}catch(exception){
		errorException(exception);
	}
}

function CHECK_CIDTYPE_NON_THAINATINALITY(ID_NO){
	//First digit = 0 
	//console.log('CHECK_CIDTYPE_NON_THAINATINALITY'+ID_NO);
//	if($("[name='NATIONALITY']").length>0){
		var subformId = $("#PERSONAL_INFO_SUBFORM [name='subformId']").val();
		refreshSubForm(subformId,'Y');
		//listBoxFilterAction('NATIONALITY',FIELD_ID_NATIONALITY,'',"NATIONALITY",'','');
		
//		if(ID_NO[0]=='0'){
//			if(ID_NO[1]=='0'){ //Nationality will filter value list are â€œMYANMARâ€�, â€œCAMBODIAâ€� and â€œLAO PEOPLE'S DEMOCRATIC REPUBLICâ€� for manual select.
//				// call back CARD_TYPEAfterListBoxFilterActionJS method for set init CARD_LEVEL
//				listBoxFilterAction('NATIONALITY',FIELD_ID_NATIONALITY,'',NON_NATIONALITY_TYPE_LISTBOX,'','');
//			}else{ // Nationality will default is non thai nationality
//				displayHtmlElement('NATIONALITY', NON_NATIONALITY_TH);
//			}
//		}else if(ID_NO[0]=='6'){ //First digit = 6 
//			var stringTest = ID_NO[5]+ID_NO[6];
//			////digit 6-7 is 00-49,
//			var re = new RegExp(REGULAR_EXP_00_49);
//			//digit 6-7 is 50-72,
//			var re2 = new RegExp(REGULAR_EXP_50_72);
//			if(re.test(stringTest)){ //check digit 6-7 is 00-49, 
//				listBoxFilterAction('NATIONALITY',FIELD_ID_NATIONALITY,'',NON_NATIONALITY_TYPE_LISTBOX,'','');
//			}else if(re2.test(stringTest)){ //check digit 6-7 is 50-72,
//				displayHtmlElement('NATIONALITY', NON_NATIONALITY_TH);
//			}else{ // Others	
//				displayHtmlElement('NATIONALITY', NATIONALITY_TH);
//			}
//		}else{ // Others
//			displayHtmlElement('NATIONALITY', NATIONALITY_TH);
//		}
//	}
	
	
	
}

function NATIONALITYInitJS() {
	try{
		var CID_TYPE = $("[name='CID_TYPE']").val();
		var ID_NO = $("[name='ID_NO']").val();
		console.log("ID_NO ::",ID_NO);
		if(!isEmpty(CID_TYPE)){
			if (CID_TYPE == CIDTYPE_IDCARD) {
				displayHtmlElement('NATIONALITY', NATIONALITY_TH);
			}// CID_TYPE is NON thai Nationality 
			else if (CID_TYPE == CIDTYPE_NON_THAI_NATINALITY ) {
				if(!isEmpty(ID_NO)){ // has id_no
					//CHECK_CIDTYPE_NON_THAINATINALITY(ID_NO);
					//displayHtmlElement('NATIONALITY', NON_NATIONALITY_TH);
				}else // not id_no
				{
					displayHtmlElement('NATIONALITY','');
				}
			}
			else{
				var NATIONALITY = $("[name='NATIONALITY']").val();
				if (NATIONALITY == NATIONALITY_TH) {
					displayHtmlElement('NATIONALITY','');
				}
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function NATIONALITYActionJS() {
	try{
		var CID_TYPE = $("[name='CID_TYPE']").val();
		var NATIONALITY = $("[name='NATIONALITY']").val();
		if(CID_TYPE == CIDTYPE_IDCARD && NATIONALITY != NATIONALITY_TH){
			alertBox('ERROR_NATIONALITY_SELECT_ONLY_THAILAND');
		}else if((CID_TYPE == CIDTYPE_PASSPORT || CID_TYPE == CIDTYPE_MIGRANT) && NATIONALITY == NATIONALITY_TH){
			alertBox('ERROR_NATIONALITY_NOT_SELECT_THAILAND');
		}
	}catch(exception){
		errorException(exception);
	}
}
function ERROR_NATIONALITY_SELECT_ONLY_THAILANDAfterAlertActionJS(){
	try{
		focusElementActionJS('NATIONALITY', NATIONALITY_TH);
	}catch(exception){
		errorException(exception);
	}
}
function ERROR_NATIONALITY_NOT_SELECT_THAILANDAfterAlertActionJS(){
	try{
		focusElementActionJS('NATIONALITY', '');
	}catch(exception){
		errorException(exception);
	}
}

function VISA_TYPEInitJS() {
	try{
//		var CID_TYPE = $("[name='CID_TYPE']").val();
//		if(CID_TYPE != CIDTYPE_PASSPORT && CID_TYPE != CIDTYPE_MIGRANT){
//			targetDisplayHtml('VISA_TYPE', MODE_VIEW, 'VISA_TYPE');
//		}else{
//			targetDisplayHtml('VISA_TYPE', MODE_EDIT, 'VISA_TYPE');
//		}
	}catch(exception){
		errorException(exception);
	}
}

function VISA_EXPIRE_DATEInitJS() {
	try{
//		var CID_TYPE = $("[name='CID_TYPE']").val();
//		if(CID_TYPE != CIDTYPE_PASSPORT && CID_TYPE != CIDTYPE_MIGRANT) {
//			targetDisplayHtml('VISA_EXPIRE_DATE',MODE_VIEW,'VISA_EXPIRE_DATE');
//		}else{
//			targetDisplayHtml('VISA_EXPIRE_DATE',MODE_EDIT,'VISA_EXPIRE_DATE');
//		}
	}catch(exception){
		errorException(exception);
	}
}
function WORK_PERMIT_NOInitJS(element, mode, name) {
	try{
		if(mode == MODE_EDIT){
			var CID_TYPE = $("[name='CID_TYPE']").val();
			if(CID_TYPE != CIDTYPE_PASSPORT && CID_TYPE != CIDTYPE_MIGRANT){
				targetDisplayHtml('WORK_PERMIT_NO',MODE_VIEW,'WORK_PERMIT_NO');
			}else{
				targetDisplayHtml('WORK_PERMIT_NO',MODE_EDIT,'WORK_PERMIT_NO');
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function WORK_PERMIT_EXPIRE_DATEInitJS(element, mode, name) {
	try{
		var CID_TYPE = $("[name='CID_TYPE']").val();
		if(mode == MODE_EDIT){
			if(CID_TYPE != CIDTYPE_PASSPORT && CID_TYPE != CIDTYPE_MIGRANT) {
				targetDisplayHtml('WORK_PERMIT_EXPIRE_DATE',MODE_VIEW,'WORK_PERMIT_EXPIRE_DATE');
			}else{
				targetDisplayHtml('WORK_PERMIT_EXPIRE_DATE',MODE_EDIT,'WORK_PERMIT_EXPIRE_DATE');
			}
		}
	}catch(exception){
		errorException(exception);
	}
}

function IDNO_EXPIRE_DATEInitJS(){
	try{
//		var CID_TYPE = $("[name='CID_TYPE']").val();
//		if (CID_TYPE != CIDTYPE_PASSPORT && CID_TYPE != CIDTYPE_MIGRANT && !isEmpty(CID_TYPE)) {
//			targetDisplayHtml('IDNO_EXPIRE_DATE', MODE_EDIT,
//					'IDNO_EXPIRE_DATE');
//		} else {
//			targetDisplayHtml('IDNO_EXPIRE_DATE', MODE_VIEW,
//					'IDNO_EXPIRE_DATE');
//		}
	}catch(exception){
		errorException(exception);
	}
}

function PASSPORT_EXPIRE_DATEInitJS() {
	try{
//		var CID_TYPE = $("[name='CID_TYPE']").val();
//		if (CID_TYPE != CIDTYPE_PASSPORT && CID_TYPE != CIDTYPE_MIGRANT) {
//			targetDisplayHtml('PASSPORT_EXPIRE_DATE', MODE_VIEW,
//					'PASSPORT_EXPIRE_DATE');
//		} else {
//			targetDisplayHtml('PASSPORT_EXPIRE_DATE', MODE_EDIT,
//					'PASSPORT_EXPIRE_DATE');
		
//		}
		var element = 'PASSPORT_EXPIRE_DATE';
		var PASSPORT_EXPIRE_DATE = $("[name='"+element+"']").val();
		var APPLY_DATE = $("[name='APPLY_DATE_EN']").val();
		if(PASSPORT_EXPIRE_DATE){
			if(validateBetweenDateToDate(PASSPORT_EXPIRE_DATE,APPLY_DATE)){
				alertBox("ERROR_PASSPORT_EXPIRT_DATE");
				
				if($("[name='"+element+"']").attr('readonly')==undefined){
					$("[name='"+element+"']").val('');
				}

			}
		}

	}catch(exception){
		errorException(exception);
	}
}

function triggerWorkExpCheck() {
	try{
		TOT_WORK_YEARActionJS();		
		PREV_WORK_YEARActionJS();
	}catch(exception){
		errorException(exception);
	}
}
// ajas call cal age year
function getCalculateAge(birthDate){
	try {
		// If NCB subform exists use DATE_CONSENT field, otherwise use hidden CONSENT_DATE field in personal subform.
		var CONSENT_DATE = $("[name='DATE_CONSENT']").val();
		if(CONSENT_DATE) {
			var day = CONSENT_DATE.split("/")[0];
			var month = CONSENT_DATE.split("/")[1];
			var year = CONSENT_DATE.split("/")[2];
			CONSENT_DATE = day + "/" + month + "/" + (parseInt(year) - 543);
		} else {
			CONSENT_DATE = $("[name='CONSENT_DATE']").val();
		}
		console.log("birthDate   :: "+birthDate);
		console.log("consentDate :: "+CONSENT_DATE);
		if(CONSENT_DATE) {
			var className = "com.eaf.orig.ulo.app.view.util.ajax.GetCalculateAge";
			var data = {
				BIDTH_DATE:birthDate,
				CONSENT_DATE:CONSENT_DATE
			};
			ajax(className, data, getCalculateAgefterActionJS);
		}
	} catch(exception) {
		errorException(exception);
	}
}

function getCalculateAgefterActionJS(data){
	// check many 20 year
	displayJSON(data);
	var FORM_ID = $("[name='formId']").val();
	var valueProp = 0 ;
	var $JSON = $.parseJSON(data);
	var propName = 'year';
	if($.isEmptyObject($JSON)){
		return;
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
	if(valueProp < parseInt(VERIFICATION_CHECK_AGE_TWENTY)){
		displayWarnMsg(FORM_ID,ERROR_BIRDTH_DATE_LEST_TWENTY_AGE);
	
	}
	
}

function TH_BIRTH_DATEInitJS(element, mode, name) {
	
	try{
		var TH_BIRTH_DATE = $("[name='TH_BIRTH_DATE']").val();
		var CIS_NUMBER = $("[name='CIS_NUMBER']").val();
		var ID_NO = $("[name='ID_NO']").val();
		
		if (isEmpty(ID_NO)) {
			if(!isEmpty(CIS_NUMBER)) {
				displayHtmlElement('TH_BIRTH_DATE', '');
			}
			targetDisplayHtml('TH_BIRTH_DATE',MODE_EDIT,'TH_BIRTH_DATE');
		}
	}catch(exception){
		errorException(exception);
	}
}
function TH_BIRTH_DATEActionJS(element, mode, name) {
	try{
		var errorFlag = 'N';
		var TH_BIRTH_DATE = $("[name='TH_BIRTH_DATE']").val();
		var FORM_ID = $("[name='formId']").val();
		var PERSONAL_TYPE =$("[name='PERSONAL_USER_TYPE']").val();
		console.log("FORM_ID >>" ,FORM_ID);
		console.log("PERSONAL_TYPE >>" ,PERSONAL_TYPE);
		console.log("th_birth_date >>>",TH_BIRTH_DATE);
		if(!isEmpty(TH_BIRTH_DATE)){
			var day = TH_BIRTH_DATE.split("/")[0];
			var month = TH_BIRTH_DATE.split("/")[1];
			var year = TH_BIRTH_DATE.split("/")[2];
			var EN_BIRTH_DATE = day + "/" + month + "/" + (parseInt(year) - 543);
			 
			
			if (!validateCurrentDateToDate(TH_BIRTH_DATE, LOCAL_TH)) {
				errorFlag = 'Y';
				alertBox('ERROR_TH_BIRTH_DATE_MORETHAN_CURRENTDATE');
			}
			else {
				$("[name='EN_BIRTH_DATE']").val(EN_BIRTH_DATE);
				triggerWorkExpCheck();
				//check rule check CalCulateAge 
				// role IA
				if(FORM_ID == 'IA_PERSONAL_APPLICANT_FORM'){
					if(PERSONAL_TYPE == PERSONAL_TYPE_APPLICANT){
						getCalculateAge(EN_BIRTH_DATE);
					}
				}
				else if (FORM_ID =='IA_INCREASE_APPLICATION_FORM'){
					getCalculateAge(EN_BIRTH_DATE);
				}else if (FORM_ID == 'NORMAL_APPLICATION_FORM'){
					getCalculateAge(EN_BIRTH_DATE);
				}
			}
		}else{
			$("[name='EN_BIRTH_DATE']").val('');
		}
		var CID_TYPE = $("[name='CID_TYPE']").val();
		var TH_FIRST_NAME = $("[name='TH_FIRST_NAME']").val();
		var TH_LAST_NAME = $("[name='TH_LAST_NAME']").val();
		var TH_BIRTH_DATE = $("[name='TH_BIRTH_DATE']").val();
		if ((CID_TYPE == CIDTYPE_PASSPORT || CID_TYPE == CIDTYPE_MIGRANT) && errorFlag == 'N') {
			if(!isEmpty(TH_FIRST_NAME) && !isEmpty(TH_LAST_NAME) && !isEmpty(TH_BIRTH_DATE)){
				getCisPersonalInfoData('TH_BIRTH_DATE',mode);
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function ERROR_TH_BIRTH_DATE_MORETHAN_CURRENTDATEAfterAlertActionJS() {
	try{
		$("[name='EN_BIRTH_DATE']").val('');
		focusElementActionJS('TH_BIRTH_DATE', '');
	}catch(exception){
		errorException(exception);
	}
}

function EN_BIRTH_DATEInitJS() {
	try{
		var EN_BIRTH_DATE = $("[name='EN_BIRTH_DATE']").val();
		var CIS_NUMBER = $("[name='CIS_NUMBER']").val();
		var ID_NO = $("[name='ID_NO']").val();
		if (isEmpty(ID_NO)) {
			if(!isEmpty(CIS_NUMBER)) {
				displayHtmlElement('EN_BIRTH_DATE', '');
			}
			targetDisplayHtml('EN_BIRTH_DATE',MODE_EDIT);
		}
		if(!isEmpty(EN_BIRTH_DATE)){
			getCalculateAge(EN_BIRTH_DATE);
		}
	}catch(exception){
		errorException(exception);
	}
}
function EN_BIRTH_DATEActionJS(element, mode, name) {
	try{
		var errorFlag = 'N';
		var EN_BIRTH_DATE = $("[name='EN_BIRTH_DATE']").val();
		if(!isEmpty(EN_BIRTH_DATE)){
			var day = EN_BIRTH_DATE.split("/")[0];
			var month = EN_BIRTH_DATE.split("/")[1];
			var year = EN_BIRTH_DATE.split("/")[2];
			var TH_BIRTH_DATE = day + "/" + month + "/" + (parseInt(year) + 543);
			if(!validateCurrentDateToDate(EN_BIRTH_DATE, LOCAL_EN)){
				errorFlag = 'Y';
				alertBox('ERROR_EN_BIRTH_DATE_MORETHAN_CURRENTDATE');
			}else{
				$("[name='TH_BIRTH_DATE']").val(TH_BIRTH_DATE);
			}
		}else{
			$("[name='TH_BIRTH_DATE']").val("");
		}
		var CID_TYPE = $("[name='CID_TYPE']").val();
		var TH_FIRST_NAME = $("[name='TH_FIRST_NAME']").val();
		var TH_LAST_NAME = $("[name='TH_LAST_NAME']").val();
		var TH_BIRTH_DATE = $("[name='TH_BIRTH_DATE']").val();
		if ((CID_TYPE == CIDTYPE_PASSPORT || CID_TYPE == CIDTYPE_MIGRANT) && errorFlag == 'N') {
			if(!isEmpty(TH_FIRST_NAME) && !isEmpty(TH_LAST_NAME) && !isEmpty(TH_BIRTH_DATE)){
				getCisPersonalInfoData('EN_BIRTH_DATE',mode);
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function ERROR_EN_BIRTH_DATE_MORETHAN_CURRENTDATEAfterAlertActionJS() {
	try{
		$("[name='TH_BIRTH_DATE']").val('');
		focusElementActionJS('EN_BIRTH_DATE', '');
	}catch(exception){
		errorException(exception);
	}
}

function RELATION_WITH_APPLICANTInitJS() {

}
function RELATION_WITH_APPLICANTActionJS() {
	try{
		RELATION_WITH_APPLICANT_OTHERInitJS();
	}catch(exception){
		errorException(exception);
	}
}
function RELATION_WITH_APPLICANT_OTHERInitJS() {
	try{
		var RELATION_WITH_APPLICANT = $("[name='RELATION_WITH_APPLICANT']").val();
		if (RELATION_WITH_APPLICANT == RELATION_WITH_APPLICANT_OTHER) {
			targetDisplayHtml('RELATION_WITH_APPLICANT_OTHER',MODE_EDIT,'RELATION_WITH_APPLICANT_OTHER');
		}else{
			targetDisplayHtml('RELATION_WITH_APPLICANT_OTHER',MODE_VIEW,'RELATION_WITH_APPLICANT_OTHER');
		}
	}catch(exception){
		errorException(exception);
	}
}
function TH_FIRST_NAMEActionJS(element, mode, name) {
	try{
		var CID_TYPE = $("[name='CID_TYPE']").val();
		var TH_FIRST_NAME = $("[name='TH_FIRST_NAME']").val();
		var TH_LAST_NAME = $("[name='TH_LAST_NAME']").val();
		var TH_BIRTH_DATE = $("[name='TH_BIRTH_DATE']").val();
		if(CID_TYPE == CIDTYPE_PASSPORT || CID_TYPE == CIDTYPE_MIGRANT){
			if(!isEmpty(TH_FIRST_NAME) && !isEmpty(TH_LAST_NAME) && !isEmpty(TH_BIRTH_DATE)){
				getCisPersonalInfoData('TH_FIRST_NAME',mode);
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function TH_LAST_NAMEActionJS(element, mode, name) {
	try{
		var CID_TYPE = $("[name='CID_TYPE']").val();
		var TH_FIRST_NAME = $("[name='TH_FIRST_NAME']").val();
		var TH_LAST_NAME = $("[name='TH_LAST_NAME']").val();
		var TH_BIRTH_DATE = $("[name='TH_BIRTH_DATE']").val();
		if(CID_TYPE == CIDTYPE_PASSPORT || CID_TYPE == CIDTYPE_MIGRANT){
			if(!isEmpty(TH_FIRST_NAME) && !isEmpty(TH_LAST_NAME) && !isEmpty(TH_BIRTH_DATE)){
				getCisPersonalInfoData('TH_LAST_NAME',mode);
			}
		}
	}catch(exception){
		errorException(exception);
	}
}

function OCCUPATIONActionJS() {
	try{
		var OCCUPATION = $("#PERSONAL_INFO_SUBFORM [name='OCCUPATION']").val();
		var PERSONAL_SEQ = $("#PERSONAL_INFO_SUBFORM [name='PERSONAL_SEQ']").val();
		var $data = '&OCCUPATION=' + OCCUPATION;
			$data += '&PERSONAL_SEQ=' + PERSONAL_SEQ;
		loadPopupDialog('POPUP_OCCUPATION_PERSONAL_FORM', 'INSERT', '0', $data,'','');
	}catch(exception){
		errorException(exception);
	}
}
function BUSINESS_TYPEActionJS() {
	try{
		var BUSINESS_TYPE = $("#PERSONAL_INFO_SUBFORM [name='BUSINESS_TYPE']").val();
		var PERSONAL_SEQ = $("#PERSONAL_INFO_SUBFORM [name='PERSONAL_SEQ']").val();
		var $data = '&BUSINESS_TYPE=' + BUSINESS_TYPE;
			$data += '&PERSONAL_SEQ=' + PERSONAL_SEQ;
		loadPopupDialog('POPUP_BUSINESS_TYPE_FORM', 'INSERT', '0', $data,'','');
	}catch(exception){
		errorException(exception);
	}
}
function PROFESSIONActionJS() {
	try{
		var PROFESSION = $("#PERSONAL_INFO_SUBFORM [name='PROFESSION']").val();
		var PERSONAL_SEQ = $("#PERSONAL_INFO_SUBFORM [name='PERSONAL_SEQ']").val();
		var $data = '&PROFESSION_TYPE=' + PROFESSION;
			$data += '&PERSONAL_SEQ=' + PERSONAL_SEQ;
		loadPopupDialog('POPUP_PROFESSION_TYPE_FORM', 'INSERT', '0', $data,'','');
	}catch(exception){
		errorException(exception);
	}
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

function getCisPersonalInfoData(elementId,mode){
	try{
	
		// clear 
		
		Pace.block();
		var className = 'com.eaf.orig.ulo.app.view.util.dih.CustomerInformation';
		var $data  = 'className='+className;
			$data += '&'+$('#appFormName').serialize();
			$data += '&elementFieldId='+elementId;
			var url = CONTEXT_PATH+'/Ajax';
			$.post(url,$data,function(data,status,xhr){
				try{
					if(responseSuccess(data,status,xhr)){
						var responseData = getResponseData(data);
						afterCisPersonalInfoDataActionJS(responseData,status,xhr,elementId,name);
					}else{
						displayHtmlElement('CIS_NUMBER','');
					}
				}catch(exception){
					errorException(exception);
				}
			}).fail(function(data,status,xhr){
				errorAjax(data,status,xhr);
			});
	}catch(exception){
		errorException(exception);
	}
}

function afterCisPersonalInfoDataActionJS(data){
	try{
		var cisCustomerResult = $.parseJSON(data);
		if(cisCustomerResult.refreshFormFlag == 'Y'){
			refreshCurrentForm();
		}else{
			Pace.unblockFlag = true;
			Pace.unblock();
		}
	}catch(exception){
		errorException(exception);
	}
}

function PASSPORT_EXPIRE_DATEActionJS(element, mode, name){
	var PASSPORT_EXPIRE_DATE = $("[name='"+element+"']").val();
	var APPLY_DATE = $("[name='APPLY_DATE_EN']").val();
	console.log("APPLY_DATE:"+APPLY_DATE);
	console.log("PASSPORT_EXPIRE_DATE:"+PASSPORT_EXPIRE_DATE);
	if(PASSPORT_EXPIRE_DATE){
		if(validateBetweenDateToDate(PASSPORT_EXPIRE_DATE,APPLY_DATE)){
			alertBox("ERROR_PASSPORT_EXPIRT_DATE",focusElementActionJS,element);
		}
		//validateBetweenDateToDate(PASSPORT_EXPIRE_DATE)
//		if(validateCurrentDateToDate(PASSPORT_EXPIRE_DATE,LOCAL_EN)){
//			alertBox("ERROR_PASSPORT_EXPIRT_DATE",focusElementActionJS,element);
//		}
	}
}

function ISSUE_DOCUMENT_DATEActionJS(element, mode, name){
	var ISSUE_DOCUMENT_DATE = $("[name='"+element+"']").val();
	var BIRTH_DATE = $("[name='EN_BIRTH_DATE']").val();
	if(ISSUE_DOCUMENT_DATE){
		if(!validateCurrentDate(ISSUE_DOCUMENT_DATE,LOCAL_EN)){
			alertBox("ERROR_ISSUE_DOCUMENT_DATE",focusElementActionJS,element);
		}else if(validateBetweenDate(ISSUE_DOCUMENT_DATE,BIRTH_DATE)){
			alertBox("ERROR_ISSUE_DOCUMENT_BIRTHDATE",focusElementActionJS,element);
		}
	}
}

function VISA_TYPEActionJS(element, mode, name){
	var subformId = $("#PERSONAL_INFO_SUBFORM [name='subformId']").val();
	refreshSubForm(subformId,'Y');
}
function NORMAL_APPLICATION_FORMMandatoryActionJS(){
	
}


//function defaultCursor(){
//	var select = $("select[name=CID_TYPE]")[0].selectize;
//	select.open();
//	setTimeout(function(){
//		select.close();
//	},5);
//}
