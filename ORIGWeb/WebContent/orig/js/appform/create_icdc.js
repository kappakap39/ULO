/**
 * #SeptemWi
 */
$(document).ready(function (){
	$('.PanelSecond').css({
		'height': $(window).height()-60
	});
});
function createICDC(){
	if(validateCardNo()){
		blockScreen();
	    var form = document.appFormName;
		form.formID.value="KEC_ICDC_FORM";
		form.currentTab.value="MAIN_TAB";
		form.action.value="CreateICDC";
		form.handleForm.value = "N";
		form.submit();
	}
}
function validateCardNo(){
	var cardNoObj = document.getElementById("card_no");
	if(cardNoObj.value == ""){
		alertMassageSelection('\u0E44\u0E21\u0E48\u0E1E\u0E1A\u0E02\u0E49\u0E2D\u0E21\u0E39\u0E25\u0E2B\u0E21\u0E32\u0E22\u0E40\u0E25\u0E02\u0E1A\u0E31\u0E15\u0E23\u0E17\u0E35\u0E48\u0E23\u0E30\u0E1A\u0E38');
		return false;
	}else{
		return true;
	}
}