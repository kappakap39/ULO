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
	$("Tr.odd-result-01").hover(
    	 function(){$(this).addClass("ResultOdd-haver-non-pointer");},
    	 function(){$(this).removeClass("ResultOdd-haver-non-pointer");}
    );
	$("Tr.even-result-01").hover(
		function(){$(this).addClass("ResultEven-haver-non-pointer");},
	    function(){$(this).removeClass("ResultEven-haver-non-pointer");}
	);	
	
	if($('#table_tracking_role').attr('id') != undefined){
		getTrackingRole();
		$('#tracking_group').change(function(){
			getTrackingRole();
		});
	}
	
	if($('#checkSearch').val() == "Y"){			
		countTracking();		
	}
	
	 var form =  document.appFormName;
	 if($(form).length > 0){
	     $(form).keypress(function (e){
	         code = e.keyCode ? e.keyCode : e.which;
	           if(code.toString() == 13){
	        	   searchTracking();
	           }
	     });
	 }
	 
//	 #septem focus screen
	 $('#button-search').focus();
	 
});
function getTrackingRole(){
	var tracking_group = $('#tracking_group').val();
	var dataString 	= 'className=com.eaf.orig.ulo.pl.product.ajax.TrackingRole&returnType=0&tracking_group='+tracking_group;
	jQuery.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){
		if(data!= null && data.length >0){
			jsonDisplayElementById(data);
		}
	},
	error : function(data){
	}
	});
}
function countTracking(){
	var dataString 	= 'className=com.eaf.orig.ulo.pl.product.ajax.TrackingCount&returnType=0&'+$('#div-tracking-search *').serialize();
	jQuery.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,	
		dataType: "json",
		success : function(data ,status ,xhr){
		if(data!= null && data.length >0){
			jsonDisplayElementById(data);
		}
	},
	error : function(data){
	}
	});
}

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
function searchTracking(){
	try{
		if(validate()){
			blockScreen();
			appFormName.checkSearch.value = "Y";
			appFormName.action.value = "SearchTracking";
			appFormName.handleForm.value = "N";
			appFormName.submit();	
		}
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}
function validate(){
//	#septem validate countChar2
	if(!countChar2(appFormName.empFirstName)){
		return false;
	}
	if(!countChar2(appFormName.empLastName)){
		return false;
	}
//	end #septem validate countChar2
	return true;
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
