/**
 * @Pipe
 */

$(document).ready(function(){	
	var role = $('#cashDay1_role').val();
	if(role != null && role == ROLE_CA){
		$('#cash_day1_bank_transfer').attr("readonly",true);
		$('#cash_day1_account_no').attr("readonly",true);
	}	
	$('#cash_day1_percent').change(function(){
		CashDayOnePercent();
	});	
	CashDayOneAccountNo();	
	DisplayCashDayOne();
	CASH_DAY_1_PERCENT_DROP_DOWN = $('#td_cash_day1_percent').html(); //Store drop down of cash day 1 percent for switch read only
	CashDayOneFlag();
});
function CashDayOneFlag(){
	var selectReadOnly = '<select name="cash_day1_percent" class="combobox sensitive comboboxReadOnly" id="cash_day1_percent"><option value="">'+PLEASE_SELECT+'</option>';
	if($('#cashdayone_displaymode').val() != 'VIEW'){
		if($('#cash_day1_day1Flag').val() != CASH_DAY_1 && $('#cash_day1_day1Flag').val() != CASH_DAY_5){
			var percent = $('#cash_day1_percent').val();
			$('#td_cash_day1_percent').html(selectReadOnly);
			$('#cash_day1_percent').val(percent);
			readOnlyCashDay1Percent();
		}else{
//			#septem set old value cash dayone
			var percent = $('#cash_day1_percent').val();
			$('#td_cash_day1_percent').html(CASH_DAY_1_PERCENT_DROP_DOWN);
			$('#cash_day1_percent').val(percent);
			$('#cash_day1_percent').change(function(){
				CashDayOnePercent();
			});
		}
		CalculateFirstTranfer();
	}
}
function readOnlyCashDay1Percent(){			
	$('#cash_day1_first_tranfer').attr("readOnly",true);
	$('#cash_day1_first_tranfer').removeClass("textboxReadOnly");
	$('#cash_day1_first_tranfer').removeClass("textbox-currency");
	$('#cash_day1_first_tranfer').addClass("textbox-currency-view");
	$('#cash_day1_first_tranfer').unbind();
}
function DisplayCashDayOne(){
	if($('#cashdayone_displaymode').val() == 'VIEW'){
		$('#cash_day1_first_tranfer').attr("readOnly",true);
		$('#cash_day1_first_tranfer').removeClass("textboxReadOnly");
		$('#cash_day1_first_tranfer').removeClass("textbox-currency");
		$('#cash_day1_first_tranfer').addClass("textbox-currency-view");
		$('#cash_day1_first_tranfer').unbind();
	}else{
		if('0' == $('#cash_day1_percent').val()){
			$('#cash_day1_first_tranfer').attr("readOnly",false);		
			$('#cash_day1_first_tranfer').removeClass("textboxReadOnly");
			$('#cash_day1_first_tranfer').removeClass("textbox-currency-view");
			$('#cash_day1_first_tranfer').addClass("textbox-currency");
			$('#cash_day1_first_tranfer').unbind();
			TextBoxCurrencyEngine('#cash_day1_first_tranfer');
			$('#cash_day1_first_tranfer').blur(function(){
				var tranfer = Number(removeCommas($("#cash_day1_first_tranfer").val()));
					tranfer = Math.floor(tranfer/100)*100;
				if(tranfer != ''){
					$("#cash_day1_first_tranfer").val(addCommaString(tranfer+''));
				}else{
					$("#cash_day1_first_tranfer").val('');
					//$("#cash_day1_first_tranfer").focus();
				}				
				if($('#ca_final_credit').val() != undefined && $('#ca_final_credit').val() != ''){
					if(parseFloat(removeCommas($('#ca_final_credit').val())) < parseFloat(removeCommas($('#cash_day1_first_tranfer').val()))){
						alertMassageSelection(CASH_FIRST_MORE_FINAL,$('#cash_day1_first_tranfer').attr('id'));
						return;
					}
				}
			});
		}else{
			$('#cash_day1_first_tranfer').attr("readOnly",true);
			$('#cash_day1_first_tranfer').removeClass("textboxReadOnly");
			$('#cash_day1_first_tranfer').removeClass("textbox-currency");
			$('#cash_day1_first_tranfer').addClass("textbox-currency-view");
			$('#cash_day1_first_tranfer').unbind();
		}
		CalculateFirstTranfer();
	}
}
function CashDayOnePercent(){
	if('0' == $('#cash_day1_percent').val()){
		$('#cash_day1_first_tranfer').attr("readOnly",false);		
		$('#cash_day1_first_tranfer').removeClass("textboxReadOnly");
		$('#cash_day1_first_tranfer').removeClass("textbox-currency-view");
		$('#cash_day1_first_tranfer').addClass("textbox-currency");
		$('#cash_day1_first_tranfer').val('');
		$('#cash_day1_first_tranfer').unbind();
		TextBoxCurrencyEngine('#cash_day1_first_tranfer');
		$('#cash_day1_first_tranfer').blur(function(){
			var tranfer = Number(removeCommas($("#cash_day1_first_tranfer").val()));
				tranfer = Math.floor(tranfer/100)*100;
			if(tranfer != ''){
				$("#cash_day1_first_tranfer").val(addCommaString(tranfer+''));
			}else{
				$("#cash_day1_first_tranfer").val('');
				//$("#cash_day1_first_tranfer").focus();
			}
			
			if($('#ca_final_credit').val() != undefined && $('#ca_final_credit').val() != ''){
				if(parseFloat(removeCommas($('#ca_final_credit').val())) < parseFloat(removeCommas($('#cash_day1_first_tranfer').val()))){
					alertMassageSelection(CASH_FIRST_MORE_FINAL,$('#cash_day1_first_tranfer').attr('id'));
					return;
				}
			}
		});
	}else{
		$('#cash_day1_first_tranfer').attr("readOnly",true);
		$('#cash_day1_first_tranfer').removeClass("textboxReadOnly");
		$('#cash_day1_first_tranfer').removeClass("textbox-currency");
		$('#cash_day1_first_tranfer').addClass("textbox-currency-view");
		$('#cash_day1_first_tranfer').val('');
		$('#cash_day1_first_tranfer').unbind();
	}
	CalculateFirstTranfer();
}
function CalculateFirstTranfer(){
	var percent =  $('#cash_day1_percent').val();
	if(percent != '' && percent != '0' && $('#ca_final_credit').val() != undefined){
		var credit = $('#ca_final_credit').val();
		if(credit != ''){
			var tranfer = Number(parseFloat(removeCommas(credit))*parseFloat(percent)/100);
				tranfer = Math.floor(tranfer/100)*100;
			if(tranfer != ''){
				$('#cash_day1_first_tranfer').val(addCommaString(tranfer+''));
			}else{
				$('#cash_day1_first_tranfer').val('');	
			}
		}
	}else{
//		#septem set cash_day1_first_tranfer = '' when cash_day1_percent = ''
		if(percent == ''){
			$('#cash_day1_first_tranfer').val('');
		}
	}
}

