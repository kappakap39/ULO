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
			var form=document.appFormName;
			form.formID.value="KEC_ICDC_FORM";
			form.currentTab.value="MAIN_TAB";
			form.action.value="LoadPLAppForm";
			form.handleForm.value = "N";
			form.appRecordID.value = obj[0];
			form.roleElement.value="FU";
			form.submit();
		}catch(e){
			unblockScreen();
			var msg = ERROR_JS+e.message;
			alertMassageERROR(msg);
		}
	});
	
	//validate year >= 2400 #Vikrom  20121120
	$('#app_date_from').blur(function(){
		var year = $('#app_date_from').val().substr(6,10);
		if(year < 2400 && year.length!=0){
			alertMassage(FORMAT_DATE_ERROR);
			$('#app_date_from').val('');
			$('#app_date_from').focus();
			$('#app_date_from').setCursorToTextEnd();
		}
	});
	$('#app_date_to').blur(function(){
		var year = $('#app_date_to').val().substr(6,10);
		if(year < 2400 && year.length!=0){
			alertMassage(FORMAT_DATE_ERROR);
			$('#app_date_to').val('');
			$('#app_date_to').focus();
			$('#app_date_to').setCursorToTextEnd();
		}
	});
	$('#fu_date_from').blur(function(){
		var year = $('#fu_date_from').val().substr(6,10);
		if(year < 2400 && year.length!=0){
			alertMassage(FORMAT_DATE_ERROR);
			$('#fu_date_from').val('');
			$('#fu_date_from').focus();
			$('#fu_date_from').setCursorToTextEnd();
		}
	});
	$('#fu_date_to').blur(function(){
		var year = $('#fu_date_to').val().substr(6,10);
		if(year < 2400 && year.length!=0){
			alertMassage(FORMAT_DATE_ERROR);
			$('#fu_date_to').val('');
			$('#fu_date_to').focus();
			$('#fu_date_to').setCursorToTextEnd();
		}
	});
	
//	 ===========Enter Submit==================
	 var form =  document.appFormName;
	 if($(form).length > 0){
	     $(form).keypress(function (e){
	         code = e.keyCode ? e.keyCode : e.which;
	           if(code.toString() == 13) 
	           {
	        	   searchApp();
	           }
	     });
	 }
//	 =============================
//	 #septem focus screen
	 $('#button-search').focus();	
});

function clickPageList(atPage){
	$("#atPage").val(atPage);
	$("#action").val('SearchMWebAction');
	$("#handleForm").val('N');
	$("#appFormName").submit();
}
function clickItemPerPageList(atItem){
	$("#itemsPerPage").val(atItem);
	$("#action").val('SearchMWebAction');
	$("#handleForm").val('N');
	$("#appFormName").submit();	
}
function validate(){
	
	var app_date_from 	=  document.appFormName.app_date_from.value;
	var app_date_to = document.appFormName.app_date_to.value;
	var fu_date_from = document.appFormName.fu_date_from.value;
	var fu_date_to = document.appFormName.fu_date_to.value;
	
	var errorMessage = "";
	
	if((app_date_from !="" && app_date_to == "") || (app_date_from == "" && app_date_to !="")){
		errorMessage = errorMessage + FU_RECEIVE_DATE;
		if(app_date_from == ""){
			alertMassageSelection(errorMessage,'app_date_from');
		}else{
			alertMassageSelection(errorMessage,'app_date_to');
		}
	    return false;
	}else if(app_date_from!="" && app_date_to!=""){
		var appdt1  = parseInt(app_date_from.substring(0,2),10);
		var appmon1 = parseInt(app_date_from.substring(3,5),10);
		var appyr1  = parseInt(app_date_from.substring(6,10),10);
		var appdt2  = parseInt(app_date_to.substring(0,2),10);
		var appmon2 = parseInt(app_date_to.substring(3,5),10);
		var appyr2  = parseInt(app_date_to.substring(6,10),10);
		var appdate1 = new Date(appyr1, appmon1, appdt1);
		var appdate2 = new Date(appyr2, appmon2, appdt2);
		if(appdate2 < appdate1){
			errorMessage = errorMessage + FU_RECEIVE_DATE;
	    	alertMassageSelection(errorMessage,'app_date_to');
	    	return false;
		}
	}
	
	if((fu_date_from !="" && fu_date_to == "") || (fu_date_from == "" && fu_date_to !="")){
		errorMessage = errorMessage + FU_FOLLOW_DATE;
		if(fu_date_from ==""){
			alertMassageSelection(errorMessage,'fu_date_from');
		}else{
			alertMassageSelection(errorMessage,'fu_date_to');
		}
	    return false;
	}else if(fu_date_from!="" && fu_date_to!=""){
		var fudt1  = parseInt(fu_date_from.substring(0,2),10);
		var fumon1 = parseInt(fu_date_from.substring(3,5),10);
		var fuyr1  = parseInt(fu_date_from.substring(6,10),10);
		var fudt2  = parseInt(fu_date_to.substring(0,2),10);
		var fumon2 = parseInt(fu_date_to.substring(3,5),10);
		var fuyr2  = parseInt(fu_date_to.substring(6,10),10);
		var fudate1 = new Date(fuyr1, fumon1, fudt1);
		var fudate2 = new Date(fuyr2, fumon2, fudt2);
		if(fudate2 < fudate1){
			errorMessage = errorMessage + FU_FOLLOW_DATE;
			alertMassageSelection(errorMessage,'fu_date_to');
	    	return false;
		}
	}
	if(($('#firstName').val() == null 		|| $('#firstName').val() == '' ) &&
	   ($('#lastName').val() == null  		|| $('#lastName').val() == '' ) &&
	   ($('#idNo').val() == null 	  		|| $('#idNo').val() == '' ) &&
	   ($('#app_date_from').val() == null 	|| $('#app_date_from').val() == '' ) &&
	   ($('#app_date_to').val() == null 	|| $('#app_date_to').val() == '' ) &&
	   ($('#appNo').val() == null 			|| $('#appNo').val() == '' ) &&
	   ($('#fu_date_from').val() == null 	|| $('#fu_date_from').val() == '' ) &&
	   ($('#fu_date_to').val() == null 		|| $('#fu_date_to').val() == '' )){
		errorMessage = errorMessage + NEED_CRITERIA;
		alertMassage(errorMessage);
		return false;
	}
	
	if(!countFirstNameChar(appFormName.firstName)){
		return false;
	}
	
	if(!countLastNameChar(appFormName.lastName)){
		return false;
	}
	
	return true;
}

function searchApp(){
	try{
		if(validate()){
			blockScreen();
			document.appFormName.action.value="FUPLInbox";
			document.appFormName.handleForm.value = "N";
			document.appFormName.fromSearch.value = "Y";
			document.appFormName.submit();	
		}
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}

function countFirstNameChar(word){
   var  textBoxValue=word.value;
	if(textBoxValue.length<2 && textBoxValue.length !=0){
	    var errorMsg = TH_FNAME_COUNT_CHAR;
		alertMassageSelection(errorMsg,word.id);
		return false;
	}
	return true;
}
function countLastNameChar(word){
   var  textBoxValue=word.value;
	if(textBoxValue.length<2 && textBoxValue.length !=0){
		var errorMsg = TH_LNAME_COUNT_CHAR;
		alertMassageSelection(errorMsg,word.id);
		return false;
	}
	return true;
}