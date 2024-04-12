$(document).ready(function(){
	
//	function for open popup ref_recommend_title #
	PopupTextBoxDescSell('ref_recommend_title','ref_name_th','ref_branch_code',RECOMMEND_CODE);
		
//	function for open popup ref_branch_code #
	PopupTextBoxDescBranch('ref_branch_code','ref_branch_name_th','ref_recommend_title','ref_name_th');
		
//	function for open popup seller_title #
	PopupTextBoxDescSell('seller_title','seller_name_th','seller_branch_code',SELLER);
		
//	function for open popup seller_branch_code #
	PopupTextBoxDescBranch('seller_branch_code','seller_branch_name','seller_title','seller_name_th');

//	function for open popup service_seller_title #
	PopupTextBoxDescSell('service_seller_title','service_seller_name_th','service_seller_branch_code',SERVICE_SALE_NAME);
		
//	function for open popup service_seller_branch_code #
	PopupTextBoxDescBranch('service_seller_branch_code','service_seller_branch_name','service_seller_title','service_seller_name_th');
		
	SensitiveCheckBox('certification');
	
});

/**function popup textbox auto desc for branch #septemwi*/
function PopupTextBoxDescBranch(attr_code,attr_desc,attr_coderef,attr_descref){
	$('#'+attr_code).keypress(function(){
		return keyPressInteger(); 
	});
	$('#'+attr_code).blur(function(){
		TextboxBranchLogic(attr_code,attr_desc,attr_coderef,attr_descref,true);
	});	
	$('#'+attr_code+'Popup').click(function(){
		LoadBranchPopup(attr_code,attr_desc,attr_coderef,attr_descref);
	});
}
function TextboxBranchLogic(attr_code,attr_desc,attr_coderef,attr_descref,getchannel){
	var branch_code = $.trim($('#'+attr_code).val());
	var sale_code = $.trim($('#'+attr_coderef).val());
	var channel = $.trim($('#channel').val());
	if(branch_code != ''){
		var dataString 	= 'className=com.eaf.orig.ulo.pl.ajax.GetBranchLogic&returnType=0&attr_code='+attr_code+'&attr_desc='+attr_desc;
			dataString += '&attr_coderef='+attr_coderef+'&attr_descref='+attr_descref+'&branch_code='+branch_code+'&sale_code='+sale_code;
			dataString += '&channel='+channel;
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
					if(attr_coderef != ''){
						$('#'+attr_coderef).val('');
					}
					if(attr_descref != ''){
						$('#'+attr_descref).val('');
					}
					LoadBranchPopup(attr_code,attr_desc,attr_coderef,attr_descref);
				}
				if(attr_code == 'seller_branch_code' && getchannel){
					getChannelDropdown();
				}
			},
			error : function(data){
			}
		});

	}else{
		$('#'+attr_code).val('');
		$('#'+attr_desc).val('');
		if(attr_coderef != ''){
			$('#'+attr_coderef).val('');
		}
		if(attr_descref != ''){
			$('#'+attr_descref).val('');
		}
		if(attr_code == 'seller_branch_code'){
			getChannelDropdown();
		}
	}	
}

function LoadBranchPopup(attr_code,attr_desc,attr_coderef,attr_descref){
	var value = $('#'+attr_code).val();
	var frameModule = (document.getElementById('frame-module')== null)? document.body :'.frame-module';
	var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadRecomendBranch&branch_code="+encodeURI($.trim(value))
						+"&textbox_code="+attr_code+"&textbox_desc="+attr_desc+"&ref_code01="+attr_coderef+"&ref_desc01="+attr_descref;		
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

/**function popup textbox auto desc for sell #septemwi*/
function PopupTextBoxDescSell(attr_code,attr_desc,attr_coderef,title){
	$('#'+attr_code).keypress(function(){
		return keyPressInteger(); 
	});
	$('#'+attr_code).blur(function(){
		TextboxSellLogic(attr_code,attr_desc,attr_coderef,title);
	});
	$('#'+attr_code+'Popup').click(function(){
		LoadSellPopup(attr_code,attr_desc,attr_coderef,title);
	});
}

function TextboxSellLogic(attr_code,attr_desc,attr_coderef,title){
	var sale_code = $.trim($('#'+attr_code).val());
	var branch_code = $.trim($('#'+attr_coderef).val());
	var channel = $.trim($('#channel').val());
	if(sale_code != ''){
		var dataString 	= 'className=com.eaf.orig.ulo.pl.ajax.GetSellLogic&returnType=0&attr_code='+attr_code+'&attr_desc='+attr_desc;
			dataString += '&branch_code='+branch_code+'&sale_code='+sale_code+'&channel='+channel;
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
					LoadSellPopup(attr_code,attr_desc,attr_coderef,title);
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

function LoadSellPopup(attr_code,attr_desc,attr_coderef,title){
	var value = $.trim($('#'+attr_code).val());
	var ref_value01 = $.trim($('#'+attr_coderef).val());
	var ref_value02 = $.trim($('#channel').val());
	
	var frameModule = (document.getElementById('frame-module')== null)? document.body :'.frame-module';
	var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadRecommendInformation&sale_id="+encodeURI($.trim(value))
						+"&textbox_code="+attr_code+"&textbox_desc="+attr_desc+"&ref_value01="+ref_value01+"&ref_value02="+ref_value02;		
	$dialog = $dialogEmpty;
	var width = 1000;
	var higth = $(window).height()-50;
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