/**
 * Pipe
 */
$(document).ready(function (){
	SetAddressFunction();
	SetAddressTextDesc();	
});
function SetAddressFunction(){
	CheckAddressButton();
	//$('Td.ResultOdd').unbind();
	$("Tr.ResultOdd").hover(
	    function(){$(this).addClass("ResultOdd-haver");},
	    function(){$(this).removeClass("ResultOdd-haver");
	});
	//$('Td.ResultEven').unbind();
	$("Tr.ResultEven").hover(
	    function(){$(this).addClass("ResultEven-haver");},
	    function(){$(this).removeClass("ResultEven-haver");
	});	
	$('Td.showAddress').unbind();
	$('Td.showAddress').click(function(){
		var displayMode = $('#address_displayMode').val();
		var obj = $(this).attr('value').split(',');
		var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadPLAddressPopup&seq="+obj[0]+"&type="+obj[1]+"&personalType="+obj[2]+"&mode="+displayMode;
		LoadAddressPopup(url, displayMode);
	});	
}

function addAddress(){
	var addressType = "";
	var personalType = $('#personalType').val();
	var displayMode = $('#address_displayMode').val();
	var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadPLAddressPopup&addressType="+addressType+"&personalType="+personalType+"&mode="+displayMode;	
	LoadAddressPopup(url,displayMode);
}

function LoadAddressPopup(url, displayMode){		
	var width = 1300;
	var higth = 530;
	var title = ADDRESS_INFO;	
	$dialog = $dialogEmpty;	
	$(".plan-dialog").remove();
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	var frameModule = (document.getElementById('frame-module')== null)? document.body :'.frame-module';
	var buttonArray;
	if(displayMode=='VIEW'){
		buttonArray = {"Close" : function() {$dialog.dialog("close");	closeDialog();}};
	}else{
		buttonArray = {"Save" : function() {if(SaveAddressPopup()){$dialog.dialog("close");	closeDialog();}}
						,"Close" : function() {$dialog.dialog("close");	closeDialog();}};
	}
	$dialog.dialog({
	    resizable : false,
	    modal : true,
	    autoOpen: false,
	    draggable: false,
	    closeOnEscape:false,
	    width: width,
	    height: higth,
	    title:title,
	    buttons : buttonArray,
	    position: { 
			        my: 'center',
			        at: 'center',
			        of: $(frameModule)
	    		},
	    close: function(){
	    	try{
	    		dialogWin.win.close();
	    	}catch(e){}
	    	closeDialog();
	    }
    });
	$dialog.dialog('open');	
}

function DisplayAddress(data){	
	if(data != null && data.length >0){
		$(".addressResult").remove();
		$('#noRecordAddress').remove();
		jsonDisplayElementById(data);
	}	
	SetAddressFunction();
	SetAddressTextDesc();	
}
function confirmdelete(){
	var checkedTable = $('#checkbox').is(':checked');
	var checkedSubTable = $(':checkbox').is(':checked');
 	if(checkedTable!=false||checkedSubTable!=false){
		alertMassageYesNoFunc(DELETE_ADDRESS_WARNING,DeleteAddress,null);
 	}else{
 		alertMassage(DELETE_ADDRESS_RECOMMEND);
 	}
}

function checkAllBox(field){
	if($('#'+field).is(':checked')){
		$('#'+field).attr('checked', true);
		$(':checkbox').attr('checked', true);
	}else{
		$('#'+field).removeAttr('checked', false);
		$(':checkbox').removeAttr('checked', false);
	}
}

function DeleteAddress(){
	 var personalType = $('#personalType').val();
	 var dataString = "action=DeletePLAddress&handleForm=N&"+$("#addressResult *").serialize()+"&personalType="+personalType
	 								+'&'+$('#table-mailling-address *').serialize();
	  jQuery.ajax( {
		type :"POST",
		url :'FrontController',
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){			
			if(data != null && data.length >0){
				$(".addressResult").remove();
				$('#noRecordAddress').remove();
				jsonDisplayElementById(data);
				SensitiveAttrNameEngine('address_object');
			}
			SetAddressFunction();
			SetAddressTextDesc();
		},
		error : function(data){
			
		}
	});
}

