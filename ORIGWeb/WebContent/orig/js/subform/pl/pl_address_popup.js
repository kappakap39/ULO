$(document).ready(function(){
	TextBoxCurrencyEngine('#returndchecks');	
	if($('#address_displayMode').val() == DISPLAY_MODE_EDIT){
		TextBoxButtonProvince();
		TextBoxButtonAmphur();	
		TextBoxButtonTambol();
		TextBoxButtonNationality();
		TextBoxButtonZipCode();
		SensitiveEngine('#address_status');	
		MandatoryAddressType();
		LogicCompanyNameAddress();
		$('#address_type').change(function(){
			MandatoryAddressType();
			LogicCompanyNameAddress();
			LogicCardLinkAddress();
		});
		$('#number').blur(function(){
			LogicCardLinkAddress();
		});
		$('#soi').blur(function(){
			LogicCardLinkAddress();
		});
		$('#moo').blur(function(){
			LogicCardLinkAddress();
		});
		$('#road').blur(function(){
			LogicCardLinkAddress();
		});
		$('#building').blur(function(){
			LogicCardLinkAddress();
		});
//		$('#address_company_title').change(function(){
//			LogicCardLinkAddress();
//		});
		$('#address_company_name').blur(function(){
			LogicCardLinkAddress();
		});
		$('#occ_department').blur(function(){
			LogicCardLinkAddress();
		});
	}
	LogicCardLinkAddress();
});

function LogicCardLinkAddress(){
	var dataString = 'className=com.eaf.orig.ulo.pl.ajax.CardLinkLine&returnType=0';
		dataString += '&'+$("div#plan-dialog :input").serialize();
 	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){	
			displayJSON(data);				
		},
		error : function(data){
		}
 	});
}
function LogicCompanyNameAddress(){
	var dataString = 'className=com.eaf.orig.ulo.pl.ajax.AddressCompany&returnType=0&address_type='+encodeURI($.trim($('#address_type').val()));
		dataString += '&companyTitle='+encodeURI($.trim($('#main_workplace_Title').val()))+'&companyName='+encodeURI($.trim($('#occ_name_th').val()));
	 	$.ajax({
			type :"POST",
			url :"AjaxDisplayServlet",
			data :dataString,
			async :false,	
			dataType: "json",
			success : function(data ,status ,xhr){	
				displayJSON(data);
				$('#building').blur(function(){
					LogicCardLinkAddress();
				});
//				$('#address_company_title').change(function(){
//					LogicCardLinkAddress();
//				});
				$('#address_company_name').blur(function(){
					LogicCardLinkAddress();
				});
				$('#occ_department').blur(function(){
					LogicCardLinkAddress();
				});
			},
			error : function(data){
			}
	 	});
}
function MandatoryAddressType(){
	if($('#address_type').val() == '03'){
		$('#error_address_status').html('');
		$('#error_work_place_name').html('*');
//		$('#error_department').html('*');
	}else{
		if($('#address_type').val() == '02'){
			$('#error_address_status').html('*');
		}else{
			$('#error_address_status').html('');
		}
		$('#error_work_place_name').html('');
//		$('#error_department').html('');
	}
}
function TextBoxButtonProvince(){
	$('#province').blur(function(){
		$(this).val($.trim($(this).val()));
		$('#zipcode').val($.trim($('#zipcode').val()));
		$('#tambol').val($.trim($('#tambol').val()));
		$('#amphur').val($.trim($('#amphur').val()));
		var province = $(this).val();
		if(province == ''){
			$('#zipcode').val('');
			$('#tambol').val('');
			$('#tambol_desc').val('');
			$('#amphur').val('');
			$('#amphur_desc').val('');
			$('#province').val('');
			$('#province_desc').val('');
			LogicCardLinkAddress();
			return;
		}
		if(validateInteger(province)){
			var dataString = 'className=GetProvince&packAge=0&returnType=0&province='+encodeURI($.trim($('#province').val()));
			jQuery.ajax( {
				type :"POST",
				url :"AjaxDisplayServlet",
				data :dataString,
				async :true,	
				dataType: "json",
				success : function(data ,status ,xhr){
					if(data!= null && data.length >0){
						jsonDisplayElementById(data);
						MandatoryAddress('province');
						LogicCardLinkAddress();
					}else{
						$('#zipcode').val('');
						$('#tambol').val('');
						$('#tambol_desc').val('');
						$('#amphur').val('');
						$('#amphur_desc').val('');		
						$('#province').val('');
						$('#province_desc').val('');
						OpenPopupProvince();
					}
				}
			});
		}else{
			$('#zipcode').val('');
			$('#tambol').val('');
			$('#tambol_desc').val('');
			$('#amphur').val('');
			$('#amphur_desc').val('');
			$('#province').val('');
			$('#province_desc').val('');
			$('#province').focus();
			$('#province').setCursorToTextEnd();
			LogicCardLinkAddress();
		}		
	});
	$("#province").keypress(function(e){
		var strChar = String.fromCharCode(e.keyCode);
		if(validateInteger(strChar)){
			return true;
		}else{
			e.preventDefault();
			return false;
		}
	});
	$('#provincePopup').click(function(){
		OpenPopupProvince();
	});
}

