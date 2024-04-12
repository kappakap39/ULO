/**
 * @author SeptemWi
 */
$(document).ready(function(){
	var main_displaymode = $('#main_displaymode').val();
	/**Get Age*/
	if(!"Y"==REFRESH_ICDC){
		GetAge();	
	}
	
	var temp = $('#loan_objective').val();
	if (temp != 10) {
		$('#other_loan_objective').attr("readOnly", "readOnly");
	}
	
	var currentRole = $('#current-role').val();
//	#septem comment
//	if(currentRole != ROLE_DE && currentRole != ROLE_DE_SUP){
//		$('#workplace-role').remove();
//	}
	if(currentRole == DF_REJECT){
		$('#reset-idno').remove();
	}

	$('#loan_objective').focusout(function(){
		var loanObjVal = $(this).val();
		if (loanObjVal == 10){									
			$('#other_loan_objective').removeAttr("readOnly");
			$('#other_loan_objective').focus();
		} else {
			$('#other_loan_objective').val('');
			$('#other_loan_objective').attr("readOnly","readOnly");
		}
	});

	/** #SeptemWi IDNO LOGIC */
	$('#identification_no').blur(function() {
		IdNoTool();
	});
	IdNoDisplay();
	$('#reset-idno').click(function() {
		ComfirmChangeIdNo();
	});

	if ($('#main_nationality').val() == '' && main_displaymode != 'VIEW') {
		$('#nationality').val('TH');
	}
	
	/**#SeptemWi Popup [...] Logic*/
		PopUpTextBoxDescFieldIDEngine('#titleThai');			
		PopUpTextBoxDescFieldIDEngine('#titleEng');	
		
//		#septemwi comment
//		if(currentRole == ROLE_DE || currentRole == ROLE_DE_SUP){
//			PopUpTextBoxDescFieldIDEngine('#occ_workplaceTitle2');
//		}
	/**End #SeptemWi*/
	
	//validate year >= 1900  #Vikrom  20121120
	$('#birth_date').blur(function(){
		var year = $('#birth_date').val().substr(6,10);
		if(year < 1900 && year.length!=0){
			alertMassage(FORMAT_DATE_ERROR);
			$('#birth_date').val('');
			$('#birth_date').focus();
			$('#birth_date').setCursorToTextEnd();
			$('#element_age').html('');
		}
	});
	//validate year >= 2400
	$('#card_expire_date').blur(function(){
		var year = $('#card_expire_date').val().substr(6,10);
		if(year < 2400 && year.length!=0){
			alertMassage(EXP_DATE_ERROR_MSG);
			$('#card_expire_date').val('');
			$('#card_expire_date').focus();
			$('#card_expire_date').setCursorToTextEnd();
		}
		
//		#septemwi change logic validate with application_date
//		var expireDay =  parseInt($('#card_expire_date').val().substr(0,2),10);
//		var expireMonth =  parseInt($('#card_expire_date').val().substr(3,2),10);
//		var expireYear = parseInt($('#card_expire_date').val().substr(6,4))-543;
//		var dtExpire = new Date(expireYear, expireMonth-1, expireDay);
//		//alert("Expire="+ dtExpire +"  now ="+new Date());
//		if(dtExpire<new Date()){
//			alertMassage(APPLICANT_CARD_EXPIRED);
//			$('#card_expire_date').val('');
//			$('#card_expire_date').focus();
//			$('#card_expire_date').setCursorToTextEnd();
//		}
		
		var dataString = "className=com.eaf.orig.ulo.pl.app.servlet.CardExpire&returnType=1&card_expire_date="+$('#card_expire_date').val();	
		$.ajax({
			type :"POST",
			url :"AjaxDisplayServlet",
			data :dataString,
			async :false,
			success : function(data ,status ,xhr){
				if(data == 'ERROR'){
					alertMassage(APPLICANT_CARD_EXPIRED);
					$('#card_expire_date').val('');
					$('#card_expire_date').focus();
					$('#card_expire_date').setCursorToTextEnd();
				}
			},
			error : function(data){
				
			}
		});
		
	});
	
	$("#customertype").change(function() {
		ComfirmChangeCustomerType();
		if ($("#customertype").val() == "01") {
			$("#identification_no").attr("maxLength", "13");
		}else{
			$("#identification_no").attr("maxLength", "20");
		}
	});
	if("Y"==REFRESH_ICDC){
		 IdNoTool();
	}
});

