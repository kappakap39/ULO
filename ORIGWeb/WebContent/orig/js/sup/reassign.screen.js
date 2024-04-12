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
	
	TextboxUserName();
	ButtonUserName();

	 var form =  document.appFormName;
	 if($(form).length > 0){
	     $(form).keypress(function (e){
	         code = e.keyCode ? e.keyCode : e.which;
	           if(code.toString() == 13){
	        	   searchReassign();
	           }
	     });
	 }
	 
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
function validate(){
	var citizen_id 	= appFormName.citizen_id.value;
	var application_no = appFormName.application_no.value;
	var firstname = appFormName.firstname.value;
	var lastname = appFormName.lastname.value;
	var priority = appFormName.SearchPriority.value;
	var overSLA = appFormName.overSLA.value;
	var product = appFormName.product.value;
	var empFirstName = appFormName.empFirstName.value;
	var empLastName = appFormName.empLastName.value;
	var empId = appFormName.empId.value;
	
//	#septemwi comment
//	var role = "FIELD";
//	if($('#role_reassign').attr('id') != undefined){
//		role = appFormName.role_reassign.value;
//	}
	
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
	
	if((application_no=="") && (citizen_id=="") && (firstname=="") && (lastname=="") //&&(role=="" && $('#role_reassign').attr('id') != undefined)
				&& (priority=="") && (overSLA=="") && (empFirstName=="") && (empLastName=="") && (empId=="")){
		alertMassage(SELECT_MORE_2_FIELD);
		return false;
	}
//	else{
//		return true;
//	}
	
	return true;	
}

function validateSaveReAssign(){
    var checkbox = selectApplication();
	if(checkbox){
		if(appFormName.userName.value == ""){
			appFormName.userName.focus();
			alertMassage(SELECT_OWNER);
			checkbox = false;
		}
	}
	return checkbox;
}

function SaveReassign(){
	try{
		if(MatchApplication()){
			if(validateSaveReAssign()){
				blockScreen();
				appFormName.action.value = "SaveReassignWebAction";
				appFormName.handleForm.value = "N";
				appFormName.submit();
			}
		}
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function reallocate(){
	try{
		if(validateSaveReAllocate()){
			blockScreen();
			appFormName.action.value = "SaveReallocateWebAction";
			appFormName.handleForm.value = "N";
			appFormName.submit();
		}
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function searchReassign(){
	try{
		if(validate()){
			blockScreen();
			appFormName.action.value = "SearchReassignWebAction";
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
function validateSaveReAllocate(){
   return selectApplication();
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

function ButtonUserName(){
	$('#userName_popup').click(function(e){
		if(MatchApplication()){
			OpenPopupUserName();
		}
	});
}
function OpenPopupUserName(){	
	var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadUserNameWebAction&"+$("#div-screen-reassign *").serialize();	
	$('#popup-action').val('');
	var frameModule = (document.getElementById('frame-module')== null)? document.body :'.frame-module';
	$dialog = $dialogEmpty;
	var width = 800;
	var higth = $(window).height()-50;
	var title = FULL_NAME;
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
		    buttons :{
		        "Close":function(){
		        	$dialog.dialog("close");	
		        	closeDialog();
		        }
	    	},
		    position: { 
		        my: 'center',
		        at: 'center',
		        of: $(frameModule)
    		},
		    close: function(){
		    	closeDialog();
		    	if($('#popup-action').val() != "SEARCH_CODE"){
		    		$('#userName').focus();
		    		$('#userName').setCursorToTextEnd();
		    	}
		    }
	});
   $dialog.dialog("open");
}
function TextboxUserName(){
	$("#userName").blur(function(){
		$(this).val($.trim($(this).val()));
		var userName = $(this).val();
		if(userName != ''){
			if(MatchApplication()){
				LoadUserName();
			}
		}else{
			$('#userName').val('');
			$('#reassignTo').val('');			
		}
	});
}
function LoadUserName(){
	var dataString = "className=GetUserName&packAge=5&returnType=0&"+$("#div-screen-reassign *").serialize();;
	jQuery.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){
			if(data!= null && data.length >0){
				jsonDisplayElementById(data);
			}else{
				$('#reassignTo').val('');
				OpenPopupUserName();
			}
		},
		error : function(response){
		}
	});
}
function MatchApplication(){
	var match = true;
	var role_action = $('#role_action').val();
	if(selectApplication()){
		if(superReassignSearch(role_action)){
			var dataString = "className=com.eaf.orig.ulo.pl.product.ajax.MatchApplication&returnType=1&"+$("#div-screen-reassign *").serialize();
			jQuery.ajax({
				type :"POST",
				url :"AjaxDisplayServlet",
				data :dataString,
				async :false,
				success : function(data ,status ,xhr){
					if(data == 'ERROR'){
						alertMassage(MSG_SELECT_APP_REASSIGN);
						$('#userName').val('');
						$('#reassignTo').val('');
						match = false;
					}else{
						match = true;
					}
				},
				error : function(response){
					match = false;
				}
			});
	
		}
	}else{
		match = false;
		$('#userName').val('');
		$('#reassignTo').val('');	
	}
	return match;
}
function selectApplication(){
	var select = false;
    var checkbox = document.getElementsByName("check_apprecid");
	if(checkbox != null && checkbox.length > 0){	
		var check = false;
		for(var i=0; i<checkbox.length; i++){
			if(checkbox[i].checked){
				check = true;
				break;
			}
		}
		if(!check){
			alertMassage(SELECT_APP);
			select = false;
		}
		if(checkbox != null && check){
			select = true;
		}		
	}else{
		alertMassage(SELECT_APP);
		select = false;
	}
	return select;
}
function superReassignSearch(role_action){
	var issuper = false;
	if(role_action == ROLE_SP){
		issuper = true;
	}
	return issuper;
}
