$(document).ready(function(){
	$("#BRANCH_REGION_TRANSFER *").prop("disabled",true);
	$("#BRANCH_ZONE_TRANSFER *").prop("disabled",true);
	$("#DSA_EXPAND_ZONE_TRANSFER *").prop("disabled",true);
	$("#NBD_ZONE_TRANSFER *").prop("disabled",true);
	
	$("input[name=BRANCH_TYPE_BOX]").change(function(){
		if($("input[name=BRANCH_TYPE_BOX]").is(":checked")){
			$("#BRANCH_REGION_TRANSFER *").prop("disabled",false);
			$("#BRANCH_ZONE_TRANSFER *").prop("disabled",false);
		}else{
			$("#BRANCH_REGION_TRANSFER .removeAll").click();
			$("#BRANCH_ZONE_TRANSFER #select1").empty();
			$("#BRANCH_ZONE_TRANSFER #select2").empty();
			$("#BRANCH_REGION_TRANSFER *").prop("disabled",true);
			$("#BRANCH_ZONE_TRANSFER *").prop("disabled",true);
		}
	});
	
	$("input[name=DSA_TYPE_BOX]").change(function(){
		if($("input[name=DSA_TYPE_BOX]").is(":checked")){
			$("#DSA_EXPAND_ZONE_TRANSFER *").prop("disabled",false);
		}else{
			$("#DSA_EXPAND_ZONE_TRANSFER .removeAll").click();
			$("#DSA_EXPAND_ZONE_TRANSFER *").prop("disabled",true);
		}
	});
	
	$("input[name=NBD_TYPE_BOX]").change(function(){
		if($("input[name=NBD_TYPE_BOX]").is(":checked")){
			$("#NBD_ZONE_TRANSFER *").prop("disabled",false);
		}else{
			$("#NBD_ZONE_TRANSFER .removeAll").click();
			$("#NBD_ZONE_TRANSFER *").prop("disabled",true);
		}
	});
	
//	$("input[name=PRODUCT_TYPE_BOX][value=CC]").change(function(){
//		if($("input[name=PRODUCT_TYPE_BOX][value=CC]").is(":checked")){
//			$("input[name=PRODUCT_TYPE_BOX][value=CC_NEW]").prop("disabled",true);
//			$("input[name=PRODUCT_TYPE_BOX][value=CC_ADD]").prop("disabled",true);
//			$("input[name=PRODUCT_TYPE_BOX][value=CC_UP]").prop("disabled",true);
//		}else{
//			$("input[name=PRODUCT_TYPE_BOX][value=CC_NEW]").prop("disabled",false);
//			$("input[name=PRODUCT_TYPE_BOX][value=CC_ADD]").prop("disabled",false);
//			$("input[name=PRODUCT_TYPE_BOX][value=CC_UP]").prop("disabled",false);
//		}
//	});
	
//	$("input[name=PRODUCT_TYPE_BOX][value=CC_KEC]").change(function(){
//		if($("input[name=PRODUCT_TYPE_BOX][value=CC_KEC]").is(":checked")){
//			$("input[name=PRODUCT_TYPE_BOX][value=CC_NEW_KEC]").prop("disabled",true);
//			$("input[name=PRODUCT_TYPE_BOX][value=CC_ADD_KEC]").prop("disabled",true);
//			$("input[name=PRODUCT_TYPE_BOX][value=CC_UP_KEC]").prop("disabled",true);
//		}else{
//			$("input[name=PRODUCT_TYPE_BOX][value=CC_NEW_KEC]").prop("disabled",false);
//			$("input[name=PRODUCT_TYPE_BOX][value=CC_ADD_KEC]").prop("disabled",false);
//			$("input[name=PRODUCT_TYPE_BOX][value=CC_UP_KEC]").prop("disabled",false);
//		}
//	});
	getOperatingReport();
});	


