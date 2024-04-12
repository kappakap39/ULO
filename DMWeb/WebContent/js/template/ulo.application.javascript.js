/**
 * Rawi Songchaisin
 */

/**
 * @author septemwi
 * function checkboxAll 
 * Send id Field checkbox All and field select for Check box please Add Class checkBoxAll
 * Used For Select Check Box All
 * */
function checkboxAll(fieldAll){
	var filed = document.getElementById(fieldAll);	
	if(filed.checked){
		$('.checkBoxAll').each(function(){
			$(this).attr("checked","checked");
		});
	}else{
		$('.checkBoxAll').each(function(){
			$(this).attr("checked",false);
		});
	}	
}
function EngineCheckboxAll(fieldAll,fieldCheck){
	var filed = document.getElementById(fieldAll);
	if(filed.checked){
		$(fieldCheck).each(function(){
			$(this).attr("checked","checked");
		});
	}else{
		$(fieldCheck).each(function(){
			$(this).attr("checked",false);
		});
	}	
}
function ToolCheckboxAll(fieldAll,fieldCheck){
	var filed = document.getElementById(fieldAll);
	if(filed.checked){
		$('.'+fieldCheck).each(function(){
			$(this).attr("checked",true);
		});
	}else{
		$('.'+fieldCheck).each(function(){
			$(this).attr("checked",false);
		});
	}	
}
function ismaxlength(obj){
	var mlength=obj.getAttribute? parseInt(obj.getAttribute("maxlength")) : "";
	if (obj.getAttribute && obj.value.length>mlength)
		obj.value=obj.value.substring(0,mlength);
}
function removecheckboxall(elementID){
	$('#'+elementID).attr('checked', false);
}