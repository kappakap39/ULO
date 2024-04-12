var modalreposition = function(d,c){
	if(c == 'auto'){
		c = 'center';
	}
    // right side
    if(typeof d === 'undefined'){
        d = '.modal';
        if (typeof c !== 'undefined'){
            d += '.' + c;
        }
    }else{
        $(d).addClass(c);
    }
    var offset = undefined;
    if($(d).hasClass('center')){
        offset = $('#page-wrapper').offset();
        offset.left += 7;
        offset.top += 2;
    }
    if(!offset) return;
    $(d).css({
        left: offset.left,
        top: offset.top,
        bottom: 23
    });
    return true;
};