function GetMailingAddress(){	
	var dataString = "ClassPackage=com.eaf.orig.ulo.pl.product.ajax&ClassName=GetMaillingAddress"; 
	var uri = "AjaxServlet";
	jQuery.ajax( {
		type :"POST",
		url :uri,
		data :dataString,	
		async :false,		
		success : function(data ,status ,xhr) {
			$("#mailling-address").html(data);
		}
	});
}

// #SeptemWi
//function DeleteMailingAddress(){
//	var dataString = "ClassPackage=com.eaf.orig.ulo.pl.product.ajax&ClassName=GetMaillingAddress"; 
//	var uri = "AjaxServlet";
//	jQuery.ajax( {
//		type :"POST",
//		url :uri,
//		data :dataString,	
//		async :false,	
//		success : function(data ,status ,xhr){
//				$("#mailling-address").html(data);
//				$("#card_link_address1").val('');
//				$("#card_link_address2").val('');
//		}
//	});
//}

function GetCardlink(){
	var dataString = "mailling="+$('#mailling_address').val();
	ajaxDisplayElementJsonAsync('GetCardLinkLine',packagePlProduct,dataString);
}

function CheckAddressButton(){
	var disable = $('#button-address-display').val();
	var addressSize = $("#addressResult tr").length;
	if(addressSize>3||disable=="disabled"){
		$('#addHomeBnt').attr("disabled", "disabled");
	}else{
		$('#addHomeBnt').removeAttr("disabled");
	}
}

//#SeptemWi
//function SetAddressEffect(TrID){
//	var Tr_ID = '#'+TrID;
//	if($(Tr_ID).hasClass("ResultOdd")){
//		$(Tr_ID).hover(
//	    	 function(){$(Tr_ID).addClass("ResultOdd-haver");},
//	    	 function(){$(Tr_ID).removeClass("ResultOdd-haver");
//	    });
//	}else{
//		$(Tr_ID).hover(
//	    	 function(){$(Tr_ID).addClass("ResultEven-haver");},
//	    	 function(){$(Tr_ID).removeClass("ResultEven-haver");
//	    });	
//	}
//}
//function showAddress(trID){
//	//Fix by Vikrom  DF000000000571
//	var displayMode = $('#address_displayMode').val();
//	var Tr_ID = '#'+trID+">td";
//	$('.showAddress').click(function(){
//		var obj = $(Tr_ID+'.showAddress').attr('value').split(',');
//		var url = CONTEXT_PATH_ORIG+"FrontController?action=LoadPLAddressPopup&seq="+obj[0]+"&type="+obj[1]+"&personalType="+obj[2]+"&mode="+displayMode;
//		LoadAddressPopup(url);
//	});	
//}
//function unCheck(){
//	$('#checkbox').attr('checked', false);
//}

function SetAddressTextDesc(){
	var role = $('#address_currentrole').val();
	if(role==ROLE_DC||role==ROLE_VC){
		$('Td.showAddress').addClass('text-verify-bold');
	}
}

function clearAddressResult(){
//	$('#addressResult tr').each(function(){
//		$(this).find($('Tr.addressResult').remove());
//		$(this).find($('#noRecordAddress').remove());
//	});
//	Clear address data  #Vikrom 20121112
//	$.post('FrontController?action=ClearDataAddressWebAction&handleForm=N');
//	$('#addressResult').append('<tr class=\"ResultNotFound ResultEven\" id=\"noRecordAddress\"><td colspan=\"8\" align=\"center\">No record found</td></tr>');
//	$("#mailling-address option[value!='']").each(function(){$(this).remove();});
//	$('#card_link_address1').val('');
//	$('#card_link_address2').val('');
}