$(document).ready(function(){
	$('#credit_card_result').change(function(){
		changeCreditCardResult();		
	});
});

function changeCreditCardResult(){
	try{
		$('#action').val('ExecuteChangeCreditCardResult');
		$('#handleForm').val('Y');
		$('#currentTab').val('MAIN_TAB');
		var dataString = $('#avale-obj-form *').serialize();
		blockScreen();
		$.post('FrontController',dataString,function(data,status,xhr){				
			if(data != null){
				$.map(data, function(item){
					if(item.RESULT_CODE == 'CH' && item.RESULT_DESC != $('#main_bussclass').val()){
						$('.boxy-wrapper').remove();
						$('.boxy-modal-blackout').remove();
						 Boxy.alert(MSG_CHANGE_SALE_TYPE
								 	,function(){
							 			blockScreen();
							 			$('#action').val('LoadSubformByBusClass');
							 			$('#handleForm').val('N');								 			
							 			$('#change-saletype').val(item.RESULT_DESC.substring(item.RESULT_DESC.lastIndexOf("_")+1,item.RESULT_DESC.length));								 			
										$('#appFormName').submit();
						 			},{title: 'Alert Message'});
					}else if (item.RESULT_CODE == 'ERROR'){
						PushNotifications("Process change Credit Card Result found error");
						OpenNotification();
					}
				});
			}
			unblockScreen();
		}).error(function(){
	//		PushNotifications("Process change Credit Card Result found error");
			PushErrorNotifications('Network or Connection Error, please try again');
	//		OpenNotification();
			unblockScreen();
		});
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}