function IdNoDisplay() {
	var idNO = $('#identification_no').val();
	if (idNO != '') {
		$('#identification_no').removeClass('textbox');
		$('#identification_no').addClass('textbox-readonly');
		$('#identification_no').attr('readOnly', true);
		$('#identification_no').unbind();
	}
	var role = $('#current-role').val();
	if(!LogicResetIDNO(role)){
		unbindIDNO();
	}
	var displayMode = $('#main_displaymode').val();
	if (displayMode == DISPLAY_MODE_VIEW) {
		unbindIDNO();
	}
}
function unbindIDNO(){
	$('#identification_no').removeClass('textbox');
	$('#identification_no').addClass('textbox-readonly');
	$('#identification_no').attr('readOnly', true);
	$('#identification_no').unbind();
	$('#reset-idno').unbind();
	$('#reset-idno').attr("disabled","disabled");
}
function LogicResetIDNO(role){
	var reset = true;
	if (role == ROLE_FU || role == ROLE_FU_SUP 
			|| role == ROLE_CA || role == ROLE_CA_SUP
				|| role == ROLE_VC || role == ROLE_VC_SUP) {
		reset = false;
	}
	return reset;
}
function IdNoTool(){
//	#SeptemWi Modify cardtype <> 04,08 Validate Idno
	if($("#cardtype").val() != "04" && $("#cardtype").val() != "08"){
		var result = ValidateIdNo('identification_no');
		if(result){
			var main_bussclass = $(	'#main_bussclass').val();
			if(main_bussclass == INCREASE_BUSSCLASS || main_bussclass == DECREASE_BUSSCLASS){
				processCIS();
			}else{
				$('#identification_no').removeClass('textbox');
				$('#identification_no').addClass('textbox-readonly');
				$('#identification_no').attr('readOnly', true);
				ExecuteManualService('button_7001', DEPLICATE_APPLICATION);
				$('#identification_no').unbind();
			}
		}else{
			unblockScreen();
		}
	}else{
		unblockScreen();
	}
}
function processCIS(){
	try{
		DestoryErrorField();
		if(!openblockscreen){
			blockScreen();
		}				
		var idno = $('#identification_no').val();
		var card_no = $('#card_info_card_no').val();
		if(card_no != null && card_no !=''){
			$('#action').val('ValidateIDWithCard');
			$('#handleForm').val('N');
			$('#currentTab').val('MAIN_TAB');
			var dataString = "action=ValidateIDWithCard&handleForm=N&idNo="+idno+"&cardNo="+card_no;
			$.post('FrontController',dataString,function(data,status,xhr){	
				try{
					if(data == 'P'){
						icdcHookEAI();
					}else{
						unblockScreen();
						alertMassageFocus(ID_DIFF_CARD,'identification_no');
						$('#identification_no').val('');
					}
				}catch(e){
					unblockScreen();
					alertMassageFocus(ID_DIFF_CARD,'identification_no');
					$('#identification_no').val('');
				}
		    }).error(function(){
		    	unblockScreen();
				alertMassageFocus(ID_DIFF_CARD,'identification_no');
				$('#identification_no').val('');
		    });	
		}else{
	//		#septemwi comment change to call ILOG Validate Duplicate Application
	//		unblockScreen();
	//		alertMassageFocus(ID_DIFF_CARD,'identification_no');
	//		$('#identification_no').val('');
			$('#identification_no').removeClass('textbox');
			$('#identification_no').addClass('textbox-readonly');
			$('#identification_no').attr('readOnly', true);
			ExecuteManualService('button_7001', DEPLICATE_APPLICATION);
			$('#identification_no').unbind();
		}
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
/** #SeptemWi For Clear IdNo */
function ComfirmChangeIdNo() {
	alertMassageYesNoFunc(CONFIRM_CLEAR_IDNO, ChangeIdNo, null);
}

function ChangeIdNo() {
	var dataString = "className=ClearDataInformation&packAge="+packageOrigPl+"&returnType=0";	
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){
			$('#identification_no').removeClass('textbox-readonly');
			$('#identification_no').attr('readOnly', false);
			$('#identification_no').addClass('textbox');
			$('Tr.addressResult').remove();
			$('#noRecordAddress').remove();
			displayJsonObjectElementById(data);			
			$('#identification_no').val('');
			$('#identification_no').blur(function() {
				IdNoTool();
			});
			$('#verification').html('Loading...');
			DisplayMatrix();
			$('#identification_no').focus();
		},
		error : function(response){
		}
	});	
}
function LoadTitlePopup(scriptAction, field1, field2) {
	var fieldVal = $('#' + field1).val();
	var popupLabel = getPopupTitle(scriptAction);
	var url = "/ORIGWeb/FrontController?action=" + scriptAction + "&" + field1
			+ "=" + encodeURI(fieldVal) + "&textboxCode=" + field2
			+ "&textboxDesc=" + field1;
	$dialog = $dialogEmpty;
	var width = 1000;
	var higth = $(window).height() - 50;
	var title = popupLabel;
	$(".plan-dialog").remove();
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	$dialog.dialog({
		resizable : false,
		modal : true,
		autoOpen : false,
		draggable : true,
		width : width,
		height : higth,
		title : title,
		position : {
			my : 'center',
			at : 'center',
			of : $(document.body)
		},
		close : function() {
			closeDialog();
		}
	});
	$dialog.dialog("open");
}

