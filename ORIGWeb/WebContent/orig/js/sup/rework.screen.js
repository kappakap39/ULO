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
function keyPressInteger(){
	var keynum;
	var keychar;
	var numcheck;	
	if(window.event){
		keynum = event.keyCode;
	}else if(event.which){
		keynum = event.which;
	}
	keychar = String.fromCharCode(keynum);
	numcheck = /\d/;
	return numcheck.test(keychar);
}
function validate(){
	var citizen_id 	= appFormName.citizen_id.value;
	var application_no = appFormName.application_no.value;
	var firstname = appFormName.firstname.value;
	var lastname = appFormName.lastname.value;
	var priority = appFormName.priority.value;
	var project_code = appFormName.project_code.value;
	var product = appFormName.product.value;
	var empFirstName = appFormName.empFirstName.value;
	var empLastName = appFormName.empLastName.value;
	var empId = appFormName.empId.value;
	
	if(product=="") {
		alertMassage(PRODUCT);
		return false;
	}
	
//	#septem validate countChar2
	if(!countChar2(appFormName.firstname)){
		return false;
	}
	if(!countChar2(appFormName.lastname)){
		return false;
	}
	if(!countChar2(appFormName.empFirstName)){
		return false;
	}
	if(!countChar2(appFormName.empLastName)){
		return false;
	}
//	end #septem validate countChar2
	
	if( (application_no=="") && (citizen_id=="") && (firstname=="") && (lastname=="") && (priority=="") && (project_code=="") && (empFirstName=="") && (empLastName=="") && (empId=="") ){
		alertMassage(SELECT_MORE_2_FIELD);
		return false;
	}
//	else{
//		return true;
//	}
	return true;
}
function validateSave(){
    var checkBoxApp = document.getElementsByName("checkAppRecId");
	if(checkBoxApp != null && checkBoxApp.length > 0){
	
		var check = false;
		for(var i=0; i<checkBoxApp.length; i++){
			if(checkBoxApp[i].checked){
				check = true;
				break;
			}
		}
		if(!check){
			alertMassage(SELECT_APP);
			return false;
		}
		if(checkBoxApp != null){
			return true;
		}
	}else{
		alertMassage(SELECT_APP);
	}
	return false;
}
function reWorkAction(){
	try{
		if(validateSave()){
			blockScreen();
			appFormName.action.value = "ReworkWebAction";
			appFormName.handleForm.value = "N";
			appFormName.submit();
		}
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function searchRework(){
	try{
		if(validate()){
			blockScreen();
			appFormName.action.value = "SearchReworkWebAction";
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

function AlertCheck(){
 	alertMassageYesNoFunc(CONFIIMR_CHANGE_VALUE, clearValue, null);
}
function clearValue(){
	$("#project_code").val("");
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
	$('#project_codePopup').click(function (){
		projectCodeAllPopup();		
	});
	$('#project_code').blur(function (){
		if(document.getElementById("project_code").value.length != 0){
			projectCodeAllPopup(document.getElementById("project_code").value);
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
	        	   searchRework();
	           }
	     });
	 }
	//=============================
//	 #septem focus screen
	 $('#button-search').focus();
});
function projectCodeAllPopup(code){
	var url;
	if(code != null){
		url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadProjectCodeAll&project_code="+code;
	} else {
		url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadProjectCodeAll";
	}
	$dialog = $dialogEmpty;
	var width = 1000;
	var higth = $(window).height()-50;
	var title = PROJECT_CODE;
	$(".plan-dialog").remove();
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	$dialog.dialog({
		    resizable : false,
		    modal : true,
		    autoOpen: false,
		    draggable: true,
		    width: width,
		    height: higth,
		    title:title,
		    position: { 
		        my: 'center',
		        at: 'center',
		        of: $(document.body)
    		},
		    close: function(){
		    	closeDialog();
		    }
	});
   $dialog.dialog("open");
}