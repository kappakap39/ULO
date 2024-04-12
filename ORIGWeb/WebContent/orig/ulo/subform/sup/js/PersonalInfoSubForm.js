
function TH_BIRTH_DATEActionJS(){ 
	try{
		var THDate = $("[name='TH_BIRTH_DATE']").val();
		var r_day = THDate.split("/")[0];
		var r_month = THDate.split("/")[1];
		var r_year = THDate.split("/")[2];
		var ENDate = r_day+"/"+r_month+"/"+(parseInt(r_year)-543);
//		alert(ENDate);
		
		$("[name='EN_BIRTH_DATE']").val(ENDate);
	}catch(exception){
		errorException(exception);
	}
}
function EN_BIRTH_DATEActionJS(){
	try{
		var ENDate = $("[name='EN_BIRTH_DATE']").val();
		var r_day = ENDate.split("/")[0];
		var r_month = ENDate.split("/")[1];
		var r_year = ENDate.split("/")[2];
		var THDate = r_day+"/"+r_month+"/"+(parseInt(r_year)+543);
//		alert(ENDate);
		$("[name='TH_BIRTH_DATE']").val(THDate);
	}catch(exception){
		errorException(exception);
	}
}

function ID_NOActionJS(){
//	var CID_TYPE = $("[name='CID_TYPE']").val();
//	alert("CID_TYPE >>"+CID_TYPE);

}

function CID_TYPEActionJS(){
	try{
		var CID_TYPE = $("[name='CID_TYPE']").val();
		
		if(CID_TYPE != "02" && CID_TYPE != "03"){ //select “หนังสือเดินทาง  or ใบทะเบียนต่างด้าว”
			$("[name='VISA_TYPE']").prop('selectedIndex', 0);  
			$("[name='VISA_TYPE']").prop("disabled",true); 

			disableAllDiv("VISA_EXPIRE_DATE",'input,img',true); 
			disableAllDiv("WORK_PERMIT_NO",'input,img',true); 
			disableAllDiv("WORK_PERMIT_EXPIRE_DATE",'input,img',true); 
			disableAllDiv("PASSPORT_EXPIRE_DATE",'input,img',true); 
		}
		else {
			$("[name='VISA_TYPE']").prop("disabled",false);  
					
			disableAllDiv("VISA_EXPIRE_DATE",'input,img',false); 
			disableAllDiv("WORK_PERMIT_NO",'input,img',false); 
			disableAllDiv("WORK_PERMIT_EXPIRE_DATE",'input,img',false); 
			disableAllDiv("PASSPORT_EXPIRE_DATE",'input,img',false); 
		}
		
		if(CID_TYPE == "01"){  //select ประจำตัวประชาชน
			$("[name='NATIONALITY']").val("TH");  
		}else {
			$("[name='NATIONALITY']").prop('selectedIndex', 0); 
		}
	}catch(exception){
		errorException(exception);
	}
}

function disableAllDiv(fieldName,type,disable)
{
//	$('#InputField_VISA_EXPIRE_DATE input,#InputField_VISA_EXPIRE_DATE img').prop("disabled",true); 
//	$('#InputField_WORK_PERMIT_EXPIRE_DATE').children('input,img').prop("disabled",true); 
	$('#InputField_'+fieldName+'').find(type).prop("disabled",disable); 
	if(type.indexOf("input") > -1 && disable == true)
	{
		$("[name='"+fieldName+"']").prop('selectedIndex', 0);  
		$("[name='"+fieldName+"']").val("");
	}
}
