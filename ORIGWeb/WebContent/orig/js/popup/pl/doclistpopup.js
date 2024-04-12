/**
 * Rawi Songchaisin
 */
var $maxHeight = 300;
$(document).ready(function (){
	$("#doc-add-button").hover(
	    function(){$(this).addClass("button-hover");},
	    function(){$(this).removeClass("button-hover");}
	);
	$("#doc-delete-button").hover(
	    function(){$(this).addClass("button-hover");},
	    function(){$(this).removeClass("button-hover");}
	);
	
	ColorTrackDoc('.radio-doc');
	
//	#septem comment
//	SensitiveRadioButton('radio_29');	
	
	DocListRadioButton();
	
	var table_doc_height = ($('#table-doc-master').attr('id') != undefined) ?$('#table-doc-master').outerHeight()+25:25;
	if(table_doc_height > $maxHeight){
		$('.div-doc-master-popup').slimScroll({
			height: $maxHeight+'px'
		});	
	}else{
		$('.div-doc-master-popup').slimScroll({
			height: table_doc_height+'px'
		});
	}
	var table_otherdoc_height = ($('#table-other-doc').attr('id') != undefined) ?$('#table-other-doc').outerHeight()+25:25;
	if(table_otherdoc_height > $maxHeight){
		$('.div-otherdoc-popup').slimScroll({
			height: $maxHeight+'px'
		});	
	}
});
function DocListRadioButton(){
	try{
		var field = ($('#sensitive-doccode').val()).split(',');
		for(var i=0; i<field.length; i++){
			try{
				var attr = $.trim(field[i]);
				if($('#radio_'+attr).attr('name') != undefined){					
					DocListSensitiveRadioButton('radio_'+attr);
				}
			}catch(e){				
			}
		}
	}catch(e){		
	}
}
function DocListAttrNameEngine(){
	try{
		var field = ($('#sensitive-doccode').val()).split(',');
		for(var i=0; i<field.length; i++){
			try{
				var attr = $.trim(field[i]);
				if($('#radio_'+attr).attr('name') == undefined){
					DocListSensitiveAttrNameEngine('radio_'+attr);
				}
			}catch(e){				
			}
		}
	}catch(e){		
	}
}
function ColorTrackDoc(attr){
	$(attr).unbind();
	$(attr).click(function (){
		if($(this).attr('id') != undefined){
			try{
				var value = $('input:radio[name='+$(this).attr('name')+']:checked').val();
				var docID = $(this).attr('id').replace('radio_','');
				$('tr#'+docID).removeClass('ResultBlue');
				if('T' == value){
					$('tr#'+docID).addClass('ResultBlue');
				}
			}catch(e){				
			}
		}
	});
}
function addOtherDoc(){
	var docID = $('#doc-others-list').val();
	if(docID == '') return;
	var dataString = "className=AddOtherDocument&packAge="+packageOrigPl+"&returnType=0&docID="+docID;	
	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(response ,status ,xhr){
			$("#doclist-notfound").remove();
			jsonDisplayElementById(response);
			var table_otherdoc_height = ($('#table-other-doc').attr('id') != undefined) ?$('#table-other-doc').outerHeight()+25:25;
			if(table_otherdoc_height > $maxHeight){
				$('.div-otherdoc-popup').slimScroll({
					height: $maxHeight+'px'
				});	
			}
			ColorTrackDoc('.radio-doc');
			DocListSensitiveAttrNameEngine('radio_'+docID);
		},
		error : function(response){
		}
	});	
}
function deleteOtherDoc(){
	var dataString = $("#table-other-doc *").serialize();
	ajaxJsonManual('DeleteOtherDocument',packageOrigPl,dataString,displayDeleteOtherDoc);
}
function displayDeleteOtherDoc(data){
	jsonDisplayElementById(data);
	var table_otherdoc_height = ($('#table-other-doc').attr('id') != undefined) ?$('#table-other-doc').outerHeight()+25:25;
	if(table_otherdoc_height > $maxHeight){
		$('.div-otherdoc-popup').slimScroll({
			height: $maxHeight+'px'
		});	
	}
	ColorTrackDoc('.radio-doc');
//	#septemwi modify sensitive field
//	if($('#radio_29').attr('name') == undefined){
//		SensitiveAttrNameEngine('radio_29');
//	}
	DocListAttrNameEngine();
}
function saveDocList(){
	if(MandatoryDocumentCheckList()){
		$('#div-doc-madatory').html('');
		var dataString = $("#doc-list-popup *").serialize();
		$AjaxFrontController('SaveDocListWabAction','N',null,dataString,displayDocStatus);	
	}
}
function displayDocStatus(data){
	jsonDisplayElementById(data);
	if($('#doc-final-status').val() == '03'){
		$('input[name=decision_option]').each(function(){
			 var val = $(this).attr("value");
			 if(val == ACTION_REQUEST_FU){
				 $(this).attr("checked",true);
			 }
		});
		GetDecisionReason();
	}else{
		$('input[name=decision_option]').each(function(){
			 var val = $(this).attr("value");
			 if(val == ACTION_SEND){
				 $(this).attr("checked",true);
			 }
		});
		GetDecisionReason();
	}
	$dialog.dialog("close");	
	closeDialog();
}
function SendEmailToBranch(){
	if(MandatoryDocumentCheckList()){
		$('#div-doc-madatory').html('');		
		var dataString = $("#doc-list-popup *").serialize();
		$AjaxFrontController('SaveDocListWabAction','N',null,dataString,doSendEmailToBranch);
	}
	return false;		
}
function StartFollow(){
	if(MandatoryDocumentCheckList()){
		$('#div-doc-madatory').html('');		
		var dataString = $("#doc-list-popup *").serialize();
		$AjaxFrontController('SaveDocListWabAction','N',null,dataString,doStartFollow);
	}
	return false;	
}
function doStartFollow(data){
	jsonDisplayElementById(data);
	if($('#doc-final-status').val() == '03'){
		$('input[name=decision_option]').each(function(){
			 var val = $(this).attr("value");
			 if(val == ACTION_REQUEST_FU){
				 $(this).attr("checked",true);
			 }
		});
		GetDecisionReason();
	}else{
		$('input[name=decision_option]').each(function(){
			 var val = $(this).attr("value");
			 if(val == ACTION_SEND){
				 $(this).attr("checked",true);
			 }
		});
		GetDecisionReason();
	}
//	MandatoryStartFollow();
	StartFollowAction();
}
function MandatoryStartFollow(){
//	$('#div-doc-madatory').html('');
//	#septemwi comment not mandatory
//	var dataString  = "className=MandatoryFollowDoc&packAge=2&returnType=0&"+$("#doc-list-popup *").serialize();	
//		dataString += "&seller_branch_code="+$('#seller_branch_code').val()+"&saleId="+$('#seller_title').val()
//							+"&refId="+$('#ref_recommend_title').val();
//	$.ajax({
//		type :"POST",
//		url :'AjaxDisplayServlet',
//		data :dataString,	
//		async :false,	
//		dataType: "json",
//		success : function(data ,status ,xhr){
//			if(data != null && data.length > 0){
//				var message = '';
//				$.map(data, function(item){
//					message = '<div style="font-family: Verdana; font-size: 16px;">'+item.value+'</div>';
//				});
//				alertMassage(message);
//			}else{
//				$('#div-doc-madatory').html('');
//				StartFollowAction();
//			}
//		},
//		error : function(data){			
//		}
//	});
}
function StartFollowAction(){
	$('#div-doc-madatory').html('');
	try{
	  	var dataString = 'action=StartFollowWebAction&handleForm=N';
		blockScreen();
		$.post('FrontController',dataString,function(data,status,xhr){	
			unblockScreen();
			jsonDisplayElementById(data);
			$('.boxy-wrapper').remove();
		  	$('.boxy-modal-blackout').remove();
		  	$dialog.dialog("close");	
		  	closeDialog();
	    }).error(function(){
		    unblockScreen();
	    });
	}catch(e){
		unblockScreen();
	}
}

