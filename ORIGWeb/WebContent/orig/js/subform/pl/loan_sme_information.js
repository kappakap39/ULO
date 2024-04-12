
$(document).ready(function(){
	//validate year >= 2400 #Vikrom  20121120
	$('#cash_define_date').blur(function(){
		var year = $('#cash_define_date').val().substr(6,10);
		if(year < 2400 && year.length!=0){
			alertMassage(FORMAT_DATE_ERROR);
			$('#cash_define_date').val('');
			$('#cash_define_date').focus();
			$('#cash_define_date').setCursorToTextEnd();
		}
	});
});