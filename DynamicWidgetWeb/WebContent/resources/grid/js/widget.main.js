$(function(){
	// Start Widget Progress
	$(".meter > span").each(
		function () {
		var getColor = $(this).parent().css('borderTopColor');
		$(this).css('background', getColor);
		$(this).data("origWidth",
			$(this).width() / $(this).parent().width() * 100).width(0)
		.animate({
			width : $(this).data("origWidth") + "%"
		}, 1000);
	});
	
	circleProgess();
});

/* ---------- Check Retina ---------- */
function retina(){
	
	var retinaMode = (window.devicePixelRatio > 1);
	
	return retinaMode;
	
}

/* ---------- Circle Progess Bars ---------- */
function circleProgess() {
	
//	var divElement = $('div'); //log all div elements
	
	if (retina()) {
		
		$(".whiteCircle").knob({
	        'min':0,
	        'max':100,
	        'readOnly': true,
	        'width': 240,
	        'height': 240,
			'bgColor': 'rgba(255,255,255,0.5)',
	        'fgColor': 'rgba(255,255,255,0.9)',
	        'dynamicDraw': true,
	        'thickness': 0.2,
	        'tickColorizeValues': true
	    });
	
		$(".circleStat").css('zoom',0.5);
		$(".whiteCircle").css('zoom',0.999);
		
		
	} else {
		
		$(".whiteCircle").knob({
	        'min':0,
	        'max':100,
	        'readOnly': true,
	        'width': 90,
	        'height': 90,
			'bgColor': 'rgba(255,255,255,0.5)',
	        'fgColor': 'rgba(255,255,255,0.9)',
	        'dynamicDraw': true,
	        'thickness': 0.2,
	        'tickColorizeValues': true,
	        'angleOffset' : 4.75
	    });
		
	}
	
	
	
	$(".circleStatsItemBox").each(function(){
		
		var value = $(this).find(".value > .number").html();
		var unit = $(this).find(".value > .unit").html();
		var percent = $(this).find("input").val()/100.0;
		countSpeed = 7300*percent;
		
		endValue = value*percent;
		$(this).find(".count > .unit").html(unit);
		$(this).find(".count > .number").countTo({
			
			from: 0,
		    to: endValue,
		    speed: countSpeed,
		    refreshInterval: 50
		
		});
		
		//$(this).find(".count").html(value*percent + unit);
		
	});
	
}  