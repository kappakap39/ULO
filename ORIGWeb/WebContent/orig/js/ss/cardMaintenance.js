
function countChar2(word){
	var textBoxValue = word.value;
	if(textBoxValue.length<2 && textBoxValue.length !=0){
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
	var sendCardDate = appFormName.sendCardDate.value;
	var product = appFormName.product.value;
	var application_no = appFormName.application_no.value;
	var firstname = appFormName.firstName.value;
	var lastname = appFormName.lastName.value;
	var cardNo = appFormName.cardNo.value;
	var cardRefNo = appFormName.cardRefNo.value;
	var cardStatus = appFormName.cardStatus.value;
	
	
//	#septem validate countChar2
	if(!countChar2(appFormName.firstName)){
		return false;
	}
	if(!countChar2(appFormName.lastName)){
		return false;
	}
//	end #septem validate countChar2
	
	if( (application_no=="") && (firstname=="") && (lastname=="") && (cardRefNo=="") && (cardNo=="") && (cardStatus=="") && (sendCardDate=="")){
		alertMassage(SELECT_MORE_2_FIELD);
		return false;
	}
	if(product=="") {
		alertMassage(PRODUCT);
		appFormName.product.focus();
		return false;
	}
	if(validateDate()){
		return true;
	}
	
}
function validateDate(){
	var curDate = new Date();
	var dateCur = curDate.getDate();
	var monthCur = curDate.getMonth()+1;
	var yearCur = curDate.getFullYear();
	var inputDate = appFormName.sendCardDate.value;
	var dateS = inputDate.split("/");
	var dayS = dateS[0];
	var monthS = dateS[1];
	var AD = dateS[2]-543;
	if(AD >= yearCur){
		if(monthS >= monthCur){
			if(dayS > dateCur){
				alertMassage(SELECT_DATE_LESS_THAN_TODAY);
				return false;
			}
		}
	}
	appFormName.hiddenSendCardDate.value=dayS+"/"+monthS+"/"+AD;
	return true;
}
function search(){
	try{
		if(validate()){
			blockScreen();
			appFormName.action.value = "SearchCardMaintenanceWebAction";
			appFormName.handleForm.value = "N";
			appFormName.submit();
		}
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function detail(){
	try{
		blockScreen();
		appFormName.action.value = "LoadCardInfoWebAction";
		appFormName.handleForm.value = "N";
		appFormName.submit();
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
function clickAllChk(){
	var check = appFormName.checkBoxAll.checked;
	if(check==true){
		$(document).ready(function(){
	  		$("#checkBoxAll").click(function(){
	    		$(":checkbox").attr("checked","checked");
	  		});
		});
	} else {
		$(document).ready(function(){
	  		$("#checkBoxAll").click(function(){
	    		$(":checkbox").removeAttr("checked");
	  		});
		});
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
			var data = $(this).attr('id').split("\|");
			appFormName.LaccId.value = data[0];
			appFormName.LappRecId.value = data[1];
			appFormName.Lproduct.value = data[2];
			appFormName.LcardNo.value = data[3];
			appFormName.LcardType.value = data[4];
			appFormName.LcardFace.value = data[5];
			appFormName.Lfname.value = data[6];
			appFormName.Llname.value = data[7];
			appFormName.LcardRefId.value = data[8];
			appFormName.LcardStatus.value = data[9];
			blockScreen();
			detail();
		}catch(e){
			unblockScreen();
			var msg = ERROR_JS+e.message;
			alertMassageERROR(msg);
		}
	});
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
