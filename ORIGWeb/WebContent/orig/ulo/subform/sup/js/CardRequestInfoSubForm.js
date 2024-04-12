function ADD_BTNActionJS(){
	try{
		if(AddCardInfoManadatory()){
			addCard();
		}
	}catch(exception){
		errorException(exception);
	}
}



function AddCardInfoManadatory(){
	try{
		var cardNo = $.trim($("input[name='CARD_NO']").val());
		if(cardNo !=""){
			return true;	
		}
		else{
			return false;
		}
	}catch(exception){
		errorException(exception);
	}
}

function addCard(){
	try{
		var cardNo = $.trim($("input[name='CARD_NO']").val());
		var	 className ='com.eaf.orig.ulo.app.view.form.subform.increase.manual.AddCardRequestInfoSubForm1';
		var	data ="FUNCTION=addCardNo";
			data +="&CARD_NO="+cardNo;
		ajax(className,data,refreshSubForm('SUP_CARD_REQUEST_INFO_SUBFROM_2'));
	}catch(exception){
		errorException(exception);
	}
	
}
function DEL_BTNActionJS(){
	$('#INCREASE_CARD_REQUEST tr').click( function() {
		try{
			var  index =  $(this).index();
			var cardNo=  $('#INCREASE_CARD_REQUEST tr:eq(' + index + ') td input[name="CARD_NO"]').val(); 
			
			var	 className ='com.eaf.orig.ulo.app.view.form.subform.increase.manual.AddCardRequestInfoSubForm1';
			var	data ="FUNCTION=delCardNo";
				data +="&CARD_NO="+cardNo;
			ajax(className,data,refreshSubForm('SUP_CARD_REQUEST_INFO_SUBFROM_2'));
		}catch(exception){
			errorException(exception);
		}
	});
	
	
}