function GetAge() {
	ajaxDisplayElementJsonAsync('GetAge', '0', "birth_date="+$("#birth_date").val());
}

function checkOtherSelect(loanChoice, otherBox) {
	var value = $('#' + loanChoice).val();
	var otherValue = $('#' + otherBox).val();
	if (value == '10' && otherValue.length == 0) {
		alertMassageFocus(LOAN_INPUT, otherBox);
	}
}

function activeDesc(mainfield, relatedfield) {
	var field1 = $('#' + mainfield).val();
	var field2 = $('#' + relatedfield).val();
	if (field1 == '10' && field2.length == 0) {
		$('#' + relatedfield).removeAttr("readOnly");
		$('#' + relatedfield).focus();
	} else {
		$('#' + relatedfield).val("");
		$('#' + relatedfield).attr("readOnly", true);
	}

}

function disableOnload(field) {
	var disablefield = document.getElementById(field);
	$(disablefield).attr("readonly", true);
}

function validateIdWithCard(field){
	var idNo = document.getElementById(field);
	var card_info_card_no = $('#card_info_card_no').val();
	var result = true;
	if(card_info_card_no != null && card_info_card_no !=''){
		$('#action').val('ValidateIDWithCard');
		$('#handleForm').val('N');
		$('#currentTab').val('MAIN_TAB');
		var dataString = "action=ValidateIDWithCard&handleForm=N&idNo="+idNo.value+"&cardNo="+card_info_card_no;
		$.post('FrontController',dataString,function(data,status,xhr){	
			try{
				if(data != 'P'){
					result = false;
				}
			}catch(e){
				result = false;
			}
	    }).error(function(){
	    	result = false;
	    });	
	}else{
		result = false;
	}
	return result;
}

