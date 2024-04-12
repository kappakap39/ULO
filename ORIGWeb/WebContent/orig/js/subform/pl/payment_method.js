/**
 * @Pipe
 */
$(document).ready(function(){
	checkEnable();
	var current_role = $('#payment_role').val();
	if(current_role!=null && current_role=="DC"){
		$('#payment_method_dueCycle').attr("disabled","disabled");
	}
	$('#payment_method_pay').unbind();	
	$('#payment_method_pay').change(function(){checkEnable();});	
});
function validateUserPaymentMethod(accountNo){
	try{
		$('#payment_method_bankAccountName').val('');
		var className="ValidateEAIAccount";
		var packAge="0";
		DestoryErrorField();
		var dataString = "className="+className+"&packAge="+packAge+"&returnType=0&serviceType=ACCOUNT&accountNo="+accountNo.value;
		if(!openblockscreen){
			blockScreen();
		}
		$.post('AjaxDisplayServlet',dataString,function(data,status,xhr){				
				if(data == null || data.length < 0){
					unblockScreen();
					return;
				}
				var result="";
				var transactionId="";
				$.map(data, function(item){
					if(item.id=="validateResult"){
						result = item.value;
				    }else if(item.id=="transactionId"){
				    	transactionId=item.value;
				    }
				});
				if("FOUND"==result){
					hookAccountNamePaymentMethod(accountNo,transactionId);
				}else if("NOT_MATCH"==result){
					unblockScreen();
					alertMassage("\u0E44\u0E21\u0E48\u0E43\u0E0A\u0E48\u0E40\u0E25\u0E02\u0E17\u0E35\u0E48\u0E1A\u0E31\u0E0D\u0E0A\u0E35\u0E02\u0E2D\u0E07\u0E1C\u0E39\u0E49\u0E2A\u0E21\u0E31\u0E04\u0E23");
					accountNo.value="";
				}else if("NOT_FOUND"==result){
					unblockScreen();
					alertMassage("\u0E44\u0E21\u0E48\u0E1E\u0E1A\u0E02\u0E49\u0E2D\u0E21\u0E39\u0E25\u0E02\u0E2D\u0E07\u0E40\u0E25\u0E02\u0E17\u0E35\u0E48\u0E1A\u0E31\u0E0D\u0E0A\u0E35\u0E19\u0E35\u0E49\u0E17\u0E35\u0E48 CIS");
					accountNo.value="";
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
					accountNo.value = "";
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
function getPaymentMethodAccountName(obj){
	if(obj.value==""){
		$("#payment_method_bankAccountName").val('');
		return;}
	validateUserPaymentMethod(obj);
}

function hookAccountNamePaymentMethod(accountNo,transactionId){
	try{
		var className="GetEAIAccount";
		var packAge="0";//default
		var dataString = "className="+className+"&packAge="+packAge+"&returnType=0&itemName=payment_method_bankAccountName&accountNo="+accountNo.value;
		dataString+="&transactionId="+transactionId;
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
						cbs_result=item.value;
				    }
					if(item.id=="payment_method_bankAccountName"){
						accountName=item.value;
					}				
				});
				if("OK"==cbs_result){
					$("#payment_method_bankAccountName").val(accountName);
				}else if("NOT_MATCH_ACCOUNT"==cbs_result){				
					alertMassage("\u0E40\u0E25\u0E02\u0E17\u0E35\u0E48\u0E1A\u0E31\u0E0D\u0E0A\u0E35\u0E44\u0E21\u0E48\u0E16\u0E39\u0E01\u0E15\u0E49\u0E2D\u0E07");
					accountNo.value="";
				}else if("NOT_FOUND"==cbs_result){
					alertMassage("\u0E44\u0E21\u0E48\u0E1E\u0E1A\u0E02\u0E49\u0E2D\u0E21\u0E39\u0E25\u0E02\u0E2D\u0E07\u0E40\u0E25\u0E02\u0E17\u0E35\u0E48\u0E1A\u0E31\u0E0D\u0E0A\u0E35\u0E19\u0E35\u0E49\u0E17\u0E35\u0E48 CBS");
					accountNo.value="";
				}else{
					$.map(response, function(item){
						if(item.id == 'ERROR'){
							if(item.value != null && item.value != ''){
								PushNotifications(item.value);
								OpenNotification();
							}
							
							error = true;
						}
						
					});
					accountNo.value="";
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

function checkEnable(){
		var payment_method_pay = $('#payment_method_pay').val();
		var payment_mode = $('#payment_method_displaymode').val();
		if(payment_method_pay!=null&&payment_method_pay=='02'&&payment_mode==DISPLAY_MODE_EDIT){
			$('[name=ManPayment]').show();
			$('#payment_method_ratio').removeAttr('disabled');
			$('#payment_method_bankAccountNo').removeClass('textboxReadOnly');
		}
		else{
			$('[name=ManPayment]').hide();
//			$('.payment_methodcheck').remove();
			$('#payment_method_ratio').val('');
			$('#payment_method_ratio').attr('disabled','disabled');
			$('#payment_method_bankAccountNo').addClass('textboxReadOnly');
			$('#payment_method_bankAccountName').addClass('textboxReadOnly');
		}

}