function doSendEmailToBranch(data){
	jsonDisplayElementById(data);
	if($('#doc-final-status').val() == '03'){
		$('input[name=decision_option]').each(function(){
			 var val = $(this).attr("value");
			 if(val == ACTION_REQUEST_FU){
				 $(this).attr("checked",true);
			 }
		});
		GetDecisionReason();
	}else{
		$('input[name=decision_option]').each(function(){
			 var val = $(this).attr("value");
			 if(val == ACTION_SEND){
				 $(this).attr("checked",true);
			 }
		});
		GetDecisionReason();
	}
	MandatoryFollowDoc();
}
function MandatoryFollowDoc(){
	$('#div-doc-madatory').html('');
	var dataString  = "className=MandatoryFollowDoc&packAge=2&returnType=0&"+$("#doc-list-popup *").serialize();	
		dataString += "&seller_branch_code="+$('#seller_branch_code').val()+"&saleId="+$('#seller_title').val()
							+"&refId="+$('#ref_recommend_title').val();
	$.ajax({
		type :"POST",
		url :'AjaxDisplayServlet',
		data :dataString,	
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){
			if(data != null && data.length > 0){
				var message = '';
				$.map(data, function(item){
//					#SeptemWi Comment
//					$('#div-doc-madatory').append('<div>'+item.value+'</div>');
					message = '<div style="font-family: Verdana; font-size: 16px;">'+item.value+'</div>';
				});
				alertMassage(message);
			}else{
				$('#div-doc-madatory').html('');
//				jsonDisplayElementById(data);
				ConfirmFollowDoc();
			}
		},
		error : function(data){			
		}
	});
}