function getCashDay1AccountName(obj){
	if($('#cash_day1_bank_transfer').val() == '004'){
		if($(obj).val() == ''){
			$("#cash_day1_account_name").val('');
			return;
		}
		validateUserCashDay1(obj);
	}	
}
function setCashAccount(){
	if($('#cash_day1_bank_transfer').val()=='004'){
		$('#cash_day1_account_no').val('');
		$("#cash_day1_account_name").val('');
		$('#cash_day1_account_no').attr("maxlength","10");
	}else{
		$('#cash_day1_account_no').val('');
		$("#cash_day1_account_name").val('');
		$('#cash_day1_account_no').attr("maxlength","20");
	}	
}
function validateUserCashDay1(accountNoObj){
	try{
		$('#cash_day1_account_name').val('');
		DestoryErrorField();
		var dataString = "className=ValidateEAIAccount&packAge=0&returnType=0&serviceType=ACCOUNT&accountNo="+accountNoObj.value;
		if(!openblockscreen){
			blockScreen();
		}
		$.post('AjaxDisplayServlet',dataString,function(data,status,xhr){
				try{
					if(data == null || data.length < 0){
						unblockScreen();
						return;
					}
					var result = "";
					var transactionId = "";
					$.map(data, function(item){
						if(item.id=="validateResult"){
							result = item.value;
					    }else if(item.id=="transactionId"){
					    	transactionId = item.value;
					    }
					});
					if("FOUND"==result){
						hookAccountNameCashDay1(accountNoObj,transactionId);
					}else if("NOT_MATCH"==result){
						unblockScreen();
						alertMassage("\u0E44\u0E21\u0E48\u0E43\u0E0A\u0E48\u0E40\u0E25\u0E02\u0E17\u0E35\u0E48\u0E1A\u0E31\u0E0D\u0E0A\u0E35\u0E02\u0E2D\u0E07\u0E1C\u0E39\u0E49\u0E2A\u0E21\u0E31\u0E04\u0E23");
						accountNoObj.value="";
						$("#cash_day1_account_name").val('');
						$('#cash_day1_day1Flag').val('NP');
						$('#cash_day1_account_no').attr("readOnly",true);
						$('#cash_day1_account_no').addClass("textboxReadOnly");
						$('#cash_day1_account_no').unbind();
						CashDayOneFlag();
					}else if("NOT_FOUND"==result){
						unblockScreen();
						alertMassage("\u0E44\u0E21\u0E48\u0E1E\u0E1A\u0E02\u0E49\u0E2D\u0E21\u0E39\u0E25\u0E02\u0E2D\u0E07\u0E40\u0E25\u0E02\u0E17\u0E35\u0E48\u0E1A\u0E31\u0E0D\u0E0A\u0E35\u0E19\u0E35\u0E49\u0E17\u0E35\u0E48 CIS");
						$('#cash_day1_day1Flag').val('NP');
						$('#cash_day1_account_no').attr("readOnly",true);
						$('#cash_day1_account_no').addClass("textboxReadOnly");
						$('#cash_day1_account_no').unbind();
						CashDayOneFlag();
						accountNoObj.value="";
					}else{
						unblockScreen();
						$.map(data, function(item){
							if(item.id == 'ERROR'){
								if(item.value != null && item.value != ''){
									PushNotifications(item.value);
									OpenNotification();
								}							
								error = true;
							}						
						});
						accountNoObj.value="";
					}
				}catch(e){
					unblockScreen();
				}
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
function hookAccountNameCashDay1(accountNoObj,transactionId){
	try{
		var dataString = "className=GetEAIAccount&packAge=0&returnType=0&itemName=cash_day1_account_name&accountNo="+accountNoObj.value;
			dataString += "&transactionId="+transactionId;
		$.ajax({
			type :"POST",
			url :"AjaxDisplayServlet",
			data :dataString,
			async :false,	
			dataType: "json",
			success : function(response ,status ,xhr){
				unblockScreen();
				if(response == null || response.length < 0) return;
				var cbs_result="";
				var accountName="";
				$.map(response, function(item){
					if(item.id=="cbs_result"){
						cbs_result = item.value;
				    }
					if(item.id=="cash_day1_account_name"){
						accountName = item.value;
					}				
				});
				if("OK"==cbs_result){
					$("#cash_day1_account_name").val(accountName);
				}else if("NOT_MATCH_ACCOUNT"==cbs_result){
					alertMassage("\u0E40\u0E25\u0E02\u0E17\u0E35\u0E48\u0E1A\u0E31\u0E0D\u0E0A\u0E35\u0E44\u0E21\u0E48\u0E16\u0E39\u0E01\u0E15\u0E49\u0E2D\u0E07");
					accountNoObj.value="";
					$('#cash_day1_day1Flag').val('NP');
					$('#cash_day1_account_no').attr("readOnly",true);
					$('#cash_day1_account_no').addClass("textboxReadOnly");
					$('#cash_day1_account_no').unbind();
					CashDayOneFlag();
				}else if("NOT_FOUND"==cbs_result){
					alertMassage("\u0E44\u0E21\u0E48\u0E1E\u0E1A\u0E02\u0E49\u0E2D\u0E21\u0E39\u0E25\u0E02\u0E2D\u0E07\u0E40\u0E25\u0E02\u0E17\u0E35\u0E48\u0E1A\u0E31\u0E0D\u0E0A\u0E35\u0E19\u0E35\u0E49\u0E17\u0E35\u0E48 CBS");
					accountNoObj.value="";
					$('#cash_day1_day1Flag').val('NP');
					$('#cash_day1_account_no').attr("readOnly",true);
					$('#cash_day1_account_no').addClass("textboxReadOnly");
					$('#cash_day1_account_no').unbind();
					CashDayOneFlag();
				}else{
					//Wichaya add to notify when cannot connect to EAI
					$.map(response, function(item){
						if(item.id == 'ERROR'){
							if(item.value != null && item.value != ''){
								PushNotifications(item.value);
								OpenNotification();
							}
							
							error = true;
						}
						
					});
					accountNoObj.value = "";
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
function setCashDay1Data(){
	if($("#cash_day1_day1Flag").val()=="NA" || $("#cash_day1_day1Flag").val()=="NP" || $("#cash_day1_day1Flag").val()==""){
//		CashDayOnePercent();
		$('.service_seller_check').remove();
		$("#cash_day1_quick_cash").checked = false;
//		$("#cash_day1_percent").val('');
//		$("#cash_day1_first_tranfer").val('');
//		#septem default cash_day1_bank_transfer
//		$("#cash_day1_bank_transfer").val('');
		$("#cash_day1_bank_transfer").val('004');
		$("#cash_day1_account_no").val('');
		$("#cash_day1_remark").val('');
		$("#cash_day1_account_name").val('');
		CashDayOnePercent();
	}else{
		$('.service_seller_check').remove();
		$('.service_check').append('<span class="service_seller_check"><font color="#ff0000">*</font></span>');
	}
	CashDayOneAccountNo();
	CashDayOneFlag();
}
function CashDayOneAccountNo(){
	var role = $('#cashDay1_role').val();
	var searchtype = $('#search_cash_day5').val();
	if(searchtype == 'SearchCashDay5' && $("#cash_day1_day1Flag").val()=='CD5'){
		editAccountName();
	}else{
//		#septem and condition NP,NA
		if($("#cash_day1_day1Flag").val()=='CD5' || $('#cashdayone_displaymode').val() == 'VIEW' 
					|| ROLE_CA == role || $("#cash_day1_day1Flag").val()=='NA' || $("#cash_day1_day1Flag").val()=='NP'
						|| $("#cash_day1_day1Flag").val()==''){
			$('#cash_day1_account_no').attr("readOnly",true);
			$('#cash_day1_account_no').addClass("textboxReadOnly");
			$('#cash_day1_account_no').unbind();
			if($('#cashdayone_displaymode').val() != 'VIEW' && $("#cash_day1_day1Flag").val() != 'CD1'){
				$("#cash_day1_account_name").val('');
				$('#cash_day1_account_no').val('');
			}
		}else{ 
			editAccountName();
		}
	}
}
function editAccountName(){
	$('#cash_day1_account_no').attr("readOnly",false);
	$('#cash_day1_account_no').removeClass("textboxReadOnly");
	$('#cash_day1_account_no').unbind();
	$('#cash_day1_account_no').keypress(function(event){
		isNumberOnKeyPress(this);
	});
	$('#cash_day1_account_no').keyup(function(event){
		isNumberOnkeyUp(this);	
	});	
	$('#cash_day1_account_no').blur(function(){
		getCashDay1AccountName(this);
	});
}