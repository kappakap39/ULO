/**
 * @Pipe
 */

$(document).ready(function(){
	
	//for check role ca or ca sup  #Vikrom  20121126
	var decisionRole = $('#decisionRole').val();
	if(decisionRole=='CA'){
		
		processRecommendDecision();
		GetDecisionReason();
		getFinalCreditLineRason();
	
		$('#ca_final_credit').blur(function(){
			addComma(this);
			returnZero(this);
		});
		$('#ca_final_credit').change(function(){
			adjustFinalCredit();
			if(overOverideCredit() && !$('#ca_final_credit').is('[readonly]')){
				var policyDocNo   = $('#ca_decision_refference').val();
				if(null == policyDocNo || '' == policyDocNo){
					alertMassageSelection(FINAL_MORE_RECOMMEND,'ca_final_credit');
				}else{
					alertMassageSelection(FINAL_MORE_BOT5X,'ca_final_credit');
				}
			}else if(lessThanMinCredit() && !$('#ca_final_credit').is('[readonly]')){
				alertMassageSelection(MIN_FINAL +' '+$('#min_credit_line').val()+' '+ BATH,'ca_final_credit');
			}
			reCalDecisionILog(); 
			getFinalCreditLineRason();
			changeCADecision();
			$('#tmp_final_credit').val($('#ca_final_credit').val());
		});
		$('#ca_final_credit').keypress(function(){
			isNumberOnKeyPress(this);	
			keyPressInteger();
		});
		$('#ca_final_credit').keyup(function(){
			isNumberOnkeyUp(this);		
		});
		$('#ca_final_credit').focus(function(){
			removeCommaField(this);		
		});
	}
});

function changeCADecision(){
	var dataString = $("#avale-obj-form *").serialize();
	ajaxDisplayElementHtml('CaDecisionAuthorize', packageDefault, dataString, 'default_decision');
	processRecommendDecision();
	GetDecisionReason();
}

function GetDecisionReason(){
	var obj = $('input[name=decision_option]:checked');
	if(obj == undefined) obj = '';
	var reasonCode = obj.attr('reason');
	if(reasonCode == null || reasonCode == 'null' || reasonCode == ''){
		 $('#span_mandate_reason').html('');
	}else{
		 $('#span_mandate_reason').html('*'); 
	}
	if(reasonCode == undefined) reasonCode = '';
	var dataString = 'decision_option='+obj.val()+'&displayMode-decision='+$('#displayMode-decision').val()
	 						+'&reasonCode='+reasonCode;
	ajaxDisplayElementHtmlAsync('GetDecisionReason', packageDefault, dataString,'div-decision-reason');
}
function getFinalCreditLineRason(){
 	 var finalCreditReason = $('#final_credit_reason').val();
	 if(finalCreditReason == undefined) finalCreditReason = '';
	 var dataString = 'reasonCode='+finalCreditReason+'&displayMode-decision='+$('#displayMode-decision').val()
	 						+'&final_credit_line='+ removeCommas($('#ca_final_credit').val())
	 						+'&recommend_credit_line='+removeCommas($('#ca_recommend_credit').val());
     //alert(dataString);
	 ajaxDisplayElementHtmlAsync('GetFinalCreditLineReason', packageDefault, dataString,'div_final_credit_reason');
}
function overOverideCredit(){
	var policyDocNo   = $('#ca_decision_refference').val();
	var finalCredit = removeCommas($('#ca_final_credit').val());
	if(finalCredit == '') finalCredit = '0';
	if(null == policyDocNo || '' == policyDocNo){
		var recommendCredit = removeCommas($('#ca_recommend_credit').val());
		if(recommendCredit == '') recommendCredit = '0';
		if(parseFloat(finalCredit) > parseFloat(recommendCredit)){
			$('#ca_final_credit').val(removeCommas($('#ca_recommend_credit').val()));
			return true;
		}else{
			return false;
		}
	}else{
		var exceptionCredit = removeCommas($('#ca_exception_credit').val());
		if(exceptionCredit == '') exceptionCredit = '0';
		if(parseFloat(finalCredit) > parseFloat(exceptionCredit)){
			$('#ca_final_credit').val(removeCommas($('#ca_exception_credit').val()));
			return true;
		}else{
			return false;
		}
	}
}
function lessThanMinCredit(){
	var minCredit = removeCommas($('#min_credit_line').val());
	var finalCredit = removeCommas($('#ca_final_credit').val());
	if(minCredit   == '') minCredit   = '0';
	if(finalCredit == '') finalCredit = '0';
	if(parseFloat(finalCredit) < parseFloat(minCredit)){
		$('#ca_final_credit').val(removeCommas($('#min_credit_line').val()));
		return true;
	}else{
		return false;
	}
}
function adjustFinalCredit(){
	var finalCredit = removeCommas($('#ca_final_credit').val());
	var adjustCredit = parseInt(finalCredit/1000)*1000;
	$('#ca_final_credit').val(adjustCredit);
}
function processRecommendDecision(){
	var objDefaultDecision = eval("document.appFormName.default_decision");
	if(objDefaultDecision != null && objDefaultDecision.value !=""){
		if($('#displayMode-decision').val() == DISPLAY_MODE_EDIT){
			autoCheckRadioDecision(objDefaultDecision.value);
		}
	}
}

function autoCheckRadioDecision(decision){
	var objRadioDecision = eval("document.appFormName.decision_option");
	for (var i=0; i<objRadioDecision.length; i++){
		if(objRadioDecision[i].value == decision){
			objRadioDecision[i].checked = true;
			return;
		}
	}
	//remove checked radio #Vikrom 20130114
	$('input[name=decision_option]').each(function(){	
		 $(this).attr("checked",false);
	});
}
function defultPolicyException(obj){
	//20130402 sankom add recal summary
	//var objDefaultDecision = eval("document.appFormName.default_decision");
	//objDefaultDecision.value='';	

//	#septem comment change logic defultPolicyException call ILOG job state STI1511 (I_SUP_CA) not call 
//	reCalDecisionILog();	
	var jobState = $('#jobState').val();
	if(jobState == 'STI1511'){
		return;
	}
	
	reCalDecisionILog();
	
	var recommendResult = $('#ca_recommend_result').html();
	if(recommendResult == RECOMMEND_DESC_REJECT){
		$('#ca_final_credit').attr("readOnly",true);
		$('#ca_final_credit').addClass("textboxReadOnly");
	}else{
		$('#ca_final_credit').attr("readOnly",false);
		$('#ca_final_credit').removeClass("textboxReadOnly");
	}
	
	if(obj.value!=''){ var decision='Policy Exception';
	var objRadioDecision = eval("document.appFormName.decision_option");
	for (var i=0; i<objRadioDecision.length; i++){
		if(objRadioDecision[i].value == decision){
			if(!objRadioDecision[i].checked){
			objRadioDecision[i].checked = true;
			}
		}
	 }
	}else{
		if(!$('#ca_final_credit').is('[readonly]') && overOverideCredit()){
			var policyDocNo   = $('#ca_decision_refference').val();
			if(null == policyDocNo || '' == policyDocNo){
				alertMassageSelection(FINAL_MORE_RECOMMEND,'ca_final_credit');
			}else{
				alertMassageSelection(FINAL_MORE_BOT5X,'ca_final_credit');
			}
		}else if(!$('#ca_final_credit').is('[readonly]') && lessThanMinCredit()){
			alertMassageSelection(MIN_FINAL +' '+$('#min_credit_line').val()+' '+ BATH,'ca_final_credit');
		}
	}
	getFinalCreditLineRason();
	
	changeCADecision();
	GetDecisionReason();
}