function icdcHookEAI(){
	try{
		var card_info_card_no = $('#card_info_card_no').val();
		var identification_no = $('#identification_no').val();
		var cardtype = $('#cardtype').val();
		if (identification_no != '' && identification_no.length != 0){		
			if(card_info_card_no != null && identification_no != null){	
				var dataString  = "className=com.eaf.orig.ulo.pl.ajax.ValidateEAIAccount&returnType=0";
					dataString += "&identification_no="+identification_no+"&cardtype="+cardtype;
				$.ajax({
					type :"POST",
					url :"AjaxDisplayServlet",
					data :dataString,
					async :false,	
					dataType: "json",
					success : function(response ,status ,xhr){
						try{
							var found = false;
							var transactionId = "";
							$.map(response, function(item){
								if(item.id == 'ERROR'){
									if(item.value != null && item.value != ''){
										PushNotifications(item.value);
										OpenNotification();
									}
								}else if(item.id=='element_cisNo'){
									htmlDisplayElementById(item.value,'result_1038');
								}else if(item.id=='validateResult'){
									if("FOUND"==item.value){
										found = true;
									}
									htmlDisplayElementById(item.value,item.id);
								}else if(item.id=="transactionId"){
							    	transactionId = item.value;
							    }
							});
							if(found){
								ajaxDisplayElementJsonValueWithError("GetEAIidNoData","5", "transactionId="+transactionId);// CIS1011I01 Personal Info
								ajaxDisplayEaiAddressJsonWithError("GetEAIaddressData","5", "transactionId="+transactionId);// CIS1015I01 Address
							}			
							$('#identification_no').removeClass('textbox');
							$('#identification_no').addClass('textbox-readonly');
							$('#identification_no').attr('readOnly', true);
							ExecuteManualService('button_7001', DEPLICATE_APPLICATION);
							$('#identification_no').unbind();
						}catch(e){
							unblockScreen();
						}
					},
					error : function(response){
						unblockScreen();
						PushErrorNotifications('Network or Connection Error, please try again');
					}
				});
			}else{
				unblockScreen();
			}
		}else{
			unblockScreen();
		}	
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}

/**
 * Praisan K.
 * Ajax Display EAI Address and notification error
 */
function ajaxDisplayEaiAddressJsonWithError(className,packAge,data){
	var dataString = "className="+className+"&packAge="+packAge+"&returnType=0";
	if(data!= null && data.length >0) dataString = dataString+"&"+data;
	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(response ,status ,xhr){
			jsonDisplayEaiAddressWithError(response);			
		},
		error : function(response){
		}
	});
}

/**
 * Praisan K.
 * 
 * Display EAI address and notification error
 */
function jsonDisplayEaiAddressWithError(data){
	var error = false;
	if(data == null || data.length < 0) return;
	$.map(data, function(item){
		if(item.id == 'ERROR'){
			if(item.value != null && item.value != ''){
				PushNotifications(item.value);
				OpenNotification();
			}
			error = true;
		}
	});
	if(!error){
		if(data != null && data.length >0){
			$.map(data, function(item){
				if(item.id == 'addressResult'){
					$(".addressResult").remove();
					$('#noRecordAddress').remove();
					jsonDisplayElementById(data);
					SetAddressFunction();
					SetAddressTextDesc();
					GetMailingAddress();
				}
			});			
		}
	}
}

/**
 * Praisan K.
 * 
 * Remove all address record and append item
 */
function displayJsonObjectEaiAddress(data){
	if(data == null || data.length < 0) return;
	$.map(data, function(item){
		var elementID = document.getElementById(item.id);
		if(elementID != null){
			$("[id$="+item.id+"]").each(function () {
				var rowHeader = $('#headerAddress');
				$(elementID).html('');
				$(elementID).append(rowHeader);
				$(elementID).append(item.value);
				GetMailingAddress();
			});
		}
	});
}

/** For Clear Card Type */
function ComfirmChangeCustomerType() {
	alertMassageYesNoFunc(CONFIRM_CLEAR_CUS_TYPE, ChangeCustomerType, NotConfirmChangeCustomerType);
}
function ChangeCustomerType() {
	var dataString = "className=ClearDataInformation&packAge="+packageOrigPl+"&returnType=0";	
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){
			$('#identification_no').removeClass('textbox-readonly');
			$('#identification_no').attr('readOnly', false);
			$('#identification_no').addClass('textbox');
			$('Tr.addressResult').remove();
			$('#noRecordAddress').remove();
			displayJsonObjectElementById(data);			
			$('#identification_no').val('');
			$('#identification_no').blur(function() {
				IdNoTool();
			});
			$('#verification').html('Loading...');
			DisplayMatrix();
			$('#identification_no').focus();
			$('#customertypeTmp').val($('#customertype').val());
		},
		error : function(response){
		}
	});
}
function NotConfirmChangeCustomerType(){
	$('#customertype').val($('#customertypeTmp').val());
}
/**#SeptemWi*/
function changeCidType(){
	$("#identification_no").focus();
	$('#identification_no').setCursorToTextEnd();
}

