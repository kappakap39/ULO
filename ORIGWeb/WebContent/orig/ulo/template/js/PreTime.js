function preTime(time) {
	try{
		var timeOut = new Date(new Date().getTime()+time*60000);
		$(".timeremain-tick").countdown(timeOut, function(event) {
			// Time display format.
			$(this).attr('timeleft', event.strftime('%M:%S'));
		}).on('finish.countdown', function() {
			// Add ".blink" class for Blinking when count down finished.
			$(this).addClass('timeup');
		});
	}catch(exception){
		errorException(exception);
	}
}
