/**
 * Rawi Songchaisin
 */
$(document).ready(function(){		
	initFollowDetail();
});
function initFollowDetail(){
	loadInformation();
	loadTelephone();	
}
function otherTelNumber(){
	DestoryTextBoxTelEngine('#fdOtherPhoneNo');
	if($('#fdPhoneNo').val() == '99'){
		$('#fdOtherPhoneNo').removeClass("textbox-tel-view").addClass("textbox-tel");
		$('#fdOtherPhoneNo').removeClass('textboxReadOnly');
		$('#fdOtherPhoneNo').addClass('textbox');
		$('#fdOtherPhoneNo').attr('readOnly',false);
		TextBoxTelEngine('#fdOtherPhoneNo');
	}else{
		$('#fdOtherPhoneNo').removeClass("textbox-tel").addClass("textbox-tel-view");
		$('#fdOtherPhoneNo').removeClass('textbox');
		$('#fdOtherPhoneNo').addClass('textboxReadOnly');
		$('#fdOtherPhoneNo').attr('readOnly',true);
		$('#fdOtherPhoneNo').val('');
	}
}
function loadInformation(personal){
	var dataString = $("#followDetail *").serialize();
		dataString += "&className=com.eaf.orig.ulo.pl.app.rule.servlet.LoadFollowUpDetail&returnType=0";
	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,	
		dataType: "json",
		success : function(data ,status ,xhr){
			jsonDisplayElementById(data);
		},
		error : function(data){
		}
	});	
}
function loadTelephone(){
	var dataString =  $("#followDetail *").serialize();
		dataString += "&className=com.eaf.orig.ulo.pl.app.rule.servlet.LoadFollowUpDetailTel&returnType=0";
	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,	
		dataType: "json",
		success : function(data ,status ,xhr){
			jsonDisplayElementById(data);
			otherTelNumber();
		},
		error : function(data){
		}
	});	
}
function validate(){
	if($('#fdPersonalType').val() == null || $('#fdPersonalType').val() == ''){
		alertMassage(FU_DETAIL_PERSONAL_ERR);
		return false;
	}
	if($('#fdPhoneNo').val() == null || $('#fdPhoneNo').val() == ''){
		alertMassage(FU_DETAIL_PHONE_ERR);
		return false;
	}
	if($('#fdPhoneNo').val() == '99' && ($('#fdOtherPhoneNo').val() == null || $('#fdOtherPhoneNo').val() == '')){
		alertMassage(FU_DETAIL_OTHER_PHONE_ERR);
		return false;
	}
	if($('#fdPhoneStatus').val() == null || $('#fdPhoneStatus').val() == ''){
		alertMassage(FU_DETAIL_CONTACT_RESULT_ERR);
		return false;
	}
	return true;
}
function addFollowDetailData(){
	if(!validate()) return;
	var dataString  = $("#followDetail *").serialize();
		dataString += '&action=SaveFollowDetailWebAction&handleForm=N';
	blockScreen();
	$.post('FrontController',dataString,function(data,status,xhr){	
		unblockScreen();
		$('#FollowDetailNotFound').remove();
		jsonDisplayElementById(data);
		ClearValue();
    }).error(function(){
	    unblockScreen();
    });
}
function ClearValue(){
	$('#fdPersonalType').val('');
	$('#fdAddressType').val('');
	$('#fdPhoneStatus').val('');
	$('#fdPhoneNo').val('');
	$('#fdRemark').val('');
	$('#fdOtherPhoneNo').val('');
	initFollowDetail();
}