function OpenPopupProvince(){
	var dataString = '&province='+encodeURI($.trim($('#province').val()));
	var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadProvince"+dataString;
	openDialog(url, 850, 600,scrollbars=0, setPrefs);
}

function TextBoxButtonAmphur(){
	$('#amphur').blur(function(){
		$(this).val($.trim($(this).val()));
		$('#zipcode').val($.trim($('#zipcode').val()));
		$('#tambol').val($.trim($('#tambol').val()));
		$('#province').val($.trim($('#province').val()));
		var amphur = $(this).val();
		if(amphur == ''){
			$('#zipcode').val('');
			$('#tambol').val('');
			$('#tambol_desc').val('');
			$('#amphur').val('');
			$('#amphur_desc').val('');
			LogicCardLinkAddress();
			return;
		}
		if(validateInteger(amphur)){
			var dataString = 'className=GetAmphur&packAge=0&returnType=0'
									+'&province='+encodeURI($.trim($('#province').val()))
										+'&amphur='+encodeURI($.trim($('#amphur').val()));
			jQuery.ajax( {
				type :"POST",
				url :"AjaxDisplayServlet",
				data :dataString,
				async :true,	
				dataType: "json",
				success : function(data ,status ,xhr){
					if(data!= null && data.length >0){
						jsonDisplayElementById(data);
						MandatoryAddress('amphur');
						LogicCardLinkAddress();
					}else{
						$('#zipcode').val('');
						$('#tambol').val('');
						$('#tambol_desc').val('');
						$('#amphur').val('');
						$('#amphur_desc').val('');						
						OpenPopupAmphur();
					}
				}
			});
		}else{
			$('#zipcode').val('');
			$('#tambol').val('');
			$('#tambol_desc').val('');			
			$('#amphur').focus();
			$('#amphur').setCursorToTextEnd();
			LogicCardLinkAddress();
		}		
	});
	$("#amphur").keypress(function(e){
		var strChar = String.fromCharCode(e.keyCode);
		if(validateInteger(strChar)){
			return true;
		}else{
			e.preventDefault();
			return false;
		}
	});
	$('#amphurPopup').click(function(){
		OpenPopupAmphur();
	});
}
function OpenPopupAmphur(){
	var dataString = '&province='+encodeURI($.trim($('#province').val()))
							+'&amphur='+encodeURI($.trim($('#amphur').val()));
	var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadDistrict"+dataString;
	openDialog(url, 850, 600,scrollbars=0, setPrefs);
}

