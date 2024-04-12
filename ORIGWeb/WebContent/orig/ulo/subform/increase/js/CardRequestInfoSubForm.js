//function ADD_BTNActionJS(){
//	if(AddCardInfoManadatory()){
//		addCard();
//	}
//}
//
//
//
//function AddCardInfoManadatory(){
//	var cardNo = $.trim($("input[name='CARD_NO']").val());
//	if(cardNo !=""){
//		return true;	
//	}
//	else{
//		return false;
//	}
//}
//
//function addCard(){
//	var cardNo = $.trim($("input[name='CARD_NO']").val());
//	var	 className ='com.eaf.orig.ulo.app.view.form.subform.increase.manual.AddCardRequestInfoSubForm1';
//	var	data ="FUNCTION=addCardNo";
//		data +="&CARD_NO="+cardNo;
//	ajax(className,data,refreshSubForm('INCREASE_CARD_REQUEST_INFO_SUBFROM_1'));
//	
//}
//function DEL_BTNActionJS(){
//	$('#INCREASE_CARD_REQUEST tr').click( function() {
//		var  index =  $(this).index();
//		var cardNo=  $('#INCREASE_CARD_REQUEST tr:eq(' + index + ') td input[name="CARD_NO"]').val(); 
//		
//		var	 className ='com.eaf.orig.ulo.app.view.form.subform.increase.manual.AddCardRequestInfoSubForm1';
//		var	data ="FUNCTION=delCardNo";
//			data +="&CARD_NO="+cardNo;
//		ajax(className,data,refreshSubForm('INCREASE_CARD_REQUEST_INFO_SUBFROM_1'));
//	});
//	
//	
//}