/** 
 * #SeptemWi
 */ 
//window.onbeforeunload = function(){
//	if((window.event.clientX<0)||(window.event.clientY<0)){	
//	}
//};
$(document).ready(function (){
	try{
		parent.leftFrame.closeMenuFrame();
	}catch(e){		
	}
	$('#container').css({
		'height': $(window).height(),
		'width':'100%'
	});
	$('#container').layout({
		 minSize: "33%"
		,maxSize: "70%"		
		,west__size:	"33%"
		,useStateCookie:	false
		,resizable:	$ResizeFrame
		,spacing_closed: 9
		,initClosed: $CloseImage
		,closable:	$CloseFrame
	});
	$('div.ui-layout-center').layout({
		 center__paneSelector: ".center-center"
		,south__paneSelector: ".center-south"
		,south__size: $(window).height()-25
		,closable:	false
		,spacing_open:1
		,resizable:	false
	});
	$('.center-center').css({
		'background-color':'#b5b7b5'
		,'border': '1px solid #b5b7b5'
	});
	$('.center-south').css({
		'border': '1px solid rgb(236, 238, 236)'
	});
	$('div.ui-layout-west').layout({
		 minSize: 128	
		,maxSize: 220
		,center__paneSelector: ".west-center"
		,south__paneSelector: ".west-south"
		,south__size: 128
		,closable:	false
		,spacing_open: 1
		,resizable:	false
	});
	$('.west-center').css({
		'border': '1px solid rgb(236, 238, 236)'
	});
	$('.west-south').css({
		'border': '1px solid rgb(236, 238, 236)'
	});
	if($('#capport_message').val() != null && $('#capport_message').val() != ''){
		alertMassage($('#capport_message').val());
		$('#capport_message').val('');
	}
});
function toggleDiv(divId) {
	$(".slide-out").each(function(index) {
		if( $(this).attr("id")== divId){
			$(this).slideToggle("fast");
		}else{
			if( $(this).is(':visible'))
				$(this).toggle(false);
		}
	});
}
//function refreshApp(){
//	alert('refresh');
//	appFormName.action.value="loadAppForm";
//	appFormName.handleForm.value = "N";
//	appFormName.submit();
//}