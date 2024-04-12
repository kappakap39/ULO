/**
 * Create App
 */
$(document).ready(function(){
	$('.PanelSecond').css({
		'height' : $(window).height() - 60
	});			
	getProductGroup();

//	#septemwi comment change to new function  PopupTextBoxDescBranch
//	$('#createbundling_branch_code').keypress(function() {
//		return keyPressInteger();
//	});
//	$('#createbundling_branch_code').blur(function(){
//		getDescByCode('createbundling_branch_code','createbundling_branch_name','BranchInfo'
//				,'LoadRecomendBranch', new Array('createbundling_branch_code'));
//	});
//	$('#createbundling_branch_codePopup').click(function() {
//		LoadBranchPopup('createbundling_branch_code','createbundling_branch_name');
//	});
//	$('#createbundling_branch_code').keyup(function() {
//		var val = $(this).val();
//		if (val.length == 0) {
//			$(this).val('');
//			$('#createbundling_branch_name').val('');
//		}
//	});
	
	PopupTextBoxDescBranch('createbundling_branch_code','createbundling_branch_name');
	
});

function validate() {
	var productdomain = appFormName.productdomain.value;
	var productgroup = appFormName.productgroup.value;
	var productfamily = appFormName.productfamily.value;
	var product = appFormName.product.value;
	var saleType = appFormName.saleType.value;
	var customerType = appFormName.customertype.value;
	var createbundling_branch_code = appFormName.createbundling_branch_code.value;
	var creditCardAppScore=appFormName.creditCardAppScore.value;
	var creditCardResult=appFormName.creditCardResult.value;
	if (productdomain == "") {
		alertMassage(ERROR_PRODUCT_DOMAIN);
		return true;
	} else if (productgroup == "") {
		alertMassage(ERROR_PRODUCT_GROUP);
		return true;
	} else if (productfamily == "") {
		alertMassage(ERROR_PRODUCT_FAMILY);
		return true;
	} else if (product == "") {
		alertMassage(ERROR_PRODUCT);
		return true;
	} else if (saleType == ""){
		alertMassage(ERROR_SALE_TYPE);
		return true;
	} else if (customerType == "") {
		alertMassage(ERROR_CUSTOMER_TYPE);
		return true;
	} else if (createbundling_branch_code == "") {
		alertMassage(ERROR_SELLER_BRANCH_CODE);
		return true;
	} else {
		var canSubmit = true;
		if (saleType == "CC") {
			if(creditCardResult==""){
				alertMassage(ERROR_CREDIT_CARD_RESULT);
				canSubmit=false;
			}else if((creditCardResult=="APPROVED" || creditCardResult=="OVERIDE") && creditCardAppScore==""){
				alertMassage(ERROR_CREDIT_CARD_APP_SCORE);
				canSubmit=false;
			}
		}
		if(canSubmit){
			blockScreen();
			try {
				appFormName.currentTab.value = "MAIN_TAB";
				appFormName.action.value = "CreateBundling";
				appFormName.handleForm.value = "N";
				appFormName.submit();
			} catch (e) {
				unblockScreen();
			}
		}
	}
}

function getProductGroup() {
	var dataString = "productdomain=" + appFormName.productdomain.value;
	ajaxDisplayElementHtml("GetProductGrpFromProductDomain", 5, dataString,"div_productgroup");
	getProductFamily();
}

function getProductFamily() {
	var dataString = "productgroup=" + appFormName.productgroup.value;
	ajaxDisplayElementHtml("GetProductFamFromProductGroup", 5, dataString,"div_productfamily");
	getProduct();
}

function getProduct() {
	var dataString = "productfamily=" + appFormName.productfamily.value;
	ajaxDisplayElementHtml("GetProductFromProductFamily", 5, dataString,"div_product");
	getSaleType();
}

function getSaleType() {
	var dataString = "product=" + appFormName.product.value;
	dataString += "&filter=Bundling";
	ajaxDisplayElementHtml("GetSaleTypeFromProduct", 5, dataString,"div_saleType");
	$("#saleType").change(function() {
		changeSaleType();
	});
}