//#SeptemWi Comment Change to function MandatoryFollowDoc
//function followDocEmailPopup(data){
//	if(data == 'not_pass'){
//		var obj = [];
//		obj.push('<div>'+EAIL_NO_BRANCH_NO_DOC+'</div>');
//		$.map(obj, function(item){
//			$('#div-doc-madatory').append(item);
//		});
//	}else{
//		jsonDisplayElementById(data);
//		confirmEmailPopup();
//	}
//}

function ConfirmFollowDoc(){
//	#septemwi change to Boxy.ask popup
//	var url = CONTEXT_PATH_ORIG+"/FrontController?action=ConfirmFollowDocEmail";
//	openDialog(url, 500, 250,scrollbars=0, setPrefs);
	 var message  = '<div style="font-family: Verdana; font-size: 14px;">';
	 	 message += '<div id="error-follow-cc-email" class="div-error-mandatory"></div>';
		 message += 'CC Email : <input id="confirm_cc_email" class="textbox-b" name="confirm_cc_email" onblur="validate()" value="" type="text">';
		 message += '</div>';
	 Boxy.ask(message, ["Yes", "No"], function(val) {	
		  $('#error-follow-cc-email').html('');
	      if("Yes" == val){
	    	  try{
		    	  	var dataString = 'action=SendFollowDocEmail&handleForm=N&emailType=FOLLOW_DOC_TO_BRANCH&confirm_cc_email='+$('#confirm_cc_email').val();
		  			blockScreen();
					$.post('FrontController',dataString,function(data,status,xhr){	
						unblockScreen();
						$('.boxy-wrapper').remove();
					  	$('.boxy-modal-blackout').remove();
					  	$dialog.dialog("close");	
					  	closeDialog();
				    }).error(function(){
					    unblockScreen();
				    });
	    	  }catch(e){
	    		  unblockScreen();
	    	  }
	      }
	      if("No" == val){
		  	  $('.boxy-wrapper').remove();
	  		  $('.boxy-modal-blackout').remove();
	  		  $dialog.dialog("close");	
	  		  closeDialog();
	      }  
   }, {title:'<div style="font-family: Verdana; font-size: 16px;">'+MSG_CONFIRM_SEND_EMAIL_FOLLOW+'</div>'});	
}
function validate(){
	$('#error-follow-cc-email').html('');
	var email = $('#confirm_cc_email').val();
	if(email != ""){
		var object = email.split(',');
		var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		for(var i=0;i<object.length;i++){
			var value = object[i];
			if (!filter.test(value)){	
				$('#confirm_cc_email').val('');
				$('#confirm_cc_email').focus();
	    		$('#error-follow-cc-email').html('*'+ERROR_EMAIL);
				break;
			}
		}
	}
}
function closeConfirmDialog(){
//	$dialog2.dialog("close");
//	$dialog2 = $dialogEmpty;
//	$('.plan-dialog-2').remove();
}

function closeDocListPopup(){
	$dialog.dialog("close");	
	closeDialog();
}

function MandatoryDocumentCheckList(){
	$('#div-doc-madatory').html('');
	var dataString = "className=MandatoryDocCheckList&packAge=2&returnType=0&"+$("#doc-list-popup *").serialize();
	var result = false;
	$.ajax({
		type :"POST",
		url :'AjaxDisplayServlet',
		data :dataString,	
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){
			if(data != null && data.length > 0){
				$.map(data, function(item){
					$('#div-doc-madatory').append('<div>'+item.value+'</div>');
				});
				result = false;		
			}else{
				result = true;
			}
		},
		error : function(data){			
		}
	});
	return result;
}