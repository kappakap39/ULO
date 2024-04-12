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
    	 function(){$(this).addClass("ResultOdd-haver-non-pointer");},
    	 function(){$(this).removeClass("ResultOdd-haver-non-pointer");}
    );
	$("Tr.ResultEven").hover(
	    function(){$(this).addClass("ResultEven-haver-non-pointer");},
	    function(){$(this).removeClass("ResultEven-haver-non-pointer");}
	);
	
	$('#project_codePopup').click(function (){
		var code = $('#project_code').val();
		projectCodeAllPopup(code);		
	});
	$('#project_code').blur(function (){
		if(document.getElementById("project_code").value.length!=0){
			blurProjectCode(document.getElementById("project_code").value);
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
	        	   searchPriority();
	           }
	     });
	 }
	//=============================
//	 #septem focus screen
	 $('#button-search').focus();
});

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
	var citizen_id = appFormName.citizen_id.value;
	var application_no = appFormName.application_no.value;
	var firstname = appFormName.firstname.value;
	var lastname = appFormName.lastname.value;
	var priority = appFormName.priority.value;
	var projectCode = appFormName.project_code.value;
	var product = appFormName.product.value;
	var empFname = appFormName.empFirstName.value;
	var empLname = appFormName.empLastName.value;
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
	
	if( (application_no=="") && (citizen_id=="") && (firstname=="") && (lastname=="") && (priority=="") && (projectCode=="") && (empFname=="") && (empLname=="") && (empId=="") ){
		alertMassage(SELECT_MORE_2_FIELD);
		return false;
	} 
//	#septem comment 
//	else {
//		return true;
//	}
//	
//	var appRecId = appFormName.document.getElementsByName("checkAppRecId").length;
//	for( var i=0; i<appRecId ; i++){
//		if(appFormName.document.getElementsByName("checkAppRecId")[i].checked == true){
//			return true;
//		}
//	}
//	return false;
	return true;
}
function validateSave(){
    var checkBoxApp = document.getElementsByName("checkAppRecId");
	if(checkBoxApp != null && checkBoxApp.length > 0){
	
		var check = false;
		for( var i=0; i<checkBoxApp.length; i++){
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
			if(appFormName.setPriority.value==""){
				alertMassage(SELECT_PRIORITY);
				appFormName.setPriority.focus();
				return false;
			}
			return true;
		}
	} else {
		alertMassage(SELECT_APP);
	}
	return false;
}
function savePriority(){
	if(validateSave()){
		alertMassageYesNoFunc(CONFIRM_SET_PRIORITY, submitPriority, null);
	}
}
function submitPriority(){
	try{
		blockScreen();
		appFormName.action.value = "SaveSetPriorityWebAction";
		appFormName.handleForm.value = "N";
		appFormName.submit();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function searchPriority(){
	try{
		if(validate()){
			blockScreen();
			appFormName.action.value = "SearchSetPriorityWebAction";
			appFormName.clickSearch.value = "Y";
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

function projectCodeAllPopup(code){
	var url;
	if(null != code){
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
function blurProjectCode(projectCode){
	if(projectCode.length > 0 &&!isNaN(projectCode)){
		var dataString = "ClassPackage=com.eaf.orig.ulo.pl.product.ajax&ClassName=GetProjectCodeNo&project_code="+projectCode;
		var uri = "AjaxServlet";
		jQuery.ajax( {
			type :"POST",
			url :uri,
			data :dataString,
			async :false,
			
			success : function(data ,status ,xhr) {
				var sData = data.split("|");  //array t
				
				if(data == "NOT_Found"){
					var fieldVal = $('#project_code').val();
					projectCodeAllPopup(fieldVal);
				} else {
					$('#project_code').val(sData[0]);
				}
			},
			error : function(data){
				alertMassage("error"+data);
			}
		});
	}
}
