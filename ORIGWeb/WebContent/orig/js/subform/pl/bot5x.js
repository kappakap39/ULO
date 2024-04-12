
$(document).ready(function(){
	if($('#displaymode-bot5x').val() == DISPLAY_MODE_EDIT){
		$('.textbox-bot5x').blur(function(){
			calculateBOT5X();
		});
	}
});
function calculateBOT5X(){
	var dataString = 'className=com.eaf.orig.ulo.pl.app.rule.servlet.CalculateBOT5X&returnType=0';
		dataString += '&'+$('#div-bot5x *').serialize();
	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){
			jsonDisplayElementById(data);
			SensitiveAttrNameEngine('bot5x_total');
		},
		error : function(data){
		}
	});
}