/**
 * #SeptemWi
 */
var $NotifiNotFound  = 	'<div class="notifications-notfound">Not found Notifications</div>';
var $NotificationObj = 	'<div class="notification-frame-before"></div><div class="notification-frame-wrapper">'+
					  	'<div class="notifications-header"><div class="inline-block">Notification Message</div>'+
					  	'<div class="inline-block inline-block-close"><img class="close-img" src="images/close.png"/></div>'+
					  	'</div><div class="notification-body" id="notification-body">'+
					  	'<div class="notifications-notfound">Not found Notifications</div>'+
					  	'</div><div class="notifications-footer"></div></div>';
var $MaxHeight = 340;
$(document).ready(function (){
	InitNotification();	
});
function StatupNotification(){
	DestoryErrorField();
	var dataString = "className=NotificationMessage&packAge=4&returnType=0";
	jQuery.ajax( {
		type :"POST",
		url :'AjaxDisplayServlet',
		data :dataString,	
		async :true,	
		dataType: "json",
		success : function(data ,status ,xhr){
			if(data != null && data.length > 0){
				DisplayErrorField(data);
				HoverNotification();
				BubbleNotification();
			}
		},
		error : function(data){
		}
	});
}
function InitNotification(){
	HoverNotification();	
	HoverNotePad();
	NotificationTab();
	NotifyNotePad();
}
function NotificationTab(){
	$("div.notification-tab-message").click(function() {
        $("div.notification-tab-message").removeClass("notification-tab-active");
        $(this).addClass("notification-tab-active");
        $('#notification-message').css({
        	'display':'none'
        });
        $('#notification-notepad').css({
        	'display':'none'
        });        
        var attrid = $(this).attr('id');
        if(attrid == 'tab-notification'){
            $('#notification-message').css({
            	'display':''
            });
            ScrollNotification();
            return false;
        }
        if(attrid == 'tab-notepad'){
        	 $('#notification-notepad').css({
             	'display':''
             });
        	 ScrollNotePad(); 	
        	 return false;
        }
    });
	$('img.close-img').click(function(){
		 $('#notification-notepad').css({
	        	'display':'none'
	      });
		 $('#notification-message').css({
	        	'display':'none'
	     });
		 $("div.notification-tab-message").removeClass("notification-tab-active");
	});
	$("div.notification-tab-message").hover(
	    function (){
	    	if($(this).hasClass('notification-tab-active')){
	    		return false;
	    	}
	        $(this).addClass("notification-tab-hover");
	    },
	    function (){
	        $(this).removeClass("notification-tab-hover");
	    }
	);
}
function NotifyNotePad(){
	$('#add-notepad').click(function(){
		if($("#notepad-input").val()== ''){
			alertMassage(ERROR_INPUT_NOTEPAD);
			return false;
		}		
		var notepadvalue = $("#notepad-input").attr("value");
			notepadvalue = notepadvalue.replace(/\n\r?/g,'<br/>'); 
		var dataString = 'className=NotePadNotification&packAge=0&returnType=0&notepad-input='+encodeURI(notepadvalue)+'&type-notepad=ADD';
		$('#notepad-notfound').remove();
//		ajaxDisplayElementJson("NotePadNotification",packageDefault,dataString);
//		ScrollNotePad();	
//		$("#notepad-input").val('');
//		HoverNotePad();
		jQuery.ajax( {
			type :"POST",
			url :'AjaxDisplayServlet',
			data :dataString,	
			async :true,	
			dataType: "json",
			success : function(data ,status ,xhr){
				if(data != null && data.length > 0){
					jsonDisplayElementById(data);
					ScrollNotePad();
					$("#notepad-input").val('');
					HoverNotePad();
					BubbleNotePad();
				}
			},
			error : function(data){
			}
		});
		return false;
	});
	BubbleNotePad();
}
function BubbleNotePad(){
	var bubble = $('.notepad-text').length;
	if(bubble > 0){
	   $('#notepad-bubble').html(bubble);
	   $('#notepad-bubble').css({
	   		'display':''
	   });		
	}else{
	   $('#notepad-bubble').css({
	   		'display':'none'
	   });
	} 
}
function HoverNotePad(){
	$('div.notepad-obj').unbind();
	$('div.notepad-obj').hover(
			function () {$(this).addClass("notepad-obj-hover");},
		    function () {$(this).removeClass("notepad-obj-hover");}
	);
}
function HoverNotification(){
	$('div.notifications-error').unbind();
	$("div.notifications-error").hover(
		    function () {$(this).addClass("notifications-error-hover");},
		    function () {$(this).removeClass("notifications-error-hover");}
		);
}
function DestoryNotification(){	
	$('#notification-message').html('');
	$('#notification-message').append($NotificationObj);
	$('img.close-img').click(function(){
		 $('#notification-notepad').css({
	        	'display':'none'
	      });
		 $('#notification-message').css({
	        	'display':'none'
	     });
		 $("div.notification-tab-message").removeClass("notification-tab-active");
	});
	BubbleNotification();
	ScrollNotification();
	CloseNotification();
}
function OpenNotification(){
	CloseNotification();
	$('#notification-message').css({
	   		'display':''
	});
	 $("div.notification-tab-message").removeClass("notification-tab-active");
	 $("#tab-notification").addClass("notification-tab-active");
	ScrollNotification();
	BubbleNotification();
}
function CloseNotification(){
   $('#notification-message').css({
   		'display':'none'
   });
   $('#notification-notepad').css({
  		'display':'none'
   });
}
function BubbleNotification(){
	var bubble = $('.notifications-error').length;
	if(bubble > 0){
	   $('#notification-tab-bubble').html(bubble);
	   $('#notification-tab-bubble').css({
	   		'display':''
	   });		
	}else{
	   $('#notification-tab-bubble').css({
	   		'display':'none'
	   });
	} 
}
function PushNotifications(message){
	$('.notifications-notfound').remove();
	$("#notification-body").prepend('<div class="notifications-error">'+message+'</div>');
}
function PerpendNotify(message){
	$('.notifications-notfound').remove();
	$("#notification-body").prepend('<div class="notifications-error">'+message+'</div>');
	HoverNotification();
	BubbleNotification();
}
function PerpendNotifyID(message ,elementID){
	$('.notifications-notfound').remove();
	$("#notification-body").prepend('<div class="notifications-error" id="'+elementID+'">'+message+'</div>');
	HoverNotification();
	BubbleNotification();
}
function PushErrorNotifications(message){
	DestoryNotification();
	PushNotifications(message);	
	OpenNotification();
}
function PushErrorNotificationsNotAlert(message){
	DestoryNotification();
	PushNotifications(message);
	BubbleNotification();
}
function ScrollNotePad(){
	var height = $('#notepad-body').outerHeight()+8;
    if(height == 0) height = 35;
    if(height > $MaxHeight){
    	height = $MaxHeight;
    	$('#notepad-body').slimScroll({
	 		height: height+'px'
	 	});
    }
}
function ScrollNotification(){
    var height = $('#notification-body').outerHeight()+8;
    if(height == 0) height = 30;
    if(height > $MaxHeight){
    	height = $MaxHeight;
    	$('#notification-body').slimScroll({
    		height: height+'px'
    	});
    }
}