/**
 * #SeptemWi
 */
$(document).ready(function (){
	if($('#displayMode-decision').val() == DISPLAY_MODE_EDIT){
		DefaultDecision();
	}
	GetDecisionReason();
});
function GetDecisionReason(){
	 var obj = $('input[name=decision_option]:checked');
	 if(obj == undefined) obj = '';
	 var reasonCode = obj.attr('reason');
	 if(reasonCode == undefined) reasonCode = '';
	 if(reasonCode == null || reasonCode == 'null' || reasonCode == ''){
		 $('#span_mandate_reason').html('');
	 }else{
		 $('#span_mandate_reason').html('*'); 
	 }
//	 #septemwi change new logic get reason
	 var dataString  = 'className=GetDecisionReason&packAge=0&returnType=1';
 	 	 dataString += '&displayMode-decision='+$('#displayMode-decision').val()+'&reasonCode='+reasonCode;
 		jQuery.ajax({
 			type :"POST",
 			url :"AjaxDisplayServlet",
 			data :dataString,
 			async :true,
 			success : function(data ,status ,xhr){			
 				htmlDisplayElementById(data,'div-decision-reason');
 				 var decision = document.getElementsByName("decision_option");
 				 for(var i=0; i<decision.length; i++){
 					 if(decision[i].checked == true){
 						 var thDesc = document.getElementsByName("decision_option")[i].getAttribute("thDesc");
 						 $('#thDescDecision').val(thDesc);
 					 }
 				 }
 			},
 			error : function(response){
 			}
 		});
 		
// 	 #septemwi comment
//	 ajaxDisplayElementHtmlAsync('GetDecisionReason', packageDefault, dataString,'div-decision-reason');
//	 var decision = document.getElementsByName("decision_option");
//	 for(var i=0; i<decision.length; i++){
//		 if(decision[i].checked == true){
//			 var thDesc = document.getElementsByName("decision_option")[i].getAttribute("thDesc");
//			 $('#thDescDecision').val(thDesc);
//		 }
//	 }
 	
}
function DefaultDecision(){
	if($('input[name=decision_option]:checked').length == 0){
//		#septem comment not default decision Confirm Reject
//		$('input[name=decision_option]').each(function(){
//			 var val = $(this).attr("value");
//			 if(val == 'Confirm Reject'){
//				 $(this).attr("checked",true);
//			 }
//		});
		$('input[name=decision_option]').each(function(){
			 var val = $(this).attr("value");
			 if(val == ACTION_SEND){
				 $(this).attr("checked",true);
			 }
		});
	}
}