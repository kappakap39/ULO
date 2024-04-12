/**
 * 
 */
function countChar2(word){
	var textBoxValue = word.value;
	if(textBoxValue.length < 2 && textBoxValue.length != 0){
		alertMassageFuncParam1(CHAR_MORE_2, setCaretPosition, word);
		return false;
	}
	return true;
}
function setCaretPosition(text){
	$('#'+$(text).attr('id')).focus();
	$('#'+$(text).attr('id')).setCursorToTextEnd();
}
function validate(){
	var product = appFormName.product.value;
	if(product==""){
		alertMassage(PRODUCT);
		return false;
	}
//	#septem validate countChar2
	if(!countChar2(appFormName.firstName)){
		return false;
	}
	if(!countChar2(appFormName.lastName)){
		return false;
	}
//	end #septem validate countChar2
	return true;
}
function search(){
	try{
		if(validate()){
			blockScreen();
			appFormName.action.value = "SearchCallCenterTrackingWebAction";
			appFormName.handleForm.value = "N";
			appFormName.submit();	
		}
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function clickPageList(atPage){
	try{
		blockScreen();
		var form = window.document.appFormName;
		form.atPage.value = atPage;
		form.action.value = "ValueListWebAction";
		form.handleForm.value = "N";
		form.submit();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function clickItemPerPageList(atItem){
	try{
		blockScreen();
		var form = window.document.appFormName;
		form.itemsPerPage.value = atItem;
		form.action.value = "ValueListWebAction";
		form.handleForm.value = "N";
		form.submit(); 
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
$(document).ready(function (){
	var height = $('.PanelSecond').outerHeight();
	if(height < $(window).height()-60){
		$('.PanelSecond').css({
			'height': $(window).height()-60
		});
	}
	$('.nav-inbox').slimScroll({
		height: $(window).height()+'px'
	});	
	$("Tr.ResultOdd").hover(
    	 function(){$(this).addClass("ResultOdd-haver-non-pointer");},
    	 function(){$(this).removeClass("ResultOdd-haver-non-pointer");}
    );
	$("Tr.ResultEven").hover(
	  	 function(){$(this).addClass("ResultEven-haver-non-pointer");},
	     function(){$(this).removeClass("ResultEven-haver-non-pointer");}
	);
	
	navinbox(0);
	
//	septemwi comment change style scroll
//	$('.nav-inbox').css({
//		'width': $(window).width()
//	});
//	$('#tScroll').css(
//		'overflow', 'auto'
//	);
//	$('.PanelValueList').css({
//		'width': $('.TableFrame').width()-40
//	});
	//===========Enter Submit==================
	 var form =  document.appFormName;
	 if($(form).length > 0)
	 {
	     $(form).keypress(function (e){
	         code = e.keyCode ? e.keyCode : e.which;
	           if(code.toString() == 13) 
	           {
	        	   search();
	           }
	     });
	 }
	//=============================	  
//	 #septem focus screen
	 $('#button-search').focus();		 
});
function navinbox(add){
	if($('.scroll-div').attr('class') != undefined){
		$('.nav-inbox').css({
			'width': $(window).width()+add
		});
	}
}