function OR_REPORT_GENERATE_BUTTONActionJS(){
	displayHtmlElement('GENERATE_BUTTON_FLAG', FLAG_YES);

		var validateId = VALIDATE_OR_REPORT;
		var $data = $('#OR_REPORT *').serialize();
		validateFormAction(validateId,$data,getOperatingReportActionJS,DISPLAY_ERROR_SCREEN);
	
}
function getOperatingReportActionJS(){
	displayHtmlElement('GENERATE_BUTTON_FLAG', FLAG_YES);
	var dateFrom = $("input[name=DATE_FROM_CALENDAR]").val();
	var dateTo = $("input[name=DATE_TO_CALENDAR]").val();
	if(validateBetweenDate(dateFrom,dateTo)){
		getOperatingReport();
	}else{
		alertBox(MESSAGE_DATE_MORE);
	}
}
function getOperatingReport(){
	var className = 'com.eaf.orig.ulo.app.view.form.manual.GenerateOperatingReportForm';
	var data = $("form").serialize();
	var BRANCH_REGION_TRANSFER = $("#BRANCH_REGION_TRANSFER #select2 option").val();
	var BRANCH_ZONE_TRANSFER = $("#BRANCH_ZONE_TRANSFER #select2 option").val();
	var DSA_EXPAND_ZONE_TRANSFER = $("#DSA_EXPAND_ZONE_TRANSFER #select2 option").val();
	var NBD_ZONE_TRANSFER = $("#NBD_ZONE_TRANSFER #select2 option").val();
	if(BRANCH_REGION_TRANSFER === undefined){
		if($("input[name=BRANCH_TYPE_BOX]").is(":checked")){
			data = data+"&BRANCH_REGION_TRANSFER="+REPORT_VALUE_ALL;
		}
	}else{
		$('#BRANCH_REGION_TRANSFER #select2 option').each(function(i) {  
			$(this).attr("selected", "selected");  
		});
	}
	
	if(BRANCH_ZONE_TRANSFER === undefined){
		if($("input[name=BRANCH_TYPE_BOX]").is(":checked")){
			$("#BRANCH_ZONE_TRANSFER #select2 option").val(REPORT_VALUE_ALL);
			data = data+"&BRANCH_ZONE_TRANSFER="+REPORT_VALUE_ALL;
		}
	}else{
		$('#BRANCH_ZONE_TRANSFER #select2 option').each(function(i) {  
			$(this).attr("selected", "selected");  
		});
	}
	
	if(DSA_EXPAND_ZONE_TRANSFER === undefined){
		if($("input[name=DSA_TYPE_BOX]").is(":checked")){
			$("#DSA_EXPAND_ZONE_TRANSFER #select2 option").val(REPORT_VALUE_ALL);
			data = data+"&DSA_EXPAND_ZONE_TRANSFER="+REPORT_VALUE_ALL;
		}
	}else{
		$('#DSA_EXPAND_ZONE_TRANSFER #select2 option').each(function(i) {  
			$(this).attr("selected", "selected");  
		});
	}
	
	if(NBD_ZONE_TRANSFER === undefined){
		if($("input[name=NBD_TYPE_BOX]").is(":checked")){
			$("#NBD_ZONE_TRANSFER #select2 option").val(REPORT_VALUE_ALL);
			data = data+"&NBD_ZONE_TRANSFER="+REPORT_VALUE_ALL;
		}
	}else{
		$('#NBD_ZONE_TRANSFER #select2 option').each(function(i) {  
			$(this).attr("selected", "selected");  
		});
	}
	
	ajax(className, data, displayJSON);
}
function displayJSON(data){
	$("#generateReport_list").empty();
	var obj = $.parseJSON(data);
	console.log(obj);
	if(false == obj.isGenerateReport){
		alertBox(MESSAGE_MAX_REPORT_ON_REQUEST);
	}
	
	for(var i=0;i<obj.reportList.length;i++){
		var htmlBuild = '<tr>';
		htmlBuild += '<td>'+(i+1)+'</td>';
		htmlBuild += '<td class="text-left">'+obj.reportList[i].productCriteria+'</td>';
		htmlBuild += '<td>'+obj.reportList[i].createBy+'</td>';
		htmlBuild += '<td>'+obj.reportList[i].createDate+'</td>';
		htmlBuild += '</tr>';
		$("#generateReport_list").append(htmlBuild);
	}
}

function OR_REPORT_RESET_BUTTONActionJS(){
	$("form").trigger("reset");
	$("#BRANCH_REGION_TRANSFER *").prop("disabled",true);
	$("#BRANCH_ZONE_TRANSFER *").prop("disabled",true);
	$("#DSA_EXPAND_ZONE_TRANSFER *").prop("disabled",true);
	$("#NBD_ZONE_TRANSFER *").prop("disabled",true);
	$("input[name=PRODUCT_TYPE_BOX][value=CC_NEW]").prop("disabled",false);
	$("input[name=PRODUCT_TYPE_BOX][value=CC_ADD]").prop("disabled",false);
	$("input[name=PRODUCT_TYPE_BOX][value=CC_UP]").prop("disabled",false);
	$("input[name=PRODUCT_TYPE_BOX][value=CC_NEW_KEC]").prop("disabled",false);
	$("input[name=PRODUCT_TYPE_BOX][value=CC_ADD_KEC]").prop("disabled",false);
	$("input[name=PRODUCT_TYPE_BOX][value=CC_UP_KEC]").prop("disabled",false);
	$(".removeAll").click();
}
function CALENDAROnblurAction(element, mode, name){
	var DATE_FROM_CALENDAR = $("[name='DATE_FROM_CALENDAR']").val();
	var DATE_TO_CALENDAR = $("[name='DATE_TO_CALENDAR']").val();
//	alert(DATE_FROM_CALENDAR);
	if(!isEmpty(DATE_FROM_CALENDAR) && !isEmpty(DATE_TO_CALENDAR)){
		if(!validateBetweenDate(DATE_FROM_CALENDAR,DATE_TO_CALENDAR)){
			alertBox(MESSAGE_DATE_MORE,$.proxy(curserfield, this, element));  
		}
	}
}

function curserfield(element){
	focusElementActionJS(element, '');
}

function generateBranchZone(){
//	var className = 'com.eaf.orig.ulo.pl.app.utility.SelectTransferTool';
//	var data = $("form").serialize();
//	ajax(className, data, drawTable);

	var className = 'com.eaf.orig.ulo.pl.app.utility.SelectTransferTool';
	var data='';
	var selection2 = $("#select2 option");
	var opt='';
	var commar='';
	for (var i=0, iLen=selection2.length; i<iLen; i++) {
	    opt += commar+selection2[i].value;
	    commar=',';
	}
	if(opt.length>0){
		data = 'BRANCH_REGION_TRANSFER='+opt;
	}
	ajax(className, data, drawTable);

}

function drawTable(data){
	var obj = $.parseJSON(data);
	var zones = obj.zones;
	$("#BRANCH_ZONE_TRANSFER #select1").empty();
	$("#BRANCH_ZONE_TRANSFER #select2").empty();
	zones.forEach(function(entry){
		$("#BRANCH_ZONE_TRANSFER #select1").append("<option value="+entry+">"+entry+"</option>");
	});
}