function TextBoxButtonTambol(){
	$('#tambol').blur(function(){
		$(this).val($.trim($(this).val()));
		$('#zipcode').val($.trim($('#zipcode').val()));
		$('#amphur').val($.trim($('#amphur').val()));
		$('#province').val($.trim($('#province').val()));
		var tambol = $(this).val();
		if(tambol == ''){
			$('#zipcode').val('');
			$('#tambol').val('');
			$('#tambol_desc').val('');
			LogicCardLinkAddress();
			return;
		}
		if(validateInteger(tambol)){
			var dataString = 'className=GetTambol&packAge=0&returnType=0'
									+'&province='+encodeURI($.trim($('#province').val()))
										+'&amphur='+encodeURI($.trim($('#amphur').val()))
											+'&tambol='+encodeURI($.trim($('#tambol').val()))
												+'&zipcode='+encodeURI($.trim($('#zipcode').val()));
			jQuery.ajax( {
				type :"POST",
				url :"AjaxDisplayServlet",
				data :dataString,
				async :true,	
				dataType: "json",
				success : function(data ,status ,xhr){
					if(data!= null && data.length >0){
						jsonDisplayElementById(data);
						LogicCardLinkAddress();
					}else{
						$('#zipcode').val('');
						$('#tambol').val('');
						$('#tambol_desc').val('');						
						OpenPopupTambol();
					}
				}
			});
		}else{
			$('#zipcode').val('');
			$('#tambol').val('');
			$('#tambol_desc').val('');			
			$('#tambol').focus();
			$('#tambol').setCursorToTextEnd();
			LogicCardLinkAddress();
		}		
	});
	$("#tambol").keypress(function(e){
		var strChar = String.fromCharCode(e.keyCode);
		if(validateInteger(strChar)){
			return true;
		}else{
			e.preventDefault();
			return false;
		}
	});
	$('#tambolPopup').click(function(){
		OpenPopupTambol();
	});
}
function OpenPopupTambol(){
	var dataString = '&province='+encodeURI($.trim($('#province').val()))
							+'&amphur='+encodeURI($.trim($('#amphur').val()))
									+'&tambol='+encodeURI($.trim($('#tambol').val()));
	var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadSubDistrict"+dataString;
	openDialog(url, 850, 600,scrollbars=0, setPrefs);
}

function TextBoxButtonZipCode(){
	$("#zipcode").blur(function(){
		$(this).val($.trim($(this).val()));
		$('#province').val($.trim($('#province').val()));
		$('#amphur').val($.trim($('#amphur').val()));
		$('#tambol').val($.trim($('#tambol').val()));		
		var zipcode = $(this).val();
		if(zipcode == ''){
			$('#zipcode').val('');
//			#septem comment
//			$('#province').val('');
//			$('#province_desc').val('');
//			$('#amphur').val('');
//			$('#amphur_desc').val('');
//			$('#tambol').val('');
//			$('#tambol_desc').val('');
			LogicCardLinkAddress();
			return;
		}
		if(validateInteger(zipcode)){
			var dataString = "className=GetZipCode&packAge=0&returnType=0&zipcode="+encodeURI($.trim(zipcode))
								+'&province='+encodeURI($.trim($('#province').val()))
									+'&amphur='+encodeURI($.trim($('#amphur').val()))
										+'&tambol='+encodeURI($.trim($('#tambol').val()));
			jQuery.ajax( {
				type :"POST",
				url :"AjaxDisplayServlet",
				data :dataString,
				async :true,	
				dataType: "json",
				success : function(data ,status ,xhr){
					if(data!= null && data.length >0){
						jsonDisplayElementById(data);
						LogicCardLinkAddress();
					}else{
//						#septem comment
//						$('#province').val('');
//						$('#province_desc').val('');
//						$('#amphur').val('');
//						$('#amphur_desc').val('');
//						$('#tambol').val('');
//						$('#tambol_desc').val('');
						OpenPopupZipCode();
					}
				}
			});
		}else{
			$('#zipcode').val('');
//			#septem comment
//			$('#province').val('');
//			$('#province_desc').val('');
//			$('#amphur').val('');
//			$('#amphur_desc').val('');
//			$('#tambol').val('');
//			$('#tambol_desc').val('');
			$('#zipcode').focus();
			$('#zipcode').setCursorToTextEnd();
			LogicCardLinkAddress();
		}
	});
	$("#zipcode").keypress(function(e){
		var strChar = String.fromCharCode(e.keyCode);
		if(validateInteger(strChar)){
			return true;
		}else{
			e.preventDefault();
			return false;
		}
	});
	$('#zipcodePopup').click(function(){
		OpenPopupZipCode();
	});
}
function OpenPopupZipCode(){
	var dataString = "&zipcode="+encodeURI($.trim($('#zipcode').val()))
							+'&province='+encodeURI($.trim($('#province').val()))
								+'&amphur='+encodeURI($.trim($('#amphur').val()))
									+'&tambol='+encodeURI($.trim($('#tambol').val()));
	var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadPLZipCode"+dataString;
	openDialog(url, 850, 600,scrollbars=0, setPrefs);
}