function getCustomerType() {
	var dataString = "customerType=" + appFormName.product.value;
	ajaxDisplayElementHtml("GetCustomerType", 5, dataString, "div_customerType");
}
function getChannel() {
	var loanType = appFormName.loanType.value;
	var server = '<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>';
	ajax(server + "/AjaxServlet?ClassName=GetChannelFromProduct&loanType="+ loanType, displayInnerHtml, "GET");
}
//septemwi comment
//function LoadBranchPopup(field1, field2) {
//	var fieldVal = $('#' + field1).val();
//	$('#' + field1).val('');
//	var url =  CONTEXT_PATH_ORIG+"/FrontController?action=LoadRecomendBranch"+ "&ref_branch_code="+encodeURI(fieldVal)+"&textboxCode="+field1+"&textboxDesc="+field2;
//	$dialog = $dialogEmpty;
//	var width = 1000;
//	var higth = $(window).height() - 50;
//	var title = BRANCH_CODE;
//	$(".plan-dialog").remove();
//	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
//	$dialog.dialog({
//		resizable : false,
//		modal : true,
//		autoOpen : false,
//		draggable : true,
//		width : width,
//		height : higth,
//		title : title,
//		position : {
//			my : 'center',
//			at : 'center',
//			of : $(document.body)
//		},
//		close : function() {
//			closeDialog();
//		}
//	});
//	$dialog.dialog("open");
//
//}
function changeSaleType(){
	var saleType=$("#saleType").val();
	if(saleType=="CC"){
		$("#creditCardResult").removeAttr("disabled");
	}else{
		$("#creditCardResult").attr("disabled","disabled");
		$("#creditCardAppScore").attr("disabled","disabled");
		$("#creditCardResult").val('');
		$("#creditCardAppScore").val('');
	}
}
function creditCardResultChange(){
	var creditCardResult=$("#creditCardResult").val();
	if(creditCardResult=="APPROVED"||creditCardResult=="OVERIDE"){
		$("#creditCardAppScore").removeAttr("disabled");
		$("#creditCardAppScore").val('');
	}else{
		$("#creditCardAppScore").attr("disabled","disabled");
		$("#creditCardAppScore").val('');
	}
}

/**function popup textbox auto desc for branch #septemwi*/
function PopupTextBoxDescBranch(attr_code,attr_desc){
	$('#'+attr_code).keypress(function(){
		return keyPressInteger(); 
	});
	$('#'+attr_code).blur(function(){
		TextboxBranchLogic(attr_code,attr_desc);
	});	
	$('#'+attr_code+'Popup').click(function(){
		LoadBranchPopup(attr_code,attr_desc);
	});
}
function TextboxBranchLogic(attr_code,attr_desc){
	var branch_code = $.trim($('#'+attr_code).val());
	if(branch_code != ''){
		var dataString 	= 'className=com.eaf.orig.ulo.pl.ajax.GetBranchLogic&returnType=0&attr_code='+attr_code+'&attr_desc='+attr_desc;
			dataString += '&branch_code='+branch_code;
		jQuery.ajax({
			type :"POST",
			url :"AjaxDisplayServlet",
			data :dataString,
			async :true,	
			dataType: "json",
			success : function(data ,status ,xhr){
				if(data!= null && data.length >0){
					jsonDisplayElementById(data);
				}else{
					$('#'+attr_desc).val('');
					LoadBranchPopup(attr_code,attr_desc);
				}
			},
			error : function(data){
			}
		});

	}else{
		$('#'+attr_code).val('');
		$('#'+attr_desc).val('');
	}	
}

function LoadBranchPopup(attr_code,attr_desc){
	var value = $('#'+attr_code).val();
	var frameModule = (document.getElementById('frame-module')== null)? document.body :'.frame-module';
	var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadRecomendBranch&branch_code="+encodeURI($.trim(value))
						+"&textbox_code="+attr_code+"&textbox_desc="+attr_desc;		
	$dialog = $dialogEmpty;
	var width = 1000;
	var higth = $(window).height()-50;
	var title = BRANCH_CODE;
	$(".plan-dialog").remove();
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	$dialog.dialog({
		    resizable : false,
		    modal : true,
		    autoOpen: false,
		    draggable: true,
		    width: width,
		    height: higth,
		    title:title,
		    buttons :{
		        "Close" : function() {						        	
		        	$dialog.dialog("close");	
		        	closeDialog();
		        }
	    	},
		    position: { 
		        my: 'center',
		        at: 'center',
		        of: $(frameModule)
    		},
		    close: function(){
		    	closeDialog();
		    	if($('#popup-action').val() != "SEARCH_CODE"){
		    		$('#'+attr_code).focus();
		    		$('#'+attr_code).setCursorToTextEnd();
		    		$('#popup-action').val('');
		    	}
		    }
	});
   $dialog.dialog("open");
}

