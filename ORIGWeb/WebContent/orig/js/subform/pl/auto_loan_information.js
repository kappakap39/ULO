
$(document).ready(function(){
	//validate year >= 2400 #Vikrom  20121120
	$('#permit_date').blur(function(){
		var year = $('#permit_date').val().substr(6,10);
		if(year < 2400 && year.length!=0){
			alertMassage(FORMAT_DATE_ERROR);
			$('#permit_date').val('');
			$('#permit_date').focus();
			$('#permit_date').setCursorToTextEnd();
		}
	});
});