function TextBoxButtonNationality(){
	$('#country_desc').blur(function(){		
		$(this).val($.trim($(this).val()));		
		var country_desc = $(this).val();		
		if(country_desc == ''){
			$('#country_no').val('');
			return;
		}		
		if(country_desc != ''){
			LoadNationality(country_desc);			
		}
	});
	$('#country_descPopup').click(function(){
		OpenPopupNationality();
	});
}

function LoadNationality(country_desc){
	var dataString = "className=GetNationalFromDesc&packAge=0&returnType=0&country_desc="+encodeURI($.trim(country_desc));
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,	
		dataType: "json",
		success : function(data ,status ,xhr){
			if(data!= null && data.length >0){
				jsonDisplayElementById(data);
			}else{
				OpenPopupNationality();
			}
		}
	});
}
function OpenPopupNationality(){
	var country_desc = $.trim($('#country_desc').val());
	var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadNational&country_desc="+encodeURI(country_desc);
	openDialog(url, 850, 600,scrollbars=0, setPrefs);
}


//#SeptemWi
//function combineAddressNo(){
//	$('#address_type').change(function(){
//		$('#address_popup_table input:not(:button)').each(function(){
//			var inputbox = $(this).prop('name');
//			inputbox = inputbox+'_'+$('#address_type').val();
//			$(this).attr('name',inputbox);
//		});
//	});
//}
//
/*** End */




function saveData(customerType,popupType){
	SaveAddressPopup();
}

function SaveAddressPopup(){
	if(validateSaveAddress()){
		var dataString = $("div#plan-dialog :input").serialize()+'&'+$('#table-mailling-address *').serialize();
		$.post('FrontController?action=SavePLAddress&handleForm=N'
				,dataString
				,function(data,status,xhr){
					DisplayAddress(data);
					SensitiveAttrNameEngine('address_object');
				}
		);	
		return true;
	}
	return false;
}
function validateSaveAddress(){
	$('#address_pop_err_div').html('');
//	var obj = [];	
//	if($('#address_type').val()==""){
//		obj.push('<div>'+REQUIRE_ADDRESS_TYPE+'</div>');
//	}
//	if($('#zipcode').val()==""){
//		obj.push('<div>'+REQUIRE_ZIPCODE+'</div>');
//	}
//	if($('#country_no').val()==""){
//		obj.push('<div>'+REQUIRE_COUNTRY+'</div>');
//	}
//	if($('#address_type').val() != '03'){
//		if($('#address_status').val()==""){
//			obj.push('<div>'+ADDRESS_STYLE+'</div>');
//		}
//	}
//	if(obj != null && obj.length >0){
//		$.map(obj, function(item){
//			$('#address_pop_err_div').append(item);
//		});
//		return false;
//	}
//	return true;
	var validate = false;
	var dataString = 'className=com.eaf.orig.ulo.pl.ajax.AddressManadatory&returnType=0';
		dataString += '&'+$("div#plan-dialog :input").serialize();
	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){	
			if(null != data && data.length > 0){
				$.map(data, function(item){
					$('#address_pop_err_div').append(item.value);
				});
				validate = false;
			}else{
				validate = true;			
			}
		},
		error : function(data){
			validate = false;
		}
	});
	return validate;
}

 function copyAddress(){
	if($('#address_type_copy').val() == '' || $('#address_type_copy').val() == $('#address_type').val()){
		return false;
	}
//	#septem comment
//	var dataString = "address_type_copy="+$('#address_type_copy').val();
// 	ajaxDisplayElementJson('LoadCopyAddress',packageOrigPl,dataString);	
	var dataString = 'className=com.eaf.orig.ulo.pl.app.servlet.LoadCopyAddress&returnType=0';
		dataString += "&address_type_copy="+$('#address_type_copy').val();
	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){	
			displayJSON(data);
		 	LogicCardLinkAddress();
		},
		error : function(data){
		}
	});
 } 

 function MandatoryAddress(textboxID){
	var dataString = "className=AddressMandatory&packAge=0&returnType=0&zipcode="+encodeURI($.trim($('#zipcode').val()))
							+'&province='+encodeURI($.trim($('#province').val()))
								+'&amphur='+encodeURI($.trim($('#amphur').val()))
									+'&tambol='+encodeURI($.trim($('#tambol').val()))
										+'&textbox_id='+textboxID;
	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,	
		dataType: "json",
		success : function(data ,status ,xhr){
			if(data!= null && data.length >0){
				jsonDisplayElementById(data);
			}
		}
	});	 
 }
 
 
