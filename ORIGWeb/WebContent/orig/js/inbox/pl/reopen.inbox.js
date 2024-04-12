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
    	 function(){$(this).addClass("ResultOdd-haver");},
    	 function(){$(this).removeClass("ResultOdd-haver");}
    );
	$("Tr.ResultEven").hover(
    	 function(){$(this).addClass("ResultEven-haver");},
    	 function(){$(this).removeClass("ResultEven-haver");}
    );
	$("Tr.Result-Obj").click(function(e){	
		e.preventDefault();
		try{
			blockScreen();
			var obj = $(this).attr('id').split(/\s+/);
			var form = document.appFormName;
			form.formID.value = "KEC_FORM";
			form.currentTab.value = "MAIN_TAB";
			form.action.value = "LoadPLAppFormReopenWebAction";
			form.handleForm.value = "N";
			form.appRecordID.value = obj[0];
			form.submit();
		}catch(e){
			unblockScreen();
			var msg = ERROR_JS+e.message;
			alertMassageERROR(msg);
		}
	});
	
	navinbox(0);
	
//	septemwi comment chage display scroll
//	$('.nav-inbox').css({
//		'width': $(window).width()
//	});
//	$('#divScroll').css(
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
	        	   searchApp();
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
function loadApplication(){
	try{
		blockScreen();
	    var form = document.appFormName;
		form.submit();	
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
function validate(){
	var product = appFormName.product.value;
//	var saleType = appFormName.saleType.value;
	if (product==""){
		alertMassage(PRODUCT);
		return false;
	} 
//	else if (saleType==""){
//		alertMassage(SELECT_SALE_TYPE);
//	} 
//	else {
//		return true;
//	}
	
//	#septem validate countChar2
	if(!countChar2(appFormName.th_firstname)){
		return false;
	}
	if(!countChar2(appFormName.th_lastname)){
		return false;
	}
//	end #septem validate countChar2
	
	return true;
}
function searchApp(){
 	if(validate()){
 		blockScreen();
		appFormName.action.value = "SearchReopenWebAction";
		appFormName.handleForm.value = "N";
		appFormName.fromSearch.value = "Y";
		appFormName.submit();	
 	}
}
function getSaleType(){
	var product = appFormName.product.value;
	var dataString = "ClassPackage=com.eaf.orig.ulo.pl.product.ajax&ClassName=GetSaleTypeFromProduct&product="+product; 
	var uri = "AjaxServlet";
	jQuery.ajax( {
	type :"POST",
	url :uri,
	data :dataString,	
	async :false,
	success : function(data ,status ,xhr) {
		$("#div_saleType").html(data);
		},
	error : function(data){
		alertMassage("error"+data);
		}
	});	

}
