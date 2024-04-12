 var DATE_MSG = '\u0E27\u0E31\u0E19\u0E17\u0E35\u0E48\u0E17\u0E33\u0E40\u0E23\u0E37\u0E48\u0E2D\u0E07\u0E15\u0E49\u0E2D\u0E07\u0E19\u0E49\u0E2D\u0E22\u0E01\u0E27\u0E48\u0E32\u0E27\u0E31\u0E19\u0E04\u0E23\u0E1A\u0E01\u0E33\u0E2B\u0E19\u0E14\u0E04\u0E37\u0E19\u0E40\u0E2D\u0E01\u0E2A\u0E32\u0E23';

function DM_DOC_MANAGEMENTActionJS(){
	var actionValue = $('[name="DM_DOC_MANAGEMENT"]:checked').val();
	console.log("actionValue : "+actionValue);
	$('[name="DM_DOC_MANAGEMENT"]').val(actionValue);
	if(DM_ACTION_DOC_RETURN==actionValue){
		DM_RETURN_DOCUMENTActionJS();
	}else if(DM_ACTION_DOC_BORROW==actionValue){
//		var date = new Date().toJSON().slice(0,10);
		var date = $("[name=NOW_DATE]").val();
//		var data = date;//.split("-");
//		var thaiDate = data[2]+"/"+data[1]+"/"+data[0];
		$("[name=DM_ACTION_DATE]").val(date);
		CALENDAROnblurAction('DM_ACTION_DATE','EDIT','DM_ACTION_DATE');
	}
} 

function isValidDateFromTo (fieldNameFrom, fieldNameTo,currentFieldName) {
	var dateFrom = $('[name="'+fieldNameFrom+'"]').val();
	var dateTo = $('[name="'+fieldNameTo+'"]').val();
	
	try {	
		if ((dateFrom != '') && (dateTo != ''))  {
			var fromSprit = dateFrom.split('/');
			var toSprit = dateTo.split('/');				
			var toInt = parseInt(toSprit [2] + toSprit [1] +toSprit [0]); 
			var fromInt = parseInt(fromSprit [2] + fromSprit [1] +fromSprit [0]); 
//			if (fromInt > toInt ) {	
//			alert(validateBetweenDate(dateFrom,dateTo));
			if(!validateBetweenDate(dateFrom,dateTo)){
				alertBox(DATE_MSG);
				$('[name="'+currentFieldName+'"]').val('');
			}
		}	
		 
	} catch (e) { 
		alert("ERROR : Checking date from - to\n -"+e.description) ;
	}
}


function calDueDate(actionDate,addDay){
	if(""!=actionDate){
		var dateSprit = actionDate.split('/');					
		var actionDateValue = new Date(dateSprit [2]-543,dateSprit [1]-1,dateSprit [0]);
		var returnTime = new Date(actionDateValue.getTime() + addDay*24*60*60*1000);
		
		var day  = returnTime.getDate();  if(day<10){day = "0"+day;}
		var month =returnTime.getMonth()+1; if(month<10){month = "0"+month;}
		var year = returnTime.getFullYear()+543;
		var dueDate = day+"/"+month+"/"+year;	
		if($('[name="DM_DOC_MANAGEMENT"]').val() !=DM_ACTION_DOC_RETURN){
			$('[name="DM_DUE_DATE"]').val(dueDate);	
		}
		
	}
} 

function DM_REQUESTED_USERActionJS(){
	$('[name="DM_REQUESTED_USER_DESC"]').val($("[name='DM_REQUESTED_USER'] option:selected").text());	
	var className = 'com.eaf.orig.ulo.app.view.util.ajax.GetBranchDesc';
	var data ="&DM_REQUESTED_USER="+$('[name="DM_REQUESTED_USER"]').val();
		 ajax(className, data,displayJSONHtml);	
}

 
function displayJSONHtml(data){
	try{
		var $JSON = $.parseJSON(data);
		if($.isEmptyObject($JSON)){
			return;
		}
		$.map($JSON, function(item){
			var elementId = item.id;
			var elementValue = item.value;
			if("AUTH_USER"==elementId){
				if(FLAG_YES==elementValue){
					$('#DM_DOC_MANAGEMENT_WITHDRAW').attr('disabled',false);
				}else{
					$('#DM_DOC_MANAGEMENT_WITHDRAW').attr('disabled',true);
					$('#DM_DOC_MANAGEMENT_WITHDRAW').prop('checked',false);
				}				
			}else{
				$('[name='+elementId+']').val(elementValue);
			}
		});
	}catch(e){		
	}
}

function DM_RETURN_DOCUMENTActionJS(){
	
	var className = 'com.eaf.orig.ulo.app.view.util.ajax.DMDocumentManageForm';
	var data;
	console.clear();
	console.log(data);
	ajax(className, data, displayJSON);

}

function displayJSON(data){
	var obj = $.parseJSON(data);
	console.log(obj);
	
	displayHtmlElement("DM_DEPARTMENT", obj.BRANCH);
	targetDisplayHtml("DM_DEPARTMENT", MODE_VIEW,"DM_DEPARTMENT");
	
	displayHtmlElement("DM_OFFICE_PHONE_NO", "");
	targetDisplayHtml("DM_OFFICE_PHONE_NO", MODE_VIEW,"DM_OFFICE_PHONE_NO");
	
	displayHtmlElement("DM_MOBILE_NO", "");
	targetDisplayHtml("DM_MOBILE_NO", MODE_VIEW,"DM_MOBILE_NO");
	
	displayHtmlElement("DM_DUE_DATE", $('[name="RETURN_DUEDATE_TEMP"]').val());
	targetDisplayHtml("DM_DUE_DATE", MODE_VIEW,"DM_DUE_DATE");
}

function CALENDAROnblurAction(elementName,mode,fieldName){
	if("DM_ACTION_DATE"==fieldName){
		var actionValue = $('[name="DM_DOC_MANAGEMENT"]:checked').val();
		if(!DM_DOC_MANAGEMENT_RETURN==actionValue){
			isValidDateFromTo('DM_ACTION_DATE','DM_DUE_DATE',"DM_ACTION_DATE");
			calDueDate($('[name="DM_ACTION_DATE"]').val(),$('[name="WH_BORROW_DOC_DAY"]').val());	
		}
	}else if("DM_DUE_DATE"==fieldName){
		isValidDateFromTo('DM_ACTION_DATE','DM_DUE_DATE',"DM_DUE_DATE");
	}
	
}

 
