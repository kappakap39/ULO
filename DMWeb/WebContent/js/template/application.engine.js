/**
 * #SeptemWi
 */
$(document).ready(function (){
	try{StartUpSensitiveTool();}catch(e){}
	try{StatupNotification();}catch(e){}	
});
function StartUpSensitiveTool(){
	var dataString = "className=com.eaf.orig.ulo.pl.mandatory.StartUpSensitiveTool&returnType=0";
	$.ajax({
		type :"POST",
		url :'AjaxDisplayServlet',
		data :dataString,	
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){
			if(data != null && data.length > 0){
				$.map(data, function(item){
					if($(item.value).attr('id') != undefined){
						SensitiveEngine(item.value);
					}
				});
			}
		},
		error : function(data){
		}
	});
}
