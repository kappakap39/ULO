/**
 * 
 */

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
			var obj = $(this).attr('id').split(/\s+/);
			blockScreen();
			var form = document.appFormName;
			form.formID.value = "KEC_FORM";
			form.currentTab.value = "MAIN_TAB";
			form.action.value = "LoadPLAppFormWithOutClaim";
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
//	$('#tScroll').css(
//		'overflow', 'auto'
//	);
//	$('.PanelValueList').css({
//		'width': $('.TableFrame').width()-40
//	});
	//===========Enter Submit==================
	 var form =  document.appFormName;
	 if($(form).length > 0){
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
function countChar2(word){
	var textBoxValue = word.value;
	if(null != textBoxValue && textBoxValue.length<2 && textBoxValue.length !=0){
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
	try{
		var citizen_id 	= appFormName.IDNO.value;
		var application_no = appFormName.appNo.value;
		var firstname = appFormName.firstName.value;
		var lastname = appFormName.lastName.value;
		var priority = appFormName.priority.value;
		var saleType = appFormName.saleType.value;
		var refNo = appFormName.refNo.value;
		var product = appFormName.product.value;
		
		if(product=="") {
			alertMassage(PRODUCT);
			return false;
		}
		
//		#septem validate countChar2
		if(!countChar2(appFormName.firstName)){
			return false;
		}
		if(!countChar2(appFormName.lastName)){
			return false;
		}
//		end #septem validate countChar2
		
		if ( (application_no=="") && (citizen_id=="") && (firstname=="") && (lastname=="") && (priority=="") && (saleType=="") && (refNo=="") ){
			alertMassage(SELECT_MORE_2_FIELD);
			return false;
		} else {
			return true;
		}
	}catch(e){
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function search(){
	try{
		if(validate()){
			blockScreen();
			appFormName.action.value = "SearchEnquiryAction";
			appFormName.handleForm.value = "N";
			appFormName.searchType.value = "Y";
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

