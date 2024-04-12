/**
 * 
 */

$(document).ready(function(){
	$("Tr.ResultOdd").hover(
	    function(){$(this).addClass("ResultOdd-haver");},
	    function(){$(this).removeClass("ResultOdd-haver");}
	);
	$("Tr.ResultEven").hover(
    	 function(){$(this).addClass("ResultEven-haver");},
    	 function(){$(this).removeClass("ResultEven-haver");}
	);
});

function ClickPageList(atPage){
	$('#action').val('ValueListDialogPopup');
	$('#atPage').val(atPage);
	$('#handleForm').val('N');
	$('#appFormName').submit();
}

function searchCode(){
	var textboxCode = $('#textboxCode').val(); 
	try{
		if(textboxCode == 'tambol'){
			$('#province').val($.trim(window.opener.$('#province').val()));
			$('#amphur').val($.trim(window.opener.$('#amphur').val()));		
		}else if(textboxCode == 'amphur'){
			$('#province').val($.trim(window.opener.$('#province').val()));
			$('#amphur').val('');
		}else if(textboxCode == 'province'){
			$('#amphur').val('');
			$('#province').val('');
		}
	}catch(e){		
	}
	$('#action').val($('#searchAction').val());
	$('#handleForm').val('N');
	$('#appFormName').submit();
}

function setData(code, desc){
	var textboxCode = $('#textboxCode').val(); 
 	var textboxDesc = $('#textboxDesc').val();
	try{	 	
	 	window.opener.$('#'+textboxCode).val($.trim(code)); 	
	 	window.opener.$('#'+textboxDesc).val($.trim(desc));
	 	LogicManualDisplay();
	}catch (e){	
	}
 	window.close();	
}

function LogicManualDisplay(){
	var textboxCode = $('#textboxCode').val(); 
 	var textboxDesc = $('#textboxDesc').val();
 	try{
		switch(textboxCode){
			case 'tambol':
				if(window.opener.$('#province').val()== ''|| window.opener.$('#amphur').val() == ''){
					window.opener.$('#tambol').val('');
					window.opener.$('#tambol_desc').val('');
					window.opener.$('#zipcode').val('');
				}				
				window.opener.$('#'+textboxCode).focus();
				window.opener.$('#'+textboxCode).setCursorToTextEnd();
				break;	
			case 'amphur':
				if(window.opener.$('#province').val()== ''){
					window.opener.$('#amphur').val('');
					window.opener.$('#amphur_desc').val('');
					window.opener.$('#tambol').val('');
					window.opener.$('#tambol_desc').val('');
					window.opener.$('#zipcode').val('');
				}
				window.opener.MandatoryAddress('amphur');
				window.opener.$('#'+textboxCode).focus();
				window.opener.$('#'+textboxCode).setCursorToTextEnd();
				break;
			case 'province':
				window.opener.MandatoryAddress('province');
				window.opener.$('#'+textboxCode).focus();
				window.opener.$('#'+textboxCode).setCursorToTextEnd();
				break;
			case 'zipcode':
				if(window.opener.$('#province').val()== '' || window.opener.$('#amphur').val() == '' || window.opener.$('#tambol').val() == ''){
					window.opener.$('#province').val('');
					window.opener.$('#province_desc').val('');
					window.opener.$('#amphur').val('');
					window.opener.$('#amphur_desc').val('');
					window.opener.$('#tambol').val('');
					window.opener.$('#tambol_desc').val('');
				}
				window.opener.$('#'+textboxCode).focus();
				window.opener.$('#'+textboxCode).setCursorToTextEnd();
				break;
			case 'country_no':
				window.opener.$('#'+textboxDesc).focus();
				window.opener.$('#'+textboxDesc).setCursorToTextEnd();
				break;
			default:
				window.opener.$('#'+textboxCode).focus();
				window.opener.$('#'+textboxCode).setCursorToTextEnd();
				break;
		}
	}catch (e){		
	}
}
function setDataTambol(tambolCode ,tambolDesc , zipcode){
	var textboxCode = $('#textboxCode').val(); 
 	var textboxDesc = $('#textboxDesc').val();
	try{		
	 	window.opener.$('#'+textboxCode).val($.trim(tambolCode)); 	
	 	window.opener.$('#'+textboxDesc).val($.trim(tambolDesc));
	 	if(null != zipcode){
	 		var data = zipcode.split("|");
	 		if(null != data){
	 			if(data.length == 1){
	 				window.opener.$('#zipcode').val($.trim(zipcode.replace('|','')));
	 			}else{
	 				for(var i=0; i<data.length; i++){
	 					var $str = data[i];
	 					if(null != $str && $str != '' && $str == $('#zipcode').val()){
	 						window.opener.$('#zipcode').val($.trim($str));
	 						break;
	 					}
	 				}
	 			}
	 		}else{
			 	window.opener.$('#zipcode').val($.trim(''));
	 		}
	 	}else{
		 	window.opener.$('#zipcode').val($.trim(''));
	 	}
	 	LogicManualDisplay();
	}catch(e){		
	}
	window.close();
}
function setDataZipCode(code,sub_districtID, sub_district_desc,districtID, district_desc, proviceID, provice_desc){
	var textboxCode = $('#textboxCode').val(); 
	try{	
	 	window.opener.$('#'+textboxCode).val($.trim(code));
		window.opener.$('#province').val($.trim(proviceID));
		window.opener.$('#province_desc').val($.trim(provice_desc));
		window.opener.$('#amphur').val($.trim(districtID));
		window.opener.$('#amphur_desc').val($.trim(district_desc));
		window.opener.$('#tambol').val($.trim(sub_districtID));
		window.opener.$('#tambol_desc').val($.trim(sub_district_desc));
		LogicManualDisplay();
	}catch(e){		
	}
	window.close();
}