/**
 * @Pipe
 */
$(document).ready(function(){
	$('#p_payment_year').blur(function(){
		var year = $(this).val();
		if(year.length!=4&&year!=0){alertMassageSelection(YEAR_ALERT,'p_payment_year');}
	});
	
	$('#p_payment_month').blur(function(){keyPressMonth('p_payment_month');});	
	$('#p_month_insurance_end').blur(function(){keyPressMonth('p_month_insurance_end');});
	
});