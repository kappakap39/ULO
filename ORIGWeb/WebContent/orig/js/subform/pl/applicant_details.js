
var last_product_feature = "";
var last_saleType = "";
var $loadChannel = 'N'; 
$(document).ready(function(){
//	function for saletype
	$('#saleType').focus(function(){
		last_saleType = $(this).val();
	});	
	$('#saleType').change(function(){
		alertMassageYesNoFunc(SALE_TYPE_WARNING,EnablechangeSaleType,CancelchangeSaleType);		
	});
	if($('#saleType').val() != null){
		$('#saleType option[value=""]').remove();
	}	
	
//	function for product feature
	createCGNSelectBox();
	
	var busclassID = $('#busClass').val();
	if(INCREASE_BUSSCLASS != busclassID && DECREASE_BUSSCLASS != busclassID){
		$('#product_feature').focus(function(){
			last_product_feature = $(this).val();
		});	
		$('#product_feature').change(function(){	
//		even change product feature clear project code
			var project_code = $('#project_code').val();
			if(project_code != ''){
				changeProductFeature();
			}
			createCGNSelectBox();
		});
	}
//	function for Channel
	getChannelDropdown();	
});

function changeProductFeature(){
	alertMassageYesNoFunc(PRODUCT_FEATURE_WARNING,ClearDataProjectCode,CancelchangeProductF);
}
function createCGNSelectBox(){
	var product_feature = $('#product_feature').val();
	if($('#customer_group_name').attr('id') != undefined){
		var dataString = "className=CGNSelectBox&packAge=5&returnType=0&product_feature="+product_feature;
		jQuery.ajax({
			type :"POST",
			url :"AjaxDisplayServlet",
			data :dataString,
			async :false,
			dataType: "json",
			success : function(data ,status ,xhr){
				jsonDisplayElementById(data);
			},
			error : function(data){
			}
		});	
	}
}

function EnablechangeSaleType(){
	try{
		blockScreen();
	//	change sale type logic #clear verification data and load new subform
		var dataString = "className=ClearVerificationInformation&packAge="+packageOrigPl+"&returnType=0";
		jQuery.ajax({
			type :"POST",
			url :"AjaxDisplayServlet",
			data :dataString,
			async :false,
			dataType: "json",
			success : function(data ,status ,xhr){
				displayJsonObjectElementById(data);
				var form = document.appFormName;
				form.action.value = "LoadSubformByBusClass";
				form.handleForm.value = "N";
				form.submit();
			},
			error : function(data){
				unblockScreen();
			}
		});	
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}

/**new function get channel dropdown*/
function getChannelDropdown(){
	var branch_code = $('#seller_branch_code').val();
	var displayMode = $('#applicantDC_displaymode').val();
	var channel = $('#channel').val();
	var dataString = "className=GetChannelByBranch&packAge=5&returnType=1&branch_code="+branch_code+"&displayMode="+displayMode+"&channel="+channel;
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,
		success : function(data ,status ,xhr){	
			htmlDisplayElementById(data,"div_customerType");
			var option = $('#channel option').length;
			if(option != undefined && option == 2){
				$('#channel option[value=""]').remove();
			}
			$('#channel').change(function(){
				$('#seller_title').val('');
				$('#seller_name_th').val('');
				SensitiveAttrNameEngine('channel');
			});
			if('Y' == $loadChannel){
				SensitiveAttrNameEngine('channel');
			}
			$loadChannel = 'Y';
		},
		error : function(response){
			$loadChannel = 'Y';
		}
	});
}

function CancelchangeSaleType(){
	$('#saleType').val(last_saleType);
}
function CancelchangeProductF(){
	$('#product_feature').val(last_product_feature);
}