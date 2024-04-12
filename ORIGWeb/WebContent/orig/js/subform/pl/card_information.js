/**
 * #Pipe
 */
$(document).ready(function(){
	var cardinfoBuss = $('#cardinfo_bussclass').val();
	if(INCREASE_BUSSCLASS==cardinfoBuss || DECREASE_BUSSCLASS==cardinfoBuss){
		$('#card_info_card_id').removeClass('textboxReadOnly');
	}
	addEventCardTypeOnchange();	
	processBeginingCardFace();
});

function processBeginingCardFace(){
	var saveCardFace = $('#card_info_card_face').val();
	var applicantType = $('input[name=applicant_radio]:checked').val();
	if(applicantType != null && applicantType != ""){
		processCardFace();
		if(saveCardFace!= null && saveCardFace != ""){
			$('#card_info_card_face').val(saveCardFace);
		}
	}
}

function addEventCardTypeOnchange(){
	$('#card_info_card_type').change(function(){
		processCardFace();
	});
}

function processCardFace() {
	var cardType = $('#card_info_card_type').val();
	var cardFace = $('#card_info_card_face').val();
	var displayMode = $('#occ_displayMode').val();
	var applicantType = $('input[name=applicant_radio]:checked').val();
	var dataString = "className=DisplayCardFace&packAge="+packageOrigPl+"&returnType=0&cardType="+cardType+"&cardFace="+cardFace+"&displayMode="+displayMode+"&applicantType="+applicantType;	
	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){
			displayJsonObjectElementById(data);
		},
		error : function(response){
		}
	});	
}

function processCardFaceByEvent(obj){
	var cardType = $('#card_info_card_type').val();
	var cardFace = $('#card_info_card_face').val();
	var displayMode = $('#occ_displayMode').val();
	var applicantType = $(obj).val();
	var dataString = "className=DisplayCardFace&packAge="+packageOrigPl+"&returnType=0&cardType="+cardType+"&cardFace="+cardFace+"&displayMode="+displayMode+"&applicantType="+applicantType;	
	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){
			displayJsonObjectElementById(data);
		},
		error : function(response){
		}
	});	
}

function validateICDCCardNo(){
	try{
		DestoryErrorField();
		openblockscreen = false;
		var cardno = $('#card_info_card_no').val();
		if(cardno != ""){
			if(!openblockscreen){
				blockScreen();
			}
			var dataString = 'action=ValidateICDCCardNo&handleForm=N&'+$("#card_icdc_div *").serialize();		
			$('#action').val('ValidateICDCCardNo');
			$('#handleForm').val('N');
			$('#currentTab').val('MAIN_TAB');
			$.post('FrontController',dataString,function(data,status,xhr){		
				validateICDCCardNoMessage(data);
		    }).error(function(){
			   unblockScreen();
			   PushErrorNotifications('Network or Connection Error, please try again');
		    });		
		}else{
			unblockScreen();
			$('#card_no_icdc_hidden').val('');
		}
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
function validateICDCCardNoMessage(data){
	try{
		var idno = $('#identification_no').val();
		$.map(data, function(item){
			if(item.value == 'Fail'){
				unblockScreen();
				$('#card_info_card_no').val($('#card_no_icdc_hidden').val());
				alertMassageSelection(NOT_FOUND_CARD_NO,'card_info_card_no');
			}else if(item.value == 'Success'){
				if(idno == null || ''==idno){
					refreshICDCData();
				}else{
					unblockScreen();
					var message  = CONFIRM_CHANGE_CARD_NO_ICDC+$('#card_info_card_no').val();
					alertMassageYesNoFunc(message,refreshICDCData,rollbackCardNo);
				}
			}
		});
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
function refreshICDCData(){
	try{
		if(!openblockscreen){
			blockScreen();
		}	
		$('#card_no_icdc_hidden').val($('#card_info_card_no').val());
		$('#action').val('RefreshICDCData');
		$('#handleForm').val('N');
		$('#currentTab').val('MAIN_TAB');
		$('#formID').val('KEC_ICDC_FORM');
		var dataString = $('#avale-obj-form *').serialize();
		$.post('FrontController',dataString,function(data,status,xhr){
			 jsonDisplayElementById(data);
			 setDefaultPaymentMethod();
	//		 if($('#projectcode_buttonmode').val() == DISPLAY_MODE_EDIT
	//				&& $('#project_code').val() == ''){
	//			ResetProjectCode();
	//		 }
			 if($('#project_code').val() != ''){
				 createCGNSelectBox();
			 }
			 DisplayMatrixHTML();
			 ActiveStatusCardNo();		 
		}).error(function(){		
			unblockScreen();
			PushErrorNotifications('Network or Connection Error, please try again');
		});
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
function ActiveStatusCardNo(){
	try{
		var dataString = 'className=com.eaf.orig.ulo.pl.app.servlet.ActiveStatusCardNo&returnType=1&'+$("#card_icdc_div *").serialize();;	
		$.ajax({
			type :"POST",
			url :"AjaxDisplayServlet",
			data :dataString,
			async :false,
			success : function(data ,status ,xhr){
				switch(data){
					case 'ACTIVE':
						doProcessICDCCard();
						break;
					case 'NOTACTIVE':
						unblockScreen();
						var message  = MSG_CARD_NOT_ACTIVE_ICDC;
						alertMassageYesNoFunc(message,doProcessICDCCard,ClearICDCCardNo);
						break;
					default:
						unblockScreen();
						break;
				}
			},
			error : function(response){
				unblockScreen();
				PushErrorNotifications('Network or Connection Error, please try again');
			}
		});
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
function doProcessICDCCard(){
	try{
		if(!openblockscreen){
			blockScreen();
		}
		IdNoTool();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
function ClearICDCCardNo(){
	$('#card_info_card_no').val('');
	$('#card_no_icdc_hidden').val('');
	$('#identification_no').unbind();
	$('#identification_no').val('');
	$('#identification_no').removeClass('textbox-readonly');
	$('#identification_no').attr('readOnly', false);
	$('#identification_no').addClass('textbox');
	$('#identification_no').blur(function(){
		IdNoTool();
	});
}
function rollbackCardNo(){
	$('#card_info_card_no').val($('#card_no_icdc_hidden').val());
} 
