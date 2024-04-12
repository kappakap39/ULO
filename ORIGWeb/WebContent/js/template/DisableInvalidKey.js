function Disable_Ctrl_N(){      
	if((event.keyCode == 78) && (event.ctrlKey)){
		event.cancelBubble = true;
		event.returnValue = false;
		event.keyCode = false; 
		return false;
	}
}
function Disable_F5(){
	if(event.keyCode == 116){
		window.event.returnValue = false;
		event.keyCode = false; 
		return false;			
	}
}
function Disable_Ctrl_R(){//Refresh Page
	if((event.keyCode == 82) && (event.ctrlKey)){
		window.event.returnValue = false;
		event.keyCode = false; 
		return false;			
	}
}
function Disable_Ctrl_F4(){
	if((event.keyCode == 115) && (event.ctrlKey)){
		window.event.returnValue = false;
		event.keyCode = false; 
		return false;			
	}
}
function Disable_Alt_F4(){
	if((event.keyCode == 115) && (event.altKey)){
	alert('Not allowed this Shortcut key!!!');
		window.event.returnValue = false;
		event.keyCode = false; 
		return false;			
	}
}
function Disable_Alt_Home(){
	if((event.keyCode == 36) && (event.altKey)){
	alert('Not allowed this Shortcut key!!!');
		window.event.returnValue = false;
		event.keyCode = false; 
		return false;			
	}
}
function Disable_Alt_RArrow(){
	if((event.keyCode == 39) && (event.altKey)){
		window.event.returnValue = false;
		event.keyCode = false; 
		return false;			
	}
}
function Disable_Alt_LArrow(){
	if((event.keyCode == 37) && (event.altKey)){
	alert('Not allowed this Shortcut key!!!');
		window.event.returnValue = false;
		event.keyCode = false; 
		return false;			
	}
}
function Disable_Alt_BackSpace(){
	if(event.keyCode == 8 && (event.altKey)){
		window.event.returnValue = false;
		event.keyCode = false; 
		return false;			
	}
}
function Disable_Shift_F10(){
	if(event.keyCode == 121 && (event.shiftKey)){
		window.event.returnValue = false;
		event.keyCode = false; 
		return false;			
	}
}
function Disable_Ctrl_W(){//Close Page
	if((event.keyCode == 87) && (event.ctrlKey)){
		window.event.returnValue = false;
		event.keyCode = false; 
		return false;			
	}
}
function Disable_Ctrl_S(){
	if((event.keyCode == 83) && (event.ctrlKey)){
		window.event.returnValue = false;
		event.keyCode = false; 
		return false;			
	}
}
function Disable_F4(){
	if((event.keyCode == 115)){
		window.event.returnValue = false;
		event.keyCode = false; 
		return false;			
	}
}
function Disable_Alt(){
	if(event.altKey){
		window.event.returnValue = false;
		event.keyCode = false; 
		return false;			
	}
}
function Disable_F12(){
	if((event.keyCode == 123)){
		window.event.returnValue = false;
		event.keyCode = false; 
		return false;			
	}
}
function Disable_Ctrl_F12(){
	if((event.keyCode == 123) && (event.ctrlKey)){
		window.event.returnValue = false;
		event.keyCode = false; 
		return false;			
	}
}
function Disable_Alt_F12(){
	if((event.keyCode == 123) && (event.altKey)){
	alert('Not allowed this Shortcut key!!!');
		window.event.returnValue = false;
		event.keyCode = false; 
		return false;			
	}
}
function AllKey(){
	Disable_Ctrl_N();
	Disable_F5();
	Disable_Alt_Home();
	Disable_Alt_RArrow();
	Disable_Alt_LArrow();
	Disable_Alt_BackSpace();
	Disable_Shift_F10();
	Disable_Ctrl_R();
	Disable_Ctrl_W();
	Disable_Ctrl_S();
	Disable_F4();
	Disable_Ctrl_F4();
	Disable_Alt_F4();
	Disable_F12();
	Disable_Ctrl_F12();
	Disable_Alt_F12();
	Disable_Alt();
}

document.onmousedown = function(){
	if (event.button==2){//Right Click
		return false;
	}
};
document.oncontextmenu=new Function("return false");

if (typeof window.event != 'undefined'){
	$(document).unbind('keydown').bind('keydown', function (event) {	 
		var doPrevent = false;
	    if (event.keyCode === 8) {
	        var d = event.srcElement || event.target;
	        if ((d.tagName.toUpperCase() === 'INPUT' && (d.type.toUpperCase() === 'TEXT' || d.type.toUpperCase() === 'PASSWORD')) 
	             || d.tagName.toUpperCase() === 'TEXTAREA') {
	            doPrevent = d.readOnly || d.disabled;
	        }
	        else {
	            doPrevent = true;
	        }
	    }
	    if (doPrevent) {
	        event.preventDefault();
	    }
	});
}else{
	document.onkeypress = function(e){
		if (e.target.nodeName.toUpperCase() != 'INPUT')
			return (e.keyCode != 8);
	};
}	

if (typeof window.event != 'undefined'){
	document.onkeydown = function(){
		AllKey();
		if ( ((event.srcElement.type != 'text') && (event.srcElement.type != 'textarea') && (!event.ctrlKey)) ||
		((event.srcElement.type == 'text' || event.srcElement.type == 'textarea')&& (event.srcElement.readOnly==true||event.srcElement.disabled==true)))
			return (event.keyCode != 8);
	};
}else{
	document.onkeypress = function(e){
		if (e.target.nodeName.toUpperCase() != 'INPUT')
			return (e.keyCode != 8);
	};
}

