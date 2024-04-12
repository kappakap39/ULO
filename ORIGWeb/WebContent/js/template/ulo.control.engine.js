/**
 * #SeptemWi This Used For Control Javascript Startup
 * Function Register SensitiveEngine,DateEngine
 */
$(document).ready(function (){
/**Destory Function*/
//	try{DestorySensitiveEngine('.sensitive');}catch(e){}
//	try{DestoryDateEngine('.textboxCalendar');}catch(e){}
//	try{DestoryTextBoxCurrencyEngine('.textbox-currency');}catch(e){}
//	try{DestoryTextBoxNumberEngine('.textbox-number');}catch(e){}
//	try{DestoryTextBoxTelEngine('.textbox-tel');}catch(e){}
/**Startup Function*/
	try{DateEngine('.textboxCalendar');}catch(e){}
	try{TextBoxCurrencyEngine('.textbox-currency');}catch(e){}
	try{TextBoxNumberEngine('.textbox-number');}catch(e){}
	try{TextBoxTelEngine('.textbox-tel');}catch(e){}
	try{ButtonNormalEngine('.button');}catch(e){}
	try{ButtonRedEngine('.button-red');}catch(e){}	
	try{TextBoxThaiEngine('.thaiChar');}catch(e){}	
	try{TextBoxEngEngine('.EngChar');}catch(e){}
});