//function getZipCode(){
//	var tambol = $('#tambol').val();
//	var dataString = "ClassPackage=com.eaf.orig.ulo.pl.product.ajax&ClassName=GetZipcodeFromTambol&tambol="+tambol;
//	jQuery.ajax( {
//		type :"POST",
//		url :'AjaxServlet',
//		data :dataString,
//		async :false,
//		success : function(data ,status ,xhr){			
//			$("#zipcode").val(data);
//		}
//	});	
//}

//function getAllAddress(){
//	var form = document.appFormName;
//	var Zipcode = form.zipcode.value;
//	var Province = form.province.value;	
//	if(Zipcode==""){
//		popupscriptFields('LoadPLZipCode',new Array('province','zipcode'),'','','zipCode');
//	}else if(Zipcode!=""&&Province==""){
//		popupscriptFields('LoadPLZipCode',new Array('province','zipcode'),'','','zipCode');
//	}
//}

function validatePopupMandatory(){
	DestoryErrorField();
	var dataString = "className=PopupMandatory&packAge="+packagePlMandatory+"&returnType=0&"+$("#avale-obj-form *").serialize();
	var error = false;
	jQuery.ajax( {
		type :"POST",
		url :'AjaxDisplayServlet',
		data :dataString,	
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){
			if(data.length > 0){
				error =  true;
				DisplayErrorField(data);
			}else{				
				error =  false;
				passSaveData();
			}
		},
		error : function(data){
			error =  true;
			PushErrorNotifications('Network or Connection Error, please try again');
		}
	});
	return error;
}
//
//function openLayer2Popup(fieldName){
//	var url = getAddressPopupAction(fieldName);
//	$('#'+fieldName).val('');
//	openDialog(url, 850, 600,scrollbars=0, setPrefs);
//}
//
//function openLayer2PopupButton(fieldName){
//	var url = getAddressPopupAction(fieldName);
//	openDialog(url, 850, 600,scrollbars=0, setPrefs);
//}
//
//function getAddressPopupAction(field){
//	if(field=='zipcode'){
//		var zipcode = $('#zipcode').val();
//		var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadPLZipCode"+"&zipcode="+zipcode+"&textboxCode=zipcode";
//		return url;
//	}
//	else if(field=='province'){
//		var province = $('#province').val();
//		var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadProvince"+"&province="+province+"&textboxCode=province"+"&textboxDesc=province_desc";
//		return url;
//	}
//	else if(field=='amphur'){
//		var province = $('#province').val();
//		var amphur =  $('#amphur').val();
//		var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadDistrict"+"&amphur="+amphur+"&province="+province+"&textboxCode=amphur"+"&textboxDesc=amphur_desc";
//		return url;
//	}
//	else if(field=='tambol'){
//		var province = $('#province').val();
//		var amphur =  $('#amphur').val();
//		var tambol = $('#tambol').val();
//		var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadSubDistrict"+"&tambol="+tambol+"&amphur="+amphur+"&province="+province+"&textboxCode=tambol"+"&textboxDesc=tambol_desc";
//		return url;
//	}
//	else if(field=='country_desc'){
//		var country_desc = $('#country_desc').val();
//		var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadNational"+"&country_desc="+encodeURI(country_desc)+"&textboxCode=country_no"+"&textboxDesc=country_desc";
//		return url;
//	}
//}
