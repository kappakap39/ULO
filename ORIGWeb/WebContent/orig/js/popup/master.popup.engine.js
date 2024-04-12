/**
 * #SeptemWi
 */
$(document).ready(function(){
	LoadMasterPopupTool();
});
function LoadMasterPopupTool(){
	var dataString = 'action='+$('#module-webaction').val()+'&handleForm=N&'+$("div#div-master-popup :input").serialize();
	jQuery.ajax( {
		type :"POST",
		url :"FrontController",
		data :dataString,
		async :false,
		success : function(data ,status ,xhr){			
			
		},
		error : function(data){